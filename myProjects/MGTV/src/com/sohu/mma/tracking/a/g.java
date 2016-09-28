package com.sohu.mma.tracking.a;

import android.content.Context;
import android.content.SharedPreferences;
import com.sohu.mma.tracking.c.d;
import com.sohu.mma.tracking.c.j;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;

public class g extends Thread
{
  public int a = 0;
  private String b;
  private Context c;
  private boolean d;
  private boolean e = false;

  g(String paramString, Context paramContext, boolean paramBoolean)
  {
    this.b = paramString;
    this.c = paramContext;
    this.d = paramBoolean;
  }

  // ERROR //
  private HttpResponse a(String paramString)
  {
    // Byte code:
    //   0: new 33	org/apache/http/impl/client/DefaultHttpClient
    //   3: dup
    //   4: invokespecial 34	org/apache/http/impl/client/DefaultHttpClient:<init>	()V
    //   7: astore_2
    //   8: aload_2
    //   9: invokevirtual 38	org/apache/http/impl/client/DefaultHttpClient:getParams	()Lorg/apache/http/params/HttpParams;
    //   12: ldc 40
    //   14: sipush 1000
    //   17: getstatic 44	com/sohu/mma/tracking/a/a:c	I
    //   20: imul
    //   21: invokestatic 50	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   24: invokeinterface 56 3 0
    //   29: pop
    //   30: aload_2
    //   31: invokevirtual 38	org/apache/http/impl/client/DefaultHttpClient:getParams	()Lorg/apache/http/params/HttpParams;
    //   34: ldc 58
    //   36: sipush 1000
    //   39: getstatic 44	com/sohu/mma/tracking/a/a:c	I
    //   42: imul
    //   43: invokestatic 50	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   46: invokeinterface 56 3 0
    //   51: pop
    //   52: aload_2
    //   53: invokevirtual 38	org/apache/http/impl/client/DefaultHttpClient:getParams	()Lorg/apache/http/params/HttpParams;
    //   56: ldc 60
    //   58: getstatic 66	java/lang/Boolean:FALSE	Ljava/lang/Boolean;
    //   61: invokeinterface 56 3 0
    //   66: pop
    //   67: aload_2
    //   68: new 68	org/apache/http/client/methods/HttpGet
    //   71: dup
    //   72: new 70	java/net/URI
    //   75: dup
    //   76: aload_1
    //   77: invokespecial 73	java/net/URI:<init>	(Ljava/lang/String;)V
    //   80: invokespecial 76	org/apache/http/client/methods/HttpGet:<init>	(Ljava/net/URI;)V
    //   83: invokevirtual 80	org/apache/http/impl/client/DefaultHttpClient:execute	(Lorg/apache/http/client/methods/HttpUriRequest;)Lorg/apache/http/HttpResponse;
    //   86: astore 10
    //   88: aload 10
    //   90: astore 5
    //   92: aload_2
    //   93: ifnull +19 -> 112
    //   96: aload_2
    //   97: invokevirtual 84	org/apache/http/impl/client/DefaultHttpClient:getConnectionManager	()Lorg/apache/http/conn/ClientConnectionManager;
    //   100: ifnull +12 -> 112
    //   103: aload_2
    //   104: invokevirtual 84	org/apache/http/impl/client/DefaultHttpClient:getConnectionManager	()Lorg/apache/http/conn/ClientConnectionManager;
    //   107: invokeinterface 89 1 0
    //   112: aload 5
    //   114: areturn
    //   115: astore_3
    //   116: aconst_null
    //   117: astore_2
    //   118: aload_3
    //   119: invokevirtual 92	java/lang/Exception:printStackTrace	()V
    //   122: ldc 94
    //   124: invokestatic 98	com/sohu/mma/tracking/c/g:a	(Ljava/lang/String;)V
    //   127: aconst_null
    //   128: astore 5
    //   130: aload_2
    //   131: ifnull -19 -> 112
    //   134: aload_2
    //   135: invokevirtual 84	org/apache/http/impl/client/DefaultHttpClient:getConnectionManager	()Lorg/apache/http/conn/ClientConnectionManager;
    //   138: astore 6
    //   140: aconst_null
    //   141: astore 5
    //   143: aload 6
    //   145: ifnull -33 -> 112
    //   148: aload_2
    //   149: invokevirtual 84	org/apache/http/impl/client/DefaultHttpClient:getConnectionManager	()Lorg/apache/http/conn/ClientConnectionManager;
    //   152: invokeinterface 89 1 0
    //   157: aconst_null
    //   158: areturn
    //   159: astore 11
    //   161: aconst_null
    //   162: astore_2
    //   163: aload 11
    //   165: astore 4
    //   167: aload_2
    //   168: ifnull +19 -> 187
    //   171: aload_2
    //   172: invokevirtual 84	org/apache/http/impl/client/DefaultHttpClient:getConnectionManager	()Lorg/apache/http/conn/ClientConnectionManager;
    //   175: ifnull +12 -> 187
    //   178: aload_2
    //   179: invokevirtual 84	org/apache/http/impl/client/DefaultHttpClient:getConnectionManager	()Lorg/apache/http/conn/ClientConnectionManager;
    //   182: invokeinterface 89 1 0
    //   187: aload 4
    //   189: athrow
    //   190: astore 4
    //   192: goto -25 -> 167
    //   195: astore_3
    //   196: goto -78 -> 118
    //
    // Exception table:
    //   from	to	target	type
    //   0	8	115	java/lang/Exception
    //   0	8	159	finally
    //   8	88	190	finally
    //   118	127	190	finally
    //   8	88	195	java/lang/Exception
  }

  private void a()
  {
    Iterator localIterator = j.a(this.c, this.b).getAll().keySet().iterator();
    String str1;
    Long localLong;
    HttpResponse localHttpResponse;
    label170: int i;
    while (true)
    {
      if (!localIterator.hasNext());
      do
      {
        return;
        str1 = (String)localIterator.next();
      }
      while ((this.e) || (!d.d(this.c)));
      try
      {
        localLong = Long.valueOf(j.b(this.c, this.b, str1));
        if ((str1 == null) || ("".equals(str1)))
          continue;
        if (localLong.longValue() <= System.currentTimeMillis())
          break;
        localHttpResponse = a(str1);
        com.sohu.mma.tracking.c.g.a("isNormal:" + this.d + " mma_request_sendUrl:" + str1);
        if (localHttpResponse != null)
          break label170;
        a(str1, localLong.longValue());
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
      continue;
      i = localHttpResponse.getStatusLine().getStatusCode();
      com.sohu.mma.tracking.c.g.a("mma_result_code:" + i);
      if (i != 200)
        break label330;
      a(this.b, str1);
    }
    while (true)
    {
      label224: Header[] arrayOfHeader = localHttpResponse.getHeaders("Location");
      if ((arrayOfHeader != null) && (arrayOfHeader.length > 0))
      {
        String str2 = arrayOfHeader[0].getValue();
        com.sohu.mma.tracking.c.g.a("重定向的URL:" + str2);
        if (str2 != null)
          a(str2);
      }
      a(this.b, str1);
      break;
      label330: 
      do
      {
        a(str1, localLong.longValue());
        break;
        j.c(this.c, this.b, str1);
        break;
        if (i == 302)
          break label224;
      }
      while (i != 301);
    }
  }

  private void a(String paramString, long paramLong)
  {
    if (this.d)
    {
      j.c(this.c, "cn.com.mma.mobile.tracking.normal", paramString);
      j.a(this.c, "cn.com.mma.mobile.tracking.falied", paramString, paramLong);
      j.a(this.c, "cn.com.mma.mobile.tracking.other", paramString, 1L);
      return;
    }
    long l = 1L + j.b(this.c, "cn.com.mma.mobile.tracking.other", paramString);
    if (l > 3L)
    {
      j.c(this.c, "cn.com.mma.mobile.tracking.falied", paramString);
      boolean bool = j.c(this.c, "cn.com.mma.mobile.tracking.other", paramString);
      com.sohu.mma.tracking.c.g.a("mma_failed发送失败超过三次，删除other中记录" + bool);
      return;
    }
    j.a(this.c, "cn.com.mma.mobile.tracking.other", paramString, l);
  }

  private void a(String paramString1, String paramString2)
  {
    j.c(this.c, paramString1, paramString2);
    if (!this.d)
    {
      boolean bool = j.c(this.c, "cn.com.mma.mobile.tracking.other", paramString2);
      com.sohu.mma.tracking.c.g.a("mma_failed数据发送成功，删除other中记录" + bool);
    }
  }

  public void interrupt()
  {
    this.e = true;
    super.interrupt();
  }

  public void run()
  {
    a();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.mma.tracking.a.g
 * JD-Core Version:    0.6.2
 */