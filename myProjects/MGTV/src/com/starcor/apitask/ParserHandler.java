package com.starcor.apitask;

import org.xml.sax.helpers.DefaultHandler;

public class ParserHandler extends DefaultHandler
{
  private Object result;

  public Object getResult()
  {
    return this.result;
  }

  public void setResult(Object paramObject)
  {
    this.result = paramObject;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.apitask.ParserHandler
 * JD-Core Version:    0.6.2
 */