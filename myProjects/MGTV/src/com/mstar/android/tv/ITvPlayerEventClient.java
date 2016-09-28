package com.mstar.android.tv;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.mstar.android.tvapi.common.vo.HbbtvEventInfo;

public abstract interface ITvPlayerEventClient extends IInterface
{
  public abstract boolean onHbbtvUiEvent(int paramInt, HbbtvEventInfo paramHbbtvEventInfo)
    throws RemoteException;

  public abstract boolean onPopupDialog(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract boolean onPvrNotifyAlwaysTimeShiftProgramNotReady(int paramInt)
    throws RemoteException;

  public abstract boolean onPvrNotifyAlwaysTimeShiftProgramReady(int paramInt)
    throws RemoteException;

  public abstract boolean onPvrNotifyCiPlusProtection(int paramInt)
    throws RemoteException;

  public abstract boolean onPvrNotifyCiPlusRetentionLimitUpdate(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract boolean onPvrNotifyOverRun(int paramInt)
    throws RemoteException;

  public abstract boolean onPvrNotifyParentalControl(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract boolean onPvrNotifyPlaybackBegin(int paramInt)
    throws RemoteException;

  public abstract boolean onPvrNotifyPlaybackSpeedChange(int paramInt)
    throws RemoteException;

  public abstract boolean onPvrNotifyPlaybackStop(int paramInt)
    throws RemoteException;

  public abstract boolean onPvrNotifyPlaybackTime(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract boolean onPvrNotifyRecordSize(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract boolean onPvrNotifyRecordStop(int paramInt)
    throws RemoteException;

  public abstract boolean onPvrNotifyRecordTime(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract boolean onPvrNotifyTimeShiftOverwritesAfter(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract boolean onPvrNotifyTimeShiftOverwritesBefore(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract boolean onPvrNotifyUsbRemoved(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract boolean onScreenSaverMode(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract boolean onSignalLock(int paramInt)
    throws RemoteException;

  public abstract boolean onSignalUnLock(int paramInt)
    throws RemoteException;

  public abstract boolean onTvProgramInfoReady(int paramInt)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements ITvPlayerEventClient
  {
    public static ITvPlayerEventClient asInterface(IBinder paramIBinder)
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
 * Qualified Name:     com.mstar.android.tv.ITvPlayerEventClient
 * JD-Core Version:    0.6.2
 */