package com.starcor.hunan.msgsys.threads;

import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.db.AdminTopicMessageColumns;
import com.starcor.hunan.db.DBProvider;
import com.starcor.hunan.db.MessageSystemV3Columns;
import com.starcor.hunan.db.PrivateTopicMessageColumns;
import com.starcor.hunan.db.PublicTopicMessageColumns;
import com.starcor.hunan.msgsys.data.MessageButtonBody;
import com.starcor.hunan.msgsys.data.MessageItemData;
import com.starcor.hunan.msgsys.data.MessageItemData.MessageActionType;
import com.starcor.hunan.msgsys.data.MessageItemData.MessageType;
import com.starcor.hunan.msgsys.data.MessageItemData.SecondaryMessageType;
import com.starcor.hunan.msgsys.interfaces.AbstractMQTTUIUpdateNotifier;
import com.starcor.hunan.msgsys.interfaces.AbstractSubTask;
import com.starcor.hunan.msgsys.interfaces.IMQTTMessageDBUpdater.TopicTableUpdateActionType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LoadAllMessageTask extends AbstractSubTask
{
  private List<MessageItemData> mAdminMessageItems = null;
  private List<MessageItemData> mMyMessageItems = null;
  private List<MessageItemData> mPrivateMessageItems = null;
  private List<MessageItemData> mPublicMessageItems = null;
  private List<MessageItemData> mSystemMessageItems = null;

  public LoadAllMessageTask()
  {
    super(LoadAllMessageTask.class.getSimpleName());
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_ADMIN_TOPIC_MESSAGE);
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_PRIVATE_TOPIC_MESSAGE);
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_PUBLIC_TOPIC_MESSAGE);
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_RESERVE_TOPIC_MESSAGE);
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_MESSAGES);
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_PRIVATE_AND_PUBLIC_TOPIC_MESSAGE);
    this.mSelf = this;
  }

  private void cleanUp()
  {
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_ADMIN_TOPIC_MESSAGE);
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_PRIVATE_TOPIC_MESSAGE);
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_PUBLIC_TOPIC_MESSAGE);
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_RESERVE_TOPIC_MESSAGE);
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_PRIVATE_AND_PUBLIC_TOPIC_MESSAGE);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_ADMIN_TOPIC_MESSAGE);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_PRIVATE_TOPIC_MESSAGE);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_PUBLIC_TOPIC_MESSAGE);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_RESERVE_TOPIC_MESSAGE);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_PRIVATE_AND_PUBLIC_TOPIC_MESSAGE);
  }

  private int loadAllAdminMsg()
  {
    String[] arrayOfString = { "mpns_admin_action", "message", "page", "detail", "video", "message_ids", "message_id", "topic" };
    Cursor localCursor = this.mDbProvider.query(this.mUri, arrayOfString, null, null, null);
    int i = 0;
    String str1;
    String str2;
    String str3;
    if (localCursor != null)
    {
      boolean bool1 = localCursor.moveToFirst();
      i = 0;
      if (bool1)
      {
        str1 = localCursor.getString(localCursor.getColumnIndexOrThrow("mpns_admin_action"));
        str2 = localCursor.getString(localCursor.getColumnIndexOrThrow("message_id"));
        int j = localCursor.getInt(localCursor.getColumnIndexOrThrow("message"));
        int k = localCursor.getInt(localCursor.getColumnIndexOrThrow("page"));
        int m = localCursor.getInt(localCursor.getColumnIndexOrThrow("detail"));
        int n = localCursor.getInt(localCursor.getColumnIndexOrThrow("video"));
        str3 = "video=" + j + "login=" + k + "pay=" + m + "text=" + n;
        if (1 != localCursor.getInt(localCursor.getColumnIndexOrThrow("unread")))
          break label316;
      }
    }
    label316: for (boolean bool2 = true; ; bool2 = false)
    {
      if (bool2)
        i++;
      MessageItemData localMessageItemData = new MessageItemData(str1, str3, null, bool2, MessageItemData.MessageType.MY_MESSAGE, MessageItemData.SecondaryMessageType.ADMIN_TOPOIC_MESSAGE, null, str2);
      this.mAdminMessageItems.add(localMessageItemData);
      if (localCursor.moveToNext())
        break;
      localCursor.close();
      return i;
    }
  }

  private int[] loadAllMsg()
  {
    int[] arrayOfInt = { 0, 0 };
    String[] arrayOfString1 = { "publish_time", "message_id", "unread", "topic", "ext", "title", "message_type", "body", "msg_display_style", "dialogway", "dialogway_args", "buttons" };
    Cursor localCursor = this.mDbProvider.query(this.mUri, arrayOfString1, null, null, null);
    int i = 0;
    int j = 0;
    if (localCursor != null)
    {
      boolean bool1 = localCursor.moveToFirst();
      i = 0;
      j = 0;
      if (!bool1);
    }
    label337: label487: label1014: 
    while (true)
    {
      String str1 = localCursor.getString(localCursor.getColumnIndexOrThrow("title"));
      String str2 = localCursor.getString(localCursor.getColumnIndexOrThrow("body"));
      String str3 = localCursor.getString(localCursor.getColumnIndexOrThrow("publish_time"));
      boolean bool2;
      Boolean localBoolean;
      String str5;
      MessageItemData.SecondaryMessageType localSecondaryMessageType;
      MessageItemData.MessageType localMessageType;
      MessageItemData localMessageItemData;
      ArrayList localArrayList;
      int m;
      String str13;
      if (1 == localCursor.getInt(localCursor.getColumnIndexOrThrow("unread")))
      {
        bool2 = true;
        localBoolean = Boolean.valueOf(bool2);
        String str4 = localCursor.getString(localCursor.getColumnIndexOrThrow("ext"));
        str5 = localCursor.getString(localCursor.getColumnIndexOrThrow("topic"));
        String str6 = localCursor.getString(localCursor.getColumnIndexOrThrow("message_type"));
        String str7 = localCursor.getString(localCursor.getColumnIndexOrThrow("dialogway"));
        String str8 = localCursor.getString(localCursor.getColumnIndexOrThrow("dialogway_args"));
        String str9 = localCursor.getString(localCursor.getColumnIndexOrThrow("buttons"));
        localSecondaryMessageType = MessageItemData.SecondaryMessageType.INVALID;
        localMessageType = MessageItemData.MessageType.SYSTEM_MESSAGE;
        if (!"public".equals(str5))
          break label487;
        localSecondaryMessageType = MessageItemData.SecondaryMessageType.PUBLIC_TOPIC_MESSAAGE;
        if (localBoolean.booleanValue())
          j++;
        String str10 = localCursor.getString(localCursor.getColumnIndexOrThrow("message_id"));
        localMessageItemData = new MessageItemData(str1, str2, str3, localBoolean.booleanValue(), localMessageType, localSecondaryMessageType, null, str10);
        localMessageItemData.setExt(str4);
        localMessageItemData.setDialogway(str7);
        localMessageItemData.setDialogway_args(str8);
        localMessageItemData.setMsgType(str6);
        localArrayList = new ArrayList();
        if (TextUtils.isEmpty(str9))
          break label662;
        String[] arrayOfString2 = str9.split("::");
        if ((arrayOfString2 == null) || (arrayOfString2.length <= 0))
          break label662;
        int k = arrayOfString2.length;
        m = 0;
        if (m >= k)
          break label662;
        str13 = arrayOfString2[m];
        if (!TextUtils.isEmpty(str13))
          break label550;
      }
      while (true)
      {
        m++;
        break label453;
        bool2 = false;
        break;
        if ("private".equals(str5))
        {
          localSecondaryMessageType = MessageItemData.SecondaryMessageType.PRIVATE_TOPOIC_MESSAGE;
          if (!localBoolean.booleanValue())
            break label337;
          j++;
          break label337;
        }
        if (!"reserve".equals(str5))
          break label337;
        localSecondaryMessageType = MessageItemData.SecondaryMessageType.RESERVE_TOPIC_MESSAGE;
        localMessageType = MessageItemData.MessageType.MY_MESSAGE;
        if (!localBoolean.booleanValue())
          break label337;
        i++;
        break label337;
        String[] arrayOfString3 = str13.split("##");
        if ((arrayOfString3 != null) && (arrayOfString3.length > 0))
        {
          int n = arrayOfString3.length;
          MessageButtonBody localMessageButtonBody2 = new MessageButtonBody();
          int i1 = 0;
          if (i1 < n)
          {
            if (i1 == 0)
              localMessageButtonBody2.setActions(arrayOfString3[i1]);
            label635: 
            do
              while (true)
              {
                i1++;
                break;
                if (1 != i1)
                  break label635;
                localMessageButtonBody2.setLabel(arrayOfString3[i1]);
              }
            while (2 != i1);
            localMessageButtonBody2.setArgs(arrayOfString3[i1]);
          }
          localArrayList.add(localMessageButtonBody2);
        }
      }
      MessageItemData.MessageActionType localMessageActionType = MessageItemData.MessageActionType.INVALID;
      String str11 = "";
      String str12 = "";
      if ((localArrayList != null) && (localArrayList.size() > 0))
      {
        MessageButtonBody localMessageButtonBody1 = (MessageButtonBody)localArrayList.get(0);
        if (localMessageButtonBody1 != null)
        {
          str11 = localMessageButtonBody1.getActions();
          str12 = localMessageButtonBody1.getArgs();
        }
      }
      if (!TextUtils.isEmpty(str11))
      {
        if ("video".equals(str11))
        {
          localMessageActionType = MessageItemData.MessageActionType.PLAY_VIDEO;
          localMessageItemData.setActionArgs(str12);
        }
      }
      else
      {
        localMessageItemData.setActionType(localMessageActionType);
        localMessageItemData.setMessageButtonBodies(localArrayList);
        Logger.i(this.TAG, "读出一条" + str5 + "类型的消息:" + localMessageItemData.toString());
        if ((!"public".equals(str5)) && (!"private".equals(str5)))
          break label991;
        this.mSystemMessageItems.add(localMessageItemData);
      }
      while (true)
      {
        if (localCursor.moveToNext())
          break label1014;
        localCursor.close();
        arrayOfInt[0] = j;
        arrayOfInt[1] = i;
        return arrayOfInt;
        if ("message".equals(str11))
        {
          localMessageActionType = MessageItemData.MessageActionType.READ_MESSAGE;
          localMessageItemData.setActionArgs(str12);
          break;
        }
        if ("detail".equals(str11))
        {
          localMessageActionType = MessageItemData.MessageActionType.VIEW_DETAIL;
          localMessageItemData.setActionArgs(str12);
          break;
        }
        if ("page".equals(str11))
        {
          localMessageActionType = MessageItemData.MessageActionType.OPEN_PAGE;
          localMessageItemData.setPageUrl(str12);
          break;
        }
        if ("topic".equals(str11))
        {
          localMessageActionType = MessageItemData.MessageActionType.SPECIAL_PAGE;
          localMessageItemData.setActionArgs(str12);
          break;
        }
        if (!"reservetopic".equals(str11))
          break;
        localMessageActionType = MessageItemData.MessageActionType.RESERVE_PAGE;
        localMessageItemData.setActionArgs(str12);
        break;
        if ("reserve".equals(str5))
          this.mMyMessageItems.add(localMessageItemData);
      }
    }
  }

  private int loadAllPrivateMsg()
  {
    String[] arrayOfString = { "publish_time", "message_id", "unread", "topic", "ext", "title", "message_type", "body", "msg_display_style", "dialogway", "dialogway_args", "buttons" };
    Cursor localCursor = this.mDbProvider.query(this.mUri, arrayOfString, null, null, null);
    int i = 0;
    boolean bool2;
    label178: String str4;
    MessageItemData.MessageActionType localMessageActionType;
    label241: MessageItemData localMessageItemData;
    String str6;
    if (localCursor != null)
    {
      boolean bool1 = localCursor.moveToFirst();
      i = 0;
      if (bool1)
      {
        String str1 = localCursor.getString(localCursor.getColumnIndexOrThrow("title"));
        String str2 = localCursor.getString(localCursor.getColumnIndexOrThrow("body"));
        String str3 = localCursor.getString(localCursor.getColumnIndexOrThrow("publish_time"));
        if (1 != localCursor.getInt(localCursor.getColumnIndexOrThrow("unread")))
          break label385;
        bool2 = true;
        Boolean localBoolean = Boolean.valueOf(bool2);
        if (localBoolean.booleanValue())
          i++;
        MessageItemData.SecondaryMessageType localSecondaryMessageType = MessageItemData.SecondaryMessageType.PRIVATE_TOPOIC_MESSAGE;
        str4 = localCursor.getString(localCursor.getColumnIndexOrThrow("action"));
        localMessageActionType = null;
        if (str4 != null)
        {
          if (!str4.equals("video"))
            break label391;
          localMessageActionType = MessageItemData.MessageActionType.PLAY_VIDEO;
        }
        String str5 = localCursor.getString(localCursor.getColumnIndexOrThrow("message_id"));
        localMessageItemData = new MessageItemData(str1, str2, str3, localBoolean.booleanValue(), MessageItemData.MessageType.MY_MESSAGE, localSecondaryMessageType, localMessageActionType, str5);
        if (str4 != null)
        {
          str6 = localCursor.getString(localCursor.getColumnIndexOrThrow("action_args"));
          if (!str4.equals("page"))
            break label470;
          localMessageItemData.setPageUrl(str6);
        }
      }
    }
    while (true)
    {
      Logger.i(this.TAG, "读出一条私有消息:" + localMessageItemData.toString());
      this.mPrivateMessageItems.add(localMessageItemData);
      if (localCursor.moveToNext())
        break;
      localCursor.close();
      return i;
      label385: bool2 = false;
      break label178;
      label391: if (str4.equals("message"))
      {
        localMessageActionType = MessageItemData.MessageActionType.READ_MESSAGE;
        break label241;
      }
      if (str4.equals("detail"))
      {
        localMessageActionType = MessageItemData.MessageActionType.VIEW_DETAIL;
        break label241;
      }
      if (str4.equals("page"))
      {
        localMessageActionType = MessageItemData.MessageActionType.OPEN_PAGE;
        break label241;
      }
      boolean bool3 = str4.equals("topic");
      localMessageActionType = null;
      if (!bool3)
        break label241;
      localMessageActionType = MessageItemData.MessageActionType.SPECIAL_PAGE;
      break label241;
      label470: if ((str4.equals("video")) || (str4.equals("detail")) || (str4.equals("topic")))
        localMessageItemData.setActionArgs(str6);
    }
  }

  private int loadAllPublicMsg()
  {
    String[] arrayOfString = { "publish_time", "message_id", "unread", "topic", "ext", "title", "message_type", "body", "msg_display_style", "dialogway", "dialogway_args", "buttons" };
    Cursor localCursor = this.mDbProvider.query(this.mUri, arrayOfString, null, null, null);
    int i = 0;
    boolean bool2;
    label178: String str4;
    MessageItemData.MessageActionType localMessageActionType;
    label241: MessageItemData localMessageItemData;
    String str6;
    if (localCursor != null)
    {
      boolean bool1 = localCursor.moveToFirst();
      i = 0;
      if (bool1)
      {
        String str1 = localCursor.getString(localCursor.getColumnIndexOrThrow("title"));
        String str2 = localCursor.getString(localCursor.getColumnIndexOrThrow("body"));
        String str3 = localCursor.getString(localCursor.getColumnIndexOrThrow("publish_time"));
        if (1 != localCursor.getInt(localCursor.getColumnIndexOrThrow("unread")))
          break label385;
        bool2 = true;
        Boolean localBoolean = Boolean.valueOf(bool2);
        if (localBoolean.booleanValue())
          i++;
        MessageItemData.SecondaryMessageType localSecondaryMessageType = MessageItemData.SecondaryMessageType.PUBLIC_TOPIC_MESSAAGE;
        str4 = localCursor.getString(localCursor.getColumnIndexOrThrow("action"));
        localMessageActionType = null;
        if (str4 != null)
        {
          if (!str4.equals("video"))
            break label391;
          localMessageActionType = MessageItemData.MessageActionType.PLAY_VIDEO;
        }
        String str5 = localCursor.getString(localCursor.getColumnIndexOrThrow("message_id"));
        localMessageItemData = new MessageItemData(str1, str2, str3, localBoolean.booleanValue(), MessageItemData.MessageType.MY_MESSAGE, localSecondaryMessageType, localMessageActionType, str5);
        if (str4 != null)
        {
          str6 = localCursor.getString(localCursor.getColumnIndexOrThrow("action_args"));
          if (!str4.equals("page"))
            break label470;
          localMessageItemData.setPageUrl(str6);
        }
      }
    }
    while (true)
    {
      Logger.i(this.TAG, "读出一条公共消息:" + localMessageItemData.toString());
      this.mPublicMessageItems.add(localMessageItemData);
      if (localCursor.moveToNext())
        break;
      localCursor.close();
      return i;
      label385: bool2 = false;
      break label178;
      label391: if (str4.equals("message"))
      {
        localMessageActionType = MessageItemData.MessageActionType.READ_MESSAGE;
        break label241;
      }
      if (str4.equals("detail"))
      {
        localMessageActionType = MessageItemData.MessageActionType.VIEW_DETAIL;
        break label241;
      }
      if (str4.equals("page"))
      {
        localMessageActionType = MessageItemData.MessageActionType.OPEN_PAGE;
        break label241;
      }
      boolean bool3 = str4.equals("topic");
      localMessageActionType = null;
      if (!bool3)
        break label241;
      localMessageActionType = MessageItemData.MessageActionType.SPECIAL_PAGE;
      break label241;
      label470: if ((str4.equals("video")) || (str4.equals("detail")) || (str4.equals("topic")))
        localMessageItemData.setActionArgs(str6);
    }
  }

  public void doRunTask()
  {
    if (this.mDbProvider != null)
    {
      if (this.mPublicMessageItems != null)
      {
        this.mPublicMessageItems.clear();
        this.mPublicMessageItems = null;
      }
      if (this.mPrivateMessageItems != null)
      {
        this.mPrivateMessageItems.clear();
        this.mPrivateMessageItems = null;
      }
      if (this.mAdminMessageItems != null)
      {
        this.mAdminMessageItems.clear();
        this.mAdminMessageItems = null;
      }
      if (!IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_MESSAGES.equals(this.mType))
        break label242;
      if (this.mSystemMessageItems != null)
      {
        this.mSystemMessageItems.clear();
        this.mSystemMessageItems = null;
      }
      if (this.mMyMessageItems != null)
      {
        this.mMyMessageItems.clear();
        this.mMyMessageItems = null;
      }
      this.mSystemMessageItems = new ArrayList();
      this.mMyMessageItems = new ArrayList();
      this.mUri = ((Uri)this.mUriMap.get(this.mType));
      int[] arrayOfInt = loadAllMsg();
      if (this.mNotifier != null)
      {
        int k = 0;
        int m = 0;
        if (arrayOfInt != null)
        {
          int n = arrayOfInt.length;
          k = 0;
          m = 0;
          if (n == 2)
          {
            m = arrayOfInt[0];
            k = arrayOfInt[1];
          }
        }
        this.mNotifier.finishLoadingAllMsg(this.mSystemMessageItems, this.mMyMessageItems, m, k);
      }
    }
    while (true)
    {
      cleanUp();
      return;
      label242: if (IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_ADMIN_TOPIC_MESSAGE.equals(this.mType))
      {
        if (this.mAdminMessageItems != null)
        {
          this.mAdminMessageItems.clear();
          this.mAdminMessageItems = null;
        }
        this.mAdminMessageItems = new ArrayList();
        loadAllAdminMsg();
        if (this.mNotifier != null)
          this.mNotifier.finishLoadingAllAdminMsg(this.mAdminMessageItems);
      }
      else if (IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_PRIVATE_TOPIC_MESSAGE.equals(this.mType))
      {
        if (this.mPrivateMessageItems != null)
        {
          this.mPrivateMessageItems.clear();
          this.mPrivateMessageItems = null;
        }
        this.mPrivateMessageItems = new ArrayList();
        loadAllPrivateMsg();
        if (this.mNotifier != null)
          this.mNotifier.finishLoadingAllPrivateMsg(this.mPrivateMessageItems);
      }
      else if (IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_PUBLIC_TOPIC_MESSAGE.equals(this.mType))
      {
        if (this.mPublicMessageItems != null)
        {
          this.mPublicMessageItems.clear();
          this.mPublicMessageItems = null;
        }
        this.mPublicMessageItems = new ArrayList();
        loadAllPublicMsg();
        if (this.mNotifier != null)
          this.mNotifier.finishLoadingAllPublicMsg(this.mPublicMessageItems);
      }
      else if (IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_PRIVATE_AND_PUBLIC_TOPIC_MESSAGE.equals(this.mType))
      {
        Uri localUri1 = PublicTopicMessageColumns.getUri();
        Uri localUri2 = PrivateTopicMessageColumns.getUri();
        if (this.mPublicMessageItems != null)
        {
          this.mPublicMessageItems.clear();
          this.mPublicMessageItems = null;
        }
        if (this.mPrivateMessageItems != null)
        {
          this.mPrivateMessageItems.clear();
          this.mPrivateMessageItems = null;
        }
        this.mPublicMessageItems = new ArrayList();
        this.mPrivateMessageItems = new ArrayList();
        this.mUri = localUri1;
        int i = loadAllPublicMsg();
        this.mUri = localUri2;
        int j = loadAllPrivateMsg();
        if (this.mNotifier != null)
          this.mNotifier.finishLoadingAllPublicAndPrivateMsg(this.mPublicMessageItems, this.mPrivateMessageItems, i, j);
      }
    }
  }

  public String getTopic()
  {
    String str = "";
    if (IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_PUBLIC_TOPIC_MESSAGE.equals(this.mType))
      str = "public";
    do
    {
      return str;
      if (IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_PRIVATE_TOPIC_MESSAGE.equals(this.mType))
        return "private";
      if (IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_RESERVE_TOPIC_MESSAGE.equals(this.mType))
        return "reserve";
      if (IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_PRIVATE_AND_PUBLIC_TOPIC_MESSAGE.equals(this.mType))
        return "public_and_private";
      if (IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_MESSAGES.equals(this.mType))
        return "all_topic";
    }
    while (!IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_ADMIN_TOPIC_MESSAGE.equals(this.mType));
    return "admin";
  }

  public void initUriMap()
  {
    Uri localUri1 = AdminTopicMessageColumns.getUri();
    Uri localUri2 = MessageSystemV3Columns.getUri();
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_MESSAGES, localUri2);
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_ADMIN_TOPIC_MESSAGE, localUri1);
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_PRIVATE_TOPIC_MESSAGE, localUri2);
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_PUBLIC_TOPIC_MESSAGE, localUri2);
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_RESERVE_TOPIC_MESSAGE, localUri2);
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.LOAD_ALL_PRIVATE_AND_PUBLIC_TOPIC_MESSAGE, localUri2);
  }

  public void runSubTask(IMQTTMessageDBUpdater.TopicTableUpdateActionType paramTopicTableUpdateActionType)
  {
    this.mUri = ((Uri)this.mUriMap.get(paramTopicTableUpdateActionType));
    this.mType = paramTopicTableUpdateActionType;
    this.mSelf.start();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.threads.LoadAllMessageTask
 * JD-Core Version:    0.6.2
 */