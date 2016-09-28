package com.starcor.hunan.msgsys.data.reservetopic;

import com.starcor.core.domain.InteractiveMetaData;
import com.starcor.hunan.msgsys.data.LiveTopicMessageData;
import com.starcor.hunan.msgsys.data.LiveTopicMessageData.Message;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

public class InteractiveMessage
{
  public static final int TYPE_LIVE = 2;
  public static final int TYPE_VOD = 1;
  public long beginTime;
  public String body = "";
  public long endTime;
  public String image = "";
  public int msgType;
  public long showTime;
  public String title = "";
  public String webUrl = "";

  public static InteractiveMessage newFromLiveMsg(LiveTopicMessageData paramLiveTopicMessageData)
  {
    InteractiveMessage localInteractiveMessage = null;
    if (paramLiveTopicMessageData != null)
    {
      LiveTopicMessageData.Message localMessage = paramLiveTopicMessageData.message;
      localInteractiveMessage = null;
      if (localMessage != null)
      {
        localInteractiveMessage = new InteractiveMessage();
        localInteractiveMessage.msgType = 2;
        localInteractiveMessage.showTime = paramLiveTopicMessageData.message.show_second;
        localInteractiveMessage.webUrl = paramLiveTopicMessageData.message.dialogway_args;
        localInteractiveMessage.image = paramLiveTopicMessageData.message.launch_image;
        localInteractiveMessage.title = paramLiveTopicMessageData.message.title;
        localInteractiveMessage.body = paramLiveTopicMessageData.message.body;
      }
    }
    return localInteractiveMessage;
  }

  public static InteractiveMessage newFromVodMsg(InteractiveMetaData paramInteractiveMetaData)
  {
    InteractiveMessage localInteractiveMessage = null;
    if (paramInteractiveMetaData != null)
    {
      localInteractiveMessage = new InteractiveMessage();
      localInteractiveMessage.msgType = 1;
      localInteractiveMessage.title = paramInteractiveMetaData.name;
      localInteractiveMessage.image = paramInteractiveMetaData.img;
      localInteractiveMessage.webUrl = paramInteractiveMetaData.url;
      localInteractiveMessage.body = paramInteractiveMetaData.content;
      localInteractiveMessage.beginTime = paramInteractiveMetaData.begin;
      localInteractiveMessage.endTime = paramInteractiveMetaData.end;
    }
    return localInteractiveMessage;
  }

  public InteractiveMessage clone()
  {
    try
    {
      InteractiveMessage localInteractiveMessage = (InteractiveMessage)super.clone();
      return localInteractiveMessage;
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      localCloneNotSupportedException.printStackTrace();
    }
    return null;
  }

  public JSONObject toJSONObject()
  {
    JSONObject localJSONObject = new JSONObject();
    while (true)
    {
      try
      {
        if (this.msgType == 1)
        {
          str = "vod";
          localJSONObject.put("type", str);
          localJSONObject.put("image", this.image);
          localJSONObject.put("webUrl", this.webUrl);
          localJSONObject.put("body", this.body);
          localJSONObject.put("title", this.title);
          if (this.msgType == 2)
          {
            localJSONObject.put("showDur", this.showTime);
            return localJSONObject;
          }
          localJSONObject.put("showBeginTime", this.beginTime);
          localJSONObject.put("showEndTime", this.endTime);
          return localJSONObject;
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        return localJSONObject;
      }
      String str = "live";
    }
  }

  public String toJSONString()
  {
    JSONStringer localJSONStringer = new JSONStringer();
    while (true)
    {
      try
      {
        localJSONStringer.object();
        localJSONStringer.key("type");
        if (this.msgType == 1)
        {
          str = "vod";
          localJSONStringer.value(str);
          localJSONStringer.key("image");
          localJSONStringer.value(this.image);
          localJSONStringer.key("webUrl");
          localJSONStringer.value(this.webUrl);
          localJSONStringer.key("title");
          localJSONStringer.value(this.title);
          localJSONStringer.key("body");
          localJSONStringer.value(this.body);
          if (this.msgType == 2)
          {
            localJSONStringer.key("showDur");
            localJSONStringer.value(this.showTime);
            localJSONStringer.endObject();
            return localJSONStringer.toString();
          }
          localJSONStringer.key("showBeginTime");
          localJSONStringer.value(this.beginTime);
          localJSONStringer.key("showEndTime");
          localJSONStringer.value(this.endTime);
          continue;
        }
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        return "";
      }
      String str = "live";
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.msgsys.data.reservetopic.InteractiveMessage
 * JD-Core Version:    0.6.2
 */