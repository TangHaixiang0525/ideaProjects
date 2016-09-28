package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class Code128Reader extends OneDReader
{
  private static final int CODE_CODE_A = 101;
  private static final int CODE_CODE_B = 100;
  private static final int CODE_CODE_C = 99;
  private static final int CODE_FNC_1 = 102;
  private static final int CODE_FNC_2 = 97;
  private static final int CODE_FNC_3 = 96;
  private static final int CODE_FNC_4_A = 101;
  private static final int CODE_FNC_4_B = 100;
  static final int[][] CODE_PATTERNS = { { 2, 1, 2, 2, 2, 2 }, { 2, 2, 2, 1, 2, 2 }, { 2, 2, 2, 2, 2, 1 }, { 1, 2, 1, 2, 2, 3 }, { 1, 2, 1, 3, 2, 2 }, { 1, 3, 1, 2, 2, 2 }, { 1, 2, 2, 2, 1, 3 }, { 1, 2, 2, 3, 1, 2 }, { 1, 3, 2, 2, 1, 2 }, { 2, 2, 1, 2, 1, 3 }, { 2, 2, 1, 3, 1, 2 }, { 2, 3, 1, 2, 1, 2 }, { 1, 1, 2, 2, 3, 2 }, { 1, 2, 2, 1, 3, 2 }, { 1, 2, 2, 2, 3, 1 }, { 1, 1, 3, 2, 2, 2 }, { 1, 2, 3, 1, 2, 2 }, { 1, 2, 3, 2, 2, 1 }, { 2, 2, 3, 2, 1, 1 }, { 2, 2, 1, 1, 3, 2 }, { 2, 2, 1, 2, 3, 1 }, { 2, 1, 3, 2, 1, 2 }, { 2, 2, 3, 1, 1, 2 }, { 3, 1, 2, 1, 3, 1 }, { 3, 1, 1, 2, 2, 2 }, { 3, 2, 1, 1, 2, 2 }, { 3, 2, 1, 2, 2, 1 }, { 3, 1, 2, 2, 1, 2 }, { 3, 2, 2, 1, 1, 2 }, { 3, 2, 2, 2, 1, 1 }, { 2, 1, 2, 1, 2, 3 }, { 2, 1, 2, 3, 2, 1 }, { 2, 3, 2, 1, 2, 1 }, { 1, 1, 1, 3, 2, 3 }, { 1, 3, 1, 1, 2, 3 }, { 1, 3, 1, 3, 2, 1 }, { 1, 1, 2, 3, 1, 3 }, { 1, 3, 2, 1, 1, 3 }, { 1, 3, 2, 3, 1, 1 }, { 2, 1, 1, 3, 1, 3 }, { 2, 3, 1, 1, 1, 3 }, { 2, 3, 1, 3, 1, 1 }, { 1, 1, 2, 1, 3, 3 }, { 1, 1, 2, 3, 3, 1 }, { 1, 3, 2, 1, 3, 1 }, { 1, 1, 3, 1, 2, 3 }, { 1, 1, 3, 3, 2, 1 }, { 1, 3, 3, 1, 2, 1 }, { 3, 1, 3, 1, 2, 1 }, { 2, 1, 1, 3, 3, 1 }, { 2, 3, 1, 1, 3, 1 }, { 2, 1, 3, 1, 1, 3 }, { 2, 1, 3, 3, 1, 1 }, { 2, 1, 3, 1, 3, 1 }, { 3, 1, 1, 1, 2, 3 }, { 3, 1, 1, 3, 2, 1 }, { 3, 3, 1, 1, 2, 1 }, { 3, 1, 2, 1, 1, 3 }, { 3, 1, 2, 3, 1, 1 }, { 3, 3, 2, 1, 1, 1 }, { 3, 1, 4, 1, 1, 1 }, { 2, 2, 1, 4, 1, 1 }, { 4, 3, 1, 1, 1, 1 }, { 1, 1, 1, 2, 2, 4 }, { 1, 1, 1, 4, 2, 2 }, { 1, 2, 1, 1, 2, 4 }, { 1, 2, 1, 4, 2, 1 }, { 1, 4, 1, 1, 2, 2 }, { 1, 4, 1, 2, 2, 1 }, { 1, 1, 2, 2, 1, 4 }, { 1, 1, 2, 4, 1, 2 }, { 1, 2, 2, 1, 1, 4 }, { 1, 2, 2, 4, 1, 1 }, { 1, 4, 2, 1, 1, 2 }, { 1, 4, 2, 2, 1, 1 }, { 2, 4, 1, 2, 1, 1 }, { 2, 2, 1, 1, 1, 4 }, { 4, 1, 3, 1, 1, 1 }, { 2, 4, 1, 1, 1, 2 }, { 1, 3, 4, 1, 1, 1 }, { 1, 1, 1, 2, 4, 2 }, { 1, 2, 1, 1, 4, 2 }, { 1, 2, 1, 2, 4, 1 }, { 1, 1, 4, 2, 1, 2 }, { 1, 2, 4, 1, 1, 2 }, { 1, 2, 4, 2, 1, 1 }, { 4, 1, 1, 2, 1, 2 }, { 4, 2, 1, 1, 1, 2 }, { 4, 2, 1, 2, 1, 1 }, { 2, 1, 2, 1, 4, 1 }, { 2, 1, 4, 1, 2, 1 }, { 4, 1, 2, 1, 2, 1 }, { 1, 1, 1, 1, 4, 3 }, { 1, 1, 1, 3, 4, 1 }, { 1, 3, 1, 1, 4, 1 }, { 1, 1, 4, 1, 1, 3 }, { 1, 1, 4, 3, 1, 1 }, { 4, 1, 1, 1, 1, 3 }, { 4, 1, 1, 3, 1, 1 }, { 1, 1, 3, 1, 4, 1 }, { 1, 1, 4, 1, 3, 1 }, { 3, 1, 1, 1, 4, 1 }, { 4, 1, 1, 1, 3, 1 }, { 2, 1, 1, 4, 1, 2 }, { 2, 1, 1, 2, 1, 4 }, { 2, 1, 1, 2, 3, 2 }, { 2, 3, 3, 1, 1, 1, 2 } };
  private static final int CODE_SHIFT = 98;
  private static final int CODE_START_A = 103;
  private static final int CODE_START_B = 104;
  private static final int CODE_START_C = 105;
  private static final int CODE_STOP = 106;
  private static final int MAX_AVG_VARIANCE = 64;
  private static final int MAX_INDIVIDUAL_VARIANCE = 179;

  private static int decodeCode(BitArray paramBitArray, int[] paramArrayOfInt, int paramInt)
    throws NotFoundException
  {
    recordPattern(paramBitArray, paramInt, paramArrayOfInt);
    int i = 64;
    int j = -1;
    for (int k = 0; k < CODE_PATTERNS.length; k++)
    {
      int m = patternMatchVariance(paramArrayOfInt, CODE_PATTERNS[k], 179);
      if (m < i)
      {
        i = m;
        j = k;
      }
    }
    if (j >= 0)
      return j;
    throw NotFoundException.getNotFoundInstance();
  }

  private static int[] findStartPattern(BitArray paramBitArray)
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
          int i3 = 64;
          int i4 = -1;
          for (int i5 = 103; i5 <= 105; i5++)
          {
            int i6 = patternMatchVariance(arrayOfInt, CODE_PATTERNS[i5], 179);
            if (i6 < i3)
            {
              i3 = i6;
              i4 = i5;
            }
          }
          if ((i4 >= 0) && (paramBitArray.isRange(Math.max(0, m - (i2 - m) / 2), m, false)))
            return new int[] { m, i2, i4 };
          m += arrayOfInt[0] + arrayOfInt[1];
          System.arraycopy(arrayOfInt, 2, arrayOfInt, 0, i1 - 2);
          arrayOfInt[(i1 - 2)] = 0;
          arrayOfInt[(i1 - 1)] = 0;
          k--;
          label224: arrayOfInt[k] = 1;
          if (n != 0)
            break label246;
        }
        label246: for (n = 1; ; n = 0)
        {
          break;
          k++;
          break label224;
        }
      }
    throw NotFoundException.getNotFoundInstance();
  }

  public Result decodeRow(int paramInt, BitArray paramBitArray, Map<DecodeHintType, ?> paramMap)
    throws NotFoundException, FormatException, ChecksumException
  {
    int[] arrayOfInt1 = findStartPattern(paramBitArray);
    int i = arrayOfInt1[2];
    int j;
    int k;
    int m;
    StringBuilder localStringBuilder;
    ArrayList localArrayList;
    int n;
    int i1;
    int[] arrayOfInt2;
    int i2;
    int i3;
    int i4;
    int i5;
    int i6;
    switch (i)
    {
    default:
      throw FormatException.getFormatInstance();
    case 103:
      j = 101;
      k = 0;
      m = 0;
      localStringBuilder = new StringBuilder(20);
      localArrayList = new ArrayList(20);
      n = arrayOfInt1[0];
      i1 = arrayOfInt1[1];
      arrayOfInt2 = new int[6];
      i2 = 0;
      i3 = 0;
      i4 = i;
      i5 = 0;
      i6 = 1;
    case 104:
    case 105:
    }
    while (true)
      if (k == 0)
      {
        int i11 = m;
        i2 = i3;
        i3 = decodeCode(paramBitArray, arrayOfInt2, i1);
        localArrayList.add(Byte.valueOf((byte)i3));
        if (i3 != 106)
          i6 = 1;
        if (i3 != 106)
        {
          i5++;
          i4 += i5 * i3;
        }
        n = i1;
        int i12 = arrayOfInt2.length;
        int i13 = 0;
        while (true)
          if (i13 < i12)
          {
            i1 += arrayOfInt2[i13];
            i13++;
            continue;
            j = 100;
            break;
            j = 99;
            break;
          }
        switch (i3)
        {
        default:
          m = 0;
          switch (j)
          {
          default:
            if (i11 != 0)
              if (j != 101);
            break;
          case 101:
          case 100:
          case 99:
          }
        case 103:
        case 104:
        case 105:
          label288: for (j = 100; ; j = 101)
          {
            break;
            throw FormatException.getFormatInstance();
            if (i3 < 64)
            {
              localStringBuilder.append((char)(i3 + 32));
              m = 0;
              break label288;
            }
            if (i3 < 96)
            {
              localStringBuilder.append((char)(i3 - 64));
              m = 0;
              break label288;
            }
            if (i3 != 106)
              i6 = 0;
            m = 0;
            switch (i3)
            {
            case 96:
            case 97:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            default:
              m = 0;
              break;
            case 98:
              m = 1;
              j = 100;
              break;
            case 100:
              j = 100;
              m = 0;
              break;
            case 99:
              j = 99;
              m = 0;
              break;
            case 106:
              k = 1;
              m = 0;
              break;
              if (i3 < 96)
              {
                localStringBuilder.append((char)(i3 + 32));
                m = 0;
                break;
              }
              if (i3 != 106)
                i6 = 0;
              m = 0;
              switch (i3)
              {
              case 96:
              case 97:
              case 100:
              case 102:
              case 103:
              case 104:
              case 105:
              default:
                m = 0;
                break;
              case 98:
                m = 1;
                j = 101;
                break;
              case 101:
                j = 101;
                m = 0;
                break;
              case 99:
                j = 99;
                m = 0;
                break;
              case 106:
                k = 1;
                m = 0;
                break;
                if (i3 < 100)
                {
                  if (i3 < 10)
                    localStringBuilder.append('0');
                  localStringBuilder.append(i3);
                  m = 0;
                  break;
                }
                if (i3 != 106)
                  i6 = 0;
                m = 0;
                switch (i3)
                {
                case 102:
                case 103:
                case 104:
                case 105:
                default:
                  m = 0;
                  break;
                case 100:
                  j = 100;
                  m = 0;
                  break;
                case 101:
                  j = 101;
                  m = 0;
                  break;
                case 106:
                  k = 1;
                  m = 0;
                  break;
                }
                break;
              }
              break;
            }
          }
        }
      }
    int i7 = paramBitArray.getNextUnset(i1);
    if (!paramBitArray.isRange(i7, Math.min(paramBitArray.getSize(), i7 + (i7 - n) / 2), false))
      throw NotFoundException.getNotFoundInstance();
    if ((i4 - i5 * i2) % 103 != i2)
      throw ChecksumException.getChecksumInstance();
    int i8 = localStringBuilder.length();
    if (i8 == 0)
      throw NotFoundException.getNotFoundInstance();
    if ((i8 > 0) && (i6 != 0))
    {
      if (j != 99)
        break label941;
      localStringBuilder.delete(i8 - 2, i8);
    }
    float f1;
    float f2;
    byte[] arrayOfByte;
    while (true)
    {
      f1 = (arrayOfInt1[1] + arrayOfInt1[0]) / 2.0F;
      f2 = (i7 + n) / 2.0F;
      int i9 = localArrayList.size();
      arrayOfByte = new byte[i9];
      for (int i10 = 0; i10 < i9; i10++)
        arrayOfByte[i10] = ((Byte)localArrayList.get(i10)).byteValue();
      label941: localStringBuilder.delete(i8 - 1, i8);
    }
    String str = localStringBuilder.toString();
    ResultPoint[] arrayOfResultPoint = new ResultPoint[2];
    ResultPoint localResultPoint1 = new ResultPoint(f1, paramInt);
    arrayOfResultPoint[0] = localResultPoint1;
    ResultPoint localResultPoint2 = new ResultPoint(f2, paramInt);
    arrayOfResultPoint[1] = localResultPoint2;
    Result localResult = new Result(str, arrayOfByte, arrayOfResultPoint, BarcodeFormat.CODE_128);
    return localResult;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.Code128Reader
 * JD-Core Version:    0.6.2
 */