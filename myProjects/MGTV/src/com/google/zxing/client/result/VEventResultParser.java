package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.List;

public final class VEventResultParser extends ResultParser
{
  private static String matchSingleVCardPrefixedField(CharSequence paramCharSequence, String paramString, boolean paramBoolean)
  {
    List localList = VCardResultParser.matchSingleVCardPrefixedField(paramCharSequence, paramString, paramBoolean);
    if ((localList == null) || (localList.isEmpty()))
      return null;
    return (String)localList.get(0);
  }

  public CalendarParsedResult parse(Result paramResult)
  {
    String str1 = paramResult.getText();
    if (str1 == null)
      return null;
    if (str1.indexOf("BEGIN:VEVENT") < 0)
      return null;
    String str2 = matchSingleVCardPrefixedField("SUMMARY", str1, true);
    String str3 = matchSingleVCardPrefixedField("DTSTART", str1, true);
    if (str3 == null)
      return null;
    String str4 = matchSingleVCardPrefixedField("DTEND", str1, true);
    String str5 = matchSingleVCardPrefixedField("LOCATION", str1, true);
    String str6 = matchSingleVCardPrefixedField("DESCRIPTION", str1, true);
    String str7 = matchSingleVCardPrefixedField("GEO", str1, true);
    double d1;
    double d3;
    if (str7 == null)
    {
      d1 = (0.0D / 0.0D);
      d3 = (0.0D / 0.0D);
    }
    while (true)
    {
      try
      {
        CalendarParsedResult localCalendarParsedResult = new CalendarParsedResult(str2, str3, str4, str5, null, str6, d1, d3);
        return localCalendarParsedResult;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        return null;
      }
      int i = str7.indexOf(';');
      try
      {
        d1 = Double.parseDouble(str7.substring(0, i));
        double d2 = Double.parseDouble(str7.substring(i + 1));
        d3 = d2;
      }
      catch (NumberFormatException localNumberFormatException)
      {
      }
    }
    return null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.client.result.VEventResultParser
 * JD-Core Version:    0.6.2
 */