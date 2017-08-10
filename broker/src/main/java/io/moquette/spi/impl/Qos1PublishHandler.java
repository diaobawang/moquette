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

import io.moquette.server.ConnectionDescriptorStore;
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
import io.netty.handler.codec.mqtt.MqttPubAckMessage;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import win.liyufan.im.IMTopic;
import win.liyufan.im.extended.mqttmessage.ModifiedMqttPubAckMessage;
import win.liyufan.im.proto.AddGroupMemberRequestOuterClass.AddGroupMemberRequest;
import win.liyufan.im.proto.ConversationOuterClass.ConversationType;
import win.liyufan.im.proto.CreateGroupRequestOuterClass.CreateGroupRequest;
import win.liyufan.im.proto.DismissGroupRequestOuterClass.DismissGroupRequest;
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
import win.liyufan.im.proto.QuitGroupRequestOuterClass.QuitGroupRequest;
import win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.protobuf.InvalidProtocolBufferException;

import static io.moquette.spi.impl.ProtocolProcessor.asStoredMessage;
import static io.moquette.spi.impl.Utils.readBytesAndRewind;
import static io.netty.handler.codec.mqtt.MqttMessageIdVariableHeader.from;
import static io.netty.handler.codec.mqtt.MqttQoS.AT_MOST_ONCE;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
		Long messageId = 0L;
		PullType pullType = m_messagesStore.storeMessage(username, clientID, message, notifyReceivers, timestamp, messageId);
		this.publisher.publish2Receivers(messageId, notifyReceivers, clientID, pullType);
		return messageId;
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
        if (topic.getTopic().equals(IMTopic.SendMessageTopic)) {
            try {
                ByteBuf payload = msg.payload();
                byte[] payloadContent = readBytesAndRewind(payload);
    			Message message = Message.parseFrom(payloadContent);
    			if (message != null) {
    				long timestamp = System.currentTimeMillis();
    				long messageId = saveAndPublish(username, clientID, message, timestamp);
    				
    				ByteBuf ack = Unpooled.buffer(16);
    				ack.writeLong(messageId);
	                ack.writeLong(timestamp);
	                sendPubAck(clientID, messageID, ack);
	                return;
    			}
    		} catch (InvalidProtocolBufferException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
            sendPubAck(clientID, messageID, null);
            return;
		} else if (topic.getTopic().equals(IMTopic.PullMessageTopic)) {
			try {
				ByteBuf payload = msg.payload();
	            byte[] payloadContent = readBytesAndRewind(payload);
				PullMessageRequest request = PullMessageRequest.parseFrom(payloadContent);

				PullMessageResult.Builder builder = PullMessageResult.newBuilder();
				long head = m_messagesStore.fetchMessage(username, clientID, request.getId(), builder);
				PullMessageResult result = builder.build();
				
				ByteBuf ack = Unpooled.buffer();
				ack.writeBytes(result.toByteArray());
				sendPubAck(clientID, messageID, ack);
				return;
			} catch (InvalidProtocolBufferException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sendPubAck(clientID, messageID, null);
			return;
		} else if (topic.getTopic().equals(IMTopic.CreateGroupTopic)) {
			try {
				ByteBuf payload = msg.payload();
	            byte[] payloadContent = readBytesAndRewind(payload);
				CreateGroupRequest request = CreateGroupRequest.parseFrom(payloadContent);
				int errorCode = m_messagesStore.createGroup(username, request.getGroup().getGroupInfo(), request.getGroup().getMembersList());
				if (errorCode == 0 && request.hasNotifyContent()) {
					Message.Builder builder = Message.newBuilder().setContent(request.getNotifyContent());
					builder.setConversation(builder.getConversationBuilder().setType(ConversationType.ConversationType_Group).setTarget(request.getGroup().getGroupInfo().getTargetId()));
					long timestamp = System.currentTimeMillis();
					builder.setFromUser(username);
					long messageId = saveAndPublish(username, null, builder.build(), timestamp);
				}
				ByteBuf ack = Unpooled.buffer();
				ack.writeInt(errorCode);
				sendPubAck(clientID, messageID, ack);
				return;
			} catch (InvalidProtocolBufferException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sendPubAck(clientID, messageID, null);
			return;
		} else if (topic.getTopic().equals(IMTopic.AddGroupMemberTopic)) {
			try {
				ByteBuf payload = msg.payload();
	            byte[] payloadContent = readBytesAndRewind(payload);
				AddGroupMemberRequest request = AddGroupMemberRequest.parseFrom(payloadContent);
				int errorCode = m_messagesStore.addGroupMembers(username, request.getGroupId(), request.getAddedMemberList());
				if (errorCode == 0 && request.hasNotifyContent()) {
					Message.Builder builder = Message.newBuilder().setContent(request.getNotifyContent());
					builder.setConversation(builder.getConversationBuilder().setType(ConversationType.ConversationType_Group).setTarget(request.getGroupId()));
					long timestamp = System.currentTimeMillis();
					builder.setFromUser(username);
					long messageId = saveAndPublish(username, null, builder.build(), timestamp);
				}
				ByteBuf ack = Unpooled.buffer();
				ack.writeInt(errorCode);
				sendPubAck(clientID, messageID, ack);
				return;
			} catch (InvalidProtocolBufferException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sendPubAck(clientID, messageID, null);
			return;
		} else if (topic.getTopic().equals(IMTopic.KickoffGroupMemberTopic)) {
			try {
				ByteBuf payload = msg.payload();
	            byte[] payloadContent = readBytesAndRewind(payload);
				RemoveGroupMemberRequest request = RemoveGroupMemberRequest.parseFrom(payloadContent);
				int errorCode = m_messagesStore.kickoffGroupMembers(username, request.getGroupId(), request.getRemovedMemberList());
				if (errorCode == 0 && request.hasNotifyContent()) {
					Message.Builder builder = Message.newBuilder().setContent(request.getNotifyContent());
					builder.setConversation(builder.getConversationBuilder().setType(ConversationType.ConversationType_Group).setTarget(request.getGroupId()));
					long timestamp = System.currentTimeMillis();
					builder.setFromUser(username);
					long messageId = saveAndPublish(username, null, builder.build(), timestamp);
				}
				ByteBuf ack = Unpooled.buffer();
				ack.writeInt(errorCode);
				sendPubAck(clientID, messageID, ack);
				return;
			} catch (InvalidProtocolBufferException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sendPubAck(clientID, messageID, null);
			return;
		} else if (topic.getTopic().equals(IMTopic.QuitGroupTopic)) {
			try {
				ByteBuf payload = msg.payload();
	            byte[] payloadContent = readBytesAndRewind(payload);
				QuitGroupRequest request = QuitGroupRequest.parseFrom(payloadContent);				
				int errorCode = m_messagesStore.quitGroup(username, request.getGroupId());
				if (errorCode == 0 && request.hasNotifyContent()) {
					Message.Builder builder = Message.newBuilder().setContent(request.getNotifyContent());
					builder.setConversation(builder.getConversationBuilder().setType(ConversationType.ConversationType_Group).setTarget(request.getGroupId()));
					long timestamp = System.currentTimeMillis();
					builder.setFromUser(username);
					long messageId = saveAndPublish(username, null, builder.build(), timestamp);
				}
				ByteBuf ack = Unpooled.buffer();
				ack.writeInt(errorCode);
				sendPubAck(clientID, messageID, ack);
				return;
			} catch (InvalidProtocolBufferException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sendPubAck(clientID, messageID, null);
			return;
		} else if (topic.getTopic().equals(IMTopic.DismissGroupTopic)) {
			try {
				ByteBuf payload = msg.payload();
	            byte[] payloadContent = readBytesAndRewind(payload);
				DismissGroupRequest request = DismissGroupRequest.parseFrom(payloadContent);
				
				int errorCode = 0;
				GroupInfo groupInfo = m_messagesStore.getGroupInfo(request.getGroupId());
				if (groupInfo != null && (groupInfo.getType() == GroupType.GroupType_Normal || groupInfo.getType() == GroupType.GroupType_Restricted) 
						&& groupInfo.getOwner() != null && groupInfo.getOwner().equals(username)) {
					
					//send notify message first, then dismiss group
					if (request.hasNotifyContent()) {
						Message.Builder builder = Message.newBuilder().setContent(request.getNotifyContent());
						builder.setConversation(builder.getConversationBuilder().setType(ConversationType.ConversationType_Group).setTarget(request.getGroupId()));
						long timestamp = System.currentTimeMillis();
						builder.setFromUser(username);
						long messageId = saveAndPublish(username, null, builder.build(), timestamp);
					}
					
					errorCode = m_messagesStore.dismissGroup(username, request.getGroupId());
					
					ByteBuf ack = Unpooled.buffer();
					ack.writeInt(errorCode);
					sendPubAck(clientID, messageID, ack);
					return;
				}
				

			} catch (InvalidProtocolBufferException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sendPubAck(clientID, messageID, null);
			return;
		} else if (topic.getTopic().equals(IMTopic.ModifyGroupInfoTopic)) {
			try {
				ByteBuf payload = msg.payload();
				byte[] payloadContent = readBytesAndRewind(payload);
				ModifyGroupInfoRequest request = ModifyGroupInfoRequest.parseFrom(payloadContent);
				int errorCode = m_messagesStore.modifyGroupInfo(username, request.getGroupInfo());
				if (errorCode == 0 && request.hasNotifyContent()) {
					Message.Builder builder = Message.newBuilder().setContent(request.getNotifyContent());
					builder.setConversation(builder.getConversationBuilder().setType(ConversationType.ConversationType_Group).setTarget(request.getGroupInfo().getTargetId()));
					long timestamp = System.currentTimeMillis();
					builder.setFromUser(username);
					long messageId = saveAndPublish(username, null, builder.build(), timestamp);
				}
				ByteBuf ack = Unpooled.buffer();
				ack.writeInt(errorCode);
				sendPubAck(clientID, messageID, ack);
			} catch (InvalidProtocolBufferException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		} else if (topic.getTopic().equals(IMTopic.GetGroupInfoTopic)) {
			try {
				ByteBuf payload = msg.payload();
				byte[] payloadContent = readBytesAndRewind(payload);
				IDListBuf request = IDListBuf.parseFrom(payloadContent);
				List<GroupInfo> infos = m_messagesStore.getGroupInfos(request.getIdList());
				
				PullGroupInfoResult result = PullGroupInfoResult.newBuilder().addAllInfo(infos).build();
				
				ByteBuf ack = Unpooled.buffer();
				ack.writeBytes(result.toByteArray());
				sendPubAck(clientID, messageID, ack);
			} catch (InvalidProtocolBufferException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		} else if (topic.getTopic().equals(IMTopic.GetGroupMemberTopic)) {
			try {
				ByteBuf payload = msg.payload();
				byte[] payloadContent = readBytesAndRewind(payload);
				IDBuf request = IDBuf.parseFrom(payloadContent);
				
				List<String> members = m_messagesStore.getGroupMembers(request.getId());
				
				PullGroupMemberResult result = PullGroupMemberResult.newBuilder().addAllMember(members).build();
				
				ByteBuf ack = Unpooled.buffer();
				ack.writeBytes(result.toByteArray());
				sendPubAck(clientID, messageID, ack);
			} catch (InvalidProtocolBufferException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
