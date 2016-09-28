package com.starcor.core.utils;

import com.starcor.xul.XulUtils;

public class MgtvAdErrorTools
{
  public static long errorTime = 0L;
  public static int errorTimes = 0;

  public static void addErrorCount()
  {
    errorTimes = 1 + errorTimes;
    errorTime = XulUtils.timestamp();
  }

  public static int getErrorTimes()
  {
    return errorTimes;
  }

  public static long lastErrorTime()
  {
    return errorTime;
  }

  public static void reset()
  {
    errorTimes = 0;
    errorTime = 0L;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.utils.MgtvAdErrorTools
 * JD-Core Version:    0.6.2
 */