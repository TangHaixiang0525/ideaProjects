package com.intertrust.wasabi.drm;

import com.intertrust.wasabi.ErrorCodeException;
import com.intertrust.wasabi.jni.Runtime;

public final class SdkInfo
{
  private int build;
  private String details;
  private int version;

  public static SdkInfo get()
    throws ErrorCodeException
  {
    SdkInfo localSdkInfo = new SdkInfo();
    try
    {
      ErrorCodeException.checkResult(Runtime.getSdkInfo(localSdkInfo));
      return localSdkInfo;
    }
    finally
    {
    }
  }

  public int getBuild()
  {
    return this.build;
  }

  public String getDetails()
  {
    return this.details;
  }

  public int getVersion()
  {
    return this.version;
  }

  public String toString()
  {
    return "{version: " + this.version + ", build: " + this.build + ", details: " + this.details + "}";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.drm.SdkInfo
 * JD-Core Version:    0.6.2
 */