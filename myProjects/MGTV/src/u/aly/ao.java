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

public class ao
  implements Serializable, Cloneable, ch<ao, e>
{
  public static final Map<e, ct> k;
  private static final dl l = new dl("AppInfo");
  private static final db m = new db("key", (byte)11, (short)1);
  private static final db n = new db("version", (byte)11, (short)2);
  private static final db o = new db("version_index", (byte)8, (short)3);
  private static final db p = new db("package_name", (byte)11, (short)4);
  private static final db q = new db("sdk_type", (byte)8, (short)5);
  private static final db r = new db("sdk_version", (byte)11, (short)6);
  private static final db s = new db("channel", (byte)11, (short)7);
  private static final db t = new db("wrapper_type", (byte)11, (short)8);
  private static final db u = new db("wrapper_version", (byte)11, (short)9);
  private static final db v = new db("vertical_type", (byte)8, (short)10);
  private static final Map<Class<? extends do>, dp> w = new HashMap();
  private static final int x = 0;
  private static final int y = 1;
  private e[] A;
  public String a;
  public String b;
  public int c;
  public String d;
  public bk e;
  public String f;
  public String g;
  public String h;
  public String i;
  public int j;
  private byte z = 0;

  static
  {
    w.put(dq.class, new b(null));
    w.put(dr.class, new d(null));
    EnumMap localEnumMap = new EnumMap(e.class);
    localEnumMap.put(e.a, new ct("key", (byte)1, new cu((byte)11)));
    localEnumMap.put(e.b, new ct("version", (byte)2, new cu((byte)11)));
    localEnumMap.put(e.c, new ct("version_index", (byte)2, new cu((byte)8)));
    localEnumMap.put(e.d, new ct("package_name", (byte)2, new cu((byte)11)));
    localEnumMap.put(e.e, new ct("sdk_type", (byte)1, new cs((byte)16, bk.class)));
    localEnumMap.put(e.f, new ct("sdk_version", (byte)1, new cu((byte)11)));
    localEnumMap.put(e.g, new ct("channel", (byte)1, new cu((byte)11)));
    localEnumMap.put(e.h, new ct("wrapper_type", (byte)2, new cu((byte)11)));
    localEnumMap.put(e.i, new ct("wrapper_version", (byte)2, new cu((byte)11)));
    localEnumMap.put(e.j, new ct("vertical_type", (byte)2, new cu((byte)8)));
    k = Collections.unmodifiableMap(localEnumMap);
    ct.a(ao.class, k);
  }

  public ao()
  {
    e[] arrayOfe = new e[6];
    arrayOfe[0] = e.b;
    arrayOfe[1] = e.c;
    arrayOfe[2] = e.d;
    arrayOfe[3] = e.h;
    arrayOfe[4] = e.i;
    arrayOfe[5] = e.j;
    this.A = arrayOfe;
  }

  public ao(String paramString1, bk parambk, String paramString2, String paramString3)
  {
    this();
    this.a = paramString1;
    this.e = parambk;
    this.f = paramString2;
    this.g = paramString3;
  }

  public ao(ao paramao)
  {
    e[] arrayOfe = new e[6];
    arrayOfe[0] = e.b;
    arrayOfe[1] = e.c;
    arrayOfe[2] = e.d;
    arrayOfe[3] = e.h;
    arrayOfe[4] = e.i;
    arrayOfe[5] = e.j;
    this.A = arrayOfe;
    this.z = paramao.z;
    if (paramao.e())
      this.a = paramao.a;
    if (paramao.i())
      this.b = paramao.b;
    this.c = paramao.c;
    if (paramao.o())
      this.d = paramao.d;
    if (paramao.r())
      this.e = paramao.e;
    if (paramao.u())
      this.f = paramao.f;
    if (paramao.x())
      this.g = paramao.g;
    if (paramao.A())
      this.h = paramao.h;
    if (paramao.D())
      this.i = paramao.i;
    this.j = paramao.j;
  }

  private void a(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    try
    {
      this.z = 0;
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

  public boolean A()
  {
    return this.h != null;
  }

  public String B()
  {
    return this.i;
  }

  public void C()
  {
    this.i = null;
  }

  public boolean D()
  {
    return this.i != null;
  }

  public int E()
  {
    return this.j;
  }

  public void F()
  {
    this.z = ce.b(this.z, 1);
  }

  public boolean G()
  {
    return ce.a(this.z, 1);
  }

  public void H()
    throws cn
  {
    if (this.a == null)
      throw new dh("Required field 'key' was not present! Struct: " + toString());
    if (this.e == null)
      throw new dh("Required field 'sdk_type' was not present! Struct: " + toString());
    if (this.f == null)
      throw new dh("Required field 'sdk_version' was not present! Struct: " + toString());
    if (this.g == null)
      throw new dh("Required field 'channel' was not present! Struct: " + toString());
  }

  public ao a()
  {
    return new ao(this);
  }

  public ao a(int paramInt)
  {
    this.c = paramInt;
    c(true);
    return this;
  }

  public ao a(String paramString)
  {
    this.a = paramString;
    return this;
  }

  public ao a(bk parambk)
  {
    this.e = parambk;
    return this;
  }

  public void a(dg paramdg)
    throws cn
  {
    ((dp)w.get(paramdg.D())).b().b(paramdg, this);
  }

  public void a(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.a = null;
  }

  public ao b(String paramString)
  {
    this.b = paramString;
    return this;
  }

  public void b()
  {
    this.a = null;
    this.b = null;
    c(false);
    this.c = 0;
    this.d = null;
    this.e = null;
    this.f = null;
    this.g = null;
    this.h = null;
    this.i = null;
    j(false);
    this.j = 0;
  }

  public void b(dg paramdg)
    throws cn
  {
    ((dp)w.get(paramdg.D())).b().a(paramdg, this);
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

  public ao c(int paramInt)
  {
    this.j = paramInt;
    j(true);
    return this;
  }

  public ao c(String paramString)
  {
    this.d = paramString;
    return this;
  }

  public void c(boolean paramBoolean)
  {
    this.z = ce.a(this.z, 0, paramBoolean);
  }

  public e d(int paramInt)
  {
    return e.a(paramInt);
  }

  public ao d(String paramString)
  {
    this.f = paramString;
    return this;
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

  public ao e(String paramString)
  {
    this.g = paramString;
    return this;
  }

  public void e(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.e = null;
  }

  public boolean e()
  {
    return this.a != null;
  }

  public String f()
  {
    return this.b;
  }

  public ao f(String paramString)
  {
    this.h = paramString;
    return this;
  }

  public void f(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.f = null;
  }

  public ao g(String paramString)
  {
    this.i = paramString;
    return this;
  }

  public void g(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.g = null;
  }

  public void h()
  {
    this.b = null;
  }

  public void h(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.h = null;
  }

  public void i(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.i = null;
  }

  public boolean i()
  {
    return this.b != null;
  }

  public int j()
  {
    return this.c;
  }

  public void j(boolean paramBoolean)
  {
    this.z = ce.a(this.z, 1, paramBoolean);
  }

  public void k()
  {
    this.z = ce.b(this.z, 0);
  }

  public boolean l()
  {
    return ce.a(this.z, 0);
  }

  public String m()
  {
    return this.d;
  }

  public void n()
  {
    this.d = null;
  }

  public boolean o()
  {
    return this.d != null;
  }

  public bk p()
  {
    return this.e;
  }

  public void q()
  {
    this.e = null;
  }

  public boolean r()
  {
    return this.e != null;
  }

  public String s()
  {
    return this.f;
  }

  public void t()
  {
    this.f = null;
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("AppInfo(");
    localStringBuilder.append("key:");
    if (this.a == null)
    {
      localStringBuilder.append("null");
      if (i())
      {
        localStringBuilder.append(", ");
        localStringBuilder.append("version:");
        if (this.b != null)
          break label368;
        localStringBuilder.append("null");
      }
      label72: if (l())
      {
        localStringBuilder.append(", ");
        localStringBuilder.append("version_index:");
        localStringBuilder.append(this.c);
      }
      if (o())
      {
        localStringBuilder.append(", ");
        localStringBuilder.append("package_name:");
        if (this.d != null)
          break label380;
        localStringBuilder.append("null");
      }
      label142: localStringBuilder.append(", ");
      localStringBuilder.append("sdk_type:");
      if (this.e != null)
        break label392;
      localStringBuilder.append("null");
      label173: localStringBuilder.append(", ");
      localStringBuilder.append("sdk_version:");
      if (this.f != null)
        break label404;
      localStringBuilder.append("null");
      label204: localStringBuilder.append(", ");
      localStringBuilder.append("channel:");
      if (this.g != null)
        break label416;
      localStringBuilder.append("null");
      label235: if (A())
      {
        localStringBuilder.append(", ");
        localStringBuilder.append("wrapper_type:");
        if (this.h != null)
          break label428;
        localStringBuilder.append("null");
      }
      label273: if (D())
      {
        localStringBuilder.append(", ");
        localStringBuilder.append("wrapper_version:");
        if (this.i != null)
          break label440;
        localStringBuilder.append("null");
      }
    }
    while (true)
    {
      if (G())
      {
        localStringBuilder.append(", ");
        localStringBuilder.append("vertical_type:");
        localStringBuilder.append(this.j);
      }
      localStringBuilder.append(")");
      return localStringBuilder.toString();
      localStringBuilder.append(this.a);
      break;
      label368: localStringBuilder.append(this.b);
      break label72;
      label380: localStringBuilder.append(this.d);
      break label142;
      label392: localStringBuilder.append(this.e);
      break label173;
      label404: localStringBuilder.append(this.f);
      break label204;
      label416: localStringBuilder.append(this.g);
      break label235;
      label428: localStringBuilder.append(this.h);
      break label273;
      label440: localStringBuilder.append(this.i);
    }
  }

  public boolean u()
  {
    return this.f != null;
  }

  public String v()
  {
    return this.g;
  }

  public void w()
  {
    this.g = null;
  }

  public boolean x()
  {
    return this.g != null;
  }

  public String y()
  {
    return this.h;
  }

  public void z()
  {
    this.h = null;
  }

  private static class a extends dq<ao>
  {
    public void a(dg paramdg, ao paramao)
      throws cn
    {
      paramdg.j();
      db localdb = paramdg.l();
      if (localdb.b == 0)
      {
        paramdg.k();
        paramao.H();
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
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      }
      while (true)
      {
        paramdg.m();
        break;
        if (localdb.b == 11)
        {
          paramao.a = paramdg.z();
          paramao.a(true);
        }
        else
        {
          dj.a(paramdg, localdb.b);
          continue;
          if (localdb.b == 11)
          {
            paramao.b = paramdg.z();
            paramao.b(true);
          }
          else
          {
            dj.a(paramdg, localdb.b);
            continue;
            if (localdb.b == 8)
            {
              paramao.c = paramdg.w();
              paramao.c(true);
            }
            else
            {
              dj.a(paramdg, localdb.b);
              continue;
              if (localdb.b == 11)
              {
                paramao.d = paramdg.z();
                paramao.d(true);
              }
              else
              {
                dj.a(paramdg, localdb.b);
                continue;
                if (localdb.b == 8)
                {
                  paramao.e = bk.a(paramdg.w());
                  paramao.e(true);
                }
                else
                {
                  dj.a(paramdg, localdb.b);
                  continue;
                  if (localdb.b == 11)
                  {
                    paramao.f = paramdg.z();
                    paramao.f(true);
                  }
                  else
                  {
                    dj.a(paramdg, localdb.b);
                    continue;
                    if (localdb.b == 11)
                    {
                      paramao.g = paramdg.z();
                      paramao.g(true);
                    }
                    else
                    {
                      dj.a(paramdg, localdb.b);
                      continue;
                      if (localdb.b == 11)
                      {
                        paramao.h = paramdg.z();
                        paramao.h(true);
                      }
                      else
                      {
                        dj.a(paramdg, localdb.b);
                        continue;
                        if (localdb.b == 11)
                        {
                          paramao.i = paramdg.z();
                          paramao.i(true);
                        }
                        else
                        {
                          dj.a(paramdg, localdb.b);
                          continue;
                          if (localdb.b == 8)
                          {
                            paramao.j = paramdg.w();
                            paramao.j(true);
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
            }
          }
        }
      }
    }

    public void b(dg paramdg, ao paramao)
      throws cn
    {
      paramao.H();
      paramdg.a(ao.I());
      if (paramao.a != null)
      {
        paramdg.a(ao.J());
        paramdg.a(paramao.a);
        paramdg.c();
      }
      if ((paramao.b != null) && (paramao.i()))
      {
        paramdg.a(ao.K());
        paramdg.a(paramao.b);
        paramdg.c();
      }
      if (paramao.l())
      {
        paramdg.a(ao.L());
        paramdg.a(paramao.c);
        paramdg.c();
      }
      if ((paramao.d != null) && (paramao.o()))
      {
        paramdg.a(ao.M());
        paramdg.a(paramao.d);
        paramdg.c();
      }
      if (paramao.e != null)
      {
        paramdg.a(ao.N());
        paramdg.a(paramao.e.a());
        paramdg.c();
      }
      if (paramao.f != null)
      {
        paramdg.a(ao.O());
        paramdg.a(paramao.f);
        paramdg.c();
      }
      if (paramao.g != null)
      {
        paramdg.a(ao.P());
        paramdg.a(paramao.g);
        paramdg.c();
      }
      if ((paramao.h != null) && (paramao.A()))
      {
        paramdg.a(ao.Q());
        paramdg.a(paramao.h);
        paramdg.c();
      }
      if ((paramao.i != null) && (paramao.D()))
      {
        paramdg.a(ao.R());
        paramdg.a(paramao.i);
        paramdg.c();
      }
      if (paramao.G())
      {
        paramdg.a(ao.S());
        paramdg.a(paramao.j);
        paramdg.c();
      }
      paramdg.d();
      paramdg.b();
    }
  }

  private static class b
    implements dp
  {
    public ao.a a()
    {
      return new ao.a(null);
    }
  }

  private static class c extends dr<ao>
  {
    public void a(dg paramdg, ao paramao)
      throws cn
    {
      dm localdm = (dm)paramdg;
      localdm.a(paramao.a);
      localdm.a(paramao.e.a());
      localdm.a(paramao.f);
      localdm.a(paramao.g);
      BitSet localBitSet = new BitSet();
      if (paramao.i())
        localBitSet.set(0);
      if (paramao.l())
        localBitSet.set(1);
      if (paramao.o())
        localBitSet.set(2);
      if (paramao.A())
        localBitSet.set(3);
      if (paramao.D())
        localBitSet.set(4);
      if (paramao.G())
        localBitSet.set(5);
      localdm.a(localBitSet, 6);
      if (paramao.i())
        localdm.a(paramao.b);
      if (paramao.l())
        localdm.a(paramao.c);
      if (paramao.o())
        localdm.a(paramao.d);
      if (paramao.A())
        localdm.a(paramao.h);
      if (paramao.D())
        localdm.a(paramao.i);
      if (paramao.G())
        localdm.a(paramao.j);
    }

    public void b(dg paramdg, ao paramao)
      throws cn
    {
      dm localdm = (dm)paramdg;
      paramao.a = localdm.z();
      paramao.a(true);
      paramao.e = bk.a(localdm.w());
      paramao.e(true);
      paramao.f = localdm.z();
      paramao.f(true);
      paramao.g = localdm.z();
      paramao.g(true);
      BitSet localBitSet = localdm.b(6);
      if (localBitSet.get(0))
      {
        paramao.b = localdm.z();
        paramao.b(true);
      }
      if (localBitSet.get(1))
      {
        paramao.c = localdm.w();
        paramao.c(true);
      }
      if (localBitSet.get(2))
      {
        paramao.d = localdm.z();
        paramao.d(true);
      }
      if (localBitSet.get(3))
      {
        paramao.h = localdm.z();
        paramao.h(true);
      }
      if (localBitSet.get(4))
      {
        paramao.i = localdm.z();
        paramao.i(true);
      }
      if (localBitSet.get(5))
      {
        paramao.j = localdm.w();
        paramao.j(true);
      }
    }
  }

  private static class d
    implements dp
  {
    public ao.c a()
    {
      return new ao.c(null);
    }
  }

  public static enum e
    implements co
  {
    private static final Map<String, e> k;
    private final short l;
    private final String m;

    static
    {
      e[] arrayOfe = new e[10];
      arrayOfe[0] = a;
      arrayOfe[1] = b;
      arrayOfe[2] = c;
      arrayOfe[3] = d;
      arrayOfe[4] = e;
      arrayOfe[5] = f;
      arrayOfe[6] = g;
      arrayOfe[7] = h;
      arrayOfe[8] = i;
      arrayOfe[9] = j;
      n = arrayOfe;
      k = new HashMap();
      Iterator localIterator = EnumSet.allOf(e.class).iterator();
      while (localIterator.hasNext())
      {
        e locale = (e)localIterator.next();
        k.put(locale.b(), locale);
      }
    }

    private e(short paramShort, String paramString)
    {
      this.l = paramShort;
      this.m = paramString;
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
        return e;
      case 6:
        return f;
      case 7:
        return g;
      case 8:
        return h;
      case 9:
        return i;
      case 10:
      }
      return j;
    }

    public static e a(String paramString)
    {
      return (e)k.get(paramString);
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
      return this.l;
    }

    public String b()
    {
      return this.m;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.ao
 * JD-Core Version:    0.6.2
 */