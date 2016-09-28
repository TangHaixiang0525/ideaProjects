package com.mstar.android.tv;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.mstar.android.tvapi.common.vo.CecSetting;

public abstract interface ITvCec extends IInterface
{
  public abstract int deviceListGetItemIndex(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract String deviceListGetListStr(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract boolean disableDeviceMenu()
    throws RemoteException;

  public abstract boolean enableDeviceMenu()
    throws RemoteException;

  public abstract int getCECListCnt(int paramInt)
    throws RemoteException;

  public abstract CecSetting getCecConfiguration()
    throws RemoteException;

  public abstract String getDeviceName(int paramInt)
    throws RemoteException;

  public abstract boolean registerOnCecEventListener(int paramInt)
    throws RemoteException;

  public abstract boolean sendCecKey(int paramInt)
    throws RemoteException;

  public abstract boolean setCecConfiguration(CecSetting paramCecSetting)
    throws RemoteException;

  public abstract boolean setMenuLanguage(int paramInt)
    throws RemoteException;

  public abstract boolean setStreamPath(int paramInt)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements ITvCec
  {
    public static ITvCec asInterface(IBinder paramIBinder)
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
 * Qualified Name:     com.mstar.android.tv.ITvCec
 * JD-Core Version:    0.6.2
 */