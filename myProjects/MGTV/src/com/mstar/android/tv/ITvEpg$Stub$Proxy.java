package com.mstar.android.tv;

import android.os.IBinder;
import android.os.RemoteException;
import com.mstar.android.tvapi.dtv.atsc.vo.AtscEpgEventInfo;
import com.mstar.android.tvapi.dtv.atsc.vo.AtscProgramInfo;

class ITvEpg$Stub$Proxy
  implements ITvEpg
{
  public void addingEpgPriority(AtscProgramInfo paramAtscProgramInfo, int paramInt)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public IBinder asBinder()
  {
    throw new RuntimeException("stub");
  }

  public boolean beginToGetEventInformation(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public void endToGetEventInformation()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public int getEventCount(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public AtscEpgEventInfo getEventExtendInfoByTime(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public AtscEpgEventInfo getEventInfoByTime(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public AtscEpgEventInfo getFirstEventInformation(long paramLong)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public String getInterfaceDescriptor()
  {
    throw new RuntimeException("stub");
  }

  public AtscEpgEventInfo getNextEventInformation()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean resetEPGProgPriority()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tv.ITvEpg.Stub.Proxy
 * JD-Core Version:    0.6.2
 */