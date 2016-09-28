package com.sohu.upload.b;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.Date;

public class b
{
  private static Context a;

  public static String a(String paramString1, String paramString2)
  {
    try
    {
      String str = a.getSharedPreferences("switch", 0).getString(paramString1, paramString2);
      return str;
    }
    catch (Exception localException)
    {
      com.sohu.upload.a.a.b("�쳣:" + localException.getMessage() + ",��ȡ����ֵʧ��");
    }
    return "";
  }

  public static void a(Context paramContext)
  {
    a = paramContext;
  }

  public static void a(String paramString)
  {
    try
    {
      SharedPreferences.Editor localEditor = a.getSharedPreferences("registerip", 0).edit();
      localEditor.putString("register_ip", paramString);
      localEditor.commit();
      return;
    }
    catch (Exception localException)
    {
      com.sohu.upload.a.a.b("�쳣:" + localException.getMessage() + ",�����ϴε�ip��ַʧ��");
    }
  }

  public static void a(String paramString, long paramLong)
  {
    if (!com.sohu.upload.consts.a.a);
    SharedPreferences.Editor localEditor;
    do
    {
      return;
      localEditor = null;
      try
      {
        localEditor = a.getSharedPreferences("record", 0).edit();
        localEditor.putString(paramString, new Date(paramLong).toLocaleString());
        localEditor.commit();
        return;
      }
      catch (Exception localException)
      {
      }
    }
    while (localEditor == null);
    localEditor.putString(paramString, "error:" + localException.getMessage());
  }

  public static void a(String paramString, Object paramObject)
  {
    try
    {
      SharedPreferences.Editor localEditor = a.getSharedPreferences("switch", 0).edit();
      if ((paramObject instanceof String))
        localEditor.putString(paramString, (String)paramObject);
      if ((paramObject instanceof Integer))
        localEditor.putInt(paramString, ((Integer)paramObject).intValue());
      if ((paramObject instanceof Boolean))
        localEditor.putBoolean(paramString, ((Boolean)paramObject).booleanValue());
      if ((paramObject instanceof Long))
        localEditor.putLong(paramString, ((Long)paramObject).longValue());
      localEditor.commit();
      return;
    }
    catch (Exception localException)
    {
      com.sohu.upload.a.a.b("�쳣:" + localException.getMessage() + ",���濪��ֵʧ��");
    }
  }

  public static String b(String paramString)
  {
    try
    {
      String str = a.getSharedPreferences("registerip", 0).getString("register_ip", paramString);
      return str;
    }
    catch (Exception localException)
    {
      com.sohu.upload.a.a.b("�쳣:" + localException.getMessage() + ",��ȡ�ϴε�ip��ַʧ��");
    }
    return "";
  }

  public static void b(String paramString, long paramLong)
  {
    if (!com.sohu.upload.consts.a.a);
    SharedPreferences.Editor localEditor;
    do
    {
      return;
      localEditor = null;
      try
      {
        localEditor = a.getSharedPreferences("upload", 0).edit();
        localEditor.putString(paramString, new Date(paramLong).toLocaleString());
        localEditor.commit();
        return;
      }
      catch (Exception localException)
      {
      }
    }
    while (localEditor == null);
    localEditor.putString(paramString, "error:" + localException.getMessage());
  }

  public static void c(String paramString, long paramLong)
  {
    if (!com.sohu.upload.consts.a.a)
      return;
    try
    {
      SharedPreferences.Editor localEditor = a.getSharedPreferences("recreate", 0).edit();
      localEditor.putString(paramString, new Date(paramLong).toLocaleString());
      localEditor.commit();
      return;
    }
    catch (Exception localException)
    {
      com.sohu.upload.a.a.b("�쳣:" + localException.getMessage() + ",����ʱ����ʧ��");
    }
  }

  public static void d(String paramString, long paramLong)
  {
    try
    {
      SharedPreferences.Editor localEditor = a.getSharedPreferences("count", 0).edit();
      localEditor.putLong(paramString, paramLong);
      localEditor.commit();
      return;
    }
    catch (Exception localException)
    {
      com.sohu.upload.a.a.b("�쳣:" + localException.getMessage() + ",����ʱ����ʧ��");
    }
  }

  public static long e(String paramString, long paramLong)
  {
    try
    {
      long l = a.getSharedPreferences("count", 0).getLong(paramString, paramLong);
      return l;
    }
    catch (Exception localException)
    {
      com.sohu.upload.a.a.b("�쳣:" + localException.getMessage() + ",��ȡʱ����ʧ��");
    }
    return 0L;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.upload.b.b
 * JD-Core Version:    0.6.2
 */