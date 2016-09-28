package com.google.zxing.common;

import com.google.zxing.DecodeHintType;
import java.util.Map;

public final class StringUtils
{
  private static final boolean ASSUME_SHIFT_JIS = false;
  private static final String EUC_JP = "EUC_JP";
  public static final String GB2312 = "GB2312";
  private static final String ISO88591 = "ISO8859_1";
  private static final String PLATFORM_DEFAULT_ENCODING = System.getProperty("file.encoding");
  public static final String SHIFT_JIS = "SJIS";
  private static final String UTF8 = "UTF8";

  static
  {
    if (("SJIS".equalsIgnoreCase(PLATFORM_DEFAULT_ENCODING)) || ("EUC_JP".equalsIgnoreCase(PLATFORM_DEFAULT_ENCODING)));
    for (boolean bool = true; ; bool = false)
    {
      ASSUME_SHIFT_JIS = bool;
      return;
    }
  }

  public static String guessEncoding(byte[] paramArrayOfByte, Map<DecodeHintType, ?> paramMap)
  {
    if (paramMap != null)
    {
      String str = (String)paramMap.get(DecodeHintType.CHARACTER_SET);
      if (str != null)
        return str;
    }
    if ((paramArrayOfByte.length > 3) && (paramArrayOfByte[0] == -17) && (paramArrayOfByte[1] == -69) && (paramArrayOfByte[2] == -65))
      return "UTF8";
    int i = paramArrayOfByte.length;
    int j = 1;
    int k = 1;
    int m = 1;
    int n = 0;
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    int i4 = 0;
    int i5 = 0;
    int i6 = 0;
    if ((i6 < i) && ((j != 0) || (k != 0) || (m != 0)))
    {
      int i7 = 0xFF & paramArrayOfByte[i6];
      if ((i7 >= 128) && (i7 <= 191))
      {
        if (n > 0)
          n--;
        label145: if (((i7 == 194) || (i7 == 195)) && (i6 < i - 1))
        {
          int i10 = 0xFF & paramArrayOfByte[(i6 + 1)];
          if ((i10 <= 191) && (((i7 == 194) && (i10 >= 160)) || ((i7 == 195) && (i10 >= 128))))
            i3 = 1;
        }
        if ((i7 >= 127) && (i7 <= 159))
          j = 0;
        if ((i7 >= 161) && (i7 <= 223) && (i5 == 0))
          i2++;
        if ((i5 == 0) && (((i7 >= 240) && (i7 <= 255)) || (i7 == 128) || (i7 == 160)))
          k = 0;
        if (((i7 < 129) || (i7 > 159)) && ((i7 < 224) || (i7 > 239)))
          break label459;
        if (i5 == 0)
          break label402;
        i5 = 0;
      }
      while (true)
      {
        i6++;
        break;
        if (n > 0)
          m = 0;
        if ((i7 < 192) || (i7 > 253))
          break label145;
        i4 = 1;
        int i8 = i7;
        while ((i8 & 0x40) != 0)
        {
          n++;
          i8 <<= 1;
        }
        break label145;
        label402: i5 = 1;
        if (i6 >= -1 + paramArrayOfByte.length)
        {
          k = 0;
        }
        else
        {
          int i9 = 0xFF & paramArrayOfByte[(i6 + 1)];
          if ((i9 < 64) || (i9 > 252))
          {
            k = 0;
          }
          else
          {
            i1++;
            continue;
            label459: i5 = 0;
          }
        }
      }
    }
    if (n > 0)
      m = 0;
    if ((k != 0) && (ASSUME_SHIFT_JIS))
      return "SJIS";
    if ((m != 0) && (i4 != 0))
      return "UTF8";
    if ((k != 0) && ((i1 >= 3) || (i2 * 20 > i)))
      return "SJIS";
    if ((i3 == 0) && (j != 0))
      return "ISO8859_1";
    return PLATFORM_DEFAULT_ENCODING;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.common.StringUtils
 * JD-Core Version:    0.6.2
 */