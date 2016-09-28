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

public class bd
  implements Serializable, Cloneable, ch<bd, e>
{
  public static final Map<e, ct> c;
  private static final dl d = new dl("Latent");
  private static final db e = new db("latency", (byte)8, (short)1);
  private static final db f = new db("interval", (byte)10, (short)2);
  private static final Map<Class<? extends do>, dp> g = new HashMap();
  private static final int h = 0;
  private static final int i = 1;
  public int a;
  public long b;
  private byte j = 0;

  static
  {
    g.put(dq.class, new b(null));
    g.put(dr.class, new d(null));
    EnumMap localEnumMap = new EnumMap(e.class);
    localEnumMap.put(e.a, new ct("latency", (byte)1, new cu((byte)8)));
    localEnumMap.put(e.b, new ct("interval", (byte)1, new cu((byte)10)));
    c = Collections.unmodifiableMap(localEnumMap);
    ct.a(bd.class, c);
  }

  public bd()
  {
  }

  public bd(int paramInt, long paramLong)
  {
    this();
    this.a = paramInt;
    a(true);
    this.b = paramLong;
    b(true);
  }

  public bd(bd parambd)
  {
    this.j = parambd.j;
    this.a = parambd.a;
    this.b = parambd.b;
  }

  private void a(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    try
    {
      this.j = 0;
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

  public bd a()
  {
    return new bd(this);
  }

  public bd a(int paramInt)
  {
    this.a = paramInt;
    a(true);
    return this;
  }

  public bd a(long paramLong)
  {
    this.b = paramLong;
    b(true);
    return this;
  }

  public void a(dg paramdg)
    throws cn
  {
    ((dp)g.get(paramdg.D())).b().b(paramdg, this);
  }

  public void a(boolean paramBoolean)
  {
    this.j = ce.a(this.j, 0, paramBoolean);
  }

  public void b()
  {
    a(false);
    this.a = 0;
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
    this.j = ce.a(this.j, 1, paramBoolean);
  }

  public int c()
  {
    return this.a;
  }

  public e c(int paramInt)
  {
    return e.a(paramInt);
  }

  public void d()
  {
    this.j = ce.b(this.j, 0);
  }

  public boolean e()
  {
    return ce.a(this.j, 0);
  }

  public long f()
  {
    return this.b;
  }

  public void h()
  {
    this.j = ce.b(this.j, 1);
  }

  public boolean i()
  {
    return ce.a(this.j, 1);
  }

  public void j()
    throws cn
  {
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("Latent(");
    localStringBuilder.append("latency:");
    localStringBuilder.append(this.a);
    localStringBuilder.append(", ");
    localStringBuilder.append("interval:");
    localStringBuilder.append(this.b);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }

  private static class a extends dq<bd>
  {
    public void a(dg paramdg, bd parambd)
      throws cn
    {
      paramdg.j();
      db localdb = paramdg.l();
      if (localdb.b == 0)
      {
        paramdg.k();
        if (!parambd.e())
          throw new dh("Required field 'latency' was not found in serialized data! Struct: " + toString());
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
          if (localdb.b == 8)
          {
            parambd.a = paramdg.w();
            parambd.a(true);
          }
          else
          {
            dj.a(paramdg, localdb.b);
            continue;
            if (localdb.b == 10)
            {
              parambd.b = paramdg.x();
              parambd.b(true);
            }
            else
            {
              dj.a(paramdg, localdb.b);
            }
          }
        }
      }
      if (!parambd.i())
        throw new dh("Required field 'interval' was not found in serialized data! Struct: " + toString());
      parambd.j();
    }

    public void b(dg paramdg, bd parambd)
      throws cn
    {
      parambd.j();
      paramdg.a(bd.k());
      paramdg.a(bd.l());
      paramdg.a(parambd.a);
      paramdg.c();
      paramdg.a(bd.m());
      paramdg.a(parambd.b);
      paramdg.c();
      paramdg.d();
      paramdg.b();
    }
  }

  private static class b
    implements dp
  {
    public bd.a a()
    {
      return new bd.a(null);
    }
  }

  private static class c extends dr<bd>
  {
    public void a(dg paramdg, bd parambd)
      throws cn
    {
      dm localdm = (dm)paramdg;
      localdm.a(parambd.a);
      localdm.a(parambd.b);
    }

    public void b(dg paramdg, bd parambd)
      throws cn
    {
      dm localdm = (dm)paramdg;
      parambd.a = localdm.w();
      parambd.a(true);
      parambd.b = localdm.x();
      parambd.b(true);
    }
  }

  private static class d
    implements dp
  {
    public bd.c a()
    {
      return new bd.c(null);
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
 * Qualified Name:     u.aly.bd
 * JD-Core Version:    0.6.2
 */