package com.starcor.hunan.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface IRemoveListService extends IInterface
{
  public abstract void clearCollectList(INotifyStatus paramINotifyStatus)
    throws RemoteException;

  public abstract void clearPlayList(INotifyStatus paramINotifyStatus)
    throws RemoteException;

  public abstract void clearTracePlayList(INotifyStatus paramINotifyStatus)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements IRemoveListService
  {
    private static final String DESCRIPTOR = "com.starcor.hunan.aidl.IRemoveListService";
    static final int TRANSACTION_clearCollectList = 2;
    static final int TRANSACTION_clearPlayList = 1;
    static final int TRANSACTION_clearTracePlayList = 3;

    public Stub()
    {
      attachInterface(this, "com.starcor.hunan.aidl.IRemoveListService");
    }

    public static IRemoveListService asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.starcor.hunan.aidl.IRemoveListService");
      if ((localIInterface != null) && ((localIInterface instanceof IRemoveListService)))
        return (IRemoveListService)localIInterface;
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
        paramParcel2.writeString("com.starcor.hunan.aidl.IRemoveListService");
        return true;
      case 1:
        paramParcel1.enforceInterface("com.starcor.hunan.aidl.IRemoveListService");
        clearPlayList(INotifyStatus.Stub.asInterface(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        return true;
      case 2:
        paramParcel1.enforceInterface("com.starcor.hunan.aidl.IRemoveListService");
        clearCollectList(INotifyStatus.Stub.asInterface(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        return true;
      case 3:
      }
      paramParcel1.enforceInterface("com.starcor.hunan.aidl.IRemoveListService");
      clearTracePlayList(INotifyStatus.Stub.asInterface(paramParcel1.readStrongBinder()));
      paramParcel2.writeNoException();
      return true;
    }

    private static class Proxy
      implements IRemoveListService
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

      public void clearCollectList(INotifyStatus paramINotifyStatus)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.hunan.aidl.IRemoveListService");
          if (paramINotifyStatus != null);
          for (IBinder localIBinder = paramINotifyStatus.asBinder(); ; localIBinder = null)
          {
            localParcel1.writeStrongBinder(localIBinder);
            this.mRemote.transact(2, localParcel1, localParcel2, 0);
            localParcel2.readException();
            return;
          }
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void clearPlayList(INotifyStatus paramINotifyStatus)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.hunan.aidl.IRemoveListService");
          if (paramINotifyStatus != null);
          for (IBinder localIBinder = paramINotifyStatus.asBinder(); ; localIBinder = null)
          {
            localParcel1.writeStrongBinder(localIBinder);
            this.mRemote.transact(1, localParcel1, localParcel2, 0);
            localParcel2.readException();
            return;
          }
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void clearTracePlayList(INotifyStatus paramINotifyStatus)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.hunan.aidl.IRemoveListService");
          if (paramINotifyStatus != null);
          for (IBinder localIBinder = paramINotifyStatus.asBinder(); ; localIBinder = null)
          {
            localParcel1.writeStrongBinder(localIBinder);
            this.mRemote.transact(3, localParcel1, localParcel2, 0);
            localParcel2.readException();
            return;
          }
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public String getInterfaceDescriptor()
      {
        return "com.starcor.hunan.aidl.IRemoveListService";
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.aidl.IRemoveListService
 * JD-Core Version:    0.6.2
 */