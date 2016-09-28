package com.starcor.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface IAppEventListener extends IInterface
{
  public abstract int onEvent(String paramString1, String paramString2)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements IAppEventListener
  {
    private static final String DESCRIPTOR = "com.starcor.service.IAppEventListener";
    static final int TRANSACTION_onEvent = 1;

    public Stub()
    {
      attachInterface(this, "com.starcor.service.IAppEventListener");
    }

    public static IAppEventListener asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.starcor.service.IAppEventListener");
      if ((localIInterface != null) && ((localIInterface instanceof IAppEventListener)))
        return (IAppEventListener)localIInterface;
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
        paramParcel2.writeString("com.starcor.service.IAppEventListener");
        return true;
      case 1:
      }
      paramParcel1.enforceInterface("com.starcor.service.IAppEventListener");
      int i = onEvent(paramParcel1.readString(), paramParcel1.readString());
      paramParcel2.writeNoException();
      paramParcel2.writeInt(i);
      return true;
    }

    private static class Proxy
      implements IAppEventListener
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
        return "com.starcor.service.IAppEventListener";
      }

      public int onEvent(String paramString1, String paramString2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.service.IAppEventListener");
          localParcel1.writeString(paramString1);
          localParcel1.writeString(paramString2);
          this.mRemote.transact(1, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          return i;
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
 * Qualified Name:     com.starcor.service.IAppEventListener
 * JD-Core Version:    0.6.2
 */