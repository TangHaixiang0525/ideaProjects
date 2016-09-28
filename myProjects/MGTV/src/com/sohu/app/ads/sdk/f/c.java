package com.sohu.app.ads.sdk.f;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class c
{
  private static Context a;

  public static int a()
  {
    try
    {
      int i = a.getSharedPreferences("SWITCH", 0).getInt("device", -1);
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }

  public static void a(Context paramContext)
  {
    a = paramContext;
  }

  public static void a(String paramString, Object paramObject)
  {
    try
    {
      SharedPreferences.Editor localEditor = a.getSharedPreferences("SWITCH", 0).edit();
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
      localException.printStackTrace();
    }
  }

  public static boolean b()
  {
    try
    {
      boolean bool = a.getSharedPreferences("SWITCH", 0).getBoolean("voiceEnable", false);
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.f.c
 * JD-Core Version:    0.6.2
 */