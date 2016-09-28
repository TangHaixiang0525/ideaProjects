package net.sourceforge.pinyin4j;

import java.io.BufferedInputStream;

class ResourceHelper
{
  static BufferedInputStream getResourceInputStream(String paramString)
  {
    return new BufferedInputStream(ResourceHelper.class.getResourceAsStream(paramString));
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     net.sourceforge.pinyin4j.ResourceHelper
 * JD-Core Version:    0.6.2
 */