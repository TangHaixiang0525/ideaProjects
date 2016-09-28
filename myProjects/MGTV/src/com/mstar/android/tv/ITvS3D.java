package com.mstar.android.tv;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface ITvS3D extends IInterface
{
  public abstract int get3DDepthMode()
    throws RemoteException;

  public abstract int get3DOffsetMode()
    throws RemoteException;

  public abstract int get3DOutputAspectMode()
    throws RemoteException;

  public abstract int getAutoStartMode()
    throws RemoteException;

  public abstract int getCurrent3dFormat()
    throws RemoteException;

  public abstract int getDisplay3DTo2DMode()
    throws RemoteException;

  public abstract int getDisplayFormat()
    throws RemoteException;

  public abstract int getLrViewSwitch()
    throws RemoteException;

  public abstract int getSelfAdaptiveDetect()
    throws RemoteException;

  public abstract boolean set3DDepthMode(int paramInt)
    throws RemoteException;

  public abstract boolean set3DOffsetMode(int paramInt)
    throws RemoteException;

  public abstract boolean set3DOutputAspectMode(int paramInt)
    throws RemoteException;

  public abstract boolean set3DTo2D(int paramInt)
    throws RemoteException;

  public abstract boolean setAutoStartMode(int paramInt)
    throws RemoteException;

  public abstract boolean setDisplayFormat(int paramInt)
    throws RemoteException;

  public abstract void setDisplayFormatForUI(int paramInt)
    throws RemoteException;

  public abstract boolean setLrViewSwitch(int paramInt)
    throws RemoteException;

  public abstract boolean setSelfAdaptiveDetect(int paramInt)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements ITvS3D
  {
    public static ITvS3D asInterface(IBinder paramIBinder)
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
 * Qualified Name:     com.mstar.android.tv.ITvS3D
 * JD-Core Version:    0.6.2
 */