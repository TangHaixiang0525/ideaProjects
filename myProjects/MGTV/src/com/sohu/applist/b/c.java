package com.sohu.applist.b;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class c extends SQLiteOpenHelper
{
  public c(Context paramContext)
  {
    super(paramContext, "miaozhen.db", null, 1);
  }

  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    paramSQLiteDatabase.execSQL("create table if not exists mz_a (id integer primary key autoincrement,a varchar(50), b integer)");
    paramSQLiteDatabase.execSQL("create table if not exists mz_b (id integer primary key autoincrement,a varchar(20), b varchar(20), c varchar(20), d varchar(20), e varchar(20),f varchar(20))");
  }

  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    paramSQLiteDatabase.execSQL("drop table if exists mz_a");
    paramSQLiteDatabase.execSQL("drop table if exists mz_b");
    onCreate(paramSQLiteDatabase);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.applist.b.c
 * JD-Core Version:    0.6.2
 */