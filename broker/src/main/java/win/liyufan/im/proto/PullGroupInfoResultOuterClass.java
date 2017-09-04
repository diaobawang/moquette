// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: pull_group_info_result.proto

package win.liyufan.im.proto;

public final class PullGroupInfoResultOuterClass {
  private PullGroupInfoResultOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface PullGroupInfoResultOrBuilder extends
      // @@protoc_insertion_point(interface_extends:mars.stn.PullGroupInfoResult)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>repeated .mars.stn.GroupInfo info = 1;</code>
     */
    java.util.List<win.liyufan.im.proto.GroupOuterClass.GroupInfo> 
        getInfoList();
    /**
     * <code>repeated .mars.stn.GroupInfo info = 1;</code>
     */
    win.liyufan.im.proto.GroupOuterClass.GroupInfo getInfo(int index);
    /**
     * <code>repeated .mars.stn.GroupInfo info = 1;</code>
     */
    int getInfoCount();
    /**
     * <code>repeated .mars.stn.GroupInfo info = 1;</code>
     */
    java.util.List<? extends win.liyufan.im.proto.GroupOuterClass.GroupInfoOrBuilder> 
        getInfoOrBuilderList();
    /**
     * <code>repeated .mars.stn.GroupInfo info = 1;</code>
     */
    win.liyufan.im.proto.GroupOuterClass.GroupInfoOrBuilder getInfoOrBuilder(
        int index);
  }
  /**
   * Protobuf type {@code mars.stn.PullGroupInfoResult}
   */
  public  static final class PullGroupInfoResult extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:mars.stn.PullGroupInfoResult)
      PullGroupInfoResultOrBuilder {
    // Use PullGroupInfoResult.newBuilder() to construct.
    private PullGroupInfoResult(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private PullGroupInfoResult() {
      info_ = java.util.Collections.emptyList();
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }
    private PullGroupInfoResult(
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
              if (!((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
                info_ = new java.util.ArrayList<win.liyufan.im.proto.GroupOuterClass.GroupInfo>();
                mutable_bitField0_ |= 0x00000001;
              }
              info_.add(
                  input.readMessage(win.liyufan.im.proto.GroupOuterClass.GroupInfo.parser(), extensionRegistry));
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
        if (((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
          info_ = java.util.Collections.unmodifiableList(info_);
        }
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return win.liyufan.im.proto.PullGroupInfoResultOuterClass.internal_static_mars_stn_PullGroupInfoResult_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return win.liyufan.im.proto.PullGroupInfoResultOuterClass.internal_static_mars_stn_PullGroupInfoResult_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult.class, win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult.Builder.class);
    }

    public static final int INFO_FIELD_NUMBER = 1;
    private java.util.List<win.liyufan.im.proto.GroupOuterClass.GroupInfo> info_;
    /**
     * <code>repeated .mars.stn.GroupInfo info = 1;</code>
     */
    public java.util.List<win.liyufan.im.proto.GroupOuterClass.GroupInfo> getInfoList() {
      return info_;
    }
    /**
     * <code>repeated .mars.stn.GroupInfo info = 1;</code>
     */
    public java.util.List<? extends win.liyufan.im.proto.GroupOuterClass.GroupInfoOrBuilder> 
        getInfoOrBuilderList() {
      return info_;
    }
    /**
     * <code>repeated .mars.stn.GroupInfo info = 1;</code>
     */
    public int getInfoCount() {
      return info_.size();
    }
    /**
     * <code>repeated .mars.stn.GroupInfo info = 1;</code>
     */
    public win.liyufan.im.proto.GroupOuterClass.GroupInfo getInfo(int index) {
      return info_.get(index);
    }
    /**
     * <code>repeated .mars.stn.GroupInfo info = 1;</code>
     */
    public win.liyufan.im.proto.GroupOuterClass.GroupInfoOrBuilder getInfoOrBuilder(
        int index) {
      return info_.get(index);
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
      for (int i = 0; i < info_.size(); i++) {
        output.writeMessage(1, info_.get(i));
      }
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      for (int i = 0; i < info_.size(); i++) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(1, info_.get(i));
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
      if (!(obj instanceof win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult)) {
        return super.equals(obj);
      }
      win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult other = (win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult) obj;

      boolean result = true;
      result = result && getInfoList()
          .equals(other.getInfoList());
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      if (getInfoCount() > 0) {
        hash = (37 * hash) + INFO_FIELD_NUMBER;
        hash = (53 * hash) + getInfoList().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult parseFrom(
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
    public static Builder newBuilder(win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult prototype) {
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
     * Protobuf type {@code mars.stn.PullGroupInfoResult}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:mars.stn.PullGroupInfoResult)
        win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResultOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return win.liyufan.im.proto.PullGroupInfoResultOuterClass.internal_static_mars_stn_PullGroupInfoResult_descriptor;
      }

      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return win.liyufan.im.proto.PullGroupInfoResultOuterClass.internal_static_mars_stn_PullGroupInfoResult_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult.class, win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult.Builder.class);
      }

      // Construct using win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult.newBuilder()
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
          getInfoFieldBuilder();
        }
      }
      public Builder clear() {
        super.clear();
        if (infoBuilder_ == null) {
          info_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
        } else {
          infoBuilder_.clear();
        }
        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return win.liyufan.im.proto.PullGroupInfoResultOuterClass.internal_static_mars_stn_PullGroupInfoResult_descriptor;
      }

      public win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult getDefaultInstanceForType() {
        return win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult.getDefaultInstance();
      }

      public win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult build() {
        win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult buildPartial() {
        win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult result = new win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult(this);
        int from_bitField0_ = bitField0_;
        if (infoBuilder_ == null) {
          if (((bitField0_ & 0x00000001) == 0x00000001)) {
            info_ = java.util.Collections.unmodifiableList(info_);
            bitField0_ = (bitField0_ & ~0x00000001);
          }
          result.info_ = info_;
        } else {
          result.info_ = infoBuilder_.build();
        }
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
        if (other instanceof win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult) {
          return mergeFrom((win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult other) {
        if (other == win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult.getDefaultInstance()) return this;
        if (infoBuilder_ == null) {
          if (!other.info_.isEmpty()) {
            if (info_.isEmpty()) {
              info_ = other.info_;
              bitField0_ = (bitField0_ & ~0x00000001);
            } else {
              ensureInfoIsMutable();
              info_.addAll(other.info_);
            }
            onChanged();
          }
        } else {
          if (!other.info_.isEmpty()) {
            if (infoBuilder_.isEmpty()) {
              infoBuilder_.dispose();
              infoBuilder_ = null;
              info_ = other.info_;
              bitField0_ = (bitField0_ & ~0x00000001);
              infoBuilder_ = 
                com.google.protobuf.GeneratedMessageV3.alwaysUseFieldBuilders ?
                   getInfoFieldBuilder() : null;
            } else {
              infoBuilder_.addAllMessages(other.info_);
            }
          }
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
        win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private java.util.List<win.liyufan.im.proto.GroupOuterClass.GroupInfo> info_ =
        java.util.Collections.emptyList();
      private void ensureInfoIsMutable() {
        if (!((bitField0_ & 0x00000001) == 0x00000001)) {
          info_ = new java.util.ArrayList<win.liyufan.im.proto.GroupOuterClass.GroupInfo>(info_);
          bitField0_ |= 0x00000001;
         }
      }

      private com.google.protobuf.RepeatedFieldBuilderV3<
          win.liyufan.im.proto.GroupOuterClass.GroupInfo, win.liyufan.im.proto.GroupOuterClass.GroupInfo.Builder, win.liyufan.im.proto.GroupOuterClass.GroupInfoOrBuilder> infoBuilder_;

      /**
       * <code>repeated .mars.stn.GroupInfo info = 1;</code>
       */
      public java.util.List<win.liyufan.im.proto.GroupOuterClass.GroupInfo> getInfoList() {
        if (infoBuilder_ == null) {
          return java.util.Collections.unmodifiableList(info_);
        } else {
          return infoBuilder_.getMessageList();
        }
      }
      /**
       * <code>repeated .mars.stn.GroupInfo info = 1;</code>
       */
      public int getInfoCount() {
        if (infoBuilder_ == null) {
          return info_.size();
        } else {
          return infoBuilder_.getCount();
        }
      }
      /**
       * <code>repeated .mars.stn.GroupInfo info = 1;</code>
       */
      public win.liyufan.im.proto.GroupOuterClass.GroupInfo getInfo(int index) {
        if (infoBuilder_ == null) {
          return info_.get(index);
        } else {
          return infoBuilder_.getMessage(index);
        }
      }
      /**
       * <code>repeated .mars.stn.GroupInfo info = 1;</code>
       */
      public Builder setInfo(
          int index, win.liyufan.im.proto.GroupOuterClass.GroupInfo value) {
        if (infoBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureInfoIsMutable();
          info_.set(index, value);
          onChanged();
        } else {
          infoBuilder_.setMessage(index, value);
        }
        return this;
      }
      /**
       * <code>repeated .mars.stn.GroupInfo info = 1;</code>
       */
      public Builder setInfo(
          int index, win.liyufan.im.proto.GroupOuterClass.GroupInfo.Builder builderForValue) {
        if (infoBuilder_ == null) {
          ensureInfoIsMutable();
          info_.set(index, builderForValue.build());
          onChanged();
        } else {
          infoBuilder_.setMessage(index, builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .mars.stn.GroupInfo info = 1;</code>
       */
      public Builder addInfo(win.liyufan.im.proto.GroupOuterClass.GroupInfo value) {
        if (infoBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureInfoIsMutable();
          info_.add(value);
          onChanged();
        } else {
          infoBuilder_.addMessage(value);
        }
        return this;
      }
      /**
       * <code>repeated .mars.stn.GroupInfo info = 1;</code>
       */
      public Builder addInfo(
          int index, win.liyufan.im.proto.GroupOuterClass.GroupInfo value) {
        if (infoBuilder_ == null) {
          if (value == null) {
            throw new NullPointerException();
          }
          ensureInfoIsMutable();
          info_.add(index, value);
          onChanged();
        } else {
          infoBuilder_.addMessage(index, value);
        }
        return this;
      }
      /**
       * <code>repeated .mars.stn.GroupInfo info = 1;</code>
       */
      public Builder addInfo(
          win.liyufan.im.proto.GroupOuterClass.GroupInfo.Builder builderForValue) {
        if (infoBuilder_ == null) {
          ensureInfoIsMutable();
          info_.add(builderForValue.build());
          onChanged();
        } else {
          infoBuilder_.addMessage(builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .mars.stn.GroupInfo info = 1;</code>
       */
      public Builder addInfo(
          int index, win.liyufan.im.proto.GroupOuterClass.GroupInfo.Builder builderForValue) {
        if (infoBuilder_ == null) {
          ensureInfoIsMutable();
          info_.add(index, builderForValue.build());
          onChanged();
        } else {
          infoBuilder_.addMessage(index, builderForValue.build());
        }
        return this;
      }
      /**
       * <code>repeated .mars.stn.GroupInfo info = 1;</code>
       */
      public Builder addAllInfo(
          java.lang.Iterable<? extends win.liyufan.im.proto.GroupOuterClass.GroupInfo> values) {
        if (infoBuilder_ == null) {
          ensureInfoIsMutable();
          com.google.protobuf.AbstractMessageLite.Builder.addAll(
              values, info_);
          onChanged();
        } else {
          infoBuilder_.addAllMessages(values);
        }
        return this;
      }
      /**
       * <code>repeated .mars.stn.GroupInfo info = 1;</code>
       */
      public Builder clearInfo() {
        if (infoBuilder_ == null) {
          info_ = java.util.Collections.emptyList();
          bitField0_ = (bitField0_ & ~0x00000001);
          onChanged();
        } else {
          infoBuilder_.clear();
        }
        return this;
      }
      /**
       * <code>repeated .mars.stn.GroupInfo info = 1;</code>
       */
      public Builder removeInfo(int index) {
        if (infoBuilder_ == null) {
          ensureInfoIsMutable();
          info_.remove(index);
          onChanged();
        } else {
          infoBuilder_.remove(index);
        }
        return this;
      }
      /**
       * <code>repeated .mars.stn.GroupInfo info = 1;</code>
       */
      public win.liyufan.im.proto.GroupOuterClass.GroupInfo.Builder getInfoBuilder(
          int index) {
        return getInfoFieldBuilder().getBuilder(index);
      }
      /**
       * <code>repeated .mars.stn.GroupInfo info = 1;</code>
       */
      public win.liyufan.im.proto.GroupOuterClass.GroupInfoOrBuilder getInfoOrBuilder(
          int index) {
        if (infoBuilder_ == null) {
          return info_.get(index);  } else {
          return infoBuilder_.getMessageOrBuilder(index);
        }
      }
      /**
       * <code>repeated .mars.stn.GroupInfo info = 1;</code>
       */
      public java.util.List<? extends win.liyufan.im.proto.GroupOuterClass.GroupInfoOrBuilder> 
           getInfoOrBuilderList() {
        if (infoBuilder_ != null) {
          return infoBuilder_.getMessageOrBuilderList();
        } else {
          return java.util.Collections.unmodifiableList(info_);
        }
      }
      /**
       * <code>repeated .mars.stn.GroupInfo info = 1;</code>
       */
      public win.liyufan.im.proto.GroupOuterClass.GroupInfo.Builder addInfoBuilder() {
        return getInfoFieldBuilder().addBuilder(
            win.liyufan.im.proto.GroupOuterClass.GroupInfo.getDefaultInstance());
      }
      /**
       * <code>repeated .mars.stn.GroupInfo info = 1;</code>
       */
      public win.liyufan.im.proto.GroupOuterClass.GroupInfo.Builder addInfoBuilder(
          int index) {
        return getInfoFieldBuilder().addBuilder(
            index, win.liyufan.im.proto.GroupOuterClass.GroupInfo.getDefaultInstance());
      }
      /**
       * <code>repeated .mars.stn.GroupInfo info = 1;</code>
       */
      public java.util.List<win.liyufan.im.proto.GroupOuterClass.GroupInfo.Builder> 
           getInfoBuilderList() {
        return getInfoFieldBuilder().getBuilderList();
      }
      private com.google.protobuf.RepeatedFieldBuilderV3<
          win.liyufan.im.proto.GroupOuterClass.GroupInfo, win.liyufan.im.proto.GroupOuterClass.GroupInfo.Builder, win.liyufan.im.proto.GroupOuterClass.GroupInfoOrBuilder> 
          getInfoFieldBuilder() {
        if (infoBuilder_ == null) {
          infoBuilder_ = new com.google.protobuf.RepeatedFieldBuilderV3<
              win.liyufan.im.proto.GroupOuterClass.GroupInfo, win.liyufan.im.proto.GroupOuterClass.GroupInfo.Builder, win.liyufan.im.proto.GroupOuterClass.GroupInfoOrBuilder>(
                  info_,
                  ((bitField0_ & 0x00000001) == 0x00000001),
                  getParentForChildren(),
                  isClean());
          info_ = null;
        }
        return infoBuilder_;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return this;
      }


      // @@protoc_insertion_point(builder_scope:mars.stn.PullGroupInfoResult)
    }

    // @@protoc_insertion_point(class_scope:mars.stn.PullGroupInfoResult)
    private static final win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult();
    }

    public static win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<PullGroupInfoResult>
        PARSER = new com.google.protobuf.AbstractParser<PullGroupInfoResult>() {
      public PullGroupInfoResult parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new PullGroupInfoResult(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<PullGroupInfoResult> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<PullGroupInfoResult> getParserForType() {
      return PARSER;
    }

    public win.liyufan.im.proto.PullGroupInfoResultOuterClass.PullGroupInfoResult getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_mars_stn_PullGroupInfoResult_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_mars_stn_PullGroupInfoResult_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\034pull_group_info_result.proto\022\010mars.stn" +
      "\032\013group.proto\"8\n\023PullGroupInfoResult\022!\n\004" +
      "info\030\001 \003(\0132\023.mars.stn.GroupInfoB5\n\024win.l" +
      "iyufan.im.protoB\035PullGroupInfoResultOute" +
      "rClassb\006proto3"
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
          win.liyufan.im.proto.GroupOuterClass.getDescriptor(),
        }, assigner);
    internal_static_mars_stn_PullGroupInfoResult_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_mars_stn_PullGroupInfoResult_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_mars_stn_PullGroupInfoResult_descriptor,
        new java.lang.String[] { "Info", });
    win.liyufan.im.proto.GroupOuterClass.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
