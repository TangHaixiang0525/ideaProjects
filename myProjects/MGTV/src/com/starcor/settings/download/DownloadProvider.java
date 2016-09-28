package com.starcor.settings.download;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import com.starcor.settings.utils.Debug;

public class DownloadProvider extends ContentProvider
{
  private static final String DATABASE_NAME = "download.db";
  private static final int DATABASE_VERSION = 5;
  private static final int DOWNLOADS = 1;
  private static final int DOWNLOADS_ID = 2;
  private static final String DOWNLOAD_LIST_TYPE = "vnd.android.cursor.dir/vnd.smedio.download.item";
  private static final String DOWNLOAD_TYPE = "vnd.android.cursor.item/vnd.smedio.download.item";
  private static final String TABLE_NAME = "download";
  private static final UriMatcher sURIMatcher = new UriMatcher(-1);
  private DatabaseHelper mOpenHelper;

  static
  {
    sURIMatcher.addURI("com.starcor.settings.download", "downloads", 1);
    sURIMatcher.addURI("com.starcor.settings.download", "downloads/#", 2);
  }

  public int delete(Uri paramUri, String paramString, String[] paramArrayOfString)
  {
    SQLiteDatabase localSQLiteDatabase = this.mOpenHelper.getWritableDatabase();
    int i = sURIMatcher.match(paramUri);
    int j;
    if (i == 1)
    {
      j = localSQLiteDatabase.delete("download", paramString, paramArrayOfString);
      if (j > 0)
        getContext().getContentResolver().notifyChange(Downloads.Item.CONTENT_URI, null);
      return j;
    }
    if (i == 2)
    {
      StringBuilder localStringBuilder = new StringBuilder("_id=").append(paramUri.getLastPathSegment());
      if (paramString != null);
      for (String str = " AND (" + paramString + ")"; ; str = "")
      {
        j = localSQLiteDatabase.delete("download", str, paramArrayOfString);
        break;
      }
    }
    throw new IllegalArgumentException("Unknown uri:" + paramUri);
  }

  public String getType(Uri paramUri)
  {
    switch (sURIMatcher.match(paramUri))
    {
    default:
      throw new IllegalArgumentException("Unknown Uri:" + paramUri);
    case 1:
      return "vnd.android.cursor.dir/vnd.smedio.download.item";
    case 2:
    }
    return "vnd.android.cursor.item/vnd.smedio.download.item";
  }

  public Uri insert(Uri paramUri, ContentValues paramContentValues)
  {
    SQLiteDatabase localSQLiteDatabase = this.mOpenHelper.getWritableDatabase();
    if (sURIMatcher.match(paramUri) == 1)
    {
      ContentValues localContentValues = new ContentValues(paramContentValues);
      if (!localContentValues.containsKey("current_bytes"))
        localContentValues.put("current_bytes", Integer.valueOf(0));
      if (!localContentValues.containsKey("total_bytes"))
        localContentValues.put("total_bytes", Integer.valueOf(-1));
      if (!localContentValues.containsKey("title"))
        localContentValues.put("title", "");
      if (!localContentValues.containsKey("description"))
        localContentValues.put("description", "");
      if (!localContentValues.containsKey("status"))
        localContentValues.put("status", Integer.valueOf(190));
      localContentValues.put("lastmod", Long.valueOf(System.currentTimeMillis()));
      Uri localUri = ContentUris.withAppendedId(paramUri, localSQLiteDatabase.insert("download", null, localContentValues));
      getContext().getContentResolver().notifyChange(paramUri, null);
      localContentValues.clear();
      return localUri;
    }
    throw new IllegalArgumentException("Unknown uri:" + paramUri);
  }

  public boolean onCreate()
  {
    this.mOpenHelper = new DatabaseHelper(getContext());
    return true;
  }

  public Cursor query(Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2)
  {
    SQLiteDatabase localSQLiteDatabase = this.mOpenHelper.getReadableDatabase();
    SQLiteQueryBuilder localSQLiteQueryBuilder = new SQLiteQueryBuilder();
    localSQLiteQueryBuilder.setTables("download");
    int i = sURIMatcher.match(paramUri);
    if (i != 1)
    {
      if (i == 2)
        localSQLiteQueryBuilder.appendWhere("_id=" + paramUri.getLastPathSegment());
    }
    else
    {
      Cursor localCursor = localSQLiteQueryBuilder.query(localSQLiteDatabase, paramArrayOfString1, paramString1, paramArrayOfString2, null, null, paramString2, null);
      if (localCursor != null)
        localCursor.setNotificationUri(getContext().getContentResolver(), paramUri);
      return localCursor;
    }
    throw new IllegalArgumentException("Unknown uri:" + paramUri);
  }

  public int update(Uri paramUri, ContentValues paramContentValues, String paramString, String[] paramArrayOfString)
  {
    SQLiteDatabase localSQLiteDatabase = this.mOpenHelper.getWritableDatabase();
    int i = sURIMatcher.match(paramUri);
    int j;
    if (i == 1)
    {
      j = localSQLiteDatabase.update("download", paramContentValues, paramString, paramArrayOfString);
      Debug.d("lx", "DownloadProvider.update()---result1=" + j);
      if (j > 0)
        getContext().getContentResolver().notifyChange(paramUri, null);
      return j;
    }
    if (i == 2)
    {
      StringBuilder localStringBuilder = new StringBuilder("_id=").append(paramUri.getLastPathSegment());
      if (paramString != null);
      for (String str = " AND (" + paramString + ")"; ; str = "")
      {
        j = localSQLiteDatabase.update("download", paramContentValues, str, paramArrayOfString);
        Debug.d("lx", "DownloadProvider.update()---result222=" + j);
        if (j <= 0)
          break;
        getContext().getContentResolver().notifyChange(paramUri, null);
        return j;
      }
    }
    throw new IllegalArgumentException("Unknown uri:" + paramUri);
  }

  private final class DatabaseHelper extends SQLiteOpenHelper
  {
    public DatabaseHelper(Context arg2)
    {
      super("download.db", null, 5);
    }

    private void createDownloadsTable(SQLiteDatabase paramSQLiteDatabase)
    {
      try
      {
        paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS download");
        paramSQLiteDatabase.execSQL("CREATE TABLE download(_id INTEGER PRIMARY KEY AUTOINCREMENT,account TEXT, uri TEXT, method INTEGER, hint TEXT, _data TEXT, mimetype TEXT, status INTEGER, numfailed INTEGER, lastmod BIGINT, downloaded_time BIGINT, total_bytes INTEGER, current_bytes INTEGER, etag TEXT, title TEXT, mediaprovider_uri TEXT, deleted BOOLEAN NOT NULL DEFAULT 0, description TEXT, scanned BOOLEAN);");
        return;
      }
      catch (SQLException localSQLException)
      {
        Debug.e("DownloadManager", "couldn't create table in downloads database");
        throw localSQLException;
      }
    }

    public void onCreate(SQLiteDatabase paramSQLiteDatabase)
    {
      createDownloadsTable(paramSQLiteDatabase);
    }

    public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
    {
      createDownloadsTable(paramSQLiteDatabase);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.settings.download.DownloadProvider
 * JD-Core Version:    0.6.2
 */