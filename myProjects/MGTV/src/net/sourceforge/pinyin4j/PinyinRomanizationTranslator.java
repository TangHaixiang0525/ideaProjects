package net.sourceforge.pinyin4j;

import com.hp.hpl.sparta.Document;
import com.hp.hpl.sparta.Element;
import com.hp.hpl.sparta.ParseException;

class PinyinRomanizationTranslator
{
  static String convertRomanizationSystem(String paramString, PinyinRomanizationType paramPinyinRomanizationType1, PinyinRomanizationType paramPinyinRomanizationType2)
  {
    String str1 = TextHelper.extractPinyinString(paramString);
    String str2 = TextHelper.extractToneNumber(paramString);
    try
    {
      String str3 = "//" + paramPinyinRomanizationType1.getTagName() + "[text()='" + str1 + "']";
      Element localElement = PinyinRomanizationResource.getInstance().getPinyinMappingDoc().xpathSelectElement(str3);
      Object localObject = null;
      if (localElement != null)
      {
        String str4 = localElement.xpathSelectString("../" + paramPinyinRomanizationType2.getTagName() + "/text()");
        String str5 = str4 + str2;
        localObject = str5;
      }
      return localObject;
    }
    catch (ParseException localParseException)
    {
      localParseException.printStackTrace();
    }
    return null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     net.sourceforge.pinyin4j.PinyinRomanizationTranslator
 * JD-Core Version:    0.6.2
 */