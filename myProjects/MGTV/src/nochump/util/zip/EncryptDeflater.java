package nochump.util.zip;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.Deflater;
import nochump.util.extend.ZipCrypto;

public class EncryptDeflater extends FilterOutputStream
{
  protected byte[] buf;
  private boolean closed = false;
  protected Deflater def;
  protected String password = null;
  boolean usesDefaultDeflater = false;

  public EncryptDeflater(OutputStream paramOutputStream)
  {
    this(paramOutputStream, new Deflater());
    this.usesDefaultDeflater = true;
  }

  public EncryptDeflater(OutputStream paramOutputStream, Deflater paramDeflater)
  {
    this(paramOutputStream, paramDeflater, 512);
  }

  public EncryptDeflater(OutputStream paramOutputStream, Deflater paramDeflater, int paramInt)
  {
    super(paramOutputStream);
    if ((paramOutputStream == null) || (paramDeflater == null))
      throw new NullPointerException();
    if (paramInt <= 0)
      throw new IllegalArgumentException("buffer size <= 0");
    this.def = paramDeflater;
    this.buf = new byte[paramInt];
  }

  public void close()
    throws IOException
  {
    if (!this.closed)
    {
      finish();
      if (this.usesDefaultDeflater)
        this.def.end();
      this.out.close();
      this.closed = true;
    }
  }

  protected void deflate()
    throws IOException
  {
    int i = this.def.deflate(this.buf, 0, this.buf.length);
    if (i > 0)
    {
      if (this.password != null)
      {
        byte[] arrayOfByte = ZipCrypto.EncryptMessage(this.buf, i);
        this.out.write(arrayOfByte, 0, i);
      }
    }
    else
      return;
    this.out.write(this.buf, 0, i);
  }

  public void finish()
    throws IOException
  {
    if (!this.def.finished())
      this.def.finish();
    while (true)
    {
      if (this.def.finished())
        return;
      deflate();
    }
  }

  public void write(int paramInt)
    throws IOException
  {
    byte[] arrayOfByte = new byte[1];
    arrayOfByte[0] = ((byte)(paramInt & 0xFF));
    write(arrayOfByte, 0, 1);
  }

  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    if (this.def.finished())
      throw new IOException("write beyond end of stream");
    if ((paramInt1 | paramInt2 | paramInt1 + paramInt2 | paramArrayOfByte.length - (paramInt1 + paramInt2)) < 0)
      throw new IndexOutOfBoundsException();
    if (paramInt2 == 0);
    while (true)
    {
      return;
      if (!this.def.finished())
      {
        this.def.setInput(paramArrayOfByte, paramInt1, paramInt2);
        while (!this.def.needsInput())
          deflate();
      }
    }
  }

  protected void writeExtData(EncryptZipEntry paramEncryptZipEntry)
    throws IOException
  {
    byte[] arrayOfByte1 = new byte[12];
    ZipCrypto.InitCipher(this.password);
    for (int i = 0; ; i++)
    {
      if (i >= 11)
      {
        arrayOfByte1[11] = ((byte)(int)(0xFF & paramEncryptZipEntry.time >> 8));
        byte[] arrayOfByte2 = ZipCrypto.EncryptMessage(arrayOfByte1, 12);
        this.out.write(arrayOfByte2, 0, arrayOfByte2.length);
        return;
      }
      arrayOfByte1[i] = ((byte)Math.round(256.0F));
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     nochump.util.zip.EncryptDeflater
 * JD-Core Version:    0.6.2
 */