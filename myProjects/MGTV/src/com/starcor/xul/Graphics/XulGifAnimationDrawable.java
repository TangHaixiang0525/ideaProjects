package com.starcor.xul.Graphics;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import com.starcor.xul.XulUtils;
import pl.droidsonroids.gif.GifDrawable;

public class XulGifAnimationDrawable extends XulAnimationDrawable
{
  XulAnimationDrawable.AnimationDrawingContext _ctx;
  GifDrawable _drawable;
  int _height;
  int _width;

  public static XulGifAnimationDrawable buildAnimation(GifDrawable paramGifDrawable, int paramInt1, int paramInt2)
  {
    XulGifAnimationDrawable localXulGifAnimationDrawable = new XulGifAnimationDrawable();
    localXulGifAnimationDrawable._drawable = paramGifDrawable;
    if (paramInt1 <= 0)
    {
      if (paramInt2 <= 0)
      {
        localXulGifAnimationDrawable._width = localXulGifAnimationDrawable._drawable.getIntrinsicWidth();
        localXulGifAnimationDrawable._height = localXulGifAnimationDrawable._drawable.getIntrinsicHeight();
        return localXulGifAnimationDrawable;
      }
      localXulGifAnimationDrawable._width = (paramInt2 * localXulGifAnimationDrawable._drawable.getIntrinsicWidth() / localXulGifAnimationDrawable._drawable.getIntrinsicHeight());
      localXulGifAnimationDrawable._height = paramInt2;
      return localXulGifAnimationDrawable;
    }
    if (paramInt2 <= 0)
    {
      localXulGifAnimationDrawable._width = paramInt1;
      localXulGifAnimationDrawable._height = (paramInt1 * localXulGifAnimationDrawable._drawable.getIntrinsicHeight() / localXulGifAnimationDrawable._drawable.getIntrinsicWidth());
      return localXulGifAnimationDrawable;
    }
    localXulGifAnimationDrawable._width = paramInt1;
    localXulGifAnimationDrawable._height = paramInt2;
    return localXulGifAnimationDrawable;
  }

  public XulAnimationDrawable.AnimationDrawingContext createDrawingCtx()
  {
    if (this._ctx != null)
      return this._ctx;
    this._ctx = new XulAnimationDrawable.AnimationDrawingContext()
    {
      int _lastFrameIndex = -1;

      public boolean isAnimationFinished()
      {
        return XulGifAnimationDrawable.this._drawable.isAnimationCompleted();
      }

      public void reset()
      {
        XulGifAnimationDrawable.this._drawable.reset();
      }

      public boolean updateAnimation(long paramAnonymousLong)
      {
        int i = XulGifAnimationDrawable.this._drawable.getCurrentFrameIndex();
        return this._lastFrameIndex != i;
      }
    };
    return this._ctx;
  }

  public boolean draw(Canvas paramCanvas, Rect paramRect1, Rect paramRect2, Paint paramPaint)
  {
    this._drawable.setBounds(paramRect2);
    this._drawable.setAlpha(paramPaint.getAlpha());
    this._drawable.draw(paramCanvas);
    return true;
  }

  public boolean draw(Canvas paramCanvas, Rect paramRect, RectF paramRectF, Paint paramPaint)
  {
    this._drawable.setBounds((int)paramRectF.left, (int)paramRectF.top, (int)paramRectF.right, (int)paramRectF.bottom);
    this._drawable.setAlpha(paramPaint.getAlpha());
    this._drawable.draw(paramCanvas);
    return true;
  }

  public boolean drawAnimation(XulAnimationDrawable.AnimationDrawingContext paramAnimationDrawingContext, XulDC paramXulDC, Rect paramRect, Paint paramPaint)
  {
    this._drawable.setBounds(paramRect);
    this._drawable.setAlpha(paramPaint.getAlpha());
    this._drawable.draw(paramXulDC.getCanvas());
    return false;
  }

  public boolean drawAnimation(XulAnimationDrawable.AnimationDrawingContext paramAnimationDrawingContext, XulDC paramXulDC, RectF paramRectF, Paint paramPaint)
  {
    this._drawable.setBounds(XulUtils.roundToInt(paramRectF.left), XulUtils.roundToInt(paramRectF.top), XulUtils.roundToInt(paramRectF.right), XulUtils.roundToInt(paramRectF.bottom));
    this._drawable.setAlpha(paramPaint.getAlpha());
    this._drawable.draw(paramXulDC.getCanvas());
    return false;
  }

  public int getHeight()
  {
    return this._height;
  }

  public int getWidth()
  {
    return this._width;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Graphics.XulGifAnimationDrawable
 * JD-Core Version:    0.6.2
 */