package com.sohutv.tv.player.util;

import android.text.TextUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class f
{
  public static String a = "partnerNo";
  public static String b = "sver";
  public static String c = "type";
  public static String d = "showLog";
  public static String e = "showPlayerLog";
  public static String f = "showDebug";
  public static String g = "isTestServer";
  public static String h = "isBeijiServer";
  public static String i = "fee";
  public static String j = "showBeforeVideoAD";
  public static String k = "showPauseAD";
  public static String l = "sohu_server_control_domain_test";
  public static String m = "sohu_server_control_domain";
  public static String n = "gitv_server_control_domain";
  public static String o = "sohu_hot_play_control_domain";
  public static String p = "gitv_hot_play_control_domain";
  public static String q = "sohu_video_play_statistics_control_domain";
  public static String r = "gitv_video_play_statistics_control_domain";
  public static String s = "sohu_play_quality_statistics_control_domain";
  public static String t = "gitv_play_quality_statistics_control_domain";
  public static String u = "sohu_user_play_statistics_control_domain";
  public static String v = "gitv_user_play_statistics_control_domain";
  private static HashMap<String, Properties> w = new HashMap();

  public static String a(String paramString1, String paramString2)
  {
    Properties localProperties = (Properties)w.get(paramString1);
    HashMap localHashMap1 = w;
    if (localProperties == null);
    try
    {
      synchronized (w)
      {
        localProperties = a(paramString1);
        w.put(paramString1, localProperties);
        if (localProperties == null)
          return null;
      }
    }
    finally
    {
    }
    return localProperties.getProperty(paramString2);
  }

  private static Properties a(String paramString)
  {
    Properties localProperties = new Properties();
    InputStream localInputStream;
    if (!TextUtils.isEmpty(paramString))
    {
      localInputStream = f.class.getResourceAsStream(paramString);
      if (localInputStream == null);
    }
    try
    {
      localProperties.load(localInputStream);
      if (localInputStream != null);
      try
      {
        localInputStream.close();
        return localProperties;
      }
      catch (IOException localIOException1)
      {
        localIOException1.printStackTrace();
        return localProperties;
      }
    }
    catch (IOException localIOException3)
    {
      do
        localIOException3.printStackTrace();
      while (localInputStream == null);
      try
      {
        localInputStream.close();
        return localProperties;
      }
      catch (IOException localIOException4)
      {
        localIOException4.printStackTrace();
        return localProperties;
      }
    }
    finally
    {
      if (localInputStream == null);
    }
    try
    {
      localInputStream.close();
      throw localObject;
    }
    catch (IOException localIOException2)
    {
      while (true)
        localIOException2.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.util.f
 * JD-Core Version:    0.6.2
 */