package com.hp.hpl.sparta.xpath;

public class ThisNodeTest extends NodeTest
{
  static final ThisNodeTest INSTANCE = new ThisNodeTest();

  public void accept(Visitor paramVisitor)
  {
    paramVisitor.visit(this);
  }

  public boolean isStringValue()
  {
    return false;
  }

  public String toString()
  {
    return ".";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.xpath.ThisNodeTest
 * JD-Core Version:    0.6.2
 */