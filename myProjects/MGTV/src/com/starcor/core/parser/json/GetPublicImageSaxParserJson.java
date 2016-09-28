package com.starcor.core.parser.json;

import com.starcor.core.domain.PublicImage;
import com.starcor.core.interfaces.IXmlParser;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GetPublicImageSaxParserJson<Result>
  implements IXmlParser<Result>
{
  private List<PublicImage> list = new ArrayList();

  public Result parser(InputStream paramInputStream)
  {
    return null;
  }

  // ERROR //
  public Result parser(byte[] paramArrayOfByte)
  {
    // Byte code:
    //   0: new 26	java/lang/String
    //   3: dup
    //   4: aload_1
    //   5: invokespecial 29	java/lang/String:<init>	([B)V
    //   8: astore_2
    //   9: new 31	org/json/JSONObject
    //   12: dup
    //   13: aload_2
    //   14: invokespecial 34	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   17: astore_3
    //   18: aload_3
    //   19: ldc 36
    //   21: invokevirtual 40	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   24: ifeq +289 -> 313
    //   27: aload_3
    //   28: ldc 36
    //   30: invokevirtual 44	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   33: astore 5
    //   35: iconst_0
    //   36: istore 6
    //   38: iload 6
    //   40: aload 5
    //   42: invokevirtual 50	org/json/JSONArray:length	()I
    //   45: if_icmpge +268 -> 313
    //   48: aload 5
    //   50: iload 6
    //   52: invokevirtual 54	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   55: astore 7
    //   57: new 56	com/starcor/core/domain/PublicImage
    //   60: dup
    //   61: invokespecial 57	com/starcor/core/domain/PublicImage:<init>	()V
    //   64: astore 8
    //   66: aload 7
    //   68: ldc 59
    //   70: invokevirtual 40	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   73: ifeq +15 -> 88
    //   76: aload 8
    //   78: aload 7
    //   80: ldc 59
    //   82: invokevirtual 63	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   85: putfield 66	com/starcor/core/domain/PublicImage:id	Ljava/lang/String;
    //   88: aload 7
    //   90: ldc 68
    //   92: invokevirtual 40	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   95: ifeq +15 -> 110
    //   98: aload 8
    //   100: aload 7
    //   102: ldc 68
    //   104: invokevirtual 63	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   107: putfield 70	com/starcor/core/domain/PublicImage:name	Ljava/lang/String;
    //   110: aload 7
    //   112: ldc 72
    //   114: invokevirtual 40	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   117: ifeq +178 -> 295
    //   120: new 31	org/json/JSONObject
    //   123: dup
    //   124: aload 7
    //   126: ldc 72
    //   128: invokevirtual 63	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   131: invokespecial 34	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   134: astore 9
    //   136: aload 8
    //   138: new 16	java/util/ArrayList
    //   141: dup
    //   142: invokespecial 17	java/util/ArrayList:<init>	()V
    //   145: putfield 75	com/starcor/core/domain/PublicImage:url_list	Ljava/util/List;
    //   148: aload 9
    //   150: ldc 36
    //   152: invokevirtual 40	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   155: ifeq +140 -> 295
    //   158: aload 9
    //   160: ldc 36
    //   162: invokevirtual 63	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   165: astore 11
    //   167: aload 11
    //   169: ldc 77
    //   171: invokevirtual 80	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   174: ifeq +55 -> 229
    //   177: new 46	org/json/JSONArray
    //   180: dup
    //   181: aload 11
    //   183: invokespecial 81	org/json/JSONArray:<init>	(Ljava/lang/String;)V
    //   186: astore 12
    //   188: iconst_0
    //   189: istore 13
    //   191: iload 13
    //   193: aload 12
    //   195: invokevirtual 50	org/json/JSONArray:length	()I
    //   198: if_icmpge +97 -> 295
    //   201: aload 12
    //   203: iload 13
    //   205: invokevirtual 84	org/json/JSONArray:getString	(I)Ljava/lang/String;
    //   208: astore 14
    //   210: aload 8
    //   212: getfield 75	com/starcor/core/domain/PublicImage:url_list	Ljava/util/List;
    //   215: aload 14
    //   217: invokeinterface 90 2 0
    //   222: pop
    //   223: iinc 13 1
    //   226: goto -35 -> 191
    //   229: aload 11
    //   231: ldc 92
    //   233: invokevirtual 80	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   236: ifeq +59 -> 295
    //   239: new 31	org/json/JSONObject
    //   242: dup
    //   243: aload 11
    //   245: invokespecial 34	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   248: astore 16
    //   250: new 94	java/lang/StringBuilder
    //   253: dup
    //   254: invokespecial 95	java/lang/StringBuilder:<init>	()V
    //   257: ldc 97
    //   259: invokevirtual 101	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   262: aload 11
    //   264: invokevirtual 101	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   267: invokevirtual 105	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   270: invokestatic 110	com/starcor/core/utils/Logger:e	(Ljava/lang/String;)V
    //   273: aload 16
    //   275: ldc 112
    //   277: invokevirtual 63	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   280: astore 17
    //   282: aload 8
    //   284: getfield 75	com/starcor/core/domain/PublicImage:url_list	Ljava/util/List;
    //   287: aload 17
    //   289: invokeinterface 90 2 0
    //   294: pop
    //   295: aload_0
    //   296: getfield 19	com/starcor/core/parser/json/GetPublicImageSaxParserJson:list	Ljava/util/List;
    //   299: aload 8
    //   301: invokeinterface 90 2 0
    //   306: pop
    //   307: iinc 6 1
    //   310: goto -272 -> 38
    //   313: ldc 114
    //   315: new 94	java/lang/StringBuilder
    //   318: dup
    //   319: invokespecial 95	java/lang/StringBuilder:<init>	()V
    //   322: ldc 116
    //   324: invokevirtual 101	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   327: aload_0
    //   328: getfield 19	com/starcor/core/parser/json/GetPublicImageSaxParserJson:list	Ljava/util/List;
    //   331: invokevirtual 117	java/lang/Object:toString	()Ljava/lang/String;
    //   334: invokevirtual 101	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   337: invokevirtual 105	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   340: invokestatic 121	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   343: aload_0
    //   344: getfield 19	com/starcor/core/parser/json/GetPublicImageSaxParserJson:list	Ljava/util/List;
    //   347: areturn
    //   348: astore 4
    //   350: aload 4
    //   352: invokevirtual 124	org/json/JSONException:printStackTrace	()V
    //   355: goto -42 -> 313
    //   358: astore 4
    //   360: goto -10 -> 350
    //
    // Exception table:
    //   from	to	target	type
    //   9	18	348	org/json/JSONException
    //   18	35	358	org/json/JSONException
    //   38	88	358	org/json/JSONException
    //   88	110	358	org/json/JSONException
    //   110	188	358	org/json/JSONException
    //   191	223	358	org/json/JSONException
    //   229	295	358	org/json/JSONException
    //   295	307	358	org/json/JSONException
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetPublicImageSaxParserJson
 * JD-Core Version:    0.6.2
 */