package com.starcor.config;

import android.os.Build;
import android.text.TextUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.hmd.HMDCapabilitySet;

public class MgtvVersion
{
  public static final String buildInfo = "";
  public static final String codeRev = "head";
  public static int factoryCode = 900000001;
  public static final String mgtvChannelName;
  public static int policy = -1;
  public static final ReleaseType releaseType;
  public static final AppVersionNumber versionNumber;

  static
  {
    mgtvChannelName = "DBEI";
    versionNumber = new AppVersionNumber();
    versionNumber.majorVersion = 4;
    versionNumber.minorVersion = 12;
    versionNumber.patchVersion = 107;
    releaseType = ReleaseType.Release;
  }

  public static String getFactory()
  {
    MgtvVersionInfo localMgtvVersionInfo = MgtvVersionTable.getMgtvVersionInfo(factoryCode);
    if (localMgtvVersionInfo == null)
      return "";
    return localMgtvVersionInfo.versionDiscribe.factoryName;
  }

  public static int getFactoryCode()
  {
    return factoryCode;
  }

  public static int getMajorVersionNum()
  {
    return versionNumber.majorVersion;
  }

  public static int getMinorVersionNum()
  {
    return versionNumber.minorVersion;
  }

  public static int getPatchVersionNum()
  {
    return versionNumber.patchVersion;
  }

  public static String getPolicyNumber()
  {
    MgtvVersionInfo localMgtvVersionInfo = MgtvVersionTable.getMgtvVersionInfo(factoryCode);
    if (localMgtvVersionInfo == null)
      return "";
    String[] arrayOfString = localMgtvVersionInfo.versionDiscribe.toString().split("\\.");
    Logger.i("getFactoryName", arrayOfString[0]);
    return arrayOfString[0];
  }

  public static ReleaseType getReleaseType()
  {
    return releaseType;
  }

  public static String getVersion()
  {
    MgtvVersionInfo localMgtvVersionInfo = MgtvVersionTable.getMgtvVersionInfo(factoryCode);
    if (localMgtvVersionInfo == null)
      return "";
    if (DeviceInfo.isHMD())
    {
      String str2 = "";
      String str3 = "";
      if (AppFuncCfg.FUNCTION_HMD_CAPABILITY_SET)
      {
        str2 = HMDCapabilitySet.buildSupportCapability();
        str3 = HMDCapabilitySet.buildUnsupportCapability();
      }
      Logger.d("HMD", "HMD unSupportCapability=" + str3 + ", supportCapability=" + str2);
      return versionNumber.toString() + "." + localMgtvVersionInfo.versionDiscribe.toString(str2) + str3 + "_" + releaseType.name();
    }
    String str1;
    if (DeviceInfo.getFactory() == 900029000)
    {
      str1 = Build.MODEL;
      if (!"CC-100".equals(str1))
        break label230;
      localMgtvVersionInfo.versionDiscribe.setDeviceCodeMinor("2");
    }
    while (true)
    {
      Logger.d("TFTF", "TFTF model=" + str1);
      return versionNumber.toString() + "." + localMgtvVersionInfo.versionDiscribe.toString() + "_" + releaseType.name();
      label230: if ("CC-200".equals(str1))
        localMgtvVersionInfo.versionDiscribe.setDeviceCodeMinor("0");
      else
        localMgtvVersionInfo.versionDiscribe.setDeviceCodeMinor("2");
    }
  }

  public static String getVersion(int paramInt)
  {
    factoryCode = paramInt;
    return getVersion();
  }

  public static String getVersionBySetDeviceCodeMajor(String paramString)
  {
    MgtvVersionInfo localMgtvVersionInfo = MgtvVersionTable.getMgtvVersionInfo(factoryCode);
    if (localMgtvVersionInfo == null)
      return "";
    if (!TextUtils.isEmpty(paramString))
      localMgtvVersionInfo.versionDiscribe.setDeviceCodeMajor(paramString);
    String str = localMgtvVersionInfo.versionDiscribe.toString();
    return versionNumber.toString() + "." + str + "_" + releaseType.name();
  }

  public static boolean isDebugVersion()
  {
    return (releaseType == ReleaseType.Debug) || (releaseType == ReleaseType.Debug_Test) || (releaseType == ReleaseType.Debug_Release);
  }

  public static boolean isNcVersion()
  {
    return (releaseType == ReleaseType.NC_Release) || (releaseType == ReleaseType.NC_Debug) || (releaseType == ReleaseType.NC_Beta);
  }

  public static class AppVersionNumber
  {
    int majorVersion;
    int minorVersion;
    int patchVersion;

    public String toString()
    {
      return this.majorVersion + "." + this.minorVersion + "." + this.patchVersion;
    }
  }

  public static enum ReleaseType
  {
    static
    {
      Beta = new ReleaseType("Beta", 1);
      Demo = new ReleaseType("Demo", 2);
      Debug = new ReleaseType("Debug", 3);
      Debug_Test = new ReleaseType("Debug_Test", 4);
      Debug_Release = new ReleaseType("Debug_Release", 5);
      Pre_Release = new ReleaseType("Pre_Release", 6);
      NC_Release = new ReleaseType("NC_Release", 7);
      NC_Beta = new ReleaseType("NC_Beta", 8);
      NC_Debug = new ReleaseType("NC_Debug", 9);
      ReleaseType[] arrayOfReleaseType = new ReleaseType[10];
      arrayOfReleaseType[0] = Release;
      arrayOfReleaseType[1] = Beta;
      arrayOfReleaseType[2] = Demo;
      arrayOfReleaseType[3] = Debug;
      arrayOfReleaseType[4] = Debug_Test;
      arrayOfReleaseType[5] = Debug_Release;
      arrayOfReleaseType[6] = Pre_Release;
      arrayOfReleaseType[7] = NC_Release;
      arrayOfReleaseType[8] = NC_Beta;
      arrayOfReleaseType[9] = NC_Debug;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.config.MgtvVersion
 * JD-Core Version:    0.6.2
 */