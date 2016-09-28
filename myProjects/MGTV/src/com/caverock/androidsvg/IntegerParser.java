package com.caverock.androidsvg;

public class IntegerParser
{
  boolean isNegative;
  int pos;
  long value;

  public IntegerParser(boolean paramBoolean, long paramLong, int paramInt)
  {
    this.isNegative = paramBoolean;
    this.value = paramLong;
    this.pos = paramInt;
  }

  public static IntegerParser parseHex(String paramString)
  {
    return parseHex(paramString, 0, paramString.length());
  }

  public static IntegerParser parseHex(String paramString, int paramInt1, int paramInt2)
  {
    int i = paramInt1;
    long l = 0L;
    if (i >= paramInt2)
      return null;
    while (true)
    {
      i++;
      if (i >= paramInt2)
        break;
      int j = paramString.charAt(i);
      if ((j >= 48) && (j <= 57))
        l = 16L * l + (j - 48);
      while (l > 4294967295L)
      {
        return null;
        if ((j >= 65) && (j <= 70))
        {
          l = 10L + (16L * l + (j - 65));
        }
        else
        {
          if ((j < 97) || (j > 102))
            break label139;
          l = 10L + (16L * l + (j - 97));
        }
      }
    }
    label139: if (i == paramInt1)
      return null;
    return new IntegerParser(false, l, i);
  }

  public static IntegerParser parseInt(String paramString)
  {
    return parseInt(paramString, 0, paramString.length());
  }

  public static IntegerParser parseInt(String paramString, int paramInt1, int paramInt2)
  {
    int i = paramInt1;
    long l = 0L;
    if (i >= paramInt2)
      return null;
    int j = paramString.charAt(i);
    boolean bool = false;
    int k;
    switch (j)
    {
    case 44:
    default:
      k = i;
    case 45:
    case 43:
    }
    while (true)
    {
      if (i >= paramInt2)
        break label153;
      int m = paramString.charAt(i);
      if ((m < 48) || (m > 57))
        break label153;
      if (bool)
      {
        l = 10L * l - (m - 48);
        if (l >= -2147483648L)
          break label147;
        return null;
        bool = true;
        i++;
        break;
      }
      l = 10L * l + (m - 48);
      if (l > 2147483647L)
        return null;
      label147: i++;
    }
    label153: if (i == k)
      return null;
    return new IntegerParser(bool, l, i);
  }

  public int getEndPos()
  {
    return this.pos;
  }

  public int value()
  {
    return (int)this.value;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.caverock.androidsvg.IntegerParser
 * JD-Core Version:    0.6.2
 */