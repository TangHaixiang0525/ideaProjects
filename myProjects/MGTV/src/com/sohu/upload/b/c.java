package com.sohu.upload.b;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Debug.MemoryInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.view.Display;
import android.view.WindowManager;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.http.conn.util.InetAddressUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class c
{
  public static Context a;
  public static String b = "0";

  public static String a(String paramString, long paramLong)
  {
    int i = (int)(paramLong % 64L);
    return com.sohu.applist.c.a.c.a(paramString, com.sohu.applist.c.a.c.b(com.sohu.applist.c.a.a.b[i]));
  }

  private static String a(JSONObject paramJSONObject)
  {
    try
    {
      if (paramJSONObject.has("LOG"))
        return "LOG";
      if (paramJSONObject.has("POSTBACK"))
        return "POSTBACK";
      return "";
    }
    catch (Exception localException)
    {
    }
    return "";
  }

  public static ArrayList<com.sohu.applist.a.c> a()
  {
    ArrayList localArrayList;
    try
    {
      ActivityManager localActivityManager = (ActivityManager)a.getSystemService("activity");
      localArrayList = new ArrayList();
      Iterator localIterator = localActivityManager.getRunningAppProcesses().iterator();
      while (localIterator.hasNext())
      {
        ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)localIterator.next();
        int i = localRunningAppProcessInfo.pid;
        int j = localRunningAppProcessInfo.uid;
        String str = localRunningAppProcessInfo.processName;
        if (str.contains(":"))
          str = str.substring(0, str.indexOf(":"));
        if (j >= 10000)
        {
          int k = localActivityManager.getProcessMemoryInfo(new int[] { i })[0].dalvikPrivateDirty;
          com.sohu.applist.a.c localc = new com.sohu.applist.a.c();
          localc.a(i);
          localc.b(j);
          localc.c(k);
          localc.a(str);
          localArrayList.add(localc);
        }
      }
    }
    catch (Exception localException)
    {
      com.sohu.upload.a.a.b("�쳣:" + localException.getMessage() + ",��ȡ����б���Ϣ");
      return null;
    }
    return localArrayList;
  }

  public static JSONObject a(com.sohu.applist.a.a parama)
  {
    if (parama == null)
      return null;
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("a", parama.b());
    }
    catch (JSONException localJSONException3)
    {
      try
      {
        localJSONObject.put("b", parama.c());
      }
      catch (JSONException localJSONException3)
      {
        try
        {
          localJSONObject.put("c", parama.g());
        }
        catch (JSONException localJSONException3)
        {
          try
          {
            while (true)
            {
              localJSONObject.put("d", parama.d());
              com.sohu.upload.a.a.b("��վ�ַ�" + localJSONObject.toString());
              return localJSONObject;
              localJSONException1 = localJSONException1;
              localJSONException1.printStackTrace();
              continue;
              localJSONException2 = localJSONException2;
              localJSONException2.printStackTrace();
              continue;
              localJSONException3 = localJSONException3;
              localJSONException3.printStackTrace();
            }
          }
          catch (JSONException localJSONException4)
          {
            while (true)
              localJSONException4.printStackTrace();
          }
        }
      }
    }
  }

  // ERROR //
  public static JSONObject a(ArrayList<com.sohu.applist.a.c> paramArrayList, ArrayList<com.sohu.applist.a.a> paramArrayList1)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull +5 -> 6
    //   4: aconst_null
    //   5: areturn
    //   6: new 193	org/json/JSONArray
    //   9: dup
    //   10: invokespecial 194	org/json/JSONArray:<init>	()V
    //   13: astore_2
    //   14: iconst_0
    //   15: istore_3
    //   16: iload_3
    //   17: aload_0
    //   18: invokevirtual 198	java/util/ArrayList:size	()I
    //   21: if_icmpge +83 -> 104
    //   24: new 37	org/json/JSONObject
    //   27: dup
    //   28: invokespecial 164	org/json/JSONObject:<init>	()V
    //   31: astore 4
    //   33: aload 4
    //   35: ldc 200
    //   37: aload_0
    //   38: iload_3
    //   39: invokevirtual 204	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   42: checkcast 122	com/sohu/applist/a/c
    //   45: invokevirtual 205	com/sohu/applist/a/c:b	()Ljava/lang/String;
    //   48: invokevirtual 173	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   51: pop
    //   52: aload 4
    //   54: ldc 207
    //   56: aload_0
    //   57: iload_3
    //   58: invokevirtual 204	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   61: checkcast 122	com/sohu/applist/a/c
    //   64: invokevirtual 209	com/sohu/applist/a/c:a	()I
    //   67: invokevirtual 212	org/json/JSONObject:put	(Ljava/lang/String;I)Lorg/json/JSONObject;
    //   70: pop
    //   71: aload_2
    //   72: aload 4
    //   74: invokevirtual 215	org/json/JSONArray:put	(Ljava/lang/Object;)Lorg/json/JSONArray;
    //   77: pop
    //   78: iinc 3 1
    //   81: goto -65 -> 16
    //   84: astore 5
    //   86: aload 5
    //   88: invokevirtual 190	org/json/JSONException:printStackTrace	()V
    //   91: goto -39 -> 52
    //   94: astore 6
    //   96: aload 6
    //   98: invokevirtual 190	org/json/JSONException:printStackTrace	()V
    //   101: goto -30 -> 71
    //   104: new 193	org/json/JSONArray
    //   107: dup
    //   108: invokespecial 194	org/json/JSONArray:<init>	()V
    //   111: astore 10
    //   113: iconst_0
    //   114: istore 11
    //   116: aload_1
    //   117: ifnull +347 -> 464
    //   120: iload 11
    //   122: aload_1
    //   123: invokevirtual 198	java/util/ArrayList:size	()I
    //   126: if_icmpge +338 -> 464
    //   129: new 37	org/json/JSONObject
    //   132: dup
    //   133: invokespecial 164	org/json/JSONObject:<init>	()V
    //   136: astore 38
    //   138: aload_1
    //   139: iload 11
    //   141: invokevirtual 204	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   144: checkcast 167	com/sohu/applist/a/a
    //   147: invokevirtual 169	com/sohu/applist/a/a:b	()Ljava/lang/String;
    //   150: invokestatic 217	com/sohu/upload/b/c:a	(Ljava/lang/String;)Z
    //   153: ifeq +226 -> 379
    //   156: ldc 219
    //   158: aload_1
    //   159: iload 11
    //   161: invokevirtual 204	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   164: checkcast 167	com/sohu/applist/a/a
    //   167: invokevirtual 169	com/sohu/applist/a/a:b	()Ljava/lang/String;
    //   170: invokevirtual 222	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   173: ifeq +183 -> 356
    //   176: aload 38
    //   178: ldc 224
    //   180: ldc 45
    //   182: invokevirtual 173	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   185: pop
    //   186: aload_1
    //   187: iload 11
    //   189: invokevirtual 204	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   192: checkcast 167	com/sohu/applist/a/a
    //   195: invokevirtual 176	com/sohu/applist/a/a:c	()Ljava/lang/String;
    //   198: invokestatic 217	com/sohu/upload/b/c:a	(Ljava/lang/String;)Z
    //   201: ifeq +214 -> 415
    //   204: ldc 219
    //   206: aload_1
    //   207: iload 11
    //   209: invokevirtual 204	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   212: checkcast 167	com/sohu/applist/a/a
    //   215: invokevirtual 176	com/sohu/applist/a/a:c	()Ljava/lang/String;
    //   218: invokevirtual 222	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   221: ifeq +171 -> 392
    //   224: aload 38
    //   226: ldc 226
    //   228: ldc 45
    //   230: invokevirtual 173	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   233: pop
    //   234: aload_1
    //   235: iload 11
    //   237: invokevirtual 204	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   240: checkcast 167	com/sohu/applist/a/a
    //   243: invokevirtual 176	com/sohu/applist/a/a:c	()Ljava/lang/String;
    //   246: invokestatic 217	com/sohu/upload/b/c:a	(Ljava/lang/String;)Z
    //   249: ifeq +202 -> 451
    //   252: ldc 219
    //   254: aload_1
    //   255: iload 11
    //   257: invokevirtual 204	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   260: checkcast 167	com/sohu/applist/a/a
    //   263: invokevirtual 176	com/sohu/applist/a/a:c	()Ljava/lang/String;
    //   266: invokevirtual 222	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   269: ifeq +159 -> 428
    //   272: aload 38
    //   274: ldc 228
    //   276: ldc 45
    //   278: invokevirtual 173	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   281: pop
    //   282: aload 38
    //   284: ldc 230
    //   286: aload_1
    //   287: iload 11
    //   289: invokevirtual 204	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   292: checkcast 167	com/sohu/applist/a/a
    //   295: invokevirtual 233	com/sohu/applist/a/a:e	()Ljava/lang/String;
    //   298: invokevirtual 173	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   301: pop
    //   302: aload 38
    //   304: ldc 235
    //   306: aload_1
    //   307: iload 11
    //   309: invokevirtual 204	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   312: checkcast 167	com/sohu/applist/a/a
    //   315: invokevirtual 238	com/sohu/applist/a/a:f	()Ljava/lang/String;
    //   318: invokevirtual 173	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   321: pop
    //   322: aload 38
    //   324: ldc 240
    //   326: aload_1
    //   327: iload 11
    //   329: invokevirtual 204	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   332: checkcast 167	com/sohu/applist/a/a
    //   335: invokevirtual 180	com/sohu/applist/a/a:g	()Ljava/lang/String;
    //   338: invokevirtual 173	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   341: pop
    //   342: aload 10
    //   344: aload 38
    //   346: invokevirtual 215	org/json/JSONArray:put	(Ljava/lang/Object;)Lorg/json/JSONArray;
    //   349: pop
    //   350: iinc 11 1
    //   353: goto -233 -> 120
    //   356: aload 38
    //   358: ldc 224
    //   360: aload_1
    //   361: iload 11
    //   363: invokevirtual 204	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   366: checkcast 167	com/sohu/applist/a/a
    //   369: invokevirtual 169	com/sohu/applist/a/a:b	()Ljava/lang/String;
    //   372: invokevirtual 173	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   375: pop
    //   376: goto -190 -> 186
    //   379: aload 38
    //   381: ldc 224
    //   383: ldc 45
    //   385: invokevirtual 173	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   388: pop
    //   389: goto -203 -> 186
    //   392: aload 38
    //   394: ldc 226
    //   396: aload_1
    //   397: iload 11
    //   399: invokevirtual 204	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   402: checkcast 167	com/sohu/applist/a/a
    //   405: invokevirtual 176	com/sohu/applist/a/a:c	()Ljava/lang/String;
    //   408: invokevirtual 173	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   411: pop
    //   412: goto -178 -> 234
    //   415: aload 38
    //   417: ldc 226
    //   419: ldc 45
    //   421: invokevirtual 173	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   424: pop
    //   425: goto -191 -> 234
    //   428: aload 38
    //   430: ldc 228
    //   432: aload_1
    //   433: iload 11
    //   435: invokevirtual 204	java/util/ArrayList:get	(I)Ljava/lang/Object;
    //   438: checkcast 167	com/sohu/applist/a/a
    //   441: invokevirtual 184	com/sohu/applist/a/a:d	()Ljava/lang/String;
    //   444: invokevirtual 173	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   447: pop
    //   448: goto -166 -> 282
    //   451: aload 38
    //   453: ldc 228
    //   455: ldc 45
    //   457: invokevirtual 173	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   460: pop
    //   461: goto -179 -> 282
    //   464: new 37	org/json/JSONObject
    //   467: dup
    //   468: invokespecial 164	org/json/JSONObject:<init>	()V
    //   471: astore 12
    //   473: aload 12
    //   475: ldc 242
    //   477: invokestatic 245	com/sohu/upload/b/c:j	()I
    //   480: invokevirtual 212	org/json/JSONObject:put	(Ljava/lang/String;I)Lorg/json/JSONObject;
    //   483: pop
    //   484: aload 12
    //   486: ldc 247
    //   488: invokestatic 250	com/sohu/upload/b/c:k	()I
    //   491: invokevirtual 212	org/json/JSONObject:put	(Ljava/lang/String;I)Lorg/json/JSONObject;
    //   494: pop
    //   495: aload 12
    //   497: ldc 252
    //   499: invokestatic 253	com/sohu/upload/b/c:c	()Ljava/lang/String;
    //   502: invokevirtual 173	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   505: pop
    //   506: aload 12
    //   508: ldc 255
    //   510: invokestatic 256	com/sohu/upload/b/c:e	()Ljava/lang/String;
    //   513: invokevirtual 173	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   516: pop
    //   517: aload 12
    //   519: ldc_w 258
    //   522: invokestatic 259	com/sohu/upload/b/c:d	()Ljava/lang/String;
    //   525: invokevirtual 173	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   528: pop
    //   529: aload 12
    //   531: ldc_w 261
    //   534: invokestatic 262	com/sohu/upload/b/c:f	()Ljava/lang/String;
    //   537: invokevirtual 173	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   540: pop
    //   541: invokestatic 265	com/sohu/upload/b/c:b	()[Ljava/lang/String;
    //   544: astore 19
    //   546: aload 19
    //   548: ifnull +151 -> 699
    //   551: aload 19
    //   553: arraylength
    //   554: iconst_2
    //   555: if_icmpne +144 -> 699
    //   558: aload 12
    //   560: ldc_w 267
    //   563: aload 19
    //   565: iconst_0
    //   566: aaload
    //   567: invokevirtual 173	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   570: pop
    //   571: aload 12
    //   573: ldc_w 269
    //   576: ldc_w 271
    //   579: invokevirtual 173	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   582: pop
    //   583: aload 12
    //   585: ldc_w 273
    //   588: invokestatic 274	com/sohu/upload/b/c:g	()Ljava/lang/String;
    //   591: invokevirtual 173	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   594: pop
    //   595: aload 12
    //   597: ldc_w 276
    //   600: aload 10
    //   602: invokevirtual 173	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   605: pop
    //   606: aload 12
    //   608: ldc_w 278
    //   611: aload_2
    //   612: invokevirtual 173	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   615: pop
    //   616: aload 12
    //   618: areturn
    //   619: astore 24
    //   621: aload 24
    //   623: invokevirtual 279	java/lang/Exception:printStackTrace	()V
    //   626: aload 12
    //   628: areturn
    //   629: astore 13
    //   631: aload 13
    //   633: invokevirtual 190	org/json/JSONException:printStackTrace	()V
    //   636: goto -152 -> 484
    //   639: astore 14
    //   641: aload 14
    //   643: invokevirtual 190	org/json/JSONException:printStackTrace	()V
    //   646: goto -151 -> 495
    //   649: astore 15
    //   651: aload 15
    //   653: invokevirtual 190	org/json/JSONException:printStackTrace	()V
    //   656: goto -150 -> 506
    //   659: astore 16
    //   661: aload 16
    //   663: invokevirtual 190	org/json/JSONException:printStackTrace	()V
    //   666: goto -149 -> 517
    //   669: astore 17
    //   671: aload 17
    //   673: invokevirtual 190	org/json/JSONException:printStackTrace	()V
    //   676: goto -147 -> 529
    //   679: astore 18
    //   681: aload 18
    //   683: invokevirtual 190	org/json/JSONException:printStackTrace	()V
    //   686: goto -145 -> 541
    //   689: astore 30
    //   691: aload 30
    //   693: invokevirtual 190	org/json/JSONException:printStackTrace	()V
    //   696: goto -125 -> 571
    //   699: aload 12
    //   701: ldc_w 267
    //   704: ldc 45
    //   706: invokevirtual 173	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   709: pop
    //   710: goto -139 -> 571
    //   713: astore 20
    //   715: aload 20
    //   717: invokevirtual 190	org/json/JSONException:printStackTrace	()V
    //   720: goto -149 -> 571
    //   723: astore 21
    //   725: aload 21
    //   727: invokevirtual 190	org/json/JSONException:printStackTrace	()V
    //   730: goto -147 -> 583
    //   733: astore 22
    //   735: aload 22
    //   737: invokevirtual 190	org/json/JSONException:printStackTrace	()V
    //   740: goto -145 -> 595
    //   743: astore 23
    //   745: aload 23
    //   747: invokevirtual 279	java/lang/Exception:printStackTrace	()V
    //   750: goto -144 -> 606
    //   753: astore 44
    //   755: goto -413 -> 342
    //   758: astore 43
    //   760: goto -438 -> 322
    //   763: astore 42
    //   765: goto -463 -> 302
    //   768: astore 39
    //   770: goto -584 -> 186
    //   773: astore 40
    //   775: goto -541 -> 234
    //   778: astore 41
    //   780: goto -498 -> 282
    //
    // Exception table:
    //   from	to	target	type
    //   33	52	84	org/json/JSONException
    //   52	71	94	org/json/JSONException
    //   606	616	619	java/lang/Exception
    //   473	484	629	org/json/JSONException
    //   484	495	639	org/json/JSONException
    //   495	506	649	org/json/JSONException
    //   506	517	659	org/json/JSONException
    //   517	529	669	org/json/JSONException
    //   529	541	679	org/json/JSONException
    //   558	571	689	org/json/JSONException
    //   699	710	713	org/json/JSONException
    //   571	583	723	org/json/JSONException
    //   583	595	733	org/json/JSONException
    //   595	606	743	java/lang/Exception
    //   322	342	753	org/json/JSONException
    //   302	322	758	org/json/JSONException
    //   282	302	763	org/json/JSONException
    //   138	186	768	org/json/JSONException
    //   356	376	768	org/json/JSONException
    //   379	389	768	org/json/JSONException
    //   186	234	773	org/json/JSONException
    //   392	412	773	org/json/JSONException
    //   415	425	773	org/json/JSONException
    //   234	282	778	org/json/JSONException
    //   428	448	778	org/json/JSONException
    //   451	461	778	org/json/JSONException
  }

  public static void a(Context paramContext)
  {
    a = paramContext;
  }

  public static boolean a(int paramInt)
  {
    try
    {
      ServerSocket localServerSocket = new ServerSocket(paramInt);
      com.sohu.upload.a.a.b("�˿ڿ��ã�" + paramInt);
      localServerSocket.close();
      return false;
    }
    catch (IOException localIOException)
    {
      com.sohu.upload.a.a.b("�˿ڱ�ռ�ã�" + paramInt);
    }
    return true;
  }

  public static boolean a(String paramString)
  {
    return (paramString != null) && (!"".equals(paramString));
  }

  public static byte[] a(InputStream paramInputStream)
  {
    if (paramInputStream != null)
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      byte[] arrayOfByte = new byte[1024];
      while (true)
      {
        int i = paramInputStream.read(arrayOfByte);
        if (i == -1)
          break;
        localByteArrayOutputStream.write(arrayOfByte, 0, i);
      }
      paramInputStream.close();
      return localByteArrayOutputStream.toByteArray();
    }
    return null;
  }

  public static Map<String, String> b(String paramString)
  {
    int i = 0;
    HashMap localHashMap = new HashMap();
    while (true)
    {
      try
      {
        if (!a(paramString))
          break label103;
        String[] arrayOfString1 = paramString.substring(1 + paramString.indexOf("?")).split("&");
        if (i < arrayOfString1.length)
        {
          String[] arrayOfString2 = arrayOfString1[i].split("=");
          if (arrayOfString2.length < 2)
            localHashMap.put(arrayOfString2[0], "");
          else
            localHashMap.put(arrayOfString2[0], arrayOfString2[1]);
        }
      }
      catch (Exception localException)
      {
        localHashMap = null;
      }
      return localHashMap;
      label103: return null;
      i++;
    }
  }

  public static String[] b()
  {
    int i = 2;
    String[] arrayOfString1 = new String[i];
    arrayOfString1[0] = "";
    arrayOfString1[1] = "";
    try
    {
      BufferedReader localBufferedReader = new BufferedReader(new FileReader("/proc/cpuinfo"), 8192);
      String[] arrayOfString2 = localBufferedReader.readLine().split("\\s+");
      while (i < arrayOfString2.length)
      {
        arrayOfString1[0] = (arrayOfString1[0] + arrayOfString2[i] + " ");
        i++;
      }
      String[] arrayOfString3 = localBufferedReader.readLine().split("\\s+");
      arrayOfString1[1] = (arrayOfString1[1] + arrayOfString3[2]);
      localBufferedReader.close();
      return arrayOfString1;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      return arrayOfString1;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return arrayOfString1;
  }

  public static String c()
  {
    try
    {
      String str = ((WifiManager)a.getSystemService("wifi")).getConnectionInfo().getMacAddress();
      return str;
    }
    catch (Exception localException)
    {
    }
    return "";
  }

  public static String c(String paramString)
  {
    try
    {
      String str = new String(Base64.encode(paramString.getBytes(), 8));
      return str;
    }
    catch (Exception localException)
    {
      com.sohu.upload.a.a.b("�������ʱ�쳣:" + localException.getMessage());
    }
    return "";
  }

  public static String d()
  {
    try
    {
      String str = ((TelephonyManager)a.getSystemService("phone")).getDeviceId();
      return str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }

  public static String d(String paramString)
  {
    try
    {
      String str = new String(Base64.decode(paramString.getBytes(), 0));
      return str;
    }
    catch (Exception localException)
    {
      com.sohu.upload.a.a.b("�������ʱ�쳣:" + localException.getMessage());
    }
    return "";
  }

  public static String e()
  {
    while (true)
    {
      try
      {
        TelephonyManager localTelephonyManager = (TelephonyManager)a.getSystemService("phone");
        if (localTelephonyManager != null)
        {
          str = localTelephonyManager.getSubscriberId();
          if (TextUtils.isEmpty(str))
            str = "";
          return str;
        }
      }
      catch (Exception localException)
      {
        return "";
      }
      String str = "";
    }
  }

  public static boolean e(String paramString)
  {
    try
    {
      ThreadGroup localThreadGroup = Thread.currentThread().getThreadGroup();
      boolean bool1 = false;
      Thread[] arrayOfThread;
      if (localThreadGroup != null)
      {
        arrayOfThread = new Thread[localThreadGroup.activeCount()];
        localThreadGroup.enumerate(arrayOfThread);
      }
      for (int i = 0; ; i++)
      {
        int j = arrayOfThread.length;
        bool1 = false;
        if (i < j)
        {
          boolean bool2 = paramString.equals(arrayOfThread[i].getName());
          if (bool2)
            bool1 = true;
        }
        else
        {
          return bool1;
        }
      }
    }
    catch (Exception localException)
    {
      com.sohu.upload.a.a.b("�жϵ�ǰ�߳��Ƿ����ʱ�����쳣");
    }
    return false;
  }

  public static String f()
  {
    return Build.MODEL;
  }

  public static String f(String paramString)
  {
    JSONObject localJSONObject1 = new JSONObject();
    while (true)
    {
      int i;
      try
      {
        JSONObject localJSONObject2 = new JSONObject(paramString);
        b.a("applist", localJSONObject2.getString("applist"));
        JSONArray localJSONArray = new JSONArray(localJSONObject2.getString("msg"));
        i = 0;
        if (i < localJSONArray.length())
        {
          JSONObject localJSONObject3 = localJSONArray.getJSONObject(i);
          String str2 = a(localJSONObject3);
          if (!"".equals(str2))
            localJSONObject1.put(str2, localJSONObject3.opt(str2));
        }
        else
        {
          String str1 = localJSONObject1.toString();
          return str1;
        }
      }
      catch (Exception localException)
      {
        return null;
      }
      i++;
    }
  }

  public static String g()
  {
    return Build.VERSION.RELEASE;
  }

  public static String h()
  {
    try
    {
      WifiInfo localWifiInfo = ((WifiManager)a.getSystemService("wifi")).getConnectionInfo();
      if (localWifiInfo.getSSID().contains("<"))
        return "";
      String str = localWifiInfo.getSSID().replace("\"", "") + "";
      return str;
    }
    catch (Exception localException)
    {
      com.sohu.upload.a.a.b("�쳣:" + localException.getMessage() + ",��ȡSSIDʧ��!");
    }
    return "";
  }

  public static String i()
  {
    try
    {
      WifiInfo localWifiInfo = ((WifiManager)a.getSystemService("wifi")).getConnectionInfo();
      if (localWifiInfo.getBSSID() == null)
        return "";
      String str = localWifiInfo.getBSSID() + "";
      return str;
    }
    catch (Exception localException)
    {
      com.sohu.upload.a.a.b("�쳣:" + localException.getMessage() + ",��ȡMac��ַʧ��!");
    }
    return "";
  }

  public static int j()
  {
    try
    {
      int i = ((WindowManager)a.getSystemService("window")).getDefaultDisplay().getWidth();
      return i;
    }
    catch (Exception localException)
    {
    }
    return 0;
  }

  public static int k()
  {
    try
    {
      int i = ((WindowManager)a.getSystemService("window")).getDefaultDisplay().getHeight();
      return i;
    }
    catch (Exception localException)
    {
    }
    return 0;
  }

  public static boolean l()
  {
    try
    {
      NetworkInfo localNetworkInfo = ((ConnectivityManager)a.getSystemService("connectivity")).getActiveNetworkInfo();
      if (localNetworkInfo != null)
        if (!localNetworkInfo.isAvailable())
        {
          boolean bool = localNetworkInfo.isConnected();
          if (!bool);
        }
        else
        {
          return true;
        }
    }
    catch (Exception localException)
    {
      com.sohu.upload.a.a.b("�ж������ʱ������쳣:" + localException.getMessage());
      return false;
    }
    return false;
  }

  public static boolean m()
  {
    try
    {
      NetworkInfo.State localState1 = ((ConnectivityManager)a.getSystemService("connectivity")).getNetworkInfo(1).getState();
      if (localState1 != null)
      {
        NetworkInfo.State localState2 = NetworkInfo.State.CONNECTED;
        if (localState2 == localState1)
          return true;
      }
      return false;
    }
    catch (Exception localException)
    {
      com.sohu.upload.a.a.b("�ж�wifi����ʱ�����쳣:" + localException.getMessage());
    }
    return false;
  }

  public static String n()
  {
    try
    {
      InetAddress localInetAddress;
      do
      {
        Enumeration localEnumeration1 = NetworkInterface.getNetworkInterfaces();
        Enumeration localEnumeration2;
        while (!localEnumeration2.hasMoreElements())
        {
          if (!localEnumeration1.hasMoreElements())
            break;
          localEnumeration2 = ((NetworkInterface)localEnumeration1.nextElement()).getInetAddresses();
        }
        localInetAddress = (InetAddress)localEnumeration2.nextElement();
      }
      while ((localInetAddress.isLoopbackAddress()) || (!InetAddressUtils.isIPv4Address(localInetAddress.getHostAddress())));
      String str = localInetAddress.getHostAddress();
      return str;
    }
    catch (SocketException localSocketException)
    {
      com.sohu.upload.a.a.b("��ȡ����ip��ַʧ��");
    }
    return "";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.upload.b.c
 * JD-Core Version:    0.6.2
 */