package com.google.zxing.client.result;

import com.google.zxing.Result;

public final class WifiResultParser extends ResultParser
{
  public WifiParsedResult parse(Result paramResult)
  {
    String str1 = paramResult.getText();
    if (!str1.startsWith("WIFI:"));
    String str2;
    do
    {
      return null;
      str2 = matchSinglePrefixedField("S:", str1, ';', false);
    }
    while ((str2 == null) || (str2.length() == 0));
    String str3 = matchSinglePrefixedField("P:", str1, ';', false);
    String str4 = matchSinglePrefixedField("T:", str1, ';', false);
    if (str4 == null)
      str4 = "nopass";
    return new WifiParsedResult(str4, str2, str3);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.client.result.WifiResultParser
 * JD-Core Version:    0.6.2
 */