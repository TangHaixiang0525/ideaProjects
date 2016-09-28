package com.starcor.hunan.opendownload.logupload;

public class LogRecord
{
  private String content;
  private LogType type;

  public LogRecord(LogType paramLogType, String paramString)
  {
    this.type = paramLogType;
    this.content = paramString;
  }

  public String getContent()
  {
    return this.content;
  }

  public LogType getType()
  {
    return this.type;
  }

  public static enum LogType
  {
    static
    {
      APP = new LogType("APP", 1);
      LogType[] arrayOfLogType = new LogType[2];
      arrayOfLogType[0] = SYSTEM;
      arrayOfLogType[1] = APP;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.opendownload.logupload.LogRecord
 * JD-Core Version:    0.6.2
 */