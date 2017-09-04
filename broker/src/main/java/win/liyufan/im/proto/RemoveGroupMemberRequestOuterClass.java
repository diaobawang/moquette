// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: remove_group_member_request.proto

package win.liyufan.im.proto;

public final class RemoveGroupMemberRequestOuterClass {
  private RemoveGroupMemberRequestOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface RemoveGroupMemberRequestOrBuilder extends
      // @@protoc_insertion_point(interface_extends:mars.stn.RemoveGroupMemberRequest)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string group_id = 1;</code>
     */
    java.lang.String getGroupId();
    /**
     * <code>string group_id = 1;</code>
     */
    com.google.protobuf.ByteString
        getGroupIdBytes();

    /**
     * <code>repeated string removed_member = 2;</code>
     */
    java.util.List<java.lang.String>
        getRemovedMemberList();
    /**
     * <code>repeated string removed_member = 2;</code>
     */
    int getRemovedMemberCount();
    /**
     * <code>repeated string removed_member = 2;</code>
     */
    java.lang.String getRemovedMember(int index);
    /**
     * <code>repeated string removed_member = 2;</code>
     */
    com.google.protobuf.ByteString
        getRemovedMemberBytes(int index);

    /**
     * <code>.mars.stn.MessageContent notify_content = 3;</code>
     */
    boolean hasNotifyContent();
    /**
     * <code>.mars.stn.MessageContent notify_content = 3;</code>
     */
    win.liyufan.im.proto.MessageContentOuterClass.MessageContent getNotifyContent();
    /**
     * <code>.mars.stn.MessageContent notify_content = 3;</code>
     */
    win.liyufan.im.proto.MessageContentOuterClass.MessageContentOrBuilder getNotifyContentOrBuilder();
  }
  /**
   * Protobuf type {@code mars.stn.RemoveGroupMemberRequest}
   */
  public  static final class RemoveGroupMemberRequest extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:mars.stn.RemoveGroupMemberRequest)
      RemoveGroupMemberRequestOrBuilder {
    // Use RemoveGroupMemberRequest.newBuilder() to construct.
    private RemoveGroupMemberRequest(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private RemoveGroupMemberRequest() {
      groupId_ = "";
      removedMember_ = com.google.protobuf.LazyStringArrayList.EMPTY;
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }
    private RemoveGroupMemberRequest(
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
              java.lang.String s = input.readStringRequireUtf8();

              groupId_ = s;
              break;
            }
            case 18: {
              java.lang.String s = input.readStringRequireUtf8();
              if (!((mutable_bitField0_ & 0x00000002) == 0x00000002)) {
                removedMember_ = new com.google.protobuf.LazyStringArrayList();
                mutable_bitField0_ |= 0x00000002;
              }
              removedMember_.add(s);
              break;
            }
            case 26: {
              win.liyufan.im.proto.MessageContentOuterClass.MessageContent.Builder subBuilder = null;
              if (notifyContent_ != null) {
                subBuilder = notifyContent_.toBuilder();
              }
              notifyContent_ = input.readMessage(win.liyufan.im.proto.MessageContentOuterClass.MessageContent.parser(), extensionRegistry);
              if (subBuilder != null) {
                subBuilder.mergeFrom(notifyContent_);
                notifyContent_ = subBuilder.buildPartial();
              }

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
        if (((mutable_bitField0_ & 0x00000002) == 0x00000002)) {
          removedMember_ = removedMember_.getUnmodifiableView();
        }
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.internal_static_mars_stn_RemoveGroupMemberRequest_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.internal_static_mars_stn_RemoveGroupMemberRequest_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest.class, win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest.Builder.class);
    }

    private int bitField0_;
    public static final int GROUP_ID_FIELD_NUMBER = 1;
    private volatile java.lang.Object groupId_;
    /**
     * <code>string group_id = 1;</code>
     */
    public java.lang.String getGroupId() {
      java.lang.Object ref = groupId_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        groupId_ = s;
        return s;
      }
    }
    /**
     * <code>string group_id = 1;</code>
     */
    public com.google.protobuf.ByteString
        getGroupIdBytes() {
      java.lang.Object ref = groupId_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        groupId_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int REMOVED_MEMBER_FIELD_NUMBER = 2;
    private com.google.protobuf.LazyStringList removedMember_;
    /**
     * <code>repeated string removed_member = 2;</code>
     */
    public com.google.protobuf.ProtocolStringList
        getRemovedMemberList() {
      return removedMember_;
    }
    /**
     * <code>repeated string removed_member = 2;</code>
     */
    public int getRemovedMemberCount() {
      return removedMember_.size();
    }
    /**
     * <code>repeated string removed_member = 2;</code>
     */
    public java.lang.String getRemovedMember(int index) {
      return removedMember_.get(index);
    }
    /**
     * <code>repeated string removed_member = 2;</code>
     */
    public com.google.protobuf.ByteString
        getRemovedMemberBytes(int index) {
      return removedMember_.getByteString(index);
    }

    public static final int NOTIFY_CONTENT_FIELD_NUMBER = 3;
    private win.liyufan.im.proto.MessageContentOuterClass.MessageContent notifyContent_;
    /**
     * <code>.mars.stn.MessageContent notify_content = 3;</code>
     */
    public boolean hasNotifyContent() {
      return notifyContent_ != null;
    }
    /**
     * <code>.mars.stn.MessageContent notify_content = 3;</code>
     */
    public win.liyufan.im.proto.MessageContentOuterClass.MessageContent getNotifyContent() {
      return notifyContent_ == null ? win.liyufan.im.proto.MessageContentOuterClass.MessageContent.getDefaultInstance() : notifyContent_;
    }
    /**
     * <code>.mars.stn.MessageContent notify_content = 3;</code>
     */
    public win.liyufan.im.proto.MessageContentOuterClass.MessageContentOrBuilder getNotifyContentOrBuilder() {
      return getNotifyContent();
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
      if (!getGroupIdBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, groupId_);
      }
      for (int i = 0; i < removedMember_.size(); i++) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 2, removedMember_.getRaw(i));
      }
      if (notifyContent_ != null) {
        output.writeMessage(3, getNotifyContent());
      }
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getGroupIdBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, groupId_);
      }
      {
        int dataSize = 0;
        for (int i = 0; i < removedMember_.size(); i++) {
          dataSize += computeStringSizeNoTag(removedMember_.getRaw(i));
        }
        size += dataSize;
        size += 1 * getRemovedMemberList().size();
      }
      if (notifyContent_ != null) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(3, getNotifyContent());
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
      if (!(obj instanceof win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest)) {
        return super.equals(obj);
      }
      win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest other = (win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest) obj;

      boolean result = true;
      result = result && getGroupId()
          .equals(other.getGroupId());
      result = result && getRemovedMemberList()
          .equals(other.getRemovedMemberList());
      result = result && (hasNotifyContent() == other.hasNotifyContent());
      if (hasNotifyContent()) {
        result = result && getNotifyContent()
            .equals(other.getNotifyContent());
      }
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + GROUP_ID_FIELD_NUMBER;
      hash = (53 * hash) + getGroupId().hashCode();
      if (getRemovedMemberCount() > 0) {
        hash = (37 * hash) + REMOVED_MEMBER_FIELD_NUMBER;
        hash = (53 * hash) + getRemovedMemberList().hashCode();
      }
      if (hasNotifyContent()) {
        hash = (37 * hash) + NOTIFY_CONTENT_FIELD_NUMBER;
        hash = (53 * hash) + getNotifyContent().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest parseFrom(
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
    public static Builder newBuilder(win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest prototype) {
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
     * Protobuf type {@code mars.stn.RemoveGroupMemberRequest}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:mars.stn.RemoveGroupMemberRequest)
        win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequestOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.internal_static_mars_stn_RemoveGroupMemberRequest_descriptor;
      }

      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.internal_static_mars_stn_RemoveGroupMemberRequest_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest.class, win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest.Builder.class);
      }

      // Construct using win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest.newBuilder()
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
        groupId_ = "";

        removedMember_ = com.google.protobuf.LazyStringArrayList.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000002);
        if (notifyContentBuilder_ == null) {
          notifyContent_ = null;
        } else {
          notifyContent_ = null;
          notifyContentBuilder_ = null;
        }
        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.internal_static_mars_stn_RemoveGroupMemberRequest_descriptor;
      }

      public win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest getDefaultInstanceForType() {
        return win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest.getDefaultInstance();
      }

      public win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest build() {
        win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest buildPartial() {
        win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest result = new win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        result.groupId_ = groupId_;
        if (((bitField0_ & 0x00000002) == 0x00000002)) {
          removedMember_ = removedMember_.getUnmodifiableView();
          bitField0_ = (bitField0_ & ~0x00000002);
        }
        result.removedMember_ = removedMember_;
        if (notifyContentBuilder_ == null) {
          result.notifyContent_ = notifyContent_;
        } else {
          result.notifyContent_ = notifyContentBuilder_.build();
        }
        result.bitField0_ = to_bitField0_;
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
        if (other instanceof win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest) {
          return mergeFrom((win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest other) {
        if (other == win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest.getDefaultInstance()) return this;
        if (!other.getGroupId().isEmpty()) {
          groupId_ = other.groupId_;
          onChanged();
        }
        if (!other.removedMember_.isEmpty()) {
          if (removedMember_.isEmpty()) {
            removedMember_ = other.removedMember_;
            bitField0_ = (bitField0_ & ~0x00000002);
          } else {
            ensureRemovedMemberIsMutable();
            removedMember_.addAll(other.removedMember_);
          }
          onChanged();
        }
        if (other.hasNotifyContent()) {
          mergeNotifyContent(other.getNotifyContent());
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
        win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private java.lang.Object groupId_ = "";
      /**
       * <code>string group_id = 1;</code>
       */
      public java.lang.String getGroupId() {
        java.lang.Object ref = groupId_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          groupId_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string group_id = 1;</code>
       */
      public com.google.protobuf.ByteString
          getGroupIdBytes() {
        java.lang.Object ref = groupId_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          groupId_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string group_id = 1;</code>
       */
      public Builder setGroupId(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        groupId_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string group_id = 1;</code>
       */
      public Builder clearGroupId() {
        
        groupId_ = getDefaultInstance().getGroupId();
        onChanged();
        return this;
      }
      /**
       * <code>string group_id = 1;</code>
       */
      public Builder setGroupIdBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        groupId_ = value;
        onChanged();
        return this;
      }

      private com.google.protobuf.LazyStringList removedMember_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      private void ensureRemovedMemberIsMutable() {
        if (!((bitField0_ & 0x00000002) == 0x00000002)) {
          removedMember_ = new com.google.protobuf.LazyStringArrayList(removedMember_);
          bitField0_ |= 0x00000002;
         }
      }
      /**
       * <code>repeated string removed_member = 2;</code>
       */
      public com.google.protobuf.ProtocolStringList
          getRemovedMemberList() {
        return removedMember_.getUnmodifiableView();
      }
      /**
       * <code>repeated string removed_member = 2;</code>
       */
      public int getRemovedMemberCount() {
        return removedMember_.size();
      }
      /**
       * <code>repeated string removed_member = 2;</code>
       */
      public java.lang.String getRemovedMember(int index) {
        return removedMember_.get(index);
      }
      /**
       * <code>repeated string removed_member = 2;</code>
       */
      public com.google.protobuf.ByteString
          getRemovedMemberBytes(int index) {
        return removedMember_.getByteString(index);
      }
      /**
       * <code>repeated string removed_member = 2;</code>
       */
      public Builder setRemovedMember(
          int index, java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  ensureRemovedMemberIsMutable();
        removedMember_.set(index, value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string removed_member = 2;</code>
       */
      public Builder addRemovedMember(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  ensureRemovedMemberIsMutable();
        removedMember_.add(value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string removed_member = 2;</code>
       */
      public Builder addAllRemovedMember(
          java.lang.Iterable<java.lang.String> values) {
        ensureRemovedMemberIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, removedMember_);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string removed_member = 2;</code>
       */
      public Builder clearRemovedMember() {
        removedMember_ = com.google.protobuf.LazyStringArrayList.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000002);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string removed_member = 2;</code>
       */
      public Builder addRemovedMemberBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        ensureRemovedMemberIsMutable();
        removedMember_.add(value);
        onChanged();
        return this;
      }

      private win.liyufan.im.proto.MessageContentOuterClass.MessageContent notifyContent_ = null;
      private com.google.protobuf.SingleFieldBuilderV3<
          win.liyufan.im.proto.MessageContentOuterClass.MessageContent, win.liyufan.im.proto.MessageContentOuterClass.MessageContent.Builder, win.liyufan.im.proto.MessageContentOuterClass.MessageContentOrBuilder> notifyContentBuilder_;
      /**
       * <code>.mars.stn.MessageContent notify_content = 3;</code>
       */
      public boolean hasNotifyContent() {
        return notifyContentBuilder_ != null || notifyContent_ != null;
      }
      /**
       * <code>.mars.stn.MessageContent notify_content = 3;</code>
       */
      public win.liyufan.im.proto.MessageContentOuterClass.MessageContent getNotifyContent() {
        if (notifyContentBuilder_ == null) {
          return notifyContent_ == null ? win.liyufan.im.proto.MessageContentOuterClass.MessageContent.getDefaultInstance() : notifyContent_;
        } else {
          return notifyContentBuilder_.getMessage();
        }
      }
      /**
       * <code>.mars.stn.MessageContent notify_content = 3;</code>
       */
      public Builder setNotifyContent(win.liyufan.im.proto.MessageContentOuterClass.MessageContent value) {
        if (notifyContentBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          notifyContent_ = value;
          onChanged();
        } else {
          notifyContentBuilder_.setMessage(value);
        }

        return this;
      }
      /**
       * <code>.mars.stn.MessageContent notify_content = 3;</code>
       */
      public Builder setNotifyContent(
          win.liyufan.im.proto.MessageContentOuterClass.MessageContent.Builder builderForValue) {
        if (notifyContentBuilder_ == null) {
          notifyContent_ = builderForValue.build();
          onChanged();
        } else {
          notifyContentBuilder_.setMessage(builderForValue.build());
        }

        return this;
      }
      /**
       * <code>.mars.stn.MessageContent notify_content = 3;</code>
       */
      public Builder mergeNotifyContent(win.liyufan.im.proto.MessageContentOuterClass.MessageContent value) {
        if (notifyContentBuilder_ == null) {
          if (notifyContent_ != null) {
            notifyContent_ =
              win.liyufan.im.proto.MessageContentOuterClass.MessageContent.newBuilder(notifyContent_).mergeFrom(value).buildPartial();
          } else {
            notifyContent_ = value;
          }
          onChanged();
        } else {
          notifyContentBuilder_.mergeFrom(value);
        }

        return this;
      }
      /**
       * <code>.mars.stn.MessageContent notify_content = 3;</code>
       */
      public Builder clearNotifyContent() {
        if (notifyContentBuilder_ == null) {
          notifyContent_ = null;
          onChanged();
        } else {
          notifyContent_ = null;
          notifyContentBuilder_ = null;
        }

        return this;
      }
      /**
       * <code>.mars.stn.MessageContent notify_content = 3;</code>
       */
      public win.liyufan.im.proto.MessageContentOuterClass.MessageContent.Builder getNotifyContentBuilder() {
        
        onChanged();
        return getNotifyContentFieldBuilder().getBuilder();
      }
      /**
       * <code>.mars.stn.MessageContent notify_content = 3;</code>
       */
      public win.liyufan.im.proto.MessageContentOuterClass.MessageContentOrBuilder getNotifyContentOrBuilder() {
        if (notifyContentBuilder_ != null) {
          return notifyContentBuilder_.getMessageOrBuilder();
        } else {
          return notifyContent_ == null ?
              win.liyufan.im.proto.MessageContentOuterClass.MessageContent.getDefaultInstance() : notifyContent_;
        }
      }
      /**
       * <code>.mars.stn.MessageContent notify_content = 3;</code>
       */
      private com.google.protobuf.SingleFieldBuilderV3<
          win.liyufan.im.proto.MessageContentOuterClass.MessageContent, win.liyufan.im.proto.MessageContentOuterClass.MessageContent.Builder, win.liyufan.im.proto.MessageContentOuterClass.MessageContentOrBuilder> 
          getNotifyContentFieldBuilder() {
        if (notifyContentBuilder_ == null) {
          notifyContentBuilder_ = new com.google.protobuf.SingleFieldBuilderV3<
              win.liyufan.im.proto.MessageContentOuterClass.MessageContent, win.liyufan.im.proto.MessageContentOuterClass.MessageContent.Builder, win.liyufan.im.proto.MessageContentOuterClass.MessageContentOrBuilder>(
                  getNotifyContent(),
                  getParentForChildren(),
                  isClean());
          notifyContent_ = null;
        }
        return notifyContentBuilder_;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }


      // @@protoc_insertion_point(builder_scope:mars.stn.RemoveGroupMemberRequest)
    }

    // @@protoc_insertion_point(class_scope:mars.stn.RemoveGroupMemberRequest)
    private static final win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest();
    }

    public static win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<RemoveGroupMemberRequest>
        PARSER = new com.google.protobuf.AbstractParser<RemoveGroupMemberRequest>() {
      public RemoveGroupMemberRequest parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new RemoveGroupMemberRequest(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<RemoveGroupMemberRequest> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<RemoveGroupMemberRequest> getParserForType() {
      return PARSER;
    }

    public win.liyufan.im.proto.RemoveGroupMemberRequestOuterClass.RemoveGroupMemberRequest getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_mars_stn_RemoveGroupMemberRequest_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_mars_stn_RemoveGroupMemberRequest_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n!remove_group_member_request.proto\022\010mar" +
      "s.stn\032\025message_content.proto\"v\n\030RemoveGr" +
      "oupMemberRequest\022\020\n\010group_id\030\001 \001(\t\022\026\n\016re" +
      "moved_member\030\002 \003(\t\0220\n\016notify_content\030\003 \001" +
      "(\0132\030.mars.stn.MessageContentB:\n\024win.liyu" +
      "fan.im.protoB\"RemoveGroupMemberRequestOu" +
      "terClassb\006proto3"
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
          win.liyufan.im.proto.MessageContentOuterClass.getDescriptor(),
        }, assigner);
    internal_static_mars_stn_RemoveGroupMemberRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_mars_stn_RemoveGroupMemberRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_mars_stn_RemoveGroupMemberRequest_descriptor,
        new java.lang.String[] { "GroupId", "RemovedMember", "NotifyContent", });
    win.liyufan.im.proto.MessageContentOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
