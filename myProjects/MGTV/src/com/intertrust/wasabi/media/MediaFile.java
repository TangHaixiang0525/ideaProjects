package com.intertrust.wasabi.media;

import com.intertrust.wasabi.Attribute;
import com.intertrust.wasabi.ErrorCodeException;
import java.util.HashMap;
import java.util.Map;

public final class MediaFile
{
  private long jniPeer;
  private String name;

  public MediaFile(String paramString)
    throws ErrorCodeException, NullPointerException
  {
    if (paramString == null)
      throw new NullPointerException("name parameter cannot be null");
    long[] arrayOfLong = new long[1];
    ErrorCodeException.checkResult(com.intertrust.wasabi.media.jni.MediaFile.open(paramString, arrayOfLong));
    this.jniPeer = arrayOfLong[0];
    this.name = paramString;
  }

  public void close()
  {
    try
    {
      com.intertrust.wasabi.media.jni.MediaFile.close(this.jniPeer);
      this.jniPeer = 0L;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public String getContentId()
  {
    return com.intertrust.wasabi.media.jni.MediaFile.getContentId(this.jniPeer);
  }

  public String getContentType()
  {
    return com.intertrust.wasabi.media.jni.MediaFile.getContentType(this.jniPeer);
  }

  public byte[] getLicense()
    throws ErrorCodeException
  {
    byte[][] arrayOfByte = new byte[1][];
    ErrorCodeException.checkResult(com.intertrust.wasabi.media.jni.MediaFile.getLicense(this.jniPeer, arrayOfByte));
    return arrayOfByte[0];
  }

  public MediaInfo getMediaInfo()
    throws ErrorCodeException
  {
    MediaInfo[] arrayOfMediaInfo = new MediaInfo[1];
    ErrorCodeException.checkResult(com.intertrust.wasabi.media.jni.MediaFile.getMediaInfo(this.jniPeer, arrayOfMediaInfo));
    return arrayOfMediaInfo[0];
  }

  public Attribute getMetadata()
    throws ErrorCodeException
  {
    Attribute[] arrayOfAttribute = new Attribute[1];
    ErrorCodeException.checkResult(com.intertrust.wasabi.media.jni.MediaFile.getMetadata(this.jniPeer, arrayOfAttribute));
    return arrayOfAttribute[0];
  }

  public String getName()
  {
    return this.name;
  }

  public FileProgress getProgress()
    throws ErrorCodeException
  {
    FileProgress[] arrayOfFileProgress = new FileProgress[1];
    ErrorCodeException.checkResult(com.intertrust.wasabi.media.jni.MediaFile.getProgress(this.jniPeer, arrayOfFileProgress));
    return arrayOfFileProgress[0];
  }

  public ProtectionType getProtectionType()
    throws ErrorCodeException
  {
    int[] arrayOfInt = new int[1];
    ErrorCodeException.checkResult(com.intertrust.wasabi.media.jni.MediaFile.getProtectionType(this.jniPeer, arrayOfInt));
    return ProtectionType.values()[arrayOfInt[0]];
  }

  public Map<String, String> getSilentLicenseAcquisitionUrls()
    throws ErrorCodeException
  {
    Attribute[] arrayOfAttribute1 = new Attribute[1];
    ErrorCodeException.checkResult(com.intertrust.wasabi.media.jni.MediaFile.getSilentLicenseAcquisitionUrls(this.jniPeer, arrayOfAttribute1));
    HashMap localHashMap = new HashMap();
    for (Attribute localAttribute : (Attribute[])arrayOfAttribute1[0].getValue())
      localHashMap.put(localAttribute.getName(), (String)localAttribute.getValue());
    return localHashMap;
  }

  public TrackInfo[] getTrackInfoArray()
    throws ErrorCodeException
  {
    TrackInfo[][] arrayOfTrackInfo; = new TrackInfo[1][];
    ErrorCodeException.checkResult(com.intertrust.wasabi.media.jni.MediaFile.getTrackInfoArray(this.jniPeer, arrayOfTrackInfo;));
    return arrayOfTrackInfo;[0];
  }

  public void setLicense(byte[] paramArrayOfByte)
    throws ErrorCodeException, NullPointerException
  {
    if (paramArrayOfByte == null)
      throw new NullPointerException("license parameter cannot be null");
    ErrorCodeException.checkResult(com.intertrust.wasabi.media.jni.MediaFile.setLicense(this.jniPeer, paramArrayOfByte));
  }

  public static enum ProtectionType
  {
    static
    {
      CLEAR_TEXT = new ProtectionType("CLEAR_TEXT", 1);
      DCF = new ProtectionType("DCF", 2);
      PDCF = new ProtectionType("PDCF", 3);
      PIFF = new ProtectionType("PIFF", 4);
      BBTS = new ProtectionType("BBTS", 5);
      CENC = new ProtectionType("CENC", 6);
      HLS_AES = new ProtectionType("HLS_AES", 7);
      ProtectionType[] arrayOfProtectionType = new ProtectionType[8];
      arrayOfProtectionType[0] = UNKNOWN;
      arrayOfProtectionType[1] = CLEAR_TEXT;
      arrayOfProtectionType[2] = DCF;
      arrayOfProtectionType[3] = PDCF;
      arrayOfProtectionType[4] = PIFF;
      arrayOfProtectionType[5] = BBTS;
      arrayOfProtectionType[6] = CENC;
      arrayOfProtectionType[7] = HLS_AES;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.media.MediaFile
 * JD-Core Version:    0.6.2
 */