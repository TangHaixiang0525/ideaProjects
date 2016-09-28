package com.starcor.xul.Graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class XulBitmapDrawable extends XulDrawable
{
  Bitmap _bmp;

  public static Bitmap detachBitmap(XulBitmapDrawable paramXulBitmapDrawable)
  {
    Bitmap localBitmap = paramXulBitmapDrawable._bmp;
    paramXulBitmapDrawable._bmp = null;
    return localBitmap;
  }

  public boolean draw(Canvas paramCanvas, Rect paramRect1, Rect paramRect2, Paint paramPaint)
  {
    Bitmap localBitmap = this._bmp;
    if (localBitmap == null)
      return false;
    paramCanvas.drawBitmap(localBitmap, paramRect1, paramRect2, paramPaint);
    return true;
  }

  public boolean draw(Canvas paramCanvas, Rect paramRect, RectF paramRectF, Paint paramPaint)
  {
    Bitmap localBitmap = this._bmp;
    if (localBitmap == null)
      return false;
    paramCanvas.drawBitmap(localBitmap, paramRect, paramRectF, paramPaint);
    return true;
  }

  public int getHeight()
  {
    Bitmap localBitmap = this._bmp;
    if (localBitmap == null)
      return 0;
    return localBitmap.getHeight();
  }

  public int getWidth()
  {
    Bitmap localBitmap = this._bmp;
    if (localBitmap == null)
      return 0;
    return localBitmap.getWidth();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Graphics.XulBitmapDrawable
 * JD-Core Version:    0.6.2
 */