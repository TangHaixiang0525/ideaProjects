package com.starcor.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;

public abstract interface IAppManager extends IInterface
{
  public abstract int forceStopApp(String paramString)
    throws RemoteException;

  public abstract ApplicationInfo getAppInfo(String paramString)
    throws RemoteException;

  public abstract String[] getAppList()
    throws RemoteException;

  public abstract int installApp(String paramString)
    throws RemoteException;

  public abstract int removeApp(String paramString)
    throws RemoteException;

  public abstract int resetApp(String paramString)
    throws RemoteException;

  public abstract int setEventListener(IAppEventListener paramIAppEventListener)
    throws RemoteException;

  public abstract int startApp(String paramString)
    throws RemoteException;

  public abstract int stopApp(String paramString)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements IAppManager
  {
    private static final String DESCRIPTOR = "com.starcor.service.IAppManager";
    static final int TRANSACTION_forceStopApp = 8;
    static final int TRANSACTION_getAppInfo = 4;
    static final int TRANSACTION_getAppList = 5;
    static final int TRANSACTION_installApp = 1;
    static final int TRANSACTION_removeApp = 2;
    static final int TRANSACTION_resetApp = 3;
    static final int TRANSACTION_setEventListener = 7;
    static final int TRANSACTION_startApp = 6;
    static final int TRANSACTION_stopApp = 9;

    public Stub()
    {
      attachInterface(this, "com.starcor.service.IAppManager");
    }

    public static IAppManager asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.starcor.service.IAppManager");
      if ((localIInterface != null) && ((localIInterface instanceof IAppManager)))
        return (IAppManager)localIInterface;
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
        paramParcel2.writeString("com.starcor.service.IAppManager");
        return true;
      case 1:
        paramParcel1.enforceInterface("com.starcor.service.IAppManager");
        int i2 = installApp(paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i2);
        return true;
      case 2:
        paramParcel1.enforceInterface("com.starcor.service.IAppManager");
        int i1 = removeApp(paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i1);
        return true;
      case 3:
        paramParcel1.enforceInterface("com.starcor.service.IAppManager");
        int n = resetApp(paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(n);
        return true;
      case 4:
        paramParcel1.enforceInterface("com.starcor.service.IAppManager");
        ApplicationInfo localApplicationInfo = getAppInfo(paramParcel1.readString());
        paramParcel2.writeNoException();
        if (localApplicationInfo != null)
        {
          paramParcel2.writeInt(1);
          localApplicationInfo.writeToParcel(paramParcel2, 1);
          return true;
        }
        paramParcel2.writeInt(0);
        return true;
      case 5:
        paramParcel1.enforceInterface("com.starcor.service.IAppManager");
        String[] arrayOfString = getAppList();
        paramParcel2.writeNoException();
        paramParcel2.writeStringArray(arrayOfString);
        return true;
      case 6:
        paramParcel1.enforceInterface("com.starcor.service.IAppManager");
        int m = startApp(paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(m);
        return true;
      case 7:
        paramParcel1.enforceInterface("com.starcor.service.IAppManager");
        int k = setEventListener(IAppEventListener.Stub.asInterface(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        paramParcel2.writeInt(k);
        return true;
      case 8:
        paramParcel1.enforceInterface("com.starcor.service.IAppManager");
        int j = forceStopApp(paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(j);
        return true;
      case 9:
      }
      paramParcel1.enforceInterface("com.starcor.service.IAppManager");
      int i = stopApp(paramParcel1.readString());
      paramParcel2.writeNoException();
      paramParcel2.writeInt(i);
      return true;
    }

    private static class Proxy
      implements IAppManager
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

      public int forceStopApp(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.service.IAppManager");
          localParcel1.writeString(paramString);
          this.mRemote.transact(8, localParcel1, localParcel2, 0);
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

      public ApplicationInfo getAppInfo(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.service.IAppManager");
          localParcel1.writeString(paramString);
          this.mRemote.transact(4, localParcel1, localParcel2, 0);
          localParcel2.readException();
          if (localParcel2.readInt() != 0)
          {
            localApplicationInfo = (ApplicationInfo)ApplicationInfo.CREATOR.createFromParcel(localParcel2);
            return localApplicationInfo;
          }
          ApplicationInfo localApplicationInfo = null;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public String[] getAppList()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.service.IAppManager");
          this.mRemote.transact(5, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String[] arrayOfString = localParcel2.createStringArray();
          return arrayOfString;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public String getInterfaceDescriptor()
      {
        return "com.starcor.service.IAppManager";
      }

      public int installApp(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.service.IAppManager");
          localParcel1.writeString(paramString);
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

      public int removeApp(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.service.IAppManager");
          localParcel1.writeString(paramString);
          this.mRemote.transact(2, localParcel1, localParcel2, 0);
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

      public int resetApp(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.service.IAppManager");
          localParcel1.writeString(paramString);
          this.mRemote.transact(3, localParcel1, localParcel2, 0);
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

      public int setEventListener(IAppEventListener paramIAppEventListener)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.service.IAppManager");
          if (paramIAppEventListener != null);
          for (IBinder localIBinder = paramIAppEventListener.asBinder(); ; localIBinder = null)
          {
            localParcel1.writeStrongBinder(localIBinder);
            this.mRemote.transact(7, localParcel1, localParcel2, 0);
            localParcel2.readException();
            int i = localParcel2.readInt();
            return i;
          }
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public int startApp(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.service.IAppManager");
          localParcel1.writeString(paramString);
          this.mRemote.transact(6, localParcel1, localParcel2, 0);
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

      public int stopApp(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.service.IAppManager");
          localParcel1.writeString(paramString);
          this.mRemote.transact(9, localParcel1, localParcel2, 0);
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
 * Qualified Name:     com.starcor.service.IAppManager
 * JD-Core Version:    0.6.2
 */