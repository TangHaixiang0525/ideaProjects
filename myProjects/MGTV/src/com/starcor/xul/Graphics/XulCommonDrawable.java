package com.starcor.xul.Graphics;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import com.starcor.xul.XulUtils;

public class XulCommonDrawable extends XulDrawable
{
  Drawable _drawable;

  public boolean draw(Canvas paramCanvas, Rect paramRect1, Rect paramRect2, Paint paramPaint)
  {
    Drawable localDrawable = this._drawable;
    if (localDrawable == null)
      return false;
    localDrawable.setBounds(paramRect2);
    localDrawable.draw(paramCanvas);
    return true;
  }

  public boolean draw(Canvas paramCanvas, Rect paramRect, RectF paramRectF, Paint paramPaint)
  {
    Drawable localDrawable = this._drawable;
    if (localDrawable == null)
      return false;
    localDrawable.setBounds(XulUtils.roundToInt(paramRectF.left), XulUtils.roundToInt(paramRectF.top), XulUtils.roundToInt(paramRectF.right), XulUtils.roundToInt(paramRectF.bottom));
    localDrawable.draw(paramCanvas);
    return true;
  }

  public int getHeight()
  {
    Drawable localDrawable = this._drawable;
    if (localDrawable == null)
      return 0;
    return localDrawable.getMinimumHeight();
  }

  public int getWidth()
  {
    Drawable localDrawable = this._drawable;
    if (localDrawable == null)
      return 0;
    return localDrawable.getMinimumWidth();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Graphics.XulCommonDrawable
 * JD-Core Version:    0.6.2
 */