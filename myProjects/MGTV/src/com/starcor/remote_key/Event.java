package com.starcor.remote_key;

import java.util.HashMap;
import org.xml.sax.Attributes;

public class Event
{
  HashMap<String, String> args = new HashMap();
  public String commandApi;
  public String commandStr;
  public int id;
  public boolean needReply;
  public EventType type;

  static
  {
    EventFactory.registerBuilder(Event.class, new _Factory());
  }

  public static Event build(byte[] paramArrayOfByte)
    throws Exception
  {
    return (Event)EventFactory.build(Event.class, paramArrayOfByte);
  }

  static enum EventType
  {
    static
    {
      keyEvent = new EventType("keyEvent", 1);
      textEvent = new EventType("textEvent", 2);
      getDeviceInfo = new EventType("getDeviceInfo", 3);
      getUserInfo = new EventType("getUserInfo", 4);
      showTipEvent = new EventType("showTipEvent", 5);
      playVideo = new EventType("playVideo", 6);
      getPlayState = new EventType("getPlayState", 7);
      pauseVideo = new EventType("pauseVideo", 8);
      resumeVideo = new EventType("resumeVideo", 9);
      stopVideo = new EventType("stopVideo", 10);
      seekVideo = new EventType("seekVideo", 11);
      refreshUserPlaylist = new EventType("refreshUserPlaylist", 12);
      getUserPlaylist = new EventType("getUserPlaylist", 13);
      deleteUserPlaylist = new EventType("deleteUserPlaylist", 14);
      EventType[] arrayOfEventType = new EventType[15];
      arrayOfEventType[0] = unknownEvent;
      arrayOfEventType[1] = keyEvent;
      arrayOfEventType[2] = textEvent;
      arrayOfEventType[3] = getDeviceInfo;
      arrayOfEventType[4] = getUserInfo;
      arrayOfEventType[5] = showTipEvent;
      arrayOfEventType[6] = playVideo;
      arrayOfEventType[7] = getPlayState;
      arrayOfEventType[8] = pauseVideo;
      arrayOfEventType[9] = resumeVideo;
      arrayOfEventType[10] = stopVideo;
      arrayOfEventType[11] = seekVideo;
      arrayOfEventType[12] = refreshUserPlaylist;
      arrayOfEventType[13] = getUserPlaylist;
      arrayOfEventType[14] = deleteUserPlaylist;
    }
  }

  static class _Builder extends EventFactory.ItemBuilder
  {
    Event _event = new Event();

    public _Builder(String paramString, Attributes paramAttributes)
    {
      String str1;
      try
      {
        this._event.id = Integer.parseInt(paramAttributes.getValue("id"));
        str1 = paramAttributes.getValue("func");
        String str2 = paramAttributes.getValue("wait_reply");
        this._event.needReply = "1".equals(str2);
        this._event.commandStr = str1;
        this._event.commandApi = "";
        if ("v_key".equals(str1))
        {
          this._event.type = Event.EventType.keyEvent;
          return;
        }
        if ("text_input".equals(str1))
        {
          this._event.type = Event.EventType.textEvent;
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
      if ("get_device_info".equals(str1))
      {
        this._event.type = Event.EventType.getDeviceInfo;
        return;
      }
      if ("get_user_info".equals(str1))
      {
        this._event.type = Event.EventType.getUserInfo;
        return;
      }
      if ("text_show".equals(str1))
      {
        this._event.type = Event.EventType.showTipEvent;
        return;
      }
      if ("play_video".equals(str1))
      {
        this._event.type = Event.EventType.playVideo;
        return;
      }
      if ("refresh_user_playlist".equals(str1))
      {
        this._event.type = Event.EventType.refreshUserPlaylist;
        return;
      }
      if ("get_user_playlist".equals(str1))
      {
        this._event.type = Event.EventType.getUserPlaylist;
        return;
      }
      if ("delete_user_playlist".equals(str1))
      {
        this._event.type = Event.EventType.deleteUserPlaylist;
        return;
      }
      if ("get_play_state".equals(str1))
      {
        this._event.type = Event.EventType.getPlayState;
        return;
      }
      if ("pause".equals(str1))
      {
        this._event.type = Event.EventType.pauseVideo;
        return;
      }
      if ("resume".equals(str1))
      {
        this._event.type = Event.EventType.resumeVideo;
        return;
      }
      if ("stop".equals(str1))
      {
        this._event.type = Event.EventType.stopVideo;
        return;
      }
      if ("seek".equals(str1))
      {
        this._event.type = Event.EventType.seekVideo;
        return;
      }
      this._event.type = Event.EventType.unknownEvent;
    }

    public Object finalItem()
    {
      return this._event;
    }

    public EventFactory.ItemBuilder pushSubItem(String paramString1, String paramString2, Attributes paramAttributes)
    {
      if ("arg_list".equals(paramString2))
        return new EventFactory.ItemBuilder()
        {
          public Object finalItem()
          {
            return super.finalItem();
          }

          public EventFactory.ItemBuilder pushSubItem(String paramAnonymousString1, String paramAnonymousString2, Attributes paramAnonymousAttributes)
          {
            if (!"arg".equals(paramAnonymousString2))
              return null;
            String str1 = paramAnonymousAttributes.getValue("name");
            String str2 = paramAnonymousAttributes.getValue("value");
            this.val$args.put(str1, str2);
            return new EventFactory.DummyItemBuilder();
          }

          public boolean pushText(String paramAnonymousString1, String paramAnonymousString2)
          {
            return super.pushText(paramAnonymousString1, paramAnonymousString2);
          }
        };
      return super.pushSubItem(paramString1, paramString2, paramAttributes);
    }

    public boolean pushText(String paramString1, String paramString2)
    {
      return super.pushText(paramString1, paramString2);
    }
  }

  static class _Factory extends EventFactory.ResultBuilder
  {
    public EventFactory.ItemBuilder build(String paramString, Attributes paramAttributes)
    {
      if ("event".equals(paramString))
        return new Event._Builder(paramString, paramAttributes);
      return super.build(paramString, paramAttributes);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.remote_key.Event
 * JD-Core Version:    0.6.2
 */