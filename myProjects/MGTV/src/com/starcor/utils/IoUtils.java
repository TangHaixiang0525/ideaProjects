package com.starcor.utils;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class IoUtils
{
  public static final int CONTINUE_LOADING_PERCENTAGE = 75;
  public static final int DEFAULT_BUFFER_SIZE = 32768;
  public static final int DEFAULT_IMAGE_TOTAL_SIZE = 512000;

  public static void closeSilently(Closeable paramCloseable)
  {
    if (paramCloseable != null);
    try
    {
      paramCloseable.close();
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public static int copyStream(InputStream paramInputStream, OutputStream paramOutputStream, CopyListener paramCopyListener)
    throws IOException
  {
    return copyStream(paramInputStream, paramOutputStream, paramCopyListener, 32768);
  }

  public static int copyStream(InputStream paramInputStream, OutputStream paramOutputStream, CopyListener paramCopyListener, int paramInt)
    throws IOException
  {
    int i = paramInputStream.available();
    if (i <= 0)
      i = 512000;
    byte[] arrayOfByte = new byte[paramInt];
    boolean bool = shouldStopLoading(paramCopyListener, 0, i);
    int j = 0;
    if (bool)
      return -1;
    int k;
    do
    {
      paramOutputStream.write(arrayOfByte, 0, k);
      j += k;
      if (shouldStopLoading(paramCopyListener, j, i))
        break;
      k = paramInputStream.read(arrayOfByte, 0, paramInt);
    }
    while (k != -1);
    paramOutputStream.flush();
    return j;
  }

  private static int read(InputStream paramInputStream)
    throws IOException
  {
    int i = paramInputStream.read();
    if (i == -1)
      throw new EOFException();
    return i;
  }

  // ERROR //
  public static void readAndCloseStream(InputStream paramInputStream)
  {
    // Byte code:
    //   0: ldc 9
    //   2: newarray byte
    //   4: astore_1
    //   5: aload_0
    //   6: aload_1
    //   7: iconst_0
    //   8: ldc 9
    //   10: invokevirtual 51	java/io/InputStream:read	([BII)I
    //   13: istore 4
    //   15: iload 4
    //   17: iconst_m1
    //   18: if_icmpne -13 -> 5
    //   21: aload_0
    //   22: invokestatic 64	com/starcor/utils/IoUtils:closeSilently	(Ljava/io/Closeable;)V
    //   25: return
    //   26: astore_3
    //   27: aload_0
    //   28: invokestatic 64	com/starcor/utils/IoUtils:closeSilently	(Ljava/io/Closeable;)V
    //   31: return
    //   32: astore_2
    //   33: aload_0
    //   34: invokestatic 64	com/starcor/utils/IoUtils:closeSilently	(Ljava/io/Closeable;)V
    //   37: aload_2
    //   38: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   5	15	26	java/io/IOException
    //   5	15	32	finally
  }

  public static int readInt(InputStream paramInputStream)
    throws IOException
  {
    return 0x0 | read(paramInputStream) << 0 | read(paramInputStream) << 8 | read(paramInputStream) << 16 | read(paramInputStream) << 24;
  }

  public static long readLong(InputStream paramInputStream)
    throws IOException
  {
    return 0L | (0xFF & read(paramInputStream)) << 0 | (0xFF & read(paramInputStream)) << 8 | (0xFF & read(paramInputStream)) << 16 | (0xFF & read(paramInputStream)) << 24 | (0xFF & read(paramInputStream)) << 32 | (0xFF & read(paramInputStream)) << 40 | (0xFF & read(paramInputStream)) << 48 | (0xFF & read(paramInputStream)) << 56;
  }

  public static String readString(InputStream paramInputStream)
    throws IOException
  {
    return new String(streamToBytes(paramInputStream, (int)readLong(paramInputStream)), "UTF-8");
  }

  public static Map<String, String> readStringStringMap(InputStream paramInputStream)
    throws IOException
  {
    int i = readInt(paramInputStream);
    Object localObject;
    if (i == 0)
      localObject = Collections.emptyMap();
    for (int j = 0; ; j++)
    {
      if (j >= i)
      {
        return localObject;
        localObject = new HashMap(i);
        break;
      }
      ((Map)localObject).put(readString(paramInputStream).intern(), readString(paramInputStream).intern());
    }
  }

  private static boolean shouldStopLoading(CopyListener paramCopyListener, int paramInt1, int paramInt2)
  {
    return (paramCopyListener != null) && (!paramCopyListener.onBytesCopied(paramInt1, paramInt2)) && (paramInt1 * 100 / paramInt2 < 75);
  }

  public static byte[] streamToBytes(InputStream paramInputStream, int paramInt)
    throws IOException
  {
    byte[] arrayOfByte = new byte[paramInt];
    int i = 0;
    while (true)
    {
      int j;
      if (i < paramInt)
      {
        j = paramInputStream.read(arrayOfByte, i, paramInt - i);
        if (j != -1);
      }
      else
      {
        if (i == paramInt)
          break;
        throw new IOException("Expected " + paramInt + " bytes, read " + i + " bytes");
      }
      i += j;
    }
    return arrayOfByte;
  }

  public static void writeInt(OutputStream paramOutputStream, int paramInt)
    throws IOException
  {
    paramOutputStream.write(0xFF & paramInt >> 0);
    paramOutputStream.write(0xFF & paramInt >> 8);
    paramOutputStream.write(0xFF & paramInt >> 16);
    paramOutputStream.write(0xFF & paramInt >> 24);
  }

  public static void writeLong(OutputStream paramOutputStream, long paramLong)
    throws IOException
  {
    paramOutputStream.write((byte)(int)(paramLong >>> 0));
    paramOutputStream.write((byte)(int)(paramLong >>> 8));
    paramOutputStream.write((byte)(int)(paramLong >>> 16));
    paramOutputStream.write((byte)(int)(paramLong >>> 24));
    paramOutputStream.write((byte)(int)(paramLong >>> 32));
    paramOutputStream.write((byte)(int)(paramLong >>> 40));
    paramOutputStream.write((byte)(int)(paramLong >>> 48));
    paramOutputStream.write((byte)(int)(paramLong >>> 56));
  }

  public static void writeString(OutputStream paramOutputStream, String paramString)
    throws IOException
  {
    byte[] arrayOfByte = paramString.getBytes("UTF-8");
    writeLong(paramOutputStream, arrayOfByte.length);
    paramOutputStream.write(arrayOfByte, 0, arrayOfByte.length);
  }

  public static void writeStringStringMap(Map<String, String> paramMap, OutputStream paramOutputStream)
    throws IOException
  {
    if (paramMap != null)
    {
      writeInt(paramOutputStream, paramMap.size());
      Iterator localIterator = paramMap.entrySet().iterator();
      while (true)
      {
        if (!localIterator.hasNext())
          return;
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        writeString(paramOutputStream, (String)localEntry.getKey());
        writeString(paramOutputStream, (String)localEntry.getValue());
      }
    }
    writeInt(paramOutputStream, 0);
  }

  public static abstract interface CopyListener
  {
    public abstract boolean onBytesCopied(int paramInt1, int paramInt2);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.utils.IoUtils
 * JD-Core Version:    0.6.2
 */