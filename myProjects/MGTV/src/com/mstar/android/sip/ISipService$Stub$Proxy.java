package com.mstar.android.sip;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.RemoteException;

class ISipService$Stub$Proxy
  implements ISipService
{
  public IBinder asBinder()
  {
    throw new RuntimeException("stub");
  }

  public void close(String paramString)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public ISipSession createSession(SipProfile paramSipProfile, ISipSessionListener paramISipSessionListener)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public String getInterfaceDescriptor()
  {
    throw new RuntimeException("stub");
  }

  public SipProfile[] getListOfProfiles()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public ISipSession getPendingSession(String paramString)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean isOpened(String paramString)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean isRegistered(String paramString)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public void open(SipProfile paramSipProfile)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public void open3(SipProfile paramSipProfile, PendingIntent paramPendingIntent, ISipSessionListener paramISipSessionListener)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public void setRegistrationListener(String paramString, ISipSessionListener paramISipSessionListener)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.sip.ISipService.Stub.Proxy
 * JD-Core Version:    0.6.2
 */