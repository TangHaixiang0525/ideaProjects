package u.aly;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class da extends dg
{
  private static final dl d = new dl("");
  private static final db e = new db("", (byte)0, (short)0);
  private static final byte[] f = new byte[16];
  private static final byte h = -126;
  private static final byte i = 1;
  private static final byte j = 31;
  private static final byte k = -32;
  private static final int l = 5;
  byte[] a = new byte[5];
  byte[] b = new byte[10];
  byte[] c = new byte[1];
  private cf m = new cf(15);
  private short n = 0;
  private db o = null;
  private Boolean p = null;
  private final long q;
  private byte[] r = new byte[1];

  static
  {
    f[0] = 0;
    f[2] = 1;
    f[3] = 3;
    f[6] = 4;
    f[8] = 5;
    f[10] = 6;
    f[4] = 7;
    f[11] = 8;
    f[15] = 9;
    f[14] = 10;
    f[13] = 11;
    f[12] = 12;
  }

  public da(du paramdu)
  {
    this(paramdu, -1L);
  }

  public da(du paramdu, long paramLong)
  {
    super(paramdu);
    this.q = paramLong;
  }

  private int E()
    throws cn
  {
    int i1 = 0;
    int i2;
    if (this.g.h() >= 5)
    {
      byte[] arrayOfByte = this.g.f();
      int i5 = this.g.g();
      int i6 = 0;
      int i7 = 0;
      while (true)
      {
        int i8 = arrayOfByte[(i5 + i1)];
        i7 |= (i8 & 0x7F) << i6;
        if ((i8 & 0x80) != 128)
        {
          this.g.a(i1 + 1);
          return i7;
        }
        i6 += 7;
        i1++;
      }
      i2 += 7;
    }
    while (true)
    {
      int i4 = u();
      i3 |= (i4 & 0x7F) << i2;
      if ((i4 & 0x80) == 128)
        break;
      return i3;
      i2 = 0;
      int i3 = 0;
    }
  }

  private long F()
    throws cn
  {
    long l1 = 0L;
    int i1 = this.g.h();
    int i2 = 0;
    if (i1 >= 10)
    {
      byte[] arrayOfByte = this.g.f();
      int i4 = this.g.g();
      int i5 = 0;
      while (true)
      {
        int i6 = arrayOfByte[(i4 + i2)];
        l1 |= (i6 & 0x7F) << i5;
        if ((i6 & 0x80) != 128)
        {
          this.g.a(i2 + 1);
          return l1;
        }
        i5 += 7;
        i2++;
      }
    }
    int i3;
    do
    {
      i2 += 7;
      i3 = u();
      l1 |= (i3 & 0x7F) << i2;
    }
    while ((i3 & 0x80) == 128);
    return l1;
  }

  private long a(byte[] paramArrayOfByte)
  {
    return (0xFF & paramArrayOfByte[7]) << 56 | (0xFF & paramArrayOfByte[6]) << 48 | (0xFF & paramArrayOfByte[5]) << 40 | (0xFF & paramArrayOfByte[4]) << 32 | (0xFF & paramArrayOfByte[3]) << 24 | (0xFF & paramArrayOfByte[2]) << 16 | (0xFF & paramArrayOfByte[1]) << 8 | 0xFF & paramArrayOfByte[0];
  }

  private void a(long paramLong, byte[] paramArrayOfByte, int paramInt)
  {
    paramArrayOfByte[(paramInt + 0)] = ((byte)(int)(paramLong & 0xFF));
    paramArrayOfByte[(paramInt + 1)] = ((byte)(int)(0xFF & paramLong >> 8));
    paramArrayOfByte[(paramInt + 2)] = ((byte)(int)(0xFF & paramLong >> 16));
    paramArrayOfByte[(paramInt + 3)] = ((byte)(int)(0xFF & paramLong >> 24));
    paramArrayOfByte[(paramInt + 4)] = ((byte)(int)(0xFF & paramLong >> 32));
    paramArrayOfByte[(paramInt + 5)] = ((byte)(int)(0xFF & paramLong >> 40));
    paramArrayOfByte[(paramInt + 6)] = ((byte)(int)(0xFF & paramLong >> 48));
    paramArrayOfByte[(paramInt + 7)] = ((byte)(int)(0xFF & paramLong >> 56));
  }

  private void a(db paramdb, byte paramByte)
    throws cn
  {
    if (paramByte == -1)
      paramByte = e(paramdb.b);
    if ((paramdb.c > this.n) && (paramdb.c - this.n <= 15))
      d(paramByte | paramdb.c - this.n << 4);
    while (true)
    {
      this.n = paramdb.c;
      return;
      b(paramByte);
      a(paramdb.c);
    }
  }

  private void a(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws cn
  {
    b(paramInt2);
    this.g.b(paramArrayOfByte, paramInt1, paramInt2);
  }

  private void b(byte paramByte)
    throws cn
  {
    this.r[0] = paramByte;
    this.g.b(this.r);
  }

  private void b(int paramInt)
    throws cn
  {
    int i2;
    for (int i1 = 0; ; i1 = i2)
    {
      if ((paramInt & 0xFFFFFF80) == 0)
      {
        byte[] arrayOfByte2 = this.a;
        int i3 = i1 + 1;
        arrayOfByte2[i1] = ((byte)paramInt);
        this.g.b(this.a, 0, i3);
        return;
      }
      byte[] arrayOfByte1 = this.a;
      i2 = i1 + 1;
      arrayOfByte1[i1] = ((byte)(0x80 | paramInt & 0x7F));
      paramInt >>>= 7;
    }
  }

  private void b(long paramLong)
    throws cn
  {
    int i2;
    for (int i1 = 0; ; i1 = i2)
    {
      if ((0xFFFFFF80 & paramLong) == 0L)
      {
        byte[] arrayOfByte2 = this.b;
        int i3 = i1 + 1;
        arrayOfByte2[i1] = ((byte)(int)paramLong);
        this.g.b(this.b, 0, i3);
        return;
      }
      byte[] arrayOfByte1 = this.b;
      i2 = i1 + 1;
      arrayOfByte1[i1] = ((byte)(int)(0x80 | 0x7F & paramLong));
      paramLong >>>= 7;
    }
  }

  private int c(int paramInt)
  {
    return paramInt << 1 ^ paramInt >> 31;
  }

  private long c(long paramLong)
  {
    return paramLong << 1 ^ paramLong >> 63;
  }

  private boolean c(byte paramByte)
  {
    int i1 = paramByte & 0xF;
    return (i1 == 1) || (i1 == 2);
  }

  private byte d(byte paramByte)
    throws dh
  {
    switch ((byte)(paramByte & 0xF))
    {
    default:
      throw new dh("don't know what type: " + (byte)(paramByte & 0xF));
    case 0:
      return 0;
    case 1:
    case 2:
      return 2;
    case 3:
      return 3;
    case 4:
      return 6;
    case 5:
      return 8;
    case 6:
      return 10;
    case 7:
      return 4;
    case 8:
      return 11;
    case 9:
      return 15;
    case 10:
      return 14;
    case 11:
      return 13;
    case 12:
    }
    return 12;
  }

  private long d(long paramLong)
  {
    return paramLong >>> 1 ^ -(1L & paramLong);
  }

  private void d(int paramInt)
    throws cn
  {
    b((byte)paramInt);
  }

  private byte e(byte paramByte)
  {
    return f[paramByte];
  }

  private byte[] e(int paramInt)
    throws cn
  {
    if (paramInt == 0)
      return new byte[0];
    byte[] arrayOfByte = new byte[paramInt];
    this.g.d(arrayOfByte, 0, paramInt);
    return arrayOfByte;
  }

  private void f(int paramInt)
    throws dh
  {
    if (paramInt < 0)
      throw new dh("Negative length: " + paramInt);
    if ((this.q != -1L) && (paramInt > this.q))
      throw new dh("Length exceeded max allowed: " + paramInt);
  }

  private int g(int paramInt)
  {
    return paramInt >>> 1 ^ -(paramInt & 0x1);
  }

  public ByteBuffer A()
    throws cn
  {
    int i1 = E();
    f(i1);
    if (i1 == 0)
      return ByteBuffer.wrap(new byte[0]);
    byte[] arrayOfByte = new byte[i1];
    this.g.d(arrayOfByte, 0, i1);
    return ByteBuffer.wrap(arrayOfByte);
  }

  public void B()
  {
    this.m.c();
    this.n = 0;
  }

  public void a()
    throws cn
  {
  }

  public void a(byte paramByte)
    throws cn
  {
    b(paramByte);
  }

  protected void a(byte paramByte, int paramInt)
    throws cn
  {
    if (paramInt <= 14)
    {
      d(paramInt << 4 | e(paramByte));
      return;
    }
    d(0xF0 | e(paramByte));
    b(paramInt);
  }

  public void a(double paramDouble)
    throws cn
  {
    byte[] arrayOfByte = { 0, 0, 0, 0, 0, 0, 0, 0 };
    a(Double.doubleToLongBits(paramDouble), arrayOfByte, 0);
    this.g.b(arrayOfByte);
  }

  public void a(int paramInt)
    throws cn
  {
    b(c(paramInt));
  }

  public void a(long paramLong)
    throws cn
  {
    b(c(paramLong));
  }

  public void a(String paramString)
    throws cn
  {
    try
    {
      byte[] arrayOfByte = paramString.getBytes("UTF-8");
      a(arrayOfByte, 0, arrayOfByte.length);
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
    }
    throw new cn("UTF-8 not supported!");
  }

  public void a(ByteBuffer paramByteBuffer)
    throws cn
  {
    int i1 = paramByteBuffer.limit() - paramByteBuffer.position();
    a(paramByteBuffer.array(), paramByteBuffer.position() + paramByteBuffer.arrayOffset(), i1);
  }

  public void a(db paramdb)
    throws cn
  {
    if (paramdb.b == 2)
    {
      this.o = paramdb;
      return;
    }
    a(paramdb, (byte)-1);
  }

  public void a(dc paramdc)
    throws cn
  {
    a(paramdc.a, paramdc.b);
  }

  public void a(dd paramdd)
    throws cn
  {
    if (paramdd.c == 0)
    {
      d(0);
      return;
    }
    b(paramdd.c);
    d(e(paramdd.a) << 4 | e(paramdd.b));
  }

  public void a(de paramde)
    throws cn
  {
    b((byte)-126);
    d(0x1 | 0xFFFFFFE0 & paramde.b << 5);
    b(paramde.c);
    a(paramde.a);
  }

  public void a(dk paramdk)
    throws cn
  {
    a(paramdk.a, paramdk.b);
  }

  public void a(dl paramdl)
    throws cn
  {
    this.m.a(this.n);
    this.n = 0;
  }

  public void a(short paramShort)
    throws cn
  {
    b(c(paramShort));
  }

  public void a(boolean paramBoolean)
    throws cn
  {
    byte b1 = 1;
    if (this.o != null)
    {
      db localdb = this.o;
      if (paramBoolean);
      while (true)
      {
        a(localdb, b1);
        this.o = null;
        return;
        b1 = 2;
      }
    }
    if (paramBoolean);
    while (true)
    {
      b(b1);
      return;
      b1 = 2;
    }
  }

  public void b()
    throws cn
  {
    this.n = this.m.a();
  }

  public void c()
    throws cn
  {
  }

  public void d()
    throws cn
  {
    b((byte)0);
  }

  public void e()
    throws cn
  {
  }

  public void f()
    throws cn
  {
  }

  public void g()
    throws cn
  {
  }

  public de h()
    throws cn
  {
    int i1 = u();
    if (i1 != -126)
      throw new dh("Expected protocol id " + Integer.toHexString(-126) + " but got " + Integer.toHexString(i1));
    int i2 = u();
    int i3 = (byte)(i2 & 0x1F);
    if (i3 != 1)
      throw new dh("Expected version 1 but got " + i3);
    byte b1 = (byte)(0x3 & i2 >> 5);
    int i4 = E();
    return new de(z(), b1, i4);
  }

  public void i()
    throws cn
  {
  }

  public dl j()
    throws cn
  {
    this.m.a(this.n);
    this.n = 0;
    return d;
  }

  public void k()
    throws cn
  {
    this.n = this.m.a();
  }

  public db l()
    throws cn
  {
    int i1 = u();
    if (i1 == 0)
      return e;
    int i2 = (short)((i1 & 0xF0) >> 4);
    short s;
    db localdb;
    if (i2 == 0)
    {
      s = v();
      localdb = new db("", d((byte)(i1 & 0xF)), s);
      if (c(i1))
        if ((byte)(i1 & 0xF) != 1)
          break label103;
    }
    label103: for (Boolean localBoolean = Boolean.TRUE; ; localBoolean = Boolean.FALSE)
    {
      this.p = localBoolean;
      this.n = localdb.c;
      return localdb;
      s = (short)(i2 + this.n);
      break;
    }
  }

  public void m()
    throws cn
  {
  }

  public dd n()
    throws cn
  {
    int i1 = E();
    if (i1 == 0);
    for (int i2 = 0; ; i2 = u())
      return new dd(d((byte)(i2 >> 4)), d((byte)(i2 & 0xF)), i1);
  }

  public void o()
    throws cn
  {
  }

  public dc p()
    throws cn
  {
    byte b1 = u();
    int i1 = 0xF & b1 >> 4;
    if (i1 == 15)
      i1 = E();
    return new dc(d(b1), i1);
  }

  public void q()
    throws cn
  {
  }

  public dk r()
    throws cn
  {
    return new dk(p());
  }

  public void s()
    throws cn
  {
  }

  public boolean t()
    throws cn
  {
    int i1 = 1;
    if (this.p != null)
    {
      i1 = this.p.booleanValue();
      this.p = null;
    }
    while (u() == i1)
      return i1;
    return false;
  }

  public byte u()
    throws cn
  {
    if (this.g.h() > 0)
    {
      byte b1 = this.g.f()[this.g.g()];
      this.g.a(1);
      return b1;
    }
    this.g.d(this.c, 0, 1);
    return this.c[0];
  }

  public short v()
    throws cn
  {
    return (short)g(E());
  }

  public int w()
    throws cn
  {
    return g(E());
  }

  public long x()
    throws cn
  {
    return d(F());
  }

  public double y()
    throws cn
  {
    byte[] arrayOfByte = new byte[8];
    this.g.d(arrayOfByte, 0, 8);
    return Double.longBitsToDouble(a(arrayOfByte));
  }

  public String z()
    throws cn
  {
    int i1 = E();
    f(i1);
    if (i1 == 0)
      return "";
    try
    {
      if (this.g.h() >= i1)
      {
        String str1 = new String(this.g.f(), this.g.g(), i1, "UTF-8");
        this.g.a(i1);
        return str1;
      }
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new cn("UTF-8 not supported!");
    }
    String str2 = new String(e(i1), "UTF-8");
    return str2;
  }

  public static class a
    implements di
  {
    private final long a;

    public a()
    {
      this.a = -1L;
    }

    public a(int paramInt)
    {
      this.a = paramInt;
    }

    public dg a(du paramdu)
    {
      return new da(paramdu, this.a);
    }
  }

  private static class b
  {
    public static final byte a = 1;
    public static final byte b = 2;
    public static final byte c = 3;
    public static final byte d = 4;
    public static final byte e = 5;
    public static final byte f = 6;
    public static final byte g = 7;
    public static final byte h = 8;
    public static final byte i = 9;
    public static final byte j = 10;
    public static final byte k = 11;
    public static final byte l = 12;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.da
 * JD-Core Version:    0.6.2
 */