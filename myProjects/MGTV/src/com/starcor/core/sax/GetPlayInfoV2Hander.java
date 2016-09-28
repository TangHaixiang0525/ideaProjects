package com.starcor.core.sax;

import android.text.TextUtils;
import com.starcor.core.domain.AuthState;
import com.starcor.core.domain.InteractiveMetaData;
import com.starcor.core.domain.MediaTimeNode;
import com.starcor.core.domain.PlayInfoV2;
import com.starcor.core.domain.UserKey;
import com.starcor.core.utils.Logger;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetPlayInfoV2Hander extends DefaultHandler
{
  InteractiveMetaData interactiveMetaData;
  boolean isInteractiveMetaData = false;
  MediaTimeNode mediaData;
  PlayInfoV2 playInfo;
  private StringBuilder sb = new StringBuilder();
  AuthState state;
  String value = "";
  UserKey videoKey;
  List<UserKey> videoKeyList;

  private int optInt(Attributes paramAttributes, String paramString)
  {
    return optInt(paramAttributes, paramString, 0);
  }

  private int optInt(Attributes paramAttributes, String paramString, int paramInt)
  {
    int i = paramInt;
    if ((!TextUtils.isEmpty(paramString)) && (paramAttributes != null));
    try
    {
      int j = Integer.parseInt(paramAttributes.getValue(paramString));
      i = j;
      return i;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return i;
  }

  private String optString(Attributes paramAttributes, String paramString)
  {
    return optString(paramAttributes, paramString, "");
  }

  private String optString(Attributes paramAttributes, String paramString1, String paramString2)
  {
    Object localObject = paramString2;
    if ((!TextUtils.isEmpty(paramString1)) && (paramAttributes != null))
    {
      String str = paramAttributes.getValue(paramString1);
      if (!TextUtils.isEmpty(str))
        localObject = str;
    }
    return localObject;
  }

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    if (paramInt2 > 0)
    {
      this.sb.append(paramArrayOfChar, paramInt1, paramInt2);
      String str = this.sb.toString();
      if ((!TextUtils.isEmpty(str)) && (paramArrayOfChar[0] != '\n'))
        this.value = str;
    }
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    if (paramString2.equals("k"))
    {
      this.videoKey = new UserKey();
      this.videoKey.key = this.value;
    }
    if (paramString2.equals("v"))
    {
      this.videoKey.value = this.value;
      if (this.videoKey.key.equals("isothercdn"))
      {
        this.playInfo.IsOtherCdn = Integer.valueOf(this.value).intValue();
        Logger.e("IsOtherCdn=" + this.playInfo.IsOtherCdn);
        this.videoKeyList.add(this.videoKey);
      }
    }
    else
    {
      if (paramString2.equals("arg"))
        this.playInfo.videoKeyList.addAll(this.videoKeyList);
      if (paramString2.equalsIgnoreCase("item"))
      {
        if (!this.isInteractiveMetaData)
          break label255;
        if (this.playInfo.interactiveMetaDataList == null)
          this.playInfo.interactiveMetaDataList = new ArrayList();
        this.playInfo.interactiveMetaDataList.add(this.interactiveMetaData);
      }
    }
    while (true)
    {
      super.endElement(paramString1, paramString2, paramString3);
      return;
      if ((!this.videoKey.key.equals("svrip")) || ("svrip".equals(this.value)))
        break;
      this.playInfo.svrip = this.value;
      break;
      label255: if (this.playInfo.mediaTimeNodeList == null)
        this.playInfo.mediaTimeNodeList = new ArrayList();
      this.playInfo.mediaTimeNodeList.add(this.mediaData);
    }
  }

  public PlayInfoV2 getPlayInfo()
  {
    return this.playInfo;
  }

  public void startDocument()
    throws SAXException
  {
    this.playInfo = new PlayInfoV2();
    if (this.playInfo != null)
    {
      this.playInfo.mediaTimeNodeList = new ArrayList();
      this.playInfo.interactiveMetaDataList = new ArrayList();
      this.playInfo.videoKeyList = new ArrayList();
    }
    this.videoKeyList = new ArrayList();
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    this.sb.setLength(0);
    if (paramString2.equals("result"))
    {
      this.state = new AuthState();
      this.state.state = optInt(paramAttributes, "state");
      this.state.reason = optString(paramAttributes, "reason");
      this.state.order_url = optString(paramAttributes, "order_url");
      this.state.is_url_jump = optInt(paramAttributes, "is_url_jump");
      this.playInfo.state = this.state;
    }
    do
    {
      do
      {
        do
        {
          return;
          if (paramString2.equalsIgnoreCase("video"))
          {
            this.playInfo.video_id = optString(paramAttributes, "id");
            this.playInfo.import_id = optString(paramAttributes, "import_id");
            this.playInfo.serial_id = optString(paramAttributes, "series_id");
            this.playInfo.type_name = optString(paramAttributes, "type_name");
            this.playInfo.activity_id = optString(paramAttributes, "activity_id");
            this.playInfo.live_video_type = optInt(paramAttributes, "live_video_type", 1);
            return;
          }
          if (paramString2.equalsIgnoreCase("index"))
          {
            this.playInfo.index_id = optString(paramAttributes, "id");
            this.playInfo.index_num = optInt(paramAttributes, "index_num");
            this.playInfo.index_import_id = optString(paramAttributes, "import_id");
            this.playInfo.index_serial_id = optString(paramAttributes, "series_id");
            return;
          }
          if (paramString2.equalsIgnoreCase("media"))
          {
            this.playInfo.media_id = optString(paramAttributes, "id");
            this.playInfo.liveId = optString(paramAttributes, "cameraposition");
            this.playInfo.playUrl = optString(paramAttributes, "url");
            this.playInfo.backUrl = optString(paramAttributes, "url_backup");
            this.playInfo.fileType = optString(paramAttributes, "filetype");
            this.playInfo.quality = optString(paramAttributes, "quality");
            this.playInfo.begin_time = optString(paramAttributes, "begin_play_time");
            this.playInfo.time_len = optString(paramAttributes, "play_time_len");
            this.playInfo.ts_limit_min = optString(paramAttributes, "ts_limit_min");
            this.playInfo.ts_limit_max = optString(paramAttributes, "ts_limit_max");
            this.playInfo.ts_default_pos = optString(paramAttributes, "ts_default_pos");
            this.playInfo.dimensions = optInt(paramAttributes, "dimensions");
            this.playInfo.fileId = optString(paramAttributes, "file_id", "0");
            this.playInfo.import_source = optString(paramAttributes, "import_source");
            this.playInfo.original_id = optString(paramAttributes, "original_id");
            return;
          }
          if (!paramString2.equalsIgnoreCase("metadata"))
            break;
          this.isInteractiveMetaData = false;
        }
        while (this.playInfo.mediaTimeNodeList != null);
        this.playInfo.mediaTimeNodeList = new ArrayList();
        return;
        if ((!paramString2.equalsIgnoreCase("interact_matadata")) && (!paramString2.equalsIgnoreCase("interact_metadata")))
          break;
        this.isInteractiveMetaData = true;
      }
      while (this.playInfo.interactiveMetaDataList != null);
      this.playInfo.interactiveMetaDataList = new ArrayList();
      return;
      if (paramString2.equalsIgnoreCase("item"))
      {
        if (this.isInteractiveMetaData)
        {
          this.interactiveMetaData = new InteractiveMetaData();
          this.interactiveMetaData.begin = optInt(paramAttributes, "begin");
          this.interactiveMetaData.end = optInt(paramAttributes, "end");
          this.interactiveMetaData.type = optInt(paramAttributes, "type");
          this.interactiveMetaData.name = optString(paramAttributes, "name");
          this.interactiveMetaData.img = optString(paramAttributes, "img");
          this.interactiveMetaData.content = optString(paramAttributes, "content");
          this.interactiveMetaData.url = optString(paramAttributes, "url");
          return;
        }
        this.mediaData = new MediaTimeNode();
        this.mediaData.begin = optInt(paramAttributes, "begin");
        this.mediaData.end = optInt(paramAttributes, "end");
        this.mediaData.type = optInt(paramAttributes, "type");
        this.mediaData.name = optString(paramAttributes, "name");
        this.mediaData.img = optString(paramAttributes, "img");
        return;
      }
    }
    while (!paramString2.equalsIgnoreCase("arg"));
    this.videoKey = new UserKey();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetPlayInfoV2Hander
 * JD-Core Version:    0.6.2
 */