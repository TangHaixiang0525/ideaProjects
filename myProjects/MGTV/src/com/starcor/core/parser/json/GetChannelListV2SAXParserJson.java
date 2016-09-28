package com.starcor.core.parser.json;

import com.starcor.core.domain.ChannelInfoV2;
import com.starcor.core.domain.ChannelItemInfoV2;
import com.starcor.core.interfaces.IXmlParser;
import java.io.InputStream;
import java.util.ArrayList;

public class GetChannelListV2SAXParserJson<Result>
  implements IXmlParser<Result>
{
  private ChannelInfoV2 channelInfo = new ChannelInfoV2();
  private ArrayList<ChannelItemInfoV2> channelList = new ArrayList();

  public GetChannelListV2SAXParserJson()
  {
    this.channelInfo.channelList = this.channelList;
  }

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
    //   6: new 36	org/json/JSONObject
    //   9: dup
    //   10: new 38	java/lang/String
    //   13: dup
    //   14: aload_1
    //   15: invokespecial 41	java/lang/String:<init>	([B)V
    //   18: invokespecial 44	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   21: astore_2
    //   22: aload_2
    //   23: ldc 46
    //   25: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   28: ifeq +664 -> 692
    //   31: aload_2
    //   32: ldc 46
    //   34: invokevirtual 54	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   37: astore 4
    //   39: aload 4
    //   41: ldc 56
    //   43: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   46: ifeq +722 -> 768
    //   49: aload 4
    //   51: ldc 56
    //   53: invokevirtual 60	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   56: astore 5
    //   58: iconst_0
    //   59: istore 6
    //   61: aconst_null
    //   62: astore 7
    //   64: iload 6
    //   66: aload 5
    //   68: invokevirtual 66	org/json/JSONArray:length	()I
    //   71: if_icmpge +736 -> 807
    //   74: new 68	com/starcor/core/domain/ChannelItemInfoV2
    //   77: dup
    //   78: invokespecial 69	com/starcor/core/domain/ChannelItemInfoV2:<init>	()V
    //   81: astore 9
    //   83: aload 5
    //   85: iload 6
    //   87: invokevirtual 72	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   90: astore 10
    //   92: aload 10
    //   94: ldc 74
    //   96: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   99: ifeq +15 -> 114
    //   102: aload 9
    //   104: aload 10
    //   106: ldc 74
    //   108: invokevirtual 78	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   111: putfield 81	com/starcor/core/domain/ChannelItemInfoV2:type	Ljava/lang/String;
    //   114: aload 10
    //   116: ldc 83
    //   118: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   121: ifeq +15 -> 136
    //   124: aload 9
    //   126: aload 10
    //   128: ldc 83
    //   130: invokevirtual 78	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   133: putfield 85	com/starcor/core/domain/ChannelItemInfoV2:id	Ljava/lang/String;
    //   136: aload 10
    //   138: ldc 87
    //   140: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   143: ifeq +15 -> 158
    //   146: aload 9
    //   148: aload 10
    //   150: ldc 87
    //   152: invokevirtual 78	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   155: putfield 89	com/starcor/core/domain/ChannelItemInfoV2:name	Ljava/lang/String;
    //   158: aload 10
    //   160: ldc 91
    //   162: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   165: ifeq +15 -> 180
    //   168: aload 9
    //   170: aload 10
    //   172: ldc 91
    //   174: invokevirtual 78	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   177: putfield 94	com/starcor/core/domain/ChannelItemInfoV2:aliasName	Ljava/lang/String;
    //   180: aload 10
    //   182: ldc 96
    //   184: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   187: ifeq +15 -> 202
    //   190: aload 9
    //   192: aload 10
    //   194: ldc 96
    //   196: invokevirtual 78	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   199: putfield 99	com/starcor/core/domain/ChannelItemInfoV2:imgH	Ljava/lang/String;
    //   202: aload 10
    //   204: ldc 101
    //   206: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   209: ifeq +15 -> 224
    //   212: aload 9
    //   214: aload 10
    //   216: ldc 101
    //   218: invokevirtual 78	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   221: putfield 104	com/starcor/core/domain/ChannelItemInfoV2:imgS	Ljava/lang/String;
    //   224: aload 10
    //   226: ldc 106
    //   228: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   231: ifeq +15 -> 246
    //   234: aload 9
    //   236: aload 10
    //   238: ldc 106
    //   240: invokevirtual 78	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   243: putfield 109	com/starcor/core/domain/ChannelItemInfoV2:imgV	Ljava/lang/String;
    //   246: aload 10
    //   248: ldc 111
    //   250: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   253: ifeq +15 -> 268
    //   256: aload 9
    //   258: aload 10
    //   260: ldc 111
    //   262: invokevirtual 78	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   265: putfield 113	com/starcor/core/domain/ChannelItemInfoV2:info	Ljava/lang/String;
    //   268: aload 10
    //   270: ldc 115
    //   272: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   275: ifeq +15 -> 290
    //   278: aload 9
    //   280: aload 10
    //   282: ldc 115
    //   284: invokevirtual 78	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   287: putfield 117	com/starcor/core/domain/ChannelItemInfoV2:import_id	Ljava/lang/String;
    //   290: aload 10
    //   292: ldc 119
    //   294: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   297: ifeq +15 -> 312
    //   300: aload 9
    //   302: aload 10
    //   304: ldc 119
    //   306: invokevirtual 78	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   309: putfield 121	com/starcor/core/domain/ChannelItemInfoV2:type_name	Ljava/lang/String;
    //   312: aload 10
    //   314: ldc 123
    //   316: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   319: ifeq +15 -> 334
    //   322: aload 9
    //   324: aload 10
    //   326: ldc 123
    //   328: invokevirtual 78	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   331: putfield 125	com/starcor/core/domain/ChannelItemInfoV2:activity_id	Ljava/lang/String;
    //   334: aload 10
    //   336: ldc 127
    //   338: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   341: ifeq +15 -> 356
    //   344: aload 9
    //   346: aload 10
    //   348: ldc 127
    //   350: invokevirtual 78	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   353: putfield 129	com/starcor/core/domain/ChannelItemInfoV2:live_video_type	Ljava/lang/String;
    //   356: aload 10
    //   358: ldc 131
    //   360: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   363: ifeq +212 -> 575
    //   366: aload 10
    //   368: ldc 131
    //   370: invokevirtual 54	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   373: astore 17
    //   375: aload 17
    //   377: ldc 133
    //   379: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   382: ifeq +21 -> 403
    //   385: aload 9
    //   387: aload 17
    //   389: ldc 133
    //   391: invokevirtual 78	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   394: invokestatic 139	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   397: invokevirtual 142	java/lang/Integer:intValue	()I
    //   400: putfield 146	com/starcor/core/domain/ChannelItemInfoV2:tsLimitMin	I
    //   403: aload 17
    //   405: ldc 148
    //   407: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   410: ifeq +21 -> 431
    //   413: aload 9
    //   415: aload 17
    //   417: ldc 148
    //   419: invokevirtual 78	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   422: invokestatic 139	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   425: invokevirtual 142	java/lang/Integer:intValue	()I
    //   428: putfield 151	com/starcor/core/domain/ChannelItemInfoV2:tsLimitPos	I
    //   431: aload 17
    //   433: ldc 153
    //   435: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   438: ifeq +21 -> 459
    //   441: aload 9
    //   443: aload 17
    //   445: ldc 153
    //   447: invokevirtual 78	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   450: invokestatic 139	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   453: invokevirtual 142	java/lang/Integer:intValue	()I
    //   456: putfield 156	com/starcor/core/domain/ChannelItemInfoV2:tsLimitMax	I
    //   459: aload 17
    //   461: ldc 158
    //   463: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   466: ifeq +15 -> 481
    //   469: aload 9
    //   471: aload 17
    //   473: ldc 158
    //   475: invokevirtual 78	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   478: putfield 160	com/starcor/core/domain/ChannelItemInfoV2:bullet_screen	Ljava/lang/String;
    //   481: aload 17
    //   483: ldc 162
    //   485: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   488: ifeq +15 -> 503
    //   491: aload 9
    //   493: aload 17
    //   495: ldc 162
    //   497: invokevirtual 78	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   500: putfield 165	com/starcor/core/domain/ChannelItemInfoV2:categoryId	Ljava/lang/String;
    //   503: aload 17
    //   505: ldc 167
    //   507: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   510: ifeq +21 -> 531
    //   513: aload 9
    //   515: aload 17
    //   517: ldc 167
    //   519: invokevirtual 78	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   522: invokestatic 139	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   525: invokevirtual 142	java/lang/Integer:intValue	()I
    //   528: putfield 170	com/starcor/core/domain/ChannelItemInfoV2:channelNum	I
    //   531: aload 17
    //   533: ldc 172
    //   535: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   538: ifeq +15 -> 553
    //   541: aload 9
    //   543: aload 17
    //   545: ldc 172
    //   547: invokevirtual 78	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   550: putfield 175	com/starcor/core/domain/ChannelItemInfoV2:capability	Ljava/lang/String;
    //   553: aload 17
    //   555: ldc 177
    //   557: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   560: ifeq +15 -> 575
    //   563: aload 9
    //   565: aload 17
    //   567: ldc 177
    //   569: invokevirtual 78	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   572: putfield 180	com/starcor/core/domain/ChannelItemInfoV2:liveMulticastUrl	Ljava/lang/String;
    //   575: aload 10
    //   577: ldc 182
    //   579: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   582: istore 11
    //   584: iload 11
    //   586: ifeq +125 -> 711
    //   589: aload 10
    //   591: ldc 182
    //   593: invokevirtual 60	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   596: astore 14
    //   598: iconst_0
    //   599: istore 15
    //   601: iload 15
    //   603: aload 14
    //   605: invokevirtual 66	org/json/JSONArray:length	()I
    //   608: if_icmpge +103 -> 711
    //   611: aload 14
    //   613: iload 15
    //   615: invokevirtual 72	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   618: astore 16
    //   620: aload 16
    //   622: ldc 184
    //   624: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   627: ifeq +15 -> 642
    //   630: aload 9
    //   632: aload 16
    //   634: ldc 184
    //   636: invokevirtual 78	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   639: putfield 186	com/starcor/core/domain/ChannelItemInfoV2:cameraposition	Ljava/lang/String;
    //   642: aload 16
    //   644: ldc 188
    //   646: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   649: ifeq +15 -> 664
    //   652: aload 9
    //   654: aload 16
    //   656: ldc 188
    //   658: invokevirtual 78	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   661: putfield 190	com/starcor/core/domain/ChannelItemInfoV2:reportway_type	Ljava/lang/String;
    //   664: aload 16
    //   666: ldc 192
    //   668: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   671: ifeq +15 -> 686
    //   674: aload 9
    //   676: aload 16
    //   678: ldc 192
    //   680: invokevirtual 78	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   683: putfield 194	com/starcor/core/domain/ChannelItemInfoV2:tv_channel	Ljava/lang/String;
    //   686: iinc 15 1
    //   689: goto -88 -> 601
    //   692: ldc 196
    //   694: ldc 198
    //   696: invokestatic 204	com/starcor/core/utils/Logger:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   699: aload_0
    //   700: getfield 21	com/starcor/core/parser/json/GetChannelListV2SAXParserJson:channelInfo	Lcom/starcor/core/domain/ChannelInfoV2;
    //   703: areturn
    //   704: astore 13
    //   706: aload 13
    //   708: invokevirtual 207	org/json/JSONException:printStackTrace	()V
    //   711: aload 10
    //   713: ldc 209
    //   715: invokevirtual 50	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   718: ifeq +22 -> 740
    //   721: aload 9
    //   723: aload 10
    //   725: ldc 209
    //   727: invokevirtual 54	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   730: ldc 211
    //   732: ldc 213
    //   734: invokevirtual 217	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   737: putfield 220	com/starcor/core/domain/ChannelItemInfoV2:barrageCategory	Ljava/lang/String;
    //   740: aload_0
    //   741: getfield 21	com/starcor/core/parser/json/GetChannelListV2SAXParserJson:channelInfo	Lcom/starcor/core/domain/ChannelInfoV2;
    //   744: getfield 27	com/starcor/core/domain/ChannelInfoV2:channelList	Ljava/util/ArrayList;
    //   747: aload 9
    //   749: invokevirtual 224	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   752: pop
    //   753: iinc 6 1
    //   756: aload 9
    //   758: astore 7
    //   760: goto -696 -> 64
    //   763: astore_3
    //   764: aload_3
    //   765: invokevirtual 225	java/lang/Exception:printStackTrace	()V
    //   768: ldc 196
    //   770: new 227	java/lang/StringBuilder
    //   773: dup
    //   774: invokespecial 228	java/lang/StringBuilder:<init>	()V
    //   777: ldc 230
    //   779: invokevirtual 234	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   782: aload_1
    //   783: invokevirtual 238	java/lang/Object:toString	()Ljava/lang/String;
    //   786: invokevirtual 234	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   789: invokevirtual 239	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   792: invokestatic 242	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   795: aload_0
    //   796: getfield 21	com/starcor/core/parser/json/GetChannelListV2SAXParserJson:channelInfo	Lcom/starcor/core/domain/ChannelInfoV2;
    //   799: areturn
    //   800: astore_3
    //   801: aload 7
    //   803: pop
    //   804: goto -40 -> 764
    //   807: aload 7
    //   809: pop
    //   810: goto -42 -> 768
    //
    // Exception table:
    //   from	to	target	type
    //   589	598	704	org/json/JSONException
    //   601	642	704	org/json/JSONException
    //   642	664	704	org/json/JSONException
    //   664	686	704	org/json/JSONException
    //   6	58	763	java/lang/Exception
    //   83	114	763	java/lang/Exception
    //   114	136	763	java/lang/Exception
    //   136	158	763	java/lang/Exception
    //   158	180	763	java/lang/Exception
    //   180	202	763	java/lang/Exception
    //   202	224	763	java/lang/Exception
    //   224	246	763	java/lang/Exception
    //   246	268	763	java/lang/Exception
    //   268	290	763	java/lang/Exception
    //   290	312	763	java/lang/Exception
    //   312	334	763	java/lang/Exception
    //   334	356	763	java/lang/Exception
    //   356	403	763	java/lang/Exception
    //   403	431	763	java/lang/Exception
    //   431	459	763	java/lang/Exception
    //   459	481	763	java/lang/Exception
    //   481	503	763	java/lang/Exception
    //   503	531	763	java/lang/Exception
    //   531	553	763	java/lang/Exception
    //   553	575	763	java/lang/Exception
    //   575	584	763	java/lang/Exception
    //   589	598	763	java/lang/Exception
    //   601	642	763	java/lang/Exception
    //   642	664	763	java/lang/Exception
    //   664	686	763	java/lang/Exception
    //   692	704	763	java/lang/Exception
    //   706	711	763	java/lang/Exception
    //   711	740	763	java/lang/Exception
    //   740	753	763	java/lang/Exception
    //   64	83	800	java/lang/Exception
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetChannelListV2SAXParserJson
 * JD-Core Version:    0.6.2
 */