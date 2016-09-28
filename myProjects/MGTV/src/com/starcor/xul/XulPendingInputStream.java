package com.starcor.xul;

import java.io.IOException;
import java.io.InputStream;

public class XulPendingInputStream extends InputStream
{
  volatile InputStream _baseStream = null;
  volatile boolean _isCancelled = false;

  public int available()
    throws IOException
  {
    return this._baseStream.available();
  }

  public void cancel()
  {
    this._isCancelled = true;
    this._baseStream = null;
    try
    {
      notifyAll();
      return;
    }
    finally
    {
    }
  }

  public boolean checkPending()
  {
    InputStream localInputStream1 = this._baseStream;
    boolean bool1 = false;
    if (localInputStream1 == null)
    {
      boolean bool2 = this._isCancelled;
      bool1 = false;
      if (bool2);
    }
    try
    {
      wait(10000L);
      InputStream localInputStream2 = this._baseStream;
      bool1 = false;
      if (localInputStream2 == null)
        bool1 = true;
      return bool1;
    }
    catch (InterruptedException localInterruptedException)
    {
      while (true)
        localInterruptedException.printStackTrace();
    }
    finally
    {
    }
  }

  public void close()
    throws IOException
  {
    this._baseStream.close();
  }

  public boolean isReady()
  {
    return this._baseStream != null;
  }

  public void mark(int paramInt)
  {
    this._baseStream.mark(paramInt);
  }

  public boolean markSupported()
  {
    return this._baseStream.markSupported();
  }

  public int read()
    throws IOException
  {
    return this._baseStream.read();
  }

  public int read(byte[] paramArrayOfByte)
    throws IOException
  {
    return this._baseStream.read(paramArrayOfByte);
  }

  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    return this._baseStream.read(paramArrayOfByte, paramInt1, paramInt2);
  }

  public void reload()
  {
    this._isCancelled = false;
    this._baseStream = null;
    try
    {
      notifyAll();
      return;
    }
    finally
    {
    }
  }

  public void reset()
    throws IOException
  {
    try
    {
      this._baseStream.reset();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void setBaseStream(InputStream paramInputStream)
  {
    this._isCancelled = false;
    this._baseStream = paramInputStream;
    try
    {
      notifyAll();
      return;
    }
    finally
    {
    }
  }

  public long skip(long paramLong)
    throws IOException
  {
    return this._baseStream.skip(paramLong);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.xul.XulPendingInputStream
 * JD-Core Version:    0.6.2
 */