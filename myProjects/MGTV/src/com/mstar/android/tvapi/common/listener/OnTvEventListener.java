package com.mstar.android.tvapi.common.listener;

public abstract interface OnTvEventListener
{
  public abstract boolean onAtscPopupDialog(int paramInt1, int paramInt2, int paramInt3);

  public abstract boolean onDeadthEvent(int paramInt1, int paramInt2, int paramInt3);

  public abstract boolean onDtvReadyPopupDialog(int paramInt1, int paramInt2, int paramInt3);

  public abstract boolean onScartMuteOsdMode(int paramInt);

  public abstract boolean onScreenSaverMode(int paramInt1, int paramInt2, int paramInt3);

  public abstract boolean onSignalLock(int paramInt);

  public abstract boolean onSignalUnlock(int paramInt);

  public abstract boolean onUnityEvent(int paramInt1, int paramInt2, int paramInt3);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.common.listener.OnTvEventListener
 * JD-Core Version:    0.6.2
 */