package com.starcor.core.parser.json;

import com.starcor.core.domain.UserCenterInfo;
import com.starcor.core.interfaces.IXmlParser;
import java.io.InputStream;

public class GetUserCenterInfoSAXParserJson<Result>
  implements IXmlParser<Result>
{
  UserCenterInfo info = new UserCenterInfo();

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
    //   6: new 27	java/lang/String
    //   9: dup
    //   10: aload_1
    //   11: invokespecial 30	java/lang/String:<init>	([B)V
    //   14: astore_2
    //   15: ldc 32
    //   17: new 34	java/lang/StringBuilder
    //   20: dup
    //   21: invokespecial 35	java/lang/StringBuilder:<init>	()V
    //   24: ldc 37
    //   26: invokevirtual 41	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   29: aload_2
    //   30: invokevirtual 41	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   33: invokevirtual 45	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   36: invokestatic 51	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   39: new 53	org/json/JSONObject
    //   42: dup
    //   43: aload_2
    //   44: invokespecial 56	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   47: astore 4
    //   49: aload 4
    //   51: ldc 58
    //   53: invokevirtual 62	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   56: ifeq +17 -> 73
    //   59: aload_0
    //   60: getfield 18	com/starcor/core/parser/json/GetUserCenterInfoSAXParserJson:info	Lcom/starcor/core/domain/UserCenterInfo;
    //   63: aload 4
    //   65: ldc 58
    //   67: invokevirtual 66	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   70: putfield 69	com/starcor/core/domain/UserCenterInfo:state	Ljava/lang/String;
    //   73: aload 4
    //   75: ldc 71
    //   77: invokevirtual 62	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   80: ifeq +17 -> 97
    //   83: aload_0
    //   84: getfield 18	com/starcor/core/parser/json/GetUserCenterInfoSAXParserJson:info	Lcom/starcor/core/domain/UserCenterInfo;
    //   87: aload 4
    //   89: ldc 71
    //   91: invokevirtual 66	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   94: putfield 73	com/starcor/core/domain/UserCenterInfo:reason	Ljava/lang/String;
    //   97: aload 4
    //   99: ldc 75
    //   101: invokevirtual 62	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   104: ifeq +345 -> 449
    //   107: new 53	org/json/JSONObject
    //   110: dup
    //   111: aload 4
    //   113: ldc 75
    //   115: invokevirtual 66	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   118: invokespecial 56	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   121: astore 5
    //   123: aload 5
    //   125: ldc 77
    //   127: invokevirtual 62	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   130: istore 6
    //   132: iload 6
    //   134: ifeq +23 -> 157
    //   137: aload_0
    //   138: getfield 18	com/starcor/core/parser/json/GetUserCenterInfoSAXParserJson:info	Lcom/starcor/core/domain/UserCenterInfo;
    //   141: aload 5
    //   143: ldc 77
    //   145: invokevirtual 66	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   148: invokestatic 83	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   151: invokevirtual 87	java/lang/Integer:intValue	()I
    //   154: putfield 90	com/starcor/core/domain/UserCenterInfo:err	I
    //   157: aload 5
    //   159: ldc 92
    //   161: invokevirtual 62	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   164: ifeq +17 -> 181
    //   167: aload_0
    //   168: getfield 18	com/starcor/core/parser/json/GetUserCenterInfoSAXParserJson:info	Lcom/starcor/core/domain/UserCenterInfo;
    //   171: aload 5
    //   173: ldc 92
    //   175: invokevirtual 66	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   178: putfield 94	com/starcor/core/domain/UserCenterInfo:status	Ljava/lang/String;
    //   181: aload 5
    //   183: ldc 96
    //   185: invokevirtual 62	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   188: ifeq +261 -> 449
    //   191: new 53	org/json/JSONObject
    //   194: dup
    //   195: aload 5
    //   197: ldc 96
    //   199: invokevirtual 66	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   202: invokespecial 56	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   205: astore 7
    //   207: aload 7
    //   209: ldc 98
    //   211: invokevirtual 62	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   214: istore 8
    //   216: iload 8
    //   218: ifeq +23 -> 241
    //   221: aload_0
    //   222: getfield 18	com/starcor/core/parser/json/GetUserCenterInfoSAXParserJson:info	Lcom/starcor/core/domain/UserCenterInfo;
    //   225: aload 7
    //   227: ldc 98
    //   229: invokevirtual 66	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   232: invokestatic 83	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   235: invokevirtual 87	java/lang/Integer:intValue	()I
    //   238: putfield 101	com/starcor/core/domain/UserCenterInfo:vipId	I
    //   241: aload 7
    //   243: ldc 103
    //   245: invokevirtual 62	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   248: ifeq +17 -> 265
    //   251: aload_0
    //   252: getfield 18	com/starcor/core/parser/json/GetUserCenterInfoSAXParserJson:info	Lcom/starcor/core/domain/UserCenterInfo;
    //   255: aload 7
    //   257: ldc 103
    //   259: invokevirtual 66	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   262: putfield 106	com/starcor/core/domain/UserCenterInfo:vipName	Ljava/lang/String;
    //   265: aload 7
    //   267: ldc 108
    //   269: invokevirtual 62	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   272: istore 9
    //   274: iload 9
    //   276: ifeq +23 -> 299
    //   279: aload_0
    //   280: getfield 18	com/starcor/core/parser/json/GetUserCenterInfoSAXParserJson:info	Lcom/starcor/core/domain/UserCenterInfo;
    //   283: aload 7
    //   285: ldc 108
    //   287: invokevirtual 66	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   290: invokestatic 83	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   293: invokevirtual 87	java/lang/Integer:intValue	()I
    //   296: putfield 111	com/starcor/core/domain/UserCenterInfo:viPower	I
    //   299: aload 7
    //   301: ldc 113
    //   303: invokevirtual 62	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   306: istore 10
    //   308: iload 10
    //   310: ifeq +23 -> 333
    //   313: aload_0
    //   314: getfield 18	com/starcor/core/parser/json/GetUserCenterInfoSAXParserJson:info	Lcom/starcor/core/domain/UserCenterInfo;
    //   317: aload 7
    //   319: ldc 113
    //   321: invokevirtual 66	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   324: invokestatic 118	java/lang/Float:valueOf	(Ljava/lang/String;)Ljava/lang/Float;
    //   327: invokevirtual 122	java/lang/Float:floatValue	()F
    //   330: putfield 125	com/starcor/core/domain/UserCenterInfo:balance	F
    //   333: aload 7
    //   335: ldc 127
    //   337: invokevirtual 62	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   340: ifeq +17 -> 357
    //   343: aload_0
    //   344: getfield 18	com/starcor/core/parser/json/GetUserCenterInfoSAXParserJson:info	Lcom/starcor/core/domain/UserCenterInfo;
    //   347: aload 7
    //   349: ldc 127
    //   351: invokevirtual 66	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   354: putfield 130	com/starcor/core/domain/UserCenterInfo:loginaccount	Ljava/lang/String;
    //   357: aload 7
    //   359: ldc 132
    //   361: invokevirtual 62	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   364: istore 11
    //   366: iload 11
    //   368: ifeq +23 -> 391
    //   371: aload_0
    //   372: getfield 18	com/starcor/core/parser/json/GetUserCenterInfoSAXParserJson:info	Lcom/starcor/core/domain/UserCenterInfo;
    //   375: aload 7
    //   377: ldc 132
    //   379: invokevirtual 66	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   382: invokestatic 83	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   385: invokevirtual 87	java/lang/Integer:intValue	()I
    //   388: putfield 135	com/starcor/core/domain/UserCenterInfo:vipEndDays	I
    //   391: aload 7
    //   393: ldc 137
    //   395: invokevirtual 62	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   398: ifeq +17 -> 415
    //   401: aload_0
    //   402: getfield 18	com/starcor/core/parser/json/GetUserCenterInfoSAXParserJson:info	Lcom/starcor/core/domain/UserCenterInfo;
    //   405: aload 7
    //   407: ldc 137
    //   409: invokevirtual 66	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   412: putfield 140	com/starcor/core/domain/UserCenterInfo:vipEndDate	Ljava/lang/String;
    //   415: aload 7
    //   417: ldc 142
    //   419: invokevirtual 62	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   422: istore 12
    //   424: iload 12
    //   426: ifeq +23 -> 449
    //   429: aload_0
    //   430: getfield 18	com/starcor/core/parser/json/GetUserCenterInfoSAXParserJson:info	Lcom/starcor/core/domain/UserCenterInfo;
    //   433: aload 7
    //   435: ldc 142
    //   437: invokevirtual 66	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   440: invokestatic 83	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   443: invokevirtual 87	java/lang/Integer:intValue	()I
    //   446: putfield 144	com/starcor/core/domain/UserCenterInfo:account_type	I
    //   449: aload_0
    //   450: getfield 18	com/starcor/core/parser/json/GetUserCenterInfoSAXParserJson:info	Lcom/starcor/core/domain/UserCenterInfo;
    //   453: areturn
    //   454: astore 18
    //   456: aload_0
    //   457: getfield 18	com/starcor/core/parser/json/GetUserCenterInfoSAXParserJson:info	Lcom/starcor/core/domain/UserCenterInfo;
    //   460: iconst_0
    //   461: putfield 90	com/starcor/core/domain/UserCenterInfo:err	I
    //   464: goto -307 -> 157
    //   467: astore_3
    //   468: aload_3
    //   469: invokevirtual 147	org/json/JSONException:printStackTrace	()V
    //   472: goto -23 -> 449
    //   475: astore 17
    //   477: aload_0
    //   478: getfield 18	com/starcor/core/parser/json/GetUserCenterInfoSAXParserJson:info	Lcom/starcor/core/domain/UserCenterInfo;
    //   481: iconst_0
    //   482: putfield 101	com/starcor/core/domain/UserCenterInfo:vipId	I
    //   485: goto -244 -> 241
    //   488: astore 16
    //   490: aload_0
    //   491: getfield 18	com/starcor/core/parser/json/GetUserCenterInfoSAXParserJson:info	Lcom/starcor/core/domain/UserCenterInfo;
    //   494: iconst_0
    //   495: putfield 111	com/starcor/core/domain/UserCenterInfo:viPower	I
    //   498: goto -199 -> 299
    //   501: astore 15
    //   503: aload_0
    //   504: getfield 18	com/starcor/core/parser/json/GetUserCenterInfoSAXParserJson:info	Lcom/starcor/core/domain/UserCenterInfo;
    //   507: fconst_0
    //   508: putfield 125	com/starcor/core/domain/UserCenterInfo:balance	F
    //   511: goto -178 -> 333
    //   514: astore 14
    //   516: aload_0
    //   517: getfield 18	com/starcor/core/parser/json/GetUserCenterInfoSAXParserJson:info	Lcom/starcor/core/domain/UserCenterInfo;
    //   520: iconst_0
    //   521: putfield 135	com/starcor/core/domain/UserCenterInfo:vipEndDays	I
    //   524: goto -133 -> 391
    //   527: astore 13
    //   529: aload_0
    //   530: getfield 18	com/starcor/core/parser/json/GetUserCenterInfoSAXParserJson:info	Lcom/starcor/core/domain/UserCenterInfo;
    //   533: iconst_0
    //   534: putfield 144	com/starcor/core/domain/UserCenterInfo:account_type	I
    //   537: goto -88 -> 449
    //
    // Exception table:
    //   from	to	target	type
    //   137	157	454	java/lang/Exception
    //   6	73	467	org/json/JSONException
    //   73	97	467	org/json/JSONException
    //   97	132	467	org/json/JSONException
    //   137	157	467	org/json/JSONException
    //   157	181	467	org/json/JSONException
    //   181	216	467	org/json/JSONException
    //   221	241	467	org/json/JSONException
    //   241	265	467	org/json/JSONException
    //   265	274	467	org/json/JSONException
    //   279	299	467	org/json/JSONException
    //   299	308	467	org/json/JSONException
    //   313	333	467	org/json/JSONException
    //   333	357	467	org/json/JSONException
    //   357	366	467	org/json/JSONException
    //   371	391	467	org/json/JSONException
    //   391	415	467	org/json/JSONException
    //   415	424	467	org/json/JSONException
    //   429	449	467	org/json/JSONException
    //   456	464	467	org/json/JSONException
    //   477	485	467	org/json/JSONException
    //   490	498	467	org/json/JSONException
    //   503	511	467	org/json/JSONException
    //   516	524	467	org/json/JSONException
    //   529	537	467	org/json/JSONException
    //   221	241	475	java/lang/Exception
    //   279	299	488	java/lang/Exception
    //   313	333	501	java/lang/Exception
    //   371	391	514	java/lang/Exception
    //   429	449	527	java/lang/Exception
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetUserCenterInfoSAXParserJson
 * JD-Core Version:    0.6.2
 */