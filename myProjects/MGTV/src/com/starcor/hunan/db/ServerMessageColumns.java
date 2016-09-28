package com.starcor.hunan.db;

import android.net.Uri;
import java.util.HashMap;
import java.util.Map;

public class ServerMessageColumns extends DataBaseColumns
{
  public static final String CONTENT = "content";
  public static final Uri CONTENT_URI = Uri.parse("content://com.starcor.mango.hunan.database/server_message");
  public static final String DATE = "date";
  public static final String ID = "id";
  public static final String PARAMS = "params";
  public static final String READFLAG = "readFlag";
  public static final String TABLE_NAME = "server_message";
  public static final String TITLE = "title";
  public static final String TYPE = "type";
  public static final Map<String, String> mColumnMap = new HashMap();

  public Uri getContentUri()
  {
    return CONTENT_URI;
  }

  protected Map<String, String> getTableMap()
  {
    mColumnMap.put("_id", "integer primary key autoincrement");
    mColumnMap.put("title", "text");
    mColumnMap.put("type", "text");
    mColumnMap.put("id", "text");
    mColumnMap.put("readFlag", "int");
    mColumnMap.put("content", "text");
    mColumnMap.put("date", "text");
    mColumnMap.put("params", "text");
    return mColumnMap;
  }

  public String getTableName()
  {
    return "server_message";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.db.ServerMessageColumns
 * JD-Core Version:    0.6.2
 */