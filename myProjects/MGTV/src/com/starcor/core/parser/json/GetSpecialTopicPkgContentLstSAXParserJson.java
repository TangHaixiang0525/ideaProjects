package com.starcor.core.parser.json;

import com.starcor.core.domain.SpecialTopicPkgCntLstInfo;
import com.starcor.core.domain.SpecialTopicPkgCntLstStruct;
import com.starcor.core.interfaces.IXmlParser;
import java.io.InputStream;

public class GetSpecialTopicPkgContentLstSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = "GetAdInfoSAXParserJson";
  private SpecialTopicPkgCntLstStruct sCntLstStructs;
  private SpecialTopicPkgCntLstInfo sPkgCntLstInfo = new SpecialTopicPkgCntLstInfo();

  public Result parser(InputStream paramInputStream)
  {
    return null;
  }

  // ERROR //
  public Result parser(byte[] paramArrayOfByte)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull +5 -> 6
    //   4: aconst_null
    //   5: areturn
    //   6: new 33	java/lang/String
    //   9: dup
    //   10: aload_1
    //   11: invokespecial 36	java/lang/String:<init>	([B)V
    //   14: invokestatic 42	com/starcor/core/utils/JsonUtils:getJsonObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   17: astore_3
    //   18: aload_3
    //   19: ldc 44
    //   21: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   24: ifeq +16 -> 40
    //   27: aload_0
    //   28: getfield 24	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sPkgCntLstInfo	Lcom/starcor/core/domain/SpecialTopicPkgCntLstInfo;
    //   31: aload_3
    //   32: ldc 44
    //   34: invokevirtual 54	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   37: putfield 56	com/starcor/core/domain/SpecialTopicPkgCntLstInfo:count_num	Ljava/lang/String;
    //   40: aload_3
    //   41: ldc 58
    //   43: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   46: ifeq +873 -> 919
    //   49: aload_3
    //   50: ldc 58
    //   52: invokevirtual 62	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   55: astore 4
    //   57: aload_0
    //   58: getfield 24	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sPkgCntLstInfo	Lcom/starcor/core/domain/SpecialTopicPkgCntLstInfo;
    //   61: new 64	java/util/ArrayList
    //   64: dup
    //   65: invokespecial 65	java/util/ArrayList:<init>	()V
    //   68: putfield 69	com/starcor/core/domain/SpecialTopicPkgCntLstInfo:sTopicPkgCntLstStructs	Ljava/util/ArrayList;
    //   71: iconst_0
    //   72: istore 5
    //   74: iload 5
    //   76: aload 4
    //   78: invokevirtual 75	org/json/JSONArray:length	()I
    //   81: if_icmpge +838 -> 919
    //   84: aload 4
    //   86: iload 5
    //   88: invokevirtual 79	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   91: astore 6
    //   93: aload_0
    //   94: new 81	com/starcor/core/domain/SpecialTopicPkgCntLstStruct
    //   97: dup
    //   98: invokespecial 82	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:<init>	()V
    //   101: putfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   104: aload 6
    //   106: ldc 86
    //   108: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   111: ifeq +17 -> 128
    //   114: aload_0
    //   115: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   118: aload 6
    //   120: ldc 86
    //   122: invokevirtual 54	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   125: putfield 88	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:id	Ljava/lang/String;
    //   128: aload 6
    //   130: ldc 90
    //   132: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   135: ifeq +17 -> 152
    //   138: aload_0
    //   139: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   142: aload 6
    //   144: ldc 90
    //   146: invokevirtual 54	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   149: putfield 92	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:name	Ljava/lang/String;
    //   152: aload 6
    //   154: ldc 94
    //   156: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   159: ifeq +17 -> 176
    //   162: aload_0
    //   163: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   166: aload 6
    //   168: ldc 94
    //   170: invokevirtual 54	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   173: putfield 96	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:type	Ljava/lang/String;
    //   176: aload 6
    //   178: ldc 98
    //   180: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   183: ifeq +17 -> 200
    //   186: aload_0
    //   187: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   190: aload 6
    //   192: ldc 98
    //   194: invokevirtual 54	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   197: putfield 100	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:video_wish	Ljava/lang/String;
    //   200: aload 6
    //   202: ldc 102
    //   204: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   207: ifeq +17 -> 224
    //   210: aload_0
    //   211: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   214: aload 6
    //   216: ldc 102
    //   218: invokevirtual 54	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   221: putfield 104	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:label_type	Ljava/lang/String;
    //   224: aload 6
    //   226: ldc 106
    //   228: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   231: istore 7
    //   233: iload 7
    //   235: ifeq +17 -> 252
    //   238: aload_0
    //   239: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   242: aload 6
    //   244: ldc 106
    //   246: invokevirtual 110	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   249: putfield 113	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:is_online	I
    //   252: aload 6
    //   254: ldc 115
    //   256: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   259: ifeq +17 -> 276
    //   262: aload_0
    //   263: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   266: aload 6
    //   268: ldc 115
    //   270: invokevirtual 54	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   273: putfield 117	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:img0	Ljava/lang/String;
    //   276: aload 6
    //   278: ldc 119
    //   280: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   283: ifeq +17 -> 300
    //   286: aload_0
    //   287: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   290: aload 6
    //   292: ldc 119
    //   294: invokevirtual 54	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   297: putfield 121	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:img1	Ljava/lang/String;
    //   300: aload 6
    //   302: ldc 123
    //   304: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   307: ifeq +17 -> 324
    //   310: aload_0
    //   311: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   314: aload 6
    //   316: ldc 123
    //   318: invokevirtual 54	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   321: putfield 125	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:img2	Ljava/lang/String;
    //   324: aload 6
    //   326: ldc 127
    //   328: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   331: ifeq +17 -> 348
    //   334: aload_0
    //   335: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   338: aload 6
    //   340: ldc 127
    //   342: invokevirtual 54	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   345: putfield 129	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:imgv	Ljava/lang/String;
    //   348: aload 6
    //   350: ldc 131
    //   352: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   355: ifeq +17 -> 372
    //   358: aload_0
    //   359: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   362: aload 6
    //   364: ldc 131
    //   366: invokevirtual 54	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   369: putfield 133	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:imgh	Ljava/lang/String;
    //   372: aload 6
    //   374: ldc 135
    //   376: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   379: ifeq +17 -> 396
    //   382: aload_0
    //   383: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   386: aload 6
    //   388: ldc 135
    //   390: invokevirtual 54	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   393: putfield 137	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:imgs	Ljava/lang/String;
    //   396: aload 6
    //   398: ldc 139
    //   400: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   403: ifeq +17 -> 420
    //   406: aload_0
    //   407: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   410: aload 6
    //   412: ldc 139
    //   414: invokevirtual 54	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   417: putfield 141	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:video_id	Ljava/lang/String;
    //   420: aload 6
    //   422: ldc 143
    //   424: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   427: ifeq +17 -> 444
    //   430: aload_0
    //   431: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   434: aload 6
    //   436: ldc 143
    //   438: invokevirtual 54	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   441: putfield 145	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:mark_img	Ljava/lang/String;
    //   444: aload 6
    //   446: ldc 147
    //   448: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   451: ifeq +17 -> 468
    //   454: aload_0
    //   455: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   458: aload 6
    //   460: ldc 147
    //   462: invokevirtual 54	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   465: putfield 149	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:watch_focus	Ljava/lang/String;
    //   468: aload 6
    //   470: ldc 151
    //   472: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   475: ifeq +17 -> 492
    //   478: aload_0
    //   479: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   482: aload 6
    //   484: ldc 151
    //   486: invokevirtual 54	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   489: putfield 153	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:img_corner	Ljava/lang/String;
    //   492: aload 6
    //   494: ldc 155
    //   496: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   499: ifeq +17 -> 516
    //   502: aload_0
    //   503: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   506: aload 6
    //   508: ldc 155
    //   510: invokevirtual 54	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   513: putfield 157	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:view_type	Ljava/lang/String;
    //   516: aload 6
    //   518: ldc 159
    //   520: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   523: ifeq +17 -> 540
    //   526: aload_0
    //   527: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   530: aload 6
    //   532: ldc 159
    //   534: invokevirtual 54	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   537: putfield 161	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:video_type	Ljava/lang/String;
    //   540: aload 6
    //   542: ldc 163
    //   544: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   547: ifeq +17 -> 564
    //   550: aload_0
    //   551: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   554: aload 6
    //   556: ldc 163
    //   558: invokevirtual 54	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   561: putfield 165	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:video_preview_type	Ljava/lang/String;
    //   564: aload 6
    //   566: ldc 167
    //   568: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   571: ifeq +17 -> 588
    //   574: aload_0
    //   575: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   578: aload 6
    //   580: ldc 167
    //   582: invokevirtual 54	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   585: putfield 169	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:video_index	Ljava/lang/String;
    //   588: aload 6
    //   590: ldc 171
    //   592: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   595: ifeq +17 -> 612
    //   598: aload_0
    //   599: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   602: aload 6
    //   604: ldc 171
    //   606: invokevirtual 54	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   609: putfield 174	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:time_len	Ljava/lang/String;
    //   612: aload 6
    //   614: ldc 176
    //   616: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   619: ifeq +17 -> 636
    //   622: aload_0
    //   623: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   626: aload 6
    //   628: ldc 176
    //   630: invokevirtual 54	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   633: putfield 178	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:packet_id	Ljava/lang/String;
    //   636: aload 6
    //   638: ldc 180
    //   640: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   643: ifeq +17 -> 660
    //   646: aload_0
    //   647: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   650: aload 6
    //   652: ldc 180
    //   654: invokevirtual 54	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   657: putfield 182	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:packet_category_id	Ljava/lang/String;
    //   660: aload 6
    //   662: ldc 184
    //   664: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   667: ifeq +17 -> 684
    //   670: aload_0
    //   671: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   674: aload 6
    //   676: ldc 184
    //   678: invokevirtual 54	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   681: putfield 186	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:eng_name	Ljava/lang/String;
    //   684: aload 6
    //   686: ldc 188
    //   688: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   691: ifeq +17 -> 708
    //   694: aload_0
    //   695: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   698: aload 6
    //   700: ldc 188
    //   702: invokevirtual 54	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   705: putfield 190	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:ex_url	Ljava/lang/String;
    //   708: aload 6
    //   710: ldc 192
    //   712: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   715: ifeq +17 -> 732
    //   718: aload_0
    //   719: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   722: aload 6
    //   724: ldc 192
    //   726: invokevirtual 54	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   729: putfield 194	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:click	Ljava/lang/String;
    //   732: aload 6
    //   734: ldc 196
    //   736: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   739: ifeq +17 -> 756
    //   742: aload_0
    //   743: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   746: aload 6
    //   748: ldc 196
    //   750: invokevirtual 54	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   753: putfield 198	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:summary	Ljava/lang/String;
    //   756: aload 6
    //   758: ldc 200
    //   760: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   763: ifeq +17 -> 780
    //   766: aload_0
    //   767: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   770: aload 6
    //   772: ldc 200
    //   774: invokevirtual 54	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   777: putfield 202	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:category_id	Ljava/lang/String;
    //   780: aload 6
    //   782: ldc 204
    //   784: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   787: ifeq +17 -> 804
    //   790: aload_0
    //   791: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   794: aload 6
    //   796: ldc 204
    //   798: invokevirtual 54	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   801: putfield 206	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:special_id	Ljava/lang/String;
    //   804: aload 6
    //   806: ldc 208
    //   808: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   811: istore 8
    //   813: iload 8
    //   815: ifeq +17 -> 832
    //   818: aload_0
    //   819: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   822: aload 6
    //   824: ldc 208
    //   826: invokevirtual 110	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   829: putfield 210	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:ui_style	I
    //   832: aload 6
    //   834: ldc 212
    //   836: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   839: ifeq +41 -> 880
    //   842: aload_0
    //   843: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   846: aload 6
    //   848: ldc 212
    //   850: invokevirtual 54	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   853: putfield 215	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:abstractInfo	Ljava/lang/String;
    //   856: ldc 217
    //   858: aload_0
    //   859: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   862: getfield 215	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:abstractInfo	Ljava/lang/String;
    //   865: invokevirtual 221	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   868: ifeq +12 -> 880
    //   871: aload_0
    //   872: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   875: ldc 223
    //   877: putfield 215	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:abstractInfo	Ljava/lang/String;
    //   880: aload_0
    //   881: getfield 24	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sPkgCntLstInfo	Lcom/starcor/core/domain/SpecialTopicPkgCntLstInfo;
    //   884: getfield 69	com/starcor/core/domain/SpecialTopicPkgCntLstInfo:sTopicPkgCntLstStructs	Ljava/util/ArrayList;
    //   887: aload_0
    //   888: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   891: invokevirtual 226	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   894: pop
    //   895: iinc 5 1
    //   898: goto -824 -> 74
    //   901: astore 12
    //   903: aload_0
    //   904: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   907: iconst_0
    //   908: putfield 113	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:is_online	I
    //   911: goto -659 -> 252
    //   914: astore_2
    //   915: aload_2
    //   916: invokevirtual 229	java/lang/Exception:printStackTrace	()V
    //   919: aload_0
    //   920: getfield 24	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sPkgCntLstInfo	Lcom/starcor/core/domain/SpecialTopicPkgCntLstInfo;
    //   923: areturn
    //   924: astore 10
    //   926: aload_0
    //   927: getfield 84	com/starcor/core/parser/json/GetSpecialTopicPkgContentLstSAXParserJson:sCntLstStructs	Lcom/starcor/core/domain/SpecialTopicPkgCntLstStruct;
    //   930: aload 6
    //   932: ldc 208
    //   934: invokevirtual 54	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   937: invokestatic 235	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   940: invokevirtual 238	java/lang/Integer:intValue	()I
    //   943: putfield 210	com/starcor/core/domain/SpecialTopicPkgCntLstStruct:ui_style	I
    //   946: goto -114 -> 832
    //   949: astore 11
    //   951: goto -119 -> 832
    //
    // Exception table:
    //   from	to	target	type
    //   238	252	901	org/json/JSONException
    //   6	40	914	java/lang/Exception
    //   40	71	914	java/lang/Exception
    //   74	128	914	java/lang/Exception
    //   128	152	914	java/lang/Exception
    //   152	176	914	java/lang/Exception
    //   176	200	914	java/lang/Exception
    //   200	224	914	java/lang/Exception
    //   224	233	914	java/lang/Exception
    //   238	252	914	java/lang/Exception
    //   252	276	914	java/lang/Exception
    //   276	300	914	java/lang/Exception
    //   300	324	914	java/lang/Exception
    //   324	348	914	java/lang/Exception
    //   348	372	914	java/lang/Exception
    //   372	396	914	java/lang/Exception
    //   396	420	914	java/lang/Exception
    //   420	444	914	java/lang/Exception
    //   444	468	914	java/lang/Exception
    //   468	492	914	java/lang/Exception
    //   492	516	914	java/lang/Exception
    //   516	540	914	java/lang/Exception
    //   540	564	914	java/lang/Exception
    //   564	588	914	java/lang/Exception
    //   588	612	914	java/lang/Exception
    //   612	636	914	java/lang/Exception
    //   636	660	914	java/lang/Exception
    //   660	684	914	java/lang/Exception
    //   684	708	914	java/lang/Exception
    //   708	732	914	java/lang/Exception
    //   732	756	914	java/lang/Exception
    //   756	780	914	java/lang/Exception
    //   780	804	914	java/lang/Exception
    //   804	813	914	java/lang/Exception
    //   832	880	914	java/lang/Exception
    //   880	895	914	java/lang/Exception
    //   903	911	914	java/lang/Exception
    //   818	832	924	java/lang/Exception
    //   926	946	949	java/lang/Exception
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetSpecialTopicPkgContentLstSAXParserJson
 * JD-Core Version:    0.6.2
 */