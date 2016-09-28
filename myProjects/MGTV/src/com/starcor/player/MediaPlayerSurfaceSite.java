package com.starcor.player;

import android.content.Context;

public abstract interface MediaPlayerSurfaceSite
{
  public abstract boolean addSurface(MediaPlayerSurfaceAdapter paramMediaPlayerSurfaceAdapter);

  public abstract MediaPlayerSurfaceAdapter getCurrentSurface();

  public abstract Context getSiteContext();

  public abstract void onSurfaceChanged();

  public abstract void onSurfaceCreated();

  public abstract void onSurfaceDestroy();

  public abstract boolean removeSurface(MediaPlayerSurfaceAdapter paramMediaPlayerSurfaceAdapter);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.player.MediaPlayerSurfaceSite
 * JD-Core Version:    0.6.2
 */