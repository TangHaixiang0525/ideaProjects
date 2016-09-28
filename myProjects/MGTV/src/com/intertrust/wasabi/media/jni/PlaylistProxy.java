package com.intertrust.wasabi.media.jni;

import com.intertrust.wasabi.media.PlaylistProxy.Flags;
import com.intertrust.wasabi.media.PlaylistProxy.LicenseType;
import com.intertrust.wasabi.media.PlaylistProxy.MediaSourceParams;
import com.intertrust.wasabi.media.PlaylistProxy.MediaSourceType;
import java.util.EnumSet;

public class PlaylistProxy
{
  public static native int create(EnumSet<PlaylistProxy.Flags> paramEnumSet, long[] paramArrayOfLong);

  public static native int getAndClearLastError(long paramLong);

  public static native int getAuthToken(long paramLong, String[] paramArrayOfString);

  public static native int makeUrl(long paramLong, String paramString, PlaylistProxy.MediaSourceType paramMediaSourceType, PlaylistProxy.MediaSourceParams paramMediaSourceParams, String[] paramArrayOfString);

  public static native int start(long paramLong);

  public static native int stop(long paramLong);

  public static native int unblockForLicense(long paramLong);

  public static native int unblockForLicense(long paramLong, PlaylistProxy.LicenseType paramLicenseType, String paramString);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.media.jni.PlaylistProxy
 * JD-Core Version:    0.6.2
 */