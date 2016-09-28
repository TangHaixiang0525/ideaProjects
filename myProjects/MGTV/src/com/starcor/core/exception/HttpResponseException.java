package com.starcor.core.exception;

import org.apache.http.client.ClientProtocolException;

public class HttpResponseException extends ClientProtocolException
{
  private static final long serialVersionUID = 2425069222735716912L;
  private int state;

  public HttpResponseException(int paramInt)
  {
    super("Wrong HTTP requested that the error status is " + paramInt);
    this.state = paramInt;
  }

  public HttpResponseException(int paramInt, Throwable paramThrowable)
  {
    super("Wrong HTTP requested that the error status is " + paramInt, paramThrowable);
    this.state = paramInt;
  }

  public int getState()
  {
    return this.state;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.exception.HttpResponseException
 * JD-Core Version:    0.6.2
 */