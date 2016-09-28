package com.starcor.xul.Factory;

import com.starcor.xul.Utils.XulCachedHashMap;
import com.starcor.xul.Utils.XulPullParser;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import org.xml.sax.Attributes;

public class XulFactory
{
  static final ResultFactory factories = new ResultFactory();

  public static <T> T build(Class<T> paramClass, InputStream paramInputStream, String paramString)
    throws Exception
  {
    Object localObject2;
    if (paramInputStream == null)
      localObject2 = null;
    do
    {
      return localObject2;
      synchronized (factories)
      {
        localObject2 = factories.build(paramClass.getName(), paramInputStream, paramString);
        if (localObject2 == null)
          throw new Exception("创建对象失败!");
      }
    }
    while (paramClass.isInstance(localObject2));
    throw new Exception("创建的结果对象类型不符合预期!");
  }

  public static <T> T build(Class<T> paramClass, byte[] paramArrayOfByte, String paramString)
    throws Exception
  {
    if (paramArrayOfByte == null)
      return null;
    return build(paramClass, new ByteArrayInputStream(paramArrayOfByte), paramString);
  }

  public static void registerBuilder(Class<?> paramClass, ResultBuilder paramResultBuilder)
  {
    synchronized (factories)
    {
      factories.addBuilder(paramClass.getName(), paramResultBuilder);
      return;
    }
  }

  public static abstract class Attributes
  {
    public abstract int getLength();

    public abstract String getName(int paramInt);

    public abstract String getValue(int paramInt);

    public abstract String getValue(String paramString);
  }

  public static class DummyItemBuilder extends XulFactory.ItemBuilder
  {
    public XulFactory.ItemBuilder pushSubItem(XulFactory.IPullParser paramIPullParser, String paramString1, String paramString2, XulFactory.Attributes paramAttributes)
    {
      return this;
    }

    public boolean pushText(String paramString, XulFactory.IPullParser paramIPullParser)
    {
      return true;
    }
  }

  public static abstract interface IPullParser
  {
    public abstract int getAttributeCount();

    public abstract String getAttributeName(int paramInt);

    public abstract String getAttributeValue(int paramInt);

    public abstract String getAttributeValue(String paramString);

    public abstract String getName();

    public abstract String getText();

    public abstract int nextToken()
      throws Exception;

    public abstract int restoreParserPos(Object paramObject);

    public abstract Object storeParserPos();
  }

  public static abstract class ItemBuilder
  {
    public Object finalItem()
    {
      return null;
    }

    public boolean initialize(String paramString, XulFactory.Attributes paramAttributes)
    {
      return true;
    }

    public ItemBuilder pushSubItem(XulFactory.IPullParser paramIPullParser, String paramString1, String paramString2, XulFactory.Attributes paramAttributes)
    {
      return null;
    }

    public boolean pushText(String paramString, XulFactory.IPullParser paramIPullParser)
    {
      return false;
    }

    public static abstract interface FinalCallback<T>
    {
      public abstract void onFinal(T paramT);
    }
  }

  public static abstract class ResultBuilder
  {
    public XulFactory.ItemBuilder build(XulFactory.ResultBuilderContext paramResultBuilderContext, String paramString, XulFactory.Attributes paramAttributes)
    {
      return null;
    }
  }

  public static abstract class ResultBuilderContext
  {
    public abstract String getName();
  }

  static class ResultFactory
  {
    XulCachedHashMap<String, XulFactory.ResultBuilder> _builderMap = new XulCachedHashMap();

    private Object _doBuild(XulFactory.ResultBuilderContext paramResultBuilderContext, XulFactory.ResultBuilder paramResultBuilder, final XulFactory.IPullParser paramIPullParser)
      throws Exception
    {
      ArrayList localArrayList = new ArrayList();
      Object localObject = null;
      XulFactory.Attributes local4 = new XulFactory.Attributes()
      {
        public int getLength()
        {
          return paramIPullParser.getAttributeCount();
        }

        public String getName(int paramAnonymousInt)
        {
          return paramIPullParser.getAttributeName(paramAnonymousInt);
        }

        public String getValue(int paramAnonymousInt)
        {
          return paramIPullParser.getAttributeValue(paramAnonymousInt);
        }

        public String getValue(String paramAnonymousString)
        {
          return paramIPullParser.getAttributeValue(paramAnonymousString);
        }
      };
      label280: 
      while (true)
      {
        int i = paramIPullParser.nextToken();
        switch (i)
        {
        default:
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        }
        while (true)
        {
          if (i != 1)
            break label280;
          return localObject;
          localArrayList.clear();
          continue;
          localArrayList.clear();
          continue;
          String str = paramIPullParser.getName();
          if (localArrayList.isEmpty())
          {
            XulFactory.ItemBuilder localItemBuilder2 = paramResultBuilder.build(paramResultBuilderContext, str, local4);
            if (localItemBuilder2 == null)
              throw new Exception("can not initialize item builder");
            localArrayList.add(localItemBuilder2);
          }
          else
          {
            XulFactory.ItemBuilder localItemBuilder1 = ((XulFactory.ItemBuilder)localArrayList.get(-1 + localArrayList.size())).pushSubItem(paramIPullParser, "", str, local4);
            if (localItemBuilder1 == null)
            {
              localObject = ((XulFactory.ItemBuilder)localArrayList.remove(-1 + localArrayList.size())).finalItem();
              break;
            }
            localArrayList.add(localItemBuilder1);
            continue;
            if (localArrayList.isEmpty())
              throw new Exception("result content not match");
            localObject = ((XulFactory.ItemBuilder)localArrayList.remove(-1 + localArrayList.size())).finalItem();
            continue;
            ((XulFactory.ItemBuilder)localArrayList.get(-1 + localArrayList.size())).pushText("", paramIPullParser);
          }
        }
      }
    }

    public void addBuilder(String paramString, XulFactory.ResultBuilder paramResultBuilder)
    {
      this._builderMap.put(paramString, paramResultBuilder);
    }

    public Object build(String paramString1, InputStream paramInputStream, final String paramString2)
      throws Exception
    {
      if (paramInputStream == null)
        return null;
      XulFactory.ResultBuilderContext local1 = new XulFactory.ResultBuilderContext()
      {
        public String getName()
        {
          return paramString2;
        }
      };
      XulFactory.ResultBuilder localResultBuilder = (XulFactory.ResultBuilder)this._builderMap.get(paramString1);
      if (localResultBuilder == null)
        throw new Exception("无法创建目标对象 " + paramString1);
      return _doBuild(local1, localResultBuilder, new XulFactory.IPullParser()
      {
        public int getAttributeCount()
        {
          return this.val$pullParser.getAttributeCount();
        }

        public String getAttributeName(int paramAnonymousInt)
        {
          return this.val$pullParser.getAttributeName(paramAnonymousInt);
        }

        public String getAttributeValue(int paramAnonymousInt)
        {
          return this.val$pullParser.getAttributeValue(paramAnonymousInt);
        }

        public String getAttributeValue(String paramAnonymousString)
        {
          return this.val$pullParser.getAttributeValue(null, paramAnonymousString);
        }

        public String getName()
        {
          return this.val$pullParser.getName();
        }

        public String getText()
        {
          return this.val$pullParser.getText();
        }

        public int nextToken()
          throws Exception
        {
          return this.val$pullParser.nextToken();
        }

        public int restoreParserPos(Object paramAnonymousObject)
        {
          return this.val$pullParser.loadPosition(paramAnonymousObject);
        }

        public Object storeParserPos()
        {
          return this.val$pullParser.storePosition();
        }
      });
    }

    public Object build(String paramString1, byte[] paramArrayOfByte, String paramString2)
      throws Exception
    {
      return build(paramString1, new ByteArrayInputStream(paramArrayOfByte), paramString2);
    }
  }

  public static class SaxAttributes extends XulFactory.Attributes
  {
    Attributes _attr;

    public void attach(Attributes paramAttributes)
    {
      this._attr = paramAttributes;
    }

    public int getLength()
    {
      if (this._attr == null)
        return 0;
      return this._attr.getLength();
    }

    public String getName(int paramInt)
    {
      if (this._attr == null)
        return null;
      return this._attr.getLocalName(paramInt);
    }

    public String getValue(int paramInt)
    {
      if (this._attr == null)
        return null;
      return this._attr.getValue(paramInt);
    }

    public String getValue(String paramString)
    {
      if (this._attr == null)
        return null;
      return this._attr.getValue("", paramString);
    }
  }

  public static class TextParser
    implements XulFactory.IPullParser
  {
    public String text;

    public int getAttributeCount()
    {
      return 0;
    }

    public String getAttributeName(int paramInt)
    {
      return null;
    }

    public String getAttributeValue(int paramInt)
    {
      return null;
    }

    public String getAttributeValue(String paramString)
    {
      return null;
    }

    public String getName()
    {
      return null;
    }

    public String getText()
    {
      return this.text;
    }

    public int nextToken()
      throws Exception
    {
      return 0;
    }

    public int restoreParserPos(Object paramObject)
    {
      return -1;
    }

    public Object storeParserPos()
    {
      return null;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Factory.XulFactory
 * JD-Core Version:    0.6.2
 */