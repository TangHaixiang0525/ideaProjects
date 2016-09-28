package com.starcor.xul.Render;

import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.KeyEvent;
import com.starcor.xul.Graphics.XulDC;
import com.starcor.xul.IXulExternalView;
import com.starcor.xul.Utils.XulLayoutHelper.ILayoutElement;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulView;

public class XulCustomViewRender extends XulViewRender
{
  private static final String TAG = XulCustomViewRender.class.getSimpleName();
  public static final String TYPE = "custom";
  IXulExternalView _extView;
  boolean _isParentVisible = true;

  public XulCustomViewRender(XulRenderContext paramXulRenderContext, XulView paramXulView)
  {
    super(paramXulRenderContext, paramXulView);
    init();
  }

  private void _syncExtViewPosition()
  {
    if (this._extView == null);
    while ((this._rect == null) || (XulUtils.calRectWidth(this._rect) >= 2147483547) || (XulUtils.calRectHeight(this._rect) >= 2147483547))
      return;
    RectF localRectF = this._view.getFocusRc();
    if (this._padding != null)
    {
      localRectF.left += this._padding.left;
      localRectF.top += this._padding.top;
      localRectF.right -= this._padding.right;
      localRectF.bottom -= this._padding.bottom;
    }
    Rect localRect = XulDC._tmpRc0;
    XulUtils.copyRect(localRectF, localRect);
    this._extView.extMoveTo(localRect);
  }

  private void _syncExtViewVisibility(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this._extView.extShow();
      if (this._view.isFocused())
        this._extView.extOnFocus();
      return;
    }
    this._extView.extHide();
  }

  public static void register()
  {
    XulRenderFactory.registerBuilder("item", "custom", new XulRenderFactory.RenderBuilder()
    {
      protected XulViewRender createRender(XulRenderContext paramAnonymousXulRenderContext, XulView paramAnonymousXulView)
      {
        return new XulCustomViewRender(paramAnonymousXulRenderContext, paramAnonymousXulView);
      }
    });
  }

  protected XulLayoutHelper.ILayoutElement createElement()
  {
    return new LayoutElement();
  }

  public void destroy()
  {
    if (this._extView == null)
      return;
    try
    {
      this._extView.extDestroy();
      this._extView = null;
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public int getDefaultFocusMode()
  {
    return 8;
  }

  public IXulExternalView getExternalView()
  {
    return this._extView;
  }

  void init()
  {
    String str = this._view.getAttrString("class");
    this._extView = this._ctx.createExternalView(str, 0, 0, 0, 0, this._view);
    if (this._extView == null)
    {
      Log.e(TAG, "create custom view failed!!");
      Log.e(TAG, "item " + this._view);
    }
  }

  public boolean onKeyEvent(KeyEvent paramKeyEvent)
  {
    if (this._extView != null)
      return this._extView.extOnKeyEvent(paramKeyEvent);
    return false;
  }

  public void onVisibilityChanged(boolean paramBoolean, XulView paramXulView)
  {
    super.onVisibilityChanged(paramBoolean, paramXulView);
    if (this._extView == null)
      Log.e(TAG, "external view is null! " + toString());
    do
    {
      return;
      if (paramXulView == this._view)
      {
        _syncExtViewVisibility(paramBoolean);
        return;
      }
      this._isParentVisible = paramBoolean;
    }
    while (!_isVisible());
    _syncExtViewVisibility(paramBoolean);
  }

  public void switchState(int paramInt)
  {
    if ((this._extView == null) || (1 == paramInt));
    try
    {
      this._extView.extOnFocus();
      while (true)
      {
        super.switchState(paramInt);
        return;
        this._extView.extOnBlur();
      }
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public void syncData()
  {
    if (!_isDataChanged());
    IXulExternalView localIXulExternalView;
    do
    {
      return;
      super.syncData();
      localIXulExternalView = this._extView;
    }
    while (localIXulExternalView == null);
    localIXulExternalView.extSyncData();
  }

  protected class LayoutElement extends XulViewRender.LayoutElement
  {
    protected LayoutElement()
    {
      super();
    }

    public int doFinal()
    {
      super.doFinal();
      XulCustomViewRender.this._syncExtViewPosition();
      return 0;
    }

    public boolean offsetBase(int paramInt1, int paramInt2)
    {
      super.offsetBase(paramInt1, paramInt2);
      XulCustomViewRender.this._syncExtViewPosition();
      return true;
    }

    public boolean setBase(int paramInt1, int paramInt2)
    {
      super.setBase(paramInt1, paramInt2);
      XulCustomViewRender.this._syncExtViewPosition();
      return true;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.XulCustomViewRender
 * JD-Core Version:    0.6.2
 */