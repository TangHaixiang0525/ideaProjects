package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.EnumMap;
import java.util.Map;

final class UPCEANExtensionSupport
{
  private static final int[] CHECK_DIGIT_ENCODINGS = { 24, 20, 18, 17, 12, 6, 3, 10, 9, 5 };
  private static final int[] EXTENSION_START_PATTERN = { 1, 1, 2 };
  private final int[] decodeMiddleCounters = new int[4];
  private final StringBuilder decodeRowStringBuffer = new StringBuilder();

  private static int determineCheckDigit(int paramInt)
    throws NotFoundException
  {
    for (int i = 0; i < 10; i++)
      if (paramInt == CHECK_DIGIT_ENCODINGS[i])
        return i;
    throw NotFoundException.getNotFoundInstance();
  }

  private static int extensionChecksum(CharSequence paramCharSequence)
  {
    int i = paramCharSequence.length();
    int j = 0;
    for (int k = i - 2; k >= 0; k -= 2)
      j += '￐' + paramCharSequence.charAt(k);
    int m = j * 3;
    for (int n = i - 1; n >= 0; n -= 2)
      m += '￐' + paramCharSequence.charAt(n);
    return m * 3 % 10;
  }

  private static Integer parseExtension2String(String paramString)
  {
    return Integer.valueOf(paramString);
  }

  private static String parseExtension5String(String paramString)
  {
    String str1;
    String str2;
    int j;
    switch (paramString.charAt(0))
    {
    default:
      str1 = "";
      int i = Integer.parseInt(paramString.substring(1));
      str2 = String.valueOf(i / 100);
      j = i % 100;
      if (j >= 10)
        break;
    case '0':
    case '5':
    case '9':
    }
    for (String str3 = "0" + j; ; str3 = String.valueOf(j))
    {
      return str1 + str2 + '.' + str3;
      str1 = "£";
      break;
      str1 = "$";
      break;
      if ("90000".equals(paramString))
        return null;
      if ("99991".equals(paramString))
        return "0.00";
      if ("99990".equals(paramString))
        return "Used";
      str1 = "";
      break;
    }
  }

  private static Map<ResultMetadataType, Object> parseExtensionString(String paramString)
  {
    ResultMetadataType localResultMetadataType;
    switch (paramString.length())
    {
    case 3:
    case 4:
    default:
      return null;
    case 2:
      localResultMetadataType = ResultMetadataType.ISSUE_NUMBER;
    case 5:
    }
    for (Object localObject = parseExtension2String(paramString); localObject != null; localObject = parseExtension5String(paramString))
    {
      EnumMap localEnumMap = new EnumMap(ResultMetadataType.class);
      localEnumMap.put(localResultMetadataType, localObject);
      return localEnumMap;
      localResultMetadataType = ResultMetadataType.SUGGESTED_PRICE;
    }
  }

  int decodeMiddle(BitArray paramBitArray, int[] paramArrayOfInt, StringBuilder paramStringBuilder)
    throws NotFoundException
  {
    int[] arrayOfInt = this.decodeMiddleCounters;
    arrayOfInt[0] = 0;
    arrayOfInt[1] = 0;
    arrayOfInt[2] = 0;
    arrayOfInt[3] = 0;
    int i = paramBitArray.getSize();
    int j = paramArrayOfInt[1];
    int k = 0;
    for (int m = 0; (m < 5) && (j < i); m++)
    {
      int i1 = UPCEANReader.decodeDigit(paramBitArray, arrayOfInt, j, UPCEANReader.L_AND_G_PATTERNS);
      paramStringBuilder.append((char)(48 + i1 % 10));
      int i2 = arrayOfInt.length;
      for (int i3 = 0; i3 < i2; i3++)
        j += arrayOfInt[i3];
      if (i1 >= 10)
        k |= 1 << 4 - m;
      if (m != 4)
        j = paramBitArray.getNextUnset(paramBitArray.getNextSet(j));
    }
    if (paramStringBuilder.length() != 5)
      throw NotFoundException.getNotFoundInstance();
    int n = determineCheckDigit(k);
    if (extensionChecksum(paramStringBuilder.toString()) != n)
      throw NotFoundException.getNotFoundInstance();
    return j;
  }

  Result decodeRow(int paramInt1, BitArray paramBitArray, int paramInt2)
    throws NotFoundException
  {
    int[] arrayOfInt = UPCEANReader.findGuardPattern(paramBitArray, paramInt2, false, EXTENSION_START_PATTERN);
    StringBuilder localStringBuilder = this.decodeRowStringBuffer;
    localStringBuilder.setLength(0);
    int i = decodeMiddle(paramBitArray, arrayOfInt, localStringBuilder);
    String str = localStringBuilder.toString();
    Map localMap = parseExtensionString(str);
    ResultPoint[] arrayOfResultPoint = new ResultPoint[2];
    arrayOfResultPoint[0] = new ResultPoint((arrayOfInt[0] + arrayOfInt[1]) / 2.0F, paramInt1);
    arrayOfResultPoint[1] = new ResultPoint(i, paramInt1);
    Result localResult = new Result(str, null, arrayOfResultPoint, BarcodeFormat.UPC_EAN_EXTENSION);
    if (localMap != null)
      localResult.putAllMetadata(localMap);
    return localResult;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.UPCEANExtensionSupport
 * JD-Core Version:    0.6.2
 */