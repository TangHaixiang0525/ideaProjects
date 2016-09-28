package com.intertrust.wasabi.media.jni;

import com.intertrust.wasabi.Attribute;
import com.intertrust.wasabi.media.FileProgress;
import com.intertrust.wasabi.media.MediaInfo;
import com.intertrust.wasabi.media.TrackInfo;

public class MediaFile
{
  public static native void close(long paramLong);

  public static native String getContentId(long paramLong);

  public static native String getContentType(long paramLong);

  public static native int getLicense(long paramLong, byte[][] paramArrayOfByte);

  public static native int getMediaInfo(long paramLong, MediaInfo[] paramArrayOfMediaInfo);

  public static native int getMetadata(long paramLong, Attribute[] paramArrayOfAttribute);

  public static native int getProgress(long paramLong, FileProgress[] paramArrayOfFileProgress);

  public static native int getProtectionType(long paramLong, int[] paramArrayOfInt);

  public static native int getSilentLicenseAcquisitionUrls(long paramLong, Attribute[] paramArrayOfAttribute);

  public static native int getTrackInfoArray(long paramLong, TrackInfo[][] paramArrayOfTrackInfo);

  public static native int open(String paramString, long[] paramArrayOfLong);

  public static native int setLicense(long paramLong, byte[] paramArrayOfByte);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.media.jni.MediaFile
 * JD-Core Version:    0.6.2
 */