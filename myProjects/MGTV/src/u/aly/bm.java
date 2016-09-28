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

public class bm
  implements Serializable, Cloneable, ch<bm, e>
{
  public static final Map<e, ct> c;
  private static final dl d = new dl("Traffic");
  private static final db e = new db("upload_traffic", (byte)8, (short)1);
  private static final db f = new db("download_traffic", (byte)8, (short)2);
  private static final Map<Class<? extends do>, dp> g = new HashMap();
  private static final int h = 0;
  private static final int i = 1;
  public int a;
  public int b;
  private byte j = 0;

  static
  {
    g.put(dq.class, new b(null));
    g.put(dr.class, new d(null));
    EnumMap localEnumMap = new EnumMap(e.class);
    localEnumMap.put(e.a, new ct("upload_traffic", (byte)1, new cu((byte)8)));
    localEnumMap.put(e.b, new ct("download_traffic", (byte)1, new cu((byte)8)));
    c = Collections.unmodifiableMap(localEnumMap);
    ct.a(bm.class, c);
  }

  public bm()
  {
  }

  public bm(int paramInt1, int paramInt2)
  {
    this();
    this.a = paramInt1;
    a(true);
    this.b = paramInt2;
    b(true);
  }

  public bm(bm parambm)
  {
    this.j = parambm.j;
    this.a = parambm.a;
    this.b = parambm.b;
  }

  private void a(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    try
    {
      this.j = 0;
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

  public bm a()
  {
    return new bm(this);
  }

  public bm a(int paramInt)
  {
    this.a = paramInt;
    a(true);
    return this;
  }

  public void a(dg paramdg)
    throws cn
  {
    ((dp)g.get(paramdg.D())).b().b(paramdg, this);
  }

  public void a(boolean paramBoolean)
  {
    this.j = ce.a(this.j, 0, paramBoolean);
  }

  public void b()
  {
    a(false);
    this.a = 0;
    b(false);
    this.b = 0;
  }

  public void b(dg paramdg)
    throws cn
  {
    ((dp)g.get(paramdg.D())).b().a(paramdg, this);
  }

  public void b(boolean paramBoolean)
  {
    this.j = ce.a(this.j, 1, paramBoolean);
  }

  public int c()
  {
    return this.a;
  }

  public bm c(int paramInt)
  {
    this.b = paramInt;
    b(true);
    return this;
  }

  public e d(int paramInt)
  {
    return e.a(paramInt);
  }

  public void d()
  {
    this.j = ce.b(this.j, 0);
  }

  public boolean e()
  {
    return ce.a(this.j, 0);
  }

  public int f()
  {
    return this.b;
  }

  public void h()
  {
    this.j = ce.b(this.j, 1);
  }

  public boolean i()
  {
    return ce.a(this.j, 1);
  }

  public void j()
    throws cn
  {
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("Traffic(");
    localStringBuilder.append("upload_traffic:");
    localStringBuilder.append(this.a);
    localStringBuilder.append(", ");
    localStringBuilder.append("download_traffic:");
    localStringBuilder.append(this.b);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }

  private static class a extends dq<bm>
  {
    public void a(dg paramdg, bm parambm)
      throws cn
    {
      paramdg.j();
      db localdb = paramdg.l();
      if (localdb.b == 0)
      {
        paramdg.k();
        if (!parambm.e())
          throw new dh("Required field 'upload_traffic' was not found in serialized data! Struct: " + toString());
      }
      else
      {
        switch (localdb.c)
        {
        default:
          dj.a(paramdg, localdb.b);
        case 1:
        case 2:
        }
        while (true)
        {
          paramdg.m();
          break;
          if (localdb.b == 8)
          {
            parambm.a = paramdg.w();
            parambm.a(true);
          }
          else
          {
            dj.a(paramdg, localdb.b);
            continue;
            if (localdb.b == 8)
            {
              parambm.b = paramdg.w();
              parambm.b(true);
            }
            else
            {
              dj.a(paramdg, localdb.b);
            }
          }
        }
      }
      if (!parambm.i())
        throw new dh("Required field 'download_traffic' was not found in serialized data! Struct: " + toString());
      parambm.j();
    }

    public void b(dg paramdg, bm parambm)
      throws cn
    {
      parambm.j();
      paramdg.a(bm.k());
      paramdg.a(bm.l());
      paramdg.a(parambm.a);
      paramdg.c();
      paramdg.a(bm.m());
      paramdg.a(parambm.b);
      paramdg.c();
      paramdg.d();
      paramdg.b();
    }
  }

  private static class b
    implements dp
  {
    public bm.a a()
    {
      return new bm.a(null);
    }
  }

  private static class c extends dr<bm>
  {
    public void a(dg paramdg, bm parambm)
      throws cn
    {
      dm localdm = (dm)paramdg;
      localdm.a(parambm.a);
      localdm.a(parambm.b);
    }

    public void b(dg paramdg, bm parambm)
      throws cn
    {
      dm localdm = (dm)paramdg;
      parambm.a = localdm.w();
      parambm.a(true);
      parambm.b = localdm.w();
      parambm.b(true);
    }
  }

  private static class d
    implements dp
  {
    public bm.c a()
    {
      return new bm.c(null);
    }
  }

  public static enum e
    implements co
  {
    private static final Map<String, e> c;
    private final short d;
    private final String e;

    static
    {
      e[] arrayOfe = new e[2];
      arrayOfe[0] = a;
      arrayOfe[1] = b;
      f = arrayOfe;
      c = new HashMap();
      Iterator localIterator = EnumSet.allOf(e.class).iterator();
      while (localIterator.hasNext())
      {
        e locale = (e)localIterator.next();
        c.put(locale.b(), locale);
      }
    }

    private e(short paramShort, String paramString)
    {
      this.d = paramShort;
      this.e = paramString;
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
      }
      return b;
    }

    public static e a(String paramString)
    {
      return (e)c.get(paramString);
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
      return this.d;
    }

    public String b()
    {
      return this.e;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.bm
 * JD-Core Version:    0.6.2
 */