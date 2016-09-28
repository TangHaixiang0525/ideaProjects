package com.starcor.hunan.msgsys.interfaces;

import android.net.Uri;
import com.starcor.hunan.db.DBProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractSubTask extends Thread
  implements ISubTask
{
  protected String TAG = null;
  protected DBProvider mDbProvider = null;
  protected List<IMQTTMessageDBUpdater.TopicTableUpdateActionType> mFilters = new ArrayList();
  protected AbstractMQTTUIUpdateNotifier mNotifier = null;
  protected AbstractSubTask mSelf = null;
  private ISubTask mTask = null;
  protected IMQTTMessageDBUpdater.TopicTableUpdateActionType mType = null;
  protected Uri mUri = null;
  protected final HashMap<IMQTTMessageDBUpdater.TopicTableUpdateActionType, Uri> mUriMap = new HashMap();

  public AbstractSubTask(String paramString)
  {
    this.TAG = paramString;
  }

  public abstract void doRunTask();

  public List<IMQTTMessageDBUpdater.TopicTableUpdateActionType> getFilters()
  {
    return this.mFilters;
  }

  public abstract String getTopic();

  public abstract void initUriMap();

  public void run()
  {
    try
    {
      doRunTask();
      this.mDbProvider = null;
      this.mNotifier = null;
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public abstract void runSubTask(IMQTTMessageDBUpdater.TopicTableUpdateActionType paramTopicTableUpdateActionType)
    throws Exception;

  public void runTask(IMQTTMessageDBUpdater.TopicTableUpdateActionType paramTopicTableUpdateActionType)
  {
    if (this.mFilters.contains(paramTopicTableUpdateActionType))
      initUriMap();
    while (this.mTask == null)
      try
      {
        runSubTask(paramTopicTableUpdateActionType);
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
    this.mTask.runTask(paramTopicTableUpdateActionType);
  }

  public void setParams(DBProvider paramDBProvider, AbstractMQTTUIUpdateNotifier paramAbstractMQTTUIUpdateNotifier)
  {
    this.mDbProvider = paramDBProvider;
    this.mNotifier = paramAbstractMQTTUIUpdateNotifier;
  }

  public void setTask(ISubTask paramISubTask)
  {
    this.mTask = paramISubTask;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.interfaces.AbstractSubTask
 * JD-Core Version:    0.6.2
 */