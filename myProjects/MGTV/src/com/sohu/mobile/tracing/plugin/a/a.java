package com.sohu.mobile.tracing.plugin.a;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class a extends SQLiteOpenHelper
{
  public a(Context paramContext)
  {
    super(paramContext, "trackingplugin.db", null, 1);
  }

  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    paramSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS tracking (id integer primary key autoincrement,url varchar(500),body varchar(20),vastTag varchar(20),exposeAction varchar(20),isUpload integer(10))");
  }

  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS tracking");
    onCreate(paramSQLiteDatabase);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.mobile.tracing.plugin.a.a
 * JD-Core Version:    0.6.2
 */