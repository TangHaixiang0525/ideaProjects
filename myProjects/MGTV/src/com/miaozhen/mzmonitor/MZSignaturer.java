package com.miaozhen.mzmonitor;

import android.content.Context;

public class MZSignaturer
{
  public static String[] randomKey = { "ec0fe20119c925a8", "d304fd244a67db36", "5aeae0e31c86ab84", "6c64a61e7edb9b54", "3b044c3507f0899c", "ecbe51ab2c79224d", "bdb4a3a756759e1c", "0f29a61dac5c227e", "8c77f8b8f5a8f2e9", "0e000ebd6e211ef4", "5bcbcbd118632aff", "381712371a7e869e", "5ff31aaf8e07b74d", "55a3ac9a01d1d2b0", "2317d45ce0048f87", "c03a3e2a68d851f0" };

  public static String getSignature(Context paramContext, String paramString)
  {
    MZDeviceInfo localMZDeviceInfo = MZDeviceInfo.getDeviceInfo(paramContext);
    long l = MZUtility.currentUnixTimestamp();
    String str = localMZDeviceInfo.getPackageName();
    StringBuffer localStringBuffer1 = new StringBuffer();
    StringBuffer localStringBuffer2 = new StringBuffer();
    localStringBuffer2.append(paramString.toLowerCase());
    localStringBuffer2.append(randomKey[((int)(l % 16L))]);
    localStringBuffer2.append(str.toLowerCase());
    localStringBuffer1.append(MZUtility.MD5(localStringBuffer2.toString()));
    localStringBuffer1.append("v");
    localStringBuffer1.append(MZSdkProfile.getSignVersion(paramContext));
    localStringBuffer1.append("t");
    localStringBuffer1.append(l);
    localStringBuffer1.append("k");
    localStringBuffer1.append(str.toLowerCase());
    return localStringBuffer1.toString();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.miaozhen.mzmonitor.MZSignaturer
 * JD-Core Version:    0.6.2
 */