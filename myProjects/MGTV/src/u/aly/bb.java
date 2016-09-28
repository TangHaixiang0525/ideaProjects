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

public class bb
  implements Serializable, Cloneable, ch<bb, e>
{
  public static final Map<e, ct> d;
  private static final dl e = new dl("ImprintValue");
  private static final db f = new db("value", (byte)11, (short)1);
  private static final db g = new db("ts", (byte)10, (short)2);
  private static final db h = new db("guid", (byte)11, (short)3);
  private static final Map<Class<? extends do>, dp> i = new HashMap();
  private static final int j;
  public String a;
  public long b;
  public String c;
  private byte k = 0;
  private e[] l;

  static
  {
    i.put(dq.class, new b(null));
    i.put(dr.class, new d(null));
    EnumMap localEnumMap = new EnumMap(e.class);
    localEnumMap.put(e.a, new ct("value", (byte)2, new cu((byte)11)));
    localEnumMap.put(e.b, new ct("ts", (byte)1, new cu((byte)10)));
    localEnumMap.put(e.c, new ct("guid", (byte)1, new cu((byte)11)));
    d = Collections.unmodifiableMap(localEnumMap);
    ct.a(bb.class, d);
  }

  public bb()
  {
    e[] arrayOfe = new e[1];
    arrayOfe[0] = e.a;
    this.l = arrayOfe;
  }

  public bb(long paramLong, String paramString)
  {
    this();
    this.b = paramLong;
    b(true);
    this.c = paramString;
  }

  public bb(bb parambb)
  {
    e[] arrayOfe = new e[1];
    arrayOfe[0] = e.a;
    this.l = arrayOfe;
    this.k = parambb.k;
    if (parambb.e())
      this.a = parambb.a;
    this.b = parambb.b;
    if (parambb.l())
      this.c = parambb.c;
  }

  private void a(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    try
    {
      this.k = 0;
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

  public bb a()
  {
    return new bb(this);
  }

  public bb a(long paramLong)
  {
    this.b = paramLong;
    b(true);
    return this;
  }

  public bb a(String paramString)
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

  public bb b(String paramString)
  {
    this.c = paramString;
    return this;
  }

  public void b()
  {
    this.a = null;
    b(false);
    this.b = 0L;
    this.c = null;
  }

  public void b(dg paramdg)
    throws cn
  {
    ((dp)i.get(paramdg.D())).b().a(paramdg, this);
  }

  public void b(boolean paramBoolean)
  {
    this.k = ce.a(this.k, 0, paramBoolean);
  }

  public String c()
  {
    return this.a;
  }

  public void c(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.c = null;
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
    this.k = ce.b(this.k, 0);
  }

  public boolean i()
  {
    return ce.a(this.k, 0);
  }

  public String j()
  {
    return this.c;
  }

  public void k()
  {
    this.c = null;
  }

  public boolean l()
  {
    return this.c != null;
  }

  public void m()
    throws cn
  {
    if (this.c == null)
      throw new dh("Required field 'guid' was not present! Struct: " + toString());
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("ImprintValue(");
    int m = 1;
    if (e())
    {
      localStringBuilder.append("value:");
      if (this.a == null)
      {
        localStringBuilder.append("null");
        m = 0;
      }
    }
    else
    {
      if (m == 0)
        localStringBuilder.append(", ");
      localStringBuilder.append("ts:");
      localStringBuilder.append(this.b);
      localStringBuilder.append(", ");
      localStringBuilder.append("guid:");
      if (this.c != null)
        break label123;
      localStringBuilder.append("null");
    }
    while (true)
    {
      localStringBuilder.append(")");
      return localStringBuilder.toString();
      localStringBuilder.append(this.a);
      break;
      label123: localStringBuilder.append(this.c);
    }
  }

  private static class a extends dq<bb>
  {
    public void a(dg paramdg, bb parambb)
      throws cn
    {
      paramdg.j();
      db localdb = paramdg.l();
      if (localdb.b == 0)
      {
        paramdg.k();
        if (!parambb.i())
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
            parambb.a = paramdg.z();
            parambb.a(true);
          }
          else
          {
            dj.a(paramdg, localdb.b);
            continue;
            if (localdb.b == 10)
            {
              parambb.b = paramdg.x();
              parambb.b(true);
            }
            else
            {
              dj.a(paramdg, localdb.b);
              continue;
              if (localdb.b == 11)
              {
                parambb.c = paramdg.z();
                parambb.c(true);
              }
              else
              {
                dj.a(paramdg, localdb.b);
              }
            }
          }
        }
      }
      parambb.m();
    }

    public void b(dg paramdg, bb parambb)
      throws cn
    {
      parambb.m();
      paramdg.a(bb.n());
      if ((parambb.a != null) && (parambb.e()))
      {
        paramdg.a(bb.o());
        paramdg.a(parambb.a);
        paramdg.c();
      }
      paramdg.a(bb.p());
      paramdg.a(parambb.b);
      paramdg.c();
      if (parambb.c != null)
      {
        paramdg.a(bb.q());
        paramdg.a(parambb.c);
        paramdg.c();
      }
      paramdg.d();
      paramdg.b();
    }
  }

  private static class b
    implements dp
  {
    public bb.a a()
    {
      return new bb.a(null);
    }
  }

  private static class c extends dr<bb>
  {
    public void a(dg paramdg, bb parambb)
      throws cn
    {
      dm localdm = (dm)paramdg;
      localdm.a(parambb.b);
      localdm.a(parambb.c);
      BitSet localBitSet = new BitSet();
      if (parambb.e())
        localBitSet.set(0);
      localdm.a(localBitSet, 1);
      if (parambb.e())
        localdm.a(parambb.a);
    }

    public void b(dg paramdg, bb parambb)
      throws cn
    {
      dm localdm = (dm)paramdg;
      parambb.b = localdm.x();
      parambb.b(true);
      parambb.c = localdm.z();
      parambb.c(true);
      if (localdm.b(1).get(0))
      {
        parambb.a = localdm.z();
        parambb.a(true);
      }
    }
  }

  private static class d
    implements dp
  {
    public bb.c a()
    {
      return new bb.c(null);
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
 * Qualified Name:     u.aly.bb
 * JD-Core Version:    0.6.2
 */