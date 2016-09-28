package com.google.zxing.qrcode.encoder;

final class MaskUtil
{
  static int applyMaskPenaltyRule1(ByteMatrix paramByteMatrix)
  {
    return applyMaskPenaltyRule1Internal(paramByteMatrix, true) + applyMaskPenaltyRule1Internal(paramByteMatrix, false);
  }

  private static int applyMaskPenaltyRule1Internal(ByteMatrix paramByteMatrix, boolean paramBoolean)
  {
    int i = 0;
    int j = 0;
    int k = -1;
    int m;
    int n;
    label27: byte[][] arrayOfByte;
    if (paramBoolean)
    {
      m = paramByteMatrix.getHeight();
      if (!paramBoolean)
        break label100;
      n = paramByteMatrix.getWidth();
      arrayOfByte = paramByteMatrix.getArray();
    }
    for (int i1 = 0; ; i1++)
    {
      if (i1 >= m)
        break label150;
      int i2 = 0;
      label46: if (i2 < n)
      {
        int i3;
        if (paramBoolean)
        {
          i3 = arrayOfByte[i1][i2];
          label67: if (i3 != k)
            break label133;
          j++;
          if (j != 5)
            break label122;
          i += 3;
        }
        while (true)
        {
          i2++;
          break label46;
          m = paramByteMatrix.getWidth();
          break;
          label100: n = paramByteMatrix.getHeight();
          break label27;
          i3 = arrayOfByte[i2][i1];
          break label67;
          label122: if (j > 5)
          {
            i++;
            continue;
            j = 1;
            k = i3;
          }
        }
      }
      label133: j = 0;
    }
    label150: return i;
  }

  static int applyMaskPenaltyRule2(ByteMatrix paramByteMatrix)
  {
    int i = 0;
    byte[][] arrayOfByte = paramByteMatrix.getArray();
    int j = paramByteMatrix.getWidth();
    int k = paramByteMatrix.getHeight();
    for (int m = 0; m < k - 1; m++)
      for (int n = 0; n < j - 1; n++)
      {
        int i1 = arrayOfByte[m][n];
        if ((i1 == arrayOfByte[m][(n + 1)]) && (i1 == arrayOfByte[(m + 1)][n]) && (i1 == arrayOfByte[(m + 1)][(n + 1)]))
          i += 3;
      }
    return i;
  }

  static int applyMaskPenaltyRule3(ByteMatrix paramByteMatrix)
  {
    int i = 0;
    byte[][] arrayOfByte = paramByteMatrix.getArray();
    int j = paramByteMatrix.getWidth();
    int k = paramByteMatrix.getHeight();
    for (int m = 0; m < k; m++)
      for (int n = 0; n < j; n++)
      {
        if ((n + 6 < j) && (arrayOfByte[m][n] == 1) && (arrayOfByte[m][(n + 1)] == 0) && (arrayOfByte[m][(n + 2)] == 1) && (arrayOfByte[m][(n + 3)] == 1) && (arrayOfByte[m][(n + 4)] == 1) && (arrayOfByte[m][(n + 5)] == 0) && (arrayOfByte[m][(n + 6)] == 1) && (((n + 10 < j) && (arrayOfByte[m][(n + 7)] == 0) && (arrayOfByte[m][(n + 8)] == 0) && (arrayOfByte[m][(n + 9)] == 0) && (arrayOfByte[m][(n + 10)] == 0)) || ((n - 4 >= 0) && (arrayOfByte[m][(n - 1)] == 0) && (arrayOfByte[m][(n - 2)] == 0) && (arrayOfByte[m][(n - 3)] == 0) && (arrayOfByte[m][(n - 4)] == 0))))
          i += 40;
        if ((m + 6 < k) && (arrayOfByte[m][n] == 1) && (arrayOfByte[(m + 1)][n] == 0) && (arrayOfByte[(m + 2)][n] == 1) && (arrayOfByte[(m + 3)][n] == 1) && (arrayOfByte[(m + 4)][n] == 1) && (arrayOfByte[(m + 5)][n] == 0) && (arrayOfByte[(m + 6)][n] == 1) && (((m + 10 < k) && (arrayOfByte[(m + 7)][n] == 0) && (arrayOfByte[(m + 8)][n] == 0) && (arrayOfByte[(m + 9)][n] == 0) && (arrayOfByte[(m + 10)][n] == 0)) || ((m - 4 >= 0) && (arrayOfByte[(m - 1)][n] == 0) && (arrayOfByte[(m - 2)][n] == 0) && (arrayOfByte[(m - 3)][n] == 0) && (arrayOfByte[(m - 4)][n] == 0))))
          i += 40;
      }
    return i;
  }

  static int applyMaskPenaltyRule4(ByteMatrix paramByteMatrix)
  {
    int i = 0;
    byte[][] arrayOfByte = paramByteMatrix.getArray();
    int j = paramByteMatrix.getWidth();
    int k = paramByteMatrix.getHeight();
    for (int m = 0; m < k; m++)
      for (int i1 = 0; i1 < j; i1++)
        if (arrayOfByte[m][i1] == 1)
          i++;
    int n = paramByteMatrix.getHeight() * paramByteMatrix.getWidth();
    return 10 * (Math.abs((int)(100.0D * (i / n) - 50.0D)) / 5);
  }

  static boolean getDataMaskBit(int paramInt1, int paramInt2, int paramInt3)
  {
    if (!QRCode.isValidMaskPattern(paramInt1))
      throw new IllegalArgumentException("Invalid mask pattern");
    int i;
    switch (paramInt1)
    {
    default:
      throw new IllegalArgumentException("Invalid mask pattern: " + paramInt1);
    case 0:
      i = 0x1 & paramInt3 + paramInt2;
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
    case 7:
    }
    while (i == 0)
    {
      return true;
      i = paramInt3 & 0x1;
      continue;
      i = paramInt2 % 3;
      continue;
      i = (paramInt3 + paramInt2) % 3;
      continue;
      i = 0x1 & (paramInt3 >>> 1) + paramInt2 / 3;
      continue;
      int k = paramInt3 * paramInt2;
      i = (k & 0x1) + k % 3;
      continue;
      int j = paramInt3 * paramInt2;
      i = 0x1 & (j & 0x1) + j % 3;
      continue;
      i = 0x1 & paramInt3 * paramInt2 % 3 + (0x1 & paramInt3 + paramInt2);
    }
    return false;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.qrcode.encoder.MaskUtil
 * JD-Core Version:    0.6.2
 */