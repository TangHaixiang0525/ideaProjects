package com.starcor.hunan.ads;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Iterator;

public class MgAdInfo
{
  private ArrayList<AdEntity> ad;
  private String extData;
  private ArrayList<Trigger> init;

  public String getAIdbyMediaFile(MediaFile paramMediaFile)
  {
    if (this.ad == null)
      return "";
    int i = this.ad.size();
    for (int j = 0; j < i; j++)
    {
      ArrayList localArrayList = ((AdEntity)this.ad.get(j)).getCreatives();
      for (int k = 0; k < localArrayList.size(); k++)
      {
        Iterator localIterator = ((Creative)localArrayList.get(k)).getMediaFiles().iterator();
        while (localIterator.hasNext())
          if (((MediaFile)localIterator.next()).equals(paramMediaFile))
            return ((AdEntity)this.ad.get(j)).getAid();
      }
    }
    return "";
  }

  public ArrayList<AdEntity> getAd()
  {
    return this.ad;
  }

  public AdEntity getAdByAid(String paramString)
  {
    AdEntity localAdEntity;
    if ((TextUtils.isEmpty(paramString)) || (this.ad == null) || (this.ad.size() <= 0))
    {
      localAdEntity = null;
      return localAdEntity;
    }
    int i = this.ad.size();
    for (int j = 0; ; j++)
    {
      if (j >= i)
        break label75;
      localAdEntity = (AdEntity)this.ad.get(j);
      if (paramString.equals(localAdEntity.getAid()))
        break;
    }
    label75: return null;
  }

  public ArrayList<Creative> getAllPauseCreative()
  {
    ArrayList localArrayList1;
    if ((this.ad == null) || (this.ad.size() <= 0))
      localArrayList1 = new ArrayList();
    while (true)
    {
      return localArrayList1;
      int i = this.ad.size();
      localArrayList1 = new ArrayList();
      for (int j = 0; j < i; j++)
      {
        ArrayList localArrayList2 = ((AdEntity)this.ad.get(j)).getCreatives();
        int k = localArrayList2.size();
        for (int m = 0; m < k; m++)
        {
          Creative localCreative = (Creative)localArrayList2.get(m);
          if ("pau".equalsIgnoreCase(localCreative.getAdformat()))
            localArrayList1.add(localCreative);
        }
      }
    }
  }

  public ArrayList<Creative> getAllPreRollCreative()
  {
    ArrayList localArrayList1;
    if ((this.ad == null) || (this.ad.size() <= 0))
      localArrayList1 = new ArrayList();
    while (true)
    {
      return localArrayList1;
      int i = this.ad.size();
      localArrayList1 = new ArrayList();
      for (int j = 0; j < i; j++)
      {
        ArrayList localArrayList2 = ((AdEntity)this.ad.get(j)).getCreatives();
        int k = localArrayList2.size();
        for (int m = 0; m < k; m++)
        {
          Creative localCreative = (Creative)localArrayList2.get(m);
          if ("vr".equalsIgnoreCase(localCreative.getAdformat()))
            localArrayList1.add(localCreative);
        }
      }
    }
  }

  public String getCreativeIdbyMediaFile(MediaFile paramMediaFile)
  {
    if (this.ad == null)
      return "";
    int i = this.ad.size();
    for (int j = 0; j < i; j++)
    {
      ArrayList localArrayList = ((AdEntity)this.ad.get(j)).getCreatives();
      for (int k = 0; k < localArrayList.size(); k++)
      {
        Iterator localIterator = ((Creative)localArrayList.get(k)).getMediaFiles().iterator();
        while (localIterator.hasNext())
        {
          MediaFile localMediaFile = (MediaFile)localIterator.next();
          if ((localMediaFile != null) && (paramMediaFile != null) && (localMediaFile.equals(paramMediaFile)))
            return ((Creative)localArrayList.get(k)).getCid();
        }
      }
    }
    return "";
  }

  public String getExtData()
  {
    return this.extData;
  }

  public ArrayList<Integer> getIdsbyAction(Action paramAction)
  {
    ArrayList localArrayList;
    if ((this.init == null) || (TextUtils.isEmpty(paramAction.getAction())))
      localArrayList = new ArrayList();
    while (true)
    {
      return localArrayList;
      int i = this.init.size();
      localArrayList = new ArrayList();
      for (int j = 0; j < i; j++)
      {
        Trigger localTrigger = (Trigger)this.init.get(j);
        if (paramAction.getAction().equalsIgnoreCase(localTrigger.getAction()))
          localArrayList.addAll(localTrigger.getAds());
      }
    }
  }

  public ArrayList<String> getImpressionbyMediaFile(MediaFile paramMediaFile)
  {
    if (this.ad == null)
      return new ArrayList();
    int i = this.ad.size();
    for (int j = 0; j < i; j++)
    {
      ArrayList localArrayList = ((AdEntity)this.ad.get(j)).getCreatives();
      for (int k = 0; k < localArrayList.size(); k++)
      {
        Iterator localIterator = ((Creative)localArrayList.get(k)).getMediaFiles().iterator();
        while (localIterator.hasNext())
          if (((MediaFile)localIterator.next()).equals(paramMediaFile))
            return ((Creative)localArrayList.get(k)).getImpression();
      }
    }
    return new ArrayList();
  }

  public ArrayList<Trigger> getInit()
  {
    return this.init;
  }

  public void setAd(ArrayList<AdEntity> paramArrayList)
  {
    this.ad = paramArrayList;
  }

  public void setExtData(String paramString)
  {
    this.extData = paramString;
  }

  public void setInit(ArrayList<Trigger> paramArrayList)
  {
    this.init = paramArrayList;
  }

  public String toString()
  {
    String str1 = "AdInfo:[" + "init:" + this.init;
    String str2 = str1 + ", ad:" + this.ad;
    String str3 = str2 + ", extData:" + this.extData;
    return str3 + "]";
  }

  public static enum Action
  {
    private String action;

    static
    {
      MIDROLL = new Action("MIDROLL", 1, "midroll");
      POSTROLL = new Action("POSTROLL", 2, "postroll");
      FLOAT = new Action("FLOAT", 3, "float");
      BUFFER = new Action("BUFFER", 4, "buffer");
      PAUSE = new Action("PAUSE", 5, "pause");
      Action[] arrayOfAction = new Action[6];
      arrayOfAction[0] = PREROLL;
      arrayOfAction[1] = MIDROLL;
      arrayOfAction[2] = POSTROLL;
      arrayOfAction[3] = FLOAT;
      arrayOfAction[4] = BUFFER;
      arrayOfAction[5] = PAUSE;
    }

    private Action(String paramString)
    {
      this.action = paramString;
    }

    public String getAction()
    {
      return this.action;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.ads.MgAdInfo
 * JD-Core Version:    0.6.2
 */