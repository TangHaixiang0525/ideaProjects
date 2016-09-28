package com.sohu.logger.util;

import android.content.Context;
import com.sohu.logger.log.OutputLog;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public class LoggerHttpUtils
{
  private static final int BUFFER_LEN = 1024;
  private static final int CONNECT_TRY = 1;
  private static final int CONN_SO_TIME_OUT = 8000;
  private static final int CONN_TIME_OUT = 8000;
  public static final int FAIL = -1;
  private static final int MAX_BUFFER_LEN = 1024;
  private static final int RETRIEVING_CONN_TIME_OUT = 4000;
  private static final int SOCKET_CONNECT_TIMEOUT = 8000;
  private static final int SOCKET_READ_TIMEOUT = 4000;
  public static final int SUCCESS = 0;
  private static final String TAG = "HttpUtils";
  private static HttpClient sHttpClient;

  public static int doGet(Context paramContext, String paramString, int paramInt, boolean paramBoolean)
  {
    return execute(paramContext, true, paramString, paramInt, null, paramBoolean);
  }

  public static int doPost(Context paramContext, String paramString, int paramInt, NameValuePair[] paramArrayOfNameValuePair, boolean paramBoolean)
  {
    return execute(paramContext, false, paramString, paramInt, paramArrayOfNameValuePair, paramBoolean);
  }

  // ERROR //
  public static String encodeUrl(String paramString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: new 49	java/net/URL
    //   5: dup
    //   6: aload_0
    //   7: invokespecial 52	java/net/URL:<init>	(Ljava/lang/String;)V
    //   10: astore_2
    //   11: new 54	java/net/URI
    //   14: dup
    //   15: aload_2
    //   16: invokevirtual 58	java/net/URL:getProtocol	()Ljava/lang/String;
    //   19: aload_2
    //   20: invokevirtual 61	java/net/URL:getUserInfo	()Ljava/lang/String;
    //   23: aload_2
    //   24: invokevirtual 64	java/net/URL:getHost	()Ljava/lang/String;
    //   27: aload_2
    //   28: invokevirtual 68	java/net/URL:getPort	()I
    //   31: aload_2
    //   32: invokevirtual 71	java/net/URL:getPath	()Ljava/lang/String;
    //   35: aload_2
    //   36: invokevirtual 74	java/net/URL:getQuery	()Ljava/lang/String;
    //   39: aload_2
    //   40: invokevirtual 77	java/net/URL:getRef	()Ljava/lang/String;
    //   43: invokespecial 80	java/net/URI:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   46: invokevirtual 84	java/net/URI:toURL	()Ljava/net/URL;
    //   49: astore 6
    //   51: aload 6
    //   53: astore_2
    //   54: aload_2
    //   55: ifnonnull +39 -> 94
    //   58: aload_0
    //   59: areturn
    //   60: astore_3
    //   61: aload_3
    //   62: invokevirtual 87	java/net/MalformedURLException:printStackTrace	()V
    //   65: aload_1
    //   66: astore_2
    //   67: goto -13 -> 54
    //   70: astore 4
    //   72: aconst_null
    //   73: astore_2
    //   74: aload 4
    //   76: invokevirtual 88	java/net/URISyntaxException:printStackTrace	()V
    //   79: goto -25 -> 54
    //   82: astore 5
    //   84: aconst_null
    //   85: astore_2
    //   86: aload 5
    //   88: invokevirtual 89	java/lang/Exception:printStackTrace	()V
    //   91: goto -37 -> 54
    //   94: aload_2
    //   95: invokevirtual 92	java/net/URL:toString	()Ljava/lang/String;
    //   98: areturn
    //   99: astore 5
    //   101: goto -15 -> 86
    //   104: astore 4
    //   106: goto -32 -> 74
    //   109: astore_3
    //   110: aload_2
    //   111: astore_1
    //   112: goto -51 -> 61
    //
    // Exception table:
    //   from	to	target	type
    //   2	11	60	java/net/MalformedURLException
    //   2	11	70	java/net/URISyntaxException
    //   2	11	82	java/lang/Exception
    //   11	51	99	java/lang/Exception
    //   11	51	104	java/net/URISyntaxException
    //   11	51	109	java/net/MalformedURLException
  }

  // ERROR //
  private static int execute(Context paramContext, boolean paramBoolean1, String paramString, int paramInt, NameValuePair[] paramArrayOfNameValuePair, boolean paramBoolean2)
  {
    // Byte code:
    //   0: invokestatic 106	java/lang/System:currentTimeMillis	()J
    //   3: lstore 6
    //   5: new 108	java/util/ArrayList
    //   8: dup
    //   9: invokespecial 109	java/util/ArrayList:<init>	()V
    //   12: astore 8
    //   14: iload_3
    //   15: ifle +39 -> 54
    //   18: aload 8
    //   20: new 111	org/apache/http/message/BasicNameValuePair
    //   23: dup
    //   24: ldc 113
    //   26: new 115	java/lang/StringBuilder
    //   29: dup
    //   30: invokespecial 116	java/lang/StringBuilder:<init>	()V
    //   33: iload_3
    //   34: invokevirtual 120	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   37: ldc 122
    //   39: invokevirtual 125	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   42: invokevirtual 126	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   45: invokespecial 129	org/apache/http/message/BasicNameValuePair:<init>	(Ljava/lang/String;Ljava/lang/String;)V
    //   48: invokeinterface 135 2 0
    //   53: pop
    //   54: aload 4
    //   56: ifnull +37 -> 93
    //   59: aload 4
    //   61: arraylength
    //   62: istore 34
    //   64: iconst_0
    //   65: istore 35
    //   67: iload 35
    //   69: iload 34
    //   71: if_icmpge +22 -> 93
    //   74: aload 8
    //   76: aload 4
    //   78: iload 35
    //   80: aaload
    //   81: invokeinterface 135 2 0
    //   86: pop
    //   87: iinc 35 1
    //   90: goto -23 -> 67
    //   93: aload_2
    //   94: invokestatic 137	com/sohu/logger/util/LoggerHttpUtils:encodeUrl	(Ljava/lang/String;)Ljava/lang/String;
    //   97: astore 9
    //   99: ldc 25
    //   101: new 115	java/lang/StringBuilder
    //   104: dup
    //   105: invokespecial 116	java/lang/StringBuilder:<init>	()V
    //   108: ldc 139
    //   110: invokevirtual 125	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   113: aload 9
    //   115: invokevirtual 125	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   118: invokevirtual 126	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   121: invokestatic 144	com/sohu/logger/log/OutputLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   124: aconst_null
    //   125: astore 10
    //   127: new 146	org/apache/http/client/entity/UrlEncodedFormEntity
    //   130: dup
    //   131: aload 8
    //   133: invokespecial 149	org/apache/http/client/entity/UrlEncodedFormEntity:<init>	(Ljava/util/List;)V
    //   136: astore 11
    //   138: aconst_null
    //   139: astore 10
    //   141: iload_1
    //   142: ifeq +136 -> 278
    //   145: new 151	org/apache/http/client/methods/HttpGet
    //   148: dup
    //   149: aload 9
    //   151: invokespecial 152	org/apache/http/client/methods/HttpGet:<init>	(Ljava/lang/String;)V
    //   154: astore 12
    //   156: iload 5
    //   158: ifeq +14 -> 172
    //   161: aload 12
    //   163: ldc 154
    //   165: ldc 156
    //   167: invokeinterface 161 3 0
    //   172: invokestatic 165	com/sohu/logger/util/LoggerHttpUtils:getHttpClient	()Lorg/apache/http/client/HttpClient;
    //   175: aload 12
    //   177: invokeinterface 170 2 0
    //   182: astore 27
    //   184: aload 27
    //   186: invokeinterface 176 1 0
    //   191: invokeinterface 181 1 0
    //   196: istore 28
    //   198: aload_0
    //   199: aload 27
    //   201: invokestatic 187	com/sohu/logger/util/NetUtils:extractServerDate	(Landroid/content/Context;Lorg/apache/http/HttpResponse;)V
    //   204: ldc 25
    //   206: new 115	java/lang/StringBuilder
    //   209: dup
    //   210: invokespecial 116	java/lang/StringBuilder:<init>	()V
    //   213: ldc 189
    //   215: invokevirtual 125	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   218: iload 28
    //   220: invokevirtual 120	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   223: ldc 191
    //   225: invokevirtual 125	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   228: sipush 200
    //   231: invokevirtual 120	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   234: ldc 193
    //   236: invokevirtual 125	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   239: sipush 206
    //   242: invokevirtual 120	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   245: invokevirtual 126	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   248: invokestatic 144	com/sohu/logger/log/OutputLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   251: iload 28
    //   253: sipush 200
    //   256: if_icmpeq +126 -> 382
    //   259: iload 28
    //   261: sipush 206
    //   264: if_icmpeq +118 -> 382
    //   267: iconst_0
    //   268: ifeq +7 -> 275
    //   271: aconst_null
    //   272: invokevirtual 198	java/io/InputStream:close	()V
    //   275: iload 28
    //   277: ireturn
    //   278: new 200	org/apache/http/client/methods/HttpPost
    //   281: dup
    //   282: aload 9
    //   284: invokespecial 201	org/apache/http/client/methods/HttpPost:<init>	(Ljava/lang/String;)V
    //   287: astore 12
    //   289: aload 12
    //   291: checkcast 200	org/apache/http/client/methods/HttpPost
    //   294: aload 11
    //   296: invokevirtual 205	org/apache/http/client/methods/HttpPost:setEntity	(Lorg/apache/http/HttpEntity;)V
    //   299: goto -143 -> 156
    //   302: astore 24
    //   304: aconst_null
    //   305: astore 25
    //   307: ldc 25
    //   309: aload 24
    //   311: invokevirtual 206	org/apache/http/conn/ConnectTimeoutException:toString	()Ljava/lang/String;
    //   314: invokestatic 209	com/sohu/logger/log/OutputLog:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   317: iconst_m1
    //   318: istore 16
    //   320: aload 25
    //   322: ifnull +8 -> 330
    //   325: aload 25
    //   327: invokevirtual 198	java/io/InputStream:close	()V
    //   330: ldc 25
    //   332: new 115	java/lang/StringBuilder
    //   335: dup
    //   336: invokespecial 116	java/lang/StringBuilder:<init>	()V
    //   339: ldc 211
    //   341: invokevirtual 125	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   344: invokestatic 106	java/lang/System:currentTimeMillis	()J
    //   347: lload 6
    //   349: lsub
    //   350: invokevirtual 214	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   353: ldc 216
    //   355: invokevirtual 125	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   358: invokevirtual 126	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   361: invokestatic 144	com/sohu/logger/log/OutputLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   364: iload 16
    //   366: ireturn
    //   367: astore 33
    //   369: ldc 25
    //   371: aload 33
    //   373: invokevirtual 217	java/io/IOException:toString	()Ljava/lang/String;
    //   376: invokestatic 144	com/sohu/logger/log/OutputLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   379: iload 28
    //   381: ireturn
    //   382: aload 27
    //   384: invokeinterface 221 1 0
    //   389: invokeinterface 227 1 0
    //   394: astore 29
    //   396: aload 29
    //   398: astore 25
    //   400: sipush 1024
    //   403: newarray byte
    //   405: astore 30
    //   407: aload 25
    //   409: aload 30
    //   411: iconst_0
    //   412: sipush 1024
    //   415: invokevirtual 231	java/io/InputStream:read	([BII)I
    //   418: istore 31
    //   420: iload 31
    //   422: iconst_m1
    //   423: if_icmpne -16 -> 407
    //   426: aload 25
    //   428: ifnull +299 -> 727
    //   431: aload 25
    //   433: invokevirtual 198	java/io/InputStream:close	()V
    //   436: iconst_0
    //   437: istore 16
    //   439: goto -109 -> 330
    //   442: astore 32
    //   444: ldc 25
    //   446: aload 32
    //   448: invokevirtual 217	java/io/IOException:toString	()Ljava/lang/String;
    //   451: invokestatic 144	com/sohu/logger/log/OutputLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   454: iconst_0
    //   455: istore 16
    //   457: goto -127 -> 330
    //   460: astore 26
    //   462: ldc 25
    //   464: aload 26
    //   466: invokevirtual 217	java/io/IOException:toString	()Ljava/lang/String;
    //   469: invokestatic 144	com/sohu/logger/log/OutputLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   472: goto -142 -> 330
    //   475: astore 22
    //   477: ldc 25
    //   479: aload 22
    //   481: invokevirtual 232	java/io/UnsupportedEncodingException:toString	()Ljava/lang/String;
    //   484: invokestatic 209	com/sohu/logger/log/OutputLog:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   487: iconst_m1
    //   488: istore 16
    //   490: aload 10
    //   492: ifnull -162 -> 330
    //   495: aload 10
    //   497: invokevirtual 198	java/io/InputStream:close	()V
    //   500: goto -170 -> 330
    //   503: astore 23
    //   505: ldc 25
    //   507: aload 23
    //   509: invokevirtual 217	java/io/IOException:toString	()Ljava/lang/String;
    //   512: invokestatic 144	com/sohu/logger/log/OutputLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   515: goto -185 -> 330
    //   518: astore 20
    //   520: ldc 25
    //   522: aload 20
    //   524: invokevirtual 233	org/apache/http/client/ClientProtocolException:toString	()Ljava/lang/String;
    //   527: invokestatic 209	com/sohu/logger/log/OutputLog:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   530: iconst_m1
    //   531: istore 16
    //   533: aload 10
    //   535: ifnull -205 -> 330
    //   538: aload 10
    //   540: invokevirtual 198	java/io/InputStream:close	()V
    //   543: goto -213 -> 330
    //   546: astore 21
    //   548: ldc 25
    //   550: aload 21
    //   552: invokevirtual 217	java/io/IOException:toString	()Ljava/lang/String;
    //   555: invokestatic 144	com/sohu/logger/log/OutputLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   558: goto -228 -> 330
    //   561: astore 18
    //   563: ldc 25
    //   565: aload 18
    //   567: invokevirtual 217	java/io/IOException:toString	()Ljava/lang/String;
    //   570: invokestatic 209	com/sohu/logger/log/OutputLog:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   573: iconst_m1
    //   574: istore 16
    //   576: aload 10
    //   578: ifnull -248 -> 330
    //   581: aload 10
    //   583: invokevirtual 198	java/io/InputStream:close	()V
    //   586: goto -256 -> 330
    //   589: astore 19
    //   591: ldc 25
    //   593: aload 19
    //   595: invokevirtual 217	java/io/IOException:toString	()Ljava/lang/String;
    //   598: invokestatic 144	com/sohu/logger/log/OutputLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   601: goto -271 -> 330
    //   604: astore 15
    //   606: ldc 25
    //   608: aload 15
    //   610: invokevirtual 234	java/lang/Exception:toString	()Ljava/lang/String;
    //   613: invokestatic 209	com/sohu/logger/log/OutputLog:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   616: iconst_m1
    //   617: istore 16
    //   619: aload 10
    //   621: ifnull -291 -> 330
    //   624: aload 10
    //   626: invokevirtual 198	java/io/InputStream:close	()V
    //   629: goto -299 -> 330
    //   632: astore 17
    //   634: ldc 25
    //   636: aload 17
    //   638: invokevirtual 217	java/io/IOException:toString	()Ljava/lang/String;
    //   641: invokestatic 144	com/sohu/logger/log/OutputLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   644: goto -314 -> 330
    //   647: astore 13
    //   649: aload 10
    //   651: ifnull +8 -> 659
    //   654: aload 10
    //   656: invokevirtual 198	java/io/InputStream:close	()V
    //   659: aload 13
    //   661: athrow
    //   662: astore 14
    //   664: ldc 25
    //   666: aload 14
    //   668: invokevirtual 217	java/io/IOException:toString	()Ljava/lang/String;
    //   671: invokestatic 144	com/sohu/logger/log/OutputLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   674: goto -15 -> 659
    //   677: astore 13
    //   679: aload 25
    //   681: astore 10
    //   683: goto -34 -> 649
    //   686: astore 15
    //   688: aload 25
    //   690: astore 10
    //   692: goto -86 -> 606
    //   695: astore 18
    //   697: aload 25
    //   699: astore 10
    //   701: goto -138 -> 563
    //   704: astore 20
    //   706: aload 25
    //   708: astore 10
    //   710: goto -190 -> 520
    //   713: astore 22
    //   715: aload 25
    //   717: astore 10
    //   719: goto -242 -> 477
    //   722: astore 24
    //   724: goto -417 -> 307
    //   727: iconst_0
    //   728: istore 16
    //   730: goto -400 -> 330
    //
    // Exception table:
    //   from	to	target	type
    //   127	138	302	org/apache/http/conn/ConnectTimeoutException
    //   145	156	302	org/apache/http/conn/ConnectTimeoutException
    //   161	172	302	org/apache/http/conn/ConnectTimeoutException
    //   172	251	302	org/apache/http/conn/ConnectTimeoutException
    //   278	299	302	org/apache/http/conn/ConnectTimeoutException
    //   382	396	302	org/apache/http/conn/ConnectTimeoutException
    //   271	275	367	java/io/IOException
    //   431	436	442	java/io/IOException
    //   325	330	460	java/io/IOException
    //   127	138	475	java/io/UnsupportedEncodingException
    //   145	156	475	java/io/UnsupportedEncodingException
    //   161	172	475	java/io/UnsupportedEncodingException
    //   172	251	475	java/io/UnsupportedEncodingException
    //   278	299	475	java/io/UnsupportedEncodingException
    //   382	396	475	java/io/UnsupportedEncodingException
    //   495	500	503	java/io/IOException
    //   127	138	518	org/apache/http/client/ClientProtocolException
    //   145	156	518	org/apache/http/client/ClientProtocolException
    //   161	172	518	org/apache/http/client/ClientProtocolException
    //   172	251	518	org/apache/http/client/ClientProtocolException
    //   278	299	518	org/apache/http/client/ClientProtocolException
    //   382	396	518	org/apache/http/client/ClientProtocolException
    //   538	543	546	java/io/IOException
    //   127	138	561	java/io/IOException
    //   145	156	561	java/io/IOException
    //   161	172	561	java/io/IOException
    //   172	251	561	java/io/IOException
    //   278	299	561	java/io/IOException
    //   382	396	561	java/io/IOException
    //   581	586	589	java/io/IOException
    //   127	138	604	java/lang/Exception
    //   145	156	604	java/lang/Exception
    //   161	172	604	java/lang/Exception
    //   172	251	604	java/lang/Exception
    //   278	299	604	java/lang/Exception
    //   382	396	604	java/lang/Exception
    //   624	629	632	java/io/IOException
    //   127	138	647	finally
    //   145	156	647	finally
    //   161	172	647	finally
    //   172	251	647	finally
    //   278	299	647	finally
    //   382	396	647	finally
    //   477	487	647	finally
    //   520	530	647	finally
    //   563	573	647	finally
    //   606	616	647	finally
    //   654	659	662	java/io/IOException
    //   307	317	677	finally
    //   400	407	677	finally
    //   407	420	677	finally
    //   400	407	686	java/lang/Exception
    //   407	420	686	java/lang/Exception
    //   400	407	695	java/io/IOException
    //   407	420	695	java/io/IOException
    //   400	407	704	org/apache/http/client/ClientProtocolException
    //   407	420	704	org/apache/http/client/ClientProtocolException
    //   400	407	713	java/io/UnsupportedEncodingException
    //   407	420	713	java/io/UnsupportedEncodingException
    //   400	407	722	org/apache/http/conn/ConnectTimeoutException
    //   407	420	722	org/apache/http/conn/ConnectTimeoutException
  }

  private static HttpParams getDefaultHttpParams()
  {
    BasicHttpParams localBasicHttpParams = new BasicHttpParams();
    HttpProtocolParams.setVersion(localBasicHttpParams, HttpVersion.HTTP_1_1);
    HttpProtocolParams.setContentCharset(localBasicHttpParams, "UTF-8");
    HttpProtocolParams.setUseExpectContinue(localBasicHttpParams, true);
    HttpProtocolParams.setHttpElementCharset(localBasicHttpParams, "UTF-8");
    ConnManagerParams.setTimeout(localBasicHttpParams, 4000L);
    ConnManagerParams.setMaxTotalConnections(localBasicHttpParams, 2);
    HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, 8000);
    HttpConnectionParams.setSoTimeout(localBasicHttpParams, 8000);
    return localBasicHttpParams;
  }

  private static HttpClient getHttpClient()
  {
    try
    {
      if (sHttpClient == null)
      {
        HttpParams localHttpParams = getDefaultHttpParams();
        sHttpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(localHttpParams, getSchemeRegistry()), localHttpParams);
      }
      HttpClient localHttpClient = sHttpClient;
      return localHttpClient;
    }
    finally
    {
    }
  }

  // ERROR //
  public static int getInternetResource(String paramString)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_1
    //   2: aload_0
    //   3: invokestatic 137	com/sohu/logger/util/LoggerHttpUtils:encodeUrl	(Ljava/lang/String;)Ljava/lang/String;
    //   6: ldc2_w 307
    //   9: ldc2_w 307
    //   12: invokestatic 312	com/sohu/logger/util/LoggerHttpUtils:tryConnection	(Ljava/lang/String;JJ)Ljava/net/HttpURLConnection;
    //   15: astore_2
    //   16: aload_2
    //   17: ifnonnull +5 -> 22
    //   20: iconst_m1
    //   21: ireturn
    //   22: aload_2
    //   23: invokevirtual 317	java/net/HttpURLConnection:getResponseCode	()I
    //   26: istore 4
    //   28: ldc 25
    //   30: new 115	java/lang/StringBuilder
    //   33: dup
    //   34: invokespecial 116	java/lang/StringBuilder:<init>	()V
    //   37: ldc_w 319
    //   40: invokevirtual 125	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   43: iload 4
    //   45: invokevirtual 120	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   48: invokevirtual 126	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   51: invokestatic 144	com/sohu/logger/log/OutputLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   54: iload 4
    //   56: sipush 200
    //   59: if_icmpeq +237 -> 296
    //   62: iload 4
    //   64: sipush 206
    //   67: if_icmpne +235 -> 302
    //   70: goto +226 -> 296
    //   73: iload 5
    //   75: ifne +233 -> 308
    //   78: ldc 25
    //   80: new 115	java/lang/StringBuilder
    //   83: dup
    //   84: invokespecial 116	java/lang/StringBuilder:<init>	()V
    //   87: ldc_w 321
    //   90: invokevirtual 125	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   93: iload 4
    //   95: invokevirtual 120	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   98: invokevirtual 126	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   101: invokestatic 144	com/sohu/logger/log/OutputLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   104: aload_2
    //   105: invokevirtual 324	java/net/HttpURLConnection:disconnect	()V
    //   108: iload 4
    //   110: sipush 404
    //   113: if_icmpeq -93 -> 20
    //   116: iload 4
    //   118: sipush 304
    //   121: if_icmpne -101 -> 20
    //   124: iconst_0
    //   125: ireturn
    //   126: new 326	java/io/BufferedInputStream
    //   129: dup
    //   130: aload_2
    //   131: invokevirtual 329	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   134: invokespecial 332	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   137: astore 6
    //   139: aload_2
    //   140: invokevirtual 335	java/net/HttpURLConnection:getContentLength	()I
    //   143: istore 7
    //   145: iload 7
    //   147: iconst_m1
    //   148: if_icmpne +20 -> 168
    //   151: aload_2
    //   152: invokestatic 339	com/sohu/logger/util/LoggerHttpUtils:isChunked	(Ljava/net/HttpURLConnection;)Z
    //   155: ifeq +13 -> 168
    //   158: aload_2
    //   159: ldc_w 341
    //   162: iconst_m1
    //   163: invokevirtual 345	java/net/HttpURLConnection:getHeaderFieldInt	(Ljava/lang/String;I)I
    //   166: istore 7
    //   168: iload 7
    //   170: ifle +8 -> 178
    //   173: iload 7
    //   175: iconst_0
    //   176: iadd
    //   177: pop
    //   178: sipush 1024
    //   181: newarray byte
    //   183: astore 8
    //   185: iconst_0
    //   186: istore 9
    //   188: aload 6
    //   190: aload 8
    //   192: iconst_0
    //   193: sipush 1024
    //   196: invokevirtual 346	java/io/BufferedInputStream:read	([BII)I
    //   199: istore 14
    //   201: iload 14
    //   203: iconst_m1
    //   204: if_icmpeq +26 -> 230
    //   207: iload 9
    //   209: iload 14
    //   211: iadd
    //   212: istore 9
    //   214: iload 14
    //   216: ifne -28 -> 188
    //   219: ldc2_w 347
    //   222: invokestatic 354	java/lang/Thread:sleep	(J)V
    //   225: goto -37 -> 188
    //   228: astore 16
    //   230: aload 6
    //   232: invokevirtual 355	java/io/BufferedInputStream:close	()V
    //   235: aload_2
    //   236: invokevirtual 324	java/net/HttpURLConnection:disconnect	()V
    //   239: iload_1
    //   240: ireturn
    //   241: astore_3
    //   242: ldc 25
    //   244: aload_3
    //   245: invokevirtual 217	java/io/IOException:toString	()Ljava/lang/String;
    //   248: invokestatic 144	com/sohu/logger/log/OutputLog:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   251: aload_2
    //   252: invokevirtual 324	java/net/HttpURLConnection:disconnect	()V
    //   255: iconst_m1
    //   256: ireturn
    //   257: astore 12
    //   259: aload 6
    //   261: invokevirtual 355	java/io/BufferedInputStream:close	()V
    //   264: iconst_m1
    //   265: istore_1
    //   266: goto -31 -> 235
    //   269: astore 10
    //   271: aload 6
    //   273: invokevirtual 355	java/io/BufferedInputStream:close	()V
    //   276: aload 10
    //   278: athrow
    //   279: astore 15
    //   281: iconst_0
    //   282: istore_1
    //   283: goto -48 -> 235
    //   286: astore 13
    //   288: goto -24 -> 264
    //   291: astore 11
    //   293: goto -17 -> 276
    //   296: iconst_1
    //   297: istore 5
    //   299: goto -226 -> 73
    //   302: iconst_0
    //   303: istore 5
    //   305: goto -232 -> 73
    //   308: iload 4
    //   310: sipush 200
    //   313: if_icmpne -187 -> 126
    //   316: goto -190 -> 126
    //
    // Exception table:
    //   from	to	target	type
    //   219	225	228	java/lang/InterruptedException
    //   22	54	241	java/io/IOException
    //   78	108	241	java/io/IOException
    //   126	139	241	java/io/IOException
    //   188	201	257	java/io/IOException
    //   219	225	257	java/io/IOException
    //   188	201	269	finally
    //   219	225	269	finally
    //   230	235	279	java/io/IOException
    //   259	264	286	java/io/IOException
    //   271	276	291	java/io/IOException
  }

  private static SchemeRegistry getSchemeRegistry()
  {
    SchemeRegistry localSchemeRegistry = new SchemeRegistry();
    localSchemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
    localSchemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
    return localSchemeRegistry;
  }

  private static boolean isChunked(HttpURLConnection paramHttpURLConnection)
  {
    return "chunked".equalsIgnoreCase(paramHttpURLConnection.getHeaderField("Transfer-Encoding"));
  }

  public static HttpURLConnection openConnection(String paramString, long paramLong, InetSocketAddress paramInetSocketAddress)
  {
    if (paramString == null)
      throw new NullPointerException("parameter url is null!");
    int i = 0;
    HttpURLConnection localHttpURLConnection = null;
    while (true)
    {
      if ((i < 1) && (localHttpURLConnection == null))
        try
        {
          URL localURL = new URL(paramString);
          if (paramInetSocketAddress == null)
            localHttpURLConnection = (HttpURLConnection)localURL.openConnection();
          while (true)
          {
            if (paramLong > 0L)
              localHttpURLConnection.setRequestProperty("RANGE", "bytes=" + paramLong + "-");
            localHttpURLConnection.setConnectTimeout(8000);
            localHttpURLConnection.setReadTimeout(4000);
            break;
            localHttpURLConnection = (HttpURLConnection)localURL.openConnection(new Proxy(Proxy.Type.HTTP, paramInetSocketAddress));
            localHttpURLConnection.setDoInput(true);
          }
        }
        catch (Exception localException)
        {
          OutputLog.d("HttpUtils", localException.toString());
          localHttpURLConnection = null;
        }
      else
        return localHttpURLConnection;
      i++;
    }
  }

  public static boolean sendPost2(String paramString1, String paramString2)
  {
    int i = -1;
    OutputLog.d("SohuLoggerAgent", "send url:  " + paramString1 + "   param" + paramString2);
    try
    {
      DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
      HttpPost localHttpPost = new HttpPost(paramString1);
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(new BasicNameValuePair("send_value", paramString2));
      localHttpPost.setEntity(new UrlEncodedFormEntity(localArrayList, "utf-8"));
      localHttpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
      int j = localDefaultHttpClient.execute(localHttpPost).getStatusLine().getStatusCode();
      i = j;
      if ((i != 200) && (i != 206))
      {
        OutputLog.d("SohuLoggerAgent", "send url result :  " + i);
        boolean bool1;
        if (i != 0)
        {
          boolean bool2 = NetUtils.isRespondCodeStandForSuccess(i);
          bool1 = false;
          if (!bool2);
        }
        else
        {
          bool1 = true;
        }
        return bool1;
      }
    }
    catch (ConnectTimeoutException localConnectTimeoutException)
    {
      while (true)
        OutputLog.e("HttpUtils", localConnectTimeoutException.toString());
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      while (true)
        OutputLog.e("HttpUtils", localUnsupportedEncodingException.toString());
    }
    catch (ClientProtocolException localClientProtocolException)
    {
      while (true)
        OutputLog.e("HttpUtils", localClientProtocolException.toString());
    }
    catch (IOException localIOException)
    {
      while (true)
        OutputLog.e("HttpUtils", localIOException.toString());
    }
    catch (Exception localException)
    {
      while (true)
      {
        OutputLog.e("HttpUtils", localException.toString());
        continue;
        i = 0;
      }
    }
  }

  public static void shutdown()
  {
    if ((sHttpClient != null) && (sHttpClient.getConnectionManager() != null))
      sHttpClient.getConnectionManager().shutdown();
  }

  private static HttpURLConnection tryConnection(String paramString, long paramLong1, long paramLong2)
  {
    HttpURLConnection localHttpURLConnection = openConnection(paramString, paramLong1, null);
    if (localHttpURLConnection == null)
      return null;
    if (paramLong1 >= 0L)
    {
      if (paramLong2 > 0L)
        break label74;
      if (paramLong1 > 0L)
        localHttpURLConnection.setRequestProperty("RANGE", "bytes=" + paramLong1 + "-");
    }
    try
    {
      while (true)
      {
        localHttpURLConnection.connect();
        return localHttpURLConnection;
        label74: localHttpURLConnection.setRequestProperty("RANGE", "bytes=" + paramLong1 + "-" + (paramLong1 + paramLong2 - 1L));
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        OutputLog.d("HttpUtils", localException.toString());
        localHttpURLConnection = null;
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.logger.util.LoggerHttpUtils
 * JD-Core Version:    0.6.2
 */