package com.starcor.report;

import com.starcor.core.utils.Logger;

public class ReportInfo
{
  private static ReportInfo info = null;
  private String SearchKey = "";
  private String WebUrl = "";
  private ReportPageInfo pageInfo = null;
  private String playSrc = "";

  public static ReportInfo getInstance()
  {
    if (info == null)
      info = new ReportInfo();
    return info;
  }

  public void clearReportInfo()
  {
    info = null;
  }

  public String getEntranceSrc()
  {
    if ("D2".equals(this.playSrc))
      return this.playSrc + "&key=" + this.SearchKey;
    if ("KW".equals(this.playSrc))
      return this.playSrc + "&url=" + this.WebUrl;
    return this.playSrc;
  }

  public ReportPageInfo getPageInfo()
  {
    return this.pageInfo;
  }

  public String getSearchKey()
  {
    return this.SearchKey;
  }

  public String getWebUrl()
  {
    return this.WebUrl;
  }

  public void setEntranceSrc(String paramString)
  {
    Logger.i("playSrc=" + paramString);
    this.playSrc = paramString;
  }

  public void setPageInfo(ReportPageInfo paramReportPageInfo)
  {
    this.pageInfo = paramReportPageInfo;
  }

  public void setSearchKey(String paramString)
  {
    this.SearchKey = paramString;
  }

  public void setWebUrl(String paramString)
  {
    this.WebUrl = paramString;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.report.ReportInfo
 * JD-Core Version:    0.6.2
 */