package com.starcor.hunan.msgsys.mediaplayerhelper.helper;

import android.content.Context;
import android.os.Bundle;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.db.DBProvider;
import com.starcor.hunan.interfaces.SuccessCallBack;
import com.starcor.hunan.msgsys.dbupdater.MQTTMessageDBUpdater;
import com.starcor.hunan.msgsys.interfaces.AbstractMQTTUIUpdateNotifier;
import com.starcor.hunan.msgsys.interfaces.IMQTTMessageDBUpdater;
import com.starcor.hunan.msgsys.interfaces.IMQTTMessageDBUpdater.TopicTableUpdateActionType;
import com.starcor.hunan.service.CollectAndPlayListLogic;

public class OrderChaseDramaHelper extends MediaAssetHelperBase
{
  private CollectAndPlayListLogic collectLogic = new CollectAndPlayListLogic();

  public OrderChaseDramaHelper()
  {
    super(OrderChaseDramaHelper.class.getSimpleName());
  }

  public OrderChaseDramaHelper(Context paramContext)
  {
    super(paramContext, OrderChaseDramaHelper.class.getSimpleName());
  }

  protected void startSubAction()
  {
    Logger.i(this.TAG, "action=" + this.mAction);
    VideoInfo localVideoInfo;
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
      while ("chase_drama".equals(this.mAction))
      {
        this.collectLogic.addTracePlay(new SuccessCallBack()
        {
          public void getDataError(String paramAnonymousString, int paramAnonymousInt)
          {
            OrderChaseDramaHelper.this.mMediaAssetHelperListener.onError(OrderChaseDramaHelper.this.mAction, "httpStateCode=" + paramAnonymousInt + " " + paramAnonymousString);
          }

          public void getDataSuccess(Void paramAnonymousVoid)
          {
            String str = "";
            if (OrderChaseDramaHelper.this.mParamsBundle != null)
            {
              str = OrderChaseDramaHelper.this.mParamsBundle.getString("message_id");
              OrderChaseDramaHelper.this.mParamsBundle.putString("name", "预约成功");
            }
            DBProvider localDBProvider = new DBProvider(OrderChaseDramaHelper.this.mContext);
            IMQTTMessageDBUpdater.TopicTableUpdateActionType localTopicTableUpdateActionType = IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_CHASE_DRAMA;
            MQTTMessageDBUpdater.getInstance(localDBProvider, OrderChaseDramaHelper.this.mParamsBundle, str, localTopicTableUpdateActionType, new AbstractMQTTUIUpdateNotifier()
            {
              public void finishUpdatingMessageValue(String paramAnonymous2String, Bundle paramAnonymous2Bundle)
              {
                super.finishUpdatingMessageValue(paramAnonymous2String, paramAnonymous2Bundle);
                OrderChaseDramaHelper.this.mMediaAssetHelperListener.onSuccess(OrderChaseDramaHelper.this.mAction, OrderChaseDramaHelper.this.mParamsBundle);
              }
            }).runTask();
          }
        }
        , localVideoInfo);
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
    if ("chase_drama_cancel".equals(this.mAction))
      this.collectLogic.delTracePlay(new SuccessCallBack()
      {
        public void getDataError(String paramAnonymousString, int paramAnonymousInt)
        {
          OrderChaseDramaHelper.this.mMediaAssetHelperListener.onError(OrderChaseDramaHelper.this.mAction, "httpStateCode=" + paramAnonymousInt + " " + paramAnonymousString);
        }

        public void getDataSuccess(Void paramAnonymousVoid)
        {
          String str = "";
          if (OrderChaseDramaHelper.this.mParamsBundle != null)
          {
            str = OrderChaseDramaHelper.this.mParamsBundle.getString("message_id");
            OrderChaseDramaHelper.this.mParamsBundle.putString("name", "预约");
          }
          DBProvider localDBProvider = new DBProvider(OrderChaseDramaHelper.this.mContext);
          IMQTTMessageDBUpdater.TopicTableUpdateActionType localTopicTableUpdateActionType = IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_CHASE_DRAMA_CANCEL;
          MQTTMessageDBUpdater.getInstance(localDBProvider, OrderChaseDramaHelper.this.mParamsBundle, str, localTopicTableUpdateActionType, new AbstractMQTTUIUpdateNotifier()
          {
            public void finishUpdatingMessageValue(String paramAnonymous2String, Bundle paramAnonymous2Bundle)
            {
              super.finishUpdatingMessageValue(paramAnonymous2String, paramAnonymous2Bundle);
              OrderChaseDramaHelper.this.mMediaAssetHelperListener.onSuccess(OrderChaseDramaHelper.this.mAction, OrderChaseDramaHelper.this.mParamsBundle);
            }
          }).runTask();
        }
      }
      , localVideoInfo.videoId, localVideoInfo);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.mediaplayerhelper.helper.OrderChaseDramaHelper
 * JD-Core Version:    0.6.2
 */