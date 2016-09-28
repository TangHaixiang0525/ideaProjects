package com.starcor.config;

public class MgtvVersionInfo
{
  public String N1AUrl;
  public int factory;
  public VersionDiscribe versionDiscribe;

  public MgtvVersionInfo(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    this.factory = paramInt1;
    this.versionDiscribe = new VersionDiscribe(paramInt2, paramString1, paramString2, paramString3, paramString4);
    this.N1AUrl = paramString5;
  }

  public static class VersionDiscribe
  {
    String cooprationCode;
    String deviceCodeMajor;
    String deviceCodeMinor;
    String factoryName;
    int policy;

    public VersionDiscribe(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4)
    {
      if (-1 == MgtvVersion.policy);
      while (true)
      {
        this.policy = paramInt;
        this.cooprationCode = paramString1;
        this.factoryName = paramString2;
        this.deviceCodeMajor = paramString3;
        this.deviceCodeMinor = paramString4;
        return;
        paramInt = MgtvVersion.policy;
      }
    }

    public void setDeviceCodeMajor(String paramString)
    {
      this.deviceCodeMajor = paramString;
    }

    public void setDeviceCodeMinor(String paramString)
    {
      this.deviceCodeMinor = paramString;
    }

    public String toString()
    {
      return String.valueOf(this.policy) + "." + this.cooprationCode + "." + this.factoryName + "." + this.deviceCodeMajor + "." + this.deviceCodeMinor;
    }

    public String toString(String paramString)
    {
      return String.valueOf(this.policy) + "." + this.cooprationCode + "." + this.factoryName + "." + this.deviceCodeMajor + paramString + "." + this.deviceCodeMinor;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.config.MgtvVersionInfo
 * JD-Core Version:    0.6.2
 */