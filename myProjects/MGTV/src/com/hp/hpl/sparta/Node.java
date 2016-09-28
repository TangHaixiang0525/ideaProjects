package com.hp.hpl.sparta;

import com.hp.hpl.sparta.xpath.BooleanExpr;
import com.hp.hpl.sparta.xpath.ElementTest;
import com.hp.hpl.sparta.xpath.NodeTest;
import com.hp.hpl.sparta.xpath.Step;
import com.hp.hpl.sparta.xpath.XPathException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Vector;

public abstract class Node
{
  private Object annotation_ = null;
  private Document doc_ = null;
  private int hash_ = 0;
  private Node nextSibling_ = null;
  private Element parentNode_ = null;
  private Node previousSibling_ = null;

  protected static void htmlEncode(Writer paramWriter, String paramString)
    throws IOException
  {
    int i = 0;
    int j = paramString.length();
    int k = 0;
    if (k >= j)
    {
      if (i < j)
        paramWriter.write(paramString, i, j - i);
      return;
    }
    int m = paramString.charAt(k);
    String str;
    if (m >= 128)
      str = "&#" + m + ";";
    while (true)
    {
      if (str != null)
      {
        paramWriter.write(paramString, i, k - i);
        paramWriter.write(str);
        i = k + 1;
      }
      k++;
      break;
      switch (m)
      {
      default:
        str = null;
        break;
      case 60:
        str = "&lt;";
        break;
      case 62:
        str = "&gt;";
        break;
      case 38:
        str = "&amp;";
        break;
      case 34:
        str = "&quot;";
        break;
      case 39:
        str = "&#39;";
      }
    }
  }

  public abstract Object clone();

  protected abstract int computeHashCode();

  public Object getAnnotation()
  {
    return this.annotation_;
  }

  public Node getNextSibling()
  {
    return this.nextSibling_;
  }

  public Document getOwnerDocument()
  {
    return this.doc_;
  }

  public Element getParentNode()
  {
    return this.parentNode_;
  }

  public Node getPreviousSibling()
  {
    return this.previousSibling_;
  }

  public int hashCode()
  {
    if (this.hash_ == 0)
      this.hash_ = computeHashCode();
    return this.hash_;
  }

  void insertAtEndOfLinkedList(Node paramNode)
  {
    this.previousSibling_ = paramNode;
    if (paramNode != null)
      paramNode.nextSibling_ = this;
  }

  Element makeMatching(Element paramElement, Step paramStep, String paramString)
    throws ParseException, XPathException
  {
    NodeTest localNodeTest = paramStep.getNodeTest();
    if (!(localNodeTest instanceof ElementTest))
      throw new ParseException("\"" + localNodeTest + "\" in \"" + paramString + "\" is not an element test");
    String str = ((ElementTest)localNodeTest).getTagName();
    Element localElement = new Element(str);
    paramStep.getPredicate().accept(new Node.1(this, localElement, paramElement, paramString, str));
    return localElement;
  }

  void notifyObservers()
  {
    this.hash_ = 0;
    if (this.doc_ != null)
      this.doc_.notifyObservers();
  }

  void removeFromLinkedList()
  {
    if (this.previousSibling_ != null)
      this.previousSibling_.nextSibling_ = this.nextSibling_;
    if (this.nextSibling_ != null)
      this.nextSibling_.previousSibling_ = this.previousSibling_;
    this.nextSibling_ = null;
    this.previousSibling_ = null;
  }

  void replaceInLinkedList(Node paramNode)
  {
    if (this.previousSibling_ != null)
      this.previousSibling_.nextSibling_ = paramNode;
    if (this.nextSibling_ != null)
      this.nextSibling_.previousSibling_ = paramNode;
    paramNode.nextSibling_ = this.nextSibling_;
    paramNode.previousSibling_ = this.previousSibling_;
    this.nextSibling_ = null;
    this.previousSibling_ = null;
  }

  public void setAnnotation(Object paramObject)
  {
    this.annotation_ = paramObject;
  }

  void setOwnerDocument(Document paramDocument)
  {
    this.doc_ = paramDocument;
  }

  void setParentNode(Element paramElement)
  {
    this.parentNode_ = paramElement;
  }

  public String toString()
  {
    try
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      OutputStreamWriter localOutputStreamWriter = new OutputStreamWriter(localByteArrayOutputStream);
      toString(localOutputStreamWriter);
      localOutputStreamWriter.flush();
      String str = new String(localByteArrayOutputStream.toByteArray());
      return str;
    }
    catch (IOException localIOException)
    {
    }
    return super.toString();
  }

  abstract void toString(Writer paramWriter)
    throws IOException;

  public String toXml()
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    OutputStreamWriter localOutputStreamWriter = new OutputStreamWriter(localByteArrayOutputStream);
    toXml(localOutputStreamWriter);
    localOutputStreamWriter.flush();
    return new String(localByteArrayOutputStream.toByteArray());
  }

  abstract void toXml(Writer paramWriter)
    throws IOException;

  public abstract Element xpathSelectElement(String paramString)
    throws ParseException;

  public abstract Enumeration xpathSelectElements(String paramString)
    throws ParseException;

  public abstract String xpathSelectString(String paramString)
    throws ParseException;

  public abstract Enumeration xpathSelectStrings(String paramString)
    throws ParseException;

  public boolean xpathSetStrings(String paramString1, String paramString2)
    throws ParseException
  {
    String str1;
    String str2;
    try
    {
      i = paramString1.lastIndexOf('/');
      if ((!paramString1.substring(i + 1).equals("text()")) && (paramString1.charAt(i + 1) != '@'))
        throw new ParseException("Last step of Xpath expression \"" + paramString1 + "\" is not \"text()\" and does not start with a '@'. It starts with a '" + paramString1.charAt(i + 1) + "'");
    }
    catch (DOMException localDOMException)
    {
      int i;
      throw new Error("Assertion failed " + localDOMException);
      str1 = paramString1.substring(0, i);
      if (paramString1.charAt(i + 1) != '@')
        break label284;
      str2 = paramString1.substring(i + 2);
      if (str2.length() == 0)
        throw new ParseException("Xpath expression \"" + paramString1 + "\" specifies zero-length attribute name\"");
    }
    catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
    {
      throw new ParseException("Xpath expression \"" + paramString1 + "\" is not in the form \"xpathExpression/@attributeName\"");
    }
    Enumeration localEnumeration2 = xpathSelectElements(str1);
    boolean bool3 = false;
    while (true)
    {
      if (!localEnumeration2.hasMoreElements())
        return bool3;
      Element localElement2 = (Element)localEnumeration2.nextElement();
      if (!paramString2.equals(localElement2.getAttribute(str2)))
      {
        localElement2.setAttribute(str2, paramString2);
        bool3 = true;
      }
    }
    label284: Enumeration localEnumeration1 = xpathSelectElements(str1);
    boolean bool1 = localEnumeration1.hasMoreElements();
    Element localElement1;
    Vector localVector;
    label414: boolean bool2;
    if (localEnumeration1.hasMoreElements())
    {
      localElement1 = (Element)localEnumeration1.nextElement();
      localVector = new Vector();
      for (Node localNode = localElement1.getFirstChild(); ; localNode = localNode.getNextSibling())
      {
        if (localNode == null)
        {
          if (localVector.size() != 0)
            break label414;
          Text localText1 = new Text(paramString2);
          if (localText1.getData().length() <= 0)
            break;
          localElement1.appendChild(localText1);
          bool1 = true;
          break;
        }
        if ((localNode instanceof Text))
          localVector.addElement((Text)localNode);
      }
      Text localText2 = (Text)localVector.elementAt(0);
      if (!localText2.getData().equals(paramString2))
      {
        localVector.removeElementAt(0);
        localText2.setData(paramString2);
        bool2 = true;
        break label506;
      }
    }
    while (true)
    {
      if (j >= localVector.size())
      {
        bool1 = bool2;
        break;
      }
      localElement1.removeChild((Text)localVector.elementAt(j));
      j++;
      bool2 = true;
      continue;
      bool2 = bool1;
      break label506;
      return bool1;
      label506: int j = 0;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.Node
 * JD-Core Version:    0.6.2
 */