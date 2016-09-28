package com.miaozhen.mzmonitor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

class c extends SQLiteOpenHelper
{
  public c(b paramb, Context paramContext, String paramString, SQLiteDatabase.CursorFactory paramCursorFactory, int paramInt)
  {
    super(paramContext, paramString, paramCursorFactory, paramInt);
  }

  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    paramSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS mzcaches (cacheId VARCHAR PRIMARY KEY, url VARCHAR, timestamp LONG, times INTEGER)");
  }

  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS mzcaches");
    paramSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS mzcaches (cacheId VARCHAR PRIMARY KEY, url VARCHAR, timestamp LONG, times INTEGER)");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.miaozhen.mzmonitor.c
 * JD-Core Version:    0.6.2
 */