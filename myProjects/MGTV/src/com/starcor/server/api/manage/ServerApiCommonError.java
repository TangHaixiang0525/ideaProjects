package com.starcor.server.api.manage;

public class ServerApiCommonError
{
  private int httpCode;
  private String httpReason = "";
  private boolean isParseError = false;
  private String location = "";
  private String parserErrorReason = "";
  private String runReason = "";
  private int runState;

  public ServerApiCommonError(int paramInt1, String paramString1, int paramInt2, String paramString2, String paramString3)
  {
    this.runState = paramInt1;
    this.runReason = paramString1;
    this.httpCode = paramInt2;
    this.httpReason = paramString2;
    this.location = paramString3;
  }

  public ServerApiCommonError(int paramInt1, String paramString1, int paramInt2, String paramString2, boolean paramBoolean, String paramString3)
  {
    this.runState = paramInt1;
    this.runReason = paramString1;
    this.httpCode = paramInt2;
    this.httpReason = paramString2;
    this.isParseError = paramBoolean;
    this.parserErrorReason = paramString3;
  }

  public int getHttpCode()
  {
    return this.httpCode;
  }

  public String getHttpReason()
  {
    return this.httpReason;
  }

  public String getParserErrorReason()
  {
    return this.parserErrorReason;
  }

  public String getRunReason()
  {
    return this.runReason;
  }

  public int getRunState()
  {
    return this.runState;
  }

  public boolean isParseError()
  {
    return this.isParseError;
  }

  public String toString()
  {
    return "ServerApiCommonError [runState=" + this.runState + ", runReason=" + this.runReason + ", httpCode=" + this.httpCode + ", httpReason=" + this.httpReason + ", isParseError=" + this.isParseError + ", parserErrorReason=" + this.parserErrorReason + ", location=" + this.location + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.server.api.manage.ServerApiCommonError
 * JD-Core Version:    0.6.2
 */