package u.aly;

import android.content.Context;
import com.umeng.analytics.f;
import com.umeng.analytics.g;
import com.umeng.analytics.onlineconfig.a;
import com.umeng.analytics.onlineconfig.c;

public final class l
  implements p
{
  private static l c;
  private p a;
  private Context b;

  private l(Context paramContext)
  {
    this.b = paramContext.getApplicationContext();
    this.a = new k(this.b);
  }

  public static l a(Context paramContext)
  {
    try
    {
      if ((c == null) && (paramContext != null))
        c = new l(paramContext);
      l locall = c;
      return locall;
    }
    finally
    {
    }
  }

  public void a()
  {
    f.b(new g()
    {
      public void a()
      {
        l.a(l.this).a();
      }
    });
  }

  public void a(a parama)
  {
    if ((parama != null) && (this.a != null))
      parama.a((c)this.a);
  }

  public void a(p paramp)
  {
    this.a = paramp;
  }

  public void a(final q paramq)
  {
    f.b(new g()
    {
      public void a()
      {
        l.a(l.this).a(paramq);
      }
    });
  }

  public void b()
  {
    f.b(new g()
    {
      public void a()
      {
        l.a(l.this).b();
      }
    });
  }

  public void b(q paramq)
  {
    this.a.b(paramq);
  }

  public void c()
  {
    f.c(new g()
    {
      public void a()
      {
        l.a(l.this).c();
      }
    });
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.l
 * JD-Core Version:    0.6.2
 */