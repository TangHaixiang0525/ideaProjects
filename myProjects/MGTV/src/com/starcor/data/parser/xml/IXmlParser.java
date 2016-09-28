package com.starcor.data.parser.xml;

import java.io.InputStream;
import java.util.List;

public abstract interface IXmlParser
{
  public abstract List<XmlNode> parse(InputStream paramInputStream);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.data.parser.xml.IXmlParser
 * JD-Core Version:    0.6.2
 */