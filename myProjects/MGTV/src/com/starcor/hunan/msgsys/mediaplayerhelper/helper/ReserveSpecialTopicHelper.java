package com.starcor.hunan.msgsys.mediaplayerhelper.helper;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.starcor.core.domain.ReserveListItem;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.db.DBProvider;
import com.starcor.hunan.domain.Reservation;
import com.starcor.hunan.msgsys.dbupdater.MQTTMessageDBUpdater;
import com.starcor.hunan.msgsys.interfaces.AbstractMQTTUIUpdateNotifier;
import com.starcor.hunan.msgsys.interfaces.IMQTTMessageDBUpdater;
import com.starcor.hunan.msgsys.interfaces.IMQTTMessageDBUpdater.TopicTableUpdateActionType;
import com.starcor.hunan.service.CollectAndPlayListLogic;
import com.starcor.hunan.service.ReservationService;
import com.starcor.sccms.api.SccmsApiLiveShowReserveTask.ISccmsApiLiveShowReserveTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;

public class ReserveSpecialTopicHelper extends MediaAssetHelperBase
{
  private CollectAndPlayListLogic mCollectLogic = new CollectAndPlayListLogic();

  public ReserveSpecialTopicHelper()
  {
    super(ReserveSpecialTopicHelper.class.getSimpleName());
  }

  public ReserveSpecialTopicHelper(Context paramContext)
  {
    super(paramContext, ReserveSpecialTopicHelper.class.getSimpleName());
  }

  protected void startSubAction()
  {
    String str1 = "";
    String str2 = "";
    String str3 = "";
    String str4 = "";
    String str5 = "";
    String str6 = "";
    try
    {
      if (this.mParamsBundle.containsKey("special_id"))
        str1 = this.mParamsBundle.getString("special_id");
      if (this.mParamsBundle.containsKey("live_show_id"))
        str2 = this.mParamsBundle.getString("live_show_id");
      if ((TextUtils.isEmpty(str2)) && (this.mParamsBundle.containsKey("special_id")))
      {
        Logger.i(this.TAG, "KEY_LIVE_SHOW_ID has no value, so try KEY_SPECIAL_ID");
        str2 = this.mParamsBundle.getString("special_id");
      }
      if (this.mParamsBundle.containsKey("live_show_web_url"))
        str3 = this.mParamsBundle.getString("live_show_web_url");
      if (this.mParamsBundle.containsKey("film_name"))
        str4 = this.mParamsBundle.getString("film_name");
      if (this.mParamsBundle.containsKey("begin_time"))
        str6 = this.mParamsBundle.getString("begin_time");
      if (this.mParamsBundle.containsKey("begin_day"))
        str5 = this.mParamsBundle.getString("begin_day");
      Logger.i(this.TAG, "special_id=" + str1 + " live_show_id=" + str2 + " live_show_url=" + str3 + " film_name=" + str4 + " begin_time" + str6 + " begin_day=" + str5);
      if (GlobalLogic.getInstance().isUserLogined())
      {
        Logger.i(this.TAG, "user has logined!");
        if ((TextUtils.isEmpty(str2)) && (this.mMediaAssetHelperListener != null))
        {
          this.mMediaAssetHelperListener.onError(this.mAction, "预约失败！请重试！");
          return;
        }
        this.mCollectLogic.addLiveShowReserve("msgsys", str1, str4, str2, str3, str5, str6, new SccmsApiLiveShowReserveTask.ISccmsApiLiveShowReserveTaskListener()
        {
          public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
          {
            if (ReserveSpecialTopicHelper.this.mMediaAssetHelperListener != null)
              ReserveSpecialTopicHelper.this.mMediaAssetHelperListener.onError(ReserveSpecialTopicHelper.this.mAction, "预约失败！请重试！");
          }

          public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ReserveListItem paramAnonymousReserveListItem)
          {
            String str = "";
            if (ReserveSpecialTopicHelper.this.mParamsBundle.containsKey("message_id"))
              str = ReserveSpecialTopicHelper.this.mParamsBundle.getString("message_id");
            ReserveSpecialTopicHelper.this.mParamsBundle.putString("name", "预约成功");
            MQTTMessageDBUpdater.getInstance(new DBProvider(ReserveSpecialTopicHelper.this.mContext), ReserveSpecialTopicHelper.this.mParamsBundle, str, IMQTTMessageDBUpdater.TopicTableUpdateActionType.RESERVE_SPECIAL_TOPIC, new AbstractMQTTUIUpdateNotifier()
            {
              public void finishUpdatingMessageValue(String paramAnonymous2String, Bundle paramAnonymous2Bundle)
              {
                super.finishUpdatingMessageValue(paramAnonymous2String, paramAnonymous2Bundle);
                if (ReserveSpecialTopicHelper.this.mMediaAssetHelperListener != null)
                  ReserveSpecialTopicHelper.this.mMediaAssetHelperListener.onSuccess(ReserveSpecialTopicHelper.this.mAction, ReserveSpecialTopicHelper.this.mParamsBundle);
              }

              public void onError(String paramAnonymous2String, Object paramAnonymous2Object)
              {
                super.onError(paramAnonymous2String, paramAnonymous2Object);
                if (ReserveSpecialTopicHelper.this.mMediaAssetHelperListener != null)
                  ReserveSpecialTopicHelper.this.mMediaAssetHelperListener.onError(ReserveSpecialTopicHelper.this.mAction, "预约失败！请重试！");
              }
            }).runTask();
          }
        });
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      if (this.mMediaAssetHelperListener != null)
      {
        this.mMediaAssetHelperListener.onError(this.mAction, "预约失败！请重试！");
        return;
        Logger.i(this.TAG, "user has not logined!");
        if ((TextUtils.isEmpty(str2)) || (TextUtils.isEmpty(str3)) || (TextUtils.isEmpty(str4)) || (TextUtils.isEmpty(str6)) || (TextUtils.isEmpty(str5)))
        {
          if (this.mMediaAssetHelperListener != null)
            this.mMediaAssetHelperListener.onError(this.mAction, "预约失败！请重试！");
        }
        else
        {
          String str7 = str6.replace("-", "").replace(":", "").replace(" ", "");
          String str8 = str7.replace("-", "");
          Reservation localReservation = new Reservation();
          localReservation.setReservationType("web");
          localReservation.setSpecial_id(str1);
          localReservation.setName(str4);
          localReservation.setVideoId(str2);
          localReservation.setLiveShowId(str2);
          localReservation.setLiveShowUrl(str3);
          localReservation.setDay(str8);
          localReservation.setBeginTime(str7);
          int i = (int)GlobalEnv.getInstance().getReservationDelayNotifyTime();
          localReservation.setTimeLen("0");
          GlobalEnv.getInstance().setReservationDelayNotifyTime(0);
          ReservationService.getinstance().addReservation(localReservation);
          GlobalEnv.getInstance().setReservationDelayNotifyTime(i);
          if (this.mMediaAssetHelperListener != null)
          {
            String str9 = "";
            if (this.mParamsBundle.containsKey("message_id"))
              str9 = this.mParamsBundle.getString("message_id");
            this.mParamsBundle.putString("name", "预约成功");
            MQTTMessageDBUpdater.getInstance(new DBProvider(this.mContext), this.mParamsBundle, str9, IMQTTMessageDBUpdater.TopicTableUpdateActionType.RESERVE_SPECIAL_TOPIC, new AbstractMQTTUIUpdateNotifier()
            {
              public void finishUpdatingMessageValue(String paramAnonymousString, Bundle paramAnonymousBundle)
              {
                super.finishUpdatingMessageValue(paramAnonymousString, paramAnonymousBundle);
                if (ReserveSpecialTopicHelper.this.mMediaAssetHelperListener != null)
                {
                  ReserveSpecialTopicHelper.this.mParamsBundle.putString("message", "系统已记录预约请求，将在您登录后提交！");
                  ReserveSpecialTopicHelper.this.mMediaAssetHelperListener.onSuccess(ReserveSpecialTopicHelper.this.mAction, ReserveSpecialTopicHelper.this.mParamsBundle);
                }
              }

              public void onError(String paramAnonymousString, Object paramAnonymousObject)
              {
                super.onError(paramAnonymousString, paramAnonymousObject);
                if (ReserveSpecialTopicHelper.this.mMediaAssetHelperListener != null)
                  ReserveSpecialTopicHelper.this.mMediaAssetHelperListener.onError(ReserveSpecialTopicHelper.this.mAction, "预约失败！请重试！");
              }
            }).runTask();
          }
        }
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.mediaplayerhelper.helper.ReserveSpecialTopicHelper
 * JD-Core Version:    0.6.2
 */