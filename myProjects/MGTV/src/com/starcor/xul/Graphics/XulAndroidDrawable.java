package com.starcor.xul.Graphics;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

class XulAndroidDrawable extends XulDrawable
{
  private final Drawable drawable;

  public XulAndroidDrawable(Drawable paramDrawable)
  {
    this.drawable = paramDrawable;
  }

  public boolean draw(Canvas paramCanvas, Rect paramRect1, Rect paramRect2, Paint paramPaint)
  {
    this.drawable.setBounds(paramRect2);
    this.drawable.draw(paramCanvas);
    return true;
  }

  public boolean draw(Canvas paramCanvas, Rect paramRect, RectF paramRectF, Paint paramPaint)
  {
    this.drawable.setBounds((int)paramRectF.left, (int)paramRectF.top, (int)paramRectF.right, (int)paramRectF.bottom);
    this.drawable.draw(paramCanvas);
    return false;
  }

  public int getHeight()
  {
    int i = this.drawable.getIntrinsicHeight();
    if (i <= 0)
      i = this.drawable.getMinimumHeight();
    return i;
  }

  public int getWidth()
  {
    int i = this.drawable.getIntrinsicWidth();
    if (i <= 0)
      i = this.drawable.getMinimumWidth();
    return i;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Graphics.XulAndroidDrawable
 * JD-Core Version:    0.6.2
 */