package com.mstar.android.tvapi.common;

import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.common.vo.EnumFavoriteId;
import com.mstar.android.tvapi.common.vo.EnumFirstServiceInputType;
import com.mstar.android.tvapi.common.vo.EnumFirstServiceType;
import com.mstar.android.tvapi.common.vo.EnumProgramAttribute;
import com.mstar.android.tvapi.common.vo.EnumProgramCountType;
import com.mstar.android.tvapi.common.vo.EnumProgramInfoType;
import com.mstar.android.tvapi.common.vo.EnumProgramLoopType;
import com.mstar.android.tvapi.common.vo.GetServiceInfo;
import com.mstar.android.tvapi.common.vo.ProgramInfo;
import com.mstar.android.tvapi.common.vo.ProgramInfoQueryCriteria;
import com.mstar.android.tvapi.dtv.atsc.vo.AtscMainListChannelInformation;

public final class ChannelManager
{
  protected static ChannelManager getInstance()
  {
    throw new RuntimeException("stub");
  }

  public void addProgramToFavorite(EnumFavoriteId paramEnumFavoriteId, int paramInt1, short paramShort, int paramInt2)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native boolean changeDtvToManualFirstService(int paramInt)
    throws TvCommonException;

  public final native boolean changeProgramList()
    throws TvCommonException;

  public boolean changeToFirstService(EnumFirstServiceInputType paramEnumFirstServiceInputType, EnumFirstServiceType paramEnumFirstServiceType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native boolean deleteAllMainList()
    throws TvCommonException;

  public final native boolean deleteAtvMainList()
    throws TvCommonException;

  public final native boolean deleteChannelInformationByRf(short paramShort)
    throws TvCommonException;

  public final native boolean deleteDtvMainList()
    throws TvCommonException;

  public void deleteProgramFromFavorite(EnumFavoriteId paramEnumFavoriteId, int paramInt1, short paramShort, int paramInt2)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  protected void finalize()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public final native int getCurrChannelNumber()
    throws TvCommonException;

  public final native AtscMainListChannelInformation getCurrentChannelInformation()
    throws TvCommonException;

  public final native ProgramInfo getCurrentProgramInfo()
    throws TvCommonException;

  public final native int getNvodReferenceServicesCount()
    throws TvCommonException;

  public final native GetServiceInfo[] getNvodReferenceServicesInfo(int paramInt)
    throws TvCommonException;

  public boolean getProgramAttribute(EnumProgramAttribute paramEnumProgramAttribute, int paramInt1, short paramShort, int paramInt2)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public int getProgramCount(EnumProgramCountType paramEnumProgramCountType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native ProgramInfo getProgramInfo(int paramInt)
    throws TvCommonException;

  public ProgramInfo getProgramInfo(ProgramInfoQueryCriteria paramProgramInfoQueryCriteria, EnumProgramInfoType paramEnumProgramInfoType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final ProgramInfo getProgramInfoById(int paramInt)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native String getProgramName(int paramInt, short paramShort1, short paramShort2)
    throws TvCommonException;

  public final native void moveProgram(int paramInt1, int paramInt2)
    throws TvCommonException;

  public final void programDown(EnumProgramLoopType paramEnumProgramLoopType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final void programUp(EnumProgramLoopType paramEnumProgramLoopType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  protected void release()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public final native void returnToPreviousProgram()
    throws TvCommonException;

  public final native void selectProgram(int paramInt1, short paramShort, int paramInt2)
    throws TvCommonException;

  public final native boolean selectProgram(int paramInt1, int paramInt2)
    throws TvCommonException;

  public final native void setDebugMode(boolean paramBoolean)
    throws TvCommonException;

  public void setProgramAttribute(EnumProgramAttribute paramEnumProgramAttribute, int paramInt1, short paramShort, int paramInt2, boolean paramBoolean)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native void setProgramName(int paramInt1, int paramInt2, String paramString)
    throws TvCommonException;

  public final native void setProgramName(int paramInt1, short paramShort, int paramInt2, String paramString)
    throws TvCommonException;

  public final native void switchPrograms(int paramInt1, int paramInt2)
    throws TvCommonException;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.common.ChannelManager
 * JD-Core Version:    0.6.2
 */