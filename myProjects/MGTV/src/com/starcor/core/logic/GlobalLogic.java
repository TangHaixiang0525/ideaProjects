package com.starcor.core.logic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.starcor.config.AppFuncCfg;
import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AAAProductItem;
import com.starcor.core.domain.AdPosEntity;
import com.starcor.core.domain.ChannelInfoV2;
import com.starcor.core.domain.CollectListItem;
import com.starcor.core.domain.MediaAssetsInfo;
import com.starcor.core.domain.MetadataGoup;
import com.starcor.core.domain.PlayBillRecommendStrut;
import com.starcor.core.domain.PublicImage;
import com.starcor.core.domain.PurchaseParam;
import com.starcor.core.domain.UiPackage;
import com.starcor.core.domain.UserCenterLogin;
import com.starcor.core.domain.UserInfo;
import com.starcor.core.domain.UserKey;
import com.starcor.core.domain.UserRecommendV2Item;
import com.starcor.core.domain.VideoIndex;
import com.starcor.core.event.GlobalEventNotify;
import com.starcor.core.utils.IOTools;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.DetailPageActivity;
import com.starcor.hunan.DetailPageActivity.DisplayStyle;
import com.starcor.hunan.HotPolymerizationActivity;
import com.starcor.hunan.LiveShowActivity;
import com.starcor.hunan.LiveShowListActivity;
import com.starcor.hunan.PopularMoviePreviewActivity;
import com.starcor.hunan.RankListActivity;
import com.starcor.hunan.SpecialActivityV2;
import com.starcor.hunan.UserFeedbackActivity;
import com.starcor.hunan.XULActivity;
import com.starcor.mgtv.boss.WebUrlBuilder;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GlobalLogic
{
  private static final String KEY_CDN_REPORT_IF1_URL = "report_play_cdn_error_interface1_url";
  private static final String KEY_CDN_REPORT_IF2_URL = "report_play_cdn_error_interface2_url";
  private static final String TAG = "GlobalLogic";
  private static String flagPlayType = null;
  private static GlobalLogic globalLogic = null;
  private static final String mLocalPlayStateConfigPath = GlobalEnv.getInstance().getConfigPath() + File.separator + "LocalPlayRecord.dat";
  private Context appContext = null;
  private String areaCode = "";
  private String autoJumpPage = "";
  private ArrayList<AdPosEntity> bootAdInfoList;
  private String cdnReportIF1Url;
  private String cdnReportIF2Url;
  private ChannelInfoV2 channelInfo;
  private String deviceId = "";
  private String exData = "";
  private String guid = "";
  private HashMap<String, ArrayList<Drawable>> imgMap = new HashMap();
  private HashMap<String, ArrayList<String>> imgPathMap = new HashMap();
  private boolean isAppInterfaceInited = false;
  private boolean isCloseXul = false;
  private boolean isFromOut = false;
  public boolean isGuestAccount = false;
  private Boolean isLoadPageResource = Boolean.valueOf(false);
  boolean isMacCheckOk = false;
  private ArrayList<UserRecommendV2Item> likeList = null;
  private int liveChannelBulletScreenCount = 0;
  private ArrayList<Drawable> loadingImageList = new ArrayList();
  private ArrayList<String> loadingImageUrlList = new ArrayList();
  private String logSecretPrefix = "";
  private String loginVideoID = "";
  private boolean mAutoJumpToDetailedPage = false;
  private String mBulletScreen;
  private String mCurrentSoundTrack;
  private String mJumpVideoContentHeadAndTail;
  private ArrayList<AAAProductItem> mProductList = null;
  private PurchaseParam mPurchaseParam;
  private String mQuality;
  private String mTitleShowInfo;
  private String mVideoLayoutRatio;
  private int media_asset_type = 0;
  private byte[] metaData;
  private ArrayList<MetadataGoup> metadataInfos;
  private String mgtvLoginUserName = "";
  private String mi_userid = "";
  private String netId = "";
  private String nnToken = "";
  private ArrayList<AdPosEntity> playerMenuAdInfoList;
  private ArrayList<CollectListItem> playlist = null;
  private String posId = "";
  private String privateConfigPath;
  private List<PublicImage> productInfoList = null;
  private List<PlayBillRecommendStrut> replayRecommadList;
  private String reqBarrageDataUrl;
  private String searchStarPackageId = "";
  private String smartCardId = "";
  private MediaAssetsInfo timeshiftAssetsInfo;
  private long tokenExpire;
  private UiPackage uiPackage;
  private String userAttr = "";
  private String userId = "";
  private UserInfo userInfo = null;
  private String userName = "";
  private UserCenterLogin verifyTokenInfo = null;
  private LinkedHashMap<String, String> videoQualityMap = new LinkedHashMap();
  private int vodBulletScreenCount = 0;
  private String webToken = "";

  private String buildGuestUserId()
  {
    return ("mgtvmac" + DeviceInfo.getMac()).replace("-", "").replace("_", "").replace(":", "");
  }

  public static GlobalLogic getInstance()
  {
    if (globalLogic == null)
    {
      Logger.i("GlobalLogic", "getInstance First Create");
      globalLogic = new GlobalLogic();
    }
    return globalLogic;
  }

  private String readPreferences(String paramString1, String paramString2)
  {
    try
    {
      String str = PreferenceManager.getDefaultSharedPreferences(this.appContext).getString(paramString1, paramString2);
      return str;
    }
    catch (Exception localException)
    {
    }
    return paramString2;
  }

  private boolean writePreferences(String paramString1, String paramString2)
  {
    try
    {
      SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.appContext).edit();
      localEditor.putString(paramString1, paramString2);
      boolean bool = localEditor.commit();
      return bool;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  public boolean checkAllowExternalToPlay()
  {
    String str = getInstance().getIsEnableIntoPlayerFlag();
    Logger.i("GlobalLogic", "is allowed into player isEnable=" + str);
    if ("0".equals(str))
    {
      Logger.e("GlobalLogic", "is not allowed into player!!");
      return false;
    }
    return true;
  }

  public boolean checkTokenStatus(int paramInt, String paramString)
  {
    if (TextUtils.isEmpty(paramString));
    do
    {
      return false;
      if ((paramInt != 0) && ((paramString.equals("0620")) || (paramString.equals("17003"))))
      {
        Intent localIntent4 = new Intent("ACTION_SHOW_TOKEN_DIALOG");
        localIntent4.putExtra("type", "KICKED");
        this.appContext.sendBroadcast(localIntent4);
        return true;
      }
      if ((paramInt != 0) && ((paramString.equals("0627")) || (paramString.equals("17001"))))
      {
        Intent localIntent3 = new Intent("ACTION_SHOW_TOKEN_DIALOG");
        localIntent3.putExtra("type", "EXPIRED");
        this.appContext.sendBroadcast(localIntent3);
        return true;
      }
      if (((paramInt != 0) && (paramString.equals("0628"))) || ((paramInt != 0) && (paramString.equals("0631"))) || ((paramInt != 0) && (paramString.equals("0680"))) || ((paramInt != 0) && (paramString.equals(""))))
      {
        Intent localIntent2 = new Intent("ACTION_SHOW_NETERROR_DIALOG");
        this.appContext.sendBroadcast(localIntent2);
        return true;
      }
    }
    while (((paramInt == 0) || (!paramString.equals("0617"))) && ((paramInt == 0) || (!paramString.equals("0618"))) && ((paramInt == 0) || (!paramString.equals("0619"))));
    Intent localIntent1 = new Intent("ACTION_SHOW_LICENSE_DIALOG");
    this.appContext.sendBroadcast(localIntent1);
    return true;
  }

  public void clearCouponCount()
  {
    if (this.userInfo != null)
    {
      this.userInfo.common_count = 0;
      this.userInfo.special_count = 0;
      writePreferences("common_count", "0");
      writePreferences("special_count", "0");
    }
  }

  public void clearLatestVideoInfo()
  {
    writePreferences("video_id", "");
    writePreferences("video_name", "");
    writePreferences("video_index", "");
    writePreferences("played_time", "");
    writePreferences("media_assets_id", "");
    writePreferences("category_id", "");
    writePreferences("current_index_release_time", "");
    writePreferences("uiStyle", "");
    writePreferences("img", "");
    writePreferences("ts_day", "");
    writePreferences("ts_begin", "");
    writePreferences("ts_time_len", "");
  }

  public String getAreaCode()
  {
    return this.areaCode;
  }

  public String getAutoJumpPage()
  {
    return this.autoJumpPage;
  }

  public boolean getAutoJumpToDetailedPage()
  {
    return this.mAutoJumpToDetailedPage;
  }

  public ArrayList<AdPosEntity> getBootAdInfoList()
  {
    return this.bootAdInfoList;
  }

  public String getBulletScreenStatus()
  {
    if (this.mBulletScreen == null)
      this.mBulletScreen = readPreferences("BulletScreen", "");
    return this.mBulletScreen;
  }

  public String getCDNReportIF1Url()
  {
    if (TextUtils.isEmpty(this.cdnReportIF1Url))
      this.cdnReportIF1Url = readPreferences("report_play_cdn_error_interface1_url", "");
    return this.cdnReportIF1Url;
  }

  public String getCDNReportIF2Url()
  {
    if (TextUtils.isEmpty(this.cdnReportIF2Url))
      this.cdnReportIF2Url = readPreferences("report_play_cdn_error_interface2_url", "");
    return this.cdnReportIF2Url;
  }

  public ChannelInfoV2 getChannelInfoV2()
  {
    return this.channelInfo;
  }

  public String getCheckWebToken()
  {
    return this.webToken;
  }

  public String getCurrentSoundTrack()
  {
    if (this.mCurrentSoundTrack == null)
      this.mCurrentSoundTrack = readPreferences("currentSoundTrack", "0");
    return this.mCurrentSoundTrack;
  }

  public VideoIndex getCurrentVideoIndex(List<VideoIndex> paramList, int paramInt)
  {
    if ((paramList == null) || (paramInt < 0));
    VideoIndex localVideoIndex;
    do
    {
      Iterator localIterator;
      while (!localIterator.hasNext())
      {
        do
          return null;
        while ((paramList == null) || (paramList.size() <= 0));
        localIterator = paramList.iterator();
      }
      localVideoIndex = (VideoIndex)localIterator.next();
    }
    while (localVideoIndex.index != paramInt);
    return localVideoIndex;
  }

  public String getDateString(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
      return paramString.split(" ")[0].replace("-", "");
    return paramString;
  }

  public String getDeviceId()
  {
    return this.deviceId;
  }

  public DetailPageActivity.DisplayStyle getDisplayStyle(int paramInt, String paramString)
  {
    if ("0".equals(paramString))
      return DetailPageActivity.DisplayStyle.NUMBER_EPISODE;
    if ("m1".equals(paramString))
      return DetailPageActivity.DisplayStyle.WATCH_FOCUS_DATE;
    if ("m2".equals(paramString))
      return DetailPageActivity.DisplayStyle.WATCH_FOCUS;
    if ("m3".equals(paramString))
      return DetailPageActivity.DisplayStyle.WATCH_FOCUS_EPISODE;
    return DetailPageActivity.DisplayStyle.NUMBER_EPISODE;
  }

  public ArrayList<Drawable> getDownLoadImageByType(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return null;
    case 0:
      return (ArrayList)this.imgMap.get("bullet_screen_live_icon");
    case 1:
      return (ArrayList)this.imgMap.get("bullet_screen_vod_icon");
    case 2:
    }
    return (ArrayList)this.imgMap.get("bullet_screen_QR_code_icon");
  }

  public HashMap<String, ArrayList<Drawable>> getDownLoadImageMap()
  {
    return this.imgMap;
  }

  public HashMap<String, ArrayList<String>> getDownLoadImagePathMap()
  {
    return this.imgPathMap;
  }

  public String getExData()
  {
    return this.exData;
  }

  public String getGuid()
  {
    return this.guid;
  }

  public boolean getIsCloseXul()
  {
    return this.isCloseXul;
  }

  public String getIsEnableIntoPlayerFlag()
  {
    return readPreferences("terminal_external_into_player_enabled", "0");
  }

  public String getIsEnableMediasConPlay()
  {
    return readPreferences("terminal_continue_play_enabled", "0");
  }

  public boolean getIsFromOut()
  {
    return this.isFromOut;
  }

  public Boolean getIsLoadPageResource()
  {
    return this.isLoadPageResource;
  }

  public String getJumpVideoContentHeadAndTail()
  {
    if (this.mJumpVideoContentHeadAndTail == null)
      this.mJumpVideoContentHeadAndTail = readPreferences("jumpVideoContentHeadAndTail", "enable");
    return this.mJumpVideoContentHeadAndTail;
  }

  public String getLatestVideoInfo(String paramString)
  {
    return readPreferences(paramString, "");
  }

  public ArrayList<UserRecommendV2Item> getLikeListMediaAssets()
  {
    return this.likeList;
  }

  public int getLiveChannelBulletScreenCount()
  {
    return this.liveChannelBulletScreenCount;
  }

  public ArrayList<String> getLoadingImageUrl()
  {
    return this.loadingImageUrlList;
  }

  public String getLocalPlayStateConfigPath()
  {
    return mLocalPlayStateConfigPath;
  }

  public String getLogSecretPrefix()
  {
    return this.logSecretPrefix;
  }

  public String getLoginedVideoId()
  {
    return this.loginVideoID;
  }

  public int getMediaAssetType()
  {
    return this.media_asset_type;
  }

  public String getMediaModeString(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return "";
    if (paramString.toUpperCase().contains("4K"))
      return getVideoQualityValue("4K");
    if (paramString.toUpperCase().contains("SD"))
      return getVideoQualityValue("SD");
    return "";
  }

  public byte[] getMetaData()
  {
    if ((this.metaData != null) && (this.metaData.length > 16))
      return this.metaData;
    File localFile = new File(GlobalEnv.getInstance().getConfigPath() + File.separator + "InitMetaData.dat");
    if (!localFile.exists())
      return null;
    this.metaData = ((byte[])IOTools.readObject(localFile));
    Logger.i("GlobalLogic", "setMetaData  metaData: " + this.metaData);
    return this.metaData;
  }

  public ArrayList<MetadataGoup> getMetadataInfos()
  {
    ArrayList localArrayList = (ArrayList)IOTools.readObject(new File(GlobalEnv.getInstance().getConfigPath() + File.separator + "mMetadataInfo.dat"));
    Logger.i("GlobalLogic", "getMetadataInfos  mMetadataInfo:" + localArrayList);
    return localArrayList;
  }

  public String getMgtvLoginUserName()
  {
    if (TextUtils.isEmpty(this.mgtvLoginUserName))
      this.mgtvLoginUserName = readPreferences("LoginUserName", "");
    return this.mgtvLoginUserName;
  }

  public ArrayList<MetadataGoup> getN3A2MetaGroups()
  {
    Logger.i("GlobalLogic", "getN3A2MetaGroups");
    if (this.metadataInfos != null)
    {
      Logger.i("GlobalLogic", "metadataInfos!=null");
      return (ArrayList)this.metadataInfos.clone();
    }
    Logger.i("GlobalLogic", "metadataInfos=null");
    return null;
  }

  public String getNetId()
  {
    return this.netId;
  }

  public String getNnToken()
  {
    return this.nnToken;
  }

  public String getPlayerAdId()
  {
    return this.posId;
  }

  public ArrayList<AdPosEntity> getPlayerMenuAdInfoList()
  {
    return this.playerMenuAdInfoList;
  }

  public ArrayList<CollectListItem> getPlaylistByMediaAssets()
  {
    return this.playlist;
  }

  public List<String> getProductInfoByType(String paramString)
  {
    if ((this.productInfoList == null) || (this.productInfoList.isEmpty()));
    label124: 
    while (true)
    {
      return null;
      int i = this.productInfoList.size();
      for (int j = 0; ; j++)
      {
        if (j >= i)
          break label124;
        PublicImage localPublicImage = (PublicImage)this.productInfoList.get(j);
        if (localPublicImage != null)
        {
          String str1 = localPublicImage.name;
          if (TextUtils.isEmpty(str1))
            break;
          String[] arrayOfString = str1.split("_");
          if (arrayOfString.length > 1);
          for (String str2 = arrayOfString[1]; str2.equalsIgnoreCase(paramString); str2 = arrayOfString[0])
            return localPublicImage.url_list;
        }
      }
    }
  }

  public List<PublicImage> getProductInfoList()
  {
    return this.productInfoList;
  }

  public ArrayList<AAAProductItem> getProductList()
  {
    return this.mProductList;
  }

  public PurchaseParam getPurchaseParam()
  {
    return this.mPurchaseParam;
  }

  public String getQuality()
  {
    if (this.mQuality == null)
      this.mQuality = readPreferences("quality", "hd");
    return this.mQuality;
  }

  public Drawable getRandomLoadingImage()
  {
    if ((this.loadingImageList == null) || (this.loadingImageList.size() == 0))
      return null;
    if (this.loadingImageList.size() == 1)
      return (Drawable)this.loadingImageList.get(0);
    int i = new Random().nextInt(this.loadingImageList.size());
    return (Drawable)this.loadingImageList.get(i);
  }

  public List<PlayBillRecommendStrut> getReplayRecommendList()
  {
    return this.replayRecommadList;
  }

  public String getReqBarrageDataUrl()
  {
    if (!TextUtils.isEmpty(this.reqBarrageDataUrl))
      return this.reqBarrageDataUrl;
    this.reqBarrageDataUrl = readPreferences("terminal_request_barrage_url", "");
    return this.reqBarrageDataUrl;
  }

  public String getSearchStarPackageId()
  {
    return this.searchStarPackageId;
  }

  public String getSmartCardId()
  {
    return "";
  }

  public String getSpecialPlayMode(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
      return readPreferences(paramString + "_mode", "list_cycle");
    return "list_cycle";
  }

  public String getTargetDateString(VideoIndex paramVideoIndex)
  {
    String str1 = paramVideoIndex.onlineTime;
    if (!TextUtils.isEmpty(str1))
    {
      str1 = str1.split(" ")[0];
      String[] arrayOfString = str1.split("-");
      if ((arrayOfString != null) && (10 == str1.length()))
      {
        String str2 = arrayOfString[1];
        String str3 = arrayOfString[2];
        str1 = str2 + "." + str3;
      }
    }
    return str1;
  }

  public String getTargetVideoQuality(String paramString)
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramString);
    return getTargetVideoQuality(localArrayList);
  }

  public String getTargetVideoQuality(ArrayList<String> paramArrayList)
  {
    if ((paramArrayList == null) || (paramArrayList.size() == 0))
      return "";
    if (this.videoQualityMap.size() == 0)
      return (String)paramArrayList.get(0);
    String str1 = paramArrayList.toString().toUpperCase();
    if ((String)this.videoQualityMap.get("HD") == null)
      return (String)paramArrayList.get(0);
    if (str1.contains("HD"))
      return "HD";
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    int j = 0;
    Iterator localIterator = this.videoQualityMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str4 = (String)localIterator.next();
      localArrayList.add(str4);
      if ("HD".equals(str4))
        i = j;
      j++;
    }
    for (int k = i + 1; k < localArrayList.size(); k++)
    {
      String str3 = (String)localArrayList.get(k);
      if (str1.contains(str3))
        return str3;
    }
    for (int m = i - 1; m >= 0; m--)
    {
      String str2 = (String)localArrayList.get(m);
      if (str1.contains(str2))
        return str2;
    }
    return (String)paramArrayList.get(0);
  }

  public MediaAssetsInfo getTimeshiftAssetsInfo()
  {
    return this.timeshiftAssetsInfo;
  }

  public String getTitleShowString()
  {
    return this.mTitleShowInfo;
  }

  public long getTokenExpire()
  {
    return this.tokenExpire;
  }

  public UiPackage getUiPackage()
  {
    UiPackage localUiPackage = (UiPackage)IOTools.readObject(new File(GlobalEnv.getInstance().getConfigPath() + File.separator + "UiPackage.dat"));
    Logger.i("GlobalLogic", "getUiPackage uiPackage = " + localUiPackage);
    return localUiPackage;
  }

  public String getUserAttr()
  {
    return this.userAttr;
  }

  public String getUserEmail()
  {
    String str = readPreferences("email", " ");
    Logger.i("GlobalLogic", "getUserEmail()=" + str);
    return str;
  }

  public String getUserId()
  {
    if (TextUtils.isEmpty(this.userId))
      return buildGuestUserId();
    return this.userId;
  }

  public UserInfo getUserInfo()
  {
    if ((this.userInfo == null) && (!TextUtils.isEmpty(this.webToken)))
    {
      this.userInfo = new UserInfo();
      this.userInfo.account = readPreferences("account", "");
      this.userInfo.avatar = readPreferences("avatar", "");
      this.userInfo.device_id = readPreferences("device_id", "");
      this.userInfo.loginMode = readPreferences("loginMode", "");
      this.userInfo.mobile = readPreferences("mobile", "");
      this.userInfo.name = readPreferences("name", "");
      this.userInfo.rtype = readPreferences("rtype", "");
      this.userInfo.vip_id = readPreferences("vip_id", "");
      this.userInfo.net_id = this.netId;
    }
    try
    {
      this.userInfo.vip_power = Integer.parseInt(readPreferences("vip_power", ""));
      this.userInfo.vip_end_date = readPreferences("vip_end_date", "");
      this.userInfo.user_id = this.userId;
      this.userInfo.web_token = this.webToken;
      this.userInfo.wechat_type = readPreferences("wechat_type", "");
      this.userInfo.vip_days = readPreferences("vip_days", "");
      this.userInfo.balance = readPreferences("balance", "");
    }
    catch (Exception localException1)
    {
      try
      {
        this.userInfo.common_count = Integer.parseInt(readPreferences("common_count", ""));
        this.userInfo.special_count = Integer.parseInt(readPreferences("special_count", ""));
        this.userInfo.mi_userid = readPreferences("mi_userid", "");
        this.userInfo.mi_mac_key = readPreferences("mi_mac_key", "");
        this.userInfo.mi_mobile = readPreferences("mi_mobile", "");
        this.userInfo.mi_email = readPreferences("mi_email", "");
        this.userInfo.mi_access_token = readPreferences("mi_access_token", "");
        return this.userInfo;
        localException1 = localException1;
        this.userInfo.vip_power = 0;
      }
      catch (Exception localException2)
      {
        while (true)
        {
          this.userInfo.common_count = 0;
          this.userInfo.special_count = 0;
        }
      }
    }
  }

  public String getUserLoginMode()
  {
    String str = readPreferences("loginMode", " ");
    Logger.i("GlobalLogic", "loginMode()=" + str);
    return str;
  }

  public String getUserName()
  {
    String str1 = readPreferences("wechat_type", "");
    String str2 = readPreferences("account", "");
    String str3 = readPreferences("loginMode", "");
    if (((!TextUtils.isEmpty(str3)) && (str3.equals("mgtv"))) || (!TextUtils.isEmpty(str1)))
      return str2;
    if ("xiaomi".equals(str3))
    {
      if ((TextUtils.isEmpty(this.mi_userid)) || ("null".equalsIgnoreCase(this.mi_userid)))
        return "";
      return this.mi_userid;
    }
    return this.userName;
  }

  public String getUserPrivateConfigPath()
  {
    return this.privateConfigPath;
  }

  public UserCenterLogin getVerifyTokenInfo()
  {
    return this.verifyTokenInfo;
  }

  public String getVideoLayoutRatio()
  {
    if (this.mVideoLayoutRatio == null)
      this.mVideoLayoutRatio = readPreferences("videoLayoutRatio", "16v9");
    return this.mVideoLayoutRatio;
  }

  public String getVideoQualityKey(String paramString)
  {
    if (paramString != null)
    {
      Iterator localIterator = this.videoQualityMap.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        if (paramString.equals(localEntry.getValue()))
          return (String)localEntry.getKey();
      }
    }
    return paramString;
  }

  public String getVideoQualityValue(String paramString)
  {
    if (TextUtils.isEmpty(paramString));
    do
    {
      return paramString;
      if (this.videoQualityMap.size() == 0)
      {
        this.videoQualityMap.put("4K", "4K");
        this.videoQualityMap.put("HD", "高清");
        this.videoQualityMap.put("SD", "超清");
        this.videoQualityMap.put("STD", "标清");
      }
    }
    while (!this.videoQualityMap.containsKey(paramString.toUpperCase(Locale.CHINA)));
    return (String)this.videoQualityMap.get(paramString.toUpperCase(Locale.CHINA));
  }

  public int getVodBulletScreenCount()
  {
    return this.vodBulletScreenCount;
  }

  public String getWebToken()
  {
    return this.webToken;
  }

  public void init(Context paramContext)
  {
    Logger.i("GlobalLogic", "init context:" + paramContext.toString());
    this.appContext = paramContext;
    this.privateConfigPath = (GlobalEnv.getInstance().getConfigPath() + File.separator + "0" + File.separator);
    File localFile = new File(this.privateConfigPath);
    if ((!localFile.exists()) && (!localFile.isDirectory()))
      localFile.mkdir();
    this.userId = readPreferences("user_id", "");
    Logger.i("GlobalLogic", "user_id:" + this.userId);
    this.guid = readPreferences("guid", "");
    this.webToken = readPreferences("web_token", "");
    Logger.i("GlobalLogic", "webToken:" + this.webToken);
    this.userName = readPreferences("user_name", "");
    Logger.i("GlobalLogic", "userName:" + this.userName);
    this.mi_userid = readPreferences("mi_userid", "");
    Logger.i("GlobalLogic", "mi_userid:" + this.mi_userid);
    this.mJumpVideoContentHeadAndTail = readPreferences("jumpVideoContentHeadAndTail", "enable");
    Logger.i("GlobalLogic", "mJumpVideoContentHeadAndTail:" + this.mJumpVideoContentHeadAndTail);
    this.mVideoLayoutRatio = readPreferences("videoLayoutRatio", "16v9");
    Logger.i("GlobalLogic", "mVideoLayoutRatio:" + this.mVideoLayoutRatio);
  }

  public boolean isAppInterfaceReady()
  {
    return this.isAppInterfaceInited;
  }

  public boolean isCanUseMovieCoupon(int paramInt1, int paramInt2)
  {
    if (paramInt2 == 2);
    do
    {
      do
      {
        return false;
        if (paramInt1 > 0)
          return true;
        if ((isVip()) && (paramInt2 == 1))
          return true;
        if (!isUserLogined())
          break;
      }
      while (paramInt2 != 1);
      return true;
    }
    while (paramInt2 != 1);
    return true;
  }

  public boolean isChannelCanUseCoupon(ArrayList<UserKey> paramArrayList)
  {
    int i = 0;
    int j = 0;
    if (paramArrayList != null)
    {
      Iterator localIterator = paramArrayList.iterator();
      while (true)
        if (localIterator.hasNext())
        {
          UserKey localUserKey = (UserKey)localIterator.next();
          if (localUserKey == null)
            continue;
          if (localUserKey.key.equals("coupon"));
          try
          {
            int k = Integer.valueOf(localUserKey.value).intValue();
            i = k;
            if (localUserKey.key.equals("coupon_list"))
            {
              String str2 = localUserKey.value;
              if ((!TextUtils.isEmpty(str2)) && (!"null".equalsIgnoreCase(str2)))
                j = 1;
            }
            if (localUserKey.key.equals("product_list"))
            {
              String str1 = localUserKey.value;
              if ((!TextUtils.isEmpty(str1)) && (!"null".equalsIgnoreCase(str1)))
                this.media_asset_type = 3;
            }
          }
          catch (Exception localException)
          {
            while (true)
              localException.printStackTrace();
          }
        }
    }
    this.media_asset_type = 3;
    return (j != 0) && (i > 0);
  }

  public boolean isMacCheckOK()
  {
    return this.isMacCheckOk;
  }

  public boolean isMediasConPlayEnable()
  {
    return !getIsEnableMediasConPlay().equals("0");
  }

  public boolean isProcessIntent(Intent paramIntent)
  {
    if (!paramIntent.getBooleanExtra("flag_action_from_mgtv", false))
    {
      if (!AppFuncCfg.FUNCTION_ENABLE_EXTERNAL_CONTROL)
      {
        Logger.i("GlobalLogic", "后台配置的外部进入开关 FUNCTION_ENABLE_EXTERNAL_CONTROL未开启");
        return false;
      }
      if (!AppFuncCfg.FUNCTION_ENABLE_FACTORY_INTERFACE)
      {
        Logger.i("GlobalLogic", "该版本不支持外部进入apkFUNCTION_ENABLE_FACTORY_INTERFACE");
        return false;
      }
    }
    return true;
  }

  public boolean isUserCenterPage(Activity paramActivity)
  {
    return ((paramActivity instanceof XULActivity)) && (!(paramActivity instanceof DetailPageActivity)) && (!(paramActivity instanceof HotPolymerizationActivity)) && (!(paramActivity instanceof LiveShowActivity)) && (!(paramActivity instanceof LiveShowListActivity)) && (!(paramActivity instanceof PopularMoviePreviewActivity)) && (!(paramActivity instanceof RankListActivity)) && (!(paramActivity instanceof SpecialActivityV2)) && (!(paramActivity instanceof UserFeedbackActivity));
  }

  public boolean isUserLogined()
  {
    return (this.webToken != null) && (this.webToken.length() > 0);
  }

  public boolean isVip()
  {
    return (getInstance().isUserLogined()) && (this.userInfo != null) && ("1".equals(this.userInfo.vip_id));
  }

  public String readPackageVersion()
  {
    return readPreferences("package_version", "");
  }

  public void saveCouponCount(int paramInt1, int paramInt2)
  {
    if (this.userInfo != null)
    {
      this.userInfo.common_count = paramInt1;
      this.userInfo.special_count = paramInt2;
      writePreferences("common_count", paramInt1 + "");
      writePreferences("special_count", paramInt2 + "");
    }
  }

  public void saveDownLoadImage(HashMap<String, ArrayList<Drawable>> paramHashMap)
  {
    this.imgMap = paramHashMap;
  }

  public void saveDownLoadImageUrl(String paramString, List<String> paramList)
  {
    if (this.imgPathMap != null)
      this.imgPathMap.put(paramString, (ArrayList)paramList);
  }

  public void saveEnableMediasConPlay(String paramString)
  {
    writePreferences("terminal_continue_play_enabled", paramString);
  }

  public void saveIsEnableIntoPlayerFlag(String paramString)
  {
    writePreferences("terminal_external_into_player_enabled", paramString);
  }

  public void saveLatestVideoInfo(CollectListItem paramCollectListItem)
  {
    writePreferences("video_id", paramCollectListItem.video_id);
    writePreferences("video_name", paramCollectListItem.video_name);
    writePreferences("video_index", String.valueOf(paramCollectListItem.video_index));
    writePreferences("played_time", String.valueOf(paramCollectListItem.played_time));
    writePreferences("media_assets_id", paramCollectListItem.media_assets_id);
    writePreferences("category_id", paramCollectListItem.category_id);
    writePreferences("current_index_release_time", paramCollectListItem.current_index_release_time);
    writePreferences("uiStyle", String.valueOf(paramCollectListItem.uiStyle));
    writePreferences("img", paramCollectListItem.img_v);
    writePreferences("ts_day", paramCollectListItem.ts_day);
    writePreferences("ts_begin", paramCollectListItem.ts_begin);
    writePreferences("ts_time_len", paramCollectListItem.ts_time_len);
  }

  public void saveLoadingImage(ArrayList<Drawable> paramArrayList)
  {
    this.loadingImageList.addAll(paramArrayList);
  }

  public void saveLoadingImageUrl(List<String> paramList)
  {
    this.loadingImageUrlList.addAll(paramList);
  }

  public void saveUserInfo()
  {
    writePreferences("user_id", this.userId);
    writePreferences("web_token", this.webToken);
    if (!"guest".equals(this.userName))
      writePreferences("user_name", this.userName);
    if (this.userInfo == null)
      this.userInfo = new UserInfo();
    writePreferences("account", this.userInfo.account);
    writePreferences("avatar", this.userInfo.avatar);
    writePreferences("device_id", this.userInfo.device_id);
    writePreferences("ex_data", this.userInfo.ex_data);
    writePreferences("loginMode", this.userInfo.loginMode);
    writePreferences("mac_id", this.userInfo.mac_id);
    writePreferences("mobile", this.userInfo.mobile);
    writePreferences("name", this.userInfo.name);
    writePreferences("nn_token", this.userInfo.nn_token);
    writePreferences("rtype", this.userInfo.rtype);
    writePreferences("wechat_type", this.userInfo.wechat_type);
    writePreferences("vip_id", this.userInfo.vip_id);
    writePreferences("vip_power", this.userInfo.vip_power + "");
    writePreferences("vip_end_date", this.userInfo.vip_end_date);
    writePreferences("vip_days", this.userInfo.vip_days + "");
    writePreferences("balance", this.userInfo.balance + "");
    writePreferences("email", this.userInfo.email + "");
    if (this.userInfo.vip_power > 0);
    while (true)
    {
      writePreferences("mi_mac_key", this.userInfo.mi_mac_key + "");
      writePreferences("mi_email", this.userInfo.mi_email + "");
      writePreferences("mi_mobile", this.userInfo.mi_mobile + "");
      writePreferences("mi_userid", this.userInfo.mi_userid + "");
      writePreferences("mi_access_token", this.userInfo.mi_access_token + "");
      return;
      if (TextUtils.isEmpty(WebUrlBuilder.getAdInfoUrl()))
        AppFuncCfg.FUNCTION_ENABLE_AD = false;
      else
        AppFuncCfg.FUNCTION_ENABLE_AD = true;
    }
  }

  public void setAppInterfaceReady(boolean paramBoolean)
  {
    Logger.i("GlobalLogic", "setAppInterfaceReady = " + paramBoolean);
    this.isAppInterfaceInited = paramBoolean;
  }

  public void setAreaCode(String paramString)
  {
    this.areaCode = paramString;
  }

  public void setAutoJumpPage(String paramString)
  {
    this.autoJumpPage = paramString;
  }

  public void setAutoJumpToDetailedPage(boolean paramBoolean)
  {
    this.mAutoJumpToDetailedPage = paramBoolean;
  }

  public void setBootAdInfoList(ArrayList<AdPosEntity> paramArrayList)
  {
    this.bootAdInfoList = paramArrayList;
  }

  public void setBulletScreenStatus(String paramString)
  {
    this.mBulletScreen = paramString;
    writePreferences("BulletScreen", this.mBulletScreen);
  }

  public void setCDNReportIF1Url(String paramString)
  {
    writePreferences("report_play_cdn_error_interface1_url", paramString);
  }

  public void setCDNReportIF2Url(String paramString)
  {
    writePreferences("report_play_cdn_error_interface2_url", paramString);
  }

  public void setChannelInfoV2(ChannelInfoV2 paramChannelInfoV2)
  {
    this.channelInfo = paramChannelInfoV2;
  }

  public void setCurrentSoundTrack(String paramString)
  {
    this.mCurrentSoundTrack = paramString;
    writePreferences("currentSoundTrack", this.mCurrentSoundTrack);
  }

  public void setGuid(String paramString)
  {
    if (paramString != null)
    {
      this.guid = paramString;
      writePreferences("guid", paramString);
    }
  }

  public void setIsCloseXul(boolean paramBoolean)
  {
    this.isCloseXul = paramBoolean;
  }

  public void setIsFromOut(boolean paramBoolean)
  {
    this.isFromOut = paramBoolean;
  }

  public void setIsLoadPageResource(Boolean paramBoolean)
  {
    this.isLoadPageResource = paramBoolean;
  }

  public void setJumpVideoContentHeadAndTail(String paramString)
  {
    this.mJumpVideoContentHeadAndTail = paramString;
    writePreferences("jumpVideoContentHeadAndTail", this.mJumpVideoContentHeadAndTail);
  }

  public void setLikeListByMediaAssets(ArrayList<UserRecommendV2Item> paramArrayList)
  {
    this.likeList = paramArrayList;
  }

  public void setLiveChannelBulletScreenCount(int paramInt)
  {
    this.liveChannelBulletScreenCount = paramInt;
  }

  public void setLogSecretPrefix(String paramString)
  {
    this.logSecretPrefix = paramString;
  }

  public void setLoginedVideoId(String paramString)
  {
    this.loginVideoID = paramString;
  }

  public void setMediaAssetType(int paramInt)
  {
    this.media_asset_type = paramInt;
  }

  public void setMetaData(byte[] paramArrayOfByte)
  {
    Logger.i("GlobalLogic", "setMetaData  data:" + paramArrayOfByte);
    IOTools.writeObject(new File(GlobalEnv.getInstance().getConfigPath() + File.separator + "InitMetaData.dat"), paramArrayOfByte);
    this.metaData = paramArrayOfByte;
  }

  public void setMgtvLoginUserName(String paramString)
  {
    this.mgtvLoginUserName = paramString;
    writePreferences("LoginUserName", this.mgtvLoginUserName);
  }

  public void setN3A2MetaGroups(ArrayList<MetadataGoup> paramArrayList)
  {
    this.metadataInfos = paramArrayList;
    writeMetadataInfos(paramArrayList);
    if (paramArrayList != null)
      Logger.i("GlobalLogic", "setN3A2MetaGroups:" + paramArrayList.toString());
  }

  public void setNetId(String paramString)
  {
    this.netId = paramString;
  }

  public void setPlayerAdId(String paramString)
  {
    this.posId = paramString;
  }

  public void setPlayerMenuAdInfoList(ArrayList<AdPosEntity> paramArrayList)
  {
    this.playerMenuAdInfoList = paramArrayList;
  }

  public void setPlaylistByMediaAssets(ArrayList<CollectListItem> paramArrayList)
  {
    this.playlist = paramArrayList;
  }

  public void setProductInfo(List<PublicImage> paramList)
  {
    if ((paramList == null) || (paramList.isEmpty()))
      return;
    this.productInfoList = new ArrayList();
    this.productInfoList.addAll(paramList);
  }

  public void setProductList(ArrayList<UserKey> paramArrayList)
  {
    if ((paramArrayList == null) || (paramArrayList.size() <= 0))
      this.mProductList = null;
    while (true)
    {
      return;
      this.mProductList = new ArrayList();
      Iterator localIterator = paramArrayList.iterator();
      label562: label692: 
      while (localIterator.hasNext())
      {
        UserKey localUserKey = (UserKey)localIterator.next();
        ArrayList localArrayList;
        while (true)
        {
          AAAProductItem localAAAProductItem;
          try
          {
            if ((!localUserKey.key.equals("vip_list")) && (!localUserKey.key.equals("product_list")) && (!localUserKey.key.equals("coupon_list")))
              break label620;
            JSONArray localJSONArray = new JSONArray(URLDecoder.decode(localUserKey.value, "utf-8"));
            localArrayList = new ArrayList();
            int i = 0;
            if (i >= localJSONArray.length())
              break label580;
            localAAAProductItem = new AAAProductItem();
            JSONObject localJSONObject = localJSONArray.getJSONObject(i);
            if (localJSONObject.has("description"))
              localAAAProductItem.desc = localJSONObject.getString("description");
            if (localJSONObject.has("asset_name"))
              localAAAProductItem.asset_name = localJSONObject.getString("asset_name");
            if (localJSONObject.has("count"))
              localAAAProductItem.count = Integer.valueOf(localJSONObject.getString("count")).intValue();
            if (localUserKey.key.equals("coupon_list"))
            {
              localAAAProductItem.product_type = 1;
              if (localJSONObject.has("price"))
                localAAAProductItem.price = Float.valueOf(localJSONObject.getString("price")).floatValue();
              if (localJSONObject.has("price_show"))
                localAAAProductItem.price_show = Float.valueOf(localJSONObject.getString("price_show")).floatValue();
              if (localJSONObject.has("id"))
                localAAAProductItem.productId = Integer.valueOf(localJSONObject.getString("id")).intValue();
              if (localJSONObject.has("product_id"))
                localAAAProductItem.productId = Integer.valueOf(localJSONObject.getString("product_id")).intValue();
              if (localJSONObject.has("name"))
                localAAAProductItem.name = localJSONObject.getString("name");
              if (localJSONObject.has("button_name"))
                localAAAProductItem.button_name = localJSONObject.getString("button_name");
              if (localJSONObject.has("time"))
                localAAAProductItem.time = localJSONObject.getString("time");
              if (!localJSONObject.has("type"))
                break label571;
              localAAAProductItem.type = Integer.valueOf(localJSONObject.getString("type")).intValue();
              localArrayList.add(localAAAProductItem);
              i++;
              continue;
            }
            if (!localUserKey.key.equals("product_list"))
              break label562;
            if (!localJSONObject.has("type"))
              continue;
            if ("1".equals(localJSONObject.getString("type")))
            {
              localAAAProductItem.product_type = 2;
              continue;
            }
          }
          catch (JSONException localJSONException)
          {
            localJSONException.printStackTrace();
            break;
            localAAAProductItem.product_type = 3;
            continue;
          }
          catch (UnsupportedEncodingException localUnsupportedEncodingException)
          {
            localUnsupportedEncodingException.printStackTrace();
          }
          break;
          localAAAProductItem.product_type = 0;
          continue;
          label571: localAAAProductItem.type = 0;
        }
        label580: if (localUserKey.key.equals("vip_list"))
        {
          this.mProductList.addAll(0, localArrayList);
        }
        else
        {
          this.mProductList.addAll(localArrayList);
          continue;
          label620: boolean bool = localUserKey.key.equals("asset_type");
          if (bool)
          {
            try
            {
              if ((TextUtils.isEmpty(localUserKey.value)) || ("asset_type".equals(localUserKey.value)))
                break label692;
              this.media_asset_type = Integer.valueOf(localUserKey.value).intValue();
            }
            catch (Exception localException)
            {
              this.media_asset_type = 2;
              localException.printStackTrace();
            }
            continue;
            this.media_asset_type = 2;
          }
        }
      }
    }
  }

  public void setPurchaseParam(PurchaseParam paramPurchaseParam)
  {
    this.mPurchaseParam = paramPurchaseParam;
  }

  public void setQuality(String paramString)
  {
    this.mQuality = paramString;
    writePreferences("quality", this.mQuality);
  }

  public void setReplayRecommendList(List<PlayBillRecommendStrut> paramList)
  {
    this.replayRecommadList = paramList;
  }

  public void setReqBarrageDataUrl(String paramString)
  {
    writePreferences("terminal_request_barrage_url", paramString);
  }

  public void setSearchStarPackageId(String paramString)
  {
    this.searchStarPackageId = paramString;
  }

  public void setSmartCardId(String paramString)
  {
    this.smartCardId = paramString;
  }

  public void setSpecialPlayMode(String paramString1, String paramString2)
  {
    if (!TextUtils.isEmpty(paramString1))
      writePreferences(paramString1 + "_mode", paramString2);
  }

  public void setTimeshiftAssetsInfo(MediaAssetsInfo paramMediaAssetsInfo)
  {
    this.timeshiftAssetsInfo = paramMediaAssetsInfo;
  }

  public void setTitleShowString(String paramString)
  {
    this.mTitleShowInfo = paramString;
  }

  public void setTokenExpire(long paramLong)
  {
    this.tokenExpire = paramLong;
  }

  public void setUiPackage(UiPackage paramUiPackage)
  {
    Logger.i("GlobalLogic", "setUiPackage  uiPackage:" + paramUiPackage);
    IOTools.writeObject(new File(GlobalEnv.getInstance().getConfigPath() + File.separator + "UiPackage.dat"), paramUiPackage);
  }

  public void setUserAttr(String paramString)
  {
    this.userAttr = paramString;
  }

  public void setUserId(String paramString)
  {
    this.userId = paramString;
  }

  public void setVerifyTokenInfo(UserCenterLogin paramUserCenterLogin)
  {
    this.verifyTokenInfo = paramUserCenterLogin;
  }

  public void setVideoLayoutRatio(String paramString)
  {
    this.mVideoLayoutRatio = paramString;
    writePreferences("videoLayoutRatio", this.mVideoLayoutRatio);
  }

  public void setVideoQualityString(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return;
    String[] arrayOfString = paramString.split(",");
    int i = arrayOfString.length;
    int j = 0;
    label22: String str;
    if (j < i)
    {
      str = arrayOfString[j];
      if ((!TextUtils.isEmpty(str)) && (str.contains("=")) && (!str.endsWith("=")))
        break label70;
    }
    while (true)
    {
      j++;
      break label22;
      break;
      label70: this.videoQualityMap.put(str.substring(0, str.indexOf("=")).toUpperCase(Locale.CHINA), str.substring(1 + str.indexOf("=")));
      Logger.i("GlobalLogic", "videoQuality: " + this.videoQualityMap.toString());
    }
  }

  public void setVodBulletScreenCount(int paramInt)
  {
    this.vodBulletScreenCount = paramInt;
  }

  public void userLogin(UserInfo paramUserInfo)
  {
    this.userInfo = paramUserInfo;
    if (paramUserInfo != null)
    {
      Logger.i("GlobalLogic", "userLogin " + paramUserInfo.toString());
      this.userId = paramUserInfo.user_id;
      this.exData = paramUserInfo.ex_data;
      this.deviceId = paramUserInfo.device_id;
      this.webToken = paramUserInfo.web_token;
      this.userName = paramUserInfo.name;
      this.mi_userid = paramUserInfo.mi_userid;
      this.nnToken = paramUserInfo.nn_token;
      if ((TextUtils.isEmpty(this.webToken)) && ("1".equals(this.userId)))
      {
        this.userId = buildGuestUserId();
        Logger.i("GlobalLogic", "userLogin new guest userId:" + this.userId);
      }
      if ((this.userId != null) && (this.userId.length() > 0))
        this.isMacCheckOk = true;
      if ((readPreferences("web_token", "") == null) || (TextUtils.isEmpty(readPreferences("web_token", ""))))
        this.isGuestAccount = true;
      saveUserInfo();
      if (paramUserInfo.vip_power > 0);
      while (true)
      {
        this.privateConfigPath = (GlobalEnv.getInstance().getConfigPath() + File.separator + this.userId + File.separator);
        File localFile = new File(this.privateConfigPath);
        if ((!localFile.exists()) && (!localFile.isDirectory()))
          localFile.mkdir();
        Logger.i("GlobalLogic", "userLogin path:" + this.privateConfigPath);
        GlobalEventNotify.getInstance().onUserLogin();
        Intent localIntent = new Intent("login_in_succeed");
        localIntent.putExtra("loginMode", paramUserInfo.loginMode);
        App.getAppContext().sendBroadcast(localIntent);
        return;
        if (TextUtils.isEmpty(WebUrlBuilder.getAdInfoUrl()))
          AppFuncCfg.FUNCTION_ENABLE_AD = false;
        else
          AppFuncCfg.FUNCTION_ENABLE_AD = true;
      }
    }
    Logger.e("GlobalLogic", "userLogin is null");
  }

  public void userLogout()
  {
    Logger.i("GlobalLogic", "userLogout user_id:" + this.userId + ", web_token:" + this.webToken);
    this.webToken = "";
    if (TextUtils.isEmpty(WebUrlBuilder.getAdInfoUrl()));
    for (AppFuncCfg.FUNCTION_ENABLE_AD = false; ; AppFuncCfg.FUNCTION_ENABLE_AD = true)
    {
      this.userId = buildGuestUserId();
      this.exData = "";
      this.deviceId = "";
      this.userName = "";
      this.mi_userid = "";
      this.userInfo = null;
      writePreferences("web_token", "");
      writePreferences("user_id", "");
      writePreferences("user_name", "");
      writePreferences("account", "");
      writePreferences("avatar", "");
      writePreferences("device_id", "");
      writePreferences("ex_data", "");
      writePreferences("mac_id", "");
      writePreferences("mobile", "");
      writePreferences("name", "");
      writePreferences("nn_token", "");
      writePreferences("rtype", "");
      writePreferences("vip_id", "");
      writePreferences("vip_power", "");
      writePreferences("vip_begin_date", "");
      writePreferences("vip_end_date", "");
      writePreferences("wechat_type", "");
      writePreferences("vip_days", "");
      writePreferences("balance", "");
      writePreferences("email", "");
      writePreferences("mi_mac_key", "");
      writePreferences("mi_email", "");
      writePreferences("mi_mobile", "");
      writePreferences("mi_userid", "");
      writePreferences("mi_access_token", "");
      clearCouponCount();
      this.privateConfigPath = (GlobalEnv.getInstance().getConfigPath() + File.separator + "0" + File.separator);
      File localFile = new File(this.privateConfigPath);
      if ((!localFile.exists()) && (!localFile.isDirectory()))
      {
        Logger.i("GlobalLogic", "privateConfigPath=" + this.privateConfigPath + " does not exist! Begin to make this directory!");
        localFile.mkdir();
      }
      GlobalEventNotify.getInstance().onUserLogout();
      return;
    }
  }

  public void userWebLogined(String paramString)
  {
    Logger.i("GlobalLogic", "userWebLogined webToken:" + paramString);
    this.webToken = paramString;
  }

  public void writeMetadataInfos(ArrayList<MetadataGoup> paramArrayList)
  {
    Logger.i("GlobalLogic", "writeMetadataInfos  mMetadataInfo:" + paramArrayList);
    IOTools.writeObject(new File(GlobalEnv.getInstance().getConfigPath() + File.separator + "mMetadataInfo.dat"), paramArrayList);
  }

  public void writePackageVersion(String paramString)
  {
    writePreferences("package_version", paramString);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.logic.GlobalLogic
 * JD-Core Version:    0.6.2
 */