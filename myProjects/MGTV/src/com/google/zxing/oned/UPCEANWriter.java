package com.google.zxing.oned;

public abstract class UPCEANWriter extends OneDimensionalCodeWriter
{
  protected UPCEANWriter()
  {
    super(UPCEANReader.START_END_PATTERN.length << 1);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.UPCEANWriter
 * JD-Core Version:    0.6.2
 */