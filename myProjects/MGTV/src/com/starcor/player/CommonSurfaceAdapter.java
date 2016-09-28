package com.starcor.player;

import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import com.starcor.core.utils.Logger;

public class CommonSurfaceAdapter extends MediaPlayerSurfaceAdapter
{
  private static final String TAG = CommonSurfaceAdapter.class.getSimpleName();
  SurfaceHolder _holder;
  MediaPlayerSurfaceSite _site;
  SurfaceView _surface;

  public CommonSurfaceAdapter(MediaPlayerSurfaceSite paramMediaPlayerSurfaceSite)
  {
    this._site = paramMediaPlayerSurfaceSite;
    this._surface = new SurfaceView(paramMediaPlayerSurfaceSite.getSiteContext());
    SurfaceHolder localSurfaceHolder = this._surface.getHolder();
    localSurfaceHolder.addCallback(new SurfaceHolder.Callback()
    {
      public void surfaceChanged(SurfaceHolder paramAnonymousSurfaceHolder, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        Logger.d(CommonSurfaceAdapter.TAG, "surfaceChanged");
        CommonSurfaceAdapter.this._holder = paramAnonymousSurfaceHolder;
        CommonSurfaceAdapter.this._site.onSurfaceChanged();
      }

      public void surfaceCreated(SurfaceHolder paramAnonymousSurfaceHolder)
      {
        Logger.d(CommonSurfaceAdapter.TAG, "surfaceCreated");
        CommonSurfaceAdapter.this._holder = paramAnonymousSurfaceHolder;
        CommonSurfaceAdapter.this._site.onSurfaceCreated();
      }

      public void surfaceDestroyed(SurfaceHolder paramAnonymousSurfaceHolder)
      {
        Logger.d(CommonSurfaceAdapter.TAG, "surfaceDestroyed");
        CommonSurfaceAdapter.this._holder = null;
        CommonSurfaceAdapter.this._site.onSurfaceDestroy();
      }
    });
    localSurfaceHolder.setType(3);
    localSurfaceHolder.setSizeFromLayout();
    this._site.addSurface(this);
  }

  static SurfaceHolder SurfaceHolderFromSurfaceSite(MediaPlayerSurfaceSite paramMediaPlayerSurfaceSite)
  {
    MediaPlayerSurfaceAdapter localMediaPlayerSurfaceAdapter = paramMediaPlayerSurfaceSite.getCurrentSurface();
    if ((localMediaPlayerSurfaceAdapter == null) || (!(localMediaPlayerSurfaceAdapter instanceof CommonSurfaceAdapter)))
    {
      if (localMediaPlayerSurfaceAdapter != null)
      {
        paramMediaPlayerSurfaceSite.removeSurface(localMediaPlayerSurfaceAdapter);
        localMediaPlayerSurfaceAdapter.destroy();
      }
      return new CommonSurfaceAdapter(paramMediaPlayerSurfaceSite).getSurfaceHolder();
    }
    return ((CommonSurfaceAdapter)localMediaPlayerSurfaceAdapter).getSurfaceHolder();
  }

  public void destroy()
  {
  }

  public SurfaceView getSurface()
  {
    return this._surface;
  }

  public SurfaceHolder getSurfaceHolder()
  {
    return this._holder;
  }

  public View getView()
  {
    return this._surface;
  }

  public boolean isReady()
  {
    return this._holder != null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.player.CommonSurfaceAdapter
 * JD-Core Version:    0.6.2
 */