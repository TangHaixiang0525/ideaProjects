package com.starcor.hunan.db;

import android.net.Uri;
import java.util.HashMap;
import java.util.Map;

public class ReportMessageColumns extends DataBaseColumns
{
  public static final String CONTENT = "content";
  public static final Uri CONTENT_URI = Uri.parse("content://com.starcor.mango.hunan.database/report_message_table");
  public static final String DESC = "desc";
  public static final String IS_LIVE_REPORT = "isLiveReport";
  public static final Map<String, String> KEY_MAP = new HashMap();
  public static final String MSG_ID = "msgId";
  public static final String TABLE_NAME = "report_message_table";

  public Uri getContentUri()
  {
    return CONTENT_URI;
  }

  protected Map<String, String> getTableMap()
  {
    KEY_MAP.put("_id", "Integer primary key autoincrement not null");
    KEY_MAP.put("msgId", "integer");
    KEY_MAP.put("desc", "text");
    KEY_MAP.put("content", "text");
    KEY_MAP.put("isLiveReport", "integer");
    return KEY_MAP;
  }

  public String getTableName()
  {
    return "report_message_table";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.db.ReportMessageColumns
 * JD-Core Version:    0.6.2
 */