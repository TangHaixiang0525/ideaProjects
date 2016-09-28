package com.starcor.hunan.widget.gridview;

public class PagerProperties extends Properties
{
  boolean isFocusable = true;
  private PagerType pagerType;

  public PagerProperties()
  {
    setPagerType(PagerType.LEFT);
  }

  public PagerType getPagerType()
  {
    return this.pagerType;
  }

  public boolean isPagerFocusable()
  {
    return this.isFocusable;
  }

  public void setPagerFocusable(boolean paramBoolean)
  {
    this.isFocusable = paramBoolean;
  }

  public void setPagerType(PagerType paramPagerType)
  {
    this.pagerType = paramPagerType;
    switch (1.$SwitchMap$com$starcor$hunan$widget$gridview$PagerProperties$PagerType[paramPagerType.ordinal()])
    {
    default:
      return;
    case 1:
      setItemType(Properties.ItemType.SCROLL);
      this.isFocusable = false;
      return;
    case 2:
      setItemOrientation(Properties.ItemOrientation.HORIZONTAL);
      setRowCount(1);
      return;
    case 3:
    }
    setItemOrientation(Properties.ItemOrientation.VERTICAL);
    setColumnCount(1);
  }

  public static enum PagerType
  {
    static
    {
      SCROLL = new PagerType("SCROLL", 2);
      TOP_WITH_ALL_ITEMS = new PagerType("TOP_WITH_ALL_ITEMS", 3);
      PagerType[] arrayOfPagerType = new PagerType[4];
      arrayOfPagerType[0] = LEFT;
      arrayOfPagerType[1] = TOP;
      arrayOfPagerType[2] = SCROLL;
      arrayOfPagerType[3] = TOP_WITH_ALL_ITEMS;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.gridview.PagerProperties
 * JD-Core Version:    0.6.2
 */