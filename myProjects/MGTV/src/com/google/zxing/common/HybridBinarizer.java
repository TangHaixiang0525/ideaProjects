package com.google.zxing.common;

import com.google.zxing.Binarizer;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import java.lang.reflect.Array;

public final class HybridBinarizer extends GlobalHistogramBinarizer
{
  private static final int BLOCK_SIZE = 8;
  private static final int BLOCK_SIZE_MASK = 7;
  private static final int BLOCK_SIZE_POWER = 3;
  private static final int MINIMUM_DIMENSION = 40;
  private BitMatrix matrix;

  public HybridBinarizer(LuminanceSource paramLuminanceSource)
  {
    super(paramLuminanceSource);
  }

  private static int[][] calculateBlackPoints(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int[] arrayOfInt = { paramInt2, paramInt1 };
    int[][] arrayOfInt1 = (int[][])Array.newInstance(Integer.TYPE, arrayOfInt);
    for (int i = 0; i < paramInt2; i++)
    {
      int j = i << 3;
      if (j + 8 >= paramInt4)
        j = paramInt4 - 8;
      for (int k = 0; k < paramInt1; k++)
      {
        int m = k << 3;
        if (m + 8 >= paramInt3)
          m = paramInt3 - 8;
        int n = 0;
        int i1 = 255;
        int i2 = 0;
        int i3 = 0;
        int i4 = m + j * paramInt3;
        while (i3 < 8)
        {
          for (int i7 = 0; i7 < 8; i7++)
          {
            int i8 = 0xFF & paramArrayOfByte[(i4 + i7)];
            n += i8;
            if (i8 < i1)
              i1 = i8;
            if (i8 > i2)
              i2 = i8;
          }
          i3++;
          i4 += paramInt3;
        }
        int i5 = n >> 6;
        if (i2 - i1 <= 24)
        {
          i5 = i1 >> 1;
          if ((i > 0) && (k > 0))
          {
            int i6 = arrayOfInt1[(i - 1)][k] + 2 * arrayOfInt1[i][(k - 1)] + arrayOfInt1[(i - 1)][(k - 1)] >> 2;
            if (i1 < i6)
              i5 = i6;
          }
        }
        arrayOfInt1[i][k] = i5;
      }
    }
    return arrayOfInt1;
  }

  private static void calculateThresholdForBlock(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[][] paramArrayOfInt, BitMatrix paramBitMatrix)
  {
    for (int i = 0; i < paramInt2; i++)
    {
      int j = i << 3;
      if (j + 8 >= paramInt4)
        j = paramInt4 - 8;
      for (int k = 0; k < paramInt1; k++)
      {
        int m = k << 3;
        if (m + 8 >= paramInt3)
          m = paramInt3 - 8;
        int n;
        label80: int i1;
        if (k > 1)
        {
          n = k;
          if (n >= paramInt1 - 2)
            break label175;
          if (i <= 1)
            break label183;
          i1 = i;
          label90: if (i1 >= paramInt2 - 2)
            break label189;
        }
        int i2;
        while (true)
        {
          i2 = 0;
          for (int i3 = -2; i3 <= 2; i3++)
          {
            int[] arrayOfInt = paramArrayOfInt[(i1 + i3)];
            i2 += arrayOfInt[(n - 2)] + arrayOfInt[(n - 1)] + arrayOfInt[n] + arrayOfInt[(n + 1)] + arrayOfInt[(n + 2)];
          }
          n = 2;
          break;
          label175: n = paramInt1 - 3;
          break label80;
          label183: i1 = 2;
          break label90;
          label189: i1 = paramInt2 - 3;
        }
        threshold8x8Block(paramArrayOfByte, m, j, i2 / 25, paramInt3, paramBitMatrix);
      }
    }
  }

  private static void threshold8x8Block(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, BitMatrix paramBitMatrix)
  {
    int i = 0;
    int j = paramInt1 + paramInt2 * paramInt4;
    while (i < 8)
    {
      for (int k = 0; k < 8; k++)
        if ((0xFF & paramArrayOfByte[(j + k)]) <= paramInt3)
          paramBitMatrix.set(paramInt1 + k, paramInt2 + i);
      i++;
      j += paramInt4;
    }
  }

  public Binarizer createBinarizer(LuminanceSource paramLuminanceSource)
  {
    return new HybridBinarizer(paramLuminanceSource);
  }

  public BitMatrix getBlackMatrix()
    throws NotFoundException
  {
    if (this.matrix != null)
      return this.matrix;
    LuminanceSource localLuminanceSource = getLuminanceSource();
    BitMatrix localBitMatrix;
    if ((localLuminanceSource.getWidth() >= 40) && (localLuminanceSource.getHeight() >= 40))
    {
      byte[] arrayOfByte = localLuminanceSource.getMatrix();
      int i = localLuminanceSource.getWidth();
      int j = localLuminanceSource.getHeight();
      int k = i >> 3;
      if ((i & 0x7) != 0)
        k++;
      int m = j >> 3;
      if ((j & 0x7) != 0)
        m++;
      int[][] arrayOfInt = calculateBlackPoints(arrayOfByte, k, m, i, j);
      localBitMatrix = new BitMatrix(i, j);
      calculateThresholdForBlock(arrayOfByte, k, m, i, j, arrayOfInt, localBitMatrix);
    }
    for (this.matrix = localBitMatrix; ; this.matrix = super.getBlackMatrix())
      return this.matrix;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.common.HybridBinarizer
 * JD-Core Version:    0.6.2
 */