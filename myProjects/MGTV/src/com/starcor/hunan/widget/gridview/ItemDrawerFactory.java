package com.starcor.hunan.widget.gridview;

import com.starcor.hunan.widget.gridview.drawers.ItemDrawer;
import com.starcor.hunan.widget.gridview.drawers.LeftPicItemDrawer;
import com.starcor.hunan.widget.gridview.drawers.LeftPicWithDetailOnBottomItemDrawer;
import com.starcor.hunan.widget.gridview.drawers.LeftPicWithDetailOnLeftItemDrawer;
import com.starcor.hunan.widget.gridview.drawers.ScrollItemDrawer;
import com.starcor.hunan.widget.gridview.drawers.TitleItemDrawer;
import com.starcor.hunan.widget.gridview.drawers.TitleWithDetailOnBottomItemDrawer;
import com.starcor.hunan.widget.gridview.drawers.TitleWithDetailOnLeftItemDrawer;
import com.starcor.hunan.widget.gridview.drawers.TopPicItemDrawer;

public abstract class ItemDrawerFactory
{
  static LeftPicItemDrawer mLeftPicItemDrawer;
  static LeftPicWithDetailOnBottomItemDrawer mLeftPicWithDetailOnBottomItemDrawer;
  static LeftPicWithDetailOnLeftItemDrawer mLeftPicWithDetailOnLeftItemDrawer;
  static ScrollItemDrawer mScrollItemDrawer;
  static TitleItemDrawer mTitleItemDrawer;
  static TitleWithDetailOnBottomItemDrawer mTitleWithDetailOnBottomItemDrawer;
  static TitleWithDetailOnLeftItemDrawer mTitleWithDetailOnLeftItemDrawer;
  static TopPicItemDrawer mTopPicItemDrawer;

  public static ItemDrawer createItemDrawer(Properties.ItemType paramItemType)
  {
    switch (1.$SwitchMap$com$starcor$hunan$widget$gridview$Properties$ItemType[paramItemType.ordinal()])
    {
    default:
      return getTitleItemDrawer();
    case 1:
      return getTitleItemDrawer();
    case 2:
      return getTitleWithDetailOnBottomItemDrawer();
    case 3:
      return getTitleWithDetailOnLeftItemDrawer();
    case 4:
      return getLeftPicItemDrawer();
    case 5:
      return getScrollItemDrawer();
    case 6:
      return getTopPicItemDrawer();
    case 7:
      return getLeftPicWithDetailOnLeftItemDrawer();
    case 8:
    }
    return getLeftPicWithDetailOnBottomItemDrawer();
  }

  public static LeftPicItemDrawer getLeftPicItemDrawer()
  {
    if (mLeftPicItemDrawer == null)
      mLeftPicItemDrawer = new LeftPicItemDrawer();
    return mLeftPicItemDrawer;
  }

  public static LeftPicWithDetailOnBottomItemDrawer getLeftPicWithDetailOnBottomItemDrawer()
  {
    if (mLeftPicWithDetailOnBottomItemDrawer == null)
      mLeftPicWithDetailOnBottomItemDrawer = new LeftPicWithDetailOnBottomItemDrawer();
    return mLeftPicWithDetailOnBottomItemDrawer;
  }

  public static LeftPicWithDetailOnLeftItemDrawer getLeftPicWithDetailOnLeftItemDrawer()
  {
    if (mLeftPicWithDetailOnLeftItemDrawer == null)
      mLeftPicWithDetailOnLeftItemDrawer = new LeftPicWithDetailOnLeftItemDrawer();
    return mLeftPicWithDetailOnLeftItemDrawer;
  }

  public static ScrollItemDrawer getScrollItemDrawer()
  {
    if (mScrollItemDrawer == null)
      mScrollItemDrawer = new ScrollItemDrawer();
    return mScrollItemDrawer;
  }

  public static TitleItemDrawer getTitleItemDrawer()
  {
    if (mTitleItemDrawer == null)
      mTitleItemDrawer = new TitleItemDrawer();
    return mTitleItemDrawer;
  }

  public static TitleWithDetailOnBottomItemDrawer getTitleWithDetailOnBottomItemDrawer()
  {
    if (mTitleWithDetailOnBottomItemDrawer == null)
      mTitleWithDetailOnBottomItemDrawer = new TitleWithDetailOnBottomItemDrawer();
    return mTitleWithDetailOnBottomItemDrawer;
  }

  public static TitleWithDetailOnLeftItemDrawer getTitleWithDetailOnLeftItemDrawer()
  {
    if (mTitleWithDetailOnLeftItemDrawer == null)
      mTitleWithDetailOnLeftItemDrawer = new TitleWithDetailOnLeftItemDrawer();
    return mTitleWithDetailOnLeftItemDrawer;
  }

  public static TopPicItemDrawer getTopPicItemDrawer()
  {
    if (mTopPicItemDrawer == null)
      mTopPicItemDrawer = new TopPicItemDrawer();
    return mTopPicItemDrawer;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.gridview.ItemDrawerFactory
 * JD-Core Version:    0.6.2
 */