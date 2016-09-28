package com.starcor.xul.Factory;

import com.starcor.xul.Utils.XulCachedHashMap;
import com.starcor.xul.XulUtils;

public class XulParserCachedTag extends XulFactory.Attributes
{
  XulCachedHashMap<String, String> _attrs = null;
  int _length = 0;
  String _name;

  public XulParserCachedTag(String paramString, XulFactory.Attributes paramAttributes)
  {
    this._name = XulUtils.getCachedString(paramString);
    if (paramAttributes == null);
    while (true)
    {
      return;
      int i = paramAttributes.getLength();
      this._length = i;
      if (i != 0)
      {
        this._attrs = new XulCachedHashMap();
        for (int j = 0; j < i; j++)
        {
          String str1 = XulUtils.getCachedString(paramAttributes.getName(j));
          String str2 = XulUtils.getCachedString(paramAttributes.getValue(j));
          this._attrs.put(str1, str2);
        }
      }
    }
  }

  public int getLength()
  {
    return this._length;
  }

  public String getName(int paramInt)
  {
    return null;
  }

  public String getTagName()
  {
    return this._name;
  }

  public String getValue(int paramInt)
  {
    return null;
  }

  public String getValue(String paramString)
  {
    if (this._attrs == null)
      return null;
    return (String)this._attrs.get(paramString);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Factory.XulParserCachedTag
 * JD-Core Version:    0.6.2
 */