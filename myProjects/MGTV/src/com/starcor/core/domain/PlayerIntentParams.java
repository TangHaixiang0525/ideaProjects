package com.starcor.core.domain;

import java.io.Serializable;
import java.util.List;

public class PlayerIntentParams
  implements Serializable, Cloneable
{
  public static final int MODE_LIVE = 1;
  public static final int MODE_NULL = 0;
  public static final int MODE_TSTV_LIVE = 4;
  public static final int MODE_TSTV_LIVE2 = 6;
  public static final int MODE_TSTV_REAL = 5;
  public static final int MODE_TSTV_VOD = 3;
  public static final int MODE_VOD = 2;
  public static final int PLAYBACK_PROGRAM_SOURCE_FROM_PLAYBILL = 1;
  public static final int PLAYBACK_PROGRAM_SOURCE_FROM_RECOMMEND = 2;
  private static final long serialVersionUID = 1L;
  public int autoPlay = 0;
  public String buyUrl;
  public String channel_index;
  public long freePlayTime;
  public String hostMediaVideoId = "";
  public String indexOrder = "";
  public boolean isAd = false;
  public boolean isCalledFromAuto = false;
  public boolean isMediasConPlay = false;
  public boolean isSelectTv = false;
  public boolean is_special = false;
  public String[] lockChargeListNum = null;
  public String mediaQuality = "";
  public int mode = 0;
  public String nns_beginTime = "";
  public String nns_day = "";
  public int nns_index;
  public List<VideoIndex> nns_mediaIndexList;
  public String nns_timeLen = "";
  public TimeMapList nns_timeMap;
  public VideoInfo nns_videoInfo;
  public String nns_video_index_id = "";
  public String nns_video_preview_type = null;
  public String out_play = "apk";
  public int playbackProgramSource = 1;
  public long played_time;
  public long real_default_back_pos = 30L;
  public long real_max_back_time_len = 25200L;
  public long real_min_back_time_len = 30L;
  public String subfix_title = "";
  public UserAuthV2 userAuth;
  public String videoRequestUrl = "";

  public Object clone()
  {
    try
    {
      Object localObject = super.clone();
      return localObject;
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      localCloneNotSupportedException.printStackTrace();
    }
    return null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.PlayerIntentParams
 * JD-Core Version:    0.6.2
 */