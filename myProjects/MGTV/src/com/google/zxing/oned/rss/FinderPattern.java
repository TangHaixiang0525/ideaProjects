package com.google.zxing.oned.rss;

import com.google.zxing.ResultPoint;

public final class FinderPattern
{
  private final ResultPoint[] resultPoints;
  private final int[] startEnd;
  private final int value;

  public FinderPattern(int paramInt1, int[] paramArrayOfInt, int paramInt2, int paramInt3, int paramInt4)
  {
    this.value = paramInt1;
    this.startEnd = paramArrayOfInt;
    ResultPoint[] arrayOfResultPoint = new ResultPoint[2];
    arrayOfResultPoint[0] = new ResultPoint(paramInt2, paramInt4);
    arrayOfResultPoint[1] = new ResultPoint(paramInt3, paramInt4);
    this.resultPoints = arrayOfResultPoint;
  }

  public ResultPoint[] getResultPoints()
  {
    return this.resultPoints;
  }

  public int[] getStartEnd()
  {
    return this.startEnd;
  }

  public int getValue()
  {
    return this.value;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.rss.FinderPattern
 * JD-Core Version:    0.6.2
 */