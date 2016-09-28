package com.starcor.xul.Wrapper;

import android.graphics.RectF;
import com.starcor.xul.Render.XulMassiveRender;
import com.starcor.xul.Render.XulMassiveRender.DataItemIterator;
import com.starcor.xul.Render.XulSliderAreaRender;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulDataNode;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulView;

public class XulMassiveAreaWrapper extends XulViewWrapper
{
  XulArea _view;

  XulMassiveAreaWrapper(XulArea paramXulArea)
  {
    super(paramXulArea);
    this._view = paramXulArea;
  }

  private void _internalMakeChildVisibleByRect(final XulMassiveRender paramXulMassiveRender, final XulSliderAreaRender paramXulSliderAreaRender, final int paramInt, final float paramFloat1, final float paramFloat2, final boolean paramBoolean, final Runnable paramRunnable)
  {
    RectF localRectF = paramXulMassiveRender.getItemRect(paramInt);
    XulRenderContext localXulRenderContext = paramXulMassiveRender.getRenderContext();
    if (localRectF == null)
    {
      paramXulMassiveRender.setUpdateLayout(true);
      localXulRenderContext.scheduleLayoutFinishedTask(new Runnable()
      {
        public void run()
        {
          XulMassiveAreaWrapper.this._internalMakeChildVisibleByRect(paramXulMassiveRender, paramXulSliderAreaRender, paramInt, paramFloat1, paramFloat2, paramBoolean, paramRunnable);
        }
      });
      return;
    }
    if (Float.isNaN(paramFloat1))
      paramXulSliderAreaRender.makeRectVisible(localRectF, paramBoolean);
    while (true)
    {
      _internalScheduleOnFinishTask(paramXulMassiveRender, paramXulSliderAreaRender, paramInt, paramRunnable, localXulRenderContext);
      return;
      paramXulSliderAreaRender.makeRectVisible(localRectF, paramFloat1, paramFloat2, paramBoolean);
    }
  }

  private void _internalScheduleOnFinishTask(final XulMassiveRender paramXulMassiveRender, final XulSliderAreaRender paramXulSliderAreaRender, final int paramInt, final Runnable paramRunnable, final XulRenderContext paramXulRenderContext)
  {
    if (paramRunnable == null)
      return;
    paramXulMassiveRender.setUpdateLayout(true);
    paramXulRenderContext.scheduleLayoutFinishedTask(new Runnable()
    {
      public void run()
      {
        if (paramXulMassiveRender.getItemView(paramInt) == null)
        {
          paramXulRenderContext.scheduleLayoutFinishedTask(this);
          return;
        }
        if (paramXulSliderAreaRender.isScrolling())
        {
          paramXulRenderContext.scheduleLayoutFinishedTask(this);
          return;
        }
        try
        {
          paramRunnable.run();
          return;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    });
  }

  public static XulMassiveAreaWrapper fromXulView(XulView paramXulView)
  {
    if (paramXulView == null);
    while (!(paramXulView.getRender() instanceof XulMassiveRender))
      return null;
    return new XulMassiveAreaWrapper((XulArea)paramXulView);
  }

  public boolean addItem(int paramInt, XulDataNode paramXulDataNode)
  {
    XulMassiveRender localXulMassiveRender = (XulMassiveRender)this._view.getRender();
    if (localXulMassiveRender == null)
      return false;
    localXulMassiveRender.addDataItem(paramInt, paramXulDataNode);
    return true;
  }

  public boolean addItem(XulDataNode paramXulDataNode)
  {
    XulMassiveRender localXulMassiveRender = (XulMassiveRender)this._view.getRender();
    if (localXulMassiveRender == null)
      return false;
    localXulMassiveRender.addDataItem(paramXulDataNode);
    return true;
  }

  public boolean clear()
  {
    XulMassiveRender localXulMassiveRender = (XulMassiveRender)this._view.getRender();
    if (localXulMassiveRender == null)
      return false;
    localXulMassiveRender.cleanDataItems();
    localXulMassiveRender.reset();
    return true;
  }

  public void eachItem(XulMassiveRender.DataItemIterator paramDataItemIterator)
  {
    XulMassiveRender localXulMassiveRender = (XulMassiveRender)this._view.getRender();
    if (localXulMassiveRender == null)
      return;
    localXulMassiveRender.eachDataItem(paramDataItemIterator);
  }

  public boolean fixedItem()
  {
    XulMassiveRender localXulMassiveRender = (XulMassiveRender)this._view.getRender();
    if (localXulMassiveRender == null)
      return false;
    return localXulMassiveRender.fixedItem();
  }

  public XulDataNode getItem(int paramInt)
  {
    XulMassiveRender localXulMassiveRender = (XulMassiveRender)this._view.getRender();
    if (localXulMassiveRender == null)
      return null;
    return localXulMassiveRender.getDataItem(paramInt);
  }

  public boolean getItemData(int paramInt, String paramString1, String paramString2)
  {
    return (XulMassiveRender)this._view.getRender() != null;
  }

  public boolean getItemData(XulView paramXulView, String paramString1, String paramString2)
  {
    return (XulMassiveRender)this._view.getRender() != null;
  }

  public int getItemIdx(XulView paramXulView)
  {
    XulMassiveRender localXulMassiveRender = (XulMassiveRender)this._view.getRender();
    if (localXulMassiveRender == null)
      return -1;
    return localXulMassiveRender.getItemIdx(paramXulView);
  }

  public RectF getItemRect(int paramInt)
  {
    return getItemRect(paramInt, new RectF());
  }

  public RectF getItemRect(int paramInt, RectF paramRectF)
  {
    XulMassiveRender localXulMassiveRender = (XulMassiveRender)this._view.getRender();
    if (localXulMassiveRender == null)
      return null;
    return localXulMassiveRender.getItemRect(paramInt, paramRectF);
  }

  public RectF getItemRect(XulView paramXulView)
  {
    return getItemRect(paramXulView, new RectF());
  }

  public RectF getItemRect(XulView paramXulView, RectF paramRectF)
  {
    XulMassiveRender localXulMassiveRender = (XulMassiveRender)this._view.getRender();
    if (localXulMassiveRender == null)
      return null;
    return localXulMassiveRender.getItemRect(localXulMassiveRender.getItemIdx(paramXulView), paramRectF);
  }

  public XulView getItemView(int paramInt)
  {
    XulMassiveRender localXulMassiveRender = (XulMassiveRender)this._view.getRender();
    if (localXulMassiveRender == null)
      return null;
    return localXulMassiveRender.getItemView(paramInt);
  }

  public int itemNum()
  {
    XulMassiveRender localXulMassiveRender = (XulMassiveRender)this._view.getRender();
    if (localXulMassiveRender == null)
      return 0;
    return localXulMassiveRender.getDataItemNum();
  }

  public boolean makeChildVisible(XulView paramXulView, int paramInt)
  {
    return makeChildVisible(paramXulView, paramInt, (0.0F / 0.0F), (0.0F / 0.0F), true);
  }

  public boolean makeChildVisible(XulView paramXulView, int paramInt, float paramFloat)
  {
    return makeChildVisible(paramXulView, paramInt, paramFloat, (0.0F / 0.0F), true);
  }

  public boolean makeChildVisible(XulView paramXulView, int paramInt, float paramFloat1, float paramFloat2)
  {
    return makeChildVisible(paramXulView, paramInt, paramFloat1, paramFloat2, true);
  }

  public boolean makeChildVisible(XulView paramXulView, int paramInt, float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    return makeChildVisible(paramXulView, paramInt, paramFloat1, paramFloat2, paramBoolean, null);
  }

  public boolean makeChildVisible(XulView paramXulView, int paramInt, float paramFloat1, float paramFloat2, boolean paramBoolean, Runnable paramRunnable)
  {
    if (!"slider".equals(paramXulView.getType()))
      return false;
    if (!this._view.isChildOf(paramXulView))
      return false;
    XulMassiveRender localXulMassiveRender = (XulMassiveRender)this._view.getRender();
    if (localXulMassiveRender == null)
      return false;
    if (!localXulMassiveRender.fixedItem())
      return false;
    if ((paramInt < 0) || (paramInt >= itemNum()))
      return false;
    XulSliderAreaRender localXulSliderAreaRender = (XulSliderAreaRender)paramXulView.getRender();
    XulView localXulView = localXulMassiveRender.getItemView(paramInt);
    if (localXulView != null)
    {
      if (Float.isNaN(paramFloat1))
        localXulSliderAreaRender.makeChildVisible(localXulView, paramBoolean);
      while (true)
      {
        _internalScheduleOnFinishTask(localXulMassiveRender, localXulSliderAreaRender, paramInt, paramRunnable, localXulMassiveRender.getRenderContext());
        return true;
        localXulSliderAreaRender.makeChildVisible(localXulView, paramFloat1, paramFloat2, paramBoolean);
      }
    }
    _internalMakeChildVisibleByRect(localXulMassiveRender, localXulSliderAreaRender, paramInt, paramFloat1, paramFloat2, paramBoolean, paramRunnable);
    return true;
  }

  public boolean makeChildVisible(XulView paramXulView, int paramInt, float paramFloat, boolean paramBoolean)
  {
    return makeChildVisible(paramXulView, paramInt, paramFloat, (0.0F / 0.0F), paramBoolean);
  }

  public boolean makeChildVisible(XulView paramXulView, int paramInt, boolean paramBoolean)
  {
    return makeChildVisible(paramXulView, paramInt, (0.0F / 0.0F), (0.0F / 0.0F), paramBoolean, null);
  }

  public boolean makeChildVisible(XulView paramXulView, int paramInt, boolean paramBoolean, Runnable paramRunnable)
  {
    return makeChildVisible(paramXulView, paramInt, (0.0F / 0.0F), (0.0F / 0.0F), paramBoolean, paramRunnable);
  }

  public String queryItemData(int paramInt, String paramString)
  {
    return null;
  }

  public String queryItemData(XulView paramXulView, String paramString)
  {
    return null;
  }

  public boolean removeItem(int paramInt)
  {
    XulMassiveRender localXulMassiveRender = (XulMassiveRender)this._view.getRender();
    if (localXulMassiveRender == null)
      return false;
    return localXulMassiveRender.removeDataItem(paramInt);
  }

  public boolean removeItem(XulView paramXulView)
  {
    XulMassiveRender localXulMassiveRender = (XulMassiveRender)this._view.getRender();
    if (localXulMassiveRender == null)
      return false;
    return localXulMassiveRender.removeItem(paramXulView);
  }

  public boolean setItemData(int paramInt, String paramString1, String paramString2)
  {
    return (XulMassiveRender)this._view.getRender() != null;
  }

  public boolean setItemData(XulView paramXulView, String paramString1, String paramString2)
  {
    return (XulMassiveRender)this._view.getRender() != null;
  }

  public void syncContentView()
  {
    XulMassiveRender localXulMassiveRender = (XulMassiveRender)this._view.getRender();
    if (localXulMassiveRender == null)
      return;
    localXulMassiveRender.syncContentView();
  }

  public boolean updateItems(int paramInt, Iterable<XulDataNode> paramIterable)
  {
    XulMassiveRender localXulMassiveRender = (XulMassiveRender)this._view.getRender();
    if (localXulMassiveRender == null)
      return false;
    return localXulMassiveRender.updateDataItems(paramInt, paramIterable);
  }

  public boolean updateItems(int paramInt, XulDataNode[] paramArrayOfXulDataNode)
  {
    XulMassiveRender localXulMassiveRender = (XulMassiveRender)this._view.getRender();
    if (localXulMassiveRender == null)
      return false;
    return localXulMassiveRender.updateDataItems(paramInt, paramArrayOfXulDataNode);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Wrapper.XulMassiveAreaWrapper
 * JD-Core Version:    0.6.2
 */