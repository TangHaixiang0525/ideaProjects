package com.starcor.hunan.service.preload;

import com.starcor.core.domain.CategoryItem;
import com.starcor.core.domain.CategoryPosterList;
import com.starcor.core.domain.ChannelInfoV2;
import com.starcor.core.domain.FilmItem;
import com.starcor.core.domain.MediaAssetsInfo;
import com.starcor.core.domain.PlayBillRecommendStrut;
import com.starcor.core.domain.SpecialTopicPutInfo;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.VideoListActivityV2;
import com.starcor.sccms.api.SccmsApiGetChannelListV2Task.ISccmsApiGetChannelListV2TaskListener;
import com.starcor.sccms.api.SccmsApiGetMediaAssetsBindLabelTask.ISccmsApiGetMediaAssetsBindLabelTaskListener;
import com.starcor.sccms.api.SccmsApiGetMediaAssetsInfoTask.ISccmsApiGetMediaAssetsInfoTaskListener;
import com.starcor.sccms.api.SccmsApiGetReplayRecommendDataTask.ISccmsApiGetReplayRecommendDataTaskListener;
import com.starcor.sccms.api.SccmsApiGetSpecialTopicPutTask.ISccmsApiGetSpecialTopicPutTaskListener;
import com.starcor.sccms.api.SccmsApiGetStaticCategoryItemListTask.ISccmsApiGetStaticCategoryItemListTaskListener;
import com.starcor.sccms.api.SccmsApiGetVideoListTask.ISccmsApiGetVideoListTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.service.ErrorCode;
import com.starcor.service.TaskScheduler;
import com.starcor.service.TaskScheduler.Task;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class PreLoadTaskModel
{
  public static final String TAG = PreLoadTaskModel.class.getSimpleName();
  private static String categoryId;

  public static class APIGetCategoryAndVideoList extends PreLoadTaskModel.APITask
  {
    public static final String TASK_NAME = APIGetCategoryAndVideoList.class.getSimpleName();
    private String packageId;

    public APIGetCategoryAndVideoList(String paramString, TaskScheduler paramTaskScheduler)
    {
      super(paramTaskScheduler);
      this.packageId = paramString;
    }

    public void start()
    {
      super.start();
      Logger.i(PreLoadTaskModel.TAG, TASK_NAME + "  is start running");
      ServerApiTask localServerApiTask = ServerApiManager.i().ApiGetStaticCategoryItemList(this.packageId, 12, 50, 30, VideoListActivityV2.REQUEST_STATIC_CATEGORY_TYPE, true, this.expirationTime, new SccmsApiGetStaticCategoryItemListTask.ISccmsApiGetStaticCategoryItemListTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          PreLoadTaskModel.APIGetCategoryAndVideoList.this.setErrorCode(new ErrorCode(PreLoadTaskModel.APIGetCategoryAndVideoList.this.taskId, "ApiGetStaticCategoryItemList"));
          PreLoadTaskModel.APIGetCategoryAndVideoList.this.notifyError();
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, LinkedHashMap<String, CategoryPosterList> paramAnonymousLinkedHashMap, MediaAssetsInfo paramAnonymousMediaAssetsInfo)
        {
          PreLoadTaskModel.APIGetCategoryAndVideoList.this.notifyFinish();
        }
      });
      localServerApiTask.setIsUiSafe(false);
      this.taskId = localServerApiTask.getTaskId();
      localServerApiTask.setTaskPriority(0);
    }
  }

  public static class APIGetChannelListV2 extends PreLoadTaskModel.APITask
  {
    public static final String TASK_NAME = APIGetChannelListV2.class.getSimpleName();
    private String packageId;

    public APIGetChannelListV2(String paramString, TaskScheduler paramTaskScheduler)
    {
      super(paramTaskScheduler);
      this.packageId = paramString;
    }

    public void start()
    {
      super.start();
      Logger.i(PreLoadTaskModel.TAG, TASK_NAME + "  is start running");
      ServerApiTask localServerApiTask = ServerApiManager.i().APIGetChannelListV2(this.packageId, PreLoadTaskModel.categoryId, new SccmsApiGetChannelListV2Task.ISccmsApiGetChannelListV2TaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          PreLoadTaskModel.APIGetChannelListV2.this.setErrorCode(new ErrorCode(PreLoadTaskModel.APIGetChannelListV2.this.taskId, "APIGetChannelListV2"));
          PreLoadTaskModel.APIGetChannelListV2.this.notifyError();
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ChannelInfoV2 paramAnonymousChannelInfoV2)
        {
          PreLoadTaskModel.APIGetChannelListV2.this.notifyFinish();
        }
      }
      , true, this.expirationTime);
      localServerApiTask.setIsUiSafe(false);
      this.taskId = localServerApiTask.getTaskId();
      localServerApiTask.setTaskPriority(0);
    }
  }

  public static class APIGetMediaAssetsInfo extends PreLoadTaskModel.APITask
  {
    public static final String TASK_NAME = APIGetMediaAssetsInfo.class.getSimpleName();
    private String packageId;

    public APIGetMediaAssetsInfo(String paramString, TaskScheduler paramTaskScheduler)
    {
      super(paramTaskScheduler);
      this.packageId = paramString;
    }

    public void start()
    {
      super.start();
      Logger.i(PreLoadTaskModel.TAG, TASK_NAME + "  is start running");
      ServerApiTask localServerApiTask = ServerApiManager.i().APIGetMediaAssetsInfo(this.packageId, new SccmsApiGetMediaAssetsInfoTask.ISccmsApiGetMediaAssetsInfoTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          PreLoadTaskModel.APIGetMediaAssetsInfo.this.setErrorCode(new ErrorCode(PreLoadTaskModel.APIGetMediaAssetsInfo.this.taskId, "APIGetMediaAssetsInfo"));
          PreLoadTaskModel.APIGetMediaAssetsInfo.this.notifyError();
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, MediaAssetsInfo paramAnonymousMediaAssetsInfo)
        {
          if ((paramAnonymousMediaAssetsInfo == null) || (paramAnonymousMediaAssetsInfo.cList == null) || (paramAnonymousMediaAssetsInfo.cList.size() <= 0))
            return;
          PreLoadTaskModel.access$002(((CategoryItem)paramAnonymousMediaAssetsInfo.cList.get(0)).id);
          PreLoadTaskModel.APIGetMediaAssetsInfo.this.notifyFinish();
        }
      }
      , true, this.expirationTime);
      localServerApiTask.setIsUiSafe(false);
      this.taskId = localServerApiTask.getTaskId();
      localServerApiTask.setTaskPriority(0);
    }
  }

  public static class APIGetReplayRecommendData extends PreLoadTaskModel.APITask
  {
    public static final String TASK_NAME = PreLoadTaskModel.APIGetChannelListV2.class.getSimpleName();

    public APIGetReplayRecommendData(TaskScheduler paramTaskScheduler)
    {
      super(paramTaskScheduler);
    }

    public void start()
    {
      super.start();
      Logger.i(PreLoadTaskModel.TAG, TASK_NAME + "  is start running");
      ServerApiTask localServerApiTask = ServerApiManager.i().APIGetReplayRecommendData(6, 0, new SccmsApiGetReplayRecommendDataTask.ISccmsApiGetReplayRecommendDataTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          PreLoadTaskModel.APIGetReplayRecommendData.this.setErrorCode(new ErrorCode(PreLoadTaskModel.APIGetReplayRecommendData.this.taskId, "APIGetReplayRecommendData"));
          PreLoadTaskModel.APIGetReplayRecommendData.this.notifyError();
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, List<PlayBillRecommendStrut> paramAnonymousList)
        {
          PreLoadTaskModel.APIGetReplayRecommendData.this.notifyFinish();
        }
      }
      , true, this.expirationTime);
      localServerApiTask.setIsUiSafe(false);
      this.taskId = localServerApiTask.getTaskId();
      localServerApiTask.setTaskPriority(0);
    }
  }

  public static class APIGetSpecialTopicPutData extends PreLoadTaskModel.APITask
  {
    public static final String TASK_NAME = APIGetSpecialTopicPutData.class.getSimpleName();
    private String packageId;

    public APIGetSpecialTopicPutData(String paramString, TaskScheduler paramTaskScheduler)
    {
      super(paramTaskScheduler);
      this.packageId = paramString;
    }

    public void start()
    {
      super.start();
      Logger.i(PreLoadTaskModel.TAG, TASK_NAME + "  is start running");
      ServerApiTask localServerApiTask = ServerApiManager.i().APIGetSpecialTopicPutData(null, this.packageId, "asset", PreLoadTaskModel.categoryId, new SccmsApiGetSpecialTopicPutTask.ISccmsApiGetSpecialTopicPutTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          PreLoadTaskModel.APIGetSpecialTopicPutData.this.setErrorCode(new ErrorCode(PreLoadTaskModel.APIGetSpecialTopicPutData.this.taskId, "APIGetSpecialTopicPutData"));
          PreLoadTaskModel.APIGetSpecialTopicPutData.this.notifyError();
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ArrayList<SpecialTopicPutInfo> paramAnonymousArrayList)
        {
          PreLoadTaskModel.APIGetSpecialTopicPutData.this.notifyFinish();
        }
      }
      , true, this.expirationTime);
      localServerApiTask.setIsUiSafe(false);
      this.taskId = localServerApiTask.getTaskId();
      localServerApiTask.setTaskPriority(0);
    }
  }

  public static class APIGetVideoLabelType extends PreLoadTaskModel.APITask
  {
    public static final String TASK_NAME = APIGetVideoLabelType.class.getSimpleName();
    private String packageId;

    public APIGetVideoLabelType(String paramString, TaskScheduler paramTaskScheduler)
    {
      super(paramTaskScheduler);
      this.packageId = paramString;
    }

    public void start()
    {
      super.start();
      Logger.i(PreLoadTaskModel.TAG, TASK_NAME + "  is start running");
      ServerApiTask localServerApiTask = ServerApiManager.i().APIGetMediaAssetsBindLabelType(this.packageId, new SccmsApiGetMediaAssetsBindLabelTask.ISccmsApiGetMediaAssetsBindLabelTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          PreLoadTaskModel.APIGetVideoLabelType.this.setErrorCode(new ErrorCode(PreLoadTaskModel.APIGetVideoLabelType.this.taskId, "APIGetMediaAssetsBindLabelType"));
          PreLoadTaskModel.APIGetVideoLabelType.this.notifyError();
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, byte[] paramAnonymousArrayOfByte)
        {
          PreLoadTaskModel.APIGetVideoLabelType.this.notifyFinish();
        }
      }
      , true, this.expirationTime);
      localServerApiTask.setIsUiSafe(false);
      this.taskId = localServerApiTask.getTaskId();
      localServerApiTask.setTaskPriority(0);
    }
  }

  public static class APIGetVideoList extends PreLoadTaskModel.APITask
  {
    public static final String TASK_NAME = APIGetVideoList.class.getSimpleName();
    private String packageId;

    public APIGetVideoList(String paramString, TaskScheduler paramTaskScheduler)
    {
      super(paramTaskScheduler);
      this.packageId = paramString;
    }

    public void start()
    {
      super.start();
      Logger.i(PreLoadTaskModel.TAG, TASK_NAME + "  is start running");
      ServerApiTask localServerApiTask = ServerApiManager.i().APIGetVideoList(this.packageId, PreLoadTaskModel.categoryId, "time", 0, 60, new SccmsApiGetVideoListTask.ISccmsApiGetVideoListTaskListener()
      {
        public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
        {
          PreLoadTaskModel.APIGetVideoList.this.setErrorCode(new ErrorCode(PreLoadTaskModel.APIGetVideoList.this.taskId, "APIGetVideoList"));
          PreLoadTaskModel.APIGetVideoList.this.notifyError();
        }

        public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, FilmItem paramAnonymousFilmItem)
        {
          PreLoadTaskModel.APIGetVideoList.this.notifyFinish();
        }
      }
      , true, this.expirationTime);
      localServerApiTask.setIsUiSafe(false);
      this.taskId = localServerApiTask.getTaskId();
      localServerApiTask.setTaskPriority(0);
    }
  }

  public static abstract class APITask extends TaskScheduler.Task
  {
    private ErrorCode errorCode;
    protected Long expirationTime;
    public int taskId = -1;

    public APITask(String paramString, TaskScheduler paramTaskScheduler)
    {
      super(paramTaskScheduler);
    }

    public ErrorCode errorCode()
    {
      return this.errorCode;
    }

    public Long getExpirationTime()
    {
      return this.expirationTime;
    }

    public void notifyError()
    {
      if (this.taskId == -1)
      {
        Logger.e(PreLoadTaskModel.TAG, "notifyError taskId == -1 taskName = " + taskId());
        return;
      }
      super.notifyError();
    }

    public void notifyFinish()
    {
      if (this.taskId == -1)
      {
        Logger.e(PreLoadTaskModel.TAG, "notifyError taskId == -1 taskName = " + taskId());
        return;
      }
      super.notifyFinish();
    }

    public void setErrorCode(ErrorCode paramErrorCode)
    {
      this.errorCode = paramErrorCode;
    }

    public void setExpirationTime(Long paramLong)
    {
      this.expirationTime = paramLong;
    }

    public void start()
    {
      notifyRunning();
    }

    public void stop()
    {
      this.taskId = -1;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.service.preload.PreLoadTaskModel
 * JD-Core Version:    0.6.2
 */