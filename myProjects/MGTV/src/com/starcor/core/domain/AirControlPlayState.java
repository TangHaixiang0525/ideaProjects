package com.starcor.core.domain;

import android.os.Bundle;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.utils.Logger;

public class AirControlPlayState
{
  public static final AirControlPlayState NULL = new AirControlPlayState();
  public static final String STATE_PAUSE = "in_pause";
  public static final String STATE_PLAY = "in_play";
  public static final String STATE_STOP = "not_play";
  public static final String TYPE_LIVE = "live";
  public static final String TYPE_PLAYBACK = "playback";
  public static final String TYPE_TSTV = "tstv";
  public static final String TYPE_VOD = "vod";
  StringParams begin = new StringParams("begin");
  StringParams now_pos = new StringParams("now_pos");
  StringParams program_name = new StringParams("program_name");
  StringParams state = new StringParams("state");
  StringParams time_len = new StringParams("time_len");
  StringParams type = new StringParams("type");
  StringParams video_id = new StringParams("video_id");
  StringParams video_index = new StringParams("video_index");
  StringParams video_index_name = new StringParams("video_index_name");
  StringParams video_name = new StringParams("video_name");
  StringParams video_type = new StringParams("video_type");

  public AirControlPlayState()
  {
    initDefaultData();
  }

  private void initDefaultData()
  {
    this.state.setValue("not_play");
    this.video_index.setValue("0");
    this.time_len.setValue("0");
  }

  public void fillBundle(Bundle paramBundle)
  {
    if (paramBundle == null)
    {
      Logger.i("AirMessage", "fillBundle data is null");
      return;
    }
    paramBundle.putString(getState().getName(), getState().getValue());
    paramBundle.putString(getType().getName(), getType().getValue());
    paramBundle.putString(getVideo_id().getName(), getVideo_id().getValue());
    paramBundle.putString(getVideo_type().getName(), getVideo_type().getValue());
    paramBundle.putString(getVideo_index().getName(), getVideo_index().getValue());
    paramBundle.putString(getVideo_name().getName(), getVideo_name().getValue());
    paramBundle.putString(getVideo_index_name().getName(), getVideo_index_name().getValue());
    paramBundle.putString(getProgram_name().getName(), getProgram_name().getValue());
    paramBundle.putString(getTime_len().getName(), getTime_len().getValue());
    paramBundle.putString(getNow_pos().getName(), getNow_pos().getValue());
    paramBundle.putString(getBegin().getName(), getBegin().getValue());
  }

  public StringParams getBegin()
  {
    return this.begin;
  }

  public StringParams getNow_pos()
  {
    return this.now_pos;
  }

  public StringParams getProgram_name()
  {
    return this.program_name;
  }

  public StringParams getState()
  {
    return this.state;
  }

  public StringParams getTime_len()
  {
    return this.time_len;
  }

  public StringParams getType()
  {
    return this.type;
  }

  public StringParams getVideo_id()
  {
    return this.video_id;
  }

  public StringParams getVideo_index()
  {
    return this.video_index;
  }

  public StringParams getVideo_index_name()
  {
    return this.video_index_name;
  }

  public StringParams getVideo_name()
  {
    return this.video_name;
  }

  public StringParams getVideo_type()
  {
    return this.video_type;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AirControlPlayState
 * JD-Core Version:    0.6.2
 */