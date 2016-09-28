package u.aly;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class az
  implements Serializable, Cloneable, ch<az, e>
{
  public static final Map<e, ct> d;
  private static final dl e = new dl("IdTracking");
  private static final db f = new db("snapshots", (byte)13, (short)1);
  private static final db g = new db("journals", (byte)15, (short)2);
  private static final db h = new db("checksum", (byte)11, (short)3);
  private static final Map<Class<? extends do>, dp> i = new HashMap();
  public Map<String, ay> a;
  public List<ax> b;
  public String c;
  private e[] j;

  static
  {
    i.put(dq.class, new b(null));
    i.put(dr.class, new d(null));
    EnumMap localEnumMap = new EnumMap(e.class);
    localEnumMap.put(e.a, new ct("snapshots", (byte)1, new cw((byte)13, new cu((byte)11), new cy((byte)12, ay.class))));
    localEnumMap.put(e.b, new ct("journals", (byte)2, new cv((byte)15, new cy((byte)12, ax.class))));
    localEnumMap.put(e.c, new ct("checksum", (byte)2, new cu((byte)11)));
    d = Collections.unmodifiableMap(localEnumMap);
    ct.a(az.class, d);
  }

  public az()
  {
    e[] arrayOfe = new e[2];
    arrayOfe[0] = e.b;
    arrayOfe[1] = e.c;
    this.j = arrayOfe;
  }

  public az(Map<String, ay> paramMap)
  {
    this();
    this.a = paramMap;
  }

  public az(az paramaz)
  {
    e[] arrayOfe = new e[2];
    arrayOfe[0] = e.b;
    arrayOfe[1] = e.c;
    this.j = arrayOfe;
    if (paramaz.f())
    {
      HashMap localHashMap = new HashMap();
      Iterator localIterator1 = paramaz.a.entrySet().iterator();
      while (localIterator1.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator1.next();
        localHashMap.put((String)localEntry.getKey(), new ay((ay)localEntry.getValue()));
      }
      this.a = localHashMap;
    }
    if (paramaz.l())
    {
      ArrayList localArrayList = new ArrayList();
      Iterator localIterator2 = paramaz.b.iterator();
      while (localIterator2.hasNext())
        localArrayList.add(new ax((ax)localIterator2.next()));
      this.b = localArrayList;
    }
    if (paramaz.o())
      this.c = paramaz.c;
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

  public az a()
  {
    return new az(this);
  }

  public az a(String paramString)
  {
    this.c = paramString;
    return this;
  }

  public az a(List<ax> paramList)
  {
    this.b = paramList;
    return this;
  }

  public az a(Map<String, ay> paramMap)
  {
    this.a = paramMap;
    return this;
  }

  public void a(String paramString, ay paramay)
  {
    if (this.a == null)
      this.a = new HashMap();
    this.a.put(paramString, paramay);
  }

  public void a(ax paramax)
  {
    if (this.b == null)
      this.b = new ArrayList();
    this.b.add(paramax);
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
    if (this.a == null)
      return 0;
    return this.a.size();
  }

  public void c(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.c = null;
  }

  public Map<String, ay> d()
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
    if (this.b == null)
      return 0;
    return this.b.size();
  }

  public Iterator<ax> i()
  {
    if (this.b == null)
      return null;
    return this.b.iterator();
  }

  public List<ax> j()
  {
    return this.b;
  }

  public void k()
  {
    this.b = null;
  }

  public boolean l()
  {
    return this.b != null;
  }

  public String m()
  {
    return this.c;
  }

  public void n()
  {
    this.c = null;
  }

  public boolean o()
  {
    return this.c != null;
  }

  public void p()
    throws cn
  {
    if (this.a == null)
      throw new dh("Required field 'snapshots' was not present! Struct: " + toString());
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("IdTracking(");
    localStringBuilder.append("snapshots:");
    if (this.a == null)
    {
      localStringBuilder.append("null");
      if (l())
      {
        localStringBuilder.append(", ");
        localStringBuilder.append("journals:");
        if (this.b != null)
          break label135;
        localStringBuilder.append("null");
      }
      label72: if (o())
      {
        localStringBuilder.append(", ");
        localStringBuilder.append("checksum:");
        if (this.c != null)
          break label147;
        localStringBuilder.append("null");
      }
    }
    while (true)
    {
      localStringBuilder.append(")");
      return localStringBuilder.toString();
      localStringBuilder.append(this.a);
      break;
      label135: localStringBuilder.append(this.b);
      break label72;
      label147: localStringBuilder.append(this.c);
    }
  }

  private static class a extends dq<az>
  {
    public void a(dg paramdg, az paramaz)
      throws cn
    {
      paramdg.j();
      db localdb = paramdg.l();
      if (localdb.b == 0)
      {
        paramdg.k();
        paramaz.p();
        return;
      }
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
          paramaz.a = new HashMap(2 * localdd.c);
          for (int j = 0; j < localdd.c; j++)
          {
            String str = paramdg.z();
            ay localay = new ay();
            localay.a(paramdg);
            paramaz.a.put(str, localay);
          }
          paramdg.o();
          paramaz.a(true);
        }
        else
        {
          dj.a(paramdg, localdb.b);
          continue;
          if (localdb.b == 15)
          {
            dc localdc = paramdg.p();
            paramaz.b = new ArrayList(localdc.b);
            for (int i = 0; i < localdc.b; i++)
            {
              ax localax = new ax();
              localax.a(paramdg);
              paramaz.b.add(localax);
            }
            paramdg.q();
            paramaz.b(true);
          }
          else
          {
            dj.a(paramdg, localdb.b);
            continue;
            if (localdb.b == 11)
            {
              paramaz.c = paramdg.z();
              paramaz.c(true);
            }
            else
            {
              dj.a(paramdg, localdb.b);
            }
          }
        }
      }
    }

    public void b(dg paramdg, az paramaz)
      throws cn
    {
      paramaz.p();
      paramdg.a(az.q());
      if (paramaz.a != null)
      {
        paramdg.a(az.r());
        paramdg.a(new dd((byte)11, (byte)12, paramaz.a.size()));
        Iterator localIterator2 = paramaz.a.entrySet().iterator();
        while (localIterator2.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)localIterator2.next();
          paramdg.a((String)localEntry.getKey());
          ((ay)localEntry.getValue()).b(paramdg);
        }
        paramdg.e();
        paramdg.c();
      }
      if ((paramaz.b != null) && (paramaz.l()))
      {
        paramdg.a(az.s());
        paramdg.a(new dc((byte)12, paramaz.b.size()));
        Iterator localIterator1 = paramaz.b.iterator();
        while (localIterator1.hasNext())
          ((ax)localIterator1.next()).b(paramdg);
        paramdg.f();
        paramdg.c();
      }
      if ((paramaz.c != null) && (paramaz.o()))
      {
        paramdg.a(az.t());
        paramdg.a(paramaz.c);
        paramdg.c();
      }
      paramdg.d();
      paramdg.b();
    }
  }

  private static class b
    implements dp
  {
    public az.a a()
    {
      return new az.a(null);
    }
  }

  private static class c extends dr<az>
  {
    public void a(dg paramdg, az paramaz)
      throws cn
    {
      dm localdm = (dm)paramdg;
      localdm.a(paramaz.a.size());
      Iterator localIterator1 = paramaz.a.entrySet().iterator();
      while (localIterator1.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator1.next();
        localdm.a((String)localEntry.getKey());
        ((ay)localEntry.getValue()).b(localdm);
      }
      BitSet localBitSet = new BitSet();
      if (paramaz.l())
        localBitSet.set(0);
      if (paramaz.o())
        localBitSet.set(1);
      localdm.a(localBitSet, 2);
      if (paramaz.l())
      {
        localdm.a(paramaz.b.size());
        Iterator localIterator2 = paramaz.b.iterator();
        while (localIterator2.hasNext())
          ((ax)localIterator2.next()).b(localdm);
      }
      if (paramaz.o())
        localdm.a(paramaz.c);
    }

    public void b(dg paramdg, az paramaz)
      throws cn
    {
      int i = 0;
      dm localdm = (dm)paramdg;
      dd localdd = new dd((byte)11, (byte)12, localdm.w());
      paramaz.a = new HashMap(2 * localdd.c);
      for (int j = 0; j < localdd.c; j++)
      {
        String str = localdm.z();
        ay localay = new ay();
        localay.a(localdm);
        paramaz.a.put(str, localay);
      }
      paramaz.a(true);
      BitSet localBitSet = localdm.b(2);
      if (localBitSet.get(0))
      {
        dc localdc = new dc((byte)12, localdm.w());
        paramaz.b = new ArrayList(localdc.b);
        while (i < localdc.b)
        {
          ax localax = new ax();
          localax.a(localdm);
          paramaz.b.add(localax);
          i++;
        }
        paramaz.b(true);
      }
      if (localBitSet.get(1))
      {
        paramaz.c = localdm.z();
        paramaz.c(true);
      }
    }
  }

  private static class d
    implements dp
  {
    public az.c a()
    {
      return new az.c(null);
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
 * Qualified Name:     u.aly.az
 * JD-Core Version:    0.6.2
 */