package com.mstar.android.sip;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface ISipSessionListener extends IInterface
{
  public abstract void onCallBusy(ISipSession paramISipSession)
    throws RemoteException;

  public abstract void onCallChangeFailed(ISipSession paramISipSession, int paramInt, String paramString)
    throws RemoteException;

  public abstract void onCallEnded(ISipSession paramISipSession)
    throws RemoteException;

  public abstract void onCallEstablished(ISipSession paramISipSession, String paramString)
    throws RemoteException;

  public abstract void onCallTransferring(ISipSession paramISipSession, String paramString)
    throws RemoteException;

  public abstract void onCalling(ISipSession paramISipSession)
    throws RemoteException;

  public abstract void onError(ISipSession paramISipSession, int paramInt, String paramString)
    throws RemoteException;

  public abstract void onRegistering(ISipSession paramISipSession)
    throws RemoteException;

  public abstract void onRegistrationDone(ISipSession paramISipSession, int paramInt)
    throws RemoteException;

  public abstract void onRegistrationFailed(ISipSession paramISipSession, int paramInt, String paramString)
    throws RemoteException;

  public abstract void onRegistrationTimeout(ISipSession paramISipSession)
    throws RemoteException;

  public abstract void onRinging(ISipSession paramISipSession, SipProfile paramSipProfile, String paramString)
    throws RemoteException;

  public abstract void onRingingBack(ISipSession paramISipSession)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements ISipSessionListener
  {
    public static ISipSessionListener asInterface(IBinder paramIBinder)
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
 * Qualified Name:     com.mstar.android.sip.ISipSessionListener
 * JD-Core Version:    0.6.2
 */