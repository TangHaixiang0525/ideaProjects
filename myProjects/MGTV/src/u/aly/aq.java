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

public class aq
  implements Serializable, Cloneable, ch<aq, e>
{
  public static final Map<e, ct> b;
  private static final dl c = new dl("ControlPolicy");
  private static final db d = new db("latent", (byte)12, (short)1);
  private static final Map<Class<? extends do>, dp> e = new HashMap();
  public bd a;
  private e[] f;

  static
  {
    e.put(dq.class, new b(null));
    e.put(dr.class, new d(null));
    EnumMap localEnumMap = new EnumMap(e.class);
    localEnumMap.put(e.a, new ct("latent", (byte)2, new cy((byte)12, bd.class)));
    b = Collections.unmodifiableMap(localEnumMap);
    ct.a(aq.class, b);
  }

  public aq()
  {
    e[] arrayOfe = new e[1];
    arrayOfe[0] = e.a;
    this.f = arrayOfe;
  }

  public aq(aq paramaq)
  {
    e[] arrayOfe = new e[1];
    arrayOfe[0] = e.a;
    this.f = arrayOfe;
    if (paramaq.e())
      this.a = new bd(paramaq.a);
  }

  private void a(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    try
    {
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

  public aq a()
  {
    return new aq(this);
  }

  public aq a(bd parambd)
  {
    this.a = parambd;
    return this;
  }

  public void a(dg paramdg)
    throws cn
  {
    ((dp)e.get(paramdg.D())).b().b(paramdg, this);
  }

  public void a(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.a = null;
  }

  public void b()
  {
    this.a = null;
  }

  public void b(dg paramdg)
    throws cn
  {
    ((dp)e.get(paramdg.D())).b().a(paramdg, this);
  }

  public bd c()
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

  public void f()
    throws cn
  {
    if (this.a != null)
      this.a.j();
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("ControlPolicy(");
    if (e())
    {
      localStringBuilder.append("latent:");
      if (this.a != null)
        break label50;
      localStringBuilder.append("null");
    }
    while (true)
    {
      localStringBuilder.append(")");
      return localStringBuilder.toString();
      label50: localStringBuilder.append(this.a);
    }
  }

  private static class a extends dq<aq>
  {
    public void a(dg paramdg, aq paramaq)
      throws cn
    {
      paramdg.j();
      db localdb = paramdg.l();
      if (localdb.b == 0)
      {
        paramdg.k();
        paramaq.f();
        return;
      }
      switch (localdb.c)
      {
      default:
        dj.a(paramdg, localdb.b);
      case 1:
      }
      while (true)
      {
        paramdg.m();
        break;
        if (localdb.b == 12)
        {
          paramaq.a = new bd();
          paramaq.a.a(paramdg);
          paramaq.a(true);
        }
        else
        {
          dj.a(paramdg, localdb.b);
        }
      }
    }

    public void b(dg paramdg, aq paramaq)
      throws cn
    {
      paramaq.f();
      paramdg.a(aq.h());
      if ((paramaq.a != null) && (paramaq.e()))
      {
        paramdg.a(aq.i());
        paramaq.a.b(paramdg);
        paramdg.c();
      }
      paramdg.d();
      paramdg.b();
    }
  }

  private static class b
    implements dp
  {
    public aq.a a()
    {
      return new aq.a(null);
    }
  }

  private static class c extends dr<aq>
  {
    public void a(dg paramdg, aq paramaq)
      throws cn
    {
      dm localdm = (dm)paramdg;
      BitSet localBitSet = new BitSet();
      if (paramaq.e())
        localBitSet.set(0);
      localdm.a(localBitSet, 1);
      if (paramaq.e())
        paramaq.a.b(localdm);
    }

    public void b(dg paramdg, aq paramaq)
      throws cn
    {
      dm localdm = (dm)paramdg;
      if (localdm.b(1).get(0))
      {
        paramaq.a = new bd();
        paramaq.a.a(localdm);
        paramaq.a(true);
      }
    }
  }

  private static class d
    implements dp
  {
    public aq.c a()
    {
      return new aq.c(null);
    }
  }

  public static enum e
    implements co
  {
    private static final Map<String, e> b;
    private final short c;
    private final String d;

    static
    {
      e[] arrayOfe = new e[1];
      arrayOfe[0] = a;
      e = arrayOfe;
      b = new HashMap();
      Iterator localIterator = EnumSet.allOf(e.class).iterator();
      while (localIterator.hasNext())
      {
        e locale = (e)localIterator.next();
        b.put(locale.b(), locale);
      }
    }

    private e(short paramShort, String paramString)
    {
      this.c = paramShort;
      this.d = paramString;
    }

    public static e a(int paramInt)
    {
      switch (paramInt)
      {
      default:
        return null;
      case 1:
      }
      return a;
    }

    public static e a(String paramString)
    {
      return (e)b.get(paramString);
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
      return this.c;
    }

    public String b()
    {
      return this.d;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.aq
 * JD-Core Version:    0.6.2
 */