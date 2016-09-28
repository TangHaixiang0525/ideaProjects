package com.hp.hpl.sparta;

public class DOMException extends Exception
{
  public static final short DOMSTRING_SIZE_ERR = 2;
  public static final short HIERARCHY_REQUEST_ERR = 3;
  public static final short NOT_FOUND_ERR = 8;
  public short code;

  public DOMException(short paramShort, String paramString)
  {
    super(paramString);
    this.code = paramShort;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.DOMException
 * JD-Core Version:    0.6.2
 */