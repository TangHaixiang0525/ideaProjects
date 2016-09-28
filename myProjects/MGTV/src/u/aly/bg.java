package u.aly;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class bg
  implements Serializable, Cloneable, ch<bg, e>
{
  public static final Map<e, ct> c;
  private static final dl d = new dl("Page");
  private static final db e = new db("page_name", (byte)11, (short)1);
  private static final db f = new db("duration", (byte)10, (short)2);
  private static final Map<Class<? extends do>, dp> g = new HashMap();
  private static final int h;
  public String a;
  public long b;
  private byte i = 0;

  static
  {
    g.put(dq.class, new b(null));
    g.put(dr.class, new d(null));
    EnumMap localEnumMap = new EnumMap(e.class);
    localEnumMap.put(e.a, new ct("page_name", (byte)1, new cu((byte)11)));
    localEnumMap.put(e.b, new ct("duration", (byte)1, new cu((byte)10)));
    c = Collections.unmodifiableMap(localEnumMap);
    ct.a(bg.class, c);
  }

  public bg()
  {
  }

  public bg(String paramString, long paramLong)
  {
    this();
    this.a = paramString;
    this.b = paramLong;
    b(true);
  }

  public bg(bg parambg)
  {
    this.i = parambg.i;
    if (parambg.e())
      this.a = parambg.a;
    this.b = parambg.b;
  }

  private void a(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    try
    {
      this.i = 0;
      a(new da(new ds(paramObjectInputStream)));
      return;
    }
    catch (cn localcn)
    {
      throw new IOException(localcn.getMessage());
    }
  }

  private void a(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    try
    {
      b(new da(new ds(paramObjectOutputStream)));
      return;
    }
    catch (cn localcn)
    {
      throw new IOException(localcn.getMessage());
    }
  }

  public e a(int paramInt)
  {
    return e.a(paramInt);
  }

  public bg a()
  {
    return new bg(this);
  }

  public bg a(long paramLong)
  {
    this.b = paramLong;
    b(true);
    return this;
  }

  public bg a(String paramString)
  {
    this.a = paramString;
    return this;
  }

  public void a(dg paramdg)
    throws cn
  {
    ((dp)g.get(paramdg.D())).b().b(paramdg, this);
  }

  public void a(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.a = null;
  }

  public void b()
  {
    this.a = null;
    b(false);
    this.b = 0L;
  }

  public void b(dg paramdg)
    throws cn
  {
    ((dp)g.get(paramdg.D())).b().a(paramdg, this);
  }

  public void b(boolean paramBoolean)
  {
    this.i = ce.a(this.i, 0, paramBoolean);
  }

  public String c()
  {
    return this.a;
  }

  public void d()
  {
    this.a = null;
  }

  public boolean e()
  {
    return this.a != null;
  }

  public long f()
  {
    return this.b;
  }

  public void h()
  {
    this.i = ce.b(this.i, 0);
  }

  public boolean i()
  {
    return ce.a(this.i, 0);
  }

  public void j()
    throws cn
  {
    if (this.a == null)
      throw new dh("Required field 'page_name' was not present! Struct: " + toString());
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("Page(");
    localStringBuilder.append("page_name:");
    if (this.a == null)
      localStringBuilder.append("null");
    while (true)
    {
      localStringBuilder.append(", ");
      localStringBuilder.append("duration:");
      localStringBuilder.append(this.b);
      localStringBuilder.append(")");
      return localStringBuilder.toString();
      localStringBuilder.append(this.a);
    }
  }

  private static class a extends dq<bg>
  {
    public void a(dg paramdg, bg parambg)
      throws cn
    {
      paramdg.j();
      db localdb = paramdg.l();
      if (localdb.b == 0)
      {
        paramdg.k();
        if (!parambg.i())
          throw new dh("Required field 'duration' was not found in serialized data! Struct: " + toString());
      }
      else
      {
        switch (localdb.c)
        {
        default:
          dj.a(paramdg, localdb.b);
        case 1:
        case 2:
        }
        while (true)
        {
          paramdg.m();
          break;
          if (localdb.b == 11)
          {
            parambg.a = paramdg.z();
            parambg.a(true);
          }
          else
          {
            dj.a(paramdg, localdb.b);
            continue;
            if (localdb.b == 10)
            {
              parambg.b = paramdg.x();
              parambg.b(true);
            }
            else
            {
              dj.a(paramdg, localdb.b);
            }
          }
        }
      }
      parambg.j();
    }

    public void b(dg paramdg, bg parambg)
      throws cn
    {
      parambg.j();
      paramdg.a(bg.k());
      if (parambg.a != null)
      {
        paramdg.a(bg.l());
        paramdg.a(parambg.a);
        paramdg.c();
      }
      paramdg.a(bg.m());
      paramdg.a(parambg.b);
      paramdg.c();
      paramdg.d();
      paramdg.b();
    }
  }

  private static class b
    implements dp
  {
    public bg.a a()
    {
      return new bg.a(null);
    }
  }

  private static class c extends dr<bg>
  {
    public void a(dg paramdg, bg parambg)
      throws cn
    {
      dm localdm = (dm)paramdg;
      localdm.a(parambg.a);
      localdm.a(parambg.b);
    }

    public void b(dg paramdg, bg parambg)
      throws cn
    {
      dm localdm = (dm)paramdg;
      parambg.a = localdm.z();
      parambg.a(true);
      parambg.b = localdm.x();
      parambg.b(true);
    }
  }

  private static class d
    implements dp
  {
    public bg.c a()
    {
      return new bg.c(null);
    }
  }

  public static enum e
    implements co
  {
    private static final Map<String, e> c;
    private final short d;
    private final String e;

    static
    {
      e[] arrayOfe = new e[2];
      arrayOfe[0] = a;
      arrayOfe[1] = b;
      f = arrayOfe;
      c = new HashMap();
      Iterator localIterator = EnumSet.allOf(e.class).iterator();
      while (localIterator.hasNext())
      {
        e locale = (e)localIterator.next();
        c.put(locale.b(), locale);
      }
    }

    private e(short paramShort, String paramString)
    {
      this.d = paramShort;
      this.e = paramString;
    }

    public static e a(int paramInt)
    {
      switch (paramInt)
      {
      default:
        return null;
      case 1:
        return a;
      case 2:
      }
      return b;
    }

    public static e a(String paramString)
    {
      return (e)c.get(paramString);
    }

    public static e b(int paramInt)
    {
      e locale = a(paramInt);
      if (locale == null)
        throw new IllegalArgumentException("Field " + paramInt + " doesn't exist!");
      return locale;
    }

    public short a()
    {
      return this.d;
    }

    public String b()
    {
      return this.e;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.bg
 * JD-Core Version:    0.6.2
 */