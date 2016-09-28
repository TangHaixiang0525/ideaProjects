package com.starcor.core.domain;

import android.os.Handler;
import com.starcor.core.epgapi.Api;
import com.starcor.core.interfaces.IXmlParser;

public class ApiTask extends Task
{
  private int MessageNumber;
  private Api api;
  private boolean cacheEnable = true;
  private Handler handler;
  private IXmlParser<?> parser;

  public ApiTask()
  {
  }

  public ApiTask(boolean paramBoolean, Api paramApi, Handler paramHandler, IXmlParser<?> paramIXmlParser, int paramInt)
  {
    this.cacheEnable = paramBoolean;
    this.api = paramApi;
    this.handler = paramHandler;
    this.parser = paramIXmlParser;
    this.MessageNumber = paramInt;
  }

  public Api getApi()
  {
    return this.api;
  }

  public Handler getHandler()
  {
    return this.handler;
  }

  public int getMessageNumber()
  {
    return this.MessageNumber;
  }

  public IXmlParser<?> getParser()
  {
    return this.parser;
  }

  public boolean isCacheEnable()
  {
    return this.cacheEnable;
  }

  public void setApi(Api paramApi)
  {
    this.api = paramApi;
  }

  public void setCacheEnable(boolean paramBoolean)
  {
    this.cacheEnable = paramBoolean;
  }

  public void setHandler(Handler paramHandler)
  {
    this.handler = paramHandler;
  }

  public void setMessageNumber(int paramInt)
  {
    this.MessageNumber = paramInt;
  }

  public void setParser(IXmlParser<?> paramIXmlParser)
  {
    this.parser = paramIXmlParser;
  }

  public String toString()
  {
    return "ApiTask [cacheEnable=" + this.cacheEnable + ", api=" + this.api + ", MessageNumber=" + this.MessageNumber + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.ApiTask
 * JD-Core Version:    0.6.2
 */