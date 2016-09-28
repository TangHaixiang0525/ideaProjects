package com.starcor.xul.Graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

public abstract class XulDrawable
{
  volatile boolean _isRecycled = false;
  String _key;
  String _url;

  public static XulDrawable fromBitmap(Bitmap paramBitmap, String paramString1, String paramString2)
  {
    if (paramBitmap == null)
      return null;
    XulBitmapDrawable localXulBitmapDrawable = new XulBitmapDrawable();
    localXulBitmapDrawable._bmp = paramBitmap;
    localXulBitmapDrawable._url = paramString1;
    localXulBitmapDrawable._key = paramString2;
    return localXulBitmapDrawable;
  }

  public static XulDrawable fromColor(int paramInt1, int paramInt2, int paramInt3, float paramFloat1, float paramFloat2, String paramString1, String paramString2)
  {
    XulColorDrawable localXulColorDrawable = new XulColorDrawable(paramInt1, paramInt2, paramInt3, paramFloat1, paramFloat2);
    localXulColorDrawable._url = paramString1;
    localXulColorDrawable._key = paramString2;
    return localXulColorDrawable;
  }

  public static XulDrawable fromColor(int paramInt1, int paramInt2, int paramInt3, String paramString1, String paramString2)
  {
    XulColorDrawable localXulColorDrawable = new XulColorDrawable(paramInt1, paramInt2, paramInt3);
    localXulColorDrawable._url = paramString1;
    localXulColorDrawable._key = paramString2;
    return localXulColorDrawable;
  }

  public static XulDrawable fromColor(int paramInt1, int paramInt2, int paramInt3, float[] paramArrayOfFloat, String paramString1, String paramString2)
  {
    XulColorDrawable localXulColorDrawable = new XulColorDrawable(paramInt1, paramInt2, paramInt3, paramArrayOfFloat);
    localXulColorDrawable._url = paramString1;
    localXulColorDrawable._key = paramString2;
    return localXulColorDrawable;
  }

  public static XulDrawable fromDrawable(Drawable paramDrawable, String paramString1, String paramString2)
  {
    if (paramDrawable == null)
      return null;
    XulAndroidDrawable localXulAndroidDrawable = new XulAndroidDrawable(paramDrawable);
    localXulAndroidDrawable._url = paramString1;
    localXulAndroidDrawable._key = paramString2;
    return localXulAndroidDrawable;
  }

  public static XulDrawable fromNinePitchBitmap(Bitmap paramBitmap, String paramString1, String paramString2)
  {
    if (paramBitmap == null)
      return null;
    XulNinePatchDrawable localXulNinePatchDrawable = new XulNinePatchDrawable();
    localXulNinePatchDrawable._url = paramString1;
    localXulNinePatchDrawable._key = paramString2;
    localXulNinePatchDrawable.attach(paramBitmap);
    return localXulNinePatchDrawable;
  }

  public boolean cacheable()
  {
    return true;
  }

  public abstract boolean draw(Canvas paramCanvas, Rect paramRect1, Rect paramRect2, Paint paramPaint);

  public abstract boolean draw(Canvas paramCanvas, Rect paramRect, RectF paramRectF, Paint paramPaint);

  public abstract int getHeight();

  public final String getKey()
  {
    return this._key;
  }

  public final String getUrl()
  {
    return this._url;
  }

  public abstract int getWidth();

  public boolean isRecycled()
  {
    return this._isRecycled;
  }

  public void recycle()
  {
    this._isRecycled = true;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Graphics.XulDrawable
 * JD-Core Version:    0.6.2
 */