package com.sohu.logger.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import java.util.HashMap;
import java.util.Map;

public class SystemUtils
{
  public static String FIRST_START_APP_KEY = "first_start_app_key";

  public static boolean checkPermissions(Context paramContext, String paramString)
  {
    return paramContext.getPackageManager().checkPermission(paramString, paramContext.getPackageName()) == 0;
  }

  public static int dip2px(Context paramContext, float paramFloat)
  {
    return (int)(0.5F + paramFloat * paramContext.getResources().getDisplayMetrics().density);
  }

  public static boolean firstStartApp(Context paramContext)
  {
    return PrefUtil.getBoolean(paramContext, FIRST_START_APP_KEY, true);
  }

  public static String getAppVersion(Context paramContext)
  {
    try
    {
      PackageInfo localPackageInfo = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0);
      return localPackageInfo.versionName;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
    }
    return "";
  }

  public static String getAvailMemory(Context paramContext)
  {
    ActivityManager localActivityManager = (ActivityManager)paramContext.getSystemService("activity");
    ActivityManager.MemoryInfo localMemoryInfo = new ActivityManager.MemoryInfo();
    localActivityManager.getMemoryInfo(localMemoryInfo);
    return Formatter.formatFileSize(paramContext, localMemoryInfo.availMem);
  }

  public static String getBuildManufacturer()
  {
    return Build.MANUFACTURER;
  }

  public static String getBuildModel()
  {
    return Build.MODEL;
  }

  public static String getBuildVersionRelease()
  {
    return Build.VERSION.RELEASE;
  }

  public static int getBuildVersionSDK()
  {
    return Build.VERSION.SDK_INT;
  }

  public static float getDensity(Context paramContext)
  {
    new DisplayMetrics();
    return paramContext.getApplicationContext().getResources().getDisplayMetrics().density;
  }

  public static int getDensityDpi(Context paramContext)
  {
    new DisplayMetrics();
    return paramContext.getApplicationContext().getResources().getDisplayMetrics().densityDpi;
  }

  public static int getDeviceHtmlWidth(Context paramContext)
  {
    return (int)(getScreenWidth(paramContext) / paramContext.getResources().getDisplayMetrics().density);
  }

  public static String getDeviceId(Context paramContext)
  {
    return ((TelephonyManager)paramContext.getSystemService("phone")).getDeviceId();
  }

  public static int getDimension(Context paramContext, int paramInt)
  {
    return (int)paramContext.getResources().getDimension(paramInt);
  }

  public static String getProjectModel()
  {
    String str = Build.MODEL;
    if (str != null)
      str = str.replace(" ", ",");
    return str;
  }

  public static int getScreenBrightness(Activity paramActivity)
  {
    ContentResolver localContentResolver = paramActivity.getContentResolver();
    try
    {
      int i = Settings.System.getInt(localContentResolver, "screen_brightness");
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }

  public static int getScreenHeight(Context paramContext)
  {
    new DisplayMetrics();
    return paramContext.getApplicationContext().getResources().getDisplayMetrics().heightPixels;
  }

  public static int getScreenWidth(Context paramContext)
  {
    new DisplayMetrics();
    return paramContext.getApplicationContext().getResources().getDisplayMetrics().widthPixels;
  }

  public static String getSubscriberId(Context paramContext)
  {
    return null;
  }

  public static String getWidthXHeight(Context paramContext)
  {
    HashMap localHashMap = getWidth_Height(paramContext);
    return localHashMap.get("w") + "x" + localHashMap.get("h");
  }

  public static HashMap<String, Integer> getWidth_Height(Context paramContext)
  {
    new DisplayMetrics();
    DisplayMetrics localDisplayMetrics = paramContext.getApplicationContext().getResources().getDisplayMetrics();
    int i = localDisplayMetrics.widthPixels;
    int j = localDisplayMetrics.heightPixels;
    HashMap localHashMap = new HashMap();
    localHashMap.put("w", Integer.valueOf(i));
    localHashMap.put("h", Integer.valueOf(j));
    return localHashMap;
  }

  public static boolean isHoneycomb()
  {
    return Build.VERSION.SDK_INT >= 11;
  }

  public static boolean isHoneycombTablet(Context paramContext)
  {
    return (isHoneycomb()) && (isTablet(paramContext));
  }

  public static boolean isSDCardMounted()
  {
    return "mounted".equals(Environment.getExternalStorageState());
  }

  public static boolean isSDCardMountedReadOnly()
  {
    return "mounted_ro".equals(Environment.getExternalStorageState());
  }

  @SuppressLint({"NewApi"})
  public static boolean isSupportH265()
  {
    return false;
  }

  public static boolean isTablet(Context paramContext)
  {
    return (0xF & paramContext.getResources().getConfiguration().screenLayout) >= 3;
  }

  public static void notifyNotFirstStartApp(Context paramContext)
  {
    PrefUtil.putBoolean(paramContext, FIRST_START_APP_KEY, false);
  }

  public static int px2dip(Context paramContext, float paramFloat)
  {
    return (int)(0.5F + paramFloat / paramContext.getResources().getDisplayMetrics().density);
  }

  public static int px2sp(Context paramContext, float paramFloat)
  {
    return (int)(0.5F + paramFloat / paramContext.getResources().getDisplayMetrics().scaledDensity);
  }

  public static int sp2px(Context paramContext, float paramFloat)
  {
    return (int)(0.5F + paramFloat * paramContext.getResources().getDisplayMetrics().scaledDensity);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.logger.util.SystemUtils
 * JD-Core Version:    0.6.2
 */