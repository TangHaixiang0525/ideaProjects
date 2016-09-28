package com.intertrust.wasabi.jni;

import com.intertrust.wasabi.Runtime.Property;
import com.intertrust.wasabi.drm.SdkInfo;

public final class Runtime
{
  public static native int checkLicense(String paramString);

  public static native int getProperty(Runtime.Property paramProperty, Object[] paramArrayOfObject);

  public static native int getSdkInfo(SdkInfo paramSdkInfo);

  public static native int initializeEx(String paramString1, String paramString2);

  public static native boolean isPersonalized();

  public static native int personalize(String paramString);

  public static native int processServiceToken(String paramString);

  public static native int setProperty(Runtime.Property paramProperty, Object paramObject);

  public static native int shutdown();
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.jni.Runtime
 * JD-Core Version:    0.6.2
 */