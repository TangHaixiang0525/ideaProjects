package com.google.zxing.qrcode.encoder;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitArray;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonEncoder;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Mode;
import com.google.zxing.qrcode.decoder.Version;
import com.google.zxing.qrcode.decoder.Version.ECBlocks;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public final class Encoder
{
  private static final int[] ALPHANUMERIC_TABLE = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 36, -1, -1, -1, 37, 38, -1, -1, -1, -1, 39, 40, -1, 41, 42, 43, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 44, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, -1, -1, -1, -1, -1 };
  static final String DEFAULT_BYTE_MODE_ENCODING = "ISO-8859-1";

  static void append8BitBytes(String paramString1, BitArray paramBitArray, String paramString2)
    throws WriterException
  {
    try
    {
      byte[] arrayOfByte = paramString1.getBytes(paramString2);
      int i = arrayOfByte.length;
      for (int j = 0; j < i; j++)
        paramBitArray.appendBits(arrayOfByte[j], 8);
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new WriterException(localUnsupportedEncodingException.toString());
    }
  }

  static void appendAlphanumericBytes(CharSequence paramCharSequence, BitArray paramBitArray)
    throws WriterException
  {
    int i = paramCharSequence.length();
    int j = 0;
    while (j < i)
    {
      int k = getAlphanumericCode(paramCharSequence.charAt(j));
      if (k == -1)
        throw new WriterException();
      if (j + 1 < i)
      {
        int m = getAlphanumericCode(paramCharSequence.charAt(j + 1));
        if (m == -1)
          throw new WriterException();
        paramBitArray.appendBits(m + k * 45, 11);
        j += 2;
      }
      else
      {
        paramBitArray.appendBits(k, 6);
        j++;
      }
    }
  }

  static void appendBytes(String paramString1, Mode paramMode, BitArray paramBitArray, String paramString2)
    throws WriterException
  {
    switch (1.$SwitchMap$com$google$zxing$qrcode$decoder$Mode[paramMode.ordinal()])
    {
    default:
      throw new WriterException("Invalid mode: " + paramMode);
    case 1:
      appendNumericBytes(paramString1, paramBitArray);
      return;
    case 2:
      appendAlphanumericBytes(paramString1, paramBitArray);
      return;
    case 3:
      append8BitBytes(paramString1, paramBitArray, paramString2);
      return;
    case 4:
    }
    appendKanjiBytes(paramString1, paramBitArray);
  }

  private static void appendECI(CharacterSetECI paramCharacterSetECI, BitArray paramBitArray)
  {
    paramBitArray.appendBits(Mode.ECI.getBits(), 4);
    paramBitArray.appendBits(paramCharacterSetECI.getValue(), 8);
  }

  static void appendKanjiBytes(String paramString, BitArray paramBitArray)
    throws WriterException
  {
    while (true)
    {
      int j;
      int m;
      int n;
      try
      {
        byte[] arrayOfByte = paramString.getBytes("Shift_JIS");
        int i = arrayOfByte.length;
        j = 0;
        if (j >= i)
          break;
        int k = 0xFF & arrayOfByte[j];
        m = 0xFF & arrayOfByte[(j + 1)] | k << 8;
        n = -1;
        if ((m >= 33088) && (m <= 40956))
        {
          n = m - 33088;
          if (n != -1)
            break label126;
          throw new WriterException("Invalid byte sequence");
        }
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        throw new WriterException(localUnsupportedEncodingException.toString());
      }
      if ((m >= 57408) && (m <= 60351))
      {
        n = m - 49472;
        continue;
        label126: paramBitArray.appendBits(192 * (n >> 8) + (n & 0xFF), 13);
        j += 2;
      }
    }
  }

  static void appendLengthInfo(int paramInt1, int paramInt2, Mode paramMode, BitArray paramBitArray)
    throws WriterException
  {
    int i = paramMode.getCharacterCountBits(Version.getVersionForNumber(paramInt2));
    if (paramInt1 > -1 + (1 << i))
      throw new WriterException(paramInt1 + "is bigger than" + (-1 + (1 << i)));
    paramBitArray.appendBits(paramInt1, i);
  }

  static void appendModeInfo(Mode paramMode, BitArray paramBitArray)
  {
    paramBitArray.appendBits(paramMode.getBits(), 4);
  }

  static void appendNumericBytes(CharSequence paramCharSequence, BitArray paramBitArray)
  {
    int i = paramCharSequence.length();
    int j = 0;
    while (j < i)
    {
      int k = '￐' + paramCharSequence.charAt(j);
      if (j + 2 < i)
      {
        int m = '￐' + paramCharSequence.charAt(j + 1);
        paramBitArray.appendBits('￐' + paramCharSequence.charAt(j + 2) + (k * 100 + m * 10), 10);
        j += 3;
      }
      else if (j + 1 < i)
      {
        paramBitArray.appendBits('￐' + paramCharSequence.charAt(j + 1) + k * 10, 7);
        j += 2;
      }
      else
      {
        paramBitArray.appendBits(k, 4);
        j++;
      }
    }
  }

  private static int calculateMaskPenalty(ByteMatrix paramByteMatrix)
  {
    return 0 + MaskUtil.applyMaskPenaltyRule1(paramByteMatrix) + MaskUtil.applyMaskPenaltyRule2(paramByteMatrix) + MaskUtil.applyMaskPenaltyRule3(paramByteMatrix) + MaskUtil.applyMaskPenaltyRule4(paramByteMatrix);
  }

  private static int chooseMaskPattern(BitArray paramBitArray, ErrorCorrectionLevel paramErrorCorrectionLevel, int paramInt, ByteMatrix paramByteMatrix)
    throws WriterException
  {
    int i = 2147483647;
    int j = -1;
    for (int k = 0; k < 8; k++)
    {
      MatrixUtil.buildMatrix(paramBitArray, paramErrorCorrectionLevel, paramInt, k, paramByteMatrix);
      int m = calculateMaskPenalty(paramByteMatrix);
      if (m < i)
      {
        i = m;
        j = k;
      }
    }
    return j;
  }

  public static Mode chooseMode(String paramString)
  {
    return chooseMode(paramString, null);
  }

  private static Mode chooseMode(String paramString1, String paramString2)
  {
    if ("Shift_JIS".equals(paramString2))
    {
      if (isOnlyDoubleByteKanji(paramString1))
        return Mode.KANJI;
      return Mode.BYTE;
    }
    int i = 0;
    int j = 0;
    int k = 0;
    if (k < paramString1.length())
    {
      int m = paramString1.charAt(k);
      if ((m >= 48) && (m <= 57))
        i = 1;
      while (true)
      {
        k++;
        break;
        if (getAlphanumericCode(m) == -1)
          break label84;
        j = 1;
      }
      label84: return Mode.BYTE;
    }
    if (j != 0)
      return Mode.ALPHANUMERIC;
    if (i != 0)
      return Mode.NUMERIC;
    return Mode.BYTE;
  }

  public static void encode(String paramString, ErrorCorrectionLevel paramErrorCorrectionLevel, QRCode paramQRCode)
    throws WriterException
  {
    encode(paramString, paramErrorCorrectionLevel, null, paramQRCode);
  }

  public static void encode(String paramString, ErrorCorrectionLevel paramErrorCorrectionLevel, Map<EncodeHintType, ?> paramMap, QRCode paramQRCode)
    throws WriterException
  {
    String str;
    Mode localMode;
    BitArray localBitArray1;
    BitArray localBitArray2;
    if (paramMap == null)
    {
      str = null;
      if (str == null)
        str = "ISO-8859-1";
      localMode = chooseMode(paramString, str);
      localBitArray1 = new BitArray();
      appendBytes(paramString, localMode, localBitArray1, str);
      initQRCode(localBitArray1.getSize(), paramErrorCorrectionLevel, localMode, paramQRCode);
      localBitArray2 = new BitArray();
      if ((localMode == Mode.BYTE) && (!"ISO-8859-1".equals(str)))
      {
        CharacterSetECI localCharacterSetECI = CharacterSetECI.getCharacterSetECIByName(str);
        if (localCharacterSetECI != null)
          appendECI(localCharacterSetECI, localBitArray2);
      }
      appendModeInfo(localMode, localBitArray2);
      if (localMode != Mode.BYTE)
        break label290;
    }
    label290: for (int i = localBitArray1.getSizeInBytes(); ; i = paramString.length())
    {
      appendLengthInfo(i, paramQRCode.getVersion(), localMode, localBitArray2);
      localBitArray2.appendBitArray(localBitArray1);
      terminateBits(paramQRCode.getNumDataBytes(), localBitArray2);
      BitArray localBitArray3 = new BitArray();
      interleaveWithECBytes(localBitArray2, paramQRCode.getNumTotalBytes(), paramQRCode.getNumDataBytes(), paramQRCode.getNumRSBlocks(), localBitArray3);
      ByteMatrix localByteMatrix = new ByteMatrix(paramQRCode.getMatrixWidth(), paramQRCode.getMatrixWidth());
      paramQRCode.setMaskPattern(chooseMaskPattern(localBitArray3, paramErrorCorrectionLevel, paramQRCode.getVersion(), localByteMatrix));
      MatrixUtil.buildMatrix(localBitArray3, paramErrorCorrectionLevel, paramQRCode.getVersion(), paramQRCode.getMaskPattern(), localByteMatrix);
      paramQRCode.setMatrix(localByteMatrix);
      if (paramQRCode.isValid())
        return;
      throw new WriterException("Invalid QR code: " + paramQRCode.toString());
      str = (String)paramMap.get(EncodeHintType.CHARACTER_SET);
      break;
    }
  }

  static byte[] generateECBytes(byte[] paramArrayOfByte, int paramInt)
  {
    int i = paramArrayOfByte.length;
    int[] arrayOfInt = new int[i + paramInt];
    for (int j = 0; j < i; j++)
      arrayOfInt[j] = (0xFF & paramArrayOfByte[j]);
    new ReedSolomonEncoder(GenericGF.QR_CODE_FIELD_256).encode(arrayOfInt, paramInt);
    byte[] arrayOfByte = new byte[paramInt];
    for (int k = 0; k < paramInt; k++)
      arrayOfByte[k] = ((byte)arrayOfInt[(i + k)]);
    return arrayOfByte;
  }

  static int getAlphanumericCode(int paramInt)
  {
    if (paramInt < ALPHANUMERIC_TABLE.length)
      return ALPHANUMERIC_TABLE[paramInt];
    return -1;
  }

  static void getNumDataBytesAndNumECBytesForBlockID(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
    throws WriterException
  {
    if (paramInt4 >= paramInt3)
      throw new WriterException("Block ID too large");
    int i = paramInt1 % paramInt3;
    int j = paramInt3 - i;
    int k = paramInt1 / paramInt3;
    int m = k + 1;
    int n = paramInt2 / paramInt3;
    int i1 = n + 1;
    int i2 = k - n;
    int i3 = m - i1;
    if (i2 != i3)
      throw new WriterException("EC bytes mismatch");
    if (paramInt3 != j + i)
      throw new WriterException("RS blocks mismatch");
    if (paramInt1 != j * (n + i2) + i * (i1 + i3))
      throw new WriterException("Total bytes mismatch");
    if (paramInt4 < j)
    {
      paramArrayOfInt1[0] = n;
      paramArrayOfInt2[0] = i2;
      return;
    }
    paramArrayOfInt1[0] = i1;
    paramArrayOfInt2[0] = i3;
  }

  private static int getTotalInputBytes(int paramInt, Version paramVersion, Mode paramMode)
  {
    return (7 + (paramInt + (4 + paramMode.getCharacterCountBits(paramVersion)))) / 8;
  }

  private static void initQRCode(int paramInt, ErrorCorrectionLevel paramErrorCorrectionLevel, Mode paramMode, QRCode paramQRCode)
    throws WriterException
  {
    paramQRCode.setECLevel(paramErrorCorrectionLevel);
    paramQRCode.setMode(paramMode);
    for (int i = 1; i <= 40; i++)
    {
      Version localVersion = Version.getVersionForNumber(i);
      int j = localVersion.getTotalCodewords();
      Version.ECBlocks localECBlocks = localVersion.getECBlocksForLevel(paramErrorCorrectionLevel);
      int k = localECBlocks.getTotalECCodewords();
      int m = localECBlocks.getNumBlocks();
      int n = j - k;
      if (n >= getTotalInputBytes(paramInt, localVersion, paramMode))
      {
        paramQRCode.setVersion(i);
        paramQRCode.setNumTotalBytes(j);
        paramQRCode.setNumDataBytes(n);
        paramQRCode.setNumRSBlocks(m);
        paramQRCode.setNumECBytes(k);
        paramQRCode.setMatrixWidth(localVersion.getDimensionForVersion());
        return;
      }
    }
    throw new WriterException("Cannot find proper rs block info (input data too big?)");
  }

  static void interleaveWithECBytes(BitArray paramBitArray1, int paramInt1, int paramInt2, int paramInt3, BitArray paramBitArray2)
    throws WriterException
  {
    if (paramBitArray1.getSizeInBytes() != paramInt2)
      throw new WriterException("Number of bits and data bytes does not match");
    int i = 0;
    int j = 0;
    int k = 0;
    ArrayList localArrayList = new ArrayList(paramInt3);
    for (int m = 0; m < paramInt3; m++)
    {
      int[] arrayOfInt1 = new int[1];
      int[] arrayOfInt2 = new int[1];
      getNumDataBytesAndNumECBytesForBlockID(paramInt1, paramInt2, paramInt3, m, arrayOfInt1, arrayOfInt2);
      int i2 = arrayOfInt1[0];
      byte[] arrayOfByte3 = new byte[i2];
      paramBitArray1.toBytes(i * 8, arrayOfByte3, 0, i2);
      byte[] arrayOfByte4 = generateECBytes(arrayOfByte3, arrayOfInt2[0]);
      localArrayList.add(new BlockPair(arrayOfByte3, arrayOfByte4));
      j = Math.max(j, i2);
      k = Math.max(k, arrayOfByte4.length);
      i += arrayOfInt1[0];
    }
    if (paramInt2 != i)
      throw new WriterException("Data bytes does not match offset");
    for (int n = 0; n < j; n++)
    {
      Iterator localIterator2 = localArrayList.iterator();
      while (localIterator2.hasNext())
      {
        byte[] arrayOfByte2 = ((BlockPair)localIterator2.next()).getDataBytes();
        if (n < arrayOfByte2.length)
          paramBitArray2.appendBits(arrayOfByte2[n], 8);
      }
    }
    for (int i1 = 0; i1 < k; i1++)
    {
      Iterator localIterator1 = localArrayList.iterator();
      while (localIterator1.hasNext())
      {
        byte[] arrayOfByte1 = ((BlockPair)localIterator1.next()).getErrorCorrectionBytes();
        if (i1 < arrayOfByte1.length)
          paramBitArray2.appendBits(arrayOfByte1[i1], 8);
      }
    }
    if (paramInt1 != paramBitArray2.getSizeInBytes())
      throw new WriterException("Interleaving error: " + paramInt1 + " and " + paramBitArray2.getSizeInBytes() + " differ.");
  }

  private static boolean isOnlyDoubleByteKanji(String paramString)
  {
    byte[] arrayOfByte;
    int i;
    try
    {
      arrayOfByte = paramString.getBytes("Shift_JIS");
      i = arrayOfByte.length;
      if (i % 2 != 0)
        return false;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      return false;
    }
    for (int j = 0; ; j += 2)
    {
      if (j >= i)
        break label78;
      int k = 0xFF & arrayOfByte[j];
      if (((k < 129) || (k > 159)) && ((k < 224) || (k > 235)))
        break;
    }
    label78: return true;
  }

  static void terminateBits(int paramInt, BitArray paramBitArray)
    throws WriterException
  {
    int i = paramInt << 3;
    if (paramBitArray.getSize() > i)
      throw new WriterException("data bits cannot fit in the QR Code" + paramBitArray.getSize() + " > " + i);
    for (int j = 0; (j < 4) && (paramBitArray.getSize() < i); j++)
      paramBitArray.appendBit(false);
    int k = 0x7 & paramBitArray.getSize();
    if (k > 0)
      for (int i2 = k; i2 < 8; i2++)
        paramBitArray.appendBit(false);
    int m = paramInt - paramBitArray.getSizeInBytes();
    int n = 0;
    if (n < m)
    {
      if ((n & 0x1) == 0);
      for (int i1 = 236; ; i1 = 17)
      {
        paramBitArray.appendBits(i1, 8);
        n++;
        break;
      }
    }
    if (paramBitArray.getSize() != i)
      throw new WriterException("Bits size does not equal capacity");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.qrcode.encoder.Encoder
 * JD-Core Version:    0.6.2
 */