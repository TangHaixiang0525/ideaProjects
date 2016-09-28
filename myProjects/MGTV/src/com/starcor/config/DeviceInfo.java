package com.starcor.config;

import android.text.TextUtils;
import com.starcor.core.net.NetTools;
import com.starcor.core.utils.DeviceIdentifier;
import com.starcor.core.utils.Logger;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class DeviceInfo
{
  private static String TAG = "DeviceInfo";
  private static int factory = 0;
  private static String mac = "";
  private static String userAgent = "";

  public static void dumpData()
  {
    Logger.i(TAG, "--------------------- dumpData begin ---------------------");
    Logger.i(TAG, "code revision: head");
    Logger.i(TAG, "build info   : ");
    Field[] arrayOfField = DeviceInfo.class.getDeclaredFields();
    int i = arrayOfField.length;
    int j = 0;
    while (true)
    {
      Field localField;
      if (j < i)
      {
        localField = arrayOfField[j];
        if (Modifier.isStatic(localField.getModifiers()))
          localField.setAccessible(true);
      }
      try
      {
        String str2 = TAG;
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = localField.getName();
        arrayOfObject2[1] = localField.get(null).toString();
        Logger.i(str2, String.format("\t DeviceInfo.%s = %s", arrayOfObject2));
        j++;
        continue;
        Method[] arrayOfMethod = DeviceInfo.class.getDeclaredMethods();
        int k = arrayOfMethod.length;
        m = 0;
        if (m < k)
        {
          Method localMethod = arrayOfMethod[m];
          if ((Modifier.isStatic(localMethod.getModifiers())) && (localMethod.getTypeParameters().length == 0))
          {
            localMethod.setAccessible(true);
            try
            {
              if ((localMethod.getName().equals("init")) || (localMethod.getName().equals("dumpData")))
                break label265;
              String str1 = TAG;
              Object[] arrayOfObject1 = new Object[2];
              arrayOfObject1[0] = localMethod.getName();
              arrayOfObject1[1] = localMethod.invoke(null, new Object[0]).toString();
              Logger.i(str1, String.format("\t DeviceInfo.%s() = %s", arrayOfObject1));
            }
            catch (Exception localException1)
            {
            }
          }
        }
        else
        {
          Logger.i(TAG, "--------------------- dumpData end ---------------------");
          return;
        }
      }
      catch (Exception localException2)
      {
        while (true)
        {
          int m;
          continue;
          label265: m++;
        }
      }
    }
  }

  public static int getFactory()
  {
    if (factory == 0)
      factory = MgtvVersion.getFactoryCode();
    return factory;
  }

  public static String getMGTVVersion()
  {
    return MgtvVersion.getVersion();
  }

  public static String getMac()
  {
    if ((MgtvVersion.ReleaseType.Debug == MgtvVersion.getReleaseType()) || (MgtvVersion.ReleaseType.Debug_Test == MgtvVersion.getReleaseType()) || (MgtvVersion.ReleaseType.Debug_Release == MgtvVersion.getReleaseType()))
    {
      mac = "00-0B-2F-33-7B-00";
      return mac;
    }
    if (17 == mac.length())
      return mac;
    String str = NetTools.getLANMac();
    if (DeviceIdentifier.isValueAvailable(str));
    while (true)
    {
      mac = str;
      Logger.i(TAG, "getMac mac:" + mac);
      if (!TextUtils.isEmpty(mac))
        break;
      Logger.i(TAG, "获取MAC地址失败！");
      return "获取MAC地址失败！";
      str = NetTools.getWifiMac();
    }
    return mac;
  }

  public static String getUserAgent()
  {
    if (userAgent.length() == 0)
      userAgent = "nn_player/std/1.0.0";
    return userAgent;
  }

  public static void init()
  {
    getFactory();
    getMac();
    MgtvUrl.init();
    if (MgtvVersion.getReleaseType() != MgtvVersion.ReleaseType.Release)
    {
      MgtvVersionTable.dumpData();
      dumpData();
      MgtvUrl.dumpData();
    }
  }

  public static boolean isBDYB()
  {
    return (factory == 900024001) || (factory == 900024002);
  }

  public static boolean isCHMS628()
  {
    return factory == 900011009;
  }

  public static boolean isDFIM()
  {
    return (factory == 900047000) || (factory == 900047001);
  }

  public static boolean isFactoryCH()
  {
    return (factory == 900011002) || (factory == 900011001) || (factory == 900011003) || (factory == 900011004) || (factory == 900011007) || (factory == 900011008) || (factory == 900011009) || (factory == 900011005);
  }

  public static boolean isFactoryTCL()
  {
    return (factory == 900010000) || (factory == 900010002) || (factory == 900010100) || (factory == 900010001) || (factory == 900010102) || (factory == 900010105) || (factory == 900010101) || (factory == 900010103) || (factory == 900010200) || (factory == 900010201) || (factory == 900010202);
  }

  public static boolean isFactoryTCLMstarXX()
  {
    return (factory == 900010100) || (factory == 900010102) || (factory == 900010105) || (factory == 900010101) || (factory == 900010103);
  }

  public static boolean isHMD()
  {
    return (factory == 900016001) || (factory == 900016202) || (factory == 900016201) || (factory == 900016200) || (factory == 900016205) || (factory == 900016000) || (factory == 900052000);
  }

  public static boolean isHMDH7()
  {
    return factory == 900016202;
  }

  public static boolean isHMDQ5With1Kernal()
  {
    return factory == 900016001;
  }

  public static boolean isHUOLE_G1()
  {
    return factory == 900039002;
  }

  public static boolean isHuaWei()
  {
    return factory == 900017001;
  }

  public static boolean isJiuShi()
  {
    return (factory == 900013001) || (factory == 900013002);
  }

  public static boolean isKLWS()
  {
    return (factory == 900044000) || (factory == 900044001);
  }

  public static boolean isKONKA()
  {
    return factory == 900051000;
  }

  public static boolean isMAILE()
  {
    return factory == 900053000;
  }

  public static boolean isMANGO_DOWNLOAD()
  {
    return factory == 900000001;
  }

  public static boolean isPHILIPMS628()
  {
    return factory == 900034003;
  }

  public static boolean isQUANZHI()
  {
    return factory == 900043001;
  }

  public static boolean isQUANZHI_A31S()
  {
    return factory == 909901102;
  }

  public static boolean isSWT()
  {
    return (factory == 900027001) || (factory == 900027002) || (factory == 900027003);
  }

  public static boolean isSWT_V15()
  {
    return factory == 900027003;
  }

  public static boolean isTCLLH_RT2982()
  {
    return factory == 900010406;
  }

  public static boolean isTCL_RT2995()
  {
    return factory == 900010001;
  }

  public static boolean isTclBox()
  {
    return factory == 900010301;
  }

  public static boolean isTclMango()
  {
    return (factory == 900010402) || (factory == 900010401) || (factory == 900010403);
  }

  public static boolean isXiaoMi()
  {
    return factory == 900057000;
  }

  public static boolean isYingChiGAL_A01()
  {
    return factory == 900023002;
  }

  public static boolean isZW()
  {
    return factory == 900048000;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.config.DeviceInfo
 * JD-Core Version:    0.6.2
 */