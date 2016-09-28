package com.hp.hpl.sparta.xpath;

import com.hp.hpl.sparta.Sparta;

public abstract class AttrCompareExpr extends AttrExpr
{
  private final String attrValue_;

  AttrCompareExpr(String paramString1, String paramString2)
  {
    super(paramString1);
    this.attrValue_ = Sparta.intern(paramString2);
  }

  public String getAttrValue()
  {
    return this.attrValue_;
  }

  protected String toString(String paramString)
  {
    return "[" + super.toString() + paramString + "'" + this.attrValue_ + "']";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.xpath.AttrCompareExpr
 * JD-Core Version:    0.6.2
 */