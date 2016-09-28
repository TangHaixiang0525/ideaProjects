package com.starcor.remote_key;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class EventFactory
{
  static final ResultFactory factories = new ResultFactory();

  public static <T> T build(Class<T> paramClass, byte[] paramArrayOfByte)
    throws Exception
  {
    Object localObject2;
    synchronized (factories)
    {
      localObject2 = factories.build(paramClass.getName(), paramArrayOfByte);
      if (localObject2 == null)
        throw new Exception("创建对象失败!");
    }
    if (!paramClass.isInstance(localObject2))
      throw new Exception("创建的结果对象类型不符合预期!");
    return localObject2;
  }

  public static void registerBuilder(Class<?> paramClass, ResultBuilder paramResultBuilder)
  {
    synchronized (factories)
    {
      factories.addBuilder(paramClass.getName(), paramResultBuilder);
      return;
    }
  }

  public static class DummyItemBuilder extends EventFactory.ItemBuilder
  {
    public EventFactory.ItemBuilder pushSubItem(String paramString1, String paramString2, Attributes paramAttributes)
    {
      return this;
    }

    public boolean pushText(String paramString1, String paramString2)
    {
      return true;
    }
  }

  public static abstract class ItemBuilder
  {
    public Object finalItem()
    {
      return null;
    }

    public ItemBuilder pushSubItem(String paramString1, String paramString2, Attributes paramAttributes)
    {
      return null;
    }

    public boolean pushText(String paramString1, String paramString2)
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
    public EventFactory.ItemBuilder build(String paramString, Attributes paramAttributes)
    {
      return null;
    }
  }

  static class ResultFactory
  {
    HashMap<String, EventFactory.ResultBuilder> _builderMap = new HashMap();
    ArrayList<EventFactory.ItemBuilder> buildStack = new ArrayList();
    Object result;

    public void addBuilder(String paramString, EventFactory.ResultBuilder paramResultBuilder)
    {
      this._builderMap.put(paramString, paramResultBuilder);
    }

    public Object build(String paramString, byte[] paramArrayOfByte)
      throws Exception
    {
      final EventFactory.ResultBuilder localResultBuilder = (EventFactory.ResultBuilder)this._builderMap.get(paramString);
      if (localResultBuilder == null)
        throw new Exception("无法创建目标对象 " + paramString);
      this.result = null;
      SAXParserFactory.newInstance().newSAXParser().parse(new ByteArrayInputStream(paramArrayOfByte), new DefaultHandler()
      {
        StringBuilder tmpTextNode = null;

        public void characters(char[] paramAnonymousArrayOfChar, int paramAnonymousInt1, int paramAnonymousInt2)
          throws SAXException
        {
          if (this.tmpTextNode == null)
            this.tmpTextNode = new StringBuilder();
          this.tmpTextNode.append(String.valueOf(paramAnonymousArrayOfChar, paramAnonymousInt1, paramAnonymousInt2));
        }

        public void endDocument()
          throws SAXException
        {
          super.endDocument();
        }

        public void endElement(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3)
          throws SAXException
        {
          finalCharacters();
          super.endElement(paramAnonymousString1, paramAnonymousString2, paramAnonymousString3);
          if (EventFactory.ResultFactory.this.buildStack.isEmpty())
            throw new SAXException("result content not match");
          EventFactory.ItemBuilder localItemBuilder = (EventFactory.ItemBuilder)EventFactory.ResultFactory.this.buildStack.remove(-1 + EventFactory.ResultFactory.this.buildStack.size());
          EventFactory.ResultFactory.this.result = localItemBuilder.finalItem();
        }

        void finalCharacters()
        {
          if (this.tmpTextNode != null)
            ((EventFactory.ItemBuilder)EventFactory.ResultFactory.this.buildStack.get(-1 + EventFactory.ResultFactory.this.buildStack.size())).pushText("", this.tmpTextNode.toString());
          this.tmpTextNode = null;
        }

        public void startDocument()
          throws SAXException
        {
          super.startDocument();
          EventFactory.ResultFactory.this.buildStack.clear();
        }

        public void startElement(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, Attributes paramAnonymousAttributes)
          throws SAXException
        {
          super.startElement(paramAnonymousString1, paramAnonymousString2, paramAnonymousString3, paramAnonymousAttributes);
          if (EventFactory.ResultFactory.this.buildStack.isEmpty())
          {
            EventFactory.ItemBuilder localItemBuilder2 = localResultBuilder.build(paramAnonymousString3, paramAnonymousAttributes);
            if (localItemBuilder2 == null)
              throw new SAXException("can not initialize item builder");
            EventFactory.ResultFactory.this.buildStack.add(localItemBuilder2);
            return;
          }
          EventFactory.ItemBuilder localItemBuilder1 = ((EventFactory.ItemBuilder)EventFactory.ResultFactory.this.buildStack.get(-1 + EventFactory.ResultFactory.this.buildStack.size())).pushSubItem("", paramAnonymousString3, paramAnonymousAttributes);
          if (localItemBuilder1 == null)
            throw new SAXException("can not initialize sub item builder");
          EventFactory.ResultFactory.this.buildStack.add(localItemBuilder1);
        }
      });
      return this.result;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.remote_key.EventFactory
 * JD-Core Version:    0.6.2
 */