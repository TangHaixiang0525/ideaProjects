package com.starcor.hunan.msgsys.mediaplayerhelper.helper;

import android.content.Context;
import android.os.Bundle;
import com.starcor.core.domain.PlayBillItem;
import com.starcor.core.domain.ReserveListItem;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.db.DBProvider;
import com.starcor.hunan.msgsys.dbupdater.MQTTMessageDBUpdater;
import com.starcor.hunan.msgsys.interfaces.AbstractMQTTUIUpdateNotifier;
import com.starcor.hunan.msgsys.interfaces.IMQTTMessageDBUpdater;
import com.starcor.hunan.msgsys.interfaces.IMQTTMessageDBUpdater.TopicTableUpdateActionType;
import com.starcor.hunan.service.CollectAndPlayListLogic;
import com.starcor.sccms.api.SccmsApiReplayReserveTask.ISccmsApiReplayReserveTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;

public class OrderReplayHelper extends MediaAssetHelperBase
{
  private CollectAndPlayListLogic collectLogic = new CollectAndPlayListLogic();

  public OrderReplayHelper()
  {
    super(OrderReplayHelper.class.getSimpleName());
  }

  public OrderReplayHelper(Context paramContext)
  {
    super(paramContext, OrderReplayHelper.class.getSimpleName());
  }

  protected void startSubAction()
  {
    Logger.i(this.TAG, "action=" + this.mAction);
    VideoInfo localVideoInfo;
    PlayBillItem localPlayBillItem;
    try
    {
      localVideoInfo = new VideoInfo();
      localVideoInfo.videoId = this.mParamsBundle.getString("video_id");
      localVideoInfo.videoType = Integer.valueOf(this.mParamsBundle.getString("video_type")).intValue();
      localVideoInfo.packageId = this.mParamsBundle.getString("media_asset_id");
      localVideoInfo.categoryId = this.mParamsBundle.getString("category_id");
      localVideoInfo.uiStyle = Integer.valueOf(this.mParamsBundle.getString("ui_style")).intValue();
      localVideoInfo.indexCurrent = Integer.valueOf(this.mParamsBundle.getString("index_current")).intValue();
      localVideoInfo.view_type = Integer.valueOf(this.mParamsBundle.getString("view_type")).intValue();
      localVideoInfo.timeLen = this.mParamsBundle.getString("time_len");
      if (this.mParamsBundle.containsKey("media_asset_name"))
        localVideoInfo.name = this.mParamsBundle.getString("media_asset_name");
      while (true)
      {
        localPlayBillItem = new PlayBillItem();
        localPlayBillItem.begin = this.mParamsBundle.getString("begin_time");
        localPlayBillItem.timeLen = Integer.valueOf(this.mParamsBundle.getString("time_len")).intValue();
        if (!"back_play".equals(this.mAction))
          break;
        this.collectLogic.addReplyPlayReserve(localVideoInfo, localPlayBillItem, new SccmsApiReplayReserveTask.ISccmsApiReplayReserveTaskListener()
        {
          public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
          {
          }

          public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ReserveListItem paramAnonymousReserveListItem)
          {
            String str = "";
            if (OrderReplayHelper.this.mParamsBundle != null)
            {
              str = OrderReplayHelper.this.mParamsBundle.getString("message_id");
              OrderReplayHelper.this.mParamsBundle.putString("name", "预约成功");
            }
            DBProvider localDBProvider = new DBProvider(OrderReplayHelper.this.mContext);
            IMQTTMessageDBUpdater.TopicTableUpdateActionType localTopicTableUpdateActionType = IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_BACK_PLAY;
            MQTTMessageDBUpdater.getInstance(localDBProvider, OrderReplayHelper.this.mParamsBundle, str, localTopicTableUpdateActionType, new AbstractMQTTUIUpdateNotifier()
            {
              public void finishUpdatingMessageValue(String paramAnonymous2String, Bundle paramAnonymous2Bundle)
              {
                super.finishUpdatingMessageValue(paramAnonymous2String, paramAnonymous2Bundle);
                OrderReplayHelper.this.mMediaAssetHelperListener.onSuccess(OrderReplayHelper.this.mAction, OrderReplayHelper.this.mParamsBundle);
              }
            }).runTask();
          }
        });
        return;
        if (this.mParamsBundle.containsKey("name"))
          localVideoInfo.name = this.mParamsBundle.getString("name");
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
    if ("back_play_cancel".equals(this.mAction))
      this.collectLogic.deltetReplyPlayReserve(localVideoInfo, localPlayBillItem, new SccmsApiReplayReserveTask.ISccmsApiReplayReserveTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ReserveListItem paramAnonymousReserveListItem)
        {
          String str = "";
          if (OrderReplayHelper.this.mParamsBundle != null)
          {
            str = OrderReplayHelper.this.mParamsBundle.getString("message_id");
            OrderReplayHelper.this.mParamsBundle.putString("name", "预约");
          }
          DBProvider localDBProvider = new DBProvider(OrderReplayHelper.this.mContext);
          IMQTTMessageDBUpdater.TopicTableUpdateActionType localTopicTableUpdateActionType = IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_BACK_PLAY_CANCEL;
          MQTTMessageDBUpdater.getInstance(localDBProvider, OrderReplayHelper.this.mParamsBundle, str, localTopicTableUpdateActionType, new AbstractMQTTUIUpdateNotifier()
          {
            public void finishUpdatingMessageValue(String paramAnonymous2String, Bundle paramAnonymous2Bundle)
            {
              super.finishUpdatingMessageValue(paramAnonymous2String, paramAnonymous2Bundle);
              OrderReplayHelper.this.mMediaAssetHelperListener.onSuccess(OrderReplayHelper.this.mAction, OrderReplayHelper.this.mParamsBundle);
            }
          }).runTask();
        }
      });
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.mediaplayerhelper.helper.OrderReplayHelper
 * JD-Core Version:    0.6.2
 */