package com.starcor.core.http;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
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
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public class HttpClientManager
{
  public static final int CONNECT_TIMEOUT = 20000;
  public static final int MAX_ROUTE_CONNECTIONS = 400;
  public static final int MAX_TOTAL_CONNECTIONS = 400;
  public static final int READ_TIMEOUT = 10000;
  public static final int WAIT_TIMEOUT = 10000;
  private static ClientConnectionManager connectionManager = new ThreadSafeClientConnManager(params, localSchemeRegistry);
  private static HttpParams params = new BasicHttpParams();

  static
  {
    HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
    HttpProtocolParams.setHttpElementCharset(params, "UTF-8");
    HttpProtocolParams.setContentCharset(params, "UTF-8");
    HttpProtocolParams.setUseExpectContinue(params, true);
    HttpConnectionParams.setTcpNoDelay(params, true);
    HttpProtocolParams.setUserAgent(params, "Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
    params.setParameter("http.protocol.cookie-policy", "rfc2965");
    ConnManagerParams.setMaxTotalConnections(params, 400);
    ConnPerRouteBean localConnPerRouteBean = new ConnPerRouteBean(400);
    ConnManagerParams.setMaxConnectionsPerRoute(params, localConnPerRouteBean);
    ConnManagerParams.setTimeout(params, 10000L);
    HttpConnectionParams.setConnectionTimeout(params, 20000);
    HttpConnectionParams.setSoTimeout(params, 10000);
    HttpConnectionParams.setSocketBufferSize(params, 8192);
    SchemeRegistry localSchemeRegistry = new SchemeRegistry();
    localSchemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
    localSchemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
  }

  public static HttpClient getHttpClient()
  {
    return new DefaultHttpClient(connectionManager, params);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.http.HttpClientManager
 * JD-Core Version:    0.6.2
 */