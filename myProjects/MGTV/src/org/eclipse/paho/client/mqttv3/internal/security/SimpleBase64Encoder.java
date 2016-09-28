package org.eclipse.paho.client.mqttv3.internal.security;

public class SimpleBase64Encoder
{
  private static final char[] PWDCHARS_ARRAY = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();
  private static final String PWDCHARS_STRING = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

  public static byte[] decode(String paramString)
  {
    byte[] arrayOfByte1 = paramString.getBytes();
    int i = arrayOfByte1.length;
    byte[] arrayOfByte2 = new byte[i * 3 / 4];
    int j = 0;
    int k = i;
    for (int m = 0; k >= 4; m += 3)
    {
      long l2 = from64(arrayOfByte1, j, 4);
      k -= 4;
      j += 4;
      for (int i1 = 2; i1 >= 0; i1--)
      {
        arrayOfByte2[(m + i1)] = ((byte)(int)(0xFF & l2));
        l2 >>= 8;
      }
    }
    if (k == 3)
    {
      long l1 = from64(arrayOfByte1, j, 3);
      for (int n = 1; n >= 0; n--)
      {
        arrayOfByte2[(m + n)] = ((byte)(int)(0xFF & l1));
        l1 >>= 8;
      }
    }
    if (k == 2)
      arrayOfByte2[m] = ((byte)(int)(0xFF & from64(arrayOfByte1, j, 2)));
    return arrayOfByte2;
  }

  public static String encode(byte[] paramArrayOfByte)
  {
    int i = paramArrayOfByte.length;
    StringBuffer localStringBuffer = new StringBuffer(4 * ((i + 2) / 3));
    int j = 0;
    for (int k = i; k >= 3; k -= 3)
    {
      localStringBuffer.append(to64((0xFF & paramArrayOfByte[j]) << 16 | (0xFF & paramArrayOfByte[(j + 1)]) << 8 | 0xFF & paramArrayOfByte[(j + 2)], 4));
      j += 3;
    }
    if (k == 2)
      localStringBuffer.append(to64((0xFF & paramArrayOfByte[j]) << 8 | 0xFF & paramArrayOfByte[(j + 1)], 3));
    if (k == 1)
      localStringBuffer.append(to64(0xFF & paramArrayOfByte[j], 2));
    return localStringBuffer.toString();
  }

  private static final long from64(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    long l1 = 0L;
    int i = 0;
    int k;
    for (int j = paramInt1; paramInt2 > 0; j = k)
    {
      paramInt2--;
      long l2 = 0L;
      k = j + 1;
      int m = paramArrayOfByte[j];
      if (m == 47)
        l2 = 1L;
      if ((m >= 48) && (m <= 57))
        l2 = -48 + (m + 2);
      if ((m >= 65) && (m <= 90))
        l2 = -65 + (m + 12);
      if ((m >= 97) && (m <= 122))
        l2 = -97 + (m + 38);
      l1 += (l2 << i);
      i += 6;
    }
    return l1;
  }

  private static final String to64(long paramLong, int paramInt)
  {
    StringBuffer localStringBuffer = new StringBuffer(paramInt);
    while (paramInt > 0)
    {
      paramInt--;
      localStringBuffer.append(PWDCHARS_ARRAY[((int)(0x3F & paramLong))]);
      paramLong >>= 6;
    }
    return localStringBuffer.toString();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     org.eclipse.paho.client.mqttv3.internal.security.SimpleBase64Encoder
 * JD-Core Version:    0.6.2
 */