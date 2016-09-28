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

public class an
  implements Serializable, Cloneable, ch<an, e>
{
  public static final Map<e, ct> c;
  private static final dl d = new dl("ActiveUser");
  private static final db e = new db("provider", (byte)11, (short)1);
  private static final db f = new db("puid", (byte)11, (short)2);
  private static final Map<Class<? extends do>, dp> g = new HashMap();
  public String a;
  public String b;

  static
  {
    g.put(dq.class, new b(null));
    g.put(dr.class, new d(null));
    EnumMap localEnumMap = new EnumMap(e.class);
    localEnumMap.put(e.a, new ct("provider", (byte)1, new cu((byte)11)));
    localEnumMap.put(e.b, new ct("puid", (byte)1, new cu((byte)11)));
    c = Collections.unmodifiableMap(localEnumMap);
    ct.a(an.class, c);
  }

  public an()
  {
  }

  public an(String paramString1, String paramString2)
  {
    this();
    this.a = paramString1;
    this.b = paramString2;
  }

  public an(an paraman)
  {
    if (paraman.e())
      this.a = paraman.a;
    if (paraman.i())
      this.b = paraman.b;
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

  public an a()
  {
    return new an(this);
  }

  public an a(String paramString)
  {
    this.a = paramString;
    return this;
  }

  public void a(dg paramdg)
    throws cn
  {
    ((dp)g.get(paramdg.D())).b().b(paramdg, this);
  }

  public void a(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.a = null;
  }

  public an b(String paramString)
  {
    this.b = paramString;
    return this;
  }

  public void b()
  {
    this.a = null;
    this.b = null;
  }

  public void b(dg paramdg)
    throws cn
  {
    ((dp)g.get(paramdg.D())).b().a(paramdg, this);
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

  public void d()
  {
    this.a = null;
  }

  public boolean e()
  {
    return this.a != null;
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

  public void j()
    throws cn
  {
    if (this.a == null)
      throw new dh("Required field 'provider' was not present! Struct: " + toString());
    if (this.b == null)
      throw new dh("Required field 'puid' was not present! Struct: " + toString());
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("ActiveUser(");
    localStringBuilder.append("provider:");
    if (this.a == null)
    {
      localStringBuilder.append("null");
      localStringBuilder.append(", ");
      localStringBuilder.append("puid:");
      if (this.b != null)
        break label83;
      localStringBuilder.append("null");
    }
    while (true)
    {
      localStringBuilder.append(")");
      return localStringBuilder.toString();
      localStringBuilder.append(this.a);
      break;
      label83: localStringBuilder.append(this.b);
    }
  }

  private static class a extends dq<an>
  {
    public void a(dg paramdg, an paraman)
      throws cn
    {
      paramdg.j();
      db localdb = paramdg.l();
      if (localdb.b == 0)
      {
        paramdg.k();
        paraman.j();
        return;
      }
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
        if (localdb.b == 11)
        {
          paraman.a = paramdg.z();
          paraman.a(true);
        }
        else
        {
          dj.a(paramdg, localdb.b);
          continue;
          if (localdb.b == 11)
          {
            paraman.b = paramdg.z();
            paraman.b(true);
          }
          else
          {
            dj.a(paramdg, localdb.b);
          }
        }
      }
    }

    public void b(dg paramdg, an paraman)
      throws cn
    {
      paraman.j();
      paramdg.a(an.k());
      if (paraman.a != null)
      {
        paramdg.a(an.l());
        paramdg.a(paraman.a);
        paramdg.c();
      }
      if (paraman.b != null)
      {
        paramdg.a(an.m());
        paramdg.a(paraman.b);
        paramdg.c();
      }
      paramdg.d();
      paramdg.b();
    }
  }

  private static class b
    implements dp
  {
    public an.a a()
    {
      return new an.a(null);
    }
  }

  private static class c extends dr<an>
  {
    public void a(dg paramdg, an paraman)
      throws cn
    {
      dm localdm = (dm)paramdg;
      localdm.a(paraman.a);
      localdm.a(paraman.b);
    }

    public void b(dg paramdg, an paraman)
      throws cn
    {
      dm localdm = (dm)paramdg;
      paraman.a = localdm.z();
      paraman.a(true);
      paraman.b = localdm.z();
      paraman.b(true);
    }
  }

  private static class d
    implements dp
  {
    public an.c a()
    {
      return new an.c(null);
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
 * Qualified Name:     u.aly.an
 * JD-Core Version:    0.6.2
 */