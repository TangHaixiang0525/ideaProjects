package android.net.ethernet;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface IEthernetManager extends IInterface
{
  public abstract String[] getDeviceNameList()
    throws RemoteException;

  public abstract EthernetDevInfo getSavedConfig()
    throws RemoteException;

  public abstract int getState()
    throws RemoteException;

  public abstract int getTotalInterface()
    throws RemoteException;

  public abstract boolean isCableConnected()
    throws RemoteException;

  public abstract boolean isConfigured()
    throws RemoteException;

  public abstract boolean isNetworkConnected()
    throws RemoteException;

  public abstract void setMode(String paramString)
    throws RemoteException;

  public abstract void setState(int paramInt)
    throws RemoteException;

  public abstract void updateDevInfo(EthernetDevInfo paramEthernetDevInfo)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements IEthernetManager
  {
    public static IEthernetManager asInterface(IBinder paramIBinder)
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
 * Qualified Name:     android.net.ethernet.IEthernetManager
 * JD-Core Version:    0.6.2
 */