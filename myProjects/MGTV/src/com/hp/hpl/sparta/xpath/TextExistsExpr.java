package com.hp.hpl.sparta.xpath;

public class TextExistsExpr extends BooleanExpr
{
  static final TextExistsExpr INSTANCE = new TextExistsExpr();

  public void accept(BooleanExprVisitor paramBooleanExprVisitor)
    throws XPathException
  {
    paramBooleanExprVisitor.visit(this);
  }

  public String toString()
  {
    return "[text()]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.xpath.TextExistsExpr
 * JD-Core Version:    0.6.2
 */