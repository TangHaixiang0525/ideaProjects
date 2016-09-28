package com.starcor.hunan.msgsys.mediaplayerhelper.helper;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.starcor.core.domain.ReserveListItem;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.db.DBProvider;
import com.starcor.hunan.msgsys.dbupdater.MQTTMessageDBUpdater;
import com.starcor.hunan.msgsys.interfaces.AbstractMQTTUIUpdateNotifier;
import com.starcor.hunan.msgsys.interfaces.IMQTTMessageDBUpdater;
import com.starcor.hunan.msgsys.interfaces.IMQTTMessageDBUpdater.TopicTableUpdateActionType;
import com.starcor.hunan.service.CollectAndPlayListLogic;
import com.starcor.sccms.api.SccmsApiBroadcastReserveTask.ISccmsApiBroadCastReserveTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;

public class ReserveTurnPlayHelper extends MediaAssetHelperBase
{
  private CollectAndPlayListLogic mCollectLogic = new CollectAndPlayListLogic();

  public ReserveTurnPlayHelper()
  {
    super(ReserveTurnPlayHelper.class.getSimpleName());
  }

  public ReserveTurnPlayHelper(Context paramContext)
  {
    super(paramContext, ReserveTurnPlayHelper.class.getSimpleName());
  }

  protected void startSubAction()
  {
    String str = "";
    try
    {
      if (this.mParamsBundle.containsKey("channelid"))
        str = this.mParamsBundle.getString("channelid");
      Logger.i(this.TAG, "channelId=" + str);
      if (TextUtils.isEmpty(str))
      {
        if (this.mMediaAssetHelperListener != null)
          this.mMediaAssetHelperListener.onError(this.mAction, "预约失败！请重试！");
      }
      else
      {
        this.mCollectLogic.addBroadCastReserve(str, new SccmsApiBroadcastReserveTask.ISccmsApiBroadCastReserveTaskListener()
        {
          public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
          {
            Logger.i(ReserveTurnPlayHelper.this.TAG, "ISccmsApiBroadCastReserveTaskListener onError");
            if (ReserveTurnPlayHelper.this.mMediaAssetHelperListener != null)
              ReserveTurnPlayHelper.this.mMediaAssetHelperListener.onError(ReserveTurnPlayHelper.this.mAction, "预约失败！请重试！");
          }

          public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ReserveListItem paramAnonymousReserveListItem)
          {
            Logger.i(ReserveTurnPlayHelper.this.TAG, "ISccmsApiBroadCastReserveTaskListener onSuccess");
            String str = "";
            if (ReserveTurnPlayHelper.this.mParamsBundle.containsKey("message_id"))
              str = ReserveTurnPlayHelper.this.mParamsBundle.getString("message_id");
            if (TextUtils.isEmpty(str))
            {
              if (ReserveTurnPlayHelper.this.mMediaAssetHelperListener != null)
                ReserveTurnPlayHelper.this.mMediaAssetHelperListener.onError(ReserveTurnPlayHelper.this.mAction, "预约失败！请重试！");
              return;
            }
            ReserveTurnPlayHelper.this.mParamsBundle.putString("name", "预约成功");
            MQTTMessageDBUpdater.getInstance(new DBProvider(ReserveTurnPlayHelper.this.mContext), ReserveTurnPlayHelper.this.mParamsBundle, str, IMQTTMessageDBUpdater.TopicTableUpdateActionType.RESERVE_TURN_PLAY, new AbstractMQTTUIUpdateNotifier()
            {
              public void finishUpdatingMessageValue(String paramAnonymous2String, Bundle paramAnonymous2Bundle)
              {
                super.finishUpdatingMessageValue(paramAnonymous2String, paramAnonymous2Bundle);
                if (ReserveTurnPlayHelper.this.mMediaAssetHelperListener != null)
                {
                  ReserveTurnPlayHelper.this.mParamsBundle.putString("message", "预约成功");
                  ReserveTurnPlayHelper.this.mMediaAssetHelperListener.onSuccess(ReserveTurnPlayHelper.this.mAction, ReserveTurnPlayHelper.this.mParamsBundle);
                }
              }

              public void onError(String paramAnonymous2String, Object paramAnonymous2Object)
              {
                super.onError(paramAnonymous2String, paramAnonymous2Object);
                if (ReserveTurnPlayHelper.this.mMediaAssetHelperListener != null)
                  ReserveTurnPlayHelper.this.mMediaAssetHelperListener.onError(ReserveTurnPlayHelper.this.mAction, "预约失败！请重试！");
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
        this.mMediaAssetHelperListener.onError(this.mAction, "预约失败！请重试！");
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.mediaplayerhelper.helper.ReserveTurnPlayHelper
 * JD-Core Version:    0.6.2
 */