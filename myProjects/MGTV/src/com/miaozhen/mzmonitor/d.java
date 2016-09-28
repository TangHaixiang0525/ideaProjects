package com.miaozhen.mzmonitor;

import android.content.Context;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;

class d
{
  private static d a;
  private final String A = "MUID";
  private final String B = "PANELID";
  private final String C = "IESID";
  private final String D = "SIGNATURE";
  private final String E = "CARRIER";
  private final String F = "LACOLE";
  private final String G = "IP";
  private Context b;
  private g c;
  private String d;
  private final String e = "mv";
  private final String f = "mr";
  private final String g = "mc";
  private final String h = "mw";
  private final String i = "mu";
  private final String j = "mj";
  private final String k = "mg";
  private final String l = "m6";
  private final String m = "APPNAME";
  private final String n = "PACKAGENAME";
  private final String o = "IMEI";
  private final String p = "TS";
  private final String q = "ANDROIDID";
  private final String r = "MODEL";
  private final String s = "LOCATION";
  private final String t = "WIFI";
  private final String u = "OPENUDID";
  private final String v = "OS";
  private final String w = "OSVS";
  private final String x = "MAC";
  private final String y = "SCWH";
  private final String z = "ODIN";

  private d(Context paramContext)
  {
    this.b = paramContext;
  }

  public static d a(Context paramContext)
  {
    try
    {
      if (a == null)
        a = new d(paramContext.getApplicationContext());
      return a;
    }
    finally
    {
    }
  }

  // ERROR //
  private g a(java.io.InputStream paramInputStream)
  {
    // Byte code:
    //   0: new 182	com/miaozhen/mzmonitor/g
    //   3: dup
    //   4: aload_0
    //   5: invokespecial 185	com/miaozhen/mzmonitor/g:<init>	(Lcom/miaozhen/mzmonitor/d;)V
    //   8: astore_2
    //   9: invokestatic 191	android/util/Xml:newPullParser	()Lorg/xmlpull/v1/XmlPullParser;
    //   12: astore 9
    //   14: aload 9
    //   16: aload_1
    //   17: ldc 193
    //   19: invokeinterface 199 3 0
    //   24: aload 9
    //   26: invokeinterface 203 1 0
    //   31: istore 10
    //   33: iconst_0
    //   34: istore 11
    //   36: aconst_null
    //   37: astore 12
    //   39: aconst_null
    //   40: astore 13
    //   42: aconst_null
    //   43: astore 14
    //   45: iload 10
    //   47: iconst_1
    //   48: if_icmpne +18 -> 66
    //   51: aload_0
    //   52: aconst_null
    //   53: putfield 205	com/miaozhen/mzmonitor/d:d	Ljava/lang/String;
    //   56: aload_1
    //   57: ifnull +7 -> 64
    //   60: aload_1
    //   61: invokevirtual 210	java/io/InputStream:close	()V
    //   64: aload_2
    //   65: areturn
    //   66: iload 10
    //   68: tableswitch	default:+32 -> 100, 0:+1257->1325, 1:+32->100, 2:+80->148, 3:+1030->1098
    //   101: dconst_0
    //   102: astore 17
    //   104: aload 12
    //   106: astore 22
    //   108: aload 13
    //   110: astore 18
    //   112: aload 22
    //   114: astore 19
    //   116: aload 9
    //   118: invokeinterface 213 1 0
    //   123: istore 20
    //   125: aload 17
    //   127: astore 14
    //   129: iload 20
    //   131: istore 10
    //   133: aload 19
    //   135: astore 21
    //   137: aload 18
    //   139: astore 13
    //   141: aload 21
    //   143: astore 12
    //   145: goto -100 -> 45
    //   148: aload_0
    //   149: aload 9
    //   151: invokeinterface 217 1 0
    //   156: putfield 205	com/miaozhen/mzmonitor/d:d	Ljava/lang/String;
    //   159: aload_0
    //   160: ldc 219
    //   162: invokespecial 222	com/miaozhen/mzmonitor/d:a	(Ljava/lang/String;)Z
    //   165: ifeq +14 -> 179
    //   168: aload_2
    //   169: new 224	java/util/ArrayList
    //   172: dup
    //   173: invokespecial 225	java/util/ArrayList:<init>	()V
    //   176: putfield 228	com/miaozhen/mzmonitor/g:a	Ljava/util/List;
    //   179: aload_2
    //   180: getfield 228	com/miaozhen/mzmonitor/g:a	Ljava/util/List;
    //   183: ifnull +22 -> 205
    //   186: aload_0
    //   187: ldc 230
    //   189: invokespecial 222	com/miaozhen/mzmonitor/d:a	(Ljava/lang/String;)Z
    //   192: ifeq +13 -> 205
    //   195: new 232	com/miaozhen/mzmonitor/f
    //   198: dup
    //   199: aload_0
    //   200: invokespecial 233	com/miaozhen/mzmonitor/f:<init>	(Lcom/miaozhen/mzmonitor/d;)V
    //   203: astore 13
    //   205: aload 13
    //   207: ifnull -107 -> 100
    //   210: aload_0
    //   211: ldc 235
    //   213: invokespecial 222	com/miaozhen/mzmonitor/d:a	(Ljava/lang/String;)Z
    //   216: ifeq +44 -> 260
    //   219: iload 11
    //   221: ifne +39 -> 260
    //   224: aload 12
    //   226: ifnonnull +34 -> 260
    //   229: aload 13
    //   231: aload 9
    //   233: invokeinterface 238 1 0
    //   238: putfield 240	com/miaozhen/mzmonitor/f:a	Ljava/lang/String;
    //   241: aload 14
    //   243: astore 17
    //   245: aload 12
    //   247: astore 48
    //   249: aload 13
    //   251: astore 18
    //   253: aload 48
    //   255: astore 19
    //   257: goto -141 -> 116
    //   260: aload_0
    //   261: ldc 242
    //   263: invokespecial 222	com/miaozhen/mzmonitor/d:a	(Ljava/lang/String;)Z
    //   266: ifeq +60 -> 326
    //   269: aload 13
    //   271: getfield 244	com/miaozhen/mzmonitor/f:b	Ljava/util/List;
    //   274: ifnonnull +15 -> 289
    //   277: aload 13
    //   279: new 224	java/util/ArrayList
    //   282: dup
    //   283: invokespecial 225	java/util/ArrayList:<init>	()V
    //   286: putfield 244	com/miaozhen/mzmonitor/f:b	Ljava/util/List;
    //   289: aload 13
    //   291: getfield 244	com/miaozhen/mzmonitor/f:b	Ljava/util/List;
    //   294: aload 9
    //   296: invokeinterface 238 1 0
    //   301: invokeinterface 250 2 0
    //   306: pop
    //   307: aload 14
    //   309: astore 17
    //   311: aload 12
    //   313: astore 47
    //   315: aload 13
    //   317: astore 18
    //   319: aload 47
    //   321: astore 19
    //   323: goto -207 -> 116
    //   326: aload_0
    //   327: ldc 252
    //   329: invokespecial 222	com/miaozhen/mzmonitor/d:a	(Ljava/lang/String;)Z
    //   332: ifeq +34 -> 366
    //   335: aload 13
    //   337: aload 9
    //   339: invokeinterface 238 1 0
    //   344: putfield 254	com/miaozhen/mzmonitor/f:c	Ljava/lang/String;
    //   347: aload 14
    //   349: astore 17
    //   351: aload 12
    //   353: astore 45
    //   355: aload 13
    //   357: astore 18
    //   359: aload 45
    //   361: astore 19
    //   363: goto -247 -> 116
    //   366: aload_0
    //   367: ldc_w 256
    //   370: invokespecial 222	com/miaozhen/mzmonitor/d:a	(Ljava/lang/String;)Z
    //   373: ifeq +34 -> 407
    //   376: aload 13
    //   378: aload 9
    //   380: invokeinterface 238 1 0
    //   385: putfield 257	com/miaozhen/mzmonitor/f:d	Ljava/lang/String;
    //   388: aload 14
    //   390: astore 17
    //   392: aload 12
    //   394: astore 44
    //   396: aload 13
    //   398: astore 18
    //   400: aload 44
    //   402: astore 19
    //   404: goto -288 -> 116
    //   407: aload_0
    //   408: ldc_w 259
    //   411: invokespecial 222	com/miaozhen/mzmonitor/d:a	(Ljava/lang/String;)Z
    //   414: ifeq +34 -> 448
    //   417: aload 13
    //   419: aload 9
    //   421: invokeinterface 238 1 0
    //   426: putfield 260	com/miaozhen/mzmonitor/f:e	Ljava/lang/String;
    //   429: aload 14
    //   431: astore 17
    //   433: aload 12
    //   435: astore 43
    //   437: aload 13
    //   439: astore 18
    //   441: aload 43
    //   443: astore 19
    //   445: goto -329 -> 116
    //   448: aload_0
    //   449: ldc_w 262
    //   452: invokespecial 222	com/miaozhen/mzmonitor/d:a	(Ljava/lang/String;)Z
    //   455: ifeq +62 -> 517
    //   458: aload 9
    //   460: invokeinterface 238 1 0
    //   465: invokevirtual 267	java/lang/String:toUpperCase	()Ljava/lang/String;
    //   468: astore 41
    //   470: aload 41
    //   472: ldc_w 269
    //   475: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   478: ifne +14 -> 492
    //   481: aload 41
    //   483: ldc_w 274
    //   486: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   489: ifeq -389 -> 100
    //   492: aload 13
    //   494: iconst_0
    //   495: putfield 277	com/miaozhen/mzmonitor/f:j	Z
    //   498: aload 14
    //   500: astore 17
    //   502: aload 12
    //   504: astore 42
    //   506: aload 13
    //   508: astore 18
    //   510: aload 42
    //   512: astore 19
    //   514: goto -398 -> 116
    //   517: aload_0
    //   518: ldc_w 279
    //   521: invokespecial 222	com/miaozhen/mzmonitor/d:a	(Ljava/lang/String;)Z
    //   524: ifeq +62 -> 586
    //   527: aload 9
    //   529: invokeinterface 238 1 0
    //   534: invokevirtual 267	java/lang/String:toUpperCase	()Ljava/lang/String;
    //   537: astore 39
    //   539: aload 39
    //   541: ldc_w 281
    //   544: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   547: ifne +14 -> 561
    //   550: aload 39
    //   552: ldc_w 283
    //   555: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   558: ifeq -458 -> 100
    //   561: aload 13
    //   563: iconst_1
    //   564: putfield 285	com/miaozhen/mzmonitor/f:i	Z
    //   567: aload 14
    //   569: astore 17
    //   571: aload 12
    //   573: astore 40
    //   575: aload 13
    //   577: astore 18
    //   579: aload 40
    //   581: astore 19
    //   583: goto -467 -> 116
    //   586: aload_0
    //   587: ldc_w 287
    //   590: invokespecial 222	com/miaozhen/mzmonitor/d:a	(Ljava/lang/String;)Z
    //   593: ifeq +37 -> 630
    //   596: aload 13
    //   598: new 224	java/util/ArrayList
    //   601: dup
    //   602: invokespecial 225	java/util/ArrayList:<init>	()V
    //   605: putfield 289	com/miaozhen/mzmonitor/f:g	Ljava/util/List;
    //   608: iconst_1
    //   609: istore 11
    //   611: aload 14
    //   613: astore 17
    //   615: aload 12
    //   617: astore 38
    //   619: aload 13
    //   621: astore 18
    //   623: aload 38
    //   625: astore 19
    //   627: goto -511 -> 116
    //   630: iload 11
    //   632: ifeq +194 -> 826
    //   635: aload_0
    //   636: ldc_w 291
    //   639: invokespecial 222	com/miaozhen/mzmonitor/d:a	(Ljava/lang/String;)Z
    //   642: ifeq +34 -> 676
    //   645: aload 14
    //   647: aload 9
    //   649: invokeinterface 238 1 0
    //   654: putfield 294	com/miaozhen/mzmonitor/e:c	Ljava/lang/String;
    //   657: aload 14
    //   659: astore 17
    //   661: aload 12
    //   663: astore 30
    //   665: aload 13
    //   667: astore 18
    //   669: aload 30
    //   671: astore 19
    //   673: goto -557 -> 116
    //   676: aload_0
    //   677: ldc 235
    //   679: invokespecial 222	com/miaozhen/mzmonitor/d:a	(Ljava/lang/String;)Z
    //   682: ifeq +34 -> 716
    //   685: aload 14
    //   687: aload 9
    //   689: invokeinterface 238 1 0
    //   694: putfield 296	com/miaozhen/mzmonitor/e:b	Ljava/lang/String;
    //   697: aload 14
    //   699: astore 17
    //   701: aload 12
    //   703: astore 29
    //   705: aload 13
    //   707: astore 18
    //   709: aload 29
    //   711: astore 19
    //   713: goto -597 -> 116
    //   716: aload_0
    //   717: ldc_w 298
    //   720: invokespecial 222	com/miaozhen/mzmonitor/d:a	(Ljava/lang/String;)Z
    //   723: ifeq +62 -> 785
    //   726: aload 9
    //   728: invokeinterface 238 1 0
    //   733: invokevirtual 267	java/lang/String:toUpperCase	()Ljava/lang/String;
    //   736: astore 27
    //   738: aload 27
    //   740: ldc_w 269
    //   743: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   746: ifne +14 -> 760
    //   749: aload 27
    //   751: ldc_w 274
    //   754: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   757: ifeq -657 -> 100
    //   760: aload 14
    //   762: iconst_0
    //   763: putfield 300	com/miaozhen/mzmonitor/e:d	Z
    //   766: aload 14
    //   768: astore 17
    //   770: aload 12
    //   772: astore 28
    //   774: aload 13
    //   776: astore 18
    //   778: aload 28
    //   780: astore 19
    //   782: goto -666 -> 116
    //   785: new 293	com/miaozhen/mzmonitor/e
    //   788: dup
    //   789: aload_0
    //   790: invokespecial 301	com/miaozhen/mzmonitor/e:<init>	(Lcom/miaozhen/mzmonitor/d;)V
    //   793: astore 25
    //   795: aload 25
    //   797: aload 9
    //   799: invokeinterface 217 1 0
    //   804: putfield 302	com/miaozhen/mzmonitor/e:a	Ljava/lang/String;
    //   807: aload 25
    //   809: astore 17
    //   811: aload 12
    //   813: astore 26
    //   815: aload 13
    //   817: astore 18
    //   819: aload 26
    //   821: astore 19
    //   823: goto -707 -> 116
    //   826: aload_0
    //   827: ldc_w 304
    //   830: invokespecial 222	com/miaozhen/mzmonitor/d:a	(Ljava/lang/String;)Z
    //   833: ifeq +34 -> 867
    //   836: aload 13
    //   838: new 224	java/util/ArrayList
    //   841: dup
    //   842: invokespecial 225	java/util/ArrayList:<init>	()V
    //   845: putfield 306	com/miaozhen/mzmonitor/f:h	Ljava/util/List;
    //   848: aload 14
    //   850: astore 17
    //   852: aload 12
    //   854: astore 37
    //   856: aload 13
    //   858: astore 18
    //   860: aload 37
    //   862: astore 19
    //   864: goto -748 -> 116
    //   867: aload_0
    //   868: ldc_w 308
    //   871: invokespecial 222	com/miaozhen/mzmonitor/d:a	(Ljava/lang/String;)Z
    //   874: ifeq +28 -> 902
    //   877: new 310	com/miaozhen/mzmonitor/h
    //   880: dup
    //   881: aload_0
    //   882: invokespecial 311	com/miaozhen/mzmonitor/h:<init>	(Lcom/miaozhen/mzmonitor/d;)V
    //   885: astore 31
    //   887: aload 14
    //   889: astore 17
    //   891: aload 13
    //   893: astore 18
    //   895: aload 31
    //   897: astore 19
    //   899: goto -783 -> 116
    //   902: aload 12
    //   904: ifnull -804 -> 100
    //   907: aload_0
    //   908: ldc_w 313
    //   911: invokespecial 222	com/miaozhen/mzmonitor/d:a	(Ljava/lang/String;)Z
    //   914: ifeq +34 -> 948
    //   917: aload 12
    //   919: aload 9
    //   921: invokeinterface 238 1 0
    //   926: putfield 314	com/miaozhen/mzmonitor/h:a	Ljava/lang/String;
    //   929: aload 14
    //   931: astore 17
    //   933: aload 12
    //   935: astore 36
    //   937: aload 13
    //   939: astore 18
    //   941: aload 36
    //   943: astore 19
    //   945: goto -829 -> 116
    //   948: aload_0
    //   949: ldc 235
    //   951: invokespecial 222	com/miaozhen/mzmonitor/d:a	(Ljava/lang/String;)Z
    //   954: ifeq +34 -> 988
    //   957: aload 12
    //   959: aload 9
    //   961: invokeinterface 238 1 0
    //   966: putfield 315	com/miaozhen/mzmonitor/h:b	Ljava/lang/String;
    //   969: aload 14
    //   971: astore 17
    //   973: aload 12
    //   975: astore 35
    //   977: aload 13
    //   979: astore 18
    //   981: aload 35
    //   983: astore 19
    //   985: goto -869 -> 116
    //   988: aload_0
    //   989: ldc_w 317
    //   992: invokespecial 222	com/miaozhen/mzmonitor/d:a	(Ljava/lang/String;)Z
    //   995: ifeq +34 -> 1029
    //   998: aload 12
    //   1000: aload 9
    //   1002: invokeinterface 238 1 0
    //   1007: putfield 318	com/miaozhen/mzmonitor/h:c	Ljava/lang/String;
    //   1010: aload 14
    //   1012: astore 17
    //   1014: aload 12
    //   1016: astore 34
    //   1018: aload 13
    //   1020: astore 18
    //   1022: aload 34
    //   1024: astore 19
    //   1026: goto -910 -> 116
    //   1029: aload_0
    //   1030: ldc_w 298
    //   1033: invokespecial 222	com/miaozhen/mzmonitor/d:a	(Ljava/lang/String;)Z
    //   1036: ifeq -936 -> 100
    //   1039: aload 9
    //   1041: invokeinterface 238 1 0
    //   1046: invokevirtual 267	java/lang/String:toUpperCase	()Ljava/lang/String;
    //   1049: astore 32
    //   1051: aload 32
    //   1053: ldc_w 269
    //   1056: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1059: ifne +14 -> 1073
    //   1062: aload 32
    //   1064: ldc_w 274
    //   1067: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1070: ifeq -970 -> 100
    //   1073: aload 12
    //   1075: iconst_0
    //   1076: putfield 319	com/miaozhen/mzmonitor/h:d	Z
    //   1079: aload 14
    //   1081: astore 17
    //   1083: aload 12
    //   1085: astore 33
    //   1087: aload 13
    //   1089: astore 18
    //   1091: aload 33
    //   1093: astore 19
    //   1095: goto -979 -> 116
    //   1098: aload_0
    //   1099: aload 9
    //   1101: invokeinterface 217 1 0
    //   1106: putfield 205	com/miaozhen/mzmonitor/d:d	Ljava/lang/String;
    //   1109: aload_0
    //   1110: ldc 230
    //   1112: invokespecial 222	com/miaozhen/mzmonitor/d:a	(Ljava/lang/String;)Z
    //   1115: ifeq +18 -> 1133
    //   1118: aload_2
    //   1119: getfield 228	com/miaozhen/mzmonitor/g:a	Ljava/util/List;
    //   1122: aload 13
    //   1124: invokeinterface 250 2 0
    //   1129: pop
    //   1130: aconst_null
    //   1131: astore 13
    //   1133: aload_0
    //   1134: ldc_w 287
    //   1137: invokespecial 222	com/miaozhen/mzmonitor/d:a	(Ljava/lang/String;)Z
    //   1140: ifeq +6 -> 1146
    //   1143: iconst_0
    //   1144: istore 11
    //   1146: iload 11
    //   1148: ifeq +58 -> 1206
    //   1151: aload_0
    //   1152: ldc_w 291
    //   1155: invokespecial 222	com/miaozhen/mzmonitor/d:a	(Ljava/lang/String;)Z
    //   1158: ifne +48 -> 1206
    //   1161: aload_0
    //   1162: ldc 235
    //   1164: invokespecial 222	com/miaozhen/mzmonitor/d:a	(Ljava/lang/String;)Z
    //   1167: ifne +39 -> 1206
    //   1170: aload_0
    //   1171: ldc_w 298
    //   1174: invokespecial 222	com/miaozhen/mzmonitor/d:a	(Ljava/lang/String;)Z
    //   1177: ifne +29 -> 1206
    //   1180: aload_0
    //   1181: ldc_w 262
    //   1184: invokespecial 222	com/miaozhen/mzmonitor/d:a	(Ljava/lang/String;)Z
    //   1187: ifne +19 -> 1206
    //   1190: aload 13
    //   1192: getfield 289	com/miaozhen/mzmonitor/f:g	Ljava/util/List;
    //   1195: aload 14
    //   1197: invokeinterface 250 2 0
    //   1202: pop
    //   1203: aconst_null
    //   1204: astore 14
    //   1206: aload_0
    //   1207: ldc_w 308
    //   1210: invokespecial 222	com/miaozhen/mzmonitor/d:a	(Ljava/lang/String;)Z
    //   1213: ifeq -1113 -> 100
    //   1216: aload 13
    //   1218: getfield 306	com/miaozhen/mzmonitor/f:h	Ljava/util/List;
    //   1221: aload 12
    //   1223: invokeinterface 250 2 0
    //   1228: pop
    //   1229: aload 14
    //   1231: astore 17
    //   1233: aload 13
    //   1235: astore 18
    //   1237: aconst_null
    //   1238: astore 19
    //   1240: goto -1124 -> 116
    //   1243: astore 7
    //   1245: aload 7
    //   1247: invokevirtual 322	org/xmlpull/v1/XmlPullParserException:printStackTrace	()V
    //   1250: aload_1
    //   1251: ifnull -1187 -> 64
    //   1254: aload_1
    //   1255: invokevirtual 210	java/io/InputStream:close	()V
    //   1258: aload_2
    //   1259: areturn
    //   1260: astore 8
    //   1262: aload 8
    //   1264: invokevirtual 323	java/io/IOException:printStackTrace	()V
    //   1267: aload_2
    //   1268: areturn
    //   1269: astore 5
    //   1271: aload 5
    //   1273: invokevirtual 323	java/io/IOException:printStackTrace	()V
    //   1276: aload_1
    //   1277: ifnull -1213 -> 64
    //   1280: aload_1
    //   1281: invokevirtual 210	java/io/InputStream:close	()V
    //   1284: aload_2
    //   1285: areturn
    //   1286: astore 6
    //   1288: aload 6
    //   1290: invokevirtual 323	java/io/IOException:printStackTrace	()V
    //   1293: aload_2
    //   1294: areturn
    //   1295: astore_3
    //   1296: aload_1
    //   1297: ifnull +7 -> 1304
    //   1300: aload_1
    //   1301: invokevirtual 210	java/io/InputStream:close	()V
    //   1304: aload_3
    //   1305: athrow
    //   1306: astore 4
    //   1308: aload 4
    //   1310: invokevirtual 323	java/io/IOException:printStackTrace	()V
    //   1313: goto -9 -> 1304
    //   1316: astore 15
    //   1318: aload 15
    //   1320: invokevirtual 323	java/io/IOException:printStackTrace	()V
    //   1323: aload_2
    //   1324: areturn
    //   1325: aload 14
    //   1327: astore 17
    //   1329: aload 12
    //   1331: astore 49
    //   1333: aload 13
    //   1335: astore 18
    //   1337: aload 49
    //   1339: astore 19
    //   1341: goto -1225 -> 116
    //
    // Exception table:
    //   from	to	target	type
    //   9	33	1243	org/xmlpull/v1/XmlPullParserException
    //   51	56	1243	org/xmlpull/v1/XmlPullParserException
    //   116	125	1243	org/xmlpull/v1/XmlPullParserException
    //   148	179	1243	org/xmlpull/v1/XmlPullParserException
    //   179	205	1243	org/xmlpull/v1/XmlPullParserException
    //   210	219	1243	org/xmlpull/v1/XmlPullParserException
    //   229	241	1243	org/xmlpull/v1/XmlPullParserException
    //   260	289	1243	org/xmlpull/v1/XmlPullParserException
    //   289	307	1243	org/xmlpull/v1/XmlPullParserException
    //   326	347	1243	org/xmlpull/v1/XmlPullParserException
    //   366	388	1243	org/xmlpull/v1/XmlPullParserException
    //   407	429	1243	org/xmlpull/v1/XmlPullParserException
    //   448	492	1243	org/xmlpull/v1/XmlPullParserException
    //   492	498	1243	org/xmlpull/v1/XmlPullParserException
    //   517	561	1243	org/xmlpull/v1/XmlPullParserException
    //   561	567	1243	org/xmlpull/v1/XmlPullParserException
    //   586	608	1243	org/xmlpull/v1/XmlPullParserException
    //   635	657	1243	org/xmlpull/v1/XmlPullParserException
    //   676	697	1243	org/xmlpull/v1/XmlPullParserException
    //   716	760	1243	org/xmlpull/v1/XmlPullParserException
    //   760	766	1243	org/xmlpull/v1/XmlPullParserException
    //   785	807	1243	org/xmlpull/v1/XmlPullParserException
    //   826	848	1243	org/xmlpull/v1/XmlPullParserException
    //   867	887	1243	org/xmlpull/v1/XmlPullParserException
    //   907	929	1243	org/xmlpull/v1/XmlPullParserException
    //   948	969	1243	org/xmlpull/v1/XmlPullParserException
    //   988	1010	1243	org/xmlpull/v1/XmlPullParserException
    //   1029	1073	1243	org/xmlpull/v1/XmlPullParserException
    //   1073	1079	1243	org/xmlpull/v1/XmlPullParserException
    //   1098	1130	1243	org/xmlpull/v1/XmlPullParserException
    //   1133	1143	1243	org/xmlpull/v1/XmlPullParserException
    //   1151	1203	1243	org/xmlpull/v1/XmlPullParserException
    //   1206	1229	1243	org/xmlpull/v1/XmlPullParserException
    //   1254	1258	1260	java/io/IOException
    //   9	33	1269	java/io/IOException
    //   51	56	1269	java/io/IOException
    //   116	125	1269	java/io/IOException
    //   148	179	1269	java/io/IOException
    //   179	205	1269	java/io/IOException
    //   210	219	1269	java/io/IOException
    //   229	241	1269	java/io/IOException
    //   260	289	1269	java/io/IOException
    //   289	307	1269	java/io/IOException
    //   326	347	1269	java/io/IOException
    //   366	388	1269	java/io/IOException
    //   407	429	1269	java/io/IOException
    //   448	492	1269	java/io/IOException
    //   492	498	1269	java/io/IOException
    //   517	561	1269	java/io/IOException
    //   561	567	1269	java/io/IOException
    //   586	608	1269	java/io/IOException
    //   635	657	1269	java/io/IOException
    //   676	697	1269	java/io/IOException
    //   716	760	1269	java/io/IOException
    //   760	766	1269	java/io/IOException
    //   785	807	1269	java/io/IOException
    //   826	848	1269	java/io/IOException
    //   867	887	1269	java/io/IOException
    //   907	929	1269	java/io/IOException
    //   948	969	1269	java/io/IOException
    //   988	1010	1269	java/io/IOException
    //   1029	1073	1269	java/io/IOException
    //   1073	1079	1269	java/io/IOException
    //   1098	1130	1269	java/io/IOException
    //   1133	1143	1269	java/io/IOException
    //   1151	1203	1269	java/io/IOException
    //   1206	1229	1269	java/io/IOException
    //   1280	1284	1286	java/io/IOException
    //   9	33	1295	finally
    //   51	56	1295	finally
    //   116	125	1295	finally
    //   148	179	1295	finally
    //   179	205	1295	finally
    //   210	219	1295	finally
    //   229	241	1295	finally
    //   260	289	1295	finally
    //   289	307	1295	finally
    //   326	347	1295	finally
    //   366	388	1295	finally
    //   407	429	1295	finally
    //   448	492	1295	finally
    //   492	498	1295	finally
    //   517	561	1295	finally
    //   561	567	1295	finally
    //   586	608	1295	finally
    //   635	657	1295	finally
    //   676	697	1295	finally
    //   716	760	1295	finally
    //   760	766	1295	finally
    //   785	807	1295	finally
    //   826	848	1295	finally
    //   867	887	1295	finally
    //   907	929	1295	finally
    //   948	969	1295	finally
    //   988	1010	1295	finally
    //   1029	1073	1295	finally
    //   1073	1079	1295	finally
    //   1098	1130	1295	finally
    //   1133	1143	1295	finally
    //   1151	1203	1295	finally
    //   1206	1229	1295	finally
    //   1245	1250	1295	finally
    //   1271	1276	1295	finally
    //   1300	1304	1306	java/io/IOException
    //   60	64	1316	java/io/IOException
  }

  private h a(String paramString, f paramf)
  {
    Iterator localIterator;
    if ((paramString != null) && (paramf.h != null))
      localIterator = paramf.h.iterator();
    h localh;
    do
    {
      if (!localIterator.hasNext())
        return null;
      localh = (h)localIterator.next();
    }
    while (!paramString.equals(localh.a));
    return localh;
  }

  private String a(a parama, f paramf)
  {
    MZDeviceInfo localMZDeviceInfo = MZDeviceInfo.getDeviceInfo(this.b);
    StringBuilder localStringBuilder = new StringBuilder();
    String str1 = paramf.c;
    String str2 = paramf.d;
    try
    {
      localStringBuilder.append(str1 + "mv" + str2 + URLEncoder.encode("a3.0", "UTF-8"));
      localStringBuilder.append(str1 + "mr" + str2 + URLEncoder.encode(new StringBuilder().append(parama.h()).toString(), "UTF-8"));
      localStringBuilder.append(str1 + "mc" + str2 + URLEncoder.encode(new StringBuilder().append(MZSdkProfile.getProfileVersion(this.b)).toString(), "UTF-8"));
      localStringBuilder.append(str1 + "mw" + str2 + URLEncoder.encode(localMZDeviceInfo.getCurrentNetworkType(), "UTF-8"));
      String str3 = localMZDeviceInfo.getWifiSSID();
      if (str3 != null)
        localStringBuilder.append(paramf.c + "mj" + paramf.d + URLEncoder.encode(str3, "UTF-8"));
      if (parama.h() > 0)
      {
        String str6 = MZUtility.currentUnixTimestamp() - parama.g();
        localStringBuilder.append(str1 + "mu" + str2 + URLEncoder.encode(str6, "UTF-8"));
      }
      while (true)
      {
        String str4 = MZSdkProfile.getLatestLocation_MZ(this.b);
        if ((str4 != null) && (!str4.contains("UNKNOWN")))
          localStringBuilder.append(str1 + "mg" + str2 + URLEncoder.encode(str4, "UTF-8"));
        String str5 = localMZDeviceInfo.getMacAddress();
        if ((str5 != null) && (!str5.equals("")))
          localStringBuilder.append(str1 + "m6" + str2 + URLEncoder.encode(MZUtility.MD5(str5).toUpperCase(), "UTF-8"));
        return localStringBuilder.toString();
        localStringBuilder.append(str1 + "mu" + str2 + URLEncoder.encode("0", "UTF-8"));
      }
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      while (true)
        localUnsupportedEncodingException.printStackTrace();
    }
  }

  private String a(String paramString, a parama, f paramf)
  {
    List localList = paramf.g;
    String str1 = paramf.c;
    String str2 = paramf.d;
    Iterator localIterator = localList.iterator();
    while (true)
    {
      if (!localIterator.hasNext())
      {
        h localh = a(parama.d(), paramf);
        if (localh != null)
          paramString = paramString.replaceAll(str1 + localh.b + str2 + "[^" + str1 + "]*", "");
        return paramString;
      }
      e locale = (e)localIterator.next();
      if (((!locale.a.equals("PANELID")) || (parama.b() != null)) && ((!locale.a.equals("MUID")) || (parama.c() != null)) && ((!locale.a.equals("IESID")) || (parama.i() != null)) && (paramString.contains(str1 + locale.b + str2)))
        paramString = paramString.replaceAll(str1 + locale.b + str2 + "[^" + str1 + "]*", "");
    }
  }

  private String a(String paramString, e parame)
  {
    if ((paramString != null) && (!paramString.equals("")))
    {
      if (parame.c.equals("md5"))
        paramString = MZUtility.MD5(paramString);
      while (true)
      {
        if (parame.d);
        try
        {
          String str = URLEncoder.encode(paramString, "UTF-8");
          paramString = str;
          return paramString;
          if (parame.c.equals("sha1"))
            paramString = MZUtility.SHA1(paramString);
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
          localUnsupportedEncodingException.printStackTrace();
          return paramString;
        }
      }
    }
    return "";
  }

  private boolean a(String paramString)
  {
    return paramString.equals(this.d);
  }

  private String b(String paramString, f paramf)
  {
    if ((paramf.e != null) && (!paramf.e.equals("")) && (paramString.contains(paramf.e)))
      return paramString.substring(paramString.indexOf(paramf.e));
    return "";
  }

  private String c(String paramString, f paramf)
  {
    if ((paramf.e != null) && (!paramf.e.equals("")) && (paramString.contains(paramf.e)))
      paramString = paramString.substring(0, paramString.indexOf(paramf.e));
    return paramString;
  }

  public f a(URL paramURL)
  {
    String str;
    if ((this.c != null) && (this.c.a != null))
      str = paramURL.getHost();
    f localf;
    Iterator localIterator2;
    do
    {
      Iterator localIterator1 = this.c.a.iterator();
      while (!localIterator2.hasNext())
      {
        if (!localIterator1.hasNext())
          return null;
        localf = (f)localIterator1.next();
        localIterator2 = localf.b.iterator();
      }
    }
    while (!str.endsWith((String)localIterator2.next()));
    return localf;
  }

  g a()
  {
    try
    {
      g localg = this.c;
      return localg;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  // ERROR //
  public URL a(a parama)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 164	com/miaozhen/mzmonitor/d:b	Landroid/content/Context;
    //   6: invokestatic 346	com/miaozhen/mzmonitor/MZDeviceInfo:getDeviceInfo	(Landroid/content/Context;)Lcom/miaozhen/mzmonitor/MZDeviceInfo;
    //   9: astore_3
    //   10: new 348	java/lang/StringBuilder
    //   13: dup
    //   14: invokespecial 349	java/lang/StringBuilder:<init>	()V
    //   17: astore 4
    //   19: aload_1
    //   20: invokevirtual 478	com/miaozhen/mzmonitor/a:a	()Ljava/lang/String;
    //   23: astore 5
    //   25: new 466	java/net/URL
    //   28: dup
    //   29: aload 5
    //   31: invokespecial 479	java/net/URL:<init>	(Ljava/lang/String;)V
    //   34: astore 6
    //   36: aload_0
    //   37: aload 6
    //   39: invokevirtual 481	com/miaozhen/mzmonitor/d:a	(Ljava/net/URL;)Lcom/miaozhen/mzmonitor/f;
    //   42: astore 11
    //   44: aload 11
    //   46: ifnull +1192 -> 1238
    //   49: aload 11
    //   51: getfield 285	com/miaozhen/mzmonitor/f:i	Z
    //   54: ifeq +1184 -> 1238
    //   57: aload_0
    //   58: aload 5
    //   60: aload 11
    //   62: invokespecial 483	com/miaozhen/mzmonitor/d:b	(Ljava/lang/String;Lcom/miaozhen/mzmonitor/f;)Ljava/lang/String;
    //   65: astore 12
    //   67: aload_0
    //   68: aload_0
    //   69: aload 5
    //   71: aload 11
    //   73: invokespecial 485	com/miaozhen/mzmonitor/d:c	(Ljava/lang/String;Lcom/miaozhen/mzmonitor/f;)Ljava/lang/String;
    //   76: aload_1
    //   77: aload 11
    //   79: invokespecial 487	com/miaozhen/mzmonitor/d:a	(Ljava/lang/String;Lcom/miaozhen/mzmonitor/a;Lcom/miaozhen/mzmonitor/f;)Ljava/lang/String;
    //   82: astore 13
    //   84: aload 4
    //   86: aload 13
    //   88: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   91: pop
    //   92: aload 11
    //   94: getfield 240	com/miaozhen/mzmonitor/f:a	Ljava/lang/String;
    //   97: ldc_w 489
    //   100: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   103: ifeq +16 -> 119
    //   106: aload 4
    //   108: aload_0
    //   109: aload_1
    //   110: aload 11
    //   112: invokespecial 491	com/miaozhen/mzmonitor/d:a	(Lcom/miaozhen/mzmonitor/a;Lcom/miaozhen/mzmonitor/f;)Ljava/lang/String;
    //   115: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   118: pop
    //   119: aload 11
    //   121: getfield 289	com/miaozhen/mzmonitor/f:g	Ljava/util/List;
    //   124: invokeinterface 328 1 0
    //   129: astore 15
    //   131: aload 15
    //   133: invokeinterface 334 1 0
    //   138: ifne +292 -> 430
    //   141: aload_1
    //   142: invokevirtual 426	com/miaozhen/mzmonitor/a:d	()Ljava/lang/String;
    //   145: ifnull +1086 -> 1231
    //   148: aload 11
    //   150: getfield 254	com/miaozhen/mzmonitor/f:c	Ljava/lang/String;
    //   153: astore 29
    //   155: aload_1
    //   156: invokevirtual 426	com/miaozhen/mzmonitor/a:d	()Ljava/lang/String;
    //   159: ldc 193
    //   161: invokestatic 368	java/net/URLEncoder:encode	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   164: astore 30
    //   166: aload_0
    //   167: aload_1
    //   168: invokevirtual 426	com/miaozhen/mzmonitor/a:d	()Ljava/lang/String;
    //   171: aload 11
    //   173: invokespecial 428	com/miaozhen/mzmonitor/d:a	(Ljava/lang/String;Lcom/miaozhen/mzmonitor/f;)Lcom/miaozhen/mzmonitor/h;
    //   176: astore 31
    //   178: aload 31
    //   180: ifnull +56 -> 236
    //   183: new 348	java/lang/StringBuilder
    //   186: dup
    //   187: aload 29
    //   189: invokestatic 353	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   192: invokespecial 356	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   195: aload 31
    //   197: getfield 315	com/miaozhen/mzmonitor/h:b	Ljava/lang/String;
    //   200: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   203: aload 11
    //   205: getfield 257	com/miaozhen/mzmonitor/f:d	Ljava/lang/String;
    //   208: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   211: invokevirtual 371	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   214: astore 29
    //   216: aload 31
    //   218: getfield 319	com/miaozhen/mzmonitor/h:d	Z
    //   221: ifeq +988 -> 1209
    //   224: aload 31
    //   226: getfield 318	com/miaozhen/mzmonitor/h:c	Ljava/lang/String;
    //   229: ldc 193
    //   231: invokestatic 368	java/net/URLEncoder:encode	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   234: astore 30
    //   236: aload 4
    //   238: new 348	java/lang/StringBuilder
    //   241: dup
    //   242: aload 29
    //   244: invokestatic 353	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   247: invokespecial 356	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   250: aload 30
    //   252: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   255: invokevirtual 371	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   258: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   261: pop
    //   262: new 348	java/lang/StringBuilder
    //   265: dup
    //   266: aload 13
    //   268: invokestatic 353	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   271: invokespecial 356	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   274: aload 29
    //   276: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   279: aload 30
    //   281: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   284: invokevirtual 371	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   287: astore 26
    //   289: aload 11
    //   291: getfield 492	com/miaozhen/mzmonitor/f:f	Ljava/lang/String;
    //   294: ifnull +58 -> 352
    //   297: aload 4
    //   299: new 348	java/lang/StringBuilder
    //   302: dup
    //   303: aload 11
    //   305: getfield 254	com/miaozhen/mzmonitor/f:c	Ljava/lang/String;
    //   308: invokestatic 353	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   311: invokespecial 356	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   314: aload 11
    //   316: getfield 492	com/miaozhen/mzmonitor/f:f	Ljava/lang/String;
    //   319: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   322: aload 11
    //   324: getfield 257	com/miaozhen/mzmonitor/f:d	Ljava/lang/String;
    //   327: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   330: aload_0
    //   331: getfield 164	com/miaozhen/mzmonitor/d:b	Landroid/content/Context;
    //   334: aload 4
    //   336: invokevirtual 371	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   339: invokestatic 498	com/miaozhen/mzmonitor/MZSignaturer:getSignature	(Landroid/content/Context;Ljava/lang/String;)Ljava/lang/String;
    //   342: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   345: invokevirtual 371	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   348: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   351: pop
    //   352: aload_1
    //   353: new 348	java/lang/StringBuilder
    //   356: dup
    //   357: aload 26
    //   359: invokestatic 353	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   362: invokespecial 356	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   365: aload 12
    //   367: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   370: invokevirtual 371	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   373: invokevirtual 500	com/miaozhen/mzmonitor/a:a	(Ljava/lang/String;)V
    //   376: ldc_w 502
    //   379: new 348	java/lang/StringBuilder
    //   382: dup
    //   383: ldc_w 504
    //   386: invokespecial 356	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   389: aload 26
    //   391: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   394: aload 12
    //   396: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   399: invokevirtual 371	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   402: invokestatic 509	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   405: pop
    //   406: new 466	java/net/URL
    //   409: dup
    //   410: aload 4
    //   412: aload 12
    //   414: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   417: invokevirtual 371	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   420: invokespecial 479	java/net/URL:<init>	(Ljava/lang/String;)V
    //   423: astore 9
    //   425: aload_0
    //   426: monitorexit
    //   427: aload 9
    //   429: areturn
    //   430: aload 15
    //   432: invokeinterface 337 1 0
    //   437: checkcast 293	com/miaozhen/mzmonitor/e
    //   440: astore 16
    //   442: new 348	java/lang/StringBuilder
    //   445: dup
    //   446: aload 11
    //   448: getfield 254	com/miaozhen/mzmonitor/f:c	Ljava/lang/String;
    //   451: invokestatic 353	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   454: invokespecial 356	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   457: aload 16
    //   459: getfield 296	com/miaozhen/mzmonitor/e:b	Ljava/lang/String;
    //   462: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   465: aload 11
    //   467: getfield 257	com/miaozhen/mzmonitor/f:d	Ljava/lang/String;
    //   470: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   473: invokevirtual 371	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   476: astore 17
    //   478: aload 16
    //   480: getfield 302	com/miaozhen/mzmonitor/e:a	Ljava/lang/String;
    //   483: astore 18
    //   485: ldc_w 416
    //   488: astore 19
    //   490: aload 18
    //   492: ldc 80
    //   494: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   497: ifeq +208 -> 705
    //   500: aload_3
    //   501: invokevirtual 512	com/miaozhen/mzmonitor/MZDeviceInfo:getAppName	()Ljava/lang/String;
    //   504: astore 19
    //   506: aload_0
    //   507: aload 19
    //   509: aload 16
    //   511: invokespecial 514	com/miaozhen/mzmonitor/d:a	(Ljava/lang/String;Lcom/miaozhen/mzmonitor/e;)Ljava/lang/String;
    //   514: astore 20
    //   516: aload 18
    //   518: ldc 140
    //   520: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   523: ifne +23 -> 546
    //   526: aload 18
    //   528: ldc 136
    //   530: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   533: ifne +13 -> 546
    //   536: aload 18
    //   538: ldc 144
    //   540: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   543: ifeq +65 -> 608
    //   546: aload 13
    //   548: aload 17
    //   550: invokevirtual 411	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   553: ifne -422 -> 131
    //   556: aload 20
    //   558: ldc_w 416
    //   561: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   564: ifeq +17 -> 581
    //   567: aload 11
    //   569: getfield 240	com/miaozhen/mzmonitor/f:a	Ljava/lang/String;
    //   572: ldc_w 489
    //   575: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   578: ifne -447 -> 131
    //   581: new 348	java/lang/StringBuilder
    //   584: dup
    //   585: aload 13
    //   587: invokestatic 353	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   590: invokespecial 356	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   593: aload 17
    //   595: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   598: aload 20
    //   600: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   603: invokevirtual 371	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   606: astore 13
    //   608: aload 20
    //   610: ldc_w 416
    //   613: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   616: ifeq +17 -> 633
    //   619: aload 11
    //   621: getfield 240	com/miaozhen/mzmonitor/f:a	Ljava/lang/String;
    //   624: ldc_w 489
    //   627: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   630: ifne -499 -> 131
    //   633: aload 4
    //   635: new 348	java/lang/StringBuilder
    //   638: dup
    //   639: aload 17
    //   641: invokestatic 353	java/lang/String:valueOf	(Ljava/lang/Object;)Ljava/lang/String;
    //   644: invokespecial 356	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   647: aload 20
    //   649: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   652: invokevirtual 371	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   655: invokevirtual 360	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   658: pop
    //   659: goto -528 -> 131
    //   662: astore 7
    //   664: aload 7
    //   666: astore 8
    //   668: aload 6
    //   670: astore 9
    //   672: ldc_w 502
    //   675: new 348	java/lang/StringBuilder
    //   678: dup
    //   679: ldc_w 516
    //   682: invokespecial 356	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   685: aload 8
    //   687: invokevirtual 519	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   690: invokevirtual 371	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   693: invokestatic 509	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   696: pop
    //   697: goto -272 -> 425
    //   700: astore_2
    //   701: aload_0
    //   702: monitorexit
    //   703: aload_2
    //   704: athrow
    //   705: aload 18
    //   707: ldc 84
    //   709: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   712: ifeq +12 -> 724
    //   715: aload_3
    //   716: invokevirtual 522	com/miaozhen/mzmonitor/MZDeviceInfo:getPackageName	()Ljava/lang/String;
    //   719: astore 19
    //   721: goto -215 -> 506
    //   724: aload 18
    //   726: ldc 96
    //   728: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   731: ifeq +12 -> 743
    //   734: aload_3
    //   735: invokevirtual 525	com/miaozhen/mzmonitor/MZDeviceInfo:getAndoirdID	()Ljava/lang/String;
    //   738: astore 19
    //   740: goto -234 -> 506
    //   743: aload 18
    //   745: ldc 124
    //   747: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   750: ifeq +44 -> 794
    //   753: aload_3
    //   754: invokevirtual 414	com/miaozhen/mzmonitor/MZDeviceInfo:getMacAddress	()Ljava/lang/String;
    //   757: astore 25
    //   759: aload 25
    //   761: ifnull -255 -> 506
    //   764: aload 25
    //   766: ldc_w 416
    //   769: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   772: ifne -266 -> 506
    //   775: aload 25
    //   777: ldc_w 527
    //   780: ldc_w 416
    //   783: invokevirtual 435	java/lang/String:replaceAll	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   786: invokevirtual 267	java/lang/String:toUpperCase	()Ljava/lang/String;
    //   789: astore 19
    //   791: goto -285 -> 506
    //   794: aload 18
    //   796: ldc 88
    //   798: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   801: ifeq +12 -> 813
    //   804: aload_3
    //   805: invokevirtual 530	com/miaozhen/mzmonitor/MZDeviceInfo:getIMEI	()Ljava/lang/String;
    //   808: astore 19
    //   810: goto -304 -> 506
    //   813: aload 18
    //   815: ldc 108
    //   817: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   820: ifeq +18 -> 838
    //   823: aload_3
    //   824: invokevirtual 533	com/miaozhen/mzmonitor/MZDeviceInfo:getWifiState	()Z
    //   827: ifeq +418 -> 1245
    //   830: ldc_w 535
    //   833: astore 19
    //   835: goto -329 -> 506
    //   838: aload 18
    //   840: ldc 100
    //   842: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   845: ifeq +12 -> 857
    //   848: aload_3
    //   849: invokevirtual 538	com/miaozhen/mzmonitor/MZDeviceInfo:getDeviceModel	()Ljava/lang/String;
    //   852: astore 19
    //   854: goto -348 -> 506
    //   857: aload 18
    //   859: ldc 92
    //   861: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   864: ifeq +61 -> 925
    //   867: aload_1
    //   868: invokevirtual 540	com/miaozhen/mzmonitor/a:f	()J
    //   871: lstore 23
    //   873: aload 11
    //   875: getfield 277	com/miaozhen/mzmonitor/f:j	Z
    //   878: ifeq +27 -> 905
    //   881: new 348	java/lang/StringBuilder
    //   884: dup
    //   885: lload 23
    //   887: ldc2_w 541
    //   890: ldiv
    //   891: invokestatic 401	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   894: invokespecial 356	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   897: invokevirtual 371	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   900: astore 19
    //   902: goto -396 -> 506
    //   905: new 348	java/lang/StringBuilder
    //   908: dup
    //   909: lload 23
    //   911: invokestatic 401	java/lang/String:valueOf	(J)Ljava/lang/String;
    //   914: invokespecial 356	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   917: invokevirtual 371	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   920: astore 19
    //   922: goto -416 -> 506
    //   925: aload 18
    //   927: ldc 104
    //   929: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   932: ifeq +30 -> 962
    //   935: aload_0
    //   936: getfield 164	com/miaozhen/mzmonitor/d:b	Landroid/content/Context;
    //   939: invokestatic 545	com/miaozhen/mzmonitor/MZSdkProfile:getLatestLocation_MMA	(Landroid/content/Context;)Ljava/lang/String;
    //   942: astore 22
    //   944: aload 22
    //   946: ldc_w 407
    //   949: invokevirtual 411	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   952: ifne -446 -> 506
    //   955: aload 22
    //   957: astore 19
    //   959: goto -453 -> 506
    //   962: aload 18
    //   964: ldc 112
    //   966: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   969: ifeq +12 -> 981
    //   972: aload_3
    //   973: invokevirtual 548	com/miaozhen/mzmonitor/MZDeviceInfo:getOpenUDID	()Ljava/lang/String;
    //   976: astore 19
    //   978: goto -472 -> 506
    //   981: aload 18
    //   983: ldc 116
    //   985: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   988: ifeq +11 -> 999
    //   991: ldc_w 422
    //   994: astore 19
    //   996: goto -490 -> 506
    //   999: aload 18
    //   1001: ldc 120
    //   1003: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1006: ifeq +12 -> 1018
    //   1009: aload_3
    //   1010: invokevirtual 551	com/miaozhen/mzmonitor/MZDeviceInfo:getOSVersion	()Ljava/lang/String;
    //   1013: astore 19
    //   1015: goto -509 -> 506
    //   1018: aload 18
    //   1020: ldc 128
    //   1022: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1025: ifeq +12 -> 1037
    //   1028: aload_3
    //   1029: invokevirtual 554	com/miaozhen/mzmonitor/MZDeviceInfo:getResolution	()Ljava/lang/String;
    //   1032: astore 19
    //   1034: goto -528 -> 506
    //   1037: aload 18
    //   1039: ldc 132
    //   1041: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1044: ifeq +12 -> 1056
    //   1047: aload_3
    //   1048: invokevirtual 557	com/miaozhen/mzmonitor/MZDeviceInfo:getODIN	()Ljava/lang/String;
    //   1051: astore 19
    //   1053: goto -547 -> 506
    //   1056: aload 18
    //   1058: ldc 140
    //   1060: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1063: ifeq +12 -> 1075
    //   1066: aload_1
    //   1067: invokevirtual 437	com/miaozhen/mzmonitor/a:b	()Ljava/lang/String;
    //   1070: astore 19
    //   1072: goto -566 -> 506
    //   1075: aload 18
    //   1077: ldc 136
    //   1079: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1082: ifeq +12 -> 1094
    //   1085: aload_1
    //   1086: invokevirtual 439	com/miaozhen/mzmonitor/a:c	()Ljava/lang/String;
    //   1089: astore 19
    //   1091: goto -585 -> 506
    //   1094: aload 18
    //   1096: ldc 144
    //   1098: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1101: ifeq +12 -> 1113
    //   1104: aload_1
    //   1105: invokevirtual 441	com/miaozhen/mzmonitor/a:i	()Ljava/lang/String;
    //   1108: astore 19
    //   1110: goto -604 -> 506
    //   1113: aload 18
    //   1115: ldc 152
    //   1117: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1120: ifeq +16 -> 1136
    //   1123: aload_3
    //   1124: aload_0
    //   1125: getfield 164	com/miaozhen/mzmonitor/d:b	Landroid/content/Context;
    //   1128: invokevirtual 560	com/miaozhen/mzmonitor/MZDeviceInfo:getCarrier	(Landroid/content/Context;)Ljava/lang/String;
    //   1131: astore 19
    //   1133: goto -627 -> 506
    //   1136: aload 18
    //   1138: ldc 156
    //   1140: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1143: ifeq +12 -> 1155
    //   1146: aload_3
    //   1147: invokevirtual 563	com/miaozhen/mzmonitor/MZDeviceInfo:getLocale	()Ljava/lang/String;
    //   1150: astore 19
    //   1152: goto -646 -> 506
    //   1155: aload 18
    //   1157: ldc 160
    //   1159: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1162: ifeq +16 -> 1178
    //   1165: aload_3
    //   1166: aload_0
    //   1167: getfield 164	com/miaozhen/mzmonitor/d:b	Landroid/content/Context;
    //   1170: invokevirtual 566	com/miaozhen/mzmonitor/MZDeviceInfo:getIP	(Landroid/content/Context;)Ljava/lang/String;
    //   1173: astore 19
    //   1175: goto -669 -> 506
    //   1178: aload 18
    //   1180: ldc 148
    //   1182: invokevirtual 272	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1185: ifeq -679 -> 506
    //   1188: aload 11
    //   1190: getfield 492	com/miaozhen/mzmonitor/f:f	Ljava/lang/String;
    //   1193: ifnonnull -1062 -> 131
    //   1196: aload 11
    //   1198: aload 16
    //   1200: getfield 296	com/miaozhen/mzmonitor/e:b	Ljava/lang/String;
    //   1203: putfield 492	com/miaozhen/mzmonitor/f:f	Ljava/lang/String;
    //   1206: goto -1075 -> 131
    //   1209: aload 31
    //   1211: getfield 318	com/miaozhen/mzmonitor/h:c	Ljava/lang/String;
    //   1214: astore 30
    //   1216: goto -980 -> 236
    //   1219: astore 34
    //   1221: aload 34
    //   1223: astore 8
    //   1225: aconst_null
    //   1226: astore 9
    //   1228: goto -556 -> 672
    //   1231: aload 13
    //   1233: astore 26
    //   1235: goto -946 -> 289
    //   1238: aload 6
    //   1240: astore 9
    //   1242: goto -817 -> 425
    //   1245: ldc_w 422
    //   1248: astore 19
    //   1250: goto -744 -> 506
    //
    // Exception table:
    //   from	to	target	type
    //   36	44	662	java/lang/Exception
    //   49	119	662	java/lang/Exception
    //   119	131	662	java/lang/Exception
    //   131	178	662	java/lang/Exception
    //   183	236	662	java/lang/Exception
    //   236	289	662	java/lang/Exception
    //   289	352	662	java/lang/Exception
    //   352	425	662	java/lang/Exception
    //   430	485	662	java/lang/Exception
    //   490	506	662	java/lang/Exception
    //   506	546	662	java/lang/Exception
    //   546	581	662	java/lang/Exception
    //   581	608	662	java/lang/Exception
    //   608	633	662	java/lang/Exception
    //   633	659	662	java/lang/Exception
    //   705	721	662	java/lang/Exception
    //   724	740	662	java/lang/Exception
    //   743	759	662	java/lang/Exception
    //   764	791	662	java/lang/Exception
    //   794	810	662	java/lang/Exception
    //   813	830	662	java/lang/Exception
    //   838	854	662	java/lang/Exception
    //   857	902	662	java/lang/Exception
    //   905	922	662	java/lang/Exception
    //   925	955	662	java/lang/Exception
    //   962	978	662	java/lang/Exception
    //   981	991	662	java/lang/Exception
    //   999	1015	662	java/lang/Exception
    //   1018	1034	662	java/lang/Exception
    //   1037	1053	662	java/lang/Exception
    //   1056	1072	662	java/lang/Exception
    //   1075	1091	662	java/lang/Exception
    //   1094	1110	662	java/lang/Exception
    //   1113	1133	662	java/lang/Exception
    //   1136	1152	662	java/lang/Exception
    //   1155	1175	662	java/lang/Exception
    //   1178	1206	662	java/lang/Exception
    //   1209	1216	662	java/lang/Exception
    //   2	25	700	finally
    //   25	36	700	finally
    //   36	44	700	finally
    //   49	119	700	finally
    //   119	131	700	finally
    //   131	178	700	finally
    //   183	236	700	finally
    //   236	289	700	finally
    //   289	352	700	finally
    //   352	425	700	finally
    //   430	485	700	finally
    //   490	506	700	finally
    //   506	546	700	finally
    //   546	581	700	finally
    //   581	608	700	finally
    //   608	633	700	finally
    //   633	659	700	finally
    //   672	697	700	finally
    //   705	721	700	finally
    //   724	740	700	finally
    //   743	759	700	finally
    //   764	791	700	finally
    //   794	810	700	finally
    //   813	830	700	finally
    //   838	854	700	finally
    //   857	902	700	finally
    //   905	922	700	finally
    //   925	955	700	finally
    //   962	978	700	finally
    //   981	991	700	finally
    //   999	1015	700	finally
    //   1018	1034	700	finally
    //   1037	1053	700	finally
    //   1056	1072	700	finally
    //   1075	1091	700	finally
    //   1094	1110	700	finally
    //   1113	1133	700	finally
    //   1136	1152	700	finally
    //   1155	1175	700	finally
    //   1178	1206	700	finally
    //   1209	1216	700	finally
    //   25	36	1219	java/lang/Exception
  }

  public void b()
  {
    try
    {
      this.c = a(new ByteArrayInputStream(MZSdkProfile.loadConfigFromLocal(this.b).getBytes("UTF-8")));
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      while (true)
        localUnsupportedEncodingException.printStackTrace();
    }
    finally
    {
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.miaozhen.mzmonitor.d
 * JD-Core Version:    0.6.2
 */