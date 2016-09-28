package u.aly;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ds extends du
{
  protected InputStream a = null;
  protected OutputStream b = null;

  protected ds()
  {
  }

  public ds(InputStream paramInputStream)
  {
    this.a = paramInputStream;
  }

  public ds(InputStream paramInputStream, OutputStream paramOutputStream)
  {
    this.a = paramInputStream;
    this.b = paramOutputStream;
  }

  public ds(OutputStream paramOutputStream)
  {
    this.b = paramOutputStream;
  }

  public int a(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws dv
  {
    if (this.a == null)
      throw new dv(1, "Cannot read from null inputStream");
    int i;
    try
    {
      i = this.a.read(paramArrayOfByte, paramInt1, paramInt2);
      if (i < 0)
        throw new dv(4);
    }
    catch (IOException localIOException)
    {
      throw new dv(0, localIOException);
    }
    return i;
  }

  public boolean a()
  {
    return true;
  }

  public void b()
    throws dv
  {
  }

  public void b(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws dv
  {
    if (this.b == null)
      throw new dv(1, "Cannot write to null outputStream");
    try
    {
      this.b.write(paramArrayOfByte, paramInt1, paramInt2);
      return;
    }
    catch (IOException localIOException)
    {
      throw new dv(0, localIOException);
    }
  }

  public void c()
  {
    if (this.a != null);
    try
    {
      this.a.close();
      this.a = null;
      if (this.b == null);
    }
    catch (IOException localIOException2)
    {
      try
      {
        this.b.close();
        this.b = null;
        return;
        localIOException2 = localIOException2;
        localIOException2.printStackTrace();
      }
      catch (IOException localIOException1)
      {
        while (true)
          localIOException1.printStackTrace();
      }
    }
  }

  public void d()
    throws dv
  {
    if (this.b == null)
      throw new dv(1, "Cannot flush null outputStream");
    try
    {
      this.b.flush();
      return;
    }
    catch (IOException localIOException)
    {
      throw new dv(0, localIOException);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     u.aly.ds
 * JD-Core Version:    0.6.2
 */