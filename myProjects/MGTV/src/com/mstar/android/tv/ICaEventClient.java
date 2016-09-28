package com.mstar.android.tv;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.mstar.android.tvapi.dtv.vo.CaLockService;
import com.mstar.android.tvapi.dtv.vo.CaStartIPPVBuyDlgInfo;

public abstract interface ICaEventClient extends IInterface
{
  public abstract boolean onActionRequest(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract boolean onDetitleReceived(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract boolean onEmailNotifyIcon(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract boolean onEntitleChanged(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract boolean onHideIPPVDlg(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract boolean onHideOSDMessage(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract boolean onLockService(int paramInt1, int paramInt2, int paramInt3, CaLockService paramCaLockService)
    throws RemoteException;

  public abstract boolean onOtaState(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract boolean onRequestFeeding(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract boolean onShowBuyMessage(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract boolean onShowFingerMessage(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract boolean onShowOSDMessage(int paramInt1, int paramInt2, int paramInt3, String paramString)
    throws RemoteException;

  public abstract boolean onShowProgressStrip(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract boolean onStartIppvBuyDlg(int paramInt1, int paramInt2, int paramInt3, CaStartIPPVBuyDlgInfo paramCaStartIPPVBuyDlgInfo)
    throws RemoteException;

  public abstract boolean onUNLockService(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements ICaEventClient
  {
    public static ICaEventClient asInterface(IBinder paramIBinder)
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
 * Qualified Name:     com.mstar.android.tv.ICaEventClient
 * JD-Core Version:    0.6.2
 */