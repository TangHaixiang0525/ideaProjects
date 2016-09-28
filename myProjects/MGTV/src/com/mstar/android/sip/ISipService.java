package com.mstar.android.sip;

import android.app.PendingIntent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface ISipService extends IInterface
{
  public abstract void close(String paramString)
    throws RemoteException;

  public abstract ISipSession createSession(SipProfile paramSipProfile, ISipSessionListener paramISipSessionListener)
    throws RemoteException;

  public abstract SipProfile[] getListOfProfiles()
    throws RemoteException;

  public abstract ISipSession getPendingSession(String paramString)
    throws RemoteException;

  public abstract boolean isOpened(String paramString)
    throws RemoteException;

  public abstract boolean isRegistered(String paramString)
    throws RemoteException;

  public abstract void open(SipProfile paramSipProfile)
    throws RemoteException;

  public abstract void open3(SipProfile paramSipProfile, PendingIntent paramPendingIntent, ISipSessionListener paramISipSessionListener)
    throws RemoteException;

  public abstract void setRegistrationListener(String paramString, ISipSessionListener paramISipSessionListener)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements ISipService
  {
    public static ISipService asInterface(IBinder paramIBinder)
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
 * Qualified Name:     com.mstar.android.sip.ISipService
 * JD-Core Version:    0.6.2
 */