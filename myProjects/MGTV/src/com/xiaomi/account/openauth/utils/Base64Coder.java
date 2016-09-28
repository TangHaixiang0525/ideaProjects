package com.xiaomi.account.openauth.utils;

public class Base64Coder
{
  private static char[] map1;
  private static byte[] map2;
  private static final String systemLineSeparator = System.getProperty("line.separator");

  static
  {
    map1 = new char[64];
    int i = 65;
    int i5;
    for (int j = 0; i <= 90; j = i5)
    {
      char[] arrayOfChar5 = map1;
      i5 = j + 1;
      arrayOfChar5[j] = i;
      i = (char)(i + 1);
    }
    int k = 97;
    while (k <= 122)
    {
      char[] arrayOfChar4 = map1;
      int i4 = j + 1;
      arrayOfChar4[j] = k;
      k = (char)(k + 1);
      j = i4;
    }
    int m = 48;
    while (m <= 57)
    {
      char[] arrayOfChar3 = map1;
      int i3 = j + 1;
      arrayOfChar3[j] = m;
      m = (char)(m + 1);
      j = i3;
    }
    char[] arrayOfChar1 = map1;
    int n = j + 1;
    arrayOfChar1[j] = '+';
    char[] arrayOfChar2 = map1;
    (n + 1);
    arrayOfChar2[n] = '/';
    map2 = new byte[''];
    for (int i1 = 0; i1 < map2.length; i1++)
      map2[i1] = -1;
    for (int i2 = 0; i2 < 64; i2++)
      map2[map1[i2]] = ((byte)i2);
  }

  public static byte[] decode(String paramString)
  {
    return decode(paramString.toCharArray());
  }

  public static byte[] decode(char[] paramArrayOfChar)
  {
    return decode(paramArrayOfChar, 0, paramArrayOfChar.length);
  }

  public static byte[] decode(char[] paramArrayOfChar, int paramInt1, int paramInt2)
  {
    if (paramInt2 % 4 != 0)
      throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
    while ((paramInt2 > 0) && (paramArrayOfChar[(-1 + (paramInt1 + paramInt2))] == '='))
      paramInt2--;
    int i = paramInt2 * 3 / 4;
    byte[] arrayOfByte = new byte[i];
    int j = paramInt1 + paramInt2;
    int k = 0;
    int m = paramInt1;
    int i6;
    label178: label189: int i13;
    int i14;
    int i15;
    if (m < j)
    {
      int n = m + 1;
      int i1 = paramArrayOfChar[m];
      int i2 = n + 1;
      int i3 = paramArrayOfChar[n];
      int i4;
      int i5;
      if (i2 < j)
      {
        int i17 = i2 + 1;
        i4 = paramArrayOfChar[i2];
        i2 = i17;
        if (i2 >= j)
          break label178;
        i6 = i2 + 1;
        i5 = paramArrayOfChar[i2];
      }
      while (true)
      {
        if ((i1 <= 127) && (i3 <= 127) && (i4 <= 127) && (i5 <= 127))
          break label189;
        throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
        i4 = 65;
        break;
        i5 = 65;
        i6 = i2;
      }
      int i7 = map2[i1];
      int i8 = map2[i3];
      int i9 = map2[i4];
      int i10 = map2[i5];
      if ((i7 < 0) || (i8 < 0) || (i9 < 0) || (i10 < 0))
        throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
      int i11 = i7 << 2 | i8 >>> 4;
      int i12 = (i8 & 0xF) << 4 | i9 >>> 2;
      i13 = i10 | (i9 & 0x3) << 6;
      i14 = k + 1;
      arrayOfByte[k] = ((byte)i11);
      if (i14 >= i)
        break label363;
      i15 = i14 + 1;
      arrayOfByte[i14] = ((byte)i12);
    }
    while (true)
    {
      int i16;
      if (i15 < i)
      {
        i16 = i15 + 1;
        arrayOfByte[i15] = ((byte)i13);
      }
      while (true)
      {
        k = i16;
        m = i6;
        break;
        return arrayOfByte;
        i16 = i15;
      }
      label363: i15 = i14;
    }
  }

  public static byte[] decodeLines(String paramString)
  {
    char[] arrayOfChar = new char[paramString.length()];
    int i = 0;
    for (int j = 0; j < paramString.length(); j++)
    {
      int k = paramString.charAt(j);
      if ((k != 32) && (k != 13) && (k != 10) && (k != 9))
      {
        int m = i + 1;
        arrayOfChar[i] = k;
        i = m;
      }
    }
    return decode(arrayOfChar, 0, i);
  }

  public static String decodeString(String paramString)
  {
    return new String(decode(paramString));
  }

  public static char[] encode(byte[] paramArrayOfByte)
  {
    return encode(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public static char[] encode(byte[] paramArrayOfByte, int paramInt)
  {
    return encode(paramArrayOfByte, 0, paramInt);
  }

  public static char[] encode(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = (2 + paramInt2 * 4) / 3;
    char[] arrayOfChar = new char[4 * ((paramInt2 + 2) / 3)];
    int j = paramInt1 + paramInt2;
    int k = 0;
    int m = paramInt1;
    if (m < j)
    {
      int n = m + 1;
      int i1 = 0xFF & paramArrayOfByte[m];
      int i2;
      int i3;
      label76: int i4;
      int i5;
      label99: int i9;
      int i12;
      label188: int i13;
      if (n < j)
      {
        i2 = n + 1;
        i3 = 0xFF & paramArrayOfByte[n];
        if (i2 >= j)
          break label245;
        i4 = i2 + 1;
        i5 = 0xFF & paramArrayOfByte[i2];
        int i6 = i1 >>> 2;
        int i7 = (i1 & 0x3) << 4 | i3 >>> 4;
        int i8 = (i3 & 0xF) << 2 | i5 >>> 6;
        i9 = i5 & 0x3F;
        int i10 = k + 1;
        arrayOfChar[k] = map1[i6];
        int i11 = i10 + 1;
        arrayOfChar[i10] = map1[i7];
        if (i11 >= i)
          break label255;
        i12 = map1[i8];
        arrayOfChar[i11] = i12;
        i13 = i11 + 1;
        if (i13 >= i)
          break label262;
      }
      label262: for (int i14 = map1[i9]; ; i14 = 61)
      {
        arrayOfChar[i13] = i14;
        k = i13 + 1;
        m = i4;
        break;
        i2 = n;
        i3 = 0;
        break label76;
        label245: i4 = i2;
        i5 = 0;
        break label99;
        label255: i12 = 61;
        break label188;
      }
    }
    return arrayOfChar;
  }

  public static String encodeLines(byte[] paramArrayOfByte)
  {
    return encodeLines(paramArrayOfByte, 0, paramArrayOfByte.length, 76, systemLineSeparator);
  }

  public static String encodeLines(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3, String paramString)
  {
    int i = paramInt3 * 3 / 4;
    if (i <= 0)
      throw new IllegalArgumentException();
    int j = (-1 + (paramInt2 + i)) / i;
    StringBuilder localStringBuilder = new StringBuilder(4 * ((paramInt2 + 2) / 3) + j * paramString.length());
    int k = 0;
    while (k < paramInt2)
    {
      int m = Math.min(paramInt2 - k, i);
      localStringBuilder.append(encode(paramArrayOfByte, paramInt1 + k, m));
      localStringBuilder.append(paramString);
      k += m;
    }
    return localStringBuilder.toString();
  }

  public static String encodeString(String paramString)
  {
    return new String(encode(paramString.getBytes()));
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.xiaomi.account.openauth.utils.Base64Coder
 * JD-Core Version:    0.6.2
 */