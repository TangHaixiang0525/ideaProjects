package com.huawei.ca;

import android.util.Log;

public class OTTCA
{
  static final String LIBOTTCA = "ottvmxca";
  static final String TAG = "HAPlayer_ottca";
  private static boolean loaded = false;

  static
  {
    try
    {
      System.loadLibrary("ottvmxca");
      Log.i("HAPlayer_ottca", "Succeed to load library ottvmxca.");
      loaded = true;
      return;
    }
    catch (UnsatisfiedLinkError localUnsatisfiedLinkError)
    {
      Log.e("HAPlayer_ottca", "Failed to load library ottvmxca!");
    }
  }

  public static String GetVer()
  {
    if (loaded)
      return nativeGetOTTCAVer();
    return "not support";
  }

  protected static native String nativeGetOTTCAVer();
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.huawei.ca.OTTCA
 * JD-Core Version:    0.6.2
 */