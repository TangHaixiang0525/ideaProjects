package com.starcor.core.parser.json;

import com.starcor.core.domain.SearchLiveListItem;
import com.starcor.core.domain.SearchPlayStruct;
import com.starcor.core.interfaces.IXmlParser;
import java.util.ArrayList;

public class SearchTvBillItemSAXParserJson<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = "SearchTvBillItemSAXParserJson";
  private SearchLiveListItem item;
  private SearchPlayStruct searchLiveStruct = new SearchPlayStruct();

  public SearchTvBillItemSAXParserJson()
  {
    this.searchLiveStruct.searchItems = new ArrayList();
  }

  // ERROR //
  public Result parser(java.io.InputStream paramInputStream)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull +5 -> 6
    //   4: aconst_null
    //   5: areturn
    //   6: new 37	java/lang/String
    //   9: dup
    //   10: aload_1
    //   11: invokestatic 43	com/starcor/core/utils/StreamTools:getBytes	(Ljava/io/InputStream;)[B
    //   14: invokespecial 46	java/lang/String:<init>	([B)V
    //   17: invokestatic 52	com/starcor/core/utils/JsonUtils:getJsonObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   20: astore_3
    //   21: aload_3
    //   22: ldc 54
    //   24: invokevirtual 60	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   27: ifeq +22 -> 49
    //   30: aload_0
    //   31: getfield 24	com/starcor/core/parser/json/SearchTvBillItemSAXParserJson:searchLiveStruct	Lcom/starcor/core/domain/SearchPlayStruct;
    //   34: aload_3
    //   35: ldc 54
    //   37: invokevirtual 64	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   40: invokestatic 70	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   43: invokevirtual 74	java/lang/Integer:intValue	()I
    //   46: putfield 78	com/starcor/core/domain/SearchPlayStruct:item_num	I
    //   49: aload_3
    //   50: ldc 79
    //   52: invokevirtual 60	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   55: ifeq +245 -> 300
    //   58: aload_3
    //   59: ldc 79
    //   61: invokestatic 83	com/starcor/core/utils/JsonUtils:getJsonString	(Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/String;
    //   64: invokestatic 87	com/starcor/core/utils/JsonUtils:getJsonArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   67: astore 5
    //   69: iconst_0
    //   70: istore 6
    //   72: iload 6
    //   74: aload 5
    //   76: invokevirtual 92	org/json/JSONArray:length	()I
    //   79: if_icmpge +221 -> 300
    //   82: aload_0
    //   83: new 94	com/starcor/core/domain/SearchLiveListItem
    //   86: dup
    //   87: invokespecial 95	com/starcor/core/domain/SearchLiveListItem:<init>	()V
    //   90: putfield 97	com/starcor/core/parser/json/SearchTvBillItemSAXParserJson:item	Lcom/starcor/core/domain/SearchLiveListItem;
    //   93: aload 5
    //   95: iload 6
    //   97: invokevirtual 101	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   100: astore 7
    //   102: aload 7
    //   104: ldc 103
    //   106: invokevirtual 60	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   109: ifeq +17 -> 126
    //   112: aload_0
    //   113: getfield 97	com/starcor/core/parser/json/SearchTvBillItemSAXParserJson:item	Lcom/starcor/core/domain/SearchLiveListItem;
    //   116: aload 7
    //   118: ldc 103
    //   120: invokevirtual 64	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   123: putfield 106	com/starcor/core/domain/SearchLiveListItem:desc	Ljava/lang/String;
    //   126: aload 7
    //   128: ldc 108
    //   130: invokevirtual 60	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   133: ifeq +17 -> 150
    //   136: aload_0
    //   137: getfield 97	com/starcor/core/parser/json/SearchTvBillItemSAXParserJson:item	Lcom/starcor/core/domain/SearchLiveListItem;
    //   140: aload 7
    //   142: ldc 108
    //   144: invokevirtual 64	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   147: putfield 110	com/starcor/core/domain/SearchLiveListItem:video_id	Ljava/lang/String;
    //   150: aload 7
    //   152: ldc 112
    //   154: invokevirtual 60	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   157: istore 8
    //   159: iload 8
    //   161: ifeq +23 -> 184
    //   164: aload_0
    //   165: getfield 97	com/starcor/core/parser/json/SearchTvBillItemSAXParserJson:item	Lcom/starcor/core/domain/SearchLiveListItem;
    //   168: aload 7
    //   170: ldc 112
    //   172: invokevirtual 64	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   175: invokestatic 70	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   178: invokevirtual 74	java/lang/Integer:intValue	()I
    //   181: putfield 114	com/starcor/core/domain/SearchLiveListItem:video_type	I
    //   184: aload 7
    //   186: ldc 116
    //   188: invokevirtual 60	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   191: ifeq +17 -> 208
    //   194: aload_0
    //   195: getfield 97	com/starcor/core/parser/json/SearchTvBillItemSAXParserJson:item	Lcom/starcor/core/domain/SearchLiveListItem;
    //   198: aload 7
    //   200: ldc 116
    //   202: invokevirtual 64	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   205: putfield 118	com/starcor/core/domain/SearchLiveListItem:day	Ljava/lang/String;
    //   208: aload 7
    //   210: ldc 120
    //   212: invokevirtual 60	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   215: ifeq +17 -> 232
    //   218: aload_0
    //   219: getfield 97	com/starcor/core/parser/json/SearchTvBillItemSAXParserJson:item	Lcom/starcor/core/domain/SearchLiveListItem;
    //   222: aload 7
    //   224: ldc 120
    //   226: invokevirtual 64	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   229: putfield 123	com/starcor/core/domain/SearchLiveListItem:time_begin	Ljava/lang/String;
    //   232: aload 7
    //   234: ldc 125
    //   236: invokevirtual 60	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   239: ifeq +17 -> 256
    //   242: aload_0
    //   243: getfield 97	com/starcor/core/parser/json/SearchTvBillItemSAXParserJson:item	Lcom/starcor/core/domain/SearchLiveListItem;
    //   246: aload 7
    //   248: ldc 125
    //   250: invokevirtual 64	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   253: putfield 127	com/starcor/core/domain/SearchLiveListItem:time_len	Ljava/lang/String;
    //   256: aload_0
    //   257: getfield 24	com/starcor/core/parser/json/SearchTvBillItemSAXParserJson:searchLiveStruct	Lcom/starcor/core/domain/SearchPlayStruct;
    //   260: getfield 31	com/starcor/core/domain/SearchPlayStruct:searchItems	Ljava/util/ArrayList;
    //   263: aload_0
    //   264: getfield 97	com/starcor/core/parser/json/SearchTvBillItemSAXParserJson:item	Lcom/starcor/core/domain/SearchLiveListItem;
    //   267: invokevirtual 131	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   270: pop
    //   271: iinc 6 1
    //   274: goto -202 -> 72
    //   277: astore 4
    //   279: aload 4
    //   281: invokevirtual 134	java/lang/Exception:printStackTrace	()V
    //   284: aload_0
    //   285: getfield 24	com/starcor/core/parser/json/SearchTvBillItemSAXParserJson:searchLiveStruct	Lcom/starcor/core/domain/SearchPlayStruct;
    //   288: iconst_0
    //   289: putfield 78	com/starcor/core/domain/SearchPlayStruct:item_num	I
    //   292: goto -243 -> 49
    //   295: astore_2
    //   296: aload_2
    //   297: invokevirtual 134	java/lang/Exception:printStackTrace	()V
    //   300: aload_0
    //   301: getfield 24	com/starcor/core/parser/json/SearchTvBillItemSAXParserJson:searchLiveStruct	Lcom/starcor/core/domain/SearchPlayStruct;
    //   304: areturn
    //   305: astore 10
    //   307: aload 10
    //   309: invokevirtual 134	java/lang/Exception:printStackTrace	()V
    //   312: aload_0
    //   313: getfield 97	com/starcor/core/parser/json/SearchTvBillItemSAXParserJson:item	Lcom/starcor/core/domain/SearchLiveListItem;
    //   316: iconst_2
    //   317: putfield 114	com/starcor/core/domain/SearchLiveListItem:video_type	I
    //   320: goto -136 -> 184
    //
    // Exception table:
    //   from	to	target	type
    //   21	49	277	java/lang/Exception
    //   6	21	295	java/lang/Exception
    //   49	69	295	java/lang/Exception
    //   72	126	295	java/lang/Exception
    //   126	150	295	java/lang/Exception
    //   150	159	295	java/lang/Exception
    //   184	208	295	java/lang/Exception
    //   208	232	295	java/lang/Exception
    //   232	256	295	java/lang/Exception
    //   256	271	295	java/lang/Exception
    //   279	292	295	java/lang/Exception
    //   307	320	295	java/lang/Exception
    //   164	184	305	java/lang/Exception
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.SearchTvBillItemSAXParserJson
 * JD-Core Version:    0.6.2
 */