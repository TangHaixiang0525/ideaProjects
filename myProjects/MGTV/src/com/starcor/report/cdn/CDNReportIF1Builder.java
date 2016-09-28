package com.starcor.report.cdn;

import com.starcor.report.ReportUtil;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class CDNReportIF1Builder
  implements ReportBuilder
{
  private static final String KEY_ACCESS_FLAG = "f";
  private static final String KEY_ACCESS_HOST = "h";
  private static final String KEY_ACCESS_URL = "l";
  private static final String KEY_CHANGE_CODE_RATE = "a";
  private static final String KEY_CONVERT_RATE_FLAG = "z";
  private static final String KEY_ERROR = "e";
  private static final String KEY_PLAY_STEP = "s";
  private static final String KEY_SOURCE_ID = "si";
  private boolean accessFlag;
  private CDNReportHelper.ChangeCodeRateCategory changeCodeRateCategory;
  private String errorCode;
  private boolean finalInvoke;
  private String interfaceHost;
  private String interfaceUrl;
  private CDNReportHelper.PlayStep playStep;
  private CDNReportHelper.PlayType playType;
  private List<ReportParamPair> reportParamPairs = new ArrayList();

  public CDNReportIF1Builder addAccessFlag(boolean paramBoolean)
  {
    this.accessFlag = paramBoolean;
    return this;
  }

  public CDNReportIF1Builder addChangeCodeRateCategory(CDNReportHelper.ChangeCodeRateCategory paramChangeCodeRateCategory)
  {
    this.changeCodeRateCategory = paramChangeCodeRateCategory;
    return this;
  }

  public CDNReportIF1Builder addErrorCode(String paramString)
  {
    this.errorCode = paramString;
    return this;
  }

  public CDNReportIF1Builder addInterfaceUrl(String paramString)
  {
    try
    {
      this.interfaceUrl = URLEncoder.encode(paramString, "UTF-8");
      this.interfaceHost = ReportUtil.getHost(paramString);
      return this;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      while (true)
      {
        this.interfaceUrl = paramString;
        localUnsupportedEncodingException.printStackTrace();
      }
    }
  }

  public CDNReportIF1Builder addInvokeFlag(boolean paramBoolean)
  {
    this.finalInvoke = paramBoolean;
    return this;
  }

  public CDNReportIF1Builder addPlayStep(CDNReportHelper.PlayStep paramPlayStep)
  {
    this.playStep = paramPlayStep;
    return this;
  }

  public CDNReportIF1Builder addPlayType(CDNReportHelper.PlayType paramPlayType)
  {
    this.playType = paramPlayType;
    return this;
  }

  public List<ReportParamPair> build()
  {
    this.reportParamPairs.clear();
    String str1;
    ReportParamPair localReportParamPair2;
    label66: ReportParamPair localReportParamPair3;
    if (this.accessFlag)
    {
      str1 = "0";
      ReportParamPair localReportParamPair1 = new ReportParamPair("f", str1);
      this.reportParamPairs.add(localReportParamPair1);
      localReportParamPair2 = new ReportParamPair("e");
      if (!this.accessFlag)
        break label324;
      localReportParamPair2.setValue("200");
      this.reportParamPairs.add(localReportParamPair2);
      localReportParamPair3 = new ReportParamPair("z");
      if (!this.finalInvoke)
        break label336;
    }
    label324: label336: for (String str2 = "1"; ; str2 = "0")
    {
      localReportParamPair3.setValue(str2);
      this.reportParamPairs.add(localReportParamPair3);
      ReportParamPair localReportParamPair4 = new ReportParamPair("s");
      if (this.playStep != null)
        localReportParamPair4.setValue(String.valueOf(this.playStep.getValue()));
      this.reportParamPairs.add(localReportParamPair4);
      ReportParamPair localReportParamPair5 = new ReportParamPair("h");
      localReportParamPair5.setValue(this.interfaceHost);
      this.reportParamPairs.add(localReportParamPair5);
      ReportParamPair localReportParamPair6 = new ReportParamPair("l");
      localReportParamPair6.setValue(this.interfaceUrl);
      this.reportParamPairs.add(localReportParamPair6);
      ReportParamPair localReportParamPair7 = new ReportParamPair("a");
      if (this.changeCodeRateCategory != null)
        localReportParamPair7.setValue(String.valueOf(this.changeCodeRateCategory.getValue()));
      this.reportParamPairs.add(localReportParamPair7);
      ReportParamPair localReportParamPair8 = new ReportParamPair("si");
      this.reportParamPairs.add(localReportParamPair8);
      this.reportParamPairs.add(CDNReportHelper.getPlayTypeParam(this.playType));
      return this.reportParamPairs;
      str1 = "-1";
      break;
      localReportParamPair2.setValue(this.errorCode);
      break label66;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.report.cdn.CDNReportIF1Builder
 * JD-Core Version:    0.6.2
 */