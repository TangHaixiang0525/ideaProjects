package com.starcor.hunan.ads;

import android.text.TextUtils;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AdInfoParser
{
  public static MgAdInfo parseAdInfo(String paramString)
  {
    MgAdInfo localMgAdInfo;
    if (TextUtils.isEmpty(paramString))
    {
      localMgAdInfo = null;
      return localMgAdInfo;
    }
    while (true)
    {
      int j;
      try
      {
        localMgAdInfo = new MgAdInfo();
        JSONObject localJSONObject1 = new JSONObject(paramString);
        if (localJSONObject1.has("init"))
        {
          ArrayList localArrayList1 = new ArrayList();
          JSONArray localJSONArray1 = localJSONObject1.getJSONArray("init");
          int i = localJSONArray1.length();
          j = 0;
          if (j < i)
          {
            JSONObject localJSONObject2 = localJSONArray1.getJSONObject(j);
            if (localJSONObject2.has("trigger"))
            {
              JSONObject localJSONObject3 = localJSONObject2.getJSONObject("trigger");
              Trigger localTrigger = new Trigger();
              if (localJSONObject3.has("id"))
                localTrigger.setId(localJSONObject3.getString("id"));
              if (localJSONObject3.has("action"))
                localTrigger.setAction(localJSONObject3.getString("action"));
              if (localJSONObject3.has("content"))
                localTrigger.setContent(localJSONObject3.getString("content"));
              if (localJSONObject3.has("ads"))
              {
                JSONArray localJSONArray2 = localJSONObject3.getJSONArray("ads");
                int k = localJSONArray2.length();
                ArrayList localArrayList2 = new ArrayList();
                int m = 0;
                if (m < k)
                {
                  localArrayList2.add((Integer)localJSONArray2.get(m));
                  m++;
                  continue;
                }
                localTrigger.setAds(localArrayList2);
              }
              localArrayList1.add(localTrigger);
            }
          }
          else
          {
            localMgAdInfo.setInit(localArrayList1);
          }
        }
        else
        {
          if (localJSONObject1.has("ad"))
          {
            JSONArray localJSONArray3 = localJSONObject1.getJSONArray("ad");
            ArrayList localArrayList3 = new ArrayList();
            int n = localJSONArray3.length();
            int i1 = 0;
            if (i1 < n)
            {
              JSONObject localJSONObject4 = localJSONArray3.getJSONObject(i1);
              AdEntity localAdEntity = new AdEntity();
              if (localJSONObject4.has("aid"))
                localAdEntity.setAid(localJSONObject4.getString("aid"));
              if (localJSONObject4.has("creative"))
              {
                ArrayList localArrayList4 = new ArrayList();
                JSONArray localJSONArray4 = localJSONObject4.getJSONArray("creative");
                int i2 = localJSONArray4.length();
                int i3 = 0;
                if (i3 < i2)
                {
                  Creative localCreative = new Creative();
                  JSONObject localJSONObject5 = localJSONArray4.getJSONObject(i3);
                  if (localJSONObject5.has("cid"))
                    localCreative.setCid(localJSONObject5.getString("cid"));
                  if (localJSONObject5.has("adformat"))
                    localCreative.setAdformat(localJSONObject5.getString("adformat"));
                  if (localJSONObject5.has("duration"))
                    localCreative.setDuration(localJSONObject5.getInt("duration"));
                  if (localJSONObject5.has("mediafiles"))
                  {
                    ArrayList localArrayList5 = new ArrayList();
                    JSONArray localJSONArray5 = localJSONObject5.getJSONArray("mediafiles");
                    int i4 = localJSONArray5.length();
                    int i5 = 0;
                    if (i5 < i4)
                    {
                      JSONObject localJSONObject6 = localJSONArray5.getJSONObject(i5);
                      MediaFile localMediaFile = new MediaFile();
                      if (localJSONObject6.has("type"))
                        localMediaFile.setType(localJSONObject6.getString("type"));
                      if (localJSONObject6.has("w"))
                        localMediaFile.setW(localJSONObject6.getInt("w"));
                      if (localJSONObject6.has("h"))
                        localMediaFile.setH(localJSONObject6.getInt("h"));
                      if (localJSONObject6.has("url"))
                        localMediaFile.setUrl(localJSONObject6.getString("url"));
                      if (localJSONObject6.has("event"))
                        localMediaFile.setEvent(localJSONObject6.getInt("event"));
                      if (localJSONObject6.has("value"))
                        localMediaFile.setValue(localJSONObject6.getString("value"));
                      localArrayList5.add(localMediaFile);
                      i5++;
                      continue;
                    }
                    localCreative.setMediaFiles(localArrayList5);
                  }
                  if (localJSONObject5.has("impression"))
                  {
                    JSONArray localJSONArray7 = localJSONObject5.getJSONArray("impression");
                    ArrayList localArrayList7 = new ArrayList();
                    int i8 = localJSONArray7.length();
                    int i9 = 0;
                    if (i9 < i8)
                    {
                      localArrayList7.add((String)localJSONArray7.get(i9));
                      i9++;
                      continue;
                    }
                    localCreative.setImpression(localArrayList7);
                  }
                  if (localJSONObject5.has("click"))
                  {
                    JSONArray localJSONArray6 = localJSONObject5.getJSONArray("click");
                    ArrayList localArrayList6 = new ArrayList();
                    int i6 = localJSONArray6.length();
                    int i7 = 0;
                    if (i7 < i6)
                    {
                      localArrayList6.add((String)localJSONArray6.get(i7));
                      i7++;
                      continue;
                    }
                    localCreative.setClick(localArrayList6);
                  }
                  localArrayList4.add(localCreative);
                  i3++;
                  continue;
                }
                localAdEntity.setCreative(localArrayList4);
              }
              localArrayList3.add(localAdEntity);
              i1++;
              continue;
            }
            localMgAdInfo.setAd(localArrayList3);
          }
          if (!localJSONObject1.has("extdata"))
            break;
          localMgAdInfo.setExtData(localJSONObject1.getString("extdata"));
          return localMgAdInfo;
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        return null;
      }
      j++;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.ads.AdInfoParser
 * JD-Core Version:    0.6.2
 */