package com.starcor.xul.Graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import com.starcor.xul.XulManager;
import com.starcor.xul.XulUtils;

public class XulNinePatchDrawable extends XulDrawable
{
  Bitmap _bmp;
  Drawable _drawable;
  int _height;
  Rect _patchRect;
  int _width;

  public void attach(Bitmap paramBitmap)
  {
    byte[] arrayOfByte = paramBitmap.getNinePatchChunk();
    if (arrayOfByte != null)
    {
      this._drawable = new NinePatchDrawable(paramBitmap, arrayOfByte, null, this._key);
      this._width = this._drawable.getMinimumWidth();
      this._height = this._drawable.getMinimumHeight();
      return;
    }
    this._width = paramBitmap.getWidth();
    this._height = paramBitmap.getHeight();
    this._patchRect = new Rect();
    this._patchRect.left = this._width;
    this._patchRect.right = 0;
    this._patchRect.top = this._height;
    this._patchRect.bottom = 0;
    for (int i = 0; i < this._width; i++)
      if (paramBitmap.getPixel(i, 0) == -16777216)
      {
        if (this._patchRect.left > i)
          this._patchRect.left = i;
        if (this._patchRect.right < i)
          this._patchRect.right = i;
      }
    this._patchRect.right = (this._width - this._patchRect.right);
    for (int j = 0; j < this._height; j++)
      if (paramBitmap.getPixel(0, j) == -16777216)
      {
        if (this._patchRect.top > j)
          this._patchRect.top = j;
        if (this._patchRect.bottom < j)
          this._patchRect.bottom = j;
      }
    this._patchRect.bottom = (this._height - this._patchRect.bottom);
    Matrix localMatrix = new Matrix();
    float f = XulManager.getGlobalXScalar();
    localMatrix.setScale(f, XulManager.getGlobalYScalar());
    this._bmp = Bitmap.createBitmap(paramBitmap, 1, 1, -2 + this._width, -2 + this._height, localMatrix, true);
    XulUtils.offsetRect(this._patchRect, -1, -1);
    this._patchRect.left = XulUtils.roundToInt(f * this._patchRect.left);
    this._patchRect.top = XulUtils.roundToInt(f * this._patchRect.top);
    this._patchRect.right = XulUtils.roundToInt(f * this._patchRect.right);
    this._patchRect.bottom = XulUtils.roundToInt(f * this._patchRect.bottom);
    this._width = this._bmp.getWidth();
    this._height = this._bmp.getHeight();
  }

  public boolean draw(Canvas paramCanvas, Rect paramRect1, Rect paramRect2, Paint paramPaint)
  {
    Drawable localDrawable = this._drawable;
    if (localDrawable != null)
    {
      localDrawable.setBounds(paramRect2);
      localDrawable.draw(paramCanvas);
      return true;
    }
    Bitmap localBitmap = this._bmp;
    if (localBitmap != null)
    {
      Rect localRect1 = XulDC._tmpRc0;
      Rect localRect2 = XulDC._tmpRc1;
      int i = this._patchRect.left;
      int j = this._width - this._patchRect.right;
      int k = this._width;
      int m = this._patchRect.top;
      int n = this._height - this._patchRect.bottom;
      int i1 = this._height;
      int i2 = paramRect2.left;
      int i3 = paramRect2.left + this._patchRect.left;
      int i4 = paramRect2.right - this._patchRect.right;
      int i5 = paramRect2.right;
      int i6 = paramRect2.top;
      int i7 = paramRect2.top + this._patchRect.top;
      int i8 = paramRect2.bottom - this._patchRect.bottom;
      int i9 = paramRect2.bottom;
      localRect1.set(0, 0, i, m);
      localRect2.set(i2, i6, i3, i7);
      paramCanvas.drawBitmap(localBitmap, localRect1, localRect2, paramPaint);
      localRect1.set(i, 0, j, m);
      localRect2.set(i3, i6, i4, i7);
      paramCanvas.drawBitmap(localBitmap, localRect1, localRect2, paramPaint);
      localRect1.set(j, 0, k, m);
      localRect2.set(i4, i6, i5, i7);
      paramCanvas.drawBitmap(localBitmap, localRect1, localRect2, paramPaint);
      localRect1.set(0, m, i, n);
      localRect2.set(i2, i7, i3, i8);
      paramCanvas.drawBitmap(localBitmap, localRect1, localRect2, paramPaint);
      localRect1.set(i, m, j, n);
      localRect2.set(i3, i7, i4, i8);
      paramCanvas.drawBitmap(localBitmap, localRect1, localRect2, paramPaint);
      localRect1.set(j, m, k, n);
      localRect2.set(i4, i7, i5, i8);
      paramCanvas.drawBitmap(localBitmap, localRect1, localRect2, paramPaint);
      localRect1.set(0, n, i, i1);
      localRect2.set(i2, i8, i3, i9);
      paramCanvas.drawBitmap(localBitmap, localRect1, localRect2, paramPaint);
      localRect1.set(i, n, j, i1);
      localRect2.set(i3, i8, i4, i9);
      paramCanvas.drawBitmap(localBitmap, localRect1, localRect2, paramPaint);
      localRect1.set(j, n, k, i1);
      localRect2.set(i4, i8, i5, i9);
      paramCanvas.drawBitmap(localBitmap, localRect1, localRect2, paramPaint);
      return true;
    }
    return false;
  }

  public boolean draw(Canvas paramCanvas, Rect paramRect, RectF paramRectF, Paint paramPaint)
  {
    Drawable localDrawable = this._drawable;
    if (localDrawable != null)
    {
      localDrawable.setBounds(XulUtils.roundToInt(paramRectF.left), XulUtils.roundToInt(paramRectF.top), XulUtils.roundToInt(paramRectF.right), XulUtils.roundToInt(paramRectF.bottom));
      localDrawable.draw(paramCanvas);
      return true;
    }
    Bitmap localBitmap = this._bmp;
    if (localBitmap != null)
    {
      Rect localRect = XulDC._tmpRc0;
      RectF localRectF = XulDC._tmpFRc1;
      int i = this._patchRect.left;
      int j = this._width - this._patchRect.right;
      int k = this._width;
      int m = this._patchRect.top;
      int n = this._height - this._patchRect.bottom;
      int i1 = this._height;
      float f1 = XulUtils.roundToInt(paramRectF.left);
      float f2 = XulUtils.roundToInt(paramRectF.right);
      float f3 = XulUtils.roundToInt(paramRectF.top);
      float f4 = XulUtils.roundToInt(paramRectF.bottom);
      float f5 = f1 + this._patchRect.left;
      float f6 = f2 - this._patchRect.right;
      float f7 = f3 + this._patchRect.top;
      float f8 = f4 - this._patchRect.bottom;
      localRect.set(0, 0, i, m);
      localRectF.set(f1, f3, f5, f7);
      paramCanvas.drawBitmap(localBitmap, localRect, localRectF, paramPaint);
      localRect.set(i, 0, j, m);
      localRectF.set(f5, f3, f6, f7);
      paramCanvas.drawBitmap(localBitmap, localRect, localRectF, paramPaint);
      localRect.set(j, 0, k, m);
      localRectF.set(f6, f3, f2, f7);
      paramCanvas.drawBitmap(localBitmap, localRect, localRectF, paramPaint);
      localRect.set(0, m, i, n);
      localRectF.set(f1, f7, f5, f8);
      paramCanvas.drawBitmap(localBitmap, localRect, localRectF, paramPaint);
      localRect.set(i, m, j, n);
      localRectF.set(f5, f7, f6, f8);
      paramCanvas.drawBitmap(localBitmap, localRect, localRectF, paramPaint);
      localRect.set(j, m, k, n);
      localRectF.set(f6, f7, f2, f8);
      paramCanvas.drawBitmap(localBitmap, localRect, localRectF, paramPaint);
      localRect.set(0, n, i, i1);
      localRectF.set(f1, f8, f5, f4);
      paramCanvas.drawBitmap(localBitmap, localRect, localRectF, paramPaint);
      localRect.set(i, n, j, i1);
      localRectF.set(f5, f8, f6, f4);
      paramCanvas.drawBitmap(localBitmap, localRect, localRectF, paramPaint);
      localRect.set(j, n, k, i1);
      localRectF.set(f6, f8, f2, f4);
      paramCanvas.drawBitmap(localBitmap, localRect, localRectF, paramPaint);
      return true;
    }
    return false;
  }

  public boolean drawBorderOnly(Canvas paramCanvas, Rect paramRect1, Rect paramRect2, Paint paramPaint)
  {
    Drawable localDrawable = this._drawable;
    if (localDrawable != null)
    {
      localDrawable.setBounds(paramRect2);
      localDrawable.draw(paramCanvas);
      return true;
    }
    Bitmap localBitmap = this._bmp;
    if (localBitmap != null)
    {
      Rect localRect1 = XulDC._tmpRc0;
      Rect localRect2 = XulDC._tmpRc1;
      int i = this._patchRect.left;
      int j = this._width - this._patchRect.right;
      int k = this._width;
      int m = this._patchRect.top;
      int n = this._height - this._patchRect.bottom;
      int i1 = this._height;
      int i2 = paramRect2.left;
      int i3 = paramRect2.left + this._patchRect.left;
      int i4 = paramRect2.right - this._patchRect.right;
      int i5 = paramRect2.right;
      int i6 = paramRect2.top;
      int i7 = paramRect2.top + this._patchRect.top;
      int i8 = paramRect2.bottom - this._patchRect.bottom;
      int i9 = paramRect2.bottom;
      localRect1.set(0, 0, i, m);
      localRect2.set(i2, i6, i3, i7);
      paramCanvas.drawBitmap(localBitmap, localRect1, localRect2, paramPaint);
      localRect1.set(i, 0, j, m);
      localRect2.set(i3, i6, i4, i7);
      paramCanvas.drawBitmap(localBitmap, localRect1, localRect2, paramPaint);
      localRect1.set(j, 0, k, m);
      localRect2.set(i4, i6, i5, i7);
      paramCanvas.drawBitmap(localBitmap, localRect1, localRect2, paramPaint);
      localRect1.set(0, m, i, n);
      localRect2.set(i2, i7, i3, i8);
      paramCanvas.drawBitmap(localBitmap, localRect1, localRect2, paramPaint);
      localRect1.set(j, m, k, n);
      localRect2.set(i4, i7, i5, i8);
      paramCanvas.drawBitmap(localBitmap, localRect1, localRect2, paramPaint);
      localRect1.set(0, n, i, i1);
      localRect2.set(i2, i8, i3, i9);
      paramCanvas.drawBitmap(localBitmap, localRect1, localRect2, paramPaint);
      localRect1.set(i, n, j, i1);
      localRect2.set(i3, i8, i4, i9);
      paramCanvas.drawBitmap(localBitmap, localRect1, localRect2, paramPaint);
      localRect1.set(j, n, k, i1);
      localRect2.set(i4, i8, i5, i9);
      paramCanvas.drawBitmap(localBitmap, localRect1, localRect2, paramPaint);
      return true;
    }
    return false;
  }

  public boolean drawBorderOnly(Canvas paramCanvas, Rect paramRect, RectF paramRectF, Paint paramPaint)
  {
    Drawable localDrawable = this._drawable;
    if (localDrawable != null)
    {
      localDrawable.setBounds(XulUtils.roundToInt(paramRectF.left), XulUtils.roundToInt(paramRectF.top), XulUtils.roundToInt(paramRectF.right), XulUtils.roundToInt(paramRectF.bottom));
      localDrawable.draw(paramCanvas);
      return true;
    }
    Bitmap localBitmap = this._bmp;
    if (localBitmap != null)
    {
      Rect localRect = XulDC._tmpRc0;
      RectF localRectF = XulDC._tmpFRc1;
      int i = this._patchRect.left;
      int j = this._width - this._patchRect.right;
      int k = this._width;
      int m = this._patchRect.top;
      int n = this._height - this._patchRect.bottom;
      int i1 = this._height;
      float f1 = XulUtils.roundToInt(paramRectF.left);
      float f2 = XulUtils.roundToInt(paramRectF.right);
      float f3 = XulUtils.roundToInt(paramRectF.top);
      float f4 = XulUtils.roundToInt(paramRectF.bottom);
      float f5 = f1 + this._patchRect.left;
      float f6 = f2 - this._patchRect.right;
      float f7 = f3 + this._patchRect.top;
      float f8 = f4 - this._patchRect.bottom;
      localRect.set(0, 0, i, m);
      localRectF.set(f1, f3, f5, f7);
      paramCanvas.drawBitmap(localBitmap, localRect, localRectF, paramPaint);
      localRect.set(i, 0, j, m);
      localRectF.set(f5, f3, f6, f7);
      paramCanvas.drawBitmap(localBitmap, localRect, localRectF, paramPaint);
      localRect.set(j, 0, k, m);
      localRectF.set(f6, f3, f2, f7);
      paramCanvas.drawBitmap(localBitmap, localRect, localRectF, paramPaint);
      localRect.set(0, m, i, n);
      localRectF.set(f1, f7, f5, f8);
      paramCanvas.drawBitmap(localBitmap, localRect, localRectF, paramPaint);
      localRect.set(j, m, k, n);
      localRectF.set(f6, f7, f2, f8);
      paramCanvas.drawBitmap(localBitmap, localRect, localRectF, paramPaint);
      localRect.set(0, n, i, i1);
      localRectF.set(f1, f8, f5, f4);
      paramCanvas.drawBitmap(localBitmap, localRect, localRectF, paramPaint);
      localRect.set(i, n, j, i1);
      localRectF.set(f5, f8, f6, f4);
      paramCanvas.drawBitmap(localBitmap, localRect, localRectF, paramPaint);
      localRect.set(j, n, k, i1);
      localRectF.set(f6, f8, f2, f4);
      paramCanvas.drawBitmap(localBitmap, localRect, localRectF, paramPaint);
      return true;
    }
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
 * Qualified Name:     com.starcor.xul.Graphics.XulNinePatchDrawable
 * JD-Core Version:    0.6.2
 */