package com.starcor.core.utils;

import android.text.TextUtils;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinYinUtil
{
  public static String getFirstSpell(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return "";
    StringBuffer localStringBuffer = new StringBuffer();
    char[] arrayOfChar = paramString.toCharArray();
    HanyuPinyinOutputFormat localHanyuPinyinOutputFormat = new HanyuPinyinOutputFormat();
    localHanyuPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
    localHanyuPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    int i = 0;
    if (i < arrayOfChar.length)
    {
      if (arrayOfChar[i] > '');
      while (true)
      {
        try
        {
          String[] arrayOfString = PinyinHelper.toHanyuPinyinStringArray(arrayOfChar[i], localHanyuPinyinOutputFormat);
          if (arrayOfString != null)
            localStringBuffer.append(arrayOfString[0].charAt(0));
          i++;
        }
        catch (BadHanyuPinyinOutputFormatCombination localBadHanyuPinyinOutputFormatCombination)
        {
          localBadHanyuPinyinOutputFormatCombination.printStackTrace();
          continue;
        }
        localStringBuffer.append(arrayOfChar[i]);
      }
    }
    return localStringBuffer.toString().replaceAll("\\W", "").trim();
  }

  public static String getFullSpell(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return "";
    StringBuffer localStringBuffer = new StringBuffer();
    char[] arrayOfChar = paramString.toCharArray();
    HanyuPinyinOutputFormat localHanyuPinyinOutputFormat = new HanyuPinyinOutputFormat();
    localHanyuPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
    localHanyuPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    int i = 0;
    if (i < arrayOfChar.length)
    {
      if (arrayOfChar[i] > '');
      while (true)
      {
        try
        {
          localStringBuffer.append(PinyinHelper.toHanyuPinyinStringArray(arrayOfChar[i], localHanyuPinyinOutputFormat)[0]);
          i++;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          continue;
        }
        localStringBuffer.append(arrayOfChar[i]);
      }
    }
    return localStringBuffer.toString();
  }

  public static String getPinyin(String paramString)
  {
    HanyuPinyinOutputFormat localHanyuPinyinOutputFormat = new HanyuPinyinOutputFormat();
    localHanyuPinyinOutputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
    localHanyuPinyinOutputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
    localHanyuPinyinOutputFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
    char[] arrayOfChar = paramString.trim().toCharArray();
    Object localObject = "";
    for (int i = 0; ; i++)
    {
      try
      {
        if (i < arrayOfChar.length)
          if (Character.toString(arrayOfChar[i]).matches("[\\u4E00-\\u9FA5]+"))
          {
            String[] arrayOfString = PinyinHelper.toHanyuPinyinStringArray(arrayOfChar[i], localHanyuPinyinOutputFormat);
            localObject = (String)localObject + arrayOfString[0];
          }
          else
          {
            String str = (String)localObject + Character.toString(arrayOfChar[i]);
            localObject = str;
          }
      }
      catch (BadHanyuPinyinOutputFormatCombination localBadHanyuPinyinOutputFormatCombination)
      {
        localBadHanyuPinyinOutputFormatCombination.printStackTrace();
      }
      return localObject;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.utils.PinYinUtil
 * JD-Core Version:    0.6.2
 */