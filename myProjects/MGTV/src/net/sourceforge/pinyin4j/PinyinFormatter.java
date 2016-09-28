package net.sourceforge.pinyin4j;

import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

class PinyinFormatter
{
  private static String convertToneNumber2ToneMark(String paramString)
  {
    String str = paramString.toLowerCase();
    int i;
    int j;
    int k;
    int m;
    int n;
    if (str.matches("[a-z]*[1-5]?"))
    {
      if (!str.matches("[a-z]*[1-5]"))
        break label254;
      i = Character.getNumericValue(str.charAt(-1 + str.length()));
      j = str.indexOf('a');
      k = str.indexOf('e');
      m = str.indexOf("ou");
      if (-1 == j)
        break label175;
      n = 97;
    }
    while (true)
    {
      if ((36 != n) && (-1 != j))
      {
        int i1 = "aeiouv".indexOf(n);
        char c = "āáăàaēéĕèeīíĭìiōóŏòoūúŭùuǖǘǚǜü".charAt(i - 1 + i1 * 5);
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append(str.substring(0, j).replaceAll("v", "ü"));
        localStringBuffer.append(c);
        localStringBuffer.append(str.substring(j + 1, -1 + str.length()).replaceAll("v", "ü"));
        str = localStringBuffer.toString();
      }
      return str;
      label175: if (-1 != k)
      {
        j = k;
        n = 101;
      }
      else if (-1 != m)
      {
        n = "ou".charAt(0);
        j = m;
      }
      else
      {
        j = -1 + str.length();
        while (true)
          if (j >= 0)
          {
            if (String.valueOf(str.charAt(j)).matches("[aeiouv]"))
            {
              n = str.charAt(j);
              break;
            }
            j--;
            continue;
            label254: return str.replaceAll("v", "ü");
          }
        j = -1;
        n = 36;
      }
    }
  }

  static String formatHanyuPinyin(String paramString, HanyuPinyinOutputFormat paramHanyuPinyinOutputFormat)
    throws BadHanyuPinyinOutputFormatCombination
  {
    if ((HanyuPinyinToneType.WITH_TONE_MARK == paramHanyuPinyinOutputFormat.getToneType()) && ((HanyuPinyinVCharType.WITH_V == paramHanyuPinyinOutputFormat.getVCharType()) || (HanyuPinyinVCharType.WITH_U_AND_COLON == paramHanyuPinyinOutputFormat.getVCharType())))
      throw new BadHanyuPinyinOutputFormatCombination("tone marks cannot be added to v or u:");
    if (HanyuPinyinToneType.WITHOUT_TONE == paramHanyuPinyinOutputFormat.getToneType())
    {
      paramString = paramString.replaceAll("[1-5]", "");
      if (HanyuPinyinVCharType.WITH_V != paramHanyuPinyinOutputFormat.getVCharType())
        break label120;
      paramString = paramString.replaceAll("u:", "v");
    }
    while (true)
    {
      if (HanyuPinyinCaseType.UPPERCASE == paramHanyuPinyinOutputFormat.getCaseType())
        paramString = paramString.toUpperCase();
      return paramString;
      if (HanyuPinyinToneType.WITH_TONE_MARK != paramHanyuPinyinOutputFormat.getToneType())
        break;
      paramString = convertToneNumber2ToneMark(paramString.replaceAll("u:", "v"));
      break;
      label120: if (HanyuPinyinVCharType.WITH_U_UNICODE == paramHanyuPinyinOutputFormat.getVCharType())
        paramString = paramString.replaceAll("u:", "ü");
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     net.sourceforge.pinyin4j.PinyinFormatter
 * JD-Core Version:    0.6.2
 */