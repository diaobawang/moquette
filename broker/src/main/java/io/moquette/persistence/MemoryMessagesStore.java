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

package io.moquette.persistence;

import io.moquette.server.Server;
import io.moquette.spi.IMatchingCondition;
import io.moquette.spi.IMessagesStore;
import io.moquette.spi.impl.subscriptions.Topic;
import win.liyufan.im.proto.ConversationOuterClass.ConversationType;
import win.liyufan.im.proto.MessageBundle;
import win.liyufan.im.proto.MessageOuterClass.Message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IAtomicLong;
import com.hazelcast.core.IMap;
import com.hazelcast.core.MultiMap;

import java.util.*;

public class MemoryMessagesStore implements IMessagesStore {
	private static final String MESSAGES_MAP = "messages_map";
	private static final String MESSAGE_ID_COUNTER = "message_id_counter";
	private static final String USER_MESSAGE_IDS = "user_message_ids";
	private static final String CHATROOM_MESSAGE_IDS = "chatroom_message_ids";
    private static final Logger LOG = LoggerFactory.getLogger(MemoryMessagesStore.class);

    private Map<Topic, StoredMessage> m_retainedStore = new HashMap<>();
    
    private Server m_Server;

    MemoryMessagesStore(Server server) {
    		m_Server = server;
    }

    @Override
    public void initStore() {
    }

    @Override
    public long storeMessage(String fromUser, String fromClientId, Message message, List<String> notifyReceivers) {
    		HazelcastInstance hzInstance = m_Server.getHazelcastInstance();
    		IMap<Long, MessageBundle> mIMap = hzInstance.getMap(MESSAGES_MAP);
    		IAtomicLong counter = hzInstance.getAtomicLong(MESSAGE_ID_COUNTER);
    		long messageId =  counter.addAndGet(1);
    		MessageBundle messageBundle = new MessageBundle(messageId, fromUser, fromClientId, message);
    		mIMap.put(messageId, messageBundle);
    		
    		ConversationType type = message.getConversation().getType();
    		if (type == ConversationType.Private
    				|| type == ConversationType.System) {
    			MultiMap<String, Long> userMessageIds = hzInstance.getMultiMap(USER_MESSAGE_IDS);
    			userMessageIds.put(fromUser, messageId);
			userMessageIds.put(message.getConversation().getTarget(), messageId);
		} else if (type == ConversationType.Group) {
			MultiMap<String, Long> userMessageIds = hzInstance.getMultiMap(USER_MESSAGE_IDS);
			userMessageIds.put(fromUser, messageId);
			//Todo get all the group member and add to the list.
		} else if (type == ConversationType.ChatRoom) {
			MultiMap<String, Long> chatroomMessageIds = hzInstance.getMultiMap(CHATROOM_MESSAGE_IDS);
			chatroomMessageIds.put(message.getConversation().getTarget(), messageId);
		}
    		return messageId;
    }
    
    @Override
    public void storeRetained(Topic topic, StoredMessage storedMessage) {
        LOG.debug("Store retained message for topic={}, CId={}", topic, storedMessage.getClientID());
        if (storedMessage.getClientID() == null) {
            throw new IllegalArgumentException( "Message to be persisted must have a not null client ID");
        }
        m_retainedStore.put(topic, storedMessage);
    }

    @Override
    public Collection<StoredMessage> searchMatching(IMatchingCondition condition) {
        LOG.debug("searchMatching scanning all retained messages, presents are {}", m_retainedStore.size());

        List<StoredMessage> results = new ArrayList<>();

        for (Map.Entry<Topic, StoredMessage> entry : m_retainedStore.entrySet()) {
            StoredMessage storedMsg = entry.getValue();
            if (condition.match(entry.getKey())) {
                results.add(storedMsg);
            }
        }

        return results;
    }

    @Override
    public void cleanRetained(Topic topic) {
        m_retainedStore.remove(topic);
    }
}
