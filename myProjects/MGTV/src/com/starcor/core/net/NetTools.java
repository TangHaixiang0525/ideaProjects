package com.starcor.core.net;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetTools
{
  public static final int NETWORKTYPE_2G = 2;
  public static final int NETWORKTYPE_3G = 3;
  public static final int NETWORKTYPE_INVALID = 0;
  public static final int NETWORKTYPE_WAP = 1;
  public static final int NETWORKTYPE_WIFI = 4;
  private static final String TAG = "NetTools";

  private static String MacString(byte[] paramArrayOfByte)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int i = paramArrayOfByte.length;
    for (int j = 0; j < i; j++)
    {
      byte b = paramArrayOfByte[j];
      if (localStringBuilder.length() > 0)
        localStringBuilder.append("-");
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Byte.valueOf(b);
      localStringBuilder.append(String.format("%02X", arrayOfObject));
    }
    return localStringBuilder.toString();
  }

  public static String getGatewayIp()
  {
    try
    {
      Process localProcess = Runtime.getRuntime().exec("ip route");
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
      localProcess.waitFor();
      Pattern localPattern = Pattern.compile("default\\s+via\\s+(\\d+(?:\\.\\d+){3})\\s+dev.+", 2);
      Matcher localMatcher;
      do
      {
        if (!localBufferedReader.ready())
          break;
        String str1 = localBufferedReader.readLine();
        Logger.i("NetTools", "getGatewayIp() ip route:" + str1);
        localMatcher = localPattern.matcher(str1);
      }
      while (!localMatcher.matches());
      String str2 = localMatcher.group(1).toLowerCase(Locale.CHINA);
      Logger.d("NetTools", "getGatewayIp() m.group(1). ip:" + str2);
      return str2;
    }
    catch (IOException localIOException)
    {
      Logger.d("NetTools", "getGatewayIp() Exception: IOException.");
      localIOException.printStackTrace();
      return "";
    }
    catch (InterruptedException localInterruptedException)
    {
      while (true)
      {
        Logger.d("NetTools", "getGatewayIp() Exception: InterruptedException.");
        localInterruptedException.printStackTrace();
      }
    }
  }

  public static String getLANMac()
  {
    String str = getMacLevel9("eth[0-9]+");
    if (str.equals(""))
      str = getMacNetcfg("eth[0-9]+");
    if (!TextUtils.isEmpty(str))
      str.toUpperCase(Locale.CHINA);
    if (str.length() != 17)
    {
      Logger.e("NetTools", "getLANMac mac:" + str);
      return str;
    }
    Logger.i("NetTools", "getLANMac mac:" + str);
    return str;
  }

  private static String getMacLevel9(String paramString)
  {
    try
    {
      Method localMethod = NetworkInterface.class.getMethod("getHardwareAddress", new Class[0]);
      Enumeration localEnumeration = NetworkInterface.getNetworkInterfaces();
      String str2;
      boolean bool;
      do
      {
        NetworkInterface localNetworkInterface;
        byte[] arrayOfByte;
        do
        {
          if (!localEnumeration.hasMoreElements())
            break;
          localNetworkInterface = (NetworkInterface)localEnumeration.nextElement();
          localMethod.invoke(localNetworkInterface, new Object[0]);
          arrayOfByte = (byte[])localMethod.invoke(localNetworkInterface, new Object[0]);
        }
        while (arrayOfByte == null);
        String str1 = localNetworkInterface.getName().toLowerCase(Locale.CHINA);
        str2 = MacString(arrayOfByte);
        Logger.d("NetTools.getMacLevel9", str1 + " " + str2);
        bool = str1.matches(paramString);
      }
      while (!bool);
      return str2;
    }
    catch (SocketException localSocketException)
    {
      Logger.d("NetTools.getMacLevel9", "Exception: SocketException.");
      localSocketException.printStackTrace();
      return "";
    }
    catch (SecurityException localSecurityException)
    {
      while (true)
      {
        Logger.d("NetTools.getMacLevel9", "Exception: SecurityException.");
        localSecurityException.printStackTrace();
      }
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      while (true)
      {
        Logger.d("NetTools.getMacLevel9", "Exception: NoSuchMethodException.");
        localNoSuchMethodException.printStackTrace();
      }
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      while (true)
      {
        Logger.d("NetTools.getMacLevel9", "Exception: IllegalArgumentException.");
        localIllegalArgumentException.printStackTrace();
      }
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      while (true)
      {
        Logger.d("NetTools.getMacLevel9", "Exception: IllegalAccessException.");
        localIllegalAccessException.printStackTrace();
      }
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      while (true)
      {
        Logger.d("NetTools.getMacLevel9", "Exception: InvocationTargetException.");
        localInvocationTargetException.printStackTrace();
      }
    }
  }

  private static String getMacNetcfg(String paramString)
  {
    try
    {
      Process localProcess = Runtime.getRuntime().exec("netcfg");
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
      localProcess.waitFor();
      Pattern localPattern = Pattern.compile("^([a-z0-9]+)\\s+(UP|DOWN)\\s+([0-9./]+)\\s+.+\\s+([0-9a-f:]+)$", 2);
      String str4;
      boolean bool;
      do
      {
        Matcher localMatcher;
        do
        {
          if (!localBufferedReader.ready())
            break;
          localMatcher = localPattern.matcher(localBufferedReader.readLine());
        }
        while (!localMatcher.matches());
        String str1 = localMatcher.group(1).toLowerCase(Locale.CHINA);
        String str2 = localMatcher.group(2);
        String str3 = localMatcher.group(3);
        str4 = localMatcher.group(4).toUpperCase(Locale.CHINA).replace(':', '-');
        Logger.d("NetTools.getMacNetcfg", "match success:" + str1 + " " + str2 + " " + str3 + " " + str4);
        bool = str1.matches(paramString);
      }
      while (!bool);
      return str4;
    }
    catch (IOException localIOException)
    {
      Logger.d("NetTools.getMacNetcfg", "Exception: IOException.");
      localIOException.printStackTrace();
      return "";
    }
    catch (InterruptedException localInterruptedException)
    {
      while (true)
      {
        Logger.d("NetTools.getMacNetcfg", "Exception: InterruptedException.");
        localInterruptedException.printStackTrace();
      }
    }
  }

  public static String getNetIP(String paramString)
  {
    Object localObject = null;
    InputStream localInputStream;
    StringBuffer localStringBuffer;
    try
    {
      localInputStream = new URL(paramString).openConnection().getInputStream();
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localInputStream, "GB2312"));
      localStringBuffer = new StringBuffer();
      while (true)
      {
        String str1 = localBufferedReader.readLine();
        if (str1 == null)
          break;
        localStringBuffer.append(str1);
      }
    }
    catch (IOException localIOException)
    {
      Logger.i("NetTools", "getNetIP(), Exception: IOException.");
      localIOException.printStackTrace();
      return null;
    }
    localInputStream.close();
    int i = localStringBuffer.indexOf("input name");
    String str2 = localStringBuffer.substring(i, i + localStringBuffer.substring(i).indexOf("/>"));
    Matcher localMatcher = Pattern.compile("(\\d{1,3})[.](\\d{1,3})[.](\\d{1,3})[.](\\d{1,3})", 2).matcher(str2);
    while (localMatcher.find())
    {
      String str3 = localMatcher.group(0);
      localObject = str3;
    }
    return localObject;
  }

  public static String getNetIpAddr()
  {
    try
    {
      Process localProcess = Runtime.getRuntime().exec("netcfg");
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
      localProcess.waitFor();
      Pattern localPattern = Pattern.compile("^([a-z0-9]+)\\s+(UP|DOWN)\\s+([0-9./]+)\\s+.+\\s+([0-9a-f:]+)$", 2);
      String str1;
      String str2;
      String str3;
      String str4;
      do
      {
        Matcher localMatcher;
        do
        {
          if (!localBufferedReader.ready())
            break;
          localMatcher = localPattern.matcher(localBufferedReader.readLine());
        }
        while (!localMatcher.matches());
        str1 = localMatcher.group(1).toLowerCase(Locale.CHINA);
        str2 = localMatcher.group(2);
        str3 = localMatcher.group(3);
        str4 = localMatcher.group(4).toUpperCase(Locale.CHINA).replace(':', '-');
        Logger.i("NetTools", "getNetIpAddr1111(), match success:" + str1 + " " + str2 + " " + str3 + " " + str4);
      }
      while (((!str1.matches("eth[0-9]+")) || (!str2.matches("UP")) || (str3.matches("0.0.0.0"))) && ((!str1.matches("wlan[0-9]+")) || (!str2.matches("UP")) || (str3.matches("0.0.0.0"))));
      Logger.i("NetTools", "getNetIpAddr, match success:" + str1 + " " + str2 + " " + str3 + " " + str4);
      String str5 = str3.split("/")[0];
      return str5;
    }
    catch (IOException localIOException)
    {
      Logger.i("NetTools", "getNetIpAddr(), Exception: IOException.");
      localIOException.printStackTrace();
      return "";
    }
    catch (InterruptedException localInterruptedException)
    {
      while (true)
      {
        Logger.i("NetTools", "getNetIpAddr(), Exception: InterruptedException.");
        localInterruptedException.printStackTrace();
      }
    }
  }

  public static String getNetWorkType(Context paramContext)
  {
    String str1 = "";
    NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
    if ((localNetworkInfo != null) && (localNetworkInfo.isConnected()))
    {
      String str2 = localNetworkInfo.getTypeName();
      if (str2.equalsIgnoreCase("WIFI"))
        str1 = "wifi";
      do
      {
        return str1;
        if (str2.equalsIgnoreCase("MOBILE"))
        {
          if (TextUtils.isEmpty(Proxy.getDefaultHost()))
          {
            if (isFastMobileNetwork(paramContext))
              return "3G";
            return "2G";
          }
          return "wap";
        }
      }
      while (!str2.equalsIgnoreCase("ETH"));
      return "eth";
    }
    return "";
  }

  public static boolean getPortIsWork()
  {
    try
    {
      Process localProcess = Runtime.getRuntime().exec("netcfg");
      BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
      localProcess.waitFor();
      Pattern localPattern = Pattern.compile("^([a-z0-9]+)\\s+(UP|DOWN)\\s+([0-9./]+)\\s+.+\\s+([0-9a-f:]+)$", 2);
      boolean bool;
      do
      {
        String str1;
        String str2;
        String str3;
        do
        {
          Matcher localMatcher;
          do
          {
            if (!localBufferedReader.ready())
              break;
            localMatcher = localPattern.matcher(localBufferedReader.readLine());
          }
          while (!localMatcher.matches());
          str1 = localMatcher.group(1).toLowerCase(Locale.CHINA);
          str2 = localMatcher.group(2);
          str3 = localMatcher.group(3);
          String str4 = localMatcher.group(4).toUpperCase(Locale.CHINA).replace(':', '-');
          Logger.i("NetTools", "getPortIsWork(), match success:" + str1 + " " + str2 + " " + str3 + " " + str4);
          if ((str1.matches("eth[0-9]+")) && (str2.matches("UP")) && (!str3.matches("0.0.0.0")))
            return true;
        }
        while ((!str1.matches("wlan[0-9]+")) || (!str2.matches("UP")));
        bool = str3.matches("0.0.0.0");
      }
      while (bool);
      return true;
    }
    catch (IOException localIOException)
    {
      Logger.i("NetTools", "getPortIsWork(), Exception: IOException.");
      localIOException.printStackTrace();
      return false;
    }
    catch (InterruptedException localInterruptedException)
    {
      while (true)
      {
        Logger.i("NetTools", "getPortIsWork(), Exception: InterruptedException.");
        localInterruptedException.printStackTrace();
      }
    }
  }

  public static String getWifiMac()
  {
    String str = getMacLevel9("wlan[0-9]+");
    if (str.equals(""))
      str = getMacNetcfg("wlan[0-9]+");
    if (!TextUtils.isEmpty(str))
      str.toUpperCase(Locale.CHINA);
    if (str.length() != 17)
    {
      Logger.e("NetTools", "getWLANMac mac:" + str);
      return str;
    }
    Logger.i("NetTools", "getWLANMac mac:" + str);
    return str;
  }

  public static String getWifiName()
  {
    return ((WifiManager)App.getInstance().getSystemService("wifi")).getConnectionInfo().getSSID();
  }

  private static boolean isFastMobileNetwork(Context paramContext)
  {
    switch (((TelephonyManager)paramContext.getSystemService("phone")).getNetworkType())
    {
    case 0:
    case 1:
    case 2:
    case 4:
    case 7:
    case 11:
    default:
      return false;
    case 5:
      return true;
    case 6:
      return true;
    case 8:
      return true;
    case 10:
      return true;
    case 9:
      return true;
    case 3:
      return true;
    case 14:
      return true;
    case 12:
      return true;
    case 15:
      return true;
    case 13:
    }
    return true;
  }

  public static boolean isNetworkConnected(Context paramContext)
  {
    if (paramContext != null)
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
      if (localNetworkInfo != null)
        return localNetworkInfo.isAvailable();
    }
    return false;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.net.NetTools
 * JD-Core Version:    0.6.2
 */