package com.mstar.android.tvapi.dtv.common;

import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.dtv.vo.DtvSubtitleInfo;

public final class SubtitleManager
{
  protected static SubtitleManager getInstance()
  {
    throw new RuntimeException("stub");
  }

  public native boolean closeSubtitle()
    throws TvCommonException;

  protected void finalize()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public native DtvSubtitleInfo getSubtitleInfo()
    throws TvCommonException;

  public native boolean openSubtitle(int paramInt)
    throws TvCommonException;

  public native void setDebugMode(boolean paramBoolean)
    throws TvCommonException;

  public void setOnSubtitleEventListener(OnSubtitleEventListener paramOnSubtitleEventListener)
  {
    throw new RuntimeException("stub");
  }

  public static abstract interface OnSubtitleEventListener
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.dtv.common.SubtitleManager
 * JD-Core Version:    0.6.2
 */