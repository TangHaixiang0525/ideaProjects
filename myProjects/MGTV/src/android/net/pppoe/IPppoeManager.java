package android.net.pppoe;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface IPppoeManager extends IInterface
{
  public abstract String getPppoeStatus()
    throws RemoteException;

  public abstract void setPppoeStatus(String paramString, boolean paramBoolean)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements IPppoeManager
  {
    public static IPppoeManager asInterface(IBinder paramIBinder)
    {
      throw new RuntimeException("stub");
    }

    public IBinder asBinder()
    {
      throw new RuntimeException("stub");
    }

    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      throw new RuntimeException("stub");
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     android.net.pppoe.IPppoeManager
 * JD-Core Version:    0.6.2
 */