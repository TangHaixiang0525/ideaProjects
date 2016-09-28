package com.hp.hpl.sparta.xpath;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;

public class XPath
{
  private static final int ASSERTION;
  private static Hashtable cache_ = new Hashtable();
  private boolean absolute_;
  private Stack steps_ = new Stack();
  private String string_;

  private XPath(String paramString)
    throws XPathException
  {
    this(paramString, new InputStreamReader(new ByteArrayInputStream(paramString.getBytes())));
  }

  private XPath(String paramString, Reader paramReader)
    throws XPathException
  {
    SimpleStreamTokenizer localSimpleStreamTokenizer;
    while (true)
    {
      try
      {
        this.string_ = paramString;
        localSimpleStreamTokenizer = new SimpleStreamTokenizer(paramReader);
        localSimpleStreamTokenizer.ordinaryChar('/');
        localSimpleStreamTokenizer.ordinaryChar('.');
        localSimpleStreamTokenizer.wordChars(':', ':');
        localSimpleStreamTokenizer.wordChars('_', '_');
        if (localSimpleStreamTokenizer.nextToken() != 47)
          break label167;
        this.absolute_ = true;
        if (localSimpleStreamTokenizer.nextToken() == 47)
        {
          localSimpleStreamTokenizer.nextToken();
          bool1 = true;
          this.steps_.push(new Step(this, bool1, localSimpleStreamTokenizer));
          if (localSimpleStreamTokenizer.ttype == 47)
            break;
          if (localSimpleStreamTokenizer.ttype == -1)
            return;
          throw new XPathException(this, "at end of XPATH expression", localSimpleStreamTokenizer, "end of expression");
        }
      }
      catch (IOException localIOException)
      {
        throw new XPathException(this, localIOException);
      }
      boolean bool1 = false;
      continue;
      label167: this.absolute_ = false;
      bool1 = false;
    }
    if (localSimpleStreamTokenizer.nextToken() == 47)
      localSimpleStreamTokenizer.nextToken();
    for (boolean bool2 = true; ; bool2 = false)
    {
      this.steps_.push(new Step(this, bool2, localSimpleStreamTokenizer));
      break;
    }
  }

  private XPath(boolean paramBoolean, Step[] paramArrayOfStep)
  {
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfStep.length)
      {
        this.absolute_ = paramBoolean;
        this.string_ = null;
        return;
      }
      this.steps_.addElement(paramArrayOfStep[i]);
    }
  }

  private String generateString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Enumeration localEnumeration = this.steps_.elements();
    for (int i = 1; ; i = 0)
    {
      if (!localEnumeration.hasMoreElements())
        return localStringBuffer.toString();
      Step localStep = (Step)localEnumeration.nextElement();
      if ((i == 0) || (this.absolute_))
      {
        localStringBuffer.append('/');
        if (localStep.isMultiLevel())
          localStringBuffer.append('/');
      }
      localStringBuffer.append(localStep.toString());
    }
  }

  public static XPath get(String paramString)
    throws XPathException
  {
    synchronized (cache_)
    {
      XPath localXPath = (XPath)cache_.get(paramString);
      if (localXPath == null)
      {
        localXPath = new XPath(paramString);
        cache_.put(paramString, localXPath);
      }
      return localXPath;
    }
  }

  public static XPath get(boolean paramBoolean, Step[] paramArrayOfStep)
  {
    XPath localXPath1 = new XPath(paramBoolean, paramArrayOfStep);
    String str = localXPath1.toString();
    synchronized (cache_)
    {
      XPath localXPath2 = (XPath)cache_.get(str);
      if (localXPath2 == null)
      {
        cache_.put(str, localXPath1);
        return localXPath1;
      }
      return localXPath2;
    }
  }

  public static boolean isStringValue(String paramString)
    throws XPathException, IOException
  {
    return get(paramString).isStringValue();
  }

  public Object clone()
  {
    Step[] arrayOfStep = new Step[this.steps_.size()];
    Enumeration localEnumeration = this.steps_.elements();
    for (int i = 0; ; i++)
    {
      if (i >= arrayOfStep.length)
        return new XPath(this.absolute_, arrayOfStep);
      arrayOfStep[i] = ((Step)localEnumeration.nextElement());
    }
  }

  public String getIndexingAttrName()
    throws XPathException
  {
    BooleanExpr localBooleanExpr = ((Step)this.steps_.peek()).getPredicate();
    if (!(localBooleanExpr instanceof AttrExistsExpr))
      throw new XPathException(this, "has no indexing attribute name (must end with predicate of the form [@attrName]");
    return ((AttrExistsExpr)localBooleanExpr).getAttrName();
  }

  public String getIndexingAttrNameOfEquals()
    throws XPathException
  {
    BooleanExpr localBooleanExpr = ((Step)this.steps_.peek()).getPredicate();
    if ((localBooleanExpr instanceof AttrEqualsExpr))
      return ((AttrEqualsExpr)localBooleanExpr).getAttrName();
    return null;
  }

  public Enumeration getSteps()
  {
    return this.steps_.elements();
  }

  public boolean isAbsolute()
  {
    return this.absolute_;
  }

  public boolean isStringValue()
  {
    return ((Step)this.steps_.peek()).isStringValue();
  }

  public String toString()
  {
    if (this.string_ == null)
      this.string_ = generateString();
    return this.string_;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.xpath.XPath
 * JD-Core Version:    0.6.2
 */