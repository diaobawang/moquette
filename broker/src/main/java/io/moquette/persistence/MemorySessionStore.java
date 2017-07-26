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

import io.moquette.server.Constants;
import io.moquette.server.Server;
import io.moquette.spi.ClientSession;
import io.moquette.spi.IMessagesStore.StoredMessage;
import io.moquette.spi.ISessionsStore;
import io.moquette.spi.ISubscriptionsStore;
import io.moquette.spi.impl.subscriptions.Subscription;
import io.moquette.spi.impl.subscriptions.Topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicReference;

public class MemorySessionStore implements ISessionsStore, ISubscriptionsStore {

    private static final Logger LOG = LoggerFactory.getLogger(MemorySessionStore.class);

    public class Session implements Comparable<Session>{
        final String clientID;
        final String username;
        final ClientSession clientSession;
        final Map<Topic, Subscription> subscriptions = new ConcurrentHashMap<>();
        final AtomicReference<PersistentSession> persistentSession = new AtomicReference<>(null);
        final BlockingQueue<StoredMessage> queue = new ArrayBlockingQueue<>(Constants.MAX_MESSAGE_QUEUE);
        final Map<Integer, StoredMessage> secondPhaseStore = new ConcurrentHashMap<>();
        final Map<Integer, StoredMessage> outboundFlightMessages =
                Collections.synchronizedMap(new HashMap<Integer, StoredMessage>());
        final Map<Integer, StoredMessage> inboundFlightMessages = new ConcurrentHashMap<>();

        Session(String username, String clientID, ClientSession clientSession) {
            this.clientID = clientID;
            this.clientSession = clientSession;
            this.username = username;
        }

		public String getClientID() {
			return clientID;
		}

		public String getUsername() {
			return username;
		}

		public ClientSession getClientSession() {
			return clientSession;
		}

		@Override
		public int compareTo(Session o) {
			// TODO Auto-generated method stub
			if (clientID.equals(o.clientID) && username.equals(o.username)) {
				return 0;
			}
			if (clientID.equals(o.clientID)) {
				return username.compareTo(o.username);
			} else {
				return clientID.compareTo(o.clientID);
			}
		}
        
    }

    private final Map<String, Session> sessions = new ConcurrentHashMap<>();
    private final Map<String, ConcurrentSkipListSet<Session>> userSessions = new ConcurrentHashMap<>();

    private Server mServer;
    public MemorySessionStore(Server server) {
    		mServer = server;
    }

    private Session getSession(String clientID) {
        Session session = sessions.get(clientID);
        if (session == null) {
            LOG.error("Can't find the session for client <{}>", clientID);
            throw new RuntimeException("Can't find the session for client <" + clientID + ">");
        }
        return session;
    }

    @Override
    public void removeSubscription(Topic topic, String clientID) {
        LOG.debug("removeSubscription topic filter: {} for clientID: {}", topic, clientID);
        getSession(clientID).subscriptions.remove(topic);
    }

    @Override
    public void initStore() {
    }

    @Override
    public ISubscriptionsStore subscriptionStore() {
        return this;
    }

    @Override
    public void addNewSubscription(Subscription newSubscription) {
        final String clientID = newSubscription.getClientId();
        Session session = sessions.get(clientID);
        if (session == null) {
            LOG.error("Can't find the session for client <{}>", clientID);
            return;
        }

        session.subscriptions.put(newSubscription.getTopicFilter(), newSubscription);
    }

    @Override
    public void wipeSubscriptions(String clientID) {
        if (!sessions.containsKey(clientID)) {
            LOG.error("Can't find the session for client <{}>", clientID);
            return;
        }

        sessions.get(clientID).subscriptions.clear();
    }

    @Override
    public boolean contains(String clientID) {
        return sessions.containsKey(clientID);
    }

    @Override
    public ClientSession createNewSession(String username, String clientID, boolean cleanSession) {
        LOG.debug("createNewSession for client <{}>", clientID);
        Session session = sessions.get(clientID);
        if (session != null) {
            LOG.error("already exists a session for client <{}>, bad condition", clientID);
            throw new IllegalArgumentException("Can't create a session with the ID of an already existing" + clientID);
        }
        LOG.debug("clientID {} is a newcome, creating it's empty subscriptions set", clientID);
        session = new Session(username, clientID, new ClientSession(clientID, this, this, cleanSession));
        session.persistentSession.set(new PersistentSession(cleanSession));
        sessions.put(clientID, session);
        ConcurrentSkipListSet<Session> sessionSet = userSessions.get(username);
        if (sessionSet == null) {
			sessionSet = new ConcurrentSkipListSet<>();
			userSessions.put(username, sessionSet);
		}
        sessionSet = userSessions.get(username);
        sessionSet.add(session);
        return session.clientSession;
    }

    @Override
    public ClientSession sessionForClient(String clientID) {
        if (!sessions.containsKey(clientID)) {
            LOG.error("Can't find the session for client <{}>", clientID);
            return null;
        }

        PersistentSession storedSession = sessions.get(clientID).persistentSession.get();
        return new ClientSession(clientID, this, this, storedSession.cleanSession);
    }
    
    @Override
    public Collection<Session> sessionForUser(String username) {
    	ConcurrentSkipListSet<Session> sessionSet = userSessions.get(username);
        if (sessionSet == null) {
			sessionSet = new ConcurrentSkipListSet<MemorySessionStore.Session>();
			userSessions.put(username, sessionSet);
		}
        sessionSet = userSessions.get(username);
        return sessionSet;
    }

    @Override
    public Collection<ClientSession> getAllSessions() {
        Collection<ClientSession> result = new ArrayList<>();
        for (Session entry : sessions.values()) {
            result.add(new ClientSession(entry.clientID, this, this, entry.persistentSession.get().cleanSession));
        }
        return result;
    }

    @Override
    public void updateCleanStatus(String clientID, boolean cleanSession) {
        if (!sessions.containsKey(clientID)) {
            LOG.error("Can't find the session for client <{}>", clientID);
            return;
        }

        sessions.get(clientID).persistentSession.set(new PersistentSession(cleanSession));
    }

    @Override
    public List<ClientTopicCouple> listAllSubscriptions() {
        List<ClientTopicCouple> allSubscriptions = new ArrayList<>();
        for (Session entry : sessions.values()) {
            for (Subscription sub : entry.subscriptions.values()) {
                allSubscriptions.add(sub.asClientTopicCouple());
            }
        }
        return allSubscriptions;
    }

    @Override
    public Subscription getSubscription(ClientTopicCouple couple) {
        String clientID = couple.clientID;
        if (!sessions.containsKey(clientID)) {
            LOG.error("Can't find the session for client <{}>", clientID);
            return null;
        }

        Map<Topic, Subscription> subscriptions = sessions.get(clientID).subscriptions;
        if (subscriptions == null || subscriptions.isEmpty()) {
            return null;
        }
        return subscriptions.get(couple.topicFilter);
    }

    @Override
    public List<Subscription> getSubscriptions() {
        List<Subscription> subscriptions = new ArrayList<>();
        for (Session entry : sessions.values()) {
            subscriptions.addAll(entry.subscriptions.values());
        }
        return subscriptions;
    }

    @Override
    public StoredMessage inFlightAck(String clientID, int messageID) {
        return getSession(clientID).outboundFlightMessages.remove(messageID);
    }

    @Override
    public void inFlight(String clientID, int messageID, StoredMessage msg) {
        Session session = sessions.get(clientID);
        if (session == null) {
            LOG.error("Can't find the session for client <{}>", clientID);
            return;
        }

        session.outboundFlightMessages.put(messageID, msg);
    }

    /**
     * Return the next valid packetIdentifier for the given client session.
     */
    @Override
    public int nextPacketID(String clientID) {
        if (!sessions.containsKey(clientID)) {
            LOG.error("Can't find the session for client <{}>", clientID);
            return -1;
        }

        Map<Integer, StoredMessage> m = sessions.get(clientID).outboundFlightMessages;
        int maxId = m.keySet().isEmpty() ? 0 : Collections.max(m.keySet());
        int nextPacketId = (maxId + 1) % 0xFFFF;
        m.put(nextPacketId, null);
        return nextPacketId;
    }

    @Override
    public BlockingQueue<StoredMessage> queue(String clientID) {
        if (!sessions.containsKey(clientID)) {
            LOG.error("Can't find the session for client <{}>", clientID);
            return null;
        }

        return sessions.get(clientID).queue;
    }

    @Override
    public void dropQueue(String clientID) {
        sessions.get(clientID).queue.clear();
    }

    @Override
    public void moveInFlightToSecondPhaseAckWaiting(String clientID, int messageID, StoredMessage msg) {
        LOG.info("Moving msg inflight second phase store, clientID <{}> messageID {}", clientID, messageID);
        Session session = sessions.get(clientID);
        if (session == null) {
            LOG.error("Can't find the session for client <{}>", clientID);
            return;
        }

        session.secondPhaseStore.put(messageID, msg);
        session.outboundFlightMessages.put(messageID, msg);
    }

    @Override
    public StoredMessage secondPhaseAcknowledged(String clientID, int messageID) {
        LOG.info("Acknowledged message in second phase, clientID <{}> messageID {}", clientID, messageID);
        return getSession(clientID).secondPhaseStore.remove(messageID);
    }

    @Override
    public int getInflightMessagesNo(String clientID) {
        Session session = sessions.get(clientID);
        if (session == null) {
            LOG.error("Can't find the session for client <{}>", clientID);
            return 0;
        }

        return session.inboundFlightMessages.size() + session.secondPhaseStore.size()
            + session.outboundFlightMessages.size();
    }

    @Override
    public StoredMessage inboundInflight(String clientID, int messageID) {
        return getSession(clientID).inboundFlightMessages.get(messageID);
    }

    @Override
    public void markAsInboundInflight(String clientID, int messageID, StoredMessage msg) {
        if (!sessions.containsKey(clientID))
            LOG.error("Can't find the session for client <{}>", clientID);

        sessions.get(clientID).inboundFlightMessages.put(messageID, msg);
    }

    @Override
    public int getPendingPublishMessagesNo(String clientID) {
        if (!sessions.containsKey(clientID)) {
            LOG.error("Can't find the session for client <{}>", clientID);
            return 0;
        }

        return sessions.get(clientID).queue.size();
    }

    @Override
    public int getSecondPhaseAckPendingMessages(String clientID) {
        if (!sessions.containsKey(clientID)) {
            LOG.error("Can't find the session for client <{}>", clientID);
            return 0;
        }

        return sessions.get(clientID).secondPhaseStore.size();
    }

    @Override
    public void cleanSession(String clientID) {
        LOG.error("Fooooooooo <{}>", clientID);

        Session session = sessions.get(clientID);
        if (session == null) {
            LOG.error("Can't find the session for client <{}>", clientID);
            return;
        }
        ConcurrentSkipListSet<Session> sessionSet = userSessions.get(session.username);
        if (sessionSet == null) {
			sessionSet = new ConcurrentSkipListSet<>();
			userSessions.put(session.username, sessionSet);
		}
        sessionSet = userSessions.get(session.username);
        sessionSet.remove(session);

        // remove also the messages stored of type QoS1/2
        LOG.info("Removing stored messages with QoS 1 and 2. ClientId={}", clientID);

        session.secondPhaseStore.clear();
        session.outboundFlightMessages.clear();
        session.inboundFlightMessages.clear();

        LOG.info("Wiping existing subscriptions. ClientId={}", clientID);
        wipeSubscriptions(clientID);

        //remove also the enqueued messages
        dropQueue(clientID);

        // TODO this missing last step breaks the junit test
        sessions.remove(clientID);
    }
    
}
