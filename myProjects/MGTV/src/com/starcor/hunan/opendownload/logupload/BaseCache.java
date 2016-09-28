package com.starcor.hunan.opendownload.logupload;

public abstract interface BaseCache
{
  public abstract void addLog2Cache(LogRecord.LogType paramLogType, String paramString);

  public abstract LogRecord getCache();
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.opendownload.logupload.BaseCache
 * JD-Core Version:    0.6.2
 */