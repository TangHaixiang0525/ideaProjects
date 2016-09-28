package u.aly;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ap
  implements Serializable, Cloneable, ch<ap, e>
{
  public static final Map<e, ct> d;
  private static final dl e = new dl("ClientStats");
  private static final db f = new db("successful_requests", (byte)8, (short)1);
  private static final db g = new db("failed_requests", (byte)8, (short)2);
  private static final db h = new db("last_request_spent_ms", (byte)8, (short)3);
  private static final Map<Class<? extends do>, dp> i = new HashMap();
  private static final int j = 0;
  private static final int k = 1;
  private static final int l = 2;
  public int a;
  public int b;
  public int c;
  private byte m = 0;
  private e[] n;

  static
  {
    i.put(dq.class, new b(null));
    i.put(dr.class, new d(null));
    EnumMap localEnumMap = new EnumMap(e.class);
    localEnumMap.put(e.a, new ct("successful_requests", (byte)1, new cu((byte)8)));
    localEnumMap.put(e.b, new ct("failed_requests", (byte)1, new cu((byte)8)));
    localEnumMap.put(e.c, new ct("last_request_spent_ms", (byte)2, new cu((byte)8)));
    d = Collections.unmodifiableMap(localEnumMap);
    ct.a(ap.class, d);
  }

  public ap()
  {
    e[] arrayOfe = new e[1];
    arrayOfe[0] = e.c;
    this.n = arrayOfe;
    this.a = 0;
    this.b = 0;
  }

  public ap(int paramInt1, int paramInt2)
  {
    this();
    this.a = paramInt1;
    a(true);
    this.b = paramInt2;
    b(true);
  }

  public ap(ap paramap)
  {
    e[] arrayOfe = new e[1];
    arrayOfe[0] = e.c;
    this.n = arrayOfe;
    this.m = paramap.m;
    this.a = paramap.a;
    this.b = paramap.b;
    this.c = paramap.c;
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

  public ap a()
  {
    return new ap(this);
  }

  public ap a(int paramInt)
  {
    this.a = paramInt;
    a(true);
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

  public void b()
  {
    this.a = 0;
    this.b = 0;
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
    this.m = ce.a(this.m, 1, paramBoolean);
  }

  public int c()
  {
    return this.a;
  }

  public ap c(int paramInt)
  {
    this.b = paramInt;
    b(true);
    return this;
  }

  public void c(boolean paramBoolean)
  {
    this.m = ce.a(this.m, 2, paramBoolean);
  }

  public ap d(int paramInt)
  {
    this.c = paramInt;
    c(true);
    return this;
  }

  public void d()
  {
    this.m = ce.b(this.m, 0);
  }

  public e e(int paramInt)
  {
    return e.a(paramInt);
  }

  public boolean e()
  {
    return ce.a(this.m, 0);
  }

  public int f()
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

  public int j()
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
    StringBuilder localStringBuilder = new StringBuilder("ClientStats(");
    localStringBuilder.append("successful_requests:");
    localStringBuilder.append(this.a);
    localStringBuilder.append(", ");
    localStringBuilder.append("failed_requests:");
    localStringBuilder.append(this.b);
    if (l())
    {
      localStringBuilder.append(", ");
      localStringBuilder.append("last_request_spent_ms:");
      localStringBuilder.append(this.c);
    }
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }

  private static class a extends dq<ap>
  {
    public void a(dg paramdg, ap paramap)
      throws cn
    {
      paramdg.j();
      db localdb = paramdg.l();
      if (localdb.b == 0)
      {
        paramdg.k();
        if (!paramap.e())
          throw new dh("Required field 'successful_requests' was not found in serialized data! Struct: " + toString());
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
          if (localdb.b == 8)
          {
            paramap.a = paramdg.w();
            paramap.a(true);
          }
          else
          {
            dj.a(paramdg, localdb.b);
            continue;
            if (localdb.b == 8)
            {
              paramap.b = paramdg.w();
              paramap.b(true);
            }
            else
            {
              dj.a(paramdg, localdb.b);
              continue;
              if (localdb.b == 8)
              {
                paramap.c = paramdg.w();
                paramap.c(true);
              }
              else
              {
                dj.a(paramdg, localdb.b);
              }
            }
          }
        }
      }
      if (!paramap.i())
        throw new dh("Required field 'failed_requests' was not found in serialized data! Struct: " + toString());
      paramap.m();
    }

    public void b(dg paramdg, ap paramap)
      throws cn
    {
      paramap.m();
      paramdg.a(ap.n());
      paramdg.a(ap.o());
      paramdg.a(paramap.a);
      paramdg.c();
      paramdg.a(ap.p());
      paramdg.a(paramap.b);
      paramdg.c();
      if (paramap.l())
      {
        paramdg.a(ap.q());
        paramdg.a(paramap.c);
        paramdg.c();
      }
      paramdg.d();
      paramdg.b();
    }
  }

  private static class b
    implements dp
  {
    public ap.a a()
    {
      return new ap.a(null);
    }
  }

  private static class c extends dr<ap>
  {
    public void a(dg paramdg, ap paramap)
      throws cn
    {
      dm localdm = (dm)paramdg;
      localdm.a(paramap.a);
      localdm.a(paramap.b);
      BitSet localBitSet = new BitSet();
      if (paramap.l())
        localBitSet.set(0);
      localdm.a(localBitSet, 1);
      if (paramap.l())
        localdm.a(paramap.c);
    }

    public void b(dg paramdg, ap paramap)
      throws cn
    {
      dm localdm = (dm)paramdg;
      paramap.a = localdm.w();
      paramap.a(true);
      paramap.b = localdm.w();
      paramap.b(true);
      if (localdm.b(1).get(0))
      {
        paramap.c = localdm.w();
        paramap.c(true);
      }
    }
  }

  private static class d
    implements dp
  {
    public ap.c a()
    {
      return new ap.c(null);
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
 * Qualified Name:     u.aly.ap
 * JD-Core Version:    0.6.2
 */