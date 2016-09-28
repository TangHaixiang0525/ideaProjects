package com.sohu.mma.tracking.c;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class d
{
  public static String a()
  {
    return Build.VERSION.RELEASE;
  }

  public static String a(Context paramContext)
  {
    WindowManager localWindowManager = (WindowManager)paramContext.getSystemService("window");
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
    return localDisplayMetrics.widthPixels + "x" + localDisplayMetrics.heightPixels;
  }

  public static String b()
  {
    return Build.MODEL;
  }

  public static String b(Context paramContext)
  {
    return ((TelephonyManager)paramContext.getSystemService("phone")).getDeviceId();
  }

  public static boolean c(Context paramContext)
  {
    return ((ConnectivityManager)paramContext.getSystemService("connectivity")).getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED;
  }

  public static boolean d(Context paramContext)
  {
    ConnectivityManager localConnectivityManager;
    if (paramContext != null)
    {
      localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
      if (localConnectivityManager != null)
        break label20;
    }
    while (true)
    {
      return false;
      label20: NetworkInfo[] arrayOfNetworkInfo = localConnectivityManager.getAllNetworkInfo();
      if (arrayOfNetworkInfo != null)
        for (int i = 0; i < arrayOfNetworkInfo.length; i++)
          if (arrayOfNetworkInfo[i].getState() == NetworkInfo.State.CONNECTED)
            return true;
    }
  }

  public static String e(Context paramContext)
  {
    PackageManager localPackageManager = paramContext.getPackageManager();
    try
    {
      String str = localPackageManager.getApplicationInfo(paramContext.getPackageName(), 128).loadLabel(localPackageManager).toString();
      return str;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      localNameNotFoundException.printStackTrace();
    }
    return "";
  }

  public static String f(Context paramContext)
  {
    return paramContext.getPackageName();
  }

  public static String g(Context paramContext)
  {
    WifiManager localWifiManager = (WifiManager)paramContext.getSystemService("wifi");
    if (localWifiManager != null)
    {
      WifiInfo localWifiInfo = localWifiManager.getConnectionInfo();
      if (localWifiInfo != null)
        return localWifiInfo.getMacAddress();
    }
    return "";
  }

  public static Map<String, String> h(Context paramContext)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("LBS", e.a(paramContext).a());
    String str1 = g(paramContext);
    if (str1 != null)
      localHashMap.put("MAC", str1.replaceAll(":", "").toUpperCase());
    localHashMap.put("ANDROIDID", i(paramContext));
    localHashMap.put("OSVS", a());
    localHashMap.put("TERM", b());
    if (c(paramContext));
    for (String str2 = "1"; ; str2 = "0")
    {
      localHashMap.put("WIFI", str2);
      localHashMap.put("ANAME", e(paramContext));
      localHashMap.put("AKEY", f(paramContext));
      localHashMap.put("OSVS", a());
      localHashMap.put("OS", "0");
      localHashMap.put("SCWH", a(paramContext));
      localHashMap.put("IMEI", b(paramContext));
      localHashMap.put("SDKVS", "1.3.0");
      return localHashMap;
    }
  }

  public static String i(Context paramContext)
  {
    String str = j.a(paramContext, "cn.com.mma.mobile.tracking.other", "android_id");
    if ((str == null) || (str.equals("")) || (str.equals("null")))
      str = k(paramContext);
    return str;
  }

  public static boolean j(Context paramContext)
  {
    String str = Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
    boolean bool1 = false;
    if (str != null)
    {
      boolean bool2 = str.equals("9774d56d682e549c");
      bool1 = false;
      if (bool2)
        bool1 = true;
    }
    return bool1;
  }

  private static String k(Context paramContext)
  {
    String str1 = Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
    if ((str1 == null) || (str1.equals("9774d56d682e549c")) || (str1.length() < 15))
      str1 = new BigInteger(64, new SecureRandom()).toString(16);
    String str2 = a.b(str1);
    j.a(paramContext, "cn.com.mma.mobile.tracking.other", "android_id", str2);
    return str2;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.mma.tracking.c.d
 * JD-Core Version:    0.6.2
 */