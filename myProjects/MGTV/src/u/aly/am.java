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

public class am
  implements Serializable, Cloneable, ch<am, e>
{
  public static final Map<e, ct> b;
  private static final dl c = new dl("ActivateMsg");
  private static final db d = new db("ts", (byte)10, (short)1);
  private static final Map<Class<? extends do>, dp> e = new HashMap();
  private static final int f;
  public long a;
  private byte g = 0;

  static
  {
    e.put(dq.class, new b(null));
    e.put(dr.class, new d(null));
    EnumMap localEnumMap = new EnumMap(e.class);
    localEnumMap.put(e.a, new ct("ts", (byte)1, new cu((byte)10)));
    b = Collections.unmodifiableMap(localEnumMap);
    ct.a(am.class, b);
  }

  public am()
  {
  }

  public am(long paramLong)
  {
    this();
    this.a = paramLong;
    a(true);
  }

  public am(am paramam)
  {
    this.g = paramam.g;
    this.a = paramam.a;
  }

  private void a(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    try
    {
      this.g = 0;
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

  public am a()
  {
    return new am(this);
  }

  public am a(long paramLong)
  {
    this.a = paramLong;
    a(true);
    return this;
  }

  public void a(dg paramdg)
    throws cn
  {
    ((dp)e.get(paramdg.D())).b().b(paramdg, this);
  }

  public void a(boolean paramBoolean)
  {
    this.g = ce.a(this.g, 0, paramBoolean);
  }

  public void b()
  {
    a(false);
    this.a = 0L;
  }

  public void b(dg paramdg)
    throws cn
  {
    ((dp)e.get(paramdg.D())).b().a(paramdg, this);
  }

  public long c()
  {
    return this.a;
  }

  public void d()
  {
    this.g = ce.b(this.g, 0);
  }

  public boolean e()
  {
    return ce.a(this.g, 0);
  }

  public void f()
    throws cn
  {
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("ActivateMsg(");
    localStringBuilder.append("ts:");
    localStringBuilder.append(this.a);
    localStringBuilder.append(")");
    return localStringBuilder.toString();
  }

  private static class a extends dq<am>
  {
    public void a(dg paramdg, am paramam)
      throws cn
    {
      paramdg.j();
      db localdb = paramdg.l();
      if (localdb.b == 0)
      {
        paramdg.k();
        if (!paramam.e())
          throw new dh("Required field 'ts' was not found in serialized data! Struct: " + toString());
      }
      else
      {
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
          if (localdb.b == 10)
          {
            paramam.a = paramdg.x();
            paramam.a(true);
          }
          else
          {
            dj.a(paramdg, localdb.b);
          }
        }
      }
      paramam.f();
    }

    public void b(dg paramdg, am paramam)
      throws cn
    {
      paramam.f();
      paramdg.a(am.h());
      paramdg.a(am.i());
      paramdg.a(paramam.a);
      paramdg.c();
      paramdg.d();
      paramdg.b();
    }
  }

  private static class b
    implements dp
  {
    public am.a a()
    {
      return new am.a(null);
    }
  }

  private static class c extends dr<am>
  {
    public void a(dg paramdg, am paramam)
      throws cn
    {
      ((dm)paramdg).a(paramam.a);
    }

    public void b(dg paramdg, am paramam)
      throws cn
    {
      paramam.a = ((dm)paramdg).x();
      paramam.a(true);
    }
  }

  private static class d
    implements dp
  {
    public am.c a()
    {
      return new am.c(null);
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
 * Qualified Name:     u.aly.am
 * JD-Core Version:    0.6.2
 */