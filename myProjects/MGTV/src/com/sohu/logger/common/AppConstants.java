package com.sohu.logger.common;

public class AppConstants
{
  private static final AppConstants mIntance = new AppConstants();
  private String mApkType;
  private String mClientType;
  private String mPartnerNo;
  private String mPlatform;
  private String mPoid;
  private String mProType;
  private int mProjectType;
  private String mSver;

  public static AppConstants getInstance()
  {
    return mIntance;
  }

  public String getClientType()
  {
    return this.mClientType;
  }

  public String getPartnerNo()
  {
    return this.mPartnerNo;
  }

  public String getPlatform()
  {
    return this.mPlatform;
  }

  public String getPoid()
  {
    return this.mPoid;
  }

  public String getSver()
  {
    return this.mSver;
  }

  public String getmApkType()
  {
    return this.mApkType;
  }

  public String getmProType()
  {
    return this.mProType;
  }

  public int getmProjectType()
  {
    return this.mProjectType;
  }

  public void setClientType(String paramString)
  {
    this.mClientType = paramString;
  }

  public void setPartnerNo(String paramString)
  {
    this.mPartnerNo = paramString;
  }

  public void setPlatform(String paramString)
  {
    this.mPlatform = paramString;
  }

  public void setPoid(String paramString)
  {
    this.mPoid = paramString;
  }

  public void setSver(String paramString)
  {
    this.mSver = paramString;
  }

  public void setmApkType(String paramString)
  {
    this.mApkType = paramString;
  }

  public void setmProType(String paramString)
  {
    this.mProType = paramString;
  }

  public void setmProjectType(int paramInt)
  {
    this.mProjectType = paramInt;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.logger.common.AppConstants
 * JD-Core Version:    0.6.2
 */