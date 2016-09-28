package com.starcor.hunan.service.preload;

import com.starcor.service.TaskScheduler;
import com.starcor.service.TaskScheduler.Task;

public class MediaAssetsTaskGroup extends TaskGroup
{
  private final String packageId;

  public MediaAssetsTaskGroup(String paramString)
  {
    this.packageId = paramString;
  }

  protected void initTasks()
  {
    this.mScheduler.clearTasks();
    PreLoadTaskModel.APIGetCategoryAndVideoList localAPIGetCategoryAndVideoList = new PreLoadTaskModel.APIGetCategoryAndVideoList(this.packageId, this.mScheduler);
    localAPIGetCategoryAndVideoList.setExpirationTime(Long.valueOf(getExpirationTime()));
    this.mScheduler.addTask(localAPIGetCategoryAndVideoList);
    PreLoadTaskModel.APIGetVideoLabelType localAPIGetVideoLabelType = new PreLoadTaskModel.APIGetVideoLabelType(this.packageId, this.mScheduler);
    localAPIGetVideoLabelType.setExpirationTime(Long.valueOf(getExpirationTime()));
    this.mScheduler.addTask(localAPIGetVideoLabelType);
    PreLoadTaskModel.APIGetVideoList localAPIGetVideoList = new PreLoadTaskModel.APIGetVideoList(this.packageId, this.mScheduler);
    localAPIGetVideoList.setExpirationTime(Long.valueOf(getExpirationTime()));
    this.mScheduler.addTask(localAPIGetVideoList);
    this.mScheduler.addTask(this.mFinishedTask);
    TaskScheduler localTaskScheduler = this.mScheduler;
    TaskScheduler.Task localTask = this.mFinishedTask;
    String[] arrayOfString = new String[3];
    arrayOfString[0] = PreLoadTaskModel.APIGetCategoryAndVideoList.TASK_NAME;
    arrayOfString[1] = PreLoadTaskModel.APIGetVideoLabelType.TASK_NAME;
    arrayOfString[2] = PreLoadTaskModel.APIGetVideoList.TASK_NAME;
    localTaskScheduler.addTaskDependencis(localTask, arrayOfString);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.service.preload.MediaAssetsTaskGroup
 * JD-Core Version:    0.6.2
 */