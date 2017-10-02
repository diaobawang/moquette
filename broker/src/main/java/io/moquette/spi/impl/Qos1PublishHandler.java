/*
 * Copyright (c) 2012-2017 The original author or authors
 * ------------------------------------------------------
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 * The Eclipse Public License is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * The Apache License v2.0 is available at
 * http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */

package io.moquette.spi.impl;

import static io.moquette.spi.impl.ProtocolProcessor.asStoredMessage;
import static io.moquette.spi.impl.Utils.readBytesAndRewind;
import static io.netty.handler.codec.mqtt.MqttMessageIdVariableHeader.from;
import static io.netty.handler.codec.mqtt.MqttQoS.AT_MOST_ONCE;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.InvalidProtocolBufferException;
import com.qiniu.util.Auth;

import io.moquette.server.ConnectionDescriptorStore;
import io.moquette.server.config.QiniuConfig;
import io.moquette.server.netty.NettyUtils;
import io.moquette.spi.IMessagesStore;
import io.moquette.spi.ISessionsStore;
import io.moquette.spi.impl.subscriptions.Topic;
import io.moquette.spi.security.IAuthorizator;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.handler.codec.mqtt.MqttFixedHeader;
import io.netty.handler.codec.mqtt.MqttMessageType;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import win.liyufan.im.ErrorCode;
import win.liyufan.im.IMTopic;
import win.liyufan.im.extended.mqttmessage.ModifiedMqttPubAckMessage;
import win.liyufan.im.proto.AddGroupMemberRequestOuterClass.AddGroupMemberRequest;
import win.liyufan.im.proto.ConversationOuterClass.ConversationType;
import win.liyufan.im.proto.CreateGroupRequestOuterClass.CreateGroupRequest;
import win.liyufan.im.proto.DismissGroupRequestOuterClass.DismissGroupRequest;
import win.liyufan.im.proto.GetUploadTokenResultOuterClass.GetUploadTokenResult;
import win.liyufan.im.proto.GroupOuterClass.GroupInfo;
import win.liyufan.im.proto.GroupOuterClass.GroupType;
import win.liyufan.im.proto.IDBufOuterClass.IDBuf;
import win.liyufan.im.proto.IDListBufOuterClass.IDListBuf;
import win.liyufan.im.proto.MessageOuterClass.Message;
import win.liyufan.im.proto.ModifyGroupInfoRequestOuterClass.ModifyGroupInfoRequest;
import win.liyufan.im.proto.NotifyMessageOuterClass.PullType;
import win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult;
import win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult;
import win.liyufan.im.proto.PullMessageRequestOuterClass.PullMessageRequest;
import win.liyufan.im.proto.PullMessageResultOuterClass.PullMessageResult;
import win.liyufan.im.proto.PullUserRequestOuterClass;
import win.liyufan.im.proto.PullUserResultOuterClass;
import win.liyufan.im.proto.QuitGroupRequestOuterClass.QuitGroupRequest;
import win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest;
import win.liyufan.im.proto.TransferGroupRequestOuterClass;

class Qos1PublishHandler extends QosPublishHandler {

    private static final Logger LOG = LoggerFactory.getLogger(Qos1PublishHandler.class);

    private final IMessagesStore m_messagesStore;
    private final BrokerInterceptor m_interceptor;
    private final ConnectionDescriptorStore connectionDescriptors;
    private final MessagesPublisher publisher;
    private final ISessionsStore m_sessionStore;

    public Qos1PublishHandler(IAuthorizator authorizator, IMessagesStore messagesStore, BrokerInterceptor interceptor,
                              ConnectionDescriptorStore connectionDescriptors, MessagesPublisher messagesPublisher,
                              ISessionsStore sessionStore) {
        super(authorizator);
        this.m_messagesStore = messagesStore;
        this.m_interceptor = interceptor;
        this.connectionDescriptors = connectionDescriptors;
        this.publisher = messagesPublisher;
        this.m_sessionStore = sessionStore;
    }

    private long saveAndPublish(String username, String clientID, Message message, long timestamp) {
    	Set<String> notifyReceivers = null;
		if (message.getConversation().getType() != ConversationType.ConversationType_ChatRoom) {
			notifyReceivers = new LinkedHashSet<>();
		}
		
		message = m_messagesStore.storeMessage(username, clientID, message, timestamp);
		PullType pullType = m_messagesStore.getNotifyReceivers(username, message, notifyReceivers);
		this.publisher.publish2Receivers(message.getMessageId(), notifyReceivers, clientID, pullType);
		return message.getMessageId();
	}

	boolean imHandler(String clientID, int messageID, String fromUser, MqttPublishMessage msg) {
        String topic = msg.variableHeader().topicName();
        boolean isConsumed = false;
        ByteBuf ackPayload = Unpooled.buffer();
        ErrorCode errorCode = ErrorCode.ERROR_CODE_SUCCESS;
        ackPayload.writeByte(errorCode.getCode());

        try {
            if (IMTopic.SendMessageTopic.equals(topic)) {
                isConsumed = true;
                ByteBuf payload = msg.payload();
                byte[] payloadContent = readBytesAndRewind(payload);
                Message message = Message.parseFrom(payloadContent);
                if (message != null) {
                    long timestamp = System.currentTimeMillis();
                    if (message.getConversation().getType() == ConversationType.ConversationType_Group &&
                        !m_messagesStore.isMemberInGroup(fromUser, message.getConversation().getTarget())) {
                        errorCode = ErrorCode.ERROR_CODE_NOT_IN_GROUP;
                    } else {
                        long messageId = saveAndPublish(fromUser, clientID, message, timestamp);
                        ackPayload = ackPayload.capacity(20);
                        ackPayload.writeLong(messageId);
                        ackPayload.writeLong(timestamp);
                    }
                } else {
                    errorCode = ErrorCode.ERROR_CODE_INVALID_MESSAGE;
                }
            } else if (IMTopic.PullMessageTopic.equals(topic)) {
                isConsumed = true;
                ByteBuf payload = msg.payload();
                byte[] payloadContent = readBytesAndRewind(payload);
                PullMessageRequest request = PullMessageRequest.parseFrom(payloadContent);

                PullMessageResult.Builder builder = PullMessageResult.newBuilder();
                long head = m_messagesStore.fetchMessage(fromUser, clientID, request.getId(), builder);
                PullMessageResult result = builder.build();

                ackPayload.writeBytes(result.toByteArray());
            } else if (IMTopic.CreateGroupTopic.equals(topic)) {
                isConsumed = true;

                ByteBuf payload = msg.payload();
                byte[] payloadContent = readBytesAndRewind(payload);
                CreateGroupRequest request = CreateGroupRequest.parseFrom(payloadContent);
                GroupInfo groupInfo = m_messagesStore.createGroup(fromUser, request.getGroup().getGroupInfo(), request.getGroup().getMembersList());
                if (groupInfo != null && request.hasNotifyContent()) {
                    Message.Builder builder = Message.newBuilder().setContent(request.getNotifyContent());
                    builder.setConversation(builder.getConversationBuilder().setType(ConversationType.ConversationType_Group).setTarget(groupInfo.getTargetId()));
                    long timestamp = System.currentTimeMillis();
                    builder.setFromUser(fromUser);
                    long messageId = saveAndPublish(fromUser, null, builder.build(), timestamp);
                }

                ackPayload.writeBytes(groupInfo.getTargetId().getBytes());
            } else if (IMTopic.AddGroupMemberTopic.equals(topic)) {
                isConsumed = true;

                ByteBuf payload = msg.payload();
                byte[] payloadContent = readBytesAndRewind(payload);
                AddGroupMemberRequest request = AddGroupMemberRequest.parseFrom(payloadContent);
                errorCode = m_messagesStore.addGroupMembers(fromUser, request.getGroupId(), request.getAddedMemberList());
                if (errorCode == ErrorCode.ERROR_CODE_SUCCESS && request.hasNotifyContent()) {
                    Message.Builder builder = Message.newBuilder().setContent(request.getNotifyContent());
                    builder.setConversation(builder.getConversationBuilder().setType(ConversationType.ConversationType_Group).setTarget(request.getGroupId()));
                    long timestamp = System.currentTimeMillis();
                    builder.setFromUser(fromUser);
                    long messageId = saveAndPublish(fromUser, null, builder.build(), timestamp);
                }
            } else if (IMTopic.KickoffGroupMemberTopic.equals(topic)) {
                isConsumed = true;

                ByteBuf payload = msg.payload();
                byte[] payloadContent = readBytesAndRewind(payload);
                RemoveGroupMemberRequest request = RemoveGroupMemberRequest.parseFrom(payloadContent);


                GroupInfo groupInfo = m_messagesStore.getGroupInfo(request.getGroupId());
                if (groupInfo == null) {
                    errorCode = ErrorCode.ERROR_CODE_GROUP_NOT_EXIST;

                } else if ((groupInfo.getType() == GroupType.GroupType_Normal || groupInfo.getType() == GroupType.GroupType_Restricted)
                    && groupInfo.getOwner() != null && groupInfo.getOwner().equals(fromUser)) {

                    //send notify message first, then kickoff the member
                    if (request.hasNotifyContent()) {
                        Message.Builder builder = Message.newBuilder().setContent(request.getNotifyContent());
                        builder.setConversation(builder.getConversationBuilder().setType(ConversationType.ConversationType_Group).setTarget(request.getGroupId()));
                        long timestamp = System.currentTimeMillis();
                        builder.setFromUser(fromUser);
                        long messageId = saveAndPublish(fromUser, null, builder.build(), timestamp);
                    }
                    errorCode = m_messagesStore.kickoffGroupMembers(fromUser, request.getGroupId(), request.getRemovedMemberList());
                } else {
                    errorCode = ErrorCode.ERROR_CODE_GROUP_NOT_RIGHT;
                }
            } else if (IMTopic.QuitGroupTopic.equals(topic)) {
                isConsumed = true;

                ByteBuf payload = msg.payload();
                byte[] payloadContent = readBytesAndRewind(payload);
                QuitGroupRequest request = QuitGroupRequest.parseFrom(payloadContent);


                GroupInfo groupInfo = m_messagesStore.getGroupInfo(request.getGroupId());
                if (groupInfo == null) {
                    errorCode = m_messagesStore.quitGroup(fromUser, request.getGroupId());
                } else {
                    //send notify message first, then quit group
                    if (request.hasNotifyContent()) {
                        Message.Builder builder = Message.newBuilder().setContent(request.getNotifyContent());
                        builder.setConversation(builder.getConversationBuilder().setType(ConversationType.ConversationType_Group).setTarget(request.getGroupId()));
                        long timestamp = System.currentTimeMillis();
                        builder.setFromUser(fromUser);
                        long messageId = saveAndPublish(fromUser, null, builder.build(), timestamp);
                    }
                    errorCode = m_messagesStore.quitGroup(fromUser, request.getGroupId());
                }
            } else if (IMTopic.DismissGroupTopic.equals(topic)) {
                isConsumed = true;

                ByteBuf payload = msg.payload();
                byte[] payloadContent = readBytesAndRewind(payload);
                DismissGroupRequest request = DismissGroupRequest.parseFrom(payloadContent);

                GroupInfo groupInfo = m_messagesStore.getGroupInfo(request.getGroupId());
                if (groupInfo == null) {
                    errorCode = m_messagesStore.dismissGroup(fromUser, request.getGroupId());

                } else if ((groupInfo.getType() == GroupType.GroupType_Normal || groupInfo.getType() == GroupType.GroupType_Restricted)
                    && groupInfo.getOwner() != null && groupInfo.getOwner().equals(fromUser)) {

                    //send notify message first, then dismiss group
                    if (request.hasNotifyContent()) {
                        Message.Builder builder = Message.newBuilder().setContent(request.getNotifyContent());
                        builder.setConversation(builder.getConversationBuilder().setType(ConversationType.ConversationType_Group).setTarget(request.getGroupId()));
                        long timestamp = System.currentTimeMillis();
                        builder.setFromUser(fromUser);
                        long messageId = saveAndPublish(fromUser, null, builder.build(), timestamp);
                    }
                    errorCode = m_messagesStore.dismissGroup(fromUser, request.getGroupId());
                } else {
                    errorCode = ErrorCode.ERROR_CODE_GROUP_NOT_RIGHT;
                }
            } else if (IMTopic.ModifyGroupInfoTopic.equals(topic)) {
                isConsumed = true;

                ByteBuf payload = msg.payload();
                byte[] payloadContent = readBytesAndRewind(payload);
                ModifyGroupInfoRequest request = ModifyGroupInfoRequest.parseFrom(payloadContent);
                errorCode = m_messagesStore.modifyGroupInfo(fromUser, request.getGroupInfo());
                if (errorCode == ErrorCode.ERROR_CODE_SUCCESS && request.hasNotifyContent()) {
                    Message.Builder builder = Message.newBuilder().setContent(request.getNotifyContent());
                    builder.setConversation(builder.getConversationBuilder().setType(ConversationType.ConversationType_Group).setTarget(request.getGroupInfo().getTargetId()));
                    long timestamp = System.currentTimeMillis();
                    builder.setFromUser(fromUser);
                    long messageId = saveAndPublish(fromUser, null, builder.build(), timestamp);
                }

            } else if (IMTopic.GetGroupInfoTopic.equals(topic)) {
                isConsumed = true;

                ByteBuf payload = msg.payload();
                byte[] payloadContent = readBytesAndRewind(payload);
                IDListBuf request = IDListBuf.parseFrom(payloadContent);
                List<GroupInfo> infos = m_messagesStore.getGroupInfos(request.getIdList());

                PullGroupInfoResult result = PullGroupInfoResult.newBuilder().addAllInfo(infos).build();

                ackPayload.writeBytes(result.toByteArray());
            } else if (IMTopic.GetGroupMemberTopic.equals(topic)) {
                isConsumed = true;

                ByteBuf payload = msg.payload();
                byte[] payloadContent = readBytesAndRewind(payload);
                IDBuf request = IDBuf.parseFrom(payloadContent);

                List<String> members = m_messagesStore.getGroupMembers(request.getId());

                PullGroupMemberResult result = PullGroupMemberResult.newBuilder().addAllMember(members).build();

                ackPayload.writeBytes(result.toByteArray());
            } else if (IMTopic.GetQiniuUploadTokenTopic.equals(topic)) {
                isConsumed = true;

                ByteBuf payload = msg.payload();
                byte[] payloadContent = readBytesAndRewind(payload);
                int type = payloadContent[0];

                Auth auth = Auth.create(QiniuConfig.QINIU_ACCESS_KEY, QiniuConfig.QINIU_SECRET_KEY);
                String token = auth.uploadToken(QiniuConfig.QINIU_BUCKET_NAME);

                GetUploadTokenResult result = GetUploadTokenResult.newBuilder().setDomain(QiniuConfig.QINIU_BUCKET_DOMAIN)
                    .setServer(QiniuConfig.QINIU_SERVER_URL)
                    .setToken(token).build();
                ackPayload.writeBytes(result.toByteArray());
            } else if (IMTopic.GetMyGroupsTopic.equals(topic)) {
                isConsumed = true;

                List<String> members = m_messagesStore.getMyGroups(fromUser);

                IDListBuf result = IDListBuf.newBuilder().addAllId(members).build();
                ackPayload.writeBytes(result.toByteArray());
            } else if(IMTopic.TransferGroupTopic.equals(topic)) {
                isConsumed = true;

                ByteBuf payload = msg.payload();
                byte[] payloadContent = readBytesAndRewind(payload);
                TransferGroupRequestOuterClass.TransferGroupRequest request = TransferGroupRequestOuterClass.TransferGroupRequest.parseFrom(payloadContent);
                errorCode = m_messagesStore.transferGroup(fromUser, request.getGroupId(), request.getNewOwner());
                if (errorCode == ErrorCode.ERROR_CODE_SUCCESS && request.hasNotifyContent()) {
                    Message.Builder builder = Message.newBuilder().setContent(request.getNotifyContent());
                    builder.setConversation(builder.getConversationBuilder().setType(ConversationType.ConversationType_Group).setTarget(request.getGroupId()));
                    long timestamp = System.currentTimeMillis();
                    builder.setFromUser(fromUser);
                    long messageId = saveAndPublish(fromUser, null, builder.build(), timestamp);
                }
            } else if(IMTopic.GetUserInfoTopic.equals(topic)) {
                isConsumed = true;
                ByteBuf payload = msg.payload();
                byte[] payloadContent = readBytesAndRewind(payload);
                PullUserRequestOuterClass.PullUserRequest request = PullUserRequestOuterClass.PullUserRequest.parseFrom(payloadContent);
                PullUserResultOuterClass.PullUserResult.Builder resultBuilder = PullUserResultOuterClass.PullUserResult.newBuilder();

                errorCode = m_messagesStore.getUserInfo(request.getRequestList(), resultBuilder);
                if (errorCode == ErrorCode.ERROR_CODE_SUCCESS) {
                    ackPayload.writeBytes(resultBuilder.build().toByteArray());
                }
            } else {
                isConsumed = true;
                errorCode = ErrorCode.ERROR_CODE_UNKNOWN_METHOD;
            }

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            errorCode = ErrorCode.ERROR_CODE_INVALID_DATA;
        } catch (Exception e) {
            e.printStackTrace();
            errorCode = ErrorCode.ERROR_CODE_SERVER_ERROR;
        }

        if (isConsumed) {
            ackPayload.setByte(0, errorCode.getCode());
            sendPubAck(clientID, messageID, ackPayload);
        }

        return isConsumed;
    }
    
    void receivedPublishQos1(Channel channel, MqttPublishMessage msg) {
        // verify if topic can be write
        final Topic topic = new Topic(msg.variableHeader().topicName());
        String clientID = NettyUtils.clientID(channel);
        String username = NettyUtils.userName(channel);
        if (!m_authorizator.canWrite(topic, username, clientID)) {
            LOG.error("MQTT client is not authorized to publish on topic. CId={}, topic={}", clientID, topic);
            return;
        }

        final int messageID = msg.variableHeader().messageId();

        if (imHandler(clientID, messageID, username, msg)) {
            return;
        }

        // route message to subscribers
        IMessagesStore.StoredMessage toStoreMsg = asStoredMessage(msg);
        toStoreMsg.setClientID(clientID);

        this.publisher.publish2Subscribers(toStoreMsg, topic, messageID);

        sendPubAck(clientID, messageID, null);

        if (msg.fixedHeader().isRetain()) {
            if (!msg.payload().isReadable()) {
                m_messagesStore.cleanRetained(topic);
            } else {
                // before wasn't stored
                m_messagesStore.storeRetained(topic, toStoreMsg);
            }
        }

        m_interceptor.notifyTopicPublished(msg, clientID, username);
    }

    private void sendPubAck(String clientId, int messageID, ByteBuf payload) {
        LOG.trace("sendPubAck invoked");
        MqttFixedHeader fixedHeader = new MqttFixedHeader(MqttMessageType.PUBACK, false, AT_MOST_ONCE, false, 0);
        ModifiedMqttPubAckMessage pubAckMessage = new ModifiedMqttPubAckMessage(fixedHeader, from(messageID), payload);

        try {
            if (connectionDescriptors == null) {
                throw new RuntimeException("Internal bad error, found connectionDescriptors to null while it should " +
                    "be initialized, somewhere it's overwritten!!");
            }
            LOG.debug("clientIDs are {}", connectionDescriptors);
            if (!connectionDescriptors.isConnected(clientId)) {
                throw new RuntimeException(String.format("Can't find a ConnectionDescriptor for client %s in cache %s",
                    clientId, connectionDescriptors));
            }
            connectionDescriptors.sendMessage(pubAckMessage, messageID, clientId);
        } catch (Throwable t) {
            LOG.error(null, t);
        }
    }

}
