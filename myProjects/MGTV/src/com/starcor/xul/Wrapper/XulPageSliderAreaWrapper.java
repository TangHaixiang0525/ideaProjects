package com.starcor.xul.Wrapper;

import com.starcor.xul.Render.XulPageSliderAreaRender;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulView;

public class XulPageSliderAreaWrapper extends XulViewWrapper
{
  public static final int IMAGE_GC_ALL = 0;
  public static final int IMAGE_GC_NORMAL = 1;
  public static final int IMAGE_GC_STRICT = 2;
  XulArea _area;

  XulPageSliderAreaWrapper(XulArea paramXulArea)
  {
    super(paramXulArea);
    this._area = paramXulArea;
  }

  public static XulPageSliderAreaWrapper fromXulView(XulView paramXulView)
  {
    if (paramXulView == null);
    while (!(paramXulView.getRender() instanceof XulPageSliderAreaRender))
      return null;
    return new XulPageSliderAreaWrapper((XulArea)paramXulView);
  }

  public int getCurrentPage()
  {
    XulPageSliderAreaRender localXulPageSliderAreaRender = (XulPageSliderAreaRender)this._area.getRender();
    if (localXulPageSliderAreaRender == null)
      return -1;
    return localXulPageSliderAreaRender.getCurrentPage();
  }

  public int getPageCount()
  {
    XulPageSliderAreaRender localXulPageSliderAreaRender = (XulPageSliderAreaRender)this._area.getRender();
    if (localXulPageSliderAreaRender == null)
      return -1;
    return localXulPageSliderAreaRender.getPageCount();
  }

  public void invokeImageGC(int paramInt)
  {
    XulPageSliderAreaRender localXulPageSliderAreaRender = (XulPageSliderAreaRender)this._area.getRender();
    if (localXulPageSliderAreaRender == null)
      return;
    switch (paramInt)
    {
    default:
      return;
    case 0:
      localXulPageSliderAreaRender.imageGC(0);
      return;
    case 1:
      localXulPageSliderAreaRender.imageGC(3);
      return;
    case 2:
    }
    localXulPageSliderAreaRender.imageGC(1);
  }

  public boolean setCurrentPage(int paramInt)
  {
    XulPageSliderAreaRender localXulPageSliderAreaRender = (XulPageSliderAreaRender)this._area.getRender();
    if (localXulPageSliderAreaRender == null)
      return false;
    boolean bool = localXulPageSliderAreaRender.setCurrentPage(paramInt);
    localXulPageSliderAreaRender.markDirtyView();
    return bool;
  }

  public void slideDown()
  {
    XulPageSliderAreaRender localXulPageSliderAreaRender = (XulPageSliderAreaRender)this._area.getRender();
    if (localXulPageSliderAreaRender == null)
      return;
    localXulPageSliderAreaRender.slideDown();
    localXulPageSliderAreaRender.markDirtyView();
  }

  public void slideLeft()
  {
    XulPageSliderAreaRender localXulPageSliderAreaRender = (XulPageSliderAreaRender)this._area.getRender();
    if (localXulPageSliderAreaRender == null)
      return;
    localXulPageSliderAreaRender.slideLeft();
    localXulPageSliderAreaRender.markDirtyView();
  }

  public void slideNext()
  {
    XulPageSliderAreaRender localXulPageSliderAreaRender = (XulPageSliderAreaRender)this._area.getRender();
    if (localXulPageSliderAreaRender == null)
      return;
    localXulPageSliderAreaRender.slideNext();
    localXulPageSliderAreaRender.markDirtyView();
  }

  public void slidePrev()
  {
    XulPageSliderAreaRender localXulPageSliderAreaRender = (XulPageSliderAreaRender)this._area.getRender();
    if (localXulPageSliderAreaRender == null)
      return;
    localXulPageSliderAreaRender.slidePrev();
    localXulPageSliderAreaRender.markDirtyView();
  }

  public void slideRight()
  {
    XulPageSliderAreaRender localXulPageSliderAreaRender = (XulPageSliderAreaRender)this._area.getRender();
    if (localXulPageSliderAreaRender == null)
      return;
    localXulPageSliderAreaRender.slideRight();
    localXulPageSliderAreaRender.markDirtyView();
  }

  public void slideUp()
  {
    XulPageSliderAreaRender localXulPageSliderAreaRender = (XulPageSliderAreaRender)this._area.getRender();
    if (localXulPageSliderAreaRender == null)
      return;
    localXulPageSliderAreaRender.slideUp();
    localXulPageSliderAreaRender.markDirtyView();
  }

  public void syncPages()
  {
    XulPageSliderAreaRender localXulPageSliderAreaRender = (XulPageSliderAreaRender)this._area.getRender();
    if (localXulPageSliderAreaRender == null)
      return;
    localXulPageSliderAreaRender.syncPages();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Wrapper.XulPageSliderAreaWrapper
 * JD-Core Version:    0.6.2
 */