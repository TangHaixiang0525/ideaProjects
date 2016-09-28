package com.sohu.app.ads.sdk.core;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.sohu.app.ads.sdk.c.a;
import com.sohu.app.ads.sdk.iterface.IVideoAdPlayer;
import com.sohu.app.ads.sdk.model.VideoProgressUpdate;
import com.sohu.app.ads.sdk.res.Const;
import java.util.TimerTask;

class u extends TimerTask
{
  u(s params)
  {
  }

  public void run()
  {
    try
    {
      if ((s.a(this.a) != null) && (s.a(this.a).e().playing()))
      {
        VideoProgressUpdate localVideoProgressUpdate = s.a(this.a).e().getProgress();
        if (localVideoProgressUpdate.getCurrentTime() <= 0.0F)
          return;
        Message localMessage = new Message();
        localMessage.what = 1;
        float f1 = localVideoProgressUpdate.getCurrentTime();
        float f2 = s.a(this.a).a() - f1;
        Bundle localBundle = new Bundle();
        localBundle.putFloat("time", f2);
        localBundle.putFloat("currentTime", f1);
        localMessage.setData(localBundle);
        if (s.d(this.a) == null)
          return;
        s.d(this.a).sendMessage(localMessage);
        return;
      }
      a.b("SOHUSDK", "adClicked=" + Const.adClicked);
      if ((Const.adClicked) || (s.d()))
      {
        Const.TimeOutStart = System.currentTimeMillis();
        a.b("SOHUSDK", "Const.adClicked || mPaused:重置超时时间");
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
    s.d(this.a).sendEmptyMessage(2);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdk.core.u
 * JD-Core Version:    0.6.2
 */