package com.huawei.dmpbase;

public class DmpLog
{
  public static final int LOG_DEBUG = 0;
  public static final int LOG_ERROR = 3;
  public static final int LOG_INFO = 1;
  public static final int LOG_WARN = 2;

  public static void CloseLocalFileLog()
  {
    DmpBase.nativeCloseLocalFileLog();
  }

  public static void CloseLogCatLog()
  {
    DmpBase.nativeCloseLogCatLog();
  }

  public static void OpenLocalFileLog(String paramString)
  {
    DmpBase.nativeOpenLocalFileLog(paramString);
  }

  public static void OpenLogCatLog()
  {
    DmpBase.nativeOpenLogCatLog();
  }

  public static void SetLogLevel(int paramInt)
  {
    DmpBase.SetLogLevel(paramInt);
  }

  public static void d(String paramString1, String paramString2)
  {
    DmpBase.WriteLog(0, paramString1, paramString2);
  }

  public static void e(String paramString1, String paramString2)
  {
    DmpBase.WriteLog(3, paramString1, paramString2);
  }

  public static void i(String paramString1, String paramString2)
  {
    DmpBase.WriteLog(1, paramString1, paramString2);
  }

  public static void w(String paramString1, String paramString2)
  {
    DmpBase.WriteLog(2, paramString1, paramString2);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.huawei.dmpbase.DmpLog
 * JD-Core Version:    0.6.2
 */