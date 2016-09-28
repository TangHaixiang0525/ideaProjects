package com.starcor.utils;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CountingInputStream extends FilterInputStream
{
  public int bytesRead = 0;

  public CountingInputStream(InputStream paramInputStream)
  {
    super(paramInputStream);
  }

  public int read()
    throws IOException
  {
    int i = super.read();
    if (i != -1)
      this.bytesRead = (1 + this.bytesRead);
    return i;
  }

  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    int i = super.read(paramArrayOfByte, paramInt1, paramInt2);
    if (i != -1)
      this.bytesRead = (i + this.bytesRead);
    return i;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.utils.CountingInputStream
 * JD-Core Version:    0.6.2
 */