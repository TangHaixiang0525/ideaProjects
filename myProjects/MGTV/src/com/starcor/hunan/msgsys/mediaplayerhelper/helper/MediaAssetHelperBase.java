package com.starcor.hunan.msgsys.mediaplayerhelper.helper;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.db.DBProvider;
import com.starcor.hunan.msgsys.dbupdater.MQTTMessageDBUpdater;
import com.starcor.hunan.msgsys.interfaces.IMQTTMessageDBUpdater;
import com.starcor.hunan.msgsys.interfaces.IMQTTMessageDBUpdater.TopicTableUpdateActionType;

public abstract class MediaAssetHelperBase
{
  protected String TAG = MediaAssetHelperBase.class.getSimpleName();
  protected String mAction = "";
  protected Context mContext = null;
  protected MediaAssetHelperCallback mMediaAssetHelperListener = null;
  protected Bundle mParamsBundle = null;

  protected MediaAssetHelperBase(Context paramContext, String paramString)
  {
    this.mContext = paramContext;
    this.TAG = paramString;
  }

  protected MediaAssetHelperBase(String paramString)
  {
    this.TAG = paramString;
  }

  public String getAction()
  {
    return this.mAction;
  }

  public Context getContext()
  {
    return this.mContext;
  }

  public String getHelperName()
  {
    return this.TAG;
  }

  public MediaAssetHelperCallback getListener()
  {
    return this.mMediaAssetHelperListener;
  }

  public Bundle getParamsBundle()
  {
    return this.mParamsBundle;
  }

  public void setAction(String paramString)
  {
    this.mAction = paramString;
  }

  public void setContext(Context paramContext)
  {
    this.mContext = paramContext;
  }

  public void setListener(MediaAssetHelperCallback paramMediaAssetHelperCallback)
  {
    this.mMediaAssetHelperListener = paramMediaAssetHelperCallback;
  }

  public void setParamsBundle(Bundle paramBundle)
  {
    this.mParamsBundle = paramBundle;
  }

  public void startExecuteAction()
  {
    startSubAction();
    String str1 = this.mParamsBundle.getString("message_id");
    String str2 = this.mParamsBundle.getString("topic");
    Logger.i(this.TAG, "startExecuteAction msgId = " + str1 + " ,msgTopic = " + str2);
    if ((TextUtils.isEmpty(str1)) || (TextUtils.isEmpty(str2)));
    while (true)
    {
      return;
      IMQTTMessageDBUpdater.TopicTableUpdateActionType localTopicTableUpdateActionType;
      if (str2.toLowerCase().contains("private"))
        localTopicTableUpdateActionType = IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_PRIVATE_TOPIC_MESSAGE_READ;
      while (localTopicTableUpdateActionType != null)
      {
        MQTTMessageDBUpdater.getInstance(new DBProvider(getContext()), null, str1, localTopicTableUpdateActionType, null).runTask();
        return;
        if (str2.toLowerCase().contains("public"))
        {
          localTopicTableUpdateActionType = IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_PUBLIC_TOPIC_MESSAGE_READ;
        }
        else
        {
          boolean bool = str2.toLowerCase().contains("reserve");
          localTopicTableUpdateActionType = null;
          if (bool)
            localTopicTableUpdateActionType = IMQTTMessageDBUpdater.TopicTableUpdateActionType.SET_RESERVE_TOPIC_MESSAGE_READ;
        }
      }
    }
  }

  protected abstract void startSubAction();
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.mediaplayerhelper.helper.MediaAssetHelperBase
 * JD-Core Version:    0.6.2
 */