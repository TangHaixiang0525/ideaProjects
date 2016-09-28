package com.sohutv.tv.player.play;

import android.content.Context;
import android.view.SurfaceView;
import com.sohutv.tv.player.interfaces.ISohuMediaControllerCallback;
import com.sohutv.tv.player.interfaces.ISohuTVPlayer;
import com.sohutv.tv.player.interfaces.IVideoListener;
import com.sohutv.tv.player.interfaces.PlayerCallback;

public class a extends SurfaceView
  implements ISohuTVPlayer
{
  public IVideoListener a;

  public a(Context paramContext)
  {
    super(paramContext);
  }

  public int getSCurrentPosition()
  {
    return 0;
  }

  public int getSDuration()
  {
    return 0;
  }

  public int getSVideoHeight()
  {
    return 0;
  }

  public int getSVideoWidth()
  {
    return 0;
  }

  public SurfaceView getSurfaceView()
  {
    return null;
  }

  public boolean isSPlaying()
  {
    return false;
  }

  public void pauseSPlay()
  {
  }

  public void resumeSPlay()
  {
  }

  public void seekSPositionTo(int paramInt)
  {
  }

  public void selectTrack(int paramInt)
  {
  }

  public void setDimension(int paramInt1, int paramInt2)
  {
  }

  public void setDimensionAuto()
  {
  }

  public void setDimensionFullScreen()
  {
  }

  public void setDimensionOriginal()
  {
  }

  public void setDimension_16_9()
  {
  }

  public void setDimension_4_3()
  {
  }

  public void setSMediaController(SohuMediaController paramSohuMediaController, ISohuMediaControllerCallback paramISohuMediaControllerCallback)
  {
  }

  public void setSVideoPath(String paramString)
  {
  }

  public void setVideoListener(IVideoListener paramIVideoListener)
  {
    this.a = paramIVideoListener;
  }

  public void setmIPlayerCallback(PlayerCallback paramPlayerCallback)
  {
  }

  public void startSPlay()
  {
  }

  public void stopSPlay()
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.play.a
 * JD-Core Version:    0.6.2
 */