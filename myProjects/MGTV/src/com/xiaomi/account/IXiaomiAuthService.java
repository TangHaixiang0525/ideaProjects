package com.xiaomi.account;

import android.accounts.Account;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;

public abstract interface IXiaomiAuthService extends IInterface
{
  public abstract void getAccessTokenInResponse(IXiaomiAuthResponse paramIXiaomiAuthResponse, Bundle paramBundle, int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract Bundle getMiCloudAccessToken(Account paramAccount, Bundle paramBundle)
    throws RemoteException;

  public abstract Bundle getMiCloudUserInfo(Account paramAccount, Bundle paramBundle)
    throws RemoteException;

  public abstract Bundle getSnsAccessToken(Account paramAccount, Bundle paramBundle)
    throws RemoteException;

  public abstract void invalidateAccessToken(Account paramAccount, Bundle paramBundle)
    throws RemoteException;

  public abstract boolean supportResponseWay()
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements IXiaomiAuthService
  {
    private static final String DESCRIPTOR = "com.xiaomi.account.IXiaomiAuthService";
    static final int TRANSACTION_getAccessTokenInResponse = 6;
    static final int TRANSACTION_getMiCloudAccessToken = 2;
    static final int TRANSACTION_getMiCloudUserInfo = 1;
    static final int TRANSACTION_getSnsAccessToken = 3;
    static final int TRANSACTION_invalidateAccessToken = 4;
    static final int TRANSACTION_supportResponseWay = 5;

    public Stub()
    {
      attachInterface(this, "com.xiaomi.account.IXiaomiAuthService");
    }

    public static IXiaomiAuthService asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.xiaomi.account.IXiaomiAuthService");
      if ((localIInterface != null) && ((localIInterface instanceof IXiaomiAuthService)))
        return (IXiaomiAuthService)localIInterface;
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
        paramParcel2.writeString("com.xiaomi.account.IXiaomiAuthService");
        return true;
      case 1:
        paramParcel1.enforceInterface("com.xiaomi.account.IXiaomiAuthService");
        Account localAccount4;
        if (paramParcel1.readInt() != 0)
        {
          localAccount4 = (Account)Account.CREATOR.createFromParcel(paramParcel1);
          if (paramParcel1.readInt() == 0)
            break label173;
        }
        for (Bundle localBundle7 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1); ; localBundle7 = null)
        {
          Bundle localBundle8 = getMiCloudUserInfo(localAccount4, localBundle7);
          paramParcel2.writeNoException();
          if (localBundle8 == null)
            break label179;
          paramParcel2.writeInt(1);
          localBundle8.writeToParcel(paramParcel2, 1);
          return true;
          localAccount4 = null;
          break;
        }
        paramParcel2.writeInt(0);
        return true;
      case 2:
        paramParcel1.enforceInterface("com.xiaomi.account.IXiaomiAuthService");
        Account localAccount3;
        if (paramParcel1.readInt() != 0)
        {
          localAccount3 = (Account)Account.CREATOR.createFromParcel(paramParcel1);
          if (paramParcel1.readInt() == 0)
            break label273;
        }
        for (Bundle localBundle5 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1); ; localBundle5 = null)
        {
          Bundle localBundle6 = getMiCloudAccessToken(localAccount3, localBundle5);
          paramParcel2.writeNoException();
          if (localBundle6 == null)
            break label279;
          paramParcel2.writeInt(1);
          localBundle6.writeToParcel(paramParcel2, 1);
          return true;
          localAccount3 = null;
          break;
        }
        paramParcel2.writeInt(0);
        return true;
      case 3:
        paramParcel1.enforceInterface("com.xiaomi.account.IXiaomiAuthService");
        Account localAccount2;
        if (paramParcel1.readInt() != 0)
        {
          localAccount2 = (Account)Account.CREATOR.createFromParcel(paramParcel1);
          if (paramParcel1.readInt() == 0)
            break label373;
        }
        for (Bundle localBundle3 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1); ; localBundle3 = null)
        {
          Bundle localBundle4 = getSnsAccessToken(localAccount2, localBundle3);
          paramParcel2.writeNoException();
          if (localBundle4 == null)
            break label379;
          paramParcel2.writeInt(1);
          localBundle4.writeToParcel(paramParcel2, 1);
          return true;
          localAccount2 = null;
          break;
        }
        paramParcel2.writeInt(0);
        return true;
      case 4:
        paramParcel1.enforceInterface("com.xiaomi.account.IXiaomiAuthService");
        Account localAccount1;
        if (paramParcel1.readInt() != 0)
        {
          localAccount1 = (Account)Account.CREATOR.createFromParcel(paramParcel1);
          if (paramParcel1.readInt() == 0)
            break label454;
        }
        for (Bundle localBundle2 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1); ; localBundle2 = null)
        {
          invalidateAccessToken(localAccount1, localBundle2);
          paramParcel2.writeNoException();
          return true;
          localAccount1 = null;
          break;
        }
      case 5:
        label173: label179: label373: label379: paramParcel1.enforceInterface("com.xiaomi.account.IXiaomiAuthService");
        label273: label279: label454: boolean bool = supportResponseWay();
        paramParcel2.writeNoException();
        int i = 0;
        if (bool)
          i = 1;
        paramParcel2.writeInt(i);
        return true;
      case 6:
      }
      paramParcel1.enforceInterface("com.xiaomi.account.IXiaomiAuthService");
      IXiaomiAuthResponse localIXiaomiAuthResponse = IXiaomiAuthResponse.Stub.asInterface(paramParcel1.readStrongBinder());
      if (paramParcel1.readInt() != 0);
      for (Bundle localBundle1 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1); ; localBundle1 = null)
      {
        getAccessTokenInResponse(localIXiaomiAuthResponse, localBundle1, paramParcel1.readInt(), paramParcel1.readInt());
        paramParcel2.writeNoException();
        return true;
      }
    }

    private static class Proxy
      implements IXiaomiAuthService
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

      public void getAccessTokenInResponse(IXiaomiAuthResponse paramIXiaomiAuthResponse, Bundle paramBundle, int paramInt1, int paramInt2)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.xiaomi.account.IXiaomiAuthService");
          IBinder localIBinder;
          if (paramIXiaomiAuthResponse != null)
          {
            localIBinder = paramIXiaomiAuthResponse.asBinder();
            localParcel1.writeStrongBinder(localIBinder);
            if (paramBundle == null)
              break label105;
            localParcel1.writeInt(1);
            paramBundle.writeToParcel(localParcel1, 0);
          }
          while (true)
          {
            localParcel1.writeInt(paramInt1);
            localParcel1.writeInt(paramInt2);
            this.mRemote.transact(6, localParcel1, localParcel2, 0);
            localParcel2.readException();
            return;
            localIBinder = null;
            break;
            label105: localParcel1.writeInt(0);
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
        return "com.xiaomi.account.IXiaomiAuthService";
      }

      public Bundle getMiCloudAccessToken(Account paramAccount, Bundle paramBundle)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.xiaomi.account.IXiaomiAuthService");
            if (paramAccount != null)
            {
              localParcel1.writeInt(1);
              paramAccount.writeToParcel(localParcel1, 0);
              if (paramBundle != null)
              {
                localParcel1.writeInt(1);
                paramBundle.writeToParcel(localParcel1, 0);
                this.mRemote.transact(2, localParcel1, localParcel2, 0);
                localParcel2.readException();
                if (localParcel2.readInt() == 0)
                  break label130;
                localBundle = (Bundle)Bundle.CREATOR.createFromParcel(localParcel2);
                return localBundle;
              }
            }
            else
            {
              localParcel1.writeInt(0);
              continue;
            }
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
          localParcel1.writeInt(0);
          continue;
          label130: Bundle localBundle = null;
        }
      }

      public Bundle getMiCloudUserInfo(Account paramAccount, Bundle paramBundle)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.xiaomi.account.IXiaomiAuthService");
            if (paramAccount != null)
            {
              localParcel1.writeInt(1);
              paramAccount.writeToParcel(localParcel1, 0);
              if (paramBundle != null)
              {
                localParcel1.writeInt(1);
                paramBundle.writeToParcel(localParcel1, 0);
                this.mRemote.transact(1, localParcel1, localParcel2, 0);
                localParcel2.readException();
                if (localParcel2.readInt() == 0)
                  break label130;
                localBundle = (Bundle)Bundle.CREATOR.createFromParcel(localParcel2);
                return localBundle;
              }
            }
            else
            {
              localParcel1.writeInt(0);
              continue;
            }
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
          localParcel1.writeInt(0);
          continue;
          label130: Bundle localBundle = null;
        }
      }

      public Bundle getSnsAccessToken(Account paramAccount, Bundle paramBundle)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.xiaomi.account.IXiaomiAuthService");
            if (paramAccount != null)
            {
              localParcel1.writeInt(1);
              paramAccount.writeToParcel(localParcel1, 0);
              if (paramBundle != null)
              {
                localParcel1.writeInt(1);
                paramBundle.writeToParcel(localParcel1, 0);
                this.mRemote.transact(3, localParcel1, localParcel2, 0);
                localParcel2.readException();
                if (localParcel2.readInt() == 0)
                  break label130;
                localBundle = (Bundle)Bundle.CREATOR.createFromParcel(localParcel2);
                return localBundle;
              }
            }
            else
            {
              localParcel1.writeInt(0);
              continue;
            }
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
          localParcel1.writeInt(0);
          continue;
          label130: Bundle localBundle = null;
        }
      }

      public void invalidateAccessToken(Account paramAccount, Bundle paramBundle)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.xiaomi.account.IXiaomiAuthService");
            if (paramAccount != null)
            {
              localParcel1.writeInt(1);
              paramAccount.writeToParcel(localParcel1, 0);
              if (paramBundle != null)
              {
                localParcel1.writeInt(1);
                paramBundle.writeToParcel(localParcel1, 0);
                this.mRemote.transact(4, localParcel1, localParcel2, 0);
                localParcel2.readException();
              }
            }
            else
            {
              localParcel1.writeInt(0);
              continue;
            }
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
          localParcel1.writeInt(0);
        }
      }

      public boolean supportResponseWay()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.xiaomi.account.IXiaomiAuthService");
          this.mRemote.transact(5, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          boolean bool = false;
          if (i != 0)
            bool = true;
          return bool;
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
 * Qualified Name:     com.xiaomi.account.IXiaomiAuthService
 * JD-Core Version:    0.6.2
 */