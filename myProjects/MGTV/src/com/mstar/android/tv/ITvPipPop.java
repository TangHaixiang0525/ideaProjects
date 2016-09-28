package com.mstar.android.tv;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.mstar.android.tvapi.common.vo.VideoWindowType;

public abstract interface ITvPipPop extends IInterface
{
  public abstract boolean checkPipSupport(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract boolean checkPipSupportOnSubSrc(int paramInt)
    throws RemoteException;

  public abstract boolean checkPopSupport(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract boolean checkTravelingModeSupport(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract boolean disable3dDualView()
    throws RemoteException;

  public abstract boolean disablePip()
    throws RemoteException;

  public abstract boolean disablePop()
    throws RemoteException;

  public abstract boolean disableTravelingMode()
    throws RemoteException;

  public abstract boolean enable3dDualView(int paramInt1, int paramInt2, VideoWindowType paramVideoWindowType1, VideoWindowType paramVideoWindowType2)
    throws RemoteException;

  public abstract int enablePipMM(int paramInt, VideoWindowType paramVideoWindowType)
    throws RemoteException;

  public abstract int enablePipTV(int paramInt1, int paramInt2, VideoWindowType paramVideoWindowType)
    throws RemoteException;

  public abstract int enablePopMM(int paramInt)
    throws RemoteException;

  public abstract int enablePopTV(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract int enableTravelingModeMM(int paramInt)
    throws RemoteException;

  public abstract int enableTravelingModeTV(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract boolean getIsPipOn()
    throws RemoteException;

  public abstract int[] getMainWindowSourceList()
    throws RemoteException;

  public abstract int getPipMode()
    throws RemoteException;

  public abstract int getPipPosition()
    throws RemoteException;

  public abstract int[] getSubWindowSourceList(boolean paramBoolean)
    throws RemoteException;

  public abstract boolean isPipModeEnabled()
    throws RemoteException;

  public abstract void setPipDisplayFocusWindow(int paramInt)
    throws RemoteException;

  public abstract boolean setPipOnFlag(boolean paramBoolean)
    throws RemoteException;

  public abstract void setPipPosition(int paramInt)
    throws RemoteException;

  public abstract boolean setPipSubwindow(VideoWindowType paramVideoWindowType)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements ITvPipPop
  {
    public static ITvPipPop asInterface(IBinder paramIBinder)
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
 * Qualified Name:     com.mstar.android.tv.ITvPipPop
 * JD-Core Version:    0.6.2
 */