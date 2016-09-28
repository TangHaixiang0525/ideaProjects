package com.mstar.android.tv;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.mstar.android.tvapi.dtv.atsc.vo.AtscEpgEventInfo;
import com.mstar.android.tvapi.dtv.atsc.vo.AtscProgramInfo;

public abstract interface ITvEpg extends IInterface
{
  public abstract void addingEpgPriority(AtscProgramInfo paramAtscProgramInfo, int paramInt)
    throws RemoteException;

  public abstract boolean beginToGetEventInformation(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws RemoteException;

  public abstract void endToGetEventInformation()
    throws RemoteException;

  public abstract int getEventCount(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong)
    throws RemoteException;

  public abstract AtscEpgEventInfo getEventExtendInfoByTime(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong)
    throws RemoteException;

  public abstract AtscEpgEventInfo getEventInfoByTime(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong)
    throws RemoteException;

  public abstract AtscEpgEventInfo getFirstEventInformation(long paramLong)
    throws RemoteException;

  public abstract AtscEpgEventInfo getNextEventInformation()
    throws RemoteException;

  public abstract boolean resetEPGProgPriority()
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements ITvEpg
  {
    public static ITvEpg asInterface(IBinder paramIBinder)
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
 * Qualified Name:     com.mstar.android.tv.ITvEpg
 * JD-Core Version:    0.6.2
 */