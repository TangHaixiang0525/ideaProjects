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

public class bo
  implements Serializable, Cloneable, ch<bo, e>
{
  public static final Map<e, ct> e;
  private static final dl f = new dl("UserInfo");
  private static final db g = new db("gender", (byte)8, (short)1);
  private static final db h = new db("age", (byte)8, (short)2);
  private static final db i = new db("id", (byte)11, (short)3);
  private static final db j = new db("source", (byte)11, (short)4);
  private static final Map<Class<? extends do>, dp> k = new HashMap();
  private static final int l;
  public aw a;
  public int b;
  public String c;
  public String d;
  private byte m = 0;
  private e[] n;

  static
  {
    k.put(dq.class, new b(null));
    k.put(dr.class, new d(null));
    EnumMap localEnumMap = new EnumMap(e.class);
    localEnumMap.put(e.a, new ct("gender", (byte)2, new cs((byte)16, aw.class)));
    localEnumMap.put(e.b, new ct("age", (byte)2, new cu((byte)8)));
    localEnumMap.put(e.c, new ct("id", (byte)2, new cu((byte)11)));
    localEnumMap.put(e.d, new ct("source", (byte)2, new cu((byte)11)));
    e = Collections.unmodifiableMap(localEnumMap);
    ct.a(bo.class, e);
  }

  public bo()
  {
    e[] arrayOfe = new e[4];
    arrayOfe[0] = e.a;
    arrayOfe[1] = e.b;
    arrayOfe[2] = e.c;
    arrayOfe[3] = e.d;
    this.n = arrayOfe;
  }

  public bo(bo parambo)
  {
    e[] arrayOfe = new e[4];
    arrayOfe[0] = e.a;
    arrayOfe[1] = e.b;
    arrayOfe[2] = e.c;
    arrayOfe[3] = e.d;
    this.n = arrayOfe;
    this.m = parambo.m;
    if (parambo.e())
      this.a = parambo.a;
    this.b = parambo.b;
    if (parambo.l())
      this.c = parambo.c;
    if (parambo.o())
      this.d = parambo.d;
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

  public bo a()
  {
    return new bo(this);
  }

  public bo a(int paramInt)
  {
    this.b = paramInt;
    b(true);
    return this;
  }

  public bo a(String paramString)
  {
    this.c = paramString;
    return this;
  }

  public bo a(aw paramaw)
  {
    this.a = paramaw;
    return this;
  }

  public void a(dg paramdg)
    throws cn
  {
    ((dp)k.get(paramdg.D())).b().b(paramdg, this);
  }

  public void a(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.a = null;
  }

  public bo b(String paramString)
  {
    this.d = paramString;
    return this;
  }

  public void b()
  {
    this.a = null;
    b(false);
    this.b = 0;
    this.c = null;
    this.d = null;
  }

  public void b(dg paramdg)
    throws cn
  {
    ((dp)k.get(paramdg.D())).b().a(paramdg, this);
  }

  public void b(boolean paramBoolean)
  {
    this.m = ce.a(this.m, 0, paramBoolean);
  }

  public aw c()
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
    this.a = null;
  }

  public void d(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.d = null;
  }

  public boolean e()
  {
    return this.a != null;
  }

  public int f()
  {
    return this.b;
  }

  public void h()
  {
    this.m = ce.b(this.m, 0);
  }

  public boolean i()
  {
    return ce.a(this.m, 0);
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

  public String m()
  {
    return this.d;
  }

  public void n()
  {
    this.d = null;
  }

  public boolean o()
  {
    return this.d != null;
  }

  public void p()
    throws cn
  {
  }

  public String toString()
  {
    int i1 = 0;
    StringBuilder localStringBuilder = new StringBuilder("UserInfo(");
    int i2 = 1;
    if (e())
    {
      localStringBuilder.append("gender:");
      if (this.a == null)
      {
        localStringBuilder.append("null");
        i2 = 0;
      }
    }
    else
    {
      if (i())
      {
        if (i2 == 0)
          localStringBuilder.append(", ");
        localStringBuilder.append("age:");
        localStringBuilder.append(this.b);
        i2 = 0;
      }
      if (!l())
        break label219;
      if (i2 == 0)
        localStringBuilder.append(", ");
      localStringBuilder.append("id:");
      if (this.c != null)
        break label193;
      localStringBuilder.append("null");
    }
    while (true)
    {
      label126: if (o())
      {
        if (i1 == 0)
          localStringBuilder.append(", ");
        localStringBuilder.append("source:");
        if (this.d != null)
          break label207;
        localStringBuilder.append("null");
      }
      while (true)
      {
        localStringBuilder.append(")");
        return localStringBuilder.toString();
        localStringBuilder.append(this.a);
        break;
        label193: localStringBuilder.append(this.c);
        i1 = 0;
        break label126;
        label207: localStringBuilder.append(this.d);
      }
      label219: i1 = i2;
    }
  }

  private static class a extends dq<bo>
  {
    public void a(dg paramdg, bo parambo)
      throws cn
    {
      paramdg.j();
      db localdb = paramdg.l();
      if (localdb.b == 0)
      {
        paramdg.k();
        parambo.p();
        return;
      }
      switch (localdb.c)
      {
      default:
        dj.a(paramdg, localdb.b);
      case 1:
      case 2:
      case 3:
      case 4:
      }
      while (true)
      {
        paramdg.m();
        break;
        if (localdb.b == 8)
        {
          parambo.a = aw.a(paramdg.w());
          parambo.a(true);
        }
        else
        {
          dj.a(paramdg, localdb.b);
          continue;
          if (localdb.b == 8)
          {
            parambo.b = paramdg.w();
            parambo.b(true);
          }
          else
          {
            dj.a(paramdg, localdb.b);
            continue;
            if (localdb.b == 11)
            {
              parambo.c = paramdg.z();
              parambo.c(true);
            }
            else
            {
              dj.a(paramdg, localdb.b);
              continue;
              if (localdb.b == 11)
              {
                parambo.d = paramdg.z();
                parambo.d(true);
              }
              else
              {
                dj.a(paramdg, localdb.b);
              }
            }
          }
        }
      }
    }

    public void b(dg paramdg, bo parambo)
      throws cn
    {
      parambo.p();
      paramdg.a(bo.q());
      if ((parambo.a != null) && (parambo.e()))
      {
        paramdg.a(bo.r());
        paramdg.a(parambo.a.a());
        paramdg.c();
      }
      if (parambo.i())
      {
        paramdg.a(bo.s());
        paramdg.a(parambo.b);
        paramdg.c();
      }
      if ((parambo.c != null) && (parambo.l()))
      {
        paramdg.a(bo.t());
        paramdg.a(parambo.c);
        paramdg.c();
      }
      if ((parambo.d != null) && (parambo.o()))
      {
        paramdg.a(bo.u());
        paramdg.a(parambo.d);
        paramdg.c();
      }
      paramdg.d();
      paramdg.b();
    }
  }

  private static class b
    implements dp
  {
    public bo.a a()
    {
      return new bo.a(null);
    }
  }

  private static class c extends dr<bo>
  {
    public void a(dg paramdg, bo parambo)
      throws cn
    {
      dm localdm = (dm)paramdg;
      BitSet localBitSet = new BitSet();
      if (parambo.e())
        localBitSet.set(0);
      if (parambo.i())
        localBitSet.set(1);
      if (parambo.l())
        localBitSet.set(2);
      if (parambo.o())
        localBitSet.set(3);
      localdm.a(localBitSet, 4);
      if (parambo.e())
        localdm.a(parambo.a.a());
      if (parambo.i())
        localdm.a(parambo.b);
      if (parambo.l())
        localdm.a(parambo.c);
      if (parambo.o())
        localdm.a(parambo.d);
    }

    public void b(dg paramdg, bo parambo)
      throws cn
    {
      dm localdm = (dm)paramdg;
      BitSet localBitSet = localdm.b(4);
      if (localBitSet.get(0))
      {
        parambo.a = aw.a(localdm.w());
        parambo.a(true);
      }
      if (localBitSet.get(1))
      {
        parambo.b = localdm.w();
        parambo.b(true);
      }
      if (localBitSet.get(2))
      {
        parambo.c = localdm.z();
        parambo.c(true);
      }
      if (localBitSet.get(3))
      {
        parambo.d = localdm.z();
        parambo.d(true);
      }
    }
  }

  private static class d
    implements dp
  {
    public bo.c a()
    {
      return new bo.c(null);
    }
  }

  public static enum e
    implements co
  {
    private static final Map<String, e> e;
    private final short f;
    private final String g;

    static
    {
      e[] arrayOfe = new e[4];
      arrayOfe[0] = a;
      arrayOfe[1] = b;
      arrayOfe[2] = c;
      arrayOfe[3] = d;
      h = arrayOfe;
      e = new HashMap();
      Iterator localIterator = EnumSet.allOf(e.class).iterator();
      while (localIterator.hasNext())
      {
        e locale = (e)localIterator.next();
        e.put(locale.b(), locale);
      }
    }

    private e(short paramShort, String paramString)
    {
      this.f = paramShort;
      this.g = paramString;
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
        return c;
      case 4:
      }
      return d;
    }

    public static e a(String paramString)
    {
      return (e)e.get(paramString);
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
      return this.f;
    }

    public String b()
    {
      return this.g;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.bo
 * JD-Core Version:    0.6.2
 */