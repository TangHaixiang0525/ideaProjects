package com.starcor.data.parser.xml;

import android.text.TextUtils;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class XmlWrapper
  implements IXmlParser
{
  public static final String XML_PATH_SUFFIX = "/";
  public static final String XML_ROOT = "xml_root";
  private IXmlParser mParser;
  private StringBuilder path;
  private XmlNode root;
  private List<XmlNode> wrapNodes;

  public XmlWrapper(IXmlParser paramIXmlParser)
  {
  }

  private void buildPath(XmlNode paramXmlNode)
  {
  }

  private void init()
  {
    this.root = null;
    this.wrapNodes = new ArrayList();
    this.path = new StringBuilder();
  }

  private void processIvalidTag(XmlNode paramXmlNode)
  {
    buildPath(paramXmlNode);
  }

  public List<XmlNode> parse(InputStream paramInputStream)
  {
    Iterator localIterator1 = this.mParser.parse(paramInputStream).iterator();
    XmlNode localXmlNode;
    while (true)
    {
      if (!localIterator1.hasNext())
        return this.wrapNodes;
      localXmlNode = (XmlNode)localIterator1.next();
      if (localXmlNode.isRoot)
      {
        this.wrapNodes.add(localXmlNode);
        this.root = localXmlNode;
      }
      else
      {
        if (!TextUtils.isEmpty(localXmlNode.text))
          break;
        processIvalidTag(localXmlNode);
      }
    }
    List localList = localXmlNode.attrs;
    Object localObject = null;
    Iterator localIterator2;
    if (localList != null)
      localIterator2 = localXmlNode.attrs.iterator();
    while (true)
    {
      if (!localIterator2.hasNext())
      {
        if ((TextUtils.isEmpty((CharSequence)localObject)) && (TextUtils.isEmpty(localXmlNode.text)))
          break;
        this.wrapNodes.add(localXmlNode);
        break;
      }
      XmlNode.XmlAttr localXmlAttr = (XmlNode.XmlAttr)localIterator2.next();
      if (localXmlAttr.name.equals("name"))
        localObject = localXmlAttr.value;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.data.parser.xml.XmlWrapper
 * JD-Core Version:    0.6.2
 */