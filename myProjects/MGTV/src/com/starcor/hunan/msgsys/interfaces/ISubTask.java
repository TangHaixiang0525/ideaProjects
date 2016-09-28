package com.starcor.hunan.msgsys.interfaces;

import com.starcor.hunan.db.DBProvider;
import java.util.List;

public abstract interface ISubTask
{
  public abstract List<IMQTTMessageDBUpdater.TopicTableUpdateActionType> getFilters();

  public abstract void runTask(IMQTTMessageDBUpdater.TopicTableUpdateActionType paramTopicTableUpdateActionType);

  public abstract void setParams(DBProvider paramDBProvider, AbstractMQTTUIUpdateNotifier paramAbstractMQTTUIUpdateNotifier);

  public abstract void setTask(ISubTask paramISubTask);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.interfaces.ISubTask
 * JD-Core Version:    0.6.2
 */