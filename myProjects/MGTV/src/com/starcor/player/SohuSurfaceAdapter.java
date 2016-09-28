package com.starcor.player;

import android.view.View;
import com.sohutv.tv.player.partner.SohuTvPlayer;

public class SohuSurfaceAdapter extends MediaPlayerSurfaceAdapter
{
  MediaPlayerSurfaceSite _site;
  SohuTvPlayer _sohuPlayer;

  public SohuSurfaceAdapter(MediaPlayerSurfaceSite paramMediaPlayerSurfaceSite)
  {
    this._site = paramMediaPlayerSurfaceSite;
    this._sohuPlayer = new SohuTvPlayer(paramMediaPlayerSurfaceSite.getSiteContext());
    this._site.addSurface(this);
  }

  public static SohuTvPlayer SohuPlayerFromSurfaceSite(MediaPlayerSurfaceSite paramMediaPlayerSurfaceSite)
  {
    MediaPlayerSurfaceAdapter localMediaPlayerSurfaceAdapter = paramMediaPlayerSurfaceSite.getCurrentSurface();
    if ((localMediaPlayerSurfaceAdapter == null) || (!(localMediaPlayerSurfaceAdapter instanceof SohuSurfaceAdapter)))
      if (localMediaPlayerSurfaceAdapter != null)
      {
        paramMediaPlayerSurfaceSite.removeSurface(localMediaPlayerSurfaceAdapter);
        localMediaPlayerSurfaceAdapter.destroy();
      }
    for (SohuSurfaceAdapter localSohuSurfaceAdapter = new SohuSurfaceAdapter(paramMediaPlayerSurfaceSite); ; localSohuSurfaceAdapter = (SohuSurfaceAdapter)localMediaPlayerSurfaceAdapter)
      return localSohuSurfaceAdapter.getPlayer();
  }

  public void destroy()
  {
    this._sohuPlayer.release();
  }

  public SohuTvPlayer getPlayer()
  {
    return this._sohuPlayer;
  }

  public View getView()
  {
    return this._sohuPlayer;
  }

  public boolean isReady()
  {
    return true;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.player.SohuSurfaceAdapter
 * JD-Core Version:    0.6.2
 */