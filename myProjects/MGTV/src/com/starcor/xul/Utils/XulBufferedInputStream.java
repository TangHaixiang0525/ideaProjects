package com.starcor.xul.Utils;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class XulBufferedInputStream extends FilterInputStream
{
  protected volatile byte[] buf;
  protected int count;
  protected int marklimit;
  protected int markpos = -1;
  protected int pos;

  public XulBufferedInputStream(InputStream paramInputStream)
  {
    this(paramInputStream, 8192);
  }

  public XulBufferedInputStream(InputStream paramInputStream, int paramInt)
  {
    super(paramInputStream);
    if (paramInt <= 0)
      throw new IllegalArgumentException("size <= 0");
    this.buf = new byte[paramInt];
  }

  private int fillbuf(InputStream paramInputStream, byte[] paramArrayOfByte)
    throws IOException
  {
    if ((this.markpos == -1) || (this.pos - this.markpos >= this.marklimit))
    {
      int i = paramInputStream.read(paramArrayOfByte);
      int j;
      if (i > 0)
      {
        this.markpos = -1;
        this.pos = 0;
        j = 0;
        if (i != -1)
          break label60;
      }
      while (true)
      {
        this.count = j;
        return i;
        label60: j = i;
      }
    }
    int k;
    if ((this.markpos == 0) && (this.marklimit > paramArrayOfByte.length))
    {
      int n = 2 * paramArrayOfByte.length;
      if (n > this.marklimit)
        n = this.marklimit;
      byte[] arrayOfByte = new byte[n];
      System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, paramArrayOfByte.length);
      this.buf = arrayOfByte;
      paramArrayOfByte = arrayOfByte;
      this.pos -= this.markpos;
      this.markpos = 0;
      this.count = 0;
      k = paramInputStream.read(paramArrayOfByte, this.pos, paramArrayOfByte.length - this.pos);
      if (k > 0)
        break label216;
    }
    label216: for (int m = this.pos; ; m = k + this.pos)
    {
      this.count = m;
      return k;
      if (this.markpos <= 0)
        break;
      System.arraycopy(paramArrayOfByte, this.markpos, paramArrayOfByte, 0, paramArrayOfByte.length - this.markpos);
      break;
    }
  }

  private IOException streamClosed()
    throws IOException
  {
    throw new IOException("BufferedInputStream is closed");
  }

  public int available()
    throws IOException
  {
    InputStream localInputStream;
    try
    {
      localInputStream = this.in;
      if ((this.buf == null) || (localInputStream == null))
        throw streamClosed();
    }
    finally
    {
    }
    int i = this.count - this.pos;
    int j = localInputStream.available();
    int k = i + j;
    return k;
  }

  public void close()
    throws IOException
  {
    resetInputStream(null);
  }

  public void mark(int paramInt)
  {
    try
    {
      this.marklimit = paramInt;
      this.markpos = this.pos;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public boolean markSupported()
  {
    return true;
  }

  public int read()
    throws IOException
  {
    int i = -1;
    byte[] arrayOfByte;
    InputStream localInputStream;
    try
    {
      arrayOfByte = this.buf;
      localInputStream = this.in;
      if ((arrayOfByte == null) || (localInputStream == null))
        throw streamClosed();
    }
    finally
    {
    }
    if (this.pos >= this.count)
    {
      int m = fillbuf(localInputStream, arrayOfByte);
      if (m != i);
    }
    while (true)
    {
      return i;
      if (arrayOfByte != this.buf)
      {
        arrayOfByte = this.buf;
        if (arrayOfByte == null)
          throw streamClosed();
      }
      if (this.count - this.pos > 0)
      {
        int j = this.pos;
        this.pos = (j + 1);
        int k = arrayOfByte[j];
        i = k & 0xFF;
      }
    }
  }

  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    int i = -1;
    byte[] arrayOfByte;
    try
    {
      arrayOfByte = this.buf;
      if (arrayOfByte == null)
        throw streamClosed();
    }
    finally
    {
    }
    if (paramInt2 == 0)
      i = 0;
    while (true)
    {
      return i;
      InputStream localInputStream = this.in;
      if (localInputStream == null)
        throw streamClosed();
      int n;
      label138: int j;
      int k;
      if (this.pos < this.count)
      {
        if (this.count - this.pos >= paramInt2);
        for (n = paramInt2; ; n = this.count - this.pos)
        {
          System.arraycopy(arrayOfByte, this.pos, paramArrayOfByte, paramInt1, n);
          this.pos = (n + this.pos);
          if (n == paramInt2)
            break;
          if (localInputStream.available() != 0)
            break label336;
          break;
        }
        if ((this.markpos == i) && (j >= arrayOfByte.length))
        {
          k = localInputStream.read(paramArrayOfByte, paramInt1, j);
          if (k == i)
          {
            if (j == paramInt2)
              continue;
            i = paramInt2 - j;
          }
        }
        else if (fillbuf(localInputStream, arrayOfByte) == i)
        {
          if (j != paramInt2)
            i = paramInt2 - j;
        }
        else
        {
          if (arrayOfByte != this.buf)
          {
            arrayOfByte = this.buf;
            if (arrayOfByte == null)
              throw streamClosed();
          }
          if (this.count - this.pos >= j);
          for (k = j; ; k = this.count - this.pos)
          {
            System.arraycopy(arrayOfByte, this.pos, paramArrayOfByte, paramInt1, k);
            this.pos = (k + this.pos);
            break;
          }
        }
      }
      else
      {
        label336: 
        do
        {
          int m = localInputStream.available();
          if (m == 0)
          {
            i = paramInt2 - j;
            break;
          }
          paramInt1 += k;
          break label138;
          i = n;
          break;
          paramInt1 += n;
          j = paramInt2 - n;
          break label138;
          j = paramInt2;
          break label138;
          j -= k;
        }
        while (j != 0);
        i = paramInt2;
      }
    }
  }

  public void reset()
    throws IOException
  {
    try
    {
      if (this.buf == null)
        throw new IOException("Stream is closed");
    }
    finally
    {
    }
    if (-1 == this.markpos)
      throw new IOException("Mark has been invalidated.");
    this.pos = this.markpos;
  }

  public void resetInputStream(InputStream paramInputStream)
    throws IOException
  {
    InputStream localInputStream = this.in;
    this.in = paramInputStream;
    this.count = 0;
    this.marklimit = 0;
    this.markpos = -1;
    this.pos = 0;
    if (localInputStream != null)
      localInputStream.close();
  }

  public long skip(long paramLong)
    throws IOException
  {
    byte[] arrayOfByte;
    InputStream localInputStream;
    try
    {
      arrayOfByte = this.buf;
      localInputStream = this.in;
      if (arrayOfByte == null)
        throw streamClosed();
    }
    finally
    {
    }
    if (paramLong < 1L)
      paramLong = 0L;
    while (true)
    {
      return paramLong;
      if (localInputStream == null)
        throw streamClosed();
      if (this.count - this.pos >= paramLong)
      {
        this.pos = ((int)(paramLong + this.pos));
      }
      else
      {
        long l1 = this.count - this.pos;
        this.pos = this.count;
        if ((this.markpos != -1) && (paramLong <= this.marklimit))
        {
          if (fillbuf(localInputStream, arrayOfByte) == -1)
          {
            paramLong = l1;
          }
          else if (this.count - this.pos >= paramLong - l1)
          {
            this.pos = ((int)(this.pos + (paramLong - l1)));
          }
          else
          {
            long l3 = l1 + (this.count - this.pos);
            this.pos = this.count;
            paramLong = l3;
          }
        }
        else
        {
          long l2 = localInputStream.skip(paramLong - l1);
          paramLong = l1 + l2;
        }
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Utils.XulBufferedInputStream
 * JD-Core Version:    0.6.2
 */