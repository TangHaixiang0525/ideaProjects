package com.sohutv.tv.player.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;

public class e
{
  public static int a(Context paramContext, String paramString, int paramInt)
  {
    return a(paramContext).getInt(paramString, paramInt);
  }

  public static SharedPreferences a(Context paramContext)
  {
    return a(paramContext, null);
  }

  public static SharedPreferences a(Context paramContext, String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return PreferenceManager.getDefaultSharedPreferences(paramContext);
    return paramContext.getSharedPreferences(paramString, 0);
  }

  public static boolean a(Context paramContext, String paramString, boolean paramBoolean)
  {
    return a(paramContext).getBoolean(paramString, paramBoolean);
  }

  public static void b(Context paramContext, String paramString, int paramInt)
  {
    a(paramContext).edit().putInt(paramString, paramInt).commit();
  }

  public static void b(Context paramContext, String paramString, boolean paramBoolean)
  {
    a(paramContext).edit().putBoolean(paramString, paramBoolean).commit();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.util.e
 * JD-Core Version:    0.6.2
 */