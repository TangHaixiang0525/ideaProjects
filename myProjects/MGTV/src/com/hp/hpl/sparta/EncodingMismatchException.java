package com.hp.hpl.sparta;

public class EncodingMismatchException extends ParseException
{
  private String declaredEncoding_;

  EncodingMismatchException(String paramString1, String paramString2, String paramString3)
  {
    super(paramString1, 0, paramString2.charAt(-1 + paramString2.length()), paramString2, "encoding '" + paramString2 + "' declared instead of of " + paramString3 + " as expected");
    this.declaredEncoding_ = paramString2;
  }

  String getDeclaredEncoding()
  {
    return this.declaredEncoding_;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.hp.hpl.sparta.EncodingMismatchException
 * JD-Core Version:    0.6.2
 */