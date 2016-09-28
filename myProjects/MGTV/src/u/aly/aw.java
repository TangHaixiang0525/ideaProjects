package u.aly;

public enum aw
  implements cl
{
  private final int d;

  static
  {
    aw[] arrayOfaw = new aw[3];
    arrayOfaw[0] = a;
    arrayOfaw[1] = b;
    arrayOfaw[2] = c;
  }

  private aw(int paramInt)
  {
    this.d = paramInt;
  }

  public static aw a(int paramInt)
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
    }
    return c;
  }

  public int a()
  {
    return this.d;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.aw
 * JD-Core Version:    0.6.2
 */