package com.starcor.core.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import com.starcor.core.net.NetTools;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DeviceIdentifier
{
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

  public static String SHA1(String paramString)
  {
    try
    {
      String str = bytetoString(MessageDigest.getInstance("SHA-1").digest(paramString.getBytes()));
      return str;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      localNoSuchAlgorithmException.printStackTrace();
    }
    return null;
  }

  private static void appendArgs(StringBuilder paramStringBuilder, String paramString)
  {
    if (isValueAvailable(paramString))
      paramStringBuilder.append(paramString).append("_");
  }

  public static String bytetoString(byte[] paramArrayOfByte)
  {
    String str1 = "";
    int i = 1;
    if (i < paramArrayOfByte.length)
    {
      String str2 = Integer.toHexString(0xFF & paramArrayOfByte[i]);
      if (str2.length() == 1);
      for (str1 = str1 + "0" + str2; ; str1 = str1 + str2)
      {
        i++;
        break;
      }
    }
    return str1.toLowerCase();
  }

  public static String getAAADeviceId(Context paramContext)
  {
    return SHA1Util.hex_sha1(getUniqueIdentifier(paramContext));
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
        String str1 = localNetworkInterface.getName().toLowerCase();
        str2 = MacString(arrayOfByte);
        bool = str1.matches(paramString);
      }
      while (!bool);
      return str2;
    }
    catch (SocketException localSocketException)
    {
      localSocketException.printStackTrace();
      return "";
    }
    catch (SecurityException localSecurityException)
    {
      while (true)
        localSecurityException.printStackTrace();
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      while (true)
        localNoSuchMethodException.printStackTrace();
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      while (true)
        localIllegalArgumentException.printStackTrace();
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      while (true)
        localIllegalAccessException.printStackTrace();
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      while (true)
        localInvocationTargetException.printStackTrace();
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
      String str2;
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
        String str1 = localMatcher.group(1).toLowerCase();
        localMatcher.group(2);
        localMatcher.group(3);
        str2 = localMatcher.group(4).toUpperCase().replace(':', '-');
        bool = str1.matches(paramString);
      }
      while (!bool);
      return str2;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      return "";
    }
    catch (InterruptedException localInterruptedException)
    {
      while (true)
        localInterruptedException.printStackTrace();
    }
  }

  public static String getTotalInternalStorageSize(Context paramContext)
  {
    StatFs localStatFs = new StatFs(Environment.getDataDirectory().getPath());
    return Formatter.formatFileSize(paramContext, localStatFs.getBlockSize() * localStatFs.getBlockCount());
  }

  public static String getTotalMemory(Context paramContext)
  {
    long l = 0L;
    try
    {
      BufferedReader localBufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), 8192);
      String str1 = localBufferedReader.readLine();
      for (String str2 : str1.split("\\s+"))
        Log.i(str1, str2 + "\t");
      l = 1024 * Integer.valueOf(???[1]).intValue();
      localBufferedReader.close();
      label114: return Formatter.formatFileSize(paramContext, l);
    }
    catch (IOException localIOException)
    {
      break label114;
    }
  }

  public static String getUniqueIdentifier(Context paramContext)
  {
    String str = NetTools.getLANMac();
    StringBuilder localStringBuilder = new StringBuilder();
    appendArgs(localStringBuilder, Build.PRODUCT);
    appendArgs(localStringBuilder, Build.DEVICE);
    appendArgs(localStringBuilder, Build.BOARD);
    appendArgs(localStringBuilder, Build.CPU_ABI);
    appendArgs(localStringBuilder, Build.MODEL);
    appendArgs(localStringBuilder, Build.SERIAL);
    appendArgs(localStringBuilder, getTotalMemory(paramContext));
    appendArgs(localStringBuilder, getTotalInternalStorageSize(paramContext));
    if (isValueAvailable(str));
    while (true)
    {
      appendArgs(localStringBuilder, str);
      localStringBuilder.deleteCharAt(-1 + localStringBuilder.length());
      return localStringBuilder.toString();
      str = NetTools.getWifiMac();
    }
  }

  public static boolean isValueAvailable(String paramString)
  {
    return (!TextUtils.isEmpty(paramString)) && (!"Unknown".equalsIgnoreCase(paramString));
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.utils.DeviceIdentifier
 * JD-Core Version:    0.6.2
 */