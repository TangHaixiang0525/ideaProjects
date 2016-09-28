package com.starcor.xul;

import android.util.Log;
import com.starcor.xul.Factory.XulFactory.Attributes;
import com.starcor.xul.Factory.XulFactory.IPullParser;
import com.starcor.xul.Factory.XulFactory.ItemBuilder;
import com.starcor.xul.Factory.XulFactory.ResultBuilderContext;
import com.starcor.xul.Factory.XulParserData;
import com.starcor.xul.Factory.XulParserDataNoStoreSupported;
import com.starcor.xul.Factory.XulParserDataStoreSupported;
import java.util.ArrayList;

public class XulComponent extends XulArea
{
  private static final String TAG = XulComponent.class.getSimpleName();
  private String _ownerId;
  private ArrayList<XulSelect> _selectors;
  private XulParserData _xulParserData;

  public XulComponent(String paramString)
  {
    this._ownerId = paramString;
  }

  private void _initComponent()
  {
    if (this._xulParserData != null)
    {
      XulUtils.ticketMarker localticketMarker = new XulUtils.ticketMarker("BENCH!! ", true);
      localticketMarker.mark();
      this._xulParserData.buildItem(_ComponentBuilder.create(this));
      this._xulParserData = null;
      updateSelectorPriorityLevel();
      localticketMarker.mark("_initComponent");
      Log.d(TAG, localticketMarker.toString());
    }
  }

  public void addSelector(XulSelect paramXulSelect)
  {
    if (this._selectors == null)
      this._selectors = new ArrayList();
    this._selectors.add(paramXulSelect);
  }

  public String getOwnerId()
  {
    return this._ownerId;
  }

  public ArrayList<XulSelect> getSelectors()
  {
    return this._selectors;
  }

  public void makeInstanceOn(XulArea paramXulArea)
  {
    _initComponent();
    int i = this._children.size();
    int j = 0;
    if (j < i)
    {
      Object localObject = this._children.get(j);
      if ((localObject instanceof XulArea))
        ((XulArea)localObject).makeClone(paramXulArea);
      while (true)
      {
        j++;
        break;
        if ((localObject instanceof XulTemplate))
          ((XulTemplate)localObject).makeClone(paramXulArea);
        else if ((localObject instanceof XulItem))
          ((XulItem)localObject).makeClone(paramXulArea);
        else
          Log.d(TAG, "unsupported children type!!! - " + localObject.getClass().getSimpleName());
      }
    }
  }

  void updateSelectorPriorityLevel()
  {
    if (this._selectors == null);
    while (true)
    {
      return;
      for (int i = 0; i < this._selectors.size(); i++)
        ((XulSelect)this._selectors.get(i)).setPriorityLevel(i + 1, 67108864);
    }
  }

  public static class _Builder extends XulFactory.ItemBuilder
  {
    XulComponent _component;
    XulFactory.ResultBuilderContext _ctx;
    XulManager _manager;
    XulParserData _xulParserData;

    public _Builder(XulManager paramXulManager, XulFactory.ResultBuilderContext paramResultBuilderContext)
    {
      this._manager = paramXulManager;
      this._ctx = paramResultBuilderContext;
      this._component = new XulComponent(this._ctx.getName());
    }

    public static _Builder create(XulFactory.ResultBuilderContext paramResultBuilderContext, XulManager paramXulManager)
    {
      return new _Builder(paramXulManager, paramResultBuilderContext);
    }

    public Object finalItem()
    {
      XulManager.addComponent(this._component);
      return super.finalItem();
    }

    public boolean initialize(String paramString, XulFactory.Attributes paramAttributes)
    {
      this._component._type = XulUtils.getCachedString("component");
      this._component._id = paramAttributes.getValue("id");
      return true;
    }

    public XulFactory.ItemBuilder pushSubItem(XulFactory.IPullParser paramIPullParser, String paramString1, String paramString2, XulFactory.Attributes paramAttributes)
    {
      if (paramIPullParser != null)
      {
        Object localObject = paramIPullParser.storeParserPos();
        if (localObject != null)
        {
          XulComponent localXulComponent2 = this._component;
          XulParserDataStoreSupported localXulParserDataStoreSupported = new XulParserDataStoreSupported(paramIPullParser, localObject);
          this._xulParserData = localXulParserDataStoreSupported;
          XulComponent.access$002(localXulComponent2, localXulParserDataStoreSupported);
          return null;
        }
      }
      XulParserDataNoStoreSupported localXulParserDataNoStoreSupported;
      if (this._xulParserData == null)
      {
        localXulParserDataNoStoreSupported = new XulParserDataNoStoreSupported();
        XulComponent localXulComponent1 = this._component;
        this._xulParserData = localXulParserDataNoStoreSupported;
        XulComponent.access$002(localXulComponent1, localXulParserDataNoStoreSupported);
      }
      while (true)
      {
        return localXulParserDataNoStoreSupported.pushSubItem(paramString1, paramString2, paramAttributes);
        localXulParserDataNoStoreSupported = (XulParserDataNoStoreSupported)this._xulParserData;
      }
    }
  }

  public static class _ComponentBuilder extends XulArea._Builder
  {
    private static ArrayList<_ComponentBuilder> _recycled_builder = new ArrayList();

    private static _ComponentBuilder create()
    {
      if (_recycled_builder.isEmpty());
      for (_ComponentBuilder local_ComponentBuilder = null; ; local_ComponentBuilder = (_ComponentBuilder)_recycled_builder.remove(-1 + _recycled_builder.size()))
      {
        if (local_ComponentBuilder == null)
          local_ComponentBuilder = new _ComponentBuilder();
        return local_ComponentBuilder;
      }
    }

    public static _ComponentBuilder create(XulComponent paramXulComponent)
    {
      _ComponentBuilder local_ComponentBuilder = create();
      local_ComponentBuilder.init(paramXulComponent);
      return local_ComponentBuilder;
    }

    private void init(XulComponent paramXulComponent)
    {
      this._area = paramXulComponent;
    }

    private static void recycle(_ComponentBuilder param_ComponentBuilder)
    {
      _recycled_builder.add(param_ComponentBuilder);
      param_ComponentBuilder._area = null;
      param_ComponentBuilder._ownerTemplate = null;
    }

    public XulFactory.ItemBuilder pushSubItem(XulFactory.IPullParser paramIPullParser, String paramString1, String paramString2, XulFactory.Attributes paramAttributes)
    {
      if ("selector".equals(paramString2))
        return new XulFactory.ItemBuilder()
        {
          public Object finalItem()
          {
            return super.finalItem();
          }

          public boolean initialize(String paramAnonymousString, XulFactory.Attributes paramAnonymousAttributes)
          {
            return super.initialize(paramAnonymousString, paramAnonymousAttributes);
          }

          public XulFactory.ItemBuilder pushSubItem(XulFactory.IPullParser paramAnonymousIPullParser, String paramAnonymousString1, String paramAnonymousString2, XulFactory.Attributes paramAnonymousAttributes)
          {
            if (paramAnonymousString2.equals("select"))
            {
              XulSelect._Builder local_Builder = XulSelect._Builder.create((XulComponent)XulComponent._ComponentBuilder.this._area);
              local_Builder.initialize(paramAnonymousString2, paramAnonymousAttributes);
              return local_Builder;
            }
            return XulManager.CommonDummyBuilder;
          }

          public boolean pushText(String paramAnonymousString, XulFactory.IPullParser paramAnonymousIPullParser)
          {
            return super.pushText(paramAnonymousString, paramAnonymousIPullParser);
          }
        };
      return super.pushSubItem(paramIPullParser, paramString1, paramString2, paramAttributes);
    }

    public void recycle()
    {
      recycle(this);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.XulComponent
 * JD-Core Version:    0.6.2
 */