package com.starcor.core.parser.json;

import com.starcor.core.domain.PlayInfo;
import com.starcor.core.interfaces.IXmlParser;
import java.io.InputStream;

public class GetPlayInfoSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private PlayInfo playInfo = new PlayInfo();

  // ERROR //
  private void getTimeNodeData(org.json.JSONArray paramJSONArray)
  {
    // Byte code:
    //   0: new 24	java/util/ArrayList
    //   3: dup
    //   4: invokespecial 25	java/util/ArrayList:<init>	()V
    //   7: astore_2
    //   8: iconst_0
    //   9: istore_3
    //   10: iload_3
    //   11: aload_1
    //   12: invokevirtual 31	org/json/JSONArray:length	()I
    //   15: if_icmpge +218 -> 233
    //   18: new 33	com/starcor/core/domain/MediaTimeNode
    //   21: dup
    //   22: invokespecial 34	com/starcor/core/domain/MediaTimeNode:<init>	()V
    //   25: astore 5
    //   27: aload_1
    //   28: iload_3
    //   29: invokevirtual 38	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   32: astore 6
    //   34: aload 6
    //   36: ldc 40
    //   38: invokevirtual 46	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   41: ifeq +15 -> 56
    //   44: aload 5
    //   46: aload 6
    //   48: ldc 40
    //   50: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   53: putfield 53	com/starcor/core/domain/MediaTimeNode:name	Ljava/lang/String;
    //   56: aload 6
    //   58: ldc 55
    //   60: invokevirtual 46	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   63: ifeq +15 -> 78
    //   66: aload 5
    //   68: aload 6
    //   70: ldc 55
    //   72: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   75: putfield 53	com/starcor/core/domain/MediaTimeNode:name	Ljava/lang/String;
    //   78: aload 6
    //   80: ldc 57
    //   82: invokevirtual 46	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   85: ifeq +21 -> 106
    //   88: aload 5
    //   90: aload 6
    //   92: ldc 57
    //   94: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   97: invokestatic 63	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   100: invokevirtual 66	java/lang/Integer:intValue	()I
    //   103: putfield 69	com/starcor/core/domain/MediaTimeNode:type	I
    //   106: aload 6
    //   108: ldc 71
    //   110: invokevirtual 46	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   113: ifeq +21 -> 134
    //   116: aload 5
    //   118: aload 6
    //   120: ldc 71
    //   122: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   125: invokestatic 63	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   128: invokevirtual 66	java/lang/Integer:intValue	()I
    //   131: putfield 73	com/starcor/core/domain/MediaTimeNode:begin	I
    //   134: aload 6
    //   136: ldc 75
    //   138: invokevirtual 46	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   141: ifeq +21 -> 162
    //   144: aload 5
    //   146: aload 6
    //   148: ldc 75
    //   150: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   153: invokestatic 63	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   156: invokevirtual 66	java/lang/Integer:intValue	()I
    //   159: putfield 77	com/starcor/core/domain/MediaTimeNode:end	I
    //   162: aload_2
    //   163: aload 5
    //   165: invokeinterface 83 2 0
    //   170: pop
    //   171: iinc 3 1
    //   174: goto -164 -> 10
    //   177: astore 7
    //   179: aload 7
    //   181: invokevirtual 86	java/lang/Exception:printStackTrace	()V
    //   184: aload 5
    //   186: iconst_m1
    //   187: putfield 69	com/starcor/core/domain/MediaTimeNode:type	I
    //   190: goto -84 -> 106
    //   193: astore 4
    //   195: aload 4
    //   197: invokevirtual 86	java/lang/Exception:printStackTrace	()V
    //   200: return
    //   201: astore 8
    //   203: aload 8
    //   205: invokevirtual 86	java/lang/Exception:printStackTrace	()V
    //   208: aload 5
    //   210: iconst_m1
    //   211: putfield 73	com/starcor/core/domain/MediaTimeNode:begin	I
    //   214: goto -80 -> 134
    //   217: astore 9
    //   219: aload 9
    //   221: invokevirtual 86	java/lang/Exception:printStackTrace	()V
    //   224: aload 5
    //   226: iconst_m1
    //   227: putfield 77	com/starcor/core/domain/MediaTimeNode:end	I
    //   230: goto -68 -> 162
    //   233: aload_0
    //   234: getfield 18	com/starcor/core/parser/json/GetPlayInfoSAXParserJson:playInfo	Lcom/starcor/core/domain/PlayInfo;
    //   237: aload_2
    //   238: putfield 90	com/starcor/core/domain/PlayInfo:mediaTimeNodeList	Ljava/util/List;
    //   241: return
    //
    // Exception table:
    //   from	to	target	type
    //   78	106	177	java/lang/Exception
    //   10	56	193	java/lang/Exception
    //   56	78	193	java/lang/Exception
    //   162	171	193	java/lang/Exception
    //   179	190	193	java/lang/Exception
    //   203	214	193	java/lang/Exception
    //   219	230	193	java/lang/Exception
    //   233	241	193	java/lang/Exception
    //   106	134	201	java/lang/Exception
    //   134	162	217	java/lang/Exception
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
    //   6: new 97	java/lang/String
    //   9: dup
    //   10: aload_1
    //   11: invokespecial 100	java/lang/String:<init>	([B)V
    //   14: astore_2
    //   15: new 42	org/json/JSONObject
    //   18: dup
    //   19: aload_2
    //   20: invokespecial 103	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   23: astore_3
    //   24: aload_3
    //   25: ldc 105
    //   27: invokevirtual 46	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   30: ifeq +10 -> 40
    //   33: aload_3
    //   34: ldc 105
    //   36: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   39: astore_2
    //   40: new 42	org/json/JSONObject
    //   43: dup
    //   44: aload_2
    //   45: invokespecial 103	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   48: astore 6
    //   50: aload 6
    //   52: ldc 107
    //   54: invokevirtual 46	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   57: ifeq +17 -> 74
    //   60: aload_0
    //   61: getfield 18	com/starcor/core/parser/json/GetPlayInfoSAXParserJson:playInfo	Lcom/starcor/core/domain/PlayInfo;
    //   64: aload 6
    //   66: ldc 107
    //   68: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   71: putfield 110	com/starcor/core/domain/PlayInfo:playUrl	Ljava/lang/String;
    //   74: aload 6
    //   76: ldc 112
    //   78: invokevirtual 46	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   81: ifeq +17 -> 98
    //   84: aload_0
    //   85: getfield 18	com/starcor/core/parser/json/GetPlayInfoSAXParserJson:playInfo	Lcom/starcor/core/domain/PlayInfo;
    //   88: aload 6
    //   90: ldc 112
    //   92: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   95: putfield 114	com/starcor/core/domain/PlayInfo:reason	Ljava/lang/String;
    //   98: aload 6
    //   100: ldc 116
    //   102: invokevirtual 46	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   105: ifeq +17 -> 122
    //   108: aload_0
    //   109: getfield 18	com/starcor/core/parser/json/GetPlayInfoSAXParserJson:playInfo	Lcom/starcor/core/domain/PlayInfo;
    //   112: aload 6
    //   114: ldc 116
    //   116: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   119: putfield 118	com/starcor/core/domain/PlayInfo:begin_time	Ljava/lang/String;
    //   122: aload 6
    //   124: ldc 120
    //   126: invokevirtual 46	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   129: ifeq +17 -> 146
    //   132: aload_0
    //   133: getfield 18	com/starcor/core/parser/json/GetPlayInfoSAXParserJson:playInfo	Lcom/starcor/core/domain/PlayInfo;
    //   136: aload 6
    //   138: ldc 120
    //   140: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   143: putfield 122	com/starcor/core/domain/PlayInfo:time_len	Ljava/lang/String;
    //   146: aload 6
    //   148: ldc 124
    //   150: invokevirtual 46	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   153: ifeq +17 -> 170
    //   156: aload_0
    //   157: getfield 18	com/starcor/core/parser/json/GetPlayInfoSAXParserJson:playInfo	Lcom/starcor/core/domain/PlayInfo;
    //   160: aload 6
    //   162: ldc 124
    //   164: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   167: putfield 126	com/starcor/core/domain/PlayInfo:fileId	Ljava/lang/String;
    //   170: aload 6
    //   172: ldc 128
    //   174: invokevirtual 46	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   177: ifeq +17 -> 194
    //   180: aload_0
    //   181: getfield 18	com/starcor/core/parser/json/GetPlayInfoSAXParserJson:playInfo	Lcom/starcor/core/domain/PlayInfo;
    //   184: aload 6
    //   186: ldc 128
    //   188: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   191: putfield 130	com/starcor/core/domain/PlayInfo:quality	Ljava/lang/String;
    //   194: aload 6
    //   196: ldc 132
    //   198: invokevirtual 46	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   201: istore 7
    //   203: iload 7
    //   205: ifeq +23 -> 228
    //   208: aload_0
    //   209: getfield 18	com/starcor/core/parser/json/GetPlayInfoSAXParserJson:playInfo	Lcom/starcor/core/domain/PlayInfo;
    //   212: aload 6
    //   214: ldc 132
    //   216: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   219: invokestatic 63	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   222: invokevirtual 66	java/lang/Integer:intValue	()I
    //   225: putfield 134	com/starcor/core/domain/PlayInfo:dimensions	I
    //   228: aload 6
    //   230: ldc 136
    //   232: invokevirtual 46	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   235: istore 8
    //   237: iload 8
    //   239: ifeq +23 -> 262
    //   242: aload_0
    //   243: getfield 18	com/starcor/core/parser/json/GetPlayInfoSAXParserJson:playInfo	Lcom/starcor/core/domain/PlayInfo;
    //   246: aload 6
    //   248: ldc 136
    //   250: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   253: invokestatic 63	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   256: invokevirtual 66	java/lang/Integer:intValue	()I
    //   259: putfield 138	com/starcor/core/domain/PlayInfo:IsOtherCdn	I
    //   262: aload 6
    //   264: ldc 140
    //   266: invokevirtual 46	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   269: istore 9
    //   271: iload 9
    //   273: ifeq +23 -> 296
    //   276: aload_0
    //   277: getfield 18	com/starcor/core/parser/json/GetPlayInfoSAXParserJson:playInfo	Lcom/starcor/core/domain/PlayInfo;
    //   280: aload 6
    //   282: ldc 140
    //   284: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   287: invokestatic 63	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   290: invokevirtual 66	java/lang/Integer:intValue	()I
    //   293: putfield 142	com/starcor/core/domain/PlayInfo:state	I
    //   296: aload 6
    //   298: ldc 144
    //   300: invokevirtual 46	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   303: ifeq +21 -> 324
    //   306: aload_0
    //   307: new 27	org/json/JSONArray
    //   310: dup
    //   311: aload 6
    //   313: ldc 144
    //   315: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   318: invokespecial 145	org/json/JSONArray:<init>	(Ljava/lang/String;)V
    //   321: invokespecial 147	com/starcor/core/parser/json/GetPlayInfoSAXParserJson:getTimeNodeData	(Lorg/json/JSONArray;)V
    //   324: ldc 149
    //   326: new 151	java/lang/StringBuilder
    //   329: dup
    //   330: invokespecial 152	java/lang/StringBuilder:<init>	()V
    //   333: ldc 154
    //   335: invokevirtual 158	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   338: aload_0
    //   339: getfield 18	com/starcor/core/parser/json/GetPlayInfoSAXParserJson:playInfo	Lcom/starcor/core/domain/PlayInfo;
    //   342: invokevirtual 161	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   345: invokevirtual 165	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   348: invokestatic 171	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   351: pop
    //   352: aload_0
    //   353: getfield 18	com/starcor/core/parser/json/GetPlayInfoSAXParserJson:playInfo	Lcom/starcor/core/domain/PlayInfo;
    //   356: areturn
    //   357: astore 12
    //   359: aload_0
    //   360: getfield 18	com/starcor/core/parser/json/GetPlayInfoSAXParserJson:playInfo	Lcom/starcor/core/domain/PlayInfo;
    //   363: iconst_m1
    //   364: putfield 134	com/starcor/core/domain/PlayInfo:dimensions	I
    //   367: goto -139 -> 228
    //   370: astore 4
    //   372: aload 4
    //   374: invokevirtual 86	java/lang/Exception:printStackTrace	()V
    //   377: goto -53 -> 324
    //   380: astore 11
    //   382: aload_0
    //   383: getfield 18	com/starcor/core/parser/json/GetPlayInfoSAXParserJson:playInfo	Lcom/starcor/core/domain/PlayInfo;
    //   386: iconst_0
    //   387: putfield 138	com/starcor/core/domain/PlayInfo:IsOtherCdn	I
    //   390: goto -128 -> 262
    //   393: astore 10
    //   395: aload_0
    //   396: getfield 18	com/starcor/core/parser/json/GetPlayInfoSAXParserJson:playInfo	Lcom/starcor/core/domain/PlayInfo;
    //   399: iconst_m1
    //   400: putfield 142	com/starcor/core/domain/PlayInfo:state	I
    //   403: goto -107 -> 296
    //
    // Exception table:
    //   from	to	target	type
    //   208	228	357	java/lang/NumberFormatException
    //   6	40	370	java/lang/Exception
    //   40	74	370	java/lang/Exception
    //   74	98	370	java/lang/Exception
    //   98	122	370	java/lang/Exception
    //   122	146	370	java/lang/Exception
    //   146	170	370	java/lang/Exception
    //   170	194	370	java/lang/Exception
    //   194	203	370	java/lang/Exception
    //   208	228	370	java/lang/Exception
    //   228	237	370	java/lang/Exception
    //   242	262	370	java/lang/Exception
    //   262	271	370	java/lang/Exception
    //   276	296	370	java/lang/Exception
    //   296	324	370	java/lang/Exception
    //   359	367	370	java/lang/Exception
    //   382	390	370	java/lang/Exception
    //   395	403	370	java/lang/Exception
    //   242	262	380	java/lang/NumberFormatException
    //   276	296	393	java/lang/NumberFormatException
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetPlayInfoSAXParserJson
 * JD-Core Version:    0.6.2
 */