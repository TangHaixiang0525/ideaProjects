package com.starcor.httpapi.core;

import org.apache.http.Header;

public class SCResponse
{
  private byte[] contents;
  private Header[] headers;
  private int httpCode;
  private String httpReason;
  private Header[] requestHeaders;
  private String runReason = "";
  private int runState = 0;
  private String taskName;

  public byte[] getContents()
  {
    return this.contents;
  }

  public String getHeaderValue(String paramString)
  {
    if (this.headers != null)
      for (int i = 0; i < this.headers.length; i++)
        if (this.headers[i].getName().equalsIgnoreCase(paramString))
          return this.headers[i].getValue();
    return null;
  }

  public Header[] getHeaders()
  {
    return this.headers;
  }

  public int getHttpCode()
  {
    return this.httpCode;
  }

  public String getHttpReason()
  {
    return this.httpReason;
  }

  public Header[] getRequestHeaders()
  {
    return this.requestHeaders;
  }

  public String getRunReason()
  {
    return this.runReason;
  }

  public int getRunState()
  {
    return this.runState;
  }

  public String getTaskName()
  {
    return this.taskName;
  }

  public void setContents(byte[] paramArrayOfByte)
  {
    this.contents = paramArrayOfByte;
  }

  public void setHeaders(Header[] paramArrayOfHeader)
  {
    this.headers = paramArrayOfHeader;
  }

  public void setHttpCode(int paramInt)
  {
    this.httpCode = paramInt;
  }

  public void setHttpReason(String paramString)
  {
    this.httpReason = paramString;
  }

  public void setRequestHeaders(Header[] paramArrayOfHeader)
  {
    this.requestHeaders = paramArrayOfHeader;
  }

  public void setRunReason(String paramString)
  {
    this.runReason = paramString;
  }

  public void setRunState(int paramInt)
  {
    this.runState = paramInt;
  }

  public String setTaskName(String paramString)
  {
    this.taskName = paramString;
    return paramString;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.httpapi.core.SCResponse
 * JD-Core Version:    0.6.2
 */