package com.mstar.android.tv;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface ITvCommon extends IInterface
{
  public abstract boolean[] GetInputSourceStatus()
    throws RemoteException;

  public abstract void addClient(String paramString, IBinder paramIBinder)
    throws RemoteException;

  public abstract void closeSurfaceView()
    throws RemoteException;

  public abstract void enterSleepMode(boolean paramBoolean1, boolean paramBoolean2)
    throws RemoteException;

  public abstract int getAtvMtsMode()
    throws RemoteException;

  public abstract int getAtvSoundMode()
    throws RemoteException;

  public abstract IBinder getClient(String paramString)
    throws RemoteException;

  public abstract int getCurrentDBInputSource()
    throws RemoteException;

  public abstract int getCurrentInputSource()
    throws RemoteException;

  public abstract int getCurrentSubInputSource()
    throws RemoteException;

  public abstract int getPowerOnSource()
    throws RemoteException;

  public abstract int[] getSourceList()
    throws RemoteException;

  public abstract void openSurfaceView(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws RemoteException;

  public abstract void rebootSystem(String paramString)
    throws RemoteException;

  public abstract void recoverySystem(String paramString)
    throws RemoteException;

  public abstract void removeClient(String paramString)
    throws RemoteException;

  public abstract int setAtvMtsMode(int paramInt)
    throws RemoteException;

  public abstract void setInputSource(int paramInt)
    throws RemoteException;

  public abstract void setInputSourceMoreArgs(int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    throws RemoteException;

  public abstract boolean setPowerOnSource(int paramInt)
    throws RemoteException;

  public abstract void setSurfaceView(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws RemoteException;

  public abstract void standbySystem(String paramString)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements ITvCommon
  {
    public static ITvCommon asInterface(IBinder paramIBinder)
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
 * Qualified Name:     com.mstar.android.tv.ITvCommon
 * JD-Core Version:    0.6.2
 */