package u.aly;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

public class ck
{
  private final dg a;
  private final dt b = new dt();

  public ck()
  {
    this(new da.a());
  }

  public ck(di paramdi)
  {
    this.a = paramdi.a(this.b);
  }

  private Object a(byte paramByte, byte[] paramArrayOfByte, co paramco, co[] paramArrayOfco)
    throws cn
  {
    try
    {
      db localdb = j(paramArrayOfByte, paramco, paramArrayOfco);
      if (localdb != null)
        switch (paramByte)
        {
        default:
        case 2:
        case 3:
        case 4:
        case 6:
        case 8:
        case 10:
        case 11:
        case 100:
        }
      do
      {
        do
        {
          do
          {
            do
            {
              do
              {
                do
                {
                  do
                  {
                    do
                      return null;
                    while (localdb.b != 2);
                    Boolean localBoolean = Boolean.valueOf(this.a.t());
                    return localBoolean;
                  }
                  while (localdb.b != 3);
                  Byte localByte = Byte.valueOf(this.a.u());
                  return localByte;
                }
                while (localdb.b != 4);
                Double localDouble = Double.valueOf(this.a.y());
                return localDouble;
              }
              while (localdb.b != 6);
              Short localShort = Short.valueOf(this.a.v());
              return localShort;
            }
            while (localdb.b != 8);
            Integer localInteger = Integer.valueOf(this.a.w());
            return localInteger;
          }
          while (localdb.b != 10);
          Long localLong = Long.valueOf(this.a.x());
          return localLong;
        }
        while (localdb.b != 11);
        String str = this.a.z();
        return str;
      }
      while (localdb.b != 11);
      ByteBuffer localByteBuffer = this.a.A();
      return localByteBuffer;
    }
    catch (Exception localException)
    {
      throw new cn(localException);
    }
    finally
    {
      this.b.e();
      this.a.B();
    }
  }

  private db j(byte[] paramArrayOfByte, co paramco, co[] paramArrayOfco)
    throws cn
  {
    int i = 0;
    this.b.a(paramArrayOfByte);
    co[] arrayOfco = new co[1 + paramArrayOfco.length];
    arrayOfco[0] = paramco;
    for (int j = 0; j < paramArrayOfco.length; j++)
      arrayOfco[(j + 1)] = paramArrayOfco[j];
    this.a.j();
    db localdb = null;
    while (i < arrayOfco.length)
    {
      localdb = this.a.l();
      if ((localdb.b == 0) || (localdb.c > arrayOfco[i].a()))
        return null;
      if (localdb.c != arrayOfco[i].a())
      {
        dj.a(this.a, localdb.b);
        this.a.m();
      }
      else
      {
        i++;
        if (i < arrayOfco.length)
          this.a.j();
      }
    }
    return localdb;
  }

  public Boolean a(byte[] paramArrayOfByte, co paramco, co[] paramArrayOfco)
    throws cn
  {
    return (Boolean)a((byte)2, paramArrayOfByte, paramco, paramArrayOfco);
  }

  public void a(ch paramch, String paramString)
    throws cn
  {
    a(paramch, paramString.getBytes());
  }

  public void a(ch paramch, String paramString1, String paramString2)
    throws cn
  {
    try
    {
      a(paramch, paramString1.getBytes(paramString2));
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new cn("JVM DOES NOT SUPPORT ENCODING: " + paramString2);
    }
    finally
    {
      this.a.B();
    }
  }

  public void a(ch paramch, byte[] paramArrayOfByte)
    throws cn
  {
    try
    {
      this.b.a(paramArrayOfByte);
      paramch.a(this.a);
      return;
    }
    finally
    {
      this.b.e();
      this.a.B();
    }
  }

  public void a(ch paramch, byte[] paramArrayOfByte, co paramco, co[] paramArrayOfco)
    throws cn
  {
    try
    {
      if (j(paramArrayOfByte, paramco, paramArrayOfco) != null)
        paramch.a(this.a);
      return;
    }
    catch (Exception localException)
    {
      throw new cn(localException);
    }
    finally
    {
      this.b.e();
      this.a.B();
    }
  }

  public Byte b(byte[] paramArrayOfByte, co paramco, co[] paramArrayOfco)
    throws cn
  {
    return (Byte)a((byte)3, paramArrayOfByte, paramco, paramArrayOfco);
  }

  public Double c(byte[] paramArrayOfByte, co paramco, co[] paramArrayOfco)
    throws cn
  {
    return (Double)a((byte)4, paramArrayOfByte, paramco, paramArrayOfco);
  }

  public Short d(byte[] paramArrayOfByte, co paramco, co[] paramArrayOfco)
    throws cn
  {
    return (Short)a((byte)6, paramArrayOfByte, paramco, paramArrayOfco);
  }

  public Integer e(byte[] paramArrayOfByte, co paramco, co[] paramArrayOfco)
    throws cn
  {
    return (Integer)a((byte)8, paramArrayOfByte, paramco, paramArrayOfco);
  }

  public Long f(byte[] paramArrayOfByte, co paramco, co[] paramArrayOfco)
    throws cn
  {
    return (Long)a((byte)10, paramArrayOfByte, paramco, paramArrayOfco);
  }

  public String g(byte[] paramArrayOfByte, co paramco, co[] paramArrayOfco)
    throws cn
  {
    return (String)a((byte)11, paramArrayOfByte, paramco, paramArrayOfco);
  }

  public ByteBuffer h(byte[] paramArrayOfByte, co paramco, co[] paramArrayOfco)
    throws cn
  {
    return (ByteBuffer)a((byte)100, paramArrayOfByte, paramco, paramArrayOfco);
  }

  public Short i(byte[] paramArrayOfByte, co paramco, co[] paramArrayOfco)
    throws cn
  {
    try
    {
      if (j(paramArrayOfByte, paramco, paramArrayOfco) != null)
      {
        this.a.j();
        Short localShort = Short.valueOf(this.a.l().c);
        return localShort;
      }
      return null;
    }
    catch (Exception localException)
    {
      throw new cn(localException);
    }
    finally
    {
      this.b.e();
      this.a.B();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.ck
 * JD-Core Version:    0.6.2
 */