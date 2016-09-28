package nochump.util.zip;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.util.zip.CRC32;
import java.util.zip.Deflater;
import java.util.zip.ZipException;

public class EncryptZipOutput extends EncryptDeflater
  implements ZipConstants
{
  public static final int DEFLATED = 8;
  public static final int STORED;
  private boolean closed = false;
  private String comment;
  private CRC32 crc = new CRC32();
  private Vector<EncryptZipEntry> entries = new Vector();
  private EncryptZipEntry entry;
  private boolean finished;
  private long locoff = 0L;
  private int method = 8;
  private Hashtable<String, EncryptZipEntry> names = new Hashtable();
  private long written = 0L;

  public EncryptZipOutput(OutputStream paramOutputStream)
  {
    super(paramOutputStream, new Deflater(-1, true));
    this.usesDefaultDeflater = true;
  }

  public EncryptZipOutput(OutputStream paramOutputStream, String paramString)
  {
    this(paramOutputStream);
    this.password = paramString;
  }

  private void ensureOpen()
    throws IOException
  {
    if (this.closed)
      throw new IOException("Stream closed");
  }

  private static byte[] getUTF8Bytes(String paramString)
  {
    char[] arrayOfChar = paramString.toCharArray();
    int i = arrayOfChar.length;
    int j = 0;
    int k = 0;
    byte[] arrayOfByte;
    int n;
    int i1;
    if (k >= i)
    {
      arrayOfByte = new byte[j];
      n = 0;
      i1 = 0;
      if (n >= i)
        return arrayOfByte;
    }
    else
    {
      int m = arrayOfChar[k];
      if (m <= 127)
        j++;
      while (true)
      {
        k++;
        break;
        if (m <= 2047)
          j += 2;
        else
          j += 3;
      }
    }
    int i2 = arrayOfChar[n];
    int i5;
    if (i2 <= 127)
    {
      i5 = i1 + 1;
      arrayOfByte[i1] = ((byte)i2);
    }
    while (true)
    {
      n++;
      i1 = i5;
      break;
      if (i2 <= 2047)
      {
        int i6 = i1 + 1;
        arrayOfByte[i1] = ((byte)(0xC0 | i2 >> 6));
        int i7 = i6 + 1;
        arrayOfByte[i6] = ((byte)(0x80 | i2 & 0x3F));
        i5 = i7;
      }
      else
      {
        int i3 = i1 + 1;
        arrayOfByte[i1] = ((byte)(0xE0 | i2 >> 12));
        int i4 = i3 + 1;
        arrayOfByte[i3] = ((byte)(0x80 | 0x3F & i2 >> 6));
        i5 = i4 + 1;
        arrayOfByte[i4] = ((byte)(0x80 | i2 & 0x3F));
      }
    }
  }

  static int getUTF8Length(String paramString)
  {
    int i = 0;
    int j = 0;
    if (j >= paramString.length())
      return i;
    int k = paramString.charAt(j);
    if (k <= 127)
      i++;
    while (true)
    {
      j++;
      break;
      if (k <= 2047)
        i += 2;
      else
        i += 3;
    }
  }

  private void writeBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    this.out.write(paramArrayOfByte, paramInt1, paramInt2);
    this.written += paramInt2;
  }

  private void writeCEN(EncryptZipEntry paramEncryptZipEntry)
    throws IOException
  {
    writeInt(33639248L);
    writeShort(paramEncryptZipEntry.version);
    writeShort(paramEncryptZipEntry.version);
    writeShort(paramEncryptZipEntry.flag);
    writeShort(paramEncryptZipEntry.method);
    writeInt(paramEncryptZipEntry.time);
    writeInt(paramEncryptZipEntry.crc);
    writeInt(paramEncryptZipEntry.csize);
    writeInt(paramEncryptZipEntry.size);
    byte[] arrayOfByte1 = getUTF8Bytes(paramEncryptZipEntry.name);
    writeShort(arrayOfByte1.length);
    int i;
    byte[] arrayOfByte2;
    if (paramEncryptZipEntry.extra != null)
    {
      i = paramEncryptZipEntry.extra.length;
      writeShort(i);
      if (paramEncryptZipEntry.comment == null)
        break label199;
      arrayOfByte2 = getUTF8Bytes(paramEncryptZipEntry.comment);
      writeShort(arrayOfByte2.length);
    }
    while (true)
    {
      writeShort(0);
      writeShort(0);
      writeInt(0L);
      writeInt(paramEncryptZipEntry.offset);
      writeBytes(arrayOfByte1, 0, arrayOfByte1.length);
      if (paramEncryptZipEntry.extra != null)
        writeBytes(paramEncryptZipEntry.extra, 0, paramEncryptZipEntry.extra.length);
      if (arrayOfByte2 != null)
        writeBytes(arrayOfByte2, 0, arrayOfByte2.length);
      return;
      i = 0;
      break;
      label199: writeShort(0);
      arrayOfByte2 = null;
    }
  }

  private void writeEND(long paramLong1, long paramLong2)
    throws IOException
  {
    writeInt(101010256L);
    writeShort(0);
    writeShort(0);
    writeShort(this.entries.size());
    writeShort(this.entries.size());
    writeInt(paramLong2);
    writeInt(paramLong1);
    if (this.comment != null)
    {
      byte[] arrayOfByte = getUTF8Bytes(this.comment);
      writeShort(arrayOfByte.length);
      writeBytes(arrayOfByte, 0, arrayOfByte.length);
      return;
    }
    writeShort(0);
  }

  private void writeEXT(EncryptZipEntry paramEncryptZipEntry)
    throws IOException
  {
    writeInt(134695760L);
    writeInt(paramEncryptZipEntry.crc);
    writeInt(paramEncryptZipEntry.csize);
    writeInt(paramEncryptZipEntry.size);
  }

  private void writeInt(long paramLong)
    throws IOException
  {
    OutputStream localOutputStream = this.out;
    localOutputStream.write((int)(0xFF & paramLong >>> 0));
    localOutputStream.write((int)(0xFF & paramLong >>> 8));
    localOutputStream.write((int)(0xFF & paramLong >>> 16));
    localOutputStream.write((int)(0xFF & paramLong >>> 24));
    this.written = (4L + this.written);
  }

  private void writeLOC(EncryptZipEntry paramEncryptZipEntry)
    throws IOException
  {
    writeInt(67324752L);
    writeShort(paramEncryptZipEntry.version);
    writeShort(paramEncryptZipEntry.flag);
    writeShort(paramEncryptZipEntry.method);
    writeInt(paramEncryptZipEntry.time);
    byte[] arrayOfByte;
    if ((0x8 & paramEncryptZipEntry.flag) == 8)
    {
      writeInt(0L);
      writeInt(0L);
      writeInt(0L);
      arrayOfByte = getUTF8Bytes(paramEncryptZipEntry.name);
      writeShort(arrayOfByte.length);
      if (paramEncryptZipEntry.extra == null)
        break label163;
    }
    label163: for (int i = paramEncryptZipEntry.extra.length; ; i = 0)
    {
      writeShort(i);
      writeBytes(arrayOfByte, 0, arrayOfByte.length);
      if (paramEncryptZipEntry.extra != null)
        writeBytes(paramEncryptZipEntry.extra, 0, paramEncryptZipEntry.extra.length);
      this.locoff = this.written;
      return;
      writeInt(paramEncryptZipEntry.crc);
      writeInt(paramEncryptZipEntry.csize);
      writeInt(paramEncryptZipEntry.size);
      break;
    }
  }

  private void writeShort(int paramInt)
    throws IOException
  {
    OutputStream localOutputStream = this.out;
    localOutputStream.write(0xFF & paramInt >>> 0);
    localOutputStream.write(0xFF & paramInt >>> 8);
    this.written = (2L + this.written);
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
    EncryptZipEntry localEncryptZipEntry = this.entry;
    if (localEncryptZipEntry != null)
      switch (localEncryptZipEntry.method)
      {
      default:
        throw new InternalError("invalid compression method");
      case 8:
        this.def.finish();
        while (true)
        {
          if (this.def.finished())
          {
            if ((0x8 & localEncryptZipEntry.flag) != 0)
              break label280;
            if (localEncryptZipEntry.size == this.def.getBytesRead())
              break;
            throw new ZipException("invalid entry size (expected " + localEncryptZipEntry.size + " but got " + this.def.getBytesRead() + " bytes)");
          }
          deflate();
        }
        if (localEncryptZipEntry.csize != this.def.getBytesWritten())
          throw new ZipException("invalid entry compressed size (expected " + localEncryptZipEntry.csize + " but got " + this.def.getBytesWritten() + " bytes)");
        if (localEncryptZipEntry.crc != this.crc.getValue())
        {
          throw new ZipException("invalid entry CRC-32 (expected 0x" + Long.toHexString(localEncryptZipEntry.crc) + " but got 0x" + Long.toHexString(this.crc.getValue()) + ")");
          label280: localEncryptZipEntry.size = this.def.getBytesRead();
          if (localEncryptZipEntry.flag == 9)
            localEncryptZipEntry.csize = (13L + localEncryptZipEntry.csize);
          localEncryptZipEntry.csize += this.def.getBytesWritten();
          localEncryptZipEntry.crc = this.crc.getValue();
          writeEXT(localEncryptZipEntry);
        }
        this.def.reset();
        this.written += localEncryptZipEntry.csize;
      case 0:
      }
    do
    {
      this.crc.reset();
      this.entry = null;
      return;
      if (localEncryptZipEntry.size != this.written - this.locoff)
        throw new ZipException("invalid entry size (expected " + localEncryptZipEntry.size + " but got " + (this.written - this.locoff) + " bytes)");
    }
    while (localEncryptZipEntry.crc == this.crc.getValue());
    throw new ZipException("invalid entry crc-32 (expected 0x" + Long.toHexString(localEncryptZipEntry.crc) + " but got 0x" + Long.toHexString(this.crc.getValue()) + ")");
  }

  public void finish()
    throws IOException
  {
    ensureOpen();
    if (this.finished)
      return;
    if (this.entry != null)
      closeEntry();
    if (this.entries.size() < 1)
      throw new ZipException("ZIP file must have at least one entry");
    long l = this.written;
    Enumeration localEnumeration = this.entries.elements();
    while (true)
    {
      if (!localEnumeration.hasMoreElements())
      {
        writeEND(l, this.written - l);
        this.finished = true;
        return;
      }
      writeCEN((EncryptZipEntry)localEnumeration.nextElement());
    }
  }

  public void putNextEntry(EncryptZipEntry paramEncryptZipEntry)
    throws IOException
  {
    ensureOpen();
    if (this.entry != null)
      closeEntry();
    if (paramEncryptZipEntry.time == -1L)
      paramEncryptZipEntry.setTime(System.currentTimeMillis());
    if (paramEncryptZipEntry.method == -1)
      paramEncryptZipEntry.method = this.method;
    switch (paramEncryptZipEntry.method)
    {
    default:
      throw new ZipException("unsupported compression method");
    case 8:
      if ((paramEncryptZipEntry.size == -1L) || (paramEncryptZipEntry.csize == -1L) || (paramEncryptZipEntry.crc == -1L))
      {
        paramEncryptZipEntry.flag = 8;
        paramEncryptZipEntry.version = 20;
      }
      break;
    case 0:
    }
    while (true)
    {
      paramEncryptZipEntry.offset = this.written;
      if (this.names.put(paramEncryptZipEntry.name, paramEncryptZipEntry) == null)
        break label350;
      throw new ZipException("duplicate entry: " + paramEncryptZipEntry.name);
      if ((paramEncryptZipEntry.size != -1L) && (paramEncryptZipEntry.csize != -1L) && (paramEncryptZipEntry.crc != -1L))
      {
        paramEncryptZipEntry.flag = 0;
        break;
      }
      throw new ZipException("DEFLATED entry missing size, compressed size, or crc-32");
      if (paramEncryptZipEntry.size == -1L)
        paramEncryptZipEntry.size = paramEncryptZipEntry.csize;
      while ((paramEncryptZipEntry.size == -1L) || (paramEncryptZipEntry.crc == -1L))
      {
        throw new ZipException("STORED entry missing size, compressed size, or crc-32");
        if (paramEncryptZipEntry.csize == -1L)
          paramEncryptZipEntry.csize = paramEncryptZipEntry.size;
        else if (paramEncryptZipEntry.size != paramEncryptZipEntry.csize)
          throw new ZipException("STORED entry where compressed != uncompressed size");
      }
      paramEncryptZipEntry.version = 10;
      paramEncryptZipEntry.flag = 0;
    }
    label350: if (this.password != null)
      paramEncryptZipEntry.flag = 9;
    writeLOC(paramEncryptZipEntry);
    if (this.password != null)
      writeExtData(paramEncryptZipEntry);
    this.entries.addElement(paramEncryptZipEntry);
    this.entry = paramEncryptZipEntry;
  }

  public void setComment(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 21845) && (getUTF8Length(paramString) > 65535))
      throw new IllegalArgumentException("ZIP file comment too long.");
    this.comment = paramString;
  }

  public void setLevel(int paramInt)
  {
    this.def.setLevel(paramInt);
  }

  public void setMethod(int paramInt)
  {
    if ((paramInt != 8) && (paramInt != 0))
      throw new IllegalArgumentException("invalid compression method");
    this.method = paramInt;
  }

  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    try
    {
      ensureOpen();
      if ((paramInt1 < 0) || (paramInt2 < 0) || (paramInt1 > paramArrayOfByte.length - paramInt2))
        throw new IndexOutOfBoundsException();
    }
    finally
    {
    }
    if (paramInt2 == 0)
      return;
    if (this.entry == null)
      throw new ZipException("no current ZIP entry");
    switch (this.entry.method)
    {
    default:
      throw new InternalError("invalid compression method");
    case 8:
      super.write(paramArrayOfByte, paramInt1, paramInt2);
    case 0:
    }
    while (true)
    {
      this.crc.update(paramArrayOfByte, paramInt1, paramInt2);
      break;
      this.written += paramInt2;
      if (this.written - this.locoff > this.entry.size)
        throw new ZipException("attempt to write past end of STORED entry");
      this.out.write(paramArrayOfByte, paramInt1, paramInt2);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     nochump.util.zip.EncryptZipOutput
 * JD-Core Version:    0.6.2
 */