package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Map;

public final class Code93Reader extends OneDReader
{
  private static final char[] ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".toCharArray();
  private static final String ALPHABET_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*";
  private static final int ASTERISK_ENCODING = CHARACTER_ENCODINGS[47];
  private static final int[] CHARACTER_ENCODINGS = { 276, 328, 324, 322, 296, 292, 290, 336, 274, 266, 424, 420, 418, 404, 402, 394, 360, 356, 354, 308, 282, 344, 332, 326, 300, 278, 436, 434, 428, 422, 406, 410, 364, 358, 310, 314, 302, 468, 466, 458, 366, 374, 430, 294, 474, 470, 306, 350 };

  private static void checkChecksums(CharSequence paramCharSequence)
    throws ChecksumException
  {
    int i = paramCharSequence.length();
    checkOneChecksum(paramCharSequence, i - 2, 20);
    checkOneChecksum(paramCharSequence, i - 1, 15);
  }

  private static void checkOneChecksum(CharSequence paramCharSequence, int paramInt1, int paramInt2)
    throws ChecksumException
  {
    int i = 1;
    int j = 0;
    for (int k = paramInt1 - 1; k >= 0; k--)
    {
      j += i * "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".indexOf(paramCharSequence.charAt(k));
      i++;
      if (i > paramInt2)
        i = 1;
    }
    if (paramCharSequence.charAt(paramInt1) != ALPHABET[(j % 47)])
      throw ChecksumException.getChecksumInstance();
  }

  private static String decodeExtended(CharSequence paramCharSequence)
    throws FormatException
  {
    int i = paramCharSequence.length();
    StringBuilder localStringBuilder = new StringBuilder(i);
    int j = 0;
    if (j < i)
    {
      char c1 = paramCharSequence.charAt(j);
      int k;
      char c2;
      if ((c1 >= 'a') && (c1 <= 'd'))
      {
        k = paramCharSequence.charAt(j + 1);
        c2 = '\000';
        switch (c1)
        {
        default:
          label92: localStringBuilder.append(c2);
          j++;
        case 'd':
        case 'a':
        case 'b':
        case 'c':
        }
      }
      while (true)
      {
        j++;
        break;
        if ((k >= 65) && (k <= 90))
        {
          c2 = (char)(k + 32);
          break label92;
        }
        throw FormatException.getFormatInstance();
        if ((k >= 65) && (k <= 90))
        {
          c2 = (char)(k - 64);
          break label92;
        }
        throw FormatException.getFormatInstance();
        if ((k >= 65) && (k <= 69))
        {
          c2 = (char)(k - 38);
          break label92;
        }
        if ((k >= 70) && (k <= 87))
        {
          c2 = (char)(k - 11);
          break label92;
        }
        throw FormatException.getFormatInstance();
        if ((k >= 65) && (k <= 79))
        {
          c2 = (char)(k - 32);
          break label92;
        }
        if (k == 90)
        {
          c2 = ':';
          break label92;
        }
        throw FormatException.getFormatInstance();
        localStringBuilder.append(c1);
      }
    }
    return localStringBuilder.toString();
  }

  private static int[] findAsteriskPattern(BitArray paramBitArray)
    throws NotFoundException
  {
    int i = paramBitArray.getSize();
    int j = paramBitArray.getNextSet(0);
    int k = 0;
    int[] arrayOfInt = new int[6];
    int m = j;
    int n = 0;
    int i1 = arrayOfInt.length;
    int i2 = j;
    while (i2 < i)
      if ((n ^ paramBitArray.get(i2)) != 0)
      {
        arrayOfInt[k] = (1 + arrayOfInt[k]);
        i2++;
      }
      else
      {
        if (k == i1 - 1)
        {
          if (toPattern(arrayOfInt) == ASTERISK_ENCODING)
            return new int[] { m, i2 };
          m += arrayOfInt[0] + arrayOfInt[1];
          System.arraycopy(arrayOfInt, 2, arrayOfInt, 0, i1 - 2);
          arrayOfInt[(i1 - 2)] = 0;
          arrayOfInt[(i1 - 1)] = 0;
          k--;
          label146: arrayOfInt[k] = 1;
          if (n != 0)
            break label168;
        }
        label168: for (n = 1; ; n = 0)
        {
          break;
          k++;
          break label146;
        }
      }
    throw NotFoundException.getNotFoundInstance();
  }

  private static char patternToChar(int paramInt)
    throws NotFoundException
  {
    for (int i = 0; i < CHARACTER_ENCODINGS.length; i++)
      if (CHARACTER_ENCODINGS[i] == paramInt)
        return ALPHABET[i];
    throw NotFoundException.getNotFoundInstance();
  }

  private static int toPattern(int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt.length;
    int j = 0;
    for (int k = 0; k < i; k++)
      j += paramArrayOfInt[k];
    int m = 0;
    for (int n = 0; ; n++)
    {
      int i2;
      if (n < i)
      {
        int i1 = 9 * (paramArrayOfInt[n] << 8) / j;
        i2 = i1 >> 8;
        if ((i1 & 0xFF) > 127)
          i2++;
        if ((i2 < 1) || (i2 > 4))
          m = -1;
      }
      else
      {
        return m;
      }
      if ((n & 0x1) == 0)
        for (int i3 = 0; i3 < i2; i3++)
          m = 0x1 | m << 1;
      m <<= i2;
    }
  }

  public Result decodeRow(int paramInt, BitArray paramBitArray, Map<DecodeHintType, ?> paramMap)
    throws NotFoundException, ChecksumException, FormatException
  {
    int[] arrayOfInt1 = findAsteriskPattern(paramBitArray);
    int i = paramBitArray.getNextSet(arrayOfInt1[1]);
    int j = paramBitArray.getSize();
    StringBuilder localStringBuilder = new StringBuilder(20);
    int[] arrayOfInt2 = new int[6];
    char c;
    int m;
    do
    {
      recordPattern(paramBitArray, i, arrayOfInt2);
      int k = toPattern(arrayOfInt2);
      if (k < 0)
        throw NotFoundException.getNotFoundInstance();
      c = patternToChar(k);
      localStringBuilder.append(c);
      m = i;
      int n = arrayOfInt2.length;
      for (int i1 = 0; i1 < n; i1++)
        i += arrayOfInt2[i1];
      i = paramBitArray.getNextSet(i);
    }
    while (c != '*');
    localStringBuilder.deleteCharAt(-1 + localStringBuilder.length());
    if ((i == j) || (!paramBitArray.get(i)))
      throw NotFoundException.getNotFoundInstance();
    if (localStringBuilder.length() < 2)
      throw NotFoundException.getNotFoundInstance();
    checkChecksums(localStringBuilder);
    localStringBuilder.setLength(-2 + localStringBuilder.length());
    String str = decodeExtended(localStringBuilder);
    float f1 = (arrayOfInt1[1] + arrayOfInt1[0]) / 2.0F;
    float f2 = (i + m) / 2.0F;
    ResultPoint[] arrayOfResultPoint = new ResultPoint[2];
    ResultPoint localResultPoint1 = new ResultPoint(f1, paramInt);
    arrayOfResultPoint[0] = localResultPoint1;
    ResultPoint localResultPoint2 = new ResultPoint(f2, paramInt);
    arrayOfResultPoint[1] = localResultPoint2;
    Result localResult = new Result(str, null, arrayOfResultPoint, BarcodeFormat.CODE_93);
    return localResult;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.Code93Reader
 * JD-Core Version:    0.6.2
 */