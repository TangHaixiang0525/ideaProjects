package com.starcor.core.parser.json;

import com.starcor.core.domain.AAAMovieCouponInfo;
import com.starcor.core.interfaces.IXmlParser;
import java.io.InputStream;
import java.util.ArrayList;

public class AAAGetMovieCouponInfoSAXParseJson<Result>
  implements IXmlParser<Result>
{
  private AAAMovieCouponInfo info = new AAAMovieCouponInfo();

  public AAAGetMovieCouponInfoSAXParseJson()
  {
    this.info.coupon_list = new ArrayList();
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
    //   6: new 34	java/lang/String
    //   9: dup
    //   10: aload_1
    //   11: invokespecial 37	java/lang/String:<init>	([B)V
    //   14: astore_2
    //   15: ldc 39
    //   17: new 41	java/lang/StringBuilder
    //   20: dup
    //   21: invokespecial 42	java/lang/StringBuilder:<init>	()V
    //   24: ldc 44
    //   26: invokevirtual 48	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   29: aload_2
    //   30: invokevirtual 48	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   33: invokevirtual 52	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   36: invokestatic 58	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   39: new 60	org/json/JSONObject
    //   42: dup
    //   43: aload_2
    //   44: invokespecial 63	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   47: astore 4
    //   49: aload 4
    //   51: ldc 65
    //   53: invokevirtual 69	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   56: ifeq +17 -> 73
    //   59: aload_0
    //   60: getfield 18	com/starcor/core/parser/json/AAAGetMovieCouponInfoSAXParseJson:info	Lcom/starcor/core/domain/AAAMovieCouponInfo;
    //   63: aload 4
    //   65: ldc 65
    //   67: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   70: putfield 76	com/starcor/core/domain/AAAMovieCouponInfo:state	Ljava/lang/String;
    //   73: aload 4
    //   75: ldc 78
    //   77: invokevirtual 69	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   80: ifeq +17 -> 97
    //   83: aload_0
    //   84: getfield 18	com/starcor/core/parser/json/AAAGetMovieCouponInfoSAXParseJson:info	Lcom/starcor/core/domain/AAAMovieCouponInfo;
    //   87: aload 4
    //   89: ldc 78
    //   91: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   94: putfield 80	com/starcor/core/domain/AAAMovieCouponInfo:reason	Ljava/lang/String;
    //   97: aload 4
    //   99: ldc 82
    //   101: invokevirtual 69	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   104: ifeq +476 -> 580
    //   107: new 60	org/json/JSONObject
    //   110: dup
    //   111: aload 4
    //   113: ldc 82
    //   115: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   118: invokespecial 63	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   121: astore 5
    //   123: aload 5
    //   125: ldc 84
    //   127: invokevirtual 69	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   130: istore 6
    //   132: iload 6
    //   134: ifeq +23 -> 157
    //   137: aload_0
    //   138: getfield 18	com/starcor/core/parser/json/AAAGetMovieCouponInfoSAXParseJson:info	Lcom/starcor/core/domain/AAAMovieCouponInfo;
    //   141: aload 5
    //   143: ldc 84
    //   145: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   148: invokestatic 90	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   151: invokevirtual 94	java/lang/Integer:intValue	()I
    //   154: putfield 97	com/starcor/core/domain/AAAMovieCouponInfo:err	I
    //   157: aload 5
    //   159: ldc 99
    //   161: invokevirtual 69	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   164: ifeq +17 -> 181
    //   167: aload_0
    //   168: getfield 18	com/starcor/core/parser/json/AAAGetMovieCouponInfoSAXParseJson:info	Lcom/starcor/core/domain/AAAMovieCouponInfo;
    //   171: aload 5
    //   173: ldc 99
    //   175: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   178: putfield 101	com/starcor/core/domain/AAAMovieCouponInfo:status	Ljava/lang/String;
    //   181: aload 5
    //   183: ldc 103
    //   185: invokevirtual 69	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   188: ifeq +392 -> 580
    //   191: new 60	org/json/JSONObject
    //   194: dup
    //   195: aload 5
    //   197: ldc 103
    //   199: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   202: invokespecial 63	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   205: astore 7
    //   207: aload 7
    //   209: ldc 105
    //   211: invokevirtual 69	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   214: istore 8
    //   216: iload 8
    //   218: ifeq +23 -> 241
    //   221: aload_0
    //   222: getfield 18	com/starcor/core/parser/json/AAAGetMovieCouponInfoSAXParseJson:info	Lcom/starcor/core/domain/AAAMovieCouponInfo;
    //   225: aload 7
    //   227: ldc 105
    //   229: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   232: invokestatic 90	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   235: invokevirtual 94	java/lang/Integer:intValue	()I
    //   238: putfield 107	com/starcor/core/domain/AAAMovieCouponInfo:all_count	I
    //   241: aload 7
    //   243: ldc 109
    //   245: invokevirtual 69	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   248: istore 9
    //   250: iload 9
    //   252: ifeq +23 -> 275
    //   255: aload_0
    //   256: getfield 18	com/starcor/core/parser/json/AAAGetMovieCouponInfoSAXParseJson:info	Lcom/starcor/core/domain/AAAMovieCouponInfo;
    //   259: aload 7
    //   261: ldc 109
    //   263: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   266: invokestatic 90	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   269: invokevirtual 94	java/lang/Integer:intValue	()I
    //   272: putfield 111	com/starcor/core/domain/AAAMovieCouponInfo:common_count	I
    //   275: aload 7
    //   277: ldc 113
    //   279: invokevirtual 69	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   282: istore 10
    //   284: iload 10
    //   286: ifeq +23 -> 309
    //   289: aload_0
    //   290: getfield 18	com/starcor/core/parser/json/AAAGetMovieCouponInfoSAXParseJson:info	Lcom/starcor/core/domain/AAAMovieCouponInfo;
    //   293: aload 7
    //   295: ldc 113
    //   297: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   300: invokestatic 90	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   303: invokevirtual 94	java/lang/Integer:intValue	()I
    //   306: putfield 115	com/starcor/core/domain/AAAMovieCouponInfo:special_count	I
    //   309: aload 7
    //   311: ldc 116
    //   313: invokevirtual 69	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   316: ifeq +264 -> 580
    //   319: aload 7
    //   321: ldc 116
    //   323: invokevirtual 120	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   326: astore 11
    //   328: ldc 39
    //   330: new 41	java/lang/StringBuilder
    //   333: dup
    //   334: invokespecial 42	java/lang/StringBuilder:<init>	()V
    //   337: ldc 122
    //   339: invokevirtual 48	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   342: aload 11
    //   344: invokevirtual 125	org/json/JSONArray:toString	()Ljava/lang/String;
    //   347: invokevirtual 48	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   350: invokevirtual 52	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   353: invokestatic 58	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   356: iconst_0
    //   357: istore 12
    //   359: iload 12
    //   361: aload 11
    //   363: invokevirtual 128	org/json/JSONArray:length	()I
    //   366: if_icmpge +214 -> 580
    //   369: aload 11
    //   371: iload 12
    //   373: invokevirtual 132	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   376: astore 13
    //   378: new 134	com/starcor/core/domain/AAACouponInfoItem
    //   381: dup
    //   382: invokespecial 135	com/starcor/core/domain/AAACouponInfoItem:<init>	()V
    //   385: astore 14
    //   387: aload 13
    //   389: ldc 137
    //   391: invokevirtual 69	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   394: ifeq +15 -> 409
    //   397: aload 14
    //   399: aload 13
    //   401: ldc 137
    //   403: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   406: putfield 139	com/starcor/core/domain/AAACouponInfoItem:coupon_id	Ljava/lang/String;
    //   409: aload 13
    //   411: ldc 141
    //   413: invokevirtual 69	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   416: ifeq +15 -> 431
    //   419: aload 14
    //   421: aload 13
    //   423: ldc 141
    //   425: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   428: putfield 143	com/starcor/core/domain/AAACouponInfoItem:type	Ljava/lang/String;
    //   431: aload 13
    //   433: ldc 145
    //   435: invokevirtual 69	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   438: ifeq +15 -> 453
    //   441: aload 14
    //   443: aload 13
    //   445: ldc 145
    //   447: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   450: putfield 147	com/starcor/core/domain/AAACouponInfoItem:asset_name	Ljava/lang/String;
    //   453: aload 13
    //   455: ldc 149
    //   457: invokevirtual 69	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   460: ifeq +15 -> 475
    //   463: aload 14
    //   465: aload 13
    //   467: ldc 149
    //   469: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   472: putfield 151	com/starcor/core/domain/AAACouponInfoItem:create_time	Ljava/lang/String;
    //   475: aload 13
    //   477: ldc 153
    //   479: invokevirtual 69	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   482: ifeq +15 -> 497
    //   485: aload 14
    //   487: aload 13
    //   489: ldc 153
    //   491: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   494: putfield 155	com/starcor/core/domain/AAACouponInfoItem:expire_time	Ljava/lang/String;
    //   497: aload 13
    //   499: ldc 157
    //   501: invokevirtual 69	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   504: ifeq +15 -> 519
    //   507: aload 14
    //   509: aload 13
    //   511: ldc 157
    //   513: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   516: putfield 159	com/starcor/core/domain/AAACouponInfoItem:from	Ljava/lang/String;
    //   519: aload 13
    //   521: ldc 99
    //   523: invokevirtual 69	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   526: ifeq +15 -> 541
    //   529: aload 14
    //   531: aload 13
    //   533: ldc 99
    //   535: invokevirtual 73	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   538: putfield 160	com/starcor/core/domain/AAACouponInfoItem:status	Ljava/lang/String;
    //   541: aload_0
    //   542: getfield 18	com/starcor/core/parser/json/AAAGetMovieCouponInfoSAXParseJson:info	Lcom/starcor/core/domain/AAAMovieCouponInfo;
    //   545: getfield 25	com/starcor/core/domain/AAAMovieCouponInfo:coupon_list	Ljava/util/List;
    //   548: aload 14
    //   550: invokeinterface 166 2 0
    //   555: pop
    //   556: iinc 12 1
    //   559: goto -200 -> 359
    //   562: astore 19
    //   564: aload_0
    //   565: getfield 18	com/starcor/core/parser/json/AAAGetMovieCouponInfoSAXParseJson:info	Lcom/starcor/core/domain/AAAMovieCouponInfo;
    //   568: iconst_0
    //   569: putfield 97	com/starcor/core/domain/AAAMovieCouponInfo:err	I
    //   572: goto -415 -> 157
    //   575: astore_3
    //   576: aload_3
    //   577: invokevirtual 169	org/json/JSONException:printStackTrace	()V
    //   580: aload_0
    //   581: getfield 18	com/starcor/core/parser/json/AAAGetMovieCouponInfoSAXParseJson:info	Lcom/starcor/core/domain/AAAMovieCouponInfo;
    //   584: areturn
    //   585: astore 18
    //   587: aload_0
    //   588: getfield 18	com/starcor/core/parser/json/AAAGetMovieCouponInfoSAXParseJson:info	Lcom/starcor/core/domain/AAAMovieCouponInfo;
    //   591: iconst_0
    //   592: putfield 107	com/starcor/core/domain/AAAMovieCouponInfo:all_count	I
    //   595: goto -354 -> 241
    //   598: astore 17
    //   600: aload_0
    //   601: getfield 18	com/starcor/core/parser/json/AAAGetMovieCouponInfoSAXParseJson:info	Lcom/starcor/core/domain/AAAMovieCouponInfo;
    //   604: iconst_0
    //   605: putfield 111	com/starcor/core/domain/AAAMovieCouponInfo:common_count	I
    //   608: goto -333 -> 275
    //   611: astore 16
    //   613: aload_0
    //   614: getfield 18	com/starcor/core/parser/json/AAAGetMovieCouponInfoSAXParseJson:info	Lcom/starcor/core/domain/AAAMovieCouponInfo;
    //   617: iconst_0
    //   618: putfield 115	com/starcor/core/domain/AAAMovieCouponInfo:special_count	I
    //   621: goto -312 -> 309
    //
    // Exception table:
    //   from	to	target	type
    //   137	157	562	java/lang/Exception
    //   6	73	575	org/json/JSONException
    //   73	97	575	org/json/JSONException
    //   97	132	575	org/json/JSONException
    //   137	157	575	org/json/JSONException
    //   157	181	575	org/json/JSONException
    //   181	216	575	org/json/JSONException
    //   221	241	575	org/json/JSONException
    //   241	250	575	org/json/JSONException
    //   255	275	575	org/json/JSONException
    //   275	284	575	org/json/JSONException
    //   289	309	575	org/json/JSONException
    //   309	356	575	org/json/JSONException
    //   359	409	575	org/json/JSONException
    //   409	431	575	org/json/JSONException
    //   431	453	575	org/json/JSONException
    //   453	475	575	org/json/JSONException
    //   475	497	575	org/json/JSONException
    //   497	519	575	org/json/JSONException
    //   519	541	575	org/json/JSONException
    //   541	556	575	org/json/JSONException
    //   564	572	575	org/json/JSONException
    //   587	595	575	org/json/JSONException
    //   600	608	575	org/json/JSONException
    //   613	621	575	org/json/JSONException
    //   221	241	585	java/lang/Exception
    //   255	275	598	java/lang/Exception
    //   289	309	611	java/lang/Exception
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.AAAGetMovieCouponInfoSAXParseJson
 * JD-Core Version:    0.6.2
 */