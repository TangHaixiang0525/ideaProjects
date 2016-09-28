package com.sohu.app.ads.sdk.a;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class a extends SQLiteOpenHelper
{
  public a(Context paramContext)
  {
    super(paramContext, "ads.db", null, 2);
  }

  private void a(SQLiteDatabase paramSQLiteDatabase)
  {
    paramSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS offlineBanner(id integer primary key autoincrement,vid VARCHAR(100),adsequence integer,isofflinead integer,Impression VARCHAR(2000),Duration VARCHAR(50),ClickThrough VARCHAR(500),ClickTracking VARCHAR(500),MediaFile VARCHAR(500),creativeView VARCHAR(500),start VARCHAR(500),firstQuartile VARCHAR(500),midpoint VARCHAR(500),thirdQuartile VARCHAR(500),complete VARCHAR(500),sdkTracking VARCHAR(2000),sdkClick VARCHAR(2000),time VARCHAR(50));");
    paramSQLiteDatabase.execSQL("create TABLE IF NOT EXISTS offlinePause(id integer primary key autoincrement,vid VARCHAR(100),Impression VARCHAR(2000),StaticResource VARCHAR(500),NonLinearClickThrough VARCHAR(500),sdkTracking VARCHAR(2000),sdkClick VARCHAR(2000),time VARCHAR(50));");
    paramSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS download_url (id integer primary key autoincrement,url varchar(500),status integer,length integer)");
    paramSQLiteDatabase.execSQL("create TABLE IF NOT EXISTS startPicture(id integer primary key autoincrement,vid VARCHAR(100),Impression VARCHAR(2000),StaticResource VARCHAR(500),NonLinearClickThrough VARCHAR(500),sdkTracking VARCHAR(2000),sdkClick VARCHAR(2000),time VARCHAR(50));");
  }

  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    com.sohu.app.ads.sdk.c.a.a("DBOpenHelper onCreate()");
    a(paramSQLiteDatabase);
  }

  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS startPicture");
    paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS download_url");
    paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS offlinePause");
    paramSQLiteDatabase.execSQL("DROP TABLE IF EXISTS offlineBanner");
    a(paramSQLiteDatabase);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.a.a
 * JD-Core Version:    0.6.2
 */