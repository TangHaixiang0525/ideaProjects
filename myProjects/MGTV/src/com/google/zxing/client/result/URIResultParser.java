package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class URIResultParser extends ResultParser
{
  private static final String PATTERN_END = "(:\\d{1,5})?(/|\\?|$)";
  private static final Pattern URL_WITHOUT_PROTOCOL_PATTERN = Pattern.compile("([a-zA-Z0-9\\-]+\\.)+[a-zA-Z0-9\\-]{2,}(:\\d{1,5})?(/|\\?|$)");
  private static final Pattern URL_WITH_PROTOCOL_PATTERN = Pattern.compile("[a-zA-Z0-9]{2,}://[a-zA-Z0-9\\-]+(\\.[a-zA-Z0-9\\-]+)*(:\\d{1,5})?(/|\\?|$)");

  static boolean isBasicallyValidURI(CharSequence paramCharSequence)
  {
    Matcher localMatcher1 = URL_WITH_PROTOCOL_PATTERN.matcher(paramCharSequence);
    if ((localMatcher1.find()) && (localMatcher1.start() == 0));
    Matcher localMatcher2;
    do
    {
      return true;
      localMatcher2 = URL_WITHOUT_PROTOCOL_PATTERN.matcher(paramCharSequence);
    }
    while ((localMatcher2.find()) && (localMatcher2.start() == 0));
    return false;
  }

  public URIParsedResult parse(Result paramResult)
  {
    String str1 = paramResult.getText();
    if (str1.startsWith("URL:"))
      str1 = str1.substring(4);
    String str2 = str1.trim();
    if (isBasicallyValidURI(str2))
      return new URIParsedResult(str2, null);
    return null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.client.result.URIResultParser
 * JD-Core Version:    0.6.2
 */