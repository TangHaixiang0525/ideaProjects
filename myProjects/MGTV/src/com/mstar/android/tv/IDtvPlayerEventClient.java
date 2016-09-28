package com.mstar.android.tv;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.mstar.android.tvapi.dtv.vo.DtvEventScan;

public abstract interface IDtvPlayerEventClient extends IInterface
{
  public abstract boolean onAudioModeChange(int paramInt, boolean paramBoolean)
    throws RemoteException;

  public abstract boolean onChangeTtxStatus(int paramInt, boolean paramBoolean)
    throws RemoteException;

  public abstract boolean onCiLoadCredentialFail(int paramInt)
    throws RemoteException;

  public abstract boolean onDtvAutoTuningScanInfo(int paramInt, DtvEventScan paramDtvEventScan)
    throws RemoteException;

  public abstract boolean onDtvAutoUpdateScan(int paramInt)
    throws RemoteException;

  public abstract boolean onDtvChannelNameReady(int paramInt)
    throws RemoteException;

  public abstract boolean onDtvPriComponentMissing(int paramInt)
    throws RemoteException;

  public abstract boolean onDtvProgramInfoReady(int paramInt)
    throws RemoteException;

  public abstract boolean onEpgTimerSimulcast(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract boolean onGingaStatusMode(int paramInt, boolean paramBoolean)
    throws RemoteException;

  public abstract boolean onHbbtvStatusMode(int paramInt, boolean paramBoolean)
    throws RemoteException;

  public abstract boolean onMheg5EventHandler(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract boolean onMheg5ReturnKey(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract boolean onMheg5StatusMode(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract boolean onOadDownload(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract boolean onOadHandler(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract boolean onOadTimeout(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract boolean onPopupScanDialogFrequencyChange(int paramInt)
    throws RemoteException;

  public abstract boolean onPopupScanDialogLossSignal(int paramInt)
    throws RemoteException;

  public abstract boolean onPopupScanDialogNewMultiplex(int paramInt)
    throws RemoteException;

  public abstract boolean onRctPresence(int paramInt)
    throws RemoteException;

  public abstract boolean onSignalLock(int paramInt)
    throws RemoteException;

  public abstract boolean onSignalUnLock(int paramInt)
    throws RemoteException;

  public abstract boolean onTsChange(int paramInt)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements IDtvPlayerEventClient
  {
    public static IDtvPlayerEventClient asInterface(IBinder paramIBinder)
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
 * Qualified Name:     com.mstar.android.tv.IDtvPlayerEventClient
 * JD-Core Version:    0.6.2
 */