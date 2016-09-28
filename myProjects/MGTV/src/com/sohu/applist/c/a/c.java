package com.sohu.applist.c.a;

import java.util.ArrayList;
import java.util.Random;

public final class c
{
  public static byte a(char paramChar)
  {
    return (byte)"0123456789ABCDEF".indexOf(paramChar);
  }

  public static String a(String paramString1, String paramString2)
  {
    int i = 0;
    if ((c(paramString1)) || (c(paramString2)))
      return null;
    ArrayList localArrayList1 = new ArrayList();
    ArrayList localArrayList2 = new ArrayList();
    int j = paramString1.getBytes().length;
    int k = j % 16;
    if (k != 0)
    {
      Random localRandom = new Random();
      String str1 = "";
      for (int m = 0; m < 16 - k; m++)
        str1 = str1 + (char)(48 + localRandom.nextInt(10)) + "";
      paramString1 = paramString1 + str1;
    }
    byte[] arrayOfByte1 = paramString1.getBytes();
    int n = arrayOfByte1.length / 16;
    for (int i1 = 0; i1 < n; i1++)
    {
      byte[] arrayOfByte3 = new byte[16];
      int i5 = i1 * 16;
      int i6 = 0;
      while (i5 < 16 + i1 * 16)
      {
        arrayOfByte3[i6] = arrayOfByte1[i5];
        i6++;
        i5++;
      }
      localArrayList1.add(arrayOfByte3);
    }
    for (int i2 = 0; i2 < localArrayList1.size(); i2++)
      localArrayList2.add(a(paramString2, (byte[])localArrayList1.get(i2)));
    String str2 = String.valueOf(j);
    StringBuilder localStringBuilder1 = new StringBuilder();
    for (int i3 = 0; i3 < str2.length(); i3++)
    {
      localStringBuilder1.append('0');
      localStringBuilder1.append(str2.charAt(i3));
    }
    new byte[16];
    byte[] arrayOfByte2 = a("00000000000000000000000000000000".substring(0, "00000000000000000000000000000000".length() - localStringBuilder1.toString().length()) + localStringBuilder1.toString());
    for (int i4 = 0; i4 < arrayOfByte2.length; i4++)
      arrayOfByte2[i4] = ((byte)(48 + arrayOfByte2[i4]));
    localArrayList2.add(a(paramString2, arrayOfByte2));
    StringBuilder localStringBuilder2 = new StringBuilder();
    while (i < localArrayList2.size())
    {
      localStringBuilder2.append(a((byte[])localArrayList2.get(i)));
      localStringBuilder2.append("");
      i++;
    }
    return localStringBuilder2.toString().trim();
  }

  public static String a(byte[] paramArrayOfByte)
  {
    StringBuilder localStringBuilder = new StringBuilder("");
    for (int i = 0; i < paramArrayOfByte.length; i++)
    {
      String str = Integer.toHexString(0xFF & paramArrayOfByte[i]);
      if (str.length() == 1)
        str = "0" + str;
      localStringBuilder.append(str);
      localStringBuilder.append("");
    }
    return localStringBuilder.toString().toUpperCase().trim();
  }

  public static byte[] a(String paramString)
  {
    byte[] arrayOfByte;
    if ((paramString == null) || (paramString.equals("")))
      arrayOfByte = null;
    while (true)
    {
      return arrayOfByte;
      String str = paramString.toUpperCase();
      int i = str.length() / 2;
      char[] arrayOfChar = str.toCharArray();
      arrayOfByte = new byte[i];
      for (int j = 0; j < i; j++)
      {
        int k = j * 2;
        arrayOfByte[j] = ((byte)(a(arrayOfChar[k]) << 4 | a(arrayOfChar[(k + 1)])));
      }
    }
  }

  private static byte[] a(String paramString, byte[] paramArrayOfByte)
  {
    d locald = new d();
    locald.a(paramString.getBytes(), 256);
    byte[] arrayOfByte = new byte[16];
    locald.a(paramArrayOfByte, arrayOfByte);
    return arrayOfByte;
  }

  public static String b(String paramString)
  {
    byte[] arrayOfByte = a(paramString);
    char[] arrayOfChar = new char[32];
    for (int i = 0; i < arrayOfByte.length; i++)
      arrayOfChar[i] = ((char)arrayOfByte[i]);
    return String.valueOf(arrayOfChar);
  }

  public static boolean c(String paramString)
  {
    return (paramString == null) || ("".equals(paramString));
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.applist.c.a.c
 * JD-Core Version:    0.6.2
 */