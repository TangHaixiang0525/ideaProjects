package com.mstar.android.tv;

import android.os.RemoteException;
import com.mstar.android.tvapi.common.vo.EnumAntennaType;
import com.mstar.android.tvapi.common.vo.ProgramInfo;
import com.mstar.android.tvapi.common.vo.TvOsType.EnumInputSource;
import com.mstar.android.tvapi.dtv.atsc.vo.AtscMainListChannelInformation;
import com.mstar.android.tvapi.dtv.atsc.vo.EnumCanadaEngRatingType;
import com.mstar.android.tvapi.dtv.atsc.vo.EnumCanadaFreRatingType;
import com.mstar.android.tvapi.dtv.atsc.vo.Region5RatingInformation;
import com.mstar.android.tvapi.dtv.atsc.vo.UsaMpaaRatingType;
import com.mstar.android.tvapi.dtv.atsc.vo.UsaTvRatingInformation;

public class TvAtscChannelManager
{
  public static TvAtscChannelManager getInstance()
  {
    throw new RuntimeException("stub");
  }

  public final boolean changeDtvToManualFirstService(int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public boolean changeProgramList()
  {
    throw new RuntimeException("stub");
  }

  public boolean deleteAllMainList()
  {
    throw new RuntimeException("stub");
  }

  public boolean deleteAtvMainList()
  {
    throw new RuntimeException("stub");
  }

  public boolean deleteChannelInformationByRf(short paramShort)
  {
    throw new RuntimeException("stub");
  }

  public boolean deleteDtvMainList()
  {
    throw new RuntimeException("stub");
  }

  public EnumCanadaEngRatingType getCanadaEngRatingLock()
  {
    throw new RuntimeException("stub");
  }

  public EnumCanadaFreRatingType getCanadaFreRatingLock()
  {
    throw new RuntimeException("stub");
  }

  public AtscMainListChannelInformation getCurrentChannelInformation()
  {
    throw new RuntimeException("stub");
  }

  public ProgramInfo getCurrentProgramInfo()
  {
    throw new RuntimeException("stub");
  }

  public String getCurrentRatingInformation()
  {
    throw new RuntimeException("stub");
  }

  public boolean getCurrentVChipBlockStatus()
  {
    throw new RuntimeException("stub");
  }

  public ProgramInfo getProgramInfo(int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public Region5RatingInformation getRRTInformation()
  {
    throw new RuntimeException("stub");
  }

  public UsaMpaaRatingType getUsaMpaaRatingLock()
  {
    throw new RuntimeException("stub");
  }

  public UsaTvRatingInformation getUsaTvRatingLock()
  {
    throw new RuntimeException("stub");
  }

  public boolean getVChipInputSourceBlockStatus(TvOsType.EnumInputSource paramEnumInputSource)
  {
    throw new RuntimeException("stub");
  }

  public boolean programSel(int paramInt1, int paramInt2)
  {
    throw new RuntimeException("stub");
  }

  public boolean resetRRTSetting()
  {
    throw new RuntimeException("stub");
  }

  public boolean setCanadaEngGuideline(int paramInt)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean setCanadaEngRatingLock(EnumCanadaEngRatingType paramEnumCanadaEngRatingType)
  {
    throw new RuntimeException("stub");
  }

  public boolean setCanadaFreGuideline(int paramInt)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean setCanadaFreRatingLock(EnumCanadaFreRatingType paramEnumCanadaFreRatingType)
  {
    throw new RuntimeException("stub");
  }

  public void setDtvAntennaType(EnumAntennaType paramEnumAntennaType)
  {
    throw new RuntimeException("stub");
  }

  public boolean setDynamicGuideline(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public final void setProgramName(int paramInt1, int paramInt2, String paramString)
  {
    throw new RuntimeException("stub");
  }

  public boolean setUsaMpaaGuideline(int paramInt, boolean paramBoolean)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean setUsaMpaaRatingLock(UsaMpaaRatingType paramUsaMpaaRatingType)
  {
    throw new RuntimeException("stub");
  }

  public boolean setUsaTvGuideline(int paramInt1, int paramInt2)
    throws RemoteException
  {
    throw new RuntimeException("stub");
  }

  public boolean setUsaTvRatingLock(UsaTvRatingInformation paramUsaTvRatingInformation)
  {
    throw new RuntimeException("stub");
  }

  public boolean setVChipGuideline(short paramShort1, short paramShort2, short paramShort3, short paramShort4)
  {
    throw new RuntimeException("stub");
  }

  public void setVChipInputSourceBlockStatus(TvOsType.EnumInputSource paramEnumInputSource, boolean paramBoolean)
  {
    throw new RuntimeException("stub");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tv.TvAtscChannelManager
 * JD-Core Version:    0.6.2
 */