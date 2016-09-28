package com.starcor.xul.Factory;

public abstract class XulParserData
{
  public static final int ITEM_TAG_BEGIN = 0;
  public static final int ITEM_TAG_END = 2;
  public static final int ITEM_TEXT = 1;

  public abstract void buildItem(XulFactory.ItemBuilder paramItemBuilder);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Factory.XulParserData
 * JD-Core Version:    0.6.2
 */