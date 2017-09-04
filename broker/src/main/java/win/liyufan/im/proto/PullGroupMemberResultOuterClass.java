// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: pull_group_member_result.proto

package win.liyufan.im.proto;

public final class PullGroupMemberResultOuterClass {
  private PullGroupMemberResultOuterClass() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface PullGroupMemberResultOrBuilder extends
      // @@protoc_insertion_point(interface_extends:mars.stn.PullGroupMemberResult)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>repeated string member = 1;</code>
     */
    java.util.List<java.lang.String>
        getMemberList();
    /**
     * <code>repeated string member = 1;</code>
     */
    int getMemberCount();
    /**
     * <code>repeated string member = 1;</code>
     */
    java.lang.String getMember(int index);
    /**
     * <code>repeated string member = 1;</code>
     */
    com.google.protobuf.ByteString
        getMemberBytes(int index);
  }
  /**
   * Protobuf type {@code mars.stn.PullGroupMemberResult}
   */
  public  static final class PullGroupMemberResult extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:mars.stn.PullGroupMemberResult)
      PullGroupMemberResultOrBuilder {
    // Use PullGroupMemberResult.newBuilder() to construct.
    private PullGroupMemberResult(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private PullGroupMemberResult() {
      member_ = com.google.protobuf.LazyStringArrayList.EMPTY;
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return com.google.protobuf.UnknownFieldSet.getDefaultInstance();
    }
    private PullGroupMemberResult(
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
              if (!((mutable_bitField0_ & 0x00000001) == 0x00000001)) {
                member_ = new com.google.protobuf.LazyStringArrayList();
                mutable_bitField0_ |= 0x00000001;
              }
              member_.add(s);
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
          member_ = member_.getUnmodifiableView();
        }
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return win.liyufan.im.proto.PullGroupMemberResultOuterClass.internal_static_mars_stn_PullGroupMemberResult_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return win.liyufan.im.proto.PullGroupMemberResultOuterClass.internal_static_mars_stn_PullGroupMemberResult_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult.class, win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult.Builder.class);
    }

    public static final int MEMBER_FIELD_NUMBER = 1;
    private com.google.protobuf.LazyStringList member_;
    /**
     * <code>repeated string member = 1;</code>
     */
    public com.google.protobuf.ProtocolStringList
        getMemberList() {
      return member_;
    }
    /**
     * <code>repeated string member = 1;</code>
     */
    public int getMemberCount() {
      return member_.size();
    }
    /**
     * <code>repeated string member = 1;</code>
     */
    public java.lang.String getMember(int index) {
      return member_.get(index);
    }
    /**
     * <code>repeated string member = 1;</code>
     */
    public com.google.protobuf.ByteString
        getMemberBytes(int index) {
      return member_.getByteString(index);
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
      for (int i = 0; i < member_.size(); i++) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, member_.getRaw(i));
      }
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      {
        int dataSize = 0;
        for (int i = 0; i < member_.size(); i++) {
          dataSize += computeStringSizeNoTag(member_.getRaw(i));
        }
        size += dataSize;
        size += 1 * getMemberList().size();
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
      if (!(obj instanceof win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult)) {
        return super.equals(obj);
      }
      win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult other = (win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult) obj;

      boolean result = true;
      result = result && getMemberList()
          .equals(other.getMemberList());
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      if (getMemberCount() > 0) {
        hash = (37 * hash) + MEMBER_FIELD_NUMBER;
        hash = (53 * hash) + getMemberList().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult parseFrom(
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
    public static Builder newBuilder(win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult prototype) {
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
     * Protobuf type {@code mars.stn.PullGroupMemberResult}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:mars.stn.PullGroupMemberResult)
        win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResultOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return win.liyufan.im.proto.PullGroupMemberResultOuterClass.internal_static_mars_stn_PullGroupMemberResult_descriptor;
      }

      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return win.liyufan.im.proto.PullGroupMemberResultOuterClass.internal_static_mars_stn_PullGroupMemberResult_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult.class, win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult.Builder.class);
      }

      // Construct using win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult.newBuilder()
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
        member_ = com.google.protobuf.LazyStringArrayList.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000001);
        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return win.liyufan.im.proto.PullGroupMemberResultOuterClass.internal_static_mars_stn_PullGroupMemberResult_descriptor;
      }

      public win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult getDefaultInstanceForType() {
        return win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult.getDefaultInstance();
      }

      public win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult build() {
        win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult buildPartial() {
        win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult result = new win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult(this);
        int from_bitField0_ = bitField0_;
        if (((bitField0_ & 0x00000001) == 0x00000001)) {
          member_ = member_.getUnmodifiableView();
          bitField0_ = (bitField0_ & ~0x00000001);
        }
        result.member_ = member_;
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
        if (other instanceof win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult) {
          return mergeFrom((win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult other) {
        if (other == win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult.getDefaultInstance()) return this;
        if (!other.member_.isEmpty()) {
          if (member_.isEmpty()) {
            member_ = other.member_;
            bitField0_ = (bitField0_ & ~0x00000001);
          } else {
            ensureMemberIsMutable();
            member_.addAll(other.member_);
          }
          onChanged();
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
        win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private com.google.protobuf.LazyStringList member_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      private void ensureMemberIsMutable() {
        if (!((bitField0_ & 0x00000001) == 0x00000001)) {
          member_ = new com.google.protobuf.LazyStringArrayList(member_);
          bitField0_ |= 0x00000001;
         }
      }
      /**
       * <code>repeated string member = 1;</code>
       */
      public com.google.protobuf.ProtocolStringList
          getMemberList() {
        return member_.getUnmodifiableView();
      }
      /**
       * <code>repeated string member = 1;</code>
       */
      public int getMemberCount() {
        return member_.size();
      }
      /**
       * <code>repeated string member = 1;</code>
       */
      public java.lang.String getMember(int index) {
        return member_.get(index);
      }
      /**
       * <code>repeated string member = 1;</code>
       */
      public com.google.protobuf.ByteString
          getMemberBytes(int index) {
        return member_.getByteString(index);
      }
      /**
       * <code>repeated string member = 1;</code>
       */
      public Builder setMember(
          int index, java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  ensureMemberIsMutable();
        member_.set(index, value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string member = 1;</code>
       */
      public Builder addMember(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  ensureMemberIsMutable();
        member_.add(value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string member = 1;</code>
       */
      public Builder addAllMember(
          java.lang.Iterable<java.lang.String> values) {
        ensureMemberIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, member_);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string member = 1;</code>
       */
      public Builder clearMember() {
        member_ = com.google.protobuf.LazyStringArrayList.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000001);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string member = 1;</code>
       */
      public Builder addMemberBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        ensureMemberIsMutable();
        member_.add(value);
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


      // @@protoc_insertion_point(builder_scope:mars.stn.PullGroupMemberResult)
    }

    // @@protoc_insertion_point(class_scope:mars.stn.PullGroupMemberResult)
    private static final win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult();
    }

    public static win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<PullGroupMemberResult>
        PARSER = new com.google.protobuf.AbstractParser<PullGroupMemberResult>() {
      public PullGroupMemberResult parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new PullGroupMemberResult(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<PullGroupMemberResult> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<PullGroupMemberResult> getParserForType() {
      return PARSER;
    }

    public win.liyufan.im.proto.PullGroupMemberResultOuterClass.PullGroupMemberResult getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_mars_stn_PullGroupMemberResult_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_mars_stn_PullGroupMemberResult_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\036pull_group_member_result.proto\022\010mars.s" +
      "tn\"\'\n\025PullGroupMemberResult\022\016\n\006member\030\001 " +
      "\003(\tB7\n\024win.liyufan.im.protoB\037PullGroupMe" +
      "mberResultOuterClassb\006proto3"
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
        }, assigner);
    internal_static_mars_stn_PullGroupMemberResult_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_mars_stn_PullGroupMemberResult_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_mars_stn_PullGroupMemberResult_descriptor,
        new java.lang.String[] { "Member", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
