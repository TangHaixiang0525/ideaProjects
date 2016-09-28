package com.google.zxing;

import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;

public abstract class Binarizer
{
  private final LuminanceSource source;

  protected Binarizer(LuminanceSource paramLuminanceSource)
  {
    this.source = paramLuminanceSource;
  }

  public abstract Binarizer createBinarizer(LuminanceSource paramLuminanceSource);

  public abstract BitMatrix getBlackMatrix()
    throws NotFoundException;

  public abstract BitArray getBlackRow(int paramInt, BitArray paramBitArray)
    throws NotFoundException;

  public int getHeight()
  {
    return this.source.getHeight();
  }

  public LuminanceSource getLuminanceSource()
  {
    return this.source;
  }

  public int getWidth()
  {
    return this.source.getWidth();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.Binarizer
 * JD-Core Version:    0.6.2
 */