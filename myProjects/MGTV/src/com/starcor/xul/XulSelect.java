package com.starcor.xul;

import android.text.TextUtils;
import com.starcor.xul.Factory.XulFactory.Attributes;
import com.starcor.xul.Factory.XulFactory.IPullParser;
import com.starcor.xul.Factory.XulFactory.ItemBuilder;
import com.starcor.xul.Factory.XulFactory.ResultBuilderContext;
import com.starcor.xul.Prop.XulAction;
import com.starcor.xul.Prop.XulAction._Builder;
import com.starcor.xul.Prop.XulAttr;
import com.starcor.xul.Prop.XulAttr._Builder;
import com.starcor.xul.Prop.XulData._Builder;
import com.starcor.xul.Prop.XulFocus;
import com.starcor.xul.Prop.XulFocus._Builder;
import com.starcor.xul.Prop.XulProp;
import com.starcor.xul.Prop.XulStyle;
import com.starcor.xul.Prop.XulStyle._Builder;
import java.util.ArrayList;

public class XulSelect
{
  String _class;
  String _id;
  ArrayList<XulProp> _prop = new ArrayList();
  String _selectKey;
  ArrayList<XulSelect> _selectors;
  int _state = -1;
  String _type;

  private void addSelector(XulSelect paramXulSelect)
  {
    if (this._selectors == null)
      this._selectors = new ArrayList();
    this._selectors.add(paramXulSelect);
  }

  public void addProp(XulProp paramXulProp)
  {
    this._prop.add(paramXulProp);
  }

  public void apply(XulView paramXulView)
  {
    int i = this._prop.size();
    int j = 0;
    if (j < i)
    {
      XulProp localXulProp = (XulProp)this._prop.get(j);
      if ((localXulProp instanceof XulAttr))
        paramXulView.addIndirectProp((XulAttr)localXulProp, this._state);
      while (true)
      {
        j++;
        break;
        if ((localXulProp instanceof XulStyle))
          paramXulView.addIndirectProp((XulStyle)localXulProp, this._state);
        else if ((localXulProp instanceof XulAction))
          paramXulView.addIndirectProp((XulAction)localXulProp, this._state);
        else if ((localXulProp instanceof XulFocus))
          paramXulView.addIndirectProp((XulFocus)localXulProp);
      }
    }
  }

  public String getSelectKey()
  {
    if (this._selectKey != null)
      return this._selectKey;
    StringBuilder localStringBuilder = new StringBuilder();
    if (!TextUtils.isEmpty(this._id))
      localStringBuilder.append("#").append(this._id);
    if (!TextUtils.isEmpty(this._class))
      localStringBuilder.append(".").append(this._class);
    if (!TextUtils.isEmpty(this._type))
      localStringBuilder.append("@").append(this._type);
    this._selectKey = localStringBuilder.toString();
    return this._selectKey;
  }

  public void setPriorityLevel(int paramInt1, int paramInt2)
  {
    int i;
    int k;
    label30: int n;
    label50: int i1;
    int i3;
    if (TextUtils.isEmpty(this._id))
    {
      i = 0;
      int j = 0 + i;
      if (!TextUtils.isEmpty(this._class))
        break label128;
      k = 0;
      int m = j + k;
      if (!TextUtils.isEmpty(this._type))
        break label134;
      n = 0;
      i1 = m + n;
      int i2 = this._state;
      i3 = 0;
      if (i2 > 0)
        break label140;
    }
    while (true)
    {
      int i4 = paramInt1 + 65536 * (i1 + i3);
      for (int i5 = 0; i5 < this._prop.size(); i5++)
        ((XulProp)this._prop.get(i5)).setPriority(i4 + paramInt2);
      i = 1;
      break;
      label128: k = 1;
      break label30;
      label134: n = 1;
      break label50;
      label140: i3 = 1;
    }
  }

  public void unApply(XulView paramXulView)
  {
    int i = 0;
    if (i < this._prop.size())
    {
      XulProp localXulProp = (XulProp)this._prop.get(i);
      if ((localXulProp instanceof XulAttr))
        paramXulView.removeIndirectProp((XulAttr)localXulProp, this._state);
      while (true)
      {
        i++;
        break;
        if ((localXulProp instanceof XulStyle))
          paramXulView.removeIndirectProp((XulStyle)localXulProp, this._state);
        else if ((localXulProp instanceof XulAction))
          paramXulView.removeIndirectProp((XulAction)localXulProp, this._state);
        else if ((localXulProp instanceof XulFocus))
          paramXulView.removeIndirectProp((XulFocus)localXulProp);
      }
    }
  }

  public static class _Builder extends XulFactory.ItemBuilder
  {
    private static _Builder _recycled_builder;
    XulSelect _select;

    private static _Builder create()
    {
      _Builder local_Builder = _recycled_builder;
      if (local_Builder == null)
        return new _Builder();
      _recycled_builder = null;
      return local_Builder;
    }

    public static _Builder create(XulFactory.ResultBuilderContext paramResultBuilderContext, XulManager paramXulManager)
    {
      _Builder local_Builder = create();
      local_Builder.init(paramXulManager);
      return local_Builder;
    }

    public static _Builder create(XulFocus paramXulFocus, String paramString)
    {
      _Builder local_Builder = create();
      local_Builder.init(paramXulFocus, paramString);
      return local_Builder;
    }

    public static _Builder create(XulComponent paramXulComponent)
    {
      _Builder local_Builder = create();
      local_Builder.init(paramXulComponent);
      return local_Builder;
    }

    public static _Builder create(XulPage paramXulPage)
    {
      _Builder local_Builder = create();
      local_Builder.init(paramXulPage);
      return local_Builder;
    }

    public static _Builder create(XulSelect paramXulSelect)
    {
      _Builder local_Builder = create();
      local_Builder.init(paramXulSelect);
      return local_Builder;
    }

    private void init(XulFocus paramXulFocus, String paramString)
    {
      this._select = new XulSelect();
      paramXulFocus.bindNextFocus(paramString, this._select);
    }

    private void init(XulComponent paramXulComponent)
    {
      this._select = new XulSelect();
      paramXulComponent.addSelector(this._select);
    }

    private void init(XulManager paramXulManager)
    {
      this._select = new XulSelect();
      paramXulManager.addSelector(this._select);
    }

    private void init(XulPage paramXulPage)
    {
      this._select = new XulSelect();
      paramXulPage.addSelector(this._select);
    }

    private void init(XulSelect paramXulSelect)
    {
      this._select = new XulSelect();
      paramXulSelect.addSelector(this._select);
    }

    private static void recycle(_Builder param_Builder)
    {
      _recycled_builder = param_Builder;
      _recycled_builder._select = null;
    }

    public Object finalItem()
    {
      XulSelect localXulSelect = this._select;
      recycle(this);
      return localXulSelect;
    }

    public boolean initialize(String paramString, XulFactory.Attributes paramAttributes)
    {
      this._select._id = paramAttributes.getValue("id");
      this._select._class = XulUtils.getCachedString(paramAttributes.getValue("class"));
      this._select._type = XulUtils.getCachedString(paramAttributes.getValue("type"));
      this._select._state = XulView.stateFromString(paramAttributes.getValue("state"));
      return true;
    }

    public XulFactory.ItemBuilder pushSubItem(XulFactory.IPullParser paramIPullParser, String paramString1, String paramString2, XulFactory.Attributes paramAttributes)
    {
      if ("select".equals(paramString2))
      {
        _Builder local_Builder5 = create(this._select);
        local_Builder5.initialize(paramString2, paramAttributes);
        return local_Builder5;
      }
      if ("action".equals(paramString2))
      {
        XulAction._Builder local_Builder4 = XulAction._Builder.create(this._select);
        local_Builder4.initialize(paramString2, paramAttributes);
        return local_Builder4;
      }
      if ("data".equals(paramString2))
      {
        XulData._Builder local_Builder3 = XulData._Builder.create(this._select);
        local_Builder3.initialize(paramString2, paramAttributes);
        return local_Builder3;
      }
      if ("attr".equals(paramString2))
      {
        XulAttr._Builder local_Builder2 = XulAttr._Builder.create(this._select);
        local_Builder2.initialize(paramString2, paramAttributes);
        return local_Builder2;
      }
      if ("style".equals(paramString2))
      {
        XulStyle._Builder local_Builder1 = XulStyle._Builder.create(this._select);
        local_Builder1.initialize(paramString2, paramAttributes);
        return local_Builder1;
      }
      if ("focus".equals(paramString2))
      {
        XulFocus._Builder local_Builder = XulFocus._Builder.create(this._select);
        local_Builder.initialize(paramString2, paramAttributes);
        return local_Builder;
      }
      return XulManager.CommonDummyBuilder;
    }

    public boolean pushText(String paramString, XulFactory.IPullParser paramIPullParser)
    {
      return super.pushText(paramString, paramIPullParser);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.XulSelect
 * JD-Core Version:    0.6.2
 */