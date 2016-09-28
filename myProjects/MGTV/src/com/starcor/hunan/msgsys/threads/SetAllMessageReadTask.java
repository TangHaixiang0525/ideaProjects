package com.starcor.hunan.msgsys.threads;

import android.content.ContentValues;
import android.net.Uri;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.db.AdminTopicMessageColumns;
import com.starcor.hunan.db.DBProvider;
import com.starcor.hunan.db.MessageSystemV3Columns;
import com.starcor.hunan.msgsys.interfaces.AbstractSubTask;
import com.starcor.hunan.msgsys.interfaces.IMQTTMessageDBUpdater.TopicTableUpdateActionType;
import java.util.HashMap;
import java.util.List;

public class SetAllMessageReadTask extends AbstractSubTask
{
  public SetAllMessageReadTask()
  {
    super(SetAllMessageReadTask.class.getSimpleName());
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_ALL_SYSTEM_MESSAGE_READ);
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_ALL_MY_MESSAGE_READ);
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_ALL_ADMIN_TOPIC_MESSAGE_READ);
    this.mSelf = this;
  }

  private void cleanUp()
  {
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_ALL_ADMIN_TOPIC_MESSAGE_READ);
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_ALL_SYSTEM_MESSAGE_READ);
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_ALL_MY_MESSAGE_READ);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_ALL_ADMIN_TOPIC_MESSAGE_READ);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_ALL_SYSTEM_MESSAGE_READ);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_ALL_MY_MESSAGE_READ);
  }

  public void doRunTask()
  {
    Logger.i(this.TAG, "设置" + this.mUri + " 中的所有消息数据为已读！");
    String str;
    String[] arrayOfString;
    if (IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_ALL_SYSTEM_MESSAGE_READ.equals(this.mType))
    {
      str = "topic=? OR topic=?";
      arrayOfString = new String[2];
      arrayOfString[0] = "public";
      arrayOfString[1] = "private";
    }
    while (true)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("unread", Integer.valueOf(0));
      int i = this.mDbProvider.update(this.mUri, localContentValues, str, arrayOfString);
      Logger.i(this.TAG, "设置" + i + "条" + getTopic() + "类型的消息数据为已读！");
      cleanUp();
      return;
      boolean bool = IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_ALL_MY_MESSAGE_READ.equals(this.mType);
      str = null;
      arrayOfString = null;
      if (bool)
      {
        str = "topic=?";
        arrayOfString = new String[] { "reserve" };
      }
    }
  }

  public String getTopic()
  {
    String str = "";
    if (IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_ALL_SYSTEM_MESSAGE_READ.equals(this.mType))
      str = "sys_msg";
    do
    {
      return str;
      if (IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_ALL_MY_MESSAGE_READ.equals(this.mType))
        return "my_msg";
    }
    while (!IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_ALL_ADMIN_TOPIC_MESSAGE_READ.equals(this.mType));
    return "admin";
  }

  public void initUriMap()
  {
    Uri localUri1 = MessageSystemV3Columns.getUri();
    Uri localUri2 = AdminTopicMessageColumns.getUri();
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_ALL_SYSTEM_MESSAGE_READ, localUri1);
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_ALL_MY_MESSAGE_READ, localUri1);
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_ALL_ADMIN_TOPIC_MESSAGE_READ, localUri2);
  }

  public void runSubTask(IMQTTMessageDBUpdater.TopicTableUpdateActionType paramTopicTableUpdateActionType)
  {
    this.mUri = ((Uri)this.mUriMap.get(paramTopicTableUpdateActionType));
    this.mType = paramTopicTableUpdateActionType;
    this.mSelf.start();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.threads.SetAllMessageReadTask
 * JD-Core Version:    0.6.2
 */