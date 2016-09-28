package com.starcor.hunan.msgsys.threads;

import android.database.Cursor;
import android.net.Uri;
import com.starcor.hunan.db.DBProvider;
import com.starcor.hunan.db.MessageSystemV3Columns;
import com.starcor.hunan.db.PrivateTopicMessageColumns;
import com.starcor.hunan.db.PublicTopicMessageColumns;
import com.starcor.hunan.msgsys.interfaces.AbstractMQTTUIUpdateNotifier;
import com.starcor.hunan.msgsys.interfaces.AbstractSubTask;
import com.starcor.hunan.msgsys.interfaces.IMQTTMessageDBUpdater.TopicTableUpdateActionType;
import java.util.HashMap;
import java.util.List;

public class ReadTotalUnreadMsgNumTask extends AbstractSubTask
{
  private int mUnreadMyMsgNum = 0;
  private int mUnreadSysMsgNum = 0;

  public ReadTotalUnreadMsgNumTask()
  {
    super(ReadTotalUnreadMsgNumTask.class.getSimpleName());
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.READ_ALL_UNREAD_MESSAGE_TOTAL_NUMBER);
    this.mSelf = this;
  }

  private void cleanUp()
  {
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.READ_ALL_UNREAD_MESSAGE_TOTAL_NUMBER);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.READ_ALL_UNREAD_MESSAGE_TOTAL_NUMBER);
  }

  private void readUnreadMsgNum()
  {
    String[] arrayOfString = { "topic", "unread" };
    Cursor localCursor = this.mDbProvider.query(this.mUri, arrayOfString, null, null, null);
    if ((localCursor != null) && (localCursor.moveToFirst()));
    label152: label173: 
    while (true)
    {
      boolean bool;
      String str;
      if (1 == localCursor.getInt(localCursor.getColumnIndexOrThrow("unread")))
      {
        bool = true;
        if (Boolean.valueOf(bool).booleanValue())
        {
          str = localCursor.getString(localCursor.getColumnIndexOrThrow("topic"));
          if ((!"public".equals(str)) && (!"private".equals(str)))
            break label152;
          this.mUnreadSysMsgNum = (1 + this.mUnreadSysMsgNum);
        }
      }
      while (true)
      {
        if (localCursor.moveToNext())
          break label173;
        if (localCursor != null)
          localCursor.close();
        this.mDbProvider.closeDB();
        return;
        bool = false;
        break;
        if ("reserve".equals(str))
          this.mUnreadMyMsgNum = (1 + this.mUnreadMyMsgNum);
      }
    }
  }

  private int readUnreadPrivateMsgNum()
  {
    String[] arrayOfString = { "unread" };
    Cursor localCursor = this.mDbProvider.query(PrivateTopicMessageColumns.getUri(), arrayOfString, null, null, null);
    int i = 0;
    if (localCursor != null)
    {
      boolean bool1 = localCursor.moveToFirst();
      i = 0;
      if (bool1)
        if (1 != localCursor.getInt(localCursor.getColumnIndexOrThrow("unread")))
          break label109;
    }
    label109: for (boolean bool2 = true; ; bool2 = false)
    {
      if (Boolean.valueOf(bool2).booleanValue())
        i++;
      if (localCursor.moveToNext())
        break;
      if (localCursor != null)
        localCursor.close();
      this.mDbProvider.closeDB();
      return i;
    }
  }

  private int readUnreadPublicMsgNum()
  {
    String[] arrayOfString = { "unread" };
    Cursor localCursor = this.mDbProvider.query(PublicTopicMessageColumns.getUri(), arrayOfString, null, null, null);
    int i = 0;
    if (localCursor != null)
    {
      boolean bool1 = localCursor.moveToFirst();
      i = 0;
      if (bool1)
        if (1 != localCursor.getInt(localCursor.getColumnIndexOrThrow("unread")))
          break label109;
    }
    label109: for (boolean bool2 = true; ; bool2 = false)
    {
      if (Boolean.valueOf(bool2).booleanValue())
        i++;
      if (localCursor.moveToNext())
        break;
      if (localCursor != null)
        localCursor.close();
      this.mDbProvider.closeDB();
      return i;
    }
  }

  public void doRunTask()
  {
    if ((this.mDbProvider != null) && (this.mType.equals(IMQTTMessageDBUpdater.TopicTableUpdateActionType.READ_ALL_UNREAD_MESSAGE_TOTAL_NUMBER)) && (this.mNotifier != null))
    {
      readUnreadMsgNum();
      this.mNotifier.updateTotalUnreadMsgNum(this.mUnreadSysMsgNum, this.mUnreadMyMsgNum);
    }
    cleanUp();
  }

  public String getTopic()
  {
    return "";
  }

  public void initUriMap()
  {
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.READ_ALL_UNREAD_MESSAGE_TOTAL_NUMBER, MessageSystemV3Columns.getUri());
  }

  public void runSubTask(IMQTTMessageDBUpdater.TopicTableUpdateActionType paramTopicTableUpdateActionType)
    throws Exception
  {
    this.mUri = ((Uri)this.mUriMap.get(paramTopicTableUpdateActionType));
    this.mType = paramTopicTableUpdateActionType;
    this.mSelf.start();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.threads.ReadTotalUnreadMsgNumTask
 * JD-Core Version:    0.6.2
 */