package miui.net;

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
  public abstract Bundle getMiCloudAccessToken(Account paramAccount, Bundle paramBundle)
    throws RemoteException;

  public abstract Bundle getMiCloudUserInfo(Account paramAccount, Bundle paramBundle)
    throws RemoteException;

  public abstract Bundle getSnsAccessToken(Account paramAccount, Bundle paramBundle)
    throws RemoteException;

  public abstract void invalidateAccessToken(Account paramAccount, Bundle paramBundle)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements IXiaomiAuthService
  {
    private static final String DESCRIPTOR = "miui.net.IXiaomiAuthService";
    static final int TRANSACTION_getMiCloudAccessToken = 2;
    static final int TRANSACTION_getMiCloudUserInfo = 1;
    static final int TRANSACTION_getSnsAccessToken = 3;
    static final int TRANSACTION_invalidateAccessToken = 4;

    public Stub()
    {
      attachInterface(this, "miui.net.IXiaomiAuthService");
    }

    public static IXiaomiAuthService asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("miui.net.IXiaomiAuthService");
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
        paramParcel2.writeString("miui.net.IXiaomiAuthService");
        return true;
      case 1:
        paramParcel1.enforceInterface("miui.net.IXiaomiAuthService");
        Account localAccount4;
        if (paramParcel1.readInt() != 0)
        {
          localAccount4 = (Account)Account.CREATOR.createFromParcel(paramParcel1);
          if (paramParcel1.readInt() == 0)
            break label157;
        }
        for (Bundle localBundle6 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1); ; localBundle6 = null)
        {
          Bundle localBundle7 = getMiCloudUserInfo(localAccount4, localBundle6);
          paramParcel2.writeNoException();
          if (localBundle7 == null)
            break label163;
          paramParcel2.writeInt(1);
          localBundle7.writeToParcel(paramParcel2, 1);
          return true;
          localAccount4 = null;
          break;
        }
        paramParcel2.writeInt(0);
        return true;
      case 2:
        paramParcel1.enforceInterface("miui.net.IXiaomiAuthService");
        Account localAccount3;
        if (paramParcel1.readInt() != 0)
        {
          localAccount3 = (Account)Account.CREATOR.createFromParcel(paramParcel1);
          if (paramParcel1.readInt() == 0)
            break label257;
        }
        for (Bundle localBundle4 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1); ; localBundle4 = null)
        {
          Bundle localBundle5 = getMiCloudAccessToken(localAccount3, localBundle4);
          paramParcel2.writeNoException();
          if (localBundle5 == null)
            break label263;
          paramParcel2.writeInt(1);
          localBundle5.writeToParcel(paramParcel2, 1);
          return true;
          localAccount3 = null;
          break;
        }
        paramParcel2.writeInt(0);
        return true;
      case 3:
        label157: label163: paramParcel1.enforceInterface("miui.net.IXiaomiAuthService");
        label257: label263: Account localAccount2;
        if (paramParcel1.readInt() != 0)
        {
          localAccount2 = (Account)Account.CREATOR.createFromParcel(paramParcel1);
          if (paramParcel1.readInt() == 0)
            break label357;
        }
        label357: for (Bundle localBundle2 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1); ; localBundle2 = null)
        {
          Bundle localBundle3 = getSnsAccessToken(localAccount2, localBundle2);
          paramParcel2.writeNoException();
          if (localBundle3 == null)
            break label363;
          paramParcel2.writeInt(1);
          localBundle3.writeToParcel(paramParcel2, 1);
          return true;
          localAccount2 = null;
          break;
        }
        label363: paramParcel2.writeInt(0);
        return true;
      case 4:
      }
      paramParcel1.enforceInterface("miui.net.IXiaomiAuthService");
      Account localAccount1;
      if (paramParcel1.readInt() != 0)
      {
        localAccount1 = (Account)Account.CREATOR.createFromParcel(paramParcel1);
        if (paramParcel1.readInt() == 0)
          break label438;
      }
      label438: for (Bundle localBundle1 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1); ; localBundle1 = null)
      {
        invalidateAccessToken(localAccount1, localBundle1);
        paramParcel2.writeNoException();
        return true;
        localAccount1 = null;
        break;
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

      public String getInterfaceDescriptor()
      {
        return "miui.net.IXiaomiAuthService";
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
            localParcel1.writeInterfaceToken("miui.net.IXiaomiAuthService");
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
            localParcel1.writeInterfaceToken("miui.net.IXiaomiAuthService");
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
            localParcel1.writeInterfaceToken("miui.net.IXiaomiAuthService");
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
            localParcel1.writeInterfaceToken("miui.net.IXiaomiAuthService");
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
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     miui.net.IXiaomiAuthService
 * JD-Core Version:    0.6.2
 */