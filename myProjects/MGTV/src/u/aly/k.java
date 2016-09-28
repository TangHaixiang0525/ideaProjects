package u.aly;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.ReportPolicy.a;
import com.umeng.analytics.ReportPolicy.b;
import com.umeng.analytics.ReportPolicy.c;
import com.umeng.analytics.ReportPolicy.d;
import com.umeng.analytics.ReportPolicy.f;
import com.umeng.analytics.ReportPolicy.g;
import com.umeng.analytics.ReportPolicy.h;
import com.umeng.analytics.b;
import com.umeng.analytics.f;
import com.umeng.analytics.g;
import com.umeng.analytics.h;
import java.util.Iterator;
import java.util.List;

public final class k
  implements com.umeng.analytics.onlineconfig.c, p, w
{
  private s a = null;
  private h b = null;
  private aa c = null;
  private ak d = new ak();
  private a e = null;
  private int f = 10;
  private int g;
  private Context h;

  public k(Context paramContext)
  {
    this.h = paramContext;
    this.a = new s(paramContext);
    this.c = new aa(paramContext);
    this.b = h.a(paramContext);
    this.d.a(this.b.d());
    this.e = new a();
    this.g = this.b.d(-1);
  }

  private bn a(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null)
      return null;
    try
    {
      bn localbn = new bn();
      new ck().a(localbn, paramArrayOfByte);
      return localbn;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  private void a(bn parambn)
  {
    d locald;
    byte[] arrayOfByte1;
    if (parambn != null)
    {
      locald = d.a(this.h);
      locald.a();
      parambn.a(locald.b());
      arrayOfByte1 = b(parambn);
      if (arrayOfByte1 != null);
    }
    else
    {
      return;
    }
    if (f());
    for (c localc = c.b(this.h, AnalyticsConfig.getAppkey(this.h), arrayOfByte1); ; localc = c.a(this.h, AnalyticsConfig.getAppkey(this.h), arrayOfByte1))
    {
      byte[] arrayOfByte2 = localc.c();
      h localh = h.a(this.h);
      localh.g();
      localh.b(arrayOfByte2);
      locald.d();
      return;
    }
  }

  private void a(boolean paramBoolean)
  {
    if (b(paramBoolean))
      e();
    while (!d())
      return;
    b();
  }

  private boolean b(boolean paramBoolean)
  {
    boolean bool = true;
    if (!bq.l(this.h))
    {
      if (br.a)
        br.c("MobclickAgent", "network is unavailable");
      bool = false;
    }
    while ((this.c.f()) || ((br.a) && (bq.w(this.h))))
      return bool;
    return this.e.c().a(paramBoolean);
  }

  private byte[] b(bn parambn)
  {
    if (parambn == null)
      return null;
    try
    {
      byte[] arrayOfByte = new cq().a(parambn);
      if (br.a)
        br.c("MobclickAgent", parambn.toString());
      return arrayOfByte;
    }
    catch (Exception localException)
    {
      br.b("MobclickAgent", "Fail to serialize log ...", localException);
    }
    return null;
  }

  private void d(int paramInt)
  {
    a(a(new int[] { paramInt, (int)(System.currentTimeMillis() - this.c.o()) }));
    f.a(new g()
    {
      public void a()
      {
        k.this.a();
      }
    }
    , paramInt);
  }

  private boolean d()
  {
    return this.a.b() > this.f;
  }

  private void e()
  {
    try
    {
      if (this.c.f())
        this.a.a(new am(this.c.n()));
      if (this.b.h())
      {
        y localy1 = new y(this.h, this.c);
        localy1.a(this);
        if (this.d.c())
          localy1.b(true);
        localy1.a();
        return;
      }
      localbn = a(new int[0]);
      if (localbn == null)
      {
        br.a("MobclickAgent", "No data to report");
        return;
      }
    }
    catch (Throwable localThrowable)
    {
      bn localbn;
      if ((!(localThrowable instanceof OutOfMemoryError)) || (localThrowable != null))
      {
        localThrowable.printStackTrace();
        return;
        y localy2 = new y(this.h, this.c);
        localy2.a(this);
        if (this.d.c())
          localy2.b(true);
        localy2.a(localbn);
        localy2.a(f());
        localy2.a();
      }
    }
  }

  private void e(int paramInt)
  {
    d(paramInt);
  }

  private boolean f()
  {
    switch (this.g)
    {
    case 0:
    default:
      return false;
    case 1:
      return true;
    case -1:
    }
    return AnalyticsConfig.sEncrypt;
  }

  protected bn a(int[] paramArrayOfInt)
  {
    int i = 0;
    while (true)
    {
      bn localbn1;
      bn localbn3;
      try
      {
        if (TextUtils.isEmpty(AnalyticsConfig.getAppkey(this.h)))
        {
          br.b("MobclickAgent", "Appkey is missing ,Please check AndroidManifest.xml");
          return null;
        }
        byte[] arrayOfByte = h.a(this.h).f();
        if (arrayOfByte == null)
        {
          localbn1 = null;
          if ((localbn1 == null) && (this.a.b() == 0))
            return null;
        }
        else
        {
          localbn1 = a(arrayOfByte);
          continue;
        }
        if (localbn1 != null)
          break label263;
        localbn2 = new bn();
        this.a.a(localbn2);
        if ((br.a) && (localbn2.B()))
        {
          Iterator localIterator = localbn2.z().iterator();
          if (localIterator.hasNext())
          {
            if (((bl)localIterator.next()).p() <= 0)
              break label257;
            j = 1;
            break label273;
          }
          if (i == 0)
            br.e("MobclickAgent", "missing Activities or PageViews");
        }
        localbn3 = this.d.a(this.h, localbn2);
        if ((paramArrayOfInt == null) || (paramArrayOfInt.length != 2))
          break label270;
        aq localaq = new aq();
        localaq.a(new bd(paramArrayOfInt[0] / 1000, paramArrayOfInt[1]));
        localbn3.a(localaq);
        return localbn3;
      }
      catch (Exception localException)
      {
        br.b("MobclickAgent", "Fail to construct message ...", localException);
        h.a(this.h).g();
        return null;
      }
      label257: int j = i;
      break label273;
      label263: bn localbn2 = localbn1;
      continue;
      label270: return localbn3;
      label273: i = j;
    }
  }

  public void a()
  {
    if (bq.l(this.h))
      e();
    while (!br.a)
      return;
    br.c("MobclickAgent", "network is unavailable");
  }

  public void a(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt <= 3))
    {
      this.d.a(paramInt);
      this.e.b(paramInt);
    }
  }

  public void a(int paramInt, long paramLong)
  {
    this.e.a(paramInt, (int)paramLong);
  }

  public void a(q paramq)
  {
    if (paramq != null)
      this.a.a(paramq);
    a(paramq instanceof bl);
  }

  public void b()
  {
    if (this.a.b() > 0);
    try
    {
      byte[] arrayOfByte = b(a(new int[0]));
      if (arrayOfByte != null)
        this.b.a(arrayOfByte);
      return;
    }
    catch (Throwable localThrowable)
    {
      do
        if ((localThrowable instanceof OutOfMemoryError))
          this.b.g();
      while (localThrowable == null);
      localThrowable.printStackTrace();
    }
  }

  public void b(int paramInt)
  {
    if (paramInt > 0)
      this.e.a(paramInt);
  }

  public void b(q paramq)
  {
    this.a.a(paramq);
  }

  public void c()
  {
    a(a(new int[0]));
  }

  public void c(int paramInt)
  {
    this.g = paramInt;
  }

  public class a
  {
    private final long b = 57600000L;
    private final int c = 1800000;
    private final int d = 60000;
    private ReportPolicy.g e;
    private int f = k.a(k.this).d();
    private int g;
    private int h;
    private int i;
    private boolean j = false;

    public a()
    {
      int k = k.a(k.this).e();
      if (k > 0)
        this.g = c(k);
      while (true)
      {
        int[] arrayOfInt = k.a(k.this).c();
        this.h = arrayOfInt[0];
        this.i = arrayOfInt[1];
        return;
        if (AnalyticsConfig.sLatentWindow > 0)
          this.g = c(AnalyticsConfig.sLatentWindow);
        else
          this.g = 60000;
      }
    }

    private ReportPolicy.g b(int paramInt1, int paramInt2)
    {
      switch (paramInt1)
      {
      case 2:
      case 3:
      default:
        return new ReportPolicy.c();
      case 1:
        return new ReportPolicy.c();
      case 6:
        return new ReportPolicy.d(k.b(k.this), paramInt2);
      case 4:
        return new ReportPolicy.f(k.b(k.this));
      case 0:
        return new ReportPolicy.g();
      case 5:
      }
      return new ReportPolicy.h(k.d(k.this));
    }

    private int c(int paramInt)
    {
      if (paramInt > 1800000)
        paramInt = 1800000;
      return paramInt;
    }

    protected void a()
    {
      int k = 1;
      Object localObject;
      if (this.f > 0)
        if (((this.e instanceof ReportPolicy.a)) && (this.e.a()))
        {
          if (k == 0)
            break label56;
          localObject = this.e;
          label39: this.e = ((ReportPolicy.g)localObject);
        }
      while (true)
      {
        this.j = false;
        return;
        k = 0;
        break;
        label56: localObject = new ReportPolicy.a(k.b(k.this), k.c(k.this));
        break label39;
        if (((this.e instanceof ReportPolicy.b)) && (this.e.a()));
        while (true)
        {
          if (k != 0)
            break label159;
          if (!b())
            break label161;
          String str = c.a(k.d(k.this));
          int m = b.a(this.g, str);
          this.e = new ReportPolicy.b(m);
          k.a(k.this, m);
          break;
          k = 0;
        }
        label159: continue;
        label161: this.e = b(this.h, this.i);
      }
    }

    public void a(int paramInt)
    {
      this.g = c(paramInt);
      d();
    }

    public void a(int paramInt1, int paramInt2)
    {
      this.h = paramInt1;
      this.i = paramInt2;
      d();
    }

    public void b(int paramInt)
    {
      this.f = paramInt;
      d();
    }

    protected boolean b()
    {
      if (k.a(k.this).h());
      while ((k.b(k.this).f()) || (System.currentTimeMillis() - k.b(k.this).o() <= 57600000L))
        return false;
      return true;
    }

    public ReportPolicy.g c()
    {
      a();
      return this.e;
    }

    protected void d()
    {
      this.j = true;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.k
 * JD-Core Version:    0.6.2
 */