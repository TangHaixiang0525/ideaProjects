package com.mstar.android.tvapi.dtv.common;

import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.dtv.vo.CACardSNInfo;
import com.mstar.android.tvapi.dtv.vo.CARatingInfo;
import com.mstar.android.tvapi.dtv.vo.CaACListInfo;
import com.mstar.android.tvapi.dtv.vo.CaDetitleChkNums;
import com.mstar.android.tvapi.dtv.vo.CaEmailContentInfo;
import com.mstar.android.tvapi.dtv.vo.CaEmailHeadInfo;
import com.mstar.android.tvapi.dtv.vo.CaEmailHeadsInfo;
import com.mstar.android.tvapi.dtv.vo.CaEmailSpaceInfo;
import com.mstar.android.tvapi.dtv.vo.CaEntitleIDs;
import com.mstar.android.tvapi.dtv.vo.CaFeedDataInfo;
import com.mstar.android.tvapi.dtv.vo.CaIPPVProgramInfos;
import com.mstar.android.tvapi.dtv.vo.CaLockService;
import com.mstar.android.tvapi.dtv.vo.CaOperatorChildStatus;
import com.mstar.android.tvapi.dtv.vo.CaOperatorIds;
import com.mstar.android.tvapi.dtv.vo.CaOperatorInfo;
import com.mstar.android.tvapi.dtv.vo.CaServiceEntitles;
import com.mstar.android.tvapi.dtv.vo.CaSlotIDs;
import com.mstar.android.tvapi.dtv.vo.CaSlotInfo;
import com.mstar.android.tvapi.dtv.vo.CaStartIPPVBuyDlgInfo;
import com.mstar.android.tvapi.dtv.vo.CaStopIPPVBuyDlgInfo;
import com.mstar.android.tvapi.dtv.vo.CaWorkTimeInfo;

public final class CaManager
{
  public static int _current_detitle_type;
  public static int _current_email_type;

  public static final native short CaChangePin(String paramString1, String paramString2)
    throws TvCommonException;

  public static final native boolean CaDelDetitleChkNum(short paramShort, int paramInt)
    throws TvCommonException;

  public static final native void CaDelEmail(int paramInt)
    throws TvCommonException;

  public static final native CaACListInfo CaGetACList(short paramShort)
    throws TvCommonException;

  public static final native CACardSNInfo CaGetCardSN()
    throws TvCommonException;

  public static final native CaDetitleChkNums CaGetDetitleChkNums(short paramShort)
    throws TvCommonException;

  public static final native boolean CaGetDetitleReaded(short paramShort)
    throws TvCommonException;

  public static final native CaEmailContentInfo CaGetEmailContent(int paramInt)
    throws TvCommonException;

  public static final native CaEmailHeadInfo CaGetEmailHead(int paramInt)
    throws TvCommonException;

  public static final native CaEmailHeadsInfo CaGetEmailHeads(short paramShort1, short paramShort2)
    throws TvCommonException;

  public static final native CaEmailSpaceInfo CaGetEmailSpaceInfo()
    throws TvCommonException;

  public static final native CaEntitleIDs CaGetEntitleIDs(short paramShort)
    throws TvCommonException;

  public static final native CaIPPVProgramInfos CaGetIPPVProgram(short paramShort)
    throws TvCommonException;

  public static final native CaOperatorChildStatus CaGetOperatorChildStatus(short paramShort)
    throws TvCommonException;

  public static final native CaOperatorIds CaGetOperatorIds()
    throws TvCommonException;

  public static final native CaOperatorInfo CaGetOperatorInfo(short paramShort)
    throws TvCommonException;

  public static final native short CaGetPlatformID()
    throws TvCommonException;

  public static final native CARatingInfo CaGetRating()
    throws TvCommonException;

  public static final native CaServiceEntitles CaGetServiceEntitles(short paramShort)
    throws TvCommonException;

  public static final native CaSlotIDs CaGetSlotIDs(short paramShort)
    throws TvCommonException;

  public static final native CaSlotInfo CaGetSlotInfo(short paramShort1, short paramShort2)
    throws TvCommonException;

  public static final native int CaGetVer()
    throws TvCommonException;

  public static final native CaWorkTimeInfo CaGetWorkTime()
    throws TvCommonException;

  public static final native short CaIsPaired(short paramShort, String paramString)
    throws TvCommonException;

  public static final native boolean CaOTAStateConfirm(int paramInt1, int paramInt2)
    throws TvCommonException;

  public static final native CaFeedDataInfo CaReadFeedDataFromParent(short paramShort)
    throws TvCommonException;

  public static final native void CaRefreshInterface()
    throws TvCommonException;

  public static final native short CaSetRating(String paramString, short paramShort)
    throws TvCommonException;

  public static final native short CaSetWorkTime(String paramString, CaWorkTimeInfo paramCaWorkTimeInfo)
    throws TvCommonException;

  public static final native short CaStopIPPVBuyDlg(CaStopIPPVBuyDlgInfo paramCaStopIPPVBuyDlgInfo)
    throws TvCommonException;

  public static final native short CaWriteFeedDataToChild(short paramShort, CaFeedDataInfo paramCaFeedDataInfo)
    throws TvCommonException;

  public static int getCurrentEvent()
  {
    throw new RuntimeException("stub");
  }

  public static int getCurrentMsgType()
  {
    throw new RuntimeException("stub");
  }

  public static CaManager getInstance()
  {
    throw new RuntimeException("stub");
  }

  public static final native void native_init();

  public static void setCurrentEvent(int paramInt)
  {
    throw new RuntimeException("stub");
  }

  public static void setCurrentMsgType(int paramInt)
  {
    throw new RuntimeException("stub");
  }

  protected void finalize()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public void setOnCaEventListener(OnCaEventListener paramOnCaEventListener)
  {
    throw new RuntimeException("stub");
  }

  protected static enum CA_EVENT
  {
  }

  public static abstract interface OnCaEventListener
  {
    public abstract boolean onActionRequest(CaManager paramCaManager, int paramInt1, int paramInt2, int paramInt3);

    public abstract boolean onDetitleReceived(CaManager paramCaManager, int paramInt1, int paramInt2, int paramInt3);

    public abstract boolean onEmailNotifyIcon(CaManager paramCaManager, int paramInt1, int paramInt2, int paramInt3);

    public abstract boolean onEntitleChanged(CaManager paramCaManager, int paramInt1, int paramInt2, int paramInt3);

    public abstract boolean onHideIPPVDlg(CaManager paramCaManager, int paramInt1, int paramInt2, int paramInt3);

    public abstract boolean onHideOSDMessage(CaManager paramCaManager, int paramInt1, int paramInt2, int paramInt3);

    public abstract boolean onLockService(CaManager paramCaManager, int paramInt1, int paramInt2, int paramInt3, CaLockService paramCaLockService);

    public abstract boolean onOtaState(CaManager paramCaManager, int paramInt1, int paramInt2, int paramInt3);

    public abstract boolean onRequestFeeding(CaManager paramCaManager, int paramInt1, int paramInt2, int paramInt3);

    public abstract boolean onShowBuyMessage(CaManager paramCaManager, int paramInt1, int paramInt2, int paramInt3);

    public abstract boolean onShowFingerMessage(CaManager paramCaManager, int paramInt1, int paramInt2, int paramInt3);

    public abstract boolean onShowOSDMessage(CaManager paramCaManager, int paramInt1, int paramInt2, int paramInt3, String paramString);

    public abstract boolean onShowProgressStrip(CaManager paramCaManager, int paramInt1, int paramInt2, int paramInt3);

    public abstract boolean onStartIppvBuyDlg(CaManager paramCaManager, int paramInt1, int paramInt2, int paramInt3, CaStartIPPVBuyDlgInfo paramCaStartIPPVBuyDlgInfo);

    public abstract boolean onUNLockService(CaManager paramCaManager, int paramInt1, int paramInt2, int paramInt3);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.dtv.common.CaManager
 * JD-Core Version:    0.6.2
 */