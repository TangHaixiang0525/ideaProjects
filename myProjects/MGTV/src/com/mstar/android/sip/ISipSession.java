package com.mstar.android.sip;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface ISipSession extends IInterface
{
  public abstract void answerCall(String paramString, int paramInt)
    throws RemoteException;

  public abstract void changeCall(String paramString, int paramInt)
    throws RemoteException;

  public abstract void endCall()
    throws RemoteException;

  public abstract String getCallId()
    throws RemoteException;

  public abstract String getLocalIp()
    throws RemoteException;

  public abstract SipProfile getLocalProfile()
    throws RemoteException;

  public abstract SipProfile getPeerProfile()
    throws RemoteException;

  public abstract int getState()
    throws RemoteException;

  public abstract boolean isInCall()
    throws RemoteException;

  public abstract void makeCall(SipProfile paramSipProfile, String paramString, int paramInt)
    throws RemoteException;

  public abstract void register(int paramInt)
    throws RemoteException;

  public abstract void setListener(ISipSessionListener paramISipSessionListener)
    throws RemoteException;

  public abstract void unregister()
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements ISipSession
  {
    public static ISipSession asInterface(IBinder paramIBinder)
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
 * Qualified Name:     com.mstar.android.sip.ISipSession
 * JD-Core Version:    0.6.2
 */