package com.hp.hpl.sparta.xpath;

public class TextNotEqualsExpr extends TextCompareExpr
{
  TextNotEqualsExpr(String paramString)
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
    return toString("!=");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.xpath.TextNotEqualsExpr
 * JD-Core Version:    0.6.2
 */