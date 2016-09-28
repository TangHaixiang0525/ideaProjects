package com.starcor.hunan.opendownload.logupload;

import com.starcor.config.AppFuncCfg;
import java.util.ArrayList;
import java.util.List;

public class LogCache
  implements BaseCache
{
  public static final int LOG_RECORD_LIMIT = 768;
  private static LogCache logCache;
  StringBuilder _appLogCache;
  StringBuilder _sysLogCache;
  private List<LogRecord> cacheList = new ArrayList();

  private void addLog2Cache(LogRecord paramLogRecord)
  {
    if (paramLogRecord == null)
      return;
    synchronized (this.cacheList)
    {
      if (this.cacheList.size() > 10000)
        this.cacheList.clear();
      this.cacheList.add(paramLogRecord);
      return;
    }
  }

  public static LogCache getInstance()
  {
    if (logCache == null)
      logCache = new LogCache();
    return logCache;
  }

  private boolean writeCache(LogRecord.LogType paramLogType, String paramString, StringBuilder paramStringBuilder)
  {
    if ((paramStringBuilder.length() == 0) && (paramString.length() >= 768))
      addLog2Cache(new LogRecord(paramLogType, paramString + "\n"));
    do
    {
      return false;
      paramStringBuilder.append(paramString).append("\n");
    }
    while (paramStringBuilder.length() < 768);
    addLog2Cache(new LogRecord(paramLogType, paramStringBuilder.toString()));
    return true;
  }

  public void addLog2Cache(LogRecord.LogType paramLogType, String paramString)
  {
    while (true)
    {
      try
      {
        boolean bool = AppFuncCfg.FUNCTION_ENABLE_LOGUPLOAD;
        if (!bool)
          return;
        if (this.cacheList == null)
          continue;
        switch (1.$SwitchMap$com$starcor$hunan$opendownload$logupload$LogRecord$LogType[paramLogType.ordinal()])
        {
        default:
          if (this._sysLogCache == null)
            this._sysLogCache = new StringBuilder();
          if (!writeCache(paramLogType, paramString, this._sysLogCache))
            continue;
          this._sysLogCache = null;
          continue;
        case 1:
        }
      }
      finally
      {
      }
      if (this._appLogCache == null)
        this._appLogCache = new StringBuilder();
      if (writeCache(paramLogType, paramString, this._appLogCache))
        this._appLogCache = null;
    }
  }

  public LogRecord getCache()
  {
    if ((this.cacheList != null) && (!this.cacheList.isEmpty()))
      return (LogRecord)this.cacheList.remove(0);
    return null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.opendownload.logupload.LogCache
 * JD-Core Version:    0.6.2
 */