package com.starcor.xulapp.debug;

import com.starcor.xulapp.http.XulHttpServer.XulHttpServerHandler;
import com.starcor.xulapp.http.XulHttpServer.XulHttpServerRequest;
import com.starcor.xulapp.http.XulHttpServer.XulHttpServerResponse;

public abstract interface IXulDebugCommandHandler
{
  public abstract XulHttpServer.XulHttpServerResponse execCommand(String paramString, XulHttpServer.XulHttpServerHandler paramXulHttpServerHandler, XulHttpServer.XulHttpServerRequest paramXulHttpServerRequest);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xulapp.debug.IXulDebugCommandHandler
 * JD-Core Version:    0.6.2
 */