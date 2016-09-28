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

public class bj
  implements Serializable, Cloneable, ch<bj, e>
{
  public static final Map<e, ct> d;
  private static final dl e = new dl("Response");
  private static final db f = new db("resp_code", (byte)8, (short)1);
  private static final db g = new db("msg", (byte)11, (short)2);
  private static final db h = new db("imprint", (byte)12, (short)3);
  private static final Map<Class<? extends do>, dp> i = new HashMap();
  private static final int j;
  public int a;
  public String b;
  public ba c;
  private byte k = 0;
  private e[] l;

  static
  {
    i.put(dq.class, new b(null));
    i.put(dr.class, new d(null));
    EnumMap localEnumMap = new EnumMap(e.class);
    localEnumMap.put(e.a, new ct("resp_code", (byte)1, new cu((byte)8)));
    localEnumMap.put(e.b, new ct("msg", (byte)2, new cu((byte)11)));
    localEnumMap.put(e.c, new ct("imprint", (byte)2, new cy((byte)12, ba.class)));
    d = Collections.unmodifiableMap(localEnumMap);
    ct.a(bj.class, d);
  }

  public bj()
  {
    e[] arrayOfe = new e[2];
    arrayOfe[0] = e.b;
    arrayOfe[1] = e.c;
    this.l = arrayOfe;
  }

  public bj(int paramInt)
  {
    this();
    this.a = paramInt;
    a(true);
  }

  public bj(bj parambj)
  {
    e[] arrayOfe = new e[2];
    arrayOfe[0] = e.b;
    arrayOfe[1] = e.c;
    this.l = arrayOfe;
    this.k = parambj.k;
    this.a = parambj.a;
    if (parambj.i())
      this.b = parambj.b;
    if (parambj.l())
      this.c = new ba(parambj.c);
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

  public bj a()
  {
    return new bj(this);
  }

  public bj a(int paramInt)
  {
    this.a = paramInt;
    a(true);
    return this;
  }

  public bj a(String paramString)
  {
    this.b = paramString;
    return this;
  }

  public bj a(ba paramba)
  {
    this.c = paramba;
    return this;
  }

  public void a(dg paramdg)
    throws cn
  {
    ((dp)i.get(paramdg.D())).b().b(paramdg, this);
  }

  public void a(boolean paramBoolean)
  {
    this.k = ce.a(this.k, 0, paramBoolean);
  }

  public void b()
  {
    a(false);
    this.a = 0;
    this.b = null;
    this.c = null;
  }

  public void b(dg paramdg)
    throws cn
  {
    ((dp)i.get(paramdg.D())).b().a(paramdg, this);
  }

  public void b(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.b = null;
  }

  public int c()
  {
    return this.a;
  }

  public e c(int paramInt)
  {
    return e.a(paramInt);
  }

  public void c(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.c = null;
  }

  public void d()
  {
    this.k = ce.b(this.k, 0);
  }

  public boolean e()
  {
    return ce.a(this.k, 0);
  }

  public String f()
  {
    return this.b;
  }

  public void h()
  {
    this.b = null;
  }

  public boolean i()
  {
    return this.b != null;
  }

  public ba j()
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
    if (this.c != null)
      this.c.n();
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("Response(");
    localStringBuilder.append("resp_code:");
    localStringBuilder.append(this.a);
    if (i())
    {
      localStringBuilder.append(", ");
      localStringBuilder.append("msg:");
      if (this.b == null)
        localStringBuilder.append("null");
    }
    else if (l())
    {
      localStringBuilder.append(", ");
      localStringBuilder.append("imprint:");
      if (this.c != null)
        break label127;
      localStringBuilder.append("null");
    }
    while (true)
    {
      localStringBuilder.append(")");
      return localStringBuilder.toString();
      localStringBuilder.append(this.b);
      break;
      label127: localStringBuilder.append(this.c);
    }
  }

  private static class a extends dq<bj>
  {
    public void a(dg paramdg, bj parambj)
      throws cn
    {
      paramdg.j();
      db localdb = paramdg.l();
      if (localdb.b == 0)
      {
        paramdg.k();
        if (!parambj.e())
          throw new dh("Required field 'resp_code' was not found in serialized data! Struct: " + toString());
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
            parambj.a = paramdg.w();
            parambj.a(true);
          }
          else
          {
            dj.a(paramdg, localdb.b);
            continue;
            if (localdb.b == 11)
            {
              parambj.b = paramdg.z();
              parambj.b(true);
            }
            else
            {
              dj.a(paramdg, localdb.b);
              continue;
              if (localdb.b == 12)
              {
                parambj.c = new ba();
                parambj.c.a(paramdg);
                parambj.c(true);
              }
              else
              {
                dj.a(paramdg, localdb.b);
              }
            }
          }
        }
      }
      parambj.m();
    }

    public void b(dg paramdg, bj parambj)
      throws cn
    {
      parambj.m();
      paramdg.a(bj.n());
      paramdg.a(bj.o());
      paramdg.a(parambj.a);
      paramdg.c();
      if ((parambj.b != null) && (parambj.i()))
      {
        paramdg.a(bj.p());
        paramdg.a(parambj.b);
        paramdg.c();
      }
      if ((parambj.c != null) && (parambj.l()))
      {
        paramdg.a(bj.q());
        parambj.c.b(paramdg);
        paramdg.c();
      }
      paramdg.d();
      paramdg.b();
    }
  }

  private static class b
    implements dp
  {
    public bj.a a()
    {
      return new bj.a(null);
    }
  }

  private static class c extends dr<bj>
  {
    public void a(dg paramdg, bj parambj)
      throws cn
    {
      dm localdm = (dm)paramdg;
      localdm.a(parambj.a);
      BitSet localBitSet = new BitSet();
      if (parambj.i())
        localBitSet.set(0);
      if (parambj.l())
        localBitSet.set(1);
      localdm.a(localBitSet, 2);
      if (parambj.i())
        localdm.a(parambj.b);
      if (parambj.l())
        parambj.c.b(localdm);
    }

    public void b(dg paramdg, bj parambj)
      throws cn
    {
      dm localdm = (dm)paramdg;
      parambj.a = localdm.w();
      parambj.a(true);
      BitSet localBitSet = localdm.b(2);
      if (localBitSet.get(0))
      {
        parambj.b = localdm.z();
        parambj.b(true);
      }
      if (localBitSet.get(1))
      {
        parambj.c = new ba();
        parambj.c.a(localdm);
        parambj.c(true);
      }
    }
  }

  private static class d
    implements dp
  {
    public bj.c a()
    {
      return new bj.c(null);
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
 * Qualified Name:     u.aly.bj
 * JD-Core Version:    0.6.2
 */