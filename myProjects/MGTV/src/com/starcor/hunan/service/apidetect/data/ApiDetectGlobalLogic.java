package com.starcor.hunan.service.apidetect.data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AAAProductItem;
import com.starcor.core.domain.AdPosEntity;
import com.starcor.core.domain.ChannelInfoV2;
import com.starcor.core.domain.MediaAssetsInfo;
import com.starcor.core.domain.MetadataGoup;
import com.starcor.core.domain.PlayBillRecommendStrut;
import com.starcor.core.domain.PublicImage;
import com.starcor.core.domain.PurchaseParam;
import com.starcor.core.domain.UiPackage;
import com.starcor.core.domain.UserInfo;
import com.starcor.core.domain.UserKey;
import com.starcor.core.event.GlobalEventNotify;
import com.starcor.core.utils.IOTools;
import com.starcor.core.utils.Logger;
import com.starcor.system.provider.HWInfoReader;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ApiDetectGlobalLogic
{
  private static final String TAG = ApiDetectGlobalLogic.class.getSimpleName();
  private static ApiDetectGlobalLogic globalLogic = null;
  private Context appContext = null;
  private ArrayList<AdPosEntity> bootAdInfoList;
  private ChannelInfoV2 channelInfo;
  private String deviceId = "";
  private String epgServer = "";
  private String exData = "";
  private boolean isAppInterfaceInited = false;
  private boolean isCloseXul = false;
  boolean isMacCheckOk = false;
  private String logSecretPrefix = "";
  private boolean mAutoJumpToDetailedPage = false;
  private String mCurrentSoundTrack;
  private String mJumpVideoContentHeadAndTail;
  private ArrayList<AAAProductItem> mProductList = null;
  private PurchaseParam mPurchaseParam;
  private String mQuality;
  private String mVideoLayoutRatio;
  private byte[] metaData;
  private ArrayList<MetadataGoup> metadataInfos;
  private String netId = "";
  private String nnToken = "";
  private ArrayList<AdPosEntity> playerMenuAdInfoList;
  private String posId = "";
  private String privateConfigPath;
  private List<PublicImage> productInfoList = null;
  private List<PlayBillRecommendStrut> replayRecommadList;
  private String searchStarPackageId = "";
  private String smartCardId = "";
  private MediaAssetsInfo timeshiftAssetsInfo;
  private long tokenExpire;
  private UiPackage uiPackage;
  private String userId = "";
  private UserInfo userInfo = null;
  private String userName = "";
  private HashMap<String, String> videoQualityMap = new HashMap();
  private String webToken = "";

  private String buildGuestUserId()
  {
    return ("mgtvmac" + DeviceInfo.getMac()).replace("-", "").replace("_", "").replace(":", "");
  }

  public static ApiDetectGlobalLogic getInstance()
  {
    if (globalLogic == null)
    {
      Logger.i(TAG, "getInstance First Create");
      globalLogic = new ApiDetectGlobalLogic();
    }
    return globalLogic;
  }

  private String readPreferences(String paramString1, String paramString2)
  {
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.appContext);
    try
    {
      String str = localSharedPreferences.getString(paramString1, paramString2);
      return str;
    }
    catch (Exception localException)
    {
    }
    return paramString2;
  }

  private boolean writePreferences(String paramString1, String paramString2)
  {
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.appContext).edit();
    try
    {
      localEditor.putString(paramString1, paramString2);
      boolean bool = localEditor.commit();
      return bool;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  public void checkTokenStatus(int paramInt, String paramString)
  {
    if ((paramInt != 0) && (paramString.equals("0620")))
    {
      Intent localIntent4 = new Intent("ACTION_SHOW_TOKEN_DIALOG");
      localIntent4.putExtra("type", "KICKED");
      this.appContext.sendBroadcast(localIntent4);
    }
    do
    {
      return;
      if ((paramInt != 0) && (paramString.equals("0627")))
      {
        Intent localIntent3 = new Intent("ACTION_SHOW_TOKEN_DIALOG");
        localIntent3.putExtra("type", "EXPIRED");
        this.appContext.sendBroadcast(localIntent3);
        return;
      }
      if (((paramInt != 0) && (paramString.equals("0628"))) || ((paramInt != 0) && (paramString.equals("0631"))) || ((paramInt != 0) && (paramString.equals("0680"))) || ((paramInt != 0) && (paramString.equals(""))))
      {
        Intent localIntent2 = new Intent("ACTION_SHOW_NETERROR_DIALOG");
        this.appContext.sendBroadcast(localIntent2);
        return;
      }
    }
    while (((paramInt == 0) || (!paramString.equals("0617"))) && ((paramInt == 0) || (!paramString.equals("0618"))) && ((paramInt == 0) || (!paramString.equals("0619"))));
    Intent localIntent1 = new Intent("ACTION_SHOW_LICENSE_DIALOG");
    this.appContext.sendBroadcast(localIntent1);
  }

  public boolean getAutoJumpToDetailedPage()
  {
    return this.mAutoJumpToDetailedPage;
  }

  public ArrayList<AdPosEntity> getBootAdInfoList()
  {
    return this.bootAdInfoList;
  }

  public ChannelInfoV2 getChannelInfoV2()
  {
    return this.channelInfo;
  }

  public String getCheckWebToken()
  {
    if (ApiDetectAppFuncCfg.FUNCTION_ENABLE_FJYD_AUTHERTICATION)
      return HWInfoReader.getInstance(this.appContext).getToken();
    return this.webToken;
  }

  public String getCurrentSoundTrack()
  {
    if (this.mCurrentSoundTrack == null)
      this.mCurrentSoundTrack = readPreferences("currentSoundTrack", "0");
    return this.mCurrentSoundTrack;
  }

  public String getDeviceId()
  {
    return this.deviceId;
  }

  public String getExData()
  {
    return this.exData;
  }

  public String getFJYDEpgServer()
  {
    if (ApiDetectAppFuncCfg.FUNCTION_ENABLE_FJYD_AUTHERTICATION)
    {
      this.epgServer = HWInfoReader.getInstance(this.appContext).getEpgServer();
      return this.epgServer;
    }
    return "";
  }

  public boolean getIsCloseXul()
  {
    return this.isCloseXul;
  }

  public String getJumpVideoContentHeadAndTail()
  {
    if (this.mJumpVideoContentHeadAndTail == null)
      this.mJumpVideoContentHeadAndTail = readPreferences("jumpVideoContentHeadAndTail", "enable");
    return this.mJumpVideoContentHeadAndTail;
  }

  public String getLogSecretPrefix()
  {
    return this.logSecretPrefix;
  }

  public byte[] getMetaData()
  {
    return this.metaData;
  }

  public ArrayList<MetadataGoup> getMetadataInfos()
  {
    ArrayList localArrayList = (ArrayList)IOTools.readObject(new File(ApiDetectGlobalEnv.getInstance().getConfigPath() + File.separator + "mMetadataInfo.dat"));
    Logger.i(TAG, "getMetadataInfos  mMetadataInfo:" + localArrayList);
    return localArrayList;
  }

  public ArrayList<MetadataGoup> getN3A2MetaGroups()
  {
    Logger.i(TAG, "getN3A2MetaGroups");
    if (this.metadataInfos != null)
    {
      Logger.i(TAG, "metadataInfos!=null");
      return (ArrayList)this.metadataInfos.clone();
    }
    Logger.i(TAG, "metadataInfos=null");
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

  public List<String> getProductInfoByType(String paramString)
  {
    if ((this.productInfoList == null) || (this.productInfoList.isEmpty()));
    label123: 
    while (true)
    {
      return null;
      int i = this.productInfoList.size();
      for (int j = 0; ; j++)
      {
        if (j >= i)
          break label123;
        PublicImage localPublicImage = (PublicImage)this.productInfoList.get(j);
        if (localPublicImage != null)
        {
          String str1 = localPublicImage.name;
          if (TextUtils.isEmpty(str1))
            break;
          String[] arrayOfString = str1.split("_");
          if (arrayOfString.length > 1);
          for (String str2 = arrayOfString[1]; str2.equals(paramString); str2 = arrayOfString[0])
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

  public List<PlayBillRecommendStrut> getReplayRecommendList()
  {
    return this.replayRecommadList;
  }

  public String getSearchStarPackageId()
  {
    return this.searchStarPackageId;
  }

  public String getSmartCardId()
  {
    return "";
  }

  public MediaAssetsInfo getTimeshiftAssetsInfo()
  {
    return this.timeshiftAssetsInfo;
  }

  public long getTokenExpire()
  {
    return this.tokenExpire;
  }

  public UiPackage getUiPackage()
  {
    return this.uiPackage;
  }

  public String getUserEmail()
  {
    String str = readPreferences("email", " ");
    Logger.i(TAG, "getUserEmail()=" + str);
    return str;
  }

  public String getUserId()
  {
    if ((ApiDetectAppFuncCfg.FUNCTION_NEW_USER_CENTER_PAGE) && (TextUtils.isEmpty(this.userId)))
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
      return this.userInfo;
    }
    catch (Exception localException)
    {
      while (true)
        this.userInfo.vip_power = 0;
    }
  }

  public String getUserName()
  {
    if ((ApiDetectAppFuncCfg.IS_VERSION_MANGO_DOWNLOAD) && (ApiDetectAppFuncCfg.FUNCTION_NEW_USER_CENTER_PAGE))
    {
      String str1 = readPreferences("wechat_type", "");
      String str2 = readPreferences("account", "");
      String str3 = readPreferences("loginMode", "");
      if (((!TextUtils.isEmpty(str3)) && (str3.equals("mgtv"))) || (!TextUtils.isEmpty(str1)))
        return str2;
      return this.userName;
    }
    return this.userName;
  }

  public String getUserPrivateConfigPath()
  {
    return this.privateConfigPath;
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
        this.videoQualityMap.put("LOW", "超清");
        this.videoQualityMap.put("STD", "标清");
      }
    }
    while (!this.videoQualityMap.containsKey(paramString.toUpperCase(Locale.CHINA)));
    return (String)this.videoQualityMap.get(paramString.toUpperCase(Locale.CHINA));
  }

  public String getWebToken()
  {
    return this.webToken;
  }

  public void init(Context paramContext)
  {
    Logger.i(TAG, "init context:" + paramContext.toString());
    this.appContext = paramContext;
    this.privateConfigPath = (ApiDetectGlobalEnv.getInstance().getConfigPath() + File.separator + "0" + File.separator);
    File localFile = new File(this.privateConfigPath);
    if ((!localFile.exists()) && (!localFile.isDirectory()))
      localFile.mkdir();
    this.userId = readPreferences("user_id", "");
    Logger.i(TAG, "user_id:" + this.userId);
    this.webToken = readPreferences("web_token", "");
    Logger.i(TAG, "webToken:" + this.webToken);
    this.userName = readPreferences("user_name", "");
    Logger.i(TAG, "userName:" + this.userName);
    this.mJumpVideoContentHeadAndTail = readPreferences("jumpVideoContentHeadAndTail", "enable");
    Logger.i(TAG, "mJumpVideoContentHeadAndTail:" + this.mJumpVideoContentHeadAndTail);
    this.mVideoLayoutRatio = readPreferences("videoLayoutRatio", "16v9");
    Logger.i(TAG, "mVideoLayoutRatio:" + this.mVideoLayoutRatio);
  }

  public boolean isAppInterfaceReady()
  {
    return this.isAppInterfaceInited;
  }

  public boolean isMacCheckOK()
  {
    return this.isMacCheckOk;
  }

  public boolean isUserLogined()
  {
    return (this.webToken != null) && (this.webToken.length() > 0);
  }

  public void setAppInterfaceReady()
  {
    Logger.i(TAG, "setAppInterfaceReady");
    this.isAppInterfaceInited = true;
  }

  public void setAutoJumpToDetailedPage(boolean paramBoolean)
  {
    this.mAutoJumpToDetailedPage = paramBoolean;
  }

  public void setBootAdInfoList(ArrayList<AdPosEntity> paramArrayList)
  {
    this.bootAdInfoList = paramArrayList;
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

  public void setIsCloseXul(boolean paramBoolean)
  {
    this.isCloseXul = paramBoolean;
  }

  public void setJumpVideoContentHeadAndTail(String paramString)
  {
    this.mJumpVideoContentHeadAndTail = paramString;
    writePreferences("jumpVideoContentHeadAndTail", this.mJumpVideoContentHeadAndTail);
  }

  public void setLogSecretPrefix(String paramString)
  {
    this.logSecretPrefix = paramString;
  }

  public void setMetaData(byte[] paramArrayOfByte)
  {
    this.metaData = paramArrayOfByte;
  }

  public void setN3A2MetaGroups(ArrayList<MetadataGoup> paramArrayList)
  {
    this.metadataInfos = paramArrayList;
    writeMetadataInfos(paramArrayList);
    if (paramArrayList != null)
      Logger.i(TAG, "setN3A2MetaGroups:" + paramArrayList.toString());
  }

  public void setPlayerAdId(String paramString)
  {
    this.posId = paramString;
  }

  public void setPlayerMenuAdInfoList(ArrayList<AdPosEntity> paramArrayList)
  {
    this.playerMenuAdInfoList = paramArrayList;
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
      while (localIterator.hasNext())
      {
        UserKey localUserKey = (UserKey)localIterator.next();
        ArrayList localArrayList;
        try
        {
          if ((!localUserKey.key.equals("vip_list")) && (!localUserKey.key.equals("product_list")))
            continue;
          JSONArray localJSONArray = new JSONArray(URLDecoder.decode(localUserKey.value, "utf-8"));
          localArrayList = new ArrayList();
          int i = 0;
          if (i < localJSONArray.length())
          {
            AAAProductItem localAAAProductItem = new AAAProductItem();
            JSONObject localJSONObject = localJSONArray.getJSONObject(i);
            if (localJSONObject.has("price"))
              localAAAProductItem.price = Float.valueOf(localJSONObject.getString("price")).floatValue();
            if (localJSONObject.has("id"))
              localAAAProductItem.productId = Integer.valueOf(localJSONObject.getString("id")).intValue();
            if (localJSONObject.has("name"))
              localAAAProductItem.name = localJSONObject.getString("name");
            if (localJSONObject.has("button_name"))
              localAAAProductItem.button_name = localJSONObject.getString("button_name");
            if (localJSONObject.has("time"))
              localAAAProductItem.time = localJSONObject.getString("time");
            if (localJSONObject.has("type"));
            for (localAAAProductItem.type = Integer.valueOf(localJSONObject.getString("type")).intValue(); ; localAAAProductItem.type = 0)
            {
              localArrayList.add(localAAAProductItem);
              i++;
              break;
            }
          }
        }
        catch (JSONException localJSONException)
        {
          localJSONException.printStackTrace();
          continue;
          if (!localUserKey.key.equals("vip_list"))
            break label367;
          this.mProductList.addAll(0, localArrayList);
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
          localUnsupportedEncodingException.printStackTrace();
        }
        continue;
        label367: this.mProductList.addAll(localArrayList);
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

  public void setSearchStarPackageId(String paramString)
  {
    this.searchStarPackageId = paramString;
  }

  public void setSmartCardId(String paramString)
  {
    this.smartCardId = paramString;
  }

  public void setTimeshiftAssetsInfo(MediaAssetsInfo paramMediaAssetsInfo)
  {
    this.timeshiftAssetsInfo = paramMediaAssetsInfo;
  }

  public void setTokenExpire(long paramLong)
  {
    this.tokenExpire = paramLong;
  }

  public void setUiPackage(UiPackage paramUiPackage)
  {
    this.uiPackage = paramUiPackage;
  }

  public void setUserId(String paramString)
  {
    this.userId = paramString;
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
      Logger.i(TAG, "videoQuality: " + this.videoQualityMap.toString());
    }
  }

  public void userLogin(UserInfo paramUserInfo)
  {
    this.userInfo = paramUserInfo;
    if (paramUserInfo != null)
    {
      Logger.i(TAG, "userLogin " + paramUserInfo.toString());
      this.userId = paramUserInfo.user_id;
      this.exData = paramUserInfo.ex_data;
      this.netId = paramUserInfo.net_id;
      this.deviceId = paramUserInfo.device_id;
      this.webToken = paramUserInfo.web_token;
      this.userName = paramUserInfo.name;
      this.nnToken = paramUserInfo.nn_token;
      if ((TextUtils.isEmpty(this.webToken)) && ("1".equals(this.userId)))
      {
        this.userId = buildGuestUserId();
        Logger.i(TAG, "userLogin new guest userId:" + this.userId);
      }
      if ((this.userId != null) && (this.userId.length() > 0))
        this.isMacCheckOk = true;
      writePreferences("user_id", this.userId);
      writePreferences("web_token", this.webToken);
      if (!"guest".equals(this.userName))
        writePreferences("user_name", this.userName);
      writePreferences("account", paramUserInfo.account);
      writePreferences("avatar", paramUserInfo.avatar);
      writePreferences("device_id", paramUserInfo.device_id);
      writePreferences("ex_data", paramUserInfo.ex_data);
      writePreferences("loginMode", paramUserInfo.loginMode);
      writePreferences("mac_id", paramUserInfo.mac_id);
      writePreferences("mobile", paramUserInfo.mobile);
      writePreferences("name", paramUserInfo.name);
      writePreferences("net_id", paramUserInfo.net_id);
      writePreferences("nn_token", paramUserInfo.nn_token);
      writePreferences("rtype", paramUserInfo.rtype);
      writePreferences("wechat_type", paramUserInfo.wechat_type);
      writePreferences("vip_id", paramUserInfo.vip_id);
      writePreferences("vip_power", paramUserInfo.vip_power + "");
      writePreferences("vip_end_date", paramUserInfo.vip_end_date);
      writePreferences("vip_days", paramUserInfo.vip_days + "");
      writePreferences("balance", paramUserInfo.balance + "");
      writePreferences("email", paramUserInfo.email + "");
      if (paramUserInfo.vip_power > 0)
        ApiDetectAppFuncCfg.FUNCTION_ENABLE_AD = false;
      while (true)
      {
        this.privateConfigPath = (ApiDetectGlobalEnv.getInstance().getConfigPath() + File.separator + this.userId + File.separator);
        File localFile = new File(this.privateConfigPath);
        if ((!localFile.exists()) && (!localFile.isDirectory()))
          localFile.mkdir();
        Logger.i(TAG, "userLogin path:" + this.privateConfigPath);
        GlobalEventNotify.getInstance().onUserLogin();
        return;
        if (TextUtils.isEmpty(ApiDetectWebUrlBuilder.getAdInfoUrl()))
          ApiDetectAppFuncCfg.FUNCTION_ENABLE_AD = false;
        else
          ApiDetectAppFuncCfg.FUNCTION_ENABLE_AD = true;
      }
    }
    Logger.e(TAG, "userLogin is null");
  }

  public void userLogout()
  {
    Logger.i(TAG, "userLogout user_id:" + this.userId + ", web_token:" + this.webToken);
    this.webToken = "";
    GlobalEventNotify.getInstance().onUserLogout();
    if (TextUtils.isEmpty(ApiDetectWebUrlBuilder.getAdInfoUrl()));
    for (ApiDetectAppFuncCfg.FUNCTION_ENABLE_AD = false; ; ApiDetectAppFuncCfg.FUNCTION_ENABLE_AD = true)
    {
      this.userId = buildGuestUserId();
      this.exData = "";
      this.netId = "";
      this.deviceId = "";
      this.userName = "";
      this.userInfo = null;
      writePreferences("web_token", "");
      writePreferences("user_id", "");
      writePreferences("user_name", "");
      writePreferences("account", "");
      writePreferences("avatar", "");
      writePreferences("device_id", "");
      writePreferences("ex_data", "");
      writePreferences("loginMode", "");
      writePreferences("mac_id", "");
      writePreferences("mobile", "");
      writePreferences("name", "");
      writePreferences("net_id", "");
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
      this.privateConfigPath = (ApiDetectGlobalEnv.getInstance().getConfigPath() + File.separator + this.userId + File.separator);
      return;
    }
  }

  public void userWebLogined(String paramString)
  {
    Logger.i(TAG, "userWebLogined webToken:" + paramString);
    this.webToken = paramString;
  }

  public void writeMetadataInfos(ArrayList<MetadataGoup> paramArrayList)
  {
    Logger.i(TAG, "writeMetadataInfos  mMetadataInfo:" + paramArrayList);
    IOTools.writeObject(new File(ApiDetectGlobalEnv.getInstance().getConfigPath() + File.separator + "mMetadataInfo.dat"), paramArrayList);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.service.apidetect.data.ApiDetectGlobalLogic
 * JD-Core Version:    0.6.2
 */