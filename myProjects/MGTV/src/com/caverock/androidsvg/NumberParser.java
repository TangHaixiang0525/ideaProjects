package com.caverock.androidsvg;

public class NumberParser
{
  static long TOO_BIG = 922337203685477580L;
  private static final float[] negativePowersOf10 = { 1.0F, 0.1F, 0.01F, 0.001F, 1.0E-004F, 1.0E-005F, 1.0E-006F, 1.0E-007F, 1.0E-008F, 1.0E-009F, 1.0E-010F, 1.0E-011F, 1.0E-012F, 1.0E-013F, 1.0E-014F, 1.0E-015F, 1.0E-016F, 1.0E-017F, 1.0E-018F, 1.0E-019F, 1.0E-020F, 1.0E-021F, 1.0E-022F, 1.0E-023F, 1.0E-024F, 1.0E-025F, 1.0E-026F, 1.0E-027F, 1.0E-028F, 1.0E-029F, 1.0E-030F, 1.0E-031F, 1.0E-032F, 1.0E-033F, 1.0E-034F, 1.0E-035F, 1.0E-036F, 1.0E-037F, 9.999999E-039F };
  private static final float[] positivePowersOf10 = { 1.0F, 10.0F, 100.0F, 1000.0F, 10000.0F, 100000.0F, 1000000.0F, 10000000.0F, 1.0E+008F, 1.0E+009F, 1.0E+010F, 1.0E+011F, 1.0E+012F, 1.0E+013F, 1.0E+014F, 1.0E+015F, 1.0E+016F, 1.0E+017F, 1.0E+018F, 1.0E+019F, 1.0E+020F, 1.0E+021F, 1.0E+022F, 1.0E+023F, 1.0E+024F, 1.0E+025F, 1.0E+026F, 1.0E+027F, 9.999999E+027F, 1.0E+029F, 1.0E+030F, 1.0E+031F, 1.0E+032F, 1.0E+033F, 1.0E+034F, 1.0E+035F, 1.0E+036F, 1.0E+037F, 1.0E+038F };
  int pos;

  public int getEndPos()
  {
    return this.pos;
  }

  public float parseNumber(String paramString)
  {
    return parseNumber(paramString, 0, paramString.length());
  }

  public float parseNumber(String paramString, int paramInt1, int paramInt2)
  {
    long l = 0L;
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    int n = 0;
    this.pos = paramInt1;
    float f;
    if (this.pos >= paramInt2)
      f = (0.0F / 0.0F);
    while (true)
    {
      return f;
      int i1 = paramString.charAt(this.pos);
      int i2 = 0;
      int i3;
      label86: int i12;
      switch (i1)
      {
      case 44:
      default:
        i3 = this.pos;
        if (this.pos < paramInt2)
        {
          i12 = paramString.charAt(this.pos);
          if (i12 == 48)
            if (i == 0)
              j++;
        }
        break;
      case 45:
      case 43:
      }
      while (true)
      {
        this.pos = (1 + this.pos);
        break label86;
        i2 = 1;
        this.pos = (1 + this.pos);
        break;
        k++;
        continue;
        if ((i12 >= 49) && (i12 <= 57))
        {
          int i13 = i + k;
          while (k > 0)
          {
            if (l > TOO_BIG)
              return (0.0F / 0.0F);
            l *= 10L;
            k--;
          }
          if (l > TOO_BIG)
            return (0.0F / 0.0F);
          l = 10L * l + (i12 - 48);
          i = i13 + 1;
          if (l < 0L)
            return (0.0F / 0.0F);
        }
        else
        {
          if ((i12 != 46) || (m != 0))
          {
            if ((m == 0) || (this.pos != n + 1))
              break label295;
            return (0.0F / 0.0F);
          }
          n = this.pos - i3;
          m = 1;
        }
      }
      label295: if (i == 0)
      {
        if (j == 0)
          return (0.0F / 0.0F);
        i = 1;
      }
      int i4;
      if (m != 0)
        i4 = n - j - i;
      int i6;
      while (this.pos < paramInt2)
      {
        int i5 = paramString.charAt(this.pos);
        if ((i5 == 69) || (i5 == 101))
        {
          i6 = 0;
          this.pos = (1 + this.pos);
          if (this.pos == paramInt2)
          {
            return (0.0F / 0.0F);
            i4 = k;
          }
          else
          {
            int i7 = paramString.charAt(this.pos);
            int i8 = 0;
            int i9 = 0;
            int i10;
            switch (i7)
            {
            case 44:
            case 46:
            case 47:
            default:
              i8 = 1;
              this.pos = (-1 + this.pos);
            case 48:
            case 49:
            case 50:
            case 51:
            case 52:
            case 53:
            case 54:
            case 55:
            case 56:
            case 57:
              if (i8 == 0)
                i10 = this.pos;
              break;
            case 45:
            case 43:
              while (true)
              {
                if (this.pos >= paramInt2)
                  break label594;
                int i11 = paramString.charAt(this.pos);
                if ((i11 < 48) || (i11 > 57))
                  break label594;
                if (i6 > TOO_BIG)
                {
                  return (0.0F / 0.0F);
                  i9 = 1;
                  this.pos = (1 + this.pos);
                  i8 = 0;
                  break;
                }
                i6 = i6 * 10 + (i11 - 48);
                this.pos = (1 + this.pos);
              }
              label594: if (this.pos == i10)
                return (0.0F / 0.0F);
              if (i9 == 0)
                break label641;
              i4 -= i6;
            }
          }
        }
      }
      while ((i4 + i > 39) || (i4 + i < -44))
      {
        return (0.0F / 0.0F);
        label641: i4 += i6;
      }
      f = (float)l;
      if (l != 0L)
      {
        if (i4 <= 0)
          break label688;
        f *= positivePowersOf10[i4];
      }
      while (i2 != 0)
      {
        return -f;
        label688: if (i4 < 0)
        {
          if (i4 < -38)
          {
            f = (float)(1.0E-020D * f);
            i4 += 20;
          }
          f *= negativePowersOf10[(-i4)];
        }
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.caverock.androidsvg.NumberParser
 * JD-Core Version:    0.6.2
 */