package com.starcor.hunan.opendownload.logupload;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class CompressString
{
  public static final int _TEMP_OUTPUT_STREAM_CAPACITY_ = 2048;
  static ByteArrayOutputStream tempOutputStream = new ByteArrayOutputStream(2048);
  private byte[] compressedString;
  private boolean isCompressed;
  private int length;

  private CompressString()
  {
  }

  public CompressString(byte[] paramArrayOfByte)
  {
    int i = paramArrayOfByte.length;
    if (i < 32)
    {
      this.compressedString = paramArrayOfByte;
      this.isCompressed = false;
    }
    while (true)
    {
      this.length = i;
      return;
      try
      {
        this.compressedString = compress(paramArrayOfByte);
        this.isCompressed = true;
      }
      catch (IOException localIOException)
      {
        this.compressedString = paramArrayOfByte;
        this.isCompressed = false;
      }
    }
  }

  private static byte[] compress(byte[] paramArrayOfByte)
    throws IOException
  {
    try
    {
      ByteArrayOutputStream localByteArrayOutputStream;
      if (32 + paramArrayOfByte.length <= 2048)
      {
        localByteArrayOutputStream = tempOutputStream;
        localByteArrayOutputStream.reset();
      }
      while (true)
      {
        GZIPOutputStream localGZIPOutputStream = new GZIPOutputStream(localByteArrayOutputStream, 128);
        localGZIPOutputStream.write(paramArrayOfByte);
        localGZIPOutputStream.close();
        byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
        localByteArrayOutputStream.close();
        return arrayOfByte;
        localByteArrayOutputStream = new ByteArrayOutputStream(paramArrayOfByte.length);
      }
    }
    finally
    {
    }
  }

  public int getLength()
  {
    return this.length;
  }

  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if ((paramInt2 <= 0) || (paramInt1 >= paramInt2))
      return -1;
    int i = Math.min(paramInt2 - paramInt1, this.length);
    if (this.isCompressed);
    try
    {
      int j = new GZIPInputStream(new ByteArrayInputStream(this.compressedString)).read(paramArrayOfByte, paramInt1, i);
      return j;
      System.arraycopy(this.compressedString, 0, paramArrayOfByte, paramInt1, i);
      return this.length;
    }
    catch (IOException localIOException)
    {
    }
    return -1;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.opendownload.logupload.CompressString
 * JD-Core Version:    0.6.2
 */