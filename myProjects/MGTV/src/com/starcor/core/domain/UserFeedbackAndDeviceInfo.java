package com.starcor.core.domain;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import com.starcor.config.DeviceInfo;
import com.starcor.config.MgtvVersion;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.logic.UserCPLLogic;
import com.starcor.core.logic.domain.LocalPlayRecordKey;
import com.starcor.core.logic.domain.LocalPlayRecordValue;
import com.starcor.core.net.NetTools;
import com.starcor.hunan.App;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class UserFeedbackAndDeviceInfo
{
  private static final String IPADDR = "http://www.ip5.me/";
  public static final int POST_FEEDBACKDATA = 0;
  public static final int POST_FEEDBACK_DEVICEINFO = 1;
  private static final String TAG = "UserFeedbackAndDeviceInfo";
  private static UserFeedbackAndDeviceInfo globalFeedbackAndDeviceInfo = null;
  private String account = "";
  private Context appContext = null;
  private String appVersion = "";
  private String company = "";
  private String contactInfo = "";
  private String cpuInfo = "";
  private String deviceId = "";
  private String deviceIp = "";
  private String deviceMac = "";
  private String guid = "";
  private String model = "";
  private String networkType = "";
  private String platformType = "";
  private String platformVersion = "";
  private String playHistory = "";
  private HashMap<LocalPlayRecordKey, LocalPlayRecordValue> playList;
  private List<PlayRecordList> playRecordLists = new ArrayList();
  private String questionDesc = "";
  private String screenPixels = "";
  private String uid = "";

  public UserFeedbackAndDeviceInfo(Context paramContext)
  {
    this.appContext = paramContext;
  }

  private String getDate(long paramLong)
  {
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(paramLong));
  }

  public static UserFeedbackAndDeviceInfo getInstance()
  {
    if (globalFeedbackAndDeviceInfo == null)
      globalFeedbackAndDeviceInfo = new UserFeedbackAndDeviceInfo(App.getInstance().getApplicationContext());
    return globalFeedbackAndDeviceInfo;
  }

  private String getPlayListData(PlayRecordList paramPlayRecordList)
  {
    return paramPlayRecordList.getBeginTime() + "   videoid:" + paramPlayRecordList.getVideoId() + "   videoname: " + paramPlayRecordList.getVideoName() + ";" + "第" + (1 + paramPlayRecordList.getVideoIndex().intValue()) + "集";
  }

  public String getAccount()
  {
    return GlobalLogic.getInstance().getUserName();
  }

  public String getAppVersion()
  {
    return MgtvVersion.getVersion();
  }

  public String getCompany()
  {
    return Build.MANUFACTURER;
  }

  public String getCpuInfo()
  {
    return Build.CPU_ABI;
  }

  public String getDeviceId()
  {
    return GlobalLogic.getInstance().getDeviceId();
  }

  public String getDeviceIp()
  {
    return NetTools.getNetIP("http://www.ip5.me/");
  }

  public String getDeviceMac()
  {
    return DeviceInfo.getMac();
  }

  public String getModel()
  {
    return Build.MODEL;
  }

  public String getNetworkType()
  {
    return NetTools.getNetWorkType(this.appContext);
  }

  public String getPlatformType()
  {
    return "Android";
  }

  public String getPlatformVersion()
  {
    return Build.VERSION.RELEASE;
  }

  public String getPlayHistory()
  {
    String str1 = "";
    this.playList = UserCPLLogic.getInstance().getLocalPlayRecordList();
    if ((this.playList != null) && (this.playList.size() != 0))
    {
      this.playRecordLists.clear();
      Iterator localIterator = this.playList.keySet().iterator();
      while (localIterator.hasNext())
      {
        LocalPlayRecordKey localLocalPlayRecordKey = (LocalPlayRecordKey)localIterator.next();
        LocalPlayRecordValue localLocalPlayRecordValue = (LocalPlayRecordValue)this.playList.get(localLocalPlayRecordKey);
        PlayRecordList localPlayRecordList = new PlayRecordList();
        localPlayRecordList.setBeginTime(getDate(localLocalPlayRecordValue.createTime));
        localPlayRecordList.setVideoIndex(localLocalPlayRecordKey.videoIndex);
        localPlayRecordList.setVideoName(localLocalPlayRecordValue.videoName);
        localPlayRecordList.setVideoId(localLocalPlayRecordKey.videoId);
        this.playRecordLists.add(localPlayRecordList);
      }
      Collections.sort(this.playRecordLists, new Comparator()
      {
        public int compare(PlayRecordList paramAnonymousPlayRecordList1, PlayRecordList paramAnonymousPlayRecordList2)
        {
          return paramAnonymousPlayRecordList1.getBeginTime().compareTo(paramAnonymousPlayRecordList2.getBeginTime());
        }
      });
      if (this.playRecordLists.size() > 5)
      {
        int j = -5 + this.playRecordLists.size();
        if (j < this.playRecordLists.size())
        {
          StringBuilder localStringBuilder2 = new StringBuilder().append(str1).append(getPlayListData((PlayRecordList)this.playRecordLists.get(j)));
          if (j + 1 < this.playRecordLists.size());
          for (String str3 = "\n"; ; str3 = "")
          {
            str1 = str3;
            j++;
            break;
          }
        }
      }
      else
      {
        int i = 0;
        if (i < this.playRecordLists.size())
        {
          StringBuilder localStringBuilder1 = new StringBuilder().append(str1).append(getPlayListData((PlayRecordList)this.playRecordLists.get(i)));
          if (i + 1 < this.playRecordLists.size());
          for (String str2 = "\n"; ; str2 = "")
          {
            str1 = str2;
            i++;
            break;
          }
        }
      }
    }
    return str1;
  }

  public Map<String, String> getPostDataByType(int paramInt)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("appVersion", getAppVersion());
    localHashMap.put("deviceId", getDeviceMac());
    localHashMap.put("company", getCompany());
    localHashMap.put("platformType", getPlatformType());
    localHashMap.put("platformVersion", getPlatformVersion());
    localHashMap.put("account", getAccount());
    localHashMap.put("uid", getUid());
    localHashMap.put("cpuInfo", getCpuInfo());
    localHashMap.put("model", getModel());
    localHashMap.put("networkType", getNetworkType());
    localHashMap.put("playHistory", getPlayHistory());
    switch (paramInt)
    {
    case 0:
    default:
      return localHashMap;
    case 1:
    }
    localHashMap.put("guid", this.guid);
    return localHashMap;
  }

  public String getPseudoRandom()
  {
    Random localRandom = new Random();
    String str = "";
    for (int i = 0; i < 6; i++)
      str = str + localRandom.nextInt(10);
    this.guid = str;
    return str;
  }

  public String getScreenPixels()
  {
    String str1 = String.valueOf(this.appContext.getResources().getDisplayMetrics().widthPixels);
    String str2 = String.valueOf(this.appContext.getResources().getDisplayMetrics().heightPixels);
    this.screenPixels = (str1 + " X " + str2);
    return this.screenPixels;
  }

  public String getUid()
  {
    return GlobalLogic.getInstance().getUserId();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.UserFeedbackAndDeviceInfo
 * JD-Core Version:    0.6.2
 */