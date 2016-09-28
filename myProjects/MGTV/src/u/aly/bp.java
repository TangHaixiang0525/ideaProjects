package u.aly;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class bp
  implements Serializable, Cloneable, ch<bp, e>
{
  private static final int A = 3;
  public static final Map<e, ct> k;
  private static final dl l = new dl("UMEnvelope");
  private static final db m = new db("version", (byte)11, (short)1);
  private static final db n = new db("address", (byte)11, (short)2);
  private static final db o = new db("signature", (byte)11, (short)3);
  private static final db p = new db("serial_num", (byte)8, (short)4);
  private static final db q = new db("ts_secs", (byte)8, (short)5);
  private static final db r = new db("length", (byte)8, (short)6);
  private static final db s = new db("entity", (byte)11, (short)7);
  private static final db t = new db("guid", (byte)11, (short)8);
  private static final db u = new db("checksum", (byte)11, (short)9);
  private static final db v = new db("codex", (byte)8, (short)10);
  private static final Map<Class<? extends do>, dp> w = new HashMap();
  private static final int x = 0;
  private static final int y = 1;
  private static final int z = 2;
  private byte B = 0;
  private e[] C;
  public String a;
  public String b;
  public String c;
  public int d;
  public int e;
  public int f;
  public ByteBuffer g;
  public String h;
  public String i;
  public int j;

  static
  {
    w.put(dq.class, new b(null));
    w.put(dr.class, new d(null));
    EnumMap localEnumMap = new EnumMap(e.class);
    localEnumMap.put(e.a, new ct("version", (byte)1, new cu((byte)11)));
    localEnumMap.put(e.b, new ct("address", (byte)1, new cu((byte)11)));
    localEnumMap.put(e.c, new ct("signature", (byte)1, new cu((byte)11)));
    localEnumMap.put(e.d, new ct("serial_num", (byte)1, new cu((byte)8)));
    localEnumMap.put(e.e, new ct("ts_secs", (byte)1, new cu((byte)8)));
    localEnumMap.put(e.f, new ct("length", (byte)1, new cu((byte)8)));
    localEnumMap.put(e.g, new ct("entity", (byte)1, new cu((byte)11, true)));
    localEnumMap.put(e.h, new ct("guid", (byte)1, new cu((byte)11)));
    localEnumMap.put(e.i, new ct("checksum", (byte)1, new cu((byte)11)));
    localEnumMap.put(e.j, new ct("codex", (byte)2, new cu((byte)8)));
    k = Collections.unmodifiableMap(localEnumMap);
    ct.a(bp.class, k);
  }

  public bp()
  {
    e[] arrayOfe = new e[1];
    arrayOfe[0] = e.j;
    this.C = arrayOfe;
  }

  public bp(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, int paramInt3, ByteBuffer paramByteBuffer, String paramString4, String paramString5)
  {
    this();
    this.a = paramString1;
    this.b = paramString2;
    this.c = paramString3;
    this.d = paramInt1;
    d(true);
    this.e = paramInt2;
    e(true);
    this.f = paramInt3;
    f(true);
    this.g = paramByteBuffer;
    this.h = paramString4;
    this.i = paramString5;
  }

  public bp(bp parambp)
  {
    e[] arrayOfe = new e[1];
    arrayOfe[0] = e.j;
    this.C = arrayOfe;
    this.B = parambp.B;
    if (parambp.e())
      this.a = parambp.a;
    if (parambp.i())
      this.b = parambp.b;
    if (parambp.l())
      this.c = parambp.c;
    this.d = parambp.d;
    this.e = parambp.e;
    this.f = parambp.f;
    if (parambp.y())
      this.g = ci.d(parambp.g);
    if (parambp.B())
      this.h = parambp.h;
    if (parambp.E())
      this.i = parambp.i;
    this.j = parambp.j;
  }

  private void a(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    try
    {
      this.B = 0;
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
    this.h = null;
  }

  public boolean B()
  {
    return this.h != null;
  }

  public String C()
  {
    return this.i;
  }

  public void D()
  {
    this.i = null;
  }

  public boolean E()
  {
    return this.i != null;
  }

  public int F()
  {
    return this.j;
  }

  public void G()
  {
    this.B = ce.b(this.B, 3);
  }

  public boolean H()
  {
    return ce.a(this.B, 3);
  }

  public void I()
    throws cn
  {
    if (this.a == null)
      throw new dh("Required field 'version' was not present! Struct: " + toString());
    if (this.b == null)
      throw new dh("Required field 'address' was not present! Struct: " + toString());
    if (this.c == null)
      throw new dh("Required field 'signature' was not present! Struct: " + toString());
    if (this.g == null)
      throw new dh("Required field 'entity' was not present! Struct: " + toString());
    if (this.h == null)
      throw new dh("Required field 'guid' was not present! Struct: " + toString());
    if (this.i == null)
      throw new dh("Required field 'checksum' was not present! Struct: " + toString());
  }

  public bp a()
  {
    return new bp(this);
  }

  public bp a(int paramInt)
  {
    this.d = paramInt;
    d(true);
    return this;
  }

  public bp a(String paramString)
  {
    this.a = paramString;
    return this;
  }

  public bp a(ByteBuffer paramByteBuffer)
  {
    this.g = paramByteBuffer;
    return this;
  }

  public bp a(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null);
    for (ByteBuffer localByteBuffer = (ByteBuffer)null; ; localByteBuffer = ByteBuffer.wrap(paramArrayOfByte))
    {
      a(localByteBuffer);
      return this;
    }
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

  public bp b(String paramString)
  {
    this.b = paramString;
    return this;
  }

  public void b()
  {
    this.a = null;
    this.b = null;
    this.c = null;
    d(false);
    this.d = 0;
    e(false);
    this.e = 0;
    f(false);
    this.f = 0;
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

  public bp c(int paramInt)
  {
    this.e = paramInt;
    e(true);
    return this;
  }

  public bp c(String paramString)
  {
    this.c = paramString;
    return this;
  }

  public void c(boolean paramBoolean)
  {
    if (!paramBoolean)
      this.c = null;
  }

  public bp d(int paramInt)
  {
    this.f = paramInt;
    f(true);
    return this;
  }

  public bp d(String paramString)
  {
    this.h = paramString;
    return this;
  }

  public void d()
  {
    this.a = null;
  }

  public void d(boolean paramBoolean)
  {
    this.B = ce.a(this.B, 0, paramBoolean);
  }

  public bp e(int paramInt)
  {
    this.j = paramInt;
    j(true);
    return this;
  }

  public bp e(String paramString)
  {
    this.i = paramString;
    return this;
  }

  public void e(boolean paramBoolean)
  {
    this.B = ce.a(this.B, 1, paramBoolean);
  }

  public boolean e()
  {
    return this.a != null;
  }

  public String f()
  {
    return this.b;
  }

  public e f(int paramInt)
  {
    return e.a(paramInt);
  }

  public void f(boolean paramBoolean)
  {
    this.B = ce.a(this.B, 2, paramBoolean);
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

  public String j()
  {
    return this.c;
  }

  public void j(boolean paramBoolean)
  {
    this.B = ce.a(this.B, 3, paramBoolean);
  }

  public void k()
  {
    this.c = null;
  }

  public boolean l()
  {
    return this.c != null;
  }

  public int m()
  {
    return this.d;
  }

  public void n()
  {
    this.B = ce.b(this.B, 0);
  }

  public boolean o()
  {
    return ce.a(this.B, 0);
  }

  public int p()
  {
    return this.e;
  }

  public void q()
  {
    this.B = ce.b(this.B, 1);
  }

  public boolean r()
  {
    return ce.a(this.B, 1);
  }

  public int s()
  {
    return this.f;
  }

  public void t()
  {
    this.B = ce.b(this.B, 2);
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("UMEnvelope(");
    localStringBuilder.append("version:");
    if (this.a == null)
    {
      localStringBuilder.append("null");
      localStringBuilder.append(", ");
      localStringBuilder.append("address:");
      if (this.b != null)
        break label321;
      localStringBuilder.append("null");
      label65: localStringBuilder.append(", ");
      localStringBuilder.append("signature:");
      if (this.c != null)
        break label333;
      localStringBuilder.append("null");
      label96: localStringBuilder.append(", ");
      localStringBuilder.append("serial_num:");
      localStringBuilder.append(this.d);
      localStringBuilder.append(", ");
      localStringBuilder.append("ts_secs:");
      localStringBuilder.append(this.e);
      localStringBuilder.append(", ");
      localStringBuilder.append("length:");
      localStringBuilder.append(this.f);
      localStringBuilder.append(", ");
      localStringBuilder.append("entity:");
      if (this.g != null)
        break label345;
      localStringBuilder.append("null");
      label202: localStringBuilder.append(", ");
      localStringBuilder.append("guid:");
      if (this.h != null)
        break label356;
      localStringBuilder.append("null");
      label233: localStringBuilder.append(", ");
      localStringBuilder.append("checksum:");
      if (this.i != null)
        break label368;
      localStringBuilder.append("null");
    }
    while (true)
    {
      if (H())
      {
        localStringBuilder.append(", ");
        localStringBuilder.append("codex:");
        localStringBuilder.append(this.j);
      }
      localStringBuilder.append(")");
      return localStringBuilder.toString();
      localStringBuilder.append(this.a);
      break;
      label321: localStringBuilder.append(this.b);
      break label65;
      label333: localStringBuilder.append(this.c);
      break label96;
      label345: ci.a(this.g, localStringBuilder);
      break label202;
      label356: localStringBuilder.append(this.h);
      break label233;
      label368: localStringBuilder.append(this.i);
    }
  }

  public boolean u()
  {
    return ce.a(this.B, 2);
  }

  public byte[] v()
  {
    a(ci.c(this.g));
    if (this.g == null)
      return null;
    return this.g.array();
  }

  public ByteBuffer w()
  {
    return this.g;
  }

  public void x()
  {
    this.g = null;
  }

  public boolean y()
  {
    return this.g != null;
  }

  public String z()
  {
    return this.h;
  }

  private static class a extends dq<bp>
  {
    public void a(dg paramdg, bp parambp)
      throws cn
    {
      paramdg.j();
      db localdb = paramdg.l();
      if (localdb.b == 0)
      {
        paramdg.k();
        if (!parambp.o())
          throw new dh("Required field 'serial_num' was not found in serialized data! Struct: " + toString());
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
            parambp.a = paramdg.z();
            parambp.a(true);
          }
          else
          {
            dj.a(paramdg, localdb.b);
            continue;
            if (localdb.b == 11)
            {
              parambp.b = paramdg.z();
              parambp.b(true);
            }
            else
            {
              dj.a(paramdg, localdb.b);
              continue;
              if (localdb.b == 11)
              {
                parambp.c = paramdg.z();
                parambp.c(true);
              }
              else
              {
                dj.a(paramdg, localdb.b);
                continue;
                if (localdb.b == 8)
                {
                  parambp.d = paramdg.w();
                  parambp.d(true);
                }
                else
                {
                  dj.a(paramdg, localdb.b);
                  continue;
                  if (localdb.b == 8)
                  {
                    parambp.e = paramdg.w();
                    parambp.e(true);
                  }
                  else
                  {
                    dj.a(paramdg, localdb.b);
                    continue;
                    if (localdb.b == 8)
                    {
                      parambp.f = paramdg.w();
                      parambp.f(true);
                    }
                    else
                    {
                      dj.a(paramdg, localdb.b);
                      continue;
                      if (localdb.b == 11)
                      {
                        parambp.g = paramdg.A();
                        parambp.g(true);
                      }
                      else
                      {
                        dj.a(paramdg, localdb.b);
                        continue;
                        if (localdb.b == 11)
                        {
                          parambp.h = paramdg.z();
                          parambp.h(true);
                        }
                        else
                        {
                          dj.a(paramdg, localdb.b);
                          continue;
                          if (localdb.b == 11)
                          {
                            parambp.i = paramdg.z();
                            parambp.i(true);
                          }
                          else
                          {
                            dj.a(paramdg, localdb.b);
                            continue;
                            if (localdb.b == 8)
                            {
                              parambp.j = paramdg.w();
                              parambp.j(true);
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
      if (!parambp.r())
        throw new dh("Required field 'ts_secs' was not found in serialized data! Struct: " + toString());
      if (!parambp.u())
        throw new dh("Required field 'length' was not found in serialized data! Struct: " + toString());
      parambp.I();
    }

    public void b(dg paramdg, bp parambp)
      throws cn
    {
      parambp.I();
      paramdg.a(bp.J());
      if (parambp.a != null)
      {
        paramdg.a(bp.K());
        paramdg.a(parambp.a);
        paramdg.c();
      }
      if (parambp.b != null)
      {
        paramdg.a(bp.L());
        paramdg.a(parambp.b);
        paramdg.c();
      }
      if (parambp.c != null)
      {
        paramdg.a(bp.M());
        paramdg.a(parambp.c);
        paramdg.c();
      }
      paramdg.a(bp.N());
      paramdg.a(parambp.d);
      paramdg.c();
      paramdg.a(bp.O());
      paramdg.a(parambp.e);
      paramdg.c();
      paramdg.a(bp.P());
      paramdg.a(parambp.f);
      paramdg.c();
      if (parambp.g != null)
      {
        paramdg.a(bp.Q());
        paramdg.a(parambp.g);
        paramdg.c();
      }
      if (parambp.h != null)
      {
        paramdg.a(bp.R());
        paramdg.a(parambp.h);
        paramdg.c();
      }
      if (parambp.i != null)
      {
        paramdg.a(bp.S());
        paramdg.a(parambp.i);
        paramdg.c();
      }
      if (parambp.H())
      {
        paramdg.a(bp.T());
        paramdg.a(parambp.j);
        paramdg.c();
      }
      paramdg.d();
      paramdg.b();
    }
  }

  private static class b
    implements dp
  {
    public bp.a a()
    {
      return new bp.a(null);
    }
  }

  private static class c extends dr<bp>
  {
    public void a(dg paramdg, bp parambp)
      throws cn
    {
      dm localdm = (dm)paramdg;
      localdm.a(parambp.a);
      localdm.a(parambp.b);
      localdm.a(parambp.c);
      localdm.a(parambp.d);
      localdm.a(parambp.e);
      localdm.a(parambp.f);
      localdm.a(parambp.g);
      localdm.a(parambp.h);
      localdm.a(parambp.i);
      BitSet localBitSet = new BitSet();
      if (parambp.H())
        localBitSet.set(0);
      localdm.a(localBitSet, 1);
      if (parambp.H())
        localdm.a(parambp.j);
    }

    public void b(dg paramdg, bp parambp)
      throws cn
    {
      dm localdm = (dm)paramdg;
      parambp.a = localdm.z();
      parambp.a(true);
      parambp.b = localdm.z();
      parambp.b(true);
      parambp.c = localdm.z();
      parambp.c(true);
      parambp.d = localdm.w();
      parambp.d(true);
      parambp.e = localdm.w();
      parambp.e(true);
      parambp.f = localdm.w();
      parambp.f(true);
      parambp.g = localdm.A();
      parambp.g(true);
      parambp.h = localdm.z();
      parambp.h(true);
      parambp.i = localdm.z();
      parambp.i(true);
      if (localdm.b(1).get(0))
      {
        parambp.j = localdm.w();
        parambp.j(true);
      }
    }
  }

  private static class d
    implements dp
  {
    public bp.c a()
    {
      return new bp.c(null);
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
 * Qualified Name:     u.aly.bp
 * JD-Core Version:    0.6.2
 */