package com.sohutv.tv.player.entity;

public class OpenAPIResponse<T>
{
  private T data;
  private String message;
  private int status;

  public T getData()
  {
    return this.data;
  }

  public String getMessage()
  {
    return this.message;
  }

  public int getStatus()
  {
    return this.status;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.entity.OpenAPIResponse
 * JD-Core Version:    0.6.2
 */