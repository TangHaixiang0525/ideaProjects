package com.sohu.mobile.tracing.plugin.b;

import com.sohu.applist.a.b;
import com.sohu.mobile.tracing.plugin.expose.Plugin_ExposeAction;
import com.sohu.mobile.tracing.plugin.expose.Plugin_ExposeAdBoby;
import com.sohu.mobile.tracing.plugin.expose.Plugin_VastTag;

public class a
{
  private int a;
  private int b;
  private String c;
  private b d;
  private Plugin_ExposeAdBoby e;
  private Plugin_VastTag f;
  private Plugin_ExposeAction g;

  public a()
  {
  }

  public a(Plugin_ExposeAdBoby paramPlugin_ExposeAdBoby, String paramString, Plugin_VastTag paramPlugin_VastTag, Plugin_ExposeAction paramPlugin_ExposeAction, int paramInt)
  {
    this.e = paramPlugin_ExposeAdBoby;
    this.c = paramString;
    this.f = paramPlugin_VastTag;
    this.g = paramPlugin_ExposeAction;
    this.b = paramInt;
  }

  public int a()
  {
    return this.a;
  }

  public void a(int paramInt)
  {
    this.a = paramInt;
  }

  public void a(b paramb)
  {
    this.d = paramb;
  }

  public void a(Plugin_ExposeAction paramPlugin_ExposeAction)
  {
    this.g = paramPlugin_ExposeAction;
  }

  public void a(Plugin_ExposeAdBoby paramPlugin_ExposeAdBoby)
  {
    this.e = paramPlugin_ExposeAdBoby;
  }

  public void a(Plugin_VastTag paramPlugin_VastTag)
  {
    this.f = paramPlugin_VastTag;
  }

  public void a(String paramString)
  {
    this.c = paramString;
  }

  public int b()
  {
    return this.b;
  }

  public void b(int paramInt)
  {
    this.b = paramInt;
  }

  public String c()
  {
    return this.c;
  }

  public Plugin_ExposeAdBoby d()
  {
    return this.e;
  }

  public Plugin_VastTag e()
  {
    return this.f;
  }

  public Plugin_ExposeAction f()
  {
    return this.g;
  }

  public b g()
  {
    return this.d;
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Plugin_TrackingEntity [id=");
    localStringBuilder.append(this.a);
    localStringBuilder.append(", isUpload=");
    localStringBuilder.append(this.b);
    localStringBuilder.append(", mUrl=");
    localStringBuilder.append(this.c);
    localStringBuilder.append(", mAdBody=");
    localStringBuilder.append(this.e);
    localStringBuilder.append(", mVastTag=");
    localStringBuilder.append(this.f);
    localStringBuilder.append(", mExposeAction=");
    localStringBuilder.append(this.g);
    localStringBuilder.append("]");
    return localStringBuilder.toString();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.mobile.tracing.plugin.b.a
 * JD-Core Version:    0.6.2
 */