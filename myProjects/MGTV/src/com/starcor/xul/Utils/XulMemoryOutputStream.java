package com.starcor.xul.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class XulMemoryOutputStream extends OutputStream
{
  byte[] _dataBuf;
  volatile int _inStreamNum = 0;
  int _maxSize = 0;
  int _writePos = 0;

  static
  {
    if (!XulMemoryOutputStream.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  public XulMemoryOutputStream()
  {
    this(0);
  }

  public XulMemoryOutputStream(int paramInt)
  {
    this._maxSize = paramInt;
  }

  private void _enlargeBuffer(int paramInt)
  {
    int i = 0xFFFFC000 & paramInt + 16383;
    if (i > this._maxSize)
      this._maxSize = i;
    do
    {
      byte[] arrayOfByte = new byte[i];
      if (this._dataBuf != null)
        System.arraycopy(this._dataBuf, 0, arrayOfByte, 0, this._writePos);
      this._dataBuf = arrayOfByte;
      return;
      i = this._maxSize;
    }
    while (this._dataBuf == null);
  }

  private void addRef()
  {
    try
    {
      this._inStreamNum = (1 + this._inStreamNum);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private void releaseRef()
  {
    try
    {
      int i = -1 + this._inStreamNum;
      this._inStreamNum = i;
      if (i == 0)
        onClose();
      return;
    }
    finally
    {
    }
  }

  public void expand(int paramInt)
  {
    _enlargeBuffer(paramInt);
  }

  public byte[] getDataBuffer()
  {
    return this._dataBuf;
  }

  public int getDataSize()
  {
    return this._writePos;
  }

  public void onClose()
  {
  }

  public void reset(int paramInt)
  {
    assert (this._inStreamNum == 0);
    this._writePos = 0;
    _enlargeBuffer(paramInt);
  }

  public void setDataSize(int paramInt)
  {
    this._writePos = paramInt;
  }

  public InputStream toInputStream()
  {
    return toInputStream(0, this._writePos);
  }

  public InputStream toInputStream(int paramInt)
  {
    return toInputStream(0, paramInt);
  }

  public InputStream toInputStream(final int paramInt1, final int paramInt2)
  {
    if (paramInt1 > this._writePos)
      paramInt1 = this._writePos;
    if (paramInt1 + paramInt2 > this._writePos)
      paramInt2 = this._writePos - paramInt1;
    InputStream local1 = new InputStream()
    {
      byte[] _dataBuf = XulMemoryOutputStream.this._dataBuf;
      int _markPos = paramInt1;
      int _maxSize = paramInt1 + paramInt2;
      int _readPos = paramInt1;

      public int available()
        throws IOException
      {
        return this._maxSize - this._readPos;
      }

      public void close()
        throws IOException
      {
        if (this._dataBuf == null)
          return;
        this._dataBuf = null;
        this._readPos = 0;
        this._maxSize = 0;
        XulMemoryOutputStream.this.releaseRef();
      }

      protected void finalize()
        throws Throwable
      {
        close();
        super.finalize();
      }

      public void mark(int paramAnonymousInt)
      {
        this._markPos = this._readPos;
      }

      public boolean markSupported()
      {
        return true;
      }

      public int read()
        throws IOException
      {
        if (this._readPos >= this._maxSize)
          return -1;
        byte[] arrayOfByte = this._dataBuf;
        int i = this._readPos;
        this._readPos = (i + 1);
        return arrayOfByte[i];
      }

      public int read(byte[] paramAnonymousArrayOfByte, int paramAnonymousInt1, int paramAnonymousInt2)
        throws IOException
      {
        int i = this._readPos;
        int j = this._maxSize - i;
        if (j <= 0)
          return -1;
        int k = Math.min(j, paramAnonymousInt2);
        System.arraycopy(this._dataBuf, i, paramAnonymousArrayOfByte, paramAnonymousInt1, k);
        this._readPos = (i + k);
        return k;
      }

      public void reset()
        throws IOException
      {
        try
        {
          this._readPos = this._markPos;
          this._markPos = paramInt1;
          return;
        }
        finally
        {
          localObject = finally;
          throw localObject;
        }
      }

      public long skip(long paramAnonymousLong)
        throws IOException
      {
        long l = Math.min(this._maxSize - this._readPos, paramAnonymousLong);
        this._readPos = ((int)(l + this._readPos));
        return l;
      }
    };
    addRef();
    return local1;
  }

  public void write(int paramInt)
    throws IOException
  {
    int i = this._writePos;
    int j = this._maxSize;
    if ((this._dataBuf == null) || (i + 1 >= j))
      _enlargeBuffer(j + 128);
    this._dataBuf[i] = ((byte)paramInt);
    this._writePos = (i + 1);
  }

  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    int i = this._writePos;
    int j = this._maxSize;
    if ((this._dataBuf == null) || (i + paramInt2 >= j))
      _enlargeBuffer(32 + (j + paramInt2));
    System.arraycopy(paramArrayOfByte, paramInt1, this._dataBuf, i, paramInt2);
    this._writePos = (i + paramInt2);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.Utils.XulMemoryOutputStream
 * JD-Core Version:    0.6.2
 */