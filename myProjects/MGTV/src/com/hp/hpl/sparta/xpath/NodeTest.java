package com.hp.hpl.sparta.xpath;

public abstract class NodeTest
{
  public abstract void accept(Visitor paramVisitor)
    throws XPathException;

  public abstract boolean isStringValue();
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.xpath.NodeTest
 * JD-Core Version:    0.6.2
 */