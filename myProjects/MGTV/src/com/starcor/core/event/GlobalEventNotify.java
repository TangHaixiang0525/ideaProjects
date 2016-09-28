package com.starcor.core.event;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.CollectListItem;
import com.starcor.core.domain.VideoIndex;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.logic.UserCPLLogic;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.HotPolymerizationActivity;
import com.starcor.hunan.service.ReservationService;
import com.starcor.hunan.tcl.TCLSender;
import com.starcor.hunan.uilogic.CommonSender;
import com.starcor.hunan.uilogic.HMDSender;
import java.util.ArrayList;
import java.util.List;

public class GlobalEventNotify
{
  private static final String ACTION_USER_LOGIN_STATE_ACTION = "action_user_login_state_action";
  public static final String TAG = "GlobalEventNotify";
  private static GlobalEventNotify globalEventNotify = null;

  public static GlobalEventNotify getInstance()
  {
    if (globalEventNotify == null)
    {
      Logger.i("GlobalEventNotify", "GlobalEventNotify first create");
      globalEventNotify = new GlobalEventNotify();
    }
    return globalEventNotify;
  }

  private VideoIndex getVideoIndexByVideoIndex(List<VideoIndex> paramList, int paramInt)
  {
    if ((paramList == null) || (paramList.size() == 0))
      return null;
    for (int i = 0; i < paramList.size(); i++)
      if (((VideoIndex)paramList.get(i)).index == paramInt)
        return (VideoIndex)paramList.get(i);
    return null;
  }

  public void onAddCollect(CollectListItem paramCollectListItem, VideoInfo paramVideoInfo, String paramString)
  {
    Logger.i("GlobalEventNotify", "onAddCollect");
    if ((paramCollectListItem == null) || (paramVideoInfo == null));
    do
    {
      do
        return;
      while (paramVideoInfo.videoType != 0);
      int i = 0;
      for (int j = 0; (j < paramVideoInfo.indexList.size()) && (j < 4); j++)
        i += ((VideoIndex)paramVideoInfo.indexList.get(j)).timeLen;
      int k = i * 60000;
      TCLSender.sendAddCollectBroadcast(App.getInstance().getApplicationContext(), paramVideoInfo.videoId, paramVideoInfo.name, paramVideoInfo.getImgUrl(), paramCollectListItem.video_index + "", "", paramVideoInfo.indexCount, paramCollectListItem.played_time, k, paramString, "cmdinfo");
      CommonSender.sendAddCollectBroadcast(App.getInstance().getApplicationContext(), paramVideoInfo.videoId, paramVideoInfo.name, paramVideoInfo.getImgUrl(), paramCollectListItem.video_index + "", "", paramVideoInfo.indexCount, paramCollectListItem.played_time, k, paramString, "");
    }
    while (!DeviceInfo.isHMD());
    HMDSender.sendAddCollectBroadcast(App.getInstance().getApplicationContext(), paramVideoInfo);
  }

  public void onAddCollectForTCT(CollectListItem paramCollectListItem)
  {
    if (paramCollectListItem == null)
      return;
    TCLSender.sendAddCollectBroadcast(App.getInstance().getApplicationContext(), paramCollectListItem.video_id, paramCollectListItem.video_name, paramCollectListItem.img_v, paramCollectListItem.video_index + "", "", paramCollectListItem.video_all_index, paramCollectListItem.played_time, 0, paramCollectListItem.quality, "cmdinfo");
  }

  public void onAddPlayRecord(CollectListItem paramCollectListItem, VideoInfo paramVideoInfo, String paramString)
  {
    Logger.i("GlobalEventNotify", "onAddPlayRecord");
    if ((paramCollectListItem == null) || (paramVideoInfo == null));
    while (paramVideoInfo.videoType != 0)
      return;
    ArrayList localArrayList = paramVideoInfo.indexList;
    int i = 0;
    VideoIndex localVideoIndex;
    if (localArrayList != null)
    {
      if (!DeviceInfo.isXiaoMi())
        break label474;
      if ("movie".equals(paramVideoInfo.packageId))
        for (int n = 0; ; n++)
        {
          int i1 = paramVideoInfo.indexList.size();
          if (n >= i1)
            break;
          i += ((VideoIndex)paramVideoInfo.indexList.get(n)).timeLen;
        }
      localVideoIndex = getVideoIndexByVideoIndex(paramVideoInfo.indexList, paramCollectListItem.video_index);
      if (localVideoIndex != null)
        break label464;
      Logger.i("GlobalEventNotify", "not found videoindex:" + paramCollectListItem.video_index);
    }
    label464: for (i = 0; ; i = localVideoIndex.timeLen)
    {
      i *= 60000;
      int j = 1000 * paramCollectListItem.played_time;
      TCLSender.sendAddPlayListBroadcast(App.getInstance().getApplicationContext(), paramVideoInfo.videoId, paramVideoInfo.name, paramVideoInfo.getImgUrl(), paramCollectListItem.video_index + "", paramCollectListItem.videoIndexName, paramVideoInfo.indexCount, j, i, paramString, "cmdinfo");
      Logger.i("GlobalEventNotify", "onAddPlayRecord playedTime:" + j + " videoId:" + paramCollectListItem.video_id + ", videoName:" + paramCollectListItem.video_name + ", videoIndex:" + paramCollectListItem.video_index + ", videoIndexName:" + paramCollectListItem.videoIndexName + "  timeLen:" + i);
      if (DeviceInfo.isHMD())
        HMDSender.sendAddPlayListBroadcast(App.getInstance().getApplicationContext(), paramVideoInfo.videoId, paramVideoInfo.videoType, paramCollectListItem.ts_day + paramCollectListItem.ts_begin, paramVideoInfo.name, paramVideoInfo.getImgUrl(), paramCollectListItem.video_index, paramCollectListItem.videoIndexName, paramVideoInfo.indexCount, paramCollectListItem.uiStyle, paramCollectListItem.played_time, i / 1000);
      CommonSender.sendAddPlayListBroadcast(App.getInstance().getApplicationContext(), paramVideoInfo.videoId, paramVideoInfo.videoType, paramCollectListItem.ts_day + paramCollectListItem.ts_begin, paramVideoInfo.name, paramVideoInfo.getImgUrl(), paramCollectListItem.video_index, paramCollectListItem.videoIndexName, paramVideoInfo.indexCount, paramCollectListItem.uiStyle, paramCollectListItem.played_time, i / 1000);
      return;
    }
    label474: for (int k = 0; ; k++)
    {
      int m = paramVideoInfo.indexList.size();
      if ((k >= m) || (k >= 4))
        break;
      i += ((VideoIndex)paramVideoInfo.indexList.get(k)).timeLen;
    }
  }

  public void onAddPlayRecordForTCT(CollectListItem paramCollectListItem)
  {
    if (paramCollectListItem == null)
      return;
    TCLSender.sendAddPlayListBroadcast(App.getInstance().getApplicationContext(), paramCollectListItem.video_id, paramCollectListItem.video_name, paramCollectListItem.img_v, paramCollectListItem.video_index + "", "", paramCollectListItem.video_all_index, paramCollectListItem.played_time, 0, paramCollectListItem.quality, "cmdinfo");
  }

  public void onDelCollect(CollectListItem paramCollectListItem)
  {
    Logger.i("GlobalEventNotify", "onDelCollect");
    if (paramCollectListItem == null);
    do
    {
      return;
      TCLSender.sendDelSingleCollectBroadcast(App.getInstance().getApplicationContext(), paramCollectListItem.video_id, "cmdinfo");
      CommonSender.sendDelSingleCollectBroadcast(App.getInstance().getApplicationContext(), paramCollectListItem.video_id, "1", "", "");
    }
    while (!DeviceInfo.isHMD());
    HMDSender.sendDelSingleCollectBroadcast(App.getInstance().getApplicationContext(), paramCollectListItem.video_id);
  }

  public void onDelPlayRecord(CollectListItem paramCollectListItem)
  {
    Logger.i("GlobalEventNotify", "onDelPlayRecord");
    if (paramCollectListItem == null)
      return;
    CommonSender.sendDelSingleCollectBroadcast(App.getInstance().getApplicationContext(), paramCollectListItem.video_id, "0", "", "");
    TCLSender.sendDelSinglePlayListBroadcast(App.getInstance().getApplicationContext(), paramCollectListItem.video_id, "cmdinfo");
  }

  public void onDeleteAllCollect()
  {
    Logger.i("GlobalEventNotify", "onDeleteAllCollect");
    TCLSender.sendDelAllCollecttionBroadcast(App.getInstance().getApplicationContext());
    if (DeviceInfo.isHMD())
      HMDSender.sendClearCollectBroadcast(App.getInstance().getApplicationContext());
  }

  public void onDeleteAllPlayRecord()
  {
    Logger.i("GlobalEventNotify", "onDeleteAllPlayRecord");
    TCLSender.sendDelAllPlayListBroadcast(App.getInstance().getApplicationContext());
  }

  public void onUserLogin()
  {
    UserCPLLogic.getInstance().userLogin();
    ReservationService.getinstance().userLogin();
    HotPolymerizationActivity.getUserAttrTask();
  }

  public void onUserLogout()
  {
    UserCPLLogic.getInstance().userLogout();
    ReservationService.getinstance().userLogout();
    HotPolymerizationActivity.getUserAttrTask();
  }

  public void sendUserLoginAction(Context paramContext, String paramString)
  {
    Logger.i("GlobalEventNotify", "sendUserLoginAction");
    if (paramContext == null)
    {
      Logger.i("GlobalEventNotify", "sendUserLoginAction context null!");
      return;
    }
    Intent localIntent = new Intent();
    localIntent.setAction("action_user_login_state_action");
    Bundle localBundle = new Bundle();
    localBundle.putString("action", paramString);
    localIntent.putExtras(localBundle);
    paramContext.sendBroadcast(localIntent);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.event.GlobalEventNotify
 * JD-Core Version:    0.6.2
 */