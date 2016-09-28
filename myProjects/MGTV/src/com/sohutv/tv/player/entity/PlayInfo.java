package com.sohutv.tv.player.entity;

import android.text.TextUtils;
import com.sohu.logger.common.DeviceConstants;
import com.sohutv.tv.player.util.c.a;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

public class PlayInfo
  implements Serializable
{
  public static final int ORDER_TYPE_ASC = 0;
  public static final int ORDER_TYPE_DESC = 1;
  public static final int TYPE_LIVE = 1;
  public static final int TYPE_LIVE_EPG = 4;
  public static final int TYPE_LIVE_SINGLE = 2;
  public static final int TYPE_VIDEO_SHOW = 3;
  public static final int TYPE_VOD = 0;
  private static final long serialVersionUID = -647439130539531866L;
  private String adSource;
  private String ag;
  private String albumID = "";
  private int allCount = 0;
  private String area = "";
  private String authorityKey = "";
  private String cateCode = "";
  private String categoryID = "";
  private String channeled = "";
  private String company = "";
  private String currentDefinition = "";
  private int currentDefinitionType = 0;
  private int currentPosition = 0;
  private String currentUrl = "";
  private int duration;
  private int endStartTime = 0;
  private String enterID = "0";
  private boolean fee = false;
  private boolean haveAuthority = false;
  private int headStartTime = 0;
  private String imgUrl;
  private int is4K = 0;
  private boolean isAuthorityToWatch = false;
  private boolean isAutoNext = true;
  private int isEdit = 0;
  private boolean isFromPush;
  private boolean isJumpEnd = false;
  private boolean isJumpHead = false;
  private boolean isPlay4K = false;
  private String isSohu;
  private String language = "";
  private String lid;
  private String liveChannelID = "";
  private String liveStreamID = "";
  private String majorActor;
  private String month;
  private String passport;
  private String playInfoSource;
  private String siteName;
  private String st;
  private int startPlayTime;
  private String statCode;
  private String subCategoryID = "";
  private String tvID = "";
  private int tvId;
  private int type = 0;
  private LinkedHashMap<String, PlayUrl> urlMap;
  private HashMap<String, String> urlMapType;
  private String vc;
  private String vidForDlna;
  private String videoID = "";
  private int videoOrder;
  private int videoOrderType = 0;
  private String videoTitle = "";
  private String videoType = "";
  private String vu;
  private String watchType = "1";
  private String year;

  private boolean _setCurrentUrl(PlayUrl paramPlayUrl)
  {
    if (paramPlayUrl == null);
    do
    {
      return false;
      if (("4K".equalsIgnoreCase(this.currentDefinition)) && (DeviceConstants.getInstance().isSupportH265()) && (!TextUtils.isEmpty(paramPlayUrl.getUrl_h265())))
      {
        this.currentUrl = paramPlayUrl.getUrl_h265();
        return true;
      }
    }
    while (TextUtils.isEmpty(paramPlayUrl.getUrl()));
    this.currentUrl = paramPlayUrl.getUrl();
    return true;
  }

  public String getAdSource()
  {
    if (a.a(this.adSource))
      return this.adSource;
    return this.adSource;
  }

  public String getAg()
  {
    return this.ag;
  }

  public String getAlbumID()
  {
    return this.albumID;
  }

  public int getAllCount()
  {
    return this.allCount;
  }

  public String getAr()
  {
    return this.area;
  }

  public String getArea()
  {
    return this.area;
  }

  public String getAuthorityKey()
  {
    return this.authorityKey;
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

  public int getCurrentPosition()
  {
    return this.currentPosition;
  }

  public String getCurrentUrl()
  {
    if (TextUtils.isEmpty(this.currentUrl))
      return "";
    if (!TextUtils.isEmpty(this.authorityKey))
    {
      if (this.currentUrl.contains("?"))
        this.currentUrl = (this.currentUrl + "&fkey=" + this.authorityKey);
    }
    else if (!this.currentUrl.contains("uid="))
      if (!this.currentUrl.contains("?"))
        break label168;
    label168: for (this.currentUrl = (this.currentUrl + "&uid=" + DeviceConstants.getInstance().getUID()); ; this.currentUrl = (this.currentUrl + "?uid=" + DeviceConstants.getInstance().getUID()))
    {
      return this.currentUrl;
      this.currentUrl = (this.currentUrl + "?fkey=" + this.authorityKey);
      break;
    }
  }

  public int getDisplayOrder()
  {
    if (this.videoOrderType == 0);
    for (int i = 1; i != 0; i = 0)
      return 1 + this.videoOrder;
    return this.allCount - this.videoOrder;
  }

  public String getDu()
  {
    return Integer.toString(this.duration);
  }

  public int getDuration()
  {
    return this.duration;
  }

  public int getEndStartTime()
  {
    return this.endStartTime;
  }

  public String getEnterID()
  {
    return this.enterID;
  }

  public int getHeadStartTime()
  {
    return this.headStartTime;
  }

  public String getImgUrl()
  {
    return this.imgUrl;
  }

  public int getIs4K()
  {
    return this.is4K;
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

  public String getLid()
  {
    return this.lid;
  }

  public String getLiveChannelID()
  {
    return this.liveChannelID;
  }

  public String getLiveStreamID()
  {
    return this.liveStreamID;
  }

  public String getMajorActor()
  {
    return this.majorActor;
  }

  public String getMonth()
  {
    return this.month;
  }

  public String getPassport()
  {
    return this.passport;
  }

  public String getPlayInfoSource()
  {
    return this.playInfoSource;
  }

  public String getSiteName()
  {
    return this.siteName;
  }

  public String getSt()
  {
    return this.st;
  }

  public int getStartPlayTime()
  {
    return this.startPlayTime;
  }

  public String getStatCode()
  {
    return this.statCode;
  }

  public String getSubCategoryID()
  {
    return this.subCategoryID;
  }

  public String getTvID()
  {
    return this.tvID;
  }

  public int getTvId()
  {
    return this.tvId;
  }

  public int getType()
  {
    return this.type;
  }

  public LinkedHashMap<String, PlayUrl> getUrlMap()
  {
    return this.urlMap;
  }

  public HashMap<String, String> getUrlMapType()
  {
    return this.urlMapType;
  }

  public String getVc()
  {
    return this.vc;
  }

  public String getVideoID()
  {
    return this.videoID;
  }

  public String getVideoIDForDlna()
  {
    return this.vidForDlna;
  }

  public int getVideoOrder()
  {
    return this.videoOrder;
  }

  public int getVideoOrderType()
  {
    return this.videoOrderType;
  }

  public String getVideoTitle()
  {
    return this.videoTitle;
  }

  public String getVideoType()
  {
    return this.videoType;
  }

  public String getVu()
  {
    return this.vu;
  }

  public String getWatchType()
  {
    return this.watchType;
  }

  public String getYear()
  {
    return this.year;
  }

  public void initCurrentUrl()
  {
    PlayUrl localPlayUrl;
    if ((this.urlMap != null) && (!this.urlMap.isEmpty()))
    {
      localPlayUrl = (PlayUrl)this.urlMap.get(this.currentDefinition);
      if (this.urlMap.get("高清") == null);
    }
    try
    {
      setVideoID(Long.toString(((PlayUrl)this.urlMap.get("高清")).getVid()));
      label68: if (_setCurrentUrl(localPlayUrl));
      label196: 
      while (true)
      {
        return;
        setCurrentDefinition("");
        Iterator localIterator = this.urlMap.keySet().iterator();
        while (true)
        {
          if (!localIterator.hasNext())
            break label196;
          setCurrentDefinition((String)localIterator.next());
          if (("4K".equalsIgnoreCase(this.currentDefinition)) && (!isPlay4K()))
          {
            setCurrentDefinition("");
          }
          else if ("蓝光".equalsIgnoreCase(this.currentDefinition))
          {
            setCurrentDefinition("");
          }
          else
          {
            if (_setCurrentUrl((PlayUrl)this.urlMap.get(this.currentDefinition)))
              break;
            setCurrentDefinition("");
          }
        }
      }
    }
    catch (Exception localException)
    {
      break label68;
    }
  }

  public boolean isAuthorityToWatch()
  {
    return this.isAuthorityToWatch;
  }

  public boolean isAutoNext()
  {
    return this.isAutoNext;
  }

  public boolean isFee()
  {
    return this.fee;
  }

  public boolean isFromPush()
  {
    return this.isFromPush;
  }

  public boolean isHaveAuthority()
  {
    return this.haveAuthority;
  }

  public boolean isJumpEnd()
  {
    return this.isJumpEnd;
  }

  public boolean isJumpHead()
  {
    return this.isJumpHead;
  }

  public boolean isPlay4K()
  {
    return this.isPlay4K;
  }

  public void setAdSource(String paramString)
  {
    this.adSource = paramString;
  }

  public void setAg(String paramString)
  {
    this.ag = paramString;
  }

  public void setAlbumID(String paramString)
  {
    this.albumID = paramString;
  }

  public void setAllCount(int paramInt)
  {
    this.allCount = paramInt;
  }

  public void setArea(String paramString)
  {
    this.area = paramString;
  }

  public void setAuthorityKey(String paramString)
  {
    this.authorityKey = paramString;
  }

  public void setAuthorityToWatch(boolean paramBoolean)
  {
    this.isAuthorityToWatch = paramBoolean;
  }

  public void setAutoNext(boolean paramBoolean)
  {
    this.isAutoNext = paramBoolean;
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
    if ((this.urlMapType != null) && (this.urlMapType.get(paramString) != null))
      setCurrentDefinitionType(Integer.parseInt((String)this.urlMapType.get(paramString)));
  }

  public void setCurrentDefinitionType(int paramInt)
  {
    this.currentDefinitionType = paramInt;
  }

  public void setCurrentPosition(int paramInt)
  {
    this.currentPosition = paramInt;
  }

  public void setCurrentUrl(String paramString)
  {
    this.currentUrl = paramString;
  }

  public void setDuration(int paramInt)
  {
    this.duration = paramInt;
  }

  public void setEndStartTime(int paramInt)
  {
    this.endStartTime = paramInt;
  }

  public void setEnterID(String paramString)
  {
    this.enterID = paramString;
  }

  public void setFee(boolean paramBoolean)
  {
    this.fee = paramBoolean;
  }

  public void setHaveAuthority(boolean paramBoolean)
  {
    this.haveAuthority = paramBoolean;
  }

  public void setHeadStartTime(int paramInt)
  {
    this.headStartTime = paramInt;
  }

  public void setImgUrl(String paramString)
  {
    this.imgUrl = paramString;
  }

  public void setIs4K(int paramInt)
  {
    this.is4K = paramInt;
  }

  public void setIsEdit(int paramInt)
  {
    this.isEdit = paramInt;
  }

  public void setIsFromPush(boolean paramBoolean)
  {
    this.isFromPush = paramBoolean;
  }

  public void setIsSohu(String paramString)
  {
    this.isSohu = paramString;
  }

  public void setJumpEnd(boolean paramBoolean)
  {
    this.isJumpEnd = paramBoolean;
  }

  public void setJumpHead(boolean paramBoolean)
  {
    this.isJumpHead = paramBoolean;
  }

  public void setLanguage(String paramString)
  {
    this.language = paramString;
  }

  public void setLid(String paramString)
  {
    this.lid = paramString;
  }

  public void setLiveChannelID(String paramString)
  {
    this.liveChannelID = paramString;
  }

  public void setLiveStreamID(String paramString)
  {
    this.liveStreamID = paramString;
  }

  public void setMajorActor(String paramString)
  {
    this.majorActor = paramString;
  }

  public void setMonth(String paramString)
  {
    this.month = paramString;
  }

  public void setPassport(String paramString)
  {
    this.passport = paramString;
  }

  public void setPlay4K(boolean paramBoolean)
  {
    if (paramBoolean);
    this.isPlay4K = paramBoolean;
  }

  public void setPlayInfoSource(String paramString)
  {
    this.playInfoSource = paramString;
  }

  public void setSiteName(String paramString)
  {
    this.siteName = paramString;
  }

  public void setSt(String paramString)
  {
    this.st = paramString;
  }

  public void setStartPlayTime(int paramInt)
  {
    this.startPlayTime = paramInt;
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

  public void setTvId(int paramInt)
  {
    this.tvId = paramInt;
  }

  public void setType(int paramInt)
  {
    this.type = paramInt;
  }

  public void setUrlMap(LinkedHashMap<String, PlayUrl> paramLinkedHashMap)
  {
    this.urlMap = paramLinkedHashMap;
  }

  public void setUrlMapType(HashMap<String, String> paramHashMap)
  {
    this.urlMapType = paramHashMap;
  }

  public void setVc(String paramString)
  {
    this.vc = paramString;
  }

  public void setVideoID(String paramString)
  {
    this.videoID = paramString;
  }

  public void setVideoIdForDlna(String paramString)
  {
    this.vidForDlna = paramString;
  }

  public void setVideoOrder(int paramInt)
  {
    this.videoOrder = paramInt;
  }

  public void setVideoOrderType(int paramInt)
  {
    this.videoOrderType = paramInt;
  }

  public void setVideoTitle(String paramString)
  {
    this.videoTitle = paramString;
  }

  public void setVideoType(String paramString)
  {
    this.videoType = paramString;
  }

  public void setVu(String paramString)
  {
    this.vu = paramString;
  }

  public void setWatchType(String paramString)
  {
    this.watchType = paramString;
  }

  public void setYear(String paramString)
  {
    this.year = paramString;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.entity.PlayInfo
 * JD-Core Version:    0.6.2
 */