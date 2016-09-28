package com.mstar.android.tv;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.mstar.android.tvapi.atv.vo.AtvEventScan;

public abstract interface IAtvPlayerEventClient extends IInterface
{
  public abstract boolean onAtvAutoTuningScanInfo(int paramInt, AtvEventScan paramAtvEventScan)
    throws RemoteException;

  public abstract boolean onAtvManualTuningScanInfo(int paramInt, AtvEventScan paramAtvEventScan)
    throws RemoteException;

  public abstract boolean onAtvProgramInfoReady(int paramInt)
    throws RemoteException;

  public abstract boolean onSignalLock(int paramInt)
    throws RemoteException;

  public abstract boolean onSignalUnLock(int paramInt)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements IAtvPlayerEventClient
  {
    public static IAtvPlayerEventClient asInterface(IBinder paramIBinder)
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
 * Qualified Name:     com.mstar.android.tv.IAtvPlayerEventClient
 * JD-Core Version:    0.6.2
 */