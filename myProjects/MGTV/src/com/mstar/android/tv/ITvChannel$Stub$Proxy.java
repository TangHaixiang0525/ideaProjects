package com.mstar.android.tv;

import android.os.IBinder;
import android.os.RemoteException;
import com.mstar.android.tvapi.common.vo.ProgramInfo;
import com.mstar.android.tvapi.common.vo.ProgramInfoQueryCriteria;
import com.mstar.android.tvapi.dtv.vo.DtvSubtitleInfo;
import com.mstar.android.tvapi.dtv.vo.RfInfo;

class ITvChannel$Stub$Proxy
  implements ITvChannel
{
  public void addProgramToFavorite(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public IBinder asBinder()
  {
    throw new RuntimeException("stub");
  }

  public boolean changeToFirstService(int paramInt1, int paramInt2)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean closeSubtitle()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public void deleteProgramFromFavorite(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public int getAtvCurrentFrequency()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public int getAtvProgramInfo(int paramInt1, int paramInt2)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public int getAtvSoundSystem()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public String getAtvStationName(int paramInt)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public int getAtvVideoSystem()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public int getChannelSwitchMode()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public int getCurrentChannelNumber()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public int getCurrentLanguageIndex(String paramString)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public String getInterfaceDescriptor()
  {
    throw new RuntimeException("stub");
  }

  public boolean getProgramAttribute(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public int getProgramCount(int paramInt)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public int getProgramCtrl(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public ProgramInfo getProgramInfo(ProgramInfoQueryCriteria paramProgramInfoQueryCriteria, int paramInt)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public String getProgramName(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public RfInfo getRfInfo(int paramInt1, int paramInt2)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public int getSIFMtsMode()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public DtvSubtitleInfo getSubtitleInfo()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public int getSystemCountry()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public int getVideoStandard()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean isSignalStabled()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean openSubtitle(int paramInt)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean pauseAtvAutoTuning()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean pauseDtvScan()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean playDtvCurrentProgram()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean programDown()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean programSel(int paramInt1, int paramInt2)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean programUp()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean registerOnAtvPlayerEventListener(int paramInt)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean registerOnDtvPlayerEventListener(int paramInt)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean registerOnTvPlayerEventListener(int paramInt)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean resumeAtvAutoTuning()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean resumeDtvScan()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public int setAtvChannel(int paramInt)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean setAtvForceSoundSystem(int paramInt)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean setAtvForceVedioSystem(int paramInt)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public int setAtvProgramInfo(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public void setChannelChangeFreezeMode(boolean paramBoolean)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean setChannelSwitchMode(int paramInt)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public void setDtvAntennaType(int paramInt)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean setDtvManualScanByFreq(int paramInt)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean setDtvManualScanByRF(int paramInt)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public void setProgramAttribute(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public int setProgramCtrl(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public void setSystemCountry(int paramInt)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean startAtvAutoTuning(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean startAtvManualTuning(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean startDtvAutoScan()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean startDtvFullScan()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean startDtvManualScan()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean stopAtvAutoTuning()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public void stopAtvManualTuning()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean stopDtvScan()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public void switchAudioTrack(int paramInt)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean switchMSrvDtvRouteCmd(int paramInt)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tv.ITvChannel.Stub.Proxy
 * JD-Core Version:    0.6.2
 */