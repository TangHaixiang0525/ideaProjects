package com.intertrust.wasabi.drm.jni;

import com.intertrust.wasabi.Attribute;
import com.intertrust.wasabi.drm.DateTime;
import com.intertrust.wasabi.drm.Service;
import com.intertrust.wasabi.drm.Subscription;
import com.intertrust.wasabi.drm.User;

public class Engine
{
  public static native int create(com.intertrust.wasabi.drm.Engine paramEngine, long[] paramArrayOfLong);

  public static native void destroy(long paramLong);

  public static native int getLinkIds(long paramLong, String[][] paramArrayOfString);

  public static native int getNodeIds(long paramLong, String[][] paramArrayOfString);

  public static native int getObjectDetails(long paramLong, String paramString, Attribute[] paramArrayOfAttribute);

  public static native int getProperty(long paramLong, String paramString, Object[] paramArrayOfObject);

  public static native int getPropertyNames(long paramLong, String[][] paramArrayOfString);

  public static native int getServiceSubscriptions(long paramLong, String paramString1, String paramString2, Subscription[][] paramArrayOfSubscription);

  public static native int getServiceUsers(long paramLong, String paramString, User[][] paramArrayOfUser);

  public static native int getServices(long paramLong, Service[][] paramArrayOfService);

  public static native int getTrustedTime(long paramLong, DateTime paramDateTime);

  public static native boolean isPersonalized(long paramLong);

  public static native int personalize(long paramLong, String paramString);

  public static native int processServiceToken(long paramLong, String paramString);

  public static native int setProperty(long paramLong, String paramString, Object paramObject);

  public static native int transformUriTemplate(long paramLong, String paramString, String[] paramArrayOfString);

  public static native int updateSecurityData(long paramLong, String paramString, int paramInt);

  public static native int vacuumData(long paramLong, int paramInt);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.drm.jni.Engine
 * JD-Core Version:    0.6.2
 */