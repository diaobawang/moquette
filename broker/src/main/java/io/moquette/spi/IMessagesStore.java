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

package io.moquette.spi;

import io.moquette.spi.impl.subscriptions.Topic;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.mqtt.MqttQoS;
import win.liyufan.im.ErrorCode;
import win.liyufan.im.MessageBundle;
import win.liyufan.im.proto.GroupOuterClass.Group;
import win.liyufan.im.proto.GroupOuterClass.GroupInfo;
import win.liyufan.im.proto.MessageOuterClass.Message;
import win.liyufan.im.proto.NotifyMessageOuterClass.PullType;
import win.liyufan.im.proto.PullMessageResultOuterClass.PullMessageResult;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IAtomicLong;
import com.hazelcast.core.IMap;

/**
 * Defines the SPI to be implemented by a StorageService that handle persistence of messages
 */
public interface IMessagesStore {

    class StoredMessage implements Serializable {

        private static final long serialVersionUID = 1755296138639817304L;
        final MqttQoS m_qos;
        final byte[] m_payload;
        final String m_topic;
        private boolean m_retained;
        private String m_clientID;
        private MessageGUID m_guid;

        public StoredMessage(byte[] message, MqttQoS qos, String topic) {
            m_qos = qos;
            m_payload = message;
            m_topic = topic;
        }

        public MqttQoS getQos() {
            return m_qos;
        }

        public String getTopic() {
            return m_topic;
        }

        public void setGuid(MessageGUID guid) {
            this.m_guid = guid;
        }

        public MessageGUID getGuid() {
            return m_guid;
        }

        public String getClientID() {
            return m_clientID;
        }

        public void setClientID(String m_clientID) {
            this.m_clientID = m_clientID;
        }

        public ByteBuf getPayload() {
            return Unpooled.copiedBuffer(m_payload);
        }

        public void setRetained(boolean retained) {
            this.m_retained = retained;
        }

        public boolean isRetained() {
            return m_retained;
        }

        @Override
        public String toString() {
            return "PublishEvent{clientID='" + m_clientID + '\'' + ", m_retain="
                    + m_retained + ", m_qos=" + m_qos + ", m_topic='" + m_topic + '\'' + '}';
        }
    }

    public Message storeMessage(String fromUser, String fromClientId, Message message, long timestamp);
	public PullType getNotifyReceivers(String fromUser, Message message, Set<String> notifyReceivers);
    long fetchMessage(String user, String exceptClientId, long fromMessageId, PullMessageResult.Builder builder);
    GroupInfo createGroup(String operator, GroupInfo groupInfo, List<String> memberList);
    ErrorCode addGroupMembers(String operator, String groupId, List<String> memberList);
    ErrorCode kickoffGroupMembers(String operator, String groupId, List<String> memberList);
    ErrorCode quitGroup(String operator, String groupId);
    ErrorCode dismissGroup(String operator, String groupId);
    ErrorCode modifyGroupInfo(String operator, GroupInfo groupInfo);
    List<GroupInfo> getGroupInfos(List<String> groupIds);
    GroupInfo getGroupInfo(String groupId);
    List<String> getGroupMembers(String groupId);
    List<String> getMyGroups(String fromUser);
    ErrorCode transferGroup(String operator, String groupId, String newOwner);
    boolean isMemberInGroup(String member, String groupId);
    /**
     * Used to initialize all persistent store structures
     */
    void initStore();

    /**
     * Return a list of retained messages that satisfy the condition.
     *
     * @param condition
     *            the condition to match during the search.
     * @return the collection of matching messages.
     */
    Collection<StoredMessage> searchMatching(IMatchingCondition condition);

    void cleanRetained(Topic topic);

    void storeRetained(Topic topic, StoredMessage storedMessage);
}
