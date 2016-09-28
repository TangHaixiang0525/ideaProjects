package u.aly;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class bh extends cr<bh, a>
{
  public static final Map<a, ct> a;
  private static final dl d = new dl("PropertyValue");
  private static final db e = new db("string_value", (byte)11, (short)1);
  private static final db f = new db("long_value", (byte)10, (short)2);

  static
  {
    EnumMap localEnumMap = new EnumMap(a.class);
    localEnumMap.put(a.a, new ct("string_value", (byte)3, new cu((byte)11)));
    localEnumMap.put(a.b, new ct("long_value", (byte)3, new cu((byte)10)));
    a = Collections.unmodifiableMap(localEnumMap);
    ct.a(bh.class, a);
  }

  public bh()
  {
  }

  public bh(a parama, Object paramObject)
  {
    super(parama, paramObject);
  }

  public bh(bh parambh)
  {
    super(parambh);
  }

  public static bh a(long paramLong)
  {
    bh localbh = new bh();
    localbh.b(paramLong);
    return localbh;
  }

  public static bh a(String paramString)
  {
    bh localbh = new bh();
    localbh.b(paramString);
    return localbh;
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

  protected Object a(dg paramdg, db paramdb)
    throws cn
  {
    a locala = a.a(paramdb.c);
    String str = null;
    if (locala != null);
    switch (1.a[locala.ordinal()])
    {
    default:
      throw new IllegalStateException("setField wasn't null, but didn't match any of the case statements!");
    case 1:
      if (paramdb.b == e.b)
      {
        str = paramdg.z();
        return str;
      }
      dj.a(paramdg, paramdb.b);
      return null;
    case 2:
    }
    if (paramdb.b == f.b)
      return Long.valueOf(paramdg.x());
    dj.a(paramdg, paramdb.b);
    return null;
  }

  protected Object a(dg paramdg, short paramShort)
    throws cn
  {
    a locala = a.a(paramShort);
    if (locala != null)
    {
      switch (1.a[locala.ordinal()])
      {
      default:
        throw new IllegalStateException("setField wasn't null, but didn't match any of the case statements!");
      case 1:
        return paramdg.z();
      case 2:
      }
      return Long.valueOf(paramdg.x());
    }
    throw new dh("Couldn't find a field with field id " + paramShort);
  }

  public a a(int paramInt)
  {
    return a.a(paramInt);
  }

  protected a a(short paramShort)
  {
    return a.b(paramShort);
  }

  public bh a()
  {
    return new bh(this);
  }

  protected db a(a parama)
  {
    switch (1.a[parama.ordinal()])
    {
    default:
      throw new IllegalArgumentException("Unknown field id " + parama);
    case 1:
      return e;
    case 2:
    }
    return f;
  }

  protected void a(a parama, Object paramObject)
    throws ClassCastException
  {
    switch (1.a[parama.ordinal()])
    {
    default:
      throw new IllegalArgumentException("Unknown field id " + parama);
    case 1:
      if (!(paramObject instanceof String))
        break;
    case 2:
    }
    do
    {
      return;
      throw new ClassCastException("Was expecting value of type String for field 'string_value', but got " + paramObject.getClass().getSimpleName());
    }
    while ((paramObject instanceof Long));
    throw new ClassCastException("Was expecting value of type Long for field 'long_value', but got " + paramObject.getClass().getSimpleName());
  }

  public boolean a(bh parambh)
  {
    return (parambh != null) && (i() == parambh.i()) && (j().equals(parambh.j()));
  }

  public int b(bh parambh)
  {
    int i = ci.a((Comparable)i(), (Comparable)parambh.i());
    if (i == 0)
      i = ci.a(j(), parambh.j());
    return i;
  }

  public void b(long paramLong)
  {
    this.c = a.b;
    this.b = Long.valueOf(paramLong);
  }

  public void b(String paramString)
  {
    if (paramString == null)
      throw new NullPointerException();
    this.c = a.a;
    this.b = paramString;
  }

  protected dl c()
  {
    return d;
  }

  protected void c(dg paramdg)
    throws cn
  {
    switch (1.a[((a)this.c).ordinal()])
    {
    default:
      throw new IllegalStateException("Cannot write union with unknown field " + this.c);
    case 1:
      paramdg.a((String)this.b);
      return;
    case 2:
    }
    paramdg.a(((Long)this.b).longValue());
  }

  public String d()
  {
    if (i() == a.a)
      return (String)j();
    throw new RuntimeException("Cannot get field 'string_value' because union is currently set to " + a((a)i()).a);
  }

  protected void d(dg paramdg)
    throws cn
  {
    switch (1.a[((a)this.c).ordinal()])
    {
    default:
      throw new IllegalStateException("Cannot write union with unknown field " + this.c);
    case 1:
      paramdg.a((String)this.b);
      return;
    case 2:
    }
    paramdg.a(((Long)this.b).longValue());
  }

  public long e()
  {
    if (i() == a.b)
      return ((Long)j()).longValue();
    throw new RuntimeException("Cannot get field 'long_value' because union is currently set to " + a((a)i()).a);
  }

  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof bh))
      return a((bh)paramObject);
    return false;
  }

  public boolean f()
  {
    return this.c == a.a;
  }

  public boolean h()
  {
    return this.c == a.b;
  }

  public int hashCode()
  {
    return 0;
  }

  public static enum a
    implements co
  {
    private static final Map<String, a> c;
    private final short d;
    private final String e;

    static
    {
      a[] arrayOfa = new a[2];
      arrayOfa[0] = a;
      arrayOfa[1] = b;
      f = arrayOfa;
      c = new HashMap();
      Iterator localIterator = EnumSet.allOf(a.class).iterator();
      while (localIterator.hasNext())
      {
        a locala = (a)localIterator.next();
        c.put(locala.b(), locala);
      }
    }

    private a(short paramShort, String paramString)
    {
      this.d = paramShort;
      this.e = paramString;
    }

    public static a a(int paramInt)
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

    public static a a(String paramString)
    {
      return (a)c.get(paramString);
    }

    public static a b(int paramInt)
    {
      a locala = a(paramInt);
      if (locala == null)
        throw new IllegalArgumentException("Field " + paramInt + " doesn't exist!");
      return locala;
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
 * Qualified Name:     u.aly.bh
 * JD-Core Version:    0.6.2
 */