package com.starcor.core.domain;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.hunan.App;
import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.MultipartEntity;

public class HeartbeatPrams extends BaseParams
{
  protected StringParams Version = new StringParams("g6");
  protected StringParams deviceCategory = new StringParams("g2");
  protected StringParams deviceId = new StringParams("g4");
  protected StringParams deviceModel = new StringParams("g3");
  protected StringParams deviceProducer = new StringParams("g1");
  protected StringParams deviceSystemVersion = new StringParams("g5");
  protected StringParams ipAddress = new StringParams("g8");
  protected StringParams licence = new StringParams("g10");
  protected StringParams linkType = new StringParams("g9");
  protected StringParams macAddress = new StringParams("g7");
  protected StringParams screenResolution = new StringParams("g12");
  protected StringParams siteId = new StringParams("site_id");
  protected StringParams userName = new StringParams("g11");

  public HeartbeatPrams(Context paramContext)
  {
    this.deviceProducer.setValue(Build.MANUFACTURER);
    this.deviceCategory.setValue("ntv");
    this.deviceModel.setValue(Build.DEVICE);
    this.deviceId.setValue(Settings.Secure.getString(paramContext.getContentResolver(), "android_id"));
    this.deviceSystemVersion.setValue("Android " + Build.VERSION.RELEASE);
    this.Version.setValue(getVersion(paramContext));
    this.macAddress.setValue(Settings.Secure.getString(paramContext.getContentResolver(), "android_id"));
    this.siteId.setValue("18");
    this.screenResolution.setValue(App.SCREEN_WIDTH + "X" + App.SCREEN_HEIGHT);
    this.linkType.setValue(getNetType(paramContext));
  }

  private String getNetType(Context paramContext)
  {
    NetworkInfo localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
    if (localNetworkInfo != null)
    {
      if (1 == localNetworkInfo.getType())
        return "wifi";
      if (localNetworkInfo.getType() == 0)
        return "3g";
    }
    return "";
  }

  private String getVersion(Context paramContext)
  {
    PackageManager localPackageManager = paramContext.getPackageManager();
    try
    {
      String str = localPackageManager.getPackageInfo(paramContext.getPackageName(), 0).versionName;
      return str;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      localNameNotFoundException.printStackTrace();
    }
    return "0";
  }

  public StringParams getDeviceCategory()
  {
    return this.deviceCategory;
  }

  public StringParams getDeviceId()
  {
    return this.deviceId;
  }

  public StringParams getDeviceModel()
  {
    return this.deviceModel;
  }

  public StringParams getDeviceProducer()
  {
    return this.deviceProducer;
  }

  public StringParams getDeviceSystemVersion()
  {
    return this.deviceSystemVersion;
  }

  public HttpEntity getEntity()
  {
    MultipartEntity localMultipartEntity = (MultipartEntity)super.getEntity();
    localMultipartEntity.addPart(this.deviceProducer.getName(), this.deviceProducer.getBody());
    localMultipartEntity.addPart(this.deviceCategory.getName(), this.deviceCategory.getBody());
    localMultipartEntity.addPart(this.deviceModel.getName(), this.deviceModel.getBody());
    localMultipartEntity.addPart(this.deviceId.getName(), this.deviceId.getBody());
    localMultipartEntity.addPart(this.deviceSystemVersion.getName(), this.deviceSystemVersion.getBody());
    localMultipartEntity.addPart(this.Version.getName(), this.Version.getBody());
    localMultipartEntity.addPart(this.macAddress.getName(), this.macAddress.getBody());
    localMultipartEntity.addPart(this.userName.getName(), this.userName.getBody());
    localMultipartEntity.addPart(this.screenResolution.getName(), this.screenResolution.getBody());
    localMultipartEntity.addPart(this.siteId.getName(), this.siteId.getBody());
    localMultipartEntity.addPart(this.linkType.getName(), this.linkType.getBody());
    return localMultipartEntity;
  }

  public StringParams getIpAddress()
  {
    return this.ipAddress;
  }

  public StringParams getLicence()
  {
    return this.licence;
  }

  public StringParams getLinkType()
  {
    return this.linkType;
  }

  public StringParams getMacAddress()
  {
    return this.macAddress;
  }

  public StringParams getScreenResolution()
  {
    return this.screenResolution;
  }

  public StringParams getSiteId()
  {
    return this.siteId;
  }

  public StringParams getUserName()
  {
    return this.userName;
  }

  public StringParams getVersion()
  {
    return this.Version;
  }

  public String toString()
  {
    return "HeartbeatPrams [deviceProducer=" + this.deviceProducer + ", deviceCategory=" + this.deviceCategory + ", deviceModel=" + this.deviceModel + ", deviceId=" + this.deviceId + ", deviceSystemVersion=" + this.deviceSystemVersion + ", Version=" + this.Version + ", macAddress=" + this.macAddress + ", ipAddress=" + this.ipAddress + ", linkType=" + this.linkType + ", licence=" + this.licence + ", userName=" + this.userName + ", screenResolution=" + this.screenResolution + ", siteId=" + this.siteId + "]";
  }

  public void updateData()
  {
    this.userName.setValue(GlobalLogic.getInstance().getUserName());
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.HeartbeatPrams
 * JD-Core Version:    0.6.2
 */