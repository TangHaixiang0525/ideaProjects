package com.sohu.applist.c.a;

public final class d
{
  private static final byte[] a;
  private static final int[] b;
  private static final int[] c;
  private static final int[] d;
  private static final int[] e;
  private static final byte[] f;
  private static final int[] g;
  private static final int[] h;
  private static final int[] i;
  private static final int[] j;
  private static final int[] k;
  private int l = 0;
  private int m = 0;
  private int n = 0;
  private int[] o = null;
  private int[] p = null;

  static
  {
    int i1 = 1;
    a = new byte[256];
    b = new int[256];
    c = new int[256];
    d = new int[256];
    e = new int[256];
    f = new byte[256];
    g = new int[256];
    h = new int[256];
    i = new int[256];
    j = new int[256];
    k = new int[10];
    int i2 = 0;
    int i5;
    int i6;
    if (i2 < 256)
    {
      int i4 = "捼睻濅、末ﻗꭶ쪂쥽繁䟰귔ꊯ鲤狀럽錦㘿㒥燘ㄕӇ⏃ᢖ֚ܒ胢뉵ঃⰚ᭮媠刻횳⧣⾄发í⃼녛櫋븹䩌壏탯꫻䍍㎅䗹ɿ值龨冣䂏銝㣵벶���ჿ촌Ꮼ得䐗쒧總摝ᥳ悁俜∪邈䛮렔���௛㨊䤆⑜싓걢醕㝭跕亩汖敺금멸┮Ღ듆琟䮽變瀾땦䠃愵垹蛁ᶞ頑槙躔鬞蟩칕⣟財褍뿦䉨䆙ⴏ끔묖".charAt(i2 >>> 1);
      if ((i2 & 0x1) == 0)
        i4 >>>= 8;
      i5 = 0xFF & (byte)i4;
      i6 = i5 << 1;
      if (i6 < 256)
        break label500;
    }
    label500: for (int i7 = i6 ^ 0x11B; ; i7 = i6)
    {
      int i8 = i7 ^ i5;
      int i9 = i2 << 1;
      if (i9 >= 256);
      for (int i10 = i9 ^ 0x11B; ; i10 = i9)
      {
        int i11 = i10 << 1;
        if (i11 >= 256);
        for (int i12 = i11 ^ 0x11B; ; i12 = i11)
        {
          int i13 = i12 << 1;
          if (i13 >= 256)
            i13 ^= 283;
          int i14 = i13 ^ i2;
          int i15 = i14 ^ i10;
          int i16 = i14 ^ i12;
          int i17 = i10 ^ (i13 ^ i12);
          a[i2] = ((byte)i5);
          int[] arrayOfInt1 = b;
          int i18 = i8 | (i7 << 24 | i5 << 16 | i5 << 8);
          arrayOfInt1[i2] = i18;
          c[i2] = (i18 >>> 8 | i18 << 24);
          d[i2] = (i18 >>> 16 | i18 << 16);
          e[i2] = (i18 >>> 24 | i18 << 8);
          f[i5] = ((byte)i2);
          int[] arrayOfInt2 = g;
          int i19 = i15 | (i17 << 24 | i14 << 16 | i16 << 8);
          arrayOfInt2[i5] = i19;
          h[i5] = (i19 >>> 8 | i19 << 24);
          i[i5] = (i19 >>> 16 | i19 << 16);
          j[i5] = (i19 >>> 24 | i19 << 8);
          i2++;
          break;
          k[0] = 16777216;
          int i3 = i1;
          while (i1 < 10)
          {
            i3 <<= 1;
            if (i3 >= 256)
              i3 ^= 283;
            k[i1] = (i3 << 24);
            i1++;
          }
          return;
        }
      }
    }
  }

  private void a()
  {
    int i1 = 1;
    int i2 = 4 * this.l;
    this.p[0] = this.o[i2];
    this.p[i1] = this.o[(i2 + 1)];
    this.p[2] = this.o[(i2 + 2)];
    this.p[3] = this.o[(i2 + 3)];
    int i3 = 4;
    int i4 = i2 - 4;
    while (i1 < this.l)
    {
      int i5 = this.o[i4];
      this.p[i3] = (g[(0xFF & a[(i5 >>> 24)])] ^ h[(0xFF & a[(0xFF & i5 >>> 16)])] ^ i[(0xFF & a[(0xFF & i5 >>> 8)])] ^ j[(0xFF & a[(i5 & 0xFF)])]);
      int i6 = this.o[(i4 + 1)];
      this.p[(i3 + 1)] = (g[(0xFF & a[(i6 >>> 24)])] ^ h[(0xFF & a[(0xFF & i6 >>> 16)])] ^ i[(0xFF & a[(0xFF & i6 >>> 8)])] ^ j[(0xFF & a[(i6 & 0xFF)])]);
      int i7 = this.o[(i4 + 2)];
      this.p[(i3 + 2)] = (g[(0xFF & a[(i7 >>> 24)])] ^ h[(0xFF & a[(0xFF & i7 >>> 16)])] ^ i[(0xFF & a[(0xFF & i7 >>> 8)])] ^ j[(0xFF & a[(i7 & 0xFF)])]);
      int i8 = this.o[(i4 + 3)];
      this.p[(i3 + 3)] = (g[(0xFF & a[(i8 >>> 24)])] ^ h[(0xFF & a[(0xFF & i8 >>> 16)])] ^ i[(0xFF & a[(0xFF & i8 >>> 8)])] ^ j[(0xFF & a[(i8 & 0xFF)])]);
      i3 += 4;
      i4 -= 4;
      i1++;
    }
    this.p[i3] = this.o[i4];
    this.p[(i3 + 1)] = this.o[(i4 + 1)];
    this.p[(i3 + 2)] = this.o[(i4 + 2)];
    this.p[(i3 + 3)] = this.o[(i4 + 3)];
  }

  private void a(byte[] paramArrayOfByte)
  {
    int i1 = 0;
    int i2 = 0;
    int i3 = 0;
    while (i3 < this.m)
    {
      this.o[i3] = (paramArrayOfByte[i2] << 24 | (0xFF & paramArrayOfByte[(i2 + 1)]) << 16 | (0xFF & paramArrayOfByte[(i2 + 2)]) << 8 | 0xFF & paramArrayOfByte[(i2 + 3)]);
      i3++;
      i2 += 4;
    }
    int i4 = this.m;
    int i5 = 0;
    if (i4 < this.n)
    {
      int i6 = this.o[(i4 - 1)];
      if (i1 == 0)
      {
        i1 = this.m;
        int i7 = a[(0xFF & i6 >>> 16)] << 24 | (0xFF & a[(0xFF & i6 >>> 8)]) << 16 | (0xFF & a[(i6 & 0xFF)]) << 8 | 0xFF & a[(i6 >>> 24)];
        int[] arrayOfInt = k;
        int i8 = i5 + 1;
        i6 = i7 ^ arrayOfInt[i5];
        i5 = i8;
      }
      while (true)
      {
        this.o[i4] = (i6 ^ this.o[(i4 - this.m)]);
        i4++;
        i1--;
        break;
        if ((this.m == 8) && (i1 == 4))
          i6 = a[(i6 >>> 24)] << 24 | (0xFF & a[(0xFF & i6 >>> 16)]) << 16 | (0xFF & a[(0xFF & i6 >>> 8)]) << 8 | 0xFF & a[(i6 & 0xFF)];
      }
    }
  }

  public void a(byte[] paramArrayOfByte, int paramInt)
  {
    a(paramArrayOfByte, paramInt, 3);
  }

  public void a(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if ((paramInt1 != 128) && (paramInt1 != 192) && (paramInt1 != 256))
      throw new RuntimeException("Invalid AES key size (" + paramInt1 + " bits)");
    this.m = (paramInt1 >>> 5);
    this.l = (6 + this.m);
    this.n = (4 * (1 + this.l));
    this.o = new int[this.n];
    this.p = new int[this.n];
    if ((paramInt2 & 0x3) != 0)
    {
      a(paramArrayOfByte);
      if ((paramInt2 & 0x2) != 0)
        a();
    }
  }

  public void a(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    int i1 = (paramArrayOfByte1[0] << 24 | (0xFF & paramArrayOfByte1[1]) << 16 | (0xFF & paramArrayOfByte1[2]) << 8 | 0xFF & paramArrayOfByte1[3]) ^ this.o[0];
    int i2 = (paramArrayOfByte1[4] << 24 | (0xFF & paramArrayOfByte1[5]) << 16 | (0xFF & paramArrayOfByte1[6]) << 8 | 0xFF & paramArrayOfByte1[7]) ^ this.o[1];
    int i3 = (paramArrayOfByte1[8] << 24 | (0xFF & paramArrayOfByte1[9]) << 16 | (0xFF & paramArrayOfByte1[10]) << 8 | 0xFF & paramArrayOfByte1[11]) ^ this.o[2];
    int i4 = (paramArrayOfByte1[12] << 24 | (0xFF & paramArrayOfByte1[13]) << 16 | (0xFF & paramArrayOfByte1[14]) << 8 | 0xFF & paramArrayOfByte1[15]) ^ this.o[3];
    int i5 = 1;
    int i6 = i1;
    int i7 = i2;
    int i14;
    for (int i8 = 0; i5 < this.l; i8 = i14)
    {
      i14 = i8 + 4;
      int i15 = b[(i6 >>> 24)] ^ c[(0xFF & i7 >>> 16)] ^ d[(0xFF & i3 >>> 8)] ^ e[(i4 & 0xFF)] ^ this.o[i14];
      int i16 = b[(i7 >>> 24)] ^ c[(0xFF & i3 >>> 16)] ^ d[(0xFF & i4 >>> 8)] ^ e[(i6 & 0xFF)] ^ this.o[(i14 + 1)];
      int i17 = b[(i3 >>> 24)] ^ c[(0xFF & i4 >>> 16)] ^ d[(0xFF & i6 >>> 8)] ^ e[(i7 & 0xFF)] ^ this.o[(i14 + 2)];
      i4 = b[(i4 >>> 24)] ^ c[(0xFF & i6 >>> 16)] ^ d[(0xFF & i7 >>> 8)] ^ e[(i3 & 0xFF)] ^ this.o[(i14 + 3)];
      i5++;
      i3 = i17;
      i7 = i16;
      i6 = i15;
    }
    int i9 = i8 + 4;
    int i10 = this.o[i9];
    paramArrayOfByte2[0] = ((byte)(a[(i6 >>> 24)] ^ i10 >>> 24));
    paramArrayOfByte2[1] = ((byte)(a[(0xFF & i7 >>> 16)] ^ i10 >>> 16));
    paramArrayOfByte2[2] = ((byte)(a[(0xFF & i3 >>> 8)] ^ i10 >>> 8));
    paramArrayOfByte2[3] = ((byte)(i10 ^ a[(i4 & 0xFF)]));
    int i11 = this.o[(i9 + 1)];
    paramArrayOfByte2[4] = ((byte)(a[(i7 >>> 24)] ^ i11 >>> 24));
    paramArrayOfByte2[5] = ((byte)(a[(0xFF & i3 >>> 16)] ^ i11 >>> 16));
    paramArrayOfByte2[6] = ((byte)(a[(0xFF & i4 >>> 8)] ^ i11 >>> 8));
    paramArrayOfByte2[7] = ((byte)(i11 ^ a[(i6 & 0xFF)]));
    int i12 = this.o[(i9 + 2)];
    paramArrayOfByte2[8] = ((byte)(a[(i3 >>> 24)] ^ i12 >>> 24));
    paramArrayOfByte2[9] = ((byte)(a[(0xFF & i4 >>> 16)] ^ i12 >>> 16));
    paramArrayOfByte2[10] = ((byte)(a[(0xFF & i6 >>> 8)] ^ i12 >>> 8));
    paramArrayOfByte2[11] = ((byte)(i12 ^ a[(i7 & 0xFF)]));
    int i13 = this.o[(i9 + 3)];
    paramArrayOfByte2[12] = ((byte)(a[(i4 >>> 24)] ^ i13 >>> 24));
    paramArrayOfByte2[13] = ((byte)(a[(0xFF & i6 >>> 16)] ^ i13 >>> 16));
    paramArrayOfByte2[14] = ((byte)(a[(0xFF & i7 >>> 8)] ^ i13 >>> 8));
    paramArrayOfByte2[15] = ((byte)(i13 ^ a[(i3 & 0xFF)]));
  }

  protected final void finalize()
  {
    if (this.o != null)
    {
      for (int i2 = 0; i2 < this.o.length; i2++)
        this.o[i2] = 0;
      this.o = null;
    }
    if (this.p != null)
    {
      for (int i1 = 0; i1 < this.p.length; i1++)
        this.p[i1] = 0;
      this.p = null;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.applist.c.a.d
 * JD-Core Version:    0.6.2
 */