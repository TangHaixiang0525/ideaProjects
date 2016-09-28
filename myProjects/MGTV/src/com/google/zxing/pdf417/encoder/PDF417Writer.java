package com.google.zxing.pdf417.encoder;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.lang.reflect.Array;
import java.util.Map;

public final class PDF417Writer
  implements Writer
{
  private static BitMatrix bitMatrixFromEncoder(PDF417 paramPDF417, String paramString, int paramInt1, int paramInt2)
    throws WriterException
  {
    paramPDF417.generateBarcodeLogic(paramString, 2);
    byte[][] arrayOfByte1 = paramPDF417.getBarcodeMatrix().getScaledMatrix(2, 8);
    int i;
    int j;
    label40: int m;
    int n;
    int i1;
    if (paramInt2 > paramInt1)
    {
      i = 1;
      if (arrayOfByte1[0].length >= arrayOfByte1.length)
        break label141;
      j = 1;
      int k = i ^ j;
      m = 0;
      if (k != 0)
      {
        arrayOfByte1 = rotateArray(arrayOfByte1);
        m = 1;
      }
      n = paramInt1 / arrayOfByte1[0].length;
      i1 = paramInt2 / arrayOfByte1.length;
      if (n >= i1)
        break label147;
    }
    label141: label147: for (int i2 = n; ; i2 = i1)
    {
      if (i2 <= 1)
        break label154;
      byte[][] arrayOfByte2 = paramPDF417.getBarcodeMatrix().getScaledMatrix(i2 * 2, 2 * (i2 * 4));
      if (m != 0)
        arrayOfByte2 = rotateArray(arrayOfByte2);
      return bitMatrixFrombitArray(arrayOfByte2);
      i = 0;
      break;
      j = 0;
      break label40;
    }
    label154: return bitMatrixFrombitArray(arrayOfByte1);
  }

  private static BitMatrix bitMatrixFrombitArray(byte[][] paramArrayOfByte)
  {
    BitMatrix localBitMatrix = new BitMatrix(60 + paramArrayOfByte.length, 60 + paramArrayOfByte[0].length);
    localBitMatrix.clear();
    for (int i = 0; i < paramArrayOfByte.length; i++)
      for (int j = 0; j < paramArrayOfByte[0].length; j++)
        if (paramArrayOfByte[i][j] == 1)
          localBitMatrix.set(i + 30, j + 30);
    return localBitMatrix;
  }

  private static PDF417 initializeEncoder(BarcodeFormat paramBarcodeFormat, boolean paramBoolean)
  {
    if (paramBarcodeFormat != BarcodeFormat.PDF_417)
      throw new IllegalArgumentException("Can only encode PDF_417, but got " + paramBarcodeFormat);
    PDF417 localPDF417 = new PDF417();
    localPDF417.setCompact(paramBoolean);
    return localPDF417;
  }

  private static byte[][] rotateArray(byte[][] paramArrayOfByte)
  {
    int[] arrayOfInt = { paramArrayOfByte[0].length, paramArrayOfByte.length };
    byte[][] arrayOfByte = (byte[][])Array.newInstance(Byte.TYPE, arrayOfInt);
    for (int i = 0; i < paramArrayOfByte.length; i++)
    {
      int j = -1 + (paramArrayOfByte.length - i);
      for (int k = 0; k < paramArrayOfByte[0].length; k++)
        arrayOfByte[k][j] = paramArrayOfByte[i][k];
    }
    return arrayOfByte;
  }

  public BitMatrix encode(String paramString, BarcodeFormat paramBarcodeFormat, int paramInt1, int paramInt2)
    throws WriterException
  {
    return bitMatrixFromEncoder(initializeEncoder(paramBarcodeFormat, false), paramString, paramInt1, paramInt2);
  }

  public BitMatrix encode(String paramString, BarcodeFormat paramBarcodeFormat, int paramInt1, int paramInt2, Map<EncodeHintType, ?> paramMap)
    throws WriterException
  {
    return encode(paramString, paramBarcodeFormat, paramInt1, paramInt2);
  }

  public BitMatrix encode(String paramString, BarcodeFormat paramBarcodeFormat, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, Compaction paramCompaction)
    throws WriterException
  {
    PDF417 localPDF417 = initializeEncoder(paramBarcodeFormat, paramBoolean);
    localPDF417.setDimensions(paramInt4, paramInt3, paramInt6, paramInt5);
    localPDF417.setCompaction(paramCompaction);
    return bitMatrixFromEncoder(localPDF417, paramString, paramInt1, paramInt2);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.pdf417.encoder.PDF417Writer
 * JD-Core Version:    0.6.2
 */