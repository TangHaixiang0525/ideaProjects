package com.starcor.xul.Render.Drawer;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import com.starcor.xul.Graphics.XulAnimationDrawable;
import com.starcor.xul.Graphics.XulAnimationDrawable.AnimationDrawingContext;
import com.starcor.xul.Graphics.XulDC;
import com.starcor.xul.Graphics.XulDrawable;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulView;

public class XulAnimationDrawer extends XulDrawer
  implements IXulAnimation
{
  XulAnimationDrawable.AnimationDrawingContext _aniCtx;
  XulAnimationDrawable _drawable;
  XulView _ownerView;
  XulRenderContext _renderCtx;
  boolean _terminated = true;

  public static XulAnimationDrawer create(XulDrawable paramXulDrawable, XulView paramXulView, XulRenderContext paramXulRenderContext)
  {
    if ((paramXulView == null) || (paramXulDrawable == null))
      return null;
    XulAnimationDrawer localXulAnimationDrawer = new XulAnimationDrawer();
    localXulAnimationDrawer._renderCtx = paramXulRenderContext;
    localXulAnimationDrawer._ownerView = paramXulView;
    localXulAnimationDrawer._drawable = ((XulAnimationDrawable)paramXulDrawable);
    localXulAnimationDrawer._aniCtx = localXulAnimationDrawer._drawable.createDrawingCtx();
    return localXulAnimationDrawer;
  }

  public void draw(XulDC paramXulDC, XulDrawable paramXulDrawable, Rect paramRect, Paint paramPaint)
  {
    if (this._terminated)
    {
      this._terminated = false;
      this._aniCtx.reset();
      this._renderCtx.addAnimation(this);
    }
    this._drawable.drawAnimation(this._aniCtx, paramXulDC, paramRect, paramPaint);
  }

  public void draw(XulDC paramXulDC, XulDrawable paramXulDrawable, Rect paramRect1, Rect paramRect2, Paint paramPaint)
  {
    if (this._terminated)
    {
      this._terminated = false;
      this._aniCtx.reset();
      this._renderCtx.addAnimation(this);
    }
    this._drawable.drawAnimation(this._aniCtx, paramXulDC, paramRect2, paramPaint);
  }

  public void draw(XulDC paramXulDC, XulDrawable paramXulDrawable, Rect paramRect, RectF paramRectF, Paint paramPaint)
  {
    if (this._terminated)
    {
      this._terminated = false;
      this._aniCtx.reset();
      this._renderCtx.addAnimation(this);
    }
    this._drawable.drawAnimation(this._aniCtx, paramXulDC, paramRectF, paramPaint);
  }

  public void draw(XulDC paramXulDC, XulDrawable paramXulDrawable, RectF paramRectF, Paint paramPaint)
  {
    if (this._terminated)
    {
      this._terminated = false;
      this._aniCtx.reset();
      this._renderCtx.addAnimation(this);
    }
    this._drawable.drawAnimation(this._aniCtx, paramXulDC, paramRectF, paramPaint);
  }

  public void reset()
  {
    this._terminated = true;
  }

  public boolean updateAnimation(long paramLong)
  {
    if (this._terminated);
    boolean bool;
    do
    {
      return false;
      bool = this._aniCtx.isAnimationFinished();
      if (this._aniCtx.updateAnimation(paramLong))
        this._ownerView.markDirtyView();
    }
    while (bool);
    return true;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.Drawer.XulAnimationDrawer
 * JD-Core Version:    0.6.2
 */