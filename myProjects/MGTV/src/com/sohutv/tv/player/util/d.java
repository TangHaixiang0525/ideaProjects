package com.sohutv.tv.player.util;

import java.security.MessageDigest;

public class d
{
  private static char[] a = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
  private static char[] b = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70 };

  public static String a(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0))
      return paramString;
    StringBuffer localStringBuffer = new StringBuffer();
    while (true)
    {
      int i;
      try
      {
        MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
        localMessageDigest.update(paramString.getBytes("GBK"));
        byte[] arrayOfByte = localMessageDigest.digest();
        i = 0;
        if (i < arrayOfByte.length)
          if ((0xFF & arrayOfByte[i]) < 16)
            localStringBuffer.append("0" + Integer.toHexString(0xFF & arrayOfByte[i]));
          else
            localStringBuffer.append(Integer.toHexString(0xFF & arrayOfByte[i]));
      }
      catch (Exception localException)
      {
        return "";
      }
      return localStringBuffer.toString();
      i++;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.util.d
 * JD-Core Version:    0.6.2
 */