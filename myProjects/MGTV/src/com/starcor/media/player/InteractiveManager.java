package com.starcor.media.player;

import android.os.Handler;
import android.os.Message;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.msgsys.data.LiveTopicMessageData;
import com.starcor.hunan.msgsys.data.reservetopic.InteractiveMessage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class InteractiveManager
{
  private static final int MSG_SUBSCRIBE_CHANNEL = 256;
  private static final int QUEUE_MAX_SIZE = 100;
  private static final String TAG = InteractiveManager.class.getSimpleName();
  private static SimpleDateFormat dateFormat;
  private static InteractiveManager manger;
  private Handler handler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default:
      case 256:
      }
      while (true)
      {
        super.handleMessage(paramAnonymousMessage);
        return;
        if (InteractiveManager.this.scribeChannelListener != null)
        {
          InteractiveManager.access$102(InteractiveManager.this, (String)paramAnonymousMessage.obj);
          InteractiveManager.this.scribeChannelListener.subScribeChannel();
        }
      }
    }
  };
  private boolean isSubscribeCommChannel = false;
  private String liveId = "";
  private LiveTopicMessageData messageData;
  private List<InteractiveMessage> msgRcvdList = new LinkedList();
  private SubScribeChannelListener scribeChannelListener;

  public static InteractiveManager getInstance()
  {
    if (manger == null)
      manger = new InteractiveManager();
    return manger;
  }

  private void sendMessage(String paramString)
  {
    if (this.handler != null)
    {
      this.handler.removeMessages(256);
      Message localMessage = this.handler.obtainMessage();
      localMessage.what = 256;
      localMessage.obj = paramString;
      this.handler.sendMessageDelayed(localMessage, 5000L);
    }
  }

  public void addMessage(LiveTopicMessageData paramLiveTopicMessageData)
  {
    while (true)
    {
      try
      {
        if (paramLiveTopicMessageData.message == null)
          break label191;
        if (this.msgRcvdList.size() < 100)
        {
          this.msgRcvdList.add(InteractiveMessage.newFromLiveMsg(paramLiveTopicMessageData));
          if (this.messageData == null)
            this.messageData = paramLiveTopicMessageData;
        }
        else
        {
          Logger.w(TAG, "接收实时消息队列已满，移除首元素. size: " + this.msgRcvdList.size());
          this.msgRcvdList.remove(0);
          this.msgRcvdList.add(InteractiveMessage.newFromLiveMsg(paramLiveTopicMessageData));
          continue;
        }
      }
      finally
      {
      }
      if (dateFormat == null)
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      try
      {
        long l = dateFormat.parse(paramLiveTopicMessageData.publish_time).getTime();
        if (dateFormat.parse(this.messageData.publish_time).getTime() > l)
          continue;
        this.messageData = paramLiveTopicMessageData;
      }
      catch (ParseException localParseException)
      {
        localParseException.printStackTrace();
      }
      continue;
      label191: Logger.e(TAG, "此条消息无实体,抛弃" + paramLiveTopicMessageData);
    }
  }

  public void clear()
  {
  }

  public List<InteractiveMessage> getInteractiveMsgListRcvd()
  {
    try
    {
      List localList = this.msgRcvdList;
      return localList;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public String getLiveId()
  {
    return this.liveId;
  }

  public LiveTopicMessageData getMessageData()
  {
    try
    {
      LiveTopicMessageData localLiveTopicMessageData1 = this.messageData;
      LiveTopicMessageData localLiveTopicMessageData2 = null;
      if (localLiveTopicMessageData1 == null);
      while (true)
      {
        return localLiveTopicMessageData2;
        localLiveTopicMessageData2 = this.messageData.clone();
        this.messageData = null;
      }
    }
    finally
    {
    }
  }

  public boolean isSubscribeCommChannel()
  {
    return this.isSubscribeCommChannel;
  }

  public void setSubScribeChannelListener(SubScribeChannelListener paramSubScribeChannelListener)
  {
    this.scribeChannelListener = paramSubScribeChannelListener;
  }

  public void subscribeChannel(String paramString)
  {
    this.isSubscribeCommChannel = true;
    sendMessage(paramString);
  }

  public void subscribeCommChannel()
  {
    this.isSubscribeCommChannel = true;
    sendMessage("");
  }

  public void unSubScribeChannel()
  {
    this.liveId = "";
    this.messageData = null;
    this.isSubscribeCommChannel = false;
    this.msgRcvdList.clear();
    if (this.scribeChannelListener != null)
      this.scribeChannelListener.unSubScribeChannel();
  }

  public static abstract interface SubScribeChannelListener
  {
    public abstract void subScribeChannel();

    public abstract void unSubScribeChannel();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.media.player.InteractiveManager
 * JD-Core Version:    0.6.2
 */