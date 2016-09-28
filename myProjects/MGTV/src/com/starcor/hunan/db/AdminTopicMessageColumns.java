package com.starcor.hunan.db;

import android.net.Uri;
import java.util.HashMap;
import java.util.Map;

public class AdminTopicMessageColumns extends DataBaseColumns
{
  private static final Uri CONTENT_URI = Uri.parse("content://com.starcor.mango.hunan.database/admin_topic_msg_table");
  private static final Map<String, String> KEY_MAP = new HashMap();
  private static final String TABLE_NAME = "admin_topic_msg_table";

  public static Uri getUri()
  {
    return CONTENT_URI;
  }

  public Uri getContentUri()
  {
    return CONTENT_URI;
  }

  protected Map<String, String> getTableMap()
  {
    KEY_MAP.put("_id", "Integer primary key autoincrement not null");
    KEY_MAP.put("message_id", "text");
    KEY_MAP.put("mpns_admin_action", "text");
    KEY_MAP.put("message", "int");
    KEY_MAP.put("page", "int");
    KEY_MAP.put("detail", "int");
    KEY_MAP.put("video", "int");
    KEY_MAP.put("message_ids", "text");
    KEY_MAP.put("unread", "int");
    KEY_MAP.put("already_operated", "int");
    KEY_MAP.put("topic", "text");
    KEY_MAP.put("need_to_reconnect", "text");
    KEY_MAP.put("next_connect_time", "int");
    return KEY_MAP;
  }

  public String getTableName()
  {
    return "admin_topic_msg_table";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.db.AdminTopicMessageColumns
 * JD-Core Version:    0.6.2
 */