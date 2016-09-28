package com.starcor.core.parser.json;

import android.text.TextUtils;
import com.starcor.core.domain.CategoryPoster;
import com.starcor.core.domain.CategoryPosterList;
import com.starcor.core.domain.MediaAssetsInfo;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.utils.Logger;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

public class CategoryPosterListParserJson<Result>
  implements IXmlParser<Result>
{
  private static final String TAG = "CategoryPosterListParserJson";
  private MediaAssetsInfo mediaAssetsInfo = new MediaAssetsInfo();
  private LinkedHashMap<String, CategoryPosterList> result = new LinkedHashMap();

  public MediaAssetsInfo getMediaAssetInfo()
  {
    return this.mediaAssetsInfo;
  }

  public Result parser(InputStream paramInputStream)
  {
    return null;
  }

  public Result parserDynamic(byte[] paramArrayOfByte)
  {
    long l = System.currentTimeMillis();
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0))
      return null;
    while (true)
    {
      int j;
      try
      {
        String str1 = new String(paramArrayOfByte);
        JSONObject localJSONObject1 = new JSONObject(str1);
        if (localJSONObject1 == null)
          return null;
        JSONObject localJSONObject2 = localJSONObject1.optJSONObject("l");
        if (localJSONObject2 == null)
          return null;
        JSONArray localJSONArray1 = localJSONObject2.optJSONArray("il");
        if (localJSONArray1 == null)
        {
          return null;
          if (i < localJSONArray1.length())
          {
            JSONObject localJSONObject3 = localJSONArray1.optJSONArray(i).optJSONObject(0);
            localCategoryPosterList = new CategoryPosterList();
            localCategoryPosterList.estimateId = localJSONObject3.optString("estimateId", "");
            localCategoryPosterList.artithmeticId = localJSONObject3.optString("artithmeticId", "");
            String str2 = localJSONObject3.optString("type", "");
            if ("guess_like".equals(str2))
            {
              localCategoryPosterList.categoryType = 8;
              localCategoryPosterList.categoryId = "G000000";
              JSONObject localJSONObject4 = localJSONObject3.optJSONObject("arg_list");
              localCategoryPosterList.videoCount = localJSONObject4.optInt("total", 0);
              JSONArray localJSONArray2 = localJSONObject4.optJSONObject("content_list").optJSONArray("il");
              j = 0;
              int k = localJSONArray2.length();
              if (j >= k)
                continue;
              JSONObject localJSONObject5 = localJSONArray2.getJSONObject(j);
              if (localJSONObject5 == null)
                break label691;
              CategoryPoster localCategoryPoster = new CategoryPoster();
              localCategoryPoster.id = localJSONObject5.optString("video_id", "");
              localCategoryPoster.name = localJSONObject5.optString("name", "");
              localCategoryPoster.posterType = localJSONObject5.optString("type", "");
              localCategoryPoster.img_v = localJSONObject5.optString("img_v", "");
              localCategoryPoster.img_h = localJSONObject5.optString("img_h", "");
              localCategoryPoster.img_s = localJSONObject5.optString("img_s", "");
              localCategoryPoster.importId = localJSONObject5.optString("asset_import_id", "");
              localCategoryPoster.seriesId = localJSONObject5.optString("series_id", "");
              localCategoryPoster.definition = localJSONObject5.optString("corner_mark", "");
              localCategoryPoster.cornerMarkImg = localJSONObject5.optString("corner_mark_img", "");
              localCategoryPoster.viewType = localJSONObject5.optInt("view_type", 0);
              localCategoryPoster.newIndex = localJSONObject5.optInt("video_new_index", 0);
              localCategoryPoster.allIndex = localJSONObject5.optInt("all_index", 0);
              localCategoryPoster.timeLength = localJSONObject5.optInt("time_len", 0);
              localCategoryPoster.playedTime = localJSONObject5.optInt("played_time_len", 0);
              localCategoryPosterList.posterList.add(localCategoryPoster);
              break label691;
            }
            if (!"play_record".equals(str2))
              continue;
            localCategoryPosterList.categoryType = 9;
            localCategoryPosterList.categoryId = "P000000";
            continue;
          }
        }
      }
      catch (Exception localException)
      {
        CategoryPosterList localCategoryPosterList;
        Logger.w("CategoryPosterListParserJson", "json exception", localException);
        Logger.i("CategoryPosterListParserJson", this.result.size() + "");
        Iterator localIterator = this.result.entrySet().iterator();
        if (localIterator.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)localIterator.next();
          Logger.d("CategoryPosterListParserJson", "key = " + localEntry.getKey() + " , value = " + localEntry.getValue().toString());
          continue;
          return null;
          this.result.put(localCategoryPosterList.categoryId, localCategoryPosterList);
          i++;
          continue;
        }
        Logger.i("CategoryPosterListParserJson", "parse time = " + (System.currentTimeMillis() - l) + "ms");
        return this.result;
      }
      int i = 0;
      continue;
      label691: j++;
    }
  }

  public Result parserStatic(byte[] paramArrayOfByte)
  {
    long l = System.currentTimeMillis();
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0))
      return null;
    int i;
    CategoryPosterList localCategoryPosterList;
    JSONArray localJSONArray3;
    int k;
    while (true)
    {
      label306: Iterator localIterator;
      try
      {
        String str = new String(paramArrayOfByte);
        JSONObject localJSONObject1 = new JSONObject(str);
        if (localJSONObject1 == null)
          return null;
        JSONObject localJSONObject2 = localJSONObject1.optJSONObject("l");
        if (localJSONObject2 == null)
          return null;
        JSONObject localJSONObject3 = localJSONObject2.optJSONObject("media_asset_info");
        this.mediaAssetsInfo.package_id = localJSONObject3.optString("media_asset_id", "");
        this.mediaAssetsInfo.package_name = localJSONObject3.optString("media_asset_name", "");
        this.mediaAssetsInfo.package_img = localJSONObject3.optString("media_asset_img", "");
        JSONArray localJSONArray1 = localJSONObject2.optJSONArray("il");
        if (localJSONArray1 != null)
          break label1344;
        return null;
        int j = localJSONArray1.length();
        if (i < j)
        {
          JSONObject localJSONObject4 = localJSONArray1.optJSONObject(i);
          localCategoryPosterList = new CategoryPosterList();
          localCategoryPosterList.categoryId = localJSONObject4.optString("id", "");
          localCategoryPosterList.categoryName = localJSONObject4.optString("name", "");
          JSONObject localJSONObject5 = localJSONObject4.optJSONObject("arg_list");
          localCategoryPosterList.categoryType = localJSONObject5.optInt("category_type", -1);
          localCategoryPosterList.videoCount = localJSONObject5.optInt("total_video_rows", 0);
          localCategoryPosterList.specialCount = localJSONObject5.optInt("total_special_rows", 0);
          if (localCategoryPosterList.categoryType == 9)
          {
            localCategoryPosterList.categoryId = "P000000";
            JSONObject localJSONObject6 = localJSONObject5.optJSONObject("special_list");
            JSONObject localJSONObject7 = localJSONObject5.optJSONObject("video_list");
            JSONArray localJSONArray2 = localJSONObject6.optJSONArray("il");
            localJSONArray3 = localJSONObject7.optJSONArray("il");
            k = 0;
            int m = localJSONArray2.length();
            if (k >= m)
              break;
            JSONObject localJSONObject8 = localJSONArray2.getJSONObject(k);
            if (localJSONObject8 == null)
              break label1350;
            CategoryPoster localCategoryPoster1 = new CategoryPoster();
            localCategoryPoster1.id = localJSONObject8.optString("id", "");
            localCategoryPoster1.name = localJSONObject8.optString("name", "");
            localCategoryPoster1.posterType = localJSONObject8.optString("type", "");
            localCategoryPoster1.img_v = localJSONObject8.optString("img_v", "");
            localCategoryPoster1.img_h = localJSONObject8.optString("img_h", "");
            localCategoryPoster1.img_s = localJSONObject8.optString("img_s", "");
            JSONObject localJSONObject9 = localJSONObject8.optJSONObject("arg_list");
            if (localJSONObject9 != null)
            {
              localCategoryPoster1.specialType = localJSONObject9.optInt("type", 0);
              localCategoryPoster1.specialUrl = localJSONObject9.optString("url", "");
              localCategoryPoster1.specialBkg = localJSONObject9.optString("poster", "");
              localCategoryPoster1.specialSummary = localJSONObject9.optString("summary", "");
              localCategoryPoster1.specialExName = localJSONObject9.optString("ex_name", "");
              localCategoryPoster1.specialPlayType = localJSONObject9.optString("play_type", "");
              localCategoryPoster1.specialOnlineMode = localJSONObject9.optString("online_mode", "");
            }
            localCategoryPosterList.posterList.add(localCategoryPoster1);
            break label1350;
          }
          if (localCategoryPosterList.categoryType != 8)
            continue;
          localCategoryPosterList.categoryId = "G000000";
          continue;
        }
      }
      catch (Exception localException)
      {
        Logger.e("CategoryPosterListParserJson", "json exception", localException);
        Logger.i("CategoryPosterListParserJson", this.result.size() + "");
        localIterator = this.result.entrySet().iterator();
        if (!localIterator.hasNext())
          break label1304;
      }
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      Logger.d("CategoryPosterListParserJson", "key = " + localEntry.getKey() + " , value = " + localEntry.getValue().toString());
    }
    for (int n = 0; ; n++)
    {
      int i1 = localJSONArray3.length();
      if (n < i1)
      {
        JSONObject localJSONObject10 = localJSONArray3.getJSONObject(n);
        if (localJSONObject10 != null)
        {
          CategoryPoster localCategoryPoster2 = new CategoryPoster();
          localCategoryPoster2.id = localJSONObject10.optString("id", "");
          localCategoryPoster2.name = localJSONObject10.optString("name", "");
          localCategoryPoster2.posterType = localJSONObject10.optString("type", "");
          localCategoryPoster2.img_v = localJSONObject10.optString("img_v", "");
          localCategoryPoster2.img_h = localJSONObject10.optString("img_h", "");
          localCategoryPoster2.img_s = localJSONObject10.optString("img_s", "");
          JSONObject localJSONObject11 = localJSONObject10.optJSONObject("arg_list");
          if (localJSONObject11 != null)
          {
            localCategoryPoster2.videoType = localJSONObject11.optInt("video_type", 0);
            localCategoryPoster2.onlineTime = localJSONObject11.optString("online_time", "");
            localCategoryPoster2.videoActor = localJSONObject11.optString("video_actor", "");
            localCategoryPoster2.videoDirector = localJSONObject11.optString("video_director", "");
            localCategoryPoster2.timeLength = localJSONObject11.optInt("time_len", 0);
            localCategoryPoster2.score = localJSONObject11.optString("user_score", "");
            localCategoryPoster2.point = localJSONObject11.optString("point", "");
            localCategoryPoster2.newIndex = localJSONObject11.optInt("new_index", 0);
            localCategoryPoster2.allIndex = localJSONObject11.optInt("all_index", 0);
            localCategoryPoster2.definition = localJSONObject11.optString("mark", "");
            localCategoryPoster2.cornerMarkImg = localJSONObject11.optString("corner_mark_img", "");
            localCategoryPoster2.viewType = localJSONObject11.optInt("view_type", 0);
            localCategoryPoster2.abstractInfo = localJSONObject11.optString("abstract", "");
            localCategoryPoster2.uiStyle = localJSONObject11.optString("ui_style", "");
            localCategoryPoster2.indexUiStyle = localJSONObject11.optString("index_show", "");
            localCategoryPoster2.playCount = localJSONObject11.optInt("play_count_v2", 0);
            localCategoryPoster2.collectCount = localJSONObject11.optInt("collect_count", 0);
            localCategoryPoster2.starAliasName = localJSONObject11.optString("alias_name", "");
            localCategoryPoster2.starOldName = localJSONObject11.optString("old_name", "");
            localCategoryPoster2.starEnglishName = localJSONObject11.optString("english_name", "");
            localCategoryPoster2.starArea = localJSONObject11.optString("area", "");
            localCategoryPoster2.starProfession = localJSONObject11.optString("profession", "");
            localCategoryPoster2.starWorks = localJSONObject11.optString("works", "");
            localCategoryPoster2.starLabelId = localJSONObject11.optString("label_id", "");
            localCategoryPoster2.starCountry = localJSONObject11.optString("country", "");
            localCategoryPoster2.starSex = localJSONObject11.optString("nns_sex", "");
          }
          localCategoryPosterList.posterList.add(localCategoryPoster2);
        }
      }
      else
      {
        if ((!TextUtils.isEmpty(localCategoryPosterList.categoryId)) && ((localCategoryPosterList.posterList.size() > 0) || (localCategoryPosterList.categoryType == 9) || (localCategoryPosterList.categoryType == 8)))
          this.result.put(localCategoryPosterList.categoryId, localCategoryPosterList);
        i++;
        break;
        label1304: Logger.i("CategoryPosterListParserJson", "parse time = " + (System.currentTimeMillis() - l) + "ms");
        return this.result;
        label1344: i = 0;
        break;
        label1350: k++;
        break label306;
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.json.CategoryPosterListParserJson
 * JD-Core Version:    0.6.2
 */