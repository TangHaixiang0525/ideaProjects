package com.hp.hpl.sparta.xpath;

public abstract class BooleanExpr
{
  public abstract void accept(BooleanExprVisitor paramBooleanExprVisitor)
    throws XPathException;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.xpath.BooleanExpr
 * JD-Core Version:    0.6.2
 */