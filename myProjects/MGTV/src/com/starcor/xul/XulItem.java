package com.starcor.xul;

import com.starcor.xul.Factory.XulFactory.Attributes;
import com.starcor.xul.Factory.XulFactory.IPullParser;
import com.starcor.xul.Factory.XulFactory.ItemBuilder;
import com.starcor.xul.Prop.XulAction._Builder;
import com.starcor.xul.Prop.XulAttr._Builder;
import com.starcor.xul.Prop.XulData._Builder;
import com.starcor.xul.Prop.XulFocus._Builder;
import com.starcor.xul.Prop.XulStyle._Builder;
import com.starcor.xul.Render.XulImageRender;
import com.starcor.xul.Render.XulRenderFactory;
import com.starcor.xul.Script.XulScriptableObject;
import com.starcor.xul.ScriptWrappr.XulImageScriptableObject;
import com.starcor.xul.ScriptWrappr.XulItemScriptableObject;

public class XulItem extends XulView
{
  public static final XulItem KEEP_FOCUS = new XulItem();
  public static final XulItem NULL_FOCUS = new XulItem();

  public XulItem()
  {
  }

  public XulItem(XulArea paramXulArea)
  {
    super(paramXulArea);
    paramXulArea.addChild(this);
  }

  public XulItem(XulArea paramXulArea, int paramInt)
  {
    super(paramXulArea);
    paramXulArea.addChild(paramInt, this);
  }

  public XulItem(XulLayout paramXulLayout)
  {
    super(paramXulLayout);
    paramXulLayout.addChild(this);
  }

  public XulItem(XulLayout paramXulLayout, XulArea paramXulArea)
  {
    super(paramXulLayout, paramXulArea);
    paramXulArea.addChild(this);
  }

  protected XulScriptableObject createScriptableObject()
  {
    if (("image".equals(this._type)) || ((this._render instanceof XulImageRender)))
      return new XulImageScriptableObject(this);
    return new XulItemScriptableObject(this);
  }

  public XulItem makeClone(XulArea paramXulArea)
  {
    return makeClone(paramXulArea, -1);
  }

  public XulItem makeClone(XulArea paramXulArea, int paramInt)
  {
    XulItem localXulItem = new XulItem(paramXulArea, paramInt);
    localXulItem.copyContent(this);
    return localXulItem;
  }

  public void prepareRender(XulRenderContext paramXulRenderContext, boolean paramBoolean)
  {
    if (this._render != null)
      return;
    this._render = XulRenderFactory.createRender("item", this._type, paramXulRenderContext, this, paramBoolean);
  }

  public static class _Builder extends XulFactory.ItemBuilder
  {
    private static _Builder _recycled_builder;
    XulItem _item;
    XulTemplate _ownerTemplate;

    private static _Builder create()
    {
      _Builder local_Builder = _recycled_builder;
      if (local_Builder == null)
        return new _Builder();
      _recycled_builder = null;
      return local_Builder;
    }

    public static _Builder create(XulArea paramXulArea)
    {
      _Builder local_Builder = create();
      local_Builder.init(paramXulArea);
      return local_Builder;
    }

    public static _Builder create(XulLayout paramXulLayout)
    {
      _Builder local_Builder = create();
      local_Builder.init(paramXulLayout);
      return local_Builder;
    }

    public static _Builder create(XulTemplate paramXulTemplate)
    {
      _Builder local_Builder = create();
      local_Builder.init(paramXulTemplate);
      return local_Builder;
    }

    private void init(XulArea paramXulArea)
    {
      this._item = new XulItem(paramXulArea._root, paramXulArea);
    }

    private void init(XulLayout paramXulLayout)
    {
      this._item = new XulItem(paramXulLayout._root, paramXulLayout);
    }

    private void init(XulTemplate paramXulTemplate)
    {
      this._item = new XulItem();
      this._ownerTemplate = paramXulTemplate;
    }

    private static void recycle(_Builder param_Builder)
    {
      _recycled_builder = param_Builder;
      _recycled_builder._item = null;
      _recycled_builder._ownerTemplate = null;
    }

    public Object finalItem()
    {
      XulItem localXulItem = this._item;
      recycle(this);
      return localXulItem;
    }

    public boolean initialize(String paramString, XulFactory.Attributes paramAttributes)
    {
      this._item._id = paramAttributes.getValue("id");
      this._item.setClass(paramAttributes.getValue("class"));
      this._item._type = XulUtils.getCachedString(paramAttributes.getValue("type"));
      this._item._binding = XulUtils.getCachedString(paramAttributes.getValue("binding"));
      this._item._desc = paramAttributes.getValue("desc");
      if (this._ownerTemplate != null)
        this._ownerTemplate.addChild(this._item, paramAttributes.getValue("filter"));
      return true;
    }

    public XulFactory.ItemBuilder pushSubItem(XulFactory.IPullParser paramIPullParser, String paramString1, String paramString2, XulFactory.Attributes paramAttributes)
    {
      if ("action".equals(paramString2))
      {
        XulAction._Builder local_Builder4 = XulAction._Builder.create(this._item);
        local_Builder4.initialize(paramString2, paramAttributes);
        return local_Builder4;
      }
      if ("data".equals(paramString2))
      {
        XulData._Builder local_Builder3 = XulData._Builder.create(this._item);
        local_Builder3.initialize(paramString2, paramAttributes);
        return local_Builder3;
      }
      if ("attr".equals(paramString2))
      {
        XulAttr._Builder local_Builder2 = XulAttr._Builder.create(this._item);
        local_Builder2.initialize(paramString2, paramAttributes);
        return local_Builder2;
      }
      if ("style".equals(paramString2))
      {
        XulStyle._Builder local_Builder1 = XulStyle._Builder.create(this._item);
        local_Builder1.initialize(paramString2, paramAttributes);
        return local_Builder1;
      }
      if ("focus".equals(paramString2))
      {
        XulFocus._Builder local_Builder = XulFocus._Builder.create(this._item);
        local_Builder.initialize(paramString2, paramAttributes);
        return local_Builder;
      }
      return XulManager.CommonDummyBuilder;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.XulItem
 * JD-Core Version:    0.6.2
 */