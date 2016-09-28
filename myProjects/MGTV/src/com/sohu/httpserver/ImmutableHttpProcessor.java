package com.sohu.httpserver;

import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;

@ThreadSafe
public final class ImmutableHttpProcessor
  implements HttpProcessor
{
  private final HttpRequestInterceptor[] a;
  private final HttpResponseInterceptor[] b;

  public ImmutableHttpProcessor(HttpRequestInterceptor[] paramArrayOfHttpRequestInterceptor, HttpResponseInterceptor[] paramArrayOfHttpResponseInterceptor)
  {
    if (paramArrayOfHttpRequestInterceptor != null)
    {
      int j = paramArrayOfHttpRequestInterceptor.length;
      this.a = new HttpRequestInterceptor[j];
      System.arraycopy(paramArrayOfHttpRequestInterceptor, 0, this.a, 0, j);
    }
    while (paramArrayOfHttpResponseInterceptor != null)
    {
      int i = paramArrayOfHttpResponseInterceptor.length;
      this.b = new HttpResponseInterceptor[i];
      System.arraycopy(paramArrayOfHttpResponseInterceptor, 0, this.b, 0, i);
      return;
      this.a = new HttpRequestInterceptor[0];
    }
    this.b = new HttpResponseInterceptor[0];
  }

  public ImmutableHttpProcessor(HttpResponseInterceptor[] paramArrayOfHttpResponseInterceptor)
  {
    this(null, paramArrayOfHttpResponseInterceptor);
  }

  public void process(HttpRequest paramHttpRequest, HttpContext paramHttpContext)
  {
    HttpRequestInterceptor[] arrayOfHttpRequestInterceptor = this.a;
    int i = arrayOfHttpRequestInterceptor.length;
    for (int j = 0; j < i; j++)
      arrayOfHttpRequestInterceptor[j].process(paramHttpRequest, paramHttpContext);
  }

  public void process(HttpResponse paramHttpResponse, HttpContext paramHttpContext)
  {
    HttpResponseInterceptor[] arrayOfHttpResponseInterceptor = this.b;
    int i = arrayOfHttpResponseInterceptor.length;
    for (int j = 0; j < i; j++)
      arrayOfHttpResponseInterceptor[j].process(paramHttpResponse, paramHttpContext);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.httpserver.ImmutableHttpProcessor
 * JD-Core Version:    0.6.2
 */