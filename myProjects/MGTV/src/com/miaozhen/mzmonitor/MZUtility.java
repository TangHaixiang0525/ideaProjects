package com.miaozhen.mzmonitor;

import java.security.MessageDigest;

public class MZUtility
{
  public static String MD5(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0))
      return "";
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.reset();
      localMessageDigest.update(paramString.getBytes("UTF-8"));
      String str = toHexString(localMessageDigest.digest(), "");
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }

  public static String SHA1(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0))
      return "";
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("SHA-1");
      localMessageDigest.update(paramString.getBytes("UTF-8"), 0, paramString.length());
      String str = toHexString(localMessageDigest.digest(), "");
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }

  public static long currentUnixTimestamp()
  {
    return System.currentTimeMillis() / 1000L;
  }

  public static int getRandomNum()
  {
    return (int)(10000.0D * Math.random());
  }

  private static String toHexString(byte[] paramArrayOfByte, String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int i = paramArrayOfByte.length;
    int j = 0;
    if (j >= i)
      return localStringBuilder.toString();
    String str = Integer.toHexString(0xFF & paramArrayOfByte[j]);
    if (str.length() == 1)
      localStringBuilder.append("0").append(str);
    while (true)
    {
      j++;
      break;
      localStringBuilder.append(str);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.miaozhen.mzmonitor.MZUtility
 * JD-Core Version:    0.6.2
 */