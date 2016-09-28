package com.sohutv.tv.player.interfacesimpl;

import android.util.Log;
import com.sohu.app.ads.sdk.iterface.IVideoAdPlayerCallback;
import com.sohu.logger.SohuLoggerAgent;
import com.sohutv.tv.player.ad.SohuTVAdvertise;
import com.sohutv.tv.player.ad.SohuTVAdvertise.PlaybackState;
import com.sohutv.tv.player.interfaces.IVideoListener;
import java.util.Iterator;
import java.util.List;

public class a
  implements IVideoListener
{
  public static boolean a = true;

  public void onVideoCompletion()
  {
    Log.i("Sohuplayer", "IVideoListener onVideoCompletion()");
    if (SohuTVAdvertise.b == SohuTVAdvertise.PlaybackState.ADPLAYING)
      try
      {
        localIterator = SohuTVAdvertise.c.iterator();
        while (localIterator.hasNext())
          ((IVideoAdPlayerCallback)localIterator.next()).onEnded();
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
    while (SohuTVAdvertise.b != SohuTVAdvertise.PlaybackState.INVIDEO)
    {
      Iterator localIterator;
      return;
    }
    Log.i("Sohuplayer", "IVideoListener onVideoCompletion() INVideo");
    try
    {
      SohuLoggerAgent.getInstance().onLogComplete();
      return;
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
  }

  public void onVideoPrepared()
  {
    Log.i("Sohuplayer", "IVideoListener onVideoPrepared()");
    if (SohuTVAdvertise.b == SohuTVAdvertise.PlaybackState.INVIDEO)
      Log.i("Sohuplayer", "IVideoListener onVideoPrepared() INVideo");
    try
    {
      SohuLoggerAgent.getInstance().onLogStart();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void onVideoStop()
  {
    if (SohuTVAdvertise.b == SohuTVAdvertise.PlaybackState.INVIDEO)
      Log.i("Sohuplayer", "IVideoListener onVideoStop() INVideo");
    try
    {
      SohuLoggerAgent.getInstance().onLogStop();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.interfacesimpl.a
 * JD-Core Version:    0.6.2
 */