package com.sohutv.tv.player.interfaces;

import android.view.SurfaceHolder;

public abstract interface PlayerCallback
{
  public abstract void OnSeekCompleteListener();

  public abstract void adRemainTime(int paramInt);

  public abstract void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3);

  public abstract void surfaceCreated(SurfaceHolder paramSurfaceHolder);

  public abstract void surfaceDestroyed(SurfaceHolder paramSurfaceHolder);

  public abstract void throwableCallBack(Throwable paramThrowable);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.interfaces.PlayerCallback
 * JD-Core Version:    0.6.2
 */