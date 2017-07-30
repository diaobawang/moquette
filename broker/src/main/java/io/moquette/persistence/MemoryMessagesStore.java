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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IAtomicLong;
import com.hazelcast.core.IMap;
import com.hazelcast.core.MultiMap;
import com.hazelcast.util.StringUtil;

import io.moquette.server.Server;
import io.moquette.spi.IMatchingCondition;
import io.moquette.spi.IMessagesStore;
import io.moquette.spi.impl.subscriptions.Topic;
import win.liyufan.im.MessageBundle;
import win.liyufan.im.proto.ConversationOuterClass.ConversationType;
import win.liyufan.im.proto.GroupOuterClass.GroupInfo;
import win.liyufan.im.proto.MessageOuterClass.Message;
import win.liyufan.im.proto.PullMessageResultOuterClass.PullMessageResult;

public class MemoryMessagesStore implements IMessagesStore {
	private static final String MESSAGES_MAP = "messages_map";
	private static final String GROUPS_MAP = "groups_map";
	private static final String MESSAGE_ID_COUNTER = "message_id_counter";
	private static final String GROUP_ID_COUNTER = "group_id_counter";
	private static final String GROUP_MEMBERS = "group_members";
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
	public long storeMessage(String fromUser, String fromClientId, Message message, Set<String> notifyReceivers, long timestamp) {
		HazelcastInstance hzInstance = m_Server.getHazelcastInstance();
		IMap<Long, MessageBundle> mIMap = hzInstance.getMap(MESSAGES_MAP);
		IAtomicLong counter = hzInstance.getAtomicLong(MESSAGE_ID_COUNTER);
		long messageId = counter.addAndGet(1);
		message = Message.newBuilder()
				.setContent(message.getContent())
				.setConversation(message.getConversation())
				.setFromUser(fromUser)
				.setMessageId(messageId)
				.setServerTimestamp(timestamp)
				.build();
		
		MessageBundle messageBundle = new MessageBundle(messageId, fromUser, fromClientId, message);
		mIMap.put(messageId, messageBundle);

		ConversationType type = message.getConversation().getType();
		if (type == ConversationType.ConversationType_Private || type == ConversationType.ConversationType_System) {
			MultiMap<String, Long> userMessageIds = hzInstance.getMultiMap(USER_MESSAGE_IDS);
			userMessageIds.put(fromUser, messageId);
			userMessageIds.put(message.getConversation().getTarget(), messageId);
			notifyReceivers.add(fromUser);
			notifyReceivers.add(message.getConversation().getTarget());
		} else if (type == ConversationType.ConversationType_Group) {
			MultiMap<String, Long> userMessageIds = hzInstance.getMultiMap(USER_MESSAGE_IDS);
			MultiMap<String, String> groupMembers = hzInstance.getMultiMap(GROUP_MEMBERS);
			userMessageIds.put(fromUser, messageId);
			notifyReceivers.add(fromUser);
			notifyReceivers.addAll(groupMembers.get(message.getConversation().getTarget()));
		} else if (type == ConversationType.ConversationType_ChatRoom) {
			MultiMap<String, Long> chatroomMessageIds = hzInstance.getMultiMap(CHATROOM_MESSAGE_IDS);
			chatroomMessageIds.put(message.getConversation().getTarget(), messageId);
		}
		return messageId;
	}
    
    @Override
    public long fetchMessage(String user, String exceptClientId, long fromMessageId, PullMessageResult.Builder builder) {
    	HazelcastInstance hzInstance = m_Server.getHazelcastInstance();
		IMap<Long, MessageBundle> mIMap = hzInstance.getMap(MESSAGES_MAP);

		MultiMap<String, Long> userMessageIds = hzInstance.getMultiMap(USER_MESSAGE_IDS);
		Collection<Long> ids = userMessageIds.get(user);
		
		if (ids == null || ids.isEmpty()) {
			builder.setCurrent(fromMessageId);
			builder.setHead(fromMessageId);
			return fromMessageId;
		}
		
		ArrayList<Long> idList = new ArrayList<>(ids);
		idList.sort(new Comparator<Long>() {

			@Override
			public int compare(Long o1, Long o2) {
				// TODO Auto-generated method stub
				if (o1 == o2) {
					return 0;
				} else if (o1 < o2) {
					return -1;
				}
				return 1;
			}
		});
		
		int index = 0;
		for (int i = 0; i < idList.size(); i++) {
			long element = idList.get(i);
			if (element > fromMessageId) {
				index = i;
				break;
			}
		}
		List<Long> pulledIds = idList.subList(index, idList.size());
		
		if (pulledIds == null || pulledIds.isEmpty()) {
			builder.setCurrent(fromMessageId);
			builder.setHead(fromMessageId);
			return fromMessageId;
		}
		
		int count = 0;
		long current = fromMessageId;
		for (Long id : pulledIds) {
			MessageBundle bundle = mIMap.get(id);
			if (bundle != null) {
				current = bundle.getMessageId();
				if (exceptClientId == null || !exceptClientId.equals(bundle.getFromClientId())) {
					count++;
					builder.addMessage(bundle.getMessage());
					if (count >= 10) {
						break;
					}
				}

			}
		}
		builder.setCurrent(current);
		builder.setHead(idList.get(idList.size() - 1));
		
		return idList.get(idList.size() - 1);
    }
    
    @Override
    public int createGroup(String fromUser, GroupInfo groupInfo, List<String> memberList) {
    	HazelcastInstance hzInstance = m_Server.getHazelcastInstance();
		IMap<String, GroupInfo> mIMap = hzInstance.getMap(GROUPS_MAP);
		IAtomicLong counter = hzInstance.getAtomicLong(GROUP_ID_COUNTER);
		String groupId = null;
		if (groupInfo.getTargetId() == null) {
			groupId = Long.toString(counter.addAndGet(1));
		} else {
			groupId = groupInfo.getTargetId();
		}
		
		groupInfo = groupInfo.toBuilder()
				.setTargetId(groupId)
				.setOwner(StringUtil.isNullOrEmpty(groupInfo.getOwner()) ? fromUser : groupInfo.getOwner())
				.build();
		

		mIMap.put(groupId, groupInfo);
		MultiMap<String, String> groupMembers = hzInstance.getMultiMap(GROUP_MEMBERS);
		for (String member : memberList) {
			groupMembers.put(groupId, member);
		}
		
    	return 0;
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
