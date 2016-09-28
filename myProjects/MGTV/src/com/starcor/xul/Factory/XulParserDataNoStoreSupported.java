package com.starcor.xul.Factory;

import android.util.Pair;
import com.starcor.xul.XulUtils;
import java.util.ArrayList;

public class XulParserDataNoStoreSupported extends XulParserData
{
  public static final Pair<Integer, Object> END_TAG = Pair.create(Integer.valueOf(2), (Object)null);
  ArrayList<Pair<Integer, Object>> _data = new ArrayList();
  XulFactory.ItemBuilder _dataBuilder = new XulFactory.ItemBuilder()
  {
    public Object finalItem()
    {
      XulParserDataNoStoreSupported.this._data.add(XulParserDataNoStoreSupported.END_TAG);
      return null;
    }

    public XulFactory.ItemBuilder pushSubItem(XulFactory.IPullParser paramAnonymousIPullParser, String paramAnonymousString1, String paramAnonymousString2, XulFactory.Attributes paramAnonymousAttributes)
    {
      XulParserCachedTag localXulParserCachedTag = new XulParserCachedTag(paramAnonymousString2, paramAnonymousAttributes);
      XulParserDataNoStoreSupported.this._data.add(Pair.create(Integer.valueOf(0), localXulParserCachedTag));
      switch (paramAnonymousString2.charAt(0))
      {
      default:
      case 'a':
      case 'b':
      case 'd':
      case 's':
      }
      do
      {
        do
        {
          do
          {
            do
              return this;
            while ((!"attr".equals(paramAnonymousString2)) && (!"action".equals(paramAnonymousString2)));
            return XulParserDataNoStoreSupported.this._dataBuilderEx;
          }
          while (!"binding".equals(paramAnonymousString2));
          return XulParserDataNoStoreSupported.this._dataBuilderEx;
        }
        while (!"data".equals(paramAnonymousString2));
        return XulParserDataNoStoreSupported.this._dataBuilderEx;
      }
      while (!"style".equals(paramAnonymousString2));
      return XulParserDataNoStoreSupported.this._dataBuilderEx;
    }

    public boolean pushText(String paramAnonymousString, XulFactory.IPullParser paramAnonymousIPullParser)
    {
      return true;
    }
  };
  XulFactory.ItemBuilder _dataBuilderEx = new XulFactory.ItemBuilder()
  {
    public Object finalItem()
    {
      XulParserDataNoStoreSupported.this._data.add(XulParserDataNoStoreSupported.END_TAG);
      return null;
    }

    public XulFactory.ItemBuilder pushSubItem(XulFactory.IPullParser paramAnonymousIPullParser, String paramAnonymousString1, String paramAnonymousString2, XulFactory.Attributes paramAnonymousAttributes)
    {
      XulParserCachedTag localXulParserCachedTag = new XulParserCachedTag(paramAnonymousString2, paramAnonymousAttributes);
      XulParserDataNoStoreSupported.this._data.add(Pair.create(Integer.valueOf(0), localXulParserCachedTag));
      return this;
    }

    public boolean pushText(String paramAnonymousString, XulFactory.IPullParser paramAnonymousIPullParser)
    {
      XulParserDataNoStoreSupported.this._data.add(Pair.create(Integer.valueOf(1), XulUtils.getCachedString(paramAnonymousIPullParser.getText())));
      return true;
    }
  };
  XulFactory.TextParser _textParser = new XulFactory.TextParser();

  public void buildItem(XulFactory.ItemBuilder paramItemBuilder)
  {
    int i = this._data.size();
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramItemBuilder);
    int j = 0;
    if (j < i)
    {
      Pair localPair = (Pair)this._data.get(j);
      int k = -1 + localArrayList.size();
      XulFactory.ItemBuilder localItemBuilder1 = (XulFactory.ItemBuilder)localArrayList.get(k);
      switch (((Integer)localPair.first).intValue())
      {
      default:
      case 0:
      case 1:
      case 2:
      }
      while (true)
      {
        j++;
        break;
        XulParserCachedTag localXulParserCachedTag = (XulParserCachedTag)localPair.second;
        XulFactory.ItemBuilder localItemBuilder2 = localItemBuilder1.pushSubItem(null, null, localXulParserCachedTag.getTagName(), localXulParserCachedTag);
        localItemBuilder2.initialize(localXulParserCachedTag.getTagName(), localXulParserCachedTag);
        localArrayList.add(localItemBuilder2);
        continue;
        this._textParser.text = ((String)localPair.second);
        localItemBuilder1.pushText(null, this._textParser);
        continue;
        localItemBuilder1.finalItem();
        localArrayList.remove(k);
      }
    }
  }

  public XulFactory.ItemBuilder pushSubItem(String paramString1, String paramString2, XulFactory.Attributes paramAttributes)
  {
    return this._dataBuilder.pushSubItem(null, paramString1, paramString2, paramAttributes);
  }

  boolean pushText(String paramString, XulFactory.IPullParser paramIPullParser)
  {
    return this._dataBuilder.pushText(paramString, paramIPullParser);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Factory.XulParserDataNoStoreSupported
 * JD-Core Version:    0.6.2
 */