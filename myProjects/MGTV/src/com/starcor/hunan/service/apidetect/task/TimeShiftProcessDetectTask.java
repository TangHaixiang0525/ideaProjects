package com.starcor.hunan.service.apidetect.task;

import com.starcor.core.domain.CategoryItem;
import com.starcor.core.domain.ChannelInfoV2;
import com.starcor.core.domain.ChannelItemInfoV2;
import com.starcor.core.domain.MediaAssetsInfo;
import com.starcor.core.domain.MetadataGoup;
import com.starcor.core.domain.PlayInfo;
import com.starcor.core.domain.PlayInfoV2;
import com.starcor.core.domain.UserAuth;
import com.starcor.core.domain.UserAuthV2;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.DialogActivity;
import com.starcor.hunan.DialogActivity.MediaAssetIdType;
import com.starcor.hunan.service.SystemTimeManager;
import com.starcor.hunan.service.apidetect.data.ApiDetectAppInfo;
import com.starcor.hunan.service.apidetect.server.ApiDetectServerApiManager;
import com.starcor.hunan.service.apidetect.task.subtask.ApiDetectSccmsApiGetEpgIndexTask.IApiDetectSccmsApiGetEpgDataTaskListener;
import com.starcor.hunan.service.apidetect.task.subtask.ApiDetectSccmsN3A2GetEpgDataTask.IApiDetectSccmsApiN3A2GetEpgDataTaskListener;
import com.starcor.sccms.api.SccmsApiGetChannelListV2Task;
import com.starcor.sccms.api.SccmsApiGetChannelListV2Task.ISccmsApiGetChannelListV2TaskListener;
import com.starcor.sccms.api.SccmsApiGetEpgIndexTask;
import com.starcor.sccms.api.SccmsApiGetMediaAssetsInfoTask;
import com.starcor.sccms.api.SccmsApiGetMediaAssetsInfoTask.ISccmsApiGetMediaAssetsInfoTaskListener;
import com.starcor.sccms.api.SccmsApiGetUserAuthTask.ISccmsApiGetUserAuthTaskListener;
import com.starcor.sccms.api.SccmsApiGetUserAuthV2Task.ISccmsApiGetUserAuthV2TaskListener;
import com.starcor.sccms.api.SccmsApiRequestVideoPlayTask.ISccmsApiRequestVideoPlayTaskListener;
import com.starcor.sccms.api.SccmsApiRequestVideoPlayV2Task;
import com.starcor.sccms.api.SccmsApiRequestVideoPlayV2Task.ISccmsApiRequestVideoPlayV2TaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TimeShiftProcessDetectTask extends ApiProcessDetectTask
{
  public static final String TAG = TimeShiftProcessDetectTask.class.getSimpleName();
  ChannelItemInfoV2 mChannelItemInfo = null;
  private String nns_category_id_timeshift = "";
  private String nns_packet_id_timeshift = "";

  protected void runTimeShiftProcess()
  {
    setResultHeaderByTaskId(this.mTimeshiftProcessTaskId, SccmsApiGetEpgIndexTask.class.getSimpleName(), "N1_A_1", "请求APK各类主入口", "无");
    this.mTimeshiftProcessTaskId = ApiDetectServerApiManager.i().APIGetEpgIndex(new SccmsApiGetEpgDataTaskListener(null));
    Logger.i(TAG, "runVodProcess mTimeshiftProcessTaskId=" + this.mTimeshiftProcessTaskId);
  }

  private class SccmsApiGetChannelListV2TaskListener
    implements SccmsApiGetChannelListV2Task.ISccmsApiGetChannelListV2TaskListener
  {
    private SccmsApiGetChannelListV2TaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      int i = paramServerApiTaskInfo.getTaskId();
      Logger.i(TimeShiftProcessDetectTask.TAG, "SccmsApiGetChannelListV2TaskListener onError: taskInfo.getTaskId()=" + i);
      TimeShiftProcessDetectTask.this.setResultBodyByTaskIdOnError(paramServerApiTaskInfo.getTaskId(), paramServerApiCommonError.toString() + TimeShiftProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo) + "\n================================================\n\n");
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ChannelInfoV2 paramChannelInfoV2)
    {
      Logger.i(TimeShiftProcessDetectTask.TAG, "SccmsApiGetChannelListV2TaskListener onSuccess taskId=" + paramServerApiTaskInfo.getTaskId());
      TimeShiftProcessDetectTask.this.setResultBodyByTaskId(paramServerApiTaskInfo.getTaskId(), paramChannelInfoV2.toString() + TimeShiftProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo));
      if ((paramChannelInfoV2.channelList != null) && (paramChannelInfoV2.channelList.size() > 0))
      {
        TimeShiftProcessDetectTask.this.mChannelItemInfo = ((ChannelItemInfoV2)paramChannelInfoV2.channelList.get(0));
        String str = GlobalLogic.getInstance().getUserId();
        TimeShiftProcessDetectTask.this.setResultHeaderByTaskId(paramServerApiTaskInfo.getTaskId(), "SccmsApiGetUserAuthTask", "N200_A_1", "时移频道鉴权", "nns_user_id=" + str + " nns_video_id=" + TimeShiftProcessDetectTask.this.mChannelItemInfo.id + " nns_video_type=1");
        int i = ApiDetectServerApiManager.i().APIGetUserAuth(str, TimeShiftProcessDetectTask.this.mChannelItemInfo.id, "1", new TimeShiftProcessDetectTask.SccmsApiGetUserAuthTaskListener(TimeShiftProcessDetectTask.this, null));
        TimeShiftProcessDetectTask.this.setNextTaskIdByCurrentTaskId(paramServerApiTaskInfo.getTaskId(), i);
        return;
      }
      TimeShiftProcessDetectTask.this.setResultBodyByTaskId(paramServerApiTaskInfo.getTaskId(), "channel list is null or has no item!\n================================================\n\n");
    }
  }

  private class SccmsApiGetEpgDataTaskListener
    implements ApiDetectSccmsApiGetEpgIndexTask.IApiDetectSccmsApiGetEpgDataTaskListener
  {
    private SccmsApiGetEpgDataTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      int i = paramServerApiTaskInfo.getTaskId();
      Logger.i(TimeShiftProcessDetectTask.TAG, "SccmsApiGetEpgDataTaskListener onError: taskInfo.getTaskId()=" + i);
      TimeShiftProcessDetectTask.this.setResultBodyByTaskIdOnError(paramServerApiTaskInfo.getTaskId(), paramServerApiCommonError.toString() + TimeShiftProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo) + "\n================================================\n\n");
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, Integer paramInteger)
    {
      Logger.i(TimeShiftProcessDetectTask.TAG, "SccmsApiGetEpgDataTaskListener onSuccess: taskInfo.getTaskId()=" + paramServerApiTaskInfo.getTaskId());
      TimeShiftProcessDetectTask.this.setResultBodyByTaskId(paramServerApiTaskInfo.getTaskId(), "ApiDetectAppInfo.URL_n39_a=" + ApiDetectAppInfo.URL_n39_a);
      TimeShiftProcessDetectTask.this.setResultHeaderByTaskId(TimeShiftProcessDetectTask.this.mTimeshiftProcessTaskId, "SccmsApiN3A2GetEpgDataTask", "N3_A_2", "获取 n3_a_i EPG总入口信息", "无");
      int i = ApiDetectServerApiManager.i().APIN3A2GetEpgData(new TimeShiftProcessDetectTask.SccmsApiN3A2GetEpgDataTaskListener(TimeShiftProcessDetectTask.this, null));
      Logger.i(TimeShiftProcessDetectTask.TAG, "SccmsApiGetEpgDataTaskListener onSuccess: nextTaskId=" + i);
      TimeShiftProcessDetectTask.this.setNextTaskIdByCurrentTaskId(paramServerApiTaskInfo.getTaskId(), i);
    }
  }

  private class SccmsApiGetMediaAssetsInfoTaskListener
    implements SccmsApiGetMediaAssetsInfoTask.ISccmsApiGetMediaAssetsInfoTaskListener
  {
    private SccmsApiGetMediaAssetsInfoTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      int i = paramServerApiTaskInfo.getTaskId();
      Logger.i(TimeShiftProcessDetectTask.TAG, "SccmsApiGetMediaAssetsInfoTaskListener onError: taskInfo.getTaskId()=" + i);
      TimeShiftProcessDetectTask.this.setResultBodyByTaskIdOnError(paramServerApiTaskInfo.getTaskId(), paramServerApiCommonError.toString() + TimeShiftProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo) + "\n================================================\n\n");
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, MediaAssetsInfo paramMediaAssetsInfo)
    {
      Logger.i(TimeShiftProcessDetectTask.TAG, "SccmsApiGetMediaAssetsInfoTaskListener onSuccess: taskInfo.getTaskId()=" + paramServerApiTaskInfo.getTaskId());
      int j;
      if (paramServerApiTaskInfo.getTaskId() == TimeShiftProcessDetectTask.this.mTimeshiftProcessTaskId)
      {
        TimeShiftProcessDetectTask.access$302(TimeShiftProcessDetectTask.this, paramMediaAssetsInfo.package_id);
        if ((paramMediaAssetsInfo.cList != null) && (paramMediaAssetsInfo.cList.size() > 0))
          j = paramMediaAssetsInfo.cList.size();
      }
      for (int k = 0; ; k++)
        if (k < j)
        {
          CategoryItem localCategoryItem = (CategoryItem)paramMediaAssetsInfo.cList.get(k);
          if ((localCategoryItem != null) && (!localCategoryItem.name.contains("VIP")) && (!localCategoryItem.name.contains("vip")) && (!localCategoryItem.name.contains("Vip")))
            TimeShiftProcessDetectTask.access$402(TimeShiftProcessDetectTask.this, localCategoryItem.id);
        }
        else
        {
          TimeShiftProcessDetectTask.this.setResultBodyByTaskId(paramServerApiTaskInfo.getTaskId(), paramMediaAssetsInfo.toString() + TimeShiftProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo));
          TimeShiftProcessDetectTask.this.setResultHeaderByTaskId(paramServerApiTaskInfo.getTaskId(), SccmsApiGetChannelListV2Task.class.getSimpleName(), "N39_A_1", "获取时移节目列表", "nns_packet_id=" + TimeShiftProcessDetectTask.this.nns_packet_id_timeshift + "nns_category_id=" + TimeShiftProcessDetectTask.this.nns_category_id_timeshift);
          int i = ApiDetectServerApiManager.i().APIGetChannelListV2(TimeShiftProcessDetectTask.this.nns_packet_id_timeshift, TimeShiftProcessDetectTask.this.nns_category_id_timeshift, new TimeShiftProcessDetectTask.SccmsApiGetChannelListV2TaskListener(TimeShiftProcessDetectTask.this, null));
          TimeShiftProcessDetectTask.this.setNextTaskIdByCurrentTaskId(paramServerApiTaskInfo.getTaskId(), i);
          return;
        }
    }
  }

  private class SccmsApiGetUserAuthTaskListener
    implements SccmsApiGetUserAuthTask.ISccmsApiGetUserAuthTaskListener
  {
    private SccmsApiGetUserAuthTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      int i = paramServerApiTaskInfo.getTaskId();
      Logger.i(TimeShiftProcessDetectTask.TAG, "SccmsApiGetUserAuthTaskListener onError: taskInfo.getTaskId()=" + i);
      TimeShiftProcessDetectTask.this.setResultBodyByTaskIdOnError(paramServerApiTaskInfo.getTaskId(), paramServerApiCommonError.toString() + TimeShiftProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo) + "\n================================================\n\n");
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UserAuth paramUserAuth)
    {
      Logger.i(TimeShiftProcessDetectTask.TAG, "SccmsApiGetUserAuthTaskListener onSuccess: taskInfo.getTaskId()=" + paramServerApiTaskInfo.getTaskId());
      TimeShiftProcessDetectTask.this.setResultBodyByTaskId(paramServerApiTaskInfo.getTaskId(), paramUserAuth.toString() + TimeShiftProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo));
      String str1;
      if (paramServerApiTaskInfo.getTaskId() == TimeShiftProcessDetectTask.this.mTimeshiftProcessTaskId)
      {
        str1 = "";
        if (!TimeShiftProcessDetectTask.this.mChannelItemInfo.capability.contains("LIVE"))
          break label364;
        str1 = "LIVE";
      }
      while (true)
      {
        Date localDate = new Date(SystemTimeManager.getCurrentServerTime());
        String str2 = new SimpleDateFormat("yyyyMMdd").format(localDate);
        String str3 = new SimpleDateFormat("HHmmss").format(localDate);
        TimeShiftProcessDetectTask.this.setResultHeaderByTaskId(paramServerApiTaskInfo.getTaskId(), SccmsApiRequestVideoPlayV2Task.class.getSimpleName(), "N50_A_2", "请时移播频道节目播放串V2", "nns_video_id=" + TimeShiftProcessDetectTask.this.mChannelItemInfo.id + " nns_video_type=1" + " nns_packet_id=" + TimeShiftProcessDetectTask.this.nns_packet_id_timeshift + " nns_category_id=" + TimeShiftProcessDetectTask.this.mChannelItemInfo.categoryId + " nns_video_index=0" + " nns_media_id=" + " nns_quality=" + " nns_begin_day=" + str2 + " nns_begin_time=" + str3 + " nns_time_len=" + " nns_huawei_cid=" + " nns_caps=" + str1);
        int i = ApiDetectServerApiManager.i().ApiRequestVideoPlayV2(TimeShiftProcessDetectTask.this.mChannelItemInfo.id, 1, TimeShiftProcessDetectTask.this.nns_packet_id_timeshift, TimeShiftProcessDetectTask.this.mChannelItemInfo.categoryId, 0, "", "", str2, str3, "", "", str1, new TimeShiftProcessDetectTask.SccmsApiRequestVideoPlayV2TaskListener(TimeShiftProcessDetectTask.this));
        TimeShiftProcessDetectTask.this.setNextTaskIdByCurrentTaskId(paramServerApiTaskInfo.getTaskId(), i);
        return;
        label364: if (TimeShiftProcessDetectTask.this.mChannelItemInfo.capability.contains("TSTV"))
          str1 = "TSTV";
      }
    }
  }

  private class SccmsApiGetUserAuthV2TaskListener
    implements SccmsApiGetUserAuthV2Task.ISccmsApiGetUserAuthV2TaskListener
  {
    private SccmsApiGetUserAuthV2TaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      int i = paramServerApiTaskInfo.getTaskId();
      Logger.i(TimeShiftProcessDetectTask.TAG, "SccmsApiGetUserAuthV2TaskListener onError: taskInfo.getTaskId()=" + i);
      TimeShiftProcessDetectTask.this.setResultBodyByTaskIdOnError(paramServerApiTaskInfo.getTaskId(), paramServerApiCommonError.toString() + TimeShiftProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo) + "\n================================================\n\n");
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, UserAuthV2 paramUserAuthV2)
    {
      Logger.i(TimeShiftProcessDetectTask.TAG, "SccmsApiGetUserAuthV2TaskListener onSuccess: taskInfo.getTaskId()=" + paramServerApiTaskInfo.getTaskId());
      TimeShiftProcessDetectTask.this.setResultBodyByTaskId(paramServerApiTaskInfo.getTaskId(), paramUserAuthV2.toString() + TimeShiftProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo));
    }
  }

  private class SccmsApiN3A2GetEpgDataTaskListener
    implements ApiDetectSccmsN3A2GetEpgDataTask.IApiDetectSccmsApiN3A2GetEpgDataTaskListener
  {
    private SccmsApiN3A2GetEpgDataTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      int i = paramServerApiTaskInfo.getTaskId();
      Logger.i(TimeShiftProcessDetectTask.TAG, "SccmsApiN3A2GetEpgDataTaskListener onError: taskInfo.getTaskId()=" + i);
      TimeShiftProcessDetectTask.this.setResultBodyByTaskIdOnError(paramServerApiTaskInfo.getTaskId(), paramServerApiCommonError.toString() + TimeShiftProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo) + "\n================================================\n\n");
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<MetadataGoup> paramArrayList)
    {
      Logger.i(TimeShiftProcessDetectTask.TAG, "SccmsApiN3A2GetEpgDataTaskListener onSuccess: taskInfo.getTaskId()=" + paramServerApiTaskInfo.getTaskId());
      if (paramServerApiTaskInfo.getTaskId() == TimeShiftProcessDetectTask.this.mTimeshiftProcessTaskId)
      {
        TimeShiftProcessDetectTask.this.setResultBodyByTaskId(paramServerApiTaskInfo.getTaskId(), paramArrayList.toString() + TimeShiftProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo));
        String str = DialogActivity.getMediaAssetIdByType(DialogActivity.MediaAssetIdType.MEDIA_ASSET_ID_TYPE_TIMESHIFT);
        Logger.i(TimeShiftProcessDetectTask.TAG, "nns_packet_id=" + str);
        TimeShiftProcessDetectTask.this.setResultHeaderByTaskId(paramServerApiTaskInfo.getTaskId(), SccmsApiGetMediaAssetsInfoTask.class.getSimpleName(), "N3_A_D_1", "请求频道栏目分类信息", "nns_packet_id=" + str);
        int i = ApiDetectServerApiManager.i().APIGetMediaAssetsInfo(str, new TimeShiftProcessDetectTask.SccmsApiGetMediaAssetsInfoTaskListener(TimeShiftProcessDetectTask.this, null));
        TimeShiftProcessDetectTask.this.setNextTaskIdByCurrentTaskId(paramServerApiTaskInfo.getTaskId(), i);
      }
    }
  }

  private class SccmsApiRequestVideoPlayTaskListener
    implements SccmsApiRequestVideoPlayTask.ISccmsApiRequestVideoPlayTaskListener
  {
    private SccmsApiRequestVideoPlayTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      int i = paramServerApiTaskInfo.getTaskId();
      Logger.i(TimeShiftProcessDetectTask.TAG, "SccmsApiRequestVideoPlayTaskListener onError: taskInfo.getTaskId()=" + i);
      TimeShiftProcessDetectTask.this.setResultBodyByTaskIdOnError(paramServerApiTaskInfo.getTaskId(), paramServerApiCommonError.toString() + TimeShiftProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo) + "\n================================================\n\n");
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, PlayInfo paramPlayInfo)
    {
      Logger.i(TimeShiftProcessDetectTask.TAG, "SccmsApiRequestVideoPlayTaskListener onSuccess taskId=" + paramServerApiTaskInfo.getTaskId());
      TimeShiftProcessDetectTask.this.setResultBodyByTaskId(paramServerApiTaskInfo.getTaskId(), paramPlayInfo.toString() + TimeShiftProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo) + "\n================================================\n\n");
      Logger.i(TimeShiftProcessDetectTask.TAG, "finish running process: " + TimeShiftProcessDetectTask.this.getCurrentApiProcessTypeByTaskId(paramServerApiTaskInfo.getTaskId()));
      TimeShiftProcessDetectTask.this.runCallbackByTaskId(paramServerApiTaskInfo.getTaskId());
      Logger.i(TimeShiftProcessDetectTask.TAG, TimeShiftProcessDetectTask.this.getApiTimeFormatString(false, "" + TimeShiftProcessDetectTask.this.getCurrentApiProcessTypeByTaskId(paramServerApiTaskInfo.getTaskId())));
    }
  }

  class SccmsApiRequestVideoPlayV2TaskListener
    implements SccmsApiRequestVideoPlayV2Task.ISccmsApiRequestVideoPlayV2TaskListener
  {
    SccmsApiRequestVideoPlayV2TaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      int i = paramServerApiTaskInfo.getTaskId();
      Logger.i(TimeShiftProcessDetectTask.TAG, "SccmsApiRequestVideoPlayV2TaskListener onError: taskInfo.getTaskId()=" + i);
      TimeShiftProcessDetectTask.this.setResultBodyByTaskIdOnError(paramServerApiTaskInfo.getTaskId(), paramServerApiCommonError.toString() + TimeShiftProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo) + "\n================================================\n\n");
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, PlayInfoV2 paramPlayInfoV2)
    {
      Logger.i(TimeShiftProcessDetectTask.TAG, "SccmsApiRequestVideoPlayV2TaskListener onSuccess taskId=" + paramServerApiTaskInfo.getTaskId());
      TimeShiftProcessDetectTask.this.setResultBodyByTaskId(paramServerApiTaskInfo.getTaskId(), paramPlayInfoV2.toString() + TimeShiftProcessDetectTask.this.getHttpHeaders(paramServerApiTaskInfo) + "\n================================================\n\n");
      Logger.i(TimeShiftProcessDetectTask.TAG, "SccmsApiRequestVideoPlayV2TaskListener onSuccess taskId=" + paramServerApiTaskInfo.getTaskId());
      Logger.i(TimeShiftProcessDetectTask.TAG, "finish running process: " + TimeShiftProcessDetectTask.this.getCurrentApiProcessTypeByTaskId(paramServerApiTaskInfo.getTaskId()));
      TimeShiftProcessDetectTask.this.runCallbackByTaskId(paramServerApiTaskInfo.getTaskId());
      Logger.i(TimeShiftProcessDetectTask.TAG, TimeShiftProcessDetectTask.this.getApiTimeFormatString(false, "" + TimeShiftProcessDetectTask.this.getCurrentApiProcessTypeByTaskId(paramServerApiTaskInfo.getTaskId())));
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.service.apidetect.task.TimeShiftProcessDetectTask
 * JD-Core Version:    0.6.2
 */