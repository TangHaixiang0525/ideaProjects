package com.starcor.xul.Render;

import android.graphics.Rect;
import com.starcor.xul.Graphics.XulDC;
import com.starcor.xul.Utils.XulAreaChildrenRender;
import com.starcor.xul.Utils.XulAreaChildrenVisibleChangeNotifier;
import com.starcor.xul.Utils.XulLayoutHelper.ILayoutElement;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulView;

public class XulAreaRender extends XulViewContainerBaseRender
{
  protected XulAreaChildrenRender _childrenRender = createChildrenRender();

  public XulAreaRender(XulRenderContext paramXulRenderContext, XulArea paramXulArea)
  {
    super(paramXulRenderContext, paramXulArea);
  }

  public static void register()
  {
    XulRenderFactory.registerBuilder("area", "*", new XulRenderFactory.RenderBuilder()
    {
      static
      {
        if (!XulAreaRender.class.desiredAssertionStatus());
        for (boolean bool = true; ; bool = false)
        {
          $assertionsDisabled = bool;
          return;
        }
      }

      protected XulViewRender createRender(XulRenderContext paramAnonymousXulRenderContext, XulView paramAnonymousXulView)
      {
        assert ((paramAnonymousXulView instanceof XulArea));
        return new XulAreaRender(paramAnonymousXulRenderContext, (XulArea)paramAnonymousXulView);
      }
    });
  }

  protected XulAreaChildrenRender createChildrenRender()
  {
    return new XulAreaChildrenRender();
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
    this._childrenRender.init(paramXulDC, paramRect, paramInt1, paramInt2);
    this._area.eachView(this._childrenRender);
  }

  public int getDefaultFocusMode()
  {
    return 37;
  }

  public boolean hitTest(int paramInt, float paramFloat1, float paramFloat2)
  {
    return super.hitTest(-1, paramFloat1, paramFloat2);
  }

  public void onVisibilityChanged(boolean paramBoolean, XulView paramXulView)
  {
    super.onVisibilityChanged(paramBoolean, paramXulView);
    XulAreaChildrenVisibleChangeNotifier localXulAreaChildrenVisibleChangeNotifier = XulAreaChildrenVisibleChangeNotifier.getNotifier();
    localXulAreaChildrenVisibleChangeNotifier.begin(paramBoolean, (XulArea)paramXulView);
    this._area.eachChild(localXulAreaChildrenVisibleChangeNotifier);
    localXulAreaChildrenVisibleChangeNotifier.end();
  }

  protected class LayoutContainer extends XulViewContainerBaseRender.LayoutContainer
  {
    protected LayoutContainer()
    {
      super();
    }

    public Rect getMargin()
    {
      if (XulAreaRender.this._margin == null)
      {
        if ((XulAreaRender.this._area.getType() == "@@@@template-container@@@@") && (XulAreaRender.this._area.getChildNum() == 1))
        {
          XulView localXulView = XulAreaRender.this._area.getChild(0);
          if (localXulView != null)
            return localXulView.getRender().getLayoutElement().getMargin();
        }
        return super.getMargin();
      }
      return XulAreaRender.this._margin;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.XulAreaRender
 * JD-Core Version:    0.6.2
 */