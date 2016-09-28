package com.hp.hpl.sparta.xpath;

public abstract interface NodeTestVisitor
{
  public abstract void visit(AllElementTest paramAllElementTest);

  public abstract void visit(AttrTest paramAttrTest);

  public abstract void visit(ElementTest paramElementTest);

  public abstract void visit(ParentNodeTest paramParentNodeTest)
    throws XPathException;

  public abstract void visit(TextTest paramTextTest);

  public abstract void visit(ThisNodeTest paramThisNodeTest);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.xpath.NodeTestVisitor
 * JD-Core Version:    0.6.2
 */