package com.starcor.core.parser.json;

import com.starcor.core.domain.VideoIdInfo;
import com.starcor.core.interfaces.IXmlParser;
import java.io.InputStream;

public class GetVideoIdInfoSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = "GetVideoIdInfoSAXParserJson";
  private VideoIdInfo videoIdInfo = new VideoIdInfo();

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
    //   6: new 31	java/lang/String
    //   9: dup
    //   10: aload_1
    //   11: invokespecial 34	java/lang/String:<init>	([B)V
    //   14: astore_2
    //   15: new 36	org/json/JSONObject
    //   18: dup
    //   19: aload_2
    //   20: invokespecial 39	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   23: astore_3
    //   24: aload_3
    //   25: ldc 41
    //   27: invokevirtual 45	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   30: ifeq +10 -> 40
    //   33: aload_3
    //   34: ldc 41
    //   36: invokevirtual 49	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   39: astore_2
    //   40: new 36	org/json/JSONObject
    //   43: dup
    //   44: aload_2
    //   45: invokespecial 39	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   48: astore 5
    //   50: aload 5
    //   52: ldc 51
    //   54: invokevirtual 45	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   57: ifeq +17 -> 74
    //   60: aload_0
    //   61: getfield 22	com/starcor/core/parser/json/GetVideoIdInfoSAXParserJson:videoIdInfo	Lcom/starcor/core/domain/VideoIdInfo;
    //   64: aload 5
    //   66: ldc 51
    //   68: invokevirtual 49	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   71: putfield 54	com/starcor/core/domain/VideoIdInfo:videoId	Ljava/lang/String;
    //   74: aload 5
    //   76: ldc 56
    //   78: invokevirtual 45	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   81: ifeq +17 -> 98
    //   84: aload_0
    //   85: getfield 22	com/starcor/core/parser/json/GetVideoIdInfoSAXParserJson:videoIdInfo	Lcom/starcor/core/domain/VideoIdInfo;
    //   88: aload 5
    //   90: ldc 56
    //   92: invokevirtual 49	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   95: putfield 59	com/starcor/core/domain/VideoIdInfo:videoName	Ljava/lang/String;
    //   98: aload_3
    //   99: ldc 61
    //   101: invokevirtual 45	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   104: ifeq +10 -> 114
    //   107: aload_3
    //   108: ldc 61
    //   110: invokevirtual 49	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   113: astore_2
    //   114: new 36	org/json/JSONObject
    //   117: dup
    //   118: aload_2
    //   119: invokespecial 39	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   122: astore 6
    //   124: aload_0
    //   125: getfield 22	com/starcor/core/parser/json/GetVideoIdInfoSAXParserJson:videoIdInfo	Lcom/starcor/core/domain/VideoIdInfo;
    //   128: aload 6
    //   130: ldc 51
    //   132: invokevirtual 49	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   135: invokestatic 67	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   138: invokevirtual 71	java/lang/Integer:intValue	()I
    //   141: putfield 75	com/starcor/core/domain/VideoIdInfo:apisodeId	I
    //   144: aload_0
    //   145: getfield 22	com/starcor/core/parser/json/GetVideoIdInfoSAXParserJson:videoIdInfo	Lcom/starcor/core/domain/VideoIdInfo;
    //   148: aload 6
    //   150: ldc 61
    //   152: invokevirtual 49	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   155: invokestatic 67	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   158: invokevirtual 71	java/lang/Integer:intValue	()I
    //   161: putfield 78	com/starcor/core/domain/VideoIdInfo:apisodeIndex	I
    //   164: aload 6
    //   166: ldc 56
    //   168: invokevirtual 45	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   171: ifeq +17 -> 188
    //   174: aload_0
    //   175: getfield 22	com/starcor/core/parser/json/GetVideoIdInfoSAXParserJson:videoIdInfo	Lcom/starcor/core/domain/VideoIdInfo;
    //   178: aload 6
    //   180: ldc 56
    //   182: invokevirtual 49	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   185: putfield 81	com/starcor/core/domain/VideoIdInfo:apisodeName	Ljava/lang/String;
    //   188: aload_3
    //   189: ldc 83
    //   191: invokevirtual 45	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   194: ifeq +10 -> 204
    //   197: aload_3
    //   198: ldc 83
    //   200: invokevirtual 49	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   203: astore_2
    //   204: new 36	org/json/JSONObject
    //   207: dup
    //   208: aload_2
    //   209: invokespecial 39	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   212: astore 9
    //   214: aload_0
    //   215: getfield 22	com/starcor/core/parser/json/GetVideoIdInfoSAXParserJson:videoIdInfo	Lcom/starcor/core/domain/VideoIdInfo;
    //   218: aload 9
    //   220: ldc 51
    //   222: invokevirtual 49	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   225: invokestatic 67	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   228: invokevirtual 71	java/lang/Integer:intValue	()I
    //   231: putfield 86	com/starcor/core/domain/VideoIdInfo:fileId	I
    //   234: aload_0
    //   235: getfield 22	com/starcor/core/parser/json/GetVideoIdInfoSAXParserJson:videoIdInfo	Lcom/starcor/core/domain/VideoIdInfo;
    //   238: aload 9
    //   240: ldc 88
    //   242: invokevirtual 49	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   245: invokestatic 67	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   248: invokevirtual 71	java/lang/Integer:intValue	()I
    //   251: putfield 90	com/starcor/core/domain/VideoIdInfo:dimensions	I
    //   254: aload 9
    //   256: ldc 92
    //   258: invokevirtual 45	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   261: ifeq +17 -> 278
    //   264: aload_0
    //   265: getfield 22	com/starcor/core/parser/json/GetVideoIdInfoSAXParserJson:videoIdInfo	Lcom/starcor/core/domain/VideoIdInfo;
    //   268: aload 9
    //   270: ldc 92
    //   272: invokevirtual 49	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   275: putfield 94	com/starcor/core/domain/VideoIdInfo:quality	Ljava/lang/String;
    //   278: aload_0
    //   279: getfield 22	com/starcor/core/parser/json/GetVideoIdInfoSAXParserJson:videoIdInfo	Lcom/starcor/core/domain/VideoIdInfo;
    //   282: areturn
    //   283: astore 7
    //   285: aload_0
    //   286: getfield 22	com/starcor/core/parser/json/GetVideoIdInfoSAXParserJson:videoIdInfo	Lcom/starcor/core/domain/VideoIdInfo;
    //   289: iconst_0
    //   290: putfield 75	com/starcor/core/domain/VideoIdInfo:apisodeId	I
    //   293: goto -149 -> 144
    //   296: astore 4
    //   298: aload 4
    //   300: invokevirtual 97	java/lang/Exception:printStackTrace	()V
    //   303: goto -25 -> 278
    //   306: astore 8
    //   308: aload_0
    //   309: getfield 22	com/starcor/core/parser/json/GetVideoIdInfoSAXParserJson:videoIdInfo	Lcom/starcor/core/domain/VideoIdInfo;
    //   312: iconst_0
    //   313: putfield 78	com/starcor/core/domain/VideoIdInfo:apisodeIndex	I
    //   316: goto -152 -> 164
    //   319: astore 10
    //   321: aload_0
    //   322: getfield 22	com/starcor/core/parser/json/GetVideoIdInfoSAXParserJson:videoIdInfo	Lcom/starcor/core/domain/VideoIdInfo;
    //   325: iconst_0
    //   326: putfield 86	com/starcor/core/domain/VideoIdInfo:fileId	I
    //   329: goto -95 -> 234
    //   332: astore 11
    //   334: aload_0
    //   335: getfield 22	com/starcor/core/parser/json/GetVideoIdInfoSAXParserJson:videoIdInfo	Lcom/starcor/core/domain/VideoIdInfo;
    //   338: iconst_0
    //   339: putfield 90	com/starcor/core/domain/VideoIdInfo:dimensions	I
    //   342: goto -88 -> 254
    //
    // Exception table:
    //   from	to	target	type
    //   124	144	283	java/lang/NumberFormatException
    //   6	40	296	java/lang/Exception
    //   40	74	296	java/lang/Exception
    //   74	98	296	java/lang/Exception
    //   98	114	296	java/lang/Exception
    //   114	124	296	java/lang/Exception
    //   124	144	296	java/lang/Exception
    //   144	164	296	java/lang/Exception
    //   164	188	296	java/lang/Exception
    //   188	204	296	java/lang/Exception
    //   204	214	296	java/lang/Exception
    //   214	234	296	java/lang/Exception
    //   234	254	296	java/lang/Exception
    //   254	278	296	java/lang/Exception
    //   285	293	296	java/lang/Exception
    //   308	316	296	java/lang/Exception
    //   321	329	296	java/lang/Exception
    //   334	342	296	java/lang/Exception
    //   144	164	306	java/lang/NumberFormatException
    //   214	234	319	java/lang/NumberFormatException
    //   234	254	332	java/lang/NumberFormatException
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetVideoIdInfoSAXParserJson
 * JD-Core Version:    0.6.2
 */