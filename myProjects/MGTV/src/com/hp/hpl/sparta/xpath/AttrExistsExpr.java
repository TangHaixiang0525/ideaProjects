package com.hp.hpl.sparta.xpath;

public class AttrExistsExpr extends AttrExpr
{
  AttrExistsExpr(String paramString)
  {
    super(paramString);
  }

  public void accept(BooleanExprVisitor paramBooleanExprVisitor)
    throws XPathException
  {
    paramBooleanExprVisitor.visit(this);
  }

  public String toString()
  {
    return "[" + super.toString() + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.xpath.AttrExistsExpr
 * JD-Core Version:    0.6.2
 */