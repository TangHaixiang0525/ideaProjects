package com.sohu.logger.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils
{
  private static char[] DigitLower = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
  private static char[] DigitUpper = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70 };

  private static String byteHEX(byte paramByte, String paramString)
  {
    if (paramString.equalsIgnoreCase("lower"));
    for (char[] arrayOfChar1 = DigitLower; ; arrayOfChar1 = DigitUpper)
    {
      char[] arrayOfChar2 = new char[2];
      arrayOfChar2[0] = arrayOfChar1[(0xF & paramByte >>> 4)];
      arrayOfChar2[1] = arrayOfChar1[(paramByte & 0xF)];
      return new String(arrayOfChar2);
      if (!paramString.equalsIgnoreCase("upper"))
        break;
    }
    throw new RuntimeException("");
  }

  public static String crypt(String paramString)
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

  public static String getMD5Lower(String paramString)
  {
    try
    {
      String str = processStr(paramString, "lower");
      return str;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      return "";
    }
    catch (NullPointerException localNullPointerException)
    {
      label10: break label10;
    }
  }

  public static String getMD5Upper(String paramString)
  {
    try
    {
      String str = processStr(paramString, "upper");
      return str;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      return "";
    }
    catch (NullPointerException localNullPointerException)
    {
      label10: break label10;
    }
  }

  private static String processStr(String paramString1, String paramString2)
  {
    String str = "";
    MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
    localMessageDigest.update(paramString1.getBytes());
    byte[] arrayOfByte = localMessageDigest.digest();
    int i = arrayOfByte.length;
    for (int j = 0; j < i; j++)
      str = str + byteHEX(arrayOfByte[j], paramString2);
    return str;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.logger.util.MD5Utils
 * JD-Core Version:    0.6.2
 */