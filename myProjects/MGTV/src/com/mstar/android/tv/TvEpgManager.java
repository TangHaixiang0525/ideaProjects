package com.mstar.android.tv;

import android.text.format.Time;
import com.mstar.android.tvapi.dtv.atsc.vo.AtscEpgEventInfo;
import com.mstar.android.tvapi.dtv.atsc.vo.AtscProgramInfo;

public class TvEpgManager
{
  public static TvEpgManager getInstance()
  {
    throw new RuntimeException("stub");
  }

  public void addingEpgPriority(AtscProgramInfo paramAtscProgramInfo, int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public boolean beginToGetEventInformation(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    throw new RuntimeException("stub");
  }

  public void endToGetEventInformation()
  {
    throw new RuntimeException("stub");
  }

  public int getEventCount(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Time paramTime)
  {
    throw new RuntimeException("stub");
  }

  public AtscEpgEventInfo getEventExtendInfoByTime(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Time paramTime)
  {
    throw new RuntimeException("stub");
  }

  public AtscEpgEventInfo getEventInfoByTime(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Time paramTime)
  {
    throw new RuntimeException("stub");
  }

  public AtscEpgEventInfo getFirstEventInformation(Time paramTime)
  {
    throw new RuntimeException("stub");
  }

  public AtscEpgEventInfo getNextEventInformation()
  {
    throw new RuntimeException("stub");
  }

  public boolean resetEPGProgPriority()
  {
    throw new RuntimeException("stub");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tv.TvEpgManager
 * JD-Core Version:    0.6.2
 */