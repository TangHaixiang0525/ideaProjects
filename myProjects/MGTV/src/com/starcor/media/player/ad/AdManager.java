package com.starcor.media.player.ad;

import android.text.TextUtils;
import com.starcor.core.domain.AdInfos;
import com.starcor.core.domain.AdvertisementPosInfo;
import com.starcor.core.domain.CategoryItem;
import com.starcor.core.domain.ChannelInfoV2;
import com.starcor.core.domain.ChannelItemInfoV2;
import com.starcor.core.domain.MediaAssetsInfo;
import com.starcor.core.domain.PlayerIntentParams;
import com.starcor.core.domain.VideoAd;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.logic.UserCPLLogic;
import com.starcor.core.logic.UserCPLLogic.TimeshiftPlayRecord;
import com.starcor.sccms.api.SccmsApiGetAdInfoByAdPosTask.ISccmsApiGetAdInfoByAdPosTaskListener;
import com.starcor.sccms.api.SccmsApiGetAdInfoByVideoIdTask.ISccmsApiGetAdInfoByVideoIdTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class AdManager
{
  private OnGetAdInfoCallback onGetAdInfoCallback;

  public AdManager(OnGetAdInfoCallback paramOnGetAdInfoCallback)
  {
    this.onGetAdInfoCallback = paramOnGetAdInfoCallback;
  }

  private void getAdInfos(final AdvertisementPosInfo paramAdvertisementPosInfo, final int paramInt)
  {
    ServerApiManager.i().APIGetAdInfoByAdPos(paramAdvertisementPosInfo.getId(), new SccmsApiGetAdInfoByAdPosTask.ISccmsApiGetAdInfoByAdPosTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        if ((paramAdvertisementPosInfo.getId().equals("start_bf")) && (AdManager.this.onGetAdInfoCallback != null))
          AdManager.this.onGetAdInfoCallback.onNoStartAd();
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, AdInfos paramAnonymousAdInfos)
      {
        if (paramAnonymousAdInfos != null)
        {
          if (!paramAdvertisementPosInfo.getId().equals("start_bf"))
            break label99;
          if ((paramAnonymousAdInfos.getVideoAdList() != null) && (paramAnonymousAdInfos.getVideoAdList().getVideos() != null) && (paramAnonymousAdInfos.getVideoAdList().getVideos().size() != 0))
            break label72;
          if (AdManager.this.onGetAdInfoCallback != null)
            AdManager.this.onGetAdInfoCallback.onNoStartAd();
        }
        label72: label99: 
        while ((!paramAdvertisementPosInfo.getId().equals("zanting_bf")) || (AdManager.this.onGetAdInfoCallback == null))
        {
          return;
          if (AdManager.this.onGetAdInfoCallback != null)
            AdManager.this.onGetAdInfoCallback.onGetStartAd(paramAnonymousAdInfos, paramInt);
        }
        AdManager.this.onGetAdInfoCallback.onGetPauaseAd(paramAnonymousAdInfos);
      }
    });
  }

  private void getAdPositionIdByVideoId(PlayerIntentParams paramPlayerIntentParams)
  {
    ServerApiManager.i().APIGetAdInfoByVideoId(paramPlayerIntentParams.nns_videoInfo.videoId, paramPlayerIntentParams.nns_videoInfo.videoType, paramPlayerIntentParams.nns_videoInfo.packageId, "", paramPlayerIntentParams.nns_videoInfo.categoryId, new SccmsApiGetAdInfoByVideoIdTask.ISccmsApiGetAdInfoByVideoIdTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        if (AdManager.this.onGetAdInfoCallback != null)
          AdManager.this.onGetAdInfoCallback.onNoStartAd();
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ArrayList<AdvertisementPosInfo> paramAnonymousArrayList)
      {
        if ((paramAnonymousArrayList == null) || (paramAnonymousArrayList.size() == 0))
          if (AdManager.this.onGetAdInfoCallback != null)
            AdManager.this.onGetAdInfoCallback.onNoStartAd();
        while (true)
        {
          return;
          ArrayList localArrayList = new ArrayList();
          Iterator localIterator1 = paramAnonymousArrayList.iterator();
          while (localIterator1.hasNext())
          {
            AdvertisementPosInfo localAdvertisementPosInfo2 = (AdvertisementPosInfo)localIterator1.next();
            if (localAdvertisementPosInfo2.getId().equals("start_bf"))
              localArrayList.add(localAdvertisementPosInfo2);
          }
          if ((localArrayList.size() == 0) && (AdManager.this.onGetAdInfoCallback != null))
            AdManager.this.onGetAdInfoCallback.onNoStartAd();
          localArrayList.clear();
          Iterator localIterator2 = paramAnonymousArrayList.iterator();
          while (localIterator2.hasNext())
          {
            AdvertisementPosInfo localAdvertisementPosInfo1 = (AdvertisementPosInfo)localIterator2.next();
            AdManager.this.getAdInfos(localAdvertisementPosInfo1, localAdvertisementPosInfo1.getAliveTime());
          }
        }
      }
    });
  }

  private String getCategoryNameById(String paramString)
  {
    MediaAssetsInfo localMediaAssetsInfo = GlobalLogic.getInstance().getTimeshiftAssetsInfo();
    if ((paramString == null) || (localMediaAssetsInfo == null) || (localMediaAssetsInfo.cList == null));
    while (true)
    {
      return null;
      for (int i = 0; i < localMediaAssetsInfo.cList.size(); i++)
      {
        CategoryItem localCategoryItem = (CategoryItem)localMediaAssetsInfo.cList.get(i);
        if (paramString.equals(localCategoryItem.id))
          return localCategoryItem.name;
      }
    }
  }

  private PlayerIntentParams getVideoIdOfTimeShift(PlayerIntentParams paramPlayerIntentParams)
  {
    ChannelInfoV2 localChannelInfoV21 = GlobalLogic.getInstance().getChannelInfoV2();
    if ((localChannelInfoV21 == null) || (localChannelInfoV21.channelList == null));
    label320: 
    do
    {
      return paramPlayerIntentParams;
      ChannelInfoV2 localChannelInfoV22 = new ChannelInfoV2();
      localChannelInfoV22.channelList = new ArrayList();
      int i = 0;
      final HashMap localHashMap = new HashMap();
      int j = 0;
      if (j < localChannelInfoV21.channelList.size())
      {
        ChannelItemInfoV2 localChannelItemInfoV23 = (ChannelItemInfoV2)localChannelInfoV21.channelList.get(j);
        if (getCategoryNameById(localChannelItemInfoV23.categoryId) == null);
        while (true)
        {
          j++;
          break;
          localChannelInfoV22.channelList.add(localChannelItemInfoV23);
          localHashMap.put(localChannelItemInfoV23.id, Integer.valueOf(i));
          i++;
        }
      }
      UserCPLLogic localUserCPLLogic1 = UserCPLLogic.getInstance();
      Comparator local1 = new Comparator()
      {
        public int compare(UserCPLLogic.TimeshiftPlayRecord paramAnonymousTimeshiftPlayRecord1, UserCPLLogic.TimeshiftPlayRecord paramAnonymousTimeshiftPlayRecord2)
        {
          boolean bool = localHashMap.containsKey(paramAnonymousTimeshiftPlayRecord1.videoId);
          if (bool == localHashMap.containsKey(paramAnonymousTimeshiftPlayRecord2.videoId))
          {
            long l = paramAnonymousTimeshiftPlayRecord2.totalPlayedTime - paramAnonymousTimeshiftPlayRecord1.totalPlayedTime;
            if (Math.abs(l) >= 2147483647L)
              return (int)(l / 2147483647L);
            return (int)l;
          }
          if (bool)
            return -1;
          return 1;
        }
      };
      List localList1 = localUserCPLLogic1.getTopTimeshiftPlayHistoryRecords(10, local1);
      UserCPLLogic localUserCPLLogic2 = UserCPLLogic.getInstance();
      Comparator local2 = new Comparator()
      {
        public int compare(UserCPLLogic.TimeshiftPlayRecord paramAnonymousTimeshiftPlayRecord1, UserCPLLogic.TimeshiftPlayRecord paramAnonymousTimeshiftPlayRecord2)
        {
          boolean bool = localHashMap.containsKey(paramAnonymousTimeshiftPlayRecord1.videoId);
          if (bool == localHashMap.containsKey(paramAnonymousTimeshiftPlayRecord2.videoId))
          {
            long l = paramAnonymousTimeshiftPlayRecord2.lastPlayTime.getTime() - paramAnonymousTimeshiftPlayRecord1.lastPlayTime.getTime();
            if (Math.abs(l) >= 2147483647L)
              return (int)(l / 2147483647L);
            return (int)l;
          }
          if (bool)
            return -1;
          return 1;
        }
      };
      List localList2 = localUserCPLLogic2.getTopTimeshiftPlayHistoryRecords(1, local2);
      String str1 = "";
      String str2 = "";
      if ((localList2 != null) && (localList2.size() > 0))
      {
        str1 = ((UserCPLLogic.TimeshiftPlayRecord)localList2.get(0)).videoId;
        str2 = ((UserCPLLogic.TimeshiftPlayRecord)localList2.get(0)).categoryId;
      }
      int k = -1;
      int m = -1;
      int n = 0;
      if (n < localChannelInfoV22.channelList.size())
      {
        ChannelItemInfoV2 localChannelItemInfoV22 = (ChannelItemInfoV2)localChannelInfoV22.channelList.get(n);
        String str4 = localChannelItemInfoV22.id;
        if (str1.equals(str4))
        {
          if ((str2 == null) || (!localChannelItemInfoV22.categoryId.equals(str2)))
            break label320;
          m = n;
        }
        while (true)
        {
          n++;
          break;
          if (k < 0)
            k = 0;
        }
      }
      if (localList1 != null)
      {
        Iterator localIterator = localList1.iterator();
        while (localIterator.hasNext())
        {
          UserCPLLogic.TimeshiftPlayRecord localTimeshiftPlayRecord = (UserCPLLogic.TimeshiftPlayRecord)localIterator.next();
          if ((localTimeshiftPlayRecord.totalPlayedTime >= 1L) && (localHashMap.containsKey(localTimeshiftPlayRecord.videoId)))
          {
            int i1 = ((Integer)localHashMap.get(localTimeshiftPlayRecord.videoId)).intValue();
            String str3 = localTimeshiftPlayRecord.videoId;
            if (str1.equals(str3))
              if ((str2 != null) && (localTimeshiftPlayRecord.categoryId.equals(str2)))
                m = i1;
              else if (k < 0)
                k = 0;
          }
        }
      }
      if (m >= 0)
        k = m;
      if (k < 0)
        k = 0;
      ChannelItemInfoV2 localChannelItemInfoV21 = (ChannelItemInfoV2)localChannelInfoV22.channelList.get(k);
      try
      {
        paramPlayerIntentParams.nns_index = 0;
        if (paramPlayerIntentParams.nns_videoInfo == null)
        {
          paramPlayerIntentParams.nns_videoInfo = new VideoInfo();
          paramPlayerIntentParams.nns_videoInfo.packageId = "TimeShift";
        }
        paramPlayerIntentParams.played_time = 0L;
        paramPlayerIntentParams.nns_videoInfo.videoId = localChannelItemInfoV21.id;
        paramPlayerIntentParams.nns_videoInfo.categoryId = localChannelItemInfoV21.categoryId;
        paramPlayerIntentParams.nns_videoInfo.name = localChannelItemInfoV21.name;
        paramPlayerIntentParams.nns_videoInfo.film_name = localChannelItemInfoV21.name;
        paramPlayerIntentParams.nns_videoInfo.videoType = 1;
        paramPlayerIntentParams.channel_index = String.valueOf(localChannelItemInfoV21.channelNum);
        paramPlayerIntentParams.mode = 6;
        return paramPlayerIntentParams;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    while (this.onGetAdInfoCallback == null);
    this.onGetAdInfoCallback.onNoStartAd();
    return paramPlayerIntentParams;
  }

  public void checkAdInfos(PlayerIntentParams paramPlayerIntentParams)
  {
    if (paramPlayerIntentParams == null)
    {
      if (this.onGetAdInfoCallback != null)
        this.onGetAdInfoCallback.onNoStartAd();
      return;
    }
    if ((TextUtils.isEmpty(paramPlayerIntentParams.nns_videoInfo.videoId)) && (paramPlayerIntentParams.nns_videoInfo.packageId.equalsIgnoreCase("TimeShift")))
    {
      getAdPositionIdByVideoId(getVideoIdOfTimeShift(paramPlayerIntentParams));
      return;
    }
    getAdPositionIdByVideoId(paramPlayerIntentParams);
  }

  public static abstract interface OnGetAdInfoCallback
  {
    public abstract void onGetEndAd(AdInfos paramAdInfos);

    public abstract void onGetPauaseAd(AdInfos paramAdInfos);

    public abstract void onGetStartAd(AdInfos paramAdInfos, int paramInt);

    public abstract void onNoEndAd();

    public abstract void onNoPauseAd();

    public abstract void onNoStartAd();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.media.player.ad.AdManager
 * JD-Core Version:    0.6.2
 */