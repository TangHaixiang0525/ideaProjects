package com.intertrust.wasabi.licensestore;

import com.intertrust.wasabi.ErrorCodeException;

public class LicenseStore
{
  public static final int WSB_LS_FLAG_LICENSE_DATA = 1;
  public static final int WSB_LS_FLAG_LICENSE_EXPIRE_DATE = 2;
  public static final int WSB_LS_FLAG_LICENSE_INSERT_DATE = 4;
  public static final int WSB_LS_FLAG_LICENSE_TAG = 8;
  private long jniPeer;

  public LicenseStore()
    throws ErrorCodeException
  {
    long[] arrayOfLong = new long[1];
    try
    {
      ErrorCodeException.checkResult(com.intertrust.wasabi.licensestore.jni.LicenseStore.open(arrayOfLong));
      this.jniPeer = arrayOfLong[0];
      return;
    }
    finally
    {
    }
  }

  public int addLicense(String paramString1, String paramString2)
    throws ErrorCodeException, NullPointerException
  {
    int[] arrayOfInt = new int[1];
    if (paramString1 == null)
      throw new NullPointerException("data parameter cannot be null");
    if (paramString2 == null)
      throw new NullPointerException("tag parameter cannot be null");
    try
    {
      ErrorCodeException.checkResult(com.intertrust.wasabi.licensestore.jni.LicenseStore.addLicense(this.jniPeer, paramString1, paramString2, arrayOfInt));
      return arrayOfInt[0];
    }
    finally
    {
    }
  }

  public void close()
    throws ErrorCodeException
  {
    try
    {
      ErrorCodeException.checkResult(com.intertrust.wasabi.licensestore.jni.LicenseStore.close(this.jniPeer));
      this.jniPeer = 0L;
      return;
    }
    finally
    {
    }
  }

  public String[] enumerateContentIds()
    throws ErrorCodeException
  {
    String[][] arrayOfString; = new String[1][];
    try
    {
      ErrorCodeException.checkResult(com.intertrust.wasabi.licensestore.jni.LicenseStore.enumerateContentIds(this.jniPeer, arrayOfString;));
      return arrayOfString;[0];
    }
    finally
    {
    }
  }

  public License[] enumerateLicenses(int paramInt)
    throws ErrorCodeException
  {
    License[][] arrayOfLicense; = new License[1][];
    try
    {
      ErrorCodeException.checkResult(com.intertrust.wasabi.licensestore.jni.LicenseStore.enumerateLicenses(this.jniPeer, paramInt, arrayOfLicense;));
      return arrayOfLicense;[0];
    }
    finally
    {
    }
  }

  public void expungeExpiredLicenses()
    throws ErrorCodeException
  {
    try
    {
      ErrorCodeException.checkResult(com.intertrust.wasabi.licensestore.jni.LicenseStore.expungeExpiredLicenses(this.jniPeer));
      return;
    }
    finally
    {
    }
  }

  public String[] findContentIdsByLicense(int paramInt)
    throws ErrorCodeException
  {
    String[][] arrayOfString; = new String[1][];
    try
    {
      ErrorCodeException.checkResult(com.intertrust.wasabi.licensestore.jni.LicenseStore.findContentIdsByLicense(this.jniPeer, paramInt, arrayOfString;));
      return arrayOfString;[0];
    }
    finally
    {
    }
  }

  public License[] findLicensesByContentIds(String[] paramArrayOfString)
    throws ErrorCodeException, NullPointerException
  {
    License[][] arrayOfLicense; = new License[1][];
    if (paramArrayOfString == null)
      throw new NullPointerException("contentIds parameter cannot be null");
    try
    {
      ErrorCodeException.checkResult(com.intertrust.wasabi.licensestore.jni.LicenseStore.findLicensesByContentIds(this.jniPeer, paramArrayOfString, arrayOfLicense;));
      return arrayOfLicense;[0];
    }
    finally
    {
    }
  }

  public License getLicenseById(int paramInt)
    throws ErrorCodeException
  {
    License[] arrayOfLicense = new License[1];
    try
    {
      ErrorCodeException.checkResult(com.intertrust.wasabi.licensestore.jni.LicenseStore.getLicenseById(this.jniPeer, paramInt, arrayOfLicense));
      return arrayOfLicense[0];
    }
    finally
    {
    }
  }

  public void removeLicense(int paramInt)
    throws ErrorCodeException
  {
    try
    {
      ErrorCodeException.checkResult(com.intertrust.wasabi.licensestore.jni.LicenseStore.removeLicense(this.jniPeer, paramInt));
      return;
    }
    finally
    {
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.intertrust.wasabi.licensestore.LicenseStore
 * JD-Core Version:    0.6.2
 */