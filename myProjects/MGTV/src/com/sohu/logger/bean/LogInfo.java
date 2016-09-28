package com.sohu.logger.bean;

import android.text.TextUtils;
import com.sohu.logger.common.DeviceConstants;

public class LogInfo
{
  public static final int ORDER_TYPE_ASC = 0;
  public static final int ORDER_TYPE_DESC = 1;
  public static final int TYPE_LIVE = 1;
  public static final int TYPE_LIVE_EPG = 4;
  public static final int TYPE_LIVE_SINGLE = 2;
  public static final int TYPE_VIDEO_SHOW = 3;
  public static final int TYPE_VOD;
  private String albumID = "";
  private String area = "";
  private String authorityKey = "";
  private String blueRayAuthorityKey = "";
  private String cateCode = "";
  private String categoryID = "";
  private String channeled = "";
  private String company = "";
  private String currentDefinition = "";
  private int currentDefinitionType = 0;
  private String currentUrl = "";
  private String enterID = "0";
  private boolean fee = false;
  private int isEdit = 0;
  private String isSohu;
  private String language = "";
  private String liveChannelID = "";
  private String liveStreamID = "";
  private String passport;
  private int playmode = 0;
  private String siteName;
  private String statCode;
  private String subCategoryID = "";
  private String tvID = "";
  private int type = 0;
  private String videoID = "";
  private String videoType = "";
  private String watchType = "1";

  public String getAlbumID()
  {
    return this.albumID;
  }

  public String getArea()
  {
    return this.area;
  }

  public String getCateCode()
  {
    return this.cateCode;
  }

  public String getCategoryID()
  {
    return this.categoryID;
  }

  public String getChanneled()
  {
    return this.channeled;
  }

  public String getCompany()
  {
    return this.company;
  }

  public String getCurrentDefinition()
  {
    return this.currentDefinition;
  }

  public int getCurrentDefinitionType()
  {
    return this.currentDefinitionType;
  }

  public String getCurrentUrl()
  {
    if (TextUtils.isEmpty(this.currentUrl))
      return "";
    if ((!TextUtils.isEmpty(this.authorityKey)) && (!TextUtils.isEmpty(getPassport())))
    {
      if (this.currentUrl.contains("?"))
        this.currentUrl = (this.currentUrl + "&fkey=" + this.authorityKey);
    }
    else
    {
      if ((!TextUtils.isEmpty(this.blueRayAuthorityKey)) && (!TextUtils.isEmpty(getPassport())))
      {
        if (!this.currentUrl.contains("?"))
          break label243;
        this.currentUrl = (this.currentUrl + "&fkey=" + this.blueRayAuthorityKey);
      }
      label143: if (!this.currentUrl.contains("uid="))
        if (!this.currentUrl.contains("?"))
          break label279;
    }
    label279: for (this.currentUrl = (this.currentUrl + "&uid=" + DeviceConstants.getInstance().getUID()); ; this.currentUrl = (this.currentUrl + "?uid=" + DeviceConstants.getInstance().getUID()))
    {
      return this.currentUrl;
      this.currentUrl = (this.currentUrl + "?fkey=" + this.authorityKey);
      break;
      label243: this.currentUrl = (this.currentUrl + "?fkey=" + this.blueRayAuthorityKey);
      break label143;
    }
  }

  public String getEnterID()
  {
    return this.enterID;
  }

  public int getIsEdit()
  {
    return this.isEdit;
  }

  public String getIsSohu()
  {
    return this.isSohu;
  }

  public String getLanguage()
  {
    return this.language;
  }

  public String getLiveChannelID()
  {
    return this.liveChannelID;
  }

  public String getLiveStreamID()
  {
    return this.liveStreamID;
  }

  public String getPassport()
  {
    return this.passport;
  }

  public int getPlaymode()
  {
    return this.playmode;
  }

  public String getSiteName()
  {
    return this.siteName;
  }

  public String getStatCode()
  {
    return this.statCode;
  }

  public String getSubCategoryID()
  {
    if ((this.enterID != null) && (this.enterID.equals(this.statCode)))
      return this.subCategoryID;
    return "";
  }

  public String getTvID()
  {
    return this.tvID;
  }

  public int getType()
  {
    return this.type;
  }

  public String getVideoID()
  {
    return this.videoID;
  }

  public String getWatchType()
  {
    return this.watchType;
  }

  public boolean isFee()
  {
    return this.fee;
  }

  public void setAlbumID(String paramString)
  {
    this.albumID = paramString;
  }

  public void setArea(String paramString)
  {
    this.area = paramString;
  }

  public void setCateCode(String paramString)
  {
    this.cateCode = paramString;
  }

  public void setCategoryID(String paramString)
  {
    this.categoryID = paramString;
  }

  public void setChanneled(String paramString)
  {
    this.channeled = paramString;
  }

  public void setCompany(String paramString)
  {
    this.company = paramString;
  }

  public void setCurrentDefinition(String paramString)
  {
    this.currentDefinition = paramString;
  }

  public void setCurrentDefinitionType(int paramInt)
  {
    this.currentDefinitionType = paramInt;
  }

  public void setCurrentUrl(String paramString)
  {
    this.currentUrl = paramString;
  }

  public void setEnterID(String paramString)
  {
    this.enterID = paramString;
  }

  public void setFee(boolean paramBoolean)
  {
    this.fee = paramBoolean;
  }

  public void setIsEdit(int paramInt)
  {
    this.isEdit = paramInt;
  }

  public void setIsSohu(String paramString)
  {
    this.isSohu = paramString;
  }

  public void setLanguage(String paramString)
  {
    this.language = paramString;
  }

  public void setLiveChannelID(String paramString)
  {
    this.liveChannelID = paramString;
  }

  public void setLiveStreamID(String paramString)
  {
    this.liveStreamID = paramString;
  }

  public void setPassport(String paramString)
  {
    this.passport = paramString;
  }

  public void setPlaymode(int paramInt)
  {
    this.playmode = paramInt;
  }

  public void setSiteName(String paramString)
  {
    this.siteName = paramString;
  }

  public void setStatCode(String paramString)
  {
    this.statCode = paramString;
  }

  public void setSubCategoryID(String paramString)
  {
    this.subCategoryID = paramString;
  }

  public void setTvID(String paramString)
  {
    this.tvID = paramString;
  }

  public void setType(int paramInt)
  {
    this.type = paramInt;
  }

  public void setVideoID(String paramString)
  {
    this.videoID = paramString;
  }

  public void setWatchType(String paramString)
  {
    this.watchType = paramString;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.logger.bean.LogInfo
 * JD-Core Version:    0.6.2
 */