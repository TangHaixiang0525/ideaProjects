package com.mstar.android.tv;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.mstar.android.tvapi.common.vo.ProgramInfo;
import com.mstar.android.tvapi.common.vo.ProgramInfoQueryCriteria;
import com.mstar.android.tvapi.dtv.vo.DtvSubtitleInfo;
import com.mstar.android.tvapi.dtv.vo.RfInfo;

public abstract interface ITvChannel extends IInterface
{
  public abstract void addProgramToFavorite(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws RemoteException;

  public abstract boolean changeToFirstService(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract boolean closeSubtitle()
    throws RemoteException;

  public abstract void deleteProgramFromFavorite(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws RemoteException;

  public abstract int getAtvCurrentFrequency()
    throws RemoteException;

  public abstract int getAtvProgramInfo(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract int getAtvSoundSystem()
    throws RemoteException;

  public abstract String getAtvStationName(int paramInt)
    throws RemoteException;

  public abstract int getAtvVideoSystem()
    throws RemoteException;

  public abstract int getChannelSwitchMode()
    throws RemoteException;

  public abstract int getCurrentChannelNumber()
    throws RemoteException;

  public abstract int getCurrentLanguageIndex(String paramString)
    throws RemoteException;

  public abstract boolean getProgramAttribute(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws RemoteException;

  public abstract int getProgramCount(int paramInt)
    throws RemoteException;

  public abstract int getProgramCtrl(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract ProgramInfo getProgramInfo(ProgramInfoQueryCriteria paramProgramInfoQueryCriteria, int paramInt)
    throws RemoteException;

  public abstract String getProgramName(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract RfInfo getRfInfo(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract int getSIFMtsMode()
    throws RemoteException;

  public abstract DtvSubtitleInfo getSubtitleInfo()
    throws RemoteException;

  public abstract int getSystemCountry()
    throws RemoteException;

  public abstract int getVideoStandard()
    throws RemoteException;

  public abstract boolean isSignalStabled()
    throws RemoteException;

  public abstract boolean openSubtitle(int paramInt)
    throws RemoteException;

  public abstract boolean pauseAtvAutoTuning()
    throws RemoteException;

  public abstract boolean pauseDtvScan()
    throws RemoteException;

  public abstract boolean playDtvCurrentProgram()
    throws RemoteException;

  public abstract boolean programDown()
    throws RemoteException;

  public abstract boolean programSel(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract boolean programUp()
    throws RemoteException;

  public abstract boolean registerOnAtvPlayerEventListener(int paramInt)
    throws RemoteException;

  public abstract boolean registerOnDtvPlayerEventListener(int paramInt)
    throws RemoteException;

  public abstract boolean registerOnTvPlayerEventListener(int paramInt)
    throws RemoteException;

  public abstract boolean resumeAtvAutoTuning()
    throws RemoteException;

  public abstract boolean resumeDtvScan()
    throws RemoteException;

  public abstract int setAtvChannel(int paramInt)
    throws RemoteException;

  public abstract boolean setAtvForceSoundSystem(int paramInt)
    throws RemoteException;

  public abstract boolean setAtvForceVedioSystem(int paramInt)
    throws RemoteException;

  public abstract int setAtvProgramInfo(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract void setChannelChangeFreezeMode(boolean paramBoolean)
    throws RemoteException;

  public abstract boolean setChannelSwitchMode(int paramInt)
    throws RemoteException;

  public abstract void setDtvAntennaType(int paramInt)
    throws RemoteException;

  public abstract boolean setDtvManualScanByFreq(int paramInt)
    throws RemoteException;

  public abstract boolean setDtvManualScanByRF(int paramInt)
    throws RemoteException;

  public abstract void setProgramAttribute(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws RemoteException;

  public abstract int setProgramCtrl(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract void setSystemCountry(int paramInt)
    throws RemoteException;

  public abstract boolean startAtvAutoTuning(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract boolean startAtvManualTuning(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract boolean startDtvAutoScan()
    throws RemoteException;

  public abstract boolean startDtvFullScan()
    throws RemoteException;

  public abstract boolean startDtvManualScan()
    throws RemoteException;

  public abstract boolean stopAtvAutoTuning()
    throws RemoteException;

  public abstract void stopAtvManualTuning()
    throws RemoteException;

  public abstract boolean stopDtvScan()
    throws RemoteException;

  public abstract void switchAudioTrack(int paramInt)
    throws RemoteException;

  public abstract boolean switchMSrvDtvRouteCmd(int paramInt)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements ITvChannel
  {
    public static ITvChannel asInterface(IBinder paramIBinder)
    {
      throw new RuntimeException("stub");
    }

    public IBinder asBinder()
    {
      throw new RuntimeException("stub");
    }

    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      throw new RuntimeException("stub");
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tv.ITvChannel
 * JD-Core Version:    0.6.2
 */