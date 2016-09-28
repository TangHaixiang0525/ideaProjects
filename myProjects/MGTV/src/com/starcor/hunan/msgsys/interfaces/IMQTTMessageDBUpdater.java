package com.starcor.hunan.msgsys.interfaces;

public abstract interface IMQTTMessageDBUpdater
{
  public abstract void runTask();

  public static enum TopicTableUpdateActionType
  {
    static
    {
      LOAD_ALL_PRIVATE_TOPIC_MESSAGE = new TopicTableUpdateActionType("LOAD_ALL_PRIVATE_TOPIC_MESSAGE", 1);
      LOAD_ALL_RESERVE_TOPIC_MESSAGE = new TopicTableUpdateActionType("LOAD_ALL_RESERVE_TOPIC_MESSAGE", 2);
      LOAD_ALL_PRIVATE_AND_PUBLIC_TOPIC_MESSAGE = new TopicTableUpdateActionType("LOAD_ALL_PRIVATE_AND_PUBLIC_TOPIC_MESSAGE", 3);
      LOAD_ALL_MESSAGES = new TopicTableUpdateActionType("LOAD_ALL_MESSAGES", 4);
      LOAD_ALL_ADMIN_TOPIC_MESSAGE = new TopicTableUpdateActionType("LOAD_ALL_ADMIN_TOPIC_MESSAGE", 5);
      DELETE_PUBLIC_TOPIC_MESSAGE = new TopicTableUpdateActionType("DELETE_PUBLIC_TOPIC_MESSAGE", 6);
      DELETE_ALL_PUBLIC_TOPIC_MESSAGE = new TopicTableUpdateActionType("DELETE_ALL_PUBLIC_TOPIC_MESSAGE", 7);
      DELETE_PRIVATE_TOPIC_MESSAGE = new TopicTableUpdateActionType("DELETE_PRIVATE_TOPIC_MESSAGE", 8);
      DELETE_ALL_PRIVATE_TOPIC_MESSAGE = new TopicTableUpdateActionType("DELETE_ALL_PRIVATE_TOPIC_MESSAGE", 9);
      DELETE_RESERVE_TOPIC_MESSAGE = new TopicTableUpdateActionType("DELETE_RESERVE_TOPIC_MESSAGE", 10);
      DELETE_ALL_RESERVE_TOPIC_MESSAGE = new TopicTableUpdateActionType("DELETE_ALL_RESERVE_TOPIC_MESSAGE", 11);
      DELETE_ALL_TOPIC_MESSAGE = new TopicTableUpdateActionType("DELETE_ALL_TOPIC_MESSAGE", 12);
      DELETE_ALL_SYSTEM_MESSAGE = new TopicTableUpdateActionType("DELETE_ALL_SYSTEM_MESSAGE", 13);
      DELETE_ALL_MY_MESSAGE = new TopicTableUpdateActionType("DELETE_ALL_MY_MESSAGE", 14);
      DELETE_ADMIN_TOPIC_MESSAGE = new TopicTableUpdateActionType("DELETE_ADMIN_TOPIC_MESSAGE", 15);
      DELETE_ALL_ADMIN_TOPIC_MESSAGE = new TopicTableUpdateActionType("DELETE_ALL_ADMIN_TOPIC_MESSAGE", 16);
      ADD_PUBLIC_TOPIC_MESSAGE = new TopicTableUpdateActionType("ADD_PUBLIC_TOPIC_MESSAGE", 17);
      ADD_PRIVATE_TOPIC_MESSAGE = new TopicTableUpdateActionType("ADD_PRIVATE_TOPIC_MESSAGE", 18);
      ADD_RESERVE_TOPIC_MESSAGE = new TopicTableUpdateActionType("ADD_RESERVE_TOPIC_MESSAGE", 19);
      ADD_ADMIN_TOPIC_MESSAGE = new TopicTableUpdateActionType("ADD_ADMIN_TOPIC_MESSAGE", 20);
      SET_PUBLIC_TOPIC_MESSAGE_UNREAD = new TopicTableUpdateActionType("SET_PUBLIC_TOPIC_MESSAGE_UNREAD", 21);
      SET_ALL_PUBLIC_TOPIC_MESSAGE_UNREAD = new TopicTableUpdateActionType("SET_ALL_PUBLIC_TOPIC_MESSAGE_UNREAD", 22);
      SET_PUBLIC_TOPIC_MESSAGE_READ = new TopicTableUpdateActionType("SET_PUBLIC_TOPIC_MESSAGE_READ", 23);
      SET_ALL_PUBLIC_TOPIC_MESSAGE_READ = new TopicTableUpdateActionType("SET_ALL_PUBLIC_TOPIC_MESSAGE_READ", 24);
      SET_PRIVATE_TOPIC_MESSAGE_UNREAD = new TopicTableUpdateActionType("SET_PRIVATE_TOPIC_MESSAGE_UNREAD", 25);
      SET_ALL_PRIVATE_TOPIC_MESSAGE_UNREAD = new TopicTableUpdateActionType("SET_ALL_PRIVATE_TOPIC_MESSAGE_UNREAD", 26);
      SET_RESERVE_TOPIC_MESSAGE_UNREAD = new TopicTableUpdateActionType("SET_RESERVE_TOPIC_MESSAGE_UNREAD", 27);
      SET_ALL_RESERVE_TOPIC_MESSAGE_UNREAD = new TopicTableUpdateActionType("SET_ALL_RESERVE_TOPIC_MESSAGE_UNREAD", 28);
      SET_PRIVATE_TOPIC_MESSAGE_READ = new TopicTableUpdateActionType("SET_PRIVATE_TOPIC_MESSAGE_READ", 29);
      SET_ALL_PRIVATE_TOPIC_MESSAGE_READ = new TopicTableUpdateActionType("SET_ALL_PRIVATE_TOPIC_MESSAGE_READ", 30);
      SET_RESERVE_TOPIC_MESSAGE_READ = new TopicTableUpdateActionType("SET_RESERVE_TOPIC_MESSAGE_READ", 31);
      SET_ALL_RESERVE_TOPIC_MESSAGE_READ = new TopicTableUpdateActionType("SET_ALL_RESERVE_TOPIC_MESSAGE_READ", 32);
      SET_ADMIN_TOPIC_MESSAGE_UNREAD = new TopicTableUpdateActionType("SET_ADMIN_TOPIC_MESSAGE_UNREAD", 33);
      SET_ALL_ADMIN_TOPIC_MESSAGE_UNREAD = new TopicTableUpdateActionType("SET_ALL_ADMIN_TOPIC_MESSAGE_UNREAD", 34);
      SET_ADMIN_TOPIC_MESSAGE_READ = new TopicTableUpdateActionType("SET_ADMIN_TOPIC_MESSAGE_READ", 35);
      SET_ALL_ADMIN_TOPIC_MESSAGE_READ = new TopicTableUpdateActionType("SET_ALL_ADMIN_TOPIC_MESSAGE_READ", 36);
      SET_ALL_SYSTEM_MESSAGE_READ = new TopicTableUpdateActionType("SET_ALL_SYSTEM_MESSAGE_READ", 37);
      SET_ALL_SYSTEM_MESSAGE_UNREAD = new TopicTableUpdateActionType("SET_ALL_SYSTEM_MESSAGE_UNREAD", 38);
      SET_ALL_MY_MESSAGE_READ = new TopicTableUpdateActionType("SET_ALL_MY_MESSAGE_READ", 39);
      SET_ALL_MY_MESSAGE_UNREAD = new TopicTableUpdateActionType("SET_ALL_MY_MESSAGE_UNREAD", 40);
      READ_ALL_UNREAD_MESSAGE_TOTAL_NUMBER = new TopicTableUpdateActionType("READ_ALL_UNREAD_MESSAGE_TOTAL_NUMBER", 41);
      ORDER_CHASE_DRAMA = new TopicTableUpdateActionType("ORDER_CHASE_DRAMA", 42);
      ORDER_CHASE_DRAMA_CANCEL = new TopicTableUpdateActionType("ORDER_CHASE_DRAMA_CANCEL", 43);
      ORDER_WISH_ORDER = new TopicTableUpdateActionType("ORDER_WISH_ORDER", 44);
      ORDER_WISH_ORDER_CANCEL = new TopicTableUpdateActionType("ORDER_WISH_ORDER_CANCEL", 45);
      ORDER_BACK_PLAY = new TopicTableUpdateActionType("ORDER_BACK_PLAY", 46);
      ORDER_BACK_PLAY_CANCEL = new TopicTableUpdateActionType("ORDER_BACK_PLAY_CANCEL", 47);
      ORDER_LIVE_SHOW = new TopicTableUpdateActionType("ORDER_LIVE_SHOW", 48);
      ORDER_LIVE_SHOW_CANCEL = new TopicTableUpdateActionType("ORDER_LIVE_SHOW_CANCEL", 49);
      RESERVE_SPECIAL_TOPIC = new TopicTableUpdateActionType("RESERVE_SPECIAL_TOPIC", 50);
      RESERVE_TURN_PLAY = new TopicTableUpdateActionType("RESERVE_TURN_PLAY", 51);
      TopicTableUpdateActionType[] arrayOfTopicTableUpdateActionType = new TopicTableUpdateActionType[52];
      arrayOfTopicTableUpdateActionType[0] = LOAD_ALL_PUBLIC_TOPIC_MESSAGE;
      arrayOfTopicTableUpdateActionType[1] = LOAD_ALL_PRIVATE_TOPIC_MESSAGE;
      arrayOfTopicTableUpdateActionType[2] = LOAD_ALL_RESERVE_TOPIC_MESSAGE;
      arrayOfTopicTableUpdateActionType[3] = LOAD_ALL_PRIVATE_AND_PUBLIC_TOPIC_MESSAGE;
      arrayOfTopicTableUpdateActionType[4] = LOAD_ALL_MESSAGES;
      arrayOfTopicTableUpdateActionType[5] = LOAD_ALL_ADMIN_TOPIC_MESSAGE;
      arrayOfTopicTableUpdateActionType[6] = DELETE_PUBLIC_TOPIC_MESSAGE;
      arrayOfTopicTableUpdateActionType[7] = DELETE_ALL_PUBLIC_TOPIC_MESSAGE;
      arrayOfTopicTableUpdateActionType[8] = DELETE_PRIVATE_TOPIC_MESSAGE;
      arrayOfTopicTableUpdateActionType[9] = DELETE_ALL_PRIVATE_TOPIC_MESSAGE;
      arrayOfTopicTableUpdateActionType[10] = DELETE_RESERVE_TOPIC_MESSAGE;
      arrayOfTopicTableUpdateActionType[11] = DELETE_ALL_RESERVE_TOPIC_MESSAGE;
      arrayOfTopicTableUpdateActionType[12] = DELETE_ALL_TOPIC_MESSAGE;
      arrayOfTopicTableUpdateActionType[13] = DELETE_ALL_SYSTEM_MESSAGE;
      arrayOfTopicTableUpdateActionType[14] = DELETE_ALL_MY_MESSAGE;
      arrayOfTopicTableUpdateActionType[15] = DELETE_ADMIN_TOPIC_MESSAGE;
      arrayOfTopicTableUpdateActionType[16] = DELETE_ALL_ADMIN_TOPIC_MESSAGE;
      arrayOfTopicTableUpdateActionType[17] = ADD_PUBLIC_TOPIC_MESSAGE;
      arrayOfTopicTableUpdateActionType[18] = ADD_PRIVATE_TOPIC_MESSAGE;
      arrayOfTopicTableUpdateActionType[19] = ADD_RESERVE_TOPIC_MESSAGE;
      arrayOfTopicTableUpdateActionType[20] = ADD_ADMIN_TOPIC_MESSAGE;
      arrayOfTopicTableUpdateActionType[21] = SET_PUBLIC_TOPIC_MESSAGE_UNREAD;
      arrayOfTopicTableUpdateActionType[22] = SET_ALL_PUBLIC_TOPIC_MESSAGE_UNREAD;
      arrayOfTopicTableUpdateActionType[23] = SET_PUBLIC_TOPIC_MESSAGE_READ;
      arrayOfTopicTableUpdateActionType[24] = SET_ALL_PUBLIC_TOPIC_MESSAGE_READ;
      arrayOfTopicTableUpdateActionType[25] = SET_PRIVATE_TOPIC_MESSAGE_UNREAD;
      arrayOfTopicTableUpdateActionType[26] = SET_ALL_PRIVATE_TOPIC_MESSAGE_UNREAD;
      arrayOfTopicTableUpdateActionType[27] = SET_RESERVE_TOPIC_MESSAGE_UNREAD;
      arrayOfTopicTableUpdateActionType[28] = SET_ALL_RESERVE_TOPIC_MESSAGE_UNREAD;
      arrayOfTopicTableUpdateActionType[29] = SET_PRIVATE_TOPIC_MESSAGE_READ;
      arrayOfTopicTableUpdateActionType[30] = SET_ALL_PRIVATE_TOPIC_MESSAGE_READ;
      arrayOfTopicTableUpdateActionType[31] = SET_RESERVE_TOPIC_MESSAGE_READ;
      arrayOfTopicTableUpdateActionType[32] = SET_ALL_RESERVE_TOPIC_MESSAGE_READ;
      arrayOfTopicTableUpdateActionType[33] = SET_ADMIN_TOPIC_MESSAGE_UNREAD;
      arrayOfTopicTableUpdateActionType[34] = SET_ALL_ADMIN_TOPIC_MESSAGE_UNREAD;
      arrayOfTopicTableUpdateActionType[35] = SET_ADMIN_TOPIC_MESSAGE_READ;
      arrayOfTopicTableUpdateActionType[36] = SET_ALL_ADMIN_TOPIC_MESSAGE_READ;
      arrayOfTopicTableUpdateActionType[37] = SET_ALL_SYSTEM_MESSAGE_READ;
      arrayOfTopicTableUpdateActionType[38] = SET_ALL_SYSTEM_MESSAGE_UNREAD;
      arrayOfTopicTableUpdateActionType[39] = SET_ALL_MY_MESSAGE_READ;
      arrayOfTopicTableUpdateActionType[40] = SET_ALL_MY_MESSAGE_UNREAD;
      arrayOfTopicTableUpdateActionType[41] = READ_ALL_UNREAD_MESSAGE_TOTAL_NUMBER;
      arrayOfTopicTableUpdateActionType[42] = ORDER_CHASE_DRAMA;
      arrayOfTopicTableUpdateActionType[43] = ORDER_CHASE_DRAMA_CANCEL;
      arrayOfTopicTableUpdateActionType[44] = ORDER_WISH_ORDER;
      arrayOfTopicTableUpdateActionType[45] = ORDER_WISH_ORDER_CANCEL;
      arrayOfTopicTableUpdateActionType[46] = ORDER_BACK_PLAY;
      arrayOfTopicTableUpdateActionType[47] = ORDER_BACK_PLAY_CANCEL;
      arrayOfTopicTableUpdateActionType[48] = ORDER_LIVE_SHOW;
      arrayOfTopicTableUpdateActionType[49] = ORDER_LIVE_SHOW_CANCEL;
      arrayOfTopicTableUpdateActionType[50] = RESERVE_SPECIAL_TOPIC;
      arrayOfTopicTableUpdateActionType[51] = RESERVE_TURN_PLAY;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.interfaces.IMQTTMessageDBUpdater
 * JD-Core Version:    0.6.2
 */