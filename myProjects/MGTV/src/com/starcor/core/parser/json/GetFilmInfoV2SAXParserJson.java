package com.starcor.core.parser.json;

import com.starcor.core.domain.MediaInfo;
import com.starcor.core.domain.VideoIndex;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.domain.VideoRoleInfo;
import com.starcor.core.interfaces.IXmlParser;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GetFilmInfoV2SAXParserJson<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = "GetFilmInfoV2SAXParserJson";
  private VideoInfo videoInfo = new VideoInfo();

  private String getJsonString(JSONObject paramJSONObject, String paramString)
    throws JSONException
  {
    if (paramJSONObject.has(paramString))
    {
      if (paramJSONObject.get(paramString) == JSONObject.NULL)
        return "";
      return paramJSONObject.getString(paramString);
    }
    return "";
  }

  private void loadActorList(JSONObject paramJSONObject)
  {
    try
    {
      this.videoInfo.actorList = new ArrayList();
      JSONArray localJSONArray = paramJSONObject.getJSONArray("actor_list");
      int i = 0;
      while (true)
        if (i < localJSONArray.length())
        {
          VideoRoleInfo localVideoRoleInfo = new VideoRoleInfo();
          JSONObject localJSONObject = localJSONArray.getJSONObject(i);
          if (localJSONObject.has("name"))
            localVideoRoleInfo.name = localJSONObject.getString("name");
          try
          {
            localVideoRoleInfo.hasLabel = Integer.valueOf(localJSONObject.getString("label")).intValue();
            if (localJSONObject.has("id"))
              localVideoRoleInfo.labelID = localJSONObject.getString("id");
            this.videoInfo.actorList.add(localVideoRoleInfo);
            i++;
          }
          catch (NumberFormatException localNumberFormatException)
          {
            while (true)
              localVideoRoleInfo.hasLabel = 0;
          }
        }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  // ERROR //
  private void loadBasicData(JSONObject paramJSONObject)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   4: aload_0
    //   5: aload_1
    //   6: ldc 99
    //   8: invokespecial 112	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:getJsonString	(Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/String;
    //   11: putfield 115	com/starcor/core/domain/VideoInfo:videoId	Ljava/lang/String;
    //   14: aload_0
    //   15: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   18: aload_0
    //   19: aload_1
    //   20: ldc 80
    //   22: invokespecial 112	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:getJsonString	(Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/String;
    //   25: putfield 116	com/starcor/core/domain/VideoInfo:name	Ljava/lang/String;
    //   28: aload_0
    //   29: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   32: aload_0
    //   33: aload_1
    //   34: ldc 118
    //   36: invokespecial 112	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:getJsonString	(Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/String;
    //   39: putfield 120	com/starcor/core/domain/VideoInfo:import_id	Ljava/lang/String;
    //   42: aload_0
    //   43: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   46: aload_0
    //   47: aload_1
    //   48: ldc 122
    //   50: invokespecial 112	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:getJsonString	(Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/String;
    //   53: putfield 125	com/starcor/core/domain/VideoInfo:serial_id	Ljava/lang/String;
    //   56: aload_0
    //   57: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   60: aload_0
    //   61: aload_1
    //   62: ldc 127
    //   64: invokespecial 112	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:getJsonString	(Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/String;
    //   67: putfield 130	com/starcor/core/domain/VideoInfo:keyWords	Ljava/lang/String;
    //   70: aload_0
    //   71: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   74: aload_0
    //   75: aload_1
    //   76: ldc 132
    //   78: invokespecial 112	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:getJsonString	(Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/String;
    //   81: putfield 134	com/starcor/core/domain/VideoInfo:index_ui_type	Ljava/lang/String;
    //   84: aload_0
    //   85: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   88: aload_0
    //   89: aload_1
    //   90: ldc 136
    //   92: invokespecial 112	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:getJsonString	(Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/String;
    //   95: putfield 138	com/starcor/core/domain/VideoInfo:watch_focus	Ljava/lang/String;
    //   98: aload_0
    //   99: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   102: aload_0
    //   103: aload_1
    //   104: ldc 140
    //   106: invokespecial 112	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:getJsonString	(Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/String;
    //   109: putfield 143	com/starcor/core/domain/VideoInfo:imgBigUrl	Ljava/lang/String;
    //   112: aload_0
    //   113: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   116: aload_0
    //   117: aload_1
    //   118: ldc 145
    //   120: invokespecial 112	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:getJsonString	(Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/String;
    //   123: putfield 148	com/starcor/core/domain/VideoInfo:imgNormalUrl	Ljava/lang/String;
    //   126: aload_0
    //   127: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   130: aload_0
    //   131: aload_1
    //   132: ldc 150
    //   134: invokespecial 112	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:getJsonString	(Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/String;
    //   137: putfield 153	com/starcor/core/domain/VideoInfo:imgSmallUrl	Ljava/lang/String;
    //   140: aload_0
    //   141: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   144: aload_0
    //   145: aload_1
    //   146: ldc 155
    //   148: invokespecial 112	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:getJsonString	(Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/String;
    //   151: putfield 158	com/starcor/core/domain/VideoInfo:imgVUrl	Ljava/lang/String;
    //   154: aload_0
    //   155: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   158: aload_0
    //   159: aload_1
    //   160: ldc 160
    //   162: invokespecial 112	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:getJsonString	(Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/String;
    //   165: putfield 163	com/starcor/core/domain/VideoInfo:imgSUrl	Ljava/lang/String;
    //   168: aload_0
    //   169: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   172: aload_0
    //   173: aload_1
    //   174: ldc 165
    //   176: invokespecial 112	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:getJsonString	(Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/String;
    //   179: putfield 168	com/starcor/core/domain/VideoInfo:imgHUrl	Ljava/lang/String;
    //   182: aload_0
    //   183: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   186: aload_0
    //   187: aload_1
    //   188: ldc 170
    //   190: invokespecial 112	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:getJsonString	(Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/String;
    //   193: putfield 173	com/starcor/core/domain/VideoInfo:timeLen	Ljava/lang/String;
    //   196: aload_0
    //   197: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   200: aload_0
    //   201: aload_1
    //   202: ldc 175
    //   204: invokespecial 112	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:getJsonString	(Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/String;
    //   207: putfield 177	com/starcor/core/domain/VideoInfo:area	Ljava/lang/String;
    //   210: aload_0
    //   211: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   214: aload_0
    //   215: aload_1
    //   216: ldc 179
    //   218: invokespecial 112	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:getJsonString	(Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/String;
    //   221: putfield 181	com/starcor/core/domain/VideoInfo:actor	Ljava/lang/String;
    //   224: ldc 183
    //   226: new 185	java/lang/StringBuilder
    //   229: dup
    //   230: invokespecial 186	java/lang/StringBuilder:<init>	()V
    //   233: ldc 188
    //   235: invokevirtual 192	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   238: aload_1
    //   239: ldc 194
    //   241: invokevirtual 46	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   244: invokevirtual 192	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   247: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   250: invokestatic 204	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   253: aload_0
    //   254: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   257: aload_1
    //   258: ldc 194
    //   260: invokevirtual 46	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   263: invokestatic 90	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   266: invokevirtual 93	java/lang/Integer:intValue	()I
    //   269: putfield 207	com/starcor/core/domain/VideoInfo:uiStyle	I
    //   272: aload_0
    //   273: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   276: aload_0
    //   277: aload_1
    //   278: ldc 209
    //   280: invokespecial 112	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:getJsonString	(Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/String;
    //   283: putfield 211	com/starcor/core/domain/VideoInfo:director	Ljava/lang/String;
    //   286: aload_0
    //   287: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   290: aload_0
    //   291: aload_1
    //   292: ldc 213
    //   294: invokespecial 112	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:getJsonString	(Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/String;
    //   297: putfield 215	com/starcor/core/domain/VideoInfo:desc	Ljava/lang/String;
    //   300: aload_0
    //   301: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   304: aload_0
    //   305: aload_1
    //   306: ldc 217
    //   308: invokespecial 112	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:getJsonString	(Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/String;
    //   311: putfield 220	com/starcor/core/domain/VideoInfo:showTime	Ljava/lang/String;
    //   314: aload_0
    //   315: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   318: aload_0
    //   319: aload_1
    //   320: ldc 222
    //   322: invokespecial 112	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:getJsonString	(Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/String;
    //   325: putfield 224	com/starcor/core/domain/VideoInfo:kind	Ljava/lang/String;
    //   328: aload_0
    //   329: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   332: aload_0
    //   333: aload_1
    //   334: ldc 226
    //   336: invokespecial 112	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:getJsonString	(Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/String;
    //   339: putfield 228	com/starcor/core/domain/VideoInfo:corner_mark	Ljava/lang/String;
    //   342: aload_0
    //   343: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   346: aload_0
    //   347: aload_1
    //   348: ldc 230
    //   350: invokespecial 112	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:getJsonString	(Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/String;
    //   353: putfield 233	com/starcor/core/domain/VideoInfo:corner_mark_img_url	Ljava/lang/String;
    //   356: aload_0
    //   357: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   360: aload_1
    //   361: ldc 235
    //   363: invokevirtual 46	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   366: invokestatic 90	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   369: invokevirtual 93	java/lang/Integer:intValue	()I
    //   372: putfield 237	com/starcor/core/domain/VideoInfo:billing	I
    //   375: aload_0
    //   376: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   379: aload_1
    //   380: ldc 239
    //   382: invokevirtual 46	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   385: invokestatic 90	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   388: invokevirtual 93	java/lang/Integer:intValue	()I
    //   391: putfield 241	com/starcor/core/domain/VideoInfo:play_count	I
    //   394: aload_0
    //   395: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   398: aload_1
    //   399: ldc 243
    //   401: invokevirtual 46	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   404: invokestatic 90	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   407: invokevirtual 93	java/lang/Integer:intValue	()I
    //   410: putfield 245	com/starcor/core/domain/VideoInfo:view_type	I
    //   413: aload_0
    //   414: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   417: aload_1
    //   418: ldc 247
    //   420: invokevirtual 46	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   423: invokestatic 90	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   426: invokevirtual 93	java/lang/Integer:intValue	()I
    //   429: putfield 250	com/starcor/core/domain/VideoInfo:indexCount	I
    //   432: aload_0
    //   433: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   436: aload_1
    //   437: ldc 252
    //   439: invokevirtual 46	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   442: invokestatic 90	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   445: invokevirtual 93	java/lang/Integer:intValue	()I
    //   448: putfield 255	com/starcor/core/domain/VideoInfo:indexCurrent	I
    //   451: aload_0
    //   452: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   455: ldc_w 256
    //   458: aload_1
    //   459: ldc_w 258
    //   462: invokevirtual 46	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   465: invokestatic 263	java/lang/Float:valueOf	(Ljava/lang/String;)Ljava/lang/Float;
    //   468: invokevirtual 267	java/lang/Float:floatValue	()F
    //   471: fmul
    //   472: invokestatic 273	java/lang/Math:round	(F)I
    //   475: i2f
    //   476: ldc_w 256
    //   479: fdiv
    //   480: putfield 276	com/starcor/core/domain/VideoInfo:point	F
    //   483: aload_0
    //   484: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   487: aload_1
    //   488: ldc_w 278
    //   491: invokevirtual 46	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   494: invokestatic 90	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   497: invokevirtual 93	java/lang/Integer:intValue	()I
    //   500: putfield 281	com/starcor/core/domain/VideoInfo:tsLimitMin	I
    //   503: aload_0
    //   504: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   507: aload_1
    //   508: ldc_w 283
    //   511: invokevirtual 46	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   514: invokestatic 90	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   517: invokevirtual 93	java/lang/Integer:intValue	()I
    //   520: putfield 286	com/starcor/core/domain/VideoInfo:tsLimitMax	I
    //   523: aload_0
    //   524: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   527: aload_1
    //   528: ldc_w 288
    //   531: invokevirtual 46	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   534: invokestatic 90	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   537: invokevirtual 93	java/lang/Integer:intValue	()I
    //   540: putfield 291	com/starcor/core/domain/VideoInfo:tsDefaultPos	I
    //   543: return
    //   544: astore_3
    //   545: ldc 183
    //   547: new 185	java/lang/StringBuilder
    //   550: dup
    //   551: invokespecial 186	java/lang/StringBuilder:<init>	()V
    //   554: ldc 188
    //   556: invokevirtual 192	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   559: aload_1
    //   560: ldc 194
    //   562: invokevirtual 46	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   565: invokevirtual 192	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   568: invokevirtual 198	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   571: invokestatic 204	com/starcor/core/utils/Logger:i	(Ljava/lang/String;Ljava/lang/String;)V
    //   574: aload_0
    //   575: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   578: iconst_0
    //   579: putfield 207	com/starcor/core/domain/VideoInfo:uiStyle	I
    //   582: goto -310 -> 272
    //   585: astore_2
    //   586: aload_2
    //   587: invokevirtual 109	java/lang/Exception:printStackTrace	()V
    //   590: return
    //   591: astore 4
    //   593: aload_0
    //   594: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   597: iconst_0
    //   598: putfield 237	com/starcor/core/domain/VideoInfo:billing	I
    //   601: goto -226 -> 375
    //   604: astore 5
    //   606: aload_0
    //   607: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   610: iconst_0
    //   611: putfield 241	com/starcor/core/domain/VideoInfo:play_count	I
    //   614: goto -220 -> 394
    //   617: astore 6
    //   619: aload_0
    //   620: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   623: iconst_0
    //   624: putfield 245	com/starcor/core/domain/VideoInfo:view_type	I
    //   627: goto -214 -> 413
    //   630: astore 7
    //   632: aload_0
    //   633: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   636: iconst_0
    //   637: putfield 250	com/starcor/core/domain/VideoInfo:indexCount	I
    //   640: goto -208 -> 432
    //   643: astore 8
    //   645: aload_0
    //   646: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   649: iconst_0
    //   650: putfield 255	com/starcor/core/domain/VideoInfo:indexCurrent	I
    //   653: goto -202 -> 451
    //   656: astore 9
    //   658: aload_0
    //   659: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   662: fconst_0
    //   663: putfield 276	com/starcor/core/domain/VideoInfo:point	F
    //   666: goto -183 -> 483
    //   669: astore 10
    //   671: aload_0
    //   672: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   675: iconst_0
    //   676: putfield 281	com/starcor/core/domain/VideoInfo:tsLimitMin	I
    //   679: goto -176 -> 503
    //   682: astore 11
    //   684: aload_0
    //   685: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   688: iconst_0
    //   689: putfield 286	com/starcor/core/domain/VideoInfo:tsLimitMax	I
    //   692: goto -169 -> 523
    //   695: astore 12
    //   697: aload_0
    //   698: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   701: iconst_0
    //   702: putfield 291	com/starcor/core/domain/VideoInfo:tsDefaultPos	I
    //   705: return
    //
    // Exception table:
    //   from	to	target	type
    //   224	272	544	java/lang/NumberFormatException
    //   0	224	585	java/lang/Exception
    //   224	272	585	java/lang/Exception
    //   272	356	585	java/lang/Exception
    //   394	413	585	java/lang/Exception
    //   545	582	585	java/lang/Exception
    //   593	601	585	java/lang/Exception
    //   606	614	585	java/lang/Exception
    //   619	627	585	java/lang/Exception
    //   632	640	585	java/lang/Exception
    //   645	653	585	java/lang/Exception
    //   658	666	585	java/lang/Exception
    //   671	679	585	java/lang/Exception
    //   684	692	585	java/lang/Exception
    //   697	705	585	java/lang/Exception
    //   356	375	591	java/lang/Exception
    //   375	394	604	java/lang/Exception
    //   394	413	617	java/lang/NumberFormatException
    //   413	432	630	java/lang/Exception
    //   432	451	643	java/lang/Exception
    //   451	483	656	java/lang/Exception
    //   483	503	669	java/lang/Exception
    //   503	523	682	java/lang/Exception
    //   523	543	695	java/lang/Exception
  }

  private void loadDirectorList(JSONObject paramJSONObject)
  {
    try
    {
      this.videoInfo.directorList = new ArrayList();
      JSONArray localJSONArray = paramJSONObject.getJSONArray("director_list");
      int i = 0;
      while (true)
        if (i < localJSONArray.length())
        {
          VideoRoleInfo localVideoRoleInfo = new VideoRoleInfo();
          JSONObject localJSONObject = localJSONArray.getJSONObject(i);
          if (localJSONObject.has("name"))
            localVideoRoleInfo.name = localJSONObject.getString("name");
          try
          {
            localVideoRoleInfo.hasLabel = Integer.valueOf(localJSONObject.getString("label")).intValue();
            if (localJSONObject.has("id"))
              localVideoRoleInfo.labelID = localJSONObject.getString("id");
            this.videoInfo.directorList.add(localVideoRoleInfo);
            i++;
          }
          catch (NumberFormatException localNumberFormatException)
          {
            while (true)
              localVideoRoleInfo.hasLabel = 0;
          }
        }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  // ERROR //
  private void loadIndexList(JSONObject paramJSONObject)
  {
    // Byte code:
    //   0: aload_1
    //   1: ldc_w 300
    //   4: invokevirtual 303	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   7: astore_3
    //   8: ldc_w 305
    //   11: aload_3
    //   12: ldc_w 307
    //   15: invokevirtual 46	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   18: invokevirtual 312	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   21: ifeq +348 -> 369
    //   24: aload_0
    //   25: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   28: iconst_0
    //   29: putfield 315	com/starcor/core/domain/VideoInfo:index_num	I
    //   32: aload_0
    //   33: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   36: new 54	java/util/ArrayList
    //   39: dup
    //   40: invokespecial 55	java/util/ArrayList:<init>	()V
    //   43: putfield 318	com/starcor/core/domain/VideoInfo:indexList	Ljava/util/ArrayList;
    //   46: aload_1
    //   47: ldc_w 300
    //   50: invokevirtual 303	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   53: ldc_w 320
    //   56: invokevirtual 65	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   59: astore 5
    //   61: iconst_0
    //   62: istore 6
    //   64: iload 6
    //   66: aload 5
    //   68: invokevirtual 71	org/json/JSONArray:length	()I
    //   71: if_icmpge +336 -> 407
    //   74: new 322	com/starcor/core/domain/VideoIndex
    //   77: dup
    //   78: invokespecial 323	com/starcor/core/domain/VideoIndex:<init>	()V
    //   81: astore 7
    //   83: aload 5
    //   85: iload 6
    //   87: invokevirtual 78	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   90: astore 8
    //   92: aload 7
    //   94: aload 8
    //   96: ldc_w 320
    //   99: invokevirtual 46	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   102: invokestatic 90	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   105: invokevirtual 93	java/lang/Integer:intValue	()I
    //   108: putfield 325	com/starcor/core/domain/VideoIndex:index	I
    //   111: aload 7
    //   113: aload 8
    //   115: ldc_w 327
    //   118: invokevirtual 46	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   121: invokestatic 263	java/lang/Float:valueOf	(Ljava/lang/String;)Ljava/lang/Float;
    //   124: invokevirtual 267	java/lang/Float:floatValue	()F
    //   127: f2i
    //   128: putfield 330	com/starcor/core/domain/VideoIndex:userScore	I
    //   131: aload 7
    //   133: aload 8
    //   135: ldc 170
    //   137: invokevirtual 46	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   140: invokestatic 90	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   143: invokevirtual 93	java/lang/Integer:intValue	()I
    //   146: putfield 332	com/starcor/core/domain/VideoIndex:timeLen	I
    //   149: aload 8
    //   151: ldc 99
    //   153: invokevirtual 32	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   156: ifeq +15 -> 171
    //   159: aload 7
    //   161: aload 8
    //   163: ldc 99
    //   165: invokevirtual 46	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   168: putfield 334	com/starcor/core/domain/VideoIndex:id	Ljava/lang/String;
    //   171: aload 8
    //   173: ldc 80
    //   175: invokevirtual 32	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   178: ifeq +15 -> 193
    //   181: aload 7
    //   183: aload 8
    //   185: ldc 80
    //   187: invokevirtual 46	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   190: putfield 335	com/starcor/core/domain/VideoIndex:name	Ljava/lang/String;
    //   193: aload 8
    //   195: ldc 213
    //   197: invokevirtual 32	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   200: ifeq +15 -> 215
    //   203: aload 7
    //   205: aload 8
    //   207: ldc 213
    //   209: invokevirtual 46	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   212: putfield 336	com/starcor/core/domain/VideoIndex:desc	Ljava/lang/String;
    //   215: aload 8
    //   217: ldc_w 338
    //   220: invokevirtual 32	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   223: ifeq +16 -> 239
    //   226: aload 7
    //   228: aload 8
    //   230: ldc_w 338
    //   233: invokevirtual 46	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   236: putfield 341	com/starcor/core/domain/VideoIndex:imgUrl	Ljava/lang/String;
    //   239: aload 8
    //   241: ldc_w 343
    //   244: invokevirtual 32	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   247: ifeq +16 -> 263
    //   250: aload 7
    //   252: aload 8
    //   254: ldc_w 343
    //   257: invokevirtual 46	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   260: putfield 346	com/starcor/core/domain/VideoIndex:onlineTime	Ljava/lang/String;
    //   263: aload 8
    //   265: ldc 118
    //   267: invokevirtual 32	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   270: ifeq +15 -> 285
    //   273: aload 7
    //   275: aload 8
    //   277: ldc 118
    //   279: invokevirtual 46	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   282: putfield 347	com/starcor/core/domain/VideoIndex:import_id	Ljava/lang/String;
    //   285: aload 8
    //   287: ldc 122
    //   289: invokevirtual 32	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   292: ifeq +15 -> 307
    //   295: aload 7
    //   297: aload 8
    //   299: ldc 122
    //   301: invokevirtual 46	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   304: putfield 348	com/starcor/core/domain/VideoIndex:serial_id	Ljava/lang/String;
    //   307: aload 8
    //   309: ldc_w 350
    //   312: invokevirtual 32	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   315: ifeq +16 -> 331
    //   318: aload 7
    //   320: aload 8
    //   322: ldc_w 350
    //   325: invokevirtual 46	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   328: putfield 352	com/starcor/core/domain/VideoIndex:import_name	Ljava/lang/String;
    //   331: aload 8
    //   333: ldc_w 354
    //   336: invokevirtual 32	org/json/JSONObject:has	(Ljava/lang/String;)Z
    //   339: ifeq +11 -> 350
    //   342: aload_0
    //   343: aload 8
    //   345: aload 7
    //   347: invokespecial 358	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:loadMediaList	(Lorg/json/JSONObject;Lcom/starcor/core/domain/VideoIndex;)V
    //   350: aload_0
    //   351: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   354: getfield 318	com/starcor/core/domain/VideoInfo:indexList	Ljava/util/ArrayList;
    //   357: aload 7
    //   359: invokevirtual 106	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   362: pop
    //   363: iinc 6 1
    //   366: goto -302 -> 64
    //   369: aload_0
    //   370: getfield 22	com/starcor/core/parser/json/GetFilmInfoV2SAXParserJson:videoInfo	Lcom/starcor/core/domain/VideoInfo;
    //   373: aload_3
    //   374: ldc_w 307
    //   377: invokevirtual 46	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   380: invokestatic 90	java/lang/Integer:valueOf	(Ljava/lang/String;)Ljava/lang/Integer;
    //   383: invokevirtual 93	java/lang/Integer:intValue	()I
    //   386: putfield 315	com/starcor/core/domain/VideoInfo:index_num	I
    //   389: goto -357 -> 32
    //   392: astore 4
    //   394: aload 4
    //   396: invokevirtual 359	java/lang/NumberFormatException:printStackTrace	()V
    //   399: goto -367 -> 32
    //   402: astore_2
    //   403: aload_2
    //   404: invokevirtual 109	java/lang/Exception:printStackTrace	()V
    //   407: return
    //   408: astore 9
    //   410: aload 7
    //   412: iconst_0
    //   413: putfield 325	com/starcor/core/domain/VideoIndex:index	I
    //   416: goto -305 -> 111
    //   419: astore 10
    //   421: aload 7
    //   423: iconst_0
    //   424: putfield 330	com/starcor/core/domain/VideoIndex:userScore	I
    //   427: goto -296 -> 131
    //   430: astore 11
    //   432: aload 11
    //   434: invokevirtual 359	java/lang/NumberFormatException:printStackTrace	()V
    //   437: aload 7
    //   439: iconst_0
    //   440: putfield 332	com/starcor/core/domain/VideoIndex:timeLen	I
    //   443: goto -294 -> 149
    //
    // Exception table:
    //   from	to	target	type
    //   8	32	392	java/lang/NumberFormatException
    //   369	389	392	java/lang/NumberFormatException
    //   0	8	402	java/lang/Exception
    //   8	32	402	java/lang/Exception
    //   32	61	402	java/lang/Exception
    //   64	92	402	java/lang/Exception
    //   92	111	402	java/lang/Exception
    //   111	131	402	java/lang/Exception
    //   131	149	402	java/lang/Exception
    //   149	171	402	java/lang/Exception
    //   171	193	402	java/lang/Exception
    //   193	215	402	java/lang/Exception
    //   215	239	402	java/lang/Exception
    //   239	263	402	java/lang/Exception
    //   263	285	402	java/lang/Exception
    //   285	307	402	java/lang/Exception
    //   307	331	402	java/lang/Exception
    //   331	350	402	java/lang/Exception
    //   350	363	402	java/lang/Exception
    //   369	389	402	java/lang/Exception
    //   394	399	402	java/lang/Exception
    //   410	416	402	java/lang/Exception
    //   421	427	402	java/lang/Exception
    //   432	443	402	java/lang/Exception
    //   92	111	408	java/lang/NumberFormatException
    //   111	131	419	java/lang/NumberFormatException
    //   131	149	430	java/lang/NumberFormatException
  }

  private void loadMediaList(JSONObject paramJSONObject, VideoIndex paramVideoIndex)
  {
    try
    {
      paramVideoIndex.mediaInfo = new ArrayList();
      JSONArray localJSONArray = new JSONArray(paramJSONObject.getString("media_list"));
      for (int i = 0; i < localJSONArray.length(); i++)
      {
        MediaInfo localMediaInfo = new MediaInfo();
        JSONObject localJSONObject = localJSONArray.getJSONObject(i);
        if (localJSONObject.has("id"))
          localMediaInfo.media_id = localJSONObject.getString("id");
        if (localJSONObject.has("online_time"))
          localMediaInfo.onLineTime = localJSONObject.getString("online_time");
        if (localJSONObject.has("type"))
          localMediaInfo.type = localJSONObject.getString("type");
        if (localJSONObject.has("caps"))
          localMediaInfo.caps = localJSONObject.getString("caps");
        if ((paramVideoIndex != null) && (paramVideoIndex.mediaInfo != null))
          paramVideoIndex.mediaInfo.add(localMediaInfo);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public Result parser(InputStream paramInputStream)
  {
    return null;
  }

  public Result parser(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      return null;
    try
    {
      JSONObject localJSONObject = new JSONObject(new String(paramArrayOfByte));
      loadBasicData(localJSONObject);
      if (localJSONObject.has("actor_list"))
        loadActorList(localJSONObject);
      if (localJSONObject.has("director_list"))
        loadDirectorList(localJSONObject);
      if (localJSONObject.has("index_list"))
        loadIndexList(localJSONObject);
      return this.videoInfo;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.GetFilmInfoV2SAXParserJson
 * JD-Core Version:    0.6.2
 */