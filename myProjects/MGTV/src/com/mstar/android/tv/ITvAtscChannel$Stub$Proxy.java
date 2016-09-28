package com.mstar.android.tv;

import android.os.IBinder;
import android.os.RemoteException;
import com.mstar.android.tvapi.common.vo.ProgramInfo;
import com.mstar.android.tvapi.dtv.atsc.vo.AtscMainListChannelInformation;
import com.mstar.android.tvapi.dtv.atsc.vo.Region5RatingInformation;
import com.mstar.android.tvapi.dtv.atsc.vo.UsaMpaaRatingType;
import com.mstar.android.tvapi.dtv.atsc.vo.UsaTvRatingInformation;

class ITvAtscChannel$Stub$Proxy
  implements ITvAtscChannel
{
  public IBinder asBinder()
  {
    throw new RuntimeException("stub");
  }

  public boolean changeDtvToManualFirstService(int paramInt)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean changeProgramList()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean deleteAllMainList()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean deleteAtvMainList()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean deleteChannelInformationByRf(int paramInt)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean deleteDtvMainList()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public int getCanadaEngRatingLock()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public int getCanadaFreRatingLock()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public AtscMainListChannelInformation getCurrentChannelInformation()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public ProgramInfo getCurrentProgramInfo()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public String getCurrentRatingInformation()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean getCurrentVChipBlockStatus()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public String getInterfaceDescriptor()
  {
    throw new RuntimeException("stub");
  }

  public ProgramInfo getProgramInfo(int paramInt)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public Region5RatingInformation getRRTInformation()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public UsaMpaaRatingType getUsaMpaaRatingLock()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public UsaTvRatingInformation getUsaTvRatingLock()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean getVChipInputSourceBlockStatus(int paramInt)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean programSel(int paramInt1, int paramInt2)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean resetRRTSetting()
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean setCanadaEngGuideline(int paramInt)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean setCanadaEngRatingLock(int paramInt)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean setCanadaFreGuideline(int paramInt)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean setCanadaFreRatingLock(int paramInt)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public void setDtvAntennaType(int paramInt)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean setDynamicGuideline(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public void setProgramName(int paramInt1, int paramInt2, String paramString)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean setUsaMpaaGuideline(int paramInt, boolean paramBoolean)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean setUsaMpaaRatingLock(UsaMpaaRatingType paramUsaMpaaRatingType)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean setUsaTvGuideline(int paramInt1, int paramInt2)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean setUsaTvRatingLock(UsaTvRatingInformation paramUsaTvRatingInformation)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean setVChipGuideline(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public void setVChipInputSourceBlockStatus(int paramInt, boolean paramBoolean)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tv.ITvAtscChannel.Stub.Proxy
 * JD-Core Version:    0.6.2
 */