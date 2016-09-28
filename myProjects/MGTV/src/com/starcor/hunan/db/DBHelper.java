package com.starcor.hunan.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import com.starcor.config.AppFuncCfg;
import com.starcor.core.utils.Logger;

public class DBHelper extends SQLiteOpenHelper
{
  private static final String TAG = DBHelper.class.getSimpleName();

  public DBHelper(Context paramContext)
  {
    super(paramContext, "database", null, 16);
  }

  private void initData(SQLiteDatabase paramSQLiteDatabase)
  {
    String str = "off";
    if (AppFuncCfg.FUNCTION_OPEN_LOGGER)
      str = "on";
    paramSQLiteDatabase.execSQL("insert into config(key,value) values ('log_output','" + str + "')");
    Logger.d(TAG, "initData database version 10");
  }

  public void onCreate(SQLiteDatabase paramSQLiteDatabase)
  {
    Logger.d(TAG, "onCreate SQLiteDatabase");
    operateTable(paramSQLiteDatabase, "");
    initData(paramSQLiteDatabase);
  }

  public void onUpgrade(SQLiteDatabase paramSQLiteDatabase, int paramInt1, int paramInt2)
  {
    Logger.d(TAG, "onUpgrade oldVersion:" + paramInt1 + ",newVersion:" + paramInt2);
    switch (paramInt2)
    {
    default:
      return;
    case 16:
    }
    operateTable(paramSQLiteDatabase, "");
    initData(paramSQLiteDatabase);
  }

  public void operateTable(SQLiteDatabase paramSQLiteDatabase, String paramString)
  {
    Class[] arrayOfClass = DataBaseColumns.getSubClasses();
    for (int i = 0; ; i++)
      if (i < arrayOfClass.length)
        try
        {
          DataBaseColumns localDataBaseColumns = (DataBaseColumns)arrayOfClass[i].newInstance();
          if (TextUtils.isEmpty(paramString))
            paramSQLiteDatabase.execSQL(localDataBaseColumns.getTableCreateor());
          else
            paramSQLiteDatabase.execSQL(paramString + localDataBaseColumns.getTableName());
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      else
        return;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.db.DBHelper
 * JD-Core Version:    0.6.2
 */