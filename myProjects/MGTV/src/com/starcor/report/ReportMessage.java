package com.starcor.report;

import com.starcor.message.Message;

public class ReportMessage extends Message
{
  public static final int FLAG_DATA_REPORT = 16384;
  public static final int REPORT_TYPE_GET = 1;
  public static final int REPORT_TYPE_POST = 2;
  public static final int REPORT_TYPE_POST_DELAY = 3;
  private boolean mDelayReport = false;
  private String mDesc = "";
  private boolean mIsLive = false;
  private int mReportType = 2;

  public ReportMessage()
  {
    this.what = 0;
    setExcuteMode(1);
    setFlags(16384);
  }

  public boolean getDelayReportEnable()
  {
    return this.mDelayReport;
  }

  public String getDesc()
  {
    return this.mDesc;
  }

  public boolean getIsLiveReport()
  {
    return this.mIsLive;
  }

  public int getReportType()
  {
    return this.mReportType;
  }

  public void setDelayReportEnable(boolean paramBoolean)
  {
    this.mDelayReport = paramBoolean;
  }

  public void setDesc(String paramString)
  {
    this.mDesc = paramString;
  }

  public void setIsLiveReport(boolean paramBoolean)
  {
    this.mIsLive = paramBoolean;
  }

  public void setReportType(int paramInt)
  {
    this.mReportType = paramInt;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.report.ReportMessage
 * JD-Core Version:    0.6.2
 */