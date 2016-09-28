package com.starcor.hunan.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface INotifyStatus extends IInterface
{
  public abstract void notify(boolean paramBoolean)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements INotifyStatus
  {
    private static final String DESCRIPTOR = "com.starcor.hunan.aidl.INotifyStatus";
    static final int TRANSACTION_notify = 1;

    public Stub()
    {
      attachInterface(this, "com.starcor.hunan.aidl.INotifyStatus");
    }

    public static INotifyStatus asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.starcor.hunan.aidl.INotifyStatus");
      if ((localIInterface != null) && ((localIInterface instanceof INotifyStatus)))
        return (INotifyStatus)localIInterface;
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
        paramParcel2.writeString("com.starcor.hunan.aidl.INotifyStatus");
        return true;
      case 1:
      }
      paramParcel1.enforceInterface("com.starcor.hunan.aidl.INotifyStatus");
      if (paramParcel1.readInt() != 0);
      for (boolean bool = true; ; bool = false)
      {
        notify(bool);
        paramParcel2.writeNoException();
        return true;
      }
    }

    private static class Proxy
      implements INotifyStatus
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
        return "com.starcor.hunan.aidl.INotifyStatus";
      }

      public void notify(boolean paramBoolean)
        throws RemoteException
      {
        int i = 1;
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.hunan.aidl.INotifyStatus");
          if (paramBoolean);
          while (true)
          {
            localParcel1.writeInt(i);
            this.mRemote.transact(1, localParcel1, localParcel2, 0);
            localParcel2.readException();
            return;
            i = 0;
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
 * Qualified Name:     com.starcor.hunan.aidl.INotifyStatus
 * JD-Core Version:    0.6.2
 */