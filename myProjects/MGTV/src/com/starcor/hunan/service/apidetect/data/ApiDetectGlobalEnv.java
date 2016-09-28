package com.starcor.hunan.service.apidetect.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.starcor.config.DeviceInfo;
import com.starcor.core.utils.IOTools;
import com.starcor.core.utils.Logger;
import java.io.File;
import java.util.HashMap;

public class ApiDetectGlobalEnv
{
  private static final long DEFAULT_FREE_PLAYTIME_PERSENT = 5L;
  private static final String PHONE_KEY = "phone";
  private static final String TAG = ApiDetectGlobalEnv.class.getSimpleName();
  private static ApiDetectGlobalEnv globalEnv = null;
  public static String phoneNumber = "4006770707";
  private static int reportPlayState = 0;
  private String AAALicense = "";
  private String apiCachePath;
  private Context appContext = null;
  private HashMap<String, String> assetHuaweiTidMap = new HashMap();
  private String bootAdPosId;
  private String cityId = "";
  private String configPath;
  private int freePlayTimePersent = 0;
  private String ip = "";
  private boolean isShowSettingPage = true;
  private String logPath;
  private String picCachePath;
  private String playerMenuAdPosId;
  private long reservationDelayNotifyTime = 300000L;
  private double samplingRate = 1.0D;
  private String tempPath;
  private int terminalGetCatchTimeDelayTime = 3600000;

  public static ApiDetectGlobalEnv getInstance()
  {
    if (globalEnv == null)
      globalEnv = new ApiDetectGlobalEnv();
    return globalEnv;
  }

  public static int getReportPlayState()
  {
    return reportPlayState;
  }

  private void initPhoneNumber()
  {
    String str = PreferenceManager.getDefaultSharedPreferences(this.appContext).getString("phone", null);
    if (TextUtils.isEmpty(str))
      return;
    phoneNumber = str;
  }

  private static HashMap<String, String> parseData(String paramString)
  {
    Gson localGson = new GsonBuilder().create();
    HashMap localHashMap1 = new HashMap();
    try
    {
      HashMap localHashMap2 = (HashMap)localGson.fromJson(paramString, new TypeToken()
      {
      }
      .getType());
      return localHashMap2;
    }
    catch (JsonSyntaxException localJsonSyntaxException)
    {
      Logger.e(TAG, "parseData()" + localJsonSyntaxException.toString());
    }
    return localHashMap1;
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

  public static void setReportPlayState(int paramInt)
  {
    reportPlayState = paramInt;
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

  public void clearApiOldCache()
  {
    IOTools.deletePathFilesOldFile(this.apiCachePath, System.currentTimeMillis() - 3600000L);
  }

  public void clearPicOldCache()
  {
    IOTools.deletePathFilesOldFile(this.picCachePath, System.currentTimeMillis() - 259200000L);
  }

  public String findHuaWeiTid(String paramString)
  {
    String str = (String)this.assetHuaweiTidMap.get(paramString);
    if (TextUtils.isEmpty(str))
      str = (String)this.assetHuaweiTidMap.get("default_id");
    return str;
  }

  public String getAAALicense()
  {
    if (TextUtils.isEmpty(this.AAALicense))
      this.AAALicense = readPreferences("license", "");
    return this.AAALicense;
  }

  public String getAAANetIp()
  {
    return this.ip;
  }

  public String getAPICachePath()
  {
    return this.apiCachePath;
  }

  public String getBootAdPosId()
  {
    return this.bootAdPosId;
  }

  public String getCityId()
  {
    return this.cityId;
  }

  public String getConfigPath()
  {
    return this.configPath;
  }

  public long getFreePlaTimePercent()
  {
    long l = 5L;
    Logger.i(TAG, "getFreePlayTimePercent freePlayTimePersent:" + this.freePlayTimePersent + " defaultFreePlayTimePersent:" + l);
    if (ApiDetectAppFuncCfg.IS_VERSION_MANGO_DOWNLOAD)
      l = this.freePlayTimePersent;
    while (this.freePlayTimePersent < 1)
      return l;
    return this.freePlayTimePersent;
  }

  public double getGsSamplingRate()
  {
    return this.samplingRate;
  }

  public String getLogPath()
  {
    return this.logPath;
  }

  public String getMainActivityBackground()
  {
    return PreferenceManager.getDefaultSharedPreferences(this.appContext).getString("MainActivityBackgroundName", "");
  }

  public String getPackageName()
  {
    return this.appContext.getPackageName();
  }

  public String getPhoneNumber()
  {
    return "客服电话：" + phoneNumber;
  }

  public String getPicCachePath()
  {
    return this.picCachePath;
  }

  public String getPlayerMenuAdPosId()
  {
    return this.playerMenuAdPosId;
  }

  public long getReservationDelayNotifyTime()
  {
    return this.reservationDelayNotifyTime;
  }

  public String getTempPath()
  {
    return this.tempPath;
  }

  public int getTerminalGetCatchTimeDelayTime()
  {
    return this.terminalGetCatchTimeDelayTime;
  }

  public int getVolumePercent()
  {
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.appContext);
    try
    {
      int i = localSharedPreferences.getInt("Volume", 40);
      Logger.i(TAG, "getVolumePercent percent:" + i);
      return i;
    }
    catch (Exception localException)
    {
      Logger.e(TAG, "getVolumePercent percent:40");
    }
    return 40;
  }

  public int init(Context paramContext)
  {
    Logger.i(TAG, "init appContext:" + paramContext.toString());
    this.appContext = paramContext;
    Logger.i(TAG, "ApiDetectAppFuncCfg.configAppFunc");
    ApiDetectAppFuncCfg.configAppFunc(DeviceInfo.getFactory());
    initPhoneNumber();
    this.logPath = (this.appContext.getDir("log", 0).toString() + File.separator);
    Logger.i(TAG, "logPath:" + this.logPath);
    this.tempPath = (this.appContext.getDir("tmp", 0).toString() + File.separator);
    Logger.i(TAG, "tempPath:" + this.tempPath);
    this.picCachePath = (this.appContext.getDir("pic", 0).toString() + File.separator);
    Logger.i(TAG, "picCachePath:" + this.picCachePath);
    this.apiCachePath = (this.appContext.getDir("api", 0).toString() + File.separator);
    Logger.i(TAG, "apiCachePath:" + this.apiCachePath);
    this.configPath = (this.appContext.getDir("config", 0).toString() + File.separator);
    Logger.i(TAG, "configPath:" + this.configPath);
    this.cityId = readPreferences("cityId", "");
    Logger.i(TAG, "init cityId:" + this.cityId);
    clearApiOldCache();
    clearPicOldCache();
    return 0;
  }

  public boolean isAppFirstStart()
  {
    try
    {
      boolean bool = PreferenceManager.getDefaultSharedPreferences(this.appContext).getBoolean("firstStart", true);
      return bool;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  public boolean isShowSettingPage()
  {
    return true;
  }

  public boolean isVolumeConfiged()
  {
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.appContext);
    try
    {
      boolean bool = localSharedPreferences.contains("Volume");
      Logger.e(TAG, "isVolumeConfiged configed:" + bool);
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Logger.e(TAG, "isVolumeConfiged configed:false");
    }
    return false;
  }

  public void setAAALicense(String paramString)
  {
    this.AAALicense = paramString;
    writePreferences("license", this.AAALicense);
  }

  public void setAAANetIp(String paramString)
  {
    this.ip = paramString;
  }

  public boolean setAppFirstStart(boolean paramBoolean)
  {
    try
    {
      SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.appContext).edit();
      localEditor.putBoolean("firstStart", paramBoolean);
      boolean bool = localEditor.commit();
      if (bool)
      {
        Logger.i(TAG, "setAppFirstStart isFirst:" + paramBoolean);
        return bool;
      }
      Logger.e(TAG, "setAppFirstStart isFirst:" + paramBoolean);
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  public void setAssetHuaWeiTid(String paramString)
  {
    this.assetHuaweiTidMap.clear();
    if (!TextUtils.isEmpty(paramString))
    {
      this.assetHuaweiTidMap = parseData(paramString);
      return;
    }
    Logger.i(TAG, "传入的assetHuaweiTid为空");
  }

  public void setBootAdPosId(String paramString)
  {
    this.bootAdPosId = paramString;
  }

  public boolean setCityId(String paramString)
  {
    if (paramString == null)
      return false;
    try
    {
      this.cityId = paramString;
      SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.appContext).edit();
      localEditor.putString("cityId", paramString);
      if (localEditor.commit())
      {
        Logger.i(TAG, "setCityId OK " + paramString);
        return true;
      }
      Logger.e(TAG, "setCityId error" + paramString);
      return false;
    }
    catch (Exception localException)
    {
      Logger.e(TAG, "setCityId " + paramString);
      localException.printStackTrace();
    }
    return false;
  }

  public void setFreePlaTimePercent(int paramInt)
  {
    Logger.i(TAG, "setFreePlaTimePercent() freePlaTimePercent:" + paramInt);
    this.freePlayTimePersent = paramInt;
  }

  public void setGsSamplingRate(double paramDouble)
  {
    this.samplingRate = paramDouble;
  }

  public void setMainActivityBackground(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return;
    Logger.i(TAG, "set MainAcitivity local path : " + paramString);
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.appContext).edit();
    localEditor.putString("MainActivityBackgroundName", paramString);
    localEditor.commit();
  }

  public void setPhoneNumer(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return;
    Logger.i(TAG, "setPhoneNumber :" + paramString);
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.appContext).edit();
    localEditor.putString("phone", paramString);
    localEditor.commit();
    phoneNumber = paramString;
  }

  public void setPlayerMenuAdPosId(String paramString)
  {
    this.playerMenuAdPosId = paramString;
  }

  public void setReservationDelayNotifyTime(int paramInt)
  {
    Logger.i(TAG, "setReservationDelayNotifyTime Value(second):" + paramInt);
    this.reservationDelayNotifyTime = (paramInt * 1000);
  }

  public void setShowSetting(boolean paramBoolean)
  {
    this.isShowSettingPage = paramBoolean;
  }

  public void setTerminalGetCatchTimeDelayTime(int paramInt)
  {
    Logger.i(TAG, "terminalGetCatchTimeDelayTime Value(second):" + paramInt);
    this.terminalGetCatchTimeDelayTime = (paramInt * 1000);
  }

  public boolean setVolumePercent(int paramInt)
  {
    try
    {
      SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this.appContext).edit();
      localEditor.putInt("Volume", paramInt);
      if (localEditor.commit())
      {
        Logger.i(TAG, "setVolumePercent OK percent:" + paramInt);
        return true;
      }
      Logger.e(TAG, "setVolumePercent error percent:" + paramInt);
      return false;
    }
    catch (Exception localException)
    {
      Logger.e(TAG, "setVolumePercent percent:" + paramInt);
      localException.printStackTrace();
    }
    return false;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.service.apidetect.data.ApiDetectGlobalEnv
 * JD-Core Version:    0.6.2
 */