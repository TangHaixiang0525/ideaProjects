package com.mstar.android.tvapi.atv.listener;

import com.mstar.android.tvapi.atv.vo.AtvEventScan;

public abstract interface OnAtvPlayerEventListener
{
  public abstract boolean onAtvAutoTuningScanInfo(int paramInt, AtvEventScan paramAtvEventScan);

  public abstract boolean onAtvManualTuningScanInfo(int paramInt, AtvEventScan paramAtvEventScan);

  public abstract boolean onAtvProgramInfoReady(int paramInt);

  public abstract boolean onSignalLock(int paramInt);

  public abstract boolean onSignalUnLock(int paramInt);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.atv.listener.OnAtvPlayerEventListener
 * JD-Core Version:    0.6.2
 */