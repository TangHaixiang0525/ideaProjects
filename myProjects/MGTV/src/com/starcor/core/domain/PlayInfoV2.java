package com.starcor.core.domain;

import android.text.TextUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlayInfoV2
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int IsOtherCdn = 0;
  public String activity_id = "";
  public String backUrl = "";
  public String begin_time = "";
  public int dimensions;
  public String fileId = "";
  public String fileType = "";
  public String import_id = "";
  public String import_source = "";
  public String index_id = "";
  public String index_import_id = "";
  public int index_num;
  public String index_serial_id = "";
  public List<InteractiveMetaData> interactiveMetaDataList;
  public String liveId = "";
  public int live_video_type = 0;
  public List<MediaTimeNode> mediaTimeNodeList;
  public String media_id = "";
  public String original_id = "";
  public String playUrl = "";
  public String quality = "";
  public String serial_id = "";
  public AuthState state;
  public String svrip = "";
  public String time_len = "";
  public String ts_default_pos = "";
  public String ts_limit_max = "";
  public String ts_limit_min = "";
  public String type_name = "";
  public ArrayList<UserKey> videoKeyList;
  public String video_id = "";

  public String getUserValueByKey(String paramString)
  {
    if (TextUtils.isEmpty(paramString));
    UserKey localUserKey;
    do
    {
      Iterator localIterator;
      while (!localIterator.hasNext())
      {
        return "";
        localIterator = this.videoKeyList.iterator();
      }
      localUserKey = (UserKey)localIterator.next();
    }
    while (!paramString.equals(localUserKey.key));
    return localUserKey.value;
  }

  public String toString()
  {
    return "PlayInfoV2{state=" + this.state + ", video_id='" + this.video_id + '\'' + ", import_id='" + this.import_id + '\'' + ", serial_id='" + this.serial_id + '\'' + ", svrip='" + this.svrip + '\'' + ", live_video_type=" + this.live_video_type + ", index_id='" + this.index_id + '\'' + ", index_num=" + this.index_num + ", index_import_id='" + this.index_import_id + '\'' + ", index_serial_id='" + this.index_serial_id + '\'' + ", media_id='" + this.media_id + '\'' + ", playUrl='" + this.playUrl + '\'' + ", backUrl='" + this.backUrl + '\'' + ", liveId='" + this.liveId + '\'' + ", fileType='" + this.fileType + '\'' + ", quality='" + this.quality + '\'' + ", begin_time='" + this.begin_time + '\'' + ", time_len='" + this.time_len + '\'' + ", ts_limit_min='" + this.ts_limit_min + '\'' + ", ts_limit_max='" + this.ts_limit_max + '\'' + ", ts_default_pos='" + this.ts_default_pos + '\'' + ", dimensions=" + this.dimensions + ", fileId='" + this.fileId + '\'' + ", import_source='" + this.import_source + '\'' + ", original_id='" + this.original_id + '\'' + ", mediaTimeNodeList=" + this.mediaTimeNodeList + ", videoKeyList=" + this.videoKeyList + ", IsOtherCdn=" + this.IsOtherCdn + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.PlayInfoV2
 * JD-Core Version:    0.6.2
 */