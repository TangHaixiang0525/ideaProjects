package com.starcor.xulapp.debug;

import com.starcor.xulapp.http.XulHttpServer.XulHttpServerHandler;
import com.starcor.xulapp.http.XulHttpServer.XulHttpServerRequest;
import com.starcor.xulapp.http.XulHttpServer.XulHttpServerResponse;
import org.xmlpull.v1.XmlSerializer;

public abstract interface IXulDebuggableObject
{
  public abstract boolean buildBriefInfo(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, XmlSerializer paramXmlSerializer);

  public abstract boolean buildDetailInfo(XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, XmlSerializer paramXmlSerializer);

  public abstract XulHttpServer.XulHttpServerResponse execCommand(String paramString, XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest, XulHttpServer.XulHttpServerHandler paramXulHttpServerHandler);

  public abstract boolean isValid();

  public abstract String name();

  public abstract boolean runInMainThread();
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xulapp.debug.IXulDebuggableObject
 * JD-Core Version:    0.6.2
 */