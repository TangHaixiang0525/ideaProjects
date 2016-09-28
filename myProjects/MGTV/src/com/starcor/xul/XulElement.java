package com.starcor.xul;

public abstract class XulElement
{
  public static final int TEMPLATE_TYPE = 2;
  public static final int VIEW_TYPE = 1;
  public final int elementType;

  public XulElement(int paramInt)
  {
    this.elementType = paramInt;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.XulElement
 * JD-Core Version:    0.6.2
 */