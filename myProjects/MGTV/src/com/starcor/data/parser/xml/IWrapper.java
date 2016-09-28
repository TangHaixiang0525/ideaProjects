package com.starcor.data.parser.xml;

import java.util.List;
import java.util.Map;

public abstract interface IWrapper
{
  public abstract Map<String, Object> wrap(List<XmlNode> paramList);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.data.parser.xml.IWrapper
 * JD-Core Version:    0.6.2
 */