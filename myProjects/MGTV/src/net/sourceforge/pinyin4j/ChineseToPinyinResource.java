package net.sourceforge.pinyin4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

class ChineseToPinyinResource
{
  private Properties unicodeToHanyuPinyinTable = null;

  private ChineseToPinyinResource()
  {
    initializeResource();
  }

  private String getHanyuPinyinRecordFromChar(char paramChar)
  {
    String str1 = Integer.toHexString(paramChar).toUpperCase();
    String str2 = getUnicodeToHanyuPinyinTable().getProperty(str1);
    if (isValidRecord(str2))
      return str2;
    return null;
  }

  static ChineseToPinyinResource getInstance()
  {
    return ChineseToPinyinResourceHolder.theInstance;
  }

  private Properties getUnicodeToHanyuPinyinTable()
  {
    return this.unicodeToHanyuPinyinTable;
  }

  private void initializeResource()
  {
    try
    {
      setUnicodeToHanyuPinyinTable(new Properties());
      getUnicodeToHanyuPinyinTable().load(ResourceHelper.getResourceInputStream("/pinyindb/unicode_to_hanyu_pinyin.txt"));
      return;
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      localFileNotFoundException.printStackTrace();
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }

  private boolean isValidRecord(String paramString)
  {
    return (paramString != null) && (!paramString.equals("(none0)")) && (paramString.startsWith("(")) && (paramString.endsWith(")"));
  }

  private void setUnicodeToHanyuPinyinTable(Properties paramProperties)
  {
    this.unicodeToHanyuPinyinTable = paramProperties;
  }

  String[] getHanyuPinyinStringArray(char paramChar)
  {
    String str = getHanyuPinyinRecordFromChar(paramChar);
    if (str != null)
    {
      int i = str.indexOf("(");
      int j = str.lastIndexOf(")");
      return str.substring(i + "(".length(), j).split(",");
    }
    return null;
  }

  private static class ChineseToPinyinResourceHolder
  {
    static final ChineseToPinyinResource theInstance = new ChineseToPinyinResource(null);
  }

  class Field
  {
    static final String COMMA = ",";
    static final String LEFT_BRACKET = "(";
    static final String RIGHT_BRACKET = ")";

    Field()
    {
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     net.sourceforge.pinyin4j.ChineseToPinyinResource
 * JD-Core Version:    0.6.2
 */