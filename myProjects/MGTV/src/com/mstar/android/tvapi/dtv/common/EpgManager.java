package com.mstar.android.tvapi.dtv.common;

import android.text.format.Time;
import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.common.vo.PresentFollowingEventInfo;
import com.mstar.android.tvapi.dtv.atsc.vo.AtscEpgEventInfo;
import com.mstar.android.tvapi.dtv.atsc.vo.AtscProgramInfo;
import com.mstar.android.tvapi.dtv.vo.DtvEitInfo;
import com.mstar.android.tvapi.dtv.vo.DtvType.EnumEpgDescriptionType;
import com.mstar.android.tvapi.dtv.vo.EpgCridEventInfo;
import com.mstar.android.tvapi.dtv.vo.EpgCridStatus;
import com.mstar.android.tvapi.dtv.vo.EpgEventInfo;
import com.mstar.android.tvapi.dtv.vo.EpgFirstMatchHdCast;
import com.mstar.android.tvapi.dtv.vo.EpgHdSimulcast;
import com.mstar.android.tvapi.dtv.vo.EpgTrailerLinkInfo;
import com.mstar.android.tvapi.dtv.vo.NvodEventInfo;
import java.util.ArrayList;

public final class EpgManager
{
  protected static EpgManager getInstance()
  {
    throw new RuntimeException("stub");
  }

  public final void addingEpgPriority(AtscProgramInfo paramAtscProgramInfo, int paramInt)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final boolean beginToGetEventInformation(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native boolean disableEpgBarkerChannel()
    throws TvCommonException;

  public final native boolean enableEpgBarkerChannel()
    throws TvCommonException;

  public final void endToGetEventInformation()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  protected void finalize()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public final ArrayList<EpgCridEventInfo> getCridAlternateList(short paramShort, int paramInt, Time paramTime)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final ArrayList<EpgCridEventInfo> getCridSeriesList(short paramShort, int paramInt, Time paramTime)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final ArrayList<EpgCridEventInfo> getCridSplitList(short paramShort, int paramInt, Time paramTime)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final EpgCridStatus getCridStatus(short paramShort, int paramInt, Time paramTime)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final DtvEitInfo getEitInfo(boolean paramBoolean)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final int getEpgEventOffsetTime(Time paramTime, boolean paramBoolean)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final EpgFirstMatchHdCast getEvent1stMatchHdBroadcast(short paramShort, int paramInt, Time paramTime)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final EpgFirstMatchHdCast getEvent1stMatchHdSimulcast(short paramShort, int paramInt, Time paramTime)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final int getEventCount(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Time paramTime)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final int getEventCount(short paramShort, int paramInt, Time paramTime)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public String getEventDescriptor(short paramShort, int paramInt, Time paramTime, DtvType.EnumEpgDescriptionType paramEnumEpgDescriptionType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final AtscEpgEventInfo getEventExtendInfoByTime(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Time paramTime)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final ArrayList<EpgHdSimulcast> getEventHdSimulcast(short paramShort1, int paramInt, Time paramTime, short paramShort2)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final ArrayList<EpgEventInfo> getEventInfo(short paramShort, int paramInt1, Time paramTime, int paramInt2)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final EpgEventInfo getEventInfoById(short paramShort1, int paramInt, short paramShort2)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final ArrayList<EpgCridEventInfo> getEventInfoByRctLink(EpgTrailerLinkInfo paramEpgTrailerLinkInfo)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final AtscEpgEventInfo getEventInfoByTime(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Time paramTime)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final EpgEventInfo getEventInfoByTime(short paramShort, int paramInt, Time paramTime)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final AtscEpgEventInfo getFirstEventInformation(Time paramTime)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final AtscEpgEventInfo getNextEventInformation()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native int getNvodTimeShiftEventCount(short paramShort, int paramInt)
    throws TvCommonException;

  public ArrayList<NvodEventInfo> getNvodTimeShiftEventInfo(short paramShort, int paramInt1, int paramInt2, DtvType.EnumEpgDescriptionType paramEnumEpgDescriptionType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public PresentFollowingEventInfo getPresentFollowingEventInfo(short paramShort, int paramInt, boolean paramBoolean, DtvType.EnumEpgDescriptionType paramEnumEpgDescriptionType)
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final ArrayList<EpgTrailerLinkInfo> getRctTrailerLink()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native void resetEpgProgramPriority()
    throws TvCommonException;

  public final native void setEpgProgramPriority(int paramInt)
    throws TvCommonException;

  public final native void setEpgProgramPriority(short paramShort, int paramInt)
    throws TvCommonException;
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.dtv.common.EpgManager
 * JD-Core Version:    0.6.2
 */