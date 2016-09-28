package u.aly;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public abstract class cr<T extends cr<?, ?>, F extends co>
  implements ch<T, F>
{
  private static final Map<Class<? extends do>, dp> a = new HashMap();
  protected Object b;
  protected F c;

  static
  {
    a.put(dq.class, new b(null));
    a.put(dr.class, new d(null));
  }

  protected cr()
  {
    this.c = null;
    this.b = null;
  }

  protected cr(F paramF, Object paramObject)
  {
    b(paramF, paramObject);
  }

  protected cr(cr<T, F> paramcr)
  {
    if (!paramcr.getClass().equals(getClass()))
      throw new ClassCastException();
    this.c = paramcr.c;
    this.b = a(paramcr.b);
  }

  private static Object a(Object paramObject)
  {
    if ((paramObject instanceof ch))
      paramObject = ((ch)paramObject).g();
    do
    {
      return paramObject;
      if ((paramObject instanceof ByteBuffer))
        return ci.d((ByteBuffer)paramObject);
      if ((paramObject instanceof List))
        return a((List)paramObject);
      if ((paramObject instanceof Set))
        return a((Set)paramObject);
    }
    while (!(paramObject instanceof Map));
    return a((Map)paramObject);
  }

  private static List a(List paramList)
  {
    ArrayList localArrayList = new ArrayList(paramList.size());
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
      localArrayList.add(a(localIterator.next()));
    return localArrayList;
  }

  private static Map a(Map<Object, Object> paramMap)
  {
    HashMap localHashMap = new HashMap();
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localHashMap.put(a(localEntry.getKey()), a(localEntry.getValue()));
    }
    return localHashMap;
  }

  private static Set a(Set paramSet)
  {
    HashSet localHashSet = new HashSet();
    Iterator localIterator = paramSet.iterator();
    while (localIterator.hasNext())
      localHashSet.add(a(localIterator.next()));
    return localHashSet;
  }

  protected abstract Object a(dg paramdg, db paramdb)
    throws cn;

  protected abstract Object a(dg paramdg, short paramShort)
    throws cn;

  protected abstract db a(F paramF);

  public void a(int paramInt, Object paramObject)
  {
    b(b((short)paramInt), paramObject);
  }

  protected abstract void a(F paramF, Object paramObject)
    throws ClassCastException;

  public void a(dg paramdg)
    throws cn
  {
    ((dp)a.get(paramdg.D())).b().b(paramdg, this);
  }

  public Object b(F paramF)
  {
    if (paramF != this.c)
      throw new IllegalArgumentException("Cannot get the value of field " + paramF + " because union's set field is " + this.c);
    return j();
  }

  protected abstract F b(short paramShort);

  public final void b()
  {
    this.c = null;
    this.b = null;
  }

  public void b(F paramF, Object paramObject)
  {
    a(paramF, paramObject);
    this.c = paramF;
    this.b = paramObject;
  }

  public void b(dg paramdg)
    throws cn
  {
    ((dp)a.get(paramdg.D())).b().a(paramdg, this);
  }

  public Object c(int paramInt)
  {
    return b(b((short)paramInt));
  }

  protected abstract dl c();

  protected abstract void c(dg paramdg)
    throws cn;

  public boolean c(F paramF)
  {
    return this.c == paramF;
  }

  protected abstract void d(dg paramdg)
    throws cn;

  public boolean d(int paramInt)
  {
    return c(b((short)paramInt));
  }

  public F i()
  {
    return this.c;
  }

  public Object j()
  {
    return this.b;
  }

  public boolean k()
  {
    return this.c != null;
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("<");
    localStringBuilder.append(getClass().getSimpleName());
    localStringBuilder.append(" ");
    Object localObject;
    if (i() != null)
    {
      localObject = j();
      localStringBuilder.append(a(i()).a);
      localStringBuilder.append(":");
      if (!(localObject instanceof ByteBuffer))
        break label99;
      ci.a((ByteBuffer)localObject, localStringBuilder);
    }
    while (true)
    {
      localStringBuilder.append(">");
      return localStringBuilder.toString();
      label99: localStringBuilder.append(localObject.toString());
    }
  }

  private static class a extends dq<cr>
  {
    public void a(dg paramdg, cr paramcr)
      throws cn
    {
      paramcr.c = null;
      paramcr.b = null;
      paramdg.j();
      db localdb = paramdg.l();
      paramcr.b = paramcr.a(paramdg, localdb);
      if (paramcr.b != null)
        paramcr.c = paramcr.b(localdb.c);
      paramdg.m();
      paramdg.l();
      paramdg.k();
    }

    public void b(dg paramdg, cr paramcr)
      throws cn
    {
      if ((paramcr.i() == null) || (paramcr.j() == null))
        throw new dh("Cannot write a TUnion with no set value!");
      paramdg.a(paramcr.c());
      paramdg.a(paramcr.a(paramcr.c));
      paramcr.c(paramdg);
      paramdg.c();
      paramdg.d();
      paramdg.b();
    }
  }

  private static class b
    implements dp
  {
    public cr.a a()
    {
      return new cr.a(null);
    }
  }

  private static class c extends dr<cr>
  {
    public void a(dg paramdg, cr paramcr)
      throws cn
    {
      paramcr.c = null;
      paramcr.b = null;
      short s = paramdg.v();
      paramcr.b = paramcr.a(paramdg, s);
      if (paramcr.b != null)
        paramcr.c = paramcr.b(s);
    }

    public void b(dg paramdg, cr paramcr)
      throws cn
    {
      if ((paramcr.i() == null) || (paramcr.j() == null))
        throw new dh("Cannot write a TUnion with no set value!");
      paramdg.a(paramcr.c.a());
      paramcr.d(paramdg);
    }
  }

  private static class d
    implements dp
  {
    public cr.c a()
    {
      return new cr.c(null);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.cr
 * JD-Core Version:    0.6.2
 */