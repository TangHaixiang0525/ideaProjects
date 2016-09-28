package com.starcor.hunan.msgsys.data.http;

public class MQTTHttpPostParam
{
  private String mMac = null;
  private String mOperator_strategy_no = null;
  private String mVersion = null;

  public MQTTHttpPostParam(String paramString1, String paramString2, String paramString3)
  {
    this.mOperator_strategy_no = paramString1;
    this.mVersion = paramString2;
    this.mMac = paramString3;
  }

  public String getMac()
  {
    return this.mMac;
  }

  public String getStrategyNO()
  {
    return this.mOperator_strategy_no;
  }

  public String getVersion()
  {
    return this.mVersion;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.data.http.MQTTHttpPostParam
 * JD-Core Version:    0.6.2
 */