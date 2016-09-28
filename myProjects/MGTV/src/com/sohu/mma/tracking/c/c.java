package com.sohu.mma.tracking.c;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;

public class c
{
  private static c a = null;

  public static c a()
  {
    if (a == null)
      a = new c();
    return a;
  }

  // ERROR //
  private String a(org.apache.http.client.HttpClient paramHttpClient, org.apache.http.client.methods.HttpUriRequest paramHttpUriRequest, boolean paramBoolean1, boolean paramBoolean2)
  {
    // Byte code:
    //   0: iload 4
    //   2: ifeq +13 -> 15
    //   5: aload_2
    //   6: ldc 28
    //   8: ldc 30
    //   10: invokeinterface 36 3 0
    //   15: aload_1
    //   16: aload_2
    //   17: invokeinterface 42 2 0
    //   22: astore 20
    //   24: aload 20
    //   26: invokeinterface 48 1 0
    //   31: astore 21
    //   33: aload 20
    //   35: invokeinterface 52 1 0
    //   40: invokevirtual 56	java/lang/Object:toString	()Ljava/lang/String;
    //   43: astore 22
    //   45: aload 20
    //   47: ldc 58
    //   49: invokeinterface 62 2 0
    //   54: astore 23
    //   56: aload 21
    //   58: ifnull +76 -> 134
    //   61: aload 21
    //   63: invokeinterface 68 1 0
    //   68: astore 24
    //   70: aload 24
    //   72: astore 6
    //   74: aload 23
    //   76: ifnull +29 -> 105
    //   79: aload 23
    //   81: invokeinterface 73 1 0
    //   86: ldc 30
    //   88: invokevirtual 79	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   91: ifeq +14 -> 105
    //   94: new 81	java/util/zip/GZIPInputStream
    //   97: dup
    //   98: aload 6
    //   100: invokespecial 84	java/util/zip/GZIPInputStream:<init>	(Ljava/io/InputStream;)V
    //   103: astore 6
    //   105: aload_0
    //   106: aload 6
    //   108: invokevirtual 87	com/sohu/mma/tracking/c/c:a	(Ljava/io/InputStream;)Ljava/lang/String;
    //   111: astore 25
    //   113: aload 25
    //   115: astore 22
    //   117: aload 6
    //   119: ifnull +262 -> 381
    //   122: aload 6
    //   124: invokevirtual 92	java/io/InputStream:close	()V
    //   127: aload 22
    //   129: astore 10
    //   131: aload 10
    //   133: areturn
    //   134: aconst_null
    //   135: astore 6
    //   137: goto -20 -> 117
    //   140: astore 18
    //   142: aconst_null
    //   143: astore 6
    //   145: aload 18
    //   147: invokevirtual 95	org/apache/http/conn/ConnectTimeoutException:printStackTrace	()V
    //   150: aconst_null
    //   151: astore 10
    //   153: aload 6
    //   155: ifnull -24 -> 131
    //   158: aload 6
    //   160: invokevirtual 92	java/io/InputStream:close	()V
    //   163: aconst_null
    //   164: areturn
    //   165: astore 19
    //   167: aload 19
    //   169: invokevirtual 96	java/io/IOException:printStackTrace	()V
    //   172: aconst_null
    //   173: areturn
    //   174: astore 16
    //   176: aconst_null
    //   177: astore 6
    //   179: aload 16
    //   181: invokevirtual 97	java/net/SocketTimeoutException:printStackTrace	()V
    //   184: aconst_null
    //   185: astore 10
    //   187: aload 6
    //   189: ifnull -58 -> 131
    //   192: aload 6
    //   194: invokevirtual 92	java/io/InputStream:close	()V
    //   197: aconst_null
    //   198: areturn
    //   199: astore 17
    //   201: aload 17
    //   203: invokevirtual 96	java/io/IOException:printStackTrace	()V
    //   206: aconst_null
    //   207: areturn
    //   208: astore 14
    //   210: aconst_null
    //   211: astore 6
    //   213: aload 14
    //   215: invokevirtual 98	java/net/UnknownHostException:printStackTrace	()V
    //   218: aconst_null
    //   219: astore 10
    //   221: aload 6
    //   223: ifnull -92 -> 131
    //   226: aload 6
    //   228: invokevirtual 92	java/io/InputStream:close	()V
    //   231: aconst_null
    //   232: areturn
    //   233: astore 15
    //   235: aload 15
    //   237: invokevirtual 96	java/io/IOException:printStackTrace	()V
    //   240: aconst_null
    //   241: areturn
    //   242: astore 12
    //   244: aconst_null
    //   245: astore 6
    //   247: aload 12
    //   249: invokevirtual 99	org/apache/http/client/ClientProtocolException:printStackTrace	()V
    //   252: aconst_null
    //   253: astore 10
    //   255: aload 6
    //   257: ifnull -126 -> 131
    //   260: aload 6
    //   262: invokevirtual 92	java/io/InputStream:close	()V
    //   265: aconst_null
    //   266: areturn
    //   267: astore 13
    //   269: aload 13
    //   271: invokevirtual 96	java/io/IOException:printStackTrace	()V
    //   274: aconst_null
    //   275: areturn
    //   276: astore 9
    //   278: aconst_null
    //   279: astore 6
    //   281: aload 9
    //   283: invokevirtual 96	java/io/IOException:printStackTrace	()V
    //   286: aconst_null
    //   287: astore 10
    //   289: aload 6
    //   291: ifnull -160 -> 131
    //   294: aload 6
    //   296: invokevirtual 92	java/io/InputStream:close	()V
    //   299: aconst_null
    //   300: areturn
    //   301: astore 11
    //   303: aload 11
    //   305: invokevirtual 96	java/io/IOException:printStackTrace	()V
    //   308: aconst_null
    //   309: areturn
    //   310: astore 5
    //   312: aconst_null
    //   313: astore 6
    //   315: aload 5
    //   317: astore 7
    //   319: aload 6
    //   321: ifnull +8 -> 329
    //   324: aload 6
    //   326: invokevirtual 92	java/io/InputStream:close	()V
    //   329: aload 7
    //   331: athrow
    //   332: astore 8
    //   334: aload 8
    //   336: invokevirtual 96	java/io/IOException:printStackTrace	()V
    //   339: goto -10 -> 329
    //   342: astore 26
    //   344: aload 26
    //   346: invokevirtual 96	java/io/IOException:printStackTrace	()V
    //   349: aconst_null
    //   350: areturn
    //   351: astore 7
    //   353: goto -34 -> 319
    //   356: astore 9
    //   358: goto -77 -> 281
    //   361: astore 12
    //   363: goto -116 -> 247
    //   366: astore 14
    //   368: goto -155 -> 213
    //   371: astore 16
    //   373: goto -194 -> 179
    //   376: astore 18
    //   378: goto -233 -> 145
    //   381: aload 22
    //   383: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   5	15	140	org/apache/http/conn/ConnectTimeoutException
    //   15	56	140	org/apache/http/conn/ConnectTimeoutException
    //   61	70	140	org/apache/http/conn/ConnectTimeoutException
    //   158	163	165	java/io/IOException
    //   5	15	174	java/net/SocketTimeoutException
    //   15	56	174	java/net/SocketTimeoutException
    //   61	70	174	java/net/SocketTimeoutException
    //   192	197	199	java/io/IOException
    //   5	15	208	java/net/UnknownHostException
    //   15	56	208	java/net/UnknownHostException
    //   61	70	208	java/net/UnknownHostException
    //   226	231	233	java/io/IOException
    //   5	15	242	org/apache/http/client/ClientProtocolException
    //   15	56	242	org/apache/http/client/ClientProtocolException
    //   61	70	242	org/apache/http/client/ClientProtocolException
    //   260	265	267	java/io/IOException
    //   5	15	276	java/io/IOException
    //   15	56	276	java/io/IOException
    //   61	70	276	java/io/IOException
    //   294	299	301	java/io/IOException
    //   5	15	310	finally
    //   15	56	310	finally
    //   61	70	310	finally
    //   324	329	332	java/io/IOException
    //   122	127	342	java/io/IOException
    //   79	105	351	finally
    //   105	113	351	finally
    //   145	150	351	finally
    //   179	184	351	finally
    //   213	218	351	finally
    //   247	252	351	finally
    //   281	286	351	finally
    //   79	105	356	java/io/IOException
    //   105	113	356	java/io/IOException
    //   79	105	361	org/apache/http/client/ClientProtocolException
    //   105	113	361	org/apache/http/client/ClientProtocolException
    //   79	105	366	java/net/UnknownHostException
    //   105	113	366	java/net/UnknownHostException
    //   79	105	371	java/net/SocketTimeoutException
    //   105	113	371	java/net/SocketTimeoutException
    //   79	105	376	org/apache/http/conn/ConnectTimeoutException
    //   105	113	376	org/apache/http/conn/ConnectTimeoutException
  }

  public String a(InputStream paramInputStream)
  {
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream), 8192);
    StringBuilder localStringBuilder = new StringBuilder();
    try
    {
      str = localBufferedReader.readLine();
      if (str != null);
    }
    finally
    {
      try
      {
        while (true)
        {
          String str;
          paramInputStream.close();
          return localStringBuilder.toString();
          localStringBuilder.append(str + "\n");
        }
        localObject = finally;
        try
        {
          paramInputStream.close();
          throw localObject;
        }
        catch (IOException localIOException1)
        {
          while (true)
            localIOException1.printStackTrace();
        }
      }
      catch (IOException localIOException2)
      {
        while (true)
          localIOException2.printStackTrace();
      }
    }
  }

  public String a(String paramString)
  {
    return a(paramString, true, true);
  }

  public String a(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    try
    {
      HttpGet localHttpGet = new HttpGet(paramString);
      String str = a(b(), localHttpGet, paramBoolean1, paramBoolean2);
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }

  public DefaultHttpClient b()
  {
    BasicHttpParams localBasicHttpParams = new BasicHttpParams();
    ConnManagerParams.setTimeout(localBasicHttpParams, 30000L);
    ConnManagerParams.setMaxConnectionsPerRoute(localBasicHttpParams, new ConnPerRouteBean(30));
    ConnManagerParams.setMaxTotalConnections(localBasicHttpParams, 30);
    HttpConnectionParams.setSoTimeout(localBasicHttpParams, 30000);
    HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, 30000);
    HttpConnectionParams.setTcpNoDelay(localBasicHttpParams, true);
    HttpConnectionParams.setSocketBufferSize(localBasicHttpParams, 8192);
    HttpProtocolParams.setVersion(localBasicHttpParams, HttpVersion.HTTP_1_1);
    HttpProtocolParams.setUserAgent(localBasicHttpParams, "HttpComponents/1.1");
    HttpProtocolParams.setUseExpectContinue(localBasicHttpParams, false);
    SchemeRegistry localSchemeRegistry = new SchemeRegistry();
    localSchemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
    localSchemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
    ThreadSafeClientConnManager localThreadSafeClientConnManager = new ThreadSafeClientConnManager(localBasicHttpParams, localSchemeRegistry);
    localBasicHttpParams.setParameter("http.protocol.handle-redirects", Boolean.FALSE);
    localBasicHttpParams.setParameter("http.protocol.content-charset", "UTF-8");
    return new DefaultHttpClient(localThreadSafeClientConnManager, localBasicHttpParams);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.mma.tracking.c.c
 * JD-Core Version:    0.6.2
 */