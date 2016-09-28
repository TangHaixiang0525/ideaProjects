package com.hp.hpl.sparta.xpath;

public class AttrLessExpr extends AttrRelationalExpr
{
  public AttrLessExpr(String paramString, int paramInt)
  {
    super(paramString, paramInt);
  }

  public void accept(BooleanExprVisitor paramBooleanExprVisitor)
    throws XPathException
  {
    paramBooleanExprVisitor.visit(this);
  }

  public String toString()
  {
    return toString("<");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.xpath.AttrLessExpr
 * JD-Core Version:    0.6.2
 */