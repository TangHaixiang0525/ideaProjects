package com.mstar.android.tv;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface ITvEventClient extends IInterface
{
  public abstract boolean onAtscPopupDialog(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract boolean onDtvReadyPopupDialog(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract boolean onScartMuteOsdMode(int paramInt)
    throws RemoteException;

  public abstract boolean onScreenSaverMode(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract boolean onSignalLock(int paramInt)
    throws RemoteException;

  public abstract boolean onSignalUnlock(int paramInt)
    throws RemoteException;

  public abstract boolean onUnityEvent(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements ITvEventClient
  {
    public static ITvEventClient asInterface(IBinder paramIBinder)
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
 * Qualified Name:     com.mstar.android.tv.ITvEventClient
 * JD-Core Version:    0.6.2
 */