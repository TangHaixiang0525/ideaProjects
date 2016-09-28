package com.mstar.android.tv;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface ITvService extends IInterface
{
  public abstract ITvAtscChannel getTvAtscChannel()
    throws RemoteException;

  public abstract ITvAudio getTvAudio()
    throws RemoteException;

  public abstract ITvCc getTvCc()
    throws RemoteException;

  public abstract ITvCec getTvCec()
    throws RemoteException;

  public abstract ITvChannel getTvChannel()
    throws RemoteException;

  public abstract ITvCommon getTvCommon()
    throws RemoteException;

  public abstract ITvEpg getTvEpg()
    throws RemoteException;

  public abstract ITvFactory getTvFactory()
    throws RemoteException;

  public abstract ITvPicture getTvPicture()
    throws RemoteException;

  public abstract ITvPipPop getTvPipPop()
    throws RemoteException;

  public abstract ITvS3D getTvS3D()
    throws RemoteException;

  public abstract ITvTimer getTvTimer()
    throws RemoteException;

  public abstract void resume()
    throws RemoteException;

  public abstract void shutdown()
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements ITvService
  {
    public static ITvService asInterface(IBinder paramIBinder)
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
 * Qualified Name:     com.mstar.android.tv.ITvService
 * JD-Core Version:    0.6.2
 */