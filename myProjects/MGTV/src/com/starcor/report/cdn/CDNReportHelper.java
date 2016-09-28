package com.starcor.report.cdn;

import android.text.TextUtils;
import com.starcor.config.DeviceInfo;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.message.MessageHandler;
import com.starcor.report.ReportMessage;
import com.starcor.report.ReportUtil;
import java.util.List;

public final class CDNReportHelper
{
  private static final String CV = "20160205";
  private static final String KEY_CLIENT_TYPE = "c";
  private static final String KEY_CLIENT_VER = "v";
  private static final String KEY_CV = "cv";
  private static final String KEY_GUID = "u";
  private static final String KEY_PLATFORM = "p";
  private static final String KEY_PLAY_TYPE = "t";
  private static final String KEY_QUALITY = "b";
  private static final String REPORT_IF1_URL = "http://v2.log.hunantv.com/info.php";
  private static final String REPORT_IF2_URL = "http://v1.play.log.hunantv.com/info.php";
  private static final String TAG = CDNReportHelper.class.getSimpleName();

  private static void addCommonParam(List<ReportParamPair> paramList)
  {
    if (paramList != null)
    {
      paramList.add(new ReportParamPair("p", "7"));
      paramList.add(new ReportParamPair("v", DeviceInfo.getMGTVVersion()));
      paramList.add(new ReportParamPair("u", GlobalLogic.getInstance().getGuid()));
      paramList.add(new ReportParamPair("cv", "20160205"));
      paramList.add(new ReportParamPair("c", "1"));
    }
  }

  public static ReportParamPair getPlayTypeParam(PlayType paramPlayType)
  {
    ReportParamPair localReportParamPair = new ReportParamPair("t");
    if (paramPlayType != null)
      localReportParamPair.setValue(String.valueOf(paramPlayType.getValue()));
    return localReportParamPair;
  }

  private static ReportParamPair getQualityParam(Quality paramQuality)
  {
    ReportParamPair localReportParamPair = new ReportParamPair("b");
    if (paramQuality != null)
      localReportParamPair.setValue(String.valueOf(paramQuality.getValue()));
    return localReportParamPair;
  }

  private static String getReportDesc(ReportBusiness paramReportBusiness)
  {
    String str = "";
    if (paramReportBusiness == ReportBusiness.THREE_LAYER_REPORT_IF1)
      str = "IF1上报";
    while (paramReportBusiness != ReportBusiness.PLAY_DOWNLOAD_REPORT_IF2)
      return str;
    return "IF2上报";
  }

  private static String getReportUrl(ReportBusiness paramReportBusiness)
  {
    String str = "";
    if (paramReportBusiness == ReportBusiness.THREE_LAYER_REPORT_IF1)
    {
      str = GlobalLogic.getInstance().getCDNReportIF1Url();
      if (TextUtils.isEmpty(str))
      {
        str = "http://v2.log.hunantv.com/info.php";
        Logger.e("ReportService", "getReportUrl IF1 url null, debug this!!! use default: " + str);
      }
    }
    do
    {
      do
        return str;
      while (paramReportBusiness != ReportBusiness.PLAY_DOWNLOAD_REPORT_IF2);
      str = GlobalLogic.getInstance().getCDNReportIF2Url();
    }
    while (!TextUtils.isEmpty(str));
    Logger.e("ReportService", "getReportUrl IF2 url null, use default. debug this!!! use default: " + "http://v1.play.log.hunantv.com/info.php");
    return "http://v1.play.log.hunantv.com/info.php";
  }

  public static Quality qualityFromString(String paramString)
  {
    Logger.d(TAG, "qualityFromString: " + paramString);
    if (!TextUtils.isEmpty(paramString))
    {
      if (paramString.equalsIgnoreCase("LOW"))
        return Quality.QUALITY_LOW;
      if (paramString.equalsIgnoreCase("STD"))
        return Quality.QUALITY_STD;
      if (paramString.equalsIgnoreCase("HD"))
        return Quality.QUALITY_HD;
      if (paramString.equalsIgnoreCase("SD"))
        return Quality.QUALITY_SHD;
      if (paramString.equalsIgnoreCase("4K"))
        return Quality.QUALITY_4K;
    }
    Logger.w(TAG, "qualityFromString quality: " + paramString + ", use default");
    return null;
  }

  private static void report(String paramString, ReportBusiness paramReportBusiness, List<ReportParamPair> paramList)
  {
    if ((paramList != null) && (!paramList.isEmpty()))
    {
      String str1 = getReportUrl(paramReportBusiness);
      if (!TextUtils.isEmpty(str1))
      {
        addCommonParam(paramList);
        String str2 = ReportUtil.formatUrl(str1, paramList);
        if ((paramString == null) || (paramString.isEmpty()))
          paramString = getReportDesc(paramReportBusiness);
        report(paramString, str2);
      }
    }
  }

  private static void report(String paramString1, String paramString2)
  {
    ReportMessage localReportMessage = new ReportMessage();
    localReportMessage.mExtData = paramString2;
    localReportMessage.setReportType(1);
    localReportMessage.setDesc(paramString1);
    Logger.d("ReportService", "report (" + paramString1 + ")事件，url：" + paramString2);
    MessageHandler.instance().doNotify(localReportMessage);
  }

  public static void reportIF1(String paramString1, boolean paramBoolean1, boolean paramBoolean2, PlayStep paramPlayStep, String paramString2, ChangeCodeRateCategory paramChangeCodeRateCategory, Quality paramQuality, PlayType paramPlayType, String paramString3)
  {
    CDNReportIF1Builder localCDNReportIF1Builder = new CDNReportIF1Builder();
    localCDNReportIF1Builder.addAccessFlag(paramBoolean1).addInvokeFlag(paramBoolean2).addInterfaceUrl(paramString2).addPlayStep(paramPlayStep).addPlayType(paramPlayType).addChangeCodeRateCategory(paramChangeCodeRateCategory).addErrorCode(paramString3);
    List localList = localCDNReportIF1Builder.build();
    localList.add(getQualityParam(paramQuality));
    report(paramString1, ReportBusiness.THREE_LAYER_REPORT_IF1, localList);
  }

  public static void reportIF2(String paramString1, int paramInt, CDNReportIF2Builder.ReportType paramReportType, String paramString2, PlayType paramPlayType, long paramLong, Quality paramQuality, String paramString3)
  {
    CDNReportIF2Builder localCDNReportIF2Builder = new CDNReportIF2Builder();
    localCDNReportIF2Builder.addFlag(paramInt).addReportType(paramReportType).addUrl(paramString2).addPlayType(paramPlayType).addPos(paramLong).addErrorCode(paramString3);
    List localList = localCDNReportIF2Builder.build();
    localList.add(getQualityParam(paramQuality));
    report(paramString1, ReportBusiness.PLAY_DOWNLOAD_REPORT_IF2, localList);
  }

  public static enum ChangeCodeRateCategory
  {
    private int value;

    static
    {
      CHANGE_CODE_RATE = new ChangeCodeRateCategory("CHANGE_CODE_RATE", 1, 1);
      MANUAL_RETRY = new ChangeCodeRateCategory("MANUAL_RETRY", 2, 2);
      AUTO_RETRY = new ChangeCodeRateCategory("AUTO_RETRY", 3, 3);
      ChangeCodeRateCategory[] arrayOfChangeCodeRateCategory = new ChangeCodeRateCategory[4];
      arrayOfChangeCodeRateCategory[0] = FIRST_LOAD;
      arrayOfChangeCodeRateCategory[1] = CHANGE_CODE_RATE;
      arrayOfChangeCodeRateCategory[2] = MANUAL_RETRY;
      arrayOfChangeCodeRateCategory[3] = AUTO_RETRY;
    }

    private ChangeCodeRateCategory(int paramInt)
    {
      this.value = paramInt;
    }

    public int getValue()
    {
      return this.value;
    }
  }

  public static enum PlayStep
  {
    private int value;

    static
    {
      ACCESS_CACHE = new PlayStep("ACCESS_CACHE", 2, 3);
      PlayStep[] arrayOfPlayStep = new PlayStep[3];
      arrayOfPlayStep[0] = ACCESS_CMS;
      arrayOfPlayStep[1] = ACCESS_DISPATCHER;
      arrayOfPlayStep[2] = ACCESS_CACHE;
    }

    private PlayStep(int paramInt)
    {
      this.value = paramInt;
    }

    public int getValue()
    {
      return this.value;
    }
  }

  public static enum PlayType
  {
    private int value;

    static
    {
      LIVE = new PlayType("LIVE", 1, 1);
      AD_PLAY = new PlayType("AD_PLAY", 2, 4);
      OFFLINE_DOWNLOAD = new PlayType("OFFLINE_DOWNLOAD", 3, 6);
      PlayType[] arrayOfPlayType = new PlayType[4];
      arrayOfPlayType[0] = VOD;
      arrayOfPlayType[1] = LIVE;
      arrayOfPlayType[2] = AD_PLAY;
      arrayOfPlayType[3] = OFFLINE_DOWNLOAD;
    }

    private PlayType(int paramInt)
    {
      this.value = paramInt;
    }

    public int getValue()
    {
      return this.value;
    }
  }

  public static enum Quality
  {
    private int value;

    static
    {
      QUALITY_LOW = new Quality("QUALITY_LOW", 1, 1);
      QUALITY_STD = new Quality("QUALITY_STD", 2, 2);
      QUALITY_HD = new Quality("QUALITY_HD", 3, 3);
      QUALITY_SHD = new Quality("QUALITY_SHD", 4, 4);
      QUALITY_4K = new Quality("QUALITY_4K", 5, 5);
      Quality[] arrayOfQuality = new Quality[6];
      arrayOfQuality[0] = QUALITY_UNKNOWN;
      arrayOfQuality[1] = QUALITY_LOW;
      arrayOfQuality[2] = QUALITY_STD;
      arrayOfQuality[3] = QUALITY_HD;
      arrayOfQuality[4] = QUALITY_SHD;
      arrayOfQuality[5] = QUALITY_4K;
    }

    private Quality(int paramInt)
    {
      this.value = paramInt;
    }

    public int getValue()
    {
      return this.value;
    }
  }

  public static enum ReportBusiness
  {
    static
    {
      PLAY_DOWNLOAD_REPORT_IF2 = new ReportBusiness("PLAY_DOWNLOAD_REPORT_IF2", 1);
      ReportBusiness[] arrayOfReportBusiness = new ReportBusiness[2];
      arrayOfReportBusiness[0] = THREE_LAYER_REPORT_IF1;
      arrayOfReportBusiness[1] = PLAY_DOWNLOAD_REPORT_IF2;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.report.cdn.CDNReportHelper
 * JD-Core Version:    0.6.2
 */