package com.starcor.core.parser.json;

import com.starcor.core.domain.CollectListItem;
import com.starcor.core.interfaces.IXmlParser;
import java.io.InputStream;
import java.util.ArrayList;

public class GetCollectListSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = "GetCollectListSAXParserJson";
  private ArrayList<CollectListItem> collectList;

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
    //   6: aload_0
    //   7: getfield 25	com/starcor/core/parser/json/GetCollectListSAXParserJson:collectList	Ljava/util/ArrayList;
    //   10: ifnonnull +521 -> 531
    //   13: aload_0
    //   14: new 27	java/util/ArrayList
    //   17: dup
    //   18: invokespecial 28	java/util/ArrayList:<init>	()V
    //   21: putfield 25	com/starcor/core/parser/json/GetCollectListSAXParserJson:collectList	Ljava/util/ArrayList;
    //   24: new 30	org/json/JSONObject
    //   27: dup
    //   28: new 32	java/lang/String
    //   31: dup
    //   32: aload_1
    //   33: invokespecial 35	java/lang/String:<init>	([B)V
    //   36: invokespecial 38	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   39: astore_2
    //   40: aload_2
    //   41: ldc 40
    //   43: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   46: ifeq +511 -> 557
    //   49: aload_2
    //   50: ldc 40
    //   52: invokevirtual 48	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   55: astore 4
    //   57: iconst_0
    //   58: istore 5
    //   60: iload 5
    //   62: aload 4
    //   64: invokevirtual 54	org/json/JSONArray:length	()I
    //   67: if_icmpge +490 -> 557
    //   70: new 56	com/starcor/core/domain/CollectListItem
    //   73: dup
    //   74: invokespecial 57	com/starcor/core/domain/CollectListItem:<init>	()V
    //   77: astore 6
    //   79: aload 4
    //   81: iload 5
    //   83: invokevirtual 61	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   86: astore 7
    //   88: aload 7
    //   90: ldc 63
    //   92: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   95: ifeq +15 -> 110
    //   98: aload 6
    //   100: aload 7
    //   102: ldc 63
    //   104: invokevirtual 67	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   107: putfield 69	com/starcor/core/domain/CollectListItem:id	Ljava/lang/String;
    //   110: aload 7
    //   112: ldc 71
    //   114: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   117: ifeq +15 -> 132
    //   120: aload 6
    //   122: aload 7
    //   124: ldc 71
    //   126: invokevirtual 67	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   129: putfield 73	com/starcor/core/domain/CollectListItem:video_id	Ljava/lang/String;
    //   132: aload 7
    //   134: ldc 75
    //   136: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   139: ifeq +21 -> 160
    //   142: aload 6
    //   144: aload 7
    //   146: ldc 75
    //   148: invokevirtual 67	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   151: invokestatic 81	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   154: invokevirtual 84	java/lang/Integer:intValue	()I
    //   157: putfield 87	com/starcor/core/domain/CollectListItem:video_type	I
    //   160: aload 7
    //   162: ldc 89
    //   164: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   167: ifeq +21 -> 188
    //   170: aload 6
    //   172: aload 7
    //   174: ldc 89
    //   176: invokevirtual 67	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   179: invokestatic 81	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   182: invokevirtual 84	java/lang/Integer:intValue	()I
    //   185: putfield 91	com/starcor/core/domain/CollectListItem:view_type	I
    //   188: aload 7
    //   190: ldc 93
    //   192: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   195: ifeq +15 -> 210
    //   198: aload 6
    //   200: aload 7
    //   202: ldc 93
    //   204: invokevirtual 67	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   207: putfield 95	com/starcor/core/domain/CollectListItem:video_name	Ljava/lang/String;
    //   210: aload 7
    //   212: ldc 97
    //   214: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   217: ifeq +18 -> 235
    //   220: aload 6
    //   222: aload 7
    //   224: ldc 97
    //   226: invokevirtual 67	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   229: invokestatic 101	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   232: putfield 104	com/starcor/core/domain/CollectListItem:uiStyle	I
    //   235: aload 7
    //   237: ldc 106
    //   239: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   242: ifeq +21 -> 263
    //   245: aload 6
    //   247: aload 7
    //   249: ldc 106
    //   251: invokevirtual 67	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   254: invokestatic 81	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   257: invokevirtual 84	java/lang/Integer:intValue	()I
    //   260: putfield 108	com/starcor/core/domain/CollectListItem:video_index	I
    //   263: aload 7
    //   265: ldc 110
    //   267: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   270: ifeq +21 -> 291
    //   273: aload 6
    //   275: aload 7
    //   277: ldc 110
    //   279: invokevirtual 67	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   282: invokestatic 81	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   285: invokevirtual 84	java/lang/Integer:intValue	()I
    //   288: putfield 112	com/starcor/core/domain/CollectListItem:played_time	I
    //   291: aload 7
    //   293: ldc 114
    //   295: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   298: ifeq +15 -> 313
    //   301: aload 6
    //   303: aload 7
    //   305: ldc 114
    //   307: invokevirtual 67	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   310: putfield 117	com/starcor/core/domain/CollectListItem:add_time	Ljava/lang/String;
    //   313: aload 7
    //   315: ldc 119
    //   317: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   320: ifeq +15 -> 335
    //   323: aload 6
    //   325: aload 7
    //   327: ldc 119
    //   329: invokevirtual 67	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   332: putfield 121	com/starcor/core/domain/CollectListItem:media_assets_id	Ljava/lang/String;
    //   335: aload 7
    //   337: ldc 123
    //   339: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   342: ifeq +15 -> 357
    //   345: aload 6
    //   347: aload 7
    //   349: ldc 123
    //   351: invokevirtual 67	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   354: putfield 125	com/starcor/core/domain/CollectListItem:category_id	Ljava/lang/String;
    //   357: aload 7
    //   359: ldc 127
    //   361: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   364: ifeq +21 -> 385
    //   367: aload 6
    //   369: aload 7
    //   371: ldc 127
    //   373: invokevirtual 67	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   376: invokestatic 81	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   379: invokevirtual 84	java/lang/Integer:intValue	()I
    //   382: putfield 129	com/starcor/core/domain/CollectListItem:online	I
    //   385: aload 7
    //   387: ldc 131
    //   389: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   392: ifeq +15 -> 407
    //   395: aload 6
    //   397: aload 7
    //   399: ldc 131
    //   401: invokevirtual 67	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   404: putfield 133	com/starcor/core/domain/CollectListItem:media_assets_name	Ljava/lang/String;
    //   407: aload 7
    //   409: ldc 135
    //   411: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   414: ifeq +21 -> 435
    //   417: aload 6
    //   419: aload 7
    //   421: ldc 135
    //   423: invokevirtual 67	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   426: invokestatic 81	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   429: invokevirtual 84	java/lang/Integer:intValue	()I
    //   432: putfield 137	com/starcor/core/domain/CollectListItem:update_index	I
    //   435: aload 7
    //   437: ldc 139
    //   439: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   442: istore 14
    //   444: iload 14
    //   446: ifeq +47 -> 493
    //   449: aload 7
    //   451: ldc 139
    //   453: invokevirtual 67	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   456: astore 17
    //   458: aload 17
    //   460: invokevirtual 140	java/lang/String:length	()I
    //   463: bipush 8
    //   465: if_icmple +152 -> 617
    //   468: aload 6
    //   470: aload 17
    //   472: iconst_0
    //   473: bipush 8
    //   475: invokevirtual 144	java/lang/String:substring	(II)Ljava/lang/String;
    //   478: putfield 147	com/starcor/core/domain/CollectListItem:ts_day	Ljava/lang/String;
    //   481: aload 6
    //   483: aload 17
    //   485: bipush 8
    //   487: invokevirtual 150	java/lang/String:substring	(I)Ljava/lang/String;
    //   490: putfield 152	com/starcor/core/domain/CollectListItem:ts_begin	Ljava/lang/String;
    //   493: aload 7
    //   495: ldc 154
    //   497: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   500: ifeq +15 -> 515
    //   503: aload 6
    //   505: aload 7
    //   507: ldc 154
    //   509: invokevirtual 67	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   512: putfield 156	com/starcor/core/domain/CollectListItem:ts_time_len	Ljava/lang/String;
    //   515: aload_0
    //   516: getfield 25	com/starcor/core/parser/json/GetCollectListSAXParserJson:collectList	Ljava/util/ArrayList;
    //   519: aload 6
    //   521: invokevirtual 160	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   524: pop
    //   525: iinc 5 1
    //   528: goto -468 -> 60
    //   531: aload_0
    //   532: getfield 25	com/starcor/core/parser/json/GetCollectListSAXParserJson:collectList	Ljava/util/ArrayList;
    //   535: invokevirtual 163	java/util/ArrayList:clear	()V
    //   538: goto -514 -> 24
    //   541: astore 8
    //   543: aload 6
    //   545: iconst_1
    //   546: putfield 87	com/starcor/core/domain/CollectListItem:video_type	I
    //   549: goto -389 -> 160
    //   552: astore_3
    //   553: aload_3
    //   554: invokevirtual 166	java/lang/Exception:printStackTrace	()V
    //   557: aload_0
    //   558: getfield 25	com/starcor/core/parser/json/GetCollectListSAXParserJson:collectList	Ljava/util/ArrayList;
    //   561: areturn
    //   562: astore 9
    //   564: aload 6
    //   566: iconst_1
    //   567: putfield 91	com/starcor/core/domain/CollectListItem:view_type	I
    //   570: goto -382 -> 188
    //   573: astore 10
    //   575: aload 6
    //   577: iconst_0
    //   578: putfield 108	com/starcor/core/domain/CollectListItem:video_index	I
    //   581: goto -318 -> 263
    //   584: astore 11
    //   586: aload 6
    //   588: iconst_0
    //   589: putfield 112	com/starcor/core/domain/CollectListItem:played_time	I
    //   592: goto -301 -> 291
    //   595: astore 12
    //   597: aload 6
    //   599: iconst_1
    //   600: putfield 129	com/starcor/core/domain/CollectListItem:online	I
    //   603: goto -218 -> 385
    //   606: astore 13
    //   608: aload 6
    //   610: iconst_0
    //   611: putfield 137	com/starcor/core/domain/CollectListItem:update_index	I
    //   614: goto -179 -> 435
    //   617: aload 6
    //   619: aload 7
    //   621: ldc 167
    //   623: invokevirtual 67	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   626: putfield 147	com/starcor/core/domain/CollectListItem:ts_day	Ljava/lang/String;
    //   629: aload 6
    //   631: aload 17
    //   633: putfield 152	com/starcor/core/domain/CollectListItem:ts_begin	Ljava/lang/String;
    //   636: goto -143 -> 493
    //   639: astore 16
    //   641: goto -148 -> 493
    //
    // Exception table:
    //   from	to	target	type
    //   132	160	541	java/lang/Exception
    //   24	57	552	java/lang/Exception
    //   60	110	552	java/lang/Exception
    //   110	132	552	java/lang/Exception
    //   188	210	552	java/lang/Exception
    //   210	235	552	java/lang/Exception
    //   291	313	552	java/lang/Exception
    //   313	335	552	java/lang/Exception
    //   335	357	552	java/lang/Exception
    //   385	407	552	java/lang/Exception
    //   435	444	552	java/lang/Exception
    //   493	515	552	java/lang/Exception
    //   515	525	552	java/lang/Exception
    //   543	549	552	java/lang/Exception
    //   564	570	552	java/lang/Exception
    //   575	581	552	java/lang/Exception
    //   586	592	552	java/lang/Exception
    //   597	603	552	java/lang/Exception
    //   608	614	552	java/lang/Exception
    //   160	188	562	java/lang/Exception
    //   235	263	573	java/lang/Exception
    //   263	291	584	java/lang/Exception
    //   357	385	595	java/lang/Exception
    //   407	435	606	java/lang/Exception
    //   449	493	639	java/lang/Exception
    //   617	636	639	java/lang/Exception
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetCollectListSAXParserJson
 * JD-Core Version:    0.6.2
 */