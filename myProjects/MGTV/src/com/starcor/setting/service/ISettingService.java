package com.starcor.setting.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;

public abstract interface ISettingService extends IInterface
{
  public abstract long getCurrentTimeMillis()
    throws RemoteException;

  public abstract int getCurrentTimeZone()
    throws RemoteException;

  public abstract NetworkStateInfo getNetworkStateInfo()
    throws RemoteException;

  public abstract String getSystemProperties(String paramString)
    throws RemoteException;

  public abstract void saveRedisplayConfig(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws RemoteException;

  public abstract boolean setCurrentTimeMillis(long paramLong)
    throws RemoteException;

  public abstract void setCurrentTimeZone(int paramInt)
    throws RemoteException;

  public abstract void setRedisplayRate(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
    throws RemoteException;

  public abstract void setSystemProperties(String paramString1, String paramString2)
    throws RemoteException;

  public abstract void showExternalNetworkSetting()
    throws RemoteException;

  public abstract void showSystemSetting()
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements ISettingService
  {
    private static final String DESCRIPTOR = "com.starcor.setting.service.ISettingService";
    static final int TRANSACTION_getCurrentTimeMillis = 2;
    static final int TRANSACTION_getCurrentTimeZone = 4;
    static final int TRANSACTION_getNetworkStateInfo = 9;
    static final int TRANSACTION_getSystemProperties = 5;
    static final int TRANSACTION_saveRedisplayConfig = 8;
    static final int TRANSACTION_setCurrentTimeMillis = 1;
    static final int TRANSACTION_setCurrentTimeZone = 3;
    static final int TRANSACTION_setRedisplayRate = 7;
    static final int TRANSACTION_setSystemProperties = 6;
    static final int TRANSACTION_showExternalNetworkSetting = 11;
    static final int TRANSACTION_showSystemSetting = 10;

    public Stub()
    {
      attachInterface(this, "com.starcor.setting.service.ISettingService");
    }

    public static ISettingService asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.starcor.setting.service.ISettingService");
      if ((localIInterface != null) && ((localIInterface instanceof ISettingService)))
        return (ISettingService)localIInterface;
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
        paramParcel2.writeString("com.starcor.setting.service.ISettingService");
        return true;
      case 1:
        paramParcel1.enforceInterface("com.starcor.setting.service.ISettingService");
        boolean bool = setCurrentTimeMillis(paramParcel1.readLong());
        paramParcel2.writeNoException();
        if (bool);
        for (int j = 1; ; j = 0)
        {
          paramParcel2.writeInt(j);
          return true;
        }
      case 2:
        paramParcel1.enforceInterface("com.starcor.setting.service.ISettingService");
        long l = getCurrentTimeMillis();
        paramParcel2.writeNoException();
        paramParcel2.writeLong(l);
        return true;
      case 3:
        paramParcel1.enforceInterface("com.starcor.setting.service.ISettingService");
        setCurrentTimeZone(paramParcel1.readInt());
        paramParcel2.writeNoException();
        return true;
      case 4:
        paramParcel1.enforceInterface("com.starcor.setting.service.ISettingService");
        int i = getCurrentTimeZone();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i);
        return true;
      case 5:
        paramParcel1.enforceInterface("com.starcor.setting.service.ISettingService");
        String str = getSystemProperties(paramParcel1.readString());
        paramParcel2.writeNoException();
        paramParcel2.writeString(str);
        return true;
      case 6:
        paramParcel1.enforceInterface("com.starcor.setting.service.ISettingService");
        setSystemProperties(paramParcel1.readString(), paramParcel1.readString());
        paramParcel2.writeNoException();
        return true;
      case 7:
        paramParcel1.enforceInterface("com.starcor.setting.service.ISettingService");
        setRedisplayRate(paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        return true;
      case 8:
        paramParcel1.enforceInterface("com.starcor.setting.service.ISettingService");
        saveRedisplayConfig(paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.readInt(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        return true;
      case 9:
        paramParcel1.enforceInterface("com.starcor.setting.service.ISettingService");
        NetworkStateInfo localNetworkStateInfo = getNetworkStateInfo();
        paramParcel2.writeNoException();
        if (localNetworkStateInfo != null)
        {
          paramParcel2.writeInt(1);
          localNetworkStateInfo.writeToParcel(paramParcel2, 1);
        }
        while (true)
        {
          return true;
          paramParcel2.writeInt(0);
        }
      case 10:
        paramParcel1.enforceInterface("com.starcor.setting.service.ISettingService");
        showSystemSetting();
        paramParcel2.writeNoException();
        return true;
      case 11:
      }
      paramParcel1.enforceInterface("com.starcor.setting.service.ISettingService");
      showExternalNetworkSetting();
      paramParcel2.writeNoException();
      return true;
    }

    private static class Proxy
      implements ISettingService
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

      public long getCurrentTimeMillis()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.setting.service.ISettingService");
          this.mRemote.transact(2, localParcel1, localParcel2, 0);
          localParcel2.readException();
          long l = localParcel2.readLong();
          return l;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public int getCurrentTimeZone()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.setting.service.ISettingService");
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

      public String getInterfaceDescriptor()
      {
        return "com.starcor.setting.service.ISettingService";
      }

      public NetworkStateInfo getNetworkStateInfo()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.setting.service.ISettingService");
          this.mRemote.transact(9, localParcel1, localParcel2, 0);
          localParcel2.readException();
          if (localParcel2.readInt() != 0)
          {
            localNetworkStateInfo = (NetworkStateInfo)NetworkStateInfo.CREATOR.createFromParcel(localParcel2);
            return localNetworkStateInfo;
          }
          NetworkStateInfo localNetworkStateInfo = null;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public String getSystemProperties(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.setting.service.ISettingService");
          localParcel1.writeString(paramString);
          this.mRemote.transact(5, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void saveRedisplayConfig(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.setting.service.ISettingService");
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          localParcel1.writeInt(paramInt3);
          localParcel1.writeInt(paramInt4);
          this.mRemote.transact(8, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public boolean setCurrentTimeMillis(long paramLong)
        throws RemoteException
      {
        boolean bool = true;
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.setting.service.ISettingService");
          localParcel1.writeLong(paramLong);
          this.mRemote.transact(1, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          if (i != 0)
            return bool;
          bool = false;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void setCurrentTimeZone(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.setting.service.ISettingService");
          localParcel1.writeInt(paramInt);
          this.mRemote.transact(3, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void setRedisplayRate(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.setting.service.ISettingService");
          localParcel1.writeInt(paramInt1);
          localParcel1.writeInt(paramInt2);
          localParcel1.writeInt(paramInt3);
          localParcel1.writeInt(paramInt4);
          localParcel1.writeInt(paramInt5);
          this.mRemote.transact(7, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void setSystemProperties(String paramString1, String paramString2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.setting.service.ISettingService");
          localParcel1.writeString(paramString1);
          localParcel1.writeString(paramString2);
          this.mRemote.transact(6, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void showExternalNetworkSetting()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.setting.service.ISettingService");
          this.mRemote.transact(11, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void showSystemSetting()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.setting.service.ISettingService");
          this.mRemote.transact(10, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
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
 * Qualified Name:     com.starcor.setting.service.ISettingService
 * JD-Core Version:    0.6.2
 */