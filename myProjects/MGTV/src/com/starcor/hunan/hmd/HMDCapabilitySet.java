package com.starcor.hunan.hmd;

import android.text.TextUtils;
import com.starcor.core.utils.Logger;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HMDCapabilitySet
{
  private static void appendMemory(StringBuilder paramStringBuilder)
  {
    if (getTotalMemorySize() / 1024 > 512)
      return;
    paramStringBuilder.append("A");
  }

  private static void appendPropertySupport(StringBuilder paramStringBuilder, String paramString1, String paramString2)
  {
    if (isYunOSSupport(getSystemProp(paramString1)))
      paramStringBuilder.append(paramString2);
  }

  private static void appendPropertyUnSupport(StringBuilder paramStringBuilder, String paramString1, String paramString2)
  {
    if (!isCapabilitySupport(getSystemProp(paramString1)))
      paramStringBuilder.append(paramString2);
  }

  public static String buildSupportCapability()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    appendPropertySupport(localStringBuilder, "ro.sys.os", "Y");
    return localStringBuilder.toString();
  }

  public static String buildUnsupportCapability()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    appendPropertyUnSupport(localStringBuilder, "persist.sys.3d", "B");
    appendPropertyUnSupport(localStringBuilder, "persist.sys.ac3", "C");
    appendPropertyUnSupport(localStringBuilder, "persist.sys.dts", "D");
    appendPropertyUnSupport(localStringBuilder, "persist.sys.h265", "E");
    appendPropertyUnSupport(localStringBuilder, "persist.sys.4k", "F");
    return localStringBuilder.toString();
  }

  public static String getSystemProp(String paramString)
  {
    try
    {
      Class localClass = Class.forName("android.os.SystemProperties");
      String str = (String)localClass.getMethod("get", new Class[] { String.class, String.class }).invoke(localClass, new Object[] { paramString, "Unknown" });
      return str;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      localClassNotFoundException.printStackTrace();
      return "Unknown";
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      localNoSuchMethodException.printStackTrace();
      return "Unknown";
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
      return "Unknown";
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
      return "Unknown";
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      localInvocationTargetException.printStackTrace();
    }
    return "Unknown";
  }

  public static int getTotalMemorySize()
  {
    int i = 0;
    try
    {
      BufferedReader localBufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), 8192);
      String str1 = localBufferedReader.readLine();
      String[] arrayOfString = str1.split("\\s+");
      int j = arrayOfString.length;
      for (int k = 0; ; k++)
      {
        i = 0;
        if (k >= j)
          break;
        String str2 = arrayOfString[k];
        Logger.i(str1, str2 + "\t");
      }
      i = Integer.valueOf(arrayOfString[1]).intValue();
      localBufferedReader.close();
      return i;
    }
    catch (IOException localIOException)
    {
    }
    return i;
  }

  private static boolean isCapabilitySupport(String paramString)
  {
    if (TextUtils.isEmpty(paramString));
    while (!"yes".equalsIgnoreCase(paramString))
      return false;
    return true;
  }

  private static boolean isYunOSSupport(String paramString)
  {
    if (TextUtils.isEmpty(paramString));
    while ((!"y".equalsIgnoreCase(paramString)) && (!"yes".equalsIgnoreCase(paramString)))
      return false;
    return true;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.hmd.HMDCapabilitySet
 * JD-Core Version:    0.6.2
 */