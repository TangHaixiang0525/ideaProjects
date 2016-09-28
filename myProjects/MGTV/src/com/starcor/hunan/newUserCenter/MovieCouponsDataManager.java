package com.starcor.hunan.newUserCenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.db.DBProvider;
import java.io.PrintStream;
import java.util.HashMap;

public class MovieCouponsDataManager
{
  private String TAG = "MovieCouponsDataManager";
  private Context mContext = null;

  public MovieCouponsDataManager(Context paramContext)
  {
    this.mContext = paramContext;
  }

  public void UpdateMovieCouponsInfo(String paramString, HashMap<String, String> paramHashMap)
  {
    DBProvider localDBProvider = new DBProvider(this.mContext);
    ContentValues localContentValues = new ContentValues();
    String[] arrayOfString = new String[1];
    arrayOfString[0] = ("" + paramString);
    localContentValues.put("presenter_movie_coupon", (String)paramHashMap.get("presenter_movie_coupon"));
    localContentValues.put("movie_couponRed_dot_state", (String)paramHashMap.get("movie_couponRed_dot_state"));
    try
    {
      localDBProvider.update(MovieCouponsColums.CONTENT_URI, localContentValues, "user_id=?", arrayOfString);
      return;
    }
    catch (Exception localException)
    {
      System.out.println("当保存观影卷红点信息时，更新观影卷信息失败" + localException);
    }
  }

  public void addMovieCouponsInfo(HashMap<String, String> paramHashMap)
  {
    Logger.i(this.TAG, "addMovieCouponsInfo");
    if (paramHashMap == null)
      return;
    DBProvider localDBProvider = new DBProvider(this.mContext);
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("user_id", (String)paramHashMap.get("user_id"));
    localContentValues.put("presenter_movie_coupon", (String)paramHashMap.get("presenter_movie_coupon"));
    localContentValues.put("movie_couponRed_dot_state", (String)paramHashMap.get("movie_couponRed_dot_state"));
    try
    {
      localDBProvider.insert(MovieCouponsColums.CONTENT_URI, localContentValues);
      return;
    }
    catch (Exception localException)
    {
      System.out.println("当保存观影卷红点信息时，添加观影卷信息失败" + localException);
    }
  }

  public int deleteMovieCouponsInfo(String paramString)
  {
    String[] arrayOfString = new String[1];
    arrayOfString[0] = ("" + paramString);
    DBProvider localDBProvider = new DBProvider(this.mContext);
    try
    {
      int i = localDBProvider.delete(MovieCouponsColums.CONTENT_URI, "user_id=?", arrayOfString);
      return i;
    }
    catch (Exception localException)
    {
      System.out.println("当保存观影卷红点信息时，删除观影卷信息失败" + localException);
    }
    return 0;
  }

  public HashMap<String, String> getMovieCouponsInfo(String paramString)
  {
    DBProvider localDBProvider = new DBProvider(this.mContext);
    HashMap localHashMap = new HashMap();
    String[] arrayOfString1 = { "user_id", "presenter_movie_coupon", "movie_couponRed_dot_state" };
    String[] arrayOfString2 = new String[1];
    arrayOfString2[0] = ("" + paramString);
    try
    {
      Cursor localCursor2 = localDBProvider.query(MovieCouponsColums.CONTENT_URI, arrayOfString1, "user_id=?", arrayOfString2, null);
      localCursor1 = localCursor2;
      if ((localCursor1 != null) && (localCursor1.moveToFirst()))
      {
        do
        {
          String str = localCursor1.getString(localCursor1.getColumnIndexOrThrow("user_id"));
          Logger.d("user_id" + str);
          localHashMap.put("user_id", str);
          localHashMap.put("presenter_movie_coupon", localCursor1.getString(localCursor1.getColumnIndexOrThrow("presenter_movie_coupon")));
          localHashMap.put("movie_couponRed_dot_state", localCursor1.getString(localCursor1.getColumnIndexOrThrow("movie_couponRed_dot_state")));
        }
        while (localCursor1.moveToNext());
        return localHashMap;
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        System.out.println("当保存观影卷红点信息时，查询观影卷信息失败" + localException);
        Cursor localCursor1 = null;
      }
      Logger.e("error !!!!");
    }
    return localHashMap;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.newUserCenter.MovieCouponsDataManager
 * JD-Core Version:    0.6.2
 */