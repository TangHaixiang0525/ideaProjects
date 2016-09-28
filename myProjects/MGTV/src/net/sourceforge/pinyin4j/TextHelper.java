package net.sourceforge.pinyin4j;

class TextHelper
{
  static String extractPinyinString(String paramString)
  {
    return paramString.substring(0, -1 + paramString.length());
  }

  static String extractToneNumber(String paramString)
  {
    return paramString.substring(-1 + paramString.length());
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     net.sourceforge.pinyin4j.TextHelper
 * JD-Core Version:    0.6.2
 */