package com.starcor.xul;

import android.annotation.TargetApi;
import android.text.TextUtils;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import com.starcor.xul.Factory.XulFactory;
import com.starcor.xul.Factory.XulFactory.Attributes;
import com.starcor.xul.Factory.XulFactory.IPullParser;
import com.starcor.xul.Factory.XulFactory.ItemBuilder;
import com.starcor.xul.Factory.XulFactory.ItemBuilder.FinalCallback;
import com.starcor.xul.Factory.XulFactory.ResultBuilder;
import com.starcor.xul.Factory.XulFactory.ResultBuilderContext;
import com.starcor.xul.Prop.XulBinding;
import com.starcor.xul.Utils.XulCachedHashMap;
import com.starcor.xul.Utils.XulQuery;
import com.starcor.xul.Utils.XulSimpleStack;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

@TargetApi(11)
public class XulDataNode
  implements Serializable
{
  private static final long serialVersionUID = 8679962897455998053L;
  XulCachedHashMap<String, XulDataNode> _attr;
  int _childrenNum = 0;
  XulDataNode _firstChild;
  XulDataNode _lastChild;
  String _name;
  XulDataNode _next;
  XulBinding _ownerBinding;
  XulDataNode _parent;
  XulDataNode _prev;
  String _value;

  static
  {
    XulFactory.registerBuilder(XulDataNode.class, new _Factory());
  }

  private XulDataNode(String paramString)
  {
    this._name = XulUtils.getCachedString(paramString);
    this._value = "";
  }

  private XulDataNode(String paramString1, String paramString2)
  {
    this._name = XulUtils.getCachedString(paramString1);
    this._value = XulUtils.getCachedString(paramString2);
  }

  public static XulDataNode build(InputStream paramInputStream)
    throws Exception
  {
    XulDataNode localXulDataNode = (XulDataNode)XulFactory.build(XulDataNode.class, paramInputStream, null);
    if (0 != 0)
    {
      null.mark("build");
      Log.d("BENCH!!!", null.toString());
    }
    return localXulDataNode;
  }

  public static XulDataNode build(String paramString)
  {
    return obtainDataNode("", paramString);
  }

  public static XulDataNode build(byte[] paramArrayOfByte)
    throws Exception
  {
    XulDataNode localXulDataNode = (XulDataNode)XulFactory.build(XulDataNode.class, paramArrayOfByte, null);
    if (0 != 0)
    {
      null.mark("build");
      Log.d("BENCH!!!", null.toString());
    }
    return localXulDataNode;
  }

  public static XulDataNode buildFromJson(InputStream paramInputStream)
  {
    if (paramInputStream == null)
      return null;
    return buildFromJson(new InputStreamReader(paramInputStream));
  }

  public static XulDataNode buildFromJson(Reader paramReader)
  {
    JsonReader localJsonReader = new JsonReader(paramReader);
    ArrayList localArrayList = new ArrayList();
    XulDataNode localXulDataNode = null;
    while (true)
    {
      try
      {
        JsonToken localJsonToken = localJsonReader.peek();
        if (localJsonToken != JsonToken.END_DOCUMENT)
          switch (1.$SwitchMap$android$util$JsonToken[localJsonToken.ordinal()])
          {
          case 3:
          case 4:
          case 5:
          case 6:
          default:
            switch (1.$SwitchMap$android$util$JsonToken[localJsonToken.ordinal()])
            {
            default:
              localJsonReader.skipValue();
              continue;
            case 3:
            case 4:
            case 5:
            case 6:
            }
            break;
          case 7:
          case 1:
          case 8:
          case 2:
          case 9:
          }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      return localXulDataNode;
      String str = localJsonReader.nextName();
      if (localJsonReader.hasNext())
        switch (1.$SwitchMap$android$util$JsonToken[localJsonReader.peek().ordinal()])
        {
        case 1:
          localJsonReader.beginObject();
          localXulDataNode = localXulDataNode.appendChild(str);
          localArrayList.add(localXulDataNode);
          break;
        case 2:
          localJsonReader.beginArray();
          localXulDataNode = localXulDataNode.appendChild(str);
          localArrayList.add(localXulDataNode);
          break;
        case 3:
        case 4:
          localXulDataNode.setAttribute(str, localJsonReader.nextString());
          break;
        case 5:
          localXulDataNode.setAttribute(str, String.valueOf(localJsonReader.nextBoolean()));
          break;
        case 6:
          localXulDataNode.setAttribute(str, null);
          continue;
          localJsonReader.beginObject();
          localXulDataNode = obtainDataNode(null);
          localArrayList.add(localXulDataNode);
          continue;
          localJsonReader.endObject();
          localArrayList.remove(-1 + localArrayList.size());
          if (!localArrayList.isEmpty())
          {
            localXulDataNode = (XulDataNode)localArrayList.get(-1 + localArrayList.size());
            continue;
            localJsonReader.beginArray();
            localXulDataNode = obtainDataNode(null);
            localArrayList.add(localXulDataNode);
            continue;
            localJsonReader.endArray();
            localArrayList.remove(-1 + localArrayList.size());
            if (!localArrayList.isEmpty())
            {
              localXulDataNode = (XulDataNode)localArrayList.get(-1 + localArrayList.size());
              continue;
              localXulDataNode.appendChild(String.valueOf(localXulDataNode.size()), localJsonReader.nextString());
              continue;
              localXulDataNode.appendChild(String.valueOf(localXulDataNode.size()), String.valueOf(localJsonReader.nextBoolean()));
              continue;
              localXulDataNode.appendChild(String.valueOf(localXulDataNode.size()));
            }
          }
          break;
        }
    }
  }

  public static XulDataNode buildFromJson(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return null;
    return buildFromJson(new StringReader(paramString));
  }

  public static XulDataNode buildFromJson(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      return null;
    return buildFromJson(new ByteArrayInputStream(paramArrayOfByte));
  }

  public static void dumpXulDataNode(XulDataNode paramXulDataNode, XmlSerializer paramXmlSerializer)
    throws IOException
  {
    String str1 = paramXulDataNode.getName();
    if (TextUtils.isEmpty(str1))
      str1 = "NONAME";
    paramXmlSerializer.startTag(null, str1);
    Map localMap = paramXulDataNode.getAttributes();
    if (localMap != null)
    {
      Iterator localIterator = localMap.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        String str3 = ((XulDataNode)localEntry.getValue()).getValue();
        if (str3 == null)
          str3 = "";
        paramXmlSerializer.attribute(null, (String)localEntry.getKey(), str3);
      }
    }
    for (XulDataNode localXulDataNode = paramXulDataNode.getFirstChild(); localXulDataNode != null; localXulDataNode = localXulDataNode.getNext())
      dumpXulDataNode(localXulDataNode, paramXmlSerializer);
    String str2 = paramXulDataNode.getValue();
    if (!TextUtils.isEmpty(str2))
      paramXmlSerializer.text(str2);
    paramXmlSerializer.endTag(null, str1);
  }

  private XulDataNode internalAppendChild(XulDataNode paramXulDataNode)
  {
    if (this._firstChild == null)
    {
      this._lastChild = paramXulDataNode;
      this._firstChild = paramXulDataNode;
    }
    while (true)
    {
      paramXulDataNode._parent = this;
      this._childrenNum = (1 + this._childrenNum);
      return paramXulDataNode;
      this._lastChild._next = paramXulDataNode;
      paramXulDataNode._prev = this._lastChild;
      this._lastChild = paramXulDataNode;
    }
  }

  private static XulCachedHashMap<String, XulDataNode> obtainAttrContainer()
  {
    try
    {
      XulCachedHashMap localXulCachedHashMap = new XulCachedHashMap();
      return localXulCachedHashMap;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public static XulDataNode obtainDataNode(String paramString)
  {
    try
    {
      XulDataNode localXulDataNode = obtainDataNode(paramString, null);
      return localXulDataNode;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public static XulDataNode obtainDataNode(String paramString1, String paramString2)
  {
    try
    {
      XulDataNode localXulDataNode = new XulDataNode(paramString1, paramString2);
      return localXulDataNode;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    try
    {
      XulDataNode localXulDataNode1 = build(paramObjectInputStream);
      this._attr = localXulDataNode1._attr;
      this._firstChild = localXulDataNode1._firstChild;
      this._lastChild = localXulDataNode1._lastChild;
      this._childrenNum = localXulDataNode1._childrenNum;
      this._name = localXulDataNode1._name;
      this._value = localXulDataNode1._value;
      this._prev = null;
      this._next = null;
      for (XulDataNode localXulDataNode2 = this._firstChild; localXulDataNode2 != null; localXulDataNode2 = localXulDataNode2.getNext())
        localXulDataNode2._parent = this;
      localXulDataNode1._attr = null;
      localXulDataNode1._firstChild = null;
      localXulDataNode1._lastChild = null;
      localXulDataNode1._childrenNum = 0;
      return;
    }
    catch (IOException localIOException)
    {
      throw localIOException;
    }
    catch (Exception localException)
    {
    }
    throw new ClassNotFoundException("can't rebuild XulDataNode");
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    try
    {
      XmlSerializer localXmlSerializer = XmlPullParserFactory.newInstance().newSerializer();
      localXmlSerializer.setOutput(paramObjectOutputStream, "utf-8");
      localXmlSerializer.startDocument("utf-8", Boolean.valueOf(true));
      dumpXulDataNode(this, localXmlSerializer);
      localXmlSerializer.endDocument();
      localXmlSerializer.flush();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public XulDataNode appendChild(XulDataNode paramXulDataNode)
  {
    internalAppendChild(paramXulDataNode);
    paramXulDataNode.setOwnerBinding(this._ownerBinding);
    return paramXulDataNode;
  }

  public XulDataNode appendChild(String paramString)
  {
    return appendChild(paramString, "");
  }

  public XulDataNode appendChild(String paramString1, String paramString2)
  {
    XulDataNode localXulDataNode = obtainDataNode(paramString1, paramString2);
    if (this._firstChild == null)
    {
      this._lastChild = localXulDataNode;
      this._firstChild = localXulDataNode;
    }
    while (true)
    {
      localXulDataNode._parent = this;
      localXulDataNode.setOwnerBinding(this._ownerBinding);
      this._childrenNum = (1 + this._childrenNum);
      return localXulDataNode;
      this._lastChild._next = localXulDataNode;
      localXulDataNode._prev = this._lastChild;
      this._lastChild = localXulDataNode;
    }
  }

  public XulDataNode getAttribute(String paramString)
  {
    if (this._attr == null)
      return null;
    return (XulDataNode)this._attr.get(paramString);
  }

  public String getAttributeValue(String paramString)
  {
    if (this._attr == null);
    XulDataNode localXulDataNode;
    do
    {
      return null;
      localXulDataNode = (XulDataNode)this._attr.get(paramString);
    }
    while (localXulDataNode == null);
    return localXulDataNode.getValue();
  }

  public Map<String, XulDataNode> getAttributes()
  {
    return this._attr;
  }

  public XulDataNode getChildNode(String paramString)
  {
    XulDataNode localXulDataNode = this._firstChild;
    if ((localXulDataNode == null) || (TextUtils.isEmpty(paramString)))
      return null;
    do
    {
      localXulDataNode = localXulDataNode._next;
      if (localXulDataNode == null)
        break;
    }
    while (!paramString.equals(localXulDataNode.getName()));
    return localXulDataNode;
  }

  public XulDataNode getChildNode(String[] paramArrayOfString)
  {
    XulDataNode localXulDataNode = this;
    int i = 0;
    int j = paramArrayOfString.length;
    while ((i < j) && (localXulDataNode != null))
    {
      localXulDataNode = localXulDataNode.getChildNode(paramArrayOfString[i]);
      i++;
    }
    return localXulDataNode;
  }

  public String getChildNodeValue(String paramString)
  {
    XulDataNode localXulDataNode = getChildNode(paramString);
    if (localXulDataNode != null)
      return localXulDataNode.getValue();
    return null;
  }

  public String getChildNodeValue(String paramString1, String paramString2)
  {
    String str = getChildNodeValue(paramString1);
    if (str == null)
      return paramString2;
    return str;
  }

  public XulDataNode getFirstChild()
  {
    return this._firstChild;
  }

  public XulDataNode getLastChild()
  {
    return this._lastChild;
  }

  public String getName()
  {
    return this._name;
  }

  public XulDataNode getNext()
  {
    return this._next;
  }

  public XulDataNode getNext(String paramString)
  {
    for (XulDataNode localXulDataNode = this._next; (localXulDataNode != null) && (!paramString.equals(localXulDataNode._name)); localXulDataNode = localXulDataNode._next);
    return localXulDataNode;
  }

  public XulBinding getOwnerBinding()
  {
    return this._ownerBinding;
  }

  public XulDataNode getParent()
  {
    return this._parent;
  }

  public XulDataNode getPrev()
  {
    return this._prev;
  }

  public XulDataNode getPrev(String paramString)
  {
    for (XulDataNode localXulDataNode = this._prev; (localXulDataNode != null) && (!paramString.equals(localXulDataNode._name)); localXulDataNode = localXulDataNode._prev);
    return localXulDataNode;
  }

  public String getValue()
  {
    return this._value;
  }

  public boolean hasAttribute(String paramString)
  {
    return (this._attr != null) && (this._attr.containsKey(paramString));
  }

  public boolean hasAttribute(String paramString1, String paramString2)
  {
    if (this._attr == null);
    XulDataNode localXulDataNode;
    do
    {
      return false;
      localXulDataNode = (XulDataNode)this._attr.get(paramString1);
    }
    while ((localXulDataNode == null) || (!paramString2.equals(localXulDataNode.getValue())));
    return true;
  }

  public boolean hasChild()
  {
    return this._firstChild != null;
  }

  public XulDataNode makeClone()
  {
    XulDataNode localXulDataNode1 = obtainDataNode(this._name, this._value);
    for (XulDataNode localXulDataNode2 = getFirstChild(); localXulDataNode2 != null; localXulDataNode2 = localXulDataNode2.getNext())
    {
      XulDataNode localXulDataNode3 = localXulDataNode2.makeClone();
      localXulDataNode1.internalAppendChild(localXulDataNode3);
      localXulDataNode3._parent = localXulDataNode1;
    }
    if (this._attr != null)
    {
      XulCachedHashMap localXulCachedHashMap = obtainAttrContainer();
      localXulDataNode1._attr = localXulCachedHashMap;
      Iterator localIterator = this._attr.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        localXulCachedHashMap.put(localEntry.getKey(), ((XulDataNode)localEntry.getValue()).makeClone());
      }
    }
    return localXulDataNode1;
  }

  public XulDataNode removeChild(XulDataNode paramXulDataNode)
  {
    if (paramXulDataNode._parent != this)
      return null;
    XulDataNode localXulDataNode1 = paramXulDataNode._prev;
    XulDataNode localXulDataNode2 = paramXulDataNode._next;
    if (localXulDataNode1 != null)
      localXulDataNode1._next = localXulDataNode2;
    if (localXulDataNode2 != null)
      localXulDataNode2._prev = localXulDataNode1;
    if (paramXulDataNode == this._firstChild)
      this._firstChild = localXulDataNode2;
    if (paramXulDataNode == this._lastChild)
      this._lastChild = localXulDataNode1;
    paramXulDataNode._next = null;
    paramXulDataNode._prev = null;
    paramXulDataNode._parent = null;
    return paramXulDataNode;
  }

  public XulDataNode selectNode(String[] paramArrayOfString)
  {
    return XulQuery.select(this, paramArrayOfString);
  }

  public XulDataNode setAttribute(String paramString, int paramInt)
  {
    return setAttribute(paramString, String.valueOf(paramInt));
  }

  public XulDataNode setAttribute(String paramString1, String paramString2)
  {
    if (this._attr == null)
      this._attr = obtainAttrContainer();
    XulDataNode localXulDataNode = obtainDataNode(paramString1, paramString2);
    this._attr.put(XulUtils.getCachedString(paramString1), localXulDataNode);
    localXulDataNode.setOwnerBinding(this._ownerBinding);
    return this;
  }

  public void setOwnerBinding(XulBinding paramXulBinding)
  {
    if (this._ownerBinding != null);
    while (true)
    {
      return;
      this._ownerBinding = paramXulBinding;
      if (this._attr != null)
      {
        Iterator localIterator = this._attr.values().iterator();
        while (localIterator.hasNext())
          ((XulDataNode)localIterator.next()).setOwnerBinding(paramXulBinding);
      }
      for (XulDataNode localXulDataNode = this._firstChild; localXulDataNode != null; localXulDataNode = localXulDataNode._next)
        localXulDataNode.setOwnerBinding(paramXulBinding);
    }
  }

  public XulDataNode setValue(String paramString)
  {
    this._value = XulUtils.getCachedString(paramString);
    return this;
  }

  public int size()
  {
    return this._childrenNum;
  }

  public String toString()
  {
    return "XulDataNode{_name='" + this._name + '\'' + '}';
  }

  public static class _Builder extends XulFactory.ItemBuilder
  {
    private static XulSimpleStack<_Builder> _recycled_builder = new XulSimpleStack(256);
    XulFactory.ItemBuilder.FinalCallback<XulDataNode> _callback;
    StringBuilder _content;
    boolean _eliminateContent = false;
    XulDataNode _node;

    private static _Builder create()
    {
      _Builder local_Builder = (_Builder)_recycled_builder.pop();
      if (local_Builder == null)
        local_Builder = new _Builder();
      return local_Builder;
    }

    public static _Builder create(XulBinding paramXulBinding, String paramString)
    {
      _Builder local_Builder = create();
      local_Builder.init(paramXulBinding, paramString);
      return local_Builder;
    }

    public static _Builder create(XulDataNode paramXulDataNode, String paramString)
    {
      _Builder local_Builder = create();
      local_Builder.init(paramXulDataNode, paramString);
      return local_Builder;
    }

    public static _Builder create(String paramString)
    {
      _Builder local_Builder = create();
      local_Builder.init(paramString);
      return local_Builder;
    }

    public static _Builder create(String paramString, XulFactory.ItemBuilder.FinalCallback<XulDataNode> paramFinalCallback)
    {
      _Builder local_Builder = create();
      local_Builder.init(paramString, paramFinalCallback);
      return local_Builder;
    }

    private void init(XulBinding paramXulBinding, String paramString)
    {
      this._node = XulDataNode.obtainDataNode(paramString);
      paramXulBinding.setData(this._node);
      this._content = null;
      this._callback = null;
    }

    private void init(XulDataNode paramXulDataNode, String paramString)
    {
      this._node = paramXulDataNode.appendChild(paramString);
      this._content = null;
      this._callback = null;
    }

    private void init(String paramString)
    {
      this._node = XulDataNode.obtainDataNode(paramString);
      this._content = null;
      this._callback = null;
    }

    private void init(String paramString, XulFactory.ItemBuilder.FinalCallback<XulDataNode> paramFinalCallback)
    {
      this._node = XulDataNode.obtainDataNode(paramString);
      this._content = null;
      this._callback = paramFinalCallback;
    }

    private static void recycle(_Builder param_Builder)
    {
      _recycled_builder.push(param_Builder);
      param_Builder._node = null;
      param_Builder._content = null;
      param_Builder._eliminateContent = false;
      param_Builder._callback = null;
    }

    public Object finalItem()
    {
      XulDataNode localXulDataNode = this._node;
      if (this._content != null)
      {
        localXulDataNode.setValue(this._content.toString());
        this._content = null;
      }
      XulFactory.ItemBuilder.FinalCallback localFinalCallback = this._callback;
      recycle(this);
      if (localFinalCallback != null)
        localFinalCallback.onFinal(localXulDataNode);
      return localXulDataNode;
    }

    public boolean initialize(String paramString, XulFactory.Attributes paramAttributes)
    {
      if (paramAttributes == null);
      while (true)
      {
        return true;
        for (int i = 0; i < paramAttributes.getLength(); i++)
          this._node.setAttribute(paramAttributes.getName(i), paramAttributes.getValue(i));
      }
    }

    public XulFactory.ItemBuilder pushSubItem(XulFactory.IPullParser paramIPullParser, String paramString1, String paramString2, XulFactory.Attributes paramAttributes)
    {
      this._eliminateContent = true;
      _Builder local_Builder = create(this._node, paramString2);
      local_Builder.initialize(paramString2, paramAttributes);
      return local_Builder;
    }

    public boolean pushText(String paramString, XulFactory.IPullParser paramIPullParser)
    {
      if (this._eliminateContent)
      {
        this._content = null;
        return true;
      }
      if (this._content == null)
        this._content = new StringBuilder();
      this._content.append(paramIPullParser.getText());
      return true;
    }
  }

  static class _Factory extends XulFactory.ResultBuilder
  {
    public XulFactory.ItemBuilder build(XulFactory.ResultBuilderContext paramResultBuilderContext, String paramString, XulFactory.Attributes paramAttributes)
    {
      XulDataNode._Builder local_Builder = XulDataNode._Builder.create(paramString);
      local_Builder.initialize(paramString, paramAttributes);
      return local_Builder;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.XulDataNode
 * JD-Core Version:    0.6.2
 */