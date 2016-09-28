package com.sohu.app.ads.sdkcodec;

public class Base64 extends BaseNCodec
{
  static final byte[] a = { 13, 10 };
  private static final byte[] i = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
  private static final byte[] j = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95 };
  private static final byte[] k = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51 };
  private final byte[] l;
  private final byte[] m;
  private final byte[] n;
  private final int o;
  private final int p;
  private int q;

  public Base64()
  {
    this(0);
  }

  public Base64(int paramInt)
  {
    this(paramInt, a);
  }

  public Base64(int paramInt, byte[] paramArrayOfByte)
  {
    this(paramInt, paramArrayOfByte, false);
  }

  public Base64(int paramInt, byte[] paramArrayOfByte, boolean paramBoolean)
  {
  }

  public static byte[] a(byte[] paramArrayOfByte)
  {
    return new Base64().b(paramArrayOfByte);
  }

  void a(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (this.f)
      return;
    if (paramInt2 < 0)
      this.f = true;
    int i1 = 0;
    while (true)
    {
      int i5;
      int i6;
      if (i1 < paramInt2)
      {
        a(this.o);
        i5 = paramInt1 + 1;
        i6 = paramArrayOfByte[paramInt1];
        if (i6 == 61)
          this.f = true;
      }
      else
      {
        if ((!this.f) || (this.h == 0))
          break;
        a(this.o);
      }
      switch (this.h)
      {
      default:
        return;
      case 2:
        this.q >>= 4;
        byte[] arrayOfByte3 = this.d;
        int i4 = this.e;
        this.e = (i4 + 1);
        arrayOfByte3[i4] = ((byte)(0xFF & this.q));
        return;
        if ((i6 >= 0) && (i6 < k.length))
        {
          int i7 = k[i6];
          if (i7 >= 0)
          {
            this.h = ((1 + this.h) % 4);
            this.q = (i7 + (this.q << 6));
            if (this.h == 0)
            {
              byte[] arrayOfByte4 = this.d;
              int i8 = this.e;
              this.e = (i8 + 1);
              arrayOfByte4[i8] = ((byte)(0xFF & this.q >> 16));
              byte[] arrayOfByte5 = this.d;
              int i9 = this.e;
              this.e = (i9 + 1);
              arrayOfByte5[i9] = ((byte)(0xFF & this.q >> 8));
              byte[] arrayOfByte6 = this.d;
              int i10 = this.e;
              this.e = (i10 + 1);
              arrayOfByte6[i10] = ((byte)(0xFF & this.q));
            }
          }
        }
        i1++;
        paramInt1 = i5;
      case 3:
      }
    }
    this.q >>= 2;
    byte[] arrayOfByte1 = this.d;
    int i2 = this.e;
    this.e = (i2 + 1);
    arrayOfByte1[i2] = ((byte)(0xFF & this.q >> 8));
    byte[] arrayOfByte2 = this.d;
    int i3 = this.e;
    this.e = (i3 + 1);
    arrayOfByte2[i3] = ((byte)(0xFF & this.q));
  }

  protected boolean a(byte paramByte)
  {
    return (paramByte >= 0) && (paramByte < this.m.length) && (this.m[paramByte] != -1);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.app.ads.sdkcodec.Base64
 * JD-Core Version:    0.6.2
 */