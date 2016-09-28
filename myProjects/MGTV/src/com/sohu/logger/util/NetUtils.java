package com.sohu.logger.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import com.sohu.logger.log.OutputLog;
import com.sohu.logger.model.UserDataLogger;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;
import org.apache.http.params.HttpParams;

public class NetUtils
{
  public static final String G2 = "2G";
  public static final String G3 = "3G";
  public static final int NETWORK_2G = -2;
  public static final int NETWORK_3G = 2;
  public static int NETWORK_CURRENT = 0;
  public static final int NETWORK_ETHERNET = 3;
  public static final int NETWORK_MOBILE = 5;
  public static final int NETWORK_NONE = 0;
  public static final int NETWORK_OFF = 4;
  public static final int NETWORK_UNKNOWN = -1;
  public static final int NETWORK_WIFI = 1;
  public static final String NET_TYPE_ETHERNET = "Ethernet";
  public static final String NET_TYPE_WIFI = "WiFi";
  public static final String NET_UNAVAILABLE = "None";
  public static final String TYPE_MOBILE = "Mobile";
  public static final String UNIWAP = "uniwap";
  public static final String UNKNOWN = "Unknown";
  public static final String WAP_3G = "3gwap";
  public static int network_current = -1;

  public static boolean Post(Context paramContext, int paramInt, String paramString)
  {
    for (int i = 0; ; i++)
    {
      boolean bool = false;
      if (i < paramInt)
      {
        if (onSend(paramContext, paramString))
          bool = true;
      }
      else
        return bool;
    }
  }

  public static boolean checkNetwork(Context paramContext)
  {
    try
    {
      ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
      if (localConnectivityManager.getActiveNetworkInfo() != null)
      {
        boolean bool = localConnectivityManager.getActiveNetworkInfo().isAvailable();
        return bool;
      }
    }
    catch (Exception localException)
    {
      return false;
    }
    return false;
  }

  static void extractServerDate(Context paramContext, HttpResponse paramHttpResponse)
  {
    HashMap localHashMap = new HashMap();
    Header[] arrayOfHeader = paramHttpResponse.getAllHeaders();
    for (int i = 0; i < arrayOfHeader.length; i++)
      localHashMap.put(arrayOfHeader[i].getName(), arrayOfHeader[i].getValue());
    String str = (String)localHashMap.get("Date");
    if (str != null);
    try
    {
      TimerUtil.setServerDateOffset(paramContext, DateUtils.parseDate(str).getTime());
      return;
    }
    catch (DateParseException localDateParseException)
    {
    }
  }

  public static String getHost(String paramString)
  {
    if ((paramString == null) || ("".equals(paramString)))
      return "";
    try
    {
      String str1 = paramString.replace("http://", "");
      String str2 = str1.substring(0, str1.indexOf("/"));
      return str2;
    }
    catch (Exception localException)
    {
    }
    return "";
  }

  public static String getHostbyWAP(Context paramContext)
  {
    if (paramContext == null);
    while (isWifi(paramContext))
      return null;
    while (true)
    {
      try
      {
        ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
        if ((localConnectivityManager == null) || (localConnectivityManager.getNetworkInfo(0) == null))
          break label120;
        Uri localUri = Uri.parse("content://telephony/carriers/preferapn");
        Cursor localCursor = paramContext.getContentResolver().query(localUri, null, null, null, null);
        if ((localCursor == null) || (!localCursor.moveToFirst()))
          break label120;
        str = localCursor.getString(localCursor.getColumnIndex("proxy"));
        if ((str != null) && (str.trim().length() > 0))
        {
          localCursor.close();
          return str;
        }
      }
      catch (Exception localException)
      {
        return null;
      }
      String str = null;
      continue;
      label120: str = null;
    }
  }

  public static String getLocalIpAddress()
  {
    try
    {
      InetAddress localInetAddress;
      do
      {
        Enumeration localEnumeration1 = NetworkInterface.getNetworkInterfaces();
        Enumeration localEnumeration2;
        while (!localEnumeration2.hasMoreElements())
        {
          if (!localEnumeration1.hasMoreElements())
            break;
          localEnumeration2 = ((NetworkInterface)localEnumeration1.nextElement()).getInetAddresses();
        }
        localInetAddress = (InetAddress)localEnumeration2.nextElement();
      }
      while (localInetAddress.isLoopbackAddress());
      String str = localInetAddress.getHostAddress().toString();
      return str;
    }
    catch (SocketException localSocketException)
    {
    }
    return null;
  }

  public static String getLocalMacAddress(Context paramContext)
  {
    WifiManager localWifiManager = (WifiManager)paramContext.getSystemService("wifi");
    if (localWifiManager != null);
    for (WifiInfo localWifiInfo = localWifiManager.getConnectionInfo(); ; localWifiInfo = null)
    {
      if (localWifiInfo != null)
        return localWifiInfo.getMacAddress();
      return "";
    }
  }

  public static String getNetworkStringByType(int paramInt)
  {
    switch (paramInt)
    {
    case -1:
    case 4:
    default:
      return "Unknown";
    case 0:
      return "None";
    case 1:
      return "WiFi";
    case 5:
      return "Mobile";
    case -2:
      return "2G";
    case 2:
      return "3G";
    case 3:
    }
    return "Ethernet";
  }

  public static int getNetworkType(Context paramContext)
  {
    NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
    int i;
    if (localNetworkInfo.getState() != NetworkInfo.State.CONNECTED)
      i = 4;
    while (true)
    {
      NETWORK_CURRENT = i;
      return i;
      if ((localNetworkInfo != null) && (localNetworkInfo.isAvailable()))
      {
        if (localNetworkInfo.getTypeName().toLowerCase().equals("wifi"))
        {
          i = 1;
        }
        else if (localNetworkInfo.getTypeName().toLowerCase().equals("mobile"))
        {
          int j = ((TelephonyManager)paramContext.getSystemService("phone")).getNetworkType();
          if ((j != 0) || ((j == 1) || (j == 2)))
            i = -2;
          else
            i = 2;
        }
        else if (localNetworkInfo.getType() == 9)
        {
          i = 3;
        }
      }
      else
      {
        i = 0;
        continue;
        i = -1;
      }
    }
  }

  public static String getWebType()
  {
    switch (getNetworkType(UserDataLogger.getInstance().getContext()))
    {
    default:
      return "0";
    case 0:
      return "0";
    case 1:
      return "1";
    case 2:
      return "2";
    case 3:
      return "3";
    case 4:
    }
    return "4";
  }

  public static boolean isCMWAPMobileNet(Context paramContext)
  {
    if (paramContext == null)
      return false;
    if (isWifi(paramContext))
      return false;
    ConnectivityManager localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
    if (localConnectivityManager != null)
    {
      NetworkInfo localNetworkInfo = localConnectivityManager.getNetworkInfo(0);
      if (localNetworkInfo != null)
      {
        String str = localNetworkInfo.getExtraInfo();
        if (str != null)
          return str.toLowerCase().contains("wap");
        return false;
      }
      return false;
    }
    return false;
  }

  public static boolean isRespondCodeStandForSuccess(int paramInt)
  {
    return (paramInt >= 200) && (paramInt < 400);
  }

  public static boolean isWifi(Context paramContext)
  {
    try
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
      if (localNetworkInfo != null)
      {
        int i = localNetworkInfo.getType();
        if (i == 1)
          return true;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  private static boolean onSend(Context paramContext, String paramString)
  {
    OutputLog.d("SohuLoggerAgent", "send url:  " + paramString);
    int i = LoggerHttpUtils.doGet(paramContext, paramString, -1, false);
    OutputLog.d("SohuLoggerAgent", "send url result :  " + i);
    boolean bool1;
    if (i != 0)
    {
      boolean bool2 = isRespondCodeStandForSuccess(i);
      bool1 = false;
      if (!bool2);
    }
    else
    {
      bool1 = true;
    }
    return bool1;
  }

  public static String parseURL(String paramString1, String paramString2)
  {
    if (paramString2 != null)
    {
      if ("".equalsIgnoreCase(paramString2))
        return "";
      int i = paramString1.indexOf("?");
      if (i + 1 <= paramString1.length())
      {
        String[] arrayOfString1 = paramString1.substring(i + 1, paramString1.length()).split("&");
        if (arrayOfString1 != null)
          for (int j = 0; j < arrayOfString1.length; j++)
            if (arrayOfString1[j].contains(paramString2))
            {
              String[] arrayOfString2 = arrayOfString1[j].split("=");
              if (arrayOfString2.length > 1)
                return arrayOfString2[1];
            }
      }
    }
    return "";
  }

  public static void setHttpClientHostProxy(Context paramContext, HttpClient paramHttpClient)
  {
    if (paramHttpClient == null)
      return;
    String str = getHostbyWAP(paramContext);
    if (str != null)
    {
      HttpHost localHttpHost = new HttpHost(str, 80, "http");
      paramHttpClient.getParams().setParameter("http.route.default-proxy", localHttpHost);
      return;
    }
    paramHttpClient.getParams().removeParameter("http.route.default-proxy");
  }

  public static void setHttpClientHostProxy(HttpClient paramHttpClient, Context paramContext)
  {
  }

  public static void setHttpProxy(String paramString1, String paramString2)
  {
    Properties localProperties = System.getProperties();
    localProperties.setProperty("http.proxyHost", paramString1);
    localProperties.setProperty("http.proxyPort", paramString2);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.logger.util.NetUtils
 * JD-Core Version:    0.6.2
 */