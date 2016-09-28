package com.starcor.hunan.db;

import android.net.Uri;
import java.util.HashMap;
import java.util.Map;

public class ConfigColums extends DataBaseColumns
{
  public static final String CONFIG_KEY = "key";
  public static final String CONFIG_VALUE = "value";
  public static final Uri CONTENT_URI = Uri.parse("content://com.starcor.mango.hunan.database/config");
  public static final Map<String, String> TABLE_CREATER_MAP = new HashMap();
  public static final String TABLE_NAME = "config";

  public Uri getContentUri()
  {
    return CONTENT_URI;
  }

  protected Map<String, String> getTableMap()
  {
    TABLE_CREATER_MAP.put("_id", "integer primary key autoincrement  not null");
    TABLE_CREATER_MAP.put("key", "String");
    TABLE_CREATER_MAP.put("value", "String");
    return TABLE_CREATER_MAP;
  }

  public String getTableName()
  {
    return "config";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.db.ConfigColums
 * JD-Core Version:    0.6.2
 */