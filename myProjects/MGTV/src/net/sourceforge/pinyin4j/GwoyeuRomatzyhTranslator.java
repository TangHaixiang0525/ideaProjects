package net.sourceforge.pinyin4j;

import com.hp.hpl.sparta.Document;
import com.hp.hpl.sparta.Element;
import com.hp.hpl.sparta.ParseException;

class GwoyeuRomatzyhTranslator
{
  private static String[] tones = { "_I", "_II", "_III", "_IV", "_V" };

  static String convertHanyuPinyinToGwoyeuRomatzyh(String paramString)
  {
    String str1 = TextHelper.extractPinyinString(paramString);
    String str2 = TextHelper.extractToneNumber(paramString);
    try
    {
      String str3 = "//" + PinyinRomanizationType.HANYU_PINYIN.getTagName() + "[text()='" + str1 + "']";
      Element localElement = GwoyeuRomatzyhResource.getInstance().getPinyinToGwoyeuMappingDoc().xpathSelectElement(str3);
      Object localObject = null;
      if (localElement != null)
      {
        String str4 = localElement.xpathSelectString("../" + PinyinRomanizationType.GWOYEU_ROMATZYH.getTagName() + tones[(-1 + java.lang.Integer.parseInt(str2))] + "/text()");
        localObject = str4;
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
 * Qualified Name:     net.sourceforge.pinyin4j.GwoyeuRomatzyhTranslator
 * JD-Core Version:    0.6.2
 */