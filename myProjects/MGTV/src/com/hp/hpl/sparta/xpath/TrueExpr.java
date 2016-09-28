package com.hp.hpl.sparta.xpath;

public class TrueExpr extends BooleanExpr
{
  static final TrueExpr INSTANCE = new TrueExpr();

  public void accept(BooleanExprVisitor paramBooleanExprVisitor)
  {
    paramBooleanExprVisitor.visit(this);
  }

  public String toString()
  {
    return "";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.xpath.TrueExpr
 * JD-Core Version:    0.6.2
 */