package com.starcor.xul.Prop;

import com.starcor.xul.Factory.XulFactory.Attributes;
import com.starcor.xul.Factory.XulFactory.IPullParser;
import com.starcor.xul.Factory.XulFactory.ItemBuilder;
import com.starcor.xul.Factory.XulFactory.ItemBuilder.FinalCallback;
import com.starcor.xul.Utils.XulPropParser;
import com.starcor.xul.XulDataNode;
import com.starcor.xul.XulDataNode._Builder;
import com.starcor.xul.XulSelect;
import com.starcor.xul.XulTemplate;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulView;
import java.util.ArrayList;

public class XulAttr extends XulProp
{
  Object _parsedVal;

  XulAttr()
  {
  }

  XulAttr(int paramInt)
  {
    this._nameId = paramInt;
  }

  XulAttr(XulAttr paramXulAttr)
  {
    super(paramXulAttr);
    this._parsedVal = paramXulAttr._parsedVal;
  }

  XulAttr(String paramString)
  {
    this._nameId = XulPropNameCache.name2Id(paramString);
  }

  public static XulAttr obtain(int paramInt)
  {
    XulAttr localXulAttr = new XulAttr(paramInt);
    localXulAttr._referent = false;
    return localXulAttr;
  }

  public static XulAttr obtain(String paramString)
  {
    XulAttr localXulAttr = new XulAttr(paramString);
    localXulAttr._referent = false;
    return localXulAttr;
  }

  public <T> T getParsedValue()
  {
    if (this._parsedVal == null)
      this._parsedVal = XulPropParser.parse(this);
    if (this._parsedVal == null)
      return null;
    return this._parsedVal;
  }

  public XulAttr makeClone()
  {
    if (isBinding())
      this = new XulAttr(this);
    return this;
  }

  public void setValue(Object paramObject)
  {
    this._parsedVal = null;
    super.setValue(paramObject);
  }

  public static class _Builder extends XulFactory.ItemBuilder
  {
    private static _Builder _recycled_builder;
    XulAttr _attr;
    XulFactory.ItemBuilder.FinalCallback<XulAttr> _callback;
    String _content;
    ArrayList<XulDataNode> _dataContent;
    XulFactory.ItemBuilder.FinalCallback<XulDataNode> _xulDataNodeCallback = new XulFactory.ItemBuilder.FinalCallback()
    {
      public void onFinal(XulDataNode paramAnonymousXulDataNode)
      {
        if (XulAttr._Builder.this._dataContent == null)
          XulAttr._Builder.this._dataContent = new ArrayList();
        XulAttr._Builder.this._dataContent.add(paramAnonymousXulDataNode);
      }
    };

    private static _Builder create()
    {
      _Builder local_Builder = _recycled_builder;
      if (local_Builder == null)
        return new _Builder();
      _recycled_builder = null;
      return local_Builder;
    }

    public static _Builder create(XulSelect paramXulSelect)
    {
      _Builder local_Builder = create();
      local_Builder.init(paramXulSelect);
      return local_Builder;
    }

    public static _Builder create(XulTemplate paramXulTemplate)
    {
      _Builder local_Builder = create();
      local_Builder.init(paramXulTemplate);
      return local_Builder;
    }

    public static _Builder create(XulView paramXulView)
    {
      _Builder local_Builder = create();
      local_Builder.init(paramXulView);
      return local_Builder;
    }

    private void init(final XulSelect paramXulSelect)
    {
      this._attr = new XulAttr();
      this._callback = new XulFactory.ItemBuilder.FinalCallback()
      {
        public void onFinal(XulAttr paramAnonymousXulAttr)
        {
          paramXulSelect.addProp(paramAnonymousXulAttr);
        }
      };
      this._content = null;
      this._dataContent = null;
    }

    private void init(final XulTemplate paramXulTemplate)
    {
      this._attr = new XulAttr();
      this._callback = new XulFactory.ItemBuilder.FinalCallback()
      {
        public void onFinal(XulAttr paramAnonymousXulAttr)
        {
          paramXulTemplate.addProp(paramAnonymousXulAttr);
        }
      };
      this._content = null;
      this._dataContent = null;
    }

    private void init(final XulView paramXulView)
    {
      this._attr = new XulAttr();
      this._callback = new XulFactory.ItemBuilder.FinalCallback()
      {
        public void onFinal(XulAttr paramAnonymousXulAttr)
        {
          paramXulView.addInplaceProp(paramAnonymousXulAttr);
        }
      };
      this._content = null;
      this._dataContent = null;
    }

    private static void recycle(_Builder param_Builder)
    {
      _recycled_builder = param_Builder;
      _recycled_builder._callback = null;
      _recycled_builder._attr = null;
      _recycled_builder._content = null;
      _recycled_builder._dataContent = null;
    }

    public Object finalItem()
    {
      if (this._dataContent != null)
        this._attr.setValue(this._dataContent);
      while (true)
      {
        XulAttr localXulAttr = this._attr;
        XulFactory.ItemBuilder.FinalCallback localFinalCallback = this._callback;
        recycle(this);
        localFinalCallback.onFinal(localXulAttr);
        return localXulAttr;
        if (this._content != null)
          this._attr._value = XulUtils.getCachedString(this._content);
      }
    }

    public boolean initialize(String paramString, XulFactory.Attributes paramAttributes)
    {
      this._attr._nameId = XulPropNameCache.name2Id(paramAttributes.getValue("name"));
      this._attr._desc = paramAttributes.getValue("desc");
      this._attr._binding = XulUtils.getCachedString(paramAttributes.getValue("binding"));
      return true;
    }

    public XulFactory.ItemBuilder pushSubItem(XulFactory.IPullParser paramIPullParser, String paramString1, String paramString2, XulFactory.Attributes paramAttributes)
    {
      XulDataNode._Builder local_Builder = XulDataNode._Builder.create(paramString2, this._xulDataNodeCallback);
      local_Builder.initialize(paramString2, paramAttributes);
      return local_Builder;
    }

    public boolean pushText(String paramString, XulFactory.IPullParser paramIPullParser)
    {
      if (this._content == null);
      for (this._content = paramIPullParser.getText(); ; this._content += paramIPullParser.getText())
        return true;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Prop.XulAttr
 * JD-Core Version:    0.6.2
 */