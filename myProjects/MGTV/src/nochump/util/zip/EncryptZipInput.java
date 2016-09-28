package nochump.util.zip;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.util.zip.CRC32;
import java.util.zip.Inflater;
import java.util.zip.ZipException;
import nochump.util.extend.ZipCrypto;

public class EncryptZipInput extends EncryptInflater
  implements ZipConstants
{
  private static final int DEFLATED = 8;
  private static final int STORED;
  private byte[] b = new byte[256];
  private boolean closed = false;
  private CRC32 crc = new CRC32();
  private EncryptZipEntry entry;
  private boolean entryEOF = false;
  private long remaining;
  private byte[] tmpbuf = new byte[512];

  public EncryptZipInput(InputStream paramInputStream, String paramString)
  {
    super(new PushbackInputStream(paramInputStream, 512), new Inflater(true), 512);
    this.usesDefaultInflater = true;
    if (paramInputStream == null)
      throw new NullPointerException("in is null");
    this.password = paramString;
  }

  private void ensureOpen()
    throws IOException
  {
    if (this.closed)
      throw new IOException("Stream closed");
  }

  private static final int get16(byte[] paramArrayOfByte, int paramInt)
  {
    return 0xFF & paramArrayOfByte[paramInt] | (0xFF & paramArrayOfByte[(paramInt + 1)]) << 8;
  }

  private static final long get32(byte[] paramArrayOfByte, int paramInt)
  {
    return get16(paramArrayOfByte, paramInt) | get16(paramArrayOfByte, paramInt + 2) << 16;
  }

  private static String getUTF8String(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = 0;
    int j = paramInt1 + paramInt2;
    int k = paramInt1;
    int m;
    while (true)
    {
      if (k >= j)
      {
        if (k == j)
          break;
        throw new IllegalArgumentException();
      }
      m = k + 1;
      switch ((0xFF & paramArrayOfByte[k]) >> 4)
      {
      case 8:
      case 9:
      case 10:
      case 11:
      default:
        throw new IllegalArgumentException();
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
        i++;
        k = m;
        break;
      case 12:
      case 13:
        k = m + 1;
        if ((0xC0 & paramArrayOfByte[m]) != 128)
          throw new IllegalArgumentException();
        i++;
      case 14:
      }
    }
    int n = m + 1;
    int i1;
    if ((0xC0 & paramArrayOfByte[m]) == 128)
    {
      i1 = n + 1;
      if ((0xC0 & paramArrayOfByte[n]) == 128);
    }
    while (true)
    {
      throw new IllegalArgumentException();
      i++;
      k = i1;
      break;
      char[] arrayOfChar = new char[i];
      int i2 = 0;
      int i3 = paramInt1;
      while (true)
      {
        if (i3 >= j)
          return new String(arrayOfChar, 0, i);
        int i4 = i3 + 1;
        int i5 = 0xFF & paramArrayOfByte[i3];
        switch (i5 >> 4)
        {
        case 8:
        case 9:
        case 10:
        case 11:
        default:
          throw new IllegalArgumentException();
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
          int i13 = i2 + 1;
          arrayOfChar[i2] = ((char)i5);
          i2 = i13;
          i3 = i4;
          break;
        case 12:
        case 13:
          int i11 = i2 + 1;
          int i12 = (i5 & 0x1F) << 6;
          i3 = i4 + 1;
          arrayOfChar[i2] = ((char)(i12 | 0x3F & paramArrayOfByte[i4]));
          i2 = i11;
          break;
        case 14:
          int i6 = i4 + 1;
          int i7 = (0x3F & paramArrayOfByte[i4]) << 6;
          int i8 = i2 + 1;
          int i9 = i7 | (i5 & 0xF) << 12;
          int i10 = i6 + 1;
          arrayOfChar[i2] = ((char)(i9 | 0x3F & paramArrayOfByte[i6]));
          i2 = i8;
          i3 = i10;
        }
      }
    }
  }

  private void readEnd(EncryptZipEntry paramEncryptZipEntry)
    throws IOException
  {
    int i = this.inf.getRemaining();
    if (i > 0)
      ((PushbackInputStream)this.in).unread(this.buf, this.len - i, i);
    if ((0x8 & paramEncryptZipEntry.flag) == 8)
    {
      readFully(this.tmpbuf, 0, 16);
      long l = get32(this.tmpbuf, 0);
      if (l == 134695760L)
        break label182;
      paramEncryptZipEntry.crc = l;
      paramEncryptZipEntry.csize = get32(this.tmpbuf, 4);
      paramEncryptZipEntry.size = get32(this.tmpbuf, 8);
      ((PushbackInputStream)this.in).unread(this.tmpbuf, 11, 4);
    }
    while (paramEncryptZipEntry.size != this.inf.getBytesWritten())
    {
      throw new ZipException("invalid entry size (expected " + paramEncryptZipEntry.size + " but got " + this.inf.getBytesWritten() + " bytes)");
      label182: paramEncryptZipEntry.crc = get32(this.tmpbuf, 4);
      paramEncryptZipEntry.csize = get32(this.tmpbuf, 8);
      if (paramEncryptZipEntry.flag == 9)
        paramEncryptZipEntry.csize -= 12L;
      paramEncryptZipEntry.size = get32(this.tmpbuf, 12);
    }
    if (paramEncryptZipEntry.csize != this.inf.getBytesRead())
      throw new ZipException("invalid entry compressed size (expected " + paramEncryptZipEntry.csize + " but got " + this.inf.getBytesRead() + " bytes)");
    if (paramEncryptZipEntry.crc != this.crc.getValue())
      throw new ZipException("invalid entry CRC (expected 0x" + Long.toHexString(paramEncryptZipEntry.crc) + " but got 0x" + Long.toHexString(this.crc.getValue()) + ")");
  }

  private void readFully(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    while (true)
    {
      if (paramInt2 <= 0)
        return;
      int i = this.in.read(paramArrayOfByte, paramInt1, paramInt2);
      if (i == -1)
        throw new EOFException();
      paramInt1 += i;
      paramInt2 -= i;
    }
  }

  private EncryptZipEntry readLOC()
    throws IOException
  {
    EncryptZipEntry localEncryptZipEntry;
    byte[] arrayOfByte2;
    do
    {
      do
      {
        do
        {
          try
          {
            readFully(this.tmpbuf, 0, 30);
            if (get32(this.tmpbuf, 0) != 67324752L)
            {
              localEncryptZipEntry = null;
              return localEncryptZipEntry;
            }
          }
          catch (EOFException localEOFException)
          {
            return null;
          }
          int i = get16(this.tmpbuf, 26);
          if (i == 0)
            throw new ZipException("missing entry name");
          int j = this.b.length;
          if (i > j)
          {
            do
              j *= 2;
            while (i > j);
            this.b = new byte[j];
          }
          readFully(this.b, 0, i);
          localEncryptZipEntry = createZipEntry(getUTF8String(this.b, 0, i));
          localEncryptZipEntry.version = get16(this.tmpbuf, 4);
          localEncryptZipEntry.flag = get16(this.tmpbuf, 6);
          localEncryptZipEntry.method = get16(this.tmpbuf, 8);
          localEncryptZipEntry.time = get32(this.tmpbuf, 10);
          if ((0x8 & localEncryptZipEntry.flag) == 8)
          {
            if (localEncryptZipEntry.method != 8)
              throw new ZipException("only DEFLATED entries can have EXT descriptor");
          }
          else
          {
            localEncryptZipEntry.crc = get32(this.tmpbuf, 14);
            localEncryptZipEntry.csize = get32(this.tmpbuf, 18);
            localEncryptZipEntry.size = get32(this.tmpbuf, 22);
          }
          int k = get16(this.tmpbuf, 28);
          if (k > 0)
          {
            byte[] arrayOfByte3 = new byte[k];
            readFully(arrayOfByte3, 0, k);
            localEncryptZipEntry.extra = arrayOfByte3;
          }
        }
        while (this.password == null);
        byte[] arrayOfByte1 = new byte[12];
        readFully(arrayOfByte1, 0, 12);
        ZipCrypto.InitCipher(this.password);
        arrayOfByte2 = ZipCrypto.DecryptMessage(arrayOfByte1, 12);
      }
      while (arrayOfByte2[11] == (byte)(int)(0xFF & localEncryptZipEntry.crc >> 24));
      if ((0x8 & localEncryptZipEntry.flag) != 8)
        throw new ZipException("The password did not match.");
    }
    while (arrayOfByte2[11] == (byte)(int)(0xFF & localEncryptZipEntry.time >> 8));
    throw new ZipException("The password did not match.");
  }

  public int available()
    throws IOException
  {
    ensureOpen();
    if (this.entryEOF)
      return 0;
    return 1;
  }

  public void close()
    throws IOException
  {
    if (!this.closed)
    {
      super.close();
      this.closed = true;
    }
  }

  public void closeEntry()
    throws IOException
  {
    ensureOpen();
    while (read(this.tmpbuf, 0, this.tmpbuf.length) != -1);
    this.entryEOF = true;
  }

  protected EncryptZipEntry createZipEntry(String paramString)
  {
    return new EncryptZipEntry(paramString);
  }

  public EncryptZipEntry getNextEntry()
    throws IOException
  {
    ensureOpen();
    if (this.entry != null)
      closeEntry();
    this.crc.reset();
    this.inf.reset();
    EncryptZipEntry localEncryptZipEntry = readLOC();
    this.entry = localEncryptZipEntry;
    if (localEncryptZipEntry == null)
      return null;
    if (this.entry.method == 0)
      this.remaining = this.entry.size;
    this.entryEOF = false;
    return this.entry;
  }

  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    int i = -1;
    ensureOpen();
    if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt1 > paramArrayOfByte.length - paramInt2))
      throw new IndexOutOfBoundsException();
    if (paramInt2 == 0)
      i = 0;
    while (this.entry == null)
      return i;
    switch (this.entry.method)
    {
    default:
      throw new InternalError("invalid compression method");
    case 8:
      int k = super.read(paramArrayOfByte, paramInt1, paramInt2);
      if (k == i)
      {
        readEnd(this.entry);
        this.entryEOF = true;
        this.entry = null;
      }
      while (true)
      {
        return k;
        this.crc.update(paramArrayOfByte, paramInt1, k);
      }
    case 0:
    }
    if (this.remaining <= 0L)
    {
      this.entryEOF = true;
      this.entry = null;
      return i;
    }
    if (paramInt2 > this.remaining)
      paramInt2 = (int)this.remaining;
    int j = this.in.read(paramArrayOfByte, paramInt1, paramInt2);
    if (j == i)
      throw new ZipException("unexpected EOF");
    this.crc.update(paramArrayOfByte, paramInt1, j);
    this.remaining -= j;
    return j;
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
        if (k > this.tmpbuf.length)
          k = this.tmpbuf.length;
        m = read(this.tmpbuf, 0, k);
        if (m != -1)
          break;
        this.entryEOF = true;
      }
      j += m;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     nochump.util.zip.EncryptZipInput
 * JD-Core Version:    0.6.2
 */