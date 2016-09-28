package com.starcor.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;

public abstract interface IDownloadEventListener extends IInterface
{
  public abstract int onEvent(String paramString, int paramInt, DownloadTaskInfo paramDownloadTaskInfo)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements IDownloadEventListener
  {
    private static final String DESCRIPTOR = "com.starcor.service.IDownloadEventListener";
    static final int TRANSACTION_onEvent = 1;

    public Stub()
    {
      attachInterface(this, "com.starcor.service.IDownloadEventListener");
    }

    public static IDownloadEventListener asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.starcor.service.IDownloadEventListener");
      if ((localIInterface != null) && ((localIInterface instanceof IDownloadEventListener)))
        return (IDownloadEventListener)localIInterface;
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
        paramParcel2.writeString("com.starcor.service.IDownloadEventListener");
        return true;
      case 1:
      }
      paramParcel1.enforceInterface("com.starcor.service.IDownloadEventListener");
      String str = paramParcel1.readString();
      int i = paramParcel1.readInt();
      if (paramParcel1.readInt() != 0);
      for (DownloadTaskInfo localDownloadTaskInfo = (DownloadTaskInfo)DownloadTaskInfo.CREATOR.createFromParcel(paramParcel1); ; localDownloadTaskInfo = null)
      {
        int j = onEvent(str, i, localDownloadTaskInfo);
        paramParcel2.writeNoException();
        paramParcel2.writeInt(j);
        return true;
      }
    }

    private static class Proxy
      implements IDownloadEventListener
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
        return "com.starcor.service.IDownloadEventListener";
      }

      public int onEvent(String paramString, int paramInt, DownloadTaskInfo paramDownloadTaskInfo)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.service.IDownloadEventListener");
          localParcel1.writeString(paramString);
          localParcel1.writeInt(paramInt);
          if (paramDownloadTaskInfo != null)
          {
            localParcel1.writeInt(1);
            paramDownloadTaskInfo.writeToParcel(localParcel1, 0);
          }
          while (true)
          {
            this.mRemote.transact(1, localParcel1, localParcel2, 0);
            localParcel2.readException();
            int i = localParcel2.readInt();
            return i;
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
 * Qualified Name:     com.starcor.service.IDownloadEventListener
 * JD-Core Version:    0.6.2
 */