package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.Map;

public final class EmailAddressResultParser extends ResultParser
{
  public EmailAddressParsedResult parse(Result paramResult)
  {
    String str1 = paramResult.getText();
    EmailAddressParsedResult localEmailAddressParsedResult;
    if ((str1.startsWith("mailto:")) || (str1.startsWith("MAILTO:")))
    {
      String str2 = str1.substring(7);
      int i = str2.indexOf('?');
      if (i >= 0)
        str2 = str2.substring(0, i);
      Map localMap = parseNameValuePairs(str1);
      String str3 = null;
      String str4 = null;
      if (localMap != null)
      {
        if (str2.length() == 0)
          str2 = (String)localMap.get("to");
        str4 = (String)localMap.get("subject");
        str3 = (String)localMap.get("body");
      }
      localEmailAddressParsedResult = new EmailAddressParsedResult(str2, str4, str3, str1);
    }
    boolean bool;
    do
    {
      return localEmailAddressParsedResult;
      bool = EmailDoCoMoResultParser.isBasicallyValidEmailAddress(str1);
      localEmailAddressParsedResult = null;
    }
    while (!bool);
    return new EmailAddressParsedResult(str1, null, null, "mailto:" + str1);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.client.result.EmailAddressResultParser
 * JD-Core Version:    0.6.2
 */