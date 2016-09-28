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

public class as
  implements Serializable, Cloneable, ch<as, e>
{
  public static final Map<e, ct> f;
  private static final dl g = new dl("Ekv");
  private static final db h = new db("ts", (byte)10, (short)1);
  private static final db i = new db("name", (byte)11, (short)2);
  private static final db j = new db("ckv", (byte)13, (short)3);
  private static final db k = new db("duration", (byte)10, (short)4);
  private static final db l = new db("acc", (byte)8, (short)5);
  private static final Map<Class<? extends do>, dp> m = new HashMap();
  private static final int n = 0;
  private static final int o = 1;
  private static final int p = 2;
  public long a;
  public String b;
  public Map<String, String> c;
  public long d;
  public int e;
  private byte q = 0;
  private e[] r;

  static
  {
    m.put(dq.class, new b(null));
    m.put(dr.class, new d(null));
    EnumMap localEnumMap = new EnumMap(e.class);
    localEnumMap.put(e.a, new ct("ts", (byte)1, new cu((byte)10)));
    localEnumMap.put(e.b, new ct("name", (byte)1, new cu((byte)11)));
    localEnumMap.put(e.c, new ct("ckv", (byte)1, new cw((byte)13, new cu((byte)11), new cu((byte)11))));
    localEnumMap.put(e.d, new ct("duration", (byte)2, new cu((byte)10)));
    localEnumMap.put(e.e, new ct("acc", (byte)2, new cu((byte)8)));
    f = Collections.unmodifiableMap(localEnumMap);
    ct.a(as.class, f);
  }

  public as()
  {
    e[] arrayOfe = new e[2];
    arrayOfe[0] = e.d;
    arrayOfe[1] = e.e;
    this.r = arrayOfe;
  }

  public as(long paramLong, String paramString, Map<String, String> paramMap)
  {
    this();
    this.a = paramLong;
    a(true);
    this.b = paramString;
    this.c = paramMap;
  }

  public as(as paramas)
  {
    e[] arrayOfe = new e[2];
    arrayOfe[0] = e.d;
    arrayOfe[1] = e.e;
    this.r = arrayOfe;
    this.q = paramas.q;
    this.a = paramas.a;
    if (paramas.i())
      this.b = paramas.b;
    if (paramas.m())
    {
      HashMap localHashMap = new HashMap();
      Iterator localIterator = paramas.c.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        localHashMap.put((String)localEntry.getKey(), (String)localEntry.getValue());
      }
      this.c = localHashMap;
    }
    this.d = paramas.d;
    this.e = paramas.e;
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

  public as a()
  {
    return new as(this);
  }

  public as a(int paramInt)
  {
    this.e = paramInt;
    e(true);
    return this;
  }

  public as a(long paramLong)
  {
    this.a = paramLong;
    a(true);
    return this;
  }

  public as a(String paramString)
  {
    this.b = paramString;
    return this;
  }

  public as a(Map<String, String> paramMap)
  {
    this.c = paramMap;
    return this;
  }

  public void a(String paramString1, String paramString2)
  {
    if (this.c == null)
      this.c = new HashMap();
    this.c.put(paramString1, paramString2);
  }

  public void a(dg paramdg)
    throws cn
  {
    ((dp)m.get(paramdg.D())).b().b(paramdg, this);
  }

  public void a(boolean paramBoolean)
  {
    this.q = ce.a(this.q, 0, paramBoolean);
  }

  public as b(long paramLong)
  {
    this.d = paramLong;
    d(true);
    return this;
  }

  public void b()
  {
    a(false);
    this.a = 0L;
    this.b = null;
    this.c = null;
    d(false);
    this.d = 0L;
    e(false);
    this.e = 0;
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

  public long c()
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
    this.q = ce.b(this.q, 0);
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
    return ce.a(this.q, 0);
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

  public int j()
  {
    if (this.c == null)
      return 0;
    return this.c.size();
  }

  public Map<String, String> k()
  {
    return this.c;
  }

  public void l()
  {
    this.c = null;
  }

  public boolean m()
  {
    return this.c != null;
  }

  public long n()
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

  public int q()
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
    if (this.b == null)
      throw new dh("Required field 'name' was not present! Struct: " + toString());
    if (this.c == null)
      throw new dh("Required field 'ckv' was not present! Struct: " + toString());
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("Ekv(");
    localStringBuilder.append("ts:");
    localStringBuilder.append(this.a);
    localStringBuilder.append(", ");
    localStringBuilder.append("name:");
    if (this.b == null)
    {
      localStringBuilder.append("null");
      localStringBuilder.append(", ");
      localStringBuilder.append("ckv:");
      if (this.c != null)
        break label179;
      localStringBuilder.append("null");
    }
    while (true)
    {
      if (p())
      {
        localStringBuilder.append(", ");
        localStringBuilder.append("duration:");
        localStringBuilder.append(this.d);
      }
      if (s())
      {
        localStringBuilder.append(", ");
        localStringBuilder.append("acc:");
        localStringBuilder.append(this.e);
      }
      localStringBuilder.append(")");
      return localStringBuilder.toString();
      localStringBuilder.append(this.b);
      break;
      label179: localStringBuilder.append(this.c);
    }
  }

  private static class a extends dq<as>
  {
    public void a(dg paramdg, as paramas)
      throws cn
    {
      paramdg.j();
      db localdb = paramdg.l();
      if (localdb.b == 0)
      {
        paramdg.k();
        if (!paramas.e())
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
          if (localdb.b == 10)
          {
            paramas.a = paramdg.x();
            paramas.a(true);
          }
          else
          {
            dj.a(paramdg, localdb.b);
            continue;
            if (localdb.b == 11)
            {
              paramas.b = paramdg.z();
              paramas.b(true);
            }
            else
            {
              dj.a(paramdg, localdb.b);
              continue;
              if (localdb.b == 13)
              {
                dd localdd = paramdg.n();
                paramas.c = new HashMap(2 * localdd.c);
                for (int i = 0; i < localdd.c; i++)
                {
                  String str1 = paramdg.z();
                  String str2 = paramdg.z();
                  paramas.c.put(str1, str2);
                }
                paramdg.o();
                paramas.c(true);
              }
              else
              {
                dj.a(paramdg, localdb.b);
                continue;
                if (localdb.b == 10)
                {
                  paramas.d = paramdg.x();
                  paramas.d(true);
                }
                else
                {
                  dj.a(paramdg, localdb.b);
                  continue;
                  if (localdb.b == 8)
                  {
                    paramas.e = paramdg.w();
                    paramas.e(true);
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
      paramas.t();
    }

    public void b(dg paramdg, as paramas)
      throws cn
    {
      paramas.t();
      paramdg.a(as.u());
      paramdg.a(as.v());
      paramdg.a(paramas.a);
      paramdg.c();
      if (paramas.b != null)
      {
        paramdg.a(as.w());
        paramdg.a(paramas.b);
        paramdg.c();
      }
      if (paramas.c != null)
      {
        paramdg.a(as.x());
        paramdg.a(new dd((byte)11, (byte)11, paramas.c.size()));
        Iterator localIterator = paramas.c.entrySet().iterator();
        while (localIterator.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)localIterator.next();
          paramdg.a((String)localEntry.getKey());
          paramdg.a((String)localEntry.getValue());
        }
        paramdg.e();
        paramdg.c();
      }
      if (paramas.p())
      {
        paramdg.a(as.y());
        paramdg.a(paramas.d);
        paramdg.c();
      }
      if (paramas.s())
      {
        paramdg.a(as.z());
        paramdg.a(paramas.e);
        paramdg.c();
      }
      paramdg.d();
      paramdg.b();
    }
  }

  private static class b
    implements dp
  {
    public as.a a()
    {
      return new as.a(null);
    }
  }

  private static class c extends dr<as>
  {
    public void a(dg paramdg, as paramas)
      throws cn
    {
      dm localdm = (dm)paramdg;
      localdm.a(paramas.a);
      localdm.a(paramas.b);
      localdm.a(paramas.c.size());
      Iterator localIterator = paramas.c.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        localdm.a((String)localEntry.getKey());
        localdm.a((String)localEntry.getValue());
      }
      BitSet localBitSet = new BitSet();
      if (paramas.p())
        localBitSet.set(0);
      if (paramas.s())
        localBitSet.set(1);
      localdm.a(localBitSet, 2);
      if (paramas.p())
        localdm.a(paramas.d);
      if (paramas.s())
        localdm.a(paramas.e);
    }

    public void b(dg paramdg, as paramas)
      throws cn
    {
      dm localdm = (dm)paramdg;
      paramas.a = localdm.x();
      paramas.a(true);
      paramas.b = localdm.z();
      paramas.b(true);
      dd localdd = new dd((byte)11, (byte)11, localdm.w());
      paramas.c = new HashMap(2 * localdd.c);
      for (int i = 0; i < localdd.c; i++)
      {
        String str1 = localdm.z();
        String str2 = localdm.z();
        paramas.c.put(str1, str2);
      }
      paramas.c(true);
      BitSet localBitSet = localdm.b(2);
      if (localBitSet.get(0))
      {
        paramas.d = localdm.x();
        paramas.d(true);
      }
      if (localBitSet.get(1))
      {
        paramas.e = localdm.w();
        paramas.e(true);
      }
    }
  }

  private static class d
    implements dp
  {
    public as.c a()
    {
      return new as.c(null);
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
 * Qualified Name:     u.aly.as
 * JD-Core Version:    0.6.2
 */