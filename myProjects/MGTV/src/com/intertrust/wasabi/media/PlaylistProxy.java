package com.intertrust.wasabi.media;

import com.intertrust.wasabi.ErrorCodeException;
import java.util.EnumSet;

public class PlaylistProxy
{
  private long nativeHandle;

  public PlaylistProxy()
    throws ErrorCodeException
  {
    create(EnumSet.noneOf(Flags.class));
  }

  public PlaylistProxy(EnumSet<Flags> paramEnumSet)
    throws ErrorCodeException
  {
    create(paramEnumSet);
  }

  private void create(EnumSet<Flags> paramEnumSet)
    throws ErrorCodeException, NullPointerException
  {
    if (paramEnumSet == null)
      throw new NullPointerException("flags cannot be null");
    long[] arrayOfLong = new long[1];
    ErrorCodeException.checkResult(com.intertrust.wasabi.media.jni.PlaylistProxy.create(paramEnumSet, arrayOfLong));
    this.nativeHandle = arrayOfLong[0];
  }

  public void getAndClearLastError()
    throws ErrorCodeException
  {
    try
    {
      ErrorCodeException.checkResult(com.intertrust.wasabi.media.jni.PlaylistProxy.getAndClearLastError(this.nativeHandle));
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public String getAuthToken()
    throws ErrorCodeException
  {
    try
    {
      String[] arrayOfString = new String[1];
      ErrorCodeException.checkResult(com.intertrust.wasabi.media.jni.PlaylistProxy.getAuthToken(this.nativeHandle, arrayOfString));
      String str = arrayOfString[0];
      return str;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public String makeUrl(String paramString, MediaSourceType paramMediaSourceType, MediaSourceParams paramMediaSourceParams)
    throws ErrorCodeException
  {
    try
    {
      String[] arrayOfString = new String[1];
      ErrorCodeException.checkResult(com.intertrust.wasabi.media.jni.PlaylistProxy.makeUrl(this.nativeHandle, paramString, paramMediaSourceType, paramMediaSourceParams, arrayOfString));
      String str = arrayOfString[0];
      return str;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void start()
    throws ErrorCodeException
  {
    try
    {
      ErrorCodeException.checkResult(com.intertrust.wasabi.media.jni.PlaylistProxy.start(this.nativeHandle));
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void stop()
    throws ErrorCodeException
  {
    try
    {
      ErrorCodeException.checkResult(com.intertrust.wasabi.media.jni.PlaylistProxy.stop(this.nativeHandle));
      this.nativeHandle = 0L;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void unblockForLicense()
    throws ErrorCodeException
  {
    try
    {
      ErrorCodeException.checkResult(com.intertrust.wasabi.media.jni.PlaylistProxy.unblockForLicense(this.nativeHandle));
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void unblockForLicense(LicenseType paramLicenseType, String paramString)
    throws ErrorCodeException
  {
    try
    {
      ErrorCodeException.checkResult(com.intertrust.wasabi.media.jni.PlaylistProxy.unblockForLicense(this.nativeHandle, paramLicenseType, paramString));
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public static enum Flags
  {
    static
    {
      BLOCK_FOR_LICENSE_EXPLICIT = new Flags("BLOCK_FOR_LICENSE_EXPLICIT", 2);
      NO_SHORT_URLS = new Flags("NO_SHORT_URLS", 3);
      Flags[] arrayOfFlags = new Flags[4];
      arrayOfFlags[0] = AUTH_TOKEN_HEADER;
      arrayOfFlags[1] = BLOCK_FOR_LICENSE_IMPLICIT;
      arrayOfFlags[2] = BLOCK_FOR_LICENSE_EXPLICIT;
      arrayOfFlags[3] = NO_SHORT_URLS;
    }
  }

  public static enum LicenseType
  {
    static
    {
      BB_TOKEN = new LicenseType("BB_TOKEN", 1);
      LicenseType[] arrayOfLicenseType = new LicenseType[2];
      arrayOfLicenseType[0] = MS3_SURL;
      arrayOfLicenseType[1] = BB_TOKEN;
    }
  }

  public static class MediaSourceParams
  {
    public String audioCodecs;
    public String bbtsIndexUrl;
    public int bitrateBitsPerSecond;
    public String contentType;
    public int durationSeconds;
    public String language;
    public String masterPlaylistAppendix;
    public String sourceContentType;
    public String videoCodecs;
  }

  public static enum MediaSourceType
  {
    static
    {
      HLS = new MediaSourceType("HLS", 1);
      DASH = new MediaSourceType("DASH", 2);
      MediaSourceType[] arrayOfMediaSourceType = new MediaSourceType[3];
      arrayOfMediaSourceType[0] = SINGLE_FILE;
      arrayOfMediaSourceType[1] = HLS;
      arrayOfMediaSourceType[2] = DASH;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.media.PlaylistProxy
 * JD-Core Version:    0.6.2
 */