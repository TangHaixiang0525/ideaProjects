package u.aly;

public enum bk
  implements cl
{
  private final int e;

  static
  {
    bk[] arrayOfbk = new bk[4];
    arrayOfbk[0] = a;
    arrayOfbk[1] = b;
    arrayOfbk[2] = c;
    arrayOfbk[3] = d;
  }

  private bk(int paramInt)
  {
    this.e = paramInt;
  }

  public static bk a(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return null;
    case 0:
      return a;
    case 1:
      return b;
    case 2:
      return c;
    case 3:
    }
    return d;
  }

  public int a()
  {
    return this.e;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.bk
 * JD-Core Version:    0.6.2
 */