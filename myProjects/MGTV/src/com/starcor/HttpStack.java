package com.starcor;

import com.starcor.core.http.HttpClientManager;
import java.io.InputStream;
import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

public class HttpStack
{
  private int statusCode;

  public InputStream getInputStreamFromURL(String paramString)
    throws Exception
  {
    HttpGet localHttpGet = new HttpGet();
    localHttpGet.setURI(URI.create(paramString));
    HttpResponse localHttpResponse = HttpClientManager.getHttpClient().execute(localHttpGet);
    this.statusCode = localHttpResponse.getStatusLine().getStatusCode();
    if (this.statusCode == 200)
      return localHttpResponse.getEntity().getContent();
    return null;
  }

  public int getStatusCode()
  {
    return this.statusCode;
  }

  public void setStatusCode(int paramInt)
  {
    this.statusCode = paramInt;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.HttpStack
 * JD-Core Version:    0.6.2
 */