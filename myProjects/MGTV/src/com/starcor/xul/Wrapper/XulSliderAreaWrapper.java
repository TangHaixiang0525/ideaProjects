package com.starcor.xul.Wrapper;

import android.graphics.RectF;
import com.starcor.xul.Render.XulSliderAreaRender;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulView;

public class XulSliderAreaWrapper extends XulViewWrapper
{
  XulArea _area;

  XulSliderAreaWrapper(XulArea paramXulArea)
  {
    super(paramXulArea);
    this._area = paramXulArea;
  }

  public static XulSliderAreaWrapper fromXulView(XulView paramXulView)
  {
    if (paramXulView == null);
    while (!(paramXulView.getRender() instanceof XulSliderAreaRender))
      return null;
    return new XulSliderAreaWrapper((XulArea)paramXulView);
  }

  public void activateScrollBar()
  {
    XulSliderAreaRender localXulSliderAreaRender = (XulSliderAreaRender)this._area.getRender();
    if (localXulSliderAreaRender == null)
      return;
    localXulSliderAreaRender.activateScrollBar();
  }

  public int getScrollDelta()
  {
    XulSliderAreaRender localXulSliderAreaRender = (XulSliderAreaRender)this._area.getRender();
    if (localXulSliderAreaRender == null)
      return 0;
    return localXulSliderAreaRender.getScrollDelta();
  }

  public int getScrollPos()
  {
    XulSliderAreaRender localXulSliderAreaRender = (XulSliderAreaRender)this._area.getRender();
    if (localXulSliderAreaRender == null)
      return 0;
    return localXulSliderAreaRender.getScrollPos();
  }

  public int getScrollRange()
  {
    XulSliderAreaRender localXulSliderAreaRender = (XulSliderAreaRender)this._area.getRender();
    if (localXulSliderAreaRender == null)
      return 0;
    return localXulSliderAreaRender.getScrollRange();
  }

  public boolean isVertical()
  {
    XulSliderAreaRender localXulSliderAreaRender = (XulSliderAreaRender)this._area.getRender();
    if (localXulSliderAreaRender == null)
      return false;
    return localXulSliderAreaRender.isVertical();
  }

  public boolean makeChildVisible(XulView paramXulView)
  {
    return makeChildVisible(paramXulView, true);
  }

  public boolean makeChildVisible(XulView paramXulView, float paramFloat)
  {
    return makeChildVisible(paramXulView, paramFloat, (0.0F / 0.0F));
  }

  public boolean makeChildVisible(XulView paramXulView, float paramFloat1, float paramFloat2)
  {
    return makeChildVisible(paramXulView, paramFloat1, paramFloat2, true);
  }

  public boolean makeChildVisible(XulView paramXulView, float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    XulSliderAreaRender localXulSliderAreaRender = (XulSliderAreaRender)this._area.getRender();
    if (localXulSliderAreaRender == null)
      return false;
    return localXulSliderAreaRender.makeChildVisible(paramXulView, paramFloat1, paramFloat2, paramBoolean);
  }

  public boolean makeChildVisible(XulView paramXulView, float paramFloat, boolean paramBoolean)
  {
    return makeChildVisible(paramXulView, paramFloat, (0.0F / 0.0F), paramBoolean);
  }

  public boolean makeChildVisible(XulView paramXulView, boolean paramBoolean)
  {
    XulSliderAreaRender localXulSliderAreaRender = (XulSliderAreaRender)this._area.getRender();
    if (localXulSliderAreaRender == null)
      return false;
    return localXulSliderAreaRender.makeChildVisible(paramXulView, paramBoolean);
  }

  public boolean makeRectVisible(RectF paramRectF)
  {
    return makeRectVisible(paramRectF, true);
  }

  public boolean makeRectVisible(RectF paramRectF, float paramFloat)
  {
    return makeRectVisible(paramRectF, paramFloat, (0.0F / 0.0F));
  }

  public boolean makeRectVisible(RectF paramRectF, float paramFloat1, float paramFloat2)
  {
    return makeRectVisible(paramRectF, paramFloat1, paramFloat2, true);
  }

  public boolean makeRectVisible(RectF paramRectF, float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    XulSliderAreaRender localXulSliderAreaRender = (XulSliderAreaRender)this._area.getRender();
    if (localXulSliderAreaRender == null)
      return false;
    return localXulSliderAreaRender.makeRectVisible(paramRectF, paramFloat1, paramFloat2, paramBoolean);
  }

  public boolean makeRectVisible(RectF paramRectF, float paramFloat, boolean paramBoolean)
  {
    return makeRectVisible(paramRectF, paramFloat, (0.0F / 0.0F), paramBoolean);
  }

  public boolean makeRectVisible(RectF paramRectF, boolean paramBoolean)
  {
    XulSliderAreaRender localXulSliderAreaRender = (XulSliderAreaRender)this._area.getRender();
    if (localXulSliderAreaRender == null)
      return false;
    return localXulSliderAreaRender.makeRectVisible(paramRectF, paramBoolean);
  }

  public void scrollByPage(int paramInt, boolean paramBoolean)
  {
    XulSliderAreaRender localXulSliderAreaRender = (XulSliderAreaRender)this._area.getRender();
    if (localXulSliderAreaRender == null)
      return;
    localXulSliderAreaRender.scrollByPage(paramInt, paramBoolean);
  }

  public void scrollTo(int paramInt)
  {
    XulSliderAreaRender localXulSliderAreaRender = (XulSliderAreaRender)this._area.getRender();
    if (localXulSliderAreaRender == null)
      return;
    localXulSliderAreaRender.scrollTo(paramInt, true);
  }

  public void scrollTo(int paramInt, boolean paramBoolean)
  {
    XulSliderAreaRender localXulSliderAreaRender = (XulSliderAreaRender)this._area.getRender();
    if (localXulSliderAreaRender == null)
      return;
    localXulSliderAreaRender.scrollTo(paramInt, paramBoolean);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Wrapper.XulSliderAreaWrapper
 * JD-Core Version:    0.6.2
 */