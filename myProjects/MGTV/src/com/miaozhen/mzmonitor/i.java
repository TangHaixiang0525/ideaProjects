package com.miaozhen.mzmonitor;

class i
{
  private static final byte[] a = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };

  public static String a(String paramString)
  {
    int i = 0;
    if (paramString == null)
      return "";
    byte[] arrayOfByte1 = paramString.getBytes();
    int j = arrayOfByte1.length;
    byte[] arrayOfByte2 = new byte[4 * ((j + 2) / 3)];
    int k = j;
    int m = 0;
    int i3;
    if (k <= 2)
      if (k != 0)
      {
        i3 = i + 1;
        arrayOfByte2[i] = a[(arrayOfByte1[m] >> 2)];
        if (k <= 1)
          break label269;
        int i6 = i3 + 1;
        arrayOfByte2[i3] = a[(((0x3 & arrayOfByte1[m]) << 4) + (arrayOfByte1[(m + 1)] >> 4))];
        int i7 = i6 + 1;
        arrayOfByte2[i6] = a[((0xF & arrayOfByte1[(m + 1)]) << 2)];
        (i7 + 1);
        arrayOfByte2[i7] = 61;
      }
    while (true)
    {
      return new String(arrayOfByte2);
      int n = i + 1;
      arrayOfByte2[i] = a[(arrayOfByte1[m] >> 2)];
      int i1 = n + 1;
      arrayOfByte2[n] = a[(((0x3 & arrayOfByte1[m]) << 4) + (arrayOfByte1[(m + 1)] >> 4))];
      int i2 = i1 + 1;
      arrayOfByte2[i1] = a[(((0xF & arrayOfByte1[(m + 1)]) << 2) + (arrayOfByte1[(m + 2)] >> 6))];
      i = i2 + 1;
      arrayOfByte2[i2] = a[(0x3F & arrayOfByte1[(m + 2)])];
      m += 3;
      k -= 3;
      break;
      label269: int i4 = i3 + 1;
      arrayOfByte2[i3] = a[((0x3 & arrayOfByte1[m]) << 4)];
      int i5 = i4 + 1;
      arrayOfByte2[i4] = 61;
      (i5 + 1);
      arrayOfByte2[i5] = 61;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.miaozhen.mzmonitor.i
 * JD-Core Version:    0.6.2
 */