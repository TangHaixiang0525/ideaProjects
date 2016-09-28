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

public class be
  implements Serializable, Cloneable, ch<be, e>
{
  public static final Map<e, ct> d;
  private static final dl e = new dl("Location");
  private static final db f = new db("lat", (byte)4, (short)1);
  private static final db g = new db("lng", (byte)4, (short)2);
  private static final db h = new db("ts", (byte)10, (short)3);
  private static final Map<Class<? extends do>, dp> i = new HashMap();
  private static final int j = 0;
  private static final int k = 1;
  private static final int l = 2;
  public double a;
  public double b;
  public long c;
  private byte m = 0;

  static
  {
    i.put(dq.class, new b(null));
    i.put(dr.class, new d(null));
    EnumMap localEnumMap = new EnumMap(e.class);
    localEnumMap.put(e.a, new ct("lat", (byte)1, new cu((byte)4)));
    localEnumMap.put(e.b, new ct("lng", (byte)1, new cu((byte)4)));
    localEnumMap.put(e.c, new ct("ts", (byte)1, new cu((byte)10)));
    d = Collections.unmodifiableMap(localEnumMap);
    ct.a(be.class, d);
  }

  public be()
  {
  }

  public be(double paramDouble1, double paramDouble2, long paramLong)
  {
    this();
    this.a = paramDouble1;
    a(true);
    this.b = paramDouble2;
    b(true);
    this.c = paramLong;
    c(true);
  }

  public be(be parambe)
  {
    this.m = parambe.m;
    this.a = parambe.a;
    this.b = parambe.b;
    this.c = parambe.c;
  }

  private void a(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    try
    {
      this.m = 0;
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

  public be a()
  {
    return new be(this);
  }

  public be a(double paramDouble)
  {
    this.a = paramDouble;
    a(true);
    return this;
  }

  public be a(long paramLong)
  {
    this.c = paramLong;
    c(true);
    return this;
  }

  public void a(dg paramdg)
    throws cn
  {
    ((dp)i.get(paramdg.D())).b().b(paramdg, this);
  }

  public void a(boolean paramBoolean)
  {
    this.m = ce.a(this.m, 0, paramBoolean);
  }

  public be b(double paramDouble)
  {
    this.b = paramDouble;
    b(true);
    return this;
  }

  public void b()
  {
    a(false);
    this.a = 0.0D;
    b(false);
    this.b = 0.0D;
    c(false);
    this.c = 0L;
  }

  public void b(dg paramdg)
    throws cn
  {
    ((dp)i.get(paramdg.D())).b().a(paramdg, this);
  }

  public void b(boolean paramBoolean)
  {
    this.m = ce.a(this.m, 1, paramBoolean);
  }

  public double c()
  {
    return this.a;
  }

  public void c(boolean paramBoolean)
  {
    this.m = ce.a(this.m, 2, paramBoolean);
  }

  public void d()
  {
    this.m = ce.b(this.m, 0);
  }

  public boolean e()
  {
    return ce.a(this.m, 0);
  }

  public double f()
  {
    return this.b;
  }

  public void h()
  {
    this.m = ce.b(this.m, 1);
  }

  public boolean i()
  {
    return ce.a(this.m, 1);
  }

  public long j()
  {
    return this.c;
  }

  public void k()
  {
    this.m = ce.b(this.m, 2);
  }

  public boolean l()
  {
    return ce.a(this.m, 2);
  }

  public void m()
    throws cn
  {
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("Location(");
    localStringBuilder.append("lat:");
    localStringBuilder.append(this.a);
    localStringBuilder.append(", ");
    localStringBuilder.append("lng:");
    localStringBuilder.append(this.b);
    localStringBuilder.append(", ");
    localStringBuilder.append("ts:");
    localStringBuilder.append(this.c);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }

  private static class a extends dq<be>
  {
    public void a(dg paramdg, be parambe)
      throws cn
    {
      paramdg.j();
      db localdb = paramdg.l();
      if (localdb.b == 0)
      {
        paramdg.k();
        if (!parambe.e())
          throw new dh("Required field 'lat' was not found in serialized data! Struct: " + toString());
      }
      else
      {
        switch (localdb.c)
        {
        default:
          dj.a(paramdg, localdb.b);
        case 1:
        case 2:
        case 3:
        }
        while (true)
        {
          paramdg.m();
          break;
          if (localdb.b == 4)
          {
            parambe.a = paramdg.y();
            parambe.a(true);
          }
          else
          {
            dj.a(paramdg, localdb.b);
            continue;
            if (localdb.b == 4)
            {
              parambe.b = paramdg.y();
              parambe.b(true);
            }
            else
            {
              dj.a(paramdg, localdb.b);
              continue;
              if (localdb.b == 10)
              {
                parambe.c = paramdg.x();
                parambe.c(true);
              }
              else
              {
                dj.a(paramdg, localdb.b);
              }
            }
          }
        }
      }
      if (!parambe.i())
        throw new dh("Required field 'lng' was not found in serialized data! Struct: " + toString());
      if (!parambe.l())
        throw new dh("Required field 'ts' was not found in serialized data! Struct: " + toString());
      parambe.m();
    }

    public void b(dg paramdg, be parambe)
      throws cn
    {
      parambe.m();
      paramdg.a(be.n());
      paramdg.a(be.o());
      paramdg.a(parambe.a);
      paramdg.c();
      paramdg.a(be.p());
      paramdg.a(parambe.b);
      paramdg.c();
      paramdg.a(be.q());
      paramdg.a(parambe.c);
      paramdg.c();
      paramdg.d();
      paramdg.b();
    }
  }

  private static class b
    implements dp
  {
    public be.a a()
    {
      return new be.a(null);
    }
  }

  private static class c extends dr<be>
  {
    public void a(dg paramdg, be parambe)
      throws cn
    {
      dm localdm = (dm)paramdg;
      localdm.a(parambe.a);
      localdm.a(parambe.b);
      localdm.a(parambe.c);
    }

    public void b(dg paramdg, be parambe)
      throws cn
    {
      dm localdm = (dm)paramdg;
      parambe.a = localdm.y();
      parambe.a(true);
      parambe.b = localdm.y();
      parambe.b(true);
      parambe.c = localdm.x();
      parambe.c(true);
    }
  }

  private static class d
    implements dp
  {
    public be.c a()
    {
      return new be.c(null);
    }
  }

  public static enum e
    implements co
  {
    private static final Map<String, e> d;
    private final short e;
    private final String f;

    static
    {
      e[] arrayOfe = new e[3];
      arrayOfe[0] = a;
      arrayOfe[1] = b;
      arrayOfe[2] = c;
      g = arrayOfe;
      d = new HashMap();
      Iterator localIterator = EnumSet.allOf(e.class).iterator();
      while (localIterator.hasNext())
      {
        e locale = (e)localIterator.next();
        d.put(locale.b(), locale);
      }
    }

    private e(short paramShort, String paramString)
    {
      this.e = paramShort;
      this.f = paramString;
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
        return b;
      case 3:
      }
      return c;
    }

    public static e a(String paramString)
    {
      return (e)d.get(paramString);
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
      return this.e;
    }

    public String b()
    {
      return this.f;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.be
 * JD-Core Version:    0.6.2
 */