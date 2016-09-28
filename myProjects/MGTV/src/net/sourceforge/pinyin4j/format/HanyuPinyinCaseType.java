package net.sourceforge.pinyin4j.format;

public class HanyuPinyinCaseType
{
  public static final HanyuPinyinCaseType LOWERCASE = new HanyuPinyinCaseType("LOWERCASE");
  public static final HanyuPinyinCaseType UPPERCASE = new HanyuPinyinCaseType("UPPERCASE");
  protected String name;

  protected HanyuPinyinCaseType(String paramString)
  {
    setName(paramString);
  }

  public String getName()
  {
    return this.name;
  }

  protected void setName(String paramString)
  {
    this.name = paramString;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     net.sourceforge.pinyin4j.format.HanyuPinyinCaseType
 * JD-Core Version:    0.6.2
 */