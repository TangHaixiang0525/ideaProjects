package com.starcor.xul.Prop;

import com.starcor.xul.Factory.XulFactory.Attributes;
import com.starcor.xul.Factory.XulFactory.IPullParser;
import com.starcor.xul.Factory.XulFactory.ItemBuilder;
import com.starcor.xul.Factory.XulFactory.ItemBuilder.FinalCallback;
import com.starcor.xul.XulDataNode;
import com.starcor.xul.XulDataNode._Builder;
import com.starcor.xul.XulSelect;
import com.starcor.xul.XulUtils;
import com.starcor.xul.XulView;
import java.util.ArrayList;

public class XulData extends XulProp
{
  public XulData()
  {
  }

  public XulData(XulData paramXulData)
  {
    super(paramXulData);
  }

  public XulData makeClone()
  {
    return new XulData(this);
  }

  public static class _Builder extends XulFactory.ItemBuilder
  {
    private static _Builder _recycled_builder;
    XulFactory.ItemBuilder.FinalCallback<XulData> _callback;
    XulData _data;
    ArrayList<XulDataNode> _dataContent;
    String _text;
    XulFactory.ItemBuilder.FinalCallback<XulDataNode> _xulDataNodeCallback = new XulFactory.ItemBuilder.FinalCallback()
    {
      public void onFinal(XulDataNode paramAnonymousXulDataNode)
      {
        if (XulData._Builder.this._dataContent == null)
          XulData._Builder.this._dataContent = new ArrayList();
        XulData._Builder.this._dataContent.add(paramAnonymousXulDataNode);
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

    public static _Builder create(XulView paramXulView)
    {
      _Builder local_Builder = create();
      local_Builder.init(paramXulView);
      return local_Builder;
    }

    private void init(final XulSelect paramXulSelect)
    {
      this._data = new XulData();
      this._callback = new XulFactory.ItemBuilder.FinalCallback()
      {
        public void onFinal(XulData paramAnonymousXulData)
        {
          paramXulSelect.addProp(paramAnonymousXulData);
        }
      };
      this._text = null;
      this._dataContent = null;
    }

    private void init(final XulView paramXulView)
    {
      this._data = new XulData();
      this._callback = new XulFactory.ItemBuilder.FinalCallback()
      {
        public void onFinal(XulData paramAnonymousXulData)
        {
          paramXulView.addInplaceProp(paramAnonymousXulData);
        }
      };
    }

    private static void recycle(_Builder param_Builder)
    {
      _recycled_builder = param_Builder;
      _recycled_builder._callback = null;
      _recycled_builder._data = null;
      _recycled_builder._text = null;
      _recycled_builder._dataContent = null;
    }

    public Object finalItem()
    {
      if (this._dataContent != null)
        this._data.setValue(this._dataContent);
      while (true)
      {
        XulData localXulData = this._data;
        XulFactory.ItemBuilder.FinalCallback localFinalCallback = this._callback;
        recycle(this);
        localFinalCallback.onFinal(localXulData);
        return localXulData;
        if (this._text != null)
          this._data.setValue(XulUtils.getCachedString(this._text));
      }
    }

    public boolean initialize(String paramString, XulFactory.Attributes paramAttributes)
    {
      this._data._nameId = XulPropNameCache.name2Id(paramAttributes.getValue("name"));
      this._data._desc = paramAttributes.getValue("desc");
      this._data._binding = XulUtils.getCachedString(paramAttributes.getValue("binding"));
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
      if (this._text == null);
      for (this._text = paramIPullParser.getText(); ; this._text += paramIPullParser.getText())
        return true;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Prop.XulData
 * JD-Core Version:    0.6.2
 */