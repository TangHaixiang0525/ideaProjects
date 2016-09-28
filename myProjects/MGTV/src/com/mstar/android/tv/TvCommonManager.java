package com.mstar.android.tv;

import android.os.RemoteException;
import com.mstar.android.tvapi.common.listener.OnTvEventListener;
import com.mstar.android.tvapi.common.vo.EnumAtvAudioModeType;
import com.mstar.android.tvapi.common.vo.EnumAudioReturn;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumInputSource;

public class TvCommonManager
{
  public static TvCommonManager getInstance()
  {
    throw new RuntimeException("stub");
  }

  public boolean[] GetInputSourceStatus()
  {
    throw new RuntimeException("stub");
  }

  public void OSD_Set3Dformat(int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public void closeSurfaceView()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public void enterSleepMode(boolean paramBoolean1, boolean paramBoolean2)
  {
    throw new RuntimeException("stub");
  }

  public EnumAtvAudioModeType getAtvMtsMode()
  {
    throw new RuntimeException("stub");
  }

  public EnumAtvAudioModeType getAtvSoundMode()
  {
    throw new RuntimeException("stub");
  }

  public TvOsType.EnumInputSource getCurrentDBInputSource()
  {
    throw new RuntimeException("stub");
  }

  public TvOsType.EnumInputSource getCurrentInputSource()
  {
    throw new RuntimeException("stub");
  }

  public TvOsType.EnumInputSource getCurrentSubInputSource()
  {
    throw new RuntimeException("stub");
  }

  public TvOsType.EnumInputSource getPowerOnSource()
  {
    throw new RuntimeException("stub");
  }

  public int[] getSourceList()
  {
    throw new RuntimeException("stub");
  }

  public void openSurfaceView(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public void rebootSystem(String paramString)
  {
    throw new RuntimeException("stub");
  }

  public void recoverySystem(String paramString)
  {
    throw new RuntimeException("stub");
  }

  public boolean registerOnTvEventListener(OnTvEventListener paramOnTvEventListener)
  {
    throw new RuntimeException("stub");
  }

  public EnumAudioReturn setAtvMtsMode(EnumAtvAudioModeType paramEnumAtvAudioModeType)
  {
    throw new RuntimeException("stub");
  }

  public void setInputSource(TvOsType.EnumInputSource paramEnumInputSource)
  {
    throw new RuntimeException("stub");
  }

  public void setInputSourceMoreArgs(TvOsType.EnumInputSource paramEnumInputSource, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    throw new RuntimeException("stub");
  }

  public boolean setPowerOnSource(TvOsType.EnumInputSource paramEnumInputSource)
  {
    throw new RuntimeException("stub");
  }

  public void setSurfaceView(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public void standbySystem(String paramString)
  {
    throw new RuntimeException("stub");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tv.TvCommonManager
 * JD-Core Version:    0.6.2
 */