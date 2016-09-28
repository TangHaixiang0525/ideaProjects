package com.starcor.xul.Render.Drawer;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import com.starcor.xul.Graphics.XulAnimationDrawable;
import com.starcor.xul.Graphics.XulDC;
import com.starcor.xul.Graphics.XulDrawable;
import com.starcor.xul.Graphics.XulGifAnimationDrawable;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulView;

public abstract class XulDrawer
{
  public static XulDrawer create(XulDrawable paramXulDrawable, XulView paramXulView, XulRenderContext paramXulRenderContext)
  {
    if (((paramXulDrawable instanceof XulAnimationDrawable)) || ((paramXulDrawable instanceof XulGifAnimationDrawable)))
      return XulAnimationDrawer.create(paramXulDrawable, paramXulView, paramXulRenderContext);
    return XulBitmapDrawer.create(paramXulDrawable, paramXulView, paramXulRenderContext);
  }

  public abstract void draw(XulDC paramXulDC, XulDrawable paramXulDrawable, Rect paramRect, Paint paramPaint);

  public abstract void draw(XulDC paramXulDC, XulDrawable paramXulDrawable, Rect paramRect1, Rect paramRect2, Paint paramPaint);

  public abstract void draw(XulDC paramXulDC, XulDrawable paramXulDrawable, Rect paramRect, RectF paramRectF, Paint paramPaint);

  public abstract void draw(XulDC paramXulDC, XulDrawable paramXulDrawable, RectF paramRectF, Paint paramPaint);

  public void reset()
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.Drawer.XulDrawer
 * JD-Core Version:    0.6.2
 */