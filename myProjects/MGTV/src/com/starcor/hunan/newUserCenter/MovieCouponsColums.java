package com.starcor.hunan.newUserCenter;

import android.net.Uri;
import com.starcor.hunan.db.DataBaseColumns;
import java.util.HashMap;
import java.util.Map;

public class MovieCouponsColums extends DataBaseColumns
{
  public static final Uri CONTENT_URI = Uri.parse("content://com.starcor.mango.hunan.database/user_movie_coupons");
  public static final String MOVIE_COUPON_RED_DOT_STATE = "movie_couponRed_dot_state";
  public static final String PRESENTER_MOVIE_COUPON = "presenter_movie_coupon";
  public static final String TABLE_NAME = "user_movie_coupons";
  public static final String USER_ID = "user_id";
  public static final Map<String, String> mColumnMap = new HashMap();

  public Uri getContentUri()
  {
    return CONTENT_URI;
  }

  protected Map<String, String> getTableMap()
  {
    mColumnMap.put("_id", "integer primary key autoincrement");
    mColumnMap.put("user_id", "text");
    mColumnMap.put("presenter_movie_coupon", "text");
    mColumnMap.put("movie_couponRed_dot_state", "text");
    return mColumnMap;
  }

  public String getTableName()
  {
    return "user_movie_coupons";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.newUserCenter.MovieCouponsColums
 * JD-Core Version:    0.6.2
 */