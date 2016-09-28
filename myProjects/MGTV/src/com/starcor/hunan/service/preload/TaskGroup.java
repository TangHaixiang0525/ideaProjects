package com.starcor.hunan.service.preload;

import com.starcor.core.utils.Logger;
import com.starcor.service.TaskScheduler;
import com.starcor.service.TaskScheduler.Task;

public abstract class TaskGroup
  implements Runnable
{
  private static final long PROTECTION_TIME = 600000L;
  protected static final String TAG = TaskGroup.class.getSimpleName();
  private String clazz = getClass().getSimpleName();
  private long expirationTime = 7200000L;
  private boolean init;
  private long lastCompletedTime;
  protected TaskScheduler.Task mFinishedTask = new TaskScheduler.Task("FINISH_TASK", this.mScheduler)
  {
    public void start()
    {
      Logger.i(TaskGroup.TAG, TaskGroup.this.clazz + ",onCompleted by mFinishedTask");
      notifyRunning();
      if (TaskGroup.this.mTaskListener != null)
        TaskGroup.this.mTaskListener.onCompleted();
      TaskGroup.access$302(TaskGroup.this, System.currentTimeMillis());
      TaskGroup.access$102(TaskGroup.this, false);
      notifyFinish();
    }

    public void stop()
    {
    }
  };
  protected TaskScheduler mScheduler = new TaskScheduler()
  {
    public void onTaskError(TaskScheduler.Task paramAnonymousTask)
    {
      Logger.w(TaskGroup.TAG, "onError by TaskScheduler");
      if (TaskGroup.this.mTaskListener != null)
      {
        TaskGroup.this.mTaskListener.onError(paramAnonymousTask);
        TaskGroup.access$102(TaskGroup.this, false);
      }
    }
  };
  private TaskListener mTaskListener;
  protected long offsetTime = 60000L;
  private boolean running;

  public long getExpirationTime()
  {
    return this.expirationTime;
  }

  public long getLastCompletedTime()
  {
    return this.lastCompletedTime;
  }

  public TaskListener getTaskListener()
  {
    return this.mTaskListener;
  }

  protected abstract void initTasks();

  public boolean isExpiration()
  {
    return System.currentTimeMillis() - this.expirationTime + this.offsetTime > this.lastCompletedTime;
  }

  public boolean isRunning()
  {
    return this.running;
  }

  public void run()
  {
    if ((this.running) || (!isExpiration()))
    {
      Logger.i(TAG, "run() running=" + this.running + ",isExpiration=" + isExpiration());
      return;
    }
    if (!this.init)
    {
      initTasks();
      this.init = true;
      Logger.i(TAG, "run() init Tasks");
    }
    Logger.i(TAG, getClass().getSimpleName() + " run");
    this.mScheduler.runTask();
    this.running = true;
  }

  public void setExpirationTime(long paramLong)
  {
    if (paramLong < 600000L)
    {
      Logger.e(TAG, "setExpirationTime() expirationTime too small param(expirationTime=" + paramLong + ")");
      return;
    }
    this.expirationTime = paramLong;
  }

  public void setTaskListener(TaskListener paramTaskListener)
  {
    this.mTaskListener = paramTaskListener;
  }

  public static abstract interface TaskListener
  {
    public abstract void onCompleted();

    public abstract void onError(TaskScheduler.Task paramTask);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.service.preload.TaskGroup
 * JD-Core Version:    0.6.2
 */