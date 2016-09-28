package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;

public abstract interface IWindowManager extends IInterface
{
  public abstract boolean injectKeyEvent(KeyEvent paramKeyEvent, boolean paramBoolean)
    throws RemoteException;

  public abstract boolean injectPointerEvent(MotionEvent paramMotionEvent, boolean paramBoolean)
    throws RemoteException;

  public abstract boolean injectTrackballEvent(MotionEvent paramMotionEvent, boolean paramBoolean)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements IWindowManager
  {
    private static final String DESCRIPTOR = "android.view.IWindowManager";
    static final int TRANSACTION_injectKeyEvent = 1;
    static final int TRANSACTION_injectPointerEvent = 2;
    static final int TRANSACTION_injectTrackballEvent = 3;

    public Stub()
    {
      attachInterface(this, "android.view.IWindowManager");
    }

    public static IWindowManager asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("android.view.IWindowManager");
      if ((localIInterface != null) && ((localIInterface instanceof IWindowManager)))
        return (IWindowManager)localIInterface;
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
        paramParcel2.writeString("android.view.IWindowManager");
        return true;
      case 1:
        paramParcel1.enforceInterface("android.view.IWindowManager");
        KeyEvent localKeyEvent;
        if (paramParcel1.readInt() != 0)
        {
          localKeyEvent = (KeyEvent)KeyEvent.CREATOR.createFromParcel(paramParcel1);
          if (paramParcel1.readInt() == 0)
            break label138;
        }
        for (boolean bool5 = true; ; bool5 = false)
        {
          boolean bool6 = injectKeyEvent(localKeyEvent, bool5);
          paramParcel2.writeNoException();
          int k = 0;
          if (bool6)
            k = 1;
          paramParcel2.writeInt(k);
          return true;
          localKeyEvent = null;
          break;
        }
      case 2:
        label138: paramParcel1.enforceInterface("android.view.IWindowManager");
        MotionEvent localMotionEvent2;
        if (paramParcel1.readInt() != 0)
        {
          localMotionEvent2 = (MotionEvent)MotionEvent.CREATOR.createFromParcel(paramParcel1);
          if (paramParcel1.readInt() == 0)
            break label220;
        }
        label220: for (boolean bool3 = true; ; bool3 = false)
        {
          boolean bool4 = injectPointerEvent(localMotionEvent2, bool3);
          paramParcel2.writeNoException();
          int j = 0;
          if (bool4)
            j = 1;
          paramParcel2.writeInt(j);
          return true;
          localMotionEvent2 = null;
          break;
        }
      case 3:
      }
      paramParcel1.enforceInterface("android.view.IWindowManager");
      MotionEvent localMotionEvent1;
      if (paramParcel1.readInt() != 0)
      {
        localMotionEvent1 = (MotionEvent)MotionEvent.CREATOR.createFromParcel(paramParcel1);
        if (paramParcel1.readInt() == 0)
          break label302;
      }
      label302: for (boolean bool1 = true; ; bool1 = false)
      {
        boolean bool2 = injectTrackballEvent(localMotionEvent1, bool1);
        paramParcel2.writeNoException();
        int i = 0;
        if (bool2)
          i = 1;
        paramParcel2.writeInt(i);
        return true;
        localMotionEvent1 = null;
        break;
      }
    }

    private static class Proxy
      implements IWindowManager
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
        return "android.view.IWindowManager";
      }

      public boolean injectKeyEvent(KeyEvent paramKeyEvent, boolean paramBoolean)
        throws RemoteException
      {
        int i = 1;
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel1.writeInterfaceToken("android.view.IWindowManager");
            if (paramKeyEvent != null)
            {
              localParcel1.writeInt(1);
              paramKeyEvent.writeToParcel(localParcel1, 0);
              break label126;
              localParcel1.writeInt(j);
              this.mRemote.transact(1, localParcel1, localParcel2, 0);
              localParcel2.readException();
              int k = localParcel2.readInt();
              if (k != 0)
                label79: return i;
            }
            else
            {
              localParcel1.writeInt(0);
            }
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
          label126: 
          do
          {
            j = 0;
            break;
            i = 0;
            break label79;
          }
          while (!paramBoolean);
          int j = i;
        }
      }

      public boolean injectPointerEvent(MotionEvent paramMotionEvent, boolean paramBoolean)
        throws RemoteException
      {
        int i = 1;
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel1.writeInterfaceToken("android.view.IWindowManager");
            if (paramMotionEvent != null)
            {
              localParcel1.writeInt(1);
              paramMotionEvent.writeToParcel(localParcel1, 0);
              break label126;
              localParcel1.writeInt(j);
              this.mRemote.transact(2, localParcel1, localParcel2, 0);
              localParcel2.readException();
              int k = localParcel2.readInt();
              if (k != 0)
                label79: return i;
            }
            else
            {
              localParcel1.writeInt(0);
            }
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
          label126: 
          do
          {
            j = 0;
            break;
            i = 0;
            break label79;
          }
          while (!paramBoolean);
          int j = i;
        }
      }

      public boolean injectTrackballEvent(MotionEvent paramMotionEvent, boolean paramBoolean)
        throws RemoteException
      {
        int i = 1;
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel1.writeInterfaceToken("android.view.IWindowManager");
            if (paramMotionEvent != null)
            {
              localParcel1.writeInt(1);
              paramMotionEvent.writeToParcel(localParcel1, 0);
              break label126;
              localParcel1.writeInt(j);
              this.mRemote.transact(3, localParcel1, localParcel2, 0);
              localParcel2.readException();
              int k = localParcel2.readInt();
              if (k != 0)
                label79: return i;
            }
            else
            {
              localParcel1.writeInt(0);
            }
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
          label126: 
          do
          {
            j = 0;
            break;
            i = 0;
            break label79;
          }
          while (!paramBoolean);
          int j = i;
        }
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     android.view.IWindowManager
 * JD-Core Version:    0.6.2
 */