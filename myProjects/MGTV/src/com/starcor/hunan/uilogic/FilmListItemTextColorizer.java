package com.starcor.hunan.uilogic;

import android.text.TextUtils;
import com.starcor.hunan.widget.FilmListView.ItemTextColorizer;
import java.util.HashMap;
import net.sourceforge.pinyin4j.PinyinHelper;

public class FilmListItemTextColorizer
  implements FilmListView.ItemTextColorizer
{
  private HashMap<String, Range> cachedColors = new HashMap();
  private int highlightColor = -867016;
  private String matcher;

  private static boolean isCJKChar(char paramChar)
  {
    return ((paramChar >= '㐀') && (paramChar <= 40959)) || ((paramChar >= '぀') && (paramChar <= 'ㄩ'));
  }

  private void setColors(String paramString, int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    while (paramInt1 < paramInt2)
    {
      char c = paramString.charAt(paramInt1);
      if (((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z')) || ((c >= '0') && (c <= '9')) || (isCJKChar(c)))
        paramArrayOfInt[paramInt1] = this.highlightColor;
      paramInt1++;
    }
  }

  public boolean getItemTextColors(int paramInt1, Object paramObject, String paramString, int[] paramArrayOfInt, int paramInt2)
  {
    if ((TextUtils.isEmpty(this.matcher)) || (paramString.length() < this.matcher.length()))
      return false;
    Range localRange1 = (Range)this.cachedColors.get(paramString);
    if (localRange1 != null)
    {
      setColors(paramString, paramArrayOfInt, localRange1.begin, localRange1.end);
      return true;
    }
    String str = paramString.toLowerCase();
    int i = 0;
    int j = 0;
    int k = 0;
    int m = i - j - k;
    if (m >= this.matcher.length())
    {
      setColors(paramString, paramArrayOfInt, j, i);
      HashMap localHashMap = this.cachedColors;
      Range localRange2 = new Range(j, i);
      localHashMap.put(paramString, localRange2);
      return true;
    }
    if (i >= str.length());
    while (true)
    {
      label151: j = i;
      if (i <= str.length() - this.matcher.length())
        break;
      return false;
      int n = this.matcher.charAt(m);
      char c = str.charAt(i);
      int i1;
      if ((c == ' ') || (c == '\t'))
      {
        k++;
        i1 = 1;
      }
      while (true)
      {
        if (i1 == 0)
          break label353;
        i++;
        if (i >= 0)
          break;
        break label151;
        if (((c >= 'a') && (c <= 'z')) || ((c >= '0') && (c <= '9')) || (n == c))
        {
          if (n == c);
          for (i1 = 1; ; i1 = 0)
            break;
        }
        String[] arrayOfString = PinyinHelper.toHanyuPinyinStringArray(c);
        if (arrayOfString != null)
        {
          int i2 = arrayOfString.length;
          for (int i3 = 0; ; i3++)
          {
            i1 = 0;
            if (i3 >= i2)
              break;
            if (arrayOfString[i3].toLowerCase().charAt(0) == n)
            {
              i1 = 1;
              break;
            }
          }
        }
        k++;
        i1 = 1;
      }
      label353: i = j + 1;
    }
  }

  public void setHighlightColor(int paramInt)
  {
    this.highlightColor = paramInt;
  }

  public void setMatcher(String paramString)
  {
    String str = paramString.toLowerCase();
    if (!str.equals(this.matcher))
    {
      this.cachedColors = new HashMap();
      this.matcher = str;
    }
  }

  private static class Range
  {
    public int begin;
    public int end;

    public Range(int paramInt1, int paramInt2)
    {
      this.begin = paramInt1;
      this.end = paramInt2;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.uilogic.FilmListItemTextColorizer
 * JD-Core Version:    0.6.2
 */