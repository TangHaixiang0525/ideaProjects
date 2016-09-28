package nochump.util.zip;

import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import java.util.zip.ZipException;
import nochump.util.extend.ZipCrypto;

public class EncryptInflater extends FilterInputStream
{
  private byte[] b = new byte[512];
  protected byte[] buf;
  private boolean closed = false;
  protected Inflater inf;
  protected int len;
  protected String password = null;
  private boolean reachEOF = false;
  private byte[] singleByteBuf = new byte[1];
  boolean usesDefaultInflater = false;

  public EncryptInflater(InputStream paramInputStream)
  {
    this(paramInputStream, new Inflater());
    this.usesDefaultInflater = true;
  }

  public EncryptInflater(InputStream paramInputStream, Inflater paramInflater)
  {
    this(paramInputStream, paramInflater, 512);
  }

  public EncryptInflater(InputStream paramInputStream, Inflater paramInflater, int paramInt)
  {
    super(paramInputStream);
    if ((paramInputStream == null) || (paramInflater == null))
      throw new NullPointerException();
    if (paramInt <= 0)
      throw new IllegalArgumentException("buffer size <= 0");
    this.inf = paramInflater;
    this.buf = new byte[paramInt];
  }

  private void ensureOpen()
    throws IOException
  {
    if (this.closed)
      throw new IOException("Stream closed");
  }

  public int available()
    throws IOException
  {
    ensureOpen();
    if (this.reachEOF)
      return 0;
    return 1;
  }

  public void close()
    throws IOException
  {
    if (!this.closed)
    {
      if (this.usesDefaultInflater)
        this.inf.end();
      this.in.close();
      this.closed = true;
    }
  }

  protected void fill()
    throws IOException
  {
    ensureOpen();
    this.len = this.in.read(this.buf, 0, this.buf.length);
    if (this.len == -1)
      throw new EOFException("Unexpected end of ZLIB input stream");
    if (this.password != null)
    {
      byte[] arrayOfByte = ZipCrypto.DecryptMessage(this.buf, this.len);
      this.inf.setInput(arrayOfByte, 0, arrayOfByte.length);
      return;
    }
    this.inf.setInput(this.buf, 0, this.len);
  }

  public void mark(int paramInt)
  {
  }

  public boolean markSupported()
  {
    return false;
  }

  public int read()
    throws IOException
  {
    ensureOpen();
    if (read(this.singleByteBuf, 0, 1) == -1)
      return -1;
    return 0xFF & this.singleByteBuf[0];
  }

  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    ensureOpen();
    if ((paramInt1 | paramInt2 | paramInt1 + paramInt2 | paramArrayOfByte.length - (paramInt1 + paramInt2)) < 0)
      throw new IndexOutOfBoundsException();
    if (paramInt2 == 0)
      return 0;
    String str;
    try
    {
      int i;
      do
      {
        if ((this.inf.finished()) || (this.inf.needsDictionary()))
        {
          this.reachEOF = true;
          return -1;
        }
        if (this.inf.needsInput())
          fill();
        i = this.inf.inflate(paramArrayOfByte, paramInt1, paramInt2);
      }
      while (i == 0);
      return i;
    }
    catch (DataFormatException localDataFormatException)
    {
      str = localDataFormatException.getMessage();
      if (str == null);
    }
    while (true)
    {
      throw new ZipException(str);
      str = "Invalid ZLIB data format";
    }
  }

  public void reset()
    throws IOException
  {
    try
    {
      throw new IOException("mark/reset not supported");
    }
    finally
    {
    }
  }

  public long skip(long paramLong)
    throws IOException
  {
    if (paramLong < 0L)
      throw new IllegalArgumentException("negative skip length");
    ensureOpen();
    int i = (int)Math.min(paramLong, 2147483647L);
    int j = 0;
    while (true)
    {
      if (j >= i);
      int m;
      while (true)
      {
        return j;
        int k = i - j;
        if (k > this.b.length)
          k = this.b.length;
        m = read(this.b, 0, k);
        if (m != -1)
          break;
        this.reachEOF = true;
      }
      j += m;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     nochump.util.zip.EncryptInflater
 * JD-Core Version:    0.6.2
 */