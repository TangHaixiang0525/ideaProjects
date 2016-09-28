package com.starcor.settings.download;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import com.starcor.settings.utils.Debug;
import java.io.File;

public class DownloadHelper
{
  public static final int TYPE_AUDIO = 1;
  public static final int TYPE_DOC = 3;
  public static final int TYPE_INVALID = -1;
  public static final int TYPE_PHOTO = 2;
  public static final int TYPE_VIDEO = 0;
  private static final String WHERE_FILTER_NO_DELETE = "deleted != '1'";
  private static final String WHERE_FILTER_NO_SCANNED = "scanned != '1'";
  private static final String WHERE_FILTER_SCANNED = "scanned = '1'";
  private static DownloadHelper sInstance;
  private Context mContext;

  private DownloadHelper(Context paramContext)
  {
    this.mContext = paramContext.getApplicationContext();
  }

  private String andAccount()
  {
    return " AND account='" + getAccount() + "'";
  }

  private String andMoreThan10Days()
  {
    long l = System.currentTimeMillis() - 864000000L;
    return " AND downloaded_time>'" + String.valueOf(l) + "'";
  }

  private String andNotDeleted()
  {
    return " AND deleted != '1'";
  }

  private String andScanStatus(boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder(" AND ");
    if (paramBoolean);
    for (String str = "scanned = '1'"; ; str = "scanned != '1'")
      return str;
  }

  private boolean downloadAlreadyExist(String paramString)
  {
    Cursor localCursor = this.mContext.getContentResolver().query(Downloads.Item.CONTENT_URI, null, "uri=?" + andNotDeleted() + andAccount(), new String[] { paramString }, null);
    if (localCursor != null)
    {
      long l = 0L;
      if (localCursor.moveToFirst())
        l = localCursor.getLong(localCursor.getColumnIndex("_id"));
      localCursor.close();
      if (l != 0L)
        return true;
    }
    return false;
  }

  private String getAccount()
  {
    return "";
  }

  private String getFileNameHint(String paramString1, String paramString2)
  {
    if (paramString2 != null)
    {
      int i = paramString2.lastIndexOf('/');
      if (i >= 0)
      {
        int j = paramString2.indexOf('.', i);
        if (j >= 0)
          paramString1 = paramString1 + paramString2.substring(j);
      }
    }
    return paramString1;
  }

  public static DownloadHelper getInstance(Context paramContext)
  {
    if (sInstance == null)
      sInstance = new DownloadHelper(paramContext);
    return sInstance;
  }

  @Deprecated
  private String getSizeString(long paramLong)
  {
    if (paramLong <= 0L)
      return "Unknown";
    int i = -1;
    float f = (float)paramLong;
    do
    {
      f /= 1024.0F;
      i++;
    }
    while ((f > 1024.0F) && (i < "KMG".length()));
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Float.valueOf(f);
    return String.format("%1$1.1f", arrayOfObject) + " " + "KMG".charAt(i) + "B";
  }

  private String getUpdateAccount()
  {
    return " account='" + getAccount() + "'";
  }

  public boolean addDownload(String paramString)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("uri", paramString);
    if (downloadAlreadyExist(paramString));
    while (Long.parseLong(this.mContext.getContentResolver().insert(Downloads.Item.CONTENT_URI, localContentValues).getLastPathSegment()) <= 0L)
      return false;
    return true;
  }

  public boolean addDownload(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    if (paramInt == -1);
    ContentValues localContentValues;
    do
    {
      return false;
      localContentValues = new ContentValues();
      localContentValues.put("uri", paramString2);
      localContentValues.put("title", paramString1);
      String str = getFileNameHint(paramString1, paramString2);
      if (str != null)
        localContentValues.put("hint", str);
      localContentValues.put("account", getAccount());
      localContentValues.put("downloaded_time", Long.valueOf(9223372036854775807L));
    }
    while (Long.parseLong(this.mContext.getContentResolver().insert(Downloads.Item.CONTENT_URI, localContentValues).getLastPathSegment()) <= 0L);
    return true;
  }

  public void cancelAllInprogress()
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("deleted", Integer.valueOf(1));
    String str = new StringBuilder("((status='200'").append(andScanStatus(false)).append(") OR ").append("status").append("<'200' OR ").append("status").append(">='300')").append(andNotDeleted()).toString() + andAccount();
    this.mContext.getContentResolver().update(Downloads.Item.CONTENT_URI, localContentValues, str, null);
  }

  public void clearAllCompleted()
  {
    String str = new StringBuilder("status>='200' AND status<'300'").append(andNotDeleted()).append(andScanStatus(true)).toString() + andAccount();
    this.mContext.getContentResolver().delete(Downloads.Item.CONTENT_URI, str, null);
  }

  public void deleteDownloadHistory()
  {
    Cursor localCursor = queryAllDownloads();
    long l = System.currentTimeMillis() - 864000000L;
    localCursor.moveToFirst();
    while (true)
    {
      if (localCursor.isAfterLast())
      {
        localCursor.close();
        return;
      }
      if (localCursor.getLong(localCursor.getColumnIndex("downloaded_time")) < l)
        deleteDownloadWithFile(localCursor.getInt(localCursor.getColumnIndex("_id")));
      localCursor.moveToNext();
    }
  }

  public void deleteDownloadWithFile(long paramLong)
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("deleted", Integer.valueOf(1));
    this.mContext.getContentResolver().update(ContentUris.withAppendedId(Downloads.Item.CONTENT_URI, paramLong), localContentValues, getUpdateAccount(), null);
    this.mContext.startService(new Intent(this.mContext.getApplicationContext(), DownloadService.class));
  }

  public void pauseAllRunningAndPending()
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("status", Integer.valueOf(193));
    String str = new StringBuilder("status<'200' AND status!='193'").append(andNotDeleted()).toString() + andAccount();
    this.mContext.getContentResolver().update(Downloads.Item.CONTENT_URI, localContentValues, str, null);
  }

  public void pauseDownload(long paramLong)
  {
    Cursor localCursor = this.mContext.getContentResolver().query(ContentUris.withAppendedId(Downloads.Item.CONTENT_URI, paramLong), new String[] { "status" }, "deleted != '1'" + andAccount(), null, null);
    if (localCursor != null)
    {
      try
      {
        if (localCursor.moveToFirst())
        {
          int i = localCursor.getInt(0);
          boolean bool = Downloads.Item.isStatusCompleted(i);
          if ((bool) || (i == 193))
            return;
        }
      }
      finally
      {
        localCursor.close();
      }
      localCursor.close();
    }
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("status", Integer.valueOf(193));
    this.mContext.getContentResolver().update(ContentUris.withAppendedId(Downloads.Item.CONTENT_URI, paramLong), localContentValues, getUpdateAccount(), null);
    this.mContext.startService(new Intent(this.mContext.getApplicationContext(), DownloadService.class));
  }

  public Cursor queryAllDownloads()
  {
    String str = new StringBuilder(" ((status='200'  OR  status<'200' OR status>='300') OR (status>='200' AND status<'300')) ").append(andNotDeleted()).toString() + andAccount();
    Debug.d("lucien", "where -> " + str);
    return this.mContext.getContentResolver().query(Downloads.Item.CONTENT_URI, null, str, null, null);
  }

  public Cursor queryCompleteDownloads()
  {
    return queryCompleteDownloads(null, null);
  }

  public Cursor queryCompleteDownloads(String paramString)
  {
    boolean bool = TextUtils.isEmpty(paramString);
    String str;
    String[] arrayOfString;
    if (bool)
    {
      str = "";
      if (!bool)
        break label34;
      arrayOfString = null;
    }
    while (true)
    {
      return queryCompleteDownloads(str, arrayOfString);
      str = " AND title LIKE ?";
      break;
      label34: arrayOfString = new String[1];
      arrayOfString[0] = ("%" + paramString + "%");
    }
  }

  public Cursor queryCompleteDownloads(String paramString, String[] paramArrayOfString)
  {
    String str1 = "status>='200' AND status<'300'" + andNotDeleted() + andScanStatus(true);
    if (!TextUtils.isEmpty(paramString))
      str1 = str1 + " AND (" + paramString + ")";
    String str2 = str1 + andAccount();
    return this.mContext.getContentResolver().query(Downloads.Item.CONTENT_URI, null, str2, null, "lastmod DESC");
  }

  public Cursor queryInProgressDownloads()
  {
    return queryInProgressDownloads(null, null);
  }

  public Cursor queryInProgressDownloads(String paramString)
  {
    boolean bool = TextUtils.isEmpty(paramString);
    String str;
    String[] arrayOfString;
    if (bool)
    {
      str = "";
      if (!bool)
        break label34;
      arrayOfString = null;
    }
    while (true)
    {
      return queryInProgressDownloads(str, arrayOfString);
      str = " AND title LIKE ?";
      break;
      label34: arrayOfString = new String[1];
      arrayOfString[0] = ("%" + paramString + "%");
    }
  }

  public Cursor queryInProgressDownloads(String paramString, String[] paramArrayOfString)
  {
    String str = new StringBuilder("(( status='200'").append(andScanStatus(false)).append(") OR status<'200' OR status>='300')").append(andNotDeleted()).toString() + andAccount();
    if (!TextUtils.isEmpty(paramString))
      str = str + " AND (" + paramString + ")";
    return this.mContext.getContentResolver().query(Downloads.Item.CONTENT_URI, null, str, paramArrayOfString, null);
  }

  public Cursor queryRunningAndPendingDownloads()
  {
    String str = new StringBuilder("status<'200' AND status!='193'").append(andNotDeleted()).toString() + andAccount();
    return this.mContext.getContentResolver().query(Downloads.Item.CONTENT_URI, null, str, null, null);
  }

  public Cursor queryRunningAndPendingDownloads(String paramString, String[] paramArrayOfString)
  {
    String str = new StringBuilder("status<'200' AND status!='193'").append(andNotDeleted()).toString() + andAccount();
    if (!TextUtils.isEmpty(paramString))
      str = str + " AND (" + paramString + ")";
    return this.mContext.getContentResolver().query(Downloads.Item.CONTENT_URI, null, str, paramArrayOfString, null);
  }

  @Deprecated
  public Cursor queryRunningDownloads()
  {
    String str = new StringBuilder("status='192'").append(andNotDeleted()).toString() + andAccount();
    return this.mContext.getContentResolver().query(Downloads.Item.CONTENT_URI, null, str, null, null);
  }

  public void restartAllFailed()
  {
    String str = new StringBuilder("status>='300'").append(andNotDeleted()).toString() + andAccount();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("current_bytes", Integer.valueOf(0));
    localContentValues.put("total_bytes", Integer.valueOf(-1));
    localContentValues.putNull("_data");
    localContentValues.put("status", Integer.valueOf(190));
    localContentValues.put("numfailed", Integer.valueOf(0));
    localContentValues.put("scanned", Integer.valueOf(0));
    localContentValues.putNull("mediaprovider_uri");
    this.mContext.getContentResolver().update(Downloads.Item.CONTENT_URI, localContentValues, str, null);
    this.mContext.startService(new Intent(this.mContext.getApplicationContext(), DownloadService.class));
  }

  public void restartDownload(long paramLong)
  {
    Cursor localCursor = this.mContext.getContentResolver().query(ContentUris.withAppendedId(Downloads.Item.CONTENT_URI, paramLong), new String[] { "status", "_data", "mimetype" }, "deleted != '1'" + andAccount(), null, null);
    if (localCursor != null);
    try
    {
      if (localCursor.moveToFirst())
      {
        boolean bool = Downloads.Item.isStatusCompleted(localCursor.getInt(0));
        if (!bool)
          return;
      }
      try
      {
        new File(localCursor.getString(1)).delete();
        localCursor.close();
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("current_bytes", Integer.valueOf(0));
        localContentValues.put("total_bytes", Integer.valueOf(-1));
        localContentValues.putNull("_data");
        localContentValues.put("status", Integer.valueOf(190));
        localContentValues.put("numfailed", Integer.valueOf(0));
        localContentValues.put("scanned", Integer.valueOf(0));
        localContentValues.putNull("mediaprovider_uri");
        this.mContext.getContentResolver().update(ContentUris.withAppendedId(Downloads.Item.CONTENT_URI, paramLong), localContentValues, getUpdateAccount(), null);
        this.mContext.startService(new Intent(this.mContext.getApplicationContext(), DownloadService.class));
        return;
      }
      catch (Exception localException)
      {
        while (true)
          Debug.w("DownloadManager", "file: '" + localCursor.getString(1) + "' couldn't be deleted", localException);
      }
    }
    finally
    {
      localCursor.close();
    }
  }

  public void resumeAllPaused()
  {
    String str = new StringBuilder("status='193'").append(andNotDeleted()).toString() + andAccount();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("status", Integer.valueOf(190));
    this.mContext.getContentResolver().update(Downloads.Item.CONTENT_URI, localContentValues, str, null);
    this.mContext.startService(new Intent(this.mContext.getApplicationContext(), DownloadService.class));
  }

  public void resumeDownload(long paramLong)
  {
    Cursor localCursor = this.mContext.getContentResolver().query(ContentUris.withAppendedId(Downloads.Item.CONTENT_URI, paramLong), new String[] { "status" }, "deleted != '1'" + andAccount(), null, null);
    if (localCursor != null)
    {
      try
      {
        if (localCursor.moveToFirst())
        {
          int i = localCursor.getInt(0);
          boolean bool = Downloads.Item.isStatusPause(i);
          if ((!bool) && (i != 500))
            return;
        }
      }
      finally
      {
        localCursor.close();
      }
      localCursor.close();
    }
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("status", Integer.valueOf(190));
    this.mContext.getContentResolver().update(ContentUris.withAppendedId(Downloads.Item.CONTENT_URI, paramLong), localContentValues, getUpdateAccount(), null);
    this.mContext.startService(new Intent(this.mContext.getApplicationContext(), DownloadService.class));
  }

  public void startDownload(long paramLong, boolean paramBoolean)
  {
    Cursor localCursor1 = this.mContext.getContentResolver().query(ContentUris.withAppendedId(Downloads.Item.CONTENT_URI, paramLong), new String[] { "status" }, "deleted != '1'" + andAccount(), null, null);
    if (localCursor1 != null)
    {
      try
      {
        if (localCursor1.moveToFirst())
        {
          int j = localCursor1.getInt(0);
          if (j == 192)
            return;
        }
      }
      finally
      {
        localCursor1.close();
      }
      localCursor1.close();
    }
    Cursor localCursor2;
    if (paramBoolean)
    {
      localCursor2 = this.mContext.getContentResolver().query(Downloads.Item.CONTENT_URI, new String[] { "status", "_id" }, "_id!=" + paramLong + " AND " + "status" + "<" + 200 + " AND " + "status" + "!=" + 193 + andNotDeleted() + andAccount(), null, null);
      if (localCursor2 == null);
    }
    try
    {
      StringBuilder localStringBuilder = new StringBuilder("_id IN (");
      int i = 0;
      while (true)
      {
        if (!localCursor2.moveToNext())
        {
          if (localStringBuilder.charAt(-1 + localStringBuilder.length()) != '(')
          {
            localStringBuilder.append(')');
            ContentValues localContentValues2 = new ContentValues();
            localContentValues2.put("status", Integer.valueOf(193));
            this.mContext.getContentResolver().update(Downloads.Item.CONTENT_URI, localContentValues2, localStringBuilder.toString() + andAccount(), null);
          }
          localCursor2.close();
          ContentValues localContentValues1 = new ContentValues();
          localContentValues1.put("numfailed", Integer.valueOf(0));
          localContentValues1.putNull("mediaprovider_uri");
          localContentValues1.put("status", Integer.valueOf(190));
          this.mContext.getContentResolver().update(ContentUris.withAppendedId(Downloads.Item.CONTENT_URI, paramLong), localContentValues1, andAccount(), null);
          this.mContext.startService(new Intent(this.mContext.getApplicationContext(), DownloadService.class));
          return;
        }
        if (i != 0)
          localStringBuilder.append(',');
        i = 1;
        localStringBuilder.append(localCursor2.getInt(1));
      }
    }
    finally
    {
      localCursor2.close();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.settings.download.DownloadHelper
 * JD-Core Version:    0.6.2
 */