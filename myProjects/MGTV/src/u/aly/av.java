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
import java.util.Map.Entry;
import java.util.Set;

public class av
  implements Serializable, Cloneable, ch<av, e>
{
  public static final Map<e, ct> f;
  private static final dl g = new dl("Event");
  private static final db h = new db("name", (byte)11, (short)1);
  private static final db i = new db("properties", (byte)13, (short)2);
  private static final db j = new db("duration", (byte)10, (short)3);
  private static final db k = new db("acc", (byte)8, (short)4);
  private static final db l = new db("ts", (byte)10, (short)5);
  private static final Map<Class<? extends do>, dp> m = new HashMap();
  private static final int n = 0;
  private static final int o = 1;
  private static final int p = 2;
  public String a;
  public Map<String, bh> b;
  public long c;
  public int d;
  public long e;
  private byte q = 0;
  private e[] r;

  static
  {
    m.put(dq.class, new b(null));
    m.put(dr.class, new d(null));
    EnumMap localEnumMap = new EnumMap(e.class);
    localEnumMap.put(e.a, new ct("name", (byte)1, new cu((byte)11)));
    localEnumMap.put(e.b, new ct("properties", (byte)1, new cw((byte)13, new cu((byte)11), new cy((byte)12, bh.class))));
    localEnumMap.put(e.c, new ct("duration", (byte)2, new cu((byte)10)));
    localEnumMap.put(e.d, new ct("acc", (byte)2, new cu((byte)8)));
    localEnumMap.put(e.e, new ct("ts", (byte)1, new cu((byte)10)));
    f = Collections.unmodifiableMap(localEnumMap);
    ct.a(av.class, f);
  }

  public av()
  {
    e[] arrayOfe = new e[2];
    arrayOfe[0] = e.c;
    arrayOfe[1] = e.d;
    this.r = arrayOfe;
  }

  public av(String paramString, Map<String, bh> paramMap, long paramLong)
  {
    this();
    this.a = paramString;
    this.b = paramMap;
    this.e = paramLong;
    e(true);
  }

  public av(av paramav)
  {
    e[] arrayOfe = new e[2];
    arrayOfe[0] = e.c;
    arrayOfe[1] = e.d;
    this.r = arrayOfe;
    this.q = paramav.q;
    if (paramav.e())
      this.a = paramav.a;
    if (paramav.j())
    {
      HashMap localHashMap = new HashMap();
      Iterator localIterator = paramav.b.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        localHashMap.put((String)localEntry.getKey(), new bh((bh)localEntry.getValue()));
      }
      this.b = localHashMap;
    }
    this.c = paramav.c;
    this.d = paramav.d;
    this.e = paramav.e;
  }

  private void a(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    try
    {
      this.q = 0;
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

  public av a()
  {
    return new av(this);
  }

  public av a(int paramInt)
  {
    this.d = paramInt;
    d(true);
    return this;
  }

  public av a(long paramLong)
  {
    this.c = paramLong;
    c(true);
    return this;
  }

  public av a(String paramString)
  {
    this.a = paramString;
    return this;
  }

  public av a(Map<String, bh> paramMap)
  {
    this.b = paramMap;
    return this;
  }

  public void a(String paramString, bh parambh)
  {
    if (this.b == null)
      this.b = new HashMap();
    this.b.put(paramString, parambh);
  }

  public void a(dg paramdg)
    throws cn
  {
    ((dp)m.get(paramdg.D())).b().b(paramdg, this);
  }

  public void a(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.a = null;
  }

  public av b(long paramLong)
  {
    this.e = paramLong;
    e(true);
    return this;
  }

  public void b()
  {
    this.a = null;
    this.b = null;
    c(false);
    this.c = 0L;
    d(false);
    this.d = 0;
    e(false);
    this.e = 0L;
  }

  public void b(dg paramdg)
    throws cn
  {
    ((dp)m.get(paramdg.D())).b().a(paramdg, this);
  }

  public void b(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.b = null;
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
    this.q = ce.a(this.q, 0, paramBoolean);
  }

  public void d()
  {
    this.a = null;
  }

  public void d(boolean paramBoolean)
  {
    this.q = ce.a(this.q, 1, paramBoolean);
  }

  public void e(boolean paramBoolean)
  {
    this.q = ce.a(this.q, 2, paramBoolean);
  }

  public boolean e()
  {
    return this.a != null;
  }

  public int f()
  {
    if (this.b == null)
      return 0;
    return this.b.size();
  }

  public Map<String, bh> h()
  {
    return this.b;
  }

  public void i()
  {
    this.b = null;
  }

  public boolean j()
  {
    return this.b != null;
  }

  public long k()
  {
    return this.c;
  }

  public void l()
  {
    this.q = ce.b(this.q, 0);
  }

  public boolean m()
  {
    return ce.a(this.q, 0);
  }

  public int n()
  {
    return this.d;
  }

  public void o()
  {
    this.q = ce.b(this.q, 1);
  }

  public boolean p()
  {
    return ce.a(this.q, 1);
  }

  public long q()
  {
    return this.e;
  }

  public void r()
  {
    this.q = ce.b(this.q, 2);
  }

  public boolean s()
  {
    return ce.a(this.q, 2);
  }

  public void t()
    throws cn
  {
    if (this.a == null)
      throw new dh("Required field 'name' was not present! Struct: " + toString());
    if (this.b == null)
      throw new dh("Required field 'properties' was not present! Struct: " + toString());
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("Event(");
    localStringBuilder.append("name:");
    if (this.a == null)
    {
      localStringBuilder.append("null");
      localStringBuilder.append(", ");
      localStringBuilder.append("properties:");
      if (this.b != null)
        break label179;
      localStringBuilder.append("null");
    }
    while (true)
    {
      if (m())
      {
        localStringBuilder.append(", ");
        localStringBuilder.append("duration:");
        localStringBuilder.append(this.c);
      }
      if (p())
      {
        localStringBuilder.append(", ");
        localStringBuilder.append("acc:");
        localStringBuilder.append(this.d);
      }
      localStringBuilder.append(", ");
      localStringBuilder.append("ts:");
      localStringBuilder.append(this.e);
      localStringBuilder.append(")");
      return localStringBuilder.toString();
      localStringBuilder.append(this.a);
      break;
      label179: localStringBuilder.append(this.b);
    }
  }

  private static class a extends dq<av>
  {
    public void a(dg paramdg, av paramav)
      throws cn
    {
      paramdg.j();
      db localdb = paramdg.l();
      if (localdb.b == 0)
      {
        paramdg.k();
        if (!paramav.s())
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
        case 4:
        case 5:
        }
        while (true)
        {
          paramdg.m();
          break;
          if (localdb.b == 11)
          {
            paramav.a = paramdg.z();
            paramav.a(true);
          }
          else
          {
            dj.a(paramdg, localdb.b);
            continue;
            if (localdb.b == 13)
            {
              dd localdd = paramdg.n();
              paramav.b = new HashMap(2 * localdd.c);
              for (int i = 0; i < localdd.c; i++)
              {
                String str = paramdg.z();
                bh localbh = new bh();
                localbh.a(paramdg);
                paramav.b.put(str, localbh);
              }
              paramdg.o();
              paramav.b(true);
            }
            else
            {
              dj.a(paramdg, localdb.b);
              continue;
              if (localdb.b == 10)
              {
                paramav.c = paramdg.x();
                paramav.c(true);
              }
              else
              {
                dj.a(paramdg, localdb.b);
                continue;
                if (localdb.b == 8)
                {
                  paramav.d = paramdg.w();
                  paramav.d(true);
                }
                else
                {
                  dj.a(paramdg, localdb.b);
                  continue;
                  if (localdb.b == 10)
                  {
                    paramav.e = paramdg.x();
                    paramav.e(true);
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
      }
      paramav.t();
    }

    public void b(dg paramdg, av paramav)
      throws cn
    {
      paramav.t();
      paramdg.a(av.u());
      if (paramav.a != null)
      {
        paramdg.a(av.v());
        paramdg.a(paramav.a);
        paramdg.c();
      }
      if (paramav.b != null)
      {
        paramdg.a(av.w());
        paramdg.a(new dd((byte)11, (byte)12, paramav.b.size()));
        Iterator localIterator = paramav.b.entrySet().iterator();
        while (localIterator.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)localIterator.next();
          paramdg.a((String)localEntry.getKey());
          ((bh)localEntry.getValue()).b(paramdg);
        }
        paramdg.e();
        paramdg.c();
      }
      if (paramav.m())
      {
        paramdg.a(av.x());
        paramdg.a(paramav.c);
        paramdg.c();
      }
      if (paramav.p())
      {
        paramdg.a(av.y());
        paramdg.a(paramav.d);
        paramdg.c();
      }
      paramdg.a(av.z());
      paramdg.a(paramav.e);
      paramdg.c();
      paramdg.d();
      paramdg.b();
    }
  }

  private static class b
    implements dp
  {
    public av.a a()
    {
      return new av.a(null);
    }
  }

  private static class c extends dr<av>
  {
    public void a(dg paramdg, av paramav)
      throws cn
    {
      dm localdm = (dm)paramdg;
      localdm.a(paramav.a);
      localdm.a(paramav.b.size());
      Iterator localIterator = paramav.b.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        localdm.a((String)localEntry.getKey());
        ((bh)localEntry.getValue()).b(localdm);
      }
      localdm.a(paramav.e);
      BitSet localBitSet = new BitSet();
      if (paramav.m())
        localBitSet.set(0);
      if (paramav.p())
        localBitSet.set(1);
      localdm.a(localBitSet, 2);
      if (paramav.m())
        localdm.a(paramav.c);
      if (paramav.p())
        localdm.a(paramav.d);
    }

    public void b(dg paramdg, av paramav)
      throws cn
    {
      dm localdm = (dm)paramdg;
      paramav.a = localdm.z();
      paramav.a(true);
      dd localdd = new dd((byte)11, (byte)12, localdm.w());
      paramav.b = new HashMap(2 * localdd.c);
      for (int i = 0; i < localdd.c; i++)
      {
        String str = localdm.z();
        bh localbh = new bh();
        localbh.a(localdm);
        paramav.b.put(str, localbh);
      }
      paramav.b(true);
      paramav.e = localdm.x();
      paramav.e(true);
      BitSet localBitSet = localdm.b(2);
      if (localBitSet.get(0))
      {
        paramav.c = localdm.x();
        paramav.c(true);
      }
      if (localBitSet.get(1))
      {
        paramav.d = localdm.w();
        paramav.d(true);
      }
    }
  }

  private static class d
    implements dp
  {
    public av.c a()
    {
      return new av.c(null);
    }
  }

  public static enum e
    implements co
  {
    private static final Map<String, e> f;
    private final short g;
    private final String h;

    static
    {
      e[] arrayOfe = new e[5];
      arrayOfe[0] = a;
      arrayOfe[1] = b;
      arrayOfe[2] = c;
      arrayOfe[3] = d;
      arrayOfe[4] = e;
      i = arrayOfe;
      f = new HashMap();
      Iterator localIterator = EnumSet.allOf(e.class).iterator();
      while (localIterator.hasNext())
      {
        e locale = (e)localIterator.next();
        f.put(locale.b(), locale);
      }
    }

    private e(short paramShort, String paramString)
    {
      this.g = paramShort;
      this.h = paramString;
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
        return d;
      case 5:
      }
      return e;
    }

    public static e a(String paramString)
    {
      return (e)f.get(paramString);
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
      return this.g;
    }

    public String b()
    {
      return this.h;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.av
 * JD-Core Version:    0.6.2
 */