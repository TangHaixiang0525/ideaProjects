package com.starcor.core.parser.json;

import com.starcor.core.domain.WeatherItem;
import com.starcor.core.domain.WeatherList;
import com.starcor.core.interfaces.IXmlParser;
import java.util.ArrayList;

public class GetWeatherSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = "GetWeatherSAXParserJson";
  private WeatherItem wItem;
  private WeatherList wList = new WeatherList();

  public GetWeatherSAXParserJson()
  {
    this.wList.listWeatherIndex = new ArrayList();
  }

  // ERROR //
  public Result parser(java.io.InputStream paramInputStream)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull +5 -> 6
    //   4: aconst_null
    //   5: areturn
    //   6: new 37	org/json/JSONObject
    //   9: dup
    //   10: new 39	java/lang/String
    //   13: dup
    //   14: aload_1
    //   15: invokestatic 45	com/starcor/core/utils/StreamTools:getBytes	(Ljava/io/InputStream;)[B
    //   18: invokespecial 48	java/lang/String:<init>	([B)V
    //   21: invokespecial 51	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   24: astore_2
    //   25: aload_2
    //   26: ldc 53
    //   28: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   31: ifeq +152 -> 183
    //   34: ldc 59
    //   36: invokestatic 64	com/starcor/core/utils/Logger:d	(Ljava/lang/String;)V
    //   39: aload_2
    //   40: ldc 53
    //   42: invokevirtual 68	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   45: astore 17
    //   47: aload 17
    //   49: ifnull +478 -> 527
    //   52: new 37	org/json/JSONObject
    //   55: dup
    //   56: aload 17
    //   58: invokespecial 51	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   61: astore 18
    //   63: aload 18
    //   65: ldc 70
    //   67: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   70: ifeq +17 -> 87
    //   73: aload_0
    //   74: getfield 24	com/starcor/core/parser/json/GetWeatherSAXParserJson:wList	Lcom/starcor/core/domain/WeatherList;
    //   77: aload 18
    //   79: ldc 70
    //   81: invokevirtual 68	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   84: putfield 72	com/starcor/core/domain/WeatherList:name	Ljava/lang/String;
    //   87: aload 18
    //   89: ldc 74
    //   91: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   94: ifeq +17 -> 111
    //   97: aload_0
    //   98: getfield 24	com/starcor/core/parser/json/GetWeatherSAXParserJson:wList	Lcom/starcor/core/domain/WeatherList;
    //   101: aload 18
    //   103: ldc 74
    //   105: invokevirtual 68	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   108: putfield 77	com/starcor/core/domain/WeatherList:current_day	Ljava/lang/String;
    //   111: aload 18
    //   113: ldc 79
    //   115: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   118: ifeq +17 -> 135
    //   121: aload_0
    //   122: getfield 24	com/starcor/core/parser/json/GetWeatherSAXParserJson:wList	Lcom/starcor/core/domain/WeatherList;
    //   125: aload 18
    //   127: ldc 79
    //   129: invokevirtual 68	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   132: putfield 81	com/starcor/core/domain/WeatherList:current_week	Ljava/lang/String;
    //   135: aload 18
    //   137: ldc 83
    //   139: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   142: ifeq +17 -> 159
    //   145: aload_0
    //   146: getfield 24	com/starcor/core/parser/json/GetWeatherSAXParserJson:wList	Lcom/starcor/core/domain/WeatherList;
    //   149: aload 18
    //   151: ldc 83
    //   153: invokevirtual 68	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   156: putfield 85	com/starcor/core/domain/WeatherList:dress	Ljava/lang/String;
    //   159: aload 18
    //   161: ldc 87
    //   163: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   166: ifeq +17 -> 183
    //   169: aload_0
    //   170: getfield 24	com/starcor/core/parser/json/GetWeatherSAXParserJson:wList	Lcom/starcor/core/domain/WeatherList;
    //   173: aload 18
    //   175: ldc 87
    //   177: invokevirtual 68	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   180: putfield 90	com/starcor/core/domain/WeatherList:uvi	Ljava/lang/String;
    //   183: aload_2
    //   184: ldc 92
    //   186: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   189: ifeq +351 -> 540
    //   192: aload_2
    //   193: ldc 92
    //   195: invokevirtual 68	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   198: astore 4
    //   200: aload 4
    //   202: ifnull +448 -> 650
    //   205: new 94	org/json/JSONArray
    //   208: dup
    //   209: aload 4
    //   211: invokespecial 95	org/json/JSONArray:<init>	(Ljava/lang/String;)V
    //   214: astore 5
    //   216: iconst_0
    //   217: istore 6
    //   219: iload 6
    //   221: aload 5
    //   223: invokevirtual 99	org/json/JSONArray:length	()I
    //   226: if_icmpge +314 -> 540
    //   229: aload_0
    //   230: new 101	com/starcor/core/domain/WeatherItem
    //   233: dup
    //   234: invokespecial 102	com/starcor/core/domain/WeatherItem:<init>	()V
    //   237: putfield 104	com/starcor/core/parser/json/GetWeatherSAXParserJson:wItem	Lcom/starcor/core/domain/WeatherItem;
    //   240: aload 5
    //   242: iload 6
    //   244: invokevirtual 108	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   247: astore 7
    //   249: aload 7
    //   251: ldc 110
    //   253: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   256: istore 8
    //   258: iload 8
    //   260: ifeq +36 -> 296
    //   263: aload 7
    //   265: ldc 110
    //   267: invokevirtual 68	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   270: invokestatic 116	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   273: ifne +23 -> 296
    //   276: aload_0
    //   277: getfield 104	com/starcor/core/parser/json/GetWeatherSAXParserJson:wItem	Lcom/starcor/core/domain/WeatherItem;
    //   280: aload 7
    //   282: ldc 110
    //   284: invokevirtual 68	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   287: invokestatic 122	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   290: invokevirtual 125	java/lang/Integer:intValue	()I
    //   293: putfield 128	com/starcor/core/domain/WeatherItem:index	I
    //   296: aload 7
    //   298: ldc 130
    //   300: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   303: istore 9
    //   305: iload 9
    //   307: ifeq +33 -> 340
    //   310: aload 7
    //   312: ldc 130
    //   314: invokevirtual 68	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   317: invokestatic 116	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   320: ifne +20 -> 340
    //   323: aload_0
    //   324: getfield 104	com/starcor/core/parser/json/GetWeatherSAXParserJson:wItem	Lcom/starcor/core/domain/WeatherItem;
    //   327: aload 7
    //   329: ldc 130
    //   331: invokevirtual 68	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   334: invokestatic 134	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   337: putfield 136	com/starcor/core/domain/WeatherItem:temp_low	I
    //   340: aload 7
    //   342: ldc 138
    //   344: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   347: istore 10
    //   349: iload 10
    //   351: ifeq +36 -> 387
    //   354: aload 7
    //   356: ldc 138
    //   358: invokevirtual 68	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   361: invokestatic 116	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   364: ifne +23 -> 387
    //   367: aload_0
    //   368: getfield 104	com/starcor/core/parser/json/GetWeatherSAXParserJson:wItem	Lcom/starcor/core/domain/WeatherItem;
    //   371: aload 7
    //   373: ldc 138
    //   375: invokevirtual 68	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   378: invokestatic 122	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   381: invokevirtual 125	java/lang/Integer:intValue	()I
    //   384: putfield 140	com/starcor/core/domain/WeatherItem:temp_high	I
    //   387: aload 7
    //   389: ldc 142
    //   391: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   394: istore 11
    //   396: iload 11
    //   398: ifeq +36 -> 434
    //   401: aload 7
    //   403: ldc 142
    //   405: invokevirtual 68	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   408: invokestatic 116	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   411: ifne +23 -> 434
    //   414: aload_0
    //   415: getfield 104	com/starcor/core/parser/json/GetWeatherSAXParserJson:wItem	Lcom/starcor/core/domain/WeatherItem;
    //   418: aload 7
    //   420: ldc 142
    //   422: invokevirtual 68	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   425: invokestatic 122	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   428: invokevirtual 125	java/lang/Integer:intValue	()I
    //   431: putfield 144	com/starcor/core/domain/WeatherItem:temp_current	I
    //   434: aload 7
    //   436: ldc 146
    //   438: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   441: ifeq +17 -> 458
    //   444: aload_0
    //   445: getfield 104	com/starcor/core/parser/json/GetWeatherSAXParserJson:wItem	Lcom/starcor/core/domain/WeatherItem;
    //   448: aload 7
    //   450: ldc 146
    //   452: invokevirtual 68	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   455: putfield 148	com/starcor/core/domain/WeatherItem:desc	Ljava/lang/String;
    //   458: aload 7
    //   460: ldc 150
    //   462: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   465: ifeq +17 -> 482
    //   468: aload_0
    //   469: getfield 104	com/starcor/core/parser/json/GetWeatherSAXParserJson:wItem	Lcom/starcor/core/domain/WeatherItem;
    //   472: aload 7
    //   474: ldc 150
    //   476: invokevirtual 68	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   479: putfield 152	com/starcor/core/domain/WeatherItem:wind	Ljava/lang/String;
    //   482: aload 7
    //   484: ldc 154
    //   486: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   489: ifeq +17 -> 506
    //   492: aload_0
    //   493: getfield 104	com/starcor/core/parser/json/GetWeatherSAXParserJson:wItem	Lcom/starcor/core/domain/WeatherItem;
    //   496: aload 7
    //   498: ldc 154
    //   500: invokevirtual 68	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   503: putfield 156	com/starcor/core/domain/WeatherItem:img_url	Ljava/lang/String;
    //   506: aload_0
    //   507: getfield 24	com/starcor/core/parser/json/GetWeatherSAXParserJson:wList	Lcom/starcor/core/domain/WeatherList;
    //   510: getfield 31	com/starcor/core/domain/WeatherList:listWeatherIndex	Ljava/util/ArrayList;
    //   513: aload_0
    //   514: getfield 104	com/starcor/core/parser/json/GetWeatherSAXParserJson:wItem	Lcom/starcor/core/domain/WeatherItem;
    //   517: invokevirtual 160	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   520: pop
    //   521: iinc 6 1
    //   524: goto -305 -> 219
    //   527: ldc 162
    //   529: invokestatic 64	com/starcor/core/utils/Logger:d	(Ljava/lang/String;)V
    //   532: goto -349 -> 183
    //   535: astore_3
    //   536: aload_3
    //   537: invokevirtual 165	java/lang/Exception:printStackTrace	()V
    //   540: ldc 11
    //   542: new 167	java/lang/StringBuilder
    //   545: dup
    //   546: invokespecial 168	java/lang/StringBuilder:<init>	()V
    //   549: ldc 170
    //   551: invokevirtual 174	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   554: aload_0
    //   555: getfield 24	com/starcor/core/parser/json/GetWeatherSAXParserJson:wList	Lcom/starcor/core/domain/WeatherList;
    //   558: invokevirtual 177	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   561: invokevirtual 181	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   564: invokestatic 185	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   567: aload_0
    //   568: getfield 24	com/starcor/core/parser/json/GetWeatherSAXParserJson:wList	Lcom/starcor/core/domain/WeatherList;
    //   571: areturn
    //   572: astore 16
    //   574: aload 16
    //   576: invokevirtual 165	java/lang/Exception:printStackTrace	()V
    //   579: aload_0
    //   580: getfield 104	com/starcor/core/parser/json/GetWeatherSAXParserJson:wItem	Lcom/starcor/core/domain/WeatherItem;
    //   583: iconst_m1
    //   584: putfield 128	com/starcor/core/domain/WeatherItem:index	I
    //   587: goto -291 -> 296
    //   590: astore 15
    //   592: aload 15
    //   594: invokevirtual 165	java/lang/Exception:printStackTrace	()V
    //   597: aload_0
    //   598: getfield 104	com/starcor/core/parser/json/GetWeatherSAXParserJson:wItem	Lcom/starcor/core/domain/WeatherItem;
    //   601: sipush -9999
    //   604: putfield 136	com/starcor/core/domain/WeatherItem:temp_low	I
    //   607: goto -267 -> 340
    //   610: astore 14
    //   612: aload 14
    //   614: invokevirtual 165	java/lang/Exception:printStackTrace	()V
    //   617: aload_0
    //   618: getfield 104	com/starcor/core/parser/json/GetWeatherSAXParserJson:wItem	Lcom/starcor/core/domain/WeatherItem;
    //   621: sipush -9999
    //   624: putfield 140	com/starcor/core/domain/WeatherItem:temp_high	I
    //   627: goto -240 -> 387
    //   630: astore 13
    //   632: aload 13
    //   634: invokevirtual 165	java/lang/Exception:printStackTrace	()V
    //   637: aload_0
    //   638: getfield 104	com/starcor/core/parser/json/GetWeatherSAXParserJson:wItem	Lcom/starcor/core/domain/WeatherItem;
    //   641: sipush -9999
    //   644: putfield 144	com/starcor/core/domain/WeatherItem:temp_current	I
    //   647: goto -213 -> 434
    //   650: ldc 187
    //   652: invokestatic 190	com/starcor/core/utils/Logger:e	(Ljava/lang/String;)V
    //   655: goto -115 -> 540
    //
    // Exception table:
    //   from	to	target	type
    //   6	47	535	java/lang/Exception
    //   52	87	535	java/lang/Exception
    //   87	111	535	java/lang/Exception
    //   111	135	535	java/lang/Exception
    //   135	159	535	java/lang/Exception
    //   159	183	535	java/lang/Exception
    //   183	200	535	java/lang/Exception
    //   205	216	535	java/lang/Exception
    //   219	258	535	java/lang/Exception
    //   296	305	535	java/lang/Exception
    //   340	349	535	java/lang/Exception
    //   387	396	535	java/lang/Exception
    //   434	458	535	java/lang/Exception
    //   458	482	535	java/lang/Exception
    //   482	506	535	java/lang/Exception
    //   506	521	535	java/lang/Exception
    //   527	532	535	java/lang/Exception
    //   574	587	535	java/lang/Exception
    //   592	607	535	java/lang/Exception
    //   612	627	535	java/lang/Exception
    //   632	647	535	java/lang/Exception
    //   650	655	535	java/lang/Exception
    //   263	296	572	java/lang/Exception
    //   310	340	590	java/lang/Exception
    //   354	387	610	java/lang/Exception
    //   401	434	630	java/lang/Exception
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetWeatherSAXParserJson
 * JD-Core Version:    0.6.2
 */