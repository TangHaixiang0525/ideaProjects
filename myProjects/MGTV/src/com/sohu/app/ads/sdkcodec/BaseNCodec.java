package com.sohu.app.ads.sdkcodec;

public abstract class BaseNCodec
{
  private final int a;
  protected final byte b = 61;
  protected final int c;
  protected byte[] d;
  protected int e;
  protected boolean f;
  protected int g;
  protected int h;
  private final int i;
  private final int j;
  private int k;

  protected BaseNCodec(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.a = paramInt1;
    this.i = paramInt2;
    if ((paramInt3 > 0) && (paramInt4 > 0));
    for (int m = paramInt2 * (paramInt3 / paramInt2); ; m = 0)
    {
      this.c = m;
      this.j = paramInt4;
      return;
    }
  }

  private void c()
  {
    if (this.d == null)
    {
      this.d = new byte[b()];
      this.e = 0;
      this.k = 0;
      return;
    }
    byte[] arrayOfByte = new byte[2 * this.d.length];
    System.arraycopy(this.d, 0, arrayOfByte, 0, this.d.length);
    this.d = arrayOfByte;
  }

  private void d()
  {
    this.d = null;
    this.e = 0;
    this.k = 0;
    this.g = 0;
    this.h = 0;
    this.f = false;
  }

  int a()
  {
    if (this.d != null)
      return this.e - this.k;
    return 0;
  }

  protected void a(int paramInt)
  {
    if ((this.d == null) || (this.d.length < paramInt + this.e))
      c();
  }

  abstract void a(byte[] paramArrayOfByte, int paramInt1, int paramInt2);

  protected abstract boolean a(byte paramByte);

  protected int b()
  {
    return 8192;
  }

  int b(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (this.d != null)
    {
      int m = Math.min(a(), paramInt2);
      System.arraycopy(this.d, this.k, paramArrayOfByte, paramInt1, m);
      this.k = (m + this.k);
      if (this.k >= this.e)
        this.d = null;
      return m;
    }
    if (this.f)
      return -1;
    return 0;
  }

  public byte[] b(byte[] paramArrayOfByte)
  {
    d();
    if ((paramArrayOfByte == null) || (paramArrayOfByte.length == 0))
      return paramArrayOfByte;
    a(paramArrayOfByte, 0, paramArrayOfByte.length);
    a(paramArrayOfByte, 0, -1);
    byte[] arrayOfByte = new byte[this.e];
    b(arrayOfByte, 0, arrayOfByte.length);
    return arrayOfByte;
  }

  protected boolean c(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null);
    while (true)
    {
      return false;
      int m = paramArrayOfByte.length;
      for (int n = 0; n < m; n++)
      {
        byte b1 = paramArrayOfByte[n];
        if ((61 == b1) || (a(b1)))
          return true;
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdkcodec.BaseNCodec
 * JD-Core Version:    0.6.2
 */