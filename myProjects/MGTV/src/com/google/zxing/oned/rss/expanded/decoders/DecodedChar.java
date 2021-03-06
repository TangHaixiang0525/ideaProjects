package com.google.zxing.oned.rss.expanded.decoders;

final class DecodedChar extends DecodedObject
{
  static final char FNC1 = '$';
  private final char value;

  DecodedChar(int paramInt, char paramChar)
  {
    super(paramInt);
    this.value = paramChar;
  }

  char getValue()
  {
    return this.value;
  }

  boolean isFNC1()
  {
    return this.value == '$';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.rss.expanded.decoders.DecodedChar
 * JD-Core Version:    0.6.2
 */