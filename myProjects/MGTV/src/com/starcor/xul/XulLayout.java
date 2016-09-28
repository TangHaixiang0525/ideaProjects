package com.starcor.xul;

import android.graphics.RectF;
import android.util.Log;
import android.view.KeyEvent;
import com.starcor.xul.Factory.XulFactory.Attributes;
import com.starcor.xul.Factory.XulFactory.IPullParser;
import com.starcor.xul.Factory.XulFactory.ItemBuilder;
import com.starcor.xul.Prop.XulAction._Builder;
import com.starcor.xul.Prop.XulAttr;
import com.starcor.xul.Prop.XulAttr._Builder;
import com.starcor.xul.Prop.XulData._Builder;
import com.starcor.xul.Prop.XulFocus._Builder;
import com.starcor.xul.Prop.XulStyle._Builder;
import java.util.ArrayList;

public class XulLayout extends XulArea
{
  private static final String TAG = XulLayout.class.getSimpleName();
  private static FindAnyFocusIterator _findAnyFocusIterator = new FindAnyFocusIterator(null);
  XulView _focus;
  XulPage _page;

  private XulLayout(XulPage paramXulPage)
  {
    this._root = this;
    this._page = paramXulPage;
    this._page.addLayout(this);
  }

  private void _findDefaultFocus(XulArea paramXulArea)
  {
    if (!paramXulArea.isEnabled())
      return;
    paramXulArea.eachChild(new XulArea.XulAreaIterator()
    {
      public boolean onXulArea(int paramAnonymousInt, XulArea paramAnonymousXulArea)
      {
        boolean bool = true;
        if (XulLayout.this._focus != null)
          bool = false;
        while ((!paramAnonymousXulArea.isEnabled()) || (!paramAnonymousXulArea.isVisible()))
          return bool;
        if ((paramAnonymousXulArea.focusable()) && (paramAnonymousXulArea.isDefaultFocus()))
        {
          XulLayout.this.requestFocus(paramAnonymousXulArea);
          return bool;
        }
        XulLayout.this._findDefaultFocus(paramAnonymousXulArea);
        return bool;
      }

      public boolean onXulItem(int paramAnonymousInt, XulItem paramAnonymousXulItem)
      {
        boolean bool = true;
        if (XulLayout.this._focus != null)
          bool = false;
        while ((!paramAnonymousXulItem.isEnabled()) || (!paramAnonymousXulItem.isVisible()) || (!paramAnonymousXulItem.focusable()) || (!paramAnonymousXulItem.isDefaultFocus()))
          return bool;
        XulLayout.this.requestFocus(paramAnonymousXulItem);
        return bool;
      }

      public boolean onXulTemplate(int paramAnonymousInt, XulTemplate paramAnonymousXulTemplate)
      {
        return true;
      }
    });
  }

  void addSelectorTarget(XulView paramXulView)
  {
    if ((paramXulView instanceof XulArea))
    {
      this._page.addSelectorTargets((XulArea)paramXulView);
      return;
    }
    this._page.addSelectorTarget(paramXulView, paramXulView.getSelectKeys());
  }

  void addSelectorTarget(XulView paramXulView, ArrayList<String> paramArrayList)
  {
    this._page.addSelectorTarget(paramXulView, paramArrayList);
  }

  void addSelectorTargets(XulArea paramXulArea)
  {
    this._page.addSelectorTargets(paramXulArea);
  }

  public void applySelectors(XulArea paramXulArea)
  {
    this._page.applySelectors(paramXulArea);
  }

  public void applySelectors(XulView paramXulView)
  {
    if ((paramXulView instanceof XulArea))
    {
      this._page.applySelectors((XulArea)paramXulView);
      return;
    }
    this._page.applySelectors((XulItem)paramXulView);
  }

  boolean applySelectors(XulView paramXulView, ArrayList<String> paramArrayList)
  {
    return this._page.applySelectors(paramXulView, paramArrayList);
  }

  public boolean doClick(XulPage.IActionCallback paramIActionCallback)
  {
    if (this._focus == null)
      return false;
    return XulPage.invokeAction(this._focus, "click", paramIActionCallback);
  }

  public XulView getFocus()
  {
    return this._focus;
  }

  public int getHeight()
  {
    XulAttr localXulAttr = getAttr("height");
    if ((localXulAttr == null) || (localXulAttr.getValue() == null))
      return this._page.getDesignPageHeight();
    String str = localXulAttr.getStringValue();
    if ("match_parent".equals(str))
      return this._page.getDesignPageHeight();
    return XulUtils.tryParseInt(str, this._page.getDesignPageHeight());
  }

  public XulPage getOwnerPage()
  {
    return this._page;
  }

  public int getWidth()
  {
    XulAttr localXulAttr = getAttr("width");
    if ((localXulAttr == null) || (localXulAttr.getValue() == null))
      return this._page.getDesignPageWidth();
    String str = localXulAttr.getStringValue();
    if ("match_parent".equals(str))
      return this._page.getDesignPageWidth();
    return XulUtils.tryParseInt(str, this._page.getDesignPageWidth());
  }

  public boolean hasFocus()
  {
    return this._focus != null;
  }

  public void initFocus()
  {
    if (this._focus != null)
      return;
    _findDefaultFocus(this);
  }

  public boolean isDiscarded()
  {
    return false;
  }

  public boolean isFocused(XulView paramXulView)
  {
    return this._focus == paramXulView;
  }

  public void killFocus()
  {
    requestFocus(null);
  }

  public XulLayout makeClone(XulPage paramXulPage)
  {
    XulLayout localXulLayout = new XulLayout(paramXulPage);
    localXulLayout.copyContent(this);
    int i = this._children.size();
    int j = 0;
    if (j < i)
    {
      Object localObject = this._children.get(j);
      if ((localObject instanceof XulArea))
        ((XulArea)localObject).makeClone(localXulLayout);
      while (true)
      {
        j++;
        break;
        if ((localObject instanceof XulTemplate))
          ((XulTemplate)localObject).makeClone(localXulLayout);
        else if ((localObject instanceof XulItem))
          ((XulItem)localObject).makeClone(localXulLayout);
        else
          Log.d(TAG, "unsupported children type!!! - " + localObject.getClass().getSimpleName());
      }
    }
    return localXulLayout;
  }

  public boolean moveFocus(FocusDirection paramFocusDirection)
  {
    XulView localXulView1 = this._focus;
    if (localXulView1 == null)
    {
      eachChild(_findAnyFocusIterator);
      requestFocus(_findAnyFocusIterator.getFocus());
      return this._focus != null;
    }
    RectF localRectF = localXulView1.getFocusRc();
    XulView localXulView2 = localXulView1._parent.findFocus(paramFocusDirection, localRectF, localXulView1);
    if (localXulView2 == null)
      return false;
    requestFocus(localXulView2);
    return true;
  }

  public boolean onKeyEvent(KeyEvent paramKeyEvent)
  {
    if (this._focus == null)
      return false;
    return this._focus.onKeyEvent(paramKeyEvent);
  }

  void removeSelectorTarget(XulView paramXulView, ArrayList<String> paramArrayList)
  {
    this._page.removeSelectorTarget(paramXulView, paramArrayList);
  }

  public void requestFocus(XulView paramXulView)
  {
    if (this._focus == paramXulView);
    XulView localXulView2;
    do
    {
      XulView localXulView1;
      do
      {
        do
        {
          return;
          localXulView1 = this._focus;
          if ((paramXulView == null) || (paramXulView.focusable()))
            break;
        }
        while ((!(paramXulView instanceof XulArea)) || ((localXulView1 != null) && (localXulView1.isChildOf(paramXulView))));
        XulArea localXulArea = (XulArea)paramXulView;
        paramXulView = localXulArea.getDynamicFocus();
        if (paramXulView == null)
        {
          localXulArea.eachChild(_findAnyFocusIterator);
          paramXulView = _findAnyFocusIterator.getFocus();
        }
      }
      while (paramXulView == null);
      this._focus = paramXulView;
      localXulView2 = paramXulView;
      if (localXulView1 != null)
      {
        localXulView1.onBlur();
        localXulView1.resetRender();
      }
      if (localXulView2 != null)
      {
        localXulView2.onFocus();
        localXulView2.resetRender();
      }
      if (localXulView1 != null)
        XulPage.invokeActionNoPopup(localXulView1, "blur");
    }
    while (localXulView2 == null);
    XulPage.invokeActionNoPopup(localXulView2, "focus");
  }

  boolean unApplySelectors(XulView paramXulView, ArrayList<String> paramArrayList)
  {
    return this._page.unApplySelectors(paramXulView, paramArrayList);
  }

  private static class FindAnyFocusIterator extends XulArea.XulAreaIterator
  {
    private XulView _newFocus = null;

    public XulView getFocus()
    {
      XulView localXulView = this._newFocus;
      this._newFocus = null;
      return localXulView;
    }

    public boolean onXulArea(int paramInt, XulArea paramXulArea)
    {
      if (this._newFocus != null)
        return false;
      if ((!paramXulArea.isEnabled()) || (!paramXulArea.isVisible()))
        return true;
      if (paramXulArea.focusable())
        this._newFocus = paramXulArea;
      while (true)
      {
        return true;
        XulView localXulView = paramXulArea.getDynamicFocus();
        if (localXulView != null)
        {
          this._newFocus = localXulView;
          return false;
        }
        paramXulArea.eachChild(this);
      }
    }

    public boolean onXulItem(int paramInt, XulItem paramXulItem)
    {
      boolean bool = true;
      if (this._newFocus != null)
        bool = false;
      while ((!paramXulItem.isEnabled()) || (!paramXulItem.isVisible()) || (!paramXulItem.focusable()))
        return bool;
      this._newFocus = paramXulItem;
      return bool;
    }
  }

  public static enum FocusDirection
  {
    static
    {
      MOVE_DOWN = new FocusDirection("MOVE_DOWN", 3);
      FocusDirection[] arrayOfFocusDirection = new FocusDirection[4];
      arrayOfFocusDirection[0] = MOVE_LEFT;
      arrayOfFocusDirection[1] = MOVE_RIGHT;
      arrayOfFocusDirection[2] = MOVE_UP;
      arrayOfFocusDirection[3] = MOVE_DOWN;
    }
  }

  public static class _Builder extends XulFactory.ItemBuilder
  {
    private static _Builder _recycled_builder;
    XulLayout _layout;

    private static _Builder create()
    {
      _Builder local_Builder = _recycled_builder;
      if (local_Builder == null)
        return new _Builder();
      _recycled_builder = null;
      return local_Builder;
    }

    public static _Builder create(XulPage paramXulPage)
    {
      _Builder local_Builder = create();
      local_Builder.init(paramXulPage);
      return local_Builder;
    }

    private void init(XulPage paramXulPage)
    {
      this._layout = new XulLayout(paramXulPage, null);
    }

    private static void recycle(_Builder param_Builder)
    {
      _recycled_builder = param_Builder;
      _recycled_builder._layout = null;
    }

    public Object finalItem()
    {
      XulLayout localXulLayout = this._layout;
      recycle(this);
      return localXulLayout;
    }

    public boolean initialize(String paramString, XulFactory.Attributes paramAttributes)
    {
      this._layout._id = paramAttributes.getValue("id");
      this._layout.setClass(paramAttributes.getValue("class"));
      this._layout._binding = paramAttributes.getValue("binding");
      this._layout._desc = paramAttributes.getValue("desc");
      return true;
    }

    public XulFactory.ItemBuilder pushSubItem(XulFactory.IPullParser paramIPullParser, String paramString1, String paramString2, XulFactory.Attributes paramAttributes)
    {
      if (paramString2.equals("area"))
      {
        XulArea._Builder local_Builder7 = XulArea._Builder.create(this._layout);
        local_Builder7.initialize(paramString2, paramAttributes);
        return local_Builder7;
      }
      if (paramString2.equals("item"))
      {
        XulItem._Builder local_Builder6 = XulItem._Builder.create(this._layout);
        local_Builder6.initialize(paramString2, paramAttributes);
        return local_Builder6;
      }
      if (paramString2.equals("template"))
      {
        XulTemplate._Builder local_Builder5 = XulTemplate._Builder.create(this._layout);
        local_Builder5.initialize(paramString2, paramAttributes);
        return local_Builder5;
      }
      if ("action".equals(paramString2))
      {
        XulAction._Builder local_Builder4 = XulAction._Builder.create(this._layout);
        local_Builder4.initialize(paramString2, paramAttributes);
        return local_Builder4;
      }
      if ("data".equals(paramString2))
      {
        XulData._Builder local_Builder3 = XulData._Builder.create(this._layout);
        local_Builder3.initialize(paramString2, paramAttributes);
        return local_Builder3;
      }
      if ("attr".equals(paramString2))
      {
        XulAttr._Builder local_Builder2 = XulAttr._Builder.create(this._layout);
        local_Builder2.initialize(paramString2, paramAttributes);
        return local_Builder2;
      }
      if ("style".equals(paramString2))
      {
        XulStyle._Builder local_Builder1 = XulStyle._Builder.create(this._layout);
        local_Builder1.initialize(paramString2, paramAttributes);
        return local_Builder1;
      }
      if ("focus".equals(paramString2))
      {
        XulFocus._Builder local_Builder = XulFocus._Builder.create(this._layout);
        local_Builder.initialize(paramString2, paramAttributes);
        return local_Builder;
      }
      return XulManager.CommonDummyBuilder;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.XulLayout
 * JD-Core Version:    0.6.2
 */