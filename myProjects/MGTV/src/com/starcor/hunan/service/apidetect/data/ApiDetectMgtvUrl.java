package com.starcor.hunan.service.apidetect.data;

import com.starcor.config.DeviceInfo;
import com.starcor.config.MgtvVersion;
import com.starcor.config.MgtvVersion.ReleaseType;
import com.starcor.config.MgtvVersionInfo;
import com.starcor.config.MgtvVersionTable;
import com.starcor.core.utils.Logger;

public class ApiDetectMgtvUrl
{
  private static final String TAG = "ApiDetectMgtvUrl";
  public static ServerPlatform serverPlatform = ServerPlatform.Develop;
  public static String urlN1A = "";
  public static String urlReportTerminalStatus = "";
  public static String urlWechatPayUrl = "";

  public static String getReportTerminalStatus()
  {
    return urlReportTerminalStatus;
  }

  public static void init()
  {
    if ((MgtvVersion.ReleaseType.Release == MgtvVersion.getReleaseType()) || (MgtvVersion.ReleaseType.Pre_Release == MgtvVersion.getReleaseType()) || (MgtvVersion.ReleaseType.NC_Release == MgtvVersion.getReleaseType()))
    {
      serverPlatform = ServerPlatform.Release;
      MgtvVersionInfo localMgtvVersionInfo1 = MgtvVersionTable.getMgtvVersionInfo(DeviceInfo.getFactory());
      if (localMgtvVersionInfo1 != null)
      {
        urlN1A = localMgtvVersionInfo1.N1AUrl;
        return;
      }
      Logger.e("ApiDetectMgtvUrl", "N1_A is null");
      urlN1A = "";
      return;
    }
    if ((MgtvVersion.ReleaseType.Beta == MgtvVersion.getReleaseType()) || (MgtvVersion.ReleaseType.NC_Beta == MgtvVersion.getReleaseType()))
    {
      serverPlatform = ServerPlatform.Test;
      if (DeviceInfo.getFactory() == 901001001)
      {
        urlN1A = "http://dxjd.interface.starordemo.net/mgtv/STBindex";
        return;
      }
      urlN1A = "http://cs.interface.hifuntv.com/mgtv/STBindex";
      return;
    }
    if (MgtvVersion.ReleaseType.Demo == MgtvVersion.getReleaseType())
    {
      serverPlatform = ServerPlatform.Release;
      MgtvVersionInfo localMgtvVersionInfo3 = MgtvVersionTable.getMgtvVersionInfo(DeviceInfo.getFactory());
      if (localMgtvVersionInfo3 != null)
      {
        urlN1A = localMgtvVersionInfo3.N1AUrl;
        return;
      }
      Logger.e("ApiDetectMgtvUrl", "N1_A is null");
      urlN1A = "";
      return;
    }
    if ((MgtvVersion.ReleaseType.Debug == MgtvVersion.getReleaseType()) || (MgtvVersion.ReleaseType.NC_Debug == MgtvVersion.getReleaseType()))
    {
      serverPlatform = ServerPlatform.Develop;
      urlN1A = "http://yf.interface.hifuntv.com/mgtv/STBindex";
      serverPlatform = ServerPlatform.Test;
      urlN1A = "http://cs.interface.hifuntv.com/mgtv/STBindex";
      return;
    }
    if (MgtvVersion.ReleaseType.Debug_Release == MgtvVersion.getReleaseType())
    {
      serverPlatform = ServerPlatform.Release;
      MgtvVersionInfo localMgtvVersionInfo2 = MgtvVersionTable.getMgtvVersionInfo(DeviceInfo.getFactory());
      if (localMgtvVersionInfo2 != null)
      {
        urlN1A = localMgtvVersionInfo2.N1AUrl;
        return;
      }
      Logger.e("ApiDetectMgtvUrl", "N1_A is null");
      urlN1A = "";
      return;
    }
    Logger.e("ApiDetectMgtvUrl", "invalid MgtvVersion.getReleaseType()=" + MgtvVersion.getReleaseType());
  }

  public static void setReportTerminalStatus(String paramString)
  {
    urlReportTerminalStatus = paramString.trim();
    Logger.i("ApiDetectMgtvUrl", "setUrlReportTerminalStatus url=" + paramString);
  }

  public static void setWechatUrl(String paramString)
  {
    urlWechatPayUrl = paramString.trim();
    Logger.i("ApiDetectMgtvUrl", "urlWechatPayUrl url=" + paramString);
  }

  public static enum ServerPlatform
  {
    static
    {
      Release = new ServerPlatform("Release", 2);
      ServerPlatform[] arrayOfServerPlatform = new ServerPlatform[3];
      arrayOfServerPlatform[0] = Develop;
      arrayOfServerPlatform[1] = Test;
      arrayOfServerPlatform[2] = Release;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.service.apidetect.data.ApiDetectMgtvUrl
 * JD-Core Version:    0.6.2
 */