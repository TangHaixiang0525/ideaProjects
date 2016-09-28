package com.xiaomi.account;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;

public abstract interface IXiaomiAuthResponse extends IInterface
{
  public abstract void onCancel()
    throws RemoteException;

  public abstract void onResult(Bundle paramBundle)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements IXiaomiAuthResponse
  {
    private static final String DESCRIPTOR = "com.xiaomi.account.IXiaomiAuthResponse";
    static final int TRANSACTION_onCancel = 2;
    static final int TRANSACTION_onResult = 1;

    public Stub()
    {
      attachInterface(this, "com.xiaomi.account.IXiaomiAuthResponse");
    }

    public static IXiaomiAuthResponse asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.xiaomi.account.IXiaomiAuthResponse");
      if ((localIInterface != null) && ((localIInterface instanceof IXiaomiAuthResponse)))
        return (IXiaomiAuthResponse)localIInterface;
      return new Proxy(paramIBinder);
    }

    public IBinder asBinder()
    {
      return this;
    }

    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      switch (paramInt1)
      {
      default:
        return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
      case 1598968902:
        paramParcel2.writeString("com.xiaomi.account.IXiaomiAuthResponse");
        return true;
      case 1:
        paramParcel1.enforceInterface("com.xiaomi.account.IXiaomiAuthResponse");
        if (paramParcel1.readInt() != 0);
        for (Bundle localBundle = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1); ; localBundle = null)
        {
          onResult(localBundle);
          paramParcel2.writeNoException();
          return true;
        }
      case 2:
      }
      paramParcel1.enforceInterface("com.xiaomi.account.IXiaomiAuthResponse");
      onCancel();
      paramParcel2.writeNoException();
      return true;
    }

    private static class Proxy
      implements IXiaomiAuthResponse
    {
      private IBinder mRemote;

      Proxy(IBinder paramIBinder)
      {
        this.mRemote = paramIBinder;
      }

      public IBinder asBinder()
      {
        return this.mRemote;
      }

      public String getInterfaceDescriptor()
      {
        return "com.xiaomi.account.IXiaomiAuthResponse";
      }

      public void onCancel()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.xiaomi.account.IXiaomiAuthResponse");
          this.mRemote.transact(2, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void onResult(Bundle paramBundle)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.xiaomi.account.IXiaomiAuthResponse");
          if (paramBundle != null)
          {
            localParcel1.writeInt(1);
            paramBundle.writeToParcel(localParcel1, 0);
          }
          while (true)
          {
            this.mRemote.transact(1, localParcel1, localParcel2, 0);
            localParcel2.readException();
            return;
            localParcel1.writeInt(0);
          }
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.xiaomi.account.IXiaomiAuthResponse
 * JD-Core Version:    0.6.2
 */