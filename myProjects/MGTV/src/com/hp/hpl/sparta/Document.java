package com.hp.hpl.sparta;

import com.hp.hpl.sparta.xpath.Step;
import com.hp.hpl.sparta.xpath.XPath;
import com.hp.hpl.sparta.xpath.XPathException;
import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class Document extends Node
{
  private static final boolean DEBUG;
  static final Enumeration EMPTY = new EmptyEnumeration();
  private static final Integer ONE = new Integer(1);
  private final Hashtable indexible_ = (Hashtable)null;
  private Sparta.Cache indices_ = Sparta.newCache();
  private Vector observers_ = new Vector();
  private Element rootElement_ = null;
  private String systemId_;

  public Document()
  {
    this.systemId_ = "MEMORY";
  }

  Document(String paramString)
  {
    this.systemId_ = paramString;
  }

  private XPathVisitor visitor(String paramString, boolean paramBoolean)
    throws XPathException
  {
    if (paramString.charAt(0) != '/')
      paramString = "/" + paramString;
    return visitor(XPath.get(paramString), paramBoolean);
  }

  public void addObserver(Observer paramObserver)
  {
    this.observers_.addElement(paramObserver);
  }

  public Object clone()
  {
    Document localDocument = new Document(this.systemId_);
    localDocument.rootElement_ = ((Element)this.rootElement_.clone());
    return localDocument;
  }

  protected int computeHashCode()
  {
    return this.rootElement_.hashCode();
  }

  public void deleteObserver(Observer paramObserver)
  {
    this.observers_.removeElement(paramObserver);
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject)
      return true;
    if (!(paramObject instanceof Document))
      return false;
    Document localDocument = (Document)paramObject;
    return this.rootElement_.equals(localDocument.rootElement_);
  }

  public Element getDocumentElement()
  {
    return this.rootElement_;
  }

  public String getSystemId()
  {
    return this.systemId_;
  }

  void monitor(XPath paramXPath)
    throws XPathException
  {
  }

  void notifyObservers()
  {
    Enumeration localEnumeration = this.observers_.elements();
    while (true)
    {
      if (!localEnumeration.hasMoreElements())
        return;
      ((Observer)localEnumeration.nextElement()).update(this);
    }
  }

  public void setDocumentElement(Element paramElement)
  {
    this.rootElement_ = paramElement;
    this.rootElement_.setOwnerDocument(this);
    notifyObservers();
  }

  public void setSystemId(String paramString)
  {
    this.systemId_ = paramString;
    notifyObservers();
  }

  public String toString()
  {
    return this.systemId_;
  }

  public void toString(Writer paramWriter)
    throws IOException
  {
    this.rootElement_.toString(paramWriter);
  }

  public void toXml(Writer paramWriter)
    throws IOException
  {
    paramWriter.write("<?xml version=\"1.0\" ?>\n");
    this.rootElement_.toXml(paramWriter);
  }

  XPathVisitor visitor(XPath paramXPath, boolean paramBoolean)
    throws XPathException
  {
    if (paramXPath.isStringValue() != paramBoolean)
    {
      if (paramBoolean);
      for (String str = "evaluates to element not string"; ; str = "evaluates to string not element")
        throw new XPathException(paramXPath, "\"" + paramXPath + "\" evaluates to " + str);
    }
    return new XPathVisitor(this, paramXPath);
  }

  public boolean xpathEnsure(String paramString)
    throws ParseException
  {
    Step[] arrayOfStep;
    try
    {
      if (xpathSelectElement(paramString) != null)
        return false;
      XPath localXPath = XPath.get(paramString);
      Enumeration localEnumeration1 = localXPath.getSteps();
      int i = 0;
      Enumeration localEnumeration2;
      Step localStep;
      int j;
      if (!localEnumeration1.hasMoreElements())
      {
        localEnumeration2 = localXPath.getSteps();
        localStep = (Step)localEnumeration2.nextElement();
        arrayOfStep = new Step[i - 1];
        j = 0;
        label64: if (j < arrayOfStep.length)
          break label113;
        if (this.rootElement_ != null)
          break label134;
        setDocumentElement(makeMatching(null, localStep, paramString));
      }
      while (true)
        if (arrayOfStep.length == 0)
        {
          return true;
          localEnumeration1.nextElement();
          i++;
          break;
          label113: arrayOfStep[j] = ((Step)localEnumeration2.nextElement());
          j++;
          break label64;
          label134: if (xpathSelectElement("/" + localStep) == null)
            throw new ParseException("Existing root element <" + this.rootElement_.getTagName() + "...> does not match first step \"" + localStep + "\" of \"" + paramString);
        }
    }
    catch (XPathException localXPathException)
    {
      throw new ParseException(paramString, localXPathException);
    }
    boolean bool = this.rootElement_.xpathEnsure(XPath.get(false, arrayOfStep).toString());
    return bool;
  }

  public Index xpathGetIndex(String paramString)
    throws ParseException
  {
    try
    {
      Index localIndex = (Index)this.indices_.get(paramString);
      if (localIndex == null)
      {
        localIndex = new Index(XPath.get(paramString));
        this.indices_.put(paramString, localIndex);
      }
      return localIndex;
    }
    catch (XPathException localXPathException)
    {
      throw new ParseException("XPath problem", localXPathException);
    }
  }

  public boolean xpathHasIndex(String paramString)
  {
    return this.indices_.get(paramString) != null;
  }

  public Element xpathSelectElement(String paramString)
    throws ParseException
  {
    try
    {
      if (paramString.charAt(0) != '/')
        paramString = "/" + paramString;
      XPath localXPath = XPath.get(paramString);
      monitor(localXPath);
      Element localElement = visitor(localXPath, false).getFirstResultElement();
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
      if (paramString.charAt(0) != '/')
        paramString = "/" + paramString;
      XPath localXPath = XPath.get(paramString);
      monitor(localXPath);
      Enumeration localEnumeration = visitor(localXPath, false).getResultEnumeration();
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

  public class Index
    implements Document.Observer
  {
    private final String attrName_;
    private transient Sparta.Cache dict_ = null;
    private final XPath xpath_;

    Index(XPath arg2)
      throws XPathException
    {
      Object localObject;
      this.attrName_ = localObject.getIndexingAttrName();
      this.xpath_ = localObject;
      Document.this.addObserver(this);
    }

    private void regenerate()
      throws ParseException
    {
      try
      {
        this.dict_ = Sparta.newCache();
        Enumeration localEnumeration = Document.this.visitor(this.xpath_, false).getResultEnumeration();
        while (true)
        {
          if (!localEnumeration.hasMoreElements())
            return;
          Element localElement = (Element)localEnumeration.nextElement();
          String str = localElement.getAttribute(this.attrName_);
          Vector localVector = (Vector)this.dict_.get(str);
          if (localVector == null)
          {
            localVector = new Vector(1);
            this.dict_.put(str, localVector);
          }
          localVector.addElement(localElement);
        }
      }
      catch (XPathException localXPathException)
      {
        throw new ParseException("XPath problem", localXPathException);
      }
    }

    public Enumeration get(String paramString)
      throws ParseException
    {
      try
      {
        if (this.dict_ == null)
          regenerate();
        Vector localVector = (Vector)this.dict_.get(paramString);
        if (localVector == null);
        Enumeration localEnumeration;
        for (Object localObject2 = Document.EMPTY; ; localObject2 = localEnumeration)
        {
          return localObject2;
          localEnumeration = localVector.elements();
        }
      }
      finally
      {
      }
    }

    public int size()
      throws ParseException
    {
      try
      {
        if (this.dict_ == null)
          regenerate();
        int i = this.dict_.size();
        return i;
      }
      finally
      {
      }
    }

    public void update(Document paramDocument)
    {
      try
      {
        this.dict_ = null;
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }
  }

  public static abstract interface Observer
  {
    public abstract void update(Document paramDocument);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.Document
 * JD-Core Version:    0.6.2
 */