package com.starcor.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;

public abstract interface IDownloadManager extends IInterface
{
  public abstract int cancelTask(int paramInt)
    throws RemoteException;

  public abstract int createTask(String paramString1, String paramString2, String paramString3)
    throws RemoteException;

  public abstract DownloadTaskInfo getTaskInfo(int paramInt)
    throws RemoteException;

  public abstract int[] getTaskList(String paramString)
    throws RemoteException;

  public abstract int pauseTask(int paramInt)
    throws RemoteException;

  public abstract int removeTask(int paramInt)
    throws RemoteException;

  public abstract int resumeTask(int paramInt)
    throws RemoteException;

  public abstract int setEventListener(IDownloadEventListener paramIDownloadEventListener)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements IDownloadManager
  {
    private static final String DESCRIPTOR = "com.starcor.service.IDownloadManager";
    static final int TRANSACTION_cancelTask = 4;
    static final int TRANSACTION_createTask = 1;
    static final int TRANSACTION_getTaskInfo = 2;
    static final int TRANSACTION_getTaskList = 7;
    static final int TRANSACTION_pauseTask = 3;
    static final int TRANSACTION_removeTask = 6;
    static final int TRANSACTION_resumeTask = 5;
    static final int TRANSACTION_setEventListener = 8;

    public Stub()
    {
      attachInterface(this, "com.starcor.service.IDownloadManager");
    }

    public static IDownloadManager asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.starcor.service.IDownloadManager");
      if ((localIInterface != null) && ((localIInterface instanceof IDownloadManager)))
        return (IDownloadManager)localIInterface;
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
        paramParcel2.writeString("com.starcor.service.IDownloadManager");
        return true;
      case 1:
        paramParcel1.enforceInterface("com.starcor.service.IDownloadManager");
        int i1 = createTask(paramParcel1.readString(), paramParcel1.readString(), paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i1);
        return true;
      case 2:
        paramParcel1.enforceInterface("com.starcor.service.IDownloadManager");
        DownloadTaskInfo localDownloadTaskInfo = getTaskInfo(paramParcel1.readInt());
        paramParcel2.writeNoException();
        if (localDownloadTaskInfo != null)
        {
          paramParcel2.writeInt(1);
          localDownloadTaskInfo.writeToParcel(paramParcel2, 1);
          return true;
        }
        paramParcel2.writeInt(0);
        return true;
      case 3:
        paramParcel1.enforceInterface("com.starcor.service.IDownloadManager");
        int n = pauseTask(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(n);
        return true;
      case 4:
        paramParcel1.enforceInterface("com.starcor.service.IDownloadManager");
        int m = cancelTask(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(m);
        return true;
      case 5:
        paramParcel1.enforceInterface("com.starcor.service.IDownloadManager");
        int k = resumeTask(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(k);
        return true;
      case 6:
        paramParcel1.enforceInterface("com.starcor.service.IDownloadManager");
        int j = removeTask(paramParcel1.readInt());
        paramParcel2.writeNoException();
        paramParcel2.writeInt(j);
        return true;
      case 7:
        paramParcel1.enforceInterface("com.starcor.service.IDownloadManager");
        int[] arrayOfInt = getTaskList(paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeIntArray(arrayOfInt);
        return true;
      case 8:
      }
      paramParcel1.enforceInterface("com.starcor.service.IDownloadManager");
      int i = setEventListener(IDownloadEventListener.Stub.asInterface(paramParcel1.readStrongBinder()));
      paramParcel2.writeNoException();
      paramParcel2.writeInt(i);
      return true;
    }

    private static class Proxy
      implements IDownloadManager
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

      public int cancelTask(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.service.IDownloadManager");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(4, localParcel1, localParcel2, 0);
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

      public int createTask(String paramString1, String paramString2, String paramString3)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.service.IDownloadManager");
          localParcel1.writeString(paramString1);
          localParcel1.writeString(paramString2);
          localParcel1.writeString(paramString3);
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

      public String getInterfaceDescriptor()
      {
        return "com.starcor.service.IDownloadManager";
      }

      public DownloadTaskInfo getTaskInfo(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.service.IDownloadManager");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(2, localParcel1, localParcel2, 0);
          localParcel2.readException();
          if (localParcel2.readInt() != 0)
          {
            localDownloadTaskInfo = (DownloadTaskInfo)DownloadTaskInfo.CREATOR.createFromParcel(localParcel2);
            return localDownloadTaskInfo;
          }
          DownloadTaskInfo localDownloadTaskInfo = null;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public int[] getTaskList(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.service.IDownloadManager");
          localParcel1.writeString(paramString);
          this.mRemote.transact(7, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int[] arrayOfInt = localParcel2.createIntArray();
          return arrayOfInt;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public int pauseTask(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.service.IDownloadManager");
          localParcel1.writeInt(paramInt);
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

      public int removeTask(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.service.IDownloadManager");
          localParcel1.writeInt(paramInt);
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

      public int resumeTask(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.service.IDownloadManager");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(5, localParcel1, localParcel2, 0);
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

      public int setEventListener(IDownloadEventListener paramIDownloadEventListener)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.service.IDownloadManager");
          if (paramIDownloadEventListener != null);
          for (IBinder localIBinder = paramIDownloadEventListener.asBinder(); ; localIBinder = null)
          {
            localParcel1.writeStrongBinder(localIBinder);
            this.mRemote.transact(8, localParcel1, localParcel2, 0);
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
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.service.IDownloadManager
 * JD-Core Version:    0.6.2
 */