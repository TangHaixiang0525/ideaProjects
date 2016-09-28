package com.sohu.mobile.tracing.plugin.c;

import android.text.TextUtils;
import com.sohu.mobile.a.a.e;
import com.sohu.mobile.tracing.plugin.expose.Plugin_ExposeAdBoby;
import com.sohu.mobile.tracing.plugin.expose.Plugin_VastTag;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class a
{
  private static a a;
  private static String b = "æçå¹¿å/SDK/V" + com.sohu.mobile.tracing.plugin.d.a.a;

  public static a a()
  {
    if (a == null)
      a = new a();
    return a;
  }

  private boolean b(com.sohu.mobile.tracing.plugin.b.a parama)
  {
    if ((parama == null) || ("".equals(parama.c())))
      return false;
    Plugin_ExposeAdBoby localPlugin_ExposeAdBoby = parama.d();
    Plugin_VastTag localPlugin_VastTag = parama.e();
    String str1 = parama.c();
    if (!TextUtils.isEmpty(str1))
    {
      com.sohu.applist.a.b localb = parama.g();
      if (localb != null)
      {
        String str3 = localb.c();
        if (com.sohu.app.ads.sdk.f.d.a(str3))
        {
          str1 = str1 + str3;
          com.sohu.mobile.tracing.plugin.d.b.a("netUrl=" + str1);
        }
      }
      str1 = str1.replaceAll(" ", "").replaceAll("\\r|\\n", "");
    }
    HttpGet localHttpGet = new HttpGet(str1);
    localHttpGet.setHeader("User-Agent", b);
    HttpParams localHttpParams = localHttpGet.getParams();
    HttpConnectionParams.setConnectionTimeout(localHttpParams, 5000);
    HttpConnectionParams.setSoTimeout(localHttpParams, 5000);
    DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient(localHttpParams);
    HttpResponse localHttpResponse;
    int i;
    try
    {
      localHttpResponse = localDefaultHttpClient.execute(localHttpGet);
      i = localHttpResponse.getStatusLine().getStatusCode();
      com.sohu.mobile.tracing.plugin.d.b.a("statusCode=" + i);
      if ((i >= 400) && (str1.contains("sohu.com")) && (localPlugin_ExposeAdBoby == Plugin_ExposeAdBoby.OAD) && (localPlugin_VastTag == Plugin_VastTag.VAST_IMPRESSION))
        e.c().a("oadTraking_CodeExp");
      if ((i < 300) && (i >= 200))
        return true;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      if ((str1.contains("sohu.com")) && (localPlugin_ExposeAdBoby == Plugin_ExposeAdBoby.OAD) && (localPlugin_VastTag == Plugin_VastTag.VAST_IMPRESSION))
        e.c().a("oadTraking_LinkExp");
      com.sohu.mobile.tracing.plugin.d.b.b("链接异常::" + str1);
      return false;
    }
    if ((i == 301) || (i == 302))
    {
      Header[] arrayOfHeader = localHttpResponse.getHeaders("Location");
      if ((arrayOfHeader != null) && (arrayOfHeader.length > 0))
      {
        String str2 = arrayOfHeader[0].getValue();
        parama.a(str2);
        com.sohu.mobile.tracing.plugin.d.b.a("redirectUrl=" + str2);
        return b(parama);
      }
    }
    return false;
  }

  public boolean a(com.sohu.mobile.tracing.plugin.b.a parama)
  {
    try
    {
      boolean bool = b(parama);
      if (bool)
        return true;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.mobile.tracing.plugin.c.a
 * JD-Core Version:    0.6.2
 */