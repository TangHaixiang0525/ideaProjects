package com.sohu.mma.tracking.a;

import android.content.Context;
import com.sohu.mma.tracking.b.g;
import com.sohu.mma.tracking.c.h;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class b
{
  private static b b = null;
  ScheduledExecutorService a = Executors.newScheduledThreadPool(2);
  private f c;
  private e d;
  private long e = 0L;

  public static b a()
  {
    if (b == null)
      b = new b();
    return b;
  }

  public void a(Context paramContext, String paramString)
  {
    this.c = f.a(paramContext);
    this.d = new e(paramContext);
    h.a(paramContext, paramString);
    g localg = h.b(paramContext);
    if (localg == null)
      this.e = 3600000L;
    a(localg);
    b(localg);
  }

  public void a(g paramg)
  {
    c localc = new c(this);
    ScheduledExecutorService localScheduledExecutorService = this.a;
    if (paramg == null);
    for (long l = this.e; ; l = 1000 * a.b)
    {
      localScheduledExecutorService.scheduleWithFixedDelay(localc, 0L, l, TimeUnit.MILLISECONDS);
      return;
    }
  }

  public void a(String paramString)
  {
    try
    {
      if (this.d != null)
        this.d.a(paramString);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void b(g paramg)
  {
    d locald = new d(this);
    ScheduledExecutorService localScheduledExecutorService = this.a;
    if (paramg == null);
    for (long l = this.e; ; l = 1000 * a.b)
    {
      localScheduledExecutorService.scheduleWithFixedDelay(locald, 0L, l, TimeUnit.MILLISECONDS);
      return;
    }
  }

  public void b(String paramString)
  {
    try
    {
      if (this.d != null)
        this.d.a(paramString);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.mma.tracking.a.b
 * JD-Core Version:    0.6.2
 */