package com.mstar.android.tvapi.atv;

import com.mstar.android.tvapi.atv.listener.OnAtvPlayerEventListener;
import com.mstar.android.tvapi.common.TvPlayer;
import com.mstar.android.tvapi.common.exception.TvCommonException;

public abstract interface AtvPlayer extends TvPlayer
{
  public abstract boolean disableAft()
    throws TvCommonException;

  public abstract boolean enableAft()
    throws TvCommonException;

  public abstract void initAtvVif()
    throws TvCommonException;

  public abstract boolean isAftEnabled()
    throws TvCommonException;

  public abstract boolean saveAtvProgram(int paramInt)
    throws TvCommonException;

  public abstract void setChannelChangeFreezeMode(boolean paramBoolean)
    throws TvCommonException;

  public abstract void setOnAtvPlayerEventListener(OnAtvPlayerEventListener paramOnAtvPlayerEventListener);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.atv.AtvPlayer
 * JD-Core Version:    0.6.2
 */