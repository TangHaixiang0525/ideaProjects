package com.starcor.report.cdn;

public class ReportParamPair
{
  private String key;
  private String value;

  public ReportParamPair(String paramString)
  {
    this.key = paramString;
    this.value = "";
  }

  public ReportParamPair(String paramString1, String paramString2)
  {
    this.key = paramString1;
    this.value = paramString2;
  }

  public String getKey()
  {
    return this.key;
  }

  public String getValue()
  {
    return this.value;
  }

  public void setValue(String paramString)
  {
    this.value = paramString;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.report.cdn.ReportParamPair
 * JD-Core Version:    0.6.2
 */