package com.hp.hpl.sparta.xpath;

public class ParentNodeTest extends NodeTest
{
  static final ParentNodeTest INSTANCE = new ParentNodeTest();

  public void accept(Visitor paramVisitor)
    throws XPathException
  {
    paramVisitor.visit(this);
  }

  public boolean isStringValue()
  {
    return false;
  }

  public String toString()
  {
    return "..";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.xpath.ParentNodeTest
 * JD-Core Version:    0.6.2
 */