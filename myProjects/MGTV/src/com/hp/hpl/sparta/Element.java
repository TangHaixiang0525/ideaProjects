package com.hp.hpl.sparta;

import com.hp.hpl.sparta.xpath.Step;
import com.hp.hpl.sparta.xpath.XPath;
import com.hp.hpl.sparta.xpath.XPathException;
import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class Element extends Node
{
  private static final boolean DEBUG;
  private Vector attributeNames_ = null;
  private Hashtable attributes_ = null;
  private Node firstChild_ = null;
  private Node lastChild_ = null;
  private String tagName_ = null;

  Element()
  {
  }

  public Element(String paramString)
  {
    this.tagName_ = Sparta.intern(paramString);
  }

  private void checkInvariant()
  {
  }

  private boolean removeChildNoChecking(Node paramNode)
  {
    Node localNode = this.firstChild_;
    int i = 0;
    while (true)
    {
      if (localNode == null)
        return false;
      if (localNode.equals(paramNode))
      {
        if (this.firstChild_ == localNode)
          this.firstChild_ = localNode.getNextSibling();
        if (this.lastChild_ == localNode)
          this.lastChild_ = localNode.getPreviousSibling();
        localNode.removeFromLinkedList();
        localNode.setParentNode(null);
        localNode.setOwnerDocument(null);
        return true;
      }
      i++;
      localNode = localNode.getNextSibling();
    }
  }

  private void replaceChild_(Node paramNode1, Node paramNode2)
    throws DOMException
  {
    int i = 0;
    for (Node localNode = this.firstChild_; ; localNode = localNode.getNextSibling())
    {
      if (localNode == null)
        throw new DOMException((short)8, "Cannot find " + paramNode2 + " in " + this);
      if (localNode == paramNode2)
      {
        if (this.firstChild_ == paramNode2)
          this.firstChild_ = paramNode1;
        if (this.lastChild_ == paramNode2)
          this.lastChild_ = paramNode1;
        paramNode2.replaceInLinkedList(paramNode1);
        paramNode1.setParentNode(this);
        paramNode2.setParentNode(null);
        return;
      }
      i++;
    }
  }

  private XPathVisitor visitor(String paramString, boolean paramBoolean)
    throws XPathException
  {
    XPath localXPath = XPath.get(paramString);
    if (localXPath.isStringValue() != paramBoolean)
    {
      if (paramBoolean);
      for (String str = "evaluates to element not string"; ; str = "evaluates to string not element")
        throw new XPathException(localXPath, "\"" + localXPath + "\" evaluates to " + str);
    }
    return new XPathVisitor(this, localXPath);
  }

  public void appendChild(Node paramNode)
  {
    if (!canHaveAsDescendent(paramNode));
    for (Object localObject = (Element)paramNode.clone(); ; localObject = paramNode)
    {
      appendChildNoChecking((Node)localObject);
      notifyObservers();
      return;
    }
  }

  void appendChildNoChecking(Node paramNode)
  {
    Element localElement = paramNode.getParentNode();
    if (localElement != null)
      localElement.removeChildNoChecking(paramNode);
    paramNode.insertAtEndOfLinkedList(this.lastChild_);
    if (this.firstChild_ == null)
      this.firstChild_ = paramNode;
    paramNode.setParentNode(this);
    this.lastChild_ = paramNode;
    paramNode.setOwnerDocument(getOwnerDocument());
  }

  boolean canHaveAsDescendent(Node paramNode)
  {
    if (paramNode == this)
      return false;
    Element localElement = getParentNode();
    if (localElement == null)
      return true;
    return localElement.canHaveAsDescendent(paramNode);
  }

  public Object clone()
  {
    return cloneElement(true);
  }

  public Element cloneElement(boolean paramBoolean)
  {
    Element localElement = new Element(this.tagName_);
    Enumeration localEnumeration;
    if (this.attributeNames_ != null)
    {
      localEnumeration = this.attributeNames_.elements();
      if (localEnumeration.hasMoreElements());
    }
    else if (!paramBoolean);
    for (Node localNode = this.firstChild_; ; localNode = localNode.getNextSibling())
    {
      if (localNode == null)
      {
        return localElement;
        String str = (String)localEnumeration.nextElement();
        localElement.setAttribute(str, (String)this.attributes_.get(str));
        break;
      }
      localElement.appendChild((Node)localNode.clone());
    }
  }

  public Element cloneShallow()
  {
    return cloneElement(false);
  }

  protected int computeHashCode()
  {
    int i = this.tagName_.hashCode();
    Enumeration localEnumeration;
    int j;
    if (this.attributes_ != null)
    {
      localEnumeration = this.attributes_.keys();
      j = i;
      if (localEnumeration.hasMoreElements());
    }
    while (true)
    {
      for (Node localNode = this.firstChild_; ; localNode = localNode.getNextSibling())
      {
        if (localNode == null)
        {
          return j;
          String str1 = (String)localEnumeration.nextElement();
          int k = j * 31 + str1.hashCode();
          String str2 = (String)this.attributes_.get(str1);
          j = k * 31 + str2.hashCode();
          break;
        }
        j = j * 31 + localNode.hashCode();
      }
      j = i;
    }
  }

  public boolean equals(Object paramObject)
  {
    boolean bool2;
    if (this == paramObject)
      bool2 = true;
    Element localElement;
    int i;
    label57: int j;
    label68: 
    do
    {
      boolean bool3;
      do
      {
        boolean bool1;
        do
        {
          return bool2;
          bool1 = paramObject instanceof Element;
          bool2 = false;
        }
        while (!bool1);
        localElement = (Element)paramObject;
        bool3 = this.tagName_.equals(localElement.tagName_);
        bool2 = false;
      }
      while (!bool3);
      if (this.attributes_ != null)
        break;
      i = 0;
      if (localElement.attributes_ != null)
        break label135;
      j = 0;
      bool2 = false;
    }
    while (i != j);
    Enumeration localEnumeration;
    label93: Node localNode1;
    if (this.attributes_ != null)
    {
      localEnumeration = this.attributes_.keys();
      if (localEnumeration.hasMoreElements());
    }
    else
    {
      localNode1 = this.firstChild_;
    }
    for (Node localNode2 = localElement.firstChild_; ; localNode2 = localNode2.getNextSibling())
    {
      if (localNode1 == null)
      {
        return true;
        i = this.attributes_.size();
        break label57;
        label135: j = localElement.attributes_.size();
        break label68;
        String str = (String)localEnumeration.nextElement();
        if (((String)this.attributes_.get(str)).equals((String)localElement.attributes_.get(str)))
          break label93;
        return false;
      }
      boolean bool4 = localNode1.equals(localNode2);
      bool2 = false;
      if (!bool4)
        break;
      localNode1 = localNode1.getNextSibling();
    }
  }

  public String getAttribute(String paramString)
  {
    if (this.attributes_ == null)
      return null;
    return (String)this.attributes_.get(paramString);
  }

  public Enumeration getAttributeNames()
  {
    if (this.attributeNames_ == null)
      return Document.EMPTY;
    return this.attributeNames_.elements();
  }

  public Node getFirstChild()
  {
    return this.firstChild_;
  }

  public Node getLastChild()
  {
    return this.lastChild_;
  }

  public String getTagName()
  {
    return this.tagName_;
  }

  public void removeAttribute(String paramString)
  {
    if (this.attributes_ == null)
      return;
    this.attributes_.remove(paramString);
    this.attributeNames_.removeElement(paramString);
    notifyObservers();
  }

  public void removeChild(Node paramNode)
    throws DOMException
  {
    if (!removeChildNoChecking(paramNode))
      throw new DOMException((short)8, "Cannot find " + paramNode + " in " + this);
    notifyObservers();
  }

  public void replaceChild(Element paramElement, Node paramNode)
    throws DOMException
  {
    replaceChild_(paramElement, paramNode);
    notifyObservers();
  }

  public void replaceChild(Text paramText, Node paramNode)
    throws DOMException
  {
    replaceChild_(paramText, paramNode);
    notifyObservers();
  }

  public void setAttribute(String paramString1, String paramString2)
  {
    if (this.attributes_ == null)
    {
      this.attributes_ = new Hashtable();
      this.attributeNames_ = new Vector();
    }
    if (this.attributes_.get(paramString1) == null)
      this.attributeNames_.addElement(paramString1);
    this.attributes_.put(paramString1, paramString2);
    notifyObservers();
  }

  public void setTagName(String paramString)
  {
    this.tagName_ = Sparta.intern(paramString);
    notifyObservers();
  }

  void toString(Writer paramWriter)
    throws IOException
  {
    for (Node localNode = this.firstChild_; ; localNode = localNode.getNextSibling())
    {
      if (localNode == null)
        return;
      localNode.toString(paramWriter);
    }
  }

  public void toXml(Writer paramWriter)
    throws IOException
  {
    paramWriter.write("<" + this.tagName_);
    Enumeration localEnumeration;
    if (this.attributeNames_ != null)
      localEnumeration = this.attributeNames_.elements();
    while (true)
    {
      if (!localEnumeration.hasMoreElements())
      {
        if (this.firstChild_ != null)
          break;
        paramWriter.write("/>");
        return;
      }
      String str1 = (String)localEnumeration.nextElement();
      String str2 = (String)this.attributes_.get(str1);
      paramWriter.write(" " + str1 + "=\"");
      Node.htmlEncode(paramWriter, str2);
      paramWriter.write("\"");
    }
    paramWriter.write(">");
    for (Node localNode = this.firstChild_; ; localNode = localNode.getNextSibling())
    {
      if (localNode == null)
      {
        paramWriter.write("</" + this.tagName_ + ">");
        return;
      }
      localNode.toXml(paramWriter);
    }
  }

  public boolean xpathEnsure(String paramString)
    throws ParseException
  {
    try
    {
      if (xpathSelectElement(paramString) != null)
        return false;
      XPath localXPath = XPath.get(paramString);
      Enumeration localEnumeration1 = localXPath.getSteps();
      int i = 0;
      Step[] arrayOfStep;
      Enumeration localEnumeration2;
      int j;
      label52: Step localStep;
      if (!localEnumeration1.hasMoreElements())
      {
        arrayOfStep = new Step[i - 1];
        localEnumeration2 = localXPath.getSteps();
        j = 0;
        if (j < arrayOfStep.length)
          break label111;
        localStep = (Step)localEnumeration2.nextElement();
        if (arrayOfStep.length != 0)
          break label132;
      }
      label111: Element localElement;
      for (Object localObject = this; ; localObject = localElement)
      {
        ((Element)localObject).appendChildNoChecking(makeMatching((Element)localObject, localStep, paramString));
        return true;
        localEnumeration1.nextElement();
        i++;
        break;
        arrayOfStep[j] = ((Step)localEnumeration2.nextElement());
        j++;
        break label52;
        label132: String str = XPath.get(localXPath.isAbsolute(), arrayOfStep).toString();
        xpathEnsure(str.toString());
        localElement = xpathSelectElement(str);
      }
    }
    catch (XPathException localXPathException)
    {
      throw new ParseException(paramString, localXPathException);
    }
  }

  public Element xpathSelectElement(String paramString)
    throws ParseException
  {
    try
    {
      Element localElement = visitor(paramString, false).getFirstResultElement();
      return localElement;
    }
    catch (XPathException localXPathException)
    {
      throw new ParseException("XPath problem", localXPathException);
    }
  }

  public Enumeration xpathSelectElements(String paramString)
    throws ParseException
  {
    try
    {
      Enumeration localEnumeration = visitor(paramString, false).getResultEnumeration();
      return localEnumeration;
    }
    catch (XPathException localXPathException)
    {
      throw new ParseException("XPath problem", localXPathException);
    }
  }

  public String xpathSelectString(String paramString)
    throws ParseException
  {
    try
    {
      String str = visitor(paramString, true).getFirstResultString();
      return str;
    }
    catch (XPathException localXPathException)
    {
      throw new ParseException("XPath problem", localXPathException);
    }
  }

  public Enumeration xpathSelectStrings(String paramString)
    throws ParseException
  {
    try
    {
      Enumeration localEnumeration = visitor(paramString, true).getResultEnumeration();
      return localEnumeration;
    }
    catch (XPathException localXPathException)
    {
      throw new ParseException("XPath problem", localXPathException);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.Element
 * JD-Core Version:    0.6.2
 */