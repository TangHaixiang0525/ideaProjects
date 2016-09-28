package com.starcor.hunan.opendownload.drm;

import android.util.Log;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class SampleHTTPProxy extends NanoHTTPD
{
  static final String HTTP_PARAM_STREAM = "stream";
  private static final String PROXY_HOST = "http://127.0.0.1:9876";
  private static final int PROXY_PORT = 9876;
  private static final String TAG = "SampleHTTPProxy";
  private Map<String, String> streamMap = new HashMap();

  public SampleHTTPProxy()
  {
    super(9876);
  }

  private void MappingRequestHeader(NanoHTTPD.IHTTPSession paramIHTTPSession, HttpGet paramHttpGet)
  {
    Map localMap = paramIHTTPSession.getHeaders();
    Iterator localIterator = localMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      String str2 = (String)localMap.get(str1);
      paramHttpGet.addHeader(str1, str2);
      Log.w("SampleHTTPProxy", "Request Header: " + str1 + ": " + str2);
    }
  }

  private void MappingRespHeader(HttpResponse paramHttpResponse, NanoHTTPD.Response paramResponse)
  {
    Header[] arrayOfHeader = paramHttpResponse.getAllHeaders();
    for (int i = 0; i < arrayOfHeader.length; i++)
    {
      Header localHeader = arrayOfHeader[i];
      Log.w("SampleHTTPProxy", "Resp Header: " + localHeader.getName() + ": " + localHeader.getValue());
      if ((localHeader.getName().equals("Accept-Ranges")) || (localHeader.getName().equals("Content-Length")))
        paramResponse.addHeader(localHeader.getName(), localHeader.getValue());
      paramResponse.addHeader("Content-Type", "video/mp2t");
    }
  }

  private String byteToHex(byte[] paramArrayOfByte)
  {
    StringBuilder localStringBuilder = new StringBuilder(2 * paramArrayOfByte.length);
    int i = paramArrayOfByte.length;
    for (int j = 0; j < i; j++)
    {
      int k = paramArrayOfByte[j];
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(k & 0xFF);
      localStringBuilder.append(String.format("%02x", arrayOfObject));
    }
    return localStringBuilder.toString();
  }

  public String makeUrl(String paramString)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("SHA-1");
      localMessageDigest.reset();
      localMessageDigest.update(paramString.getBytes("UTF-8"));
      String str1 = byteToHex(localMessageDigest.digest());
      this.streamMap.put(str1, paramString);
      String str2 = "http://127.0.0.1:9876/?stream=" + str1;
      return str2;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public NanoHTTPD.Response serve(NanoHTTPD.IHTTPSession paramIHTTPSession)
  {
    NanoHTTPD.Method localMethod = paramIHTTPSession.getMethod();
    String str1 = paramIHTTPSession.getUri();
    Log.w("SampleHTTPProxy", "Method: " + localMethod);
    Log.w("SampleHTTPProxy", "Url: " + str1);
    String str2 = (String)paramIHTTPSession.getParms().get("stream");
    String str3 = (String)this.streamMap.get(str2);
    Log.w("SampleHTTPProxy", "stream key: " + str2);
    Log.w("SampleHTTPProxy", "stream url: " + str3);
    DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
    HttpGet localHttpGet = new HttpGet(str3);
    MappingRequestHeader(paramIHTTPSession, localHttpGet);
    HttpResponse localHttpResponse;
    try
    {
      localHttpResponse = localDefaultHttpClient.execute(localHttpGet);
      Log.w("SampleHTTPProxy", "HTTP Resp: " + localHttpResponse.getStatusLine().getStatusCode());
      if (localHttpResponse.getStatusLine().getStatusCode() == 200)
      {
        HttpEntity localHttpEntity2 = localHttpResponse.getEntity();
        if (localHttpEntity2 != null)
        {
          NanoHTTPD.Response localResponse3 = new NanoHTTPD.Response(NanoHTTPD.Response.Status.OK, "video/mp2t", localHttpEntity2.getContent());
          MappingRespHeader(localHttpResponse, localResponse3);
          localResponse3.setChunkedTransfer(true);
          return localResponse3;
        }
        NanoHTTPD.Response localResponse4 = new NanoHTTPD.Response(NanoHTTPD.Response.Status.INTERNAL_ERROR, "text/plain", "Internal Error");
        return localResponse4;
      }
    }
    catch (Exception localException)
    {
      return new NanoHTTPD.Response(NanoHTTPD.Response.Status.INTERNAL_ERROR, "text/plain", "Internal Error");
    }
    if (localHttpResponse.getStatusLine().getStatusCode() == 206)
    {
      HttpEntity localHttpEntity1 = localHttpResponse.getEntity();
      if (localHttpEntity1 != null)
      {
        NanoHTTPD.Response localResponse2 = new NanoHTTPD.Response(NanoHTTPD.Response.Status.PARTIAL_CONTENT, "video/mp2t", localHttpEntity1.getContent());
        MappingRespHeader(localHttpResponse, localResponse2);
        localResponse2.setChunkedTransfer(true);
        return localResponse2;
      }
      return new NanoHTTPD.Response(NanoHTTPD.Response.Status.INTERNAL_ERROR, "text/plain", "Internal Error");
    }
    NanoHTTPD.Response localResponse1 = new NanoHTTPD.Response(NanoHTTPD.Response.Status.INTERNAL_ERROR, "text/plain", "Internal Error");
    return localResponse1;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.opendownload.drm.SampleHTTPProxy
 * JD-Core Version:    0.6.2
 */