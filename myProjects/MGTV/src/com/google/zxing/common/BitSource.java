package com.google.zxing.common;

public final class BitSource
{
  private int bitOffset;
  private int byteOffset;
  private final byte[] bytes;

  public BitSource(byte[] paramArrayOfByte)
  {
    this.bytes = paramArrayOfByte;
  }

  public int available()
  {
    return 8 * (this.bytes.length - this.byteOffset) - this.bitOffset;
  }

  public int getByteOffset()
  {
    return this.byteOffset;
  }

  public int readBits(int paramInt)
  {
    if ((paramInt < 1) || (paramInt > 32))
      throw new IllegalArgumentException();
    int i = this.bitOffset;
    int j = 0;
    int n;
    if (i > 0)
    {
      n = 8 - this.bitOffset;
      if (paramInt >= n)
        break label166;
    }
    label166: for (int i1 = paramInt; ; i1 = n)
    {
      int i2 = n - i1;
      j = (255 >> 8 - i1 << i2 & this.bytes[this.byteOffset]) >> i2;
      paramInt -= i1;
      this.bitOffset = (i1 + this.bitOffset);
      if (this.bitOffset == 8)
      {
        this.bitOffset = 0;
        this.byteOffset = (1 + this.byteOffset);
      }
      if (paramInt <= 0)
        break label224;
      while (paramInt >= 8)
      {
        j = j << 8 | 0xFF & this.bytes[this.byteOffset];
        this.byteOffset = (1 + this.byteOffset);
        paramInt -= 8;
      }
    }
    if (paramInt > 0)
    {
      int k = 8 - paramInt;
      int m = 255 >> k << k;
      j = j << paramInt | (m & this.bytes[this.byteOffset]) >> k;
      this.bitOffset = (paramInt + this.bitOffset);
    }
    label224: return j;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.common.BitSource
 * JD-Core Version:    0.6.2
 */