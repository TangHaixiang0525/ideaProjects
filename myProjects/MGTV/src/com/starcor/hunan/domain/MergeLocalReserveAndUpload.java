package com.starcor.hunan.domain;

import com.starcor.core.domain.PlayBillItem;
import com.starcor.core.domain.ReserveListItem;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.service.CollectAndPlayListLogic;
import com.starcor.hunan.service.ReservationService;
import com.starcor.sccms.api.SccmsApiBroadcastReserveTask.ISccmsApiBroadCastReserveTaskListener;
import com.starcor.sccms.api.SccmsApiLiveShowReserveTask.ISccmsApiLiveShowReserveTaskListener;
import com.starcor.sccms.api.SccmsApiReplayReserveTask.ISccmsApiReplayReserveTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MergeLocalReserveAndUpload
{
  private static String TAG = MergeLocalReserveAndUpload.class.getSimpleName();
  private static MergeLocalReserveAndUpload instance = null;
  private ArrayList<String> broadcastReserve = new ArrayList();
  CollectAndPlayListLogic collect = new CollectAndPlayListLogic();
  private Map<String, Reservation> liveReserve = new ConcurrentHashMap();
  private ReservationService mReservationService = ReservationService.getinstance();
  private Map<PlayBillItem, Reservation> reservationMap = new ConcurrentHashMap();
  private VideoInfo videoinfo = null;

  public static MergeLocalReserveAndUpload getInstance()
  {
    if (instance == null)
      instance = new MergeLocalReserveAndUpload();
    return instance;
  }

  public void addLocalBroadCastReserve(String paramString)
  {
    this.broadcastReserve.add(paramString);
  }

  public void addLocalLiveReserve(String paramString, Reservation paramReservation)
  {
    this.liveReserve.put(paramString, paramReservation);
  }

  public void addLocalReplyReserve(PlayBillItem paramPlayBillItem, Reservation paramReservation)
  {
    this.reservationMap.put(paramPlayBillItem, paramReservation);
  }

  public void mangoLiveShowReserveReport(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    this.collect.addLiveShowReserve("", paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, new SccmsApiLiveShowReserveTask.ISccmsApiLiveShowReserveTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ReserveListItem paramAnonymousReserveListItem)
      {
        Logger.i(MergeLocalReserveAndUpload.TAG, "mangoLiveShowReserveReport->onSuccess() result" + paramAnonymousReserveListItem.toString());
      }
    });
  }

  public void mangoReplayReserveReport(String paramString1, int paramInt, String paramString2, String paramString3, PlayBillItem paramPlayBillItem, String paramString4)
  {
    if (this.videoinfo == null)
      this.videoinfo = new VideoInfo();
    this.videoinfo.packageId = paramString2;
    this.videoinfo.categoryId = paramString3;
    this.videoinfo.videoId = paramString4;
    this.videoinfo.uiStyle = paramInt;
    if ("cancel".equals(paramString1))
      this.collect.deltetReplyPlayReserve(this.videoinfo, paramPlayBillItem, new SccmsApiReplayReserveTask.ISccmsApiReplayReserveTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ReserveListItem paramAnonymousReserveListItem)
        {
          Logger.i(MergeLocalReserveAndUpload.TAG, "cancel mangoReplayReserveReport->onSuccess() result" + paramAnonymousReserveListItem.toString());
        }
      });
    while (!"reserve".equals(paramString1))
      return;
    this.collect.addReplyPlayReserve(this.videoinfo, paramPlayBillItem, new SccmsApiReplayReserveTask.ISccmsApiReplayReserveTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ReserveListItem paramAnonymousReserveListItem)
      {
        Logger.i(MergeLocalReserveAndUpload.TAG, "reserve mangoReplayReserveReport->onSuccess() result" + paramAnonymousReserveListItem.toString());
      }
    });
  }

  public void mergeBroadCastReserves()
  {
    if ((this.broadcastReserve == null) || (this.broadcastReserve.isEmpty()))
      return;
    Logger.i(TAG, "mergeBroadCastReserves");
    for (int i = 0; i < this.broadcastReserve.size(); i++)
      this.collect.addBroadCastReserve((String)this.broadcastReserve.get(i), new SccmsApiBroadcastReserveTask.ISccmsApiBroadCastReserveTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ReserveListItem paramAnonymousReserveListItem)
        {
          Logger.i(MergeLocalReserveAndUpload.TAG, "mergeBroadCastReserves->onSuccess() result" + paramAnonymousReserveListItem.toString());
        }
      });
    this.broadcastReserve.clear();
  }

  public void mergeLiveShowReserves()
  {
    if ((this.liveReserve == null) || (this.liveReserve.isEmpty()));
    while (true)
    {
      return;
      Logger.i(TAG, "mergeLiveShowReserves");
      Iterator localIterator = this.liveReserve.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        Reservation localReservation = (Reservation)this.liveReserve.get(str);
        mangoLiveShowReserveReport(localReservation.getSpecial_id(), localReservation.getName(), localReservation.getLiveShowId(), localReservation.getLiveShowUrl(), localReservation.getDay(), localReservation.getBeginTime());
        this.mReservationService.removeReservation(((Reservation)this.liveReserve.get(str)).get_id());
        this.liveReserve.remove(str);
      }
    }
  }

  public void mergeReplyReserves()
  {
    if ((this.reservationMap == null) || (this.reservationMap.isEmpty()));
    while (true)
    {
      return;
      Logger.i(TAG, "mergeReplyReserves");
      Iterator localIterator = this.reservationMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        PlayBillItem localPlayBillItem = (PlayBillItem)localIterator.next();
        Reservation localReservation = (Reservation)this.reservationMap.get(localPlayBillItem);
        mangoReplayReserveReport("reserve", 0, localReservation.getPackageId(), localReservation.getCategoryId(), localPlayBillItem, localReservation.getVideoId());
        this.mReservationService.removeReservation(((Reservation)this.reservationMap.get(localPlayBillItem)).get_id());
        this.reservationMap.remove(localPlayBillItem);
      }
    }
  }

  public void mergeReservesAndUpload()
  {
    mergeReplyReserves();
    mergeLiveShowReserves();
    mergeBroadCastReserves();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.domain.MergeLocalReserveAndUpload
 * JD-Core Version:    0.6.2
 */