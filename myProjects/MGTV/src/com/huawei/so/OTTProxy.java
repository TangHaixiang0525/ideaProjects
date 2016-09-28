package com.huawei.so;

import com.huawei.dmpbase.DmpLog;

public class OTTProxy
{
  private static boolean loaded = false;

  static
  {
    try
    {
      System.loadLibrary("stlport_shared");
      System.loadLibrary("curl");
      System.loadLibrary("epp");
      loaded = true;
      return;
    }
    catch (UnsatisfiedLinkError localUnsatisfiedLinkError)
    {
      DmpLog.e("HAPlayer_libepp", localUnsatisfiedLinkError.getMessage());
    }
  }

  public static String GetVer()
  {
    if (loaded)
      return native_proxy_get_eppversion();
    return "not support";
  }

  public static void loadso()
  {
  }

  public static native int native_proxy_delete_downloadfile(String paramString, int paramInt);

  public static native int native_proxy_download(String paramString1, String paramString2, int paramInt1, int paramInt2);

  public static native int native_proxy_get_allTsNum();

  public static native int native_proxy_get_callback();

  public static native int native_proxy_get_downloadAllTsDuation();

  public static native int native_proxy_get_downloadBitrate();

  public static native int native_proxy_get_downloadOneTsDuation();

  public static native float native_proxy_get_downloadSpeed();

  public static native String native_proxy_get_eppversion();

  public static native int native_proxy_get_finishedTsNum();

  public static native String native_proxy_get_language_list();

  public static native String native_proxy_get_original_bitrate();

  public static native float native_proxy_get_tsdownloadrate();

  public static native void native_proxy_init();

  public static native String native_proxy_play_local(String paramString1, String paramString2, int paramInt);

  public static native void native_proxy_set_audio_preferedLang(String paramString);

  public static native void native_proxy_set_filterbitrate(int paramInt1, int paramInt2);

  public static native void native_proxy_set_loglevel(int paramInt);

  public static native void native_proxy_set_manualbitrate(int paramInt);

  public static native void native_proxy_set_ntpdiff(int paramInt);

  public static native void native_proxy_set_seektime(long paramLong);

  public static native void native_proxy_set_subtitle_preferedLang(String paramString);

  public static native int native_proxy_stop_download();

  public static native void native_proxy_tsClose();

  public static native String native_proxy_tsOpen(String paramString, int paramInt, long paramLong, float paramFloat);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.huawei.so.OTTProxy
 * JD-Core Version:    0.6.2
 */