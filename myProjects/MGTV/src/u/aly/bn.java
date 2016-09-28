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

public class bn
  implements Serializable, Cloneable, ch<bn, e>
{
  public static final Map<e, ct> l;
  private static final dl m = new dl("UALogEntry");
  private static final db n = new db("client_stats", (byte)12, (short)1);
  private static final db o = new db("app_info", (byte)12, (short)2);
  private static final db p = new db("device_info", (byte)12, (short)3);
  private static final db q = new db("misc_info", (byte)12, (short)4);
  private static final db r = new db("activate_msg", (byte)12, (short)5);
  private static final db s = new db("instant_msgs", (byte)15, (short)6);
  private static final db t = new db("sessions", (byte)15, (short)7);
  private static final db u = new db("imprint", (byte)12, (short)8);
  private static final db v = new db("id_tracking", (byte)12, (short)9);
  private static final db w = new db("active_user", (byte)12, (short)10);
  private static final db x = new db("control_policy", (byte)12, (short)11);
  private static final Map<Class<? extends do>, dp> y = new HashMap();
  public ap a;
  public ao b;
  public ar c;
  public bf d;
  public am e;
  public List<bc> f;
  public List<bl> g;
  public ba h;
  public az i;
  public an j;
  public aq k;
  private e[] z;

  static
  {
    y.put(dq.class, new b(null));
    y.put(dr.class, new d(null));
    EnumMap localEnumMap = new EnumMap(e.class);
    localEnumMap.put(e.a, new ct("client_stats", (byte)1, new cy((byte)12, ap.class)));
    localEnumMap.put(e.b, new ct("app_info", (byte)1, new cy((byte)12, ao.class)));
    localEnumMap.put(e.c, new ct("device_info", (byte)1, new cy((byte)12, ar.class)));
    localEnumMap.put(e.d, new ct("misc_info", (byte)1, new cy((byte)12, bf.class)));
    localEnumMap.put(e.e, new ct("activate_msg", (byte)2, new cy((byte)12, am.class)));
    localEnumMap.put(e.f, new ct("instant_msgs", (byte)2, new cv((byte)15, new cy((byte)12, bc.class))));
    localEnumMap.put(e.g, new ct("sessions", (byte)2, new cv((byte)15, new cy((byte)12, bl.class))));
    localEnumMap.put(e.h, new ct("imprint", (byte)2, new cy((byte)12, ba.class)));
    localEnumMap.put(e.i, new ct("id_tracking", (byte)2, new cy((byte)12, az.class)));
    localEnumMap.put(e.j, new ct("active_user", (byte)2, new cy((byte)12, an.class)));
    localEnumMap.put(e.k, new ct("control_policy", (byte)2, new cy((byte)12, aq.class)));
    l = Collections.unmodifiableMap(localEnumMap);
    ct.a(bn.class, l);
  }

  public bn()
  {
    e[] arrayOfe = new e[7];
    arrayOfe[0] = e.e;
    arrayOfe[1] = e.f;
    arrayOfe[2] = e.g;
    arrayOfe[3] = e.h;
    arrayOfe[4] = e.i;
    arrayOfe[5] = e.j;
    arrayOfe[6] = e.k;
    this.z = arrayOfe;
  }

  public bn(ap paramap, ao paramao, ar paramar, bf parambf)
  {
    this();
    this.a = paramap;
    this.b = paramao;
    this.c = paramar;
    this.d = parambf;
  }

  public bn(bn parambn)
  {
    e[] arrayOfe = new e[7];
    arrayOfe[0] = e.e;
    arrayOfe[1] = e.f;
    arrayOfe[2] = e.g;
    arrayOfe[3] = e.h;
    arrayOfe[4] = e.i;
    arrayOfe[5] = e.j;
    arrayOfe[6] = e.k;
    this.z = arrayOfe;
    if (parambn.e())
      this.a = new ap(parambn.a);
    if (parambn.i())
      this.b = new ao(parambn.b);
    if (parambn.l())
      this.c = new ar(parambn.c);
    if (parambn.o())
      this.d = new bf(parambn.d);
    if (parambn.r())
      this.e = new am(parambn.e);
    if (parambn.w())
    {
      ArrayList localArrayList1 = new ArrayList();
      Iterator localIterator1 = parambn.f.iterator();
      while (localIterator1.hasNext())
        localArrayList1.add(new bc((bc)localIterator1.next()));
      this.f = localArrayList1;
    }
    if (parambn.B())
    {
      ArrayList localArrayList2 = new ArrayList();
      Iterator localIterator2 = parambn.g.iterator();
      while (localIterator2.hasNext())
        localArrayList2.add(new bl((bl)localIterator2.next()));
      this.g = localArrayList2;
    }
    if (parambn.E())
      this.h = new ba(parambn.h);
    if (parambn.H())
      this.i = new az(parambn.i);
    if (parambn.K())
      this.j = new an(parambn.j);
    if (parambn.N())
      this.k = new aq(parambn.k);
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

  public void A()
  {
    this.g = null;
  }

  public boolean B()
  {
    return this.g != null;
  }

  public ba C()
  {
    return this.h;
  }

  public void D()
  {
    this.h = null;
  }

  public boolean E()
  {
    return this.h != null;
  }

  public az F()
  {
    return this.i;
  }

  public void G()
  {
    this.i = null;
  }

  public boolean H()
  {
    return this.i != null;
  }

  public an I()
  {
    return this.j;
  }

  public void J()
  {
    this.j = null;
  }

  public boolean K()
  {
    return this.j != null;
  }

  public aq L()
  {
    return this.k;
  }

  public void M()
  {
    this.k = null;
  }

  public boolean N()
  {
    return this.k != null;
  }

  public void O()
    throws cn
  {
    if (this.a == null)
      throw new dh("Required field 'client_stats' was not present! Struct: " + toString());
    if (this.b == null)
      throw new dh("Required field 'app_info' was not present! Struct: " + toString());
    if (this.c == null)
      throw new dh("Required field 'device_info' was not present! Struct: " + toString());
    if (this.d == null)
      throw new dh("Required field 'misc_info' was not present! Struct: " + toString());
    if (this.a != null)
      this.a.m();
    if (this.b != null)
      this.b.H();
    if (this.c != null)
      this.c.ac();
    if (this.d != null)
      this.d.H();
    if (this.e != null)
      this.e.f();
    if (this.h != null)
      this.h.n();
    if (this.i != null)
      this.i.p();
    if (this.j != null)
      this.j.j();
    if (this.k != null)
      this.k.f();
  }

  public e a(int paramInt)
  {
    return e.a(paramInt);
  }

  public bn a()
  {
    return new bn(this);
  }

  public bn a(List<bc> paramList)
  {
    this.f = paramList;
    return this;
  }

  public bn a(am paramam)
  {
    this.e = paramam;
    return this;
  }

  public bn a(an paraman)
  {
    this.j = paraman;
    return this;
  }

  public bn a(ao paramao)
  {
    this.b = paramao;
    return this;
  }

  public bn a(ap paramap)
  {
    this.a = paramap;
    return this;
  }

  public bn a(aq paramaq)
  {
    this.k = paramaq;
    return this;
  }

  public bn a(ar paramar)
  {
    this.c = paramar;
    return this;
  }

  public bn a(az paramaz)
  {
    this.i = paramaz;
    return this;
  }

  public bn a(ba paramba)
  {
    this.h = paramba;
    return this;
  }

  public bn a(bf parambf)
  {
    this.d = parambf;
    return this;
  }

  public void a(bc parambc)
  {
    if (this.f == null)
      this.f = new ArrayList();
    this.f.add(parambc);
  }

  public void a(bl parambl)
  {
    if (this.g == null)
      this.g = new ArrayList();
    this.g.add(parambl);
  }

  public void a(dg paramdg)
    throws cn
  {
    ((dp)y.get(paramdg.D())).b().b(paramdg, this);
  }

  public void a(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.a = null;
  }

  public bn b(List<bl> paramList)
  {
    this.g = paramList;
    return this;
  }

  public void b()
  {
    this.a = null;
    this.b = null;
    this.c = null;
    this.d = null;
    this.e = null;
    this.f = null;
    this.g = null;
    this.h = null;
    this.i = null;
    this.j = null;
    this.k = null;
  }

  public void b(dg paramdg)
    throws cn
  {
    ((dp)y.get(paramdg.D())).b().a(paramdg, this);
  }

  public void b(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.b = null;
  }

  public ap c()
  {
    return this.a;
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

  public void e(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.e = null;
  }

  public boolean e()
  {
    return this.a != null;
  }

  public ao f()
  {
    return this.b;
  }

  public void f(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.f = null;
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

  public ar j()
  {
    return this.c;
  }

  public void j(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.j = null;
  }

  public void k()
  {
    this.c = null;
  }

  public void k(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.k = null;
  }

  public boolean l()
  {
    return this.c != null;
  }

  public bf m()
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

  public am p()
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

  public int s()
  {
    if (this.f == null)
      return 0;
    return this.f.size();
  }

  public Iterator<bc> t()
  {
    if (this.f == null)
      return null;
    return this.f.iterator();
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("UALogEntry(");
    localStringBuilder.append("client_stats:");
    if (this.a == null)
    {
      localStringBuilder.append("null");
      localStringBuilder.append(", ");
      localStringBuilder.append("app_info:");
      if (this.b != null)
        break label418;
      localStringBuilder.append("null");
      label65: localStringBuilder.append(", ");
      localStringBuilder.append("device_info:");
      if (this.c != null)
        break label430;
      localStringBuilder.append("null");
      label96: localStringBuilder.append(", ");
      localStringBuilder.append("misc_info:");
      if (this.d != null)
        break label442;
      localStringBuilder.append("null");
      label127: if (r())
      {
        localStringBuilder.append(", ");
        localStringBuilder.append("activate_msg:");
        if (this.e != null)
          break label454;
        localStringBuilder.append("null");
      }
      label165: if (w())
      {
        localStringBuilder.append(", ");
        localStringBuilder.append("instant_msgs:");
        if (this.f != null)
          break label466;
        localStringBuilder.append("null");
      }
      label203: if (B())
      {
        localStringBuilder.append(", ");
        localStringBuilder.append("sessions:");
        if (this.g != null)
          break label478;
        localStringBuilder.append("null");
      }
      label241: if (E())
      {
        localStringBuilder.append(", ");
        localStringBuilder.append("imprint:");
        if (this.h != null)
          break label490;
        localStringBuilder.append("null");
      }
      label279: if (H())
      {
        localStringBuilder.append(", ");
        localStringBuilder.append("id_tracking:");
        if (this.i != null)
          break label502;
        localStringBuilder.append("null");
      }
      label317: if (K())
      {
        localStringBuilder.append(", ");
        localStringBuilder.append("active_user:");
        if (this.j != null)
          break label514;
        localStringBuilder.append("null");
      }
      label355: if (N())
      {
        localStringBuilder.append(", ");
        localStringBuilder.append("control_policy:");
        if (this.k != null)
          break label526;
        localStringBuilder.append("null");
      }
    }
    while (true)
    {
      localStringBuilder.append(")");
      return localStringBuilder.toString();
      localStringBuilder.append(this.a);
      break;
      label418: localStringBuilder.append(this.b);
      break label65;
      label430: localStringBuilder.append(this.c);
      break label96;
      label442: localStringBuilder.append(this.d);
      break label127;
      label454: localStringBuilder.append(this.e);
      break label165;
      label466: localStringBuilder.append(this.f);
      break label203;
      label478: localStringBuilder.append(this.g);
      break label241;
      label490: localStringBuilder.append(this.h);
      break label279;
      label502: localStringBuilder.append(this.i);
      break label317;
      label514: localStringBuilder.append(this.j);
      break label355;
      label526: localStringBuilder.append(this.k);
    }
  }

  public List<bc> u()
  {
    return this.f;
  }

  public void v()
  {
    this.f = null;
  }

  public boolean w()
  {
    return this.f != null;
  }

  public int x()
  {
    if (this.g == null)
      return 0;
    return this.g.size();
  }

  public Iterator<bl> y()
  {
    if (this.g == null)
      return null;
    return this.g.iterator();
  }

  public List<bl> z()
  {
    return this.g;
  }

  private static class a extends dq<bn>
  {
    public void a(dg paramdg, bn parambn)
      throws cn
    {
      paramdg.j();
      db localdb = paramdg.l();
      if (localdb.b == 0)
      {
        paramdg.k();
        parambn.O();
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
      case 11:
      }
      while (true)
      {
        paramdg.m();
        break;
        if (localdb.b == 12)
        {
          parambn.a = new ap();
          parambn.a.a(paramdg);
          parambn.a(true);
        }
        else
        {
          dj.a(paramdg, localdb.b);
          continue;
          if (localdb.b == 12)
          {
            parambn.b = new ao();
            parambn.b.a(paramdg);
            parambn.b(true);
          }
          else
          {
            dj.a(paramdg, localdb.b);
            continue;
            if (localdb.b == 12)
            {
              parambn.c = new ar();
              parambn.c.a(paramdg);
              parambn.c(true);
            }
            else
            {
              dj.a(paramdg, localdb.b);
              continue;
              if (localdb.b == 12)
              {
                parambn.d = new bf();
                parambn.d.a(paramdg);
                parambn.d(true);
              }
              else
              {
                dj.a(paramdg, localdb.b);
                continue;
                if (localdb.b == 12)
                {
                  parambn.e = new am();
                  parambn.e.a(paramdg);
                  parambn.e(true);
                }
                else
                {
                  dj.a(paramdg, localdb.b);
                  continue;
                  if (localdb.b == 15)
                  {
                    dc localdc2 = paramdg.p();
                    parambn.f = new ArrayList(localdc2.b);
                    for (int j = 0; j < localdc2.b; j++)
                    {
                      bc localbc = new bc();
                      localbc.a(paramdg);
                      parambn.f.add(localbc);
                    }
                    paramdg.q();
                    parambn.f(true);
                  }
                  else
                  {
                    dj.a(paramdg, localdb.b);
                    continue;
                    if (localdb.b == 15)
                    {
                      dc localdc1 = paramdg.p();
                      parambn.g = new ArrayList(localdc1.b);
                      for (int i = 0; i < localdc1.b; i++)
                      {
                        bl localbl = new bl();
                        localbl.a(paramdg);
                        parambn.g.add(localbl);
                      }
                      paramdg.q();
                      parambn.g(true);
                    }
                    else
                    {
                      dj.a(paramdg, localdb.b);
                      continue;
                      if (localdb.b == 12)
                      {
                        parambn.h = new ba();
                        parambn.h.a(paramdg);
                        parambn.h(true);
                      }
                      else
                      {
                        dj.a(paramdg, localdb.b);
                        continue;
                        if (localdb.b == 12)
                        {
                          parambn.i = new az();
                          parambn.i.a(paramdg);
                          parambn.i(true);
                        }
                        else
                        {
                          dj.a(paramdg, localdb.b);
                          continue;
                          if (localdb.b == 12)
                          {
                            parambn.j = new an();
                            parambn.j.a(paramdg);
                            parambn.j(true);
                          }
                          else
                          {
                            dj.a(paramdg, localdb.b);
                            continue;
                            if (localdb.b == 12)
                            {
                              parambn.k = new aq();
                              parambn.k.a(paramdg);
                              parambn.k(true);
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
    }

    public void b(dg paramdg, bn parambn)
      throws cn
    {
      parambn.O();
      paramdg.a(bn.P());
      if (parambn.a != null)
      {
        paramdg.a(bn.Q());
        parambn.a.b(paramdg);
        paramdg.c();
      }
      if (parambn.b != null)
      {
        paramdg.a(bn.R());
        parambn.b.b(paramdg);
        paramdg.c();
      }
      if (parambn.c != null)
      {
        paramdg.a(bn.S());
        parambn.c.b(paramdg);
        paramdg.c();
      }
      if (parambn.d != null)
      {
        paramdg.a(bn.T());
        parambn.d.b(paramdg);
        paramdg.c();
      }
      if ((parambn.e != null) && (parambn.r()))
      {
        paramdg.a(bn.U());
        parambn.e.b(paramdg);
        paramdg.c();
      }
      if ((parambn.f != null) && (parambn.w()))
      {
        paramdg.a(bn.V());
        paramdg.a(new dc((byte)12, parambn.f.size()));
        Iterator localIterator2 = parambn.f.iterator();
        while (localIterator2.hasNext())
          ((bc)localIterator2.next()).b(paramdg);
        paramdg.f();
        paramdg.c();
      }
      if ((parambn.g != null) && (parambn.B()))
      {
        paramdg.a(bn.W());
        paramdg.a(new dc((byte)12, parambn.g.size()));
        Iterator localIterator1 = parambn.g.iterator();
        while (localIterator1.hasNext())
          ((bl)localIterator1.next()).b(paramdg);
        paramdg.f();
        paramdg.c();
      }
      if ((parambn.h != null) && (parambn.E()))
      {
        paramdg.a(bn.X());
        parambn.h.b(paramdg);
        paramdg.c();
      }
      if ((parambn.i != null) && (parambn.H()))
      {
        paramdg.a(bn.Y());
        parambn.i.b(paramdg);
        paramdg.c();
      }
      if ((parambn.j != null) && (parambn.K()))
      {
        paramdg.a(bn.Z());
        parambn.j.b(paramdg);
        paramdg.c();
      }
      if ((parambn.k != null) && (parambn.N()))
      {
        paramdg.a(bn.aa());
        parambn.k.b(paramdg);
        paramdg.c();
      }
      paramdg.d();
      paramdg.b();
    }
  }

  private static class b
    implements dp
  {
    public bn.a a()
    {
      return new bn.a(null);
    }
  }

  private static class c extends dr<bn>
  {
    public void a(dg paramdg, bn parambn)
      throws cn
    {
      dm localdm = (dm)paramdg;
      parambn.a.b(localdm);
      parambn.b.b(localdm);
      parambn.c.b(localdm);
      parambn.d.b(localdm);
      BitSet localBitSet = new BitSet();
      if (parambn.r())
        localBitSet.set(0);
      if (parambn.w())
        localBitSet.set(1);
      if (parambn.B())
        localBitSet.set(2);
      if (parambn.E())
        localBitSet.set(3);
      if (parambn.H())
        localBitSet.set(4);
      if (parambn.K())
        localBitSet.set(5);
      if (parambn.N())
        localBitSet.set(6);
      localdm.a(localBitSet, 7);
      if (parambn.r())
        parambn.e.b(localdm);
      if (parambn.w())
      {
        localdm.a(parambn.f.size());
        Iterator localIterator2 = parambn.f.iterator();
        while (localIterator2.hasNext())
          ((bc)localIterator2.next()).b(localdm);
      }
      if (parambn.B())
      {
        localdm.a(parambn.g.size());
        Iterator localIterator1 = parambn.g.iterator();
        while (localIterator1.hasNext())
          ((bl)localIterator1.next()).b(localdm);
      }
      if (parambn.E())
        parambn.h.b(localdm);
      if (parambn.H())
        parambn.i.b(localdm);
      if (parambn.K())
        parambn.j.b(localdm);
      if (parambn.N())
        parambn.k.b(localdm);
    }

    public void b(dg paramdg, bn parambn)
      throws cn
    {
      int i = 0;
      dm localdm = (dm)paramdg;
      parambn.a = new ap();
      parambn.a.a(localdm);
      parambn.a(true);
      parambn.b = new ao();
      parambn.b.a(localdm);
      parambn.b(true);
      parambn.c = new ar();
      parambn.c.a(localdm);
      parambn.c(true);
      parambn.d = new bf();
      parambn.d.a(localdm);
      parambn.d(true);
      BitSet localBitSet = localdm.b(7);
      if (localBitSet.get(0))
      {
        parambn.e = new am();
        parambn.e.a(localdm);
        parambn.e(true);
      }
      if (localBitSet.get(1))
      {
        dc localdc1 = new dc((byte)12, localdm.w());
        parambn.f = new ArrayList(localdc1.b);
        for (int j = 0; j < localdc1.b; j++)
        {
          bc localbc = new bc();
          localbc.a(localdm);
          parambn.f.add(localbc);
        }
        parambn.f(true);
      }
      if (localBitSet.get(2))
      {
        dc localdc2 = new dc((byte)12, localdm.w());
        parambn.g = new ArrayList(localdc2.b);
        while (i < localdc2.b)
        {
          bl localbl = new bl();
          localbl.a(localdm);
          parambn.g.add(localbl);
          i++;
        }
        parambn.g(true);
      }
      if (localBitSet.get(3))
      {
        parambn.h = new ba();
        parambn.h.a(localdm);
        parambn.h(true);
      }
      if (localBitSet.get(4))
      {
        parambn.i = new az();
        parambn.i.a(localdm);
        parambn.i(true);
      }
      if (localBitSet.get(5))
      {
        parambn.j = new an();
        parambn.j.a(localdm);
        parambn.j(true);
      }
      if (localBitSet.get(6))
      {
        parambn.k = new aq();
        parambn.k.a(localdm);
        parambn.k(true);
      }
    }
  }

  private static class d
    implements dp
  {
    public bn.c a()
    {
      return new bn.c(null);
    }
  }

  public static enum e
    implements co
  {
    private static final Map<String, e> l;
    private final short m;
    private final String n;

    static
    {
      e[] arrayOfe = new e[11];
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
      arrayOfe[10] = k;
      o = arrayOfe;
      l = new HashMap();
      Iterator localIterator = EnumSet.allOf(e.class).iterator();
      while (localIterator.hasNext())
      {
        e locale = (e)localIterator.next();
        l.put(locale.b(), locale);
      }
    }

    private e(short paramShort, String paramString)
    {
      this.m = paramShort;
      this.n = paramString;
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
        return j;
      case 11:
      }
      return k;
    }

    public static e a(String paramString)
    {
      return (e)l.get(paramString);
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
      return this.m;
    }

    public String b()
    {
      return this.n;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.bn
 * JD-Core Version:    0.6.2
 */