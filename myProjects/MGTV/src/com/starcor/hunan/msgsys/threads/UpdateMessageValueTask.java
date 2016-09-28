package com.starcor.hunan.msgsys.threads;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.db.DBProvider;
import com.starcor.hunan.db.MessageSystemV3Columns;
import com.starcor.hunan.msgsys.interfaces.AbstractMQTTUIUpdateNotifier;
import com.starcor.hunan.msgsys.interfaces.AbstractSubTask;
import com.starcor.hunan.msgsys.interfaces.IMQTTMessageDBUpdater.TopicTableUpdateActionType;
import java.util.HashMap;
import java.util.List;

public class UpdateMessageValueTask extends AbstractSubTask
{
  private Bundle mParamsBundle = null;

  public UpdateMessageValueTask(Bundle paramBundle)
  {
    super(UpdateMessageValueTask.class.getSimpleName());
    this.mParamsBundle = paramBundle;
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.RESERVE_SPECIAL_TOPIC);
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.RESERVE_TURN_PLAY);
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_CHASE_DRAMA);
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_CHASE_DRAMA_CANCEL);
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_BACK_PLAY);
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_BACK_PLAY_CANCEL);
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_LIVE_SHOW);
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_LIVE_SHOW_CANCEL);
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_WISH_ORDER);
    this.mFilters.add(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_WISH_ORDER_CANCEL);
    this.mSelf = this;
  }

  private void cleanUp()
  {
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_BACK_PLAY);
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_BACK_PLAY_CANCEL);
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_CHASE_DRAMA);
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_CHASE_DRAMA_CANCEL);
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_LIVE_SHOW);
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_LIVE_SHOW_CANCEL);
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_WISH_ORDER);
    this.mUriMap.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_WISH_ORDER_CANCEL);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_CHASE_DRAMA);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_CHASE_DRAMA_CANCEL);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_BACK_PLAY);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_BACK_PLAY_CANCEL);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_LIVE_SHOW);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_LIVE_SHOW_CANCEL);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_WISH_ORDER);
    this.mFilters.remove(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_WISH_ORDER_CANCEL);
  }

  public void doRunTask()
  {
    String str1 = "";
    String str2 = "";
    String str3 = "";
    while (true)
    {
      int k;
      try
      {
        if (this.mParamsBundle != null)
        {
          if (this.mParamsBundle.containsKey("message_id"))
            str1 = this.mParamsBundle.getString("message_id");
          if (this.mParamsBundle.containsKey("name"))
            str2 = this.mParamsBundle.getString("name");
          if (this.mParamsBundle.containsKey("action"))
            str3 = this.mParamsBundle.getString("action");
        }
        Logger.i(this.TAG, "更新" + this.mUri + " 中某一条消息数据！" + str1);
        String[] arrayOfString1 = new String[1];
        arrayOfString1[0] = ("" + str1);
        String[] arrayOfString2 = { "topic", "buttons" };
        Cursor localCursor = this.mDbProvider.query(this.mUri, arrayOfString2, "message_id=?", arrayOfString1, null);
        String str4 = "";
        if ((localCursor != null) && (localCursor.moveToFirst()))
        {
          String str5 = localCursor.getString(localCursor.getColumnIndexOrThrow("buttons"));
          Logger.i(this.TAG, "buttons=" + str5 + " btnAction=" + str3);
          if (!TextUtils.isEmpty(str5))
          {
            String[] arrayOfString4 = str5.split("::");
            if (arrayOfString4 != null)
            {
              int j = arrayOfString4.length;
              k = 0;
              if (k < j)
              {
                String str7 = arrayOfString4[k];
                if (TextUtils.isEmpty(str7))
                  break label721;
                String[] arrayOfString5 = str7.split("##");
                String str8 = "";
                if (arrayOfString5 != null)
                {
                  int m = arrayOfString5.length;
                  if (m > 1)
                  {
                    if ((TextUtils.isEmpty(str3)) || (!str3.equals(arrayOfString5[0])))
                      break label727;
                    arrayOfString5[1] = str2;
                    break label727;
                    if (n < m)
                    {
                      str8 = str8 + arrayOfString5[n];
                      if (n + 1 >= m)
                        break label733;
                      str8 = str8 + "##";
                      break label733;
                    }
                  }
                  else if (1 == m)
                  {
                    str8 = arrayOfString5[0];
                  }
                }
                str4 = str4 + str8;
                if (k + 1 >= j)
                  break label721;
                str4 = str4 + "::";
                break label721;
              }
            }
          }
          if (localCursor.moveToNext())
            continue;
        }
        else
        {
          Logger.i(this.TAG, "newBtnArr=" + str4);
          if (localCursor != null)
            localCursor.close();
          ContentValues localContentValues = new ContentValues();
          String str6 = getTopic();
          String[] arrayOfString3 = new String[1];
          arrayOfString3[0] = ("" + str1);
          localContentValues.put("buttons", str4);
          int i = this.mDbProvider.update(this.mUri, localContentValues, "message_id=?", arrayOfString3);
          Logger.i(this.TAG, "共更新" + str6 + "类型的消息" + i + "条！");
          cleanUp();
          this.mDbProvider.closeDB();
          if (this.mNotifier != null)
            this.mNotifier.finishUpdatingMessageValue(str3, this.mParamsBundle);
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        if (this.mNotifier == null)
          continue;
        this.mNotifier.onError(str3, "fail to update db");
        return;
      }
      label721: k++;
      continue;
      label727: int n = 0;
      continue;
      label733: n++;
    }
  }

  public String getTopic()
  {
    String str = "";
    if ((this.mParamsBundle != null) && (this.mParamsBundle.containsKey("topic")))
      str = this.mParamsBundle.getString("topic");
    return str;
  }

  public void initUriMap()
  {
    Uri localUri = MessageSystemV3Columns.getUri();
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.RESERVE_SPECIAL_TOPIC, localUri);
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.RESERVE_TURN_PLAY, localUri);
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_BACK_PLAY, localUri);
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_BACK_PLAY_CANCEL, localUri);
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_CHASE_DRAMA, localUri);
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_CHASE_DRAMA_CANCEL, localUri);
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_LIVE_SHOW, localUri);
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_LIVE_SHOW_CANCEL, localUri);
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_WISH_ORDER, localUri);
    this.mUriMap.put(IMQTTMessageDBUpdater.TopicTableUpdateActionType.ORDER_WISH_ORDER_CANCEL, localUri);
  }

  public void runSubTask(IMQTTMessageDBUpdater.TopicTableUpdateActionType paramTopicTableUpdateActionType)
  {
    this.mUri = ((Uri)this.mUriMap.get(paramTopicTableUpdateActionType));
    this.mType = paramTopicTableUpdateActionType;
    this.mSelf.start();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.threads.UpdateMessageValueTask
 * JD-Core Version:    0.6.2
 */