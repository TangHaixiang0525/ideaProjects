package com.starcor.player;

import android.view.View;

public abstract class MediaPlayerSurfaceAdapter
{
  public abstract void destroy();

  public abstract View getView();

  public abstract boolean isReady();
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.player.MediaPlayerSurfaceAdapter
 * JD-Core Version:    0.6.2
 */