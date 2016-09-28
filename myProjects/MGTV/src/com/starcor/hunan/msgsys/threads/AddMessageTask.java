package com.starcor.hunan.msgsys.threads;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.db.AdminTopicMessageColumns;
import com.starcor.hunan.db.DBProvider;
import com.starcor.hunan.db.MessageSystemV3Columns;
import com.starcor.hunan.msgsys.data.MessageButtonBody;
import com.starcor.hunan.msgsys.data.admintopic.AdminTopicMessageData;
import com.starcor.hunan.msgsys.data.privatetopic.PrivateTopicMessageData;
import com.starcor.hunan.msgsys.data.privatetopic.PrivateTopicMessageRawBody;
import com.starcor.hunan.msgsys.data.publictopic.PublicTopicMessageData;
import com.starcor.hunan.msgsys.data.publictopic.PublicTopicMessageRawBody;
import com.starcor.hunan.msgsys.data.reservetopic.ReserveTopicMessageData;
import com.starcor.hunan.msgsys.data.reservetopic.ReserveTopicMessageRawBody;
import com.starcor.hunan.msgsys.interfaces.AbstractMQTTUIUpdateNotifier;
import com.starcor.hunan.msgsys.interfaces.AbstractSubTask;
import com.starcor.hunan.msgsys.interfaces.IMQTTMessageDBUpdater.TopicTableUpdateActionType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AddMessageTask extends AbstractSubTask
{
  private static final int MAX_MSG_NUM = 30;
  private AdminTopicMessageData mAdminTopicMessageData = null;
  private Object mMsgData = null;
  private PrivateTopicMessageData mPrivateTopicMessageData = null;
  private PublicTopicMessageData mPublicTopicMessageData = null;
  private ReserveTopicMessageData mReserveTopicMessageData = null;
  private IMQTTMessageDBUpdater.TopicTableUpdateActionType mType = null;

  public AddMessageTask(Object paramObject)
  {
    super(AddMessageTask.class.getSimpleName());
    this.mMsgData = paramObject;
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ADD_ADMIN_TOPIC_MESSAGE);
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ADD_PRIVATE_TOPIC_MESSAGE);
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ADD_PUBLIC_TOPIC_MESSAGE);
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ADD_RESERVE_TOPIC_MESSAGE);
    this.mSelf = this;
  }

  private boolean checkCurrentMsgNum()
  {
    Logger.i(this.TAG, "getMsgNum=" + getMsgNum());
    return getMsgNum() >= 30;
  }

  private boolean checkWhetherMessageExists()
  {
    String[] arrayOfString1 = { "message_id" };
    String[] arrayOfString2;
    if (IMQTTMessageDBUpdater.TopicTableUpdateActionType.ADD_PUBLIC_TOPIC_MESSAGE.equals(this.mType))
    {
      arrayOfString2 = new String[1];
      arrayOfString2[0] = this.mPublicTopicMessageData.getMessage_id();
    }
    while (true)
    {
      Cursor localCursor = this.mDbProvider.query(this.mUri, arrayOfString1, "message_id=?", arrayOfString2, null);
      if (localCursor == null)
        break;
      int i = localCursor.getCount();
      Logger.i(this.TAG, "数据库中公有" + i + "条消息记录和这条新消息一样！");
      localCursor.close();
      if (i <= 0)
        break;
      return true;
      if (IMQTTMessageDBUpdater.TopicTableUpdateActionType.ADD_PRIVATE_TOPIC_MESSAGE.equals(this.mType))
      {
        arrayOfString2 = new String[1];
        arrayOfString2[0] = this.mPrivateTopicMessageData.getMessage_id();
      }
      else
      {
        boolean bool = IMQTTMessageDBUpdater.TopicTableUpdateActionType.ADD_RESERVE_TOPIC_MESSAGE.equals(this.mType);
        arrayOfString2 = null;
        if (bool)
        {
          arrayOfString2 = new String[1];
          arrayOfString2[0] = this.mReserveTopicMessageData.getMessage_id();
        }
      }
    }
    Logger.i(this.TAG, "数据库中并没有这条消息数据记录！");
    return false;
  }

  private void cleanUp()
  {
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ADD_ADMIN_TOPIC_MESSAGE);
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ADD_PRIVATE_TOPIC_MESSAGE);
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ADD_PUBLIC_TOPIC_MESSAGE);
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ADD_RESERVE_TOPIC_MESSAGE);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ADD_ADMIN_TOPIC_MESSAGE);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ADD_PRIVATE_TOPIC_MESSAGE);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ADD_PUBLIC_TOPIC_MESSAGE);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ADD_RESERVE_TOPIC_MESSAGE);
  }

  private boolean deleteCommonMessage(String paramString)
  {
    String[] arrayOfString = new String[1];
    arrayOfString[0] = ("" + paramString);
    int i = this.mDbProvider.delete(this.mUri, "message_id=?", arrayOfString);
    Logger.i(this.TAG, "共删除" + i + "条数据");
    if ((i > 0) && (this.mNotifier != null))
      this.mNotifier.finishDeleteMessage(paramString);
    while (i > 0)
      return true;
    return false;
  }

  private void deleteOldestMessage()
  {
    Logger.i(this.TAG, "deleteOldestMessage");
    String str = findAndDelete(MessageSystemV3Columns.getUri());
    Logger.i(this.TAG, "deleteOldestMessage msgId=" + str);
  }

  private String findAndDelete(Uri paramUri)
  {
    String[] arrayOfString = { "message_id" };
    Cursor localCursor = this.mDbProvider.query(paramUri, arrayOfString, null, null, null);
    if ((localCursor != null) && (localCursor.moveToFirst()))
    {
      String str = localCursor.getString(localCursor.getColumnIndexOrThrow("message_id"));
      Logger.i(this.TAG, "deleteOldestMessage findAndDelete msgId=" + str);
      if ((!TextUtils.isEmpty(str)) && (deleteCommonMessage(str)))
        return str;
    }
    return null;
  }

  private int getMsgNum()
  {
    String[] arrayOfString = { "message_id" };
    Cursor localCursor = this.mDbProvider.query(MessageSystemV3Columns.getUri(), arrayOfString, null, null, null);
    int i = 0;
    if (localCursor != null)
    {
      boolean bool = localCursor.moveToFirst();
      i = 0;
      if (bool)
        i = localCursor.getCount();
    }
    if (localCursor != null)
      localCursor.close();
    this.mDbProvider.closeDB();
    return i;
  }

  private int getPrivateMsgNum()
  {
    String[] arrayOfString = { "topic" };
    Cursor localCursor = this.mDbProvider.query(MessageSystemV3Columns.getUri(), arrayOfString, null, null, null);
    int i = 0;
    if (localCursor != null)
    {
      boolean bool = localCursor.moveToFirst();
      i = 0;
      if (bool)
        do
          if ("public".equals(localCursor.getString(localCursor.getColumnIndexOrThrow("topic"))))
            i++;
        while (localCursor.moveToNext());
    }
    if (localCursor != null)
      localCursor.close();
    this.mDbProvider.closeDB();
    return i;
  }

  private int getPublicMsgNum()
  {
    String[] arrayOfString = { "topic" };
    Cursor localCursor = this.mDbProvider.query(MessageSystemV3Columns.getUri(), arrayOfString, null, null, null);
    int i = 0;
    if (localCursor != null)
    {
      boolean bool = localCursor.moveToFirst();
      i = 0;
      if (bool)
        do
          if ("public".equals(localCursor.getString(localCursor.getColumnIndexOrThrow("topic"))))
            i++;
        while (localCursor.moveToNext());
    }
    if (localCursor != null)
      localCursor.close();
    this.mDbProvider.closeDB();
    return i;
  }

  private int getReserveMsgNum()
  {
    String[] arrayOfString = { "topic" };
    Cursor localCursor = this.mDbProvider.query(MessageSystemV3Columns.getUri(), arrayOfString, null, null, null);
    int i = 0;
    if (localCursor != null)
    {
      boolean bool = localCursor.moveToFirst();
      i = 0;
      if (bool)
        do
          if ("public".equals(localCursor.getString(localCursor.getColumnIndexOrThrow("topic"))))
            i++;
        while (localCursor.moveToNext());
    }
    if (localCursor != null)
      localCursor.close();
    this.mDbProvider.closeDB();
    return i;
  }

  public void doRunTask()
  {
    ContentValues localContentValues;
    String str1;
    String str2;
    String str3;
    String str4;
    String str5;
    String str6;
    String str7;
    int i;
    int j;
    String str8;
    String str9;
    if (this.mDbProvider != null)
    {
      localContentValues = new ContentValues();
      str1 = "";
      str2 = "";
      str3 = "";
      str4 = "";
      str5 = "";
      str6 = "";
      str7 = "";
      i = 1;
      j = 1;
      str8 = "";
      str9 = "";
      if (IMQTTMessageDBUpdater.TopicTableUpdateActionType.ADD_ADMIN_TOPIC_MESSAGE.equals(this.mType))
        Logger.i(this.TAG, "目前不把管理消息添加到数据库中!");
    }
    else
    {
      return;
    }
    ArrayList localArrayList;
    Object localObject;
    int k;
    int m;
    label413: MessageButtonBody localMessageButtonBody;
    if (IMQTTMessageDBUpdater.TopicTableUpdateActionType.ADD_PUBLIC_TOPIC_MESSAGE.equals(this.mType))
    {
      if (this.mPublicTopicMessageData == null)
      {
        Logger.i(this.TAG, "添加公共频道数据失败，因为消息数据内容为空！");
        return;
      }
      if (checkWhetherMessageExists())
      {
        Logger.i(this.TAG, "这条公共频道消息已经存在于当地数据库，因此不重新添加！");
        this.mDbProvider.closeDB();
        return;
      }
      if (checkCurrentMsgNum())
        deleteOldestMessage();
      Logger.i(this.TAG, "往  " + this.mUri + " 中的添加一条公共数据：" + this.mPublicTopicMessageData.toString());
      str2 = this.mPublicTopicMessageData.getMessage_id();
      str3 = this.mPublicTopicMessageData.getPublish_time();
      PublicTopicMessageRawBody localPublicTopicMessageRawBody = (PublicTopicMessageRawBody)this.mPublicTopicMessageData.getMessage();
      localArrayList = null;
      if (localPublicTopicMessageRawBody != null)
      {
        str4 = localPublicTopicMessageRawBody.getExt();
        str5 = localPublicTopicMessageRawBody.getTitle();
        str6 = localPublicTopicMessageRawBody.getMessage_type();
        str7 = localPublicTopicMessageRawBody.getBody();
        i = localPublicTopicMessageRawBody.getAlter();
        j = localPublicTopicMessageRawBody.getDialogway();
        str8 = localPublicTopicMessageRawBody.getDialogway_args();
        localArrayList = localPublicTopicMessageRawBody.getMessageButtonBodies();
      }
      str1 = "public";
      localObject = this.mPublicTopicMessageData;
      localContentValues.put("message_id", str2);
      localContentValues.put("topic", str1);
      localContentValues.put("unread", Integer.valueOf(1));
      localContentValues.put("publish_time", str3);
      localContentValues.put("ext", str4);
      localContentValues.put("title", str5);
      localContentValues.put("message_type", str6);
      localContentValues.put("body", str7);
      localContentValues.put("msg_display_style", Integer.valueOf(i));
      localContentValues.put("dialogway", Integer.valueOf(j));
      localContentValues.put("dialogway_args", str8);
      if (localArrayList == null)
        break label1081;
      k = localArrayList.size();
      m = 0;
      if (m >= k)
        break label1081;
      localMessageButtonBody = (MessageButtonBody)localArrayList.get(m);
      if (localMessageButtonBody != null)
        break label889;
    }
    while (true)
    {
      m++;
      break label413;
      if (IMQTTMessageDBUpdater.TopicTableUpdateActionType.ADD_PRIVATE_TOPIC_MESSAGE.equals(this.mType))
      {
        if (this.mPrivateTopicMessageData == null)
        {
          Logger.i(this.TAG, "添加私有频道消息数据失败，因为消息数据为空！");
          return;
        }
        if (checkWhetherMessageExists())
        {
          Logger.i(this.TAG, "这条私有频道消息已经存在于当地数据库，因此不重新添加！");
          this.mDbProvider.closeDB();
          return;
        }
        if (checkCurrentMsgNum())
          deleteOldestMessage();
        Logger.i(this.TAG, "往  " + this.mUri + " 中的添加一条私有数据：" + this.mPrivateTopicMessageData.toString());
        str2 = this.mPrivateTopicMessageData.getMessage_id();
        str3 = this.mPrivateTopicMessageData.getPublish_time();
        PrivateTopicMessageRawBody localPrivateTopicMessageRawBody = (PrivateTopicMessageRawBody)this.mPrivateTopicMessageData.getMessage();
        localArrayList = null;
        if (localPrivateTopicMessageRawBody != null)
        {
          str4 = localPrivateTopicMessageRawBody.getExt();
          str5 = localPrivateTopicMessageRawBody.getTitle();
          str6 = localPrivateTopicMessageRawBody.getMessage_type();
          str7 = localPrivateTopicMessageRawBody.getBody();
          i = localPrivateTopicMessageRawBody.getAlter();
          j = localPrivateTopicMessageRawBody.getDialogway();
          str8 = localPrivateTopicMessageRawBody.getDialogway_args();
          localArrayList = localPrivateTopicMessageRawBody.getMessageButtonBodies();
        }
        str1 = "private";
        localObject = this.mPrivateTopicMessageData;
        break;
      }
      boolean bool = IMQTTMessageDBUpdater.TopicTableUpdateActionType.ADD_RESERVE_TOPIC_MESSAGE.equals(this.mType);
      localObject = null;
      localArrayList = null;
      if (!bool)
        break;
      if (this.mReserveTopicMessageData == null)
      {
        Logger.i(this.TAG, "添加预约频道消息数据失败，因为消息数据为空！");
        return;
      }
      if (checkWhetherMessageExists())
      {
        Logger.i(this.TAG, "这条预约频道消息已经存在于当地数据库，因此不重新添加！");
        this.mDbProvider.closeDB();
        return;
      }
      if (checkCurrentMsgNum())
        deleteOldestMessage();
      Logger.i(this.TAG, "往  " + this.mUri + " 中的添加一条预约数据：" + this.mReserveTopicMessageData.toString());
      str2 = this.mReserveTopicMessageData.getMessage_id();
      str3 = this.mReserveTopicMessageData.getPublish_time();
      ReserveTopicMessageRawBody localReserveTopicMessageRawBody = (ReserveTopicMessageRawBody)this.mReserveTopicMessageData.getMessage();
      localArrayList = null;
      if (localReserveTopicMessageRawBody != null)
      {
        str4 = localReserveTopicMessageRawBody.getExt();
        str5 = localReserveTopicMessageRawBody.getTitle();
        str6 = localReserveTopicMessageRawBody.getMessage_type();
        str7 = localReserveTopicMessageRawBody.getBody();
        i = localReserveTopicMessageRawBody.getAlter();
        j = localReserveTopicMessageRawBody.getDialogway();
        str8 = localReserveTopicMessageRawBody.getDialogway_args();
        localArrayList = localReserveTopicMessageRawBody.getMessageButtonBodies();
      }
      str1 = "reserve";
      localObject = this.mReserveTopicMessageData;
      break;
      label889: String str10 = localMessageButtonBody.getActions();
      String str11 = localMessageButtonBody.getArgs();
      String str12 = localMessageButtonBody.getLabel();
      String str13 = str9 + str10;
      if (!TextUtils.isEmpty(str10))
        str13 = str13 + "##";
      String str14 = str13 + str12;
      if (!TextUtils.isEmpty(str12))
        str14 = str14 + "##";
      str9 = str14 + str11;
      if ((m + 1 < k) && (!TextUtils.isEmpty(str9)))
        str9 = str9 + "::";
    }
    label1081: Logger.i(this.TAG, "buttonsStr=" + str9);
    localContentValues.put("buttons", str9);
    if (!TextUtils.isEmpty(str2))
    {
      this.mDbProvider.insert(this.mUri, localContentValues);
      if (this.mNotifier != null)
        this.mNotifier.finishAddMessage(str1, localObject);
    }
    while (true)
    {
      this.mDbProvider.closeDB();
      cleanUp();
      return;
      Logger.i(this.TAG, "message Id is empty! NOT add it to DB!");
    }
  }

  public String getTopic()
  {
    return "";
  }

  public void initUriMap()
  {
    Uri localUri1 = MessageSystemV3Columns.getUri();
    Uri localUri2 = AdminTopicMessageColumns.getUri();
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ADD_ADMIN_TOPIC_MESSAGE, localUri2);
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ADD_PRIVATE_TOPIC_MESSAGE, localUri1);
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ADD_PUBLIC_TOPIC_MESSAGE, localUri1);
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ADD_RESERVE_TOPIC_MESSAGE, localUri1);
  }

  public void runSubTask(IMQTTMessageDBUpdater.TopicTableUpdateActionType paramTopicTableUpdateActionType)
  {
    this.mType = paramTopicTableUpdateActionType;
    this.mUri = ((Uri)this.mUriMap.get(paramTopicTableUpdateActionType));
    switch (1.$SwitchMap$com$starcor$hunan$msgsys$interfaces$IMQTTMessageDBUpdater$TopicTableUpdateActionType[this.mType.ordinal()])
    {
    default:
      Logger.i(this.TAG, "添加新数据到数据库失败，因为收到未知的任务类型！");
      return;
    case 1:
      this.mAdminTopicMessageData = ((AdminTopicMessageData)this.mMsgData);
    case 2:
    case 3:
    case 4:
    }
    while (true)
    {
      this.mSelf.start();
      return;
      this.mPrivateTopicMessageData = ((PrivateTopicMessageData)this.mMsgData);
      continue;
      this.mPublicTopicMessageData = ((PublicTopicMessageData)this.mMsgData);
      continue;
      this.mReserveTopicMessageData = ((ReserveTopicMessageData)this.mMsgData);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.threads.AddMessageTask
 * JD-Core Version:    0.6.2
 */