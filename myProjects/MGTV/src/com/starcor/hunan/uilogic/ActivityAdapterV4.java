package com.starcor.hunan.uilogic;

import android.content.Context;
import com.starcor.hunan.ExtWebActivity;
import com.starcor.hunan.MainActivityV2;
import com.starcor.hunan.MplayerV2;
import com.starcor.hunan.MyMediaActivityV2;
import com.starcor.hunan.TimeSearchActivityV4;
import com.starcor.hunan.VideoListActivityV2;
import com.starcor.hunan.msgsys.activity.MessageSystemActivityV3;
import com.starcor.hunan.widget.ExtWebDialog;
import com.starcor.hunan.widget.WebDialogBase;

public class ActivityAdapterV4 extends ActivityAdapter
{
  public ActivityAdapterV4()
  {
    this.MPlayer_INTENT_EXITAPP_FLAG = "exit_tcl_app";
    this.MPlayer_INTENT_EXIT_APP = "exit_app";
    this.MPlayer_INTENT_FLAG = "PINTENT";
  }

  public void MainActivity_putPosterToCache(String paramString, Object paramObject)
  {
    MainActivityV2.putPosterToCache(paramString, paramObject);
  }

  public WebDialogBase createWebDialog(Context paramContext, int paramInt)
  {
    return new ExtWebDialog(paramContext, paramInt);
  }

  public Class<?> getMPlayer()
  {
    return MplayerV2.class;
  }

  public Class<?> getMainActivity()
  {
    return MainActivityV2.class;
  }

  public Class<?> getMessageSystemActivity()
  {
    return MessageSystemActivityV3.class;
  }

  public Class<?> getMyMediaActivity()
  {
    return MyMediaActivityV2.class;
  }

  public Class<?> getSearchActivity()
  {
    return TimeSearchActivityV4.class;
  }

  public Class<?> getVideoListActivity()
  {
    return VideoListActivityV2.class;
  }

  public Class<?> getWebActivity()
  {
    return ExtWebActivity.class;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.uilogic.ActivityAdapterV4
 * JD-Core Version:    0.6.2
 */