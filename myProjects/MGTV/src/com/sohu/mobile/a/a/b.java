package com.sohu.mobile.a.a;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.text.TextUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class b
  implements c
{
  private static b a = new b();
  private static String b = null;
  private static Context c;
  private static String d;
  private static String e;
  private static String f;
  private static boolean g = true;

  public static c a()
  {
    return a;
  }

  private void b(String paramString)
  {
    try
    {
      boolean bool = g;
      if (bool);
      while (true)
      {
        return;
        SharedPreferences.Editor localEditor = c.getSharedPreferences("DTQS", 0).edit();
        localEditor.putString("dtqs_url", paramString);
        localEditor.commit();
        b = paramString;
      }
    }
    finally
    {
    }
  }

  public void a(Context paramContext, String paramString1, String paramString2)
  {
    try
    {
      c = paramContext;
      d = paramString1;
      e = paramString2;
      f = a.a(c);
      String str = c();
      if (TextUtils.isEmpty(str));
      for (b = "http://mmg.aty.sohu.com/dtqs?appversion=" + f + "&version=" + d + "&appChannel=" + e + "&deviceName=" + Build.MODEL + "&offlineOadReqTimes=0" + "&offlineOadReqSucessTimes=0" + "&offlineOadReqFailedTimes=0" + "&offlineOadDownloadErrorTimes=0" + "&offlineOadInterfaceReqTimes=0" + "&offlineOadhaveData=0" + "&offlineOadNoData=0" + "&onlineOadDownloadErrorTimes=0" + "&offlineOadSize=0" + "&offlineOadNotNullSize=0" + "&offlineOadNullSize=0" + "&offlineOad1=0" + "&offlineOad2=0" + "&offlineOad3=0" + "&offlineOad4=0" + "&onlineOadInterfaceReqTimes=0" + "&onlineOadhaveData=0" + "&onlineOadNoData=0" + "&onlineOadSize=0" + "&onlineOadNotNullSize=0" + "&onlineOadNullSize=0" + "&onlineOad1=0" + "&onlineOad2=0" + "&onlineOad3=0" + "&onlineOad4=0" + "&onlineOad1Error=0" + "&onlineOad2Error=0" + "&onlineOad3Error=0" + "&onlineOad4Error=0" + "&onlineOad1Timeout=0" + "&onlineOad2Timeout=0" + "&onlineOad3Timeout=0" + "&onlineOad4Timeout=0" + "&toAppTrackingCallback=0" + "&toAppSaveCallback=0" + "&platform=ANDROID"; ; b = str)
        return;
    }
    finally
    {
    }
  }

  public void a(String paramString)
  {
    try
    {
      boolean bool = g;
      if (bool);
      while (true)
      {
        return;
        if (!TextUtils.isEmpty(paramString))
        {
          Matcher localMatcher = Pattern.compile(paramString + "\\=(.*?)&").matcher(b);
          if (localMatcher.find())
          {
            int i = Integer.parseInt(localMatcher.group(1));
            b(b.replaceAll(paramString + "=" + i, paramString + "=" + (i + 1)));
          }
        }
      }
    }
    finally
    {
    }
  }

  public void a(String paramString, int paramInt)
  {
    try
    {
      boolean bool = g;
      if (bool);
      while (true)
      {
        return;
        if (!TextUtils.isEmpty(paramString))
        {
          Matcher localMatcher = Pattern.compile(paramString + "\\=(.*?)&").matcher(b);
          if (localMatcher.find())
          {
            int i = Integer.parseInt(localMatcher.group(1));
            b(b.replaceAll(paramString + "=" + i, paramString + "=" + (i + paramInt)));
          }
        }
      }
    }
    finally
    {
    }
  }

  public void a(boolean paramBoolean)
  {
    try
    {
      g = paramBoolean;
      if (g)
        b = "";
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void b()
  {
    try
    {
      boolean bool = g;
      if (bool);
      while (true)
      {
        return;
        c.getSharedPreferences("DTQS", 0).edit().clear().commit();
        b = "http://mmg.aty.sohu.com/dtqs?appversion=" + f + "&version=" + d + "&appChannel=" + e + "&deviceName=" + Build.MODEL + "&offlineOadReqTimes=0" + "&offlineOadReqSucessTimes=0" + "&offlineOadReqFailedTimes=0" + "&offlineOadDownloadErrorTimes=0" + "&offlineOadInterfaceReqTimes=0" + "&offlineOadhaveData=0" + "&offlineOadNoData=0" + "&onlineOadDownloadErrorTimes=0" + "&offlineOadSize=0" + "&offlineOadNotNullSize=0" + "&offlineOadNullSize=0" + "&offlineOad1=0" + "&offlineOad2=0" + "&offlineOad3=0" + "&offlineOad4=0" + "&onlineOadInterfaceReqTimes=0" + "&onlineOadhaveData=0" + "&onlineOadNoData=0" + "&onlineOadSize=0" + "&onlineOadNotNullSize=0" + "&onlineOadNullSize=0" + "&onlineOad1=0" + "&onlineOad2=0" + "&onlineOad3=0" + "&onlineOad4=0" + "&onlineOad1Error=0" + "&onlineOad2Error=0" + "&onlineOad3Error=0" + "&onlineOad4Error=0" + "&onlineOad1Timeout=0" + "&onlineOad2Timeout=0" + "&onlineOad3Timeout=0" + "&onlineOad4Timeout=0" + "&toAppTrackingCallback=0" + "&toAppSaveCallback=0" + "&platform=ANDROID";
      }
    }
    finally
    {
    }
  }

  public String c()
  {
    try
    {
      if (g);
      String str;
      for (Object localObject2 = ""; ; localObject2 = str)
      {
        return localObject2;
        str = c.getSharedPreferences("DTQS", 0).getString("dtqs_url", "");
      }
    }
    finally
    {
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.mobile.a.a.b
 * JD-Core Version:    0.6.2
 */