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

public class ay
  implements Serializable, Cloneable, ch<ay, e>
{
  public static final Map<e, ct> d;
  private static final dl e = new dl("IdSnapshot");
  private static final db f = new db("identity", (byte)11, (short)1);
  private static final db g = new db("ts", (byte)10, (short)2);
  private static final db h = new db("version", (byte)8, (short)3);
  private static final Map<Class<? extends do>, dp> i = new HashMap();
  private static final int j = 0;
  private static final int k = 1;
  public String a;
  public long b;
  public int c;
  private byte l = 0;

  static
  {
    i.put(dq.class, new b(null));
    i.put(dr.class, new d(null));
    EnumMap localEnumMap = new EnumMap(e.class);
    localEnumMap.put(e.a, new ct("identity", (byte)1, new cu((byte)11)));
    localEnumMap.put(e.b, new ct("ts", (byte)1, new cu((byte)10)));
    localEnumMap.put(e.c, new ct("version", (byte)1, new cu((byte)8)));
    d = Collections.unmodifiableMap(localEnumMap);
    ct.a(ay.class, d);
  }

  public ay()
  {
  }

  public ay(String paramString, long paramLong, int paramInt)
  {
    this();
    this.a = paramString;
    this.b = paramLong;
    b(true);
    this.c = paramInt;
    c(true);
  }

  public ay(ay paramay)
  {
    this.l = paramay.l;
    if (paramay.e())
      this.a = paramay.a;
    this.b = paramay.b;
    this.c = paramay.c;
  }

  private void a(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    try
    {
      this.l = 0;
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

  public ay a()
  {
    return new ay(this);
  }

  public ay a(int paramInt)
  {
    this.c = paramInt;
    c(true);
    return this;
  }

  public ay a(long paramLong)
  {
    this.b = paramLong;
    b(true);
    return this;
  }

  public ay a(String paramString)
  {
    this.a = paramString;
    return this;
  }

  public void a(dg paramdg)
    throws cn
  {
    ((dp)i.get(paramdg.D())).b().b(paramdg, this);
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
    c(false);
    this.c = 0;
  }

  public void b(dg paramdg)
    throws cn
  {
    ((dp)i.get(paramdg.D())).b().a(paramdg, this);
  }

  public void b(boolean paramBoolean)
  {
    this.l = ce.a(this.l, 0, paramBoolean);
  }

  public String c()
  {
    return this.a;
  }

  public e c(int paramInt)
  {
    return e.a(paramInt);
  }

  public void c(boolean paramBoolean)
  {
    this.l = ce.a(this.l, 1, paramBoolean);
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
    this.l = ce.b(this.l, 0);
  }

  public boolean i()
  {
    return ce.a(this.l, 0);
  }

  public int j()
  {
    return this.c;
  }

  public void k()
  {
    this.l = ce.b(this.l, 1);
  }

  public boolean l()
  {
    return ce.a(this.l, 1);
  }

  public void m()
    throws cn
  {
    if (this.a == null)
      throw new dh("Required field 'identity' was not present! Struct: " + toString());
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("IdSnapshot(");
    localStringBuilder.append("identity:");
    if (this.a == null)
      localStringBuilder.append("null");
    while (true)
    {
      localStringBuilder.append(", ");
      localStringBuilder.append("ts:");
      localStringBuilder.append(this.b);
      localStringBuilder.append(", ");
      localStringBuilder.append("version:");
      localStringBuilder.append(this.c);
      localStringBuilder.append(")");
      return localStringBuilder.toString();
      localStringBuilder.append(this.a);
    }
  }

  private static class a extends dq<ay>
  {
    public void a(dg paramdg, ay paramay)
      throws cn
    {
      paramdg.j();
      db localdb = paramdg.l();
      if (localdb.b == 0)
      {
        paramdg.k();
        if (!paramay.i())
          throw new dh("Required field 'ts' was not found in serialized data! Struct: " + toString());
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
          if (localdb.b == 11)
          {
            paramay.a = paramdg.z();
            paramay.a(true);
          }
          else
          {
            dj.a(paramdg, localdb.b);
            continue;
            if (localdb.b == 10)
            {
              paramay.b = paramdg.x();
              paramay.b(true);
            }
            else
            {
              dj.a(paramdg, localdb.b);
              continue;
              if (localdb.b == 8)
              {
                paramay.c = paramdg.w();
                paramay.c(true);
              }
              else
              {
                dj.a(paramdg, localdb.b);
              }
            }
          }
        }
      }
      if (!paramay.l())
        throw new dh("Required field 'version' was not found in serialized data! Struct: " + toString());
      paramay.m();
    }

    public void b(dg paramdg, ay paramay)
      throws cn
    {
      paramay.m();
      paramdg.a(ay.n());
      if (paramay.a != null)
      {
        paramdg.a(ay.o());
        paramdg.a(paramay.a);
        paramdg.c();
      }
      paramdg.a(ay.p());
      paramdg.a(paramay.b);
      paramdg.c();
      paramdg.a(ay.q());
      paramdg.a(paramay.c);
      paramdg.c();
      paramdg.d();
      paramdg.b();
    }
  }

  private static class b
    implements dp
  {
    public ay.a a()
    {
      return new ay.a(null);
    }
  }

  private static class c extends dr<ay>
  {
    public void a(dg paramdg, ay paramay)
      throws cn
    {
      dm localdm = (dm)paramdg;
      localdm.a(paramay.a);
      localdm.a(paramay.b);
      localdm.a(paramay.c);
    }

    public void b(dg paramdg, ay paramay)
      throws cn
    {
      dm localdm = (dm)paramdg;
      paramay.a = localdm.z();
      paramay.a(true);
      paramay.b = localdm.x();
      paramay.b(true);
      paramay.c = localdm.w();
      paramay.c(true);
    }
  }

  private static class d
    implements dp
  {
    public ay.c a()
    {
      return new ay.c(null);
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
 * Qualified Name:     u.aly.ay
 * JD-Core Version:    0.6.2
 */