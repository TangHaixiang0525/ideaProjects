package com.hp.hpl.sparta.xpath;

public class TextTest extends NodeTest
{
  static final TextTest INSTANCE = new TextTest();

  public void accept(Visitor paramVisitor)
    throws XPathException
  {
    paramVisitor.visit(this);
  }

  public boolean isStringValue()
  {
    return true;
  }

  public String toString()
  {
    return "text()";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.xpath.TextTest
 * JD-Core Version:    0.6.2
 */