package com.sohu.logger.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;

public class PrefUtil
{
  public static boolean getBoolean(Context paramContext, String paramString1, String paramString2, boolean paramBoolean)
  {
    return getSharedPreferences(paramContext, paramString1).getBoolean(paramString2, paramBoolean);
  }

  public static boolean getBoolean(Context paramContext, String paramString, boolean paramBoolean)
  {
    return getSharedPreferences(paramContext).getBoolean(paramString, paramBoolean);
  }

  public static float getFloat(Context paramContext, String paramString, float paramFloat)
  {
    return getSharedPreferences(paramContext).getFloat(paramString, paramFloat);
  }

  public static float getFloat(Context paramContext, String paramString1, String paramString2, float paramFloat)
  {
    return getSharedPreferences(paramContext, paramString1).getFloat(paramString2, paramFloat);
  }

  public static int getInt(Context paramContext, String paramString, int paramInt)
  {
    return getSharedPreferences(paramContext).getInt(paramString, paramInt);
  }

  public static int getInt(Context paramContext, String paramString1, String paramString2, int paramInt)
  {
    return getSharedPreferences(paramContext, paramString1).getInt(paramString2, paramInt);
  }

  public static long getLong(Context paramContext, String paramString, long paramLong)
  {
    return getSharedPreferences(paramContext).getLong(paramString, paramLong);
  }

  public static long getLong(Context paramContext, String paramString1, String paramString2, long paramLong)
  {
    return getSharedPreferences(paramContext, paramString1).getLong(paramString2, paramLong);
  }

  public static SharedPreferences getSharedPreferences(Context paramContext)
  {
    return getSharedPreferences(paramContext, null);
  }

  public static SharedPreferences getSharedPreferences(Context paramContext, String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return PreferenceManager.getDefaultSharedPreferences(paramContext);
    return paramContext.getSharedPreferences(paramString, 0);
  }

  public static String getString(Context paramContext, String paramString1, String paramString2)
  {
    return getSharedPreferences(paramContext).getString(paramString1, paramString2);
  }

  public static String getString(Context paramContext, String paramString1, String paramString2, String paramString3)
  {
    return getSharedPreferences(paramContext, paramString1).getString(paramString2, paramString3);
  }

  public static void putBoolean(Context paramContext, String paramString1, String paramString2, boolean paramBoolean)
  {
    getSharedPreferences(paramContext, paramString1).edit().putBoolean(paramString2, paramBoolean).commit();
  }

  public static void putBoolean(Context paramContext, String paramString, boolean paramBoolean)
  {
    getSharedPreferences(paramContext).edit().putBoolean(paramString, paramBoolean).commit();
  }

  public static void putFloat(Context paramContext, String paramString, float paramFloat)
  {
    getSharedPreferences(paramContext).edit().putFloat(paramString, paramFloat).commit();
  }

  public static void putFloat(Context paramContext, String paramString1, String paramString2, float paramFloat)
  {
    getSharedPreferences(paramContext, paramString1).edit().putFloat(paramString2, paramFloat).commit();
  }

  public static void putInt(Context paramContext, String paramString, int paramInt)
  {
    getSharedPreferences(paramContext).edit().putInt(paramString, paramInt).commit();
  }

  public static void putInt(Context paramContext, String paramString1, String paramString2, int paramInt)
  {
    getSharedPreferences(paramContext, paramString1).edit().putInt(paramString2, paramInt).commit();
  }

  public static void putLong(Context paramContext, String paramString, long paramLong)
  {
    getSharedPreferences(paramContext).edit().putLong(paramString, paramLong).commit();
  }

  public static void putLong(Context paramContext, String paramString1, String paramString2, long paramLong)
  {
    getSharedPreferences(paramContext, paramString1).edit().putLong(paramString2, paramLong).commit();
  }

  public static void putString(Context paramContext, String paramString1, String paramString2)
  {
    getSharedPreferences(paramContext).edit().putString(paramString1, paramString2).commit();
  }

  public static void putString(Context paramContext, String paramString1, String paramString2, String paramString3)
  {
    getSharedPreferences(paramContext, paramString1).edit().putString(paramString2, paramString3).commit();
  }

  public static void remove(Context paramContext, String paramString)
  {
    getSharedPreferences(paramContext).edit().remove(paramString).commit();
  }

  public static void remove(Context paramContext, String paramString1, String paramString2)
  {
    getSharedPreferences(paramContext, paramString1).edit().remove(paramString2).commit();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.logger.util.PrefUtil
 * JD-Core Version:    0.6.2
 */