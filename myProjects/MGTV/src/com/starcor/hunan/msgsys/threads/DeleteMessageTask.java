package com.starcor.hunan.msgsys.threads;

import android.net.Uri;
import android.text.TextUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.db.AdminTopicMessageColumns;
import com.starcor.hunan.db.DBProvider;
import com.starcor.hunan.db.MessageSystemV3Columns;
import com.starcor.hunan.msgsys.interfaces.AbstractMQTTUIUpdateNotifier;
import com.starcor.hunan.msgsys.interfaces.AbstractSubTask;
import com.starcor.hunan.msgsys.interfaces.IMQTTMessageDBUpdater.TopicTableUpdateActionType;
import java.util.HashMap;
import java.util.List;

public class DeleteMessageTask extends AbstractSubTask
{
  private String mMsgId = null;

  public DeleteMessageTask(String paramString)
  {
    super(DeleteMessageTask.class.getSimpleName());
    this.mMsgId = paramString;
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ADMIN_TOPIC_MESSAGE);
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_PUBLIC_TOPIC_MESSAGE);
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_PRIVATE_TOPIC_MESSAGE);
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_RESERVE_TOPIC_MESSAGE);
    this.mSelf = this;
  }

  private void cleanUp()
  {
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ADMIN_TOPIC_MESSAGE);
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_PRIVATE_TOPIC_MESSAGE);
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_PUBLIC_TOPIC_MESSAGE);
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_RESERVE_TOPIC_MESSAGE);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ADMIN_TOPIC_MESSAGE);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_PUBLIC_TOPIC_MESSAGE);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_PRIVATE_TOPIC_MESSAGE);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_RESERVE_TOPIC_MESSAGE);
  }

  private boolean deleteCommonMessage(String paramString)
  {
    String str1 = "message_id=?";
    String str2 = getTopic();
    String[] arrayOfString;
    int i;
    if (!TextUtils.isEmpty(str2))
    {
      str1 = str1 + " AND topic=?";
      arrayOfString = new String[2];
      arrayOfString[0] = ("" + paramString);
      arrayOfString[1] = ("" + str2);
      i = this.mDbProvider.delete(this.mUri, str1, arrayOfString);
      Logger.i(this.TAG, "共删除" + i + "条数据");
      if ((i <= 0) || (this.mNotifier == null))
        break label193;
      this.mNotifier.finishDeleteMessage(str2, this.mMsgId);
    }
    label193: 
    while (i > 0)
    {
      return true;
      arrayOfString = new String[1];
      arrayOfString[0] = ("" + paramString);
      break;
    }
    return false;
  }

  public void doRunTask()
  {
    Logger.i(this.TAG, "删除 " + this.mUri + " 中某一条数据：" + this.mMsgId);
    if (this.mDbProvider != null)
      deleteCommonMessage(this.mMsgId);
    cleanUp();
  }

  public String getTopic()
  {
    String str = "";
    if (IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_PUBLIC_TOPIC_MESSAGE.equals(this.mType))
      str = "public";
    do
    {
      return str;
      if (IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_PRIVATE_TOPIC_MESSAGE.equals(this.mType))
        return "private";
      if (IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_RESERVE_TOPIC_MESSAGE.equals(this.mType))
        return "reserve";
    }
    while (!IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ADMIN_TOPIC_MESSAGE.equals(this.mType));
    return "admin";
  }

  public void initUriMap()
  {
    Uri localUri1 = AdminTopicMessageColumns.getUri();
    Uri localUri2 = MessageSystemV3Columns.getUri();
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_ADMIN_TOPIC_MESSAGE, localUri1);
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_PRIVATE_TOPIC_MESSAGE, localUri2);
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_PUBLIC_TOPIC_MESSAGE, localUri2);
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.DELETE_RESERVE_TOPIC_MESSAGE, localUri2);
  }

  public void runSubTask(IMQTTMessageDBUpdater.TopicTableUpdateActionType paramTopicTableUpdateActionType)
  {
    this.mUri = ((Uri)this.mUriMap.get(paramTopicTableUpdateActionType));
    this.mType = paramTopicTableUpdateActionType;
    this.mSelf.run();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.threads.DeleteMessageTask
 * JD-Core Version:    0.6.2
 */