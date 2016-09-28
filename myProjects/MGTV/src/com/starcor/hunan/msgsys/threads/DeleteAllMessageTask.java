package com.starcor.hunan.msgsys.threads;

import android.net.Uri;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.db.AdminTopicMessageColumns;
import com.starcor.hunan.db.DBProvider;
import com.starcor.hunan.db.MessageSystemV3Columns;
import com.starcor.hunan.msgsys.interfaces.AbstractSubTask;
import com.starcor.hunan.msgsys.interfaces.IMQTTMessageDBUpdater.TopicTableUpdateActionType;
import java.util.HashMap;
import java.util.List;

public class DeleteAllMessageTask extends AbstractSubTask
{
  public DeleteAllMessageTask()
  {
    super(DeleteAllMessageTask.class.getSimpleName());
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_SYSTEM_MESSAGE);
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_MY_MESSAGE);
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_ADMIN_TOPIC_MESSAGE);
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_PRIVATE_TOPIC_MESSAGE);
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_PUBLIC_TOPIC_MESSAGE);
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_TOPIC_MESSAGE);
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_RESERVE_TOPIC_MESSAGE);
    this.mSelf = this;
  }

  private void cleanUp()
  {
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_ADMIN_TOPIC_MESSAGE);
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_PRIVATE_TOPIC_MESSAGE);
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_PUBLIC_TOPIC_MESSAGE);
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_TOPIC_MESSAGE);
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_RESERVE_TOPIC_MESSAGE);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_ADMIN_TOPIC_MESSAGE);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_PRIVATE_TOPIC_MESSAGE);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_PUBLIC_TOPIC_MESSAGE);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_TOPIC_MESSAGE);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_RESERVE_TOPIC_MESSAGE);
  }

  public void doRunTask()
  {
    String str1;
    String[] arrayOfString;
    if (IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_SYSTEM_MESSAGE.equals(this.mType))
    {
      str1 = "topic=? OR topic=?";
      arrayOfString = new String[2];
      arrayOfString[0] = "public";
      arrayOfString[1] = "private";
      if (this.mType == null)
        break label178;
    }
    label178: for (String str2 = this.mType.name(); ; str2 = "")
    {
      Logger.i(this.TAG, "删除 " + this.mUri + " 中的所有关于 " + str2 + " 的主题信息数据！");
      int i = this.mDbProvider.delete(this.mUri, str1, arrayOfString);
      Logger.i(this.TAG, "共删除" + i + "条消息记录！");
      cleanUp();
      return;
      boolean bool = IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_MY_MESSAGE.equals(this.mType);
      str1 = null;
      arrayOfString = null;
      if (!bool)
        break;
      str1 = "topic=?";
      arrayOfString = new String[] { "reserve" };
      break;
    }
  }

  public String getTopic()
  {
    String str = "";
    if (IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_PUBLIC_TOPIC_MESSAGE.equals(this.mType))
      str = "public";
    do
    {
      return str;
      if (IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_PRIVATE_TOPIC_MESSAGE.equals(this.mType))
        return "private";
      if (IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_RESERVE_TOPIC_MESSAGE.equals(this.mType))
        return "reserve";
    }
    while (!IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_TOPIC_MESSAGE.equals(this.mType));
    return "all_topic";
  }

  public void initUriMap()
  {
    Uri localUri1 = AdminTopicMessageColumns.getUri();
    Uri localUri2 = MessageSystemV3Columns.getUri();
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_SYSTEM_MESSAGE, localUri2);
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_MY_MESSAGE, localUri2);
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_ADMIN_TOPIC_MESSAGE, localUri1);
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_PRIVATE_TOPIC_MESSAGE, localUri2);
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_PUBLIC_TOPIC_MESSAGE, localUri2);
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_TOPIC_MESSAGE, localUri2);
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ALL_RESERVE_TOPIC_MESSAGE, localUri2);
  }

  public void runSubTask(IMQTTMessageDBUpdater.TopicTableUpdateActionType paramTopicTableUpdateActionType)
  {
    this.mUri = ((Uri)this.mUriMap.get(paramTopicTableUpdateActionType));
    this.mType = paramTopicTableUpdateActionType;
    this.mSelf.start();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.threads.DeleteAllMessageTask
 * JD-Core Version:    0.6.2
 */