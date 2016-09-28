package com.miaozhen.mzmonitor;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Locale;

public class MZDeviceInfo
{
  public static final String NetworkType_Mobile = "2";
  public static final String NetworkType_NotActive = "0";
  public static final String NetworkType_WIFI = "1";
  static boolean OpenUDIDSyncComplete = false;
  private static MZDeviceInfo deviceInfo = null;
  private Context context;

  protected MZDeviceInfo(Context paramContext)
  {
    this.context = paramContext;
  }

  public static MZDeviceInfo getDeviceInfo(Context paramContext)
  {
    try
    {
      if (deviceInfo == null)
        deviceInfo = new MZDeviceInfo(paramContext.getApplicationContext());
      MZDeviceInfo localMZDeviceInfo = deviceInfo;
      return localMZDeviceInfo;
    }
    finally
    {
    }
  }

  public String getAndoirdID()
  {
    return Settings.Secure.getString(this.context.getContentResolver(), "android_id");
  }

  public String getAppName()
  {
    try
    {
      ApplicationInfo localApplicationInfo = this.context.getPackageManager().getApplicationInfo(this.context.getPackageName(), 128);
      if (localApplicationInfo != null)
      {
        if (localApplicationInfo.labelRes != 0)
          return this.context.getResources().getString(localApplicationInfo.labelRes);
        if (localApplicationInfo.nonLocalizedLabel == null)
          return null;
        String str = localApplicationInfo.nonLocalizedLabel.toString();
        return str;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }

  public String getCarrier(Context paramContext)
  {
    TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
    if (localTelephonyManager != null)
    {
      String str = localTelephonyManager.getNetworkOperatorName();
      if ((localTelephonyManager.getNetworkOperatorName() != null) && (!localTelephonyManager.getNetworkOperatorName().equals("")))
        return str;
    }
    return null;
  }

  public String getCurrentNetworkType()
  {
    try
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)this.context.getSystemService("connectivity")).getActiveNetworkInfo();
      if (localNetworkInfo != null)
      {
        if (localNetworkInfo.getType() == 1)
          return "1";
      }
      else
        return "0";
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return "0";
    }
    return "2";
  }

  public String getDeviceModel()
  {
    return Build.MODEL;
  }

  public String getIMEI()
  {
    return ((TelephonyManager)this.context.getSystemService("phone")).getDeviceId();
  }

  public String getIP(Context paramContext)
  {
    try
    {
      Enumeration localEnumeration = ((NetworkInterface)NetworkInterface.getNetworkInterfaces().nextElement()).getInetAddresses();
      while (localEnumeration.hasMoreElements())
      {
        InetAddress localInetAddress = (InetAddress)localEnumeration.nextElement();
        if (!localInetAddress.isLinkLocalAddress())
        {
          String str = localInetAddress.getHostAddress();
          return str;
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public String getLocale()
  {
    Locale localLocale = Locale.getDefault();
    return localLocale.getLanguage() + "_" + localLocale.getCountry();
  }

  public String getMacAddress()
  {
    WifiInfo localWifiInfo = ((WifiManager)this.context.getSystemService("wifi")).getConnectionInfo();
    if (localWifiInfo != null)
      return localWifiInfo.getMacAddress();
    return "";
  }

  public String getODIN()
  {
    try
    {
      String str2 = Settings.System.getString(this.context.getContentResolver(), "android_id");
      localObject = str2;
      return MZUtility.SHA1((String)localObject);
    }
    catch (Exception localException1)
    {
      try
      {
        String str1 = Settings.System.getString(this.context.getContentResolver(), "android_id");
        Object localObject = str1;
      }
      catch (Exception localException2)
      {
      }
    }
    return null;
  }

  public String getOSVersion()
  {
    return Build.VERSION.RELEASE;
  }

  protected String getOpenUDID()
  {
    if (!OpenUDIDSyncComplete)
      o.a(this.context);
    if (o.b())
      return o.a();
    return "";
  }

  public String getPackageName()
  {
    String str = "";
    try
    {
      PackageManager localPackageManager = this.context.getPackageManager();
      if (localPackageManager != null)
      {
        PackageInfo localPackageInfo = localPackageManager.getPackageInfo(this.context.getPackageName(), 0);
        if (localPackageInfo != null)
          str = localPackageInfo.packageName;
      }
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return str;
  }

  public String getResolution()
  {
    WindowManager localWindowManager = (WindowManager)this.context.getSystemService("window");
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
    return localDisplayMetrics.widthPixels + "x" + localDisplayMetrics.heightPixels;
  }

  public String getWifiSSID()
  {
    try
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)this.context.getSystemService("connectivity")).getActiveNetworkInfo();
      if ((localNetworkInfo != null) && (localNetworkInfo.getType() == 1))
      {
        WifiInfo localWifiInfo = ((WifiManager)this.context.getSystemService("wifi")).getConnectionInfo();
        if (localWifiInfo == null)
          return "NULL";
        String str = localWifiInfo.getSSID();
        return str;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public boolean getWifiState()
  {
    return ((ConnectivityManager)this.context.getSystemService("connectivity")).getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.miaozhen.mzmonitor.MZDeviceInfo
 * JD-Core Version:    0.6.2
 */