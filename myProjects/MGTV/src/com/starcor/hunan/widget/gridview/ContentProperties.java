package com.starcor.hunan.widget.gridview;

import android.graphics.Bitmap;

public class ContentProperties extends Properties
{
  private static final int DEFAULT_ITEMS_PER_PAGE;
  private Bitmap emptyImage;
  private int itemsPerPage = 0;
  private Bitmap vipImage;

  public Bitmap getEmptyImage()
  {
    return this.emptyImage;
  }

  public int getItemsPerPage()
  {
    return this.itemsPerPage;
  }

  public Bitmap getVipImage()
  {
    return this.vipImage;
  }

  public void setEmptyImage(Bitmap paramBitmap)
  {
    this.emptyImage = paramBitmap;
  }

  public void setItemsPerPage(int paramInt)
  {
    this.itemsPerPage = paramInt;
  }

  public void setVipImage(Bitmap paramBitmap)
  {
    this.vipImage = paramBitmap;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.gridview.ContentProperties
 * JD-Core Version:    0.6.2
 */