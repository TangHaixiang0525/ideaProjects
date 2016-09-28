package u.aly;

import java.io.ByteArrayOutputStream;

public class cj extends ByteArrayOutputStream
{
  public cj()
  {
  }

  public cj(int paramInt)
  {
    super(paramInt);
  }

  public byte[] a()
  {
    return this.buf;
  }

  public int b()
  {
    return this.count;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.cj
 * JD-Core Version:    0.6.2
 */