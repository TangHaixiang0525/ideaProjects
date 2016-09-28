package com.starcor.core.parser.json;

import com.starcor.core.domain.FilmItem;
import com.starcor.core.domain.FilmListItem;
import com.starcor.core.interfaces.IXmlParser;
import java.io.InputStream;
import java.util.ArrayList;

public class GetRelevantFilmsSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private FilmListItem fItem;
  private FilmItem filmItem = new FilmItem();

  public GetRelevantFilmsSAXParserJson()
  {
    this.filmItem.filmList = new ArrayList();
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
    //   6: new 34	org/json/JSONObject
    //   9: dup
    //   10: new 36	java/lang/String
    //   13: dup
    //   14: aload_1
    //   15: invokespecial 39	java/lang/String:<init>	([B)V
    //   18: invokespecial 42	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   21: astore_2
    //   22: aload_2
    //   23: ldc 44
    //   25: invokevirtual 48	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   28: ifeq +22 -> 50
    //   31: aload_0
    //   32: getfield 20	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:filmItem	Lcom/starcor/core/domain/FilmItem;
    //   35: aload_2
    //   36: ldc 44
    //   38: invokevirtual 52	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   41: invokestatic 58	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   44: invokevirtual 62	java/lang/Integer:intValue	()I
    //   47: putfield 66	com/starcor/core/domain/FilmItem:film_num	I
    //   50: aload_2
    //   51: ldc 68
    //   53: invokevirtual 48	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   56: ifeq +16 -> 72
    //   59: aload_0
    //   60: getfield 20	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:filmItem	Lcom/starcor/core/domain/FilmItem;
    //   63: aload_2
    //   64: ldc 68
    //   66: invokevirtual 52	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   69: putfield 71	com/starcor/core/domain/FilmItem:estimateId	Ljava/lang/String;
    //   72: aload_2
    //   73: ldc 73
    //   75: invokevirtual 48	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   78: ifeq +16 -> 94
    //   81: aload_0
    //   82: getfield 20	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:filmItem	Lcom/starcor/core/domain/FilmItem;
    //   85: aload_2
    //   86: ldc 73
    //   88: invokevirtual 52	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   91: putfield 75	com/starcor/core/domain/FilmItem:artithmeticId	Ljava/lang/String;
    //   94: aload_2
    //   95: ldc 77
    //   97: invokevirtual 48	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   100: ifeq +681 -> 781
    //   103: aload_2
    //   104: ldc 77
    //   106: invokevirtual 81	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   109: astore 5
    //   111: iconst_0
    //   112: istore 6
    //   114: iload 6
    //   116: aload 5
    //   118: invokevirtual 86	org/json/JSONArray:length	()I
    //   121: if_icmpge +660 -> 781
    //   124: aload_0
    //   125: new 88	com/starcor/core/domain/FilmListItem
    //   128: dup
    //   129: invokespecial 89	com/starcor/core/domain/FilmListItem:<init>	()V
    //   132: putfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   135: aload 5
    //   137: iload 6
    //   139: invokevirtual 95	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   142: astore 7
    //   144: aload 7
    //   146: ldc 97
    //   148: invokevirtual 48	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   151: ifeq +17 -> 168
    //   154: aload_0
    //   155: getfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   158: aload 7
    //   160: ldc 97
    //   162: invokevirtual 52	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   165: putfield 100	com/starcor/core/domain/FilmListItem:package_id	Ljava/lang/String;
    //   168: aload 7
    //   170: ldc 102
    //   172: invokevirtual 48	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   175: ifeq +17 -> 192
    //   178: aload_0
    //   179: getfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   182: aload 7
    //   184: ldc 102
    //   186: invokevirtual 52	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   189: putfield 104	com/starcor/core/domain/FilmListItem:category_id	Ljava/lang/String;
    //   192: aload 7
    //   194: ldc 106
    //   196: invokevirtual 48	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   199: ifeq +17 -> 216
    //   202: aload_0
    //   203: getfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   206: aload 7
    //   208: ldc 106
    //   210: invokevirtual 52	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   213: putfield 109	com/starcor/core/domain/FilmListItem:item_id	Ljava/lang/String;
    //   216: aload 7
    //   218: ldc 111
    //   220: invokevirtual 48	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   223: ifeq +17 -> 240
    //   226: aload_0
    //   227: getfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   230: aload 7
    //   232: ldc 111
    //   234: invokevirtual 52	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   237: putfield 114	com/starcor/core/domain/FilmListItem:film_name	Ljava/lang/String;
    //   240: aload 7
    //   242: ldc 116
    //   244: invokevirtual 48	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   247: ifeq +17 -> 264
    //   250: aload_0
    //   251: getfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   254: aload 7
    //   256: ldc 116
    //   258: invokevirtual 52	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   261: putfield 119	com/starcor/core/domain/FilmListItem:big_img_url	Ljava/lang/String;
    //   264: aload 7
    //   266: ldc 121
    //   268: invokevirtual 48	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   271: ifeq +17 -> 288
    //   274: aload_0
    //   275: getfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   278: aload 7
    //   280: ldc 121
    //   282: invokevirtual 52	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   285: putfield 124	com/starcor/core/domain/FilmListItem:normal_img_url	Ljava/lang/String;
    //   288: aload 7
    //   290: ldc 126
    //   292: invokevirtual 48	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   295: ifeq +17 -> 312
    //   298: aload_0
    //   299: getfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   302: aload 7
    //   304: ldc 126
    //   306: invokevirtual 52	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   309: putfield 129	com/starcor/core/domain/FilmListItem:small_img_url	Ljava/lang/String;
    //   312: aload 7
    //   314: ldc 131
    //   316: invokevirtual 48	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   319: ifeq +17 -> 336
    //   322: aload_0
    //   323: getfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   326: aload 7
    //   328: ldc 131
    //   330: invokevirtual 52	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   333: putfield 134	com/starcor/core/domain/FilmListItem:v_img_url	Ljava/lang/String;
    //   336: aload 7
    //   338: ldc 136
    //   340: invokevirtual 48	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   343: ifeq +17 -> 360
    //   346: aload_0
    //   347: getfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   350: aload 7
    //   352: ldc 136
    //   354: invokevirtual 52	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   357: putfield 139	com/starcor/core/domain/FilmListItem:h_img_url	Ljava/lang/String;
    //   360: aload 7
    //   362: ldc 141
    //   364: invokevirtual 48	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   367: ifeq +17 -> 384
    //   370: aload_0
    //   371: getfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   374: aload 7
    //   376: ldc 141
    //   378: invokevirtual 52	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   381: putfield 144	com/starcor/core/domain/FilmListItem:s_img_url	Ljava/lang/String;
    //   384: aload 7
    //   386: ldc 146
    //   388: invokevirtual 48	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   391: ifeq +17 -> 408
    //   394: aload_0
    //   395: getfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   398: aload 7
    //   400: ldc 146
    //   402: invokevirtual 52	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   405: putfield 148	com/starcor/core/domain/FilmListItem:desc	Ljava/lang/String;
    //   408: aload 7
    //   410: ldc 150
    //   412: invokevirtual 48	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   415: ifeq +17 -> 432
    //   418: aload_0
    //   419: getfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   422: aload 7
    //   424: ldc 150
    //   426: invokevirtual 52	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   429: putfield 152	com/starcor/core/domain/FilmListItem:video_id	Ljava/lang/String;
    //   432: aload 7
    //   434: ldc 154
    //   436: invokevirtual 48	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   439: ifeq +17 -> 456
    //   442: aload_0
    //   443: getfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   446: aload 7
    //   448: ldc 154
    //   450: invokevirtual 52	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   453: putfield 157	com/starcor/core/domain/FilmListItem:import_id	Ljava/lang/String;
    //   456: aload 7
    //   458: ldc 159
    //   460: invokevirtual 48	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   463: ifeq +17 -> 480
    //   466: aload_0
    //   467: getfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   470: aload 7
    //   472: ldc 159
    //   474: invokevirtual 52	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   477: putfield 162	com/starcor/core/domain/FilmListItem:serial_id	Ljava/lang/String;
    //   480: aload 7
    //   482: ldc 164
    //   484: invokevirtual 48	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   487: ifeq +17 -> 504
    //   490: aload_0
    //   491: getfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   494: aload 7
    //   496: ldc 164
    //   498: invokevirtual 168	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   501: putfield 171	com/starcor/core/domain/FilmListItem:uiStyle	I
    //   504: aload 7
    //   506: ldc 173
    //   508: invokevirtual 48	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   511: ifeq +17 -> 528
    //   514: aload_0
    //   515: getfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   518: aload 7
    //   520: ldc 173
    //   522: invokevirtual 52	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   525: putfield 175	com/starcor/core/domain/FilmListItem:corner_mark	Ljava/lang/String;
    //   528: aload 7
    //   530: ldc 177
    //   532: invokevirtual 48	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   535: ifeq +17 -> 552
    //   538: aload_0
    //   539: getfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   542: aload 7
    //   544: ldc 177
    //   546: invokevirtual 52	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   549: putfield 180	com/starcor/core/domain/FilmListItem:corner_mark_img_url	Ljava/lang/String;
    //   552: aload 7
    //   554: ldc 182
    //   556: invokevirtual 48	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   559: ifeq +23 -> 582
    //   562: aload_0
    //   563: getfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   566: aload 7
    //   568: ldc 182
    //   570: invokevirtual 52	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   573: invokestatic 58	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   576: invokevirtual 62	java/lang/Integer:intValue	()I
    //   579: putfield 184	com/starcor/core/domain/FilmListItem:video_type	I
    //   582: aload 7
    //   584: ldc 186
    //   586: invokevirtual 48	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   589: ifeq +23 -> 612
    //   592: aload_0
    //   593: getfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   596: aload 7
    //   598: ldc 186
    //   600: invokevirtual 52	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   603: invokestatic 58	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   606: invokevirtual 62	java/lang/Integer:intValue	()I
    //   609: putfield 189	com/starcor/core/domain/FilmListItem:viewType	I
    //   612: aload 7
    //   614: ldc 191
    //   616: invokevirtual 48	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   619: ifeq +23 -> 642
    //   622: aload_0
    //   623: getfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   626: aload 7
    //   628: ldc 191
    //   630: invokevirtual 52	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   633: invokestatic 58	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   636: invokevirtual 62	java/lang/Integer:intValue	()I
    //   639: putfield 193	com/starcor/core/domain/FilmListItem:billing	I
    //   642: aload 7
    //   644: ldc 195
    //   646: invokevirtual 48	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   649: ifeq +23 -> 672
    //   652: aload_0
    //   653: getfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   656: aload 7
    //   658: ldc 195
    //   660: invokevirtual 52	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   663: invokestatic 58	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   666: invokevirtual 62	java/lang/Integer:intValue	()I
    //   669: putfield 197	com/starcor/core/domain/FilmListItem:play_count	I
    //   672: aload 7
    //   674: ldc 199
    //   676: invokevirtual 48	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   679: ifeq +28 -> 707
    //   682: aload 7
    //   684: ldc 199
    //   686: invokevirtual 52	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   689: invokestatic 204	java/lang/Float:valueOf	(Ljava/lang/String;)Ljava/lang/Float;
    //   692: invokevirtual 208	java/lang/Float:floatValue	()F
    //   695: fstore 16
    //   697: aload_0
    //   698: getfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   701: fload 16
    //   703: f2i
    //   704: putfield 210	com/starcor/core/domain/FilmListItem:user_score	I
    //   707: aload 7
    //   709: ldc 212
    //   711: invokevirtual 48	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   714: ifeq +23 -> 737
    //   717: aload_0
    //   718: getfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   721: aload 7
    //   723: ldc 212
    //   725: invokevirtual 52	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   728: invokestatic 58	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   731: invokevirtual 62	java/lang/Integer:intValue	()I
    //   734: putfield 215	com/starcor/core/domain/FilmListItem:point	I
    //   737: aload_0
    //   738: getfield 20	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:filmItem	Lcom/starcor/core/domain/FilmItem;
    //   741: getfield 27	com/starcor/core/domain/FilmItem:filmList	Ljava/util/ArrayList;
    //   744: aload_0
    //   745: getfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   748: invokevirtual 219	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   751: pop
    //   752: iinc 6 1
    //   755: goto -641 -> 114
    //   758: astore_3
    //   759: aload_3
    //   760: invokevirtual 222	java/lang/Exception:printStackTrace	()V
    //   763: aload_0
    //   764: getfield 20	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:filmItem	Lcom/starcor/core/domain/FilmItem;
    //   767: iconst_0
    //   768: putfield 66	com/starcor/core/domain/FilmItem:film_num	I
    //   771: goto -721 -> 50
    //   774: astore 4
    //   776: aload 4
    //   778: invokevirtual 222	java/lang/Exception:printStackTrace	()V
    //   781: aload_0
    //   782: getfield 20	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:filmItem	Lcom/starcor/core/domain/FilmItem;
    //   785: areturn
    //   786: astore 8
    //   788: aload 8
    //   790: invokevirtual 222	java/lang/Exception:printStackTrace	()V
    //   793: aload_0
    //   794: getfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   797: ldc 224
    //   799: putfield 162	com/starcor/core/domain/FilmListItem:serial_id	Ljava/lang/String;
    //   802: goto -322 -> 480
    //   805: astore 9
    //   807: aload 9
    //   809: invokevirtual 222	java/lang/Exception:printStackTrace	()V
    //   812: aload_0
    //   813: getfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   816: iconst_m1
    //   817: putfield 184	com/starcor/core/domain/FilmListItem:video_type	I
    //   820: goto -238 -> 582
    //   823: astore 10
    //   825: aload 10
    //   827: invokevirtual 222	java/lang/Exception:printStackTrace	()V
    //   830: aload_0
    //   831: getfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   834: iconst_m1
    //   835: putfield 189	com/starcor/core/domain/FilmListItem:viewType	I
    //   838: goto -226 -> 612
    //   841: astore 11
    //   843: aload 11
    //   845: invokevirtual 222	java/lang/Exception:printStackTrace	()V
    //   848: aload_0
    //   849: getfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   852: iconst_0
    //   853: putfield 193	com/starcor/core/domain/FilmListItem:billing	I
    //   856: goto -214 -> 642
    //   859: astore 12
    //   861: aload 12
    //   863: invokevirtual 222	java/lang/Exception:printStackTrace	()V
    //   866: aload_0
    //   867: getfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   870: iconst_0
    //   871: putfield 197	com/starcor/core/domain/FilmListItem:play_count	I
    //   874: goto -202 -> 672
    //   877: astore 13
    //   879: aload 13
    //   881: invokevirtual 222	java/lang/Exception:printStackTrace	()V
    //   884: aload_0
    //   885: getfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   888: iconst_0
    //   889: putfield 210	com/starcor/core/domain/FilmListItem:user_score	I
    //   892: goto -185 -> 707
    //   895: astore 14
    //   897: aload 14
    //   899: invokevirtual 222	java/lang/Exception:printStackTrace	()V
    //   902: aload_0
    //   903: getfield 91	com/starcor/core/parser/json/GetRelevantFilmsSAXParserJson:fItem	Lcom/starcor/core/domain/FilmListItem;
    //   906: iconst_0
    //   907: putfield 215	com/starcor/core/domain/FilmListItem:point	I
    //   910: goto -173 -> 737
    //
    // Exception table:
    //   from	to	target	type
    //   22	50	758	java/lang/Exception
    //   6	22	774	java/lang/Exception
    //   50	72	774	java/lang/Exception
    //   72	94	774	java/lang/Exception
    //   94	111	774	java/lang/Exception
    //   114	168	774	java/lang/Exception
    //   168	192	774	java/lang/Exception
    //   192	216	774	java/lang/Exception
    //   216	240	774	java/lang/Exception
    //   240	264	774	java/lang/Exception
    //   264	288	774	java/lang/Exception
    //   288	312	774	java/lang/Exception
    //   312	336	774	java/lang/Exception
    //   336	360	774	java/lang/Exception
    //   360	384	774	java/lang/Exception
    //   384	408	774	java/lang/Exception
    //   408	432	774	java/lang/Exception
    //   432	456	774	java/lang/Exception
    //   480	504	774	java/lang/Exception
    //   504	528	774	java/lang/Exception
    //   528	552	774	java/lang/Exception
    //   737	752	774	java/lang/Exception
    //   759	771	774	java/lang/Exception
    //   788	802	774	java/lang/Exception
    //   807	820	774	java/lang/Exception
    //   825	838	774	java/lang/Exception
    //   843	856	774	java/lang/Exception
    //   861	874	774	java/lang/Exception
    //   879	892	774	java/lang/Exception
    //   897	910	774	java/lang/Exception
    //   456	480	786	java/lang/Exception
    //   552	582	805	java/lang/Exception
    //   582	612	823	java/lang/Exception
    //   612	642	841	java/lang/Exception
    //   642	672	859	java/lang/Exception
    //   672	707	877	java/lang/Exception
    //   707	737	895	java/lang/Exception
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetRelevantFilmsSAXParserJson
 * JD-Core Version:    0.6.2
 */