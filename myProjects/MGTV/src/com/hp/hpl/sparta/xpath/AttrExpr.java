package com.hp.hpl.sparta.xpath;

public abstract class AttrExpr extends BooleanExpr
{
  private final String attrName_;

  AttrExpr(String paramString)
  {
    this.attrName_ = paramString;
  }

  public String getAttrName()
  {
    return this.attrName_;
  }

  public String toString()
  {
    return "@" + this.attrName_;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.xpath.AttrExpr
 * JD-Core Version:    0.6.2
 */