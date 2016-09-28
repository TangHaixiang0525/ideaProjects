package com.mstar.android.tvapi.common;

import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.common.listener.OnCecEventListener;
import com.mstar.android.tvapi.common.vo.CecSetting;
import com.mstar.android.tvapi.common.vo.EnumCecDeviceLa;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumLanguage;

public final class CecManager
{
  protected static CecManager getInstance()
  {
    throw new RuntimeException("stub");
  }

  public final native int deviceListGetItemIndex(int paramInt1, int paramInt2)
    throws TvCommonException;

  public final native String deviceListGetListStr(int paramInt1, int paramInt2)
    throws TvCommonException;

  public final native boolean disableDeviceMenu()
    throws TvCommonException;

  public final native boolean enableDeviceMenu()
    throws TvCommonException;

  protected void finalize()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public final native int getCECListCnt(int paramInt)
    throws TvCommonException;

  public final native CecSetting getCecConfiguration()
    throws TvCommonException;

  public final native String getDeviceName(int paramInt)
    throws TvCommonException;

  protected void release()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public final native boolean sendCecKey(int paramInt)
    throws TvCommonException;

  public final native void setCecConfiguration(CecSetting paramCecSetting);

  public final native void setDebugMode(boolean paramBoolean)
    throws TvCommonException;

  public int setMenuLanguage(TvOsType.EnumLanguage paramEnumLanguage)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public void setOnCecEventListener(OnCecEventListener paramOnCecEventListener)
  {
    throw new RuntimeException("stub");
  }

  public void setStreamPath(EnumCecDeviceLa paramEnumCecDeviceLa)
  {
    throw new RuntimeException("stub");
  }

  protected static enum EVENT
  {
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.common.CecManager
 * JD-Core Version:    0.6.2
 */