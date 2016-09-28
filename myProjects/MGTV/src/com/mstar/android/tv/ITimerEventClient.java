package com.mstar.android.tv;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface ITimerEventClient extends IInterface
{
  public abstract boolean onDestroyCountDown(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract boolean onEpgTimeUp(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract boolean onEpgTimerCountDown(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract boolean onEpgTimerRecordStart(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract boolean onLastMinuteWarn(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract boolean onOadTimeScan(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract boolean onOneSecondBeat(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract boolean onPowerDownTime(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract boolean onPvrNotifyRecordStop(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract boolean onSignalLock(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract boolean onUpdateLastMinute(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements ITimerEventClient
  {
    public static ITimerEventClient asInterface(IBinder paramIBinder)
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
 * Qualified Name:     com.mstar.android.tv.ITimerEventClient
 * JD-Core Version:    0.6.2
 */