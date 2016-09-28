package com.starcor.remote_key;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import android.view.KeyEvent;

public abstract interface IAirCommandListener extends IInterface
{
  public abstract boolean onKeyEvent(KeyEvent paramKeyEvent)
    throws RemoteException;

  public abstract boolean onMessage(String paramString, Bundle paramBundle)
    throws RemoteException;

  public abstract boolean onTextInput(String paramString)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements IAirCommandListener
  {
    private static final String DESCRIPTOR = "com.starcor.remote_key.IAirCommandListener";
    static final int TRANSACTION_onKeyEvent = 1;
    static final int TRANSACTION_onMessage = 3;
    static final int TRANSACTION_onTextInput = 2;

    public Stub()
    {
      attachInterface(this, "com.starcor.remote_key.IAirCommandListener");
    }

    public static IAirCommandListener asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.starcor.remote_key.IAirCommandListener");
      if ((localIInterface != null) && ((localIInterface instanceof IAirCommandListener)))
        return (IAirCommandListener)localIInterface;
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
        paramParcel2.writeString("com.starcor.remote_key.IAirCommandListener");
        return true;
      case 1:
        paramParcel1.enforceInterface("com.starcor.remote_key.IAirCommandListener");
        if (paramParcel1.readInt() != 0);
        for (KeyEvent localKeyEvent = (KeyEvent)KeyEvent.CREATOR.createFromParcel(paramParcel1); ; localKeyEvent = null)
        {
          boolean bool3 = onKeyEvent(localKeyEvent);
          paramParcel2.writeNoException();
          int k = 0;
          if (bool3)
            k = 1;
          paramParcel2.writeInt(k);
          return true;
        }
      case 2:
        paramParcel1.enforceInterface("com.starcor.remote_key.IAirCommandListener");
        boolean bool2 = onTextInput(paramParcel1.readString());
        paramParcel2.writeNoException();
        int j = 0;
        if (bool2)
          j = 1;
        paramParcel2.writeInt(j);
        return true;
      case 3:
      }
      paramParcel1.enforceInterface("com.starcor.remote_key.IAirCommandListener");
      String str = paramParcel1.readString();
      Bundle localBundle;
      if (paramParcel1.readInt() != 0)
      {
        localBundle = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);
        boolean bool1 = onMessage(str, localBundle);
        paramParcel2.writeNoException();
        if (!bool1)
          break label251;
      }
      label251: for (int i = 1; ; i = 0)
      {
        paramParcel2.writeInt(i);
        if (localBundle == null)
          break label257;
        paramParcel2.writeInt(1);
        localBundle.writeToParcel(paramParcel2, 1);
        return true;
        localBundle = null;
        break;
      }
      label257: paramParcel2.writeInt(0);
      return true;
    }

    private static class Proxy
      implements IAirCommandListener
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
        return "com.starcor.remote_key.IAirCommandListener";
      }

      public boolean onKeyEvent(KeyEvent paramKeyEvent)
        throws RemoteException
      {
        boolean bool = true;
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.starcor.remote_key.IAirCommandListener");
            if (paramKeyEvent != null)
            {
              localParcel1.writeInt(1);
              paramKeyEvent.writeToParcel(localParcel1, 0);
              this.mRemote.transact(1, localParcel1, localParcel2, 0);
              localParcel2.readException();
              int i = localParcel2.readInt();
              if (i != 0)
                return bool;
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
          bool = false;
        }
      }

      public boolean onMessage(String paramString, Bundle paramBundle)
        throws RemoteException
      {
        boolean bool = true;
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.starcor.remote_key.IAirCommandListener");
            localParcel1.writeString(paramString);
            if (paramBundle != null)
            {
              localParcel1.writeInt(1);
              paramBundle.writeToParcel(localParcel1, 0);
              this.mRemote.transact(3, localParcel1, localParcel2, 0);
              localParcel2.readException();
              if (localParcel2.readInt() != 0)
              {
                if (localParcel2.readInt() != 0)
                  paramBundle.readFromParcel(localParcel2);
                return bool;
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
          bool = false;
        }
      }

      public boolean onTextInput(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.starcor.remote_key.IAirCommandListener");
          localParcel1.writeString(paramString);
          this.mRemote.transact(2, localParcel1, localParcel2, 0);
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
 * Qualified Name:     com.starcor.remote_key.IAirCommandListener
 * JD-Core Version:    0.6.2
 */