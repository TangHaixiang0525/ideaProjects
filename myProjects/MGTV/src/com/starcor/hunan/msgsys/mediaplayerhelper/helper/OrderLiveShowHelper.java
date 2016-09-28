package com.starcor.hunan.msgsys.mediaplayerhelper.helper;

import android.content.Context;
import android.os.Bundle;
import com.starcor.core.domain.ReserveListItem;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.db.DBProvider;
import com.starcor.hunan.msgsys.dbupdater.MQTTMessageDBUpdater;
import com.starcor.hunan.msgsys.interfaces.AbstractMQTTUIUpdateNotifier;
import com.starcor.hunan.msgsys.interfaces.IMQTTMessageDBUpdater;
import com.starcor.hunan.msgsys.interfaces.IMQTTMessageDBUpdater.TopicTableUpdateActionType;
import com.starcor.hunan.service.CollectAndPlayListLogic;
import com.starcor.sccms.api.SccmsApiLiveShowReserveTask.ISccmsApiLiveShowReserveTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;

public class OrderLiveShowHelper extends MediaAssetHelperBase
{
  private CollectAndPlayListLogic collectLogic = new CollectAndPlayListLogic();

  public OrderLiveShowHelper()
  {
    super(OrderLiveShowHelper.class.getSimpleName());
  }

  public OrderLiveShowHelper(Context paramContext)
  {
    super(paramContext, OrderLiveShowHelper.class.getSimpleName());
  }

  protected void startSubAction()
  {
    Logger.i(this.TAG, "action=" + this.mAction);
    try
    {
      VideoInfo localVideoInfo = new VideoInfo();
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
        this.mParamsBundle.getString("special_id");
        if (!"live_show".equals(this.mAction))
          break;
        this.collectLogic.addLiveShowReserve("msgsys", "", "", "", "", "", "", new SccmsApiLiveShowReserveTask.ISccmsApiLiveShowReserveTaskListener()
        {
          public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
          {
          }

          public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ReserveListItem paramAnonymousReserveListItem)
          {
            String str = "";
            if (OrderLiveShowHelper.this.mParamsBundle != null)
            {
              str = OrderLiveShowHelper.this.mParamsBundle.getString("message_id");
              OrderLiveShowHelper.this.mParamsBundle.putString("name", "预约成功");
            }
            DBProvider localDBProvider = new DBProvider(OrderLiveShowHelper.this.mContext);
            IMQTTMessageDBUpdater.TopicTableUpdateActionType localTopicTableUpdateActionType = IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_LIVE_SHOW;
            MQTTMessageDBUpdater.getInstance(localDBProvider, OrderLiveShowHelper.this.mParamsBundle, str, localTopicTableUpdateActionType, new AbstractMQTTUIUpdateNotifier()
            {
              public void finishUpdatingMessageValue(String paramAnonymous2String, Bundle paramAnonymous2Bundle)
              {
                super.finishUpdatingMessageValue(paramAnonymous2String, paramAnonymous2Bundle);
                OrderLiveShowHelper.this.mMediaAssetHelperListener.onSuccess(OrderLiveShowHelper.this.mAction, OrderLiveShowHelper.this.mParamsBundle);
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
    boolean bool = "live_show_cancel".equals(this.mAction);
    if (bool);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.mediaplayerhelper.helper.OrderLiveShowHelper
 * JD-Core Version:    0.6.2
 */