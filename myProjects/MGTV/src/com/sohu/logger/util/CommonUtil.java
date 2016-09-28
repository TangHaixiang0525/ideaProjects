package com.sohu.logger.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CommonUtil
{
  private static String capitalize(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0))
      paramString = "";
    char c;
    do
    {
      return paramString;
      c = paramString.charAt(0);
    }
    while (Character.isUpperCase(c));
    return Character.toUpperCase(c) + paramString.substring(1);
  }

  public static boolean checkPermissions(Context paramContext, String paramString)
  {
    return paramContext.getPackageManager().checkPermission(paramString, paramContext.getPackageName()) == 0;
  }

  public static boolean checkPhoneState(Context paramContext)
  {
    return paramContext.getPackageManager().checkPermission("android.permission.READ_PHONE_STATE", paramContext.getPackageName()) == 0;
  }

  public static boolean currentNoteworkTypeIsWIFI(Context paramContext)
  {
    return ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo().getType() == 1;
  }

  public static String getActivityName(Context paramContext)
  {
    if (paramContext == null)
      return "";
    ActivityManager localActivityManager = (ActivityManager)paramContext.getSystemService("activity");
    if (checkPermissions(paramContext, "android.permission.GET_TASKS"))
      return ((ActivityManager.RunningTaskInfo)localActivityManager.getRunningTasks(1).get(0)).topActivity.getShortClassName();
    return "";
  }

  public static String getCurVersion(Context paramContext)
  {
    String str = "";
    try
    {
      str = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionName;
      if ((str == null) || (str.length() <= 0))
        str = "";
      return str;
    }
    catch (Exception localException)
    {
    }
    return str;
  }

  public static String getDeviceID(Context paramContext)
  {
    String str;
    if (paramContext == null)
      str = "";
    do
    {
      return str;
      if (!checkPermissions(paramContext, "android.permission.READ_PHONE_STATE"))
        break;
      str = "";
      if (checkPhoneState(paramContext))
        str = ((TelephonyManager)paramContext.getSystemService("phone")).getDeviceId();
    }
    while (str != null);
    return "";
    return "";
  }

  public static String getDeviceName()
  {
    String str1 = Build.MANUFACTURER;
    String str2 = Build.MODEL;
    if (str2.startsWith(str1))
      return capitalize(str2);
    return capitalize(str1) + " " + str2;
  }

  public static String getNetworkType(Context paramContext)
  {
    int i = ((TelephonyManager)paramContext.getSystemService("phone")).getNetworkType();
    String str = "UNKNOWN";
    if (i == 4)
      str = "CDMA";
    if (i == 2)
      str = "EDGE";
    if (i == 5)
      str = "EVDO_0";
    if (i == 6)
      str = "EVDO_A";
    if (i == 1)
      str = "GPRS";
    if (i == 8)
      str = "HSDPA";
    if (i == 10)
      str = "HSPA";
    if (i == 9)
      str = "HSUPA";
    if (i == 3)
      str = "UMTS";
    if (i == 0)
      str = "UNKNOWN";
    if (i == 7)
      str = "1xRTT";
    if (i == 11)
      str = "iDen";
    if (i == 12)
      str = "EVDO_B";
    if (i == 13)
      str = "LTE";
    if (i == 14)
      str = "eHRPD";
    if (i == 15)
      str = "HSPA+";
    return str;
  }

  public static String getNetworkTypeWIFI2G3G(Context paramContext)
  {
    ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
    String str1 = localConnectivityManager.getActiveNetworkInfo().getTypeName().toLowerCase();
    if (str1.equals("wifi"))
      return str1;
    String str2 = localConnectivityManager.getNetworkInfo(0).getExtraInfo();
    System.out.println(str2);
    return str2;
  }

  public static String getOsVersion(Context paramContext)
  {
    if (checkPhoneState(paramContext))
      return Build.VERSION.RELEASE;
    return null;
  }

  public static String getPackageName(Context paramContext)
  {
    ActivityManager localActivityManager = (ActivityManager)paramContext.getSystemService("activity");
    if (checkPermissions(paramContext, "android.permission.GET_TASKS"))
      return ((ActivityManager.RunningTaskInfo)localActivityManager.getRunningTasks(1).get(0)).topActivity.getPackageName();
    return null;
  }

  public static String getSdkVersion(Context paramContext)
  {
    if (!checkPhoneState(paramContext))
      return Build.VERSION.RELEASE;
    return null;
  }

  public static String getTime()
  {
    Date localDate = new Date();
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(localDate);
  }

  public static String getVersion(Context paramContext)
  {
    String str = "";
    if (paramContext == null)
      return "";
    try
    {
      str = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionName;
      if ((str == null) || (str.length() <= 0))
        return "";
    }
    catch (Exception localException)
    {
    }
    return str;
  }

  public static boolean isHaveGravity(Context paramContext)
  {
    return (SensorManager)paramContext.getSystemService("sensor") != null;
  }

  public static boolean isNetworkAvailable(Context paramContext)
  {
    if (checkPermissions(paramContext, "android.permission.INTERNET"))
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
      return (localNetworkInfo != null) && (localNetworkInfo.isAvailable());
    }
    return false;
  }

  public static boolean isNetworkTypeWifi(Context paramContext)
  {
    if (checkPermissions(paramContext, "android.permission.INTERNET"))
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
      return (localNetworkInfo != null) && (localNetworkInfo.isAvailable()) && (localNetworkInfo.getTypeName().equals("WIFI"));
    }
    return false;
  }

  public static boolean isWiFiActive(Context paramContext)
  {
    boolean bool1 = checkPermissions(paramContext, "android.permission.ACCESS_WIFI_STATE");
    boolean bool2 = false;
    NetworkInfo[] arrayOfNetworkInfo;
    if (bool1)
    {
      ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getApplicationContext().getSystemService("connectivity");
      bool2 = false;
      if (localConnectivityManager != null)
      {
        arrayOfNetworkInfo = localConnectivityManager.getAllNetworkInfo();
        bool2 = false;
        if (arrayOfNetworkInfo == null);
      }
    }
    for (int i = 0; ; i++)
    {
      int j = arrayOfNetworkInfo.length;
      bool2 = false;
      if (i < j)
      {
        if ((arrayOfNetworkInfo[i].getTypeName().equals("WIFI")) && (arrayOfNetworkInfo[i].isConnected()))
          bool2 = true;
      }
      else
        return bool2;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.logger.util.CommonUtil
 * JD-Core Version:    0.6.2
 */