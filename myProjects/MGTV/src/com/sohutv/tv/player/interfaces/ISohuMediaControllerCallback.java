package com.sohutv.tv.player.interfaces;

public abstract interface ISohuMediaControllerCallback
{
  public abstract int getBufferPercent();

  public abstract int getCurrentPosition();

  public abstract int getDuration();

  public abstract boolean isPlaying();

  public abstract void pause();

  public abstract void seekTo(int paramInt);

  public abstract void start();
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.interfaces.ISohuMediaControllerCallback
 * JD-Core Version:    0.6.2
 */