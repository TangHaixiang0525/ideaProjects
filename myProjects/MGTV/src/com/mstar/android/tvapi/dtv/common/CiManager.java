package com.mstar.android.tvapi.dtv.common;

import com.mstar.android.tvapi.common.exception.TvCommonException;
import com.mstar.android.tvapi.dtv.vo.EnumCardState;
import com.mstar.android.tvapi.dtv.vo.EnumMmiType;

public final class CiManager
{
  protected static CiManager getInstance()
  {
    throw new RuntimeException("stub");
  }

  public final native boolean answerEnq(String paramString)
    throws TvCommonException;

  public final native void answerMenu(short paramShort)
    throws TvCommonException;

  public final native boolean backEnq()
    throws TvCommonException;

  public final native void backMenu()
    throws TvCommonException;

  public final native void close()
    throws TvCommonException;

  public final native void enterMenu()
    throws TvCommonException;

  protected void finalize()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public EnumCardState getCardState()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native CredentialValidDateRange getCiCredentialValidRange()
    throws TvCommonException;

  public final native short getEnqAnsLength()
    throws TvCommonException;

  public final native short getEnqBlindAnswer()
    throws TvCommonException;

  public final native short getEnqLength()
    throws TvCommonException;

  public final native String getEnqString()
    throws TvCommonException;

  public final native int getListBottomLength()
    throws TvCommonException;

  public final native String getListBottomString()
    throws TvCommonException;

  public final native short getListChoiceNumber()
    throws TvCommonException;

  public final native String getListSelectionString(int paramInt)
    throws TvCommonException;

  public final native int getListSubtitleLength()
    throws TvCommonException;

  public native String getListSubtitleString()
    throws TvCommonException;

  public final native int getListTitleLength()
    throws TvCommonException;

  public native String getListTitleString()
    throws TvCommonException;

  public final native int getMenuBottomLength()
    throws TvCommonException;

  public final native String getMenuBottomString()
    throws TvCommonException;

  public final native short getMenuChoiceNumber()
    throws TvCommonException;

  public final native String getMenuSelectionString(int paramInt)
    throws TvCommonException;

  public final native String getMenuString()
    throws TvCommonException;

  public final native int getMenuSubtitleLength()
    throws TvCommonException;

  public final native String getMenuSubtitleString()
    throws TvCommonException;

  public final native int getMenuTitleLength()
    throws TvCommonException;

  public final native String getMenuTitleString()
    throws TvCommonException;

  public EnumMmiType getMmiType()
    throws TvCommonException
  {
    throw new RuntimeException("stub");
  }

  public final native boolean isCiCredentialModeValid(short paramShort)
    throws TvCommonException;

  public final native boolean isCiMenuOn()
    throws TvCommonException;

  public final native boolean isDataExisted()
    throws TvCommonException;

  public final native void setCiCredentialMode(short paramShort)
    throws TvCommonException;

  public final native void setDebugMode(boolean paramBoolean)
    throws TvCommonException;

  public void setOnCiEventListener(OnCiEventListener paramOnCiEventListener)
  {
    throw new RuntimeException("stub");
  }

  public static class CredentialValidDateRange
  {
    public int validFromDate;
    public int validToDate;
  }

  protected static enum EVENT
  {
  }

  public static abstract interface OnCiEventListener
  {
    public abstract boolean onUiAutotestMessageShown(CiManager paramCiManager, int paramInt);

    public abstract boolean onUiCardInserted(CiManager paramCiManager, int paramInt);

    public abstract boolean onUiCardRemoved(CiManager paramCiManager, int paramInt);

    public abstract boolean onUiCloseMmi(CiManager paramCiManager, int paramInt);

    public abstract boolean onUiDataReady(CiManager paramCiManager, int paramInt);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.dtv.common.CiManager
 * JD-Core Version:    0.6.2
 */