package com.google.zxing.qrcode.decoder;

import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitSource;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.StringUtils;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

final class DecodedBitStreamParser
{
  private static final char[] ALPHANUMERIC_CHARS = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 32, 36, 37, 42, 43, 45, 46, 47, 58 };
  private static final int GB2312_SUBSET = 1;

  static DecoderResult decode(byte[] paramArrayOfByte, Version paramVersion, ErrorCorrectionLevel paramErrorCorrectionLevel, Map<DecodeHintType, ?> paramMap)
    throws FormatException
  {
    BitSource localBitSource = new BitSource(paramArrayOfByte);
    StringBuilder localStringBuilder = new StringBuilder(50);
    CharacterSetECI localCharacterSetECI = null;
    boolean bool = false;
    ArrayList localArrayList = new ArrayList(1);
    Object localObject;
    label78: String str1;
    if (localBitSource.available() < 4)
    {
      localObject = Mode.TERMINATOR;
      if (localObject != Mode.TERMINATOR)
      {
        if ((localObject != Mode.FNC1_FIRST_POSITION) && (localObject != Mode.FNC1_SECOND_POSITION))
          break label152;
        bool = true;
      }
      if (localObject != Mode.TERMINATOR)
        break label343;
      str1 = localStringBuilder.toString();
      if (localArrayList.isEmpty())
        localArrayList = null;
      if (paramErrorCorrectionLevel != null)
        break label349;
    }
    label152: label343: label349: for (String str2 = null; ; str2 = paramErrorCorrectionLevel.toString())
    {
      while (true)
      {
        return new DecoderResult(paramArrayOfByte, str1, localArrayList, str2);
        try
        {
          Mode localMode = Mode.forBits(localBitSource.readBits(4));
          localObject = localMode;
        }
        catch (IllegalArgumentException localIllegalArgumentException)
        {
          throw FormatException.getFormatInstance();
        }
      }
      if (localObject == Mode.STRUCTURED_APPEND)
      {
        localBitSource.readBits(16);
        break label78;
      }
      if (localObject == Mode.ECI)
      {
        localCharacterSetECI = CharacterSetECI.getCharacterSetECIByValue(parseECIValue(localBitSource));
        if (localCharacterSetECI != null)
          break label78;
        throw FormatException.getFormatInstance();
      }
      if (localObject == Mode.HANZI)
      {
        int j = localBitSource.readBits(4);
        int k = localBitSource.readBits(((Mode)localObject).getCharacterCountBits(paramVersion));
        if (j != 1)
          break label78;
        decodeHanziSegment(localBitSource, localStringBuilder, k);
        break label78;
      }
      int i = localBitSource.readBits(((Mode)localObject).getCharacterCountBits(paramVersion));
      if (localObject == Mode.NUMERIC)
      {
        decodeNumericSegment(localBitSource, localStringBuilder, i);
        break label78;
      }
      if (localObject == Mode.ALPHANUMERIC)
      {
        decodeAlphanumericSegment(localBitSource, localStringBuilder, i, bool);
        break label78;
      }
      if (localObject == Mode.BYTE)
      {
        decodeByteSegment(localBitSource, localStringBuilder, i, localCharacterSetECI, localArrayList, paramMap);
        break label78;
      }
      if (localObject == Mode.KANJI)
      {
        decodeKanjiSegment(localBitSource, localStringBuilder, i);
        break label78;
        break;
      }
      throw FormatException.getFormatInstance();
    }
  }

  private static void decodeAlphanumericSegment(BitSource paramBitSource, StringBuilder paramStringBuilder, int paramInt, boolean paramBoolean)
    throws FormatException
  {
    int i = paramStringBuilder.length();
    while (paramInt > 1)
    {
      int k = paramBitSource.readBits(11);
      paramStringBuilder.append(toAlphaNumericChar(k / 45));
      paramStringBuilder.append(toAlphaNumericChar(k % 45));
      paramInt -= 2;
    }
    if (paramInt == 1)
      paramStringBuilder.append(toAlphaNumericChar(paramBitSource.readBits(6)));
    if (paramBoolean)
    {
      int j = i;
      if (j < paramStringBuilder.length())
      {
        if (paramStringBuilder.charAt(j) == '%')
        {
          if ((j >= -1 + paramStringBuilder.length()) || (paramStringBuilder.charAt(j + 1) != '%'))
            break label137;
          paramStringBuilder.deleteCharAt(j + 1);
        }
        while (true)
        {
          j++;
          break;
          label137: paramStringBuilder.setCharAt(j, '\035');
        }
      }
    }
  }

  private static void decodeByteSegment(BitSource paramBitSource, StringBuilder paramStringBuilder, int paramInt, CharacterSetECI paramCharacterSetECI, Collection<byte[]> paramCollection, Map<DecodeHintType, ?> paramMap)
    throws FormatException
  {
    if (paramInt << 3 > paramBitSource.available())
      throw FormatException.getFormatInstance();
    byte[] arrayOfByte = new byte[paramInt];
    for (int i = 0; i < paramInt; i++)
      arrayOfByte[i] = ((byte)paramBitSource.readBits(8));
    String str;
    if (paramCharacterSetECI == null)
      str = StringUtils.guessEncoding(arrayOfByte, paramMap);
    try
    {
      while (true)
      {
        paramStringBuilder.append(new String(arrayOfByte, str));
        paramCollection.add(arrayOfByte);
        return;
        str = paramCharacterSetECI.name();
      }
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
    }
    throw FormatException.getFormatInstance();
  }

  private static void decodeHanziSegment(BitSource paramBitSource, StringBuilder paramStringBuilder, int paramInt)
    throws FormatException
  {
    if (paramInt * 13 > paramBitSource.available())
      throw FormatException.getFormatInstance();
    byte[] arrayOfByte = new byte[paramInt * 2];
    int i = 0;
    if (paramInt > 0)
    {
      int j = paramBitSource.readBits(13);
      int k = j / 96 << 8 | j % 96;
      if (k < 959);
      for (int m = k + 41377; ; m = k + 42657)
      {
        arrayOfByte[i] = ((byte)(0xFF & m >> 8));
        arrayOfByte[(i + 1)] = ((byte)(m & 0xFF));
        i += 2;
        paramInt--;
        break;
      }
    }
    try
    {
      paramStringBuilder.append(new String(arrayOfByte, "GB2312"));
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
    }
    throw FormatException.getFormatInstance();
  }

  private static void decodeKanjiSegment(BitSource paramBitSource, StringBuilder paramStringBuilder, int paramInt)
    throws FormatException
  {
    if (paramInt * 13 > paramBitSource.available())
      throw FormatException.getFormatInstance();
    byte[] arrayOfByte = new byte[paramInt * 2];
    int i = 0;
    if (paramInt > 0)
    {
      int j = paramBitSource.readBits(13);
      int k = j / 192 << 8 | j % 192;
      if (k < 7936);
      for (int m = k + 33088; ; m = k + 49472)
      {
        arrayOfByte[i] = ((byte)(m >> 8));
        arrayOfByte[(i + 1)] = ((byte)m);
        i += 2;
        paramInt--;
        break;
      }
    }
    try
    {
      paramStringBuilder.append(new String(arrayOfByte, "SJIS"));
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
    }
    throw FormatException.getFormatInstance();
  }

  private static void decodeNumericSegment(BitSource paramBitSource, StringBuilder paramStringBuilder, int paramInt)
    throws FormatException
  {
    while (paramInt >= 3)
    {
      if (paramBitSource.available() < 10)
        throw FormatException.getFormatInstance();
      int k = paramBitSource.readBits(10);
      if (k >= 1000)
        throw FormatException.getFormatInstance();
      paramStringBuilder.append(toAlphaNumericChar(k / 100));
      paramStringBuilder.append(toAlphaNumericChar(k / 10 % 10));
      paramStringBuilder.append(toAlphaNumericChar(k % 10));
      paramInt -= 3;
    }
    if (paramInt == 2)
    {
      if (paramBitSource.available() < 7)
        throw FormatException.getFormatInstance();
      j = paramBitSource.readBits(7);
      if (j >= 100)
        throw FormatException.getFormatInstance();
      paramStringBuilder.append(toAlphaNumericChar(j / 10));
      paramStringBuilder.append(toAlphaNumericChar(j % 10));
    }
    while (paramInt != 1)
    {
      int j;
      return;
    }
    if (paramBitSource.available() < 4)
      throw FormatException.getFormatInstance();
    int i = paramBitSource.readBits(4);
    if (i >= 10)
      throw FormatException.getFormatInstance();
    paramStringBuilder.append(toAlphaNumericChar(i));
  }

  private static int parseECIValue(BitSource paramBitSource)
  {
    int i = paramBitSource.readBits(8);
    if ((i & 0x80) == 0)
      return i & 0x7F;
    if ((i & 0xC0) == 128)
      return paramBitSource.readBits(8) | (i & 0x3F) << 8;
    if ((i & 0xE0) == 192)
      return paramBitSource.readBits(16) | (i & 0x1F) << 16;
    throw new IllegalArgumentException("Bad ECI bits starting with byte " + i);
  }

  private static char toAlphaNumericChar(int paramInt)
    throws FormatException
  {
    if (paramInt >= ALPHANUMERIC_CHARS.length)
      throw FormatException.getFormatInstance();
    return ALPHANUMERIC_CHARS[paramInt];
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.qrcode.decoder.DecodedBitStreamParser
 * JD-Core Version:    0.6.2
 */