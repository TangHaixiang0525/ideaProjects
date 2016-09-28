package com.umeng.analytics;

import android.content.Context;
import u.aly.aa;
import u.aly.ak;
import u.aly.bq;
import u.aly.s;

public class ReportPolicy
{
  public static final int BATCH_AT_LAUNCH = 1;
  public static final int BATCH_BY_INTERVAL = 6;
  public static final int BATCH_BY_SIZE = 7;
  public static final int DAILY = 4;
  public static final int REALTIME = 0;
  public static final int WIFIONLY = 5;
  static final int a = 2;
  static final int b = 3;

  public static class a extends ReportPolicy.g
  {
    private ak a;
    private aa b;

    public a(aa paramaa, ak paramak)
    {
      this.b = paramaa;
      this.a = paramak;
    }

    public boolean a()
    {
      return this.a.c();
    }

    public boolean a(boolean paramBoolean)
    {
      long l1 = System.currentTimeMillis();
      long l2 = this.a.a();
      return l1 - this.b.c >= l2;
    }
  }

  public static class b extends ReportPolicy.g
  {
    private long a;
    private long b = 0L;

    public b(int paramInt)
    {
      this.a = paramInt;
      this.b = System.currentTimeMillis();
    }

    public boolean a()
    {
      return System.currentTimeMillis() - this.b < this.a;
    }

    public boolean a(boolean paramBoolean)
    {
      return System.currentTimeMillis() - this.b >= this.a;
    }
  }

  public static class c extends ReportPolicy.g
  {
    public boolean a(boolean paramBoolean)
    {
      return paramBoolean;
    }
  }

  public static class d extends ReportPolicy.g
  {
    private long a = 90000L;
    private long b;
    private aa c;

    public d(aa paramaa, long paramLong)
    {
      this.c = paramaa;
      if (paramLong < this.a)
        paramLong = this.a;
      this.b = paramLong;
    }

    public boolean a(boolean paramBoolean)
    {
      return System.currentTimeMillis() - this.c.c >= this.b;
    }

    public long b()
    {
      return this.b;
    }
  }

  public static class e extends ReportPolicy.g
  {
    private final int a;
    private s b;

    public e(s params, int paramInt)
    {
      this.a = paramInt;
      this.b = params;
    }

    public boolean a(boolean paramBoolean)
    {
      return this.b.b() > this.a;
    }
  }

  public static class f extends ReportPolicy.g
  {
    private long a = 86400000L;
    private aa b;

    public f(aa paramaa)
    {
      this.b = paramaa;
    }

    public boolean a(boolean paramBoolean)
    {
      return System.currentTimeMillis() - this.b.c >= this.a;
    }
  }

  public static class g
  {
    public boolean a()
    {
      return true;
    }

    public boolean a(boolean paramBoolean)
    {
      return true;
    }
  }

  public static class h extends ReportPolicy.g
  {
    private Context a = null;

    public h(Context paramContext)
    {
      this.a = paramContext;
    }

    public boolean a(boolean paramBoolean)
    {
      return bq.k(this.a);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.umeng.analytics.ReportPolicy
 * JD-Core Version:    0.6.2
 */