package com.hp.hpl.sparta.xpath;

public class AttrTest extends NodeTest
{
  private final String attrName_;

  AttrTest(String paramString)
  {
    this.attrName_ = paramString;
  }

  public void accept(Visitor paramVisitor)
  {
    paramVisitor.visit(this);
  }

  public String getAttrName()
  {
    return this.attrName_;
  }

  public boolean isStringValue()
  {
    return true;
  }

  public String toString()
  {
    return "@" + this.attrName_;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.xpath.AttrTest
 * JD-Core Version:    0.6.2
 */