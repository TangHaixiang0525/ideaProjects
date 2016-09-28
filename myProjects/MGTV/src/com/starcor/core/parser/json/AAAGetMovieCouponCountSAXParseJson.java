package com.starcor.core.parser.json;

import com.starcor.core.domain.AAAMovieCouponCount;
import com.starcor.core.interfaces.IXmlParser;
import java.io.InputStream;

public class AAAGetMovieCouponCountSAXParseJson<Result>
  implements IXmlParser<Result>
{
  private AAAMovieCouponCount info = new AAAMovieCouponCount();

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
    //   60: getfield 18	com/starcor/core/parser/json/AAAGetMovieCouponCountSAXParseJson:info	Lcom/starcor/core/domain/AAAMovieCouponCount;
    //   63: aload 4
    //   65: ldc 58
    //   67: invokevirtual 66	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   70: putfield 69	com/starcor/core/domain/AAAMovieCouponCount:state	Ljava/lang/String;
    //   73: aload 4
    //   75: ldc 71
    //   77: invokevirtual 62	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   80: ifeq +17 -> 97
    //   83: aload_0
    //   84: getfield 18	com/starcor/core/parser/json/AAAGetMovieCouponCountSAXParseJson:info	Lcom/starcor/core/domain/AAAMovieCouponCount;
    //   87: aload 4
    //   89: ldc 71
    //   91: invokevirtual 66	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   94: putfield 73	com/starcor/core/domain/AAAMovieCouponCount:reason	Ljava/lang/String;
    //   97: aload 4
    //   99: ldc 75
    //   101: invokevirtual 62	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   104: ifeq +171 -> 275
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
    //   138: getfield 18	com/starcor/core/parser/json/AAAGetMovieCouponCountSAXParseJson:info	Lcom/starcor/core/domain/AAAMovieCouponCount;
    //   141: aload 5
    //   143: ldc 77
    //   145: invokevirtual 66	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   148: invokestatic 83	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   151: invokevirtual 87	java/lang/Integer:intValue	()I
    //   154: putfield 90	com/starcor/core/domain/AAAMovieCouponCount:err	I
    //   157: aload 5
    //   159: ldc 92
    //   161: invokevirtual 62	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   164: ifeq +17 -> 181
    //   167: aload_0
    //   168: getfield 18	com/starcor/core/parser/json/AAAGetMovieCouponCountSAXParseJson:info	Lcom/starcor/core/domain/AAAMovieCouponCount;
    //   171: aload 5
    //   173: ldc 92
    //   175: invokevirtual 66	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   178: putfield 94	com/starcor/core/domain/AAAMovieCouponCount:status	Ljava/lang/String;
    //   181: aload 5
    //   183: ldc 96
    //   185: invokevirtual 62	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   188: ifeq +87 -> 275
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
    //   222: getfield 18	com/starcor/core/parser/json/AAAGetMovieCouponCountSAXParseJson:info	Lcom/starcor/core/domain/AAAMovieCouponCount;
    //   225: aload 7
    //   227: ldc 98
    //   229: invokevirtual 66	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   232: invokestatic 83	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   235: invokevirtual 87	java/lang/Integer:intValue	()I
    //   238: putfield 100	com/starcor/core/domain/AAAMovieCouponCount:common_count	I
    //   241: aload 7
    //   243: ldc 102
    //   245: invokevirtual 62	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   248: istore 9
    //   250: iload 9
    //   252: ifeq +23 -> 275
    //   255: aload_0
    //   256: getfield 18	com/starcor/core/parser/json/AAAGetMovieCouponCountSAXParseJson:info	Lcom/starcor/core/domain/AAAMovieCouponCount;
    //   259: aload 7
    //   261: ldc 102
    //   263: invokevirtual 66	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   266: invokestatic 83	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   269: invokevirtual 87	java/lang/Integer:intValue	()I
    //   272: putfield 104	com/starcor/core/domain/AAAMovieCouponCount:special_count	I
    //   275: aload_0
    //   276: getfield 18	com/starcor/core/parser/json/AAAGetMovieCouponCountSAXParseJson:info	Lcom/starcor/core/domain/AAAMovieCouponCount;
    //   279: areturn
    //   280: astore 12
    //   282: aload_0
    //   283: getfield 18	com/starcor/core/parser/json/AAAGetMovieCouponCountSAXParseJson:info	Lcom/starcor/core/domain/AAAMovieCouponCount;
    //   286: iconst_0
    //   287: putfield 90	com/starcor/core/domain/AAAMovieCouponCount:err	I
    //   290: goto -133 -> 157
    //   293: astore_3
    //   294: aload_3
    //   295: invokevirtual 107	org/json/JSONException:printStackTrace	()V
    //   298: goto -23 -> 275
    //   301: astore 11
    //   303: aload_0
    //   304: getfield 18	com/starcor/core/parser/json/AAAGetMovieCouponCountSAXParseJson:info	Lcom/starcor/core/domain/AAAMovieCouponCount;
    //   307: iconst_0
    //   308: putfield 100	com/starcor/core/domain/AAAMovieCouponCount:common_count	I
    //   311: goto -70 -> 241
    //   314: astore 10
    //   316: aload_0
    //   317: getfield 18	com/starcor/core/parser/json/AAAGetMovieCouponCountSAXParseJson:info	Lcom/starcor/core/domain/AAAMovieCouponCount;
    //   320: iconst_0
    //   321: putfield 104	com/starcor/core/domain/AAAMovieCouponCount:special_count	I
    //   324: goto -49 -> 275
    //
    // Exception table:
    //   from	to	target	type
    //   137	157	280	java/lang/Exception
    //   6	73	293	org/json/JSONException
    //   73	97	293	org/json/JSONException
    //   97	132	293	org/json/JSONException
    //   137	157	293	org/json/JSONException
    //   157	181	293	org/json/JSONException
    //   181	216	293	org/json/JSONException
    //   221	241	293	org/json/JSONException
    //   241	250	293	org/json/JSONException
    //   255	275	293	org/json/JSONException
    //   282	290	293	org/json/JSONException
    //   303	311	293	org/json/JSONException
    //   316	324	293	org/json/JSONException
    //   221	241	301	java/lang/Exception
    //   255	275	314	java/lang/Exception
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.AAAGetMovieCouponCountSAXParseJson
 * JD-Core Version:    0.6.2
 */