package com.hp.hpl.sparta.xpath;

public class AttrEqualsExpr extends AttrCompareExpr
{
  AttrEqualsExpr(String paramString1, String paramString2)
  {
    super(paramString1, paramString2);
  }

  public void accept(BooleanExprVisitor paramBooleanExprVisitor)
    throws XPathException
  {
    paramBooleanExprVisitor.visit(this);
  }

  public String toString()
  {
    return toString("=");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.xpath.AttrEqualsExpr
 * JD-Core Version:    0.6.2
 */