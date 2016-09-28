package net.sourceforge.pinyin4j;

import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinHelper
{
  private static String[] convertToGwoyeuRomatzyhStringArray(char paramChar)
  {
    String[] arrayOfString1 = getUnformattedHanyuPinyinStringArray(paramChar);
    if (arrayOfString1 != null)
    {
      String[] arrayOfString2 = new String[arrayOfString1.length];
      for (int i = 0; i < arrayOfString1.length; i++)
        arrayOfString2[i] = GwoyeuRomatzyhTranslator.convertHanyuPinyinToGwoyeuRomatzyh(arrayOfString1[i]);
      return arrayOfString2;
    }
    return null;
  }

  private static String[] convertToTargetPinyinStringArray(char paramChar, PinyinRomanizationType paramPinyinRomanizationType)
  {
    String[] arrayOfString1 = getUnformattedHanyuPinyinStringArray(paramChar);
    if (arrayOfString1 != null)
    {
      String[] arrayOfString2 = new String[arrayOfString1.length];
      for (int i = 0; i < arrayOfString1.length; i++)
        arrayOfString2[i] = PinyinRomanizationTranslator.convertRomanizationSystem(arrayOfString1[i], PinyinRomanizationType.HANYU_PINYIN, paramPinyinRomanizationType);
      return arrayOfString2;
    }
    return null;
  }

  private static String getFirstHanyuPinyinString(char paramChar, HanyuPinyinOutputFormat paramHanyuPinyinOutputFormat)
    throws BadHanyuPinyinOutputFormatCombination
  {
    String[] arrayOfString = getFormattedHanyuPinyinStringArray(paramChar, paramHanyuPinyinOutputFormat);
    if ((arrayOfString != null) && (arrayOfString.length > 0))
      return arrayOfString[0];
    return null;
  }

  private static String[] getFormattedHanyuPinyinStringArray(char paramChar, HanyuPinyinOutputFormat paramHanyuPinyinOutputFormat)
    throws BadHanyuPinyinOutputFormatCombination
  {
    String[] arrayOfString = getUnformattedHanyuPinyinStringArray(paramChar);
    if (arrayOfString != null)
    {
      for (int i = 0; i < arrayOfString.length; i++)
        arrayOfString[i] = PinyinFormatter.formatHanyuPinyin(arrayOfString[i], paramHanyuPinyinOutputFormat);
      return arrayOfString;
    }
    return null;
  }

  private static String[] getUnformattedHanyuPinyinStringArray(char paramChar)
  {
    return ChineseToPinyinResource.getInstance().getHanyuPinyinStringArray(paramChar);
  }

  public static String[] toGwoyeuRomatzyhStringArray(char paramChar)
  {
    return convertToGwoyeuRomatzyhStringArray(paramChar);
  }

  public static String toHanyuPinyinString(String paramString1, HanyuPinyinOutputFormat paramHanyuPinyinOutputFormat, String paramString2)
    throws BadHanyuPinyinOutputFormatCombination
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    if (i < paramString1.length())
    {
      String str = getFirstHanyuPinyinString(paramString1.charAt(i), paramHanyuPinyinOutputFormat);
      if (str != null)
      {
        localStringBuffer.append(str);
        if (i != -1 + paramString1.length())
          localStringBuffer.append(paramString2);
      }
      while (true)
      {
        i++;
        break;
        localStringBuffer.append(paramString1.charAt(i));
      }
    }
    return localStringBuffer.toString();
  }

  public static String[] toHanyuPinyinStringArray(char paramChar)
  {
    return getUnformattedHanyuPinyinStringArray(paramChar);
  }

  public static String[] toHanyuPinyinStringArray(char paramChar, HanyuPinyinOutputFormat paramHanyuPinyinOutputFormat)
    throws BadHanyuPinyinOutputFormatCombination
  {
    return getFormattedHanyuPinyinStringArray(paramChar, paramHanyuPinyinOutputFormat);
  }

  public static String[] toMPS2PinyinStringArray(char paramChar)
  {
    return convertToTargetPinyinStringArray(paramChar, PinyinRomanizationType.MPS2_PINYIN);
  }

  public static String[] toTongyongPinyinStringArray(char paramChar)
  {
    return convertToTargetPinyinStringArray(paramChar, PinyinRomanizationType.TONGYONG_PINYIN);
  }

  public static String[] toWadeGilesPinyinStringArray(char paramChar)
  {
    return convertToTargetPinyinStringArray(paramChar, PinyinRomanizationType.WADEGILES_PINYIN);
  }

  public static String[] toYalePinyinStringArray(char paramChar)
  {
    return convertToTargetPinyinStringArray(paramChar, PinyinRomanizationType.YALE_PINYIN);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     net.sourceforge.pinyin4j.PinyinHelper
 * JD-Core Version:    0.6.2
 */