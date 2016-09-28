package u.aly;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class d
{
  public static d a;
  private final String b = "umeng_it.cache";
  private File c;
  private az d = null;
  private long e;
  private long f;
  private Set<a> g = new HashSet();
  private a h = null;

  d(Context paramContext)
  {
    this.c = new File(paramContext.getFilesDir(), "umeng_it.cache");
    this.f = 86400000L;
    this.h = new a(paramContext);
    this.h.b();
  }

  public static d a(Context paramContext)
  {
    try
    {
      if (a == null)
      {
        a = new d(paramContext);
        a.a(new e(paramContext));
        a.a(new g(paramContext));
        a.a(new b(paramContext));
        a.a(new j(paramContext));
        a.a(new i(paramContext));
        a.a(new h());
        a.e();
      }
      d locald = a;
      return locald;
    }
    finally
    {
    }
  }

  private void a(az paramaz)
  {
    if (paramaz != null);
    try
    {
      try
      {
        byte[] arrayOfByte = new cq().a(paramaz);
        if (arrayOfByte != null)
          cd.a(this.c, arrayOfByte);
        return;
      }
      finally
      {
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void g()
  {
    az localaz = new az();
    HashMap localHashMap = new HashMap();
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.g.iterator();
    while (localIterator.hasNext())
    {
      a locala = (a)localIterator.next();
      if (locala.c())
      {
        if (locala.d() != null)
          localHashMap.put(locala.b(), locala.d());
        if ((locala.e() != null) && (!locala.e().isEmpty()))
          localArrayList.addAll(locala.e());
      }
    }
    localaz.a(localArrayList);
    localaz.a(localHashMap);
    try
    {
      this.d = localaz;
      return;
    }
    finally
    {
    }
  }

  // ERROR //
  private az h()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 51	u/aly/d:c	Ljava/io/File;
    //   4: invokevirtual 168	java/io/File:exists	()Z
    //   7: ifne +5 -> 12
    //   10: aconst_null
    //   11: areturn
    //   12: new 170	java/io/FileInputStream
    //   15: dup
    //   16: aload_0
    //   17: getfield 51	u/aly/d:c	Ljava/io/File;
    //   20: invokespecial 173	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   23: astore_1
    //   24: aload_1
    //   25: invokestatic 176	u/aly/cd:b	(Ljava/io/InputStream;)[B
    //   28: astore 4
    //   30: new 107	u/aly/az
    //   33: dup
    //   34: invokespecial 108	u/aly/az:<init>	()V
    //   37: astore 5
    //   39: new 178	u/aly/ck
    //   42: dup
    //   43: invokespecial 179	u/aly/ck:<init>	()V
    //   46: aload 5
    //   48: aload 4
    //   50: invokevirtual 182	u/aly/ck:a	(Lu/aly/ch;[B)V
    //   53: aload_1
    //   54: invokestatic 185	u/aly/cd:c	(Ljava/io/InputStream;)V
    //   57: aload 5
    //   59: areturn
    //   60: astore_2
    //   61: aconst_null
    //   62: astore_1
    //   63: aload_2
    //   64: invokevirtual 105	java/lang/Exception:printStackTrace	()V
    //   67: aload_1
    //   68: invokestatic 185	u/aly/cd:c	(Ljava/io/InputStream;)V
    //   71: aconst_null
    //   72: areturn
    //   73: astore 6
    //   75: aconst_null
    //   76: astore_1
    //   77: aload 6
    //   79: astore_3
    //   80: aload_1
    //   81: invokestatic 185	u/aly/cd:c	(Ljava/io/InputStream;)V
    //   84: aload_3
    //   85: athrow
    //   86: astore_3
    //   87: goto -7 -> 80
    //   90: astore_2
    //   91: goto -28 -> 63
    //
    // Exception table:
    //   from	to	target	type
    //   12	24	60	java/lang/Exception
    //   12	24	73	finally
    //   24	53	86	finally
    //   63	67	86	finally
    //   24	53	90	java/lang/Exception
  }

  public void a()
  {
    long l = System.currentTimeMillis();
    if (l - this.e >= this.f)
    {
      Iterator localIterator = this.g.iterator();
      int i = 0;
      while (localIterator.hasNext())
      {
        a locala = (a)localIterator.next();
        if (locala.c())
          if (locala.a())
          {
            i = 1;
            if (!locala.c())
              this.h.b(locala.b());
          }
      }
      if (i != 0)
      {
        g();
        this.h.a();
        f();
      }
      this.e = l;
    }
  }

  public void a(long paramLong)
  {
    this.f = paramLong;
  }

  public boolean a(a parama)
  {
    if (this.h.a(parama.b()))
      return this.g.add(parama);
    return false;
  }

  public az b()
  {
    return this.d;
  }

  public String c()
  {
    return null;
  }

  public void d()
  {
    Iterator localIterator = this.g.iterator();
    int i = 0;
    while (localIterator.hasNext())
    {
      a locala = (a)localIterator.next();
      if (locala.c())
      {
        if ((locala.e() == null) || (locala.e().isEmpty()))
          break label88;
        locala.a(null);
      }
    }
    label88: for (int j = 1; ; j = i)
    {
      i = j;
      break;
      if (i != 0)
      {
        this.d.b(false);
        f();
      }
      return;
    }
  }

  public void e()
  {
    az localaz = h();
    if (localaz == null)
      return;
    ArrayList localArrayList = new ArrayList(this.g.size());
    try
    {
      this.d = localaz;
      Iterator localIterator1 = this.g.iterator();
      while (localIterator1.hasNext())
      {
        a locala2 = (a)localIterator1.next();
        locala2.a(this.d);
        if (!locala2.c())
          localArrayList.add(locala2);
      }
    }
    finally
    {
    }
    Iterator localIterator2 = localArrayList.iterator();
    while (localIterator2.hasNext())
    {
      a locala1 = (a)localIterator2.next();
      this.g.remove(locala1);
    }
    g();
  }

  public void f()
  {
    if (this.d != null)
      a(this.d);
  }

  public static class a
  {
    private Context a;
    private Set<String> b = new HashSet();

    public a(Context paramContext)
    {
      this.a = paramContext;
    }

    public void a()
    {
      if (!this.b.isEmpty())
      {
        StringBuilder localStringBuilder = new StringBuilder();
        Iterator localIterator = this.b.iterator();
        while (localIterator.hasNext())
        {
          localStringBuilder.append((String)localIterator.next());
          localStringBuilder.append(',');
        }
        localStringBuilder.deleteCharAt(-1 + localStringBuilder.length());
        x.a(this.a).edit().putString("invld_id", localStringBuilder.toString()).commit();
      }
    }

    public boolean a(String paramString)
    {
      return !this.b.contains(paramString);
    }

    public void b()
    {
      String str1 = x.a(this.a).getString("invld_id", null);
      if (!TextUtils.isEmpty(str1))
      {
        String[] arrayOfString = str1.split(",");
        if (arrayOfString != null)
        {
          int i = arrayOfString.length;
          for (int j = 0; j < i; j++)
          {
            String str2 = arrayOfString[j];
            if (!TextUtils.isEmpty(str2))
              this.b.add(str2);
          }
        }
      }
    }

    public void b(String paramString)
    {
      this.b.add(paramString);
    }

    public void c(String paramString)
    {
      this.b.remove(paramString);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.d
 * JD-Core Version:    0.6.2
 */