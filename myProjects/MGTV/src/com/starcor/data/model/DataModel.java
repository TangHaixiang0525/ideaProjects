package com.starcor.data.model;

import android.text.TextUtils;
import com.starcor.data.parser.xml.XmlNode;
import com.starcor.data.parser.xml.XmlNode.XmlAttr;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class DataModel
{
  public static final int MODEL_TYPE_DATA_FILL = 1;
  public static final int MODEL_TYPE_DATA_PULL = 2;
  private Iteractor mIteractor;
  private List<XmlNode> mList;
  private int mType;

  private String compareAttr(String paramString, XmlNode paramXmlNode)
  {
    Iterator localIterator = paramXmlNode.attrs.iterator();
    XmlNode.XmlAttr localXmlAttr;
    do
    {
      if (!localIterator.hasNext())
        return null;
      localXmlAttr = (XmlNode.XmlAttr)localIterator.next();
    }
    while (!localXmlAttr.value.startsWith(paramString));
    return localXmlAttr.value;
  }

  private Pair getPairFromNode(String paramString, XmlNode paramXmlNode)
  {
    Pair localPair = new Pair(null);
    if (paramXmlNode.name.startsWith(paramString))
    {
      localPair.key = paramXmlNode.name;
      localPair.value = paramXmlNode.text;
    }
    while (paramXmlNode.attrs == null)
      return localPair;
    String str = compareAttr(paramString, paramXmlNode);
    if (!TextUtils.isEmpty(str))
      localPair.key = str;
    localPair.value = paramXmlNode.text;
    return localPair;
  }

  public static void processModel(DataModel paramDataModel, List<XmlNode> paramList, int paramInt)
  {
    paramDataModel.setData(paramList);
    paramDataModel.setType(paramInt);
    paramDataModel.init();
    paramDataModel.processData();
  }

  private Pair serchForPair(String paramString)
  {
    Pair localPair;
    do
    {
      if (!this.mIteractor.hasNext())
        return null;
      localPair = getPairFromNode(paramString, this.mIteractor.next());
    }
    while (localPair.isEmpty());
    return localPair;
  }

  private String serchForValue(String paramString)
  {
    Pair localPair = serchForPair(paramString);
    this.mIteractor.reset();
    if (localPair == null)
      return null;
    return localPair.value;
  }

  public void cloneData(DataModel paramDataModel)
  {
    this.mType = paramDataModel.mType;
    this.mList = paramDataModel.mList;
  }

  protected Object getData()
  {
    return this.mList;
  }

  protected boolean hasInit()
  {
    return this.mList != null;
  }

  public void init()
  {
    this.mIteractor = new Iteractor(null);
  }

  protected Map<String, Boolean> processBooleanArrays(String paramString)
  {
    Object localObject = null;
    while (true)
    {
      if (!this.mIteractor.hasNext())
      {
        this.mIteractor.reset();
        return localObject;
      }
      Pair localPair = serchForPair(paramString);
      if (localPair != null)
      {
        if (localObject == null)
          localObject = new HashMap();
        ((Map)localObject).put(localPair.key, Boolean.valueOf(localPair.value));
      }
    }
  }

  protected abstract void processData();

  protected int processIntValue(String paramString)
  {
    String str = serchForValue(paramString);
    if (TextUtils.isEmpty(str))
      return 0;
    return Integer.valueOf(str).intValue();
  }

  protected Map<String, String> processStringArrays(String paramString)
  {
    Object localObject = null;
    while (true)
    {
      if (!this.mIteractor.hasNext())
      {
        this.mIteractor.reset();
        return localObject;
      }
      Pair localPair = serchForPair(paramString);
      if (localPair != null)
      {
        if (localObject == null)
          localObject = new HashMap();
        ((Map)localObject).put(localPair.key, localPair.value);
      }
    }
  }

  protected String processStringValue(String paramString)
  {
    return serchForValue(paramString);
  }

  protected void setData(List<XmlNode> paramList)
  {
    if (this.mList == null)
    {
      this.mList = paramList;
      return;
    }
    this.mList.addAll(paramList);
  }

  protected void setType(int paramInt)
  {
    this.mType = paramInt;
  }

  private class Iteractor
  {
    int index = 0;

    private Iteractor()
    {
    }

    XmlNode forward(int paramInt)
    {
      return (XmlNode)DataModel.this.mList.get(paramInt + this.index);
    }

    boolean hasNext()
    {
      return this.index < DataModel.this.mList.size();
    }

    XmlNode next()
    {
      List localList = DataModel.this.mList;
      int i = this.index;
      this.index = (i + 1);
      return (XmlNode)localList.get(i);
    }

    void reset()
    {
      this.index = 0;
    }
  }

  private class Pair
  {
    String key;
    String value;

    private Pair()
    {
    }

    boolean isEmpty()
    {
      return (TextUtils.isEmpty(this.key)) || (TextUtils.isEmpty(this.value));
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.data.model.DataModel
 * JD-Core Version:    0.6.2
 */