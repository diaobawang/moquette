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
import win.liyufan.im.proto.ConversationOuterClass.ConversationType;
import win.liyufan.im.proto.MessageOuterClass.Message;
import win.liyufan.im.proto.PullMessageRequestOuterClass.PullMessageRequest;
import win.liyufan.im.proto.PullMessageResultOuterClass.PullMessageResult;

import org.eclipse.jetty.util.log.Log;
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
    				Set<String> notifyReceivers = null;
    				if (message.getConversation().getType() != ConversationType.ChatRoom) {
    					notifyReceivers = new LinkedHashSet<>();
    				}
    				
    				long timestamp = System.currentTimeMillis();
    				
    				long messageId = m_messagesStore.storeMessage(username, clientID, message, notifyReceivers, timestamp);
    				this.publisher.publish2Receivers(messageId, notifyReceivers, clientID);
    				
    				ByteBuf ack = Unpooled.buffer(16);
    				ack.writeLong(messageId);
	                
	                ack.writeLong(timestamp);
	                System.out.println("the size is " + ack.readableBytes());
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
