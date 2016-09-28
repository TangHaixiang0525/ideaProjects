package com.sohu.mobile.tracing.plugin;

import android.content.Context;
import com.sohu.mobile.tracing.plugin.expose.Plugin_ExposeAction;
import com.sohu.mobile.tracing.plugin.expose.Plugin_ExposeAdBoby;
import com.sohu.mobile.tracing.plugin.expose.Plugin_VastTag;

public abstract interface a
{
  public abstract void a();

  public abstract void a(Context paramContext);

  public abstract void a(Plugin_ExposeAdBoby paramPlugin_ExposeAdBoby, String paramString, Plugin_VastTag paramPlugin_VastTag, Plugin_ExposeAction paramPlugin_ExposeAction);

  public abstract void a(String paramString);

  public abstract void a(boolean paramBoolean);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.mobile.tracing.plugin.a
 * JD-Core Version:    0.6.2
 */