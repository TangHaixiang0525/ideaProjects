package u.aly;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class cz extends dg
{
  protected static final int a = -65536;
  protected static final int b = -2147418112;
  private static final dl h = new dl();
  protected boolean c = false;
  protected boolean d = true;
  protected int e;
  protected boolean f = false;
  private byte[] i = new byte[1];
  private byte[] j = new byte[2];
  private byte[] k = new byte[4];
  private byte[] l = new byte[8];
  private byte[] m = new byte[1];
  private byte[] n = new byte[2];
  private byte[] o = new byte[4];
  private byte[] p = new byte[8];

  public cz(du paramdu)
  {
    this(paramdu, false, true);
  }

  public cz(du paramdu, boolean paramBoolean1, boolean paramBoolean2)
  {
    super(paramdu);
    this.c = paramBoolean1;
    this.d = paramBoolean2;
  }

  private int a(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws cn
  {
    d(paramInt2);
    return this.g.d(paramArrayOfByte, paramInt1, paramInt2);
  }

  public ByteBuffer A()
    throws cn
  {
    int i1 = w();
    d(i1);
    if (this.g.h() >= i1)
    {
      ByteBuffer localByteBuffer = ByteBuffer.wrap(this.g.f(), this.g.g(), i1);
      this.g.a(i1);
      return localByteBuffer;
    }
    byte[] arrayOfByte = new byte[i1];
    this.g.d(arrayOfByte, 0, i1);
    return ByteBuffer.wrap(arrayOfByte);
  }

  public void a()
  {
  }

  public void a(byte paramByte)
    throws cn
  {
    this.i[0] = paramByte;
    this.g.b(this.i, 0, 1);
  }

  public void a(double paramDouble)
    throws cn
  {
    a(Double.doubleToLongBits(paramDouble));
  }

  public void a(int paramInt)
    throws cn
  {
    this.k[0] = ((byte)(0xFF & paramInt >> 24));
    this.k[1] = ((byte)(0xFF & paramInt >> 16));
    this.k[2] = ((byte)(0xFF & paramInt >> 8));
    this.k[3] = ((byte)(paramInt & 0xFF));
    this.g.b(this.k, 0, 4);
  }

  public void a(long paramLong)
    throws cn
  {
    this.l[0] = ((byte)(int)(0xFF & paramLong >> 56));
    this.l[1] = ((byte)(int)(0xFF & paramLong >> 48));
    this.l[2] = ((byte)(int)(0xFF & paramLong >> 40));
    this.l[3] = ((byte)(int)(0xFF & paramLong >> 32));
    this.l[4] = ((byte)(int)(0xFF & paramLong >> 24));
    this.l[5] = ((byte)(int)(0xFF & paramLong >> 16));
    this.l[6] = ((byte)(int)(0xFF & paramLong >> 8));
    this.l[7] = ((byte)(int)(0xFF & paramLong));
    this.g.b(this.l, 0, 8);
  }

  public void a(String paramString)
    throws cn
  {
    try
    {
      byte[] arrayOfByte = paramString.getBytes("UTF-8");
      a(arrayOfByte.length);
      this.g.b(arrayOfByte, 0, arrayOfByte.length);
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
    }
    throw new cn("JVM DOES NOT SUPPORT UTF-8");
  }

  public void a(ByteBuffer paramByteBuffer)
    throws cn
  {
    int i1 = paramByteBuffer.limit() - paramByteBuffer.position();
    a(i1);
    this.g.b(paramByteBuffer.array(), paramByteBuffer.position() + paramByteBuffer.arrayOffset(), i1);
  }

  public void a(db paramdb)
    throws cn
  {
    a(paramdb.b);
    a(paramdb.c);
  }

  public void a(dc paramdc)
    throws cn
  {
    a(paramdc.a);
    a(paramdc.b);
  }

  public void a(dd paramdd)
    throws cn
  {
    a(paramdd.a);
    a(paramdd.b);
    a(paramdd.c);
  }

  public void a(de paramde)
    throws cn
  {
    if (this.d)
    {
      a(0x80010000 | paramde.b);
      a(paramde.a);
      a(paramde.c);
      return;
    }
    a(paramde.a);
    a(paramde.b);
    a(paramde.c);
  }

  public void a(dk paramdk)
    throws cn
  {
    a(paramdk.a);
    a(paramdk.b);
  }

  public void a(dl paramdl)
  {
  }

  public void a(short paramShort)
    throws cn
  {
    this.j[0] = ((byte)(0xFF & paramShort >> 8));
    this.j[1] = ((byte)(paramShort & 0xFF));
    this.g.b(this.j, 0, 2);
  }

  public void a(boolean paramBoolean)
    throws cn
  {
    if (paramBoolean);
    for (byte b1 = 1; ; b1 = 0)
    {
      a(b1);
      return;
    }
  }

  public String b(int paramInt)
    throws cn
  {
    try
    {
      d(paramInt);
      byte[] arrayOfByte = new byte[paramInt];
      this.g.d(arrayOfByte, 0, paramInt);
      String str = new String(arrayOfByte, "UTF-8");
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
    }
    throw new cn("JVM DOES NOT SUPPORT UTF-8");
  }

  public void b()
  {
  }

  public void c()
  {
  }

  public void c(int paramInt)
  {
    this.e = paramInt;
    this.f = true;
  }

  public void d()
    throws cn
  {
    a((byte)0);
  }

  protected void d(int paramInt)
    throws cn
  {
    if (paramInt < 0)
      throw new dh("Negative length: " + paramInt);
    if (this.f)
    {
      this.e -= paramInt;
      if (this.e < 0)
        throw new dh("Message length exceeded: " + paramInt);
    }
  }

  public void e()
  {
  }

  public void f()
  {
  }

  public void g()
  {
  }

  public de h()
    throws cn
  {
    int i1 = w();
    if (i1 < 0)
    {
      if ((0xFFFF0000 & i1) != -2147418112)
        throw new dh(4, "Bad version in readMessageBegin");
      return new de(z(), (byte)(i1 & 0xFF), w());
    }
    if (this.c)
      throw new dh(4, "Missing version in readMessageBegin, old client?");
    return new de(b(i1), u(), w());
  }

  public void i()
  {
  }

  public dl j()
  {
    return h;
  }

  public void k()
  {
  }

  public db l()
    throws cn
  {
    byte b1 = u();
    if (b1 == 0);
    for (short s = 0; ; s = v())
      return new db("", b1, s);
  }

  public void m()
  {
  }

  public dd n()
    throws cn
  {
    return new dd(u(), u(), w());
  }

  public void o()
  {
  }

  public dc p()
    throws cn
  {
    return new dc(u(), w());
  }

  public void q()
  {
  }

  public dk r()
    throws cn
  {
    return new dk(u(), w());
  }

  public void s()
  {
  }

  public boolean t()
    throws cn
  {
    return u() == 1;
  }

  public byte u()
    throws cn
  {
    if (this.g.h() >= 1)
    {
      byte b1 = this.g.f()[this.g.g()];
      this.g.a(1);
      return b1;
    }
    a(this.m, 0, 1);
    return this.m[0];
  }

  public short v()
    throws cn
  {
    byte[] arrayOfByte = this.n;
    int i1;
    if (this.g.h() >= 2)
    {
      arrayOfByte = this.g.f();
      i1 = this.g.g();
      this.g.a(2);
    }
    while (true)
    {
      return (short)((0xFF & arrayOfByte[i1]) << 8 | 0xFF & arrayOfByte[(i1 + 1)]);
      a(this.n, 0, 2);
      i1 = 0;
    }
  }

  public int w()
    throws cn
  {
    byte[] arrayOfByte = this.o;
    int i1;
    if (this.g.h() >= 4)
    {
      arrayOfByte = this.g.f();
      i1 = this.g.g();
      this.g.a(4);
    }
    while (true)
    {
      return (0xFF & arrayOfByte[i1]) << 24 | (0xFF & arrayOfByte[(i1 + 1)]) << 16 | (0xFF & arrayOfByte[(i1 + 2)]) << 8 | 0xFF & arrayOfByte[(i1 + 3)];
      a(this.o, 0, 4);
      i1 = 0;
    }
  }

  public long x()
    throws cn
  {
    byte[] arrayOfByte = this.p;
    int i1;
    if (this.g.h() >= 8)
    {
      arrayOfByte = this.g.f();
      i1 = this.g.g();
      this.g.a(8);
    }
    while (true)
    {
      return (0xFF & arrayOfByte[i1]) << 56 | (0xFF & arrayOfByte[(i1 + 1)]) << 48 | (0xFF & arrayOfByte[(i1 + 2)]) << 40 | (0xFF & arrayOfByte[(i1 + 3)]) << 32 | (0xFF & arrayOfByte[(i1 + 4)]) << 24 | (0xFF & arrayOfByte[(i1 + 5)]) << 16 | (0xFF & arrayOfByte[(i1 + 6)]) << 8 | 0xFF & arrayOfByte[(i1 + 7)];
      a(this.p, 0, 8);
      i1 = 0;
    }
  }

  public double y()
    throws cn
  {
    return Double.longBitsToDouble(x());
  }

  public String z()
    throws cn
  {
    int i1 = w();
    if (this.g.h() >= i1)
      try
      {
        String str = new String(this.g.f(), this.g.g(), i1, "UTF-8");
        this.g.a(i1);
        return str;
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        throw new cn("JVM DOES NOT SUPPORT UTF-8");
      }
    return b(i1);
  }

  public static class a
    implements di
  {
    protected boolean a = false;
    protected boolean b = true;
    protected int c;

    public a()
    {
      this(false, true);
    }

    public a(boolean paramBoolean1, boolean paramBoolean2)
    {
      this(paramBoolean1, paramBoolean2, 0);
    }

    public a(boolean paramBoolean1, boolean paramBoolean2, int paramInt)
    {
      this.a = paramBoolean1;
      this.b = paramBoolean2;
      this.c = paramInt;
    }

    public dg a(du paramdu)
    {
      cz localcz = new cz(paramdu, this.a, this.b);
      if (this.c != 0)
        localcz.c(this.c);
      return localcz;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.cz
 * JD-Core Version:    0.6.2
 */