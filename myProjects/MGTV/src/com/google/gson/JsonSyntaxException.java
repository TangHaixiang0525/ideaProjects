package com.google.gson;

public final class JsonSyntaxException extends JsonParseException
{
  private static final long serialVersionUID = 1L;

  public JsonSyntaxException(String paramString)
  {
    super(paramString);
  }

  public JsonSyntaxException(String paramString, Throwable paramThrowable)
  {
    super(paramString, paramThrowable);
  }

  public JsonSyntaxException(Throwable paramThrowable)
  {
    super(paramThrowable);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.gson.JsonSyntaxException
 * JD-Core Version:    0.6.2
 */