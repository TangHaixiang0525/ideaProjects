package com.starcor.core.parser.json;

import com.starcor.core.domain.CollectListItem;
import com.starcor.core.interfaces.IXmlParser;
import java.io.InputStream;
import java.util.ArrayList;

public class GetCollectListV2SAXParserJson<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = "GetCollectListSAXParserJson";
  private ArrayList<CollectListItem> collectList;

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
    //   7: getfield 25	com/starcor/core/parser/json/GetCollectListV2SAXParserJson:collectList	Ljava/util/ArrayList;
    //   10: ifnonnull +914 -> 924
    //   13: aload_0
    //   14: new 27	java/util/ArrayList
    //   17: dup
    //   18: invokespecial 28	java/util/ArrayList:<init>	()V
    //   21: putfield 25	com/starcor/core/parser/json/GetCollectListV2SAXParserJson:collectList	Ljava/util/ArrayList;
    //   24: new 30	org/json/JSONObject
    //   27: dup
    //   28: new 32	java/lang/String
    //   31: dup
    //   32: aload_1
    //   33: invokespecial 35	java/lang/String:<init>	([B)V
    //   36: invokespecial 38	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   39: astore_2
    //   40: aload_2
    //   41: ldc 40
    //   43: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   46: ifeq +904 -> 950
    //   49: new 46	org/json/JSONArray
    //   52: dup
    //   53: aload_2
    //   54: ldc 40
    //   56: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   59: invokespecial 51	org/json/JSONArray:<init>	(Ljava/lang/String;)V
    //   62: astore 5
    //   64: iconst_0
    //   65: istore 6
    //   67: iload 6
    //   69: aload 5
    //   71: invokevirtual 55	org/json/JSONArray:length	()I
    //   74: if_icmpge +876 -> 950
    //   77: new 57	com/starcor/core/domain/CollectListItem
    //   80: dup
    //   81: invokespecial 58	com/starcor/core/domain/CollectListItem:<init>	()V
    //   84: astore 7
    //   86: aload 5
    //   88: iload 6
    //   90: invokevirtual 62	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   93: astore 8
    //   95: aload 8
    //   97: ldc 64
    //   99: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   102: ifeq +15 -> 117
    //   105: aload 7
    //   107: aload 8
    //   109: ldc 64
    //   111: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   114: putfield 66	com/starcor/core/domain/CollectListItem:type	Ljava/lang/String;
    //   117: aload 8
    //   119: ldc 68
    //   121: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   124: ifeq +15 -> 139
    //   127: aload 7
    //   129: aload 8
    //   131: ldc 68
    //   133: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   136: putfield 70	com/starcor/core/domain/CollectListItem:id	Ljava/lang/String;
    //   139: aload 8
    //   141: ldc 72
    //   143: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   146: ifeq +15 -> 161
    //   149: aload 7
    //   151: aload 8
    //   153: ldc 72
    //   155: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   158: putfield 75	com/starcor/core/domain/CollectListItem:video_name	Ljava/lang/String;
    //   161: aload 8
    //   163: ldc 77
    //   165: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   168: ifeq +15 -> 183
    //   171: aload 7
    //   173: aload 8
    //   175: ldc 77
    //   177: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   180: putfield 79	com/starcor/core/domain/CollectListItem:img_v	Ljava/lang/String;
    //   183: aload 8
    //   185: ldc 81
    //   187: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   190: ifeq +15 -> 205
    //   193: aload 7
    //   195: aload 8
    //   197: ldc 81
    //   199: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   202: putfield 83	com/starcor/core/domain/CollectListItem:img_s	Ljava/lang/String;
    //   205: aload 8
    //   207: ldc 85
    //   209: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   212: ifeq +15 -> 227
    //   215: aload 7
    //   217: aload 8
    //   219: ldc 85
    //   221: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   224: putfield 87	com/starcor/core/domain/CollectListItem:img_h	Ljava/lang/String;
    //   227: aload 8
    //   229: ldc 89
    //   231: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   234: ifeq +15 -> 249
    //   237: aload 7
    //   239: aload 8
    //   241: ldc 89
    //   243: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   246: putfield 91	com/starcor/core/domain/CollectListItem:info	Ljava/lang/String;
    //   249: aload 8
    //   251: ldc 93
    //   253: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   256: ifeq +793 -> 1049
    //   259: new 46	org/json/JSONArray
    //   262: dup
    //   263: aload_2
    //   264: ldc 93
    //   266: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   269: invokespecial 51	org/json/JSONArray:<init>	(Ljava/lang/String;)V
    //   272: astore 9
    //   274: iconst_0
    //   275: istore 10
    //   277: iload 10
    //   279: aload 9
    //   281: invokevirtual 55	org/json/JSONArray:length	()I
    //   284: if_icmpge +765 -> 1049
    //   287: aload 9
    //   289: iload 6
    //   291: invokevirtual 62	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   294: astore 12
    //   296: aload 12
    //   298: ldc 95
    //   300: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   303: ifeq +15 -> 318
    //   306: aload 7
    //   308: aload 12
    //   310: ldc 95
    //   312: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   315: putfield 97	com/starcor/core/domain/CollectListItem:video_id	Ljava/lang/String;
    //   318: aload 12
    //   320: ldc 99
    //   322: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   325: istore 13
    //   327: iload 13
    //   329: ifeq +15 -> 344
    //   332: aload 7
    //   334: aload 12
    //   336: ldc 99
    //   338: invokevirtual 103	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   341: putfield 106	com/starcor/core/domain/CollectListItem:video_type	I
    //   344: aload 12
    //   346: ldc 108
    //   348: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   351: ifeq +15 -> 366
    //   354: aload 7
    //   356: aload 12
    //   358: ldc 108
    //   360: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   363: putfield 110	com/starcor/core/domain/CollectListItem:sub_name	Ljava/lang/String;
    //   366: aload 12
    //   368: ldc 112
    //   370: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   373: ifeq +59 -> 432
    //   376: aload 7
    //   378: aload 12
    //   380: ldc 112
    //   382: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   385: putfield 115	com/starcor/core/domain/CollectListItem:ts_begin	Ljava/lang/String;
    //   388: aload 12
    //   390: ldc 112
    //   392: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   395: astore 27
    //   397: aload 27
    //   399: invokevirtual 116	java/lang/String:length	()I
    //   402: bipush 8
    //   404: if_icmple +28 -> 432
    //   407: aload 7
    //   409: aload 27
    //   411: iconst_0
    //   412: bipush 8
    //   414: invokevirtual 120	java/lang/String:substring	(II)Ljava/lang/String;
    //   417: putfield 123	com/starcor/core/domain/CollectListItem:ts_day	Ljava/lang/String;
    //   420: aload 7
    //   422: aload 27
    //   424: bipush 8
    //   426: invokevirtual 126	java/lang/String:substring	(I)Ljava/lang/String;
    //   429: putfield 115	com/starcor/core/domain/CollectListItem:ts_begin	Ljava/lang/String;
    //   432: aload 12
    //   434: ldc 128
    //   436: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   439: ifeq +15 -> 454
    //   442: aload 7
    //   444: aload 12
    //   446: ldc 128
    //   448: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   451: putfield 131	com/starcor/core/domain/CollectListItem:ts_time_len	Ljava/lang/String;
    //   454: aload 12
    //   456: ldc 133
    //   458: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   461: istore 14
    //   463: iload 14
    //   465: ifeq +15 -> 480
    //   468: aload 7
    //   470: aload 12
    //   472: ldc 133
    //   474: invokevirtual 103	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   477: putfield 135	com/starcor/core/domain/CollectListItem:video_index	I
    //   480: aload 12
    //   482: ldc 137
    //   484: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   487: ifeq +15 -> 502
    //   490: aload 7
    //   492: aload 12
    //   494: ldc 137
    //   496: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   499: putfield 139	com/starcor/core/domain/CollectListItem:media_assets_id	Ljava/lang/String;
    //   502: aload 12
    //   504: ldc 141
    //   506: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   509: ifeq +15 -> 524
    //   512: aload 7
    //   514: aload 12
    //   516: ldc 141
    //   518: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   521: putfield 144	com/starcor/core/domain/CollectListItem:category_id	Ljava/lang/String;
    //   524: aload 12
    //   526: ldc 146
    //   528: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   531: ifeq +15 -> 546
    //   534: aload 7
    //   536: aload 12
    //   538: ldc 146
    //   540: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   543: putfield 148	com/starcor/core/domain/CollectListItem:version	Ljava/lang/String;
    //   546: aload 12
    //   548: ldc 150
    //   550: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   553: istore 15
    //   555: iload 15
    //   557: ifeq +15 -> 572
    //   560: aload 7
    //   562: aload 12
    //   564: ldc 150
    //   566: invokevirtual 103	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   569: putfield 153	com/starcor/core/domain/CollectListItem:played_time	I
    //   572: aload 12
    //   574: ldc 155
    //   576: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   579: istore 16
    //   581: iload 16
    //   583: ifeq +15 -> 598
    //   586: aload 7
    //   588: aload 12
    //   590: ldc 155
    //   592: invokevirtual 103	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   595: putfield 157	com/starcor/core/domain/CollectListItem:video_all_index	I
    //   598: aload 12
    //   600: ldc 159
    //   602: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   605: istore 17
    //   607: iload 17
    //   609: ifeq +15 -> 624
    //   612: aload 7
    //   614: aload 12
    //   616: ldc 159
    //   618: invokevirtual 103	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   621: putfield 162	com/starcor/core/domain/CollectListItem:update_index	I
    //   624: aload 12
    //   626: ldc 164
    //   628: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   631: istore 18
    //   633: iload 18
    //   635: ifeq +15 -> 650
    //   638: aload 7
    //   640: aload 12
    //   642: ldc 164
    //   644: invokevirtual 103	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   647: putfield 166	com/starcor/core/domain/CollectListItem:view_type	I
    //   650: aload 12
    //   652: ldc 168
    //   654: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   657: ifeq +15 -> 672
    //   660: aload 7
    //   662: aload 12
    //   664: ldc 164
    //   666: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   669: putfield 170	com/starcor/core/domain/CollectListItem:index_ui_style	Ljava/lang/String;
    //   672: aload 12
    //   674: ldc 172
    //   676: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   679: ifeq +15 -> 694
    //   682: aload 7
    //   684: aload 12
    //   686: ldc 172
    //   688: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   691: putfield 174	com/starcor/core/domain/CollectListItem:video_director	Ljava/lang/String;
    //   694: aload 12
    //   696: ldc 176
    //   698: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   701: ifeq +15 -> 716
    //   704: aload 7
    //   706: aload 12
    //   708: ldc 176
    //   710: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   713: putfield 178	com/starcor/core/domain/CollectListItem:video_actor	Ljava/lang/String;
    //   716: aload 12
    //   718: ldc 180
    //   720: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   723: ifeq +15 -> 738
    //   726: aload 7
    //   728: aload 12
    //   730: ldc 180
    //   732: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   735: putfield 182	com/starcor/core/domain/CollectListItem:video_kind	Ljava/lang/String;
    //   738: aload 12
    //   740: ldc 184
    //   742: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   745: ifeq +15 -> 760
    //   748: aload 7
    //   750: aload 12
    //   752: ldc 184
    //   754: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   757: putfield 187	com/starcor/core/domain/CollectListItem:add_time	Ljava/lang/String;
    //   760: aload 12
    //   762: ldc 188
    //   764: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   767: ifeq +15 -> 782
    //   770: aload 7
    //   772: aload 12
    //   774: ldc 188
    //   776: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   779: putfield 187	com/starcor/core/domain/CollectListItem:add_time	Ljava/lang/String;
    //   782: aload 12
    //   784: ldc 190
    //   786: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   789: ifeq +15 -> 804
    //   792: aload 7
    //   794: aload 12
    //   796: ldc 190
    //   798: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   801: putfield 192	com/starcor/core/domain/CollectListItem:quality	Ljava/lang/String;
    //   804: aload 12
    //   806: ldc 194
    //   808: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   811: ifeq +15 -> 826
    //   814: aload 7
    //   816: aload 12
    //   818: ldc 194
    //   820: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   823: putfield 196	com/starcor/core/domain/CollectListItem:mark	Ljava/lang/String;
    //   826: aload 12
    //   828: ldc 198
    //   830: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   833: ifeq +15 -> 848
    //   836: aload 7
    //   838: aload 12
    //   840: ldc 198
    //   842: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   845: putfield 200	com/starcor/core/domain/CollectListItem:corner_mark_img	Ljava/lang/String;
    //   848: aload 12
    //   850: ldc 202
    //   852: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   855: istore 19
    //   857: iload 19
    //   859: ifeq +15 -> 874
    //   862: aload 7
    //   864: aload 12
    //   866: ldc 202
    //   868: invokevirtual 103	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   871: putfield 204	com/starcor/core/domain/CollectListItem:online	I
    //   874: aload 12
    //   876: ldc 206
    //   878: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   881: ifeq +15 -> 896
    //   884: aload 7
    //   886: aload 12
    //   888: ldc 206
    //   890: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   893: putfield 208	com/starcor/core/domain/CollectListItem:new_index_release_time	Ljava/lang/String;
    //   896: aload 12
    //   898: ldc 210
    //   900: invokevirtual 44	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   903: ifeq +15 -> 918
    //   906: aload 7
    //   908: aload 12
    //   910: ldc 210
    //   912: invokevirtual 50	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   915: putfield 212	com/starcor/core/domain/CollectListItem:current_index_release_time	Ljava/lang/String;
    //   918: iinc 10 1
    //   921: goto -644 -> 277
    //   924: aload_0
    //   925: getfield 25	com/starcor/core/parser/json/GetCollectListV2SAXParserJson:collectList	Ljava/util/ArrayList;
    //   928: invokevirtual 215	java/util/ArrayList:clear	()V
    //   931: goto -907 -> 24
    //   934: astore 28
    //   936: aload 7
    //   938: iconst_1
    //   939: putfield 106	com/starcor/core/domain/CollectListItem:video_type	I
    //   942: goto -598 -> 344
    //   945: astore_3
    //   946: aload_3
    //   947: invokevirtual 218	java/lang/Exception:printStackTrace	()V
    //   950: ldc 11
    //   952: new 220	java/lang/StringBuilder
    //   955: dup
    //   956: invokespecial 221	java/lang/StringBuilder:<init>	()V
    //   959: ldc 223
    //   961: invokevirtual 227	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   964: aload_1
    //   965: invokevirtual 231	java/lang/Object:toString	()Ljava/lang/String;
    //   968: invokevirtual 227	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   971: invokevirtual 232	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   974: invokestatic 238	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   977: pop
    //   978: aload_0
    //   979: getfield 25	com/starcor/core/parser/json/GetCollectListV2SAXParserJson:collectList	Ljava/util/ArrayList;
    //   982: areturn
    //   983: astore 25
    //   985: aload 7
    //   987: iconst_0
    //   988: putfield 135	com/starcor/core/domain/CollectListItem:video_index	I
    //   991: goto -511 -> 480
    //   994: astore 24
    //   996: aload 7
    //   998: iconst_0
    //   999: putfield 153	com/starcor/core/domain/CollectListItem:played_time	I
    //   1002: goto -430 -> 572
    //   1005: astore 23
    //   1007: aload 7
    //   1009: iconst_0
    //   1010: putfield 157	com/starcor/core/domain/CollectListItem:video_all_index	I
    //   1013: goto -415 -> 598
    //   1016: astore 22
    //   1018: aload 7
    //   1020: iconst_0
    //   1021: putfield 162	com/starcor/core/domain/CollectListItem:update_index	I
    //   1024: goto -400 -> 624
    //   1027: astore 21
    //   1029: aload 7
    //   1031: iconst_0
    //   1032: putfield 166	com/starcor/core/domain/CollectListItem:view_type	I
    //   1035: goto -385 -> 650
    //   1038: astore 20
    //   1040: aload 7
    //   1042: iconst_1
    //   1043: putfield 204	com/starcor/core/domain/CollectListItem:online	I
    //   1046: goto -172 -> 874
    //   1049: aload_0
    //   1050: getfield 25	com/starcor/core/parser/json/GetCollectListV2SAXParserJson:collectList	Ljava/util/ArrayList;
    //   1053: aload 7
    //   1055: invokevirtual 242	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   1058: pop
    //   1059: iinc 6 1
    //   1062: goto -995 -> 67
    //   1065: astore 26
    //   1067: goto -635 -> 432
    //
    // Exception table:
    //   from	to	target	type
    //   332	344	934	java/lang/Exception
    //   24	64	945	java/lang/Exception
    //   67	117	945	java/lang/Exception
    //   117	139	945	java/lang/Exception
    //   139	161	945	java/lang/Exception
    //   161	183	945	java/lang/Exception
    //   183	205	945	java/lang/Exception
    //   205	227	945	java/lang/Exception
    //   227	249	945	java/lang/Exception
    //   249	274	945	java/lang/Exception
    //   277	318	945	java/lang/Exception
    //   318	327	945	java/lang/Exception
    //   344	366	945	java/lang/Exception
    //   366	388	945	java/lang/Exception
    //   432	454	945	java/lang/Exception
    //   454	463	945	java/lang/Exception
    //   480	502	945	java/lang/Exception
    //   502	524	945	java/lang/Exception
    //   524	546	945	java/lang/Exception
    //   546	555	945	java/lang/Exception
    //   572	581	945	java/lang/Exception
    //   598	607	945	java/lang/Exception
    //   624	633	945	java/lang/Exception
    //   650	672	945	java/lang/Exception
    //   672	694	945	java/lang/Exception
    //   694	716	945	java/lang/Exception
    //   716	738	945	java/lang/Exception
    //   738	760	945	java/lang/Exception
    //   760	782	945	java/lang/Exception
    //   782	804	945	java/lang/Exception
    //   804	826	945	java/lang/Exception
    //   826	848	945	java/lang/Exception
    //   848	857	945	java/lang/Exception
    //   874	896	945	java/lang/Exception
    //   896	918	945	java/lang/Exception
    //   936	942	945	java/lang/Exception
    //   985	991	945	java/lang/Exception
    //   996	1002	945	java/lang/Exception
    //   1007	1013	945	java/lang/Exception
    //   1018	1024	945	java/lang/Exception
    //   1029	1035	945	java/lang/Exception
    //   1040	1046	945	java/lang/Exception
    //   1049	1059	945	java/lang/Exception
    //   468	480	983	java/lang/Exception
    //   560	572	994	java/lang/Exception
    //   586	598	1005	java/lang/Exception
    //   612	624	1016	java/lang/Exception
    //   638	650	1027	java/lang/Exception
    //   862	874	1038	java/lang/Exception
    //   388	432	1065	java/lang/Exception
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetCollectListV2SAXParserJson
 * JD-Core Version:    0.6.2
 */