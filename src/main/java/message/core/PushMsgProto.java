// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: core/PushMsg.proto

package message.core;

public final class PushMsgProto {
  private PushMsgProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  /**
   * Protobuf enum {@code message.core.PushType}
   *
   * <pre>
   * 推送数据类型
   * </pre>
   */
  public enum PushType
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <code>PUSH_PLAYER = 1;</code>
     *
     * <pre>
     *玩家数据  PlayerMsg
     * </pre>
     */
    PUSH_PLAYER(0, 1),
    /**
     * <code>PUSH_JOIN_GAME = 2;</code>
     *
     * <pre>
     *玩家加入游戏   JoinerInfoMsg
     * </pre>
     */
    PUSH_JOIN_GAME(1, 2),
    /**
     * <code>PUSH_QUIT_GAME = 3;</code>
     *
     * <pre>
     *玩家离开游戏    JoinerInfoMsg
     * </pre>
     */
    PUSH_QUIT_GAME(2, 3),
    /**
     * <code>PUSH_SEND_TXT_MSG = 4;</code>
     *
     * <pre>
     *文本信息推送   GameMsgPro
     * </pre>
     */
    PUSH_SEND_TXT_MSG(3, 4),
    /**
     * <code>PUSH_SEND_VOICE_MSG = 5;</code>
     *
     * <pre>
     *语音信息推送
     * </pre>
     */
    PUSH_SEND_VOICE_MSG(4, 5),
    /**
     * <code>PUSH_ROOM_MSG = 6;</code>
     *
     * <pre>
     *房间信息变更  RoomMsg
     * </pre>
     */
    PUSH_ROOM_MSG(5, 6),
    ;

    /**
     * <code>PUSH_PLAYER = 1;</code>
     *
     * <pre>
     *玩家数据  PlayerMsg
     * </pre>
     */
    public static final int PUSH_PLAYER_VALUE = 1;
    /**
     * <code>PUSH_JOIN_GAME = 2;</code>
     *
     * <pre>
     *玩家加入游戏   JoinerInfoMsg
     * </pre>
     */
    public static final int PUSH_JOIN_GAME_VALUE = 2;
    /**
     * <code>PUSH_QUIT_GAME = 3;</code>
     *
     * <pre>
     *玩家离开游戏    JoinerInfoMsg
     * </pre>
     */
    public static final int PUSH_QUIT_GAME_VALUE = 3;
    /**
     * <code>PUSH_SEND_TXT_MSG = 4;</code>
     *
     * <pre>
     *文本信息推送   GameMsgPro
     * </pre>
     */
    public static final int PUSH_SEND_TXT_MSG_VALUE = 4;
    /**
     * <code>PUSH_SEND_VOICE_MSG = 5;</code>
     *
     * <pre>
     *语音信息推送
     * </pre>
     */
    public static final int PUSH_SEND_VOICE_MSG_VALUE = 5;
    /**
     * <code>PUSH_ROOM_MSG = 6;</code>
     *
     * <pre>
     *房间信息变更  RoomMsg
     * </pre>
     */
    public static final int PUSH_ROOM_MSG_VALUE = 6;


    public final int getNumber() { return value; }

    public static PushType valueOf(int value) {
      switch (value) {
        case 1: return PUSH_PLAYER;
        case 2: return PUSH_JOIN_GAME;
        case 3: return PUSH_QUIT_GAME;
        case 4: return PUSH_SEND_TXT_MSG;
        case 5: return PUSH_SEND_VOICE_MSG;
        case 6: return PUSH_ROOM_MSG;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<PushType>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static com.google.protobuf.Internal.EnumLiteMap<PushType>
        internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<PushType>() {
            public PushType findValueByNumber(int number) {
              return PushType.valueOf(number);
            }
          };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      return getDescriptor().getValues().get(index);
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return message.core.PushMsgProto.getDescriptor().getEnumTypes().get(0);
    }

    private static final PushType[] VALUES = values();

    public static PushType valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      return VALUES[desc.getIndex()];
    }

    private final int index;
    private final int value;

    private PushType(int index, int value) {
      this.index = index;
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:message.core.PushType)
  }


  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\022core/PushMsg.proto\022\014message.core*\206\001\n\010P" +
      "ushType\022\017\n\013PUSH_PLAYER\020\001\022\022\n\016PUSH_JOIN_GA" +
      "ME\020\002\022\022\n\016PUSH_QUIT_GAME\020\003\022\025\n\021PUSH_SEND_TX" +
      "T_MSG\020\004\022\027\n\023PUSH_SEND_VOICE_MSG\020\005\022\021\n\rPUSH" +
      "_ROOM_MSG\020\006B\016B\014PushMsgProto"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
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
  }

  // @@protoc_insertion_point(outer_class_scope)
}
