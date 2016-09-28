package com.sohu.logger.bean;

import com.sohu.logger.common.AppConstants;
import com.sohu.logger.common.DeviceConstants;
import com.sohu.logger.common.ParamsConstant;
import com.sohu.logger.util.LoggerUtil;
import com.sohu.logger.util.NetUtils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class LogItem
  implements Serializable
{
  private static final long serialVersionUID = -7766508464112231555L;
  protected boolean isDeleted = false;
  protected boolean isRealtime = true;
  protected boolean isStored = true;
  protected int mItemType;
  protected Map<String, String> paramsMap = new HashMap();
  protected Map<String, String> paramsNotNeedMap = null;
  protected int retryNum = 3;

  public LogItem()
  {
    initParams();
  }

  private String paramsToUrl()
  {
    if ((this.paramsMap == null) || (this.paramsMap.isEmpty()))
      return "";
    String[] arrayOfString = getParamsTemplate();
    if (arrayOfString == null)
      return "";
    StringBuilder localStringBuilder = new StringBuilder();
    int i = arrayOfString.length;
    int j = 0;
    if (j < i)
    {
      String str = arrayOfString[j];
      if ((this.paramsNotNeedMap != null) && (this.paramsNotNeedMap.containsKey(str)));
      while (true)
      {
        j++;
        break;
        localStringBuilder.append("&").append(str).append("=").append(getParamsMapValue(str));
      }
    }
    return LoggerUtil.buildUrlByType(this.mItemType) + localStringBuilder.toString().replaceFirst("&", "");
  }

  public void fillGlobleAppParams()
  {
    if (this.paramsMap == null);
    AppConstants localAppConstants;
    do
    {
      return;
      DeviceConstants localDeviceConstants = DeviceConstants.getInstance();
      localAppConstants = AppConstants.getInstance();
      this.paramsMap.put("cv", localAppConstants.getSver());
      this.paramsMap.put("mos", "2");
      this.paramsMap.put("mosv", localDeviceConstants.mSystemVersion);
      this.paramsMap.put("mtype", localAppConstants.getPlatform());
      this.paramsMap.put("mfo", localDeviceConstants.getManufacturer());
      this.paramsMap.put("mfov", localDeviceConstants.mDeviceName);
      this.paramsMap.put("channelid", localAppConstants.getPartnerNo());
      this.paramsMap.put("pro", localAppConstants.getPoid());
      this.paramsMap.put("uid", localDeviceConstants.getUID());
      this.paramsMap.put("loc", localDeviceConstants.getUIDBeforeV3());
      this.paramsMap.put("startid", LoggerUtil.getStartId());
      this.paramsMap.put("screen", localDeviceConstants.getScreen());
      this.paramsMap.put("poid", localAppConstants.getPoid());
      this.paramsMap.put("plat", localAppConstants.getPlatform());
      this.paramsMap.put("sver", localAppConstants.getSver());
      this.paramsMap.put("os", "2");
      this.paramsMap.put("sysver", localDeviceConstants.mSystemVersion);
      this.paramsMap.put("pn", localDeviceConstants.mDeviceName);
      if ((this.mItemType == 6) || (this.mItemType == 5))
      {
        this.paramsMap.put("sid", localDeviceConstants.getUID());
        this.paramsMap.put("v", localAppConstants.getSver());
        this.paramsMap.put("type", "700");
        this.paramsMap.put("ab", "0");
      }
    }
    while (AppConstants.getInstance().getmProjectType() != 1);
    this.paramsMap.put("isonline", "on");
    this.paramsMap.put("pro_type", localAppConstants.getmProType());
    this.paramsMap.put("pro_form", "2");
    this.paramsMap.put("channel_id", localAppConstants.getPartnerNo());
    this.paramsMap.put("platform", localAppConstants.getPlatform());
    this.paramsMap.put("apk_type", localAppConstants.getmApkType());
  }

  public void fillParams()
  {
  }

  public void fillRealTimeRarams()
  {
    this.paramsMap.put("webtype", LoggerUtil.getNetworkType());
    this.paramsMap.put("passport", LoggerUtil.getPassport());
    this.paramsMap.put("newuser", LoggerUtil.isNewUser());
    this.paramsMap.put("sim", LoggerUtil.getSimState());
    if ((this.paramsMap.get("url") != null) && (((String)this.paramsMap.get("url")).equals("1002")))
      if (AppConstants.getInstance().getmProjectType() == 1)
      {
        this.paramsMap.put("disk_space_total", LoggerUtil.getTotalInternalMemorySize() + "");
        this.paramsMap.put("disk_space_use", LoggerUtil.getAvailableInternalMemorySize() + "");
        this.paramsMap.put("sd_space_total", LoggerUtil.getTotalExternalMemorySize() + "");
        this.paramsMap.put("sd_space_use", LoggerUtil.getAvailableExternalMemorySize() + "");
      }
    while (true)
    {
      this.paramsMap.put("ts", String.valueOf(System.currentTimeMillis()));
      this.paramsMap.put("net", LoggerUtil.getNetworkType());
      if (AppConstants.getInstance().getmProjectType() == 1)
        this.paramsMap.put("webtype", NetUtils.getWebType());
      return;
      this.paramsMap.put("time", String.valueOf(System.currentTimeMillis()));
    }
  }

  public int getItemType()
  {
    return this.mItemType;
  }

  public String getParamsMapValue(String paramString)
  {
    if ((this.paramsMap == null) || (this.paramsMap.get(paramString) == null))
      return "";
    return (String)this.paramsMap.get(paramString);
  }

  public String[] getParamsTemplate()
  {
    switch (this.mItemType)
    {
    default:
      return null;
    case 4:
      if (AppConstants.getInstance().getmProjectType() == 1)
        return ParamsConstant.FOXPAD_COUNT_PARAMS;
      return ParamsConstant.PLAY_INFO_PARAMS;
    case 2:
      return ParamsConstant.PLAY_QUALITY_PARAMS;
    case 0:
      if (AppConstants.getInstance().getmProjectType() == 1)
        return ParamsConstant.FOXPAD_MCC_PARAMS;
      return ParamsConstant.USER_ACTION_PARAMS;
    case 3:
      if (AppConstants.getInstance().getmProjectType() == 1)
        return ParamsConstant.FOXPAD_ACTION_PARAMS;
      return ParamsConstant.USER_CILICK_BEHAVIOR_PARAMS;
    case 1:
      if (AppConstants.getInstance().getmProjectType() == 1)
        return ParamsConstant.FOXPAD_MVV_PARAMS;
      return ParamsConstant.VIDEO_PLAY_PARAMS;
    case 5:
      return ParamsConstant.RECOMMENDED_SHOW_PARAMS;
    case 6:
    }
    return ParamsConstant.RECOMMENDED_CLICK_PARAMS;
  }

  public int getRetryNum()
  {
    return this.retryNum;
  }

  public void initParams()
  {
  }

  public boolean isDeleted()
  {
    return this.isDeleted;
  }

  public boolean isStored()
  {
    return this.isStored;
  }

  public boolean needSendByHeartbeat()
  {
    return true;
  }

  public boolean needSendRealtime()
  {
    return this.isRealtime;
  }

  public void setDeleted(boolean paramBoolean)
  {
    this.isDeleted = paramBoolean;
  }

  public void setParamsMapItem(String paramString, int paramInt)
  {
    this.paramsMap.put(paramString, Integer.toString(paramInt));
  }

  public void setParamsMapItem(String paramString, long paramLong)
  {
    this.paramsMap.put(paramString, Long.toString(paramLong));
  }

  public void setParamsMapItem(String paramString1, String paramString2)
  {
    if (paramString2 != null)
      this.paramsMap.put(paramString1, paramString2);
  }

  public void setRealtime(boolean paramBoolean)
  {
    this.isRealtime = paramBoolean;
  }

  public void setRetryNum(int paramInt)
  {
    this.retryNum = paramInt;
  }

  public void setStored(boolean paramBoolean)
  {
    this.isStored = paramBoolean;
  }

  public String toUrl()
  {
    return paramsToUrl();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.logger.bean.LogItem
 * JD-Core Version:    0.6.2
 */