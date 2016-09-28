package com.miaozhen.mzmonitor;

import android.content.Context;
import java.net.HttpURLConnection;
import java.net.URL;

class q
{
  private a a;
  private Context b;

  public q(Context paramContext, a parama)
  {
    this.b = paramContext.getApplicationContext();
    this.a = parama;
  }

  private void a(String paramString)
  {
    if (paramString != null);
    try
    {
      URL localURL = new URL(paramString);
      HttpURLConnection.setFollowRedirects(true);
      HttpURLConnection localHttpURLConnection = (HttpURLConnection)localURL.openConnection();
      if (localHttpURLConnection != null)
      {
        localHttpURLConnection.setDefaultUseCaches(false);
        localHttpURLConnection.setUseCaches(false);
        localHttpURLConnection.setConnectTimeout(5000);
        localHttpURLConnection.setReadTimeout(5000);
        localHttpURLConnection.connect();
        localHttpURLConnection.getResponseCode();
        localHttpURLConnection.disconnect();
      }
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public HttpURLConnection a()
  {
    URL localURL = d.a(this.b).a(this.a);
    HttpURLConnection localHttpURLConnection = (HttpURLConnection)localURL.openConnection();
    if (MZDeviceInfo.getDeviceInfo(this.b).getCurrentNetworkType().equals("1"));
    for (int i = 5000; ; i = 10000)
    {
      localHttpURLConnection.setReadTimeout(i);
      localHttpURLConnection.setConnectTimeout(i);
      localHttpURLConnection.setUseCaches(false);
      localHttpURLConnection.setInstanceFollowRedirects(false);
      String str = d.a(this.b).a(localURL).a;
      if ((str != null) && (str.equals("miaozhen")))
        localHttpURLConnection.setRequestProperty("X-MZ-UIC", MZUtility.MD5(localURL.toString()));
      return localHttpURLConnection;
    }
  }

  // ERROR //
  public void b()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: aload_0
    //   3: getfield 21	com/miaozhen/mzmonitor/q:b	Landroid/content/Context;
    //   6: invokestatic 130	com/miaozhen/mzmonitor/b:a	(Landroid/content/Context;)Lcom/miaozhen/mzmonitor/b;
    //   9: astore_2
    //   10: aload_0
    //   11: invokevirtual 132	com/miaozhen/mzmonitor/q:a	()Ljava/net/HttpURLConnection;
    //   14: astore 9
    //   16: aload 9
    //   18: astore 4
    //   20: aconst_null
    //   21: astore_1
    //   22: aload 4
    //   24: ifnull +53 -> 77
    //   27: aload 4
    //   29: invokevirtual 56	java/net/HttpURLConnection:connect	()V
    //   32: aload 4
    //   34: invokevirtual 60	java/net/HttpURLConnection:getResponseCode	()I
    //   37: istore 10
    //   39: iload 10
    //   41: iflt +76 -> 117
    //   44: iload 10
    //   46: sipush 400
    //   49: if_icmpge +68 -> 117
    //   52: ldc 134
    //   54: ldc 136
    //   56: invokestatic 142	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   59: pop
    //   60: aload_2
    //   61: aload_0
    //   62: getfield 23	com/miaozhen/mzmonitor/q:a	Lcom/miaozhen/mzmonitor/a;
    //   65: iconst_1
    //   66: invokevirtual 145	com/miaozhen/mzmonitor/b:a	(Lcom/miaozhen/mzmonitor/a;Z)V
    //   69: aload 4
    //   71: ldc 147
    //   73: invokevirtual 150	java/net/HttpURLConnection:getHeaderField	(Ljava/lang/String;)Ljava/lang/String;
    //   76: astore_1
    //   77: aload_0
    //   78: getfield 21	com/miaozhen/mzmonitor/q:b	Landroid/content/Context;
    //   81: invokestatic 156	com/miaozhen/mzmonitor/MZSdkProfile:isLocationInvalid	(Landroid/content/Context;)Z
    //   84: ifeq +17 -> 101
    //   87: aload_0
    //   88: getfield 21	com/miaozhen/mzmonitor/q:b	Landroid/content/Context;
    //   91: invokestatic 161	com/miaozhen/mzmonitor/k:a	(Landroid/content/Context;)Lcom/miaozhen/mzmonitor/k;
    //   94: aload_0
    //   95: getfield 21	com/miaozhen/mzmonitor/q:b	Landroid/content/Context;
    //   98: invokevirtual 164	com/miaozhen/mzmonitor/k:b	(Landroid/content/Context;)V
    //   101: aload_0
    //   102: aload_1
    //   103: invokespecial 166	com/miaozhen/mzmonitor/q:a	(Ljava/lang/String;)V
    //   106: aload 4
    //   108: ifnull +8 -> 116
    //   111: aload 4
    //   113: invokevirtual 63	java/net/HttpURLConnection:disconnect	()V
    //   116: return
    //   117: ldc 134
    //   119: ldc 168
    //   121: invokestatic 142	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   124: pop
    //   125: aload_2
    //   126: aload_0
    //   127: getfield 23	com/miaozhen/mzmonitor/q:a	Lcom/miaozhen/mzmonitor/a;
    //   130: iconst_0
    //   131: invokevirtual 145	com/miaozhen/mzmonitor/b:a	(Lcom/miaozhen/mzmonitor/a;Z)V
    //   134: aconst_null
    //   135: astore_1
    //   136: goto -59 -> 77
    //   139: astore_3
    //   140: aload_3
    //   141: invokevirtual 169	java/io/IOException:printStackTrace	()V
    //   144: ldc 134
    //   146: new 171	java/lang/StringBuilder
    //   149: dup
    //   150: ldc 173
    //   152: invokespecial 174	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   155: aload_3
    //   156: invokevirtual 177	java/io/IOException:getMessage	()Ljava/lang/String;
    //   159: invokevirtual 181	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   162: invokevirtual 182	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   165: invokestatic 142	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   168: pop
    //   169: aload_2
    //   170: aload_0
    //   171: getfield 23	com/miaozhen/mzmonitor/q:a	Lcom/miaozhen/mzmonitor/a;
    //   174: iconst_0
    //   175: invokevirtual 145	com/miaozhen/mzmonitor/b:a	(Lcom/miaozhen/mzmonitor/a;Z)V
    //   178: aload_0
    //   179: aload_1
    //   180: invokespecial 166	com/miaozhen/mzmonitor/q:a	(Ljava/lang/String;)V
    //   183: aload 4
    //   185: ifnull -69 -> 116
    //   188: aload 4
    //   190: invokevirtual 63	java/net/HttpURLConnection:disconnect	()V
    //   193: return
    //   194: astore 7
    //   196: aconst_null
    //   197: astore 4
    //   199: aload 7
    //   201: invokevirtual 183	java/lang/NullPointerException:printStackTrace	()V
    //   204: ldc 134
    //   206: new 171	java/lang/StringBuilder
    //   209: dup
    //   210: ldc 185
    //   212: invokespecial 174	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   215: aload_0
    //   216: getfield 23	com/miaozhen/mzmonitor/q:a	Lcom/miaozhen/mzmonitor/a;
    //   219: invokevirtual 189	com/miaozhen/mzmonitor/a:a	()Ljava/lang/String;
    //   222: invokevirtual 181	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   225: invokevirtual 182	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   228: invokestatic 142	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   231: pop
    //   232: aload_0
    //   233: aload_1
    //   234: invokespecial 166	com/miaozhen/mzmonitor/q:a	(Ljava/lang/String;)V
    //   237: aload 4
    //   239: ifnull -123 -> 116
    //   242: aload 4
    //   244: invokevirtual 63	java/net/HttpURLConnection:disconnect	()V
    //   247: return
    //   248: astore 5
    //   250: aconst_null
    //   251: astore 4
    //   253: aload_0
    //   254: aload_1
    //   255: invokespecial 166	com/miaozhen/mzmonitor/q:a	(Ljava/lang/String;)V
    //   258: aload 4
    //   260: ifnull +8 -> 268
    //   263: aload 4
    //   265: invokevirtual 63	java/net/HttpURLConnection:disconnect	()V
    //   268: aload 5
    //   270: athrow
    //   271: astore 5
    //   273: goto -20 -> 253
    //   276: astore 7
    //   278: goto -79 -> 199
    //   281: astore_3
    //   282: aconst_null
    //   283: astore_1
    //   284: aconst_null
    //   285: astore 4
    //   287: goto -147 -> 140
    //
    // Exception table:
    //   from	to	target	type
    //   27	39	139	java/io/IOException
    //   52	77	139	java/io/IOException
    //   77	101	139	java/io/IOException
    //   117	134	139	java/io/IOException
    //   10	16	194	java/lang/NullPointerException
    //   10	16	248	finally
    //   27	39	271	finally
    //   52	77	271	finally
    //   77	101	271	finally
    //   117	134	271	finally
    //   140	178	271	finally
    //   199	232	271	finally
    //   27	39	276	java/lang/NullPointerException
    //   52	77	276	java/lang/NullPointerException
    //   77	101	276	java/lang/NullPointerException
    //   117	134	276	java/lang/NullPointerException
    //   10	16	281	java/io/IOException
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.miaozhen.mzmonitor.q
 * JD-Core Version:    0.6.2
 */