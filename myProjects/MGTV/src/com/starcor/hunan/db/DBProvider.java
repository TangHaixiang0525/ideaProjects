package com.starcor.hunan.db;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;
import com.starcor.core.logic.GlobalEnv;

public class DBProvider extends ContentProvider
{
  private static final UriMatcher sUriMatcher = new UriMatcher(-1);
  private Context mContext = null;
  private SQLiteDatabase mDB = null;
  private DBHelper mDBHelper = null;

  static
  {
    for (int i = 0; i < DataBaseAdapter.mDataBaseColumns.length; i++)
      sUriMatcher.addURI("com.starcor.mango.hunan.database", DataBaseAdapter.mDataBaseColumns[i].getTableName(), i);
  }

  public DBProvider()
  {
    this.mContext = getContext();
  }

  public DBProvider(Context paramContext)
  {
    this.mDBHelper = new DBHelper(paramContext);
    this.mContext = paramContext;
  }

  private void getDB()
  {
    if (this.mDB == null)
      this.mDB = this.mDBHelper.getWritableDatabase();
  }

  public void closeDB()
  {
    if (this.mDB != null)
    {
      this.mDB.close();
      this.mDB = null;
    }
  }

  public int delete(Uri paramUri, String paramString, String[] paramArrayOfString)
  {
    getDB();
    int i = sUriMatcher.match(paramUri);
    int j = 0;
    if (i >= 0)
    {
      int k = DataBaseAdapter.mDataBaseColumns.length;
      j = 0;
      if (i < k)
        j = this.mDB.delete(DataBaseAdapter.mDataBaseColumns[i].getTableName(), paramString, paramArrayOfString);
    }
    if (this.mContext == null)
      getContext().getContentResolver().notifyChange(paramUri, null);
    while (true)
    {
      closeDB();
      return j;
      this.mContext.getContentResolver().notifyChange(paramUri, null);
    }
  }

  public String getType(Uri paramUri)
  {
    return DataBaseAdapter.mContentType[sUriMatcher.match(paramUri)];
  }

  public Context getmContext()
  {
    return this.mContext;
  }

  public Uri insert(Uri paramUri, ContentValues paramContentValues)
  {
    ContentValues localContentValues;
    Uri localUri2;
    if (paramContentValues != null)
    {
      localContentValues = new ContentValues(paramContentValues);
      getDB();
      long l = 0L;
      int i = sUriMatcher.match(paramUri);
      Uri localUri1 = null;
      if (i >= 0)
      {
        int j = DataBaseAdapter.mDataBaseColumns.length;
        localUri1 = null;
        if (i < j)
        {
          l = this.mDB.insert(DataBaseAdapter.mDataBaseColumns[i].getTableName(), null, localContentValues);
          localUri1 = DataBaseAdapter.mDataBaseColumns[i].getContentUri();
        }
      }
      if (l <= 0L)
        break label154;
      localUri2 = ContentUris.withAppendedId(localUri1, l);
      if (this.mContext != null)
        break label138;
      getContext().getContentResolver().notifyChange(localUri2, null);
    }
    while (true)
    {
      closeDB();
      return localUri2;
      localContentValues = new ContentValues();
      break;
      label138: this.mContext.getContentResolver().notifyChange(localUri2, null);
    }
    label154: closeDB();
    return null;
  }

  public boolean onCreate()
  {
    if (this.mDBHelper == null)
      this.mDBHelper = new DBHelper(getContext());
    return true;
  }

  public Cursor query(Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2)
  {
    return query(paramUri, paramArrayOfString1, paramString1, paramArrayOfString2, paramString2, null);
  }

  public Cursor query(Uri paramUri, String[] paramArrayOfString1, String paramString1, String[] paramArrayOfString2, String paramString2, String paramString3)
  {
    SQLiteQueryBuilder localSQLiteQueryBuilder = new SQLiteQueryBuilder();
    int i = sUriMatcher.match(paramUri);
    if ((i >= 0) && (i < DataBaseAdapter.mDataBaseColumns.length))
    {
      localSQLiteQueryBuilder.setTables(DataBaseAdapter.mDataBaseColumns[i].getTableName());
      if ("config".equals(DataBaseAdapter.mDataBaseColumns[i].getTableName()))
      {
        MatrixCursor localMatrixCursor = new MatrixCursor(new String[] { "value" });
        if (GlobalEnv.getInstance().getLogConfig())
        {
          localMatrixCursor.addRow(new String[] { "on" });
          return localMatrixCursor;
        }
        localMatrixCursor.addRow(new String[] { "off" });
        return localMatrixCursor;
      }
    }
    getDB();
    Cursor localCursor = localSQLiteQueryBuilder.query(this.mDB, paramArrayOfString1, paramString1, paramArrayOfString2, null, null, paramString2, paramString3);
    if (this.mContext == null)
      localCursor.setNotificationUri(getContext().getContentResolver(), paramUri);
    while (true)
    {
      return localCursor;
      localCursor.setNotificationUri(this.mContext.getContentResolver(), paramUri);
    }
  }

  public int update(Uri paramUri, ContentValues paramContentValues, String paramString, String[] paramArrayOfString)
  {
    getDB();
    int i = sUriMatcher.match(paramUri);
    int j = 0;
    if (i >= 0)
    {
      int k = DataBaseAdapter.mDataBaseColumns.length;
      j = 0;
      if (i < k)
      {
        j = this.mDB.update(DataBaseAdapter.mDataBaseColumns[i].getTableName(), paramContentValues, paramString, paramArrayOfString);
        if (("config".equals(DataBaseAdapter.mDataBaseColumns[i].getTableName())) && (paramContentValues != null))
        {
          Log.i("binjing", "binjing  = " + paramContentValues.get("value"));
          if (!"on".equals(paramContentValues.get("value")))
            break label157;
          GlobalEnv.getInstance().setLogConfig(true);
        }
      }
    }
    if (this.mContext == null)
      getContext().getContentResolver().notifyChange(paramUri, null);
    while (true)
    {
      closeDB();
      return j;
      label157: GlobalEnv.getInstance().setLogConfig(false);
      break;
      this.mContext.getContentResolver().notifyChange(paramUri, null);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.db.DBProvider
 * JD-Core Version:    0.6.2
 */