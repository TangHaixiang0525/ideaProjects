package com.sohu.httpserver;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.DefaultHttpServerConnection;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpRequestHandlerRegistry;
import org.apache.http.protocol.HttpService;
import org.apache.http.protocol.ResponseConnControl;
import org.apache.http.protocol.ResponseContent;
import org.apache.http.protocol.ResponseDate;
import org.apache.http.protocol.ResponseServer;

class a extends Thread
{
  private final ServerSocket a;
  private final HttpParams b;
  private final HttpService c;

  public a(int paramInt)
  {
    this.a = new ServerSocket(paramInt);
    HttpResponseInterceptor[] arrayOfHttpResponseInterceptor = new HttpResponseInterceptor[4];
    arrayOfHttpResponseInterceptor[0] = new ResponseDate();
    arrayOfHttpResponseInterceptor[1] = new ResponseServer();
    arrayOfHttpResponseInterceptor[2] = new ResponseContent();
    arrayOfHttpResponseInterceptor[3] = new ResponseConnControl();
    ImmutableHttpProcessor localImmutableHttpProcessor = new ImmutableHttpProcessor(arrayOfHttpResponseInterceptor);
    this.b = new BasicHttpParams();
    this.b.setIntParameter("http.socket.timeout", 5000).setIntParameter("http.socket.buffer-size", 8192).setBooleanParameter("http.connection.stalecheck", false).setBooleanParameter("http.tcp.nodelay", true).setParameter("http.origin-server", "HttpComponents/1.1");
    HttpRequestHandlerRegistry localHttpRequestHandlerRegistry = new HttpRequestHandlerRegistry();
    localHttpRequestHandlerRegistry.register("*", new b());
    this.c = new HttpService(localImmutableHttpProcessor, new DefaultConnectionReuseStrategy(), new DefaultHttpResponseFactory());
    this.c.setParams(this.b);
    this.c.setHandlerResolver(localHttpRequestHandlerRegistry);
  }

  public void run()
  {
    com.sohu.upload.a.a.a("Listening on port " + this.a.getLocalPort());
    com.sohu.upload.a.a.a("Thread.interrupted = " + Thread.interrupted());
    while (!Thread.interrupted())
      try
      {
        Socket localSocket = this.a.accept();
        DefaultHttpServerConnection localDefaultHttpServerConnection = new DefaultHttpServerConnection();
        HttpServer.a(localSocket.getInetAddress().getHostAddress());
        com.sohu.upload.a.a.a("Incoming connection from " + localSocket.getInetAddress());
        localDefaultHttpServerConnection.bind(localSocket, this.b);
        if (com.sohu.upload.b.c.e("thread_work"))
          return;
        c localc = new c(this.c, localDefaultHttpServerConnection);
        localc.setDaemon(true);
        localc.start();
      }
      catch (InterruptedIOException localInterruptedIOException)
      {
      }
      catch (IOException localIOException)
      {
      }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.httpserver.a
 * JD-Core Version:    0.6.2
 */