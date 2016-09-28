package com.mstar.android.tvapi.common;

import com.mstar.android.tvapi.common.listener.OnMhlEventListener;

public final class MhlManager
{
  protected static MhlManager getInstance()
  {
    throw new RuntimeException("stub");
  }

  public final native boolean CbusStatus();

  public final native boolean IRKeyProcess(int paramInt, boolean paramBoolean);

  public final native boolean IsMhlPortInUse();

  protected void finalize()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public final native boolean getAutoSwitch();

  protected void release()
    throws Throwable
  {
    throw new RuntimeException("stub");
  }

  public final native void setAutoSwitch(boolean paramBoolean);

  public final native void setDebugMode(boolean paramBoolean);

  public void setOnMhlEventListener(OnMhlEventListener paramOnMhlEventListener)
  {
    throw new RuntimeException("stub");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.mstar.android.tvapi.common.MhlManager
 * JD-Core Version:    0.6.2
 */