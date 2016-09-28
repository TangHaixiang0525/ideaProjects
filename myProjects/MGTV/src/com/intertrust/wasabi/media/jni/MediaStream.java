package com.intertrust.wasabi.media.jni;

import com.intertrust.wasabi.media.MediaStream.FormatInfo;
import com.intertrust.wasabi.media.MediaStream.SourceType;
import com.intertrust.wasabi.media.MediaStreamInterface;

public final class MediaStream
{
  public static native void close(long paramLong);

  public static native int getContentType(long paramLong, String[] paramArrayOfString);

  public static native int getSize(long paramLong, long[] paramArrayOfLong);

  public static native int open(MediaStreamInterface paramMediaStreamInterface, MediaStream.FormatInfo paramFormatInfo, long[] paramArrayOfLong);

  public static native int openUrl(String paramString, MediaStream.SourceType paramSourceType, MediaStream.FormatInfo paramFormatInfo, long[] paramArrayOfLong);

  public static native int read(long paramLong, byte[] paramArrayOfByte, int paramInt1, int paramInt2, int[] paramArrayOfInt);

  public static native int seek(long paramLong1, long paramLong2);

  public static native int tell(long paramLong, long[] paramArrayOfLong);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.media.jni.MediaStream
 * JD-Core Version:    0.6.2
 */