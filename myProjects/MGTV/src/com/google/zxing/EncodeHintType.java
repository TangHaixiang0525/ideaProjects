package com.google.zxing;

public enum EncodeHintType
{
  static
  {
    CHARACTER_SET = new EncodeHintType("CHARACTER_SET", 1);
    EncodeHintType[] arrayOfEncodeHintType = new EncodeHintType[2];
    arrayOfEncodeHintType[0] = ERROR_CORRECTION;
    arrayOfEncodeHintType[1] = CHARACTER_SET;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.EncodeHintType
 * JD-Core Version:    0.6.2
 */