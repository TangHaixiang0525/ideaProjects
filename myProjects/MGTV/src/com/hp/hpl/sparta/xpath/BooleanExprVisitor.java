package com.hp.hpl.sparta.xpath;

public abstract interface BooleanExprVisitor
{
  public abstract void visit(AttrEqualsExpr paramAttrEqualsExpr)
    throws XPathException;

  public abstract void visit(AttrExistsExpr paramAttrExistsExpr)
    throws XPathException;

  public abstract void visit(AttrGreaterExpr paramAttrGreaterExpr)
    throws XPathException;

  public abstract void visit(AttrLessExpr paramAttrLessExpr)
    throws XPathException;

  public abstract void visit(AttrNotEqualsExpr paramAttrNotEqualsExpr)
    throws XPathException;

  public abstract void visit(PositionEqualsExpr paramPositionEqualsExpr)
    throws XPathException;

  public abstract void visit(TextEqualsExpr paramTextEqualsExpr)
    throws XPathException;

  public abstract void visit(TextExistsExpr paramTextExistsExpr)
    throws XPathException;

  public abstract void visit(TextNotEqualsExpr paramTextNotEqualsExpr)
    throws XPathException;

  public abstract void visit(TrueExpr paramTrueExpr);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.xpath.BooleanExprVisitor
 * JD-Core Version:    0.6.2
 */