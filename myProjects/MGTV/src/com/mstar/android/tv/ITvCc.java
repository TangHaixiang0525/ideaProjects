package com.mstar.android.tv;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.mstar.android.tvapi.common.vo.CaptionOptionSetting;

public abstract interface ITvCc extends IInterface
{
  public abstract boolean creatPreviewCcWindow()
    throws RemoteException;

  public abstract boolean drawPreviewCc(CaptionOptionSetting paramCaptionOptionSetting)
    throws RemoteException;

  public abstract boolean exitPreviewCc()
    throws RemoteException;

  public abstract boolean startCc()
    throws RemoteException;

  public abstract boolean stopCc()
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements ITvCc
  {
    public static ITvCc asInterface(IBinder paramIBinder)
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
 * Qualified Name:     com.mstar.android.tv.ITvCc
 * JD-Core Version:    0.6.2
 */