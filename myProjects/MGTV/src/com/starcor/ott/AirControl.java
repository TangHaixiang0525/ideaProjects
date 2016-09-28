package com.starcor.ott;

public class AirControl
{
  private static boolean _isInitialized;

  static
  {
    try
    {
      _isInitialized = false;
      System.loadLibrary("_All_imgoTV_nn_tv_air_control");
      _isInitialized = true;
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public static native int doAction(String paramString1, String paramString2);

  public static native String getEvent();

  public static boolean init()
  {
    return _isInitialized;
  }

  public static boolean isValid()
  {
    return _isInitialized;
  }

  public static native int replyEvent(int paramInt1, int paramInt2, String paramString1, String paramString2);

  public static native int setConfig(String paramString1, String paramString2);

  public static native int start(int paramInt, String paramString);

  public static native int stop();
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.ott.AirControl
 * JD-Core Version:    0.6.2
 */