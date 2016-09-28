package com.starcor.xul.Render;

import android.text.TextUtils;
import com.starcor.xul.Prop.XulAction;
import com.starcor.xul.Prop.XulPropNameCache.TagId;
import com.starcor.xul.Utils.XulLayoutHelper.ILayoutElement;
import com.starcor.xul.XulArea;
import com.starcor.xul.XulComponent;
import com.starcor.xul.XulLayout;
import com.starcor.xul.XulManager;
import com.starcor.xul.XulPage;
import com.starcor.xul.XulRenderContext;
import com.starcor.xul.XulView;

public class XulComponentRender extends XulAreaRender
{
  XulComponent _component;
  String _componentId;

  public XulComponentRender(XulRenderContext paramXulRenderContext, XulArea paramXulArea)
  {
    super(paramXulRenderContext, paramXulArea);
  }

  private void _cleanOldComponent()
  {
    if (this._componentId != null)
    {
      XulLayout localXulLayout = this._area.getRootLayout();
      XulView localXulView = localXulLayout.getFocus();
      if ((localXulView != null) && (localXulView.isChildOf(this._area)))
        localXulLayout.killFocus();
    }
    this._componentId = null;
    this._component = null;
    this._area.removeAllChildrenUpdateSelector();
  }

  private void _syncComponent(String paramString)
  {
    this._componentId = paramString;
    this._component = XulManager.getComponent(paramString);
    if (this._component == null);
    XulAction localXulAction;
    do
    {
      return;
      this._component.makeInstanceOn(this._area);
      this._area.prepareChildrenRender(getRenderContext());
      XulPage localXulPage = this._area.getOwnerPage();
      localXulPage.addSelectors(this._component);
      localXulPage.rebindView(this._area, getRenderContext().getDefaultActionCallback());
      localXulPage.addSelectorTargets(this._area);
      localXulPage.applySelectors(this._area);
      localXulAction = this._component.getAction(XulPropNameCache.TagId.ACTION_COMPONENT_INSTANCED);
    }
    while (localXulAction == null);
    XulPage.invokeActionNoPopup(this._area, localXulAction);
  }

  private void _syncComponentAttr()
  {
    if (!_isDataChanged());
    XulAction localXulAction;
    do
    {
      do
      {
        String str;
        do
        {
          do
          {
            return;
            str = this._area.getAttrString(XulPropNameCache.TagId.COMPONENT);
          }
          while (this._componentId == str);
          if (TextUtils.isEmpty(str))
          {
            _cleanOldComponent();
            return;
          }
        }
        while (str.equals(this._componentId));
        _cleanOldComponent();
        _syncComponent(str);
      }
      while (this._component == null);
      this._area.getOwnerPage();
      localXulAction = this._component.getAction(XulPropNameCache.TagId.ACTION_COMPONENT_CHANGED);
    }
    while (localXulAction == null);
    XulPage.invokeActionNoPopup(this._area, localXulAction);
  }

  public static void register()
  {
    XulRenderFactory.registerBuilder("area", "component", new XulRenderFactory.RenderBuilder()
    {
      static
      {
        if (!XulComponentRender.class.desiredAssertionStatus());
        for (boolean bool = true; ; bool = false)
        {
          $assertionsDisabled = bool;
          return;
        }
      }

      protected XulViewRender createRender(XulRenderContext paramAnonymousXulRenderContext, XulView paramAnonymousXulView)
      {
        assert ((paramAnonymousXulView instanceof XulArea));
        return new XulComponentRender(paramAnonymousXulRenderContext, (XulArea)paramAnonymousXulView);
      }
    });
  }

  protected XulLayoutHelper.ILayoutElement createElement()
  {
    return new ComponentLayoutContainer();
  }

  public void syncData()
  {
    if (!_isDataChanged())
      return;
    super.syncData();
    _syncComponentAttr();
  }

  protected class ComponentLayoutContainer extends XulAreaRender.LayoutContainer
  {
    protected ComponentLayoutContainer()
    {
      super();
    }

    public int prepare()
    {
      XulComponentRender.this._syncComponentAttr();
      return super.prepare();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Render.XulComponentRender
 * JD-Core Version:    0.6.2
 */