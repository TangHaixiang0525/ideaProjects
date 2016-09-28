package com.starcor.hunan.widget.gridview;

import android.graphics.Bitmap;

public class ItemData
{
  private Object data;
  private String detail;
  private boolean focused = false;
  private int pageIndex;
  private Bitmap picture;
  private boolean selected = false;
  private String title;

  public Object getData()
  {
    return this.data;
  }

  public String getDetail()
  {
    return this.detail;
  }

  public int getPageIndex()
  {
    return this.pageIndex;
  }

  public Bitmap getPicture()
  {
    return this.picture;
  }

  public String getTitle()
  {
    return this.title;
  }

  public boolean isFocused()
  {
    return this.focused;
  }

  public boolean isSelected()
  {
    return this.selected;
  }

  public void setData(Object paramObject)
  {
    this.data = paramObject;
  }

  public void setDetail(String paramString)
  {
    this.detail = paramString;
  }

  public void setFocused(boolean paramBoolean)
  {
    this.focused = paramBoolean;
  }

  public void setPageIndex(int paramInt)
  {
    this.pageIndex = paramInt;
  }

  public void setPicture(Bitmap paramBitmap)
  {
    this.picture = paramBitmap;
  }

  public void setSelected(boolean paramBoolean)
  {
    this.selected = paramBoolean;
  }

  public void setTitle(String paramString)
  {
    this.title = paramString;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.widget.gridview.ItemData
 * JD-Core Version:    0.6.2
 */