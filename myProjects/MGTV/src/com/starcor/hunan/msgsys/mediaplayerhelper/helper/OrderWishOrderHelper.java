package com.starcor.hunan.msgsys.mediaplayerhelper.helper;

import android.content.Context;
import android.os.Bundle;
import com.starcor.core.domain.CommonState;
import com.starcor.core.domain.CommonState.Result;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.db.DBProvider;
import com.starcor.hunan.msgsys.dbupdater.MQTTMessageDBUpdater;
import com.starcor.hunan.msgsys.interfaces.AbstractMQTTUIUpdateNotifier;
import com.starcor.hunan.msgsys.interfaces.IMQTTMessageDBUpdater;
import com.starcor.hunan.msgsys.interfaces.IMQTTMessageDBUpdater.TopicTableUpdateActionType;
import com.starcor.sccms.api.SccmsApiCancelVideoWishTask.ISccmsApiCancelVideoWishTaskListener;
import com.starcor.sccms.api.SccmsApiSetVideoWishTask.ISccmsApiSetVideoWishTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;

public class OrderWishOrderHelper extends MediaAssetHelperBase
{
  public OrderWishOrderHelper()
  {
    super(OrderWishOrderHelper.class.getSimpleName());
  }

  public OrderWishOrderHelper(Context paramContext)
  {
    super(paramContext, OrderWishOrderHelper.class.getSimpleName());
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
      while ("wish_order".equals(this.mAction))
      {
        ServerApiManager.i().APISetVideoWish(localVideoInfo, new SccmsApiSetVideoWishTask.ISccmsApiSetVideoWishTaskListener()
        {
          public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
          {
            Logger.e(OrderWishOrderHelper.this.TAG, "APISetVideoWish failed!! " + paramAnonymousServerApiCommonError.toString());
            OrderWishOrderHelper.this.mMediaAssetHelperListener.onError(OrderWishOrderHelper.this.mAction, paramAnonymousServerApiCommonError.toString());
          }

          public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, CommonState paramAnonymousCommonState)
          {
            Logger.e(OrderWishOrderHelper.this.TAG, "APISetVideoWish success!! result = " + paramAnonymousCommonState);
            if ((paramAnonymousCommonState != null) && (paramAnonymousCommonState.result != null) && ("0".equals(paramAnonymousCommonState.result.state)))
            {
              String str = "";
              if (OrderWishOrderHelper.this.mParamsBundle != null)
              {
                str = OrderWishOrderHelper.this.mParamsBundle.getString("message_id");
                OrderWishOrderHelper.this.mParamsBundle.putString("name", "预约成功");
              }
              DBProvider localDBProvider = new DBProvider(OrderWishOrderHelper.this.mContext);
              IMQTTMessageDBUpdater.TopicTableUpdateActionType localTopicTableUpdateActionType = IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_WISH_ORDER;
              MQTTMessageDBUpdater.getInstance(localDBProvider, OrderWishOrderHelper.this.mParamsBundle, str, localTopicTableUpdateActionType, new AbstractMQTTUIUpdateNotifier()
              {
                public void finishUpdatingMessageValue(String paramAnonymous2String, Bundle paramAnonymous2Bundle)
                {
                  super.finishUpdatingMessageValue(paramAnonymous2String, paramAnonymous2Bundle);
                  OrderWishOrderHelper.this.mMediaAssetHelperListener.onSuccess(OrderWishOrderHelper.this.mAction, OrderWishOrderHelper.this.mParamsBundle);
                }
              }).runTask();
              return;
            }
            OrderWishOrderHelper.this.mMediaAssetHelperListener.onError(OrderWishOrderHelper.this.mAction, "APISetVideoWish result invalid!");
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
    if ("wish_order_cancel".equals(this.mAction))
      ServerApiManager.i().APICancelVideoWish(localVideoInfo, new SccmsApiCancelVideoWishTask.ISccmsApiCancelVideoWishTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          Logger.e(OrderWishOrderHelper.this.TAG, "APICancelVideoWish failed!! " + paramAnonymousServerApiCommonError.toString());
          OrderWishOrderHelper.this.mMediaAssetHelperListener.onError(OrderWishOrderHelper.this.mAction, paramAnonymousServerApiCommonError.toString());
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, CommonState paramAnonymousCommonState)
        {
          Logger.e(OrderWishOrderHelper.this.TAG, "APICancelVideoWish success!! result = " + paramAnonymousCommonState);
          if ((paramAnonymousCommonState != null) && (paramAnonymousCommonState.result != null) && ("0".equals(paramAnonymousCommonState.result.state)))
          {
            Logger.i(OrderWishOrderHelper.this.TAG, "total" + paramAnonymousCommonState.total);
            String str = "";
            if (OrderWishOrderHelper.this.mParamsBundle != null)
            {
              str = OrderWishOrderHelper.this.mParamsBundle.getString("message_id");
              OrderWishOrderHelper.this.mParamsBundle.putString("name", "预约");
            }
            DBProvider localDBProvider = new DBProvider(OrderWishOrderHelper.this.mContext);
            IMQTTMessageDBUpdater.TopicTableUpdateActionType localTopicTableUpdateActionType = IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_WISH_ORDER_CANCEL;
            MQTTMessageDBUpdater.getInstance(localDBProvider, OrderWishOrderHelper.this.mParamsBundle, str, localTopicTableUpdateActionType, new AbstractMQTTUIUpdateNotifier()
            {
              public void finishUpdatingMessageValue(String paramAnonymous2String, Bundle paramAnonymous2Bundle)
              {
                super.finishUpdatingMessageValue(paramAnonymous2String, paramAnonymous2Bundle);
                OrderWishOrderHelper.this.mMediaAssetHelperListener.onSuccess(OrderWishOrderHelper.this.mAction, OrderWishOrderHelper.this.mParamsBundle);
              }
            }).runTask();
            return;
          }
          OrderWishOrderHelper.this.mMediaAssetHelperListener.onError(OrderWishOrderHelper.this.mAction, "APICancelVideoWish result invalid!");
        }
      });
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.mediaplayerhelper.helper.OrderWishOrderHelper
 * JD-Core Version:    0.6.2
 */