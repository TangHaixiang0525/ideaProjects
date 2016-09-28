package com.umeng.analytics;

import java.security.MessageDigest;
import java.util.Locale;
import java.util.Random;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import u.aly.br;

public class b
{
  private static final byte[] a = { 10, 1, 11, 5, 4, 15, 7, 9, 23, 3, 1, 6, 8, 12, 13, 91 };

  public static int a(int paramInt, String paramString)
  {
    if (new Random().nextFloat() < 0.001D)
      if (paramString == null)
        br.b("--->", "null signature..");
    try
    {
      int k = Integer.parseInt(paramString.substring(9, 11), 16);
      j = k;
      int i = 1000 * (j | 0x80);
      do
      {
        return i;
        i = new Random().nextInt(paramInt);
      }
      while ((i > 255000) || (i < 128000));
      return 127000;
    }
    catch (Exception localException)
    {
      while (true)
        int j = 0;
    }
  }

  public static String a(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      return null;
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < paramArrayOfByte.length; i++)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Byte.valueOf(paramArrayOfByte[i]);
      localStringBuffer.append(String.format("%02X", arrayOfObject));
    }
    return localStringBuffer.toString().toLowerCase(Locale.US);
  }

  public static byte[] a(String paramString)
  {
    byte[] arrayOfByte = null;
    if (paramString == null);
    while (true)
    {
      return arrayOfByte;
      int i = paramString.length();
      int j = i % 2;
      arrayOfByte = null;
      if (j == 0)
      {
        arrayOfByte = new byte[i / 2];
        for (int k = 0; k < i; k += 2)
          arrayOfByte[(k / 2)] = ((byte)Integer.valueOf(paramString.substring(k, k + 2), 16).intValue());
      }
    }
  }

  public static byte[] a(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws Exception
  {
    Cipher localCipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
    localCipher.init(1, new SecretKeySpec(paramArrayOfByte2, "AES"), new IvParameterSpec(a));
    return localCipher.doFinal(paramArrayOfByte1);
  }

  public static byte[] b(byte[] paramArrayOfByte)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.reset();
      localMessageDigest.update(paramArrayOfByte);
      byte[] arrayOfByte = localMessageDigest.digest();
      return arrayOfByte;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public static byte[] b(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws Exception
  {
    Cipher localCipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
    localCipher.init(2, new SecretKeySpec(paramArrayOfByte2, "AES"), new IvParameterSpec(a));
    return localCipher.doFinal(paramArrayOfByte1);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.umeng.analytics.b
 * JD-Core Version:    0.6.2
 */