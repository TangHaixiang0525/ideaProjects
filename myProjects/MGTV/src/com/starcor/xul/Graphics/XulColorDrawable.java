package com.starcor.xul.Graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.shapes.RoundRectShape;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulUtils;

public class XulColorDrawable extends XulDrawable
{
  int _color;
  int _height;
  float[] _radius = null;
  RoundRectShape _roundRectShape;
  int _width;

  public XulColorDrawable(int paramInt1, int paramInt2, int paramInt3)
  {
    this(paramInt1, paramInt2, paramInt3, 0.0F, 0.0F);
  }

  public XulColorDrawable(int paramInt1, int paramInt2, int paramInt3, float paramFloat1, float paramFloat2)
  {
  }

  public XulColorDrawable(int paramInt1, int paramInt2, int paramInt3, float[] paramArrayOfFloat)
  {
    this._color = paramInt1;
    this._width = paramInt2;
    this._height = paramInt3;
    this._radius = paramArrayOfFloat;
    if (this._radius == null);
    while (this._radius.length != 8)
      return;
    this._roundRectShape = new RoundRectShape(this._radius, null, null);
  }

  private int calDrawingColor(Paint paramPaint)
  {
    int i = Color.alpha(this._color);
    if (i == 0);
    int j;
    do
    {
      return 0;
      j = i * paramPaint.getAlpha();
    }
    while (j == 0);
    return Color.argb(j / 255, Color.red(this._color), Color.green(this._color), Color.blue(this._color));
  }

  private boolean drawRect(Canvas paramCanvas, Rect paramRect, Paint paramPaint)
  {
    int i = calDrawingColor(paramPaint);
    if (i == 0)
      return true;
    XulUtils.saveCanvas(paramCanvas);
    paramCanvas.clipRect(paramRect);
    paramCanvas.drawColor(i);
    XulUtils.restoreCanvas(paramCanvas);
    return true;
  }

  private boolean drawRect(Canvas paramCanvas, RectF paramRectF, Paint paramPaint)
  {
    int i = calDrawingColor(paramPaint);
    if (i == 0)
      return true;
    XulUtils.saveCanvas(paramCanvas);
    paramCanvas.clipRect(paramRectF);
    paramCanvas.drawColor(i);
    XulUtils.restoreCanvas(paramCanvas);
    return true;
  }

  private boolean drawRoundRect(Canvas paramCanvas, Rect paramRect, Paint paramPaint)
  {
    RectF localRectF = XulDC._tmpFRc1;
    localRectF.set(paramRect);
    return drawRoundRect(paramCanvas, localRectF, paramPaint);
  }

  private boolean drawRoundRect(Canvas paramCanvas, RectF paramRectF, Paint paramPaint)
  {
    int i = calDrawingColor(paramPaint);
    if (i == 0);
    Paint localPaint;
    do
    {
      return true;
      localPaint = XulRenderContext.getDefSolidPaint();
      localPaint.setColor(i);
      if (this._radius.length == 2)
      {
        paramCanvas.drawRoundRect(paramRectF, this._radius[0], this._radius[1], localPaint);
        return true;
      }
    }
    while (this._roundRectShape == null);
    XulUtils.saveCanvas(paramCanvas);
    paramCanvas.translate(paramRectF.left, paramRectF.top);
    this._roundRectShape.resize(paramRectF.width(), paramRectF.height());
    this._roundRectShape.draw(paramCanvas, localPaint);
    XulUtils.restoreCanvas(paramCanvas);
    return true;
  }

  public boolean draw(Canvas paramCanvas, Rect paramRect1, Rect paramRect2, Paint paramPaint)
  {
    if (this._radius != null)
      return drawRoundRect(paramCanvas, paramRect2, paramPaint);
    return drawRect(paramCanvas, paramRect2, paramPaint);
  }

  public boolean draw(Canvas paramCanvas, Rect paramRect, RectF paramRectF, Paint paramPaint)
  {
    if (this._radius != null)
      return drawRoundRect(paramCanvas, paramRectF, paramPaint);
    return drawRect(paramCanvas, paramRectF, paramPaint);
  }

  public int getHeight()
  {
    return this._width;
  }

  public int getWidth()
  {
    return this._height;
  }

  public void recycle()
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Graphics.XulColorDrawable
 * JD-Core Version:    0.6.2
 */