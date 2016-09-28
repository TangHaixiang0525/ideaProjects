package com.starcor.xul.Render;

import android.graphics.Rect;
import android.graphics.RectF;
import com.starcor.xul.Events.XulStateChangeEvent;
import com.starcor.xul.Graphics.XulDC;
import com.starcor.xul.Prop.XulAttr;
import com.starcor.xul.Prop.XulPropNameCache.TagId;
import com.starcor.xul.Utils.XulAreaChildrenRender;
import com.starcor.xul.Utils.XulAreaChildrenVisibleChangeNotifier;
import com.starcor.xul.Utils.XulLayoutHelper.ILayoutElement;
import com.starcor.xul.Utils.XulPropParser.xulParsedAttr_Direction;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulLayout.FocusDirection;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulView;

public class XulGridAreaRender extends XulViewContainerBaseRender
{
  boolean _isVertical = false;
  XulAreaChildrenRender childrenRender = new XulAreaChildrenRender();

  public XulGridAreaRender(XulRenderContext paramXulRenderContext, XulArea paramXulArea)
  {
    super(paramXulRenderContext, paramXulArea);
  }

  private boolean onChildFocused(XulView paramXulView)
  {
    RectF localRectF = paramXulView.getFocusRc();
    XulUtils.offsetRect(localRectF, -(this._screenX + this._padding.left + this._rect.left), -(this._screenY + this._padding.top + this._rect.top));
    if (localRectF.left < 0.0F);
    while (true)
    {
      return false;
      if (localRectF.right + this._padding.left <= XulUtils.calRectWidth(this._rect) - this._padding.right);
    }
  }

  public static void register()
  {
    XulRenderFactory.registerBuilder("area", "grid", new XulRenderFactory.RenderBuilder()
    {
      static
      {
        if (!XulGridAreaRender.class.desiredAssertionStatus());
        for (boolean bool = true; ; bool = false)
        {
          $assertionsDisabled = bool;
          return;
        }
      }

      protected XulViewRender createRender(XulRenderContext paramAnonymousXulRenderContext, XulView paramAnonymousXulView)
      {
        assert ((paramAnonymousXulView instanceof XulArea));
        return new XulGridAreaRender(paramAnonymousXulRenderContext, (XulArea)paramAnonymousXulView);
      }
    });
  }

  private void syncDirection()
  {
    if (!_isViewChanged());
    XulAttr localXulAttr;
    do
    {
      return;
      localXulAttr = this._area.getAttr(XulPropNameCache.TagId.DIRECTION);
    }
    while (localXulAttr == null);
    this._isVertical = ((XulPropParser.xulParsedAttr_Direction)localXulAttr.getParsedValue()).vertical;
  }

  protected XulLayoutHelper.ILayoutElement createElement()
  {
    return new LayoutContainer();
  }

  public void draw(XulDC paramXulDC, Rect paramRect, int paramInt1, int paramInt2)
  {
    if (_isInvisible())
      return;
    super.draw(paramXulDC, paramRect, paramInt1, paramInt2);
    this.childrenRender.init(paramXulDC, paramRect, paramInt1, paramInt2);
    this._area.eachView(this.childrenRender);
  }

  public int getDefaultFocusMode()
  {
    return 37;
  }

  public boolean onChildStateChanged(XulStateChangeEvent paramXulStateChangeEvent)
  {
    return false;
  }

  public void onVisibilityChanged(boolean paramBoolean, XulView paramXulView)
  {
    super.onVisibilityChanged(paramBoolean, paramXulView);
    XulAreaChildrenVisibleChangeNotifier localXulAreaChildrenVisibleChangeNotifier = XulAreaChildrenVisibleChangeNotifier.getNotifier();
    localXulAreaChildrenVisibleChangeNotifier.begin(paramBoolean, (XulArea)paramXulView);
    this._area.eachChild(localXulAreaChildrenVisibleChangeNotifier);
    localXulAreaChildrenVisibleChangeNotifier.end();
  }

  public XulView postFindFocus(XulLayout.FocusDirection paramFocusDirection, RectF paramRectF, XulView paramXulView)
  {
    XulView localXulView1 = this._area.getLastChild();
    XulView localXulView3;
    if (localXulView1 == null)
      localXulView3 = null;
    RectF localRectF5;
    RectF localRectF6;
    do
    {
      return localXulView3;
      if (!this._area.testFocusModeBits(64))
        break;
      localRectF5 = XulDC._tmpFRc0;
      XulUtils.copyRect(paramRectF, localRectF5);
      localRectF6 = this._area.getFocusRc();
      if (this._isVertical)
        break label244;
      if (paramFocusDirection != XulLayout.FocusDirection.MOVE_LEFT)
        break label212;
      XulUtils.offsetRect(localRectF5, XulUtils.calRectWidth(localRectF6), -XulUtils.calRectHeight(localRectF5));
      if (localRectF5 == null)
        break;
      localXulView3 = this._area.findSubFocusByDirection(paramFocusDirection, localRectF5, paramXulView);
    }
    while (localXulView3 != null);
    if (this._area.testFocusModeBits(2))
    {
      RectF localRectF2 = XulDC._tmpFRc0;
      XulUtils.copyRect(paramRectF, localRectF2);
      RectF localRectF3 = this._area.getFocusRc();
      XulView localXulView2 = this._area.getFirstChild();
      if ((localXulView1 == paramXulView) || (paramXulView.isChildOf(localXulView1)))
        if ((!this._isVertical) && (paramFocusDirection == XulLayout.FocusDirection.MOVE_RIGHT))
          localRectF2.offsetTo(localRectF3.left - XulUtils.calRectWidth(localRectF2), localRectF3.top);
      while (true)
      {
        if (localRectF2 == null)
          break label444;
        return this._area.findSubFocusByDirection(paramFocusDirection, localRectF2, paramXulView);
        label212: if (paramFocusDirection == XulLayout.FocusDirection.MOVE_RIGHT)
        {
          XulUtils.offsetRect(localRectF5, -XulUtils.calRectWidth(localRectF6), XulUtils.calRectHeight(localRectF5));
          break;
        }
        localRectF5 = null;
        break;
        label244: if (paramFocusDirection == XulLayout.FocusDirection.MOVE_UP)
        {
          XulUtils.offsetRect(localRectF5, -XulUtils.calRectWidth(localRectF5), XulUtils.calRectHeight(localRectF6));
          break;
        }
        if (paramFocusDirection == XulLayout.FocusDirection.MOVE_DOWN)
        {
          XulUtils.offsetRect(localRectF5, XulUtils.calRectWidth(localRectF5), -XulUtils.calRectHeight(localRectF6));
          break;
        }
        localRectF5 = null;
        break;
        if ((this._isVertical) && (paramFocusDirection == XulLayout.FocusDirection.MOVE_DOWN))
        {
          localRectF2.offsetTo(localRectF3.left, localRectF3.top - XulUtils.calRectHeight(localRectF2));
        }
        else
        {
          localRectF2 = null;
          continue;
          if ((localXulView2 == paramXulView) || (paramXulView.isChildOf(localXulView2)))
          {
            RectF localRectF4 = localXulView1.getFocusRc();
            if ((!this._isVertical) && (paramFocusDirection == XulLayout.FocusDirection.MOVE_LEFT))
              localRectF2.offsetTo(localRectF4.right, localRectF4.top);
            else if ((this._isVertical) && (paramFocusDirection == XulLayout.FocusDirection.MOVE_UP))
              localRectF2.offsetTo(localRectF4.left, localRectF4.bottom);
            else
              localRectF2 = null;
          }
          else
          {
            localRectF2 = null;
          }
        }
      }
    }
    label444: RectF localRectF1 = XulDC._tmpFRc0;
    XulUtils.copyRect(localXulView1.getFocusRc(), localRectF1);
    if (this._isVertical)
      if ((localXulView1 == paramXulView) || (paramXulView.isChildOf(localXulView1)))
        if (paramFocusDirection == XulLayout.FocusDirection.MOVE_DOWN)
          localRectF1.offsetTo(localRectF1.left - XulUtils.calRectWidth(localRectF1), localRectF1.top);
    while (true)
    {
      return this._area.findSubFocusByDirection(paramFocusDirection, localRectF1, paramXulView);
      return null;
      if (paramFocusDirection == XulLayout.FocusDirection.MOVE_RIGHT)
      {
        localRectF1.offsetTo(paramRectF.left, localRectF1.top);
      }
      else
      {
        return null;
        if ((localXulView1 == paramXulView) || (paramXulView.isChildOf(localXulView1)))
        {
          if (paramFocusDirection == XulLayout.FocusDirection.MOVE_RIGHT)
            localRectF1.offsetTo(localRectF1.left, localRectF1.top - XulUtils.calRectHeight(localRectF1));
          else
            return null;
        }
        else
        {
          if (paramFocusDirection != XulLayout.FocusDirection.MOVE_DOWN)
            break;
          localRectF1.offsetTo(localRectF1.left, paramRectF.top);
        }
      }
    }
    return null;
  }

  protected class LayoutContainer extends XulViewContainerBaseRender.LayoutContainer
  {
    protected LayoutContainer()
    {
      super();
    }

    public int getLayoutMode()
    {
      if ((XulGridAreaRender.this._isRTL()) && (!XulGridAreaRender.this._isVertical))
        return 16777220;
      if (XulGridAreaRender.this._isVertical)
        return 8;
      return 4;
    }

    public int prepare()
    {
      super.prepare();
      XulGridAreaRender.this.syncDirection();
      return 0;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.XulGridAreaRender
 * JD-Core Version:    0.6.2
 */