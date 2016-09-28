package com.google.zxing.oned.rss;

public final class RSSUtils
{
  private static int combins(int paramInt1, int paramInt2)
  {
    int i;
    if (paramInt1 - paramInt2 > paramInt2)
      i = paramInt2;
    int k;
    int m;
    for (int j = paramInt1 - paramInt2; ; j = paramInt2)
    {
      k = 1;
      m = 1;
      for (int n = paramInt1; n > j; n--)
      {
        k *= n;
        if (m <= i)
        {
          k /= m;
          m++;
        }
      }
      i = paramInt1 - paramInt2;
    }
    while (m <= i)
    {
      k /= m;
      m++;
    }
    return k;
  }

  static int[] elements(int[] paramArrayOfInt, int paramInt1, int paramInt2)
  {
    int[] arrayOfInt = new int[2 + paramArrayOfInt.length];
    int i = paramInt2 << 1;
    arrayOfInt[0] = 1;
    int j = 10;
    int k = 1;
    for (int m = 1; m < i - 2; m += 2)
    {
      arrayOfInt[m] = (paramArrayOfInt[(m - 1)] - arrayOfInt[(m - 1)]);
      arrayOfInt[(m + 1)] = (paramArrayOfInt[m] - arrayOfInt[m]);
      k += arrayOfInt[m] + arrayOfInt[(m + 1)];
      if (arrayOfInt[m] < j)
        j = arrayOfInt[m];
    }
    arrayOfInt[(i - 1)] = (paramInt1 - k);
    if (arrayOfInt[(i - 1)] < j)
      j = arrayOfInt[(i - 1)];
    if (j > 1)
      for (int n = 0; n < i; n += 2)
      {
        arrayOfInt[n] += j - 1;
        int i1 = n + 1;
        arrayOfInt[i1] -= j - 1;
      }
    return arrayOfInt;
  }

  public static int getRSSvalue(int[] paramArrayOfInt, int paramInt, boolean paramBoolean)
  {
    int i = paramArrayOfInt.length;
    int j = 0;
    for (int k = 0; k < i; k++)
      j += paramArrayOfInt[k];
    int m = 0;
    int n = 0;
    for (int i1 = 0; i1 < i - 1; i1++)
    {
      int i2 = 1;
      n |= 1 << i1;
      if (i2 < paramArrayOfInt[i1])
      {
        int i3 = combins(-1 + (j - i2), -2 + (i - i1));
        if ((paramBoolean) && (n == 0) && (j - i2 - (-1 + (i - i1)) >= -1 + (i - i1)))
          i3 -= combins(j - i2 - (i - i1), -2 + (i - i1));
        if (-1 + (i - i1) > 1)
        {
          int i4 = 0;
          for (int i5 = j - i2 - (-2 + (i - i1)); i5 > paramInt; i5--)
            i4 += combins(-1 + (j - i2 - i5), -3 + (i - i1));
          i3 -= i4 * (i - 1 - i1);
        }
        while (true)
        {
          m += i3;
          i2++;
          n &= (0xFFFFFFFF ^ 1 << i1);
          break;
          if (j - i2 > paramInt)
            i3--;
        }
      }
      j -= i2;
    }
    return m;
  }

  static int[] getRSSwidths(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
  {
    int[] arrayOfInt = new int[paramInt3];
    int i = 0;
    int j = 0;
    if (j < paramInt3 - 1)
    {
      i |= 1 << j;
      int k = 1;
      while (true)
      {
        int m = combins(-1 + (paramInt2 - k), -2 + (paramInt3 - j));
        if ((paramBoolean) && (i == 0) && (paramInt2 - k - (-1 + (paramInt3 - j)) >= -1 + (paramInt3 - j)))
          m -= combins(paramInt2 - k - (paramInt3 - j), -2 + (paramInt3 - j));
        if (-1 + (paramInt3 - j) > 1)
        {
          int n = 0;
          for (int i1 = paramInt2 - k - (-2 + (paramInt3 - j)); i1 > paramInt4; i1--)
            n += combins(-1 + (paramInt2 - k - i1), -3 + (paramInt3 - j));
          m -= n * (paramInt3 - 1 - j);
        }
        while (true)
        {
          paramInt1 -= m;
          if (paramInt1 >= 0)
            break label226;
          paramInt1 += m;
          paramInt2 -= k;
          arrayOfInt[j] = k;
          j++;
          break;
          if (paramInt2 - k > paramInt4)
            m--;
        }
        label226: k++;
        i &= (0xFFFFFFFF ^ 1 << j);
      }
    }
    arrayOfInt[j] = paramInt2;
    return arrayOfInt;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.google.zxing.oned.rss.RSSUtils
 * JD-Core Version:    0.6.2
 */