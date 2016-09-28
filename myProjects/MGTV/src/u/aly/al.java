package u.aly;

public enum al
  implements cl
{
  private final int e;

  static
  {
    al[] arrayOfal = new al[4];
    arrayOfal[0] = a;
    arrayOfal[1] = b;
    arrayOfal[2] = c;
    arrayOfal[3] = d;
  }

  private al(int paramInt)
  {
    this.e = paramInt;
  }

  public static al a(int paramInt)
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
 * Qualified Name:     u.aly.al
 * JD-Core Version:    0.6.2
 */