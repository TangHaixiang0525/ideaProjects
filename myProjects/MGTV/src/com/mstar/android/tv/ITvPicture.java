package com.mstar.android.tv;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.mstar.android.tvapi.common.vo.ColorTemperatureExData;
import com.mstar.android.tvapi.common.vo.VideoInfo;
import com.mstar.android.tvapi.common.vo.VideoWindowType;

public abstract interface ITvPicture extends IInterface
{
  public abstract void disableBacklight()
    throws RemoteException;

  public abstract boolean disableDbc()
    throws RemoteException;

  public abstract boolean disableDcc()
    throws RemoteException;

  public abstract boolean disableDlc()
    throws RemoteException;

  public abstract void enableBacklight()
    throws RemoteException;

  public abstract boolean enableDbc()
    throws RemoteException;

  public abstract boolean enableDcc()
    throws RemoteException;

  public abstract boolean enableDlc()
    throws RemoteException;

  public abstract boolean execAutoPc()
    throws RemoteException;

  public abstract boolean freezeImage()
    throws RemoteException;

  public abstract int getBacklight()
    throws RemoteException;

  public abstract byte getColorRange()
    throws RemoteException;

  public abstract int getColorTempIdx()
    throws RemoteException;

  public abstract ColorTemperatureExData getColorTempPara()
    throws RemoteException;

  public abstract int getFilmMode()
    throws RemoteException;

  public abstract int getMFC()
    throws RemoteException;

  public abstract int getNR()
    throws RemoteException;

  public abstract int getPCClock()
    throws RemoteException;

  public abstract int getPCHPos()
    throws RemoteException;

  public abstract int getPCPhase()
    throws RemoteException;

  public abstract int getPCVPos()
    throws RemoteException;

  public abstract int getPictureModeIdx()
    throws RemoteException;

  public abstract int getVideoArc()
    throws RemoteException;

  public abstract VideoInfo getVideoInfo()
    throws RemoteException;

  public abstract int getVideoItem(int paramInt)
    throws RemoteException;

  public abstract int getVideoItemByInputSource(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract boolean isDbcEnabled()
    throws RemoteException;

  public abstract boolean isDccEnabled()
    throws RemoteException;

  public abstract boolean isDlcEnabled()
    throws RemoteException;

  public abstract boolean setBacklight(int paramInt)
    throws RemoteException;

  public abstract boolean setColorRange(byte paramByte)
    throws RemoteException;

  public abstract boolean setColorTempIdx(int paramInt)
    throws RemoteException;

  public abstract boolean setColorTempPara(ColorTemperatureExData paramColorTemperatureExData)
    throws RemoteException;

  public abstract void setDisplayWindow(VideoWindowType paramVideoWindowType)
    throws RemoteException;

  public abstract boolean setFilmMode(int paramInt)
    throws RemoteException;

  public abstract void setMFC(int paramInt)
    throws RemoteException;

  public abstract boolean setNR(int paramInt)
    throws RemoteException;

  public abstract boolean setPCClock(int paramInt)
    throws RemoteException;

  public abstract boolean setPCHPos(int paramInt)
    throws RemoteException;

  public abstract boolean setPCPhase(int paramInt)
    throws RemoteException;

  public abstract boolean setPCVPos(int paramInt)
    throws RemoteException;

  public abstract boolean setPictureModeIdx(int paramInt)
    throws RemoteException;

  public abstract boolean setVideoArc(int paramInt)
    throws RemoteException;

  public abstract boolean setVideoItem(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract boolean setVideoItemByInputSource(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract boolean unFreezeImage()
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements ITvPicture
  {
    public static ITvPicture asInterface(IBinder paramIBinder)
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
 * Qualified Name:     com.mstar.android.tv.ITvPicture
 * JD-Core Version:    0.6.2
 */