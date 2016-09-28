package com.starcor.xul.Render.Drawer;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import com.starcor.xul.Graphics.XulDC;
import com.starcor.xul.Graphics.XulDrawable;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulView;

public class XulBitmapDrawer extends XulDrawer
{
  static XulBitmapDrawer _instance = new XulBitmapDrawer();

  public static XulBitmapDrawer create(XulDrawable paramXulDrawable, XulView paramXulView, XulRenderContext paramXulRenderContext)
  {
    return _instance;
  }

  public void draw(XulDC paramXulDC, XulDrawable paramXulDrawable, Rect paramRect, Paint paramPaint)
  {
    paramXulDC.drawBitmap(paramXulDrawable, paramRect, paramPaint);
  }

  public void draw(XulDC paramXulDC, XulDrawable paramXulDrawable, Rect paramRect1, Rect paramRect2, Paint paramPaint)
  {
    paramXulDC.drawBitmap(paramXulDrawable, paramRect1, paramRect2, paramPaint);
  }

  public void draw(XulDC paramXulDC, XulDrawable paramXulDrawable, Rect paramRect, RectF paramRectF, Paint paramPaint)
  {
    paramXulDC.drawBitmap(paramXulDrawable, paramRect, paramRectF, paramPaint);
  }

  public void draw(XulDC paramXulDC, XulDrawable paramXulDrawable, RectF paramRectF, Paint paramPaint)
  {
    paramXulDC.drawBitmap(paramXulDrawable, paramRectF, paramPaint);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.Drawer.XulBitmapDrawer
 * JD-Core Version:    0.6.2
 */