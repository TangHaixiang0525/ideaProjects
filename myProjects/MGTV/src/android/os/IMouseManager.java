package android.os;

public abstract interface IMouseManager extends IInterface
{
  public abstract void addApk(String paramString)
    throws RemoteException;

  public abstract String[] getApkList()
    throws RemoteException;

  public abstract boolean getMouseControlFlag()
    throws RemoteException;

  public abstract boolean hasApk(String paramString)
    throws RemoteException;

  public abstract void removeAllApk()
    throws RemoteException;

  public abstract void removeApk(String paramString)
    throws RemoteException;

  public abstract void sendKeyEvent(int paramInt1, int paramInt2, long paramLong)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements IMouseManager
  {
    public static IMouseManager asInterface(IBinder paramIBinder)
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
 * Qualified Name:     android.os.IMouseManager
 * JD-Core Version:    0.6.2
 */