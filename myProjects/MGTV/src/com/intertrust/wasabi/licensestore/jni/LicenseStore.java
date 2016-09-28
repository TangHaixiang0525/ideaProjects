package com.intertrust.wasabi.licensestore.jni;

import com.intertrust.wasabi.licensestore.License;

public class LicenseStore
{
  public static native int addLicense(long paramLong, String paramString1, String paramString2, int[] paramArrayOfInt);

  public static native int close(long paramLong);

  public static native int enumerateContentIds(long paramLong, String[][] paramArrayOfString);

  public static native int enumerateLicenses(long paramLong, int paramInt, License[][] paramArrayOfLicense);

  public static native int expungeExpiredLicenses(long paramLong);

  public static native int findContentIdsByLicense(long paramLong, int paramInt, String[][] paramArrayOfString);

  public static native int findLicensesByContentIds(long paramLong, String[] paramArrayOfString, License[][] paramArrayOfLicense);

  public static native int getLicenseById(long paramLong, int paramInt, License[] paramArrayOfLicense);

  public static native int open(long[] paramArrayOfLong);

  public static native int removeLicense(long paramLong, int paramInt);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.licensestore.jni.LicenseStore
 * JD-Core Version:    0.6.2
 */