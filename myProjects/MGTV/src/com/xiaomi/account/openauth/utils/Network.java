package com.xiaomi.account.openauth.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Network
{
  public static final String CMWAP_GATEWAY = "10.0.0.172";
  public static final String CMWAP_HEADER_HOST_KEY = "X-Online-Host";
  public static final int CONNECTION_TIMEOUT = 10000;
  private static final String LogTag = "com.xiaomi.common.Network";
  public static final int READ_TIMEOUT = 15000;
  public static final String USER_AGENT = "User-Agent";
  private static TrustManager ignoreCertificationTrustManger = new X509TrustManager()
  {
    private X509Certificate[] certificates;

    public void checkClientTrusted(X509Certificate[] paramAnonymousArrayOfX509Certificate, String paramAnonymousString)
      throws CertificateException
    {
      if (this.certificates == null)
      {
        this.certificates = paramAnonymousArrayOfX509Certificate;
        Log.v("openauth", "init at checkClientTrusted.");
      }
    }

    public void checkServerTrusted(X509Certificate[] paramAnonymousArrayOfX509Certificate, String paramAnonymousString)
      throws CertificateException
    {
      if (this.certificates == null)
      {
        this.certificates = paramAnonymousArrayOfX509Certificate;
        Log.v("openauth", "init at checkServerTrusted");
      }
    }

    public X509Certificate[] getAcceptedIssuers()
    {
      return null;
    }
  };
  private static HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier()
  {
    public boolean verify(String paramAnonymousString, SSLSession paramAnonymousSSLSession)
    {
      Log.v("openauth", "WARNING: Hostname is not matched for cert.");
      return true;
    }
  };

  public static String downloadXml(Context paramContext, URL paramURL)
    throws IOException
  {
    return downloadXml(paramContext, paramURL, false, null, "UTF-8", null);
  }

  // ERROR //
  public static String downloadXml(Context paramContext, URL paramURL, String paramString1, String paramString2, Map<String, String> paramMap, HttpHeaderInfo paramHttpHeaderInfo)
    throws IOException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 6
    //   3: aload_0
    //   4: aload_1
    //   5: aload_2
    //   6: aload_3
    //   7: aload 4
    //   9: aload 5
    //   11: invokestatic 55	com/xiaomi/account/openauth/utils/Network:downloadXmlAsStream	(Landroid/content/Context;Ljava/net/URL;Ljava/lang/String;Ljava/lang/String;Ljava/util/Map;Lcom/xiaomi/account/openauth/utils/Network$HttpHeaderInfo;)Ljava/io/InputStream;
    //   14: astore 6
    //   16: new 57	java/lang/StringBuilder
    //   19: dup
    //   20: sipush 1024
    //   23: invokespecial 60	java/lang/StringBuilder:<init>	(I)V
    //   26: astore 13
    //   28: new 62	java/io/BufferedReader
    //   31: dup
    //   32: new 64	java/io/InputStreamReader
    //   35: dup
    //   36: aload 6
    //   38: ldc 47
    //   40: invokespecial 67	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/lang/String;)V
    //   43: sipush 1024
    //   46: invokespecial 70	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   49: astore 14
    //   51: aconst_null
    //   52: astore 6
    //   54: aload 14
    //   56: invokevirtual 74	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   59: astore 15
    //   61: aload 15
    //   63: ifnull +51 -> 114
    //   66: aload 13
    //   68: aload 15
    //   70: invokevirtual 78	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   73: pop
    //   74: aload 13
    //   76: ldc 80
    //   78: invokevirtual 78	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   81: pop
    //   82: goto -28 -> 54
    //   85: astore 7
    //   87: aload 14
    //   89: astore 8
    //   91: aload 6
    //   93: ifnull +8 -> 101
    //   96: aload 6
    //   98: invokevirtual 85	java/io/InputStream:close	()V
    //   101: aload 8
    //   103: ifnull +8 -> 111
    //   106: aload 8
    //   108: invokevirtual 86	java/io/BufferedReader:close	()V
    //   111: aload 7
    //   113: athrow
    //   114: iconst_0
    //   115: ifeq +7 -> 122
    //   118: aconst_null
    //   119: invokevirtual 85	java/io/InputStream:close	()V
    //   122: aload 14
    //   124: ifnull +8 -> 132
    //   127: aload 14
    //   129: invokevirtual 86	java/io/BufferedReader:close	()V
    //   132: aload 13
    //   134: invokevirtual 89	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   137: areturn
    //   138: astore 20
    //   140: ldc 17
    //   142: new 57	java/lang/StringBuilder
    //   145: dup
    //   146: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   149: ldc 92
    //   151: invokevirtual 78	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   154: aload 20
    //   156: invokevirtual 93	java/io/IOException:toString	()Ljava/lang/String;
    //   159: invokevirtual 78	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   162: invokevirtual 89	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   165: invokestatic 99	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   168: pop
    //   169: goto -47 -> 122
    //   172: astore 18
    //   174: ldc 17
    //   176: new 57	java/lang/StringBuilder
    //   179: dup
    //   180: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   183: ldc 101
    //   185: invokevirtual 78	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   188: aload 18
    //   190: invokevirtual 93	java/io/IOException:toString	()Ljava/lang/String;
    //   193: invokevirtual 78	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   196: invokevirtual 89	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   199: invokestatic 99	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   202: pop
    //   203: goto -71 -> 132
    //   206: astore 11
    //   208: ldc 17
    //   210: new 57	java/lang/StringBuilder
    //   213: dup
    //   214: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   217: ldc 92
    //   219: invokevirtual 78	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   222: aload 11
    //   224: invokevirtual 93	java/io/IOException:toString	()Ljava/lang/String;
    //   227: invokevirtual 78	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   230: invokevirtual 89	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   233: invokestatic 99	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   236: pop
    //   237: goto -136 -> 101
    //   240: astore 9
    //   242: ldc 17
    //   244: new 57	java/lang/StringBuilder
    //   247: dup
    //   248: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   251: ldc 101
    //   253: invokevirtual 78	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   256: aload 9
    //   258: invokevirtual 93	java/io/IOException:toString	()Ljava/lang/String;
    //   261: invokevirtual 78	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   264: invokevirtual 89	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   267: invokestatic 99	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   270: pop
    //   271: goto -160 -> 111
    //   274: astore 7
    //   276: aconst_null
    //   277: astore 8
    //   279: goto -188 -> 91
    //
    // Exception table:
    //   from	to	target	type
    //   54	61	85	finally
    //   66	82	85	finally
    //   118	122	138	java/io/IOException
    //   127	132	172	java/io/IOException
    //   96	101	206	java/io/IOException
    //   106	111	240	java/io/IOException
    //   3	51	274	finally
  }

  // ERROR //
  private static String downloadXml(Context paramContext, URL paramURL, boolean paramBoolean, String paramString1, String paramString2, String paramString3)
    throws IOException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 6
    //   3: aload_0
    //   4: aload_1
    //   5: aload_3
    //   6: aload 5
    //   8: invokestatic 104	com/xiaomi/account/openauth/utils/Network:downloadXmlAsStream	(Landroid/content/Context;Ljava/net/URL;Ljava/lang/String;Ljava/lang/String;)Ljava/io/InputStream;
    //   11: astore 6
    //   13: new 57	java/lang/StringBuilder
    //   16: dup
    //   17: sipush 1024
    //   20: invokespecial 60	java/lang/StringBuilder:<init>	(I)V
    //   23: astore 13
    //   25: new 62	java/io/BufferedReader
    //   28: dup
    //   29: new 64	java/io/InputStreamReader
    //   32: dup
    //   33: aload 6
    //   35: aload 4
    //   37: invokespecial 67	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/lang/String;)V
    //   40: sipush 1024
    //   43: invokespecial 70	java/io/BufferedReader:<init>	(Ljava/io/Reader;I)V
    //   46: astore 14
    //   48: aconst_null
    //   49: astore 6
    //   51: aload 14
    //   53: invokevirtual 74	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   56: astore 15
    //   58: aload 15
    //   60: ifnull +51 -> 111
    //   63: aload 13
    //   65: aload 15
    //   67: invokevirtual 78	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   70: pop
    //   71: aload 13
    //   73: ldc 80
    //   75: invokevirtual 78	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   78: pop
    //   79: goto -28 -> 51
    //   82: astore 7
    //   84: aload 14
    //   86: astore 8
    //   88: aload 6
    //   90: ifnull +8 -> 98
    //   93: aload 6
    //   95: invokevirtual 85	java/io/InputStream:close	()V
    //   98: aload 8
    //   100: ifnull +8 -> 108
    //   103: aload 8
    //   105: invokevirtual 86	java/io/BufferedReader:close	()V
    //   108: aload 7
    //   110: athrow
    //   111: iconst_0
    //   112: ifeq +7 -> 119
    //   115: aconst_null
    //   116: invokevirtual 85	java/io/InputStream:close	()V
    //   119: aload 14
    //   121: ifnull +8 -> 129
    //   124: aload 14
    //   126: invokevirtual 86	java/io/BufferedReader:close	()V
    //   129: aload 13
    //   131: invokevirtual 89	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   134: areturn
    //   135: astore 20
    //   137: ldc 17
    //   139: new 57	java/lang/StringBuilder
    //   142: dup
    //   143: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   146: ldc 92
    //   148: invokevirtual 78	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   151: aload 20
    //   153: invokevirtual 93	java/io/IOException:toString	()Ljava/lang/String;
    //   156: invokevirtual 78	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   159: invokevirtual 89	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   162: invokestatic 99	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   165: pop
    //   166: goto -47 -> 119
    //   169: astore 18
    //   171: ldc 17
    //   173: new 57	java/lang/StringBuilder
    //   176: dup
    //   177: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   180: ldc 101
    //   182: invokevirtual 78	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   185: aload 18
    //   187: invokevirtual 93	java/io/IOException:toString	()Ljava/lang/String;
    //   190: invokevirtual 78	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   193: invokevirtual 89	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   196: invokestatic 99	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   199: pop
    //   200: goto -71 -> 129
    //   203: astore 11
    //   205: ldc 17
    //   207: new 57	java/lang/StringBuilder
    //   210: dup
    //   211: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   214: ldc 92
    //   216: invokevirtual 78	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   219: aload 11
    //   221: invokevirtual 93	java/io/IOException:toString	()Ljava/lang/String;
    //   224: invokevirtual 78	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   227: invokevirtual 89	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   230: invokestatic 99	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   233: pop
    //   234: goto -136 -> 98
    //   237: astore 9
    //   239: ldc 17
    //   241: new 57	java/lang/StringBuilder
    //   244: dup
    //   245: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   248: ldc 101
    //   250: invokevirtual 78	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   253: aload 9
    //   255: invokevirtual 93	java/io/IOException:toString	()Ljava/lang/String;
    //   258: invokevirtual 78	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   261: invokevirtual 89	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   264: invokestatic 99	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   267: pop
    //   268: goto -160 -> 108
    //   271: astore 7
    //   273: aconst_null
    //   274: astore 8
    //   276: goto -188 -> 88
    //
    // Exception table:
    //   from	to	target	type
    //   51	58	82	finally
    //   63	79	82	finally
    //   115	119	135	java/io/IOException
    //   124	129	169	java/io/IOException
    //   93	98	203	java/io/IOException
    //   103	108	237	java/io/IOException
    //   3	48	271	finally
  }

  private static InputStream downloadXmlAsStream(Context paramContext, URL paramURL, String paramString1, String paramString2)
    throws IOException
  {
    return downloadXmlAsStream(paramContext, paramURL, paramString1, paramString2, null, null);
  }

  private static InputStream downloadXmlAsStream(Context paramContext, URL paramURL, String paramString1, String paramString2, Map<String, String> paramMap, HttpHeaderInfo paramHttpHeaderInfo)
    throws IOException
  {
    if (paramContext == null)
      throw new IllegalArgumentException("context");
    if (paramURL == null)
      throw new IllegalArgumentException("url");
    HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
    HttpURLConnection.setFollowRedirects(true);
    HttpURLConnection localHttpURLConnection = getHttpUrlConnection(paramContext, paramURL);
    localHttpURLConnection.setConnectTimeout(10000);
    localHttpURLConnection.setReadTimeout(15000);
    TrustManager[] arrayOfTrustManager = new TrustManager[1];
    arrayOfTrustManager[0] = ignoreCertificationTrustManger;
    SSLContext localSSLContext = null;
    try
    {
      localSSLContext = SSLContext.getInstance("SSL");
      localSSLContext.init(null, arrayOfTrustManager, new SecureRandom());
      SSLSocketFactory localSSLSocketFactory = localSSLContext.getSocketFactory();
      ((HttpsURLConnection)localHttpURLConnection).setSSLSocketFactory(localSSLSocketFactory);
      if (!TextUtils.isEmpty(paramString1))
        localHttpURLConnection.setRequestProperty("User-Agent", paramString1);
      if (paramString2 != null)
        localHttpURLConnection.setRequestProperty("Cookie", paramString2);
      if (paramMap != null)
      {
        Iterator localIterator = paramMap.keySet().iterator();
        while (localIterator.hasNext())
        {
          String str3 = (String)localIterator.next();
          localHttpURLConnection.setRequestProperty(str3, (String)paramMap.get(str3));
        }
      }
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      while (true)
        localNoSuchAlgorithmException.printStackTrace();
    }
    catch (KeyManagementException localKeyManagementException)
    {
      while (true)
        localKeyManagementException.printStackTrace();
      if (paramHttpHeaderInfo == null)
        break label316;
    }
    int i;
    if ((paramURL.getProtocol().equals("http")) || (paramURL.getProtocol().equals("https")))
    {
      paramHttpHeaderInfo.ResponseCode = localHttpURLConnection.getResponseCode();
      if (paramHttpHeaderInfo.AllHeaders == null)
        paramHttpHeaderInfo.AllHeaders = new HashMap();
      i = 0;
    }
    while (true)
    {
      String str1 = localHttpURLConnection.getHeaderFieldKey(i);
      String str2 = localHttpURLConnection.getHeaderField(i);
      if ((str1 == null) && (str2 == null));
      try
      {
        label316: InputStream localInputStream2 = localHttpURLConnection.getInputStream();
        localInputStream1 = localInputStream2;
        return new DoneHandlerInputStream(localInputStream1);
        if ((TextUtils.isEmpty(str1)) || (TextUtils.isEmpty(str2)));
        while (true)
        {
          i++;
          break;
          paramHttpHeaderInfo.AllHeaders.put(str1, str2);
        }
      }
      catch (IOException localIOException)
      {
        while (true)
          InputStream localInputStream1 = localHttpURLConnection.getErrorStream();
      }
    }
  }

  private static String getCMWapUrl(URL paramURL)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramURL.getProtocol()).append("://").append("10.0.0.172").append(paramURL.getPath());
    if (!TextUtils.isEmpty(paramURL.getQuery()))
      localStringBuilder.append("?").append(paramURL.getQuery());
    return localStringBuilder.toString();
  }

  private static HttpURLConnection getHttpUrlConnection(Context paramContext, URL paramURL)
    throws IOException
  {
    if (isCtwap(paramContext))
      return (HttpURLConnection)paramURL.openConnection(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.0.0.200", 80)));
    if (isCmwap(paramContext))
    {
      String str = paramURL.getHost();
      HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(getCMWapUrl(paramURL)).openConnection();
      localHttpURLConnection.addRequestProperty("X-Online-Host", str);
      return localHttpURLConnection;
    }
    return (HttpURLConnection)paramURL.openConnection();
  }

  private static boolean isCmwap(Context paramContext)
  {
    TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
    if (localTelephonyManager == null);
    String str;
    do
    {
      NetworkInfo localNetworkInfo;
      do
      {
        ConnectivityManager localConnectivityManager;
        do
        {
          do
            return false;
          while (!"CN".equalsIgnoreCase(localTelephonyManager.getSimCountryIso()));
          localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
        }
        while (localConnectivityManager == null);
        localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
      }
      while (localNetworkInfo == null);
      str = localNetworkInfo.getExtraInfo();
    }
    while ((TextUtils.isEmpty(str)) || (str.length() < 3) || (str.contains("ctwap")));
    return str.regionMatches(true, -3 + str.length(), "wap", 0, 3);
  }

  private static boolean isCtwap(Context paramContext)
  {
    TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
    if (localTelephonyManager == null);
    String str;
    do
    {
      NetworkInfo localNetworkInfo;
      do
      {
        ConnectivityManager localConnectivityManager;
        do
        {
          do
            return false;
          while (!"CN".equalsIgnoreCase(localTelephonyManager.getSimCountryIso()));
          localConnectivityManager = (ConnectivityManager)paramContext.getSystemService("connectivity");
        }
        while (localConnectivityManager == null);
        localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
      }
      while (localNetworkInfo == null);
      str = localNetworkInfo.getExtraInfo();
    }
    while ((TextUtils.isEmpty(str)) || (str.length() < 3));
    return str.contains("ctwap");
  }

  private static final class DoneHandlerInputStream extends FilterInputStream
  {
    private boolean done;

    public DoneHandlerInputStream(InputStream paramInputStream)
    {
      super();
    }

    public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      if (!this.done)
      {
        int i = super.read(paramArrayOfByte, paramInt1, paramInt2);
        if (i != -1)
          return i;
      }
      this.done = true;
      return -1;
    }
  }

  public static class HttpHeaderInfo
  {
    public Map<String, String> AllHeaders;
    public String ContentType;
    public int ResponseCode;
    public String UserAgent;
    public String realUrl;

    public String toString()
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(this.ResponseCode);
      arrayOfObject[1] = this.AllHeaders.toString();
      return String.format("resCode = %1$d, headers = %2$s", arrayOfObject);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.xiaomi.account.openauth.utils.Network
 * JD-Core Version:    0.6.2
 */