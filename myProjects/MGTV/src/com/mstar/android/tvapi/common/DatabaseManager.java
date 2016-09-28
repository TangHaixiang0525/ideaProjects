package com.mstar.android.tvapi.common;

import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.common.vo.EnumDatabaseTypeIndex;

public final class DatabaseManager
{
  protected static DatabaseManager getInstance()
  {
    throw new RuntimeException("stub");
  }

  public final native void clearNativeDatabaseDirty(short paramShort)
    throws TvCommonException;

  protected void finalize()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public final String getCustomerSqlDbPathInformation(EnumDatabaseTypeIndex paramEnumDatabaseTypeIndex)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final String getCustomerSqlDbTableName(EnumDatabaseTypeIndex paramEnumDatabaseTypeIndex)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native boolean isNativeDatabaseDirty(short paramShort)
    throws TvCommonException;

  protected void release()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public final native void setDatabaseDirtyByApplication(short paramShort)
    throws TvCommonException;

  public final native void setDebugMode(boolean paramBoolean)
    throws TvCommonException;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.common.DatabaseManager
 * JD-Core Version:    0.6.2
 */