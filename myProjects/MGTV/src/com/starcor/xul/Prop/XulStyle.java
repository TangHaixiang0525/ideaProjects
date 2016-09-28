package com.starcor.xul.Prop;

import com.starcor.xul.Factory.XulFactory.Attributes;
import com.starcor.xul.Factory.XulFactory.IPullParser;
import com.starcor.xul.Factory.XulFactory.ItemBuilder;
import com.starcor.xul.Factory.XulFactory.ItemBuilder.FinalCallback;
import com.starcor.xul.Utils.XulPropParser;
import com.starcor.xul.XulManager;
import com.starcor.xul.XulSelect;
import com.starcor.xul.XulView;

public class XulStyle extends XulProp
{
  Object _parsedVal;

  XulStyle()
  {
  }

  XulStyle(int paramInt)
  {
    this._nameId = paramInt;
  }

  XulStyle(XulStyle paramXulStyle)
  {
    super(paramXulStyle);
    this._parsedVal = paramXulStyle._parsedVal;
  }

  XulStyle(String paramString)
  {
    this._nameId = XulPropNameCache.name2Id(paramString);
  }

  public static XulStyle obtain(int paramInt)
  {
    XulStyle localXulStyle = new XulStyle(paramInt);
    localXulStyle._referent = false;
    return localXulStyle;
  }

  public static XulStyle obtain(String paramString)
  {
    XulStyle localXulStyle = new XulStyle(paramString);
    localXulStyle._referent = false;
    return localXulStyle;
  }

  public <T> T getParsedValue()
  {
    if (this._parsedVal == null)
      this._parsedVal = XulPropParser.parse(this);
    if (this._parsedVal == null)
      return null;
    return this._parsedVal;
  }

  public XulStyle makeClone()
  {
    if (isBinding())
      this = new XulStyle(this);
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
    XulFactory.ItemBuilder.FinalCallback<XulStyle> _callback;
    String _content;
    XulStyle _style;

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

    public static _Builder create(XulView paramXulView)
    {
      _Builder local_Builder = create();
      local_Builder.init(paramXulView);
      return local_Builder;
    }

    private void init(final XulSelect paramXulSelect)
    {
      this._style = new XulStyle();
      this._callback = new XulFactory.ItemBuilder.FinalCallback()
      {
        public void onFinal(XulStyle paramAnonymousXulStyle)
        {
          paramXulSelect.addProp(paramAnonymousXulStyle);
        }
      };
      this._content = null;
    }

    private void init(final XulView paramXulView)
    {
      this._style = new XulStyle();
      this._callback = new XulFactory.ItemBuilder.FinalCallback()
      {
        public void onFinal(XulStyle paramAnonymousXulStyle)
        {
          paramXulView.addInplaceProp(paramAnonymousXulStyle);
        }
      };
      this._content = null;
    }

    private static void recycle(_Builder param_Builder)
    {
      _recycled_builder = param_Builder;
      _recycled_builder._callback = null;
      _recycled_builder._style = null;
      _recycled_builder._content = null;
    }

    public Object finalItem()
    {
      if (this._content != null)
      {
        this._style.setValue(this._content);
        this._content = null;
      }
      XulStyle localXulStyle = this._style;
      XulFactory.ItemBuilder.FinalCallback localFinalCallback = this._callback;
      recycle(this);
      localFinalCallback.onFinal(localXulStyle);
      return localXulStyle;
    }

    public boolean initialize(String paramString, XulFactory.Attributes paramAttributes)
    {
      this._style._nameId = XulPropNameCache.name2Id(paramAttributes.getValue("name"));
      this._style._desc = paramAttributes.getValue("desc");
      this._style._binding = paramAttributes.getValue("binding");
      return true;
    }

    public XulFactory.ItemBuilder pushSubItem(XulFactory.IPullParser paramIPullParser, String paramString1, String paramString2, XulFactory.Attributes paramAttributes)
    {
      return XulManager.CommonDummyBuilder;
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
 * Qualified Name:     com.starcor.xul.Prop.XulStyle
 * JD-Core Version:    0.6.2
 */