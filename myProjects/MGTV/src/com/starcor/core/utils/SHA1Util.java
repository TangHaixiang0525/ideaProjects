package com.starcor.core.utils;

import java.io.PrintStream;

public class SHA1Util
{
  private static final String b64pad = "=";
  private static final int chrsz = 8;
  private static final boolean hexcase;

  public static String b64_hmac_sha1(String paramString1, String paramString2)
  {
    return binb2b64(core_hmac_sha1(paramString1, paramString2));
  }

  public static String b64_sha1(String paramString)
  {
    if (paramString == null)
      paramString = "";
    return binb2b64(core_sha1(str2binb(paramString), 8 * paramString.length()));
  }

  private static String binb2b64(int[] paramArrayOfInt)
  {
    String str = "";
    int[] arrayOfInt = strechbinarray(paramArrayOfInt, 4 * paramArrayOfInt.length);
    for (int i = 0; i < 4 * arrayOfInt.length; i += 3)
    {
      int j = (0xFF & arrayOfInt[(i >> 2)] >> 8 * (3 - i % 4)) << 16 | (0xFF & arrayOfInt[(i + 1 >> 2)] >> 8 * (3 - (i + 1) % 4)) << 8 | 0xFF & arrayOfInt[(i + 2 >> 2)] >> 8 * (3 - (i + 2) % 4);
      int k = 0;
      if (k < 4)
      {
        if (i * 8 + k * 6 > 32 * arrayOfInt.length);
        for (str = str + "="; ; str = str + "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz0123456789+/".charAt(0x3F & j >> 6 * (3 - k)))
        {
          k++;
          break;
        }
      }
    }
    return cleanb64str(str);
  }

  private static String binb2hex(int[] paramArrayOfInt)
  {
    String str = "";
    for (int i = 0; i < 4 * paramArrayOfInt.length; i++)
    {
      char c1 = "0123456789abcdef".charAt(0xF & paramArrayOfInt[(i >> 2)] >> 4 + 8 * (3 - i % 4));
      char c2 = "0123456789abcdef".charAt(0xF & paramArrayOfInt[(i >> 2)] >> 8 * (3 - i % 4));
      str = str + Character.toString(c1) + Character.toString(c2);
    }
    return str;
  }

  private static String binb2str(int[] paramArrayOfInt)
  {
    String str = "";
    for (int i = 0; i < 32 * paramArrayOfInt.length; i += 8)
      str = str + (char)(0xFF & paramArrayOfInt[(i >> 5)] >>> 24 - i % 32);
    return str;
  }

  private static int bit_rol(int paramInt1, int paramInt2)
  {
    return paramInt1 << paramInt2 | paramInt1 >>> 32 - paramInt2;
  }

  private static String cleanb64str(String paramString)
  {
    if (paramString == null)
      paramString = "";
    int i = paramString.length();
    if (i <= 1)
      return paramString;
    int j = paramString.charAt(i - 1);
    String str = "";
    for (int k = i - 1; (k >= 0) && (paramString.charAt(k) == j); k--)
      str = str + paramString.charAt(k);
    return paramString.substring(0, paramString.indexOf(str));
  }

  private static int[] complete216(int[] paramArrayOfInt)
  {
    if (paramArrayOfInt.length >= 16)
      return paramArrayOfInt;
    int[] arrayOfInt = new int[16 - paramArrayOfInt.length];
    for (int i = 0; i < arrayOfInt.length; i++)
      arrayOfInt[i] = 0;
    return concat(paramArrayOfInt, arrayOfInt);
  }

  private static int[] concat(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    int[] arrayOfInt = new int[paramArrayOfInt1.length + paramArrayOfInt2.length];
    int i = 0;
    if (i < paramArrayOfInt1.length + paramArrayOfInt2.length)
    {
      if (i < paramArrayOfInt1.length)
        arrayOfInt[i] = paramArrayOfInt1[i];
      while (true)
      {
        i++;
        break;
        arrayOfInt[i] = paramArrayOfInt2[(i - paramArrayOfInt1.length)];
      }
    }
    return arrayOfInt;
  }

  private static int[] core_hmac_sha1(String paramString1, String paramString2)
  {
    if (paramString1 == null)
      paramString1 = "";
    if (paramString2 == null)
      paramString2 = "";
    int[] arrayOfInt1 = complete216(str2binb(paramString1));
    if (arrayOfInt1.length > 16)
      arrayOfInt1 = core_sha1(arrayOfInt1, 8 * paramString1.length());
    int[] arrayOfInt2 = new int[16];
    int[] arrayOfInt3 = new int[16];
    for (int i = 0; i < 16; i++)
    {
      arrayOfInt2[i] = 0;
      arrayOfInt3[i] = 0;
    }
    for (int j = 0; j < 16; j++)
    {
      arrayOfInt2[j] = (0x36363636 ^ arrayOfInt1[j]);
      arrayOfInt3[j] = (0x5C5C5C5C ^ arrayOfInt1[j]);
    }
    return core_sha1(concat(arrayOfInt3, core_sha1(concat(arrayOfInt2, str2binb(paramString2)), 512 + 8 * paramString2.length())), 672);
  }

  private static int[] core_sha1(int[] paramArrayOfInt, int paramInt)
  {
    int[] arrayOfInt1 = strechbinarray(paramArrayOfInt, paramInt >> 5);
    int i = paramInt >> 5;
    arrayOfInt1[i] |= 128 << 24 - paramInt % 32;
    int[] arrayOfInt2 = strechbinarray(arrayOfInt1, 15 + (paramInt + 64 >> 9 << 4));
    arrayOfInt2[(15 + (paramInt + 64 >> 9 << 4))] = paramInt;
    int[] arrayOfInt3 = new int[80];
    int j = 1732584193;
    int k = -271733879;
    int m = -1732584194;
    int n = 271733878;
    int i1 = -1009589776;
    for (int i2 = 0; i2 < arrayOfInt2.length; i2 += 16)
    {
      int i3 = j;
      int i4 = k;
      int i5 = m;
      int i6 = n;
      int i7 = i1;
      int i8 = 0;
      if (i8 < 80)
      {
        if (i8 < 16)
          arrayOfInt3[i8] = arrayOfInt2[(i2 + i8)];
        while (true)
        {
          int i9 = safe_add(safe_add(rol(j, 5), sha1_ft(i8, k, m, n)), safe_add(safe_add(i1, arrayOfInt3[i8]), sha1_kt(i8)));
          i1 = n;
          n = m;
          m = rol(k, 30);
          k = j;
          j = i9;
          i8++;
          break;
          arrayOfInt3[i8] = rol(arrayOfInt3[(i8 - 3)] ^ arrayOfInt3[(i8 - 8)] ^ arrayOfInt3[(i8 - 14)] ^ arrayOfInt3[(i8 - 16)], 1);
        }
      }
      j = safe_add(j, i3);
      k = safe_add(k, i4);
      m = safe_add(m, i5);
      n = safe_add(n, i6);
      i1 = safe_add(i1, i7);
    }
    return new int[] { j, k, m, n, i1 };
  }

  private static void dotest()
  {
    System.out.println("hex_sha1(" + "data" + ")=" + hex_sha1("data"));
    System.out.println("b64_sha1(" + "data" + ")=" + b64_sha1("data"));
    System.out.println("str_sha1(" + "data" + ")=" + str_sha1("data"));
    System.out.println("hex_hmac_sha1(" + "key" + "," + "data" + ")=" + hex_hmac_sha1("key", "data"));
    System.out.println("b64_hmac_sha1(" + "key" + "," + "data" + ")=" + b64_hmac_sha1("key", "data"));
    System.out.println("str_hmac_sha1(" + "key" + "," + "data" + ")=" + str_hmac_sha1("key", "data"));
  }

  public static String hex_hmac_sha1(String paramString1, String paramString2)
  {
    return binb2hex(core_hmac_sha1(paramString1, paramString2));
  }

  public static String hex_sha1(String paramString)
  {
    if (paramString == null)
      paramString = "";
    return binb2hex(core_sha1(str2binb(paramString), 8 * paramString.length()));
  }

  private static int rol(int paramInt1, int paramInt2)
  {
    return paramInt1 << paramInt2 | paramInt1 >>> 32 - paramInt2;
  }

  private static int safe_add(int paramInt1, int paramInt2)
  {
    int i = (paramInt1 & 0xFFFF) + (paramInt2 & 0xFFFF);
    return (paramInt1 >> 16) + (paramInt2 >> 16) + (i >> 16) << 16 | i & 0xFFFF;
  }

  private static int sha1_ft(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramInt1 < 20)
      return paramInt2 & paramInt3 | paramInt4 & (paramInt2 ^ 0xFFFFFFFF);
    if (paramInt1 < 40)
      return paramInt4 ^ (paramInt2 ^ paramInt3);
    if (paramInt1 < 60)
      return paramInt2 & paramInt3 | paramInt2 & paramInt4 | paramInt3 & paramInt4;
    return paramInt4 ^ (paramInt2 ^ paramInt3);
  }

  private static int sha1_kt(int paramInt)
  {
    if (paramInt < 20)
      return 1518500249;
    if (paramInt < 40)
      return 1859775393;
    if (paramInt < 60)
      return -1894007588;
    return -899497514;
  }

  private static boolean sha1_vm_test()
  {
    return hex_sha1("abc").equals("a9993e364706816aba3e25717850c26c9cd0d89d");
  }

  private static int[] str2binb(String paramString)
  {
    if (paramString == null)
      paramString = "";
    int[] arrayOfInt1 = new int[8 * paramString.length()];
    for (int i = 0; i < 8 * paramString.length(); i += 8)
    {
      int m = i >> 5;
      arrayOfInt1[m] |= (0xFF & paramString.charAt(i / 8)) << 24 - i % 32;
    }
    int j = 0;
    int k = 0;
    while ((k < arrayOfInt1.length) && (arrayOfInt1[k] != 0))
    {
      k++;
      j++;
    }
    int[] arrayOfInt2 = new int[j];
    System.arraycopy(arrayOfInt1, 0, arrayOfInt2, 0, j);
    return arrayOfInt2;
  }

  public static String str_hmac_sha1(String paramString1, String paramString2)
  {
    return binb2str(core_hmac_sha1(paramString1, paramString2));
  }

  public static String str_sha1(String paramString)
  {
    if (paramString == null)
      paramString = "";
    return binb2str(core_sha1(str2binb(paramString), 8 * paramString.length()));
  }

  private static int[] strechbinarray(int[] paramArrayOfInt, int paramInt)
  {
    int i = paramArrayOfInt.length;
    if (i >= paramInt + 1)
      return paramArrayOfInt;
    int[] arrayOfInt = new int[paramInt + 1];
    for (int j = 0; j < paramInt; j++)
      arrayOfInt[j] = 0;
    for (int k = 0; k < i; k++)
      arrayOfInt[k] = paramArrayOfInt[k];
    return arrayOfInt;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.utils.SHA1Util
 * JD-Core Version:    0.6.2
 */