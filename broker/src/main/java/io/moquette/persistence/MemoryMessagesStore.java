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

import java.security.MessageDigest;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hazelcast.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.util.StringUtil;

import io.moquette.server.Server;
import io.moquette.spi.IMatchingCondition;
import io.moquette.spi.IMessagesStore;
import io.moquette.spi.impl.subscriptions.Topic;
import sun.misc.BASE64Encoder;
import win.liyufan.im.DBUtil;
import win.liyufan.im.ErrorCode;
import win.liyufan.im.MessageBundle;
import win.liyufan.im.proto.*;
import win.liyufan.im.proto.ConversationOuterClass.ConversationType;
import win.liyufan.im.proto.GroupOuterClass.GroupInfo;
import win.liyufan.im.proto.GroupOuterClass.GroupType;
import win.liyufan.im.proto.MessageOuterClass.Message;
import win.liyufan.im.proto.NotifyMessageOuterClass.PullType;
import win.liyufan.im.proto.PullMessageResultOuterClass.PullMessageResult;

import static win.liyufan.im.MyInfoType.*;

public class MemoryMessagesStore implements IMessagesStore {
	private static final String MESSAGES_MAP = "messages_map";
	private static final String GROUPS_MAP = "groups_map";
	private static final String MESSAGE_ID_COUNTER = "message_id_counter";
	private static final String GROUP_ID_COUNTER = "group_id_counter";
	private static final String GROUP_MEMBERS = "group_members";
    private static final String USER_GROUPS = "user_groups";
	private static final String USER_MESSAGE_IDS = "user_message_ids";
	private static final String CHATROOM_MESSAGE_IDS = "chatroom_message_ids";

	private static final String USER_FRIENDS = "user_friends";
    private static final String USER_FRIENDS_REQUEST = "user_friends_request";

    private static final String USERS = "users";
    private static final Logger LOG = LoggerFactory.getLogger(MemoryMessagesStore.class);

    private Map<Topic, StoredMessage> m_retainedStore = new HashMap<>();
    
    private Server m_Server;

    MemoryMessagesStore(Server server) {
    		m_Server = server;
    }

    @Override
    public void initStore() {
    }

    private long getMaxMessageId() {
    		String sql = "select max(`_mid`) from t_messages;";
    	    Connection connection = null;
		PreparedStatement statement = null;
		ResultSet rs = null; 
		long max = 0;	
    		try {
			connection = DBUtil.getConnection();
    			statement = connection.prepareStatement(sql);
			rs = statement.executeQuery();
	    		if (rs.next()) {
	    			max = rs.getLong(1);	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.closeDB(connection, statement, rs);
		}
    		if (max == 0) {
			max = 1;
		}
    		return max;
    }
    private void persistMessage(Message message) {
    		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DBUtil.getConnection();
			String sql;
			if (StringUtil.isNullOrEmpty(message.getContent().getSearchableContent())) {
				sql = "insert into t_messages (`_mid`, `_from`, `_type`, `_target`, `_line`, `_data`, `_dt`) values(?, ?, ?, ?, ?, ?, ?)";
			} else {
				sql = "insert into t_messages (`_mid`, `_from`, `_type`, `_target`, `_line`, `_data`, `_searchable_key`, `_dt`) values(?, ?, ?, ?, ?, ?, ?, ?)";
			}
			
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setLong(index++, message.getMessageId());
			statement.setString(index++, message.getFromUser());
			statement.setInt(index++, message.getConversation().getType().getNumber());
			statement.setString(index++, message.getConversation().getTarget());
			statement.setInt(index++, message.getConversation().getLine());
			Blob blob = connection.createBlob();
			blob.setBytes(1, message.getContent().toByteArray());
			statement.setBlob(index++, blob);
			if (!StringUtil.isNullOrEmpty(message.getContent().getSearchableContent())) {
				statement.setString(index++, message.getContent().getSearchableContent());
			}
			statement.setLong(index++, message.getServerTimestamp());
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.closeDB(connection, statement);
		}
	}
    private void persistUserMessage(long messageId, String toTarget, ConversationType type, Set<String> userIds, String fromUser) {
    		Connection connection = null;
    		PreparedStatement statement = null;
		try {
			connection = DBUtil.getConnection();
			for (String userId : userIds) {
				String sql = "insert into t_user_messages (`_mid`, `_target`, `_type`, `_uid`) values(?, ?, ?, ?)";
			
				statement = connection.prepareStatement(sql);
				int index = 1;
				statement.setLong(index++, messageId);
				if ((type == ConversationType.ConversationType_Private || type == ConversationType.ConversationType_System) && toTarget.equals(userId)) {
					statement.setString(index++, fromUser);
				} else {
					statement.setString(index++, toTarget);
				}
				statement.setInt(index++, type.getNumber());
				statement.setString(index++, userId);
				statement.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DBUtil.closeDB(connection, statement);
		}
	}

    private void persistGroupInfo(GroupInfo groupInfo) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "insert into t_group (`_gid`" +
                ", `_name`" +
                ", `_portrait`" +
                ", `_owner`" +
                ", `_type`" +
                ", `_extra`" +
                ", `_dt`) values(?, ?, ?, ?, ?, ?, ?, ?)";

            statement = connection.prepareStatement(sql);

            int index = 1;
            statement.setString(index++, groupInfo.getTargetId());
            statement.setString(index++, groupInfo.getName());
            statement.setString(index++, groupInfo.getPortrait());
            statement.setString(index++, groupInfo.getOwner());
            statement.setInt(index++, groupInfo.getType().getNumber());
            statement.setString(index++, groupInfo.getExtra());
            statement.setLong(index++, System.currentTimeMillis());

            statement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            DBUtil.closeDB(connection, statement);
        }
    }

    private GroupInfo getPersistGroupInfo(String groupId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select `_name`" +
                ", `_portrait`" +
                ", `_owner`" +
                ", `_type`" +
                ", `_extra`" +
                ", `_dt` from t_group  where `_gid` = ?";

            statement = connection.prepareStatement(sql);

            int index = 1;
            statement.setString(index++, groupId);

            rs = statement.executeQuery();
            if (rs.next()) {
                String strValue;
                int intValue;
                GroupInfo.Builder builder = GroupInfo.newBuilder();
                index = 1;

                builder.setTargetId(groupId);

                strValue = rs.getString(index++);
                strValue = (strValue == null ? "" : strValue);
                builder.setName(strValue);

                strValue = rs.getString(index++);
                strValue = (strValue == null ? "" : strValue);
                builder.setPortrait(strValue);

                strValue = rs.getString(index++);
                strValue = (strValue == null ? "" : strValue);
                builder.setOwner(strValue);

                strValue = rs.getString(index++);
                strValue = (strValue == null ? "" : strValue);
                builder.setExtra(strValue);

                intValue = rs.getInt(index++);
                builder.setTypeValue(intValue);

                long longValue = rs.getLong(index++);

                return builder.build();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            DBUtil.closeDB(connection, statement);
        }
        return null;
    }

	private void persistUser(UserOuterClass.User user, String password) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "insert into t_user (`_uid`" +
                    ", `_name`" +
                    ", `_display_name`" +
                    ", `_portrait`" +
                    ", `_mobile`" +
                    ", `_email`" +
                    ", `_address`" +
                    ", `_company`" +
                    ", `_social`" +
                    ", `_passwd_md5`" +
                    ", `_extra`" +
                    ", `_dt`) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            statement = connection.prepareStatement(sql);

            int index = 1;
            statement.setString(index++, user.getUid());
            statement.setString(index++, user.getName());
            statement.setString(index++, user.getDisplayName());
            statement.setString(index++, user.getPortrait());
            statement.setString(index++, user.getMobile());
            statement.setString(index++, user.getEmail());
            statement.setString(index++, user.getAddress());
            statement.setString(index++, user.getCompany());
            statement.setString(index++, "");

            try {
                MessageDigest md5= MessageDigest.getInstance("MD5");
                BASE64Encoder base64en = new BASE64Encoder();
                String passwdMd5=base64en.encode(md5.digest(password.getBytes("utf-8")));
                statement.setString(index, passwdMd5  );
            } catch (Exception e) {
                statement.setString(index, "");
            }
            index++;

            statement.setString(index++, user.getExtra());
            statement.setLong(index++, System.currentTimeMillis());

            statement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            DBUtil.closeDB(connection, statement);
        }
    }

    private void updateUser(UserOuterClass.User user) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "update t_user set `_display_name` = ?" +
                ", `_portrait` = ?" +
                ", `_mobile` = ?" +
                ", `_email` = ?" +
                ", `_address` = ?" +
                ", `_company` = ?" +
                ", `_social` = ?" +
//                ", `_passwd_md5`" +
                ", `_extra` = ?" +
                ", `_dt` = ? where _uid = ?";

            statement = connection.prepareStatement(sql);

            int index = 1;


            statement.setString(index++, user.getDisplayName());
            statement.setString(index++, user.getPortrait());
            statement.setString(index++, user.getMobile());
            statement.setString(index++, user.getEmail());
            statement.setString(index++, user.getAddress());
            statement.setString(index++, user.getCompany());
            statement.setString(index++, "");

            statement.setString(index++, user.getExtra());
            statement.setLong(index++, user.getUpdateDt() == 0 ? System.currentTimeMillis() : user.getUpdateDt());
            statement.setString(index++, user.getUid());

            statement.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            DBUtil.closeDB(connection, statement);
        }
    }

    private UserOuterClass.User getPersistUser(String userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select `_name`" +
                ", `_display_name`" +
                ", `_portrait`" +
                ", `_mobile`" +
                ", `_email`" +
                ", `_address`" +
                ", `_company`" +
                ", `_social`" +
                ", `_extra`" +
                ", `_dt` from t_user where `_uid` = ?";
            statement = connection.prepareStatement(sql);

            int index = 1;
            statement.setString(index++, userId);


            rs = statement.executeQuery();
            if (rs.next()) {
                UserOuterClass.User.Builder builder = UserOuterClass.User.newBuilder();
                index = 1;
                String value = rs.getString(index++);
                value = (value == null ? "" : value);
                builder.setUid(userId);
                builder.setName(value);

                value = rs.getString(index++);
                value = (value == null ? "" : value);
                builder.setDisplayName(value);

                value = rs.getString(index++);
                value = (value == null ? "" : value);
                builder.setPortrait(value);

                value = rs.getString(index++);
                value = (value == null ? "" : value);
                builder.setMobile(value);

                value = rs.getString(index++);
                value = (value == null ? "" : value);
                builder.setEmail(value);

                value = rs.getString(index++);
                value = (value == null ? "" : value);
                builder.setAddress(value);

                value = rs.getString(index++);
                value = (value == null ? "" : value);
                builder.setCompany(value);

                value = rs.getString(index++);
                value = (value == null ? "" : value);
//                builder.setSocial(value);

                value = rs.getString(index++);
                value = (value == null ? "" : value);
                builder.setExtra(value);

                long longValue = rs.getLong(index++);
                builder.setUpdateDt(longValue);

                return builder.build();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            DBUtil.closeDB(connection, statement);
        }
        return null;
    }

    private List<String> getPersistFriends(String userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select `_friend_uid` from t_friend where `_uid` = ?";
            statement = connection.prepareStatement(sql);

            int index = 1;
            statement.setString(index++, userId);


            rs = statement.executeQuery();
            List<String> out = new ArrayList<>();
            while (rs.next()) {
                String value = rs.getString(1);
                out.add(value);
            }
            return  out;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            DBUtil.closeDB(connection, statement);
        }
        return null;
    }

    private List<FriendRequestOuterClass.FriendRequest> getPersistFriendRequests(String userId) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select   `_uid`" +
                ", `_friend_uid`" +
                ", `_reason`" +
                ", `_status`" +
                ", `_dt`" +
                ", `_from_read_status`" +
                ", `_to_read_status` from t_friend_request where `_uid` = ? or `_friend_uid` = ?";
            statement = connection.prepareStatement(sql);

            int index = 1;
            statement.setString(index++, userId);


            rs = statement.executeQuery();
            List<FriendRequestOuterClass.FriendRequest> out = new ArrayList<>();
            while (rs.next()) {
                FriendRequestOuterClass.FriendRequest.Builder builder = FriendRequestOuterClass.FriendRequest.newBuilder();
                index = 1;
                String value = rs.getString(index++);
                value = (value == null ? "" : value);
                builder.setFromUid(value);

                value = rs.getString(index++);
                value = (value == null ? "" : value);
                builder.setToUid(value);


                value = rs.getString(index++);
                value = (value == null ? "" : value);
                builder.setReason(value);

                int intValue = rs.getInt(index++);
                builder.setStatus(FriendRequestOuterClass.RequestStatus.valueOf(intValue));

                long longValue = rs.getLong(index++);
                builder.setUpdateDt(longValue);

                boolean b = rs.getBoolean(index++);
                builder.setFromReadStatus(b);

                b = rs.getBoolean(index++);
                builder.setToReadStatus(b);

                out.add(builder.build());
            }
            return  out;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            DBUtil.closeDB(connection, statement);
        }
        return null;
    }

    @Override
    public Message storeMessage(String fromUser, String fromClientId, Message message, long timestamp) {
		HazelcastInstance hzInstance = m_Server.getHazelcastInstance();
		IMap<Long, MessageBundle> mIMap = hzInstance.getMap(MESSAGES_MAP);
		IAtomicLong counter = hzInstance.getAtomicLong(MESSAGE_ID_COUNTER);
		if(counter.get() == 0) {
			long maxId = getMaxMessageId();
			counter.compareAndSet(0, maxId);
		}
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
		
		persistMessage(message);
		
		return message;
	}
    
   @Override
	public PullType getNotifyReceivers(String fromUser, Message message, Set<String> notifyReceivers) {
		HazelcastInstance hzInstance = m_Server.getHazelcastInstance();
		ConversationType type = message.getConversation().getType();
		long messageId = message.getMessageId();
		
		PullType pullType = null;
		if (type == ConversationType.ConversationType_Private || type == ConversationType.ConversationType_System) {
			MultiMap<String, Long> userMessageIds = hzInstance.getMultiMap(USER_MESSAGE_IDS);
			userMessageIds.put(fromUser, messageId);
			userMessageIds.put(message.getConversation().getTarget(), messageId);
			notifyReceivers.add(fromUser);
			notifyReceivers.add(message.getConversation().getTarget());
			persistUserMessage(messageId, message.getConversation().getTarget(), message.getConversation().getType(), notifyReceivers, fromUser);
			pullType = PullType.Pull_Normal;
		} else if (type == ConversationType.ConversationType_Group) {
			MultiMap<String, Long> userMessageIds = hzInstance.getMultiMap(USER_MESSAGE_IDS);
			MultiMap<String, GroupOuterClass.GroupMember> groupMembers = hzInstance.getMultiMap(GROUP_MEMBERS);

			Collection<GroupOuterClass.GroupMember> members = groupMembers.get(message.getConversation().getTarget());

            for (GroupOuterClass.GroupMember member : members) {
                userMessageIds.put(member.getMemberId(), messageId);
                notifyReceivers.add(member.getMemberId());
            }

            notifyReceivers.add(fromUser);

			persistUserMessage(messageId, message.getConversation().getTarget(), message.getConversation().getType(), notifyReceivers, fromUser);
			//如果是群助手的消息，返回pull type group，否则返回normal
			pullType = PullType.Pull_Normal;
		} else if (type == ConversationType.ConversationType_ChatRoom) {
			MultiMap<String, Long> chatroomMessageIds = hzInstance.getMultiMap(CHATROOM_MESSAGE_IDS);
			chatroomMessageIds.put(message.getConversation().getTarget(), messageId);
			pullType = PullType.Pull_ChatRoom;
		}
	
		return pullType;
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
		
		int count = 0;
		long current = fromMessageId;
		for (int i = 0; i < idList.size(); i++) {
			long element = idList.get(i);
			if (element > fromMessageId) {
				MessageBundle bundle = mIMap.get(element);
				if (bundle != null) {
					current = bundle.getMessageId();
					if (exceptClientId == null || !exceptClientId.equals(bundle.getFromClientId()) || !user.equals(bundle.getFromUser())) {
						count++;
						builder.addMessage(bundle.getMessage());
						if (count >= 100) {
							break;
						}
					}

				}
			}
		}
		
		builder.setCurrent(current);
		builder.setHead(idList.get(idList.size() - 1));
		
		return idList.get(idList.size() - 1);
    }
    
    @Override
    public GroupInfo createGroup(String fromUser, GroupInfo groupInfo, List<GroupOuterClass.GroupMember> memberList) {
    	HazelcastInstance hzInstance = m_Server.getHazelcastInstance();
		IMap<String, GroupInfo> mIMap = hzInstance.getMap(GROUPS_MAP);


		IAtomicLong counter = hzInstance.getAtomicLong(GROUP_ID_COUNTER);
		String groupId = null;
		if (StringUtil.isNullOrEmpty(groupInfo.getTargetId())) {
			groupId = "System_Created_Group_Num" + Long.toString(counter.addAndGet(1));
			
			groupInfo = groupInfo.toBuilder()
					.setTargetId(groupId)
					.setName(groupInfo.getName())
					.setPortrait(groupInfo.getPortrait())
					.setType(groupInfo.getType())
					.setExtra(groupInfo.getExtra())
					.setOwner(StringUtil.isNullOrEmpty(groupInfo.getOwner()) ? fromUser : groupInfo.getOwner())
					.build();
		} else {
			groupId = groupInfo.getTargetId();
		}

		mIMap.put(groupId, groupInfo);
		persistGroupInfo(groupInfo);
		MultiMap<String, GroupOuterClass.GroupMember> groupMembers = hzInstance.getMultiMap(GROUP_MEMBERS);
        MultiMap<String, String> userGroups = hzInstance.getMultiMap(USER_GROUPS);

		for (GroupOuterClass.GroupMember member : memberList) {
			groupMembers.put(groupId, member);
			userGroups.put(member.getMemberId(), groupId);
		}

//		if (!memberList.contains(groupInfo.getOwner())) {
//            groupMembers.put(groupId, groupInfo.getOwner());
//            userGroups.put(groupInfo.getOwner(), groupId);
//        }

        persistGroupInfo(groupInfo);

    	return groupInfo;
    }
    
    
    @Override
    public ErrorCode addGroupMembers(String operator, String groupId, List<GroupOuterClass.GroupMember> memberList) {
    	HazelcastInstance hzInstance = m_Server.getHazelcastInstance();
		IMap<String, GroupInfo> mIMap = hzInstance.getMap(GROUPS_MAP);

		GroupInfo groupInfo = mIMap.get(groupId);
		if (groupInfo == null) {
			return ErrorCode.ERROR_CODE_GROUP_NOT_EXIST;
		}
		if (groupInfo.getType() == GroupType.GroupType_Restricted && (groupInfo.getOwner() == null || !groupInfo.getOwner().equals(operator))) {
			return ErrorCode.ERROR_CODE_GROUP_NOT_RIGHT;
		}
		
		MultiMap<String, GroupOuterClass.GroupMember> groupMembers = hzInstance.getMultiMap(GROUP_MEMBERS);
        MultiMap<String, String> userGroups = hzInstance.getMultiMap(USER_GROUPS);
		for (GroupOuterClass.GroupMember member : memberList) {
			groupMembers.put(groupId, member);
			userGroups.put(member.getMemberId(), groupId);
		}
		
    	return ErrorCode.ERROR_CODE_SUCCESS;
    }
    
    @Override
    public ErrorCode kickoffGroupMembers(String operator, String groupId, List<String> memberList) {
    	HazelcastInstance hzInstance = m_Server.getHazelcastInstance();
		IMap<String, GroupInfo> mIMap = hzInstance.getMap(GROUPS_MAP);

		GroupInfo groupInfo = mIMap.get(groupId);
		if (groupInfo == null) {
            return ErrorCode.ERROR_CODE_GROUP_NOT_EXIST;
		}
		if ((groupInfo.getType() == GroupType.GroupType_Restricted || groupInfo.getType() == GroupType.GroupType_Normal) 
				&& (groupInfo.getOwner() == null || !groupInfo.getOwner().equals(operator))) {
            return ErrorCode.ERROR_CODE_GROUP_NOT_RIGHT;
		}
		
		MultiMap<String, GroupOuterClass.GroupMember> groupMembers = hzInstance.getMultiMap(GROUP_MEMBERS);
        MultiMap<String, String> userGroups = hzInstance.getMultiMap(USER_GROUPS);
		for (String member : memberList) {
			userGroups.remove(member, groupId);
		}

		List<GroupOuterClass.GroupMember> memberTobeRemove = new ArrayList<>();
        for (GroupOuterClass.GroupMember member: groupMembers.get(groupId)
             ) {
            if (memberList.contains(member.getMemberId())) {
                memberTobeRemove.add(member);
            }
        }
        groupMembers.remove(groupId, memberTobeRemove);


        return ErrorCode.ERROR_CODE_SUCCESS;
    }
    
    @Override
    public ErrorCode quitGroup(String operator, String groupId) {
    	HazelcastInstance hzInstance = m_Server.getHazelcastInstance();
		IMap<String, GroupInfo> mIMap = hzInstance.getMap(GROUPS_MAP);


		GroupInfo groupInfo = mIMap.get(groupId);
		if (groupInfo == null) {
            MultiMap<String, GroupOuterClass.GroupMember> groupMembers = hzInstance.getMultiMap(GROUP_MEMBERS);
            MultiMap<String, String> userGroups = hzInstance.getMultiMap(USER_GROUPS);
            for (GroupOuterClass.GroupMember member: groupMembers.get(groupId)
                 ) {
                if (member.getMemberId().equals(operator)) {
                    groupMembers.remove(groupId, member);
                    break;
                }
            }
            userGroups.remove(operator, groupId);
            return ErrorCode.ERROR_CODE_GROUP_NOT_EXIST;
		}
		if (groupInfo.getType() != GroupType.GroupType_Free && groupInfo.getOwner() != null && groupInfo.getOwner().equals(operator)) {
            return ErrorCode.ERROR_CODE_GROUP_NOT_RIGHT;
		}
		MultiMap<String, GroupOuterClass.GroupMember> groupMembers = hzInstance.getMultiMap(GROUP_MEMBERS);
        MultiMap<String, String> userGroups = hzInstance.getMultiMap(USER_GROUPS);
        for (GroupOuterClass.GroupMember member: groupMembers.get(groupId)
            ) {
            if (member.getMemberId().equals(operator)) {
                groupMembers.remove(groupId, member);
                break;
            }
        }
		userGroups.remove(operator, groupId);

        return ErrorCode.ERROR_CODE_SUCCESS;
    }
    
    @Override
    public ErrorCode dismissGroup(String operator, String groupId) {
    	HazelcastInstance hzInstance = m_Server.getHazelcastInstance();
		IMap<String, GroupInfo> mIMap = hzInstance.getMap(GROUPS_MAP);


		GroupInfo groupInfo = mIMap.get(groupId);
		if (groupInfo == null) {
		    //maybe dirty data, remove it
            MultiMap<String, GroupOuterClass.GroupMember> groupMembers = hzInstance.getMultiMap(GROUP_MEMBERS);
            MultiMap<String, String> userGroups = hzInstance.getMultiMap(USER_GROUPS);
            for (GroupOuterClass.GroupMember member :
                groupMembers.get(operator)) {
                userGroups.remove(member.getMemberId(), groupId);
            }
            groupMembers.remove(groupId);


            return ErrorCode.ERROR_CODE_GROUP_NOT_EXIST;
		}
		
		if (groupInfo.getType() == GroupType.GroupType_Free || 
				(groupInfo.getType() == GroupType.GroupType_Restricted || groupInfo.getType() == GroupType.GroupType_Normal) 
				&& (groupInfo.getOwner() == null || !groupInfo.getOwner().equals(operator))) {
            return ErrorCode.ERROR_CODE_GROUP_NOT_RIGHT;
		}
		
		MultiMap<String, GroupOuterClass.GroupMember> groupMembers = hzInstance.getMultiMap(GROUP_MEMBERS);
        MultiMap<String, String> userGroups = hzInstance.getMultiMap(USER_GROUPS);
        for (GroupOuterClass.GroupMember member :
            groupMembers.get(groupId)) {
            userGroups.remove(member.getMemberId(), groupId);
        }

		groupMembers.remove(groupId);
        mIMap.remove(groupId);

        return ErrorCode.ERROR_CODE_SUCCESS;
    }
    
    @Override
    public ErrorCode modifyGroupInfo(String operator, GroupInfo groupInfo) {
		if (groupInfo == null) {
            return ErrorCode.ERROR_CODE_GROUP_NOT_EXIST;
		}
		
    	HazelcastInstance hzInstance = m_Server.getHazelcastInstance();
		IMap<String, GroupInfo> mIMap = hzInstance.getMap(GROUPS_MAP);

		GroupInfo oldInfo = mIMap.get(groupInfo.getTargetId());

		if (oldInfo == null) {
            return ErrorCode.ERROR_CODE_GROUP_NOT_EXIST;
		}
		
		if ((groupInfo.getType() == GroupType.GroupType_Restricted || groupInfo.getType() == GroupType.GroupType_Normal) 
				&& (groupInfo.getOwner() == null || !groupInfo.getOwner().equals(operator))) {
            return ErrorCode.ERROR_CODE_GROUP_NOT_RIGHT;
		}
		
		GroupInfo.Builder newInfoBuilder = oldInfo.toBuilder();
		if (!StringUtil.isNullOrEmpty(groupInfo.getName())) {
			newInfoBuilder = newInfoBuilder.setName(groupInfo.getName());
		}
		
		if (!StringUtil.isNullOrEmpty(groupInfo.getOwner())) {
			newInfoBuilder = newInfoBuilder.setOwner(groupInfo.getOwner());
		}
		
		if (!StringUtil.isNullOrEmpty(groupInfo.getPortrait())) {
			newInfoBuilder = newInfoBuilder.setPortrait(groupInfo.getPortrait());
		}
		
		if (groupInfo.getExtra() == null || groupInfo.getExtra().length() > 0) {
			newInfoBuilder = newInfoBuilder.setExtra(groupInfo.getExtra());
		}
		
		mIMap.put(groupInfo.getTargetId(), newInfoBuilder.build());
        return ErrorCode.ERROR_CODE_SUCCESS;
    }
    
    @Override
    public List<GroupInfo> getGroupInfos(List<String> groupIds) {
    	HazelcastInstance hzInstance = m_Server.getHazelcastInstance();
		IMap<String, GroupInfo> mIMap = hzInstance.getMap(GROUPS_MAP);
		ArrayList<GroupInfo> out = new ArrayList<>();
		for (String groupId : groupIds) {
			GroupInfo groupInfo = mIMap.get(groupId);
			if (groupInfo == null) {
			    groupInfo = getPersistGroupInfo(groupId);
			    if (groupInfo != null) {
			        mIMap.set(groupId, groupInfo);
                }
            }

			if (groupInfo != null) {
				out.add(groupInfo);
			}
		}
		
		return out;
    }
    
    @Override
    public GroupInfo getGroupInfo(String groupId) {
    	HazelcastInstance hzInstance = m_Server.getHazelcastInstance();
		IMap<String, GroupInfo> mIMap = hzInstance.getMap(GROUPS_MAP);

        GroupInfo groupInfo = mIMap.get(groupId);
		if (groupInfo == null) {
            groupInfo = getPersistGroupInfo(groupId);
            if (groupInfo != null) {
                mIMap.put(groupId, groupInfo);
            }
        }
		return groupInfo;
    }
    
    @Override
    public List<GroupOuterClass.GroupMember> getGroupMembers(String groupId) {
    	HazelcastInstance hzInstance = m_Server.getHazelcastInstance();
		IMap<String, GroupInfo> mIMap = hzInstance.getMap(GROUPS_MAP);

		GroupInfo groupInfo = mIMap.get(groupId);
		if (groupInfo == null) {
			return null;//group not exist
		}

		MultiMap<String, GroupOuterClass.GroupMember> groupMembers = hzInstance.getMultiMap(GROUP_MEMBERS);
		return new ArrayList<>(groupMembers.get(groupId));
    }

    @Override
    public List<String> getMyGroups(String fromUser) {
        HazelcastInstance hzInstance = m_Server.getHazelcastInstance();
        MultiMap<String, String> userGroups = hzInstance.getMultiMap(USER_GROUPS);
        return new ArrayList<>(userGroups.get(fromUser));
    }

    @Override
    public ErrorCode transferGroup(String operator, String groupId, String newOwner) {
        HazelcastInstance hzInstance = m_Server.getHazelcastInstance();
        IMap<String, GroupInfo> mIMap = hzInstance.getMap(GROUPS_MAP);


        GroupInfo groupInfo = mIMap.get(groupId);

        if (groupInfo == null) {
            return ErrorCode.ERROR_CODE_GROUP_NOT_EXIST;
        }

        if ((groupInfo.getType() == GroupType.GroupType_Restricted || groupInfo.getType() == GroupType.GroupType_Normal)
            && (groupInfo.getOwner() == null || !groupInfo.getOwner().equals(operator))) {
            return ErrorCode.ERROR_CODE_GROUP_NOT_RIGHT;
        }

        //check the new owner is in member list? is that necessary?

        groupInfo = groupInfo.toBuilder().setOwner(newOwner).build();

        mIMap.set(groupId, groupInfo);

        return ErrorCode.ERROR_CODE_SUCCESS;
    }

    @Override
    public boolean isMemberInGroup(String memberId, String groupId) {
        HazelcastInstance hzInstance = m_Server.getHazelcastInstance();
        MultiMap<String, GroupOuterClass.GroupMember> groupMembers = hzInstance.getMultiMap(GROUP_MEMBERS);

        Collection<GroupOuterClass.GroupMember> members = groupMembers.get(groupId);

        for (GroupOuterClass.GroupMember member: members
             ) {
            if (member.getMemberId().equals(memberId))
                return  true;
        }

        return false;
    }

    @Override
    public ErrorCode getUserInfo(List<PullUserRequestOuterClass.UserRequest> requestList, PullUserResultOuterClass.PullUserResult.Builder builder) {
        HazelcastInstance hzInstance = m_Server.getHazelcastInstance();
        IMap<String, UserOuterClass.User> mUserMap = hzInstance.getMap(USERS);

        for (PullUserRequestOuterClass.UserRequest request : requestList
             ) {
            UserOuterClass.User user = mUserMap.get(request.getUid());
            if (user == null) {
                user = getPersistUser(request.getUid());
                if(user != null) {
                    mUserMap.set(request.getUid(), user);
                }
            }
            PullUserResultOuterClass.UserResult.Builder resultBuilder = PullUserResultOuterClass.UserResult.newBuilder();
            if (user == null) {
                user = UserOuterClass.User.newBuilder().setUid(request.getUid()).build();
                resultBuilder.setUser(user);
                resultBuilder.setCode(PullUserResultOuterClass.UserResultCode.NotFound);
            } else {
                if (user.getUpdateDt() > request.getUpdateDt()) {
                    resultBuilder.setUser(user);
                } else {
                    user = UserOuterClass.User.newBuilder().setUid(request.getUid()).build();
                    resultBuilder.setUser(user);
                    resultBuilder.setCode(PullUserResultOuterClass.UserResultCode.NotModified);
                }
            }
            builder.addResult(resultBuilder.build());

        }

        return  ErrorCode.ERROR_CODE_SUCCESS;
    }

    @Override
    public ErrorCode modifyUserInfo(String userId, ModifyMyInfoOuterClass.ModifyMyInfoRequest request) {
        HazelcastInstance hzInstance = m_Server.getHazelcastInstance();
        IMap<String, UserOuterClass.User> mUserMap = hzInstance.getMap(USERS);


            UserOuterClass.User user = mUserMap.get(userId);
            if (user == null) {
                user = getPersistUser(userId);
                if(user != null) {
                    mUserMap.set(userId, user);
                } else {
                    return ErrorCode.ERROR_CODE_USER_NOT_EXIST;
                }
            }

        UserOuterClass.User.Builder builder = user.toBuilder();
            boolean modified = false;
        for (ModifyMyInfoOuterClass.InfoEntry entry: request.getEntryList()
             ) {
            switch (entry.getType()) {
                case Modify_DisplayName:
                    builder.setDisplayName(entry.getValue());
                    modified = true;
                    break;
                case Modify_Portrait:
                    builder.setPortrait(entry.getValue());
                    modified = true;
                    break;
                case Modify_Mobile:
                    builder.setMobile(entry.getValue());
                    modified = true;
                    break;
                case Modify_Email:
                    builder.setEmail(entry.getValue());
                    modified = true;
                    break;
                case Modify_Address:
                    builder.setAddress(entry.getValue());
                    modified = true;
                    break;
                case Modify_Company:
                    builder.setCompany(entry.getValue());
                    modified = true;
                    break;
                case Modify_Social:

                    break;
                case Modify_Extra:
                    builder.setExtra(entry.getValue());
                    modified = true;
                    break;
                default:
                    break;
            }
        }

        if(modified) {
            builder.setUpdateDt(System.currentTimeMillis());
            user = builder.build();
            mUserMap.set(userId, user);
            updateUser(user);
            return ErrorCode.ERROR_CODE_SUCCESS;
        } else {
            return ErrorCode.ERROR_CODE_NOT_MODIFIED;
        }

    }
    @Override
    public void addUserInfo(UserOuterClass.User user, String password) {
        HazelcastInstance hzInstance = m_Server.getHazelcastInstance();
        IMap<String, UserOuterClass.User> mUserMap = hzInstance.getMap(USERS);
        mUserMap.put(user.getUid(), user);
        persistUser(user, password);
    }

    @Override
    public UserOuterClass.User getUserInfo(String userId) {
        HazelcastInstance hzInstance = m_Server.getHazelcastInstance();
        IMap<String, UserOuterClass.User> mUserMap = hzInstance.getMap(USERS);
        UserOuterClass.User user = mUserMap.get(userId);
        if (user == null) {
            user = getPersistUser(userId);
            if (user != null) {
                mUserMap.put(userId, user);
            }
        }
        return user;
    }

    @Override
    public ErrorCode login(String name, String password, List<String> userIdRet) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = DBUtil.getConnection();
            String sql = "select `_uid`, `_passwd_md5` from t_user where `_name` = ?";

            statement = connection.prepareStatement(sql);

            int index = 1;
            statement.setString(index++, name);

            rs = statement.executeQuery();
            if (rs.next()) {
                String uid = rs.getString(1);
                String pwd_md5 = rs.getString(2);
                try {
                    index++;
                    MessageDigest md5= MessageDigest.getInstance("MD5");
                    BASE64Encoder base64en = new BASE64Encoder();
                    String passwdMd5 = base64en.encode(md5.digest(password.getBytes("utf-8")));
                    if (passwdMd5.equals(pwd_md5)) {
                        userIdRet.add(uid);
                        return ErrorCode.ERROR_CODE_SUCCESS;
                    } else {
                        return ErrorCode.ERROR_CODE_PASSWORD_INCORRECT;
                    }
                } catch (Exception e) {
                    return ErrorCode.ERROR_CODE_PASSWORD_INCORRECT;
                }
            } else {
                return ErrorCode.ERROR_CODE_USER_NOT_EXIST;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return ErrorCode.ERROR_CODE_SERVER_ERROR;
        } finally {
            DBUtil.closeDB(connection, statement, rs);
        }
    }

    @Override
    public List<String> getFriendList(String userId) {
        List<String> out = new ArrayList<String>();

        HazelcastInstance hzInstance = m_Server.getHazelcastInstance();
        MultiMap<String, String> friendsMap = hzInstance.getMultiMap(USER_FRIENDS);
        Collection<String> friends = friendsMap.get(userId);
        if (friends == null || friends.size() == 0) {
            friends = getPersistFriends(userId);
            for (String friend :
                friends) {
                friendsMap.put(userId, friend);
            }
        }

        out.addAll(friends);
        return out;
    }

    @Override
    public List<FriendRequestOuterClass.FriendRequest> getFriendRequestList(String userId) {
        List<FriendRequestOuterClass.FriendRequest> out = new ArrayList<>();

        HazelcastInstance hzInstance = m_Server.getHazelcastInstance();
        MultiMap<String, FriendRequestOuterClass.FriendRequest> requestMap = hzInstance.getMultiMap(USER_FRIENDS_REQUEST);
        Collection<FriendRequestOuterClass.FriendRequest> requests = requestMap.get(userId);
        if (requests == null || requests.size() == 0) {
            requests = getPersistFriendRequests(userId);
            if (requests != null) {
                for (FriendRequestOuterClass.FriendRequest request: requests
                     ) {
                    requestMap.put(userId, request);
                }
            }
        }

        out.addAll(requests);
        return out;
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
