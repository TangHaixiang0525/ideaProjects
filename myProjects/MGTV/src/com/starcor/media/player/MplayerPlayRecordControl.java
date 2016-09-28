package com.starcor.media.player;

import com.starcor.core.domain.CollectListItem;
import com.starcor.core.domain.PlayerIntentParams;
import com.starcor.core.domain.VideoIndex;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.epgapi.params.AddCollectV2Params;
import com.starcor.core.event.GlobalEventNotify;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.logic.UserCPLLogic;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.service.CollectAndPlayListLogic;
import java.util.Iterator;
import java.util.List;

public class MplayerPlayRecordControl
{
  private String TAG = "MplayerPlayRecordControl";

  private String getOnlineTime(PlayerIntentParams paramPlayerIntentParams)
  {
    List localList = paramPlayerIntentParams.nns_mediaIndexList;
    Object localObject = null;
    if (localList != null)
    {
      Iterator localIterator = paramPlayerIntentParams.nns_mediaIndexList.iterator();
      VideoIndex localVideoIndex;
      do
      {
        boolean bool = localIterator.hasNext();
        localObject = null;
        if (!bool)
          break;
        localVideoIndex = (VideoIndex)localIterator.next();
      }
      while (localVideoIndex.index != paramPlayerIntentParams.nns_index);
      localObject = localVideoIndex;
    }
    if (localObject != null)
    {
      Logger.d(this.TAG, "onlineTime: " + GlobalLogic.getInstance().getDateString(localObject.onlineTime));
      return GlobalLogic.getInstance().getDateString(localObject.onlineTime);
    }
    return "0";
  }

  public void addPlayRecord(PlayerIntentParams paramPlayerIntentParams, int paramInt1, int paramInt2)
  {
    CollectListItem localCollectListItem = new CollectListItem();
    localCollectListItem.video_id = paramPlayerIntentParams.nns_videoInfo.videoId;
    localCollectListItem.video_type = paramPlayerIntentParams.nns_videoInfo.videoType;
    localCollectListItem.video_name = paramPlayerIntentParams.nns_videoInfo.name;
    localCollectListItem.video_index = paramPlayerIntentParams.nns_index;
    localCollectListItem.videoIndexName = paramPlayerIntentParams.subfix_title;
    localCollectListItem.duration = paramInt1;
    localCollectListItem.played_time = paramInt2;
    UserCPLLogic.getInstance().addPlayRecordLocal(localCollectListItem);
    UserCPLLogic.getInstance().addPlayRecord(localCollectListItem, paramPlayerIntentParams.nns_videoInfo, false, paramPlayerIntentParams.mediaQuality);
    UserCPLLogic.getInstance().dirtyPlayRecordList();
    AddCollectV2Params localAddCollectV2Params = new AddCollectV2Params(2, paramPlayerIntentParams.nns_videoInfo.videoId, paramPlayerIntentParams.nns_videoInfo.name, paramPlayerIntentParams.nns_videoInfo.videoType, paramPlayerIntentParams.nns_index, localCollectListItem.played_time, paramPlayerIntentParams.nns_day, paramPlayerIntentParams.nns_beginTime, localCollectListItem.duration + "", paramPlayerIntentParams.nns_videoInfo.packageId, paramPlayerIntentParams.nns_videoInfo.categoryId, paramPlayerIntentParams.nns_videoInfo.uiStyle);
    localAddCollectV2Params.getQuality().setValue(paramPlayerIntentParams.mediaQuality);
    localAddCollectV2Params.getVideo_online_time().setValue(getOnlineTime(paramPlayerIntentParams));
    new CollectAndPlayListLogic().addPlayList(null, localAddCollectV2Params, paramPlayerIntentParams.nns_videoInfo);
    Logger.d(this.TAG, "addPlayRecord: " + localCollectListItem.toString());
  }

  public void clearLatestPlayRecord()
  {
    GlobalLogic.getInstance().clearLatestVideoInfo();
    Logger.d(this.TAG, "clearLatestPlayRecord!");
  }

  public void saveLatestPlayRecord(PlayerIntentParams paramPlayerIntentParams, int paramInt1, int paramInt2)
  {
    CollectListItem localCollectListItem = new CollectListItem();
    localCollectListItem.video_id = paramPlayerIntentParams.nns_videoInfo.videoId;
    localCollectListItem.video_name = paramPlayerIntentParams.nns_videoInfo.name;
    localCollectListItem.video_name = paramPlayerIntentParams.nns_videoInfo.name;
    localCollectListItem.video_index = paramPlayerIntentParams.nns_index;
    localCollectListItem.played_time = paramInt2;
    localCollectListItem.media_assets_id = paramPlayerIntentParams.nns_videoInfo.packageId;
    localCollectListItem.category_id = paramPlayerIntentParams.nns_videoInfo.categoryId;
    localCollectListItem.img_v = paramPlayerIntentParams.nns_videoInfo.imgVUrl;
    localCollectListItem.ts_begin = paramPlayerIntentParams.nns_beginTime;
    localCollectListItem.ts_day = paramPlayerIntentParams.nns_day;
    localCollectListItem.current_index_release_time = getOnlineTime(paramPlayerIntentParams);
    localCollectListItem.ts_time_len = (paramInt1 + "");
    GlobalLogic.getInstance().saveLatestVideoInfo(localCollectListItem);
    GlobalEventNotify.getInstance().onAddPlayRecord(localCollectListItem, paramPlayerIntentParams.nns_videoInfo, paramPlayerIntentParams.mediaQuality);
    Logger.d(this.TAG, "saveLatestPlayRecord: " + localCollectListItem.toString());
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.media.player.MplayerPlayRecordControl
 * JD-Core Version:    0.6.2
 */