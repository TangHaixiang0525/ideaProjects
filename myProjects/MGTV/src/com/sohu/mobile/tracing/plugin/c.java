package com.sohu.mobile.tracing.plugin;

import android.os.Handler;
import android.os.Message;
import com.miaozhen.mzmonitor.MZMonitor;

final class c extends Handler
{
  public void dispatchMessage(Message paramMessage)
  {
    super.dispatchMessage(paramMessage);
    switch (paramMessage.what)
    {
    default:
      return;
    case 10:
    }
    String str = (String)paramMessage.obj;
    com.sohu.mobile.tracing.plugin.d.b.b("<在线/MIAOZHEN>调用秒针曝光,Url=" + str);
    MZMonitor.adTrack(b.d(), str.trim());
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.mobile.tracing.plugin.c
 * JD-Core Version:    0.6.2
 */