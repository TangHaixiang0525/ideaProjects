package net.sourceforge.pinyin4j.format;

public class HanyuPinyinToneType
{
  public static final HanyuPinyinToneType WITHOUT_TONE = new HanyuPinyinToneType("WITHOUT_TONE");
  public static final HanyuPinyinToneType WITH_TONE_MARK = new HanyuPinyinToneType("WITH_TONE_MARK");
  public static final HanyuPinyinToneType WITH_TONE_NUMBER = new HanyuPinyinToneType("WITH_TONE_NUMBER");
  protected String name;

  protected HanyuPinyinToneType(String paramString)
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
 * Qualified Name:     net.sourceforge.pinyin4j.format.HanyuPinyinToneType
 * JD-Core Version:    0.6.2
 */