package com.starcor.system.provider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.starcor.core.utils.Logger;

public class HWInfoReader
{
  private static final String TAG = HWInfoReader.class.getSimpleName();
  private static Context mContext;
  private static HWInfoReader reader;

  public static HWInfoReader getInstance(Context paramContext)
  {
    mContext = paramContext;
    if (reader == null)
      reader = new HWInfoReader();
    return reader;
  }

  public String getEpgServer()
  {
    String str = "";
    try
    {
      Uri localUri = Uri.parse("content://stbconfig/authentication");
      Cursor localCursor = mContext.getContentResolver().query(localUri, null, null, null, null);
      if (localCursor != null)
      {
        while (localCursor.moveToNext())
          if ("epg_server".equals(localCursor.getString(localCursor.getColumnIndex("name"))))
            str = localCursor.getString(localCursor.getColumnIndex("value"));
        localCursor.close();
      }
      Logger.i(TAG, "epgServer--->" + str);
      return str;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public String getToken()
  {
    String str = "";
    try
    {
      Uri localUri = Uri.parse("content://stbconfig/authentication");
      Cursor localCursor = mContext.getContentResolver().query(localUri, null, null, null, null);
      if (localCursor != null)
      {
        while (localCursor.moveToNext())
          if ("user_token".equals(localCursor.getString(localCursor.getColumnIndex("name"))))
            str = localCursor.getString(localCursor.getColumnIndex("value"));
        localCursor.close();
      }
      Logger.i(TAG, "Token--->" + str);
      return str;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public String getUserName()
  {
    String str = null;
    try
    {
      Uri localUri = Uri.parse("content://stbconfig/authentication");
      Cursor localCursor = mContext.getContentResolver().query(localUri, null, null, null, null);
      str = null;
      if (localCursor != null)
      {
        do
        {
          boolean bool = localCursor.moveToNext();
          str = null;
          if (!bool)
            break;
        }
        while (!"username".equals(localCursor.getString(localCursor.getColumnIndex("name"))));
        str = localCursor.getString(localCursor.getColumnIndex("value"));
        localCursor.close();
      }
      Logger.i(TAG, "UserName--->" + str);
      return str;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.system.provider.HWInfoReader
 * JD-Core Version:    0.6.2
 */