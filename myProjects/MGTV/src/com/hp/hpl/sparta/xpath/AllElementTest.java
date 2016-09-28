package com.hp.hpl.sparta.xpath;

public class AllElementTest extends NodeTest
{
  static final AllElementTest INSTANCE = new AllElementTest();

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
    return "*";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.xpath.AllElementTest
 * JD-Core Version:    0.6.2
 */