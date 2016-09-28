package com.starcor.xul.Factory;

import java.util.ArrayList;

public class XulParserDataStoreSupported extends XulParserData
{
  private final XulFactory.IPullParser _parser;
  private Object _store_pos;

  public XulParserDataStoreSupported(XulFactory.IPullParser paramIPullParser, Object paramObject)
  {
    this._parser = paramIPullParser;
    this._store_pos = paramObject;
  }

  private int next_event()
    throws Exception
  {
    if (this._store_pos != null)
    {
      Object localObject = this._store_pos;
      this._store_pos = null;
      return this._parser.restoreParserPos(localObject);
    }
    return this._parser.nextToken();
  }

  public void buildItem(XulFactory.ItemBuilder paramItemBuilder)
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(paramItemBuilder);
    XulFactory.Attributes local1 = new XulFactory.Attributes()
    {
      public int getLength()
      {
        return XulParserDataStoreSupported.this._parser.getAttributeCount();
      }

      public String getName(int paramAnonymousInt)
      {
        return XulParserDataStoreSupported.this._parser.getAttributeName(paramAnonymousInt);
      }

      public String getValue(int paramAnonymousInt)
      {
        return XulParserDataStoreSupported.this._parser.getAttributeValue(paramAnonymousInt);
      }

      public String getValue(String paramAnonymousString)
      {
        return XulParserDataStoreSupported.this._parser.getAttributeValue(paramAnonymousString);
      }
    };
    int i;
    do
      while (true)
      {
        try
        {
          i = next_event();
          switch (i)
          {
          case 0:
            localArrayList.clear();
          case 1:
          case 2:
          case 3:
          case 4:
          case 5:
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          return;
        }
        localArrayList.clear();
        break;
        String str = this._parser.getName();
        if (localArrayList.isEmpty())
          throw new Exception("can not initialize item builder");
        XulFactory.ItemBuilder localItemBuilder = ((XulFactory.ItemBuilder)localArrayList.get(-1 + localArrayList.size())).pushSubItem(this._parser, "", str, local1);
        if (localItemBuilder == null)
        {
          ((XulFactory.ItemBuilder)localArrayList.remove(-1 + localArrayList.size())).finalItem();
        }
        else
        {
          localArrayList.add(localItemBuilder);
          break;
          if (localArrayList.isEmpty())
            throw new Exception("result content not match");
          ((XulFactory.ItemBuilder)localArrayList.remove(-1 + localArrayList.size())).finalItem();
          break;
          ((XulFactory.ItemBuilder)localArrayList.get(-1 + localArrayList.size())).pushText("", this._parser);
        }
      }
    while (i != 1);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Factory.XulParserDataStoreSupported
 * JD-Core Version:    0.6.2
 */