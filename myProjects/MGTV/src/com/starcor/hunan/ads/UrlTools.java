package com.starcor.hunan.ads;

import android.text.TextUtils;
import java.net.URLEncoder;

public class UrlTools
{
  private static String getParamsStr(String paramString)
  {
    String str1 = paramString.trim();
    String[] arrayOfString = str1.split("[?]");
    int i = str1.length();
    String str2 = null;
    if (i > 1)
    {
      int j = arrayOfString.length;
      str2 = null;
      if (j > 1)
      {
        String str3 = arrayOfString[1];
        str2 = null;
        if (str3 != null)
          str2 = arrayOfString[1];
      }
    }
    return str2;
  }

  public static String getPath(String paramString)
  {
    String str1 = paramString.trim();
    if (!str1.contains("?"))
      return str1;
    String[] arrayOfString = str1.split("[?]");
    int i = str1.length();
    String str2 = null;
    if (i > 0)
    {
      int j = arrayOfString.length;
      str2 = null;
      if (j > 1)
      {
        String str3 = arrayOfString[0];
        str2 = null;
        if (str3 != null)
          str2 = arrayOfString[0];
      }
    }
    return str2;
  }

  public static String getUrlParamsEncoded(String paramString)
  {
    String str1 = getParamsStr(paramString);
    if (TextUtils.isEmpty(str1));
    String[] arrayOfString;
    do
    {
      return str1;
      arrayOfString = str1.split("&");
    }
    while ((arrayOfString == null) || (arrayOfString.length < 0));
    String str2 = "";
    int i = arrayOfString.length;
    int j = 0;
    while (true)
      if (j < i)
      {
        String str3 = arrayOfString[j];
        try
        {
          String str5 = str2 + str3.substring(0, str3.indexOf("=")) + "=" + URLEncoder.encode(str3.substring(1 + str3.indexOf("=")));
          str4 = str5;
          str2 = str4 + "&";
          j++;
        }
        catch (Exception localException)
        {
          while (true)
            String str4 = str2 + str3;
        }
      }
    if (str2.endsWith("&"))
      str2 = str2.substring(0, -1 + str2.length());
    return str2;
  }

  public static String getUrlParamsMap(String paramString)
  {
    return getParamsStr(paramString);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.ads.UrlTools
 * JD-Core Version:    0.6.2
 */