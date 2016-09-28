package com.starcor.hunan.service.preload;

import com.starcor.service.TaskScheduler;
import com.starcor.service.TaskScheduler.Task;

public class ReplayTaskGroup extends TaskGroup
{
  private final String packageId;

  public ReplayTaskGroup(String paramString)
  {
    this.packageId = paramString;
  }

  protected void initTasks()
  {
    this.mScheduler.clearTasks();
    PreLoadTaskModel.APIGetMediaAssetsInfo localAPIGetMediaAssetsInfo = new PreLoadTaskModel.APIGetMediaAssetsInfo(this.packageId, this.mScheduler);
    localAPIGetMediaAssetsInfo.setExpirationTime(Long.valueOf(getExpirationTime()));
    this.mScheduler.addTask(localAPIGetMediaAssetsInfo);
    PreLoadTaskModel.APIGetChannelListV2 localAPIGetChannelListV2 = new PreLoadTaskModel.APIGetChannelListV2(this.packageId, this.mScheduler);
    this.mScheduler.addTask(localAPIGetChannelListV2);
    TaskScheduler localTaskScheduler1 = this.mScheduler;
    TaskScheduler.Task localTask1 = this.mFinishedTask;
    String[] arrayOfString1 = new String[1];
    arrayOfString1[0] = PreLoadTaskModel.APIGetMediaAssetsInfo.TASK_NAME;
    localTaskScheduler1.addTaskDependencis(localTask1, arrayOfString1);
    PreLoadTaskModel.APIGetReplayRecommendData localAPIGetReplayRecommendData = new PreLoadTaskModel.APIGetReplayRecommendData(this.mScheduler);
    this.mScheduler.addTask(localAPIGetReplayRecommendData);
    this.mScheduler.addTask(this.mFinishedTask);
    TaskScheduler localTaskScheduler2 = this.mScheduler;
    TaskScheduler.Task localTask2 = this.mFinishedTask;
    String[] arrayOfString2 = new String[2];
    arrayOfString2[0] = PreLoadTaskModel.APIGetChannelListV2.TASK_NAME;
    arrayOfString2[1] = PreLoadTaskModel.APIGetReplayRecommendData.TASK_NAME;
    localTaskScheduler2.addTaskDependencis(localTask2, arrayOfString2);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.service.preload.ReplayTaskGroup
 * JD-Core Version:    0.6.2
 */