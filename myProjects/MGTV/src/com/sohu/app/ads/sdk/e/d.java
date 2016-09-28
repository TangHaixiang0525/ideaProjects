package com.sohu.app.ads.sdk.e;

import android.text.TextUtils;
import com.sohu.app.ads.sdk.c.a;
import com.sohu.app.ads.sdk.model.emu.DownloadEmue;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class d extends Thread
{
  private static String d = "";
  private static boolean e;
  private static File f;
  private final String a = "status";
  private final String b = "length";
  private e c = null;
  private ArrayList<com.sohu.app.ads.sdk.model.e> g = null;

  public d(File paramFile)
  {
    f = paramFile;
  }

  // ERROR //
  public Map<String, java.lang.Object> a(String paramString1, File paramFile, String paramString2)
  {
    // Byte code:
    //   0: new 48	java/util/HashMap
    //   3: dup
    //   4: invokespecial 49	java/util/HashMap:<init>	()V
    //   7: astore 4
    //   9: aconst_null
    //   10: astore 5
    //   12: aconst_null
    //   13: astore 6
    //   15: new 51	java/lang/StringBuilder
    //   18: dup
    //   19: invokespecial 52	java/lang/StringBuilder:<init>	()V
    //   22: ldc 54
    //   24: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   27: aload_1
    //   28: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   31: invokevirtual 62	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   34: invokestatic 67	com/sohu/app/ads/sdk/c/a:d	(Ljava/lang/String;)V
    //   37: new 69	java/net/URL
    //   40: dup
    //   41: aload_1
    //   42: invokespecial 71	java/net/URL:<init>	(Ljava/lang/String;)V
    //   45: invokevirtual 75	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   48: checkcast 77	java/net/HttpURLConnection
    //   51: astore 13
    //   53: aload 13
    //   55: iconst_0
    //   56: invokevirtual 81	java/net/HttpURLConnection:setInstanceFollowRedirects	(Z)V
    //   59: aload 13
    //   61: sipush 5000
    //   64: invokevirtual 85	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   67: aload 13
    //   69: ldc 87
    //   71: invokevirtual 90	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   74: aload 13
    //   76: ldc 92
    //   78: ldc 94
    //   80: invokevirtual 98	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   83: aload 13
    //   85: ldc 100
    //   87: ldc 102
    //   89: invokevirtual 98	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   92: aload 13
    //   94: ldc 104
    //   96: ldc 106
    //   98: invokevirtual 98	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   101: aload 13
    //   103: ldc 108
    //   105: ldc 110
    //   107: invokevirtual 98	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   110: aload 13
    //   112: ldc 112
    //   114: ldc 114
    //   116: invokevirtual 98	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   119: aload 13
    //   121: invokevirtual 118	java/net/HttpURLConnection:getContentLength	()I
    //   124: istore 14
    //   126: aload 13
    //   128: invokevirtual 121	java/net/HttpURLConnection:getResponseCode	()I
    //   131: istore 15
    //   133: new 51	java/lang/StringBuilder
    //   136: dup
    //   137: invokespecial 52	java/lang/StringBuilder:<init>	()V
    //   140: ldc 123
    //   142: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   145: iload 14
    //   147: invokevirtual 126	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   150: invokevirtual 62	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   153: invokestatic 67	com/sohu/app/ads/sdk/c/a:d	(Ljava/lang/String;)V
    //   156: new 51	java/lang/StringBuilder
    //   159: dup
    //   160: invokespecial 52	java/lang/StringBuilder:<init>	()V
    //   163: ldc 128
    //   165: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   168: iload 15
    //   170: invokevirtual 126	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   173: invokevirtual 62	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   176: invokestatic 67	com/sohu/app/ads/sdk/c/a:d	(Ljava/lang/String;)V
    //   179: aconst_null
    //   180: astore 6
    //   182: aconst_null
    //   183: astore 5
    //   185: iload 15
    //   187: sipush 400
    //   190: if_icmple +606 -> 796
    //   193: invokestatic 133	com/sohu/mobile/a/a/e:c	()Lcom/sohu/mobile/a/a/d;
    //   196: ldc 135
    //   198: aload_1
    //   199: invokestatic 141	java/net/URLEncoder:encode	(Ljava/lang/String;)Ljava/lang/String;
    //   202: invokeinterface 145 3 0
    //   207: invokestatic 150	com/sohu/mobile/a/a/b:a	()Lcom/sohu/mobile/a/a/c;
    //   210: ldc 152
    //   212: invokeinterface 156 2 0
    //   217: goto +579 -> 796
    //   220: aload 13
    //   222: ldc 158
    //   224: invokevirtual 161	java/net/HttpURLConnection:getHeaderField	(Ljava/lang/String;)Ljava/lang/String;
    //   227: astore 16
    //   229: new 51	java/lang/StringBuilder
    //   232: dup
    //   233: invokespecial 52	java/lang/StringBuilder:<init>	()V
    //   236: ldc 163
    //   238: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   241: aload 16
    //   243: invokevirtual 58	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   246: invokevirtual 62	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   249: invokestatic 67	com/sohu/app/ads/sdk/c/a:d	(Ljava/lang/String;)V
    //   252: aload_0
    //   253: aload 16
    //   255: aload_2
    //   256: aload_3
    //   257: invokevirtual 165	com/sohu/app/ads/sdk/e/d:a	(Ljava/lang/String;Ljava/io/File;Ljava/lang/String;)Ljava/util/Map;
    //   260: astore 17
    //   262: iconst_0
    //   263: ifeq +7 -> 270
    //   266: aconst_null
    //   267: invokevirtual 170	java/io/InputStream:close	()V
    //   270: iconst_0
    //   271: ifeq +7 -> 278
    //   274: aconst_null
    //   275: invokevirtual 173	java/io/FileOutputStream:close	()V
    //   278: aload 17
    //   280: areturn
    //   281: astore 19
    //   283: aload 19
    //   285: invokevirtual 176	java/io/IOException:printStackTrace	()V
    //   288: goto -18 -> 270
    //   291: astore 18
    //   293: aload 18
    //   295: invokevirtual 176	java/io/IOException:printStackTrace	()V
    //   298: aload 17
    //   300: areturn
    //   301: iload 14
    //   303: ifgt +70 -> 373
    //   306: aload 4
    //   308: ldc 29
    //   310: iconst_0
    //   311: invokestatic 182	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   314: invokeinterface 188 3 0
    //   319: pop
    //   320: aload 4
    //   322: ldc 33
    //   324: iconst_m1
    //   325: invokestatic 193	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   328: invokeinterface 188 3 0
    //   333: pop
    //   334: iconst_0
    //   335: ifeq +7 -> 342
    //   338: aconst_null
    //   339: invokevirtual 170	java/io/InputStream:close	()V
    //   342: iconst_0
    //   343: ifeq +7 -> 350
    //   346: aconst_null
    //   347: invokevirtual 173	java/io/FileOutputStream:close	()V
    //   350: aload 4
    //   352: areturn
    //   353: astore 38
    //   355: aload 38
    //   357: invokevirtual 176	java/io/IOException:printStackTrace	()V
    //   360: goto -18 -> 342
    //   363: astore 37
    //   365: aload 37
    //   367: invokevirtual 176	java/io/IOException:printStackTrace	()V
    //   370: goto -20 -> 350
    //   373: new 195	java/io/File
    //   376: dup
    //   377: aload_2
    //   378: aload_3
    //   379: invokespecial 198	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   382: astore 20
    //   384: aload 20
    //   386: invokevirtual 202	java/io/File:exists	()Z
    //   389: istore 21
    //   391: aconst_null
    //   392: astore 6
    //   394: aconst_null
    //   395: astore 5
    //   397: iload 21
    //   399: ifeq +97 -> 496
    //   402: aload 20
    //   404: invokevirtual 205	java/io/File:length	()J
    //   407: iload 14
    //   409: i2l
    //   410: lcmp
    //   411: ifne +79 -> 490
    //   414: ldc 207
    //   416: invokestatic 67	com/sohu/app/ads/sdk/c/a:d	(Ljava/lang/String;)V
    //   419: aload 4
    //   421: ldc 29
    //   423: iconst_1
    //   424: invokestatic 182	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   427: invokeinterface 188 3 0
    //   432: pop
    //   433: aload 4
    //   435: ldc 33
    //   437: aload 20
    //   439: invokevirtual 205	java/io/File:length	()J
    //   442: invokestatic 212	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   445: invokeinterface 188 3 0
    //   450: pop
    //   451: iconst_0
    //   452: ifeq +7 -> 459
    //   455: aconst_null
    //   456: invokevirtual 170	java/io/InputStream:close	()V
    //   459: iconst_0
    //   460: ifeq +7 -> 467
    //   463: aconst_null
    //   464: invokevirtual 173	java/io/FileOutputStream:close	()V
    //   467: aload 4
    //   469: areturn
    //   470: astore 34
    //   472: aload 34
    //   474: invokevirtual 176	java/io/IOException:printStackTrace	()V
    //   477: goto -18 -> 459
    //   480: astore 33
    //   482: aload 33
    //   484: invokevirtual 176	java/io/IOException:printStackTrace	()V
    //   487: goto -20 -> 467
    //   490: aload 20
    //   492: invokevirtual 215	java/io/File:delete	()Z
    //   495: pop
    //   496: aload 13
    //   498: invokevirtual 219	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   501: astore 23
    //   503: sipush 1024
    //   506: newarray byte
    //   508: astore 24
    //   510: new 172	java/io/FileOutputStream
    //   513: dup
    //   514: aload 20
    //   516: invokespecial 221	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   519: astore 25
    //   521: aload 23
    //   523: aload 24
    //   525: iconst_0
    //   526: sipush 1024
    //   529: invokevirtual 225	java/io/InputStream:read	([BII)I
    //   532: istore 26
    //   534: iload 26
    //   536: iconst_m1
    //   537: if_icmpeq +54 -> 591
    //   540: aload 25
    //   542: aload 24
    //   544: iconst_0
    //   545: iload 26
    //   547: invokevirtual 229	java/io/FileOutputStream:write	([BII)V
    //   550: goto -29 -> 521
    //   553: astore 7
    //   555: aload 25
    //   557: astore 6
    //   559: aload 23
    //   561: astore 5
    //   563: aload 7
    //   565: invokevirtual 230	java/lang/Exception:printStackTrace	()V
    //   568: aload 5
    //   570: ifnull +8 -> 578
    //   573: aload 5
    //   575: invokevirtual 170	java/io/InputStream:close	()V
    //   578: aload 6
    //   580: ifnull +8 -> 588
    //   583: aload 6
    //   585: invokevirtual 173	java/io/FileOutputStream:close	()V
    //   588: aload 4
    //   590: areturn
    //   591: aload 25
    //   593: invokevirtual 233	java/io/FileOutputStream:flush	()V
    //   596: aload 25
    //   598: invokevirtual 173	java/io/FileOutputStream:close	()V
    //   601: aload 23
    //   603: invokevirtual 170	java/io/InputStream:close	()V
    //   606: ldc 235
    //   608: invokestatic 67	com/sohu/app/ads/sdk/c/a:d	(Ljava/lang/String;)V
    //   611: aload 4
    //   613: ldc 29
    //   615: iconst_1
    //   616: invokestatic 182	java/lang/Boolean:valueOf	(Z)Ljava/lang/Boolean;
    //   619: invokeinterface 188 3 0
    //   624: pop
    //   625: aload 4
    //   627: ldc 33
    //   629: iload 14
    //   631: invokestatic 193	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   634: invokeinterface 188 3 0
    //   639: pop
    //   640: aload 23
    //   642: ifnull +8 -> 650
    //   645: aload 23
    //   647: invokevirtual 170	java/io/InputStream:close	()V
    //   650: aload 25
    //   652: ifnull +8 -> 660
    //   655: aload 25
    //   657: invokevirtual 173	java/io/FileOutputStream:close	()V
    //   660: aload 4
    //   662: areturn
    //   663: astore 30
    //   665: aload 30
    //   667: invokevirtual 176	java/io/IOException:printStackTrace	()V
    //   670: goto -20 -> 650
    //   673: astore 29
    //   675: aload 29
    //   677: invokevirtual 176	java/io/IOException:printStackTrace	()V
    //   680: goto -20 -> 660
    //   683: astore 12
    //   685: aload 12
    //   687: invokevirtual 176	java/io/IOException:printStackTrace	()V
    //   690: goto -112 -> 578
    //   693: astore 11
    //   695: aload 11
    //   697: invokevirtual 176	java/io/IOException:printStackTrace	()V
    //   700: goto -112 -> 588
    //   703: astore 8
    //   705: aload 5
    //   707: ifnull +8 -> 715
    //   710: aload 5
    //   712: invokevirtual 170	java/io/InputStream:close	()V
    //   715: aload 6
    //   717: ifnull +8 -> 725
    //   720: aload 6
    //   722: invokevirtual 173	java/io/FileOutputStream:close	()V
    //   725: aload 8
    //   727: athrow
    //   728: astore 10
    //   730: aload 10
    //   732: invokevirtual 176	java/io/IOException:printStackTrace	()V
    //   735: goto -20 -> 715
    //   738: astore 9
    //   740: aload 9
    //   742: invokevirtual 176	java/io/IOException:printStackTrace	()V
    //   745: goto -20 -> 725
    //   748: astore 8
    //   750: aload 23
    //   752: astore 5
    //   754: aconst_null
    //   755: astore 6
    //   757: goto -52 -> 705
    //   760: astore 8
    //   762: aload 25
    //   764: astore 6
    //   766: aload 23
    //   768: astore 5
    //   770: goto -65 -> 705
    //   773: astore 7
    //   775: aconst_null
    //   776: astore 6
    //   778: aconst_null
    //   779: astore 5
    //   781: goto -218 -> 563
    //   784: astore 7
    //   786: aload 23
    //   788: astore 5
    //   790: aconst_null
    //   791: astore 6
    //   793: goto -230 -> 563
    //   796: iload 15
    //   798: sipush 301
    //   801: if_icmpeq -581 -> 220
    //   804: iload 15
    //   806: sipush 302
    //   809: if_icmpne -508 -> 301
    //   812: goto -592 -> 220
    //
    // Exception table:
    //   from	to	target	type
    //   266	270	281	java/io/IOException
    //   274	278	291	java/io/IOException
    //   338	342	353	java/io/IOException
    //   346	350	363	java/io/IOException
    //   455	459	470	java/io/IOException
    //   463	467	480	java/io/IOException
    //   521	534	553	java/lang/Exception
    //   540	550	553	java/lang/Exception
    //   591	640	553	java/lang/Exception
    //   645	650	663	java/io/IOException
    //   655	660	673	java/io/IOException
    //   573	578	683	java/io/IOException
    //   583	588	693	java/io/IOException
    //   37	179	703	finally
    //   193	217	703	finally
    //   220	262	703	finally
    //   306	334	703	finally
    //   373	391	703	finally
    //   402	451	703	finally
    //   490	496	703	finally
    //   496	503	703	finally
    //   563	568	703	finally
    //   710	715	728	java/io/IOException
    //   720	725	738	java/io/IOException
    //   503	521	748	finally
    //   521	534	760	finally
    //   540	550	760	finally
    //   591	640	760	finally
    //   37	179	773	java/lang/Exception
    //   193	217	773	java/lang/Exception
    //   220	262	773	java/lang/Exception
    //   306	334	773	java/lang/Exception
    //   373	391	773	java/lang/Exception
    //   402	451	773	java/lang/Exception
    //   490	496	773	java/lang/Exception
    //   496	503	773	java/lang/Exception
    //   503	521	784	java/lang/Exception
  }

  public void a()
  {
    start();
  }

  public void a(e parame)
  {
    this.c = parame;
  }

  public void a(ArrayList<com.sohu.app.ads.sdk.model.e> paramArrayList)
  {
    this.g = paramArrayList;
  }

  public boolean b()
  {
    return e;
  }

  public void run()
  {
    super.run();
    while (true)
    {
      try
      {
        e = true;
        if ((this.g == null) || (this.g.size() <= 0))
          break;
        a.e("线程已开启,下载任务大小==" + this.g.size());
        com.sohu.app.ads.sdk.model.e locale = (com.sohu.app.ads.sdk.model.e)this.g.get(0);
        a.e("11111111111");
        if ((locale != null) && (!TextUtils.isEmpty(locale.a())))
        {
          a.e("222222222222");
          d = locale.a();
          a.e("3333333333333");
          if (this.c != null)
            this.c.a(DownloadEmue.DOWNLOADING, d, -1);
          Map localMap = a(d, f, com.sohu.app.ads.sdk.f.d.b(d));
          boolean bool = ((Boolean)localMap.get("status")).booleanValue();
          int i = ((Integer)localMap.get("length")).intValue();
          if (!bool)
            break label232;
          if (this.c != null)
            this.c.a(DownloadEmue.SUCESS, d, i);
        }
        a.e("999999999999999");
        this.g.remove(locale);
        continue;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
      label232: if (this.c != null)
        this.c.a(DownloadEmue.FAILED, d, -1);
    }
    a.e("********************while complete***************************");
    e = false;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.e.d
 * JD-Core Version:    0.6.2
 */