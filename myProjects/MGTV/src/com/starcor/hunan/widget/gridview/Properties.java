package com.starcor.hunan.widget.gridview;

import android.graphics.Bitmap;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.text.SpannableStringBuilder;

public abstract class Properties
{
  private static final int DEFAULT_COLUMN_COUNT = 1;
  private static final int DEFAULT_ITEM_HEIGHT = 60;
  private static final ItemOrientation DEFAULT_ITEM_ORIENTATION = ItemOrientation.HORIZONTAL;
  private static final ItemType DEFAULT_ITEM_TYPE = ItemType.TITLE;
  private static final int DEFAULT_ITEM_WIDTH = 150;
  private static final int DEFAULT_MARGIN = 0;
  private static final int DEFAULT_ROW_COUNT = 1;
  private static final ScrollType DEFAULT_SCROLL_TYPE = ScrollType.ITEM;
  private static final int DEFAULT_TEXT_COLOR = -16777216;
  private static final int DEFAULT_TEXT_SIZE = 20;
  protected Bitmap background;
  protected int columnCount = 1;
  protected Bitmap detailFocusedBackground = null;
  protected int detailFocusedTextColor = -16777216;
  protected int detailFocusedTextSize = 15;
  protected int detailFocusedTypeFaceStyle = 0;
  protected Bitmap detailNormalBackground = null;
  protected int detailNormalTextColor = -16777216;
  protected int detailNormalTextSize = 15;
  protected int detailNormalTypeFaceStyle = 0;
  protected Bitmap detailSelectedBackground = null;
  protected int detailSelectedTextColor = -16777216;
  protected int detailSelectedTextSize = 15;
  protected int detailSelectedTypeFaceStyle = 0;
  protected int height;
  protected Bitmap horizontalDivider;
  protected boolean horizontalScrollBarEnabled = false;
  protected NinePatchDrawable horizontalScrollBarImage;
  protected boolean horizontalScrollIndicatorEnabled = false;
  protected Bitmap indicatorBottom;
  protected Bitmap indicatorLeft;
  protected Bitmap indicatorRight;
  protected Bitmap indicatorTop;
  protected boolean isMultiSelect = false;
  protected OnItemDrawingListener itemDrawingListener;
  protected Bitmap itemFocusedBackground = null;
  protected int itemFocusedTextColor = -16777216;
  protected int itemFocusedTextSize = 20;
  protected int itemFocusedTypeFaceStyle = 0;
  protected int itemHeight = 60;
  protected int itemMarginBottom = 0;
  protected int itemMarginLeft = 0;
  protected int itemMarginRight = 0;
  protected int itemMarginTop = 0;
  protected int itemMaxTextLength;
  protected Bitmap itemNormalBackground = null;
  protected int itemNormalTextColor = -16777216;
  protected int itemNormalTextSize = 20;
  protected int itemNormalTypeFaceStyle = 0;
  protected ItemOrientation itemOrientation = DEFAULT_ITEM_ORIENTATION;
  protected Bitmap itemSelectedBackground = null;
  protected int itemSelectedTextColor = -16777216;
  protected int itemSelectedTextSize = 20;
  protected int itemSelectedTypeFaceStyle = 0;
  protected ItemType itemType = DEFAULT_ITEM_TYPE;
  protected int itemWidth = 150;
  protected boolean loopFocusEnabled;
  protected int marginBottom = 0;
  protected int marginLeft = 0;
  protected int marginRight = 0;
  protected int marginTop = 0;
  protected int rowCount = 1;
  protected ScrollType scrollType = DEFAULT_SCROLL_TYPE;
  protected Paint.Align textAlign = Paint.Align.CENTER;
  protected int textPadding;
  protected Bitmap verticalDivider;
  protected boolean verticalScrollBarEnabled = false;
  protected NinePatchDrawable verticalScrollBarImage;
  protected boolean verticalScrollIndicatorEnabled = false;
  protected int width;

  private void fillItemRectWithMargin(Rect paramRect)
  {
    paramRect.left += this.itemMarginLeft;
    paramRect.right -= this.itemMarginRight;
    paramRect.top += this.itemMarginTop;
    paramRect.bottom -= this.itemMarginBottom;
  }

  public Bitmap getBackground()
  {
    return this.background;
  }

  public int getColumnCount()
  {
    return this.columnCount;
  }

  public Bitmap getDetailFocusedBackground()
  {
    return this.detailFocusedBackground;
  }

  public int getDetailFocusedTextColor()
  {
    return this.detailFocusedTextColor;
  }

  public int getDetailFocusedTextSize()
  {
    return this.detailFocusedTextSize;
  }

  public int getDetailFocusedTypeFaceStyle()
  {
    return this.detailFocusedTypeFaceStyle;
  }

  public Bitmap getDetailNormalBackground()
  {
    return this.detailNormalBackground;
  }

  public int getDetailNormalTextColor()
  {
    return this.detailNormalTextColor;
  }

  public int getDetailNormalTextSize()
  {
    return this.detailNormalTextSize;
  }

  public int getDetailNormalTypeFaceStyle()
  {
    return this.detailNormalTypeFaceStyle;
  }

  public Bitmap getDetailSelectedBackground()
  {
    return this.detailSelectedBackground;
  }

  public int getDetailSelectedTextColor()
  {
    return this.detailSelectedTextColor;
  }

  public int getDetailSelectedTextSize()
  {
    return this.detailSelectedTextSize;
  }

  public int getDetailSelectedTypeFaceStyle()
  {
    return this.detailSelectedTypeFaceStyle;
  }

  public int getHeight()
  {
    return this.height;
  }

  public Bitmap getHorizontalDivider()
  {
    return this.horizontalDivider;
  }

  public NinePatchDrawable getHorizontalScrollBarImage()
  {
    return this.horizontalScrollBarImage;
  }

  public Bitmap getIndicatorBottom()
  {
    return this.indicatorBottom;
  }

  public Bitmap getIndicatorLeft()
  {
    return this.indicatorLeft;
  }

  public Bitmap getIndicatorRight()
  {
    return this.indicatorRight;
  }

  public Bitmap getIndicatorTop()
  {
    return this.indicatorTop;
  }

  public int getItemColumnPosition(int paramInt)
  {
    if (this.itemOrientation == ItemOrientation.VERTICAL)
      return paramInt / this.rowCount;
    return paramInt % this.columnCount;
  }

  public OnItemDrawingListener getItemDrawingListener()
  {
    return this.itemDrawingListener;
  }

  public Bitmap getItemFocusedBackground()
  {
    return this.itemFocusedBackground;
  }

  public int getItemFocusedTextColor()
  {
    return this.itemFocusedTextColor;
  }

  public int getItemFocusedTextSize()
  {
    return this.itemFocusedTextSize;
  }

  public int getItemFocusedTypeFaceStyle()
  {
    return this.itemFocusedTypeFaceStyle;
  }

  public int getItemHeight()
  {
    return this.itemHeight;
  }

  public int getItemMarginBottom()
  {
    return this.itemMarginBottom;
  }

  public int getItemMarginLeft()
  {
    return this.itemMarginLeft;
  }

  public int getItemMarginRight()
  {
    return this.itemMarginRight;
  }

  public int getItemMarginTop()
  {
    return this.itemMarginTop;
  }

  public Bitmap getItemNormalBackground()
  {
    return this.itemNormalBackground;
  }

  public int getItemNormalTextColor()
  {
    return this.itemNormalTextColor;
  }

  public int getItemNormalTextSize()
  {
    return this.itemNormalTextSize;
  }

  public int getItemNormalTypeFaceStyle()
  {
    return this.itemNormalTypeFaceStyle;
  }

  public ItemOrientation getItemOrientation()
  {
    return this.itemOrientation;
  }

  public Rect getItemRectByPosition(Rect paramRect, int paramInt)
  {
    Rect localRect = getItemRectByPositionNoMargin(paramRect, paramInt);
    fillItemRectWithMargin(localRect);
    return localRect;
  }

  public Rect getItemRectByPositionNoMargin(Rect paramRect, int paramInt)
  {
    if (this.itemType == ItemType.SCROLL)
      return new Rect(paramRect.left, paramRect.top, this.itemWidth, this.itemHeight);
    int i;
    if (this.itemOrientation == ItemOrientation.VERTICAL)
      i = paramInt % this.rowCount;
    for (int j = paramInt / this.rowCount; ; j = paramInt % this.columnCount)
    {
      int k = paramRect.left + j * this.itemWidth;
      int m = paramRect.top + i * this.itemHeight;
      return new Rect(k, m, k + this.itemWidth, m + this.itemHeight);
      i = paramInt / this.columnCount;
    }
  }

  public int getItemRowPosition(int paramInt)
  {
    if (this.itemOrientation == ItemOrientation.VERTICAL)
      return paramInt % this.rowCount;
    return paramInt / this.columnCount;
  }

  public Bitmap getItemSelectedBackground()
  {
    return this.itemSelectedBackground;
  }

  public int getItemSelectedTextColor()
  {
    return this.itemSelectedTextColor;
  }

  public int getItemSelectedTextSize()
  {
    return this.itemSelectedTextSize;
  }

  public int getItemSelectedTypeFaceStyle()
  {
    return this.itemSelectedTypeFaceStyle;
  }

  public ItemType getItemType()
  {
    return this.itemType;
  }

  public int getItemWidth()
  {
    return this.itemWidth;
  }

  public int getMarginBottom()
  {
    return this.marginBottom;
  }

  public int getMarginLeft()
  {
    return this.marginLeft;
  }

  public int getMarginRight()
  {
    return this.marginRight;
  }

  public int getMarginTop()
  {
    return this.marginTop;
  }

  public int getRowCount()
  {
    return this.rowCount;
  }

  public ScrollType getScrollType()
  {
    return this.scrollType;
  }

  public Paint.Align getTextAlign()
  {
    return this.textAlign;
  }

  public int getTextPadding()
  {
    return this.textPadding;
  }

  public Bitmap getVerticalDivider()
  {
    return this.verticalDivider;
  }

  public NinePatchDrawable getVerticalScrollBarImage()
  {
    return this.verticalScrollBarImage;
  }

  public int getWidth()
  {
    return this.width;
  }

  public boolean isHorizontalScrollBarEnabled()
  {
    return this.horizontalScrollBarEnabled;
  }

  public boolean isHorizontalScrollIndicatorEnabled()
  {
    return this.horizontalScrollIndicatorEnabled;
  }

  public boolean isLoopFocusEnabled()
  {
    return this.loopFocusEnabled;
  }

  public boolean isMultiSelect()
  {
    return this.isMultiSelect;
  }

  public boolean isVerticalScrollBarEnabled()
  {
    return this.verticalScrollBarEnabled;
  }

  public boolean isVerticalScrollIndicatorEnabled()
  {
    return this.verticalScrollIndicatorEnabled;
  }

  public void setBackground(Bitmap paramBitmap)
  {
    this.background = paramBitmap;
  }

  public void setColumnCount(int paramInt)
  {
    this.columnCount = paramInt;
  }

  public void setDetailBackgroundImage(Bitmap paramBitmap)
  {
    this.detailSelectedBackground = paramBitmap;
    this.detailFocusedBackground = paramBitmap;
    this.detailNormalBackground = paramBitmap;
  }

  public void setDetailFocusedBackground(Bitmap paramBitmap)
  {
    this.detailFocusedBackground = paramBitmap;
  }

  public void setDetailFocusedTextColor(int paramInt)
  {
    this.detailFocusedTextColor = paramInt;
  }

  public void setDetailFocusedTextSize(int paramInt)
  {
    this.detailFocusedTextSize = paramInt;
  }

  public void setDetailFocusedTypeFaceStyle(int paramInt)
  {
    this.detailFocusedTypeFaceStyle = paramInt;
  }

  public void setDetailNormalBackground(Bitmap paramBitmap)
  {
    this.detailNormalBackground = paramBitmap;
  }

  public void setDetailNormalTextColor(int paramInt)
  {
    this.detailNormalTextColor = paramInt;
  }

  public void setDetailNormalTextSize(int paramInt)
  {
    this.detailNormalTextSize = paramInt;
  }

  public void setDetailNormalTypeFaceStyle(int paramInt)
  {
    this.detailNormalTypeFaceStyle = paramInt;
  }

  public void setDetailSelectedBackground(Bitmap paramBitmap)
  {
    this.detailSelectedBackground = paramBitmap;
  }

  public void setDetailSelectedTextColor(int paramInt)
  {
    this.detailSelectedTextColor = paramInt;
  }

  public void setDetailSelectedTextSize(int paramInt)
  {
    this.detailSelectedTextSize = paramInt;
  }

  public void setDetailSelectedTypeFaceStyle(int paramInt)
  {
    this.detailSelectedTypeFaceStyle = paramInt;
  }

  public void setDetailTextColor(int paramInt)
  {
    this.detailSelectedTextColor = paramInt;
    this.detailFocusedTextColor = paramInt;
    this.detailNormalTextColor = paramInt;
  }

  public void setDetailTextSize(int paramInt)
  {
    this.detailSelectedTextSize = paramInt;
    this.detailFocusedTextSize = paramInt;
    this.detailNormalTextSize = paramInt;
  }

  public void setDetailTypefaceStyle(int paramInt)
  {
    this.detailSelectedTypeFaceStyle = paramInt;
    this.detailFocusedTypeFaceStyle = paramInt;
    this.detailNormalTypeFaceStyle = paramInt;
  }

  public void setHeight(int paramInt)
  {
    this.height = paramInt;
  }

  public void setHorizontalDivider(Bitmap paramBitmap)
  {
    this.horizontalDivider = paramBitmap;
  }

  public void setHorizontalScrollBarEnabled(boolean paramBoolean)
  {
    this.horizontalScrollBarEnabled = paramBoolean;
  }

  public void setHorizontalScrollBarImage(NinePatchDrawable paramNinePatchDrawable)
  {
    this.horizontalScrollBarImage = paramNinePatchDrawable;
  }

  public void setHorizontalScrollIndicatorEnabled(boolean paramBoolean)
  {
    this.horizontalScrollIndicatorEnabled = paramBoolean;
  }

  public void setIndicatorBottom(Bitmap paramBitmap)
  {
    this.indicatorBottom = paramBitmap;
  }

  public void setIndicatorLeft(Bitmap paramBitmap)
  {
    this.indicatorLeft = paramBitmap;
  }

  public void setIndicatorRight(Bitmap paramBitmap)
  {
    this.indicatorRight = paramBitmap;
  }

  public void setIndicatorTop(Bitmap paramBitmap)
  {
    this.indicatorTop = paramBitmap;
  }

  public void setItemDrawingListener(OnItemDrawingListener paramOnItemDrawingListener)
  {
    this.itemDrawingListener = paramOnItemDrawingListener;
  }

  public void setItemFocusedBackground(Bitmap paramBitmap)
  {
    this.itemFocusedBackground = paramBitmap;
  }

  public void setItemFocusedTextColor(int paramInt)
  {
    this.itemFocusedTextColor = paramInt;
  }

  public void setItemFocusedTextSize(int paramInt)
  {
    this.itemFocusedTextSize = paramInt;
  }

  public void setItemFocusedTypeFaceStyle(int paramInt)
  {
    this.itemFocusedTypeFaceStyle = paramInt;
  }

  public void setItemHeight(int paramInt)
  {
    this.itemHeight = paramInt;
  }

  public void setItemMarginBottom(int paramInt)
  {
    this.itemMarginBottom = paramInt;
  }

  public void setItemMarginLeft(int paramInt)
  {
    this.itemMarginLeft = paramInt;
  }

  public void setItemMarginRight(int paramInt)
  {
    this.itemMarginRight = paramInt;
  }

  public void setItemMarginTop(int paramInt)
  {
    this.itemMarginTop = paramInt;
  }

  public void setItemNormalBackground(Bitmap paramBitmap)
  {
    this.itemNormalBackground = paramBitmap;
  }

  public void setItemNormalTextColor(int paramInt)
  {
    this.itemNormalTextColor = paramInt;
  }

  public void setItemNormalTextSize(int paramInt)
  {
    this.itemNormalTextSize = paramInt;
  }

  public void setItemNormalTypeFaceStyle(int paramInt)
  {
    this.itemNormalTypeFaceStyle = paramInt;
  }

  public void setItemOrientation(ItemOrientation paramItemOrientation)
  {
    this.itemOrientation = paramItemOrientation;
  }

  public void setItemSelectedBackground(Bitmap paramBitmap)
  {
    this.itemSelectedBackground = paramBitmap;
  }

  public void setItemSelectedTextColor(int paramInt)
  {
    this.itemSelectedTextColor = paramInt;
  }

  public void setItemSelectedTextSize(int paramInt)
  {
    this.itemSelectedTextSize = paramInt;
  }

  public void setItemSelectedTypeFaceStyle(int paramInt)
  {
    this.itemSelectedTypeFaceStyle = paramInt;
  }

  public void setItemTextColor(int paramInt)
  {
    this.itemSelectedTextColor = paramInt;
    this.itemFocusedTextColor = paramInt;
    this.itemNormalTextColor = paramInt;
  }

  public void setItemTextSize(int paramInt)
  {
    this.itemSelectedTextSize = paramInt;
    this.itemFocusedTextSize = paramInt;
    this.itemNormalTextSize = paramInt;
  }

  public void setItemType(ItemType paramItemType)
  {
    this.itemType = paramItemType;
  }

  public void setItemTypeFaceStyle(int paramInt)
  {
    this.itemSelectedTypeFaceStyle = paramInt;
    this.itemFocusedTypeFaceStyle = paramInt;
    this.itemNormalTypeFaceStyle = paramInt;
  }

  public void setItemWidth(int paramInt)
  {
    this.itemWidth = paramInt;
  }

  public void setLoopFocusEnabled(boolean paramBoolean)
  {
    this.loopFocusEnabled = paramBoolean;
  }

  public void setMarginBottom(int paramInt)
  {
    this.marginBottom = paramInt;
  }

  public void setMarginLeft(int paramInt)
  {
    this.marginLeft = paramInt;
  }

  public void setMarginRight(int paramInt)
  {
    this.marginRight = paramInt;
  }

  public void setMarginTop(int paramInt)
  {
    this.marginTop = paramInt;
  }

  public void setMultiSelect(boolean paramBoolean)
  {
    this.isMultiSelect = paramBoolean;
  }

  public void setRowCount(int paramInt)
  {
    this.rowCount = paramInt;
  }

  public void setScrollType(ScrollType paramScrollType)
  {
    this.scrollType = paramScrollType;
  }

  public void setTextAlign(Paint.Align paramAlign)
  {
    this.textAlign = paramAlign;
  }

  public void setTextPadding(int paramInt)
  {
    this.textPadding = paramInt;
  }

  public void setVerticalDivider(Bitmap paramBitmap)
  {
    this.verticalDivider = paramBitmap;
  }

  public void setVerticalScrollBarEnabled(boolean paramBoolean)
  {
    this.verticalScrollBarEnabled = paramBoolean;
  }

  public void setVerticalScrollBarImage(NinePatchDrawable paramNinePatchDrawable)
  {
    this.verticalScrollBarImage = paramNinePatchDrawable;
  }

  public void setVerticalScrollIndicatorEnabled(boolean paramBoolean)
  {
    this.verticalScrollIndicatorEnabled = paramBoolean;
  }

  public void setWidth(int paramInt)
  {
    this.width = paramInt;
  }

  public static class BitmapHolder
  {
    Bitmap value;

    public BitmapHolder(Bitmap paramBitmap)
    {
      this.value = paramBitmap;
    }

    public Bitmap getValue()
    {
      return this.value;
    }

    public void setValue(Bitmap paramBitmap)
    {
      this.value = paramBitmap;
    }
  }

  public static class BooleanHolder
  {
    boolean value;

    public BooleanHolder(boolean paramBoolean)
    {
      this.value = paramBoolean;
    }

    public boolean getValue()
    {
      return this.value;
    }

    public void setValue(boolean paramBoolean)
    {
      this.value = paramBoolean;
    }
  }

  public static class ItemDrawInfo
  {
    private Properties.BitmapHolder backgroundImageHolder;
    private Rect backgroundRect;
    private Object data;
    private Properties.BitmapHolder detailBackgroundImageHolder;
    private Rect detailRect;
    private SpannableStringBuilder detailString;
    private Paint.Align detailTextAlign;
    private int detailTextColor;
    private int detailTextSize;
    private Properties.BooleanHolder forceMarqueeHolder;
    private Properties.BitmapHolder itemPicHolder;
    private Rect itemRect;
    private Rect picRect;
    private Properties.BooleanHolder picVisibleHolder;
    private Rect titleRect;
    private SpannableStringBuilder titleString;
    private Paint.Align titleTextAlign;
    private int titleTextColor;
    private int titleTextSize;

    public Properties.BitmapHolder getBackgroundImageHolder()
    {
      return this.backgroundImageHolder;
    }

    public Rect getBackgroundRect()
    {
      return this.backgroundRect;
    }

    public Object getData()
    {
      return this.data;
    }

    public Properties.BitmapHolder getDetailBackgroundImageHolder()
    {
      return this.detailBackgroundImageHolder;
    }

    public Rect getDetailRect()
    {
      return this.detailRect;
    }

    public SpannableStringBuilder getDetailString()
    {
      return this.detailString;
    }

    public Paint.Align getDetailTextAlign()
    {
      return this.detailTextAlign;
    }

    public int getDetailTextColor()
    {
      return this.detailTextColor;
    }

    public int getDetailTextSize()
    {
      return this.detailTextSize;
    }

    public Properties.BooleanHolder getForceMarqueeHolder()
    {
      return this.forceMarqueeHolder;
    }

    public Properties.BitmapHolder getItemPicHolder()
    {
      return this.itemPicHolder;
    }

    public Rect getItemRect()
    {
      return this.itemRect;
    }

    public Rect getPicRect()
    {
      return this.picRect;
    }

    public Properties.BooleanHolder getPicVisibleHolder()
    {
      return this.picVisibleHolder;
    }

    public Rect getTitleRect()
    {
      return this.titleRect;
    }

    public SpannableStringBuilder getTitleString()
    {
      return this.titleString;
    }

    public Paint.Align getTitleTextAlign()
    {
      return this.titleTextAlign;
    }

    public int getTitleTextColor()
    {
      return this.titleTextColor;
    }

    public int getTitleTextSize()
    {
      return this.titleTextSize;
    }

    public void setBackgroundImageHolder(Properties.BitmapHolder paramBitmapHolder)
    {
      this.backgroundImageHolder = paramBitmapHolder;
    }

    public void setBackgroundRect(Rect paramRect)
    {
      this.backgroundRect = paramRect;
    }

    public void setData(Object paramObject)
    {
      this.data = paramObject;
    }

    public void setDetailBackgroundImageHolder(Properties.BitmapHolder paramBitmapHolder)
    {
      this.detailBackgroundImageHolder = paramBitmapHolder;
    }

    public void setDetailRect(Rect paramRect)
    {
      this.detailRect = paramRect;
    }

    public void setDetailString(SpannableStringBuilder paramSpannableStringBuilder)
    {
      this.detailString = paramSpannableStringBuilder;
    }

    public void setDetailTextAlign(Paint.Align paramAlign)
    {
      this.detailTextAlign = paramAlign;
    }

    public void setDetailTextColor(int paramInt)
    {
      this.detailTextColor = paramInt;
    }

    public void setDetailTextSize(int paramInt)
    {
      this.detailTextSize = paramInt;
    }

    public void setForceMarqueeHolder(Properties.BooleanHolder paramBooleanHolder)
    {
      this.forceMarqueeHolder = paramBooleanHolder;
    }

    public void setItemPicHolder(Properties.BitmapHolder paramBitmapHolder)
    {
      this.itemPicHolder = paramBitmapHolder;
    }

    public void setItemRect(Rect paramRect)
    {
      this.itemRect = paramRect;
    }

    public void setPicRect(Rect paramRect)
    {
      this.picRect = paramRect;
    }

    public void setPicVisibleHolder(Properties.BooleanHolder paramBooleanHolder)
    {
      this.picVisibleHolder = paramBooleanHolder;
    }

    public void setTitleRect(Rect paramRect)
    {
      this.titleRect = paramRect;
    }

    public void setTitleString(SpannableStringBuilder paramSpannableStringBuilder)
    {
      this.titleString = paramSpannableStringBuilder;
    }

    public void setTitleTextAlign(Paint.Align paramAlign)
    {
      this.titleTextAlign = paramAlign;
    }

    public void setTitleTextColor(int paramInt)
    {
      this.titleTextColor = paramInt;
    }

    public void setTitleTextSize(int paramInt)
    {
      this.titleTextSize = paramInt;
    }
  }

  public static enum ItemOrientation
  {
    static
    {
      ItemOrientation[] arrayOfItemOrientation = new ItemOrientation[2];
      arrayOfItemOrientation[0] = HORIZONTAL;
      arrayOfItemOrientation[1] = VERTICAL;
    }
  }

  public static enum ItemType
  {
    static
    {
      TITLE_WITH_LEFT_PIC_AND_DETAIL_ON_LEFT = new ItemType("TITLE_WITH_LEFT_PIC_AND_DETAIL_ON_LEFT", 5);
      TITLE_WITH_LEFT_PIC_AND_DETAIL_ON_BOTTOM = new ItemType("TITLE_WITH_LEFT_PIC_AND_DETAIL_ON_BOTTOM", 6);
      SCROLL = new ItemType("SCROLL", 7);
      CUSTOM = new ItemType("CUSTOM", 8);
      ItemType[] arrayOfItemType = new ItemType[9];
      arrayOfItemType[0] = TITLE;
      arrayOfItemType[1] = TITLE_WITH_DETAIL_ON_BOTTOM;
      arrayOfItemType[2] = TITLE_WITH_DETAIL_ON_LEFT;
      arrayOfItemType[3] = TITLE_WITH_LEFT_PIC;
      arrayOfItemType[4] = TITLE_WITH_TOP_PIC;
      arrayOfItemType[5] = TITLE_WITH_LEFT_PIC_AND_DETAIL_ON_LEFT;
      arrayOfItemType[6] = TITLE_WITH_LEFT_PIC_AND_DETAIL_ON_BOTTOM;
      arrayOfItemType[7] = SCROLL;
      arrayOfItemType[8] = CUSTOM;
    }
  }

  public static abstract interface OnItemDrawingListener
  {
    public abstract void onItemDraw(Rect paramRect1, Rect paramRect2, Rect paramRect3, Rect paramRect4, Rect paramRect5, Paint.Align paramAlign, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Properties.BitmapHolder paramBitmapHolder, Bitmap paramBitmap, SpannableStringBuilder paramSpannableStringBuilder1, SpannableStringBuilder paramSpannableStringBuilder2, int paramInt5, Properties.BooleanHolder paramBooleanHolder1, Properties.BooleanHolder paramBooleanHolder2, Object paramObject);
  }

  public static enum ScrollType
  {
    static
    {
      ScrollType[] arrayOfScrollType = new ScrollType[2];
      arrayOfScrollType[0] = ITEM;
      arrayOfScrollType[1] = PAGE;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.gridview.Properties
 * JD-Core Version:    0.6.2
 */