package com.starcor.core.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.starcor.config.DeviceInfo;
import com.starcor.config.MgtvVersion;
import com.starcor.config.MgtvVersion.ReleaseType;
import com.starcor.hunan.service.SystemTimeManager;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.UUID;

public class Tools
{
  public static String getDeviceIdFromSystemProperties()
  {
    String str = "";
    try
    {
      Class localClass = Class.forName("android.os.SystemProperties");
      str = (String)localClass.getMethod("get", new Class[] { String.class, String.class }).invoke(localClass, new Object[] { "ro.device_id", "" });
      Logger.d("Tools", "getDeviceIdFromSystemProperties deviceId:" + str);
      if (MgtvVersion.ReleaseType.Debug_Test == MgtvVersion.getReleaseType())
        str = "PND000000000002";
      return str;
    }
    catch (Exception localException)
    {
      while (true)
        Logger.e("Tools", "getDeviceIdFromSystemProperties exception:" + localException.getLocalizedMessage());
    }
  }

  public static String getLocalIpAddress()
  {
    Object localObject = "127.0.0.1";
    try
    {
      Enumeration localEnumeration1 = NetworkInterface.getNetworkInterfaces();
      while (localEnumeration1.hasMoreElements())
      {
        Enumeration localEnumeration2 = ((NetworkInterface)localEnumeration1.nextElement()).getInetAddresses();
        while (localEnumeration2.hasMoreElements())
        {
          InetAddress localInetAddress = (InetAddress)localEnumeration2.nextElement();
          if (!localInetAddress.isLoopbackAddress())
          {
            String str = localInetAddress.getHostAddress();
            localObject = str;
          }
        }
      }
    }
    catch (SocketException localSocketException)
    {
      Logger.e("WifiPreference IpAddress", localSocketException.toString());
    }
    return localObject;
  }

  public static String getMac()
  {
    String str = DeviceInfo.getMac();
    StringBuffer localStringBuffer = new StringBuffer();
    if (str != null)
    {
      String[] arrayOfString = str.split("-");
      if ((arrayOfString != null) && (arrayOfString.length > 0))
        for (int i = 0; i < arrayOfString.length; i++)
          localStringBuffer.append(arrayOfString[i]);
    }
    return localStringBuffer.toString();
  }

  public static String getOutPlayOriginal()
  {
    String[] arrayOfString = DeviceInfo.getMGTVVersion().split("\\.");
    Logger.i("debugOutPlayOriginal", "DeviceInfo.getMGTVVersion()=" + DeviceInfo.getMGTVVersion() + ",data.length=" + arrayOfString.length);
    for (int i = 0; i < arrayOfString.length; i++)
      Logger.i("debugOutPlayOriginal", "data=" + arrayOfString[i]);
    if ((arrayOfString != null) && (arrayOfString.length >= 8))
    {
      Logger.i("debugOutPlayOriginal", "OutPlayOriginal=" + arrayOfString[5]);
      return arrayOfString[5];
    }
    return "";
  }

  public static String getTimeString()
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try
    {
      String str = localSimpleDateFormat.format(new Date(SystemTimeManager.getCurrentServerTime()));
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public static String getUUID()
  {
    return UUID.randomUUID().toString();
  }

  public static boolean isWifi(Context paramContext)
  {
    NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
    return (localNetworkInfo != null) && (localNetworkInfo.getType() == 1);
  }

  public static String stringToMD5(String paramString)
  {
    try
    {
      byte[] arrayOfByte = paramString.getBytes();
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(arrayOfByte);
      String str = toHexString(localMessageDigest.digest());
      return str;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public static long time2sec(String paramString)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    try
    {
      long l = localSimpleDateFormat.parse(paramString).getTime();
      return l;
    }
    catch (ParseException localParseException)
    {
      localParseException.printStackTrace();
    }
    return 0L;
  }

  private static String toHexString(byte[] paramArrayOfByte)
  {
    char[] arrayOfChar1 = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
    int i = paramArrayOfByte.length;
    char[] arrayOfChar2 = new char[i * 2];
    for (int j = 0; j < i; j++)
    {
      int k = paramArrayOfByte[j];
      arrayOfChar2[(j * 2)] = arrayOfChar1[(0xF & k >>> 4)];
      arrayOfChar2[(1 + j * 2)] = arrayOfChar1[(k & 0xF)];
    }
    return new String(arrayOfChar2);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.utils.Tools
 * JD-Core Version:    0.6.2
 */