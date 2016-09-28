package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Map;

public final class CodaBarReader extends OneDReader
{
  static final char[] ALPHABET = "0123456789-$:/.+ABCDTN".toCharArray();
  private static final String ALPHABET_STRING = "0123456789-$:/.+ABCDTN";
  static final int[] CHARACTER_ENCODINGS = { 3, 6, 9, 96, 18, 66, 33, 36, 48, 72, 12, 24, 69, 81, 84, 21, 26, 41, 11, 14, 26, 41 };
  private static final char[] STARTEND_ENCODING = { 69, 42, 65, 66, 67, 68, 84, 78 };
  private static final int minCharacterLength = 6;

  static boolean arrayContains(char[] paramArrayOfChar, char paramChar)
  {
    if (paramArrayOfChar != null)
    {
      int i = paramArrayOfChar.length;
      for (int j = 0; j < i; j++)
        if (paramArrayOfChar[j] == paramChar)
          return true;
    }
    return false;
  }

  private static int[] findAsteriskPattern(BitArray paramBitArray)
    throws NotFoundException
  {
    int i = paramBitArray.getSize();
    int j = paramBitArray.getNextSet(0);
    int k = 0;
    int[] arrayOfInt1 = new int[7];
    int m = j;
    int n = 0;
    int i1 = arrayOfInt1.length;
    int i2 = j;
    while (i2 < i)
      if ((n ^ paramBitArray.get(i2)) != 0)
      {
        arrayOfInt1[k] = (1 + arrayOfInt1[k]);
        i2++;
      }
      else
      {
        if (k == i1 - 1)
          try
          {
            if ((arrayContains(STARTEND_ENCODING, toNarrowWidePattern(arrayOfInt1))) && (paramBitArray.isRange(Math.max(0, m - (i2 - m) / 2), m, false)))
            {
              int[] arrayOfInt2 = { m, i2 };
              return arrayOfInt2;
            }
          }
          catch (IllegalArgumentException localIllegalArgumentException)
          {
            m += arrayOfInt1[0] + arrayOfInt1[1];
            System.arraycopy(arrayOfInt1, 2, arrayOfInt1, 0, i1 - 2);
            arrayOfInt1[(i1 - 2)] = 0;
            arrayOfInt1[(i1 - 1)] = 0;
            k--;
          }
        while (true)
        {
          arrayOfInt1[k] = 1;
          n ^= 1;
          break;
          k++;
        }
      }
    throw NotFoundException.getNotFoundInstance();
  }

  private static char toNarrowWidePattern(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt.length;
    int j = 0;
    int k = 2147483647;
    for (int m = 0; m < i; m++)
    {
      if (paramArrayOfInt[m] < k)
        k = paramArrayOfInt[m];
      if (paramArrayOfInt[m] > j)
        j = paramArrayOfInt[m];
    }
    do
    {
      int n = 0;
      int i1 = 0;
      for (int i2 = 0; i2 < i; i2++)
        if (paramArrayOfInt[i2] > j)
        {
          i1 |= 1 << i - 1 - i2;
          n++;
        }
      if ((n == 2) || (n == 3))
        for (int i3 = 0; i3 < CHARACTER_ENCODINGS.length; i3++)
          if (CHARACTER_ENCODINGS[i3] == i1)
            return ALPHABET[i3];
      j--;
    }
    while (j > k);
    return '!';
  }

  public Result decodeRow(int paramInt, BitArray paramBitArray, Map<DecodeHintType, ?> paramMap)
    throws NotFoundException
  {
    int[] arrayOfInt1 = findAsteriskPattern(paramBitArray);
    arrayOfInt1[1] = 0;
    int i = paramBitArray.getNextSet(arrayOfInt1[1]);
    int j = paramBitArray.getSize();
    StringBuilder localStringBuilder = new StringBuilder();
    int[] arrayOfInt2 = new int[7];
    int m;
    do
    {
      for (int k = 0; k < arrayOfInt2.length; k++)
        arrayOfInt2[k] = 0;
      recordPattern(paramBitArray, i, arrayOfInt2);
      char c1 = toNarrowWidePattern(arrayOfInt2);
      if (c1 == '!')
        throw NotFoundException.getNotFoundInstance();
      localStringBuilder.append(c1);
      m = i;
      int n = arrayOfInt2.length;
      for (int i1 = 0; i1 < n; i1++)
        i += arrayOfInt2[i1];
      i = paramBitArray.getNextSet(i);
    }
    while (i < j);
    int i2 = 0;
    int i3 = arrayOfInt2.length;
    for (int i4 = 0; i4 < i3; i4++)
      i2 += arrayOfInt2[i4];
    int i5 = i - m - i2;
    if ((i != j) && (i5 / 2 < i2))
      throw NotFoundException.getNotFoundInstance();
    if (localStringBuilder.length() < 2)
      throw NotFoundException.getNotFoundInstance();
    char c2 = localStringBuilder.charAt(0);
    if (!arrayContains(STARTEND_ENCODING, c2))
      throw NotFoundException.getNotFoundInstance();
    for (int i6 = 1; ; i6++)
      if (i6 < localStringBuilder.length())
      {
        if ((localStringBuilder.charAt(i6) == c2) && (i6 + 1 != localStringBuilder.length()))
          localStringBuilder.delete(i6 + 1, -1 + localStringBuilder.length());
      }
      else
      {
        if (localStringBuilder.length() > 6)
          break;
        throw NotFoundException.getNotFoundInstance();
      }
    localStringBuilder.deleteCharAt(-1 + localStringBuilder.length());
    localStringBuilder.deleteCharAt(0);
    float f1 = (arrayOfInt1[1] + arrayOfInt1[0]) / 2.0F;
    float f2 = (i + m) / 2.0F;
    String str = localStringBuilder.toString();
    ResultPoint[] arrayOfResultPoint = new ResultPoint[2];
    ResultPoint localResultPoint1 = new ResultPoint(f1, paramInt);
    arrayOfResultPoint[0] = localResultPoint1;
    ResultPoint localResultPoint2 = new ResultPoint(f2, paramInt);
    arrayOfResultPoint[1] = localResultPoint2;
    return new Result(str, null, arrayOfResultPoint, BarcodeFormat.CODABAR);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.CodaBarReader
 * JD-Core Version:    0.6.2
 */