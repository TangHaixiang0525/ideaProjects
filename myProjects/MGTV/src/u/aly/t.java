package u.aly;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.a;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class t
{
  private String a;
  private String b = "10.0.0.172";
  private int c = 80;
  private Context d;
  private r e;

  public t(Context paramContext)
  {
    this.d = paramContext;
    this.a = a(paramContext);
  }

  private String a(Context paramContext)
  {
    StringBuffer localStringBuffer1 = new StringBuffer();
    localStringBuffer1.append("Android");
    localStringBuffer1.append("/");
    localStringBuffer1.append("5.4.1");
    localStringBuffer1.append(" ");
    try
    {
      StringBuffer localStringBuffer2 = new StringBuffer();
      localStringBuffer2.append(bq.v(paramContext));
      localStringBuffer2.append("/");
      localStringBuffer2.append(bq.d(paramContext));
      localStringBuffer2.append(" ");
      localStringBuffer2.append(Build.MODEL);
      localStringBuffer2.append("/");
      localStringBuffer2.append(Build.VERSION.RELEASE);
      localStringBuffer2.append(" ");
      localStringBuffer2.append(cd.a(AnalyticsConfig.getAppkey(paramContext)));
      localStringBuffer1.append(URLEncoder.encode(localStringBuffer2.toString(), "UTF-8"));
      return localStringBuffer1.toString();
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  private boolean a()
  {
    if (this.d.getPackageManager().checkPermission("android.permission.ACCESS_NETWORK_STATE", this.d.getPackageName()) != 0)
      return false;
    try
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)this.d.getSystemService("connectivity")).getActiveNetworkInfo();
      if ((localNetworkInfo != null) && (localNetworkInfo.getType() != 1))
      {
        String str = localNetworkInfo.getExtraInfo();
        if (str != null)
          if ((!str.equals("cmwap")) && (!str.equals("3gwap")))
          {
            boolean bool = str.equals("uniwap");
            if (!bool);
          }
          else
          {
            return true;
          }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  private byte[] a(byte[] paramArrayOfByte, String paramString)
  {
    HttpPost localHttpPost = new HttpPost(paramString);
    BasicHttpParams localBasicHttpParams = new BasicHttpParams();
    HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, 10000);
    HttpConnectionParams.setSoTimeout(localBasicHttpParams, 30000);
    DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient(localBasicHttpParams);
    localHttpPost.addHeader("X-Umeng-UTC", String.valueOf(System.currentTimeMillis()));
    localHttpPost.addHeader("X-Umeng-Sdk", this.a);
    localHttpPost.addHeader("Msg-Type", "envelope");
    try
    {
      if (a())
      {
        HttpHost localHttpHost = new HttpHost(this.b, this.c);
        localDefaultHttpClient.getParams().setParameter("http.route.default-proxy", localHttpHost);
      }
      localHttpPost.setEntity(new InputStreamEntity(new ByteArrayInputStream(paramArrayOfByte), paramArrayOfByte.length));
      if (this.e != null)
        this.e.a();
      HttpResponse localHttpResponse = localDefaultHttpClient.execute(localHttpPost);
      if (this.e != null)
        this.e.b();
      int i = localHttpResponse.getStatusLine().getStatusCode();
      br.a("MobclickAgent", "status code : " + i);
      if (localHttpResponse.getStatusLine().getStatusCode() == 200)
      {
        br.a("MobclickAgent", "Send message to " + paramString);
        HttpEntity localHttpEntity = localHttpResponse.getEntity();
        if (localHttpEntity != null)
        {
          InputStream localInputStream = localHttpEntity.getContent();
          try
          {
            byte[] arrayOfByte = cd.b(localInputStream);
            return arrayOfByte;
          }
          finally
          {
            cd.c(localInputStream);
          }
        }
      }
    }
    catch (ClientProtocolException localClientProtocolException)
    {
      br.b("MobclickAgent", "ClientProtocolException,Failed to send message.", localClientProtocolException);
      return null;
      return null;
      return null;
    }
    catch (IOException localIOException)
    {
      br.b("MobclickAgent", "IOException,Failed to send message.", localIOException);
    }
    return null;
  }

  public void a(r paramr)
  {
    this.e = paramr;
  }

  public byte[] a(byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte = null;
    for (int i = 0; ; i++)
    {
      if (i < a.f.length)
      {
        arrayOfByte = a(paramArrayOfByte, a.f[i]);
        if (arrayOfByte == null)
          break label45;
        if (this.e != null)
          this.e.c();
      }
      return arrayOfByte;
      label45: if (this.e != null)
        this.e.d();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.t
 * JD-Core Version:    0.6.2
 */