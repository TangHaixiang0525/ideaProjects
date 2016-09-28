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

public class bc
  implements Serializable, Cloneable, ch<bc, e>
{
  public static final Map<e, ct> e;
  private static final dl f = new dl("InstantMsg");
  private static final db g = new db("id", (byte)11, (short)1);
  private static final db h = new db("errors", (byte)15, (short)2);
  private static final db i = new db("events", (byte)15, (short)3);
  private static final db j = new db("game_events", (byte)15, (short)4);
  private static final Map<Class<? extends do>, dp> k = new HashMap();
  public String a;
  public List<at> b;
  public List<av> c;
  public List<av> d;
  private e[] l;

  static
  {
    k.put(dq.class, new b(null));
    k.put(dr.class, new d(null));
    EnumMap localEnumMap = new EnumMap(e.class);
    localEnumMap.put(e.a, new ct("id", (byte)1, new cu((byte)11)));
    localEnumMap.put(e.b, new ct("errors", (byte)2, new cv((byte)15, new cy((byte)12, at.class))));
    localEnumMap.put(e.c, new ct("events", (byte)2, new cv((byte)15, new cy((byte)12, av.class))));
    localEnumMap.put(e.d, new ct("game_events", (byte)2, new cv((byte)15, new cy((byte)12, av.class))));
    e = Collections.unmodifiableMap(localEnumMap);
    ct.a(bc.class, e);
  }

  public bc()
  {
    e[] arrayOfe = new e[3];
    arrayOfe[0] = e.b;
    arrayOfe[1] = e.c;
    arrayOfe[2] = e.d;
    this.l = arrayOfe;
  }

  public bc(String paramString)
  {
    this();
    this.a = paramString;
  }

  public bc(bc parambc)
  {
    e[] arrayOfe = new e[3];
    arrayOfe[0] = e.b;
    arrayOfe[1] = e.c;
    arrayOfe[2] = e.d;
    this.l = arrayOfe;
    if (parambc.e())
      this.a = parambc.a;
    if (parambc.k())
    {
      ArrayList localArrayList1 = new ArrayList();
      Iterator localIterator1 = parambc.b.iterator();
      while (localIterator1.hasNext())
        localArrayList1.add(new at((at)localIterator1.next()));
      this.b = localArrayList1;
    }
    if (parambc.p())
    {
      ArrayList localArrayList2 = new ArrayList();
      Iterator localIterator2 = parambc.c.iterator();
      while (localIterator2.hasNext())
        localArrayList2.add(new av((av)localIterator2.next()));
      this.c = localArrayList2;
    }
    if (parambc.u())
    {
      ArrayList localArrayList3 = new ArrayList();
      Iterator localIterator3 = parambc.d.iterator();
      while (localIterator3.hasNext())
        localArrayList3.add(new av((av)localIterator3.next()));
      this.d = localArrayList3;
    }
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

  public bc a()
  {
    return new bc(this);
  }

  public bc a(String paramString)
  {
    this.a = paramString;
    return this;
  }

  public bc a(List<at> paramList)
  {
    this.b = paramList;
    return this;
  }

  public void a(at paramat)
  {
    if (this.b == null)
      this.b = new ArrayList();
    this.b.add(paramat);
  }

  public void a(av paramav)
  {
    if (this.c == null)
      this.c = new ArrayList();
    this.c.add(paramav);
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

  public bc b(List<av> paramList)
  {
    this.c = paramList;
    return this;
  }

  public void b()
  {
    this.a = null;
    this.b = null;
    this.c = null;
    this.d = null;
  }

  public void b(av paramav)
  {
    if (this.d == null)
      this.d = new ArrayList();
    this.d.add(paramav);
  }

  public void b(dg paramdg)
    throws cn
  {
    ((dp)k.get(paramdg.D())).b().a(paramdg, this);
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

  public bc c(List<av> paramList)
  {
    this.d = paramList;
    return this;
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
    if (this.b == null)
      return 0;
    return this.b.size();
  }

  public Iterator<at> h()
  {
    if (this.b == null)
      return null;
    return this.b.iterator();
  }

  public List<at> i()
  {
    return this.b;
  }

  public void j()
  {
    this.b = null;
  }

  public boolean k()
  {
    return this.b != null;
  }

  public int l()
  {
    if (this.c == null)
      return 0;
    return this.c.size();
  }

  public Iterator<av> m()
  {
    if (this.c == null)
      return null;
    return this.c.iterator();
  }

  public List<av> n()
  {
    return this.c;
  }

  public void o()
  {
    this.c = null;
  }

  public boolean p()
  {
    return this.c != null;
  }

  public int q()
  {
    if (this.d == null)
      return 0;
    return this.d.size();
  }

  public Iterator<av> r()
  {
    if (this.d == null)
      return null;
    return this.d.iterator();
  }

  public List<av> s()
  {
    return this.d;
  }

  public void t()
  {
    this.d = null;
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("InstantMsg(");
    localStringBuilder.append("id:");
    if (this.a == null)
    {
      localStringBuilder.append("null");
      if (k())
      {
        localStringBuilder.append(", ");
        localStringBuilder.append("errors:");
        if (this.b != null)
          break label173;
        localStringBuilder.append("null");
      }
      label72: if (p())
      {
        localStringBuilder.append(", ");
        localStringBuilder.append("events:");
        if (this.c != null)
          break label185;
        localStringBuilder.append("null");
      }
      label110: if (u())
      {
        localStringBuilder.append(", ");
        localStringBuilder.append("game_events:");
        if (this.d != null)
          break label197;
        localStringBuilder.append("null");
      }
    }
    while (true)
    {
      localStringBuilder.append(")");
      return localStringBuilder.toString();
      localStringBuilder.append(this.a);
      break;
      label173: localStringBuilder.append(this.b);
      break label72;
      label185: localStringBuilder.append(this.c);
      break label110;
      label197: localStringBuilder.append(this.d);
    }
  }

  public boolean u()
  {
    return this.d != null;
  }

  public void v()
    throws cn
  {
    if (this.a == null)
      throw new dh("Required field 'id' was not present! Struct: " + toString());
  }

  private static class a extends dq<bc>
  {
    public void a(dg paramdg, bc parambc)
      throws cn
    {
      paramdg.j();
      db localdb = paramdg.l();
      if (localdb.b == 0)
      {
        paramdg.k();
        parambc.v();
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
        if (localdb.b == 11)
        {
          parambc.a = paramdg.z();
          parambc.a(true);
        }
        else
        {
          dj.a(paramdg, localdb.b);
          continue;
          if (localdb.b == 15)
          {
            dc localdc3 = paramdg.p();
            parambc.b = new ArrayList(localdc3.b);
            for (int k = 0; k < localdc3.b; k++)
            {
              at localat = new at();
              localat.a(paramdg);
              parambc.b.add(localat);
            }
            paramdg.q();
            parambc.b(true);
          }
          else
          {
            dj.a(paramdg, localdb.b);
            continue;
            if (localdb.b == 15)
            {
              dc localdc2 = paramdg.p();
              parambc.c = new ArrayList(localdc2.b);
              for (int j = 0; j < localdc2.b; j++)
              {
                av localav2 = new av();
                localav2.a(paramdg);
                parambc.c.add(localav2);
              }
              paramdg.q();
              parambc.c(true);
            }
            else
            {
              dj.a(paramdg, localdb.b);
              continue;
              if (localdb.b == 15)
              {
                dc localdc1 = paramdg.p();
                parambc.d = new ArrayList(localdc1.b);
                for (int i = 0; i < localdc1.b; i++)
                {
                  av localav1 = new av();
                  localav1.a(paramdg);
                  parambc.d.add(localav1);
                }
                paramdg.q();
                parambc.d(true);
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

    public void b(dg paramdg, bc parambc)
      throws cn
    {
      parambc.v();
      paramdg.a(bc.w());
      if (parambc.a != null)
      {
        paramdg.a(bc.x());
        paramdg.a(parambc.a);
        paramdg.c();
      }
      if ((parambc.b != null) && (parambc.k()))
      {
        paramdg.a(bc.y());
        paramdg.a(new dc((byte)12, parambc.b.size()));
        Iterator localIterator3 = parambc.b.iterator();
        while (localIterator3.hasNext())
          ((at)localIterator3.next()).b(paramdg);
        paramdg.f();
        paramdg.c();
      }
      if ((parambc.c != null) && (parambc.p()))
      {
        paramdg.a(bc.z());
        paramdg.a(new dc((byte)12, parambc.c.size()));
        Iterator localIterator2 = parambc.c.iterator();
        while (localIterator2.hasNext())
          ((av)localIterator2.next()).b(paramdg);
        paramdg.f();
        paramdg.c();
      }
      if ((parambc.d != null) && (parambc.u()))
      {
        paramdg.a(bc.A());
        paramdg.a(new dc((byte)12, parambc.d.size()));
        Iterator localIterator1 = parambc.d.iterator();
        while (localIterator1.hasNext())
          ((av)localIterator1.next()).b(paramdg);
        paramdg.f();
        paramdg.c();
      }
      paramdg.d();
      paramdg.b();
    }
  }

  private static class b
    implements dp
  {
    public bc.a a()
    {
      return new bc.a(null);
    }
  }

  private static class c extends dr<bc>
  {
    public void a(dg paramdg, bc parambc)
      throws cn
    {
      dm localdm = (dm)paramdg;
      localdm.a(parambc.a);
      BitSet localBitSet = new BitSet();
      if (parambc.k())
        localBitSet.set(0);
      if (parambc.p())
        localBitSet.set(1);
      if (parambc.u())
        localBitSet.set(2);
      localdm.a(localBitSet, 3);
      if (parambc.k())
      {
        localdm.a(parambc.b.size());
        Iterator localIterator3 = parambc.b.iterator();
        while (localIterator3.hasNext())
          ((at)localIterator3.next()).b(localdm);
      }
      if (parambc.p())
      {
        localdm.a(parambc.c.size());
        Iterator localIterator2 = parambc.c.iterator();
        while (localIterator2.hasNext())
          ((av)localIterator2.next()).b(localdm);
      }
      if (parambc.u())
      {
        localdm.a(parambc.d.size());
        Iterator localIterator1 = parambc.d.iterator();
        while (localIterator1.hasNext())
          ((av)localIterator1.next()).b(localdm);
      }
    }

    public void b(dg paramdg, bc parambc)
      throws cn
    {
      int i = 0;
      dm localdm = (dm)paramdg;
      parambc.a = localdm.z();
      parambc.a(true);
      BitSet localBitSet = localdm.b(3);
      if (localBitSet.get(0))
      {
        dc localdc1 = new dc((byte)12, localdm.w());
        parambc.b = new ArrayList(localdc1.b);
        for (int j = 0; j < localdc1.b; j++)
        {
          at localat = new at();
          localat.a(localdm);
          parambc.b.add(localat);
        }
        parambc.b(true);
      }
      if (localBitSet.get(1))
      {
        dc localdc2 = new dc((byte)12, localdm.w());
        parambc.c = new ArrayList(localdc2.b);
        for (int k = 0; k < localdc2.b; k++)
        {
          av localav1 = new av();
          localav1.a(localdm);
          parambc.c.add(localav1);
        }
        parambc.c(true);
      }
      if (localBitSet.get(2))
      {
        dc localdc3 = new dc((byte)12, localdm.w());
        parambc.d = new ArrayList(localdc3.b);
        while (i < localdc3.b)
        {
          av localav2 = new av();
          localav2.a(localdm);
          parambc.d.add(localav2);
          i++;
        }
        parambc.d(true);
      }
    }
  }

  private static class d
    implements dp
  {
    public bc.c a()
    {
      return new bc.c(null);
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
 * Qualified Name:     u.aly.bc
 * JD-Core Version:    0.6.2
 */