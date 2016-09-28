package com.sohutv.tv.player.util;

import android.text.TextUtils;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

public class b
{
  private static String a(InputStream paramInputStream)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    byte[] arrayOfByte = new byte[4096];
    while (true)
    {
      int i = paramInputStream.read(arrayOfByte);
      if (i == -1)
        break;
      localStringBuffer.append(new String(arrayOfByte, 0, i));
    }
    return localStringBuffer.toString();
  }

  public static HttpResponse a(String paramString, List<NameValuePair> paramList)
  {
    return a(paramString, paramList, 5000);
  }

  private static HttpResponse a(String paramString, List<NameValuePair> paramList, int paramInt)
  {
    try
    {
      HttpPost localHttpPost = new HttpPost(paramString);
      if (paramList != null)
        localHttpPost.setEntity(new UrlEncodedFormEntity(paramList, "UTF-8"));
      HttpResponse localHttpResponse = a().execute(localHttpPost);
      return localHttpResponse;
    }
    catch (Exception localException)
    {
      Log.i("Sohuplayer", "postHttp error");
      localException.printStackTrace();
    }
    return null;
  }

  public static HttpClient a()
  {
    try
    {
      BasicHttpParams localBasicHttpParams = new BasicHttpParams();
      HttpProtocolParams.setVersion(localBasicHttpParams, HttpVersion.HTTP_1_1);
      HttpProtocolParams.setContentCharset(localBasicHttpParams, "UTF-8");
      HttpProtocolParams.setUseExpectContinue(localBasicHttpParams, true);
      HttpProtocolParams.setUserAgent(localBasicHttpParams, "Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
      ConnManagerParams.setTimeout(localBasicHttpParams, 5000L);
      HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, 5000);
      HttpConnectionParams.setSoTimeout(localBasicHttpParams, 5000);
      SchemeRegistry localSchemeRegistry = new SchemeRegistry();
      localSchemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
      localSchemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
      localDefaultHttpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(localBasicHttpParams, localSchemeRegistry), localBasicHttpParams);
      return localDefaultHttpClient;
    }
    catch (Exception localException)
    {
      while (true)
      {
        localException.printStackTrace();
        DefaultHttpClient localDefaultHttpClient = null;
      }
    }
    finally
    {
    }
  }

  public static void a(String paramString, final c paramc)
  {
    j.a().execute(new Runnable()
    {
      public void run()
      {
        try
        {
          b.a(b.a().execute(new HttpGet(this.a)), paramc);
          return;
        }
        catch (Exception localException1)
        {
          localException1.printStackTrace();
          try
          {
            b.a(b.a().execute(new HttpGet(this.a)), paramc);
            return;
          }
          catch (Exception localException2)
          {
            localException2.printStackTrace();
          }
        }
      }
    });
  }

  public static void a(String paramString, final List<NameValuePair> paramList, final c paramc)
  {
    if (TextUtils.isEmpty(paramString))
    {
      Object[] arrayOfObject = new Object[4];
      arrayOfObject[0] = Integer.valueOf(-1);
      arrayOfObject[1] = null;
      arrayOfObject[2] = "";
      arrayOfObject[3] = new Throwable("request url is null");
      paramc.a(arrayOfObject);
      return;
    }
    j.a().execute(new Runnable()
    {
      HttpResponse a = null;
      Header[] b = null;
      String c = "";

      public void run()
      {
        try
        {
          this.a = b.a(this.d, paramList);
          if ((this.a != null) && (this.a.getStatusLine().getStatusCode() == 200))
          {
            this.c = EntityUtils.toString(this.a.getEntity(), "GBK");
            if (this.c.indexOf("=") > 0)
              this.c = this.c.split("=")[1];
            this.b = this.a.getAllHeaders();
            Object[] arrayOfObject3 = new Object[3];
            arrayOfObject3[0] = Integer.valueOf(this.a.getStatusLine().getStatusCode());
            arrayOfObject3[1] = this.b;
            arrayOfObject3[2] = this.c;
            paramc.b(arrayOfObject3);
            return;
          }
          Object[] arrayOfObject2 = new Object[4];
          arrayOfObject2[0] = Integer.valueOf(-1);
          arrayOfObject2[1] = null;
          arrayOfObject2[2] = "";
          arrayOfObject2[3] = new Throwable("response is error");
          paramc.a(arrayOfObject2);
          return;
        }
        catch (Exception localException)
        {
          Object[] arrayOfObject1 = new Object[4];
          arrayOfObject1[0] = Integer.valueOf(-1);
          arrayOfObject1[1] = null;
          arrayOfObject1[2] = this.c;
          arrayOfObject1[3] = localException.getCause();
          paramc.a(arrayOfObject1);
          localException.printStackTrace();
        }
      }
    });
  }

  private static void b(HttpResponse paramHttpResponse, c paramc)
  {
    String str = "";
    Header[] arrayOfHeader = null;
    try
    {
      arrayOfHeader = paramHttpResponse.getAllHeaders();
      int i = paramHttpResponse.getStatusLine().getStatusCode();
      str = a(paramHttpResponse.getEntity().getContent());
      if (TextUtils.isEmpty(str))
      {
        Object[] arrayOfObject5 = new Object[4];
        arrayOfObject5[0] = Integer.valueOf(-1);
        arrayOfObject5[1] = arrayOfHeader;
        arrayOfObject5[2] = str;
        arrayOfObject5[3] = new Throwable("response text is null");
        paramc.a(arrayOfObject5);
        return;
      }
      Object[] arrayOfObject4 = new Object[3];
      arrayOfObject4[0] = Integer.valueOf(i);
      arrayOfObject4[1] = arrayOfHeader;
      arrayOfObject4[2] = str;
      paramc.b(arrayOfObject4);
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      localIllegalStateException.printStackTrace();
      Object[] arrayOfObject3 = new Object[4];
      arrayOfObject3[0] = Integer.valueOf(-1);
      arrayOfObject3[1] = arrayOfHeader;
      arrayOfObject3[2] = str;
      arrayOfObject3[3] = localIllegalStateException.getCause();
      paramc.a(arrayOfObject3);
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      Object[] arrayOfObject2 = new Object[4];
      arrayOfObject2[0] = Integer.valueOf(-1);
      arrayOfObject2[1] = arrayOfHeader;
      arrayOfObject2[2] = str;
      arrayOfObject2[3] = localIOException.getCause();
      paramc.a(arrayOfObject2);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      Object[] arrayOfObject1 = new Object[4];
      arrayOfObject1[0] = Integer.valueOf(-1);
      arrayOfObject1[1] = arrayOfHeader;
      arrayOfObject1[2] = str;
      arrayOfObject1[3] = localException.getCause();
      paramc.a(arrayOfObject1);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.util.b
 * JD-Core Version:    0.6.2
 */