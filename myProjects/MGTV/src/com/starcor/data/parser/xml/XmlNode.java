package com.starcor.data.parser.xml;

import java.util.ArrayList;
import java.util.List;

public class XmlNode
{
  public List<XmlAttr> attrs;
  public boolean isRoot;
  public String name;
  public String path;
  public String text;

  public void addAttr(String paramString1, String paramString2)
  {
    if (this.attrs == null)
      this.attrs = new ArrayList();
    this.attrs.add(new XmlAttr(paramString1, paramString2));
  }

  public class XmlAttr
  {
    public String name;
    public String value;

    public XmlAttr(String paramString1, String arg3)
    {
      this.name = paramString1;
      Object localObject;
      this.value = localObject;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.data.parser.xml.XmlNode
 * JD-Core Version:    0.6.2
 */