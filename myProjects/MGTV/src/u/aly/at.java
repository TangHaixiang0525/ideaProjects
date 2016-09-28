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

public class at
  implements Serializable, Cloneable, ch<at, e>
{
  public static final Map<e, ct> d;
  private static final dl e = new dl("Error");
  private static final db f = new db("ts", (byte)10, (short)1);
  private static final db g = new db("context", (byte)11, (short)2);
  private static final db h = new db("source", (byte)8, (short)3);
  private static final Map<Class<? extends do>, dp> i = new HashMap();
  private static final int j;
  public long a;
  public String b;
  public au c;
  private byte k = 0;
  private e[] l;

  static
  {
    i.put(dq.class, new b(null));
    i.put(dr.class, new d(null));
    EnumMap localEnumMap = new EnumMap(e.class);
    localEnumMap.put(e.a, new ct("ts", (byte)1, new cu((byte)10)));
    localEnumMap.put(e.b, new ct("context", (byte)1, new cu((byte)11)));
    localEnumMap.put(e.c, new ct("source", (byte)2, new cs((byte)16, au.class)));
    d = Collections.unmodifiableMap(localEnumMap);
    ct.a(at.class, d);
  }

  public at()
  {
    e[] arrayOfe = new e[1];
    arrayOfe[0] = e.c;
    this.l = arrayOfe;
  }

  public at(long paramLong, String paramString)
  {
    this();
    this.a = paramLong;
    b(true);
    this.b = paramString;
  }

  public at(at paramat)
  {
    e[] arrayOfe = new e[1];
    arrayOfe[0] = e.c;
    this.l = arrayOfe;
    this.k = paramat.k;
    this.a = paramat.a;
    if (paramat.i())
      this.b = paramat.b;
    if (paramat.l())
      this.c = paramat.c;
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

  public at a()
  {
    return new at(this);
  }

  public at a(long paramLong)
  {
    this.a = paramLong;
    b(true);
    return this;
  }

  public at a(String paramString)
  {
    this.b = paramString;
    return this;
  }

  public at a(au paramau)
  {
    this.c = paramau;
    return this;
  }

  public void a(dg paramdg)
    throws cn
  {
    ((dp)i.get(paramdg.D())).b().b(paramdg, this);
  }

  public void b()
  {
    b(false);
    this.a = 0L;
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
    this.k = ce.a(this.k, 0, paramBoolean);
  }

  public long c()
  {
    return this.a;
  }

  public void c(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.b = null;
  }

  public void d()
  {
    this.k = ce.b(this.k, 0);
  }

  public void d(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.c = null;
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

  public au j()
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
    if (this.b == null)
      throw new dh("Required field 'context' was not present! Struct: " + toString());
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("Error(");
    localStringBuilder.append("ts:");
    localStringBuilder.append(this.a);
    localStringBuilder.append(", ");
    localStringBuilder.append("context:");
    if (this.b == null)
    {
      localStringBuilder.append("null");
      if (l())
      {
        localStringBuilder.append(", ");
        localStringBuilder.append("source:");
        if (this.c != null)
          break label122;
        localStringBuilder.append("null");
      }
    }
    while (true)
    {
      localStringBuilder.append(")");
      return localStringBuilder.toString();
      localStringBuilder.append(this.b);
      break;
      label122: localStringBuilder.append(this.c);
    }
  }

  private static class a extends dq<at>
  {
    public void a(dg paramdg, at paramat)
      throws cn
    {
      paramdg.j();
      db localdb = paramdg.l();
      if (localdb.b == 0)
      {
        paramdg.k();
        if (!paramat.e())
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
          if (localdb.b == 10)
          {
            paramat.a = paramdg.x();
            paramat.b(true);
          }
          else
          {
            dj.a(paramdg, localdb.b);
            continue;
            if (localdb.b == 11)
            {
              paramat.b = paramdg.z();
              paramat.c(true);
            }
            else
            {
              dj.a(paramdg, localdb.b);
              continue;
              if (localdb.b == 8)
              {
                paramat.c = au.a(paramdg.w());
                paramat.d(true);
              }
              else
              {
                dj.a(paramdg, localdb.b);
              }
            }
          }
        }
      }
      paramat.m();
    }

    public void b(dg paramdg, at paramat)
      throws cn
    {
      paramat.m();
      paramdg.a(at.n());
      paramdg.a(at.o());
      paramdg.a(paramat.a);
      paramdg.c();
      if (paramat.b != null)
      {
        paramdg.a(at.p());
        paramdg.a(paramat.b);
        paramdg.c();
      }
      if ((paramat.c != null) && (paramat.l()))
      {
        paramdg.a(at.q());
        paramdg.a(paramat.c.a());
        paramdg.c();
      }
      paramdg.d();
      paramdg.b();
    }
  }

  private static class b
    implements dp
  {
    public at.a a()
    {
      return new at.a(null);
    }
  }

  private static class c extends dr<at>
  {
    public void a(dg paramdg, at paramat)
      throws cn
    {
      dm localdm = (dm)paramdg;
      localdm.a(paramat.a);
      localdm.a(paramat.b);
      BitSet localBitSet = new BitSet();
      if (paramat.l())
        localBitSet.set(0);
      localdm.a(localBitSet, 1);
      if (paramat.l())
        localdm.a(paramat.c.a());
    }

    public void b(dg paramdg, at paramat)
      throws cn
    {
      dm localdm = (dm)paramdg;
      paramat.a = localdm.x();
      paramat.b(true);
      paramat.b = localdm.z();
      paramat.c(true);
      if (localdm.b(1).get(0))
      {
        paramat.c = au.a(localdm.w());
        paramat.d(true);
      }
    }
  }

  private static class d
    implements dp
  {
    public at.c a()
    {
      return new at.c(null);
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
 * Qualified Name:     u.aly.at
 * JD-Core Version:    0.6.2
 */