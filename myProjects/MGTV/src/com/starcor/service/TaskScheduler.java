package com.starcor.service;

import com.starcor.core.utils.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class TaskScheduler
{
  private static final String TAG = TaskScheduler.class.getSimpleName();
  private boolean isErrorOcurred = false;
  private Map<String, TaskInfo> taskMap;

  private ArrayList<Task> findReadyTask()
  {
    Object localObject;
    if ((this.taskMap == null) || (this.taskMap.size() <= 0))
      localObject = null;
    while (true)
    {
      return localObject;
      localObject = new ArrayList();
      Iterator localIterator = this.taskMap.entrySet().iterator();
      while (localIterator.hasNext())
      {
        TaskInfo localTaskInfo = (TaskInfo)((Map.Entry)localIterator.next()).getValue();
        if (localTaskInfo.isReady())
          ((ArrayList)localObject).add(localTaskInfo.task);
      }
    }
  }

  private void onError(Task paramTask)
  {
    Logger.e(TAG, "TaskScheduler received a error has occured !!!");
    if (!this.isErrorOcurred)
    {
      onTaskError(paramTask);
      this.isErrorOcurred = true;
      ArrayList localArrayList = findReadyTask();
      int i = localArrayList.size();
      for (int j = 0; j < i; j++)
        ((Task)localArrayList.get(j)).stop();
      Logger.e(TAG, "TaskScheduler process Error of " + paramTask.taskId());
      return;
    }
    Logger.e(TAG, "TaskScheduler ingore Error of " + paramTask.taskId());
  }

  private void onFinish(Task paramTask)
  {
    Logger.d(TAG, "TaskScheduler received a task has finish !!! taskName -- >" + paramTask.taskId());
    runNextTasks();
  }

  private boolean runNextTasks()
  {
    ArrayList localArrayList = findReadyTask();
    if ((localArrayList == null) || (localArrayList.size() <= 0))
    {
      Logger.d(TAG, "There is has no task in Scheduler !!!");
      return false;
    }
    int i = localArrayList.size();
    for (int j = 0; j < i; j++)
      ((Task)localArrayList.get(j)).start();
    return true;
  }

  public void addTask(Task paramTask)
  {
    if (this.taskMap == null)
      this.taskMap = new HashMap();
    this.taskMap.put(paramTask.taskId(), new TaskInfo(paramTask));
  }

  public boolean addTaskDependencis(Task paramTask, String[] paramArrayOfString)
  {
    if (this.taskMap == null);
    TaskInfo localTaskInfo1;
    do
    {
      return false;
      localTaskInfo1 = (TaskInfo)this.taskMap.get(paramTask.taskId());
    }
    while (localTaskInfo1 == null);
    int i = paramArrayOfString.length;
    int j = 0;
    if (j < i)
    {
      String str = paramArrayOfString[j];
      TaskInfo localTaskInfo2 = (TaskInfo)this.taskMap.get(str);
      if (localTaskInfo2 == null);
      while (true)
      {
        j++;
        break;
        localTaskInfo1.addDependency(localTaskInfo2);
      }
    }
    return true;
  }

  public void clearTasks()
  {
    if (this.taskMap != null)
      this.taskMap.clear();
  }

  public void onTaskError(Task paramTask)
  {
  }

  public void runTask()
  {
    runNextTasks();
  }

  public static abstract class Task
  {
    final String _taskId;
    private boolean isError = false;
    private boolean isFinish = false;
    private boolean isRunning = false;
    final TaskScheduler scheduler;

    public Task(String paramString, TaskScheduler paramTaskScheduler)
    {
      this._taskId = paramString;
      this.scheduler = paramTaskScheduler;
    }

    public boolean isError()
    {
      return this.isError;
    }

    public boolean isFinish()
    {
      return this.isFinish;
    }

    public boolean isRunning()
    {
      return this.isRunning;
    }

    public void notifyError()
    {
      this.isError = true;
      this.isRunning = false;
      this.scheduler.onError(this);
    }

    public void notifyFinish()
    {
      this.isFinish = true;
      this.isRunning = false;
      this.scheduler.onFinish(this);
    }

    public void notifyRunning()
    {
      this.isRunning = true;
    }

    public abstract void start();

    public abstract void stop();

    public String taskId()
    {
      return this._taskId;
    }
  }

  private class TaskInfo
  {
    private ArrayList<TaskInfo> dependencies;
    public final TaskScheduler.Task task;

    public TaskInfo(TaskScheduler.Task arg2)
    {
      Object localObject;
      this.task = localObject;
    }

    public void addDependency(TaskInfo paramTaskInfo)
    {
      if (this.dependencies == null)
        this.dependencies = new ArrayList();
      this.dependencies.add(paramTaskInfo);
    }

    public boolean isReady()
    {
      if ((this.task.isFinish()) || (this.task.isRunning()) || (this.task.isError()))
        return false;
      if ((this.dependencies == null) || (this.dependencies.isEmpty()))
        return true;
      int i = this.dependencies.size();
      for (int j = 0; ; j++)
      {
        if (j >= i)
          break label104;
        TaskInfo localTaskInfo = (TaskInfo)this.dependencies.get(j);
        if ((localTaskInfo.task.isError()) || (!localTaskInfo.task.isFinish()))
          break;
      }
      label104: return true;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.service.TaskScheduler
 * JD-Core Version:    0.6.2
 */