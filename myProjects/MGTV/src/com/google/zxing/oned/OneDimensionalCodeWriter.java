package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

public abstract class OneDimensionalCodeWriter
  implements Writer
{
  private final int sidesMargin;

  protected OneDimensionalCodeWriter(int paramInt)
  {
    this.sidesMargin = paramInt;
  }

  protected static int appendPattern(byte[] paramArrayOfByte, int paramInt1, int[] paramArrayOfInt, int paramInt2)
  {
    if ((paramInt2 != 0) && (paramInt2 != 1))
      throw new IllegalArgumentException("startColor must be either 0 or 1, but got: " + paramInt2);
    int i = (byte)paramInt2;
    int j = 0;
    int k = paramArrayOfInt.length;
    for (int m = 0; m < k; m++)
    {
      int n = paramArrayOfInt[m];
      for (int i1 = 0; i1 < n; i1++)
      {
        paramArrayOfByte[paramInt1] = i;
        paramInt1++;
        j++;
      }
      i = (byte)(i ^ 0x1);
    }
    return j;
  }

  private BitMatrix renderResult(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = paramArrayOfByte.length;
    int j = i + this.sidesMargin;
    int k = Math.max(paramInt1, j);
    int m = Math.max(1, paramInt2);
    int n = k / j;
    int i1 = (k - i * n) / 2;
    BitMatrix localBitMatrix = new BitMatrix(k, m);
    int i2 = 0;
    int i3 = i1;
    while (i2 < i)
    {
      if (paramArrayOfByte[i2] == 1)
        localBitMatrix.setRegion(i3, 0, n, m);
      i2++;
      i3 += n;
    }
    return localBitMatrix;
  }

  public BitMatrix encode(String paramString, BarcodeFormat paramBarcodeFormat, int paramInt1, int paramInt2)
    throws WriterException
  {
    return encode(paramString, paramBarcodeFormat, paramInt1, paramInt2, null);
  }

  public BitMatrix encode(String paramString, BarcodeFormat paramBarcodeFormat, int paramInt1, int paramInt2, Map<EncodeHintType, ?> paramMap)
    throws WriterException
  {
    if (paramString.length() == 0)
      throw new IllegalArgumentException("Found empty contents");
    if ((paramInt1 < 0) || (paramInt2 < 0))
      throw new IllegalArgumentException("Negative size is not allowed. Input: " + paramInt1 + 'x' + paramInt2);
    return renderResult(encode(paramString), paramInt1, paramInt2);
  }

  public abstract byte[] encode(String paramString);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.OneDimensionalCodeWriter
 * JD-Core Version:    0.6.2
 */