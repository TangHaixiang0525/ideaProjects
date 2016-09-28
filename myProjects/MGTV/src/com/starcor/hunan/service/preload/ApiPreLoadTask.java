package com.starcor.hunan.service.preload;

import com.starcor.core.utils.Logger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApiPreLoadTask extends TimerTask
{
  protected static final String TAG = ApiPreLoadTask.class.getSimpleName();
  public static final ExecutorService threadPool = Executors.newSingleThreadExecutor();
  private List<TaskGroup> tasks = new ArrayList();

  public void addTask(TaskGroup paramTaskGroup)
  {
    if (!this.tasks.contains(paramTaskGroup))
      this.tasks.add(paramTaskGroup);
  }

  public List<TaskGroup> getTasks()
  {
    return this.tasks;
  }

  public boolean removeTask(TaskGroup paramTaskGroup)
  {
    return this.tasks.remove(paramTaskGroup);
  }

  public void run()
  {
    Logger.i(TAG, "ApiPreLoadTask running");
    Iterator localIterator = this.tasks.iterator();
    while (localIterator.hasNext())
    {
      TaskGroup localTaskGroup = (TaskGroup)localIterator.next();
      threadPool.execute(localTaskGroup);
    }
  }

  public void setTasks(List<TaskGroup> paramList)
  {
    this.tasks = paramList;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.service.preload.ApiPreLoadTask
 * JD-Core Version:    0.6.2
 */