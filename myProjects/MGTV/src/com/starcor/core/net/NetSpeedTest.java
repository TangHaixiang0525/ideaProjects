package com.starcor.core.net;

import com.google.gson.Gson;
import com.starcor.config.AppFuncCfg;
import com.starcor.config.DeviceInfo;
import com.starcor.config.MgtvUrl;
import com.starcor.core.domain.SpeedStruct;
import com.starcor.core.domain.SpeedTestServerInfo;
import com.starcor.core.domain.SpeedTestUrlInfo;
import com.starcor.core.logic.UserCPLLogic;
import com.starcor.core.utils.Logger;
import com.starcor.mgtv.boss.WebUrlBuilder;
import com.starcor.sccms.api.SccmsApiGetSpeedTestMissionInfoTask.ISccmsApiGetSpeedTestMissionInfoTaskListener;
import com.starcor.sccms.api.SccmsApiReportSpeedTestResultTask.ISccmsApiReportSpeedTestResultTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

public class NetSpeedTest
{
  private static final int ANALYZE_CONTROL_START = 1;
  private static final int ANALYZE_CONTROL_STOP = 2;
  private static final int ANALYZE_STATE_ABNORMAL = 4;
  private static final int ANALYZE_STATE_ABORTED = 2;
  private static final int ANALYZE_STATE_OK = 3;
  private static final int ANALYZE_STATE_ONGING = 1;
  private static final int ANALYZE_STATE_STOPPED = 5;
  private static final int DEFAULT_NET_SPEED = 800;
  private static final int HTTP_TIMEOUT_LENGTH = 10000;
  private static final int NETWORK_PATH_APP_TO_GATEWAY = 1;
  private static final int NETWORK_PATH_APP_TO_SERVER = 2;
  private static final int PING_INTERVAL_TIME = 500;
  private static final int PING_STATE_ABNORMAL = 1;
  private static final int PING_STATE_FAILED = 2;
  private static final int PING_STATE_OK = 0;
  private static final String TAG = "NetSpeedTest";
  private int ID = 0;
  private int analyzeControl = 2;
  private int currentAnalyzePath = 1;
  private int currentAnalyzeState = 5;
  private boolean isTimeOut = false;
  private ArrayList<DownloadSpeed> mgtvDownloadSpeed;
  private ArrayList<SpeedTestServerInfo> mgtvSpeedTestMissionInfo;
  private ArrayList<SpeedTestUrlInfo> mgtvSpeedTestUrlInfo;
  private int optimizeTimeLength = 0;
  private ArrayList<ScDownloadSpeed> scDownloadSpeed;
  SccmsApiGetSpeedTestMissionInfoTask.ISccmsApiGetSpeedTestMissionInfoTaskListener scGetMissionLsnr;
  private ArrayList<SpeedStruct> scSpeedTestMissionInfo;
  private NetSpeedTestListener testListener = null;

  private int calculateDownloadSpeed(long paramLong, int paramInt)
  {
    long l = paramLong * 8L;
    if (paramInt > 2);
    for (double d = paramInt - 0.2D; ; d = paramInt)
      return (int)(l / d / 1024.0D);
  }

  private int calculateNetSpeed(long paramLong, int paramInt)
  {
    int i = (int)Math.ceil(paramLong / 1440);
    long l1 = i * 1506;
    long l2 = l1 * 8L;
    if (paramInt > 2);
    for (double d = paramInt - 0.2D; ; d = paramInt)
    {
      int j = (int)(l2 / d / 1024.0D);
      Logger.i("NetSpeedTest", "calculateNetSpeed(), tsdataSize:" + paramLong + ", tsdatabits:" + 8L * paramLong + ",downloadTime:" + paramInt);
      Logger.i("NetSpeedTest", "calculateNetSpeed(), ethPacketNum:" + i + ", toltalBytes:" + l1 + ", toltalBits:" + l2);
      Logger.i("NetSpeedTest", "calculateNetSpeed(), dataTransTime:" + d + ", speed:" + j);
      return j;
    }
  }

  // ERROR //
  private int calculateSpeedByDownloadingFile(int paramInt1, String paramString1, String paramString2, int paramInt2)
  {
    // Byte code:
    //   0: ldc 33
    //   2: ldc 192
    //   4: invokestatic 169	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   7: iload 4
    //   9: sipush 1000
    //   12: imul
    //   13: i2l
    //   14: lstore 5
    //   16: new 194	java/net/URL
    //   19: dup
    //   20: aload_3
    //   21: invokespecial 197	java/net/URL:<init>	(Ljava/lang/String;)V
    //   24: astore 7
    //   26: ldc 33
    //   28: new 142	java/lang/StringBuilder
    //   31: dup
    //   32: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   35: ldc 199
    //   37: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   40: aload 7
    //   42: invokevirtual 200	java/net/URL:toString	()Ljava/lang/String;
    //   45: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   48: invokevirtual 163	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   51: invokestatic 169	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   54: ldc 202
    //   56: astore 9
    //   58: ldc 204
    //   60: invokestatic 210	java/util/regex/Pattern:compile	(Ljava/lang/String;)Ljava/util/regex/Pattern;
    //   63: aload 7
    //   65: invokevirtual 200	java/net/URL:toString	()Ljava/lang/String;
    //   68: invokevirtual 214	java/util/regex/Pattern:matcher	(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
    //   71: astore 10
    //   73: aload 10
    //   75: invokevirtual 220	java/util/regex/Matcher:find	()Z
    //   78: pop
    //   79: aload 10
    //   81: invokevirtual 223	java/util/regex/Matcher:groupCount	()I
    //   84: ifle +11 -> 95
    //   87: aload 10
    //   89: iconst_1
    //   90: invokevirtual 227	java/util/regex/Matcher:group	(I)Ljava/lang/String;
    //   93: astore 9
    //   95: new 229	java/util/Timer
    //   98: dup
    //   99: invokespecial 230	java/util/Timer:<init>	()V
    //   102: astore 12
    //   104: new 232	com/starcor/core/net/NetSpeedTest$2
    //   107: dup
    //   108: aload_0
    //   109: invokespecial 234	com/starcor/core/net/NetSpeedTest$2:<init>	(Lcom/starcor/core/net/NetSpeedTest;)V
    //   112: astore 13
    //   114: ldc 33
    //   116: new 142	java/lang/StringBuilder
    //   119: dup
    //   120: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   123: ldc 236
    //   125: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   128: aload_3
    //   129: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   132: invokevirtual 163	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   135: invokestatic 169	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   138: aload_0
    //   139: iconst_0
    //   140: putfield 69	com/starcor/core/net/NetSpeedTest:isTimeOut	Z
    //   143: lconst_0
    //   144: lstore 15
    //   146: sipush 10240
    //   149: newarray byte
    //   151: astore 22
    //   153: ldc 238
    //   155: ldc 240
    //   157: invokestatic 246	java/lang/System:setProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   160: pop
    //   161: aload 7
    //   163: invokevirtual 250	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   166: astore 24
    //   168: aload 24
    //   170: sipush 10000
    //   173: invokevirtual 256	java/net/URLConnection:setConnectTimeout	(I)V
    //   176: aload 24
    //   178: sipush 10000
    //   181: invokevirtual 259	java/net/URLConnection:setReadTimeout	(I)V
    //   184: aload 12
    //   186: aload 13
    //   188: lload 5
    //   190: invokevirtual 263	java/util/Timer:schedule	(Ljava/util/TimerTask;J)V
    //   193: aload 24
    //   195: invokevirtual 267	java/net/URLConnection:getInputStream	()Ljava/io/InputStream;
    //   198: astore 25
    //   200: invokestatic 271	java/lang/System:currentTimeMillis	()J
    //   203: lstore 26
    //   205: ldc2_w 272
    //   208: lstore 28
    //   210: aload 25
    //   212: aload 22
    //   214: iconst_0
    //   215: sipush 10240
    //   218: invokevirtual 279	java/io/InputStream:read	([BII)I
    //   221: istore 30
    //   223: iload 30
    //   225: iconst_m1
    //   226: if_icmpeq +144 -> 370
    //   229: lload 15
    //   231: iload 30
    //   233: i2l
    //   234: ladd
    //   235: lstore 15
    //   237: invokestatic 271	java/lang/System:currentTimeMillis	()J
    //   240: lstore 32
    //   242: lload 32
    //   244: lload 26
    //   246: lsub
    //   247: lstore 34
    //   249: lload 34
    //   251: lload 28
    //   253: lcmp
    //   254: ifle +101 -> 355
    //   257: lload 28
    //   259: ldc2_w 272
    //   262: ladd
    //   263: lstore 28
    //   265: ldc 33
    //   267: new 142	java/lang/StringBuilder
    //   270: dup
    //   271: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   274: ldc_w 281
    //   277: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   280: lload 15
    //   282: invokevirtual 152	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   285: invokevirtual 163	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   288: invokestatic 169	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   291: ldc 33
    //   293: new 142	java/lang/StringBuilder
    //   296: dup
    //   297: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   300: ldc_w 283
    //   303: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   306: lload 32
    //   308: invokevirtual 152	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   311: ldc_w 285
    //   314: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   317: lload 26
    //   319: invokevirtual 152	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   322: ldc_w 287
    //   325: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   328: lload 32
    //   330: lload 26
    //   332: lsub
    //   333: invokevirtual 152	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   336: ldc_w 289
    //   339: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   342: aload_0
    //   343: getfield 69	com/starcor/core/net/NetSpeedTest:isTimeOut	Z
    //   346: invokevirtual 292	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   349: invokevirtual 163	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   352: invokestatic 169	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   355: aload_0
    //   356: getfield 69	com/starcor/core/net/NetSpeedTest:isTimeOut	Z
    //   359: ifne +11 -> 370
    //   362: lload 34
    //   364: lload 5
    //   366: lcmp
    //   367: ifle +208 -> 575
    //   370: ldc 33
    //   372: ldc_w 294
    //   375: invokestatic 169	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   378: new 296	com/starcor/core/net/NetSpeedTest$3
    //   381: dup
    //   382: aload_0
    //   383: aload 25
    //   385: invokespecial 299	com/starcor/core/net/NetSpeedTest$3:<init>	(Lcom/starcor/core/net/NetSpeedTest;Ljava/io/InputStream;)V
    //   388: astore 31
    //   390: new 301	java/lang/Thread
    //   393: dup
    //   394: aload 31
    //   396: invokespecial 304	java/lang/Thread:<init>	(Ljava/lang/Runnable;)V
    //   399: invokevirtual 307	java/lang/Thread:start	()V
    //   402: aload 12
    //   404: invokevirtual 310	java/util/Timer:cancel	()V
    //   407: iload_1
    //   408: ifne +219 -> 627
    //   411: new 312	com/starcor/core/net/NetSpeedTest$DownloadSpeed
    //   414: dup
    //   415: aload_0
    //   416: invokespecial 313	com/starcor/core/net/NetSpeedTest$DownloadSpeed:<init>	(Lcom/starcor/core/net/NetSpeedTest;)V
    //   419: astore 17
    //   421: aload 17
    //   423: aload_2
    //   424: putfield 316	com/starcor/core/net/NetSpeedTest$DownloadSpeed:id	Ljava/lang/String;
    //   427: aload 17
    //   429: aload 9
    //   431: putfield 319	com/starcor/core/net/NetSpeedTest$DownloadSpeed:ip	Ljava/lang/String;
    //   434: aload 17
    //   436: aload_0
    //   437: lload 15
    //   439: iload 4
    //   441: invokespecial 321	com/starcor/core/net/NetSpeedTest:calculateDownloadSpeed	(JI)I
    //   444: putfield 324	com/starcor/core/net/NetSpeedTest$DownloadSpeed:speed	I
    //   447: aload_0
    //   448: getfield 326	com/starcor/core/net/NetSpeedTest:mgtvDownloadSpeed	Ljava/util/ArrayList;
    //   451: ifnonnull +14 -> 465
    //   454: aload_0
    //   455: new 328	java/util/ArrayList
    //   458: dup
    //   459: invokespecial 329	java/util/ArrayList:<init>	()V
    //   462: putfield 326	com/starcor/core/net/NetSpeedTest:mgtvDownloadSpeed	Ljava/util/ArrayList;
    //   465: aload_0
    //   466: getfield 326	com/starcor/core/net/NetSpeedTest:mgtvDownloadSpeed	Ljava/util/ArrayList;
    //   469: aload 17
    //   471: invokevirtual 333	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   474: pop
    //   475: ldc 33
    //   477: new 142	java/lang/StringBuilder
    //   480: dup
    //   481: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   484: ldc_w 335
    //   487: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   490: aload 17
    //   492: getfield 316	com/starcor/core/net/NetSpeedTest$DownloadSpeed:id	Ljava/lang/String;
    //   495: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   498: ldc_w 337
    //   501: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   504: aload 17
    //   506: getfield 319	com/starcor/core/net/NetSpeedTest$DownloadSpeed:ip	Ljava/lang/String;
    //   509: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   512: ldc_w 339
    //   515: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   518: aload 17
    //   520: getfield 324	com/starcor/core/net/NetSpeedTest$DownloadSpeed:speed	I
    //   523: invokevirtual 159	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   526: invokevirtual 163	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   529: invokestatic 169	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   532: ldc 33
    //   534: new 142	java/lang/StringBuilder
    //   537: dup
    //   538: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   541: ldc_w 341
    //   544: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   547: aload_0
    //   548: lload 15
    //   550: iload 4
    //   552: invokespecial 343	com/starcor/core/net/NetSpeedTest:calculateNetSpeed	(JI)I
    //   555: invokevirtual 159	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   558: invokevirtual 163	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   561: invokestatic 169	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   564: iconst_3
    //   565: ireturn
    //   566: astore 8
    //   568: aload 8
    //   570: invokevirtual 346	java/net/MalformedURLException:printStackTrace	()V
    //   573: iconst_2
    //   574: ireturn
    //   575: iconst_2
    //   576: aload_0
    //   577: getfield 67	com/starcor/core/net/NetSpeedTest:analyzeControl	I
    //   580: if_icmpne -370 -> 210
    //   583: aload 12
    //   585: invokevirtual 310	java/util/Timer:cancel	()V
    //   588: aconst_null
    //   589: astore 12
    //   591: ldc 33
    //   593: ldc_w 348
    //   596: invokestatic 169	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   599: iconst_5
    //   600: ireturn
    //   601: astore 21
    //   603: aload 21
    //   605: invokevirtual 349	java/net/SocketTimeoutException:printStackTrace	()V
    //   608: lconst_0
    //   609: lstore 15
    //   611: goto -209 -> 402
    //   614: astore 14
    //   616: aload 14
    //   618: invokevirtual 350	java/lang/Exception:printStackTrace	()V
    //   621: lconst_0
    //   622: lstore 15
    //   624: goto -222 -> 402
    //   627: new 352	com/starcor/core/net/NetSpeedTest$ScDownloadSpeed
    //   630: dup
    //   631: aload_0
    //   632: invokespecial 353	com/starcor/core/net/NetSpeedTest$ScDownloadSpeed:<init>	(Lcom/starcor/core/net/NetSpeedTest;)V
    //   635: astore 19
    //   637: aload 19
    //   639: aload_2
    //   640: putfield 354	com/starcor/core/net/NetSpeedTest$ScDownloadSpeed:id	Ljava/lang/String;
    //   643: aload 19
    //   645: aload_3
    //   646: putfield 357	com/starcor/core/net/NetSpeedTest$ScDownloadSpeed:url	Ljava/lang/String;
    //   649: aload 19
    //   651: aload_0
    //   652: lload 15
    //   654: iload 4
    //   656: invokespecial 321	com/starcor/core/net/NetSpeedTest:calculateDownloadSpeed	(JI)I
    //   659: putfield 360	com/starcor/core/net/NetSpeedTest$ScDownloadSpeed:kbsp	I
    //   662: aload_0
    //   663: getfield 362	com/starcor/core/net/NetSpeedTest:scDownloadSpeed	Ljava/util/ArrayList;
    //   666: ifnonnull +14 -> 680
    //   669: aload_0
    //   670: new 328	java/util/ArrayList
    //   673: dup
    //   674: invokespecial 329	java/util/ArrayList:<init>	()V
    //   677: putfield 362	com/starcor/core/net/NetSpeedTest:scDownloadSpeed	Ljava/util/ArrayList;
    //   680: aload_0
    //   681: getfield 362	com/starcor/core/net/NetSpeedTest:scDownloadSpeed	Ljava/util/ArrayList;
    //   684: aload 19
    //   686: invokevirtual 333	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   689: pop
    //   690: ldc 33
    //   692: new 142	java/lang/StringBuilder
    //   695: dup
    //   696: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   699: ldc_w 335
    //   702: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   705: aload 19
    //   707: getfield 354	com/starcor/core/net/NetSpeedTest$ScDownloadSpeed:id	Ljava/lang/String;
    //   710: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   713: ldc_w 364
    //   716: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   719: aload 19
    //   721: getfield 357	com/starcor/core/net/NetSpeedTest$ScDownloadSpeed:url	Ljava/lang/String;
    //   724: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   727: ldc_w 366
    //   730: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   733: aload 19
    //   735: getfield 360	com/starcor/core/net/NetSpeedTest$ScDownloadSpeed:kbsp	I
    //   738: invokevirtual 159	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   741: invokevirtual 163	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   744: invokestatic 169	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   747: ldc 33
    //   749: new 142	java/lang/StringBuilder
    //   752: dup
    //   753: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   756: ldc_w 341
    //   759: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   762: aload_0
    //   763: lload 15
    //   765: iload 4
    //   767: invokespecial 343	com/starcor/core/net/NetSpeedTest:calculateNetSpeed	(JI)I
    //   770: invokevirtual 159	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   773: invokevirtual 163	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   776: invokestatic 169	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   779: goto -215 -> 564
    //   782: astore 8
    //   784: goto -216 -> 568
    //
    // Exception table:
    //   from	to	target	type
    //   16	26	566	java/net/MalformedURLException
    //   138	143	601	java/net/SocketTimeoutException
    //   146	205	601	java/net/SocketTimeoutException
    //   210	223	601	java/net/SocketTimeoutException
    //   237	242	601	java/net/SocketTimeoutException
    //   265	355	601	java/net/SocketTimeoutException
    //   355	362	601	java/net/SocketTimeoutException
    //   370	402	601	java/net/SocketTimeoutException
    //   575	588	601	java/net/SocketTimeoutException
    //   591	599	601	java/net/SocketTimeoutException
    //   138	143	614	java/lang/Exception
    //   146	205	614	java/lang/Exception
    //   210	223	614	java/lang/Exception
    //   237	242	614	java/lang/Exception
    //   265	355	614	java/lang/Exception
    //   355	362	614	java/lang/Exception
    //   370	402	614	java/lang/Exception
    //   575	588	614	java/lang/Exception
    //   591	599	614	java/lang/Exception
    //   26	54	782	java/net/MalformedURLException
  }

  private void checkSpeedData()
  {
    if (this.mgtvDownloadSpeed == null)
      this.mgtvDownloadSpeed = new ArrayList();
    int i = 0;
    if (i < this.mgtvDownloadSpeed.size())
    {
      DownloadSpeed localDownloadSpeed2 = (DownloadSpeed)this.mgtvDownloadSpeed.get(i);
      if (this.mgtvDownloadSpeed == null);
      do
      {
        i++;
        break;
        Logger.i("NetSpeedTest", "checkSpeedData(), speed.id:" + localDownloadSpeed2.id + ",speedData.ip:" + localDownloadSpeed2.ip + ",speedData.speed:" + localDownloadSpeed2.speed);
      }
      while (localDownloadSpeed2.speed <= 0);
      return;
    }
    this.mgtvDownloadSpeed.clear();
    DownloadSpeed localDownloadSpeed1 = new DownloadSpeed();
    localDownloadSpeed1.id = "N1A1";
    localDownloadSpeed1.ip = "";
    localDownloadSpeed1.speed = 800;
    this.mgtvDownloadSpeed.add(localDownloadSpeed1);
  }

  private void getDataForMgtvSpeedTest()
  {
    getSpeedTestServerInfo();
    if ((this.mgtvSpeedTestMissionInfo == null) || (this.mgtvSpeedTestMissionInfo.isEmpty()))
    {
      Logger.i("NetSpeedTest", "getDataForMgtvSpeedTest() mgtvSpeedTestMissionInfo is null or empty");
      return;
    }
    int i = 0;
    label32: SpeedTestServerInfo localSpeedTestServerInfo;
    if (i < this.mgtvSpeedTestMissionInfo.size())
    {
      if (2 == this.analyzeControl)
      {
        Logger.i("NetSpeedTest", "getDataForMgtvSpeedTest() getSpeedTestUrlInfo AYALYZE_CONTROL_STOP");
        return;
      }
      localSpeedTestServerInfo = (SpeedTestServerInfo)this.mgtvSpeedTestMissionInfo.get(i);
      if ((localSpeedTestServerInfo != null) && (localSpeedTestServerInfo.serverType != null) && (localSpeedTestServerInfo.valId != null))
        break label96;
    }
    while (true)
    {
      i++;
      break label32;
      break;
      label96: getSpeedTestUrlInfo(localSpeedTestServerInfo.serverType, localSpeedTestServerInfo.valId);
    }
  }

  private DefaultHttpClient getHttpClient()
  {
    BasicHttpParams localBasicHttpParams = new BasicHttpParams();
    HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, 10000);
    HttpConnectionParams.setSoTimeout(localBasicHttpParams, 10000);
    SchemeRegistry localSchemeRegistry = new SchemeRegistry();
    localSchemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
    localSchemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
    return new DefaultHttpClient(new ThreadSafeClientConnManager(localBasicHttpParams, localSchemeRegistry), localBasicHttpParams);
  }

  private void getSccmsMediaServerTestMissionData()
  {
    this.scGetMissionLsnr = new SccmsApiGetSpeedTestMissionInfoTaskListener(null);
    ServerApiManager.i().APIGetSpeedTestMissionInfo(this.scGetMissionLsnr);
  }

  // ERROR //
  private void getSpeedTestServerInfo()
  {
    // Byte code:
    //   0: new 482	com/starcor/core/parser/json/GetSpeedTestServerInfoSAXParserJson
    //   3: dup
    //   4: invokespecial 483	com/starcor/core/parser/json/GetSpeedTestServerInfoSAXParserJson:<init>	()V
    //   7: astore_1
    //   8: new 485	org/apache/http/client/methods/HttpPost
    //   11: dup
    //   12: invokespecial 486	org/apache/http/client/methods/HttpPost:<init>	()V
    //   15: astore_2
    //   16: new 488	org/apache/http/entity/mime/MultipartEntity
    //   19: dup
    //   20: invokespecial 489	org/apache/http/entity/mime/MultipartEntity:<init>	()V
    //   23: astore_3
    //   24: invokestatic 495	com/starcor/core/logic/UserCPLLogic:getInstance	()Lcom/starcor/core/logic/UserCPLLogic;
    //   27: invokevirtual 498	com/starcor/core/logic/UserCPLLogic:getLastPlayMgtvFileId	()Ljava/lang/String;
    //   30: astore 5
    //   32: aload 5
    //   34: ifnull +352 -> 386
    //   37: aload 5
    //   39: invokevirtual 503	java/lang/String:length	()I
    //   42: ifne +6 -> 48
    //   45: goto +341 -> 386
    //   48: aload_3
    //   49: ldc_w 505
    //   52: new 507	org/apache/http/entity/mime/content/StringBody
    //   55: dup
    //   56: aload 5
    //   58: invokespecial 508	org/apache/http/entity/mime/content/StringBody:<init>	(Ljava/lang/String;)V
    //   61: invokevirtual 512	org/apache/http/entity/mime/MultipartEntity:addPart	(Ljava/lang/String;Lorg/apache/http/entity/mime/content/ContentBody;)V
    //   64: ldc 33
    //   66: new 142	java/lang/StringBuilder
    //   69: dup
    //   70: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   73: ldc_w 514
    //   76: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   79: aload 5
    //   81: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   84: invokevirtual 163	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   87: invokestatic 169	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   90: aload_3
    //   91: ldc_w 516
    //   94: new 507	org/apache/http/entity/mime/content/StringBody
    //   97: dup
    //   98: invokestatic 521	com/starcor/core/logic/GlobalLogic:getInstance	()Lcom/starcor/core/logic/GlobalLogic;
    //   101: invokevirtual 524	com/starcor/core/logic/GlobalLogic:getUserId	()Ljava/lang/String;
    //   104: invokespecial 508	org/apache/http/entity/mime/content/StringBody:<init>	(Ljava/lang/String;)V
    //   107: invokevirtual 512	org/apache/http/entity/mime/MultipartEntity:addPart	(Ljava/lang/String;Lorg/apache/http/entity/mime/content/ContentBody;)V
    //   110: ldc 33
    //   112: new 142	java/lang/StringBuilder
    //   115: dup
    //   116: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   119: ldc_w 526
    //   122: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   125: invokestatic 521	com/starcor/core/logic/GlobalLogic:getInstance	()Lcom/starcor/core/logic/GlobalLogic;
    //   128: invokevirtual 524	com/starcor/core/logic/GlobalLogic:getUserId	()Ljava/lang/String;
    //   131: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   134: invokevirtual 163	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   137: invokestatic 169	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   140: aload_3
    //   141: ldc_w 528
    //   144: new 507	org/apache/http/entity/mime/content/StringBody
    //   147: dup
    //   148: invokestatic 533	com/starcor/config/DeviceInfo:getMGTVVersion	()Ljava/lang/String;
    //   151: invokespecial 508	org/apache/http/entity/mime/content/StringBody:<init>	(Ljava/lang/String;)V
    //   154: invokevirtual 512	org/apache/http/entity/mime/MultipartEntity:addPart	(Ljava/lang/String;Lorg/apache/http/entity/mime/content/ContentBody;)V
    //   157: ldc 33
    //   159: new 142	java/lang/StringBuilder
    //   162: dup
    //   163: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   166: ldc_w 535
    //   169: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   172: invokestatic 533	com/starcor/config/DeviceInfo:getMGTVVersion	()Ljava/lang/String;
    //   175: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   178: invokevirtual 163	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   181: invokestatic 169	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   184: invokestatic 540	com/starcor/mgtv/boss/WebUrlBuilder:getSpeedTestServerInfoUrl	()Ljava/lang/String;
    //   187: astore 6
    //   189: ldc 33
    //   191: new 142	java/lang/StringBuilder
    //   194: dup
    //   195: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   198: ldc_w 542
    //   201: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   204: aload 6
    //   206: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   209: invokevirtual 163	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   212: invokestatic 169	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   215: aload_2
    //   216: aload 6
    //   218: invokestatic 548	java/net/URI:create	(Ljava/lang/String;)Ljava/net/URI;
    //   221: invokevirtual 552	org/apache/http/client/methods/HttpPost:setURI	(Ljava/net/URI;)V
    //   224: aload_2
    //   225: aload_3
    //   226: invokevirtual 556	org/apache/http/client/methods/HttpPost:setEntity	(Lorg/apache/http/HttpEntity;)V
    //   229: aload_0
    //   230: invokespecial 558	com/starcor/core/net/NetSpeedTest:getHttpClient	()Lorg/apache/http/impl/client/DefaultHttpClient;
    //   233: aload_2
    //   234: invokevirtual 562	org/apache/http/impl/client/DefaultHttpClient:execute	(Lorg/apache/http/client/methods/HttpUriRequest;)Lorg/apache/http/HttpResponse;
    //   237: astore 8
    //   239: aload 8
    //   241: invokeinterface 568 1 0
    //   246: invokeinterface 573 1 0
    //   251: istore 9
    //   253: iload 9
    //   255: sipush 200
    //   258: if_icmpeq +38 -> 296
    //   261: ldc 33
    //   263: new 142	java/lang/StringBuilder
    //   266: dup
    //   267: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   270: ldc_w 575
    //   273: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   276: iload 9
    //   278: invokevirtual 159	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   281: invokevirtual 163	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   284: invokestatic 169	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   287: return
    //   288: astore 4
    //   290: aload 4
    //   292: invokevirtual 576	java/io/UnsupportedEncodingException:printStackTrace	()V
    //   295: return
    //   296: aload_0
    //   297: aload_1
    //   298: aload 8
    //   300: invokeinterface 580 1 0
    //   305: invokeinterface 585 1 0
    //   310: invokevirtual 589	com/starcor/core/parser/json/GetSpeedTestServerInfoSAXParserJson:parser	(Ljava/io/InputStream;)Ljava/lang/Object;
    //   313: checkcast 328	java/util/ArrayList
    //   316: putfield 392	com/starcor/core/net/NetSpeedTest:mgtvSpeedTestMissionInfo	Ljava/util/ArrayList;
    //   319: ldc 33
    //   321: new 142	java/lang/StringBuilder
    //   324: dup
    //   325: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   328: ldc_w 591
    //   331: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   334: aload_0
    //   335: getfield 392	com/starcor/core/net/NetSpeedTest:mgtvSpeedTestMissionInfo	Ljava/util/ArrayList;
    //   338: invokevirtual 592	java/util/ArrayList:toString	()Ljava/lang/String;
    //   341: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   344: invokevirtual 163	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   347: invokestatic 169	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   350: return
    //   351: astore 7
    //   353: aload 7
    //   355: invokevirtual 350	java/lang/Exception:printStackTrace	()V
    //   358: ldc 33
    //   360: new 142	java/lang/StringBuilder
    //   363: dup
    //   364: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   367: ldc_w 594
    //   370: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   373: invokestatic 597	com/starcor/mgtv/boss/WebUrlBuilder:getSpeedUrl	()Ljava/lang/String;
    //   376: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   379: invokevirtual 163	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   382: invokestatic 600	com/starcor/core/utils/Logger:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   385: return
    //   386: ldc_w 602
    //   389: astore 5
    //   391: goto -343 -> 48
    //
    // Exception table:
    //   from	to	target	type
    //   24	32	288	java/io/UnsupportedEncodingException
    //   37	45	288	java/io/UnsupportedEncodingException
    //   48	184	288	java/io/UnsupportedEncodingException
    //   224	253	351	java/lang/Exception
    //   261	287	351	java/lang/Exception
    //   296	350	351	java/lang/Exception
  }

  // ERROR //
  private void getSpeedTestUrlInfo(String paramString1, String paramString2)
  {
    // Byte code:
    //   0: new 604	com/starcor/core/parser/json/GetSpeedTestUrlInfoSAXParserJson
    //   3: dup
    //   4: invokespecial 605	com/starcor/core/parser/json/GetSpeedTestUrlInfoSAXParserJson:<init>	()V
    //   7: astore_3
    //   8: new 485	org/apache/http/client/methods/HttpPost
    //   11: dup
    //   12: invokespecial 486	org/apache/http/client/methods/HttpPost:<init>	()V
    //   15: astore 4
    //   17: new 488	org/apache/http/entity/mime/MultipartEntity
    //   20: dup
    //   21: invokespecial 489	org/apache/http/entity/mime/MultipartEntity:<init>	()V
    //   24: astore 5
    //   26: aload 5
    //   28: ldc_w 607
    //   31: new 507	org/apache/http/entity/mime/content/StringBody
    //   34: dup
    //   35: aload_1
    //   36: invokespecial 508	org/apache/http/entity/mime/content/StringBody:<init>	(Ljava/lang/String;)V
    //   39: invokevirtual 512	org/apache/http/entity/mime/MultipartEntity:addPart	(Ljava/lang/String;Lorg/apache/http/entity/mime/content/ContentBody;)V
    //   42: ldc 33
    //   44: new 142	java/lang/StringBuilder
    //   47: dup
    //   48: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   51: ldc_w 609
    //   54: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   57: aload_1
    //   58: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   61: invokevirtual 163	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   64: invokestatic 169	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   67: aload 5
    //   69: ldc_w 611
    //   72: new 507	org/apache/http/entity/mime/content/StringBody
    //   75: dup
    //   76: aload_2
    //   77: invokespecial 508	org/apache/http/entity/mime/content/StringBody:<init>	(Ljava/lang/String;)V
    //   80: invokevirtual 512	org/apache/http/entity/mime/MultipartEntity:addPart	(Ljava/lang/String;Lorg/apache/http/entity/mime/content/ContentBody;)V
    //   83: ldc 33
    //   85: new 142	java/lang/StringBuilder
    //   88: dup
    //   89: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   92: ldc_w 613
    //   95: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   98: aload_2
    //   99: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   102: invokevirtual 163	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   105: invokestatic 169	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   108: aload 5
    //   110: ldc_w 516
    //   113: new 507	org/apache/http/entity/mime/content/StringBody
    //   116: dup
    //   117: invokestatic 521	com/starcor/core/logic/GlobalLogic:getInstance	()Lcom/starcor/core/logic/GlobalLogic;
    //   120: invokevirtual 524	com/starcor/core/logic/GlobalLogic:getUserId	()Ljava/lang/String;
    //   123: invokespecial 508	org/apache/http/entity/mime/content/StringBody:<init>	(Ljava/lang/String;)V
    //   126: invokevirtual 512	org/apache/http/entity/mime/MultipartEntity:addPart	(Ljava/lang/String;Lorg/apache/http/entity/mime/content/ContentBody;)V
    //   129: ldc 33
    //   131: new 142	java/lang/StringBuilder
    //   134: dup
    //   135: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   138: ldc_w 615
    //   141: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   144: invokestatic 521	com/starcor/core/logic/GlobalLogic:getInstance	()Lcom/starcor/core/logic/GlobalLogic;
    //   147: invokevirtual 524	com/starcor/core/logic/GlobalLogic:getUserId	()Ljava/lang/String;
    //   150: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   153: invokevirtual 163	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   156: invokestatic 169	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   159: aload 5
    //   161: ldc_w 617
    //   164: new 507	org/apache/http/entity/mime/content/StringBody
    //   167: dup
    //   168: invokestatic 620	com/starcor/config/DeviceInfo:getMac	()Ljava/lang/String;
    //   171: invokespecial 508	org/apache/http/entity/mime/content/StringBody:<init>	(Ljava/lang/String;)V
    //   174: invokevirtual 512	org/apache/http/entity/mime/MultipartEntity:addPart	(Ljava/lang/String;Lorg/apache/http/entity/mime/content/ContentBody;)V
    //   177: ldc 33
    //   179: new 142	java/lang/StringBuilder
    //   182: dup
    //   183: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   186: ldc_w 622
    //   189: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   192: invokestatic 620	com/starcor/config/DeviceInfo:getMac	()Ljava/lang/String;
    //   195: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   198: invokevirtual 163	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   201: invokestatic 169	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   204: aload 5
    //   206: ldc_w 528
    //   209: new 507	org/apache/http/entity/mime/content/StringBody
    //   212: dup
    //   213: invokestatic 533	com/starcor/config/DeviceInfo:getMGTVVersion	()Ljava/lang/String;
    //   216: invokespecial 508	org/apache/http/entity/mime/content/StringBody:<init>	(Ljava/lang/String;)V
    //   219: invokevirtual 512	org/apache/http/entity/mime/MultipartEntity:addPart	(Ljava/lang/String;Lorg/apache/http/entity/mime/content/ContentBody;)V
    //   222: ldc 33
    //   224: new 142	java/lang/StringBuilder
    //   227: dup
    //   228: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   231: ldc_w 624
    //   234: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   237: invokestatic 533	com/starcor/config/DeviceInfo:getMGTVVersion	()Ljava/lang/String;
    //   240: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   243: invokevirtual 163	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   246: invokestatic 169	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   249: ldc 33
    //   251: new 142	java/lang/StringBuilder
    //   254: dup
    //   255: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   258: ldc_w 626
    //   261: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   264: invokestatic 629	com/starcor/mgtv/boss/WebUrlBuilder:getSpeedTestUrlV2	()Ljava/lang/String;
    //   267: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   270: invokevirtual 163	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   273: invokestatic 169	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   276: aload 4
    //   278: invokestatic 629	com/starcor/mgtv/boss/WebUrlBuilder:getSpeedTestUrlV2	()Ljava/lang/String;
    //   281: invokestatic 548	java/net/URI:create	(Ljava/lang/String;)Ljava/net/URI;
    //   284: invokevirtual 552	org/apache/http/client/methods/HttpPost:setURI	(Ljava/net/URI;)V
    //   287: aload 4
    //   289: aload 5
    //   291: invokevirtual 556	org/apache/http/client/methods/HttpPost:setEntity	(Lorg/apache/http/HttpEntity;)V
    //   294: aload_0
    //   295: invokespecial 558	com/starcor/core/net/NetSpeedTest:getHttpClient	()Lorg/apache/http/impl/client/DefaultHttpClient;
    //   298: aload 4
    //   300: invokevirtual 562	org/apache/http/impl/client/DefaultHttpClient:execute	(Lorg/apache/http/client/methods/HttpUriRequest;)Lorg/apache/http/HttpResponse;
    //   303: astore 8
    //   305: aload 8
    //   307: invokeinterface 568 1 0
    //   312: invokeinterface 573 1 0
    //   317: istore 9
    //   319: iload 9
    //   321: sipush 200
    //   324: if_icmpeq +38 -> 362
    //   327: ldc 33
    //   329: new 142	java/lang/StringBuilder
    //   332: dup
    //   333: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   336: ldc_w 631
    //   339: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   342: iload 9
    //   344: invokevirtual 159	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   347: invokevirtual 163	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   350: invokestatic 169	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   353: return
    //   354: astore 6
    //   356: aload 6
    //   358: invokevirtual 576	java/io/UnsupportedEncodingException:printStackTrace	()V
    //   361: return
    //   362: aload_3
    //   363: aload 8
    //   365: invokeinterface 580 1 0
    //   370: invokeinterface 585 1 0
    //   375: invokevirtual 632	com/starcor/core/parser/json/GetSpeedTestUrlInfoSAXParserJson:parser	(Ljava/io/InputStream;)Ljava/lang/Object;
    //   378: checkcast 328	java/util/ArrayList
    //   381: astore 10
    //   383: ldc 33
    //   385: new 142	java/lang/StringBuilder
    //   388: dup
    //   389: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   392: ldc_w 634
    //   395: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   398: aload 10
    //   400: invokevirtual 592	java/util/ArrayList:toString	()Ljava/lang/String;
    //   403: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   406: invokevirtual 163	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   409: invokestatic 169	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   412: aload 10
    //   414: ifnull -61 -> 353
    //   417: aload 10
    //   419: invokevirtual 395	java/util/ArrayList:isEmpty	()Z
    //   422: ifne -69 -> 353
    //   425: aload_0
    //   426: getfield 636	com/starcor/core/net/NetSpeedTest:mgtvSpeedTestUrlInfo	Ljava/util/ArrayList;
    //   429: ifnonnull +14 -> 443
    //   432: aload_0
    //   433: new 328	java/util/ArrayList
    //   436: dup
    //   437: invokespecial 329	java/util/ArrayList:<init>	()V
    //   440: putfield 636	com/starcor/core/net/NetSpeedTest:mgtvSpeedTestUrlInfo	Ljava/util/ArrayList;
    //   443: aload_0
    //   444: getfield 636	com/starcor/core/net/NetSpeedTest:mgtvSpeedTestUrlInfo	Ljava/util/ArrayList;
    //   447: aload 10
    //   449: iconst_0
    //   450: invokevirtual 373	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   453: invokevirtual 333	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   456: pop
    //   457: aload_0
    //   458: aload_0
    //   459: getfield 71	com/starcor/core/net/NetSpeedTest:optimizeTimeLength	I
    //   462: aload 10
    //   464: iconst_0
    //   465: invokevirtual 373	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   468: checkcast 638	com/starcor/core/domain/SpeedTestUrlInfo
    //   471: getfield 641	com/starcor/core/domain/SpeedTestUrlInfo:time	I
    //   474: iadd
    //   475: putfield 71	com/starcor/core/net/NetSpeedTest:optimizeTimeLength	I
    //   478: return
    //   479: astore 7
    //   481: aload 7
    //   483: invokevirtual 350	java/lang/Exception:printStackTrace	()V
    //   486: ldc 33
    //   488: new 142	java/lang/StringBuilder
    //   491: dup
    //   492: invokespecial 143	java/lang/StringBuilder:<init>	()V
    //   495: ldc_w 643
    //   498: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   501: invokestatic 597	com/starcor/mgtv/boss/WebUrlBuilder:getSpeedUrl	()Ljava/lang/String;
    //   504: invokevirtual 149	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   507: invokevirtual 163	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   510: invokestatic 600	com/starcor/core/utils/Logger:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   513: return
    //
    // Exception table:
    //   from	to	target	type
    //   26	249	354	java/io/UnsupportedEncodingException
    //   287	319	479	java/lang/Exception
    //   327	353	479	java/lang/Exception
    //   362	412	479	java/lang/Exception
    //   417	443	479	java/lang/Exception
    //   443	478	479	java/lang/Exception
  }

  private void initData()
  {
    this.optimizeTimeLength = 0;
    if (this.mgtvDownloadSpeed != null)
      this.mgtvDownloadSpeed.clear();
    if (this.scDownloadSpeed != null)
      this.scDownloadSpeed.clear();
    if (this.mgtvSpeedTestUrlInfo != null)
      this.mgtvSpeedTestUrlInfo.clear();
    if (this.mgtvSpeedTestMissionInfo != null)
      this.mgtvSpeedTestMissionInfo.clear();
    if (this.scSpeedTestMissionInfo != null)
      this.scSpeedTestMissionInfo.clear();
  }

  private void notifyAnalyzeState(int paramInt1, int paramInt2)
  {
    if (this.testListener != null)
    {
      this.testListener.testState(this.ID, paramInt1, paramInt2, this.mgtvDownloadSpeed, this.scDownloadSpeed);
      Logger.i("NetSpeedTest", "notifyAnalyzeState(), ID:" + this.ID + ", networkPath:" + paramInt1 + ", analyzeState:" + paramInt2);
      if (this.mgtvDownloadSpeed != null)
        for (int j = 0; j < this.mgtvDownloadSpeed.size(); j++)
          Logger.i("NetSpeedTest", "notifyAnalyzeState() mgtvDownloadSpeed, ID:" + this.ID + ", id:" + ((DownloadSpeed)this.mgtvDownloadSpeed.get(j)).id + ", ip:" + ((DownloadSpeed)this.mgtvDownloadSpeed.get(j)).ip + ", speed:" + ((DownloadSpeed)this.mgtvDownloadSpeed.get(j)).speed);
      if (this.scDownloadSpeed != null)
        for (int i = 0; i < this.scDownloadSpeed.size(); i++)
          Logger.i("NetSpeedTest", "notifyAnalyzeState() scDownloadSpeed, ID:" + this.ID + ", id:" + ((ScDownloadSpeed)this.scDownloadSpeed.get(i)).id + ", kbsp:" + ((ScDownloadSpeed)this.scDownloadSpeed.get(i)).kbsp + ", url:" + ((ScDownloadSpeed)this.scDownloadSpeed.get(i)).url);
    }
  }

  private void notifyOptimizeTimeLength(int paramInt1, int paramInt2)
  {
    if (this.testListener != null)
    {
      this.testListener.optimizeTimeLength(this.ID, paramInt1, paramInt2);
      Logger.i("NetSpeedTest", "notifyOptimizeTimeLength(), ID:" + this.ID + ", serverCount:" + paramInt1 + ", timeLength:" + paramInt2);
    }
  }

  private int pingHost(String paramString, int paramInt)
  {
    try
    {
      Process localProcess = Runtime.getRuntime().exec("/system/bin/ping -c " + paramInt + " " + paramString);
      localBufferedReader = new BufferedReader(new InputStreamReader(localProcess.getInputStream()));
      int i = localProcess.waitFor();
      localPattern = Pattern.compile("\\d+\\s+packets\\s+transmitted.+(\\d+)\\s+received.+", 2);
      if (i != 0)
      {
        Logger.i("NetSpeedTest", "pingHost() PING_STATE_ABNORMAL. host ip:" + paramString + ", status:" + i);
        while (localBufferedReader.ready())
        {
          String str1 = localBufferedReader.readLine();
          Logger.i("NetSpeedTest", "pingHost() ping PING_STATE_ABNORMAL info:" + str1);
        }
      }
    }
    catch (IOException localIOException)
    {
      BufferedReader localBufferedReader;
      Pattern localPattern;
      Logger.d("NetSpeedTest", "pingHost() Exception: IOException.");
      localIOException.printStackTrace();
      Matcher localMatcher;
      do
      {
        while (!localBufferedReader.ready())
          return 1;
        String str2 = localBufferedReader.readLine();
        Logger.i("NetSpeedTest", "pingHost() ping result:" + str2);
        localMatcher = localPattern.matcher(str2);
      }
      while (!localMatcher.matches());
      String str3 = localMatcher.group(1).toLowerCase();
      Logger.i("NetSpeedTest", "pingHost() m.group(1). received:" + str3);
      if (Integer.parseInt(str3) > 0)
      {
        Logger.i("NetSpeedTest", "pingHost() success");
        return 0;
      }
      Logger.i("NetSpeedTest", "pingHost() failed");
      return 2;
    }
    catch (InterruptedException localInterruptedException)
    {
      Logger.d("NetSpeedTest", "pingHosts() Exception: InterruptedException.");
      localInterruptedException.printStackTrace();
    }
    return 1;
  }

  private void reportMgtvNetSpeed()
  {
    String str1 = "";
    if (this.mgtvDownloadSpeed == null)
    {
      Logger.i("NetSpeedTest", "reportMgtvNetSpeed(), mgtvDownloadSpeed is null");
      return;
    }
    Iterator localIterator = this.mgtvDownloadSpeed.iterator();
    while (localIterator.hasNext())
    {
      DownloadSpeed localDownloadSpeed = (DownloadSpeed)localIterator.next();
      if ((localDownloadSpeed == null) || (localDownloadSpeed.id == null) || (localDownloadSpeed.ip == null))
        Logger.i("NetSpeedTest", "reportMgtvNetSpeed(), invalid speedData");
      else
        str1 = str1 + localDownloadSpeed.id + "," + localDownloadSpeed.ip + "," + localDownloadSpeed.speed + ";";
    }
    Logger.i("NetSpeedTest", "reportMgtvNetSpeed(),speeds:" + str1);
    if (str1.length() == 0)
    {
      Logger.i("NetSpeedTest", "reportMgtvNetSpeed(), speeds.length() is 0");
      return;
    }
    String str2 = WebUrlBuilder.getSpeedReportUrlV2();
    Logger.i("NetSpeedTest", "reportMgtvNetSpeed(), reportSpeedTest url:" + str2);
    HttpPost localHttpPost = new HttpPost();
    localHttpPost.setURI(URI.create(str2));
    supportGzip(localHttpPost);
    while (true)
    {
      int i;
      try
      {
        MultipartEntity localMultipartEntity = new MultipartEntity();
        localMultipartEntity.addPart("speeds", new StringBody(str1));
        Logger.i("NetSpeedTest", "reportMgtvNetSpeed(), request entity speeds:" + str1);
        localMultipartEntity.addPart("macid", new StringBody(DeviceInfo.getMac()));
        Logger.i("NetSpeedTest", "reportMgtvNetSpeed(), request entity macid:" + DeviceInfo.getMac());
        str3 = UserCPLLogic.getInstance().getLastPlayMgtvFileId();
        if ((str3 == null) || (str3.length() == 0))
          break label514;
        localMultipartEntity.addPart("file_id", new StringBody(str3));
        Logger.i("NetSpeedTest", "reportMgtvNetSpeed(), request entity file_id:" + str3);
        localHttpPost.setEntity(localMultipartEntity);
        i = getHttpClient().execute(localHttpPost).getStatusLine().getStatusCode();
        if (i != 200)
        {
          Logger.i("NetSpeedTest", "reportMgtvNetSpeed(), failed. statusCode=" + i + " url:" + str2);
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
      Logger.i("NetSpeedTest", "reportMgtvNetSpeed(), success. statusCode=" + i + " url:" + str2);
      return;
      label514: String str3 = "0";
    }
  }

  private void reportSccmsNetSpeed()
  {
    if (this.scDownloadSpeed == null)
    {
      Logger.i("NetSpeedTest", "reportSccmsNetSpeed(), scDownloadSpeed is null");
      return;
    }
    Iterator localIterator = this.scDownloadSpeed.iterator();
    while (localIterator.hasNext())
    {
      ScDownloadSpeed localScDownloadSpeed = (ScDownloadSpeed)localIterator.next();
      if ((localScDownloadSpeed == null) || (localScDownloadSpeed.id == null) || (localScDownloadSpeed.url == null))
        Logger.i("NetSpeedTest", "reportMgtvNetSpeed(), invalid speedData");
      else
        Logger.i("NetSpeedTest", "reportSccmsNetSpeed() speedData.id:" + localScDownloadSpeed.id + ", speedData.kbps:" + localScDownloadSpeed.kbsp + ", speedData.url:" + localScDownloadSpeed.url);
    }
    String str = new Gson().toJson(this.scDownloadSpeed);
    SccmsApiReportSpeedTestResultTaskListener localSccmsApiReportSpeedTestResultTaskListener = new SccmsApiReportSpeedTestResultTaskListener(null);
    ServerApiManager.i().APIReportSpeedTestResult(str, localSccmsApiReportSpeedTestResultTaskListener);
  }

  private void startNetSpeedTestThread()
  {
    new Thread()
    {
      public void run()
      {
        NetSpeedTest.access$102(NetSpeedTest.this, 1);
        NetSpeedTest.this.notifyAnalyzeState(NetSpeedTest.this.currentAnalyzePath, 1);
        NetSpeedTest.access$302(NetSpeedTest.this, NetSpeedTest.this.testN1A1());
        if (5 == NetSpeedTest.this.currentAnalyzeState)
        {
          NetSpeedTest.this.notifyAnalyzeState(NetSpeedTest.this.currentAnalyzePath, NetSpeedTest.this.currentAnalyzeState);
          NetSpeedTest.access$502(NetSpeedTest.this, 2);
          Logger.i("NetSpeedTest", "startNetworkSpeedTest() testN1A1 stopped");
          return;
        }
        if (3 == NetSpeedTest.this.currentAnalyzeState)
        {
          NetSpeedTest.this.notifyAnalyzeState(NetSpeedTest.this.currentAnalyzePath, 3);
          NetSpeedTest.access$102(NetSpeedTest.this, 2);
          NetSpeedTest.this.notifyAnalyzeState(NetSpeedTest.this.currentAnalyzePath, 1);
          NetSpeedTest.access$302(NetSpeedTest.this, NetSpeedTest.this.testMediaServer());
          if (3 != NetSpeedTest.this.currentAnalyzeState)
            break label443;
          NetSpeedTest.this.reportMgtvNetSpeed();
          if (AppFuncCfg.FUNCTION_SPEED_TEST_FOR_PLAYBACK_AND_TIMESHIFT)
            NetSpeedTest.this.reportSccmsNetSpeed();
        }
        while (true)
        {
          NetSpeedTest.this.checkSpeedData();
          NetSpeedTest.access$302(NetSpeedTest.this, NetSpeedTest.this.testN1A1());
          if (NetSpeedTest.this.currentAnalyzeState != 3)
          {
            NetSpeedTest.access$302(NetSpeedTest.this, NetSpeedTest.this.testGateway());
            if (NetSpeedTest.this.currentAnalyzeState != 3)
              NetSpeedTest.access$102(NetSpeedTest.this, 1);
          }
          NetSpeedTest.this.notifyAnalyzeState(NetSpeedTest.this.currentAnalyzePath, NetSpeedTest.this.currentAnalyzeState);
          NetSpeedTest.access$502(NetSpeedTest.this, 2);
          return;
          NetSpeedTest.access$302(NetSpeedTest.this, NetSpeedTest.this.testGateway());
          if (5 == NetSpeedTest.this.currentAnalyzeState)
          {
            NetSpeedTest.this.notifyAnalyzeState(NetSpeedTest.this.currentAnalyzePath, NetSpeedTest.this.currentAnalyzeState);
            NetSpeedTest.access$502(NetSpeedTest.this, 2);
            Logger.i("NetSpeedTest", "startNetworkSpeedTest() testGateway stopped");
            return;
          }
          if (3 == NetSpeedTest.this.currentAnalyzeState)
          {
            NetSpeedTest.this.notifyAnalyzeState(NetSpeedTest.this.currentAnalyzePath, 3);
            NetSpeedTest.access$102(NetSpeedTest.this, 2);
            NetSpeedTest.this.notifyAnalyzeState(NetSpeedTest.this.currentAnalyzePath, 4);
            Logger.i("NetSpeedTest", "startNetworkSpeedTest() testGateway success but testN1A1 failed");
            return;
          }
          NetSpeedTest.this.notifyAnalyzeState(NetSpeedTest.this.currentAnalyzePath, NetSpeedTest.this.currentAnalyzeState);
          NetSpeedTest.access$502(NetSpeedTest.this, 2);
          Logger.i("NetSpeedTest", "startNetworkSpeedTest() testGateway failed");
          return;
          label443: Logger.i("NetSpeedTest", "startNetworkSpeedTest() testMediaServer failed");
        }
      }
    }
    .start();
  }

  private void supportGzip(HttpRequest paramHttpRequest)
  {
    paramHttpRequest.addHeader("Accept-Encoding", "gzip");
  }

  private int testGateway()
  {
    String str = NetTools.getGatewayIp();
    if ("" == str)
      return 4;
    int i = 0;
    while (i < 10)
    {
      if (2 == this.analyzeControl)
      {
        Logger.i("NetSpeedTest", "testGateway() ping AYALYZE_CONTROL_STOP");
        return 5;
      }
      if (pingHost(str, 1) == 0)
        return 3;
      try
      {
        Thread.sleep(500L);
        i++;
      }
      catch (InterruptedException localInterruptedException)
      {
        Logger.d("NetSpeedTest", "testGateway() sleep() Exception: InterruptedException.");
        localInterruptedException.printStackTrace();
      }
    }
    return 2;
  }

  private int testInternet()
  {
    if ("" == "www.baidu.com")
      return 4;
    int i = 0;
    while (i < 10)
    {
      if (2 == this.analyzeControl)
      {
        Logger.i("NetSpeedTest", "testInternet() ping AYALYZE_CONTROL_STOP");
        return 5;
      }
      if (pingHost("www.baidu.com", 1) != 2)
        return 3;
      try
      {
        Thread.sleep(500L);
        i++;
      }
      catch (InterruptedException localInterruptedException)
      {
        Logger.d("NetSpeedTest", "testInternet() sleep() Exception: InterruptedException.");
        localInterruptedException.printStackTrace();
      }
    }
    return 2;
  }

  private int testMediaServer()
  {
    getDataForMgtvSpeedTest();
    if ((this.mgtvSpeedTestUrlInfo == null) || (this.mgtvSpeedTestUrlInfo.isEmpty()))
    {
      Logger.i("NetSpeedTest", "testMediaServer() getDataForMgtvSpeedTest failed mgtvSpeedTestUrlInfo");
      return 4;
    }
    notifyOptimizeTimeLength(this.mgtvSpeedTestUrlInfo.size(), this.optimizeTimeLength);
    Iterator localIterator1 = this.mgtvSpeedTestUrlInfo.iterator();
    while (localIterator1.hasNext())
    {
      SpeedTestUrlInfo localSpeedTestUrlInfo = (SpeedTestUrlInfo)localIterator1.next();
      if (2 == this.analyzeControl)
      {
        Logger.i("NetSpeedTest", "getDataForMgtvSpeedTest() AYALYZE_CONTROL_STOP");
        return 5;
      }
      Logger.i("NetSpeedTest", "testMediaServer(), mgtvUrlInfo:" + localSpeedTestUrlInfo.toString());
      if ((localSpeedTestUrlInfo.time <= 0) || (localSpeedTestUrlInfo.url == null))
      {
        Logger.i("NetSpeedTest", "testMediaServer(), invalid mgtvUrlInfo:");
      }
      else
      {
        int j = calculateSpeedByDownloadingFile(0, localSpeedTestUrlInfo.id, localSpeedTestUrlInfo.url, localSpeedTestUrlInfo.time);
        if (5 == j)
          return j;
      }
    }
    if (!AppFuncCfg.FUNCTION_SPEED_TEST_FOR_PLAYBACK_AND_TIMESHIFT)
      return 3;
    if ((this.scSpeedTestMissionInfo == null) || (this.scSpeedTestMissionInfo.isEmpty()))
    {
      Logger.i("NetSpeedTest", "testMediaServer() getDataForMgtvSpeedTest failed mgtvSpeedTestUrlInfo");
      return 4;
    }
    Iterator localIterator2 = this.scSpeedTestMissionInfo.iterator();
    while (localIterator2.hasNext())
    {
      SpeedStruct localSpeedStruct = (SpeedStruct)localIterator2.next();
      if (2 == this.analyzeControl)
      {
        Logger.i("NetSpeedTest", "getDataForMgtvSpeedTest() AYALYZE_CONTROL_STOP");
        return 5;
      }
      Logger.i("NetSpeedTest", "testMediaServer(), urlInfo:" + localSpeedStruct.toString());
      if ((localSpeedStruct.time <= 0) || (localSpeedStruct.url == null))
      {
        Logger.i("NetSpeedTest", "testMediaServer(), invalid scUrlInfo:");
      }
      else
      {
        int i = calculateSpeedByDownloadingFile(1, localSpeedStruct.id, localSpeedStruct.url, localSpeedStruct.time);
        if (5 == i)
          return i;
      }
    }
    return 3;
  }

  private int testN1A1()
  {
    int i = 5;
    HttpGet localHttpGet = new HttpGet();
    localHttpGet.setURI(URI.create(MgtvUrl.N1_A()));
    Logger.i("NetSpeedTest", "testN1A1() url:" + MgtvUrl.N1_A());
    int j = 0;
    while (true)
      if (j < 3)
        try
        {
          if (2 == this.analyzeControl)
            return i;
          int k = getHttpClient().execute(localHttpGet).getStatusLine().getStatusCode();
          if (200 == k)
          {
            Logger.i("NetSpeedTest", "testN1A1() http request success. statusCode:" + k + ", times:" + j);
            return 3;
          }
          Logger.i("NetSpeedTest", "testN1A1() http request failed. statusCode:" + k + ", times:" + j);
          j++;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          Logger.e("NetSpeedTest", "testN1A1() Exception");
        }
    if (2 != this.analyzeControl)
      i = 4;
    return i;
  }

  public void setID(int paramInt)
  {
    this.ID = paramInt;
  }

  public void setNetSpeedTestListener(NetSpeedTestListener paramNetSpeedTestListener)
  {
    this.testListener = paramNetSpeedTestListener;
  }

  public void startNetworkOptimize()
  {
    startNetworkSpeedTest();
  }

  public void startNetworkSpeedTest()
  {
    Logger.i("NetSpeedTest", "startNetworkSpeedTest analyzeControl=" + this.analyzeControl);
    if (this.analyzeControl != 2)
      return;
    this.analyzeControl = 1;
    initData();
    if (AppFuncCfg.FUNCTION_SPEED_TEST_FOR_PLAYBACK_AND_TIMESHIFT)
    {
      getSccmsMediaServerTestMissionData();
      return;
    }
    startNetSpeedTestThread();
  }

  public void stopNetworkSpeedTest()
  {
    if (2 == this.analyzeControl)
      return;
    this.analyzeControl = 2;
    notifyAnalyzeState(this.currentAnalyzePath, 5);
    this.ID = 0;
    this.scGetMissionLsnr = null;
  }

  public class DownloadSpeed
  {
    public String id;
    public String ip;
    public int speed;

    public DownloadSpeed()
    {
    }
  }

  public static abstract interface NetSpeedTestListener
  {
    public abstract void optimizeTimeLength(int paramInt1, int paramInt2, int paramInt3);

    public abstract void testState(int paramInt1, int paramInt2, int paramInt3, ArrayList<NetSpeedTest.DownloadSpeed> paramArrayList, ArrayList<NetSpeedTest.ScDownloadSpeed> paramArrayList1);
  }

  public class ScDownloadSpeed
  {
    public String id;
    public int kbsp;
    public String url;

    public ScDownloadSpeed()
    {
    }
  }

  private final class SccmsApiGetSpeedTestMissionInfoTaskListener
    implements SccmsApiGetSpeedTestMissionInfoTask.ISccmsApiGetSpeedTestMissionInfoTaskListener
  {
    private SccmsApiGetSpeedTestMissionInfoTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("NetSpeedTest", "SccmsApiGetSpeedTestMissionInfoTaskListener.onError()");
      NetSpeedTest.access$1302(NetSpeedTest.this, null);
      NetSpeedTest.this.startNetSpeedTestThread();
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<SpeedStruct> paramArrayList)
    {
      NetSpeedTest.access$1302(NetSpeedTest.this, paramArrayList);
      if (NetSpeedTest.this.scSpeedTestMissionInfo != null)
      {
        Iterator localIterator = NetSpeedTest.this.scSpeedTestMissionInfo.iterator();
        while (localIterator.hasNext())
        {
          SpeedStruct localSpeedStruct = (SpeedStruct)localIterator.next();
          if ((localSpeedStruct != null) && (localSpeedStruct.time > 0))
            NetSpeedTest.access$1512(NetSpeedTest.this, localSpeedStruct.time);
        }
      }
      Logger.i("NetSpeedTest", "SccmsApiGetSpeedTestMissionInfoTaskListener.onSuccess() scSpeedTestMissionInfo:" + NetSpeedTest.this.scSpeedTestMissionInfo.toString());
      NetSpeedTest.this.startNetSpeedTestThread();
    }
  }

  private final class SccmsApiReportSpeedTestResultTaskListener
    implements SccmsApiReportSpeedTestResultTask.ISccmsApiReportSpeedTestResultTaskListener
  {
    private SccmsApiReportSpeedTestResultTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("NetSpeedTest", "SccmsApiReportSpeedTestResultTaskListener.onError()");
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo)
    {
      Logger.i("NetSpeedTest", "SccmsApiReportSpeedTestResultTaskListener.onSuccess()");
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.net.NetSpeedTest
 * JD-Core Version:    0.6.2
 */