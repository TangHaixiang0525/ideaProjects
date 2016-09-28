package com.starcor.xul.Render.Components;

import android.graphics.Rect;
import android.graphics.RectF;
import com.starcor.xul.Graphics.IXulDrawable;
import com.starcor.xul.Graphics.XulDC;

public abstract class BaseScrollBar
  implements IXulDrawable
{
  private final ScrollBarHelper _helper;

  public BaseScrollBar(ScrollBarHelper paramScrollBarHelper)
  {
    this._helper = paramScrollBarHelper;
  }

  public abstract boolean draw(XulDC paramXulDC, Rect paramRect, int paramInt1, int paramInt2);

  public abstract boolean draw(XulDC paramXulDC, RectF paramRectF, float paramFloat1, float paramFloat2);

  public int getContentHeight()
  {
    return this._helper.getContentHeight();
  }

  public int getContentWidth()
  {
    return this._helper.getContentWidth();
  }

  public int getScrollPos()
  {
    return this._helper.getScrollPos();
  }

  public boolean isVertical()
  {
    return this._helper.isVertical();
  }

  public void recycle()
  {
  }

  public abstract void reset();

  public static abstract class ScrollBarHelper
  {
    public abstract int getContentHeight();

    public abstract int getContentWidth();

    public abstract int getScrollPos();

    public abstract boolean isVertical();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.Components.BaseScrollBar
 * JD-Core Version:    0.6.2
 */