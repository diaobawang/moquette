// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: message.proto

package win.liyufan.im.proto;

public final class MessageOuterClass {
  private MessageOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface MessageOrBuilder extends
      // @@protoc_insertion_point(interface_extends:proto.Message)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>.proto.Conversation conversation = 1;</code>
     */
    boolean hasConversation();
    /**
     * <code>.proto.Conversation conversation = 1;</code>
     */
    win.liyufan.im.proto.ConversationOuterClass.Conversation getConversation();
    /**
     * <code>.proto.Conversation conversation = 1;</code>
     */
    win.liyufan.im.proto.ConversationOuterClass.ConversationOrBuilder getConversationOrBuilder();

    /**
     * <code>.proto.MessageContent content = 2;</code>
     */
    boolean hasContent();
    /**
     * <code>.proto.MessageContent content = 2;</code>
     */
    win.liyufan.im.proto.MessageContentOuterClass.MessageContent getContent();
    /**
     * <code>.proto.MessageContent content = 2;</code>
     */
    win.liyufan.im.proto.MessageContentOuterClass.MessageContentOrBuilder getContentOrBuilder();

    /**
     * <code>int64 message_id = 3;</code>
     */
    long getMessageId();

    /**
     * <code>int64 server_timestamp = 4;</code>
     */
    long getServerTimestamp();
  }
  /**
   * Protobuf type {@code proto.Message}
   */
  public  static final class Message extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:proto.Message)
      MessageOrBuilder {
    // Use Message.newBuilder() to construct.
    private Message(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Message() {
      messageId_ = 0L;
      serverTimestamp_ = 0L;
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }
    private Message(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      int mutable_bitField0_ = 0;
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!input.skipField(tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              win.liyufan.im.proto.ConversationOuterClass.Conversation.Builder subBuilder = null;
              if (conversation_ != null) {
                subBuilder = conversation_.toBuilder();
              }
              conversation_ = input.readMessage(win.liyufan.im.proto.ConversationOuterClass.Conversation.parser(), extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(conversation_);
                conversation_ = subBuilder.buildPartial();
              }

              break;
            }
            case 18: {
              win.liyufan.im.proto.MessageContentOuterClass.MessageContent.Builder subBuilder = null;
              if (content_ != null) {
                subBuilder = content_.toBuilder();
              }
              content_ = input.readMessage(win.liyufan.im.proto.MessageContentOuterClass.MessageContent.parser(), extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(content_);
                content_ = subBuilder.buildPartial();
              }

              break;
            }
            case 24: {

              messageId_ = input.readInt64();
              break;
            }
            case 32: {

              serverTimestamp_ = input.readInt64();
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return win.liyufan.im.proto.MessageOuterClass.internal_static_proto_Message_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return win.liyufan.im.proto.MessageOuterClass.internal_static_proto_Message_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              win.liyufan.im.proto.MessageOuterClass.Message.class, win.liyufan.im.proto.MessageOuterClass.Message.Builder.class);
    }

    public static final int CONVERSATION_FIELD_NUMBER = 1;
    private win.liyufan.im.proto.ConversationOuterClass.Conversation conversation_;
    /**
     * <code>.proto.Conversation conversation = 1;</code>
     */
    public boolean hasConversation() {
      return conversation_ != null;
    }
    /**
     * <code>.proto.Conversation conversation = 1;</code>
     */
    public win.liyufan.im.proto.ConversationOuterClass.Conversation getConversation() {
      return conversation_ == null ? win.liyufan.im.proto.ConversationOuterClass.Conversation.getDefaultInstance() : conversation_;
    }
    /**
     * <code>.proto.Conversation conversation = 1;</code>
     */
    public win.liyufan.im.proto.ConversationOuterClass.ConversationOrBuilder getConversationOrBuilder() {
      return getConversation();
    }

    public static final int CONTENT_FIELD_NUMBER = 2;
    private win.liyufan.im.proto.MessageContentOuterClass.MessageContent content_;
    /**
     * <code>.proto.MessageContent content = 2;</code>
     */
    public boolean hasContent() {
      return content_ != null;
    }
    /**
     * <code>.proto.MessageContent content = 2;</code>
     */
    public win.liyufan.im.proto.MessageContentOuterClass.MessageContent getContent() {
      return content_ == null ? win.liyufan.im.proto.MessageContentOuterClass.MessageContent.getDefaultInstance() : content_;
    }
    /**
     * <code>.proto.MessageContent content = 2;</code>
     */
    public win.liyufan.im.proto.MessageContentOuterClass.MessageContentOrBuilder getContentOrBuilder() {
      return getContent();
    }

    public static final int MESSAGE_ID_FIELD_NUMBER = 3;
    private long messageId_;
    /**
     * <code>int64 message_id = 3;</code>
     */
    public long getMessageId() {
      return messageId_;
    }

    public static final int SERVER_TIMESTAMP_FIELD_NUMBER = 4;
    private long serverTimestamp_;
    /**
     * <code>int64 server_timestamp = 4;</code>
     */
    public long getServerTimestamp() {
      return serverTimestamp_;
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (conversation_ != null) {
        output.writeMessage(1, getConversation());
      }
      if (content_ != null) {
        output.writeMessage(2, getContent());
      }
      if (messageId_ != 0L) {
        output.writeInt64(3, messageId_);
      }
      if (serverTimestamp_ != 0L) {
        output.writeInt64(4, serverTimestamp_);
      }
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (conversation_ != null) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(1, getConversation());
      }
      if (content_ != null) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(2, getContent());
      }
      if (messageId_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(3, messageId_);
      }
      if (serverTimestamp_ != 0L) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt64Size(4, serverTimestamp_);
      }
      memoizedSize = size;
      return size;
    }

    private static final long serialVersionUID = 0L;
    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof win.liyufan.im.proto.MessageOuterClass.Message)) {
        return super.equals(obj);
      }
      win.liyufan.im.proto.MessageOuterClass.Message other = (win.liyufan.im.proto.MessageOuterClass.Message) obj;

      boolean result = true;
      result = result && (hasConversation() == other.hasConversation());
      if (hasConversation()) {
        result = result && getConversation()
            .equals(other.getConversation());
      }
      result = result && (hasContent() == other.hasContent());
      if (hasContent()) {
        result = result && getContent()
            .equals(other.getContent());
      }
      result = result && (getMessageId()
          == other.getMessageId());
      result = result && (getServerTimestamp()
          == other.getServerTimestamp());
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      if (hasConversation()) {
        hash = (37 * hash) + CONVERSATION_FIELD_NUMBER;
        hash = (53 * hash) + getConversation().hashCode();
      }
      if (hasContent()) {
        hash = (37 * hash) + CONTENT_FIELD_NUMBER;
        hash = (53 * hash) + getContent().hashCode();
      }
      hash = (37 * hash) + MESSAGE_ID_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getMessageId());
      hash = (37 * hash) + SERVER_TIMESTAMP_FIELD_NUMBER;
      hash = (53 * hash) + com.google.protobuf.Internal.hashLong(
          getServerTimestamp());
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static win.liyufan.im.proto.MessageOuterClass.Message parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static win.liyufan.im.proto.MessageOuterClass.Message parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static win.liyufan.im.proto.MessageOuterClass.Message parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static win.liyufan.im.proto.MessageOuterClass.Message parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static win.liyufan.im.proto.MessageOuterClass.Message parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static win.liyufan.im.proto.MessageOuterClass.Message parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static win.liyufan.im.proto.MessageOuterClass.Message parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static win.liyufan.im.proto.MessageOuterClass.Message parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static win.liyufan.im.proto.MessageOuterClass.Message parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static win.liyufan.im.proto.MessageOuterClass.Message parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static win.liyufan.im.proto.MessageOuterClass.Message parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static win.liyufan.im.proto.MessageOuterClass.Message parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(win.liyufan.im.proto.MessageOuterClass.Message prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code proto.Message}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:proto.Message)
        win.liyufan.im.proto.MessageOuterClass.MessageOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return win.liyufan.im.proto.MessageOuterClass.internal_static_proto_Message_descriptor;
      }

      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return win.liyufan.im.proto.MessageOuterClass.internal_static_proto_Message_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                win.liyufan.im.proto.MessageOuterClass.Message.class, win.liyufan.im.proto.MessageOuterClass.Message.Builder.class);
      }

      // Construct using win.liyufan.im.proto.MessageOuterClass.Message.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      public Builder clear() {
        super.clear();
        if (conversationBuilder_ == null) {
          conversation_ = null;
        } else {
          conversation_ = null;
          conversationBuilder_ = null;
        }
        if (contentBuilder_ == null) {
          content_ = null;
        } else {
          content_ = null;
          contentBuilder_ = null;
        }
        messageId_ = 0L;

        serverTimestamp_ = 0L;

        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return win.liyufan.im.proto.MessageOuterClass.internal_static_proto_Message_descriptor;
      }

      public win.liyufan.im.proto.MessageOuterClass.Message getDefaultInstanceForType() {
        return win.liyufan.im.proto.MessageOuterClass.Message.getDefaultInstance();
      }

      public win.liyufan.im.proto.MessageOuterClass.Message build() {
        win.liyufan.im.proto.MessageOuterClass.Message result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public win.liyufan.im.proto.MessageOuterClass.Message buildPartial() {
        win.liyufan.im.proto.MessageOuterClass.Message result = new win.liyufan.im.proto.MessageOuterClass.Message(this);
        if (conversationBuilder_ == null) {
          result.conversation_ = conversation_;
        } else {
          result.conversation_ = conversationBuilder_.build();
        }
        if (contentBuilder_ == null) {
          result.content_ = content_;
        } else {
          result.content_ = contentBuilder_.build();
        }
        result.messageId_ = messageId_;
        result.serverTimestamp_ = serverTimestamp_;
        onBuilt();
        return result;
      }

      public Builder clone() {
        return (Builder) super.clone();
      }
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.setField(field, value);
      }
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return (Builder) super.clearField(field);
      }
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return (Builder) super.clearOneof(oneof);
      }
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, Object value) {
        return (Builder) super.setRepeatedField(field, index, value);
      }
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          Object value) {
        return (Builder) super.addRepeatedField(field, value);
      }
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof win.liyufan.im.proto.MessageOuterClass.Message) {
          return mergeFrom((win.liyufan.im.proto.MessageOuterClass.Message)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(win.liyufan.im.proto.MessageOuterClass.Message other) {
        if (other == win.liyufan.im.proto.MessageOuterClass.Message.getDefaultInstance()) return this;
        if (other.hasConversation()) {
          mergeConversation(other.getConversation());
        }
        if (other.hasContent()) {
          mergeContent(other.getContent());
        }
        if (other.getMessageId() != 0L) {
          setMessageId(other.getMessageId());
        }
        if (other.getServerTimestamp() != 0L) {
          setServerTimestamp(other.getServerTimestamp());
        }
        onChanged();
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        win.liyufan.im.proto.MessageOuterClass.Message parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (win.liyufan.im.proto.MessageOuterClass.Message) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private win.liyufan.im.proto.ConversationOuterClass.Conversation conversation_ = null;
      private com.google.protobuf.SingleFieldBuilderV3<
          win.liyufan.im.proto.ConversationOuterClass.Conversation, win.liyufan.im.proto.ConversationOuterClass.Conversation.Builder, win.liyufan.im.proto.ConversationOuterClass.ConversationOrBuilder> conversationBuilder_;
      /**
       * <code>.proto.Conversation conversation = 1;</code>
       */
      public boolean hasConversation() {
        return conversationBuilder_ != null || conversation_ != null;
      }
      /**
       * <code>.proto.Conversation conversation = 1;</code>
       */
      public win.liyufan.im.proto.ConversationOuterClass.Conversation getConversation() {
        if (conversationBuilder_ == null) {
          return conversation_ == null ? win.liyufan.im.proto.ConversationOuterClass.Conversation.getDefaultInstance() : conversation_;
        } else {
          return conversationBuilder_.getMessage();
        }
      }
      /**
       * <code>.proto.Conversation conversation = 1;</code>
       */
      public Builder setConversation(win.liyufan.im.proto.ConversationOuterClass.Conversation value) {
        if (conversationBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          conversation_ = value;
          onChanged();
        } else {
          conversationBuilder_.setMessage(value);
        }

        return this;
      }
      /**
       * <code>.proto.Conversation conversation = 1;</code>
       */
      public Builder setConversation(
          win.liyufan.im.proto.ConversationOuterClass.Conversation.Builder builderForValue) {
        if (conversationBuilder_ == null) {
          conversation_ = builderForValue.build();
          onChanged();
        } else {
          conversationBuilder_.setMessage(builderForValue.build());
        }

        return this;
      }
      /**
       * <code>.proto.Conversation conversation = 1;</code>
       */
      public Builder mergeConversation(win.liyufan.im.proto.ConversationOuterClass.Conversation value) {
        if (conversationBuilder_ == null) {
          if (conversation_ != null) {
            conversation_ =
              win.liyufan.im.proto.ConversationOuterClass.Conversation.newBuilder(conversation_).mergeFrom(value).buildPartial();
          } else {
            conversation_ = value;
          }
          onChanged();
        } else {
          conversationBuilder_.mergeFrom(value);
        }

        return this;
      }
      /**
       * <code>.proto.Conversation conversation = 1;</code>
       */
      public Builder clearConversation() {
        if (conversationBuilder_ == null) {
          conversation_ = null;
          onChanged();
        } else {
          conversation_ = null;
          conversationBuilder_ = null;
        }

        return this;
      }
      /**
       * <code>.proto.Conversation conversation = 1;</code>
       */
      public win.liyufan.im.proto.ConversationOuterClass.Conversation.Builder getConversationBuilder() {
        
        onChanged();
        return getConversationFieldBuilder().getBuilder();
      }
      /**
       * <code>.proto.Conversation conversation = 1;</code>
       */
      public win.liyufan.im.proto.ConversationOuterClass.ConversationOrBuilder getConversationOrBuilder() {
        if (conversationBuilder_ != null) {
          return conversationBuilder_.getMessageOrBuilder();
        } else {
          return conversation_ == null ?
              win.liyufan.im.proto.ConversationOuterClass.Conversation.getDefaultInstance() : conversation_;
        }
      }
      /**
       * <code>.proto.Conversation conversation = 1;</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
          win.liyufan.im.proto.ConversationOuterClass.Conversation, win.liyufan.im.proto.ConversationOuterClass.Conversation.Builder, win.liyufan.im.proto.ConversationOuterClass.ConversationOrBuilder> 
          getConversationFieldBuilder() {
        if (conversationBuilder_ == null) {
          conversationBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
              win.liyufan.im.proto.ConversationOuterClass.Conversation, win.liyufan.im.proto.ConversationOuterClass.Conversation.Builder, win.liyufan.im.proto.ConversationOuterClass.ConversationOrBuilder>(
                  getConversation(),
                  getParentForChildren(),
                  isClean());
          conversation_ = null;
        }
        return conversationBuilder_;
      }

      private win.liyufan.im.proto.MessageContentOuterClass.MessageContent content_ = null;
      private com.google.protobuf.SingleFieldBuilderV3<
          win.liyufan.im.proto.MessageContentOuterClass.MessageContent, win.liyufan.im.proto.MessageContentOuterClass.MessageContent.Builder, win.liyufan.im.proto.MessageContentOuterClass.MessageContentOrBuilder> contentBuilder_;
      /**
       * <code>.proto.MessageContent content = 2;</code>
       */
      public boolean hasContent() {
        return contentBuilder_ != null || content_ != null;
      }
      /**
       * <code>.proto.MessageContent content = 2;</code>
       */
      public win.liyufan.im.proto.MessageContentOuterClass.MessageContent getContent() {
        if (contentBuilder_ == null) {
          return content_ == null ? win.liyufan.im.proto.MessageContentOuterClass.MessageContent.getDefaultInstance() : content_;
        } else {
          return contentBuilder_.getMessage();
        }
      }
      /**
       * <code>.proto.MessageContent content = 2;</code>
       */
      public Builder setContent(win.liyufan.im.proto.MessageContentOuterClass.MessageContent value) {
        if (contentBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          content_ = value;
          onChanged();
        } else {
          contentBuilder_.setMessage(value);
        }

        return this;
      }
      /**
       * <code>.proto.MessageContent content = 2;</code>
       */
      public Builder setContent(
          win.liyufan.im.proto.MessageContentOuterClass.MessageContent.Builder builderForValue) {
        if (contentBuilder_ == null) {
          content_ = builderForValue.build();
          onChanged();
        } else {
          contentBuilder_.setMessage(builderForValue.build());
        }

        return this;
      }
      /**
       * <code>.proto.MessageContent content = 2;</code>
       */
      public Builder mergeContent(win.liyufan.im.proto.MessageContentOuterClass.MessageContent value) {
        if (contentBuilder_ == null) {
          if (content_ != null) {
            content_ =
              win.liyufan.im.proto.MessageContentOuterClass.MessageContent.newBuilder(content_).mergeFrom(value).buildPartial();
          } else {
            content_ = value;
          }
          onChanged();
        } else {
          contentBuilder_.mergeFrom(value);
        }

        return this;
      }
      /**
       * <code>.proto.MessageContent content = 2;</code>
       */
      public Builder clearContent() {
        if (contentBuilder_ == null) {
          content_ = null;
          onChanged();
        } else {
          content_ = null;
          contentBuilder_ = null;
        }

        return this;
      }
      /**
       * <code>.proto.MessageContent content = 2;</code>
       */
      public win.liyufan.im.proto.MessageContentOuterClass.MessageContent.Builder getContentBuilder() {
        
        onChanged();
        return getContentFieldBuilder().getBuilder();
      }
      /**
       * <code>.proto.MessageContent content = 2;</code>
       */
      public win.liyufan.im.proto.MessageContentOuterClass.MessageContentOrBuilder getContentOrBuilder() {
        if (contentBuilder_ != null) {
          return contentBuilder_.getMessageOrBuilder();
        } else {
          return content_ == null ?
              win.liyufan.im.proto.MessageContentOuterClass.MessageContent.getDefaultInstance() : content_;
        }
      }
      /**
       * <code>.proto.MessageContent content = 2;</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
          win.liyufan.im.proto.MessageContentOuterClass.MessageContent, win.liyufan.im.proto.MessageContentOuterClass.MessageContent.Builder, win.liyufan.im.proto.MessageContentOuterClass.MessageContentOrBuilder> 
          getContentFieldBuilder() {
        if (contentBuilder_ == null) {
          contentBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
              win.liyufan.im.proto.MessageContentOuterClass.MessageContent, win.liyufan.im.proto.MessageContentOuterClass.MessageContent.Builder, win.liyufan.im.proto.MessageContentOuterClass.MessageContentOrBuilder>(
                  getContent(),
                  getParentForChildren(),
                  isClean());
          content_ = null;
        }
        return contentBuilder_;
      }

      private long messageId_ ;
      /**
       * <code>int64 message_id = 3;</code>
       */
      public long getMessageId() {
        return messageId_;
      }
      /**
       * <code>int64 message_id = 3;</code>
       */
      public Builder setMessageId(long value) {
        
        messageId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 message_id = 3;</code>
       */
      public Builder clearMessageId() {
        
        messageId_ = 0L;
        onChanged();
        return this;
      }

      private long serverTimestamp_ ;
      /**
       * <code>int64 server_timestamp = 4;</code>
       */
      public long getServerTimestamp() {
        return serverTimestamp_;
      }
      /**
       * <code>int64 server_timestamp = 4;</code>
       */
      public Builder setServerTimestamp(long value) {
        
        serverTimestamp_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>int64 server_timestamp = 4;</code>
       */
      public Builder clearServerTimestamp() {
        
        serverTimestamp_ = 0L;
        onChanged();
        return this;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }


      // @@protoc_insertion_point(builder_scope:proto.Message)
    }

    // @@protoc_insertion_point(class_scope:proto.Message)
    private static final win.liyufan.im.proto.MessageOuterClass.Message DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new win.liyufan.im.proto.MessageOuterClass.Message();
    }

    public static win.liyufan.im.proto.MessageOuterClass.Message getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<Message>
        PARSER = new com.google.protobuf.AbstractParser<Message>() {
      public Message parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new Message(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Message> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<Message> getParserForType() {
      return PARSER;
    }

    public win.liyufan.im.proto.MessageOuterClass.Message getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_proto_Message_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_proto_Message_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\rmessage.proto\022\005proto\032\022conversation.pro" +
      "to\032\025message_content.proto\"\212\001\n\007Message\022)\n" +
      "\014conversation\030\001 \001(\0132\023.proto.Conversation" +
      "\022&\n\007content\030\002 \001(\0132\025.proto.MessageContent" +
      "\022\022\n\nmessage_id\030\003 \001(\003\022\030\n\020server_timestamp" +
      "\030\004 \001(\003B)\n\024win.liyufan.im.protoB\021MessageO" +
      "uterClassb\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          win.liyufan.im.proto.ConversationOuterClass.getDescriptor(),
          win.liyufan.im.proto.MessageContentOuterClass.getDescriptor(),
        }, assigner);
    internal_static_proto_Message_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_proto_Message_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_proto_Message_descriptor,
        new java.lang.String[] { "Conversation", "Content", "MessageId", "ServerTimestamp", });
    win.liyufan.im.proto.ConversationOuterClass.getDescriptor();
    win.liyufan.im.proto.MessageContentOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
