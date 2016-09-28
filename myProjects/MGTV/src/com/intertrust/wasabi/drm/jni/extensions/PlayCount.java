package com.intertrust.wasabi.drm.jni.extensions;

public class PlayCount
{
  public static native int addEvent(long paramLong, String paramString1, String paramString2);

  public static native int close(long paramLong);

  public static native int open(long[] paramArrayOfLong);

  public static native int uploadEvents(long paramLong, String paramString);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.drm.jni.extensions.PlayCount
 * JD-Core Version:    0.6.2
 */