package com.starcor.hunan.db;

import android.net.Uri;
import java.util.HashMap;
import java.util.Map;

public class ReservationColums extends DataBaseColumns
{
  public static final String BEGIN_TIME = "nns_beginTime";
  public static final String CHANNEL = "film_channel";
  public static final Uri CONTENT_URI = Uri.parse("content://com.starcor.mango.hunan.database/reservation");
  public static final String DAY = "nns_day";
  public static final String FILM_TYPE = "film_type";
  public static final String INDEX = "nns_index";
  public static final String NAME = "name";
  public static final String RESERVATION_TIME = "reservation_time";
  public static final Map<String, String> TABLE_CREATER_MAP = new HashMap();
  public static final String TABLE_NAME = "reservation";
  public static final String TIME_LEN = "nns_timeLen";
  public static final String VIDEO_ID = "video_id";

  public Uri getContentUri()
  {
    return CONTENT_URI;
  }

  protected Map<String, String> getTableMap()
  {
    TABLE_CREATER_MAP.put("_id", "integer primary key autoincrement  not null");
    TABLE_CREATER_MAP.put("nns_beginTime", "text");
    TABLE_CREATER_MAP.put("nns_day", "text");
    TABLE_CREATER_MAP.put("nns_index", "integer");
    TABLE_CREATER_MAP.put("nns_timeLen", "text");
    TABLE_CREATER_MAP.put("video_id", "text");
    TABLE_CREATER_MAP.put("film_type", "integer");
    TABLE_CREATER_MAP.put("reservation_time", "integer");
    TABLE_CREATER_MAP.put("name", "String");
    TABLE_CREATER_MAP.put("film_channel", "String");
    return TABLE_CREATER_MAP;
  }

  public String getTableName()
  {
    return "reservation";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.db.ReservationColums
 * JD-Core Version:    0.6.2
 */