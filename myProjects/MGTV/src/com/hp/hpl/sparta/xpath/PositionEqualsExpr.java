package com.hp.hpl.sparta.xpath;

public class PositionEqualsExpr extends BooleanExpr
{
  private final int position_;

  public PositionEqualsExpr(int paramInt)
  {
    this.position_ = paramInt;
  }

  public void accept(BooleanExprVisitor paramBooleanExprVisitor)
    throws XPathException
  {
    paramBooleanExprVisitor.visit(this);
  }

  public int getPosition()
  {
    return this.position_;
  }

  public String toString()
  {
    return "[" + this.position_ + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.xpath.PositionEqualsExpr
 * JD-Core Version:    0.6.2
 */