package com.hp.hpl.sparta.xpath;

public abstract class AttrRelationalExpr extends AttrExpr
{
  private final int attrValue_;

  AttrRelationalExpr(String paramString, int paramInt)
  {
    super(paramString);
    this.attrValue_ = paramInt;
  }

  public double getAttrValue()
  {
    return this.attrValue_;
  }

  protected String toString(String paramString)
  {
    return "[" + super.toString() + paramString + "'" + this.attrValue_ + "']";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.xpath.AttrRelationalExpr
 * JD-Core Version:    0.6.2
 */