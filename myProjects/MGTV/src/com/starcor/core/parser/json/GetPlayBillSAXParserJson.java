package com.starcor.core.parser.json;

import com.starcor.core.domain.PlayBillItem;
import com.starcor.core.domain.PlayBillStruct;
import com.starcor.core.interfaces.IXmlParser;
import java.io.InputStream;
import java.util.ArrayList;

public class GetPlayBillSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = "GetPlayBillSAXParserJson";
  private PlayBillItem pBill;
  private PlayBillStruct pBillInfo;
  private ArrayList<PlayBillStruct> playBillList;
  private int tsDefaultPos = 0;
  private int tsLimitMax = 0;
  private int tsLimitMin = 0;

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
    //   7: getfield 39	com/starcor/core/parser/json/GetPlayBillSAXParserJson:playBillList	Ljava/util/ArrayList;
    //   10: ifnonnull +438 -> 448
    //   13: aload_0
    //   14: new 41	java/util/ArrayList
    //   17: dup
    //   18: invokespecial 42	java/util/ArrayList:<init>	()V
    //   21: putfield 39	com/starcor/core/parser/json/GetPlayBillSAXParserJson:playBillList	Ljava/util/ArrayList;
    //   24: new 44	org/json/JSONObject
    //   27: dup
    //   28: new 46	java/lang/String
    //   31: dup
    //   32: aload_1
    //   33: invokespecial 49	java/lang/String:<init>	([B)V
    //   36: invokespecial 52	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   39: astore_2
    //   40: aload_2
    //   41: ldc 54
    //   43: invokevirtual 58	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   46: ifeq +19 -> 65
    //   49: aload_0
    //   50: aload_2
    //   51: ldc 54
    //   53: invokevirtual 62	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   56: invokestatic 68	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   59: invokevirtual 72	java/lang/Integer:intValue	()I
    //   62: putfield 28	com/starcor/core/parser/json/GetPlayBillSAXParserJson:tsLimitMin	I
    //   65: aload_2
    //   66: ldc 74
    //   68: invokevirtual 58	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   71: ifeq +19 -> 90
    //   74: aload_0
    //   75: aload_2
    //   76: ldc 74
    //   78: invokevirtual 62	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   81: invokestatic 68	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   84: invokevirtual 72	java/lang/Integer:intValue	()I
    //   87: putfield 28	com/starcor/core/parser/json/GetPlayBillSAXParserJson:tsLimitMin	I
    //   90: aload_2
    //   91: ldc 76
    //   93: invokevirtual 58	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   96: ifeq +19 -> 115
    //   99: aload_0
    //   100: aload_2
    //   101: ldc 76
    //   103: invokevirtual 62	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   106: invokestatic 68	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   109: invokevirtual 72	java/lang/Integer:intValue	()I
    //   112: putfield 28	com/starcor/core/parser/json/GetPlayBillSAXParserJson:tsLimitMin	I
    //   115: aload_2
    //   116: ldc 78
    //   118: invokevirtual 58	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   121: ifeq +352 -> 473
    //   124: new 80	org/json/JSONArray
    //   127: dup
    //   128: aload_2
    //   129: ldc 78
    //   131: invokevirtual 62	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   134: invokespecial 81	org/json/JSONArray:<init>	(Ljava/lang/String;)V
    //   137: astore 5
    //   139: iconst_0
    //   140: istore 6
    //   142: iload 6
    //   144: aload 5
    //   146: invokevirtual 84	org/json/JSONArray:length	()I
    //   149: if_icmpge +324 -> 473
    //   152: aload_0
    //   153: new 86	com/starcor/core/domain/PlayBillStruct
    //   156: dup
    //   157: invokespecial 87	com/starcor/core/domain/PlayBillStruct:<init>	()V
    //   160: putfield 89	com/starcor/core/parser/json/GetPlayBillSAXParserJson:pBillInfo	Lcom/starcor/core/domain/PlayBillStruct;
    //   163: aload_0
    //   164: getfield 89	com/starcor/core/parser/json/GetPlayBillSAXParserJson:pBillInfo	Lcom/starcor/core/domain/PlayBillStruct;
    //   167: aload_0
    //   168: getfield 28	com/starcor/core/parser/json/GetPlayBillSAXParserJson:tsLimitMin	I
    //   171: putfield 90	com/starcor/core/domain/PlayBillStruct:tsLimitMin	I
    //   174: aload_0
    //   175: getfield 89	com/starcor/core/parser/json/GetPlayBillSAXParserJson:pBillInfo	Lcom/starcor/core/domain/PlayBillStruct;
    //   178: aload_0
    //   179: getfield 30	com/starcor/core/parser/json/GetPlayBillSAXParserJson:tsLimitMax	I
    //   182: putfield 91	com/starcor/core/domain/PlayBillStruct:tsLimitMax	I
    //   185: aload_0
    //   186: getfield 89	com/starcor/core/parser/json/GetPlayBillSAXParserJson:pBillInfo	Lcom/starcor/core/domain/PlayBillStruct;
    //   189: aload_0
    //   190: getfield 32	com/starcor/core/parser/json/GetPlayBillSAXParserJson:tsDefaultPos	I
    //   193: putfield 92	com/starcor/core/domain/PlayBillStruct:tsDefaultPos	I
    //   196: aload_0
    //   197: getfield 89	com/starcor/core/parser/json/GetPlayBillSAXParserJson:pBillInfo	Lcom/starcor/core/domain/PlayBillStruct;
    //   200: new 41	java/util/ArrayList
    //   203: dup
    //   204: invokespecial 42	java/util/ArrayList:<init>	()V
    //   207: putfield 95	com/starcor/core/domain/PlayBillStruct:arrPlayBill	Ljava/util/ArrayList;
    //   210: aload 5
    //   212: iload 6
    //   214: invokevirtual 99	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   217: astore 7
    //   219: aload 7
    //   221: ldc 78
    //   223: invokevirtual 58	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   226: ifeq +17 -> 243
    //   229: aload_0
    //   230: getfield 89	com/starcor/core/parser/json/GetPlayBillSAXParserJson:pBillInfo	Lcom/starcor/core/domain/PlayBillStruct;
    //   233: aload 7
    //   235: ldc 78
    //   237: invokevirtual 62	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   240: putfield 101	com/starcor/core/domain/PlayBillStruct:day	Ljava/lang/String;
    //   243: aload 7
    //   245: ldc 103
    //   247: invokevirtual 58	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   250: ifeq +301 -> 551
    //   253: aload 7
    //   255: ldc 103
    //   257: invokevirtual 107	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   260: astore 9
    //   262: iconst_0
    //   263: istore 10
    //   265: iload 10
    //   267: aload 9
    //   269: invokevirtual 84	org/json/JSONArray:length	()I
    //   272: if_icmpge +279 -> 551
    //   275: aload_0
    //   276: new 109	com/starcor/core/domain/PlayBillItem
    //   279: dup
    //   280: invokespecial 110	com/starcor/core/domain/PlayBillItem:<init>	()V
    //   283: putfield 112	com/starcor/core/parser/json/GetPlayBillSAXParserJson:pBill	Lcom/starcor/core/domain/PlayBillItem;
    //   286: aload 9
    //   288: iload 10
    //   290: invokevirtual 99	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   293: astore 11
    //   295: aload 11
    //   297: ldc 114
    //   299: invokevirtual 58	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   302: ifeq +17 -> 319
    //   305: aload_0
    //   306: getfield 112	com/starcor/core/parser/json/GetPlayBillSAXParserJson:pBill	Lcom/starcor/core/domain/PlayBillItem;
    //   309: aload 11
    //   311: ldc 114
    //   313: invokevirtual 62	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   316: putfield 117	com/starcor/core/domain/PlayBillItem:desc	Ljava/lang/String;
    //   319: aload 11
    //   321: ldc 119
    //   323: invokevirtual 58	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   326: ifeq +23 -> 349
    //   329: aload_0
    //   330: getfield 112	com/starcor/core/parser/json/GetPlayBillSAXParserJson:pBill	Lcom/starcor/core/domain/PlayBillItem;
    //   333: aload 11
    //   335: ldc 119
    //   337: invokevirtual 62	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   340: invokestatic 68	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   343: invokevirtual 72	java/lang/Integer:intValue	()I
    //   346: putfield 121	com/starcor/core/domain/PlayBillItem:can_play	I
    //   349: aload 11
    //   351: ldc 123
    //   353: invokevirtual 58	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   356: ifeq +23 -> 379
    //   359: aload_0
    //   360: getfield 112	com/starcor/core/parser/json/GetPlayBillSAXParserJson:pBill	Lcom/starcor/core/domain/PlayBillItem;
    //   363: aload 11
    //   365: ldc 123
    //   367: invokevirtual 62	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   370: invokestatic 68	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   373: invokevirtual 72	java/lang/Integer:intValue	()I
    //   376: putfield 126	com/starcor/core/domain/PlayBillItem:timeLen	I
    //   379: aload 11
    //   381: ldc 128
    //   383: invokevirtual 58	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   386: ifeq +17 -> 403
    //   389: aload_0
    //   390: getfield 112	com/starcor/core/parser/json/GetPlayBillSAXParserJson:pBill	Lcom/starcor/core/domain/PlayBillItem;
    //   393: aload 11
    //   395: ldc 128
    //   397: invokevirtual 62	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   400: putfield 130	com/starcor/core/domain/PlayBillItem:begin	Ljava/lang/String;
    //   403: aload 11
    //   405: ldc 132
    //   407: invokevirtual 58	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   410: ifeq +17 -> 427
    //   413: aload_0
    //   414: getfield 112	com/starcor/core/parser/json/GetPlayBillSAXParserJson:pBill	Lcom/starcor/core/domain/PlayBillItem;
    //   417: aload 11
    //   419: ldc 132
    //   421: invokevirtual 62	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   424: putfield 134	com/starcor/core/domain/PlayBillItem:huawei_cid	Ljava/lang/String;
    //   427: aload_0
    //   428: getfield 89	com/starcor/core/parser/json/GetPlayBillSAXParserJson:pBillInfo	Lcom/starcor/core/domain/PlayBillStruct;
    //   431: getfield 95	com/starcor/core/domain/PlayBillStruct:arrPlayBill	Ljava/util/ArrayList;
    //   434: aload_0
    //   435: getfield 112	com/starcor/core/parser/json/GetPlayBillSAXParserJson:pBill	Lcom/starcor/core/domain/PlayBillItem;
    //   438: invokevirtual 138	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   441: pop
    //   442: iinc 10 1
    //   445: goto -180 -> 265
    //   448: aload_0
    //   449: getfield 39	com/starcor/core/parser/json/GetPlayBillSAXParserJson:playBillList	Ljava/util/ArrayList;
    //   452: invokevirtual 141	java/util/ArrayList:clear	()V
    //   455: goto -431 -> 24
    //   458: astore_3
    //   459: aload_3
    //   460: invokevirtual 144	java/lang/Exception:printStackTrace	()V
    //   463: goto -348 -> 115
    //   466: astore 4
    //   468: aload 4
    //   470: invokevirtual 144	java/lang/Exception:printStackTrace	()V
    //   473: ldc 11
    //   475: new 146	java/lang/StringBuilder
    //   478: dup
    //   479: invokespecial 147	java/lang/StringBuilder:<init>	()V
    //   482: ldc 149
    //   484: invokevirtual 153	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   487: aload_1
    //   488: invokevirtual 157	java/lang/Object:toString	()Ljava/lang/String;
    //   491: invokevirtual 153	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   494: invokevirtual 158	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   497: invokestatic 164	com/starcor/core/utils/Logger:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   500: aload_0
    //   501: getfield 39	com/starcor/core/parser/json/GetPlayBillSAXParserJson:playBillList	Ljava/util/ArrayList;
    //   504: areturn
    //   505: astore 12
    //   507: aload 12
    //   509: invokevirtual 144	java/lang/Exception:printStackTrace	()V
    //   512: aload_0
    //   513: getfield 112	com/starcor/core/parser/json/GetPlayBillSAXParserJson:pBill	Lcom/starcor/core/domain/PlayBillItem;
    //   516: iconst_0
    //   517: putfield 121	com/starcor/core/domain/PlayBillItem:can_play	I
    //   520: goto -171 -> 349
    //   523: astore 13
    //   525: aload 13
    //   527: invokevirtual 144	java/lang/Exception:printStackTrace	()V
    //   530: aload_0
    //   531: getfield 112	com/starcor/core/parser/json/GetPlayBillSAXParserJson:pBill	Lcom/starcor/core/domain/PlayBillItem;
    //   534: iconst_0
    //   535: putfield 126	com/starcor/core/domain/PlayBillItem:timeLen	I
    //   538: goto -159 -> 379
    //   541: astore 14
    //   543: aload 14
    //   545: invokevirtual 144	java/lang/Exception:printStackTrace	()V
    //   548: goto -106 -> 442
    //   551: aload_0
    //   552: getfield 39	com/starcor/core/parser/json/GetPlayBillSAXParserJson:playBillList	Ljava/util/ArrayList;
    //   555: aload_0
    //   556: getfield 89	com/starcor/core/parser/json/GetPlayBillSAXParserJson:pBillInfo	Lcom/starcor/core/domain/PlayBillStruct;
    //   559: invokevirtual 138	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   562: pop
    //   563: iinc 6 1
    //   566: goto -424 -> 142
    //
    // Exception table:
    //   from	to	target	type
    //   40	65	458	java/lang/Exception
    //   65	90	458	java/lang/Exception
    //   90	115	458	java/lang/Exception
    //   24	40	466	java/lang/Exception
    //   115	139	466	java/lang/Exception
    //   142	243	466	java/lang/Exception
    //   243	262	466	java/lang/Exception
    //   265	319	466	java/lang/Exception
    //   379	403	466	java/lang/Exception
    //   403	427	466	java/lang/Exception
    //   459	463	466	java/lang/Exception
    //   507	520	466	java/lang/Exception
    //   525	538	466	java/lang/Exception
    //   543	548	466	java/lang/Exception
    //   551	563	466	java/lang/Exception
    //   319	349	505	java/lang/Exception
    //   349	379	523	java/lang/Exception
    //   427	442	541	java/lang/Exception
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetPlayBillSAXParserJson
 * JD-Core Version:    0.6.2
 */