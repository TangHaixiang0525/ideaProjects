package com.starcor.report.cdn;

import com.starcor.core.utils.Logger;
import com.starcor.report.ReportUtil;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class CDNReportIF2Builder
  implements ReportBuilder
{
  private static final String KEY_ERROR_CODE = "e";
  private static final String KEY_FLAG = "f";
  private static final String KEY_HOST = "h";
  private static final String KEY_POS = "o";
  private static final String KEY_REPORT_TYPE = "r";
  private static final String KEY_URL = "l";
  private static final String TAG = CDNReportIF2Builder.class.getSimpleName();
  private String errorCode;
  private int flag;
  private String host;
  private CDNReportHelper.PlayType playType;
  private long pos;
  private List<ReportParamPair> reportParamPairList = new ArrayList();
  private ReportType reportType;
  private String url;

  public CDNReportIF2Builder addErrorCode(String paramString)
  {
    this.errorCode = paramString;
    return this;
  }

  public CDNReportIF2Builder addFlag(int paramInt)
  {
    this.flag = paramInt;
    return this;
  }

  public CDNReportIF2Builder addPlayType(CDNReportHelper.PlayType paramPlayType)
  {
    this.playType = paramPlayType;
    return this;
  }

  public CDNReportIF2Builder addPos(long paramLong)
  {
    this.pos = paramLong;
    return this;
  }

  public CDNReportIF2Builder addReportType(ReportType paramReportType)
  {
    this.reportType = paramReportType;
    return this;
  }

  public CDNReportIF2Builder addUrl(String paramString)
  {
    try
    {
      this.url = URLEncoder.encode(paramString, "utf-8");
      this.host = ReportUtil.getHost(paramString);
      return this;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      while (true)
      {
        localUnsupportedEncodingException.printStackTrace();
        this.url = paramString;
      }
    }
  }

  public List<ReportParamPair> build()
  {
    this.reportParamPairList.clear();
    ReportParamPair localReportParamPair1 = new ReportParamPair("f");
    localReportParamPair1.setValue(String.valueOf(this.flag));
    this.reportParamPairList.add(localReportParamPair1);
    ReportParamPair localReportParamPair2 = new ReportParamPair("r");
    label110: ReportParamPair localReportParamPair5;
    if (this.reportType != null)
    {
      localReportParamPair2.setValue(String.valueOf(this.reportType.getValue()));
      this.reportParamPairList.add(localReportParamPair2);
      ReportParamPair localReportParamPair3 = new ReportParamPair("l");
      if (this.url == null)
        break label289;
      localReportParamPair3.setValue(this.url);
      this.reportParamPairList.add(localReportParamPair3);
      ReportParamPair localReportParamPair4 = new ReportParamPair("h");
      if (this.host == null)
        break label300;
      localReportParamPair4.setValue(this.host);
      label149: this.reportParamPairList.add(localReportParamPair4);
      this.reportParamPairList.add(CDNReportHelper.getPlayTypeParam(this.playType));
      localReportParamPair5 = new ReportParamPair("o");
      if (this.playType == null)
        break label352;
      if (this.playType != CDNReportHelper.PlayType.LIVE)
        break label311;
      localReportParamPair5.setValue(null);
    }
    while (true)
    {
      this.reportParamPairList.add(localReportParamPair5);
      ReportParamPair localReportParamPair6 = new ReportParamPair("e");
      if ((this.reportType != null) && (this.reportType == ReportType.ERROR_REPORT))
        localReportParamPair6.setValue(this.errorCode);
      this.reportParamPairList.add(localReportParamPair6);
      return this.reportParamPairList;
      Logger.e(TAG, "上报类型为空");
      break;
      label289: Logger.e(TAG, "URL为空");
      break label110;
      label300: Logger.e(TAG, "host 为空");
      break label149;
      label311: if ((this.reportType != null) && (this.reportType == ReportType.TIMER_REPORT))
      {
        localReportParamPair5.setValue(null);
      }
      else
      {
        localReportParamPair5.setValue(String.valueOf(this.pos));
        continue;
        label352: Logger.e(TAG, "缺少播放类型");
      }
    }
  }

  public static enum ReportType
  {
    private int value;

    static
    {
      ERROR_REPORT = new ReportType("ERROR_REPORT", 2, 2);
      COMPLETE_REPORT = new ReportType("COMPLETE_REPORT", 3, 3);
      ReportType[] arrayOfReportType = new ReportType[4];
      arrayOfReportType[0] = BUFFER_REPORT;
      arrayOfReportType[1] = TIMER_REPORT;
      arrayOfReportType[2] = ERROR_REPORT;
      arrayOfReportType[3] = COMPLETE_REPORT;
    }

    private ReportType(int paramInt)
    {
      this.value = paramInt;
    }

    public int getValue()
    {
      return this.value;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.report.cdn.CDNReportIF2Builder
 * JD-Core Version:    0.6.2
 */