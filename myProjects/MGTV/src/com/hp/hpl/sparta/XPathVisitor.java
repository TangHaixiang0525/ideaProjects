package com.hp.hpl.sparta;

import com.hp.hpl.sparta.xpath.AllElementTest;
import com.hp.hpl.sparta.xpath.AttrCompareExpr;
import com.hp.hpl.sparta.xpath.AttrEqualsExpr;
import com.hp.hpl.sparta.xpath.AttrExistsExpr;
import com.hp.hpl.sparta.xpath.AttrExpr;
import com.hp.hpl.sparta.xpath.AttrGreaterExpr;
import com.hp.hpl.sparta.xpath.AttrLessExpr;
import com.hp.hpl.sparta.xpath.AttrNotEqualsExpr;
import com.hp.hpl.sparta.xpath.AttrRelationalExpr;
import com.hp.hpl.sparta.xpath.AttrTest;
import com.hp.hpl.sparta.xpath.BooleanExpr;
import com.hp.hpl.sparta.xpath.ElementTest;
import com.hp.hpl.sparta.xpath.NodeTest;
import com.hp.hpl.sparta.xpath.ParentNodeTest;
import com.hp.hpl.sparta.xpath.PositionEqualsExpr;
import com.hp.hpl.sparta.xpath.Step;
import com.hp.hpl.sparta.xpath.TextCompareExpr;
import com.hp.hpl.sparta.xpath.TextEqualsExpr;
import com.hp.hpl.sparta.xpath.TextExistsExpr;
import com.hp.hpl.sparta.xpath.TextNotEqualsExpr;
import com.hp.hpl.sparta.xpath.TextTest;
import com.hp.hpl.sparta.xpath.ThisNodeTest;
import com.hp.hpl.sparta.xpath.TrueExpr;
import com.hp.hpl.sparta.xpath.Visitor;
import com.hp.hpl.sparta.xpath.XPath;
import com.hp.hpl.sparta.xpath.XPathException;
import java.util.Enumeration;
import java.util.Vector;

class XPathVisitor
  implements Visitor
{
  private static final Boolean FALSE = new Boolean(false);
  private static final Boolean TRUE = new Boolean(true);
  private Node contextNode_;
  private final BooleanStack exprStack_ = new BooleanStack(null);
  private boolean multiLevel_;
  private Object node_ = null;
  private Vector nodelistFiltered_ = new Vector();
  private final NodeListWithPosition nodelistRaw_ = new NodeListWithPosition();
  private Enumeration nodesetIterator_ = null;
  private XPath xpath_;

  public XPathVisitor(Document paramDocument, XPath paramXPath)
    throws XPathException
  {
    this(paramXPath, paramDocument);
  }

  public XPathVisitor(Element paramElement, XPath paramXPath)
    throws XPathException
  {
    this(paramXPath, paramElement);
    if (paramXPath.isAbsolute())
      throw new XPathException(paramXPath, "Cannot use element as context node for absolute xpath");
  }

  private XPathVisitor(XPath paramXPath, Node paramNode)
    throws XPathException
  {
    this.xpath_ = paramXPath;
    this.contextNode_ = paramNode;
    this.nodelistFiltered_ = new Vector(1);
    this.nodelistFiltered_.addElement(this.contextNode_);
    Enumeration localEnumeration = paramXPath.getSteps();
    while (true)
    {
      if (!localEnumeration.hasMoreElements())
        return;
      Step localStep = (Step)localEnumeration.nextElement();
      this.multiLevel_ = localStep.isMultiLevel();
      this.nodesetIterator_ = null;
      localStep.getNodeTest().accept(this);
      this.nodesetIterator_ = this.nodelistRaw_.iterator();
      this.nodelistFiltered_.removeAllElements();
      BooleanExpr localBooleanExpr = localStep.getPredicate();
      while (this.nodesetIterator_.hasMoreElements())
      {
        this.node_ = this.nodesetIterator_.nextElement();
        localBooleanExpr.accept(this);
        if (this.exprStack_.pop().booleanValue())
          this.nodelistFiltered_.addElement(this.node_);
      }
    }
  }

  private void accumulateElements(Document paramDocument)
  {
    Element localElement = paramDocument.getDocumentElement();
    this.nodelistRaw_.add(localElement, 1);
    if (this.multiLevel_)
      accumulateElements(localElement);
  }

  private void accumulateElements(Element paramElement)
  {
    int i = 0;
    for (Node localNode = paramElement.getFirstChild(); ; localNode = localNode.getNextSibling())
    {
      if (localNode == null)
        return;
      if ((localNode instanceof Element))
      {
        NodeListWithPosition localNodeListWithPosition = this.nodelistRaw_;
        int j = i + 1;
        localNodeListWithPosition.add(localNode, j);
        if (this.multiLevel_)
          accumulateElements((Element)localNode);
        i = j;
      }
    }
  }

  private void accumulateMatchingElements(Document paramDocument, String paramString)
  {
    Element localElement = paramDocument.getDocumentElement();
    if (localElement == null);
    do
    {
      return;
      if (localElement.getTagName() == paramString)
        this.nodelistRaw_.add(localElement, 1);
    }
    while (!this.multiLevel_);
    accumulateMatchingElements(localElement, paramString);
  }

  private void accumulateMatchingElements(Element paramElement, String paramString)
  {
    int i = 0;
    for (Node localNode = paramElement.getFirstChild(); ; localNode = localNode.getNextSibling())
    {
      if (localNode == null)
        return;
      if ((localNode instanceof Element))
      {
        Element localElement = (Element)localNode;
        if (localElement.getTagName() == paramString)
        {
          NodeListWithPosition localNodeListWithPosition = this.nodelistRaw_;
          i++;
          localNodeListWithPosition.add(localElement, i);
        }
        if (this.multiLevel_)
          accumulateMatchingElements(localElement, paramString);
      }
    }
  }

  public Element getFirstResultElement()
  {
    if (this.nodelistFiltered_.size() == 0)
      return null;
    return (Element)this.nodelistFiltered_.elementAt(0);
  }

  public String getFirstResultString()
  {
    if (this.nodelistFiltered_.size() == 0)
      return null;
    return this.nodelistFiltered_.elementAt(0).toString();
  }

  public Enumeration getResultEnumeration()
  {
    return this.nodelistFiltered_.elements();
  }

  public void visit(AllElementTest paramAllElementTest)
  {
    Vector localVector = this.nodelistFiltered_;
    this.nodelistRaw_.removeAllElements();
    Enumeration localEnumeration = localVector.elements();
    while (true)
    {
      if (!localEnumeration.hasMoreElements())
        return;
      Object localObject = localEnumeration.nextElement();
      if ((localObject instanceof Element))
        accumulateElements((Element)localObject);
      else if ((localObject instanceof Document))
        accumulateElements((Document)localObject);
    }
  }

  public void visit(AttrEqualsExpr paramAttrEqualsExpr)
    throws XPathException
  {
    if (!(this.node_ instanceof Element))
      throw new XPathException(this.xpath_, "Cannot test attribute of document");
    String str = ((Element)this.node_).getAttribute(paramAttrEqualsExpr.getAttrName());
    boolean bool = paramAttrEqualsExpr.getAttrValue().equals(str);
    BooleanStack localBooleanStack = this.exprStack_;
    if (bool);
    for (Boolean localBoolean = TRUE; ; localBoolean = FALSE)
    {
      localBooleanStack.push(localBoolean);
      return;
    }
  }

  public void visit(AttrExistsExpr paramAttrExistsExpr)
    throws XPathException
  {
    if (!(this.node_ instanceof Element))
      throw new XPathException(this.xpath_, "Cannot test attribute of document");
    String str = ((Element)this.node_).getAttribute(paramAttrExistsExpr.getAttrName());
    int i;
    BooleanStack localBooleanStack;
    if ((str != null) && (str.length() > 0))
    {
      i = 1;
      localBooleanStack = this.exprStack_;
      if (i == 0)
        break label80;
    }
    label80: for (Boolean localBoolean = TRUE; ; localBoolean = FALSE)
    {
      localBooleanStack.push(localBoolean);
      return;
      i = 0;
      break;
    }
  }

  public void visit(AttrGreaterExpr paramAttrGreaterExpr)
    throws XPathException
  {
    if (!(this.node_ instanceof Element))
      throw new XPathException(this.xpath_, "Cannot test attribute of document");
    int i;
    BooleanStack localBooleanStack;
    if (Long.parseLong(((Element)this.node_).getAttribute(paramAttrGreaterExpr.getAttrName())) > paramAttrGreaterExpr.getAttrValue())
    {
      i = 1;
      localBooleanStack = this.exprStack_;
      if (i == 0)
        break label78;
    }
    label78: for (Boolean localBoolean = TRUE; ; localBoolean = FALSE)
    {
      localBooleanStack.push(localBoolean);
      return;
      i = 0;
      break;
    }
  }

  public void visit(AttrLessExpr paramAttrLessExpr)
    throws XPathException
  {
    if (!(this.node_ instanceof Element))
      throw new XPathException(this.xpath_, "Cannot test attribute of document");
    int i;
    BooleanStack localBooleanStack;
    if (Long.parseLong(((Element)this.node_).getAttribute(paramAttrLessExpr.getAttrName())) < paramAttrLessExpr.getAttrValue())
    {
      i = 1;
      localBooleanStack = this.exprStack_;
      if (i == 0)
        break label78;
    }
    label78: for (Boolean localBoolean = TRUE; ; localBoolean = FALSE)
    {
      localBooleanStack.push(localBoolean);
      return;
      i = 0;
      break;
    }
  }

  public void visit(AttrNotEqualsExpr paramAttrNotEqualsExpr)
    throws XPathException
  {
    if (!(this.node_ instanceof Element))
      throw new XPathException(this.xpath_, "Cannot test attribute of document");
    String str = ((Element)this.node_).getAttribute(paramAttrNotEqualsExpr.getAttrName());
    int i;
    BooleanStack localBooleanStack;
    if (!paramAttrNotEqualsExpr.getAttrValue().equals(str))
    {
      i = 1;
      localBooleanStack = this.exprStack_;
      if (i == 0)
        break label80;
    }
    label80: for (Boolean localBoolean = TRUE; ; localBoolean = FALSE)
    {
      localBooleanStack.push(localBoolean);
      return;
      i = 0;
      break;
    }
  }

  public void visit(AttrTest paramAttrTest)
  {
    Vector localVector = this.nodelistFiltered_;
    this.nodelistRaw_.removeAllElements();
    Enumeration localEnumeration = localVector.elements();
    while (true)
    {
      if (!localEnumeration.hasMoreElements())
        return;
      Node localNode = (Node)localEnumeration.nextElement();
      if ((localNode instanceof Element))
      {
        String str = ((Element)localNode).getAttribute(paramAttrTest.getAttrName());
        if (str != null)
          this.nodelistRaw_.add(str);
      }
    }
  }

  public void visit(ElementTest paramElementTest)
  {
    String str = paramElementTest.getTagName();
    Vector localVector = this.nodelistFiltered_;
    int i = localVector.size();
    this.nodelistRaw_.removeAllElements();
    int j = 0;
    if (j >= i)
      return;
    Object localObject = localVector.elementAt(j);
    if ((localObject instanceof Element))
      accumulateMatchingElements((Element)localObject, str);
    while (true)
    {
      j++;
      break;
      if ((localObject instanceof Document))
        accumulateMatchingElements((Document)localObject, str);
    }
  }

  public void visit(ParentNodeTest paramParentNodeTest)
    throws XPathException
  {
    this.nodelistRaw_.removeAllElements();
    Element localElement = this.contextNode_.getParentNode();
    if (localElement == null)
      throw new XPathException(this.xpath_, "Illegal attempt to apply \"..\" to node with no parent.");
    this.nodelistRaw_.add(localElement, 1);
  }

  public void visit(PositionEqualsExpr paramPositionEqualsExpr)
    throws XPathException
  {
    if (!(this.node_ instanceof Element))
      throw new XPathException(this.xpath_, "Cannot test position of document");
    Element localElement = (Element)this.node_;
    int i;
    BooleanStack localBooleanStack;
    if (this.nodelistRaw_.position(localElement) == paramPositionEqualsExpr.getPosition())
    {
      i = 1;
      localBooleanStack = this.exprStack_;
      if (i == 0)
        break label78;
    }
    label78: for (Boolean localBoolean = TRUE; ; localBoolean = FALSE)
    {
      localBooleanStack.push(localBoolean);
      return;
      i = 0;
      break;
    }
  }

  public void visit(TextEqualsExpr paramTextEqualsExpr)
    throws XPathException
  {
    if (!(this.node_ instanceof Element))
      throw new XPathException(this.xpath_, "Cannot test attribute of document");
    for (Node localNode = ((Element)this.node_).getFirstChild(); ; localNode = localNode.getNextSibling())
    {
      if (localNode == null)
      {
        this.exprStack_.push(FALSE);
        return;
      }
      if (((localNode instanceof Text)) && (((Text)localNode).getData().equals(paramTextEqualsExpr.getValue())))
      {
        this.exprStack_.push(TRUE);
        return;
      }
    }
  }

  public void visit(TextExistsExpr paramTextExistsExpr)
    throws XPathException
  {
    if (!(this.node_ instanceof Element))
      throw new XPathException(this.xpath_, "Cannot test attribute of document");
    for (Node localNode = ((Element)this.node_).getFirstChild(); ; localNode = localNode.getNextSibling())
    {
      if (localNode == null)
      {
        this.exprStack_.push(FALSE);
        return;
      }
      if ((localNode instanceof Text))
      {
        this.exprStack_.push(TRUE);
        return;
      }
    }
  }

  public void visit(TextNotEqualsExpr paramTextNotEqualsExpr)
    throws XPathException
  {
    if (!(this.node_ instanceof Element))
      throw new XPathException(this.xpath_, "Cannot test attribute of document");
    for (Node localNode = ((Element)this.node_).getFirstChild(); ; localNode = localNode.getNextSibling())
    {
      if (localNode == null)
      {
        this.exprStack_.push(FALSE);
        return;
      }
      if (((localNode instanceof Text)) && (!((Text)localNode).getData().equals(paramTextNotEqualsExpr.getValue())))
      {
        this.exprStack_.push(TRUE);
        return;
      }
    }
  }

  public void visit(TextTest paramTextTest)
  {
    Vector localVector = this.nodelistFiltered_;
    this.nodelistRaw_.removeAllElements();
    Enumeration localEnumeration = localVector.elements();
    while (true)
    {
      if (!localEnumeration.hasMoreElements())
        return;
      Object localObject = localEnumeration.nextElement();
      if ((localObject instanceof Element))
        for (Node localNode = ((Element)localObject).getFirstChild(); localNode != null; localNode = localNode.getNextSibling())
          if ((localNode instanceof Text))
            this.nodelistRaw_.add(((Text)localNode).getData());
    }
  }

  public void visit(ThisNodeTest paramThisNodeTest)
  {
    this.nodelistRaw_.removeAllElements();
    this.nodelistRaw_.add(this.contextNode_, 1);
  }

  public void visit(TrueExpr paramTrueExpr)
  {
    this.exprStack_.push(TRUE);
  }

  private static class BooleanStack
  {
    private Item top_ = null;

    private BooleanStack()
    {
    }

    BooleanStack(XPathVisitor.1 param1)
    {
      this();
    }

    Boolean pop()
    {
      Boolean localBoolean = this.top_.bool;
      this.top_ = this.top_.prev;
      return localBoolean;
    }

    void push(Boolean paramBoolean)
    {
      this.top_ = new Item(paramBoolean, this.top_);
    }

    private static class Item
    {
      final Boolean bool;
      final Item prev;

      Item(Boolean paramBoolean, Item paramItem)
      {
        this.bool = paramBoolean;
        this.prev = paramItem;
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.XPathVisitor
 * JD-Core Version:    0.6.2
 */