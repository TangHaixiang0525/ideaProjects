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
import java.util.Map.Entry;
import java.util.Set;

public class ba
  implements Serializable, Cloneable, ch<ba, e>
{
  public static final Map<e, ct> d;
  private static final dl e = new dl("Imprint");
  private static final db f = new db("property", (byte)13, (short)1);
  private static final db g = new db("version", (byte)8, (short)2);
  private static final db h = new db("checksum", (byte)11, (short)3);
  private static final Map<Class<? extends do>, dp> i = new HashMap();
  private static final int j;
  public Map<String, bb> a;
  public int b;
  public String c;
  private byte k = 0;

  static
  {
    i.put(dq.class, new b(null));
    i.put(dr.class, new d(null));
    EnumMap localEnumMap = new EnumMap(e.class);
    localEnumMap.put(e.a, new ct("property", (byte)1, new cw((byte)13, new cu((byte)11), new cy((byte)12, bb.class))));
    localEnumMap.put(e.b, new ct("version", (byte)1, new cu((byte)8)));
    localEnumMap.put(e.c, new ct("checksum", (byte)1, new cu((byte)11)));
    d = Collections.unmodifiableMap(localEnumMap);
    ct.a(ba.class, d);
  }

  public ba()
  {
  }

  public ba(Map<String, bb> paramMap, int paramInt, String paramString)
  {
    this();
    this.a = paramMap;
    this.b = paramInt;
    b(true);
    this.c = paramString;
  }

  public ba(ba paramba)
  {
    this.k = paramba.k;
    if (paramba.f())
    {
      HashMap localHashMap = new HashMap();
      Iterator localIterator = paramba.a.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        localHashMap.put((String)localEntry.getKey(), new bb((bb)localEntry.getValue()));
      }
      this.a = localHashMap;
    }
    this.b = paramba.b;
    if (paramba.m())
      this.c = paramba.c;
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

  public ba a()
  {
    return new ba(this);
  }

  public ba a(int paramInt)
  {
    this.b = paramInt;
    b(true);
    return this;
  }

  public ba a(String paramString)
  {
    this.c = paramString;
    return this;
  }

  public ba a(Map<String, bb> paramMap)
  {
    this.a = paramMap;
    return this;
  }

  public void a(String paramString, bb parambb)
  {
    if (this.a == null)
      this.a = new HashMap();
    this.a.put(paramString, parambb);
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
    this.b = 0;
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

  public int c()
  {
    if (this.a == null)
      return 0;
    return this.a.size();
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

  public Map<String, bb> d()
  {
    return this.a;
  }

  public void e()
  {
    this.a = null;
  }

  public boolean f()
  {
    return this.a != null;
  }

  public int h()
  {
    return this.b;
  }

  public void i()
  {
    this.k = ce.b(this.k, 0);
  }

  public boolean j()
  {
    return ce.a(this.k, 0);
  }

  public String k()
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

  public void n()
    throws cn
  {
    if (this.a == null)
      throw new dh("Required field 'property' was not present! Struct: " + toString());
    if (this.c == null)
      throw new dh("Required field 'checksum' was not present! Struct: " + toString());
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("Imprint(");
    localStringBuilder.append("property:");
    if (this.a == null)
    {
      localStringBuilder.append("null");
      localStringBuilder.append(", ");
      localStringBuilder.append("version:");
      localStringBuilder.append(this.b);
      localStringBuilder.append(", ");
      localStringBuilder.append("checksum:");
      if (this.c != null)
        break label115;
      localStringBuilder.append("null");
    }
    while (true)
    {
      localStringBuilder.append(")");
      return localStringBuilder.toString();
      localStringBuilder.append(this.a);
      break;
      label115: localStringBuilder.append(this.c);
    }
  }

  private static class a extends dq<ba>
  {
    public void a(dg paramdg, ba paramba)
      throws cn
    {
      paramdg.j();
      db localdb = paramdg.l();
      if (localdb.b == 0)
      {
        paramdg.k();
        if (!paramba.j())
          throw new dh("Required field 'version' was not found in serialized data! Struct: " + toString());
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
          if (localdb.b == 13)
          {
            dd localdd = paramdg.n();
            paramba.a = new HashMap(2 * localdd.c);
            for (int i = 0; i < localdd.c; i++)
            {
              String str = paramdg.z();
              bb localbb = new bb();
              localbb.a(paramdg);
              paramba.a.put(str, localbb);
            }
            paramdg.o();
            paramba.a(true);
          }
          else
          {
            dj.a(paramdg, localdb.b);
            continue;
            if (localdb.b == 8)
            {
              paramba.b = paramdg.w();
              paramba.b(true);
            }
            else
            {
              dj.a(paramdg, localdb.b);
              continue;
              if (localdb.b == 11)
              {
                paramba.c = paramdg.z();
                paramba.c(true);
              }
              else
              {
                dj.a(paramdg, localdb.b);
              }
            }
          }
        }
      }
      paramba.n();
    }

    public void b(dg paramdg, ba paramba)
      throws cn
    {
      paramba.n();
      paramdg.a(ba.o());
      if (paramba.a != null)
      {
        paramdg.a(ba.p());
        paramdg.a(new dd((byte)11, (byte)12, paramba.a.size()));
        Iterator localIterator = paramba.a.entrySet().iterator();
        while (localIterator.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)localIterator.next();
          paramdg.a((String)localEntry.getKey());
          ((bb)localEntry.getValue()).b(paramdg);
        }
        paramdg.e();
        paramdg.c();
      }
      paramdg.a(ba.q());
      paramdg.a(paramba.b);
      paramdg.c();
      if (paramba.c != null)
      {
        paramdg.a(ba.r());
        paramdg.a(paramba.c);
        paramdg.c();
      }
      paramdg.d();
      paramdg.b();
    }
  }

  private static class b
    implements dp
  {
    public ba.a a()
    {
      return new ba.a(null);
    }
  }

  private static class c extends dr<ba>
  {
    public void a(dg paramdg, ba paramba)
      throws cn
    {
      dm localdm = (dm)paramdg;
      localdm.a(paramba.a.size());
      Iterator localIterator = paramba.a.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        localdm.a((String)localEntry.getKey());
        ((bb)localEntry.getValue()).b(localdm);
      }
      localdm.a(paramba.b);
      localdm.a(paramba.c);
    }

    public void b(dg paramdg, ba paramba)
      throws cn
    {
      dm localdm = (dm)paramdg;
      dd localdd = new dd((byte)11, (byte)12, localdm.w());
      paramba.a = new HashMap(2 * localdd.c);
      for (int i = 0; i < localdd.c; i++)
      {
        String str = localdm.z();
        bb localbb = new bb();
        localbb.a(localdm);
        paramba.a.put(str, localbb);
      }
      paramba.a(true);
      paramba.b = localdm.w();
      paramba.b(true);
      paramba.c = localdm.z();
      paramba.c(true);
    }
  }

  private static class d
    implements dp
  {
    public ba.c a()
    {
      return new ba.c(null);
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
 * Qualified Name:     u.aly.ba
 * JD-Core Version:    0.6.2
 */