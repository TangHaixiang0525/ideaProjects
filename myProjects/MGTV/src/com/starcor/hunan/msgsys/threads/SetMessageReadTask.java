package com.starcor.hunan.msgsys.threads;

import android.content.ContentValues;
import android.net.Uri;
import android.text.TextUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.db.AdminTopicMessageColumns;
import com.starcor.hunan.db.DBProvider;
import com.starcor.hunan.db.MessageSystemV3Columns;
import com.starcor.hunan.msgsys.interfaces.AbstractSubTask;
import com.starcor.hunan.msgsys.interfaces.IMQTTMessageDBUpdater.TopicTableUpdateActionType;
import java.util.HashMap;
import java.util.List;

public class SetMessageReadTask extends AbstractSubTask
{
  private String mMsgId = null;

  public SetMessageReadTask(String paramString)
  {
    super(SetMessageReadTask.class.getSimpleName());
    this.mMsgId = paramString;
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_PUBLIC_TOPIC_MESSAGE_READ);
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_PRIVATE_TOPIC_MESSAGE_READ);
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_RESERVE_TOPIC_MESSAGE_READ);
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_ADMIN_TOPIC_MESSAGE_READ);
    this.mSelf = this;
  }

  private void cleanUp()
  {
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_PUBLIC_TOPIC_MESSAGE_READ);
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_PRIVATE_TOPIC_MESSAGE_READ);
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_RESERVE_TOPIC_MESSAGE_READ);
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_ADMIN_TOPIC_MESSAGE_READ);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_PUBLIC_TOPIC_MESSAGE_READ);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_PRIVATE_TOPIC_MESSAGE_READ);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_RESERVE_TOPIC_MESSAGE_READ);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_ADMIN_TOPIC_MESSAGE_READ);
  }

  public void doRunTask()
  {
    Logger.i(this.TAG, "设置" + this.mUri + " 中某一条消息数据为已读！" + this.mMsgId);
    ContentValues localContentValues = new ContentValues();
    String str1 = getTopic();
    String str2;
    String[] arrayOfString;
    if (!TextUtils.isEmpty(str1))
    {
      str2 = "message_id=? AND topic=?";
      arrayOfString = new String[2];
      arrayOfString[0] = ("" + this.mMsgId);
      arrayOfString[1] = ("" + str1);
    }
    while (true)
    {
      localContentValues.put("unread", Integer.valueOf(0));
      int i = this.mDbProvider.update(this.mUri, localContentValues, str2, arrayOfString);
      Logger.i(this.TAG, "设置" + str1 + "类型的消息" + i + "条为已读！");
      cleanUp();
      return;
      str2 = "message_id=?";
      arrayOfString = new String[1];
      arrayOfString[0] = ("" + this.mMsgId);
    }
  }

  public String getTopic()
  {
    String str = "";
    if (IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_PUBLIC_TOPIC_MESSAGE_READ.equals(this.mType))
      str = "public";
    do
    {
      return str;
      if (IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_PRIVATE_TOPIC_MESSAGE_READ.equals(this.mType))
        return "private";
      if (IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_RESERVE_TOPIC_MESSAGE_READ.equals(this.mType))
        return "reserve";
    }
    while (!IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_ADMIN_TOPIC_MESSAGE_READ.equals(this.mType));
    return "admin";
  }

  public void initUriMap()
  {
    Uri localUri1 = MessageSystemV3Columns.getUri();
    Uri localUri2 = AdminTopicMessageColumns.getUri();
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_PUBLIC_TOPIC_MESSAGE_READ, localUri1);
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_PRIVATE_TOPIC_MESSAGE_READ, localUri1);
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_RESERVE_TOPIC_MESSAGE_READ, localUri1);
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_ADMIN_TOPIC_MESSAGE_READ, localUri2);
  }

  public void runSubTask(IMQTTMessageDBUpdater.TopicTableUpdateActionType paramTopicTableUpdateActionType)
  {
    this.mUri = ((Uri)this.mUriMap.get(paramTopicTableUpdateActionType));
    this.mType = paramTopicTableUpdateActionType;
    this.mSelf.start();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.threads.SetMessageReadTask
 * JD-Core Version:    0.6.2
 */