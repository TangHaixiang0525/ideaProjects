package com.starcor.core.parser.json;

import com.starcor.core.domain.FilmItem;
import com.starcor.core.domain.FilmListItem;
import com.starcor.core.interfaces.IXmlParser;
import java.util.ArrayList;

public class GetFilmItemSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = "GetFilmItemSAXParserJson";
  private FilmListItem fItem;
  private FilmItem filmItem = new FilmItem();

  public GetFilmItemSAXParserJson()
  {
    this.filmItem.filmList = new ArrayList();
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
    //   31: ifeq +22 -> 53
    //   34: aload_0
    //   35: getfield 24	com/starcor/core/parser/json/GetFilmItemSAXParserJson:filmItem	Lcom/starcor/core/domain/FilmItem;
    //   38: aload_2
    //   39: ldc 53
    //   41: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   44: invokestatic 67	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   47: invokevirtual 71	java/lang/Integer:intValue	()I
    //   50: putfield 75	com/starcor/core/domain/FilmItem:film_num	I
    //   53: aload_2
    //   54: ldc 77
    //   56: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   59: ifeq +538 -> 597
    //   62: new 79	org/json/JSONArray
    //   65: dup
    //   66: aload_2
    //   67: ldc 77
    //   69: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   72: invokespecial 80	org/json/JSONArray:<init>	(Ljava/lang/String;)V
    //   75: astore 5
    //   77: iconst_0
    //   78: istore 6
    //   80: iload 6
    //   82: aload 5
    //   84: invokevirtual 83	org/json/JSONArray:length	()I
    //   87: if_icmpge +510 -> 597
    //   90: aload_0
    //   91: new 85	com/starcor/core/domain/FilmListItem
    //   94: dup
    //   95: invokespecial 86	com/starcor/core/domain/FilmListItem:<init>	()V
    //   98: putfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   101: aload 5
    //   103: iload 6
    //   105: invokevirtual 92	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   108: astore 7
    //   110: aload 7
    //   112: ldc 94
    //   114: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   117: ifeq +17 -> 134
    //   120: aload_0
    //   121: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   124: aload 7
    //   126: ldc 94
    //   128: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   131: putfield 97	com/starcor/core/domain/FilmListItem:package_id	Ljava/lang/String;
    //   134: aload 7
    //   136: ldc 99
    //   138: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   141: ifeq +17 -> 158
    //   144: aload_0
    //   145: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   148: aload 7
    //   150: ldc 99
    //   152: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   155: putfield 101	com/starcor/core/domain/FilmListItem:category_id	Ljava/lang/String;
    //   158: aload 7
    //   160: ldc 103
    //   162: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   165: ifeq +17 -> 182
    //   168: aload_0
    //   169: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   172: aload 7
    //   174: ldc 103
    //   176: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   179: putfield 106	com/starcor/core/domain/FilmListItem:item_id	Ljava/lang/String;
    //   182: aload 7
    //   184: ldc 108
    //   186: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   189: ifeq +17 -> 206
    //   192: aload_0
    //   193: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   196: aload 7
    //   198: ldc 108
    //   200: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   203: putfield 111	com/starcor/core/domain/FilmListItem:film_name	Ljava/lang/String;
    //   206: aload 7
    //   208: ldc 113
    //   210: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   213: ifeq +17 -> 230
    //   216: aload_0
    //   217: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   220: aload 7
    //   222: ldc 113
    //   224: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   227: putfield 116	com/starcor/core/domain/FilmListItem:big_img_url	Ljava/lang/String;
    //   230: aload 7
    //   232: ldc 118
    //   234: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   237: ifeq +17 -> 254
    //   240: aload_0
    //   241: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   244: aload 7
    //   246: ldc 118
    //   248: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   251: putfield 121	com/starcor/core/domain/FilmListItem:normal_img_url	Ljava/lang/String;
    //   254: aload 7
    //   256: ldc 123
    //   258: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   261: ifeq +17 -> 278
    //   264: aload_0
    //   265: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   268: aload 7
    //   270: ldc 123
    //   272: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   275: putfield 126	com/starcor/core/domain/FilmListItem:small_img_url	Ljava/lang/String;
    //   278: aload 7
    //   280: ldc 128
    //   282: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   285: ifeq +17 -> 302
    //   288: aload_0
    //   289: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   292: aload 7
    //   294: ldc 128
    //   296: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   299: putfield 131	com/starcor/core/domain/FilmListItem:corner_mark_img_url	Ljava/lang/String;
    //   302: aload 7
    //   304: ldc 133
    //   306: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   309: ifeq +17 -> 326
    //   312: aload_0
    //   313: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   316: aload 7
    //   318: ldc 133
    //   320: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   323: putfield 135	com/starcor/core/domain/FilmListItem:desc	Ljava/lang/String;
    //   326: aload 7
    //   328: ldc 137
    //   330: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   333: ifeq +17 -> 350
    //   336: aload_0
    //   337: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   340: aload 7
    //   342: ldc 137
    //   344: invokevirtual 141	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   347: putfield 144	com/starcor/core/domain/FilmListItem:uiStyle	I
    //   350: aload 7
    //   352: ldc 146
    //   354: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   357: ifeq +17 -> 374
    //   360: aload_0
    //   361: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   364: aload 7
    //   366: ldc 146
    //   368: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   371: putfield 148	com/starcor/core/domain/FilmListItem:corner_mark	Ljava/lang/String;
    //   374: aload 7
    //   376: ldc 150
    //   378: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   381: ifeq +17 -> 398
    //   384: aload_0
    //   385: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   388: aload 7
    //   390: ldc 150
    //   392: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   395: putfield 152	com/starcor/core/domain/FilmListItem:video_id	Ljava/lang/String;
    //   398: aload 7
    //   400: ldc 154
    //   402: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   405: ifeq +23 -> 428
    //   408: aload_0
    //   409: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   412: aload 7
    //   414: ldc 154
    //   416: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   419: invokestatic 67	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   422: invokevirtual 71	java/lang/Integer:intValue	()I
    //   425: putfield 156	com/starcor/core/domain/FilmListItem:video_type	I
    //   428: aload 7
    //   430: ldc 158
    //   432: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   435: ifeq +23 -> 458
    //   438: aload_0
    //   439: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   442: aload 7
    //   444: ldc 158
    //   446: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   449: invokestatic 67	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   452: invokevirtual 71	java/lang/Integer:intValue	()I
    //   455: putfield 160	com/starcor/core/domain/FilmListItem:play_count	I
    //   458: aload 7
    //   460: ldc 162
    //   462: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   465: ifeq +28 -> 493
    //   468: aload 7
    //   470: ldc 162
    //   472: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   475: invokestatic 167	java/lang/Float:valueOf	(Ljava/lang/String;)Ljava/lang/Float;
    //   478: invokevirtual 171	java/lang/Float:floatValue	()F
    //   481: fstore 14
    //   483: aload_0
    //   484: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   487: fload 14
    //   489: f2i
    //   490: putfield 173	com/starcor/core/domain/FilmListItem:user_score	I
    //   493: aload 7
    //   495: ldc 175
    //   497: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   500: ifeq +23 -> 523
    //   503: aload_0
    //   504: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   507: aload 7
    //   509: ldc 175
    //   511: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   514: invokestatic 67	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   517: invokevirtual 71	java/lang/Integer:intValue	()I
    //   520: putfield 177	com/starcor/core/domain/FilmListItem:billing	I
    //   523: aload 7
    //   525: ldc 179
    //   527: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   530: ifeq +23 -> 553
    //   533: aload_0
    //   534: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   537: aload 7
    //   539: ldc 179
    //   541: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   544: invokestatic 67	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   547: invokevirtual 71	java/lang/Integer:intValue	()I
    //   550: putfield 182	com/starcor/core/domain/FilmListItem:point	I
    //   553: aload_0
    //   554: getfield 24	com/starcor/core/parser/json/GetFilmItemSAXParserJson:filmItem	Lcom/starcor/core/domain/FilmItem;
    //   557: getfield 31	com/starcor/core/domain/FilmItem:filmList	Ljava/util/ArrayList;
    //   560: aload_0
    //   561: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   564: invokevirtual 186	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   567: pop
    //   568: iinc 6 1
    //   571: goto -491 -> 80
    //   574: astore_3
    //   575: aload_3
    //   576: invokevirtual 189	java/lang/Exception:printStackTrace	()V
    //   579: aload_0
    //   580: getfield 24	com/starcor/core/parser/json/GetFilmItemSAXParserJson:filmItem	Lcom/starcor/core/domain/FilmItem;
    //   583: iconst_0
    //   584: putfield 75	com/starcor/core/domain/FilmItem:film_num	I
    //   587: goto -534 -> 53
    //   590: astore 4
    //   592: aload 4
    //   594: invokevirtual 189	java/lang/Exception:printStackTrace	()V
    //   597: aload_0
    //   598: getfield 24	com/starcor/core/parser/json/GetFilmItemSAXParserJson:filmItem	Lcom/starcor/core/domain/FilmItem;
    //   601: areturn
    //   602: astore 8
    //   604: aload 8
    //   606: invokevirtual 189	java/lang/Exception:printStackTrace	()V
    //   609: aload_0
    //   610: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   613: iconst_m1
    //   614: putfield 156	com/starcor/core/domain/FilmListItem:video_type	I
    //   617: goto -189 -> 428
    //   620: astore 9
    //   622: aload 9
    //   624: invokevirtual 189	java/lang/Exception:printStackTrace	()V
    //   627: aload_0
    //   628: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   631: iconst_0
    //   632: putfield 160	com/starcor/core/domain/FilmListItem:play_count	I
    //   635: goto -177 -> 458
    //   638: astore 10
    //   640: aload 10
    //   642: invokevirtual 189	java/lang/Exception:printStackTrace	()V
    //   645: aload_0
    //   646: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   649: iconst_0
    //   650: putfield 173	com/starcor/core/domain/FilmListItem:user_score	I
    //   653: goto -160 -> 493
    //   656: astore 11
    //   658: aload 11
    //   660: invokevirtual 189	java/lang/Exception:printStackTrace	()V
    //   663: aload_0
    //   664: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   667: iconst_0
    //   668: putfield 177	com/starcor/core/domain/FilmListItem:billing	I
    //   671: goto -148 -> 523
    //   674: astore 12
    //   676: aload 12
    //   678: invokevirtual 189	java/lang/Exception:printStackTrace	()V
    //   681: aload_0
    //   682: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   685: iconst_0
    //   686: putfield 182	com/starcor/core/domain/FilmListItem:point	I
    //   689: goto -136 -> 553
    //
    // Exception table:
    //   from	to	target	type
    //   25	53	574	java/lang/Exception
    //   6	25	590	java/lang/Exception
    //   53	77	590	java/lang/Exception
    //   80	134	590	java/lang/Exception
    //   134	158	590	java/lang/Exception
    //   158	182	590	java/lang/Exception
    //   182	206	590	java/lang/Exception
    //   206	230	590	java/lang/Exception
    //   230	254	590	java/lang/Exception
    //   254	278	590	java/lang/Exception
    //   278	302	590	java/lang/Exception
    //   302	326	590	java/lang/Exception
    //   326	350	590	java/lang/Exception
    //   350	374	590	java/lang/Exception
    //   374	398	590	java/lang/Exception
    //   553	568	590	java/lang/Exception
    //   575	587	590	java/lang/Exception
    //   604	617	590	java/lang/Exception
    //   622	635	590	java/lang/Exception
    //   640	653	590	java/lang/Exception
    //   658	671	590	java/lang/Exception
    //   676	689	590	java/lang/Exception
    //   398	428	602	java/lang/Exception
    //   428	458	620	java/lang/Exception
    //   458	493	638	java/lang/Exception
    //   493	523	656	java/lang/Exception
    //   523	553	674	java/lang/Exception
  }

  // ERROR //
  public Result parser(byte[] paramArrayOfByte)
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
    //   15: invokespecial 48	java/lang/String:<init>	([B)V
    //   18: invokespecial 51	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   21: astore_2
    //   22: aload_2
    //   23: ldc 53
    //   25: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   28: ifeq +22 -> 50
    //   31: aload_0
    //   32: getfield 24	com/starcor/core/parser/json/GetFilmItemSAXParserJson:filmItem	Lcom/starcor/core/domain/FilmItem;
    //   35: aload_2
    //   36: ldc 53
    //   38: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   41: invokestatic 67	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   44: invokevirtual 71	java/lang/Integer:intValue	()I
    //   47: putfield 75	com/starcor/core/domain/FilmItem:film_num	I
    //   50: aload_2
    //   51: ldc 192
    //   53: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   56: ifeq +22 -> 78
    //   59: aload_0
    //   60: getfield 24	com/starcor/core/parser/json/GetFilmItemSAXParserJson:filmItem	Lcom/starcor/core/domain/FilmItem;
    //   63: aload_2
    //   64: ldc 192
    //   66: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   69: invokestatic 67	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   72: invokevirtual 71	java/lang/Integer:intValue	()I
    //   75: putfield 195	com/starcor/core/domain/FilmItem:page_index	I
    //   78: aload_2
    //   79: ldc 197
    //   81: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   84: ifeq +22 -> 106
    //   87: aload_0
    //   88: getfield 24	com/starcor/core/parser/json/GetFilmItemSAXParserJson:filmItem	Lcom/starcor/core/domain/FilmItem;
    //   91: aload_2
    //   92: ldc 197
    //   94: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   97: invokestatic 67	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   100: invokevirtual 71	java/lang/Integer:intValue	()I
    //   103: putfield 200	com/starcor/core/domain/FilmItem:page_total	I
    //   106: aload_2
    //   107: ldc 77
    //   109: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   112: ifeq +651 -> 763
    //   115: aload_2
    //   116: ldc 77
    //   118: invokevirtual 204	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   121: astore 5
    //   123: iconst_0
    //   124: istore 6
    //   126: iload 6
    //   128: aload 5
    //   130: invokevirtual 83	org/json/JSONArray:length	()I
    //   133: if_icmpge +630 -> 763
    //   136: aload_0
    //   137: new 85	com/starcor/core/domain/FilmListItem
    //   140: dup
    //   141: invokespecial 86	com/starcor/core/domain/FilmListItem:<init>	()V
    //   144: putfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   147: aload 5
    //   149: iload 6
    //   151: invokevirtual 92	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   154: astore 7
    //   156: aload 7
    //   158: ldc 94
    //   160: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   163: ifeq +17 -> 180
    //   166: aload_0
    //   167: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   170: aload 7
    //   172: ldc 94
    //   174: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   177: putfield 97	com/starcor/core/domain/FilmListItem:package_id	Ljava/lang/String;
    //   180: aload 7
    //   182: ldc 99
    //   184: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   187: ifeq +17 -> 204
    //   190: aload_0
    //   191: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   194: aload 7
    //   196: ldc 99
    //   198: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   201: putfield 101	com/starcor/core/domain/FilmListItem:category_id	Ljava/lang/String;
    //   204: aload 7
    //   206: ldc 103
    //   208: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   211: ifeq +17 -> 228
    //   214: aload_0
    //   215: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   218: aload 7
    //   220: ldc 103
    //   222: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   225: putfield 106	com/starcor/core/domain/FilmListItem:item_id	Ljava/lang/String;
    //   228: aload 7
    //   230: ldc 108
    //   232: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   235: ifeq +17 -> 252
    //   238: aload_0
    //   239: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   242: aload 7
    //   244: ldc 108
    //   246: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   249: putfield 111	com/starcor/core/domain/FilmListItem:film_name	Ljava/lang/String;
    //   252: aload 7
    //   254: ldc 113
    //   256: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   259: ifeq +17 -> 276
    //   262: aload_0
    //   263: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   266: aload 7
    //   268: ldc 113
    //   270: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   273: putfield 116	com/starcor/core/domain/FilmListItem:big_img_url	Ljava/lang/String;
    //   276: aload 7
    //   278: ldc 118
    //   280: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   283: ifeq +17 -> 300
    //   286: aload_0
    //   287: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   290: aload 7
    //   292: ldc 118
    //   294: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   297: putfield 121	com/starcor/core/domain/FilmListItem:normal_img_url	Ljava/lang/String;
    //   300: aload 7
    //   302: ldc 123
    //   304: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   307: ifeq +17 -> 324
    //   310: aload_0
    //   311: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   314: aload 7
    //   316: ldc 123
    //   318: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   321: putfield 126	com/starcor/core/domain/FilmListItem:small_img_url	Ljava/lang/String;
    //   324: aload 7
    //   326: ldc 206
    //   328: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   331: ifeq +17 -> 348
    //   334: aload_0
    //   335: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   338: aload 7
    //   340: ldc 206
    //   342: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   345: putfield 209	com/starcor/core/domain/FilmListItem:v_img_url	Ljava/lang/String;
    //   348: aload 7
    //   350: ldc 211
    //   352: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   355: ifeq +17 -> 372
    //   358: aload_0
    //   359: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   362: aload 7
    //   364: ldc 211
    //   366: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   369: putfield 214	com/starcor/core/domain/FilmListItem:h_img_url	Ljava/lang/String;
    //   372: aload 7
    //   374: ldc 216
    //   376: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   379: ifeq +17 -> 396
    //   382: aload_0
    //   383: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   386: aload 7
    //   388: ldc 216
    //   390: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   393: putfield 219	com/starcor/core/domain/FilmListItem:s_img_url	Ljava/lang/String;
    //   396: aload 7
    //   398: ldc 137
    //   400: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   403: ifeq +17 -> 420
    //   406: aload_0
    //   407: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   410: aload 7
    //   412: ldc 137
    //   414: invokevirtual 141	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   417: putfield 144	com/starcor/core/domain/FilmListItem:uiStyle	I
    //   420: aload 7
    //   422: ldc 146
    //   424: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   427: ifeq +17 -> 444
    //   430: aload_0
    //   431: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   434: aload 7
    //   436: ldc 146
    //   438: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   441: putfield 148	com/starcor/core/domain/FilmListItem:corner_mark	Ljava/lang/String;
    //   444: aload 7
    //   446: ldc 128
    //   448: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   451: ifeq +17 -> 468
    //   454: aload_0
    //   455: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   458: aload 7
    //   460: ldc 128
    //   462: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   465: putfield 131	com/starcor/core/domain/FilmListItem:corner_mark_img_url	Ljava/lang/String;
    //   468: aload 7
    //   470: ldc 221
    //   472: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   475: ifeq +17 -> 492
    //   478: aload_0
    //   479: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   482: aload 7
    //   484: ldc 221
    //   486: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   489: putfield 224	com/starcor/core/domain/FilmListItem:updateInfo	Ljava/lang/String;
    //   492: aload 7
    //   494: ldc 133
    //   496: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   499: ifeq +17 -> 516
    //   502: aload_0
    //   503: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   506: aload 7
    //   508: ldc 133
    //   510: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   513: putfield 135	com/starcor/core/domain/FilmListItem:desc	Ljava/lang/String;
    //   516: aload 7
    //   518: ldc 150
    //   520: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   523: ifeq +17 -> 540
    //   526: aload_0
    //   527: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   530: aload 7
    //   532: ldc 150
    //   534: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   537: putfield 152	com/starcor/core/domain/FilmListItem:video_id	Ljava/lang/String;
    //   540: aload 7
    //   542: ldc 154
    //   544: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   547: ifeq +23 -> 570
    //   550: aload_0
    //   551: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   554: aload 7
    //   556: ldc 154
    //   558: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   561: invokestatic 67	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   564: invokevirtual 71	java/lang/Integer:intValue	()I
    //   567: putfield 156	com/starcor/core/domain/FilmListItem:video_type	I
    //   570: aload 7
    //   572: ldc 158
    //   574: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   577: ifeq +23 -> 600
    //   580: aload_0
    //   581: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   584: aload 7
    //   586: ldc 158
    //   588: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   591: invokestatic 67	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   594: invokevirtual 71	java/lang/Integer:intValue	()I
    //   597: putfield 160	com/starcor/core/domain/FilmListItem:play_count	I
    //   600: aload 7
    //   602: ldc 175
    //   604: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   607: ifeq +23 -> 630
    //   610: aload_0
    //   611: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   614: aload 7
    //   616: ldc 175
    //   618: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   621: invokestatic 67	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   624: invokevirtual 71	java/lang/Integer:intValue	()I
    //   627: putfield 177	com/starcor/core/domain/FilmListItem:billing	I
    //   630: aload 7
    //   632: ldc 162
    //   634: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   637: ifeq +28 -> 665
    //   640: aload 7
    //   642: ldc 162
    //   644: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   647: invokestatic 167	java/lang/Float:valueOf	(Ljava/lang/String;)Ljava/lang/Float;
    //   650: invokevirtual 171	java/lang/Float:floatValue	()F
    //   653: fstore 14
    //   655: aload_0
    //   656: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   659: fload 14
    //   661: f2i
    //   662: putfield 173	com/starcor/core/domain/FilmListItem:user_score	I
    //   665: aload 7
    //   667: ldc 179
    //   669: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   672: ifeq +23 -> 695
    //   675: aload_0
    //   676: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   679: aload 7
    //   681: ldc 179
    //   683: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   686: invokestatic 67	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   689: invokevirtual 71	java/lang/Integer:intValue	()I
    //   692: putfield 182	com/starcor/core/domain/FilmListItem:point	I
    //   695: aload 7
    //   697: ldc 226
    //   699: invokevirtual 57	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   702: ifeq +17 -> 719
    //   705: aload_0
    //   706: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   709: aload 7
    //   711: ldc 226
    //   713: invokevirtual 61	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   716: putfield 229	com/starcor/core/domain/FilmListItem:media_mode	Ljava/lang/String;
    //   719: aload_0
    //   720: getfield 24	com/starcor/core/parser/json/GetFilmItemSAXParserJson:filmItem	Lcom/starcor/core/domain/FilmItem;
    //   723: getfield 31	com/starcor/core/domain/FilmItem:filmList	Ljava/util/ArrayList;
    //   726: aload_0
    //   727: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   730: invokevirtual 186	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   733: pop
    //   734: iinc 6 1
    //   737: goto -611 -> 126
    //   740: astore_3
    //   741: aload_3
    //   742: invokevirtual 189	java/lang/Exception:printStackTrace	()V
    //   745: aload_0
    //   746: getfield 24	com/starcor/core/parser/json/GetFilmItemSAXParserJson:filmItem	Lcom/starcor/core/domain/FilmItem;
    //   749: iconst_0
    //   750: putfield 75	com/starcor/core/domain/FilmItem:film_num	I
    //   753: goto -647 -> 106
    //   756: astore 4
    //   758: aload 4
    //   760: invokevirtual 189	java/lang/Exception:printStackTrace	()V
    //   763: aload_0
    //   764: getfield 24	com/starcor/core/parser/json/GetFilmItemSAXParserJson:filmItem	Lcom/starcor/core/domain/FilmItem;
    //   767: areturn
    //   768: astore 8
    //   770: aload 8
    //   772: invokevirtual 189	java/lang/Exception:printStackTrace	()V
    //   775: aload_0
    //   776: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   779: iconst_m1
    //   780: putfield 156	com/starcor/core/domain/FilmListItem:video_type	I
    //   783: goto -213 -> 570
    //   786: astore 9
    //   788: aload 9
    //   790: invokevirtual 189	java/lang/Exception:printStackTrace	()V
    //   793: aload_0
    //   794: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   797: iconst_0
    //   798: putfield 160	com/starcor/core/domain/FilmListItem:play_count	I
    //   801: goto -201 -> 600
    //   804: astore 10
    //   806: aload 10
    //   808: invokevirtual 189	java/lang/Exception:printStackTrace	()V
    //   811: aload_0
    //   812: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   815: iconst_0
    //   816: putfield 177	com/starcor/core/domain/FilmListItem:billing	I
    //   819: goto -189 -> 630
    //   822: astore 11
    //   824: aload_0
    //   825: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   828: iconst_0
    //   829: putfield 173	com/starcor/core/domain/FilmListItem:user_score	I
    //   832: goto -167 -> 665
    //   835: astore 12
    //   837: aload 12
    //   839: invokevirtual 189	java/lang/Exception:printStackTrace	()V
    //   842: aload_0
    //   843: getfield 88	com/starcor/core/parser/json/GetFilmItemSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   846: iconst_0
    //   847: putfield 182	com/starcor/core/domain/FilmListItem:point	I
    //   850: goto -155 -> 695
    //
    // Exception table:
    //   from	to	target	type
    //   22	50	740	java/lang/Exception
    //   50	78	740	java/lang/Exception
    //   78	106	740	java/lang/Exception
    //   6	22	756	java/lang/Exception
    //   106	123	756	java/lang/Exception
    //   126	180	756	java/lang/Exception
    //   180	204	756	java/lang/Exception
    //   204	228	756	java/lang/Exception
    //   228	252	756	java/lang/Exception
    //   252	276	756	java/lang/Exception
    //   276	300	756	java/lang/Exception
    //   300	324	756	java/lang/Exception
    //   324	348	756	java/lang/Exception
    //   348	372	756	java/lang/Exception
    //   372	396	756	java/lang/Exception
    //   396	420	756	java/lang/Exception
    //   420	444	756	java/lang/Exception
    //   444	468	756	java/lang/Exception
    //   468	492	756	java/lang/Exception
    //   492	516	756	java/lang/Exception
    //   516	540	756	java/lang/Exception
    //   695	719	756	java/lang/Exception
    //   719	734	756	java/lang/Exception
    //   741	753	756	java/lang/Exception
    //   770	783	756	java/lang/Exception
    //   788	801	756	java/lang/Exception
    //   806	819	756	java/lang/Exception
    //   824	832	756	java/lang/Exception
    //   837	850	756	java/lang/Exception
    //   540	570	768	java/lang/Exception
    //   570	600	786	java/lang/Exception
    //   600	630	804	java/lang/Exception
    //   630	665	822	java/lang/Exception
    //   665	695	835	java/lang/Exception
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetFilmItemSAXParserJson
 * JD-Core Version:    0.6.2
 */