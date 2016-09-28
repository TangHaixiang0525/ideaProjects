package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

public final class ITFWriter extends UPCEANWriter
{
  public BitMatrix encode(String paramString, BarcodeFormat paramBarcodeFormat, int paramInt1, int paramInt2, Map<EncodeHintType, ?> paramMap)
    throws WriterException
  {
    if (paramBarcodeFormat != BarcodeFormat.ITF)
      throw new IllegalArgumentException("Can only encode ITF, but got " + paramBarcodeFormat);
    return super.encode(paramString, paramBarcodeFormat, paramInt1, paramInt2, paramMap);
  }

  public byte[] encode(String paramString)
  {
    int i = paramString.length();
    if (i % 2 != 0)
      throw new IllegalArgumentException("The lenght of the input should be even");
    if (i > 80)
      throw new IllegalArgumentException("Requested contents should be less than 80 digits long, but got " + i);
    byte[] arrayOfByte = new byte[9 + i * 9];
    int j = appendPattern(arrayOfByte, 0, new int[] { 1, 1, 1, 1 }, 1);
    for (int k = 0; k < i; k += 2)
    {
      int m = Character.digit(paramString.charAt(k), 10);
      int n = Character.digit(paramString.charAt(k + 1), 10);
      int[] arrayOfInt = new int[18];
      for (int i1 = 0; i1 < 5; i1++)
      {
        arrayOfInt[(i1 << 1)] = ITFReader.PATTERNS[m][i1];
        arrayOfInt[(1 + (i1 << 1))] = ITFReader.PATTERNS[n][i1];
      }
      j += appendPattern(arrayOfByte, j, arrayOfInt, 1);
    }
    (j + appendPattern(arrayOfByte, j, new int[] { 3, 1, 1 }, 1));
    return arrayOfByte;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.ITFWriter
 * JD-Core Version:    0.6.2
 */