package com.starcor.cache.disk;

import com.starcor.utils.CountingInputStream;
import com.starcor.utils.IoUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class DiskDataManager
{
  private static final int CACHE_MAGIC = 538051844;

  public static DiskCache.Entry readEntry(File paramFile)
    throws IOException
  {
    DiskBaseCache.CacheHeader localCacheHeader = new DiskBaseCache.CacheHeader();
    CountingInputStream localCountingInputStream = new CountingInputStream(new FileInputStream(paramFile));
    if (IoUtils.readInt(localCountingInputStream) != 538051844)
      throw new IOException();
    localCacheHeader.key = IoUtils.readString(localCountingInputStream);
    IoUtils.readLong(localCountingInputStream);
    byte[] arrayOfByte = IoUtils.streamToBytes(localCountingInputStream, (int)(paramFile.length() - localCountingInputStream.bytesRead));
    DiskCache.Entry localEntry = new DiskCache.Entry();
    localEntry.data = arrayOfByte;
    if (localCountingInputStream != null);
    try
    {
      localCountingInputStream.close();
      return localEntry;
    }
    catch (IOException localIOException)
    {
    }
    return null;
  }

  public static DiskBaseCache.CacheHeader readHeader(File paramFile)
    throws IOException
  {
    DiskBaseCache.CacheHeader localCacheHeader = new DiskBaseCache.CacheHeader();
    CountingInputStream localCountingInputStream = new CountingInputStream(new FileInputStream(paramFile));
    if (IoUtils.readInt(localCountingInputStream) != 538051844)
      throw new IOException();
    localCacheHeader.key = IoUtils.readString(localCountingInputStream);
    localCacheHeader.lastModified = IoUtils.readLong(localCountingInputStream);
    localCacheHeader.size = (paramFile.length() - localCountingInputStream.bytesRead);
    if (localCountingInputStream != null);
    try
    {
      localCountingInputStream.close();
      return localCacheHeader;
    }
    catch (IOException localIOException)
    {
    }
    return null;
  }

  // ERROR //
  public static boolean writeHeader(DiskBaseCache.CacheHeader paramCacheHeader, File paramFile)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_2
    //   2: new 83	java/io/FileOutputStream
    //   5: dup
    //   6: aload_1
    //   7: invokespecial 84	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   10: astore_3
    //   11: aload_3
    //   12: ldc 7
    //   14: invokestatic 88	com/starcor/utils/IoUtils:writeInt	(Ljava/io/OutputStream;I)V
    //   17: aload_3
    //   18: aload_0
    //   19: getfield 43	com/starcor/cache/disk/DiskBaseCache$CacheHeader:key	Ljava/lang/String;
    //   22: invokestatic 92	com/starcor/utils/IoUtils:writeString	(Ljava/io/OutputStream;Ljava/lang/String;)V
    //   25: aload_3
    //   26: aload_0
    //   27: getfield 76	com/starcor/cache/disk/DiskBaseCache$CacheHeader:lastModified	J
    //   30: invokestatic 96	com/starcor/utils/IoUtils:writeLong	(Ljava/io/OutputStream;J)V
    //   33: aload_3
    //   34: invokevirtual 99	java/io/FileOutputStream:flush	()V
    //   37: aload_3
    //   38: invokestatic 103	com/starcor/utils/IoUtils:closeSilently	(Ljava/io/Closeable;)V
    //   41: iconst_1
    //   42: ireturn
    //   43: astore 4
    //   45: ldc 105
    //   47: aload 4
    //   49: invokevirtual 109	java/io/IOException:toString	()Ljava/lang/String;
    //   52: invokestatic 115	com/starcor/utils/Logger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   55: aload_2
    //   56: invokestatic 103	com/starcor/utils/IoUtils:closeSilently	(Ljava/io/Closeable;)V
    //   59: iconst_0
    //   60: ireturn
    //   61: astore 5
    //   63: aload_2
    //   64: invokestatic 103	com/starcor/utils/IoUtils:closeSilently	(Ljava/io/Closeable;)V
    //   67: aload 5
    //   69: athrow
    //   70: astore 5
    //   72: aload_3
    //   73: astore_2
    //   74: goto -11 -> 63
    //   77: astore 4
    //   79: aload_3
    //   80: astore_2
    //   81: goto -36 -> 45
    //
    // Exception table:
    //   from	to	target	type
    //   2	11	43	java/io/IOException
    //   2	11	61	finally
    //   45	55	61	finally
    //   11	37	70	finally
    //   11	37	77	java/io/IOException
  }

  // ERROR //
  public static boolean writeHeaderAndEntry(DiskBaseCache.CacheHeader paramCacheHeader, DiskCache.Entry paramEntry, File paramFile)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: new 83	java/io/FileOutputStream
    //   5: dup
    //   6: aload_2
    //   7: invokespecial 84	java/io/FileOutputStream:<init>	(Ljava/io/File;)V
    //   10: astore 4
    //   12: aload 4
    //   14: ldc 7
    //   16: invokestatic 88	com/starcor/utils/IoUtils:writeInt	(Ljava/io/OutputStream;I)V
    //   19: aload 4
    //   21: aload_0
    //   22: getfield 43	com/starcor/cache/disk/DiskBaseCache$CacheHeader:key	Ljava/lang/String;
    //   25: invokestatic 92	com/starcor/utils/IoUtils:writeString	(Ljava/io/OutputStream;Ljava/lang/String;)V
    //   28: aload 4
    //   30: aload_0
    //   31: getfield 76	com/starcor/cache/disk/DiskBaseCache$CacheHeader:lastModified	J
    //   34: invokestatic 96	com/starcor/utils/IoUtils:writeLong	(Ljava/io/OutputStream;J)V
    //   37: aload_1
    //   38: getfield 121	com/starcor/cache/disk/DiskCache$Entry:inputStream	Ljava/io/InputStream;
    //   41: astore 7
    //   43: aload 7
    //   45: ifnull +74 -> 119
    //   48: aload_1
    //   49: getfield 121	com/starcor/cache/disk/DiskCache$Entry:inputStream	Ljava/io/InputStream;
    //   52: aload 4
    //   54: aconst_null
    //   55: invokestatic 125	com/starcor/utils/IoUtils:copyStream	(Ljava/io/InputStream;Ljava/io/OutputStream;Lcom/starcor/utils/IoUtils$CopyListener;)I
    //   58: istore 9
    //   60: aload_1
    //   61: getfield 121	com/starcor/cache/disk/DiskCache$Entry:inputStream	Ljava/io/InputStream;
    //   64: invokestatic 103	com/starcor/utils/IoUtils:closeSilently	(Ljava/io/Closeable;)V
    //   67: aload_0
    //   68: iload 9
    //   70: i2l
    //   71: putfield 79	com/starcor/cache/disk/DiskBaseCache$CacheHeader:size	J
    //   74: aload 4
    //   76: invokevirtual 99	java/io/FileOutputStream:flush	()V
    //   79: aload 4
    //   81: invokestatic 103	com/starcor/utils/IoUtils:closeSilently	(Ljava/io/Closeable;)V
    //   84: iconst_1
    //   85: ireturn
    //   86: astore 8
    //   88: aload_1
    //   89: getfield 121	com/starcor/cache/disk/DiskCache$Entry:inputStream	Ljava/io/InputStream;
    //   92: invokestatic 103	com/starcor/utils/IoUtils:closeSilently	(Ljava/io/Closeable;)V
    //   95: aload 8
    //   97: athrow
    //   98: astore 6
    //   100: aload 4
    //   102: astore_3
    //   103: ldc 105
    //   105: aload 6
    //   107: invokevirtual 109	java/io/IOException:toString	()Ljava/lang/String;
    //   110: invokestatic 115	com/starcor/utils/Logger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   113: aload_3
    //   114: invokestatic 103	com/starcor/utils/IoUtils:closeSilently	(Ljava/io/Closeable;)V
    //   117: iconst_0
    //   118: ireturn
    //   119: aload 4
    //   121: aload_1
    //   122: getfield 67	com/starcor/cache/disk/DiskCache$Entry:data	[B
    //   125: invokevirtual 129	java/io/FileOutputStream:write	([B)V
    //   128: goto -54 -> 74
    //   131: astore 5
    //   133: aload 4
    //   135: astore_3
    //   136: aload_3
    //   137: invokestatic 103	com/starcor/utils/IoUtils:closeSilently	(Ljava/io/Closeable;)V
    //   140: aload 5
    //   142: athrow
    //   143: astore 5
    //   145: goto -9 -> 136
    //   148: astore 6
    //   150: aconst_null
    //   151: astore_3
    //   152: goto -49 -> 103
    //
    // Exception table:
    //   from	to	target	type
    //   48	60	86	finally
    //   12	43	98	java/io/IOException
    //   60	74	98	java/io/IOException
    //   74	79	98	java/io/IOException
    //   88	98	98	java/io/IOException
    //   119	128	98	java/io/IOException
    //   12	43	131	finally
    //   60	74	131	finally
    //   74	79	131	finally
    //   88	98	131	finally
    //   119	128	131	finally
    //   2	12	143	finally
    //   103	113	143	finally
    //   2	12	148	java/io/IOException
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.cache.disk.DiskDataManager
 * JD-Core Version:    0.6.2
 */