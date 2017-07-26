package win.liyufan.im.proto;

import win.liyufan.im.proto.ConversationOuterClass.ConversationType;
import win.liyufan.im.proto.MessageOuterClass.Message;

public class MessageBundle {
	private String fromUser;
	private String fromClientId;
	private ConversationType type;
	private String targetId;
	private Message message;
	private long messageId;
	public MessageBundle(long messageId, String fromUser, String fromClientId, Message message) {
		super();
		this.fromUser = fromUser;
		this.fromClientId = fromClientId;
		this.type = message.getConversation().getType();
		this.targetId = message.getConversation().getTarget();
		this.message = message;
		this.messageId = messageId;
	}
	
	public long getMessageId() {
		return messageId;
	}

	public String getFromUser() {
		return fromUser;
	}
	public String getFromClientId() {
		return fromClientId;
	}
	public ConversationType getType() {
		return type;
	}
	public String getTargetId() {
		return targetId;
	}
	public Message getMessage() {
		return message;
	}
	
}
