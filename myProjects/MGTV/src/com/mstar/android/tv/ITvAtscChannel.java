package com.mstar.android.tv;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.mstar.android.tvapi.common.vo.ProgramInfo;
import com.mstar.android.tvapi.dtv.atsc.vo.AtscMainListChannelInformation;
import com.mstar.android.tvapi.dtv.atsc.vo.Region5RatingInformation;
import com.mstar.android.tvapi.dtv.atsc.vo.UsaMpaaRatingType;
import com.mstar.android.tvapi.dtv.atsc.vo.UsaTvRatingInformation;

public abstract interface ITvAtscChannel extends IInterface
{
  public abstract boolean changeDtvToManualFirstService(int paramInt)
    throws RemoteException;

  public abstract boolean changeProgramList()
    throws RemoteException;

  public abstract boolean deleteAllMainList()
    throws RemoteException;

  public abstract boolean deleteAtvMainList()
    throws RemoteException;

  public abstract boolean deleteChannelInformationByRf(int paramInt)
    throws RemoteException;

  public abstract boolean deleteDtvMainList()
    throws RemoteException;

  public abstract int getCanadaEngRatingLock()
    throws RemoteException;

  public abstract int getCanadaFreRatingLock()
    throws RemoteException;

  public abstract AtscMainListChannelInformation getCurrentChannelInformation()
    throws RemoteException;

  public abstract ProgramInfo getCurrentProgramInfo()
    throws RemoteException;

  public abstract String getCurrentRatingInformation()
    throws RemoteException;

  public abstract boolean getCurrentVChipBlockStatus()
    throws RemoteException;

  public abstract ProgramInfo getProgramInfo(int paramInt)
    throws RemoteException;

  public abstract Region5RatingInformation getRRTInformation()
    throws RemoteException;

  public abstract UsaMpaaRatingType getUsaMpaaRatingLock()
    throws RemoteException;

  public abstract UsaTvRatingInformation getUsaTvRatingLock()
    throws RemoteException;

  public abstract boolean getVChipInputSourceBlockStatus(int paramInt)
    throws RemoteException;

  public abstract boolean programSel(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract boolean resetRRTSetting()
    throws RemoteException;

  public abstract boolean setCanadaEngGuideline(int paramInt)
    throws RemoteException;

  public abstract boolean setCanadaEngRatingLock(int paramInt)
    throws RemoteException;

  public abstract boolean setCanadaFreGuideline(int paramInt)
    throws RemoteException;

  public abstract boolean setCanadaFreRatingLock(int paramInt)
    throws RemoteException;

  public abstract void setDtvAntennaType(int paramInt)
    throws RemoteException;

  public abstract boolean setDynamicGuideline(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract void setProgramName(int paramInt1, int paramInt2, String paramString)
    throws RemoteException;

  public abstract boolean setUsaMpaaGuideline(int paramInt, boolean paramBoolean)
    throws RemoteException;

  public abstract boolean setUsaMpaaRatingLock(UsaMpaaRatingType paramUsaMpaaRatingType)
    throws RemoteException;

  public abstract boolean setUsaTvGuideline(int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract boolean setUsaTvRatingLock(UsaTvRatingInformation paramUsaTvRatingInformation)
    throws RemoteException;

  public abstract boolean setVChipGuideline(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws RemoteException;

  public abstract void setVChipInputSourceBlockStatus(int paramInt, boolean paramBoolean)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements ITvAtscChannel
  {
    public static ITvAtscChannel asInterface(IBinder paramIBinder)
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
 * Qualified Name:     com.mstar.android.tv.ITvAtscChannel
 * JD-Core Version:    0.6.2
 */