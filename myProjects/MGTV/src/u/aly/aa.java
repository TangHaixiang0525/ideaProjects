package u.aly;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.umeng.analytics.h;

public class aa
  implements r
{
  private static final String h = "successful_request";
  private static final String i = "failed_requests ";
  private static final String j = "last_request_spent_ms";
  private static final String k = "last_request_time";
  private static final String l = "first_activate_time";
  private static final String m = "last_req";
  public int a;
  public int b;
  public long c;
  private final int d = 3600000;
  private int e;
  private long f = 0L;
  private long g = 0L;
  private Context n;

  public aa(Context paramContext)
  {
    b(paramContext);
  }

  public static ap a(Context paramContext)
  {
    SharedPreferences localSharedPreferences = x.a(paramContext);
    ap localap = new ap();
    localap.c(localSharedPreferences.getInt("failed_requests ", 0));
    localap.d(localSharedPreferences.getInt("last_request_spent_ms", 0));
    localap.a(localSharedPreferences.getInt("successful_request", 0));
    return localap;
  }

  private void b(Context paramContext)
  {
    this.n = paramContext.getApplicationContext();
    SharedPreferences localSharedPreferences = x.a(paramContext);
    this.a = localSharedPreferences.getInt("successful_request", 0);
    this.b = localSharedPreferences.getInt("failed_requests ", 0);
    this.e = localSharedPreferences.getInt("last_request_spent_ms", 0);
    this.c = localSharedPreferences.getLong("last_request_time", 0L);
    this.f = localSharedPreferences.getLong("last_req", 0L);
  }

  public void a()
  {
    i();
  }

  public void b()
  {
    j();
  }

  public void c()
  {
    g();
  }

  public void d()
  {
    h();
  }

  public int e()
  {
    if (this.e > 3600000)
      return 3600000;
    return this.e;
  }

  public boolean f()
  {
    int i1;
    if (this.c == 0L)
    {
      i1 = 1;
      if (h.a(this.n).h())
        break label41;
    }
    label41: for (int i2 = 1; ; i2 = 0)
    {
      if ((i1 == 0) || (i2 == 0))
        break label46;
      return true;
      i1 = 0;
      break;
    }
    label46: return false;
  }

  public void g()
  {
    this.a = (1 + this.a);
    this.c = this.f;
  }

  public void h()
  {
    this.b = (1 + this.b);
  }

  public void i()
  {
    this.f = System.currentTimeMillis();
  }

  public void j()
  {
    this.e = ((int)(System.currentTimeMillis() - this.f));
  }

  public void k()
  {
    x.a(this.n).edit().putInt("successful_request", this.a).putInt("failed_requests ", this.b).putInt("last_request_spent_ms", this.e).putLong("last_request_time", this.c).putLong("last_req", this.f).commit();
  }

  public void l()
  {
    x.a(this.n).edit().putLong("first_activate_time", System.currentTimeMillis()).commit();
  }

  public boolean m()
  {
    if (this.g == 0L)
      this.g = x.a(this.n).getLong("first_activate_time", 0L);
    return this.g == 0L;
  }

  public long n()
  {
    if (m())
      return System.currentTimeMillis();
    return this.g;
  }

  public long o()
  {
    return this.f;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.aa
 * JD-Core Version:    0.6.2
 */