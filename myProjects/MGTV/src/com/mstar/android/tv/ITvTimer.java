package com.mstar.android.tv;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.mstar.android.tvapi.common.vo.OnTimeTvDescriptor;
import com.mstar.android.tvapi.common.vo.StandardTime;

public abstract interface ITvTimer extends IInterface
{
  public abstract int getClockOffset()
    throws RemoteException;

  public abstract StandardTime getCurTimer()
    throws RemoteException;

  public abstract StandardTime getOffTimer()
    throws RemoteException;

  public abstract OnTimeTvDescriptor getOnTimeEvent()
    throws RemoteException;

  public abstract StandardTime getOnTimer()
    throws RemoteException;

  public abstract int getRtcClock()
    throws RemoteException;

  public abstract int getSleepMode()
    throws RemoteException;

  public abstract int getTimeZone()
    throws RemoteException;

  public abstract boolean isOffTimerEnable()
    throws RemoteException;

  public abstract boolean isOnTimerEnable()
    throws RemoteException;

  public abstract boolean setOffTimer(StandardTime paramStandardTime)
    throws RemoteException;

  public abstract boolean setOffTimerEnable(boolean paramBoolean)
    throws RemoteException;

  public abstract boolean setOnTimeEvent(OnTimeTvDescriptor paramOnTimeTvDescriptor)
    throws RemoteException;

  public abstract boolean setOnTimer(StandardTime paramStandardTime)
    throws RemoteException;

  public abstract boolean setOnTimerEnable(boolean paramBoolean)
    throws RemoteException;

  public abstract boolean setSleepMode(int paramInt)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements ITvTimer
  {
    public static ITvTimer asInterface(IBinder paramIBinder)
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
 * Qualified Name:     com.mstar.android.tv.ITvTimer
 * JD-Core Version:    0.6.2
 */