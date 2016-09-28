package com.hp.hpl.sparta.xpath;

public abstract class TextCompareExpr extends BooleanExpr
{
  private final String value_;

  TextCompareExpr(String paramString)
  {
    this.value_ = paramString;
  }

  public String getValue()
  {
    return this.value_;
  }

  protected String toString(String paramString)
  {
    return "[text()" + paramString + "'" + this.value_ + "']";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.xpath.TextCompareExpr
 * JD-Core Version:    0.6.2
 */