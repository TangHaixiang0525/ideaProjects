package com.starcor.hunan.service;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.starcor.core.domain.CollectListItem;
import com.starcor.core.domain.MediaInfo;
import com.starcor.core.domain.PlayBillItem;
import com.starcor.core.domain.ReserveListItem;
import com.starcor.core.domain.VideoIndex;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.epgapi.params.AddCollectV2Params;
import com.starcor.core.epgapi.params.UploadAllRecordsParams;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.logic.UserCPLLogic;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.domain.MergeLocalReserveAndUpload;
import com.starcor.hunan.domain.Reservation;
import com.starcor.hunan.interfaces.ErrorCallBack;
import com.starcor.hunan.interfaces.SuccessCallBack;
import com.starcor.sccms.api.SccmsApiAddCatchVideoRecordTask.ISccmsApiAddCatchVideoRecordTaskListener;
import com.starcor.sccms.api.SccmsApiAddCatchVideoRecordV2Task.ISccmsApiAddCatchVideoRecordV2TaskListener;
import com.starcor.sccms.api.SccmsApiAddCollectRecordTask.ISccmsApiAddCollectRecordTaskListener;
import com.starcor.sccms.api.SccmsApiAddCollectRecordV2Task.ISccmsApiAddCollectRecordV2TaskListener;
import com.starcor.sccms.api.SccmsApiAddPlayRecordV2Task.ISccmsApiAddPlayRecordV2TaskListener;
import com.starcor.sccms.api.SccmsApiBroadcastReserveTask.ISccmsApiBroadCastReserveTaskListener;
import com.starcor.sccms.api.SccmsApiDelCatchVideoRecordTask.ISccmsApiDelCatchVideoRecordTaskListener;
import com.starcor.sccms.api.SccmsApiDelCatchVideoRecordV2Task.ISccmsApiDelCatchVideoRecordV2TaskListener;
import com.starcor.sccms.api.SccmsApiDelCollectRecordTask.ISccmsApiDelColllectRecordTaskListener;
import com.starcor.sccms.api.SccmsApiDelCollectRecordV2Task.ISccmsApiDelColllectRecordV2TaskListener;
import com.starcor.sccms.api.SccmsApiDelPlayRecordV2Task.ISccmsApiDelPlayRecordV2TaskListener;
import com.starcor.sccms.api.SccmsApiGetCatchVideoRecordTask.ISccmsApiGetCatchVideoRecordTaskListener;
import com.starcor.sccms.api.SccmsApiGetCatchVideoRecordV2Task.ISccmsApiGetCatchVideoRecordV2TaskListener;
import com.starcor.sccms.api.SccmsApiGetCollectRecordTask.ISccmsApiGetCollectRecordTaskListener;
import com.starcor.sccms.api.SccmsApiGetCollectRecordV2Task.ISccmsApiGetCollectRecordV2TaskListener;
import com.starcor.sccms.api.SccmsApiGetPlayRecordTask.ISccmsApiGetPlayRecordTaskListener;
import com.starcor.sccms.api.SccmsApiGetPlayRecordV2Task.ISccmsApiGetPlayRecordV2TaskListener;
import com.starcor.sccms.api.SccmsApiLiveShowReserveTask.ISccmsApiLiveShowReserveTaskListener;
import com.starcor.sccms.api.SccmsApiReplayReserveTask.ISccmsApiReplayReserveTaskListener;
import com.starcor.sccms.api.SccmsApiUploadAllCatchVideoRecordTask.ISccmsApiUploadAllCatchVideoRecordTaskListener;
import com.starcor.sccms.api.SccmsApiUploadAllCollectRecordTask.ISccmsApiUploadAllCollectRecordTaskListener;
import com.starcor.sccms.api.SccmsApiUploadAllPlayRecordTask.ISccmsApiUploadAllPlayRecordTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CollectAndPlayListLogic
{
  private static final int MSG_ADD_COLLECT = 2;
  private static final int MSG_ADD_TRACEPLAY = 6;
  private static final int MSG_DEL_COLLECT = 3;
  private static final int MSG_DEL_TRACEPLAY = 7;
  private static final int MSG_GET_COLLECT_LIST = 1;
  private static final int MSG_GET_PLAY_LIST_OK = 4;
  private static final int MSG_GET_TRACEPLAY_LIST = 5;
  private static final String TAG = "CollectAndPlayListLogic";
  private SuccessCallBack<Void> addCollectCallBack;
  private SuccessCallBack<Void> addTracePlayCallBack;
  private SuccessCallBack<ArrayList<CollectListItem>> collectCallBack;
  private String currentCollectId;
  private String currentQulity;
  private String currentVideoId;
  private String currentVideoName;
  private SuccessCallBack<Void> delCollectCallBack;
  private SuccessCallBack<Void> delTracePlayCallBack;
  private SuccessCallBack<ArrayList<CollectListItem>> getPlayListCallBack;
  boolean hasHD = false;
  boolean hasSTD = false;
  private VideoInfo info;
  private ErrorCallBack mErrorCallBack = null;
  private final Handler mHandler = new Handler(Looper.getMainLooper())
  {
    boolean hasHD = false;
    boolean hasSTD = false;
    private String mediaQuality;

    private void porcessGetCollectList(Message paramAnonymousMessage)
    {
      try
      {
        ArrayList localArrayList = (ArrayList)paramAnonymousMessage.obj;
        UserCPLLogic.getInstance().refreshCollectList(localArrayList);
        if (CollectAndPlayListLogic.this.refreshCallBack != null)
          CollectAndPlayListLogic.this.refreshCallBack.getDataSuccess(localArrayList);
        return;
      }
      catch (Exception localException)
      {
      }
    }

    private void processAddCollect(Message paramAnonymousMessage)
    {
      Logger.i("test", "processAddCollect " + paramAnonymousMessage);
      if (CollectAndPlayListLogic.this.mErrorCallBack != null)
      {
        Logger.i("CollectAndPlayListLogic", "processAddCollect str_collect_failed=" + CollectAndPlayListLogic.this.str_collect_failed);
        CollectAndPlayListLogic.this.mErrorCallBack.getDataError(CollectAndPlayListLogic.this.str_collect_failed, paramAnonymousMessage.arg1);
      }
      if ((paramAnonymousMessage.obj != null) && (paramAnonymousMessage.arg1 == 200) && (CollectAndPlayListLogic.this.info != null))
      {
        localCollectListItem = new CollectListItem();
        localCollectListItem.id = ((String)paramAnonymousMessage.obj);
        localCollectListItem.video_id = CollectAndPlayListLogic.this.info.videoId;
        localCollectListItem.video_name = CollectAndPlayListLogic.this.info.name;
        localCollectListItem.video_type = CollectAndPlayListLogic.this.info.videoType;
        UserCPLLogic.getInstance().dirtyCollectList();
        setMediaQuality();
        UserCPLLogic.getInstance().addCollect(localCollectListItem, CollectAndPlayListLogic.this.info, true, this.mediaQuality);
        if (CollectAndPlayListLogic.this.addCollectCallBack != null)
          CollectAndPlayListLogic.this.addCollectCallBack.getDataSuccess(null);
      }
      while (CollectAndPlayListLogic.this.mErrorCallBack == null)
      {
        CollectListItem localCollectListItem;
        return;
      }
      CollectAndPlayListLogic.this.mErrorCallBack.getDataError(CollectAndPlayListLogic.this.str_collect_failed, paramAnonymousMessage.arg1);
    }

    private void processDelCollect(Message paramAnonymousMessage)
    {
      if ((paramAnonymousMessage != null) && (paramAnonymousMessage.arg1 == 200))
      {
        UserCPLLogic.getInstance().dirtyCollectList();
        UserCPLLogic.getInstance().delectCollect(CollectAndPlayListLogic.this.currentCollectId, true);
        if (CollectAndPlayListLogic.this.delCollectCallBack != null)
          CollectAndPlayListLogic.this.delCollectCallBack.getDataSuccess(null);
      }
      while (CollectAndPlayListLogic.this.mErrorCallBack == null)
        return;
      CollectAndPlayListLogic.this.mErrorCallBack.getDataError(CollectAndPlayListLogic.this.str_uncollect_failed, paramAnonymousMessage.arg1);
    }

    private void processGetPllayList(Message paramAnonymousMessage)
    {
      try
      {
        ArrayList localArrayList = (ArrayList)paramAnonymousMessage.obj;
        UserCPLLogic.getInstance().refreshPlayRecordList(localArrayList);
        if (CollectAndPlayListLogic.this.getPlayListCallBack != null)
          CollectAndPlayListLogic.this.getPlayListCallBack.getDataSuccess(localArrayList);
        return;
      }
      catch (Exception localException)
      {
      }
    }

    private void setMediaQuality()
    {
      if ((CollectAndPlayListLogic.this.info.indexList != null) && (CollectAndPlayListLogic.this.info.indexList.size() > 0) && (CollectAndPlayListLogic.this.info.indexList.get(0) != null) && (((VideoIndex)CollectAndPlayListLogic.this.info.indexList.get(0)).mediaInfo != null))
      {
        ArrayList localArrayList = ((VideoIndex)CollectAndPlayListLogic.this.info.indexList.get(0)).mediaInfo;
        this.hasHD = false;
        this.hasSTD = false;
        this.mediaQuality = "";
        Iterator localIterator = localArrayList.iterator();
        while (localIterator.hasNext())
        {
          MediaInfo localMediaInfo = (MediaInfo)localIterator.next();
          if ("HD".equalsIgnoreCase(localMediaInfo.type))
            this.hasHD = true;
          else if ("STD".equalsIgnoreCase(localMediaInfo.type))
            this.hasSTD = true;
          else if (!"LOW".equalsIgnoreCase(localMediaInfo.type));
        }
      }
      if ((this.hasHD) && (this.hasSTD))
      {
        this.mediaQuality = "HD";
        return;
      }
      if ((this.hasHD) && (!this.hasSTD))
      {
        this.mediaQuality = "HD";
        return;
      }
      if ((!this.hasHD) && (this.hasSTD))
      {
        this.mediaQuality = "STD";
        return;
      }
      this.mediaQuality = "LOW";
    }

    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default:
        return;
      case 1:
        porcessGetCollectList(paramAnonymousMessage);
        return;
      case 2:
        processAddCollect(paramAnonymousMessage);
        return;
      case 3:
        processDelCollect(paramAnonymousMessage);
        return;
      case 4:
        processGetPllayList(paramAnonymousMessage);
        return;
      case 5:
        CollectAndPlayListLogic.this.porcessGetTracePlayList(paramAnonymousMessage);
        return;
      case 6:
        CollectAndPlayListLogic.this.processAddTracePlay(paramAnonymousMessage);
        return;
      case 7:
      }
      CollectAndPlayListLogic.this.processDelTracePlay(paramAnonymousMessage);
    }
  };
  private LiveShowReserveCallback mLiveShowCallback = null;
  private ReplayOrderCallback mReplayOrderCallback = null;
  private String mediaQuality;
  private SuccessCallBack<ArrayList<CollectListItem>> refreshCallBack;
  private String str_collect_failed = "  收藏失败";
  private String str_traceplay_failed = "  追剧失败";
  private String str_uncollect_failed = "  取消收藏失败";
  private String str_untraceplay_failed = "  取消追剧失败";
  private SuccessCallBack<ArrayList<CollectListItem>> tracePlayCallBack;
  private SuccessCallBack<Void> uploadCatchCallBack;
  private SuccessCallBack<Void> uploadCollectCallBack;
  private SuccessCallBack<Void> uploadPlayListCallBack;

  private void porcessGetTracePlayList(Message paramMessage)
  {
    try
    {
      ArrayList localArrayList = (ArrayList)paramMessage.obj;
      UserCPLLogic.getInstance().refreshTracePlayList(localArrayList);
      if (this.tracePlayCallBack != null)
        this.tracePlayCallBack.getDataSuccess(localArrayList);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  private void processAddTracePlay(Message paramMessage)
  {
    if ((paramMessage.obj != null) && (paramMessage.arg1 == 200) && (this.info != null))
    {
      localCollectListItem = new CollectListItem();
      localCollectListItem.id = ((String)paramMessage.obj);
      localCollectListItem.video_id = this.info.videoId;
      localCollectListItem.video_name = this.info.name;
      localCollectListItem.video_type = this.info.videoType;
      UserCPLLogic.getInstance().dirtyTracePlayList();
      UserCPLLogic.getInstance().addTracePlay(localCollectListItem, true);
      if (this.addTracePlayCallBack != null)
        this.addTracePlayCallBack.getDataSuccess(null);
    }
    while (this.mErrorCallBack == null)
    {
      CollectListItem localCollectListItem;
      return;
    }
    this.mErrorCallBack.getDataError(this.str_traceplay_failed, paramMessage.arg1);
  }

  private void processDelTracePlay(Message paramMessage)
  {
    if ((paramMessage != null) && (paramMessage.arg1 == 200))
    {
      UserCPLLogic.getInstance().dirtyTracePlayList();
      UserCPLLogic.getInstance().delectTracePlay(this.currentCollectId, true);
      if (this.delTracePlayCallBack != null)
        this.delTracePlayCallBack.getDataSuccess(null);
    }
    while (this.mErrorCallBack == null)
      return;
    this.mErrorCallBack.getDataError(this.str_untraceplay_failed, paramMessage.arg1);
  }

  private void setMediaQuality()
  {
    if ((this.info.indexList != null) && (this.info.indexList.size() > 0) && (this.info.indexList.get(0) != null) && (((VideoIndex)this.info.indexList.get(0)).mediaInfo != null))
    {
      ArrayList localArrayList = ((VideoIndex)this.info.indexList.get(0)).mediaInfo;
      this.hasHD = false;
      this.hasSTD = false;
      this.mediaQuality = "";
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        MediaInfo localMediaInfo = (MediaInfo)localIterator.next();
        if ("HD".equalsIgnoreCase(localMediaInfo.type))
          this.hasHD = true;
        else if ("STD".equalsIgnoreCase(localMediaInfo.type))
          this.hasSTD = true;
        else if (!"LOW".equalsIgnoreCase(localMediaInfo.type));
      }
    }
    if ((this.hasHD) && (this.hasSTD))
    {
      this.mediaQuality = "HD";
      return;
    }
    if ((this.hasHD) && (!this.hasSTD))
    {
      this.mediaQuality = "HD";
      return;
    }
    if ((!this.hasHD) && (this.hasSTD))
    {
      this.mediaQuality = "STD";
      return;
    }
    this.mediaQuality = "LOW";
  }

  public void addBroadCastReserve(String paramString, SccmsApiBroadcastReserveTask.ISccmsApiBroadCastReserveTaskListener paramISccmsApiBroadCastReserveTaskListener)
  {
    if (paramString == null);
    do
    {
      return;
      if (GlobalLogic.getInstance().isUserLogined())
        break;
      MergeLocalReserveAndUpload.getInstance().addLocalBroadCastReserve(paramString);
    }
    while (paramISccmsApiBroadCastReserveTaskListener == null);
    paramISccmsApiBroadCastReserveTaskListener.onSuccess(null, null);
    return;
    ServerApiManager.i().APIAddBroadCastRecord(paramString, paramISccmsApiBroadCastReserveTaskListener);
  }

  public void addCollect(SuccessCallBack<Void> paramSuccessCallBack, VideoInfo paramVideoInfo)
  {
    if (paramVideoInfo == null)
      return;
    this.info = paramVideoInfo;
    this.addCollectCallBack = paramSuccessCallBack;
    AddCollectV2Params localAddCollectV2Params = new AddCollectV2Params(paramVideoInfo.videoId, paramVideoInfo.name, paramVideoInfo.videoType, 0, paramVideoInfo.packageId, paramVideoInfo.categoryId, paramVideoInfo.view_type);
    localAddCollectV2Params.getVideo_name().setValue(paramVideoInfo.name);
    localAddCollectV2Params.getPackage_id().setValue(paramVideoInfo.packageId);
    this.currentVideoId = paramVideoInfo.videoId;
    this.currentVideoName = paramVideoInfo.name;
    this.currentQulity = UserCPLLogic.getInstance().getMediaQuality(paramVideoInfo);
    this.info = paramVideoInfo;
    ServerApiManager.i().APIAddCollectRecordV2(localAddCollectV2Params, new SccmsApiAddCollectRecordV2TaskListener());
  }

  public void addLiveShowReserve(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, SccmsApiLiveShowReserveTask.ISccmsApiLiveShowReserveTaskListener paramISccmsApiLiveShowReserveTaskListener)
  {
    if ((paramString2 == null) && (paramString1 == null));
    do
    {
      return;
      if (GlobalLogic.getInstance().isUserLogined())
        break;
    }
    while (!paramString1.equals("msgsys"));
    Reservation localReservation = new Reservation();
    localReservation.setName(paramString3);
    localReservation.setVideoId(paramString4);
    localReservation.setLiveShowId(paramString4);
    localReservation.setLiveShowUrl(paramString5);
    localReservation.setDay(paramString6);
    localReservation.setBeginTime(paramString7);
    localReservation.setSpecial_id(paramString2);
    ReservationService.getinstance().addReservation(localReservation);
    MergeLocalReserveAndUpload.getInstance().addLocalLiveReserve(paramString4, localReservation);
    return;
    ServerApiManager.i().APIAddLiveShowRecord(paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramISccmsApiLiveShowReserveTaskListener);
  }

  public void addPlayList(SuccessCallBack<ArrayList<CollectListItem>> paramSuccessCallBack, AddCollectV2Params paramAddCollectV2Params, VideoInfo paramVideoInfo)
  {
    this.getPlayListCallBack = paramSuccessCallBack;
    this.currentCollectId = paramAddCollectV2Params.getPackage_id().getValue();
    this.currentVideoName = paramAddCollectV2Params.getName().getValue();
    this.currentVideoId = paramAddCollectV2Params.getVideoId().getValue();
    this.currentQulity = paramAddCollectV2Params.getQuality().getValue();
    this.info = paramVideoInfo;
    ServerApiManager.i().APIAddPlayRecordV2(paramAddCollectV2Params, new SccmsApiAddPlayRecordV2TaskListener());
  }

  public void addReplyPlayReserve(VideoInfo paramVideoInfo, PlayBillItem paramPlayBillItem, SccmsApiReplayReserveTask.ISccmsApiReplayReserveTaskListener paramISccmsApiReplayReserveTaskListener)
  {
    if ((paramVideoInfo == null) || (paramPlayBillItem == null))
      return;
    ServerApiManager.i().APIAddReplyPlayRecord(paramVideoInfo, paramPlayBillItem, paramISccmsApiReplayReserveTaskListener);
  }

  public void addTracePlay(SuccessCallBack<Void> paramSuccessCallBack, VideoInfo paramVideoInfo)
  {
    if (paramVideoInfo == null)
      return;
    this.info = paramVideoInfo;
    this.addTracePlayCallBack = paramSuccessCallBack;
    this.currentCollectId = UserCPLLogic.getInstance().findTracePlayIdbyVideoId(paramVideoInfo.videoId);
    this.currentVideoName = paramVideoInfo.name;
    this.currentVideoId = paramVideoInfo.videoId;
    this.info = paramVideoInfo;
    AddCollectV2Params localAddCollectV2Params = new AddCollectV2Params(paramVideoInfo.videoId, paramVideoInfo.name, paramVideoInfo.videoType, paramVideoInfo.indexCurrent, 0, paramVideoInfo.packageId, paramVideoInfo.categoryId, paramVideoInfo.view_type);
    localAddCollectV2Params.getVideo_name().setValue(paramVideoInfo.name);
    ServerApiManager.i().APIAddCatchVideoRecordV2(localAddCollectV2Params, new SccmsApiAddCatchVideoRecordV2TaskListener());
  }

  public void delAllCollect(SuccessCallBack<ArrayList<CollectListItem>> paramSuccessCallBack)
  {
    this.collectCallBack = paramSuccessCallBack;
    this.currentCollectId = "";
    List localList = UserCPLLogic.getInstance().getCollectList();
    int i = -1 + localList.size();
    if (i >= 0)
    {
      String str = ((CollectListItem)localList.get(i)).id;
      if (TextUtils.isEmpty(this.currentCollectId));
      for (this.currentCollectId = str; ; this.currentCollectId = (this.currentCollectId + "," + str))
      {
        i--;
        break;
      }
    }
    ServerApiManager.i().APIDelCollectRecordV2(this.currentCollectId, new SccmsApiDelAllCollectRecordV2TaskListener());
  }

  public void delAllPlayList(SuccessCallBack<ArrayList<CollectListItem>> paramSuccessCallBack)
  {
    this.getPlayListCallBack = paramSuccessCallBack;
    this.currentCollectId = "";
    List localList = UserCPLLogic.getInstance().getPlayRecordList();
    int i = -1 + localList.size();
    if (i >= 0)
    {
      String str = ((CollectListItem)localList.get(i)).id;
      if (TextUtils.isEmpty(this.currentCollectId));
      for (this.currentCollectId = str; ; this.currentCollectId = (this.currentCollectId + "," + str))
      {
        i--;
        break;
      }
    }
    ServerApiManager.i().APIDelPlayRecordV2(this.currentCollectId, new SccmsApiDelPlayRecordV2TaskListener());
  }

  public void delAllTracePlay(SuccessCallBack<Void> paramSuccessCallBack)
  {
    this.delTracePlayCallBack = paramSuccessCallBack;
    this.currentCollectId = "";
    List localList = UserCPLLogic.getInstance().getTracePlayList();
    int i = -1 + localList.size();
    if (i >= 0)
    {
      String str = ((CollectListItem)localList.get(i)).id;
      if (TextUtils.isEmpty(this.currentCollectId));
      for (this.currentCollectId = str; ; this.currentCollectId = (this.currentCollectId + "," + str))
      {
        i--;
        break;
      }
    }
    ServerApiManager.i().APIDelCatchVideoRecordV2(this.currentCollectId, null, new SccmsApiDelCatchVideoRecordV2TaskListener());
  }

  public void delCollect(SuccessCallBack<Void> paramSuccessCallBack, String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return;
    this.delCollectCallBack = paramSuccessCallBack;
    this.currentCollectId = UserCPLLogic.getInstance().findCollectIdbyVideoId(paramString);
    this.currentVideoId = paramString;
    ServerApiManager.i().APIDelCollectRecordV2(this.currentCollectId, new SccmsApiDelCollectRecordV2TaskListener());
  }

  public void delLiveShowReserve(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    if (paramString1 == null)
      return;
    ServerApiManager.i().APIDelLiveShowRecord(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, new SccmsApiLiveShowReserveTaskListener());
  }

  public void delPlayList(SuccessCallBack<ArrayList<CollectListItem>> paramSuccessCallBack, String paramString)
  {
    this.getPlayListCallBack = paramSuccessCallBack;
    this.currentCollectId = UserCPLLogic.getInstance().findPlayRecordIdbyVideoId(paramString);
    Logger.e("currentCollectId=" + this.currentCollectId + "videoid=" + paramString);
    this.currentVideoId = paramString;
    ServerApiManager.i().APIDelPlayRecordV2(this.currentCollectId, new SccmsApiDelPlayRecordV2TaskListener());
  }

  public void delTracePlay(SuccessCallBack<Void> paramSuccessCallBack, String paramString, VideoInfo paramVideoInfo)
  {
    if (TextUtils.isEmpty(paramString))
      return;
    this.delTracePlayCallBack = paramSuccessCallBack;
    this.currentCollectId = UserCPLLogic.getInstance().findTracePlayIdbyVideoId(paramString);
    this.currentVideoId = paramString;
    ServerApiManager.i().APIDelCatchVideoRecordV2(this.currentCollectId, paramVideoInfo, new SccmsApiDelCatchVideoRecordV2TaskListener());
  }

  public void deltetReplyPlayReserve(VideoInfo paramVideoInfo, PlayBillItem paramPlayBillItem, SccmsApiReplayReserveTask.ISccmsApiReplayReserveTaskListener paramISccmsApiReplayReserveTaskListener)
  {
    if ((paramVideoInfo == null) || (paramPlayBillItem == null))
      return;
    ServerApiManager.i().APIDeleteReplyPlayRecord(paramVideoInfo, paramPlayBillItem, paramISccmsApiReplayReserveTaskListener);
  }

  public boolean findCollect(String paramString)
  {
    return !TextUtils.isEmpty(UserCPLLogic.getInstance().findCollectIdbyVideoId(paramString));
  }

  public boolean findTracePlay(String paramString)
  {
    return !TextUtils.isEmpty(UserCPLLogic.getInstance().findTracePlayIdbyVideoId(paramString));
  }

  public void getCollect(SuccessCallBack<ArrayList<CollectListItem>> paramSuccessCallBack)
  {
    this.refreshCallBack = paramSuccessCallBack;
    if (UserCPLLogic.getInstance().isCollectListReady())
    {
      if (this.refreshCallBack != null)
        this.refreshCallBack.getDataSuccess((ArrayList)UserCPLLogic.getInstance().getCollectList());
      return;
    }
    ServerApiManager.i().APIGetCollectRecordV2(new SccmsApiGetCollectRecordV2TaskListener());
  }

  public void getPlayList(SuccessCallBack<ArrayList<CollectListItem>> paramSuccessCallBack)
  {
    this.getPlayListCallBack = paramSuccessCallBack;
    if (UserCPLLogic.getInstance().isPlayRecordListReady())
    {
      if (this.getPlayListCallBack != null)
        this.getPlayListCallBack.getDataSuccess((ArrayList)UserCPLLogic.getInstance().getPlayRecordList());
      return;
    }
    ServerApiManager.i().APIGetPlayRecordV2(new SccmsApiGetPlayRecordV2TaskListener());
  }

  public void getTracePlay(SuccessCallBack<ArrayList<CollectListItem>> paramSuccessCallBack)
  {
    this.tracePlayCallBack = paramSuccessCallBack;
    if (UserCPLLogic.getInstance().isTracePlayListReady())
    {
      if (this.tracePlayCallBack != null)
        this.tracePlayCallBack.getDataSuccess((ArrayList)UserCPLLogic.getInstance().getTracePlayList());
      return;
    }
    ServerApiManager.i().APIGetCatchVideoRecordV2(new SccmsApiGetCatchVideoRecordTaskV2Listener());
  }

  public boolean isPlayBack(String paramString, int paramInt)
  {
    return UserCPLLogic.getInstance().isPlayRecordExistsIncludeLocal(paramString, paramInt);
  }

  public void refreshCoolect(SuccessCallBack<ArrayList<CollectListItem>> paramSuccessCallBack)
  {
    this.refreshCallBack = paramSuccessCallBack;
    if (UserCPLLogic.getInstance().isCollectListReady())
    {
      if (paramSuccessCallBack != null)
        paramSuccessCallBack.getDataSuccess(null);
      return;
    }
    getCollect(paramSuccessCallBack);
  }

  public void refreshPlayList(SuccessCallBack<ArrayList<CollectListItem>> paramSuccessCallBack)
  {
    this.getPlayListCallBack = paramSuccessCallBack;
    if (UserCPLLogic.getInstance().isCollectListReady())
    {
      if (this.getPlayListCallBack != null)
        this.getPlayListCallBack.getDataSuccess(null);
      return;
    }
    getPlayList(this.getPlayListCallBack);
  }

  public void refreshTracePlay(SuccessCallBack<ArrayList<CollectListItem>> paramSuccessCallBack)
  {
    this.tracePlayCallBack = paramSuccessCallBack;
    if (UserCPLLogic.getInstance().isTracePlayListReady())
    {
      if (paramSuccessCallBack != null)
        paramSuccessCallBack.getDataSuccess(null);
      return;
    }
    ServerApiManager.i().APIGetCatchVideoRecordV2(new SccmsApiGetCatchVideoRecordTaskV2Listener());
  }

  public void setActionStatusResult(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.str_collect_failed = paramString1;
    this.str_uncollect_failed = paramString2;
    this.str_traceplay_failed = paramString3;
    this.str_untraceplay_failed = paramString4;
  }

  public void setErrorCallBack(ErrorCallBack paramErrorCallBack)
  {
    this.mErrorCallBack = paramErrorCallBack;
  }

  public void uploadCatchToCloud(SuccessCallBack<Void> paramSuccessCallBack, List<CollectListItem> paramList)
  {
    if (paramList == null)
      return;
    this.uploadCatchCallBack = paramSuccessCallBack;
    UploadAllRecordsParams localUploadAllRecordsParams = new UploadAllRecordsParams(3, paramList);
    ServerApiManager.i().APIUploadAllCatchVideoRecordsToCloud(localUploadAllRecordsParams, new SccmsApiUploadAllCatchVideoRecordTaskListener());
  }

  public void uploadCollectToCloud(SuccessCallBack<Void> paramSuccessCallBack, List<CollectListItem> paramList)
  {
    if (paramList == null)
      return;
    this.uploadCollectCallBack = paramSuccessCallBack;
    UploadAllRecordsParams localUploadAllRecordsParams = new UploadAllRecordsParams(1, paramList);
    ServerApiManager.i().APIUploadAllCollectRecordsToCloud(localUploadAllRecordsParams, new SccmsApiUploadAllCollectRecordTaskListener());
  }

  public void uploadPlayListToCloud(SuccessCallBack<Void> paramSuccessCallBack, List<CollectListItem> paramList)
  {
    if (paramList == null)
      return;
    this.uploadPlayListCallBack = paramSuccessCallBack;
    UploadAllRecordsParams localUploadAllRecordsParams = new UploadAllRecordsParams(2, paramList);
    ServerApiManager.i().APIUploadAllPlayRecordsToCloud(localUploadAllRecordsParams, new SccmsApiUploadAllPlayRecordTaskListener());
  }

  public static abstract interface LiveShowReserveCallback
  {
    public abstract void onError(String paramString);

    public abstract void onSuccess(String paramString);
  }

  public static abstract interface ReplayOrderCallback
  {
    public abstract void onError(String paramString);

    public abstract void onSuccess(String paramString);
  }

  class SccmsApiAddCatchVideoRecordTaskListener
    implements SccmsApiAddCatchVideoRecordTask.ISccmsApiAddCatchVideoRecordTaskListener
  {
    SccmsApiAddCatchVideoRecordTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiAddCatchVideoRecordTaskListener.onError() error:" + paramServerApiCommonError);
      if (CollectAndPlayListLogic.this.mErrorCallBack != null)
        CollectAndPlayListLogic.this.mErrorCallBack.getDataError(CollectAndPlayListLogic.this.str_traceplay_failed, paramServerApiCommonError.getHttpCode());
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, String paramString)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiAddCatchVideoRecordTaskListener.onSuccess() result:" + paramString);
      CollectListItem localCollectListItem = new CollectListItem();
      localCollectListItem.id = paramString;
      localCollectListItem.video_id = CollectAndPlayListLogic.this.info.videoId;
      localCollectListItem.video_name = CollectAndPlayListLogic.this.info.name;
      localCollectListItem.video_type = CollectAndPlayListLogic.this.info.videoType;
      UserCPLLogic.getInstance().dirtyTracePlayList();
      UserCPLLogic.getInstance().addTracePlay(localCollectListItem, true);
      if (CollectAndPlayListLogic.this.addTracePlayCallBack != null)
        CollectAndPlayListLogic.this.addTracePlayCallBack.getDataSuccess(null);
    }
  }

  class SccmsApiAddCatchVideoRecordV2TaskListener
    implements SccmsApiAddCatchVideoRecordV2Task.ISccmsApiAddCatchVideoRecordV2TaskListener
  {
    SccmsApiAddCatchVideoRecordV2TaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiAddCatchVideoRecordTaskListener.onError()  增加追剧 error:" + paramServerApiCommonError);
      if (CollectAndPlayListLogic.this.mErrorCallBack != null)
        CollectAndPlayListLogic.this.mErrorCallBack.getDataError(CollectAndPlayListLogic.this.str_traceplay_failed, paramServerApiCommonError.getHttpCode());
      if (CollectAndPlayListLogic.this.addTracePlayCallBack != null)
        CollectAndPlayListLogic.this.addTracePlayCallBack.getDataError("", paramServerApiCommonError.getHttpCode());
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<CollectListItem> paramArrayList)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiAddCatchVideoRecordTaskListener.onSuccess()   增加追剧result:" + paramArrayList);
      if (paramArrayList != null)
      {
        UserCPLLogic.getInstance().dirtyTracePlayList();
        UserCPLLogic.getInstance().addTracePlay(paramArrayList, true);
      }
      if (CollectAndPlayListLogic.this.addTracePlayCallBack != null)
        CollectAndPlayListLogic.this.addTracePlayCallBack.getDataSuccess(null);
    }
  }

  class SccmsApiAddCollectRecordTaskListener
    implements SccmsApiAddCollectRecordTask.ISccmsApiAddCollectRecordTaskListener
  {
    SccmsApiAddCollectRecordTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiAddCollectRecordTaskListener.onError() error:" + paramServerApiCommonError);
      if (CollectAndPlayListLogic.this.mErrorCallBack != null)
        CollectAndPlayListLogic.this.mErrorCallBack.getDataError(CollectAndPlayListLogic.this.str_collect_failed, paramServerApiCommonError.getHttpCode());
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, String paramString)
    {
      if (TextUtils.isEmpty(paramString))
      {
        if (CollectAndPlayListLogic.this.mErrorCallBack != null)
          CollectAndPlayListLogic.this.mErrorCallBack.getDataError(CollectAndPlayListLogic.this.str_collect_failed, 200);
        return;
      }
      Logger.i("CollectAndPlayListLogic", "SccmsApiAddCollectRecordTaskListener.onSuccess() result:" + paramString);
      CollectListItem localCollectListItem = new CollectListItem();
      localCollectListItem.id = paramString;
      localCollectListItem.video_id = CollectAndPlayListLogic.this.info.videoId;
      localCollectListItem.video_name = CollectAndPlayListLogic.this.info.name;
      localCollectListItem.video_type = CollectAndPlayListLogic.this.info.videoType;
      UserCPLLogic.getInstance().dirtyCollectList();
      CollectAndPlayListLogic.this.setMediaQuality();
      UserCPLLogic.getInstance().addCollect(localCollectListItem, CollectAndPlayListLogic.this.info, true, CollectAndPlayListLogic.this.mediaQuality);
      if (CollectAndPlayListLogic.this.addCollectCallBack != null)
        CollectAndPlayListLogic.this.addCollectCallBack.getDataSuccess(null);
      Logger.i("debug_report_meg", "Fav mesg=" + localCollectListItem);
    }
  }

  class SccmsApiAddCollectRecordV2TaskListener
    implements SccmsApiAddCollectRecordV2Task.ISccmsApiAddCollectRecordV2TaskListener
  {
    SccmsApiAddCollectRecordV2TaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("CollectAndPlayListLogic", " 增加收藏记录 error:" + paramServerApiCommonError);
      if (CollectAndPlayListLogic.this.mErrorCallBack != null)
        CollectAndPlayListLogic.this.mErrorCallBack.getDataError(CollectAndPlayListLogic.this.str_collect_failed, paramServerApiCommonError.getHttpCode());
      if (CollectAndPlayListLogic.this.addCollectCallBack != null)
        CollectAndPlayListLogic.this.addCollectCallBack.getDataError("", paramServerApiCommonError.getHttpCode());
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<CollectListItem> paramArrayList)
    {
      if (paramArrayList != null)
        Logger.i("CollectAndPlayListLogic", "增加收藏记录 result:" + paramArrayList.toString());
      if (paramArrayList != null)
      {
        UserCPLLogic.getInstance().dirtyCollectList();
        UserCPLLogic.getInstance().addCollect(paramArrayList, CollectAndPlayListLogic.this.info, true, CollectAndPlayListLogic.this.currentQulity);
      }
      if (CollectAndPlayListLogic.this.addCollectCallBack != null)
        CollectAndPlayListLogic.this.addCollectCallBack.getDataSuccess(null);
    }
  }

  class SccmsApiAddPlayRecordV2TaskListener
    implements SccmsApiAddPlayRecordV2Task.ISccmsApiAddPlayRecordV2TaskListener
  {
    SccmsApiAddPlayRecordV2TaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiGetPlayRecordTaskListener.onError()添加所有播放记error:" + paramServerApiCommonError);
      if (CollectAndPlayListLogic.this.getPlayListCallBack != null)
        CollectAndPlayListLogic.this.getPlayListCallBack.getDataError("", paramServerApiCommonError.getHttpCode());
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<CollectListItem> paramArrayList)
    {
      Logger.i("CollectAndPlayListLogic", " 添加播放记录 result:" + paramArrayList);
      if (paramArrayList != null)
      {
        UserCPLLogic.getInstance().dirtyPlayRecordList();
        UserCPLLogic.getInstance().addPlayRecord(paramArrayList, CollectAndPlayListLogic.this.info, true);
      }
      if (CollectAndPlayListLogic.this.getPlayListCallBack != null)
        CollectAndPlayListLogic.this.getPlayListCallBack.getDataSuccess(paramArrayList);
    }
  }

  class SccmsApiDelAllCollectRecordV2TaskListener
    implements SccmsApiDelCollectRecordV2Task.ISccmsApiDelColllectRecordV2TaskListener
  {
    SccmsApiDelAllCollectRecordV2TaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiDelCollectRecordTaskListener.onError() 删除收藏 error:" + paramServerApiCommonError);
      if (CollectAndPlayListLogic.this.mErrorCallBack != null)
        CollectAndPlayListLogic.this.mErrorCallBack.getDataError(CollectAndPlayListLogic.this.str_uncollect_failed, paramServerApiCommonError.getHttpCode());
      if (CollectAndPlayListLogic.this.collectCallBack != null)
        CollectAndPlayListLogic.this.collectCallBack.getDataError("", paramServerApiCommonError.getHttpCode());
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<CollectListItem> paramArrayList)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiDelCollectRecordTaskListener.onSuccess()  删除收藏 result:" + paramArrayList);
      UserCPLLogic.getInstance().deleteAllCollect(false);
      if (CollectAndPlayListLogic.this.collectCallBack != null)
        CollectAndPlayListLogic.this.collectCallBack.getDataSuccess(null);
    }
  }

  class SccmsApiDelCatchVideoRecordTaskListener
    implements SccmsApiDelCatchVideoRecordTask.ISccmsApiDelCatchVideoRecordTaskListener
  {
    SccmsApiDelCatchVideoRecordTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiDelCatchVideoRecordTaskListener.onError() error:" + paramServerApiCommonError);
      if (CollectAndPlayListLogic.this.mErrorCallBack != null)
        CollectAndPlayListLogic.this.mErrorCallBack.getDataError(CollectAndPlayListLogic.this.str_untraceplay_failed, paramServerApiCommonError.getHttpCode());
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, String paramString)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiDelCatchVideoRecordTaskListener.onSuccess() result:" + paramString);
      UserCPLLogic.getInstance().dirtyTracePlayList();
      UserCPLLogic.getInstance().delectTracePlay(CollectAndPlayListLogic.this.currentCollectId, true);
      if (CollectAndPlayListLogic.this.delTracePlayCallBack != null)
        CollectAndPlayListLogic.this.delTracePlayCallBack.getDataSuccess(null);
    }
  }

  class SccmsApiDelCatchVideoRecordV2TaskListener
    implements SccmsApiDelCatchVideoRecordV2Task.ISccmsApiDelCatchVideoRecordV2TaskListener
  {
    SccmsApiDelCatchVideoRecordV2TaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiDelCatchVideoRecordTaskListener.onError()   删除追剧 error:" + paramServerApiCommonError);
      if (CollectAndPlayListLogic.this.mErrorCallBack != null)
        CollectAndPlayListLogic.this.mErrorCallBack.getDataError(CollectAndPlayListLogic.this.str_untraceplay_failed, paramServerApiCommonError.getHttpCode());
      if (CollectAndPlayListLogic.this.delTracePlayCallBack != null)
        CollectAndPlayListLogic.this.delTracePlayCallBack.getDataError("", paramServerApiCommonError.getHttpCode());
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<CollectListItem> paramArrayList)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiDelCatchVideoRecordTaskListener.onSuccess()  删除追剧result:" + paramArrayList);
      UserCPLLogic.getInstance().dirtyTracePlayList();
      UserCPLLogic.getInstance().delectTracePlay(CollectAndPlayListLogic.this.currentCollectId, true);
      if (CollectAndPlayListLogic.this.delTracePlayCallBack != null)
        CollectAndPlayListLogic.this.delTracePlayCallBack.getDataSuccess(null);
    }
  }

  class SccmsApiDelCollectRecordTaskListener
    implements SccmsApiDelCollectRecordTask.ISccmsApiDelColllectRecordTaskListener
  {
    SccmsApiDelCollectRecordTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiDelCollectRecordTaskListener.onError() error:" + paramServerApiCommonError);
      if (CollectAndPlayListLogic.this.mErrorCallBack != null)
        CollectAndPlayListLogic.this.mErrorCallBack.getDataError(CollectAndPlayListLogic.this.str_untraceplay_failed, paramServerApiCommonError.getHttpCode());
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, String paramString)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiDelCollectRecordTaskListener.onSuccess() result:" + paramString);
      UserCPLLogic.getInstance().dirtyCollectList();
      UserCPLLogic.getInstance().delectCollect(CollectAndPlayListLogic.this.currentCollectId, true);
      if (CollectAndPlayListLogic.this.delCollectCallBack != null)
        CollectAndPlayListLogic.this.delCollectCallBack.getDataSuccess(null);
    }
  }

  class SccmsApiDelCollectRecordV2TaskListener
    implements SccmsApiDelCollectRecordV2Task.ISccmsApiDelColllectRecordV2TaskListener
  {
    SccmsApiDelCollectRecordV2TaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiDelCollectRecordTaskListener.onError() 删除收藏 error:" + paramServerApiCommonError);
      if (CollectAndPlayListLogic.this.mErrorCallBack != null)
        CollectAndPlayListLogic.this.mErrorCallBack.getDataError(CollectAndPlayListLogic.this.str_uncollect_failed, paramServerApiCommonError.getHttpCode());
      if (CollectAndPlayListLogic.this.delCollectCallBack != null)
        CollectAndPlayListLogic.this.delCollectCallBack.getDataError("", paramServerApiCommonError.getHttpCode());
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<CollectListItem> paramArrayList)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiDelCollectRecordTaskListener.onSuccess()  删除收藏 result:" + paramArrayList);
      UserCPLLogic.getInstance().delectCollect(CollectAndPlayListLogic.this.currentCollectId, true);
      if (CollectAndPlayListLogic.this.delCollectCallBack != null)
        CollectAndPlayListLogic.this.delCollectCallBack.getDataSuccess(null);
    }
  }

  class SccmsApiDelPlayRecordV2TaskListener
    implements SccmsApiDelPlayRecordV2Task.ISccmsApiDelPlayRecordV2TaskListener
  {
    SccmsApiDelPlayRecordV2TaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiDelPlayRecordV2TaskListener.onError() 删除播放记error:" + paramServerApiCommonError);
      if (CollectAndPlayListLogic.this.getPlayListCallBack != null)
        CollectAndPlayListLogic.this.getPlayListCallBack.getDataError("", paramServerApiCommonError.getHttpCode());
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<CollectListItem> paramArrayList)
    {
      Logger.i("CollectAndPlayListLogic", "删除播放记录 result:" + paramArrayList);
      UserCPLLogic.getInstance().dirtyPlayRecordList();
      UserCPLLogic.getInstance().deletePlayRecord(CollectAndPlayListLogic.this.currentCollectId, true);
      if (CollectAndPlayListLogic.this.getPlayListCallBack != null)
        CollectAndPlayListLogic.this.getPlayListCallBack.getDataSuccess(paramArrayList);
    }
  }

  class SccmsApiGetCatchVideoRecordTaskListener
    implements SccmsApiGetCatchVideoRecordTask.ISccmsApiGetCatchVideoRecordTaskListener
  {
    SccmsApiGetCatchVideoRecordTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiGetCatchVideoRecordTaskListener.onError() error:" + paramServerApiCommonError);
      if (CollectAndPlayListLogic.this.tracePlayCallBack != null)
        CollectAndPlayListLogic.this.tracePlayCallBack.getDataError(null, paramServerApiCommonError.getHttpCode());
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<CollectListItem> paramArrayList)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiGetCatchVideoRecordTaskListener.onSuccess() result:" + paramArrayList);
      UserCPLLogic.getInstance().refreshTracePlayList(paramArrayList);
      if (CollectAndPlayListLogic.this.tracePlayCallBack != null)
        CollectAndPlayListLogic.this.tracePlayCallBack.getDataSuccess(paramArrayList);
    }
  }

  class SccmsApiGetCatchVideoRecordTaskV2Listener
    implements SccmsApiGetCatchVideoRecordV2Task.ISccmsApiGetCatchVideoRecordV2TaskListener
  {
    SccmsApiGetCatchVideoRecordTaskV2Listener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiGetCatchVideoRecordTaskListener.onError()  获取追剧 error:" + paramServerApiCommonError);
      if (CollectAndPlayListLogic.this.tracePlayCallBack != null)
        CollectAndPlayListLogic.this.tracePlayCallBack.getDataError("", paramServerApiCommonError.getHttpCode());
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<CollectListItem> paramArrayList)
    {
      if (paramArrayList != null);
      try
      {
        UserCPLLogic.getInstance().dirtyTracePlayList();
        UserCPLLogic.getInstance().refreshTracePlayList(paramArrayList);
        if (CollectAndPlayListLogic.this.tracePlayCallBack != null)
          CollectAndPlayListLogic.this.tracePlayCallBack.getDataSuccess(paramArrayList);
        return;
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }
  }

  class SccmsApiGetCollectRecordTaskListener
    implements SccmsApiGetCollectRecordTask.ISccmsApiGetCollectRecordTaskListener
  {
    SccmsApiGetCollectRecordTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiGetCollectRecordTaskListener.onError() error:" + paramServerApiCommonError);
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<CollectListItem> paramArrayList)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiGetCollectRecordTaskListener.onSuccess() result:" + paramArrayList);
      UserCPLLogic.getInstance().refreshCollectList(paramArrayList);
      if (CollectAndPlayListLogic.this.refreshCallBack != null)
        CollectAndPlayListLogic.this.refreshCallBack.getDataSuccess(paramArrayList);
    }
  }

  class SccmsApiGetCollectRecordV2TaskListener
    implements SccmsApiGetCollectRecordV2Task.ISccmsApiGetCollectRecordV2TaskListener
  {
    SccmsApiGetCollectRecordV2TaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiGetCollectRecordTaskListener.onError() 获取收藏记录 error:" + paramServerApiCommonError);
      if (CollectAndPlayListLogic.this.refreshCallBack != null)
        CollectAndPlayListLogic.this.refreshCallBack.getDataError(null, paramServerApiCommonError.getHttpCode());
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<CollectListItem> paramArrayList)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiGetCollectRecordTaskListener.onSuccess()  获取收藏记录 result:" + paramArrayList);
      if (paramArrayList != null)
      {
        UserCPLLogic.getInstance().dirtyCollectList();
        UserCPLLogic.getInstance().refreshCollectList(paramArrayList);
      }
      if (CollectAndPlayListLogic.this.refreshCallBack != null)
        CollectAndPlayListLogic.this.refreshCallBack.getDataSuccess(paramArrayList);
    }
  }

  class SccmsApiGetPlayRecordTaskListener
    implements SccmsApiGetPlayRecordTask.ISccmsApiGetPlayRecordTaskListener
  {
    SccmsApiGetPlayRecordTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiGetPlayRecordTaskListener.onError() error:" + paramServerApiCommonError);
      if (CollectAndPlayListLogic.this.getPlayListCallBack != null)
        CollectAndPlayListLogic.this.getPlayListCallBack.getDataError(null, paramServerApiCommonError.getHttpCode());
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<CollectListItem> paramArrayList)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiGetPlayRecordTaskListener.onSuccess() result:" + paramArrayList);
      UserCPLLogic.getInstance().dirtyPlayRecordList();
      UserCPLLogic.getInstance().refreshPlayRecordList(paramArrayList);
      if (CollectAndPlayListLogic.this.getPlayListCallBack != null)
        CollectAndPlayListLogic.this.getPlayListCallBack.getDataSuccess(paramArrayList);
    }
  }

  class SccmsApiGetPlayRecordV2TaskListener
    implements SccmsApiGetPlayRecordV2Task.ISccmsApiGetPlayRecordV2TaskListener
  {
    SccmsApiGetPlayRecordV2TaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiGetPlayRecordTaskListener.onError() 获取所有播放记error:" + paramServerApiCommonError);
      if (CollectAndPlayListLogic.this.getPlayListCallBack != null)
        CollectAndPlayListLogic.this.getPlayListCallBack.getDataError("", paramServerApiCommonError.getHttpCode());
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<CollectListItem> paramArrayList)
    {
      Logger.i("CollectAndPlayListLogic", " 获取所有播放记录 result:" + paramArrayList);
      if (paramArrayList != null)
      {
        UserCPLLogic.getInstance().dirtyPlayRecordList();
        UserCPLLogic.getInstance().refreshPlayRecordList(paramArrayList);
      }
      if (CollectAndPlayListLogic.this.getPlayListCallBack != null)
        CollectAndPlayListLogic.this.getPlayListCallBack.getDataSuccess(paramArrayList);
    }
  }

  class SccmsApiLiveShowReserveTaskListener
    implements SccmsApiLiveShowReserveTask.ISccmsApiLiveShowReserveTaskListener
  {
    SccmsApiLiveShowReserveTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("CollectAndPlayListLogic", "onError SccmsApiLiveShowReserveTaskListener ");
      if ((paramServerApiTaskInfo != null) && (paramServerApiCommonError != null))
        Logger.i("CollectAndPlayListLogic", "error SccmsApiLiveShowReserveTaskListener-> result=" + paramServerApiTaskInfo.toString() + ",error=" + paramServerApiCommonError.toString());
      if ((CollectAndPlayListLogic.this.mLiveShowCallback != null) && (paramServerApiCommonError != null))
        CollectAndPlayListLogic.this.mLiveShowCallback.onError(paramServerApiCommonError.toString());
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ReserveListItem paramReserveListItem)
    {
      Logger.i("CollectAndPlayListLogic", "onSuccess SccmsApiLiveShowReserveTaskListener");
      if (paramReserveListItem != null)
        Logger.i("CollectAndPlayListLogic", "onSuccess SccmsApiLiveShowReserveTaskListener-> result=" + paramReserveListItem.toString());
      if (CollectAndPlayListLogic.this.mLiveShowCallback != null)
        CollectAndPlayListLogic.this.mLiveShowCallback.onSuccess("live show add success!");
    }
  }

  class SccmsApiReplayReserveTaskListener
    implements SccmsApiReplayReserveTask.ISccmsApiReplayReserveTaskListener
  {
    SccmsApiReplayReserveTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("CollectAndPlayListLogic", "onError SccmsApiReplayReserveTaskListener ");
      if ((paramServerApiTaskInfo != null) && (paramServerApiCommonError != null))
        Logger.i("CollectAndPlayListLogic", "onSuccess SccmsApiReplayReserveTaskListener-> result=" + paramServerApiTaskInfo.toString() + ",error=" + paramServerApiCommonError.toString());
      if ((CollectAndPlayListLogic.this.mReplayOrderCallback != null) && (paramServerApiCommonError != null))
        CollectAndPlayListLogic.this.mReplayOrderCallback.onError(paramServerApiCommonError.toString());
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ReserveListItem paramReserveListItem)
    {
      Logger.i("CollectAndPlayListLogic", "onSuccess SccmsApiReplayReserveTaskListener");
      if (paramReserveListItem != null)
      {
        Logger.i("CollectAndPlayListLogic", "onSuccess SccmsApiReplayReserveTaskListener-> result=" + paramReserveListItem.toString());
        if (CollectAndPlayListLogic.this.mReplayOrderCallback != null)
          CollectAndPlayListLogic.this.mReplayOrderCallback.onSuccess("SccmsApiReplayReserveTaskListener success!");
      }
      while (CollectAndPlayListLogic.this.mReplayOrderCallback == null)
        return;
      CollectAndPlayListLogic.this.mReplayOrderCallback.onError("SccmsApiReplayReserveTaskListener invalid result!");
    }
  }

  class SccmsApiUploadAllCatchVideoRecordTaskListener
    implements SccmsApiUploadAllCatchVideoRecordTask.ISccmsApiUploadAllCatchVideoRecordTaskListener
  {
    SccmsApiUploadAllCatchVideoRecordTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiUploadAllCatchVideoRecordTaskListener.onError() error:" + paramServerApiCommonError);
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<CollectListItem> paramArrayList)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiUploadAllCatchVideoRecordTaskListener.onSuccess() result:" + paramArrayList);
      try
      {
        UserCPLLogic.getInstance().dirtyTracePlayList();
        UserCPLLogic.getInstance().refreshTracePlayList(paramArrayList);
        Logger.i("CollectAndPlayListLogic", "SccmsApiUploadAllCatchVideoRecordTaskListener.onSuccess1111() result:" + paramArrayList + ",result.size()=" + paramArrayList.size());
        if (CollectAndPlayListLogic.this.uploadCatchCallBack != null)
          CollectAndPlayListLogic.this.uploadCatchCallBack.getDataSuccess(null);
        return;
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }
  }

  class SccmsApiUploadAllCollectRecordTaskListener
    implements SccmsApiUploadAllCollectRecordTask.ISccmsApiUploadAllCollectRecordTaskListener
  {
    SccmsApiUploadAllCollectRecordTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiUploadAllCollectRecordTaskListener.onError() error:" + paramServerApiCommonError);
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<CollectListItem> paramArrayList)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiUploadAllCollectRecordTaskListener.onSuccess() result:" + paramArrayList);
      UserCPLLogic.getInstance().dirtyCollectList();
      UserCPLLogic.getInstance().refreshCollectList(paramArrayList);
      UserCPLLogic.getInstance().notifyTCLaddCollectRecords(paramArrayList);
      if (CollectAndPlayListLogic.this.uploadCollectCallBack != null)
        CollectAndPlayListLogic.this.uploadCollectCallBack.getDataSuccess(null);
    }
  }

  class SccmsApiUploadAllPlayRecordTaskListener
    implements SccmsApiUploadAllPlayRecordTask.ISccmsApiUploadAllPlayRecordTaskListener
  {
    SccmsApiUploadAllPlayRecordTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiUploadAllPlayRecordTaskListener.onError() error:" + paramServerApiCommonError);
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<CollectListItem> paramArrayList)
    {
      Logger.i("CollectAndPlayListLogic", "SccmsApiUploadAllPlayRecordTaskListener.onSuccess() result:" + paramArrayList);
      UserCPLLogic.getInstance().dirtyPlayRecordList();
      UserCPLLogic.getInstance().refreshPlayRecordList(paramArrayList);
      UserCPLLogic.getInstance().addPlayRecordList2Local(paramArrayList);
      UserCPLLogic.getInstance().notifyTCLAddPlayRecords(paramArrayList);
      if (CollectAndPlayListLogic.this.uploadPlayListCallBack != null)
        CollectAndPlayListLogic.this.uploadPlayListCallBack.getDataSuccess(null);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.service.CollectAndPlayListLogic
 * JD-Core Version:    0.6.2
 */