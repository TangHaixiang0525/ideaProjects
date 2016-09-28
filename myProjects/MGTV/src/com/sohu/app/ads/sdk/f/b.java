package com.sohu.app.ads.sdk.f;

import com.sohu.app.ads.sdk.model.AdsResponse;
import java.io.InputStream;
import java.util.ArrayList;

public class b
{
  private static b a;

  public static b a()
  {
    if (a == null)
      a = new b();
    return a;
  }

  // ERROR //
  private ArrayList<AdsResponse> a(InputStream paramInputStream)
  {
    // Byte code:
    //   0: invokestatic 31	org/xmlpull/v1/XmlPullParserFactory:newInstance	()Lorg/xmlpull/v1/XmlPullParserFactory;
    //   3: invokevirtual 35	org/xmlpull/v1/XmlPullParserFactory:newPullParser	()Lorg/xmlpull/v1/XmlPullParser;
    //   6: astore 14
    //   8: aload 14
    //   10: aload_1
    //   11: ldc 37
    //   13: invokeinterface 43 3 0
    //   18: aload 14
    //   20: invokeinterface 47 1 0
    //   25: istore 15
    //   27: aconst_null
    //   28: astore 16
    //   30: iload 15
    //   32: istore 17
    //   34: aconst_null
    //   35: astore 4
    //   37: iconst_1
    //   38: iload 17
    //   40: if_icmpeq +1304 -> 1344
    //   43: aload 14
    //   45: invokeinterface 51 1 0
    //   50: astore 19
    //   52: iload 17
    //   54: tableswitch	default:+30 -> 84, 0:+66->120, 1:+30->84, 2:+82->136, 3:+1262->1316
    //   85: iconst_1
    //   86: astore 21
    //   88: aload 16
    //   90: astore 22
    //   92: aload 14
    //   94: invokeinterface 54 1 0
    //   99: istore 26
    //   101: aload 22
    //   103: astore 27
    //   105: aload 21
    //   107: astore 4
    //   109: iload 26
    //   111: istore 17
    //   113: aload 27
    //   115: astore 16
    //   117: goto -80 -> 37
    //   120: new 56	java/util/ArrayList
    //   123: dup
    //   124: invokespecial 57	java/util/ArrayList:<init>	()V
    //   127: astore 21
    //   129: aload 16
    //   131: astore 22
    //   133: goto -41 -> 92
    //   136: ldc 59
    //   138: aload 19
    //   140: invokevirtual 65	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   143: ifeq +59 -> 202
    //   146: new 67	com/sohu/app/ads/sdk/model/AdsResponse
    //   149: dup
    //   150: invokespecial 68	com/sohu/app/ads/sdk/model/AdsResponse:<init>	()V
    //   153: astore 28
    //   155: aload 28
    //   157: aload 14
    //   159: aconst_null
    //   160: ldc 70
    //   162: invokeinterface 74 3 0
    //   167: invokestatic 80	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   170: invokevirtual 84	com/sohu/app/ads/sdk/model/AdsResponse:setAdSequence	(I)V
    //   173: aload 4
    //   175: astore 21
    //   177: aload 28
    //   179: astore 22
    //   181: goto -89 -> 92
    //   184: astore 29
    //   186: aload 29
    //   188: invokevirtual 87	java/lang/Exception:printStackTrace	()V
    //   191: aload 4
    //   193: astore 21
    //   195: aload 28
    //   197: astore 22
    //   199: goto -107 -> 92
    //   202: ldc 89
    //   204: aload 19
    //   206: invokevirtual 65	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   209: ifeq +26 -> 235
    //   212: aload 16
    //   214: aload 14
    //   216: invokeinterface 92 1 0
    //   221: invokevirtual 96	com/sohu/app/ads/sdk/model/AdsResponse:setAdSystem	(Ljava/lang/String;)V
    //   224: aload 4
    //   226: astore 21
    //   228: aload 16
    //   230: astore 22
    //   232: goto -140 -> 92
    //   235: ldc 98
    //   237: aload 19
    //   239: invokevirtual 65	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   242: ifeq +26 -> 268
    //   245: aload 16
    //   247: aload 14
    //   249: invokeinterface 92 1 0
    //   254: invokevirtual 101	com/sohu/app/ads/sdk/model/AdsResponse:setAdTitle	(Ljava/lang/String;)V
    //   257: aload 4
    //   259: astore 21
    //   261: aload 16
    //   263: astore 22
    //   265: goto -173 -> 92
    //   268: ldc 103
    //   270: aload 19
    //   272: invokevirtual 65	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   275: ifeq +37 -> 312
    //   278: aload 14
    //   280: invokeinterface 92 1 0
    //   285: invokevirtual 106	java/lang/String:trim	()Ljava/lang/String;
    //   288: astore 64
    //   290: aload 16
    //   292: invokevirtual 110	com/sohu/app/ads/sdk/model/AdsResponse:getImpression	()Ljava/util/ArrayList;
    //   295: aload 64
    //   297: invokevirtual 114	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   300: pop
    //   301: aload 4
    //   303: astore 21
    //   305: aload 16
    //   307: astore 22
    //   309: goto -217 -> 92
    //   312: ldc 116
    //   314: aload 19
    //   316: invokevirtual 65	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   319: ifeq +73 -> 392
    //   322: aload 14
    //   324: invokeinterface 92 1 0
    //   329: astore 62
    //   331: aload 16
    //   333: new 118	java/text/SimpleDateFormat
    //   336: dup
    //   337: ldc 120
    //   339: invokespecial 122	java/text/SimpleDateFormat:<init>	(Ljava/lang/String;)V
    //   342: aload 62
    //   344: invokevirtual 126	java/text/SimpleDateFormat:parse	(Ljava/lang/String;)Ljava/util/Date;
    //   347: invokevirtual 131	java/util/Date:getSeconds	()I
    //   350: invokevirtual 134	com/sohu/app/ads/sdk/model/AdsResponse:setDuration	(I)V
    //   353: aload 4
    //   355: astore 21
    //   357: aload 16
    //   359: astore 22
    //   361: goto -269 -> 92
    //   364: astore 63
    //   366: aload 16
    //   368: iconst_0
    //   369: invokevirtual 134	com/sohu/app/ads/sdk/model/AdsResponse:setDuration	(I)V
    //   372: aload 63
    //   374: invokevirtual 135	java/text/ParseException:printStackTrace	()V
    //   377: goto -24 -> 353
    //   380: astore_3
    //   381: aload_3
    //   382: invokevirtual 136	org/xmlpull/v1/XmlPullParserException:printStackTrace	()V
    //   385: aload_1
    //   386: invokevirtual 141	java/io/InputStream:close	()V
    //   389: aload 4
    //   391: areturn
    //   392: ldc 143
    //   394: aload 19
    //   396: invokevirtual 65	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   399: istore 30
    //   401: iload 30
    //   403: ifeq +382 -> 785
    //   406: aload 14
    //   408: aconst_null
    //   409: ldc 145
    //   411: invokeinterface 74 3 0
    //   416: astore 61
    //   418: aload 61
    //   420: astore 54
    //   422: aload 54
    //   424: ifnull +1028 -> 1452
    //   427: ldc 147
    //   429: aload 54
    //   431: invokevirtual 65	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   434: ifne +1018 -> 1452
    //   437: ldc 149
    //   439: aload 54
    //   441: invokevirtual 65	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   444: ifeq +31 -> 475
    //   447: aload 16
    //   449: aload 14
    //   451: invokeinterface 92 1 0
    //   456: invokevirtual 152	com/sohu/app/ads/sdk/model/AdsResponse:setCreativeView	(Ljava/lang/String;)V
    //   459: goto +993 -> 1452
    //   462: astore 53
    //   464: aload 53
    //   466: invokevirtual 87	java/lang/Exception:printStackTrace	()V
    //   469: aconst_null
    //   470: astore 54
    //   472: goto -50 -> 422
    //   475: ldc 154
    //   477: aload 54
    //   479: invokevirtual 65	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   482: ifeq +42 -> 524
    //   485: aload 16
    //   487: aload 14
    //   489: invokeinterface 92 1 0
    //   494: invokevirtual 157	com/sohu/app/ads/sdk/model/AdsResponse:setStart	(Ljava/lang/String;)V
    //   497: goto +955 -> 1452
    //   500: astore 9
    //   502: aload 9
    //   504: invokevirtual 158	java/io/IOException:printStackTrace	()V
    //   507: aload_1
    //   508: invokevirtual 141	java/io/InputStream:close	()V
    //   511: aload 4
    //   513: areturn
    //   514: astore 10
    //   516: aload 10
    //   518: invokevirtual 158	java/io/IOException:printStackTrace	()V
    //   521: aload 4
    //   523: areturn
    //   524: ldc 160
    //   526: aload 54
    //   528: invokevirtual 65	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   531: ifeq +42 -> 573
    //   534: aload 16
    //   536: aload 14
    //   538: invokeinterface 92 1 0
    //   543: invokevirtual 163	com/sohu/app/ads/sdk/model/AdsResponse:setMidpoint	(Ljava/lang/String;)V
    //   546: goto +906 -> 1452
    //   549: astore 12
    //   551: aload 12
    //   553: invokevirtual 87	java/lang/Exception:printStackTrace	()V
    //   556: aload_1
    //   557: invokevirtual 141	java/io/InputStream:close	()V
    //   560: aload 4
    //   562: areturn
    //   563: astore 13
    //   565: aload 13
    //   567: invokevirtual 158	java/io/IOException:printStackTrace	()V
    //   570: aload 4
    //   572: areturn
    //   573: ldc 165
    //   575: aload 54
    //   577: invokevirtual 65	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   580: ifeq +27 -> 607
    //   583: aload 16
    //   585: aload 14
    //   587: invokeinterface 92 1 0
    //   592: invokevirtual 168	com/sohu/app/ads/sdk/model/AdsResponse:setFirstQuartile	(Ljava/lang/String;)V
    //   595: goto +857 -> 1452
    //   598: astore 5
    //   600: aload_1
    //   601: invokevirtual 141	java/io/InputStream:close	()V
    //   604: aload 5
    //   606: athrow
    //   607: ldc 170
    //   609: aload 54
    //   611: invokevirtual 65	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   614: ifeq +18 -> 632
    //   617: aload 16
    //   619: aload 14
    //   621: invokeinterface 92 1 0
    //   626: invokevirtual 173	com/sohu/app/ads/sdk/model/AdsResponse:setThirdQuartile	(Ljava/lang/String;)V
    //   629: goto +823 -> 1452
    //   632: ldc 175
    //   634: aload 54
    //   636: invokevirtual 65	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   639: ifeq +18 -> 657
    //   642: aload 16
    //   644: aload 14
    //   646: invokeinterface 92 1 0
    //   651: invokevirtual 178	com/sohu/app/ads/sdk/model/AdsResponse:setComplete	(Ljava/lang/String;)V
    //   654: goto +798 -> 1452
    //   657: ldc 180
    //   659: aload 54
    //   661: invokevirtual 65	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   664: ifeq +788 -> 1452
    //   667: new 182	com/sohu/app/ads/sdk/model/d
    //   670: dup
    //   671: invokespecial 183	com/sohu/app/ads/sdk/model/d:<init>	()V
    //   674: astore 55
    //   676: aload 14
    //   678: aconst_null
    //   679: ldc 185
    //   681: invokeinterface 74 3 0
    //   686: astore 56
    //   688: aload 14
    //   690: aconst_null
    //   691: ldc 187
    //   693: invokeinterface 74 3 0
    //   698: astore 57
    //   700: aload 14
    //   702: invokeinterface 92 1 0
    //   707: astore 58
    //   709: aload 56
    //   711: invokestatic 191	com/sohu/app/ads/sdk/f/d:a	(Ljava/lang/String;)Z
    //   714: ifeq +25 -> 739
    //   717: aload 55
    //   719: new 118	java/text/SimpleDateFormat
    //   722: dup
    //   723: ldc 120
    //   725: invokespecial 122	java/text/SimpleDateFormat:<init>	(Ljava/lang/String;)V
    //   728: aload 56
    //   730: invokevirtual 126	java/text/SimpleDateFormat:parse	(Ljava/lang/String;)Ljava/util/Date;
    //   733: invokevirtual 131	java/util/Date:getSeconds	()I
    //   736: invokevirtual 193	com/sohu/app/ads/sdk/model/d:a	(I)V
    //   739: aload 57
    //   741: invokestatic 191	com/sohu/app/ads/sdk/f/d:a	(Ljava/lang/String;)Z
    //   744: ifeq +10 -> 754
    //   747: aload 55
    //   749: aload 57
    //   751: invokevirtual 195	com/sohu/app/ads/sdk/model/d:a	(Ljava/lang/String;)V
    //   754: aload 55
    //   756: aload 58
    //   758: invokevirtual 198	com/sohu/app/ads/sdk/model/d:b	(Ljava/lang/String;)V
    //   761: aload 16
    //   763: invokevirtual 201	com/sohu/app/ads/sdk/model/AdsResponse:getSdkTracking	()Ljava/util/ArrayList;
    //   766: aload 55
    //   768: invokevirtual 114	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   771: pop
    //   772: goto +680 -> 1452
    //   775: astore 59
    //   777: aload 59
    //   779: invokevirtual 135	java/text/ParseException:printStackTrace	()V
    //   782: goto +670 -> 1452
    //   785: ldc 203
    //   787: aload 19
    //   789: invokevirtual 65	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   792: ifeq +26 -> 818
    //   795: aload 16
    //   797: aload 14
    //   799: invokeinterface 92 1 0
    //   804: invokevirtual 206	com/sohu/app/ads/sdk/model/AdsResponse:setClickThrough	(Ljava/lang/String;)V
    //   807: aload 4
    //   809: astore 21
    //   811: aload 16
    //   813: astore 22
    //   815: goto -723 -> 92
    //   818: ldc 208
    //   820: aload 19
    //   822: invokevirtual 65	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   825: ifeq +80 -> 905
    //   828: aload 14
    //   830: aconst_null
    //   831: ldc 187
    //   833: invokeinterface 74 3 0
    //   838: astore 50
    //   840: aload 50
    //   842: invokestatic 191	com/sohu/app/ads/sdk/f/d:a	(Ljava/lang/String;)Z
    //   845: ifeq +45 -> 890
    //   848: new 210	com/sohu/app/ads/sdk/model/c
    //   851: dup
    //   852: invokespecial 211	com/sohu/app/ads/sdk/model/c:<init>	()V
    //   855: astore 51
    //   857: aload 51
    //   859: aload 50
    //   861: invokevirtual 212	com/sohu/app/ads/sdk/model/c:a	(Ljava/lang/String;)V
    //   864: aload 51
    //   866: aload 14
    //   868: invokeinterface 92 1 0
    //   873: invokevirtual 213	com/sohu/app/ads/sdk/model/c:b	(Ljava/lang/String;)V
    //   876: aload 16
    //   878: invokevirtual 216	com/sohu/app/ads/sdk/model/AdsResponse:getSdkClickTracking	()Ljava/util/ArrayList;
    //   881: aload 51
    //   883: invokevirtual 114	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   886: pop
    //   887: goto +576 -> 1463
    //   890: aload 16
    //   892: aload 14
    //   894: invokeinterface 92 1 0
    //   899: invokevirtual 219	com/sohu/app/ads/sdk/model/AdsResponse:setClickTracking	(Ljava/lang/String;)V
    //   902: goto +561 -> 1463
    //   905: ldc 221
    //   907: aload 19
    //   909: invokevirtual 65	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   912: ifeq +26 -> 938
    //   915: aload 16
    //   917: aload 14
    //   919: invokeinterface 92 1 0
    //   924: invokevirtual 224	com/sohu/app/ads/sdk/model/AdsResponse:setMediaFile	(Ljava/lang/String;)V
    //   927: aload 4
    //   929: astore 21
    //   931: aload 16
    //   933: astore 22
    //   935: goto -843 -> 92
    //   938: ldc 226
    //   940: aload 19
    //   942: invokevirtual 65	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   945: ifeq +45 -> 990
    //   948: aload 14
    //   950: invokeinterface 92 1 0
    //   955: astore 49
    //   957: aload 49
    //   959: invokestatic 191	com/sohu/app/ads/sdk/f/d:a	(Ljava/lang/String;)Z
    //   962: ifeq +19 -> 981
    //   965: aload 16
    //   967: aload 49
    //   969: invokevirtual 229	com/sohu/app/ads/sdk/model/AdsResponse:setDisplayKeyword	(Ljava/lang/String;)V
    //   972: aload 16
    //   974: iconst_1
    //   975: invokevirtual 233	com/sohu/app/ads/sdk/model/AdsResponse:setVoiceAd	(Z)V
    //   978: goto +496 -> 1474
    //   981: aload 16
    //   983: iconst_0
    //   984: invokevirtual 233	com/sohu/app/ads/sdk/model/AdsResponse:setVoiceAd	(Z)V
    //   987: goto +487 -> 1474
    //   990: ldc 235
    //   992: aload 19
    //   994: invokevirtual 65	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   997: istore 31
    //   999: iload 31
    //   1001: ifeq +44 -> 1045
    //   1004: aload 16
    //   1006: aload 14
    //   1008: invokeinterface 92 1 0
    //   1013: invokevirtual 238	com/sohu/app/ads/sdk/model/AdsResponse:setSuccessKeyword	(Ljava/lang/String;)V
    //   1016: aload 4
    //   1018: astore 21
    //   1020: aload 16
    //   1022: astore 22
    //   1024: goto -932 -> 92
    //   1027: astore 48
    //   1029: aload 48
    //   1031: invokevirtual 87	java/lang/Exception:printStackTrace	()V
    //   1034: aload 4
    //   1036: astore 21
    //   1038: aload 16
    //   1040: astore 22
    //   1042: goto -950 -> 92
    //   1045: ldc 240
    //   1047: aload 19
    //   1049: invokevirtual 65	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   1052: istore 32
    //   1054: iload 32
    //   1056: ifeq +53 -> 1109
    //   1059: aload 14
    //   1061: invokeinterface 92 1 0
    //   1066: invokestatic 244	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   1069: invokevirtual 247	java/lang/Integer:intValue	()I
    //   1072: istore 47
    //   1074: iload 47
    //   1076: istore 46
    //   1078: aload 16
    //   1080: iload 46
    //   1082: invokevirtual 250	com/sohu/app/ads/sdk/model/AdsResponse:setSkipSeconds	(I)V
    //   1085: aload 4
    //   1087: astore 21
    //   1089: aload 16
    //   1091: astore 22
    //   1093: goto -1001 -> 92
    //   1096: astore 45
    //   1098: aload 45
    //   1100: invokevirtual 251	java/lang/NumberFormatException:printStackTrace	()V
    //   1103: iconst_5
    //   1104: istore 46
    //   1106: goto -28 -> 1078
    //   1109: ldc 253
    //   1111: aload 19
    //   1113: invokevirtual 65	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   1116: istore 33
    //   1118: iload 33
    //   1120: ifeq +53 -> 1173
    //   1123: aload 14
    //   1125: invokeinterface 92 1 0
    //   1130: invokestatic 244	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   1133: invokevirtual 247	java/lang/Integer:intValue	()I
    //   1136: istore 44
    //   1138: iload 44
    //   1140: istore 43
    //   1142: aload 16
    //   1144: iload 43
    //   1146: invokevirtual 256	com/sohu/app/ads/sdk/model/AdsResponse:setStartSkipSeconds	(I)V
    //   1149: aload 4
    //   1151: astore 21
    //   1153: aload 16
    //   1155: astore 22
    //   1157: goto -1065 -> 92
    //   1160: astore 42
    //   1162: aload 42
    //   1164: invokevirtual 251	java/lang/NumberFormatException:printStackTrace	()V
    //   1167: iconst_0
    //   1168: istore 43
    //   1170: goto -28 -> 1142
    //   1173: ldc_w 258
    //   1176: aload 19
    //   1178: invokevirtual 65	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   1181: istore 34
    //   1183: iload 34
    //   1185: ifeq +53 -> 1238
    //   1188: aload 14
    //   1190: invokeinterface 92 1 0
    //   1195: invokestatic 244	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   1198: invokevirtual 247	java/lang/Integer:intValue	()I
    //   1201: istore 41
    //   1203: iload 41
    //   1205: istore 40
    //   1207: aload 16
    //   1209: iload 40
    //   1211: invokevirtual 261	com/sohu/app/ads/sdk/model/AdsResponse:setLanguage	(I)V
    //   1214: aload 4
    //   1216: astore 21
    //   1218: aload 16
    //   1220: astore 22
    //   1222: goto -1130 -> 92
    //   1225: astore 39
    //   1227: aload 39
    //   1229: invokevirtual 251	java/lang/NumberFormatException:printStackTrace	()V
    //   1232: iconst_1
    //   1233: istore 40
    //   1235: goto -28 -> 1207
    //   1238: ldc_w 263
    //   1241: aload 19
    //   1243: invokevirtual 65	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   1246: ifeq -1162 -> 84
    //   1249: new 210	com/sohu/app/ads/sdk/model/c
    //   1252: dup
    //   1253: invokespecial 211	com/sohu/app/ads/sdk/model/c:<init>	()V
    //   1256: astore 35
    //   1258: aload 14
    //   1260: aconst_null
    //   1261: ldc_w 265
    //   1264: invokeinterface 74 3 0
    //   1269: astore 36
    //   1271: aload 14
    //   1273: invokeinterface 92 1 0
    //   1278: astore 37
    //   1280: aload 35
    //   1282: aload 36
    //   1284: invokevirtual 212	com/sohu/app/ads/sdk/model/c:a	(Ljava/lang/String;)V
    //   1287: aload 35
    //   1289: aload 37
    //   1291: invokevirtual 213	com/sohu/app/ads/sdk/model/c:b	(Ljava/lang/String;)V
    //   1294: aload 16
    //   1296: invokevirtual 268	com/sohu/app/ads/sdk/model/AdsResponse:getVoiceExposes	()Ljava/util/ArrayList;
    //   1299: aload 35
    //   1301: invokevirtual 114	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   1304: pop
    //   1305: aload 4
    //   1307: astore 21
    //   1309: aload 16
    //   1311: astore 22
    //   1313: goto -1221 -> 92
    //   1316: ldc 59
    //   1318: aload 19
    //   1320: invokevirtual 65	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   1323: ifeq -1239 -> 84
    //   1326: aload 4
    //   1328: aload 16
    //   1330: invokevirtual 114	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   1333: pop
    //   1334: aload 4
    //   1336: astore 21
    //   1338: aconst_null
    //   1339: astore 22
    //   1341: goto -1249 -> 92
    //   1344: aload_1
    //   1345: invokevirtual 141	java/io/InputStream:close	()V
    //   1348: aload 4
    //   1350: areturn
    //   1351: astore 18
    //   1353: aload 18
    //   1355: invokevirtual 158	java/io/IOException:printStackTrace	()V
    //   1358: aload 4
    //   1360: areturn
    //   1361: astore 7
    //   1363: aload 7
    //   1365: invokevirtual 158	java/io/IOException:printStackTrace	()V
    //   1368: aload 4
    //   1370: areturn
    //   1371: astore 6
    //   1373: aload 6
    //   1375: invokevirtual 158	java/io/IOException:printStackTrace	()V
    //   1378: goto -774 -> 604
    //   1381: astore 11
    //   1383: aload 11
    //   1385: astore 12
    //   1387: aconst_null
    //   1388: astore 4
    //   1390: goto -839 -> 551
    //   1393: astore 25
    //   1395: aload 21
    //   1397: astore 4
    //   1399: aload 25
    //   1401: astore 12
    //   1403: goto -852 -> 551
    //   1406: astore 8
    //   1408: aload 8
    //   1410: astore 9
    //   1412: aconst_null
    //   1413: astore 4
    //   1415: goto -913 -> 502
    //   1418: astore 24
    //   1420: aload 21
    //   1422: astore 4
    //   1424: aload 24
    //   1426: astore 9
    //   1428: goto -926 -> 502
    //   1431: astore_2
    //   1432: aload_2
    //   1433: astore_3
    //   1434: aconst_null
    //   1435: astore 4
    //   1437: goto -1056 -> 381
    //   1440: astore 23
    //   1442: aload 21
    //   1444: astore 4
    //   1446: aload 23
    //   1448: astore_3
    //   1449: goto -1068 -> 381
    //   1452: aload 4
    //   1454: astore 21
    //   1456: aload 16
    //   1458: astore 22
    //   1460: goto -1368 -> 92
    //   1463: aload 4
    //   1465: astore 21
    //   1467: aload 16
    //   1469: astore 22
    //   1471: goto -1379 -> 92
    //   1474: aload 4
    //   1476: astore 21
    //   1478: aload 16
    //   1480: astore 22
    //   1482: goto -1390 -> 92
    //
    // Exception table:
    //   from	to	target	type
    //   155	173	184	java/lang/Exception
    //   331	353	364	java/text/ParseException
    //   43	52	380	org/xmlpull/v1/XmlPullParserException
    //   120	129	380	org/xmlpull/v1/XmlPullParserException
    //   136	155	380	org/xmlpull/v1/XmlPullParserException
    //   155	173	380	org/xmlpull/v1/XmlPullParserException
    //   186	191	380	org/xmlpull/v1/XmlPullParserException
    //   202	224	380	org/xmlpull/v1/XmlPullParserException
    //   235	257	380	org/xmlpull/v1/XmlPullParserException
    //   268	301	380	org/xmlpull/v1/XmlPullParserException
    //   312	331	380	org/xmlpull/v1/XmlPullParserException
    //   331	353	380	org/xmlpull/v1/XmlPullParserException
    //   366	377	380	org/xmlpull/v1/XmlPullParserException
    //   392	401	380	org/xmlpull/v1/XmlPullParserException
    //   406	418	380	org/xmlpull/v1/XmlPullParserException
    //   427	459	380	org/xmlpull/v1/XmlPullParserException
    //   464	469	380	org/xmlpull/v1/XmlPullParserException
    //   475	497	380	org/xmlpull/v1/XmlPullParserException
    //   524	546	380	org/xmlpull/v1/XmlPullParserException
    //   573	595	380	org/xmlpull/v1/XmlPullParserException
    //   607	629	380	org/xmlpull/v1/XmlPullParserException
    //   632	654	380	org/xmlpull/v1/XmlPullParserException
    //   657	709	380	org/xmlpull/v1/XmlPullParserException
    //   709	739	380	org/xmlpull/v1/XmlPullParserException
    //   739	754	380	org/xmlpull/v1/XmlPullParserException
    //   754	772	380	org/xmlpull/v1/XmlPullParserException
    //   777	782	380	org/xmlpull/v1/XmlPullParserException
    //   785	807	380	org/xmlpull/v1/XmlPullParserException
    //   818	887	380	org/xmlpull/v1/XmlPullParserException
    //   890	902	380	org/xmlpull/v1/XmlPullParserException
    //   905	927	380	org/xmlpull/v1/XmlPullParserException
    //   938	978	380	org/xmlpull/v1/XmlPullParserException
    //   981	987	380	org/xmlpull/v1/XmlPullParserException
    //   990	999	380	org/xmlpull/v1/XmlPullParserException
    //   1004	1016	380	org/xmlpull/v1/XmlPullParserException
    //   1029	1034	380	org/xmlpull/v1/XmlPullParserException
    //   1045	1054	380	org/xmlpull/v1/XmlPullParserException
    //   1059	1074	380	org/xmlpull/v1/XmlPullParserException
    //   1078	1085	380	org/xmlpull/v1/XmlPullParserException
    //   1098	1103	380	org/xmlpull/v1/XmlPullParserException
    //   1109	1118	380	org/xmlpull/v1/XmlPullParserException
    //   1123	1138	380	org/xmlpull/v1/XmlPullParserException
    //   1142	1149	380	org/xmlpull/v1/XmlPullParserException
    //   1162	1167	380	org/xmlpull/v1/XmlPullParserException
    //   1173	1183	380	org/xmlpull/v1/XmlPullParserException
    //   1188	1203	380	org/xmlpull/v1/XmlPullParserException
    //   1207	1214	380	org/xmlpull/v1/XmlPullParserException
    //   1227	1232	380	org/xmlpull/v1/XmlPullParserException
    //   1238	1305	380	org/xmlpull/v1/XmlPullParserException
    //   1316	1334	380	org/xmlpull/v1/XmlPullParserException
    //   406	418	462	java/lang/Exception
    //   43	52	500	java/io/IOException
    //   120	129	500	java/io/IOException
    //   136	155	500	java/io/IOException
    //   155	173	500	java/io/IOException
    //   186	191	500	java/io/IOException
    //   202	224	500	java/io/IOException
    //   235	257	500	java/io/IOException
    //   268	301	500	java/io/IOException
    //   312	331	500	java/io/IOException
    //   331	353	500	java/io/IOException
    //   366	377	500	java/io/IOException
    //   392	401	500	java/io/IOException
    //   406	418	500	java/io/IOException
    //   427	459	500	java/io/IOException
    //   464	469	500	java/io/IOException
    //   475	497	500	java/io/IOException
    //   524	546	500	java/io/IOException
    //   573	595	500	java/io/IOException
    //   607	629	500	java/io/IOException
    //   632	654	500	java/io/IOException
    //   657	709	500	java/io/IOException
    //   709	739	500	java/io/IOException
    //   739	754	500	java/io/IOException
    //   754	772	500	java/io/IOException
    //   777	782	500	java/io/IOException
    //   785	807	500	java/io/IOException
    //   818	887	500	java/io/IOException
    //   890	902	500	java/io/IOException
    //   905	927	500	java/io/IOException
    //   938	978	500	java/io/IOException
    //   981	987	500	java/io/IOException
    //   990	999	500	java/io/IOException
    //   1004	1016	500	java/io/IOException
    //   1029	1034	500	java/io/IOException
    //   1045	1054	500	java/io/IOException
    //   1059	1074	500	java/io/IOException
    //   1078	1085	500	java/io/IOException
    //   1098	1103	500	java/io/IOException
    //   1109	1118	500	java/io/IOException
    //   1123	1138	500	java/io/IOException
    //   1142	1149	500	java/io/IOException
    //   1162	1167	500	java/io/IOException
    //   1173	1183	500	java/io/IOException
    //   1188	1203	500	java/io/IOException
    //   1207	1214	500	java/io/IOException
    //   1227	1232	500	java/io/IOException
    //   1238	1305	500	java/io/IOException
    //   1316	1334	500	java/io/IOException
    //   507	511	514	java/io/IOException
    //   43	52	549	java/lang/Exception
    //   120	129	549	java/lang/Exception
    //   136	155	549	java/lang/Exception
    //   186	191	549	java/lang/Exception
    //   202	224	549	java/lang/Exception
    //   235	257	549	java/lang/Exception
    //   268	301	549	java/lang/Exception
    //   312	331	549	java/lang/Exception
    //   331	353	549	java/lang/Exception
    //   366	377	549	java/lang/Exception
    //   392	401	549	java/lang/Exception
    //   427	459	549	java/lang/Exception
    //   464	469	549	java/lang/Exception
    //   475	497	549	java/lang/Exception
    //   524	546	549	java/lang/Exception
    //   573	595	549	java/lang/Exception
    //   607	629	549	java/lang/Exception
    //   632	654	549	java/lang/Exception
    //   657	709	549	java/lang/Exception
    //   709	739	549	java/lang/Exception
    //   739	754	549	java/lang/Exception
    //   754	772	549	java/lang/Exception
    //   777	782	549	java/lang/Exception
    //   785	807	549	java/lang/Exception
    //   818	887	549	java/lang/Exception
    //   890	902	549	java/lang/Exception
    //   905	927	549	java/lang/Exception
    //   938	978	549	java/lang/Exception
    //   981	987	549	java/lang/Exception
    //   990	999	549	java/lang/Exception
    //   1029	1034	549	java/lang/Exception
    //   1045	1054	549	java/lang/Exception
    //   1059	1074	549	java/lang/Exception
    //   1078	1085	549	java/lang/Exception
    //   1098	1103	549	java/lang/Exception
    //   1109	1118	549	java/lang/Exception
    //   1123	1138	549	java/lang/Exception
    //   1142	1149	549	java/lang/Exception
    //   1162	1167	549	java/lang/Exception
    //   1173	1183	549	java/lang/Exception
    //   1188	1203	549	java/lang/Exception
    //   1207	1214	549	java/lang/Exception
    //   1227	1232	549	java/lang/Exception
    //   1238	1305	549	java/lang/Exception
    //   1316	1334	549	java/lang/Exception
    //   556	560	563	java/io/IOException
    //   0	27	598	finally
    //   43	52	598	finally
    //   92	101	598	finally
    //   120	129	598	finally
    //   136	155	598	finally
    //   155	173	598	finally
    //   186	191	598	finally
    //   202	224	598	finally
    //   235	257	598	finally
    //   268	301	598	finally
    //   312	331	598	finally
    //   331	353	598	finally
    //   366	377	598	finally
    //   381	385	598	finally
    //   392	401	598	finally
    //   406	418	598	finally
    //   427	459	598	finally
    //   464	469	598	finally
    //   475	497	598	finally
    //   502	507	598	finally
    //   524	546	598	finally
    //   551	556	598	finally
    //   573	595	598	finally
    //   607	629	598	finally
    //   632	654	598	finally
    //   657	709	598	finally
    //   709	739	598	finally
    //   739	754	598	finally
    //   754	772	598	finally
    //   777	782	598	finally
    //   785	807	598	finally
    //   818	887	598	finally
    //   890	902	598	finally
    //   905	927	598	finally
    //   938	978	598	finally
    //   981	987	598	finally
    //   990	999	598	finally
    //   1004	1016	598	finally
    //   1029	1034	598	finally
    //   1045	1054	598	finally
    //   1059	1074	598	finally
    //   1078	1085	598	finally
    //   1098	1103	598	finally
    //   1109	1118	598	finally
    //   1123	1138	598	finally
    //   1142	1149	598	finally
    //   1162	1167	598	finally
    //   1173	1183	598	finally
    //   1188	1203	598	finally
    //   1207	1214	598	finally
    //   1227	1232	598	finally
    //   1238	1305	598	finally
    //   1316	1334	598	finally
    //   709	739	775	java/text/ParseException
    //   739	754	775	java/text/ParseException
    //   754	772	775	java/text/ParseException
    //   1004	1016	1027	java/lang/Exception
    //   1059	1074	1096	java/lang/NumberFormatException
    //   1123	1138	1160	java/lang/NumberFormatException
    //   1188	1203	1225	java/lang/NumberFormatException
    //   1344	1348	1351	java/io/IOException
    //   385	389	1361	java/io/IOException
    //   600	604	1371	java/io/IOException
    //   0	27	1381	java/lang/Exception
    //   92	101	1393	java/lang/Exception
    //   0	27	1406	java/io/IOException
    //   92	101	1418	java/io/IOException
    //   0	27	1431	org/xmlpull/v1/XmlPullParserException
    //   92	101	1440	org/xmlpull/v1/XmlPullParserException
  }

  // ERROR //
  private com.sohu.app.ads.sdk.model.a b(InputStream paramInputStream)
  {
    // Byte code:
    //   0: invokestatic 31	org/xmlpull/v1/XmlPullParserFactory:newInstance	()Lorg/xmlpull/v1/XmlPullParserFactory;
    //   3: invokevirtual 35	org/xmlpull/v1/XmlPullParserFactory:newPullParser	()Lorg/xmlpull/v1/XmlPullParser;
    //   6: astore 11
    //   8: aload 11
    //   10: aload_1
    //   11: ldc 37
    //   13: invokeinterface 43 3 0
    //   18: aload 11
    //   20: invokeinterface 47 1 0
    //   25: istore 12
    //   27: aconst_null
    //   28: astore 4
    //   30: iload 12
    //   32: istore 13
    //   34: iconst_1
    //   35: iload 13
    //   37: if_icmpeq +351 -> 388
    //   40: aload 11
    //   42: invokeinterface 51 1 0
    //   47: astore 15
    //   49: iload 13
    //   51: tableswitch	default:+25 -> 76, 0:+49->100, 1:+25->76, 2:+56->107
    //   77: iconst_1
    //   78: astore 16
    //   80: aload 11
    //   82: invokeinterface 54 1 0
    //   87: istore 19
    //   89: aload 16
    //   91: astore 4
    //   93: iload 19
    //   95: istore 13
    //   97: goto -63 -> 34
    //   100: aload 4
    //   102: astore 16
    //   104: goto -24 -> 80
    //   107: ldc 59
    //   109: aload 15
    //   111: invokevirtual 65	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   114: ifeq +27 -> 141
    //   117: new 271	com/sohu/app/ads/sdk/model/a
    //   120: dup
    //   121: invokespecial 272	com/sohu/app/ads/sdk/model/a:<init>	()V
    //   124: astore 16
    //   126: goto -46 -> 80
    //   129: astore_3
    //   130: aload_3
    //   131: invokevirtual 136	org/xmlpull/v1/XmlPullParserException:printStackTrace	()V
    //   134: aload_1
    //   135: invokevirtual 141	java/io/InputStream:close	()V
    //   138: aload 4
    //   140: areturn
    //   141: ldc 103
    //   143: aload 15
    //   145: invokevirtual 65	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   148: ifeq +29 -> 177
    //   151: aload 4
    //   153: invokevirtual 274	com/sohu/app/ads/sdk/model/a:a	()Ljava/util/ArrayList;
    //   156: aload 11
    //   158: invokeinterface 92 1 0
    //   163: invokevirtual 106	java/lang/String:trim	()Ljava/lang/String;
    //   166: invokevirtual 114	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   169: pop
    //   170: aload 4
    //   172: astore 16
    //   174: goto -94 -> 80
    //   177: ldc_w 276
    //   180: aload 15
    //   182: invokevirtual 65	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   185: ifeq +22 -> 207
    //   188: aload 4
    //   190: aload 11
    //   192: invokeinterface 92 1 0
    //   197: invokevirtual 277	com/sohu/app/ads/sdk/model/a:a	(Ljava/lang/String;)V
    //   200: aload 4
    //   202: astore 16
    //   204: goto -124 -> 80
    //   207: ldc_w 279
    //   210: aload 15
    //   212: invokevirtual 65	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   215: ifeq +61 -> 276
    //   218: aload 11
    //   220: aconst_null
    //   221: ldc 187
    //   223: invokeinterface 74 3 0
    //   228: astore 24
    //   230: new 182	com/sohu/app/ads/sdk/model/d
    //   233: dup
    //   234: invokespecial 183	com/sohu/app/ads/sdk/model/d:<init>	()V
    //   237: astore 25
    //   239: aload 25
    //   241: aload 24
    //   243: invokevirtual 195	com/sohu/app/ads/sdk/model/d:a	(Ljava/lang/String;)V
    //   246: aload 25
    //   248: aload 11
    //   250: invokeinterface 92 1 0
    //   255: invokevirtual 198	com/sohu/app/ads/sdk/model/d:b	(Ljava/lang/String;)V
    //   258: aload 4
    //   260: invokevirtual 282	com/sohu/app/ads/sdk/model/a:c	()Ljava/util/ArrayList;
    //   263: aload 25
    //   265: invokevirtual 114	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   268: pop
    //   269: aload 4
    //   271: astore 16
    //   273: goto -193 -> 80
    //   276: ldc_w 284
    //   279: aload 15
    //   281: invokevirtual 65	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   284: ifeq +22 -> 306
    //   287: aload 4
    //   289: aload 11
    //   291: invokeinterface 92 1 0
    //   296: invokevirtual 285	com/sohu/app/ads/sdk/model/a:b	(Ljava/lang/String;)V
    //   299: aload 4
    //   301: astore 16
    //   303: goto -223 -> 80
    //   306: ldc 143
    //   308: aload 15
    //   310: invokevirtual 65	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   313: istore 20
    //   315: iload 20
    //   317: ifeq -241 -> 76
    //   320: new 182	com/sohu/app/ads/sdk/model/d
    //   323: dup
    //   324: invokespecial 183	com/sohu/app/ads/sdk/model/d:<init>	()V
    //   327: astore 21
    //   329: aload 21
    //   331: aload 11
    //   333: aconst_null
    //   334: ldc 187
    //   336: invokeinterface 74 3 0
    //   341: invokevirtual 195	com/sohu/app/ads/sdk/model/d:a	(Ljava/lang/String;)V
    //   344: aload 21
    //   346: aload 11
    //   348: invokeinterface 92 1 0
    //   353: invokevirtual 198	com/sohu/app/ads/sdk/model/d:b	(Ljava/lang/String;)V
    //   356: aload 4
    //   358: invokevirtual 287	com/sohu/app/ads/sdk/model/a:b	()Ljava/util/ArrayList;
    //   361: aload 21
    //   363: invokevirtual 114	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   366: pop
    //   367: aload 4
    //   369: astore 16
    //   371: goto -291 -> 80
    //   374: astore 22
    //   376: aload 22
    //   378: invokevirtual 87	java/lang/Exception:printStackTrace	()V
    //   381: aload 4
    //   383: astore 16
    //   385: goto -305 -> 80
    //   388: aload_1
    //   389: invokevirtual 141	java/io/InputStream:close	()V
    //   392: aload 4
    //   394: areturn
    //   395: astore 14
    //   397: aload 14
    //   399: invokevirtual 158	java/io/IOException:printStackTrace	()V
    //   402: aload 4
    //   404: areturn
    //   405: astore 7
    //   407: aload 7
    //   409: invokevirtual 158	java/io/IOException:printStackTrace	()V
    //   412: aload 4
    //   414: areturn
    //   415: astore 8
    //   417: aconst_null
    //   418: astore 4
    //   420: aload 8
    //   422: astore 9
    //   424: aload 9
    //   426: invokevirtual 158	java/io/IOException:printStackTrace	()V
    //   429: aload_1
    //   430: invokevirtual 141	java/io/InputStream:close	()V
    //   433: aload 4
    //   435: areturn
    //   436: astore 10
    //   438: aload 10
    //   440: invokevirtual 158	java/io/IOException:printStackTrace	()V
    //   443: aload 4
    //   445: areturn
    //   446: astore 5
    //   448: aload_1
    //   449: invokevirtual 141	java/io/InputStream:close	()V
    //   452: aload 5
    //   454: athrow
    //   455: astore 6
    //   457: aload 6
    //   459: invokevirtual 158	java/io/IOException:printStackTrace	()V
    //   462: goto -10 -> 452
    //   465: astore 18
    //   467: aload 16
    //   469: astore 4
    //   471: aload 18
    //   473: astore 9
    //   475: goto -51 -> 424
    //   478: astore 9
    //   480: goto -56 -> 424
    //   483: astore_2
    //   484: aload_2
    //   485: astore_3
    //   486: aconst_null
    //   487: astore 4
    //   489: goto -359 -> 130
    //   492: astore 17
    //   494: aload 16
    //   496: astore 4
    //   498: aload 17
    //   500: astore_3
    //   501: goto -371 -> 130
    //
    // Exception table:
    //   from	to	target	type
    //   40	49	129	org/xmlpull/v1/XmlPullParserException
    //   107	126	129	org/xmlpull/v1/XmlPullParserException
    //   141	170	129	org/xmlpull/v1/XmlPullParserException
    //   177	200	129	org/xmlpull/v1/XmlPullParserException
    //   207	269	129	org/xmlpull/v1/XmlPullParserException
    //   276	299	129	org/xmlpull/v1/XmlPullParserException
    //   306	315	129	org/xmlpull/v1/XmlPullParserException
    //   320	367	129	org/xmlpull/v1/XmlPullParserException
    //   376	381	129	org/xmlpull/v1/XmlPullParserException
    //   320	367	374	java/lang/Exception
    //   388	392	395	java/io/IOException
    //   134	138	405	java/io/IOException
    //   0	27	415	java/io/IOException
    //   429	433	436	java/io/IOException
    //   0	27	446	finally
    //   40	49	446	finally
    //   80	89	446	finally
    //   107	126	446	finally
    //   130	134	446	finally
    //   141	170	446	finally
    //   177	200	446	finally
    //   207	269	446	finally
    //   276	299	446	finally
    //   306	315	446	finally
    //   320	367	446	finally
    //   376	381	446	finally
    //   424	429	446	finally
    //   448	452	455	java/io/IOException
    //   80	89	465	java/io/IOException
    //   40	49	478	java/io/IOException
    //   107	126	478	java/io/IOException
    //   141	170	478	java/io/IOException
    //   177	200	478	java/io/IOException
    //   207	269	478	java/io/IOException
    //   276	299	478	java/io/IOException
    //   306	315	478	java/io/IOException
    //   320	367	478	java/io/IOException
    //   376	381	478	java/io/IOException
    //   0	27	483	org/xmlpull/v1/XmlPullParserException
    //   80	89	492	org/xmlpull/v1/XmlPullParserException
  }

  public ArrayList<AdsResponse> a(String paramString1, String paramString2)
  {
    try
    {
      InputStream localInputStream = a.a().a(paramString1, paramString2);
      Object localObject = null;
      if (localInputStream != null)
      {
        ArrayList localArrayList = a(localInputStream);
        localObject = localArrayList;
      }
      return localObject;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public com.sohu.app.ads.sdk.model.a b(String paramString1, String paramString2)
  {
    InputStream localInputStream = a.a().a(paramString1, paramString2);
    com.sohu.app.ads.sdk.model.a locala = null;
    if (localInputStream != null)
      locala = b(localInputStream);
    return locala;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.f.b
 * JD-Core Version:    0.6.2
 */