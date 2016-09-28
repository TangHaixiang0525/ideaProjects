package com.starcor.hunan.db;

import android.net.Uri;
import java.util.HashMap;
import java.util.Map;

public class PrivateTopicMessageColumns extends DataBaseColumns
{
  private static final Uri CONTENT_URI = Uri.parse("content://com.starcor.mango.hunan.database/private_topic_msg_table");
  private static final Map<String, String> KEY_MAP = new HashMap();
  private static final String TABLE_NAME = "private_topic_msg_table";

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
    KEY_MAP.put("topic", "text");
    KEY_MAP.put("unread", "int");
    KEY_MAP.put("publish_time", "text");
    KEY_MAP.put("ext", "text");
    KEY_MAP.put("title", "text");
    KEY_MAP.put("message_type", "text");
    KEY_MAP.put("body", "text");
    KEY_MAP.put("msg_display_style", "int");
    KEY_MAP.put("dialogway", "int");
    KEY_MAP.put("dialogway_args", "text");
    KEY_MAP.put("buttons", "text");
    return KEY_MAP;
  }

  public String getTableName()
  {
    return "private_topic_msg_table";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.db.PrivateTopicMessageColumns
 * JD-Core Version:    0.6.2
 */