package com.huawei.playerinterface.entity;

public class DRMInfo
{
  public static final String DRM_VMX = "Verimatrix";
  private String DRMType;
  private String companyName;
  private String deviceId;
  private String logPath;
  private String server;

  public DRMInfo()
  {
  }

  public DRMInfo(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.DRMType = "Verimatrix";
    this.server = paramString1;
    this.deviceId = paramString3;
    this.companyName = paramString2;
    this.logPath = paramString4;
  }

  public DRMInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    this.DRMType = paramString1;
    this.server = paramString2;
    this.deviceId = paramString4;
    this.companyName = paramString3;
    this.logPath = paramString5;
  }

  public String getCompanyName()
  {
    return this.companyName;
  }

  public String getDRMType()
  {
    return this.DRMType;
  }

  public String getDeviceId()
  {
    return this.deviceId;
  }

  public String getLogPath()
  {
    return this.logPath;
  }

  public String getServer()
  {
    return this.server;
  }

  public void setCompanyName(String paramString)
  {
    this.companyName = paramString;
  }

  public void setDeviceId(String paramString)
  {
    this.deviceId = paramString;
  }

  public void setLogPath(String paramString)
  {
    this.logPath = paramString;
  }

  public void setServer(String paramString)
  {
    this.server = paramString;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.huawei.playerinterface.entity.DRMInfo
 * JD-Core Version:    0.6.2
 */