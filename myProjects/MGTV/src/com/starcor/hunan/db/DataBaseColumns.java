package com.starcor.hunan.db;

import android.net.Uri;
import android.provider.BaseColumns;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

public abstract class DataBaseColumns
  implements BaseColumns
{
  public static final String AUTHORITY = "com.starcor.mango.hunan.database";
  public static final String DATABASE_NAME = "database";
  public static final int DATABASE_VERSION = 16;

  public static final Class<DataBaseColumns>[] getSubClasses()
  {
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    while (true)
      if (i < DataBaseAdapter.mDataBaseColumns.length)
        try
        {
          localArrayList.add(DataBaseAdapter.mDataBaseColumns[i].getClass());
          i++;
        }
        catch (Exception localException)
        {
          while (true)
            localException.printStackTrace();
        }
    return (Class[])localArrayList.toArray(new Class[0]);
  }

  private static final String getTableCreator(String paramString, Map<String, String> paramMap)
  {
    String[] arrayOfString = (String[])paramMap.keySet().toArray(new String[0]);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("CREATE TABLE IF NOT EXISTS ").append(paramString).append(" (");
    int i = arrayOfString.length;
    for (int j = 0; j < i; j++)
    {
      String str = (String)paramMap.get(arrayOfString[j]);
      localStringBuilder.append(arrayOfString[j]).append(" ");
      localStringBuilder.append(str);
      if (j < i - 1)
        localStringBuilder.append(",");
    }
    localStringBuilder.append(");");
    return localStringBuilder.toString();
  }

  public String[] getColumns()
  {
    return (String[])getTableMap().values().toArray(new String[0]);
  }

  public abstract Uri getContentUri();

  public String getTableCreateor()
  {
    return getTableCreator(getTableName(), getTableMap());
  }

  protected abstract Map<String, String> getTableMap();

  public abstract String getTableName();
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.db.DataBaseColumns
 * JD-Core Version:    0.6.2
 */