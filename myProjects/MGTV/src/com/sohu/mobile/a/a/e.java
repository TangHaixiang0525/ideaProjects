package com.sohu.mobile.a.a;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.text.TextUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class e
  implements d
{
  private static e a = new e();
  private static String b = null;
  private static Context c;
  private static String d;
  private static String e;
  private static boolean f = true;

  private void b(String paramString)
  {
    try
    {
      boolean bool = f;
      if (bool);
      while (true)
      {
        return;
        SharedPreferences.Editor localEditor = c.getSharedPreferences("MQS", 0).edit();
        localEditor.putString("url", paramString);
        localEditor.commit();
        b = paramString;
      }
    }
    finally
    {
    }
  }

  public static d c()
  {
    return a;
  }

  public void a()
  {
    try
    {
      boolean bool = f;
      if (bool);
      while (true)
      {
        return;
        c.getSharedPreferences("MQS", 0).edit().clear().commit();
        b = "http://mmg.aty.sohu.com/mqs?iflyNoVoice=0&iflyNetError=0&iflyNoPermission=0&iflyFailed=0&iflySucess=0&iflyLongClick=0&iflyLoginError=0&iflyUploadError=0&iflyRegnizeError=0&oadReqTimes=0&oadPlayTimeout=0&oadReq_LinkExp=0&oadReq_CodeExp=0&oadTraking_Times=0&oadTraking_SucessTimes=0&oadTraking_FailedTimes=0&oadTraking_LinkExp=0&oadTraking_CodeExp=0&version=" + d + "appversion=" + e + "&deviceName=" + Build.MODEL + "&downloadErrorUrl=0" + "&platform=ANDROID";
      }
    }
    finally
    {
    }
  }

  public void a(Context paramContext, String paramString)
  {
    try
    {
      c = paramContext;
      d = paramString;
      e = a.a(c);
      String str = b();
      if (TextUtils.isEmpty(str));
      for (b = "http://mmg.aty.sohu.com/mqs?iflyNoVoice=0&iflyNetError=0&iflyNoPermission=0&iflyFailed=0&iflySucess=0&iflyLongClick=0&iflyLoginError=0&iflyUploadError=0&iflyRegnizeError=0&oadReqTimes=0&oadPlayTimeout=0&oadReq_LinkExp=0&oadReq_CodeExp=0&oadTraking_Times=0&oadTraking_SucessTimes=0&oadTraking_FailedTimes=0&oadTraking_LinkExp=0&oadTraking_CodeExp=0&version=" + d + "appversion=" + e + "&deviceName=" + Build.MODEL + "&downloadErrorUrl=0&platform=ANDROID"; ; b = str)
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
      boolean bool = f;
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

  public void a(String paramString1, String paramString2)
  {
    try
    {
      boolean bool = f;
      if (bool);
      while (true)
      {
        return;
        if ((!TextUtils.isEmpty(paramString1)) && ("downloadErrorUrl".equals(paramString1)))
        {
          Matcher localMatcher = Pattern.compile(paramString1 + "\\=(.*?)&").matcher(b);
          if (localMatcher.find())
          {
            String str = localMatcher.group(1);
            b(b.replaceAll(paramString1 + "=" + str, paramString1 + "=" + paramString2));
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
      f = paramBoolean;
      if (f)
        b = "";
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public String b()
  {
    try
    {
      if (f);
      String str;
      for (Object localObject2 = ""; ; localObject2 = str)
      {
        return localObject2;
        str = c.getSharedPreferences("MQS", 0).getString("url", "");
      }
    }
    finally
    {
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.mobile.a.a.e
 * JD-Core Version:    0.6.2
 */