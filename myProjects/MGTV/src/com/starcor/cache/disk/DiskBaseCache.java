package com.starcor.cache.disk;

import android.os.SystemClock;
import com.starcor.utils.Logger;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class DiskBaseCache
  implements DiskCache
{
  private static final int DEFAULT_DISK_USAGE_BYTES = 5242880;
  private static final float HYSTERESIS_FACTOR = 0.9F;
  protected static final String TAG = DiskBaseCache.class.getSimpleName();
  private final Map<String, CacheHeader> mEntries = new LinkedHashMap(16, 0.75F, true);
  private final int mMaxCacheSizeInBytes;
  private final File mRootDirectory;
  private long mTotalSize = 0L;

  public DiskBaseCache(File paramFile)
  {
    this(paramFile, 5242880);
  }

  public DiskBaseCache(File paramFile, int paramInt)
  {
    this.mRootDirectory = paramFile;
    this.mMaxCacheSizeInBytes = paramInt;
  }

  private String getFilenameForKey(String paramString)
  {
    int i = paramString.length() / 2;
    return String.valueOf(paramString.substring(0, i).hashCode()) + String.valueOf(paramString.substring(i).hashCode());
  }

  private void pruneIfNeeded()
  {
    if (this.mTotalSize < this.mMaxCacheSizeInBytes)
      return;
    if (Logger.DEBUG)
      Logger.d(TAG, "Pruning old cache entries.");
    long l1 = this.mTotalSize;
    int i = 0;
    long l2 = SystemClock.elapsedRealtime();
    Iterator localIterator = this.mEntries.entrySet().iterator();
    label56: label66: CacheHeader localCacheHeader;
    if (!localIterator.hasNext())
    {
      if (Logger.DEBUG)
      {
        String str2 = TAG;
        Object[] arrayOfObject2 = new Object[3];
        arrayOfObject2[0] = Integer.valueOf(i);
        arrayOfObject2[1] = Long.valueOf(this.mTotalSize - l1);
        arrayOfObject2[2] = Long.valueOf(SystemClock.elapsedRealtime() - l2);
        Logger.d(str2, String.format("pruned %d files, %d bytes, %d ms", arrayOfObject2));
      }
    }
    else
    {
      localCacheHeader = (CacheHeader)((Map.Entry)localIterator.next()).getValue();
      if (!getFileForKey(localCacheHeader.key).delete())
        break label209;
      this.mTotalSize -= localCacheHeader.size;
    }
    while (true)
    {
      localIterator.remove();
      i++;
      if ((float)this.mTotalSize >= 0.9F * this.mMaxCacheSizeInBytes)
        break label56;
      break label66;
      break;
      label209: String str1 = TAG;
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = localCacheHeader.key;
      arrayOfObject1[1] = getFilenameForKey(localCacheHeader.key);
      Logger.d(str1, String.format("Could not delete cache entry for key=%s, filename=%s", arrayOfObject1));
    }
  }

  private void putEntry(String paramString, CacheHeader paramCacheHeader)
  {
    if (!this.mEntries.containsKey(paramString));
    CacheHeader localCacheHeader;
    for (this.mTotalSize += paramCacheHeader.size; ; this.mTotalSize += paramCacheHeader.size - localCacheHeader.size)
    {
      this.mEntries.put(paramString, paramCacheHeader);
      return;
      localCacheHeader = (CacheHeader)this.mEntries.get(paramString);
    }
  }

  private void removeEntry(String paramString)
  {
    CacheHeader localCacheHeader = (CacheHeader)this.mEntries.get(paramString);
    if (localCacheHeader != null)
    {
      this.mTotalSize -= localCacheHeader.size;
      this.mEntries.remove(paramString);
    }
  }

  public void clear()
  {
    while (true)
    {
      int i;
      int j;
      try
      {
        File[] arrayOfFile = this.mRootDirectory.listFiles();
        if (arrayOfFile != null)
        {
          i = arrayOfFile.length;
          j = 0;
        }
        else
        {
          this.mEntries.clear();
          this.mTotalSize = 0L;
          Logger.d(TAG, "Cache cleared.");
          return;
          arrayOfFile[j].delete();
          j++;
        }
      }
      finally
      {
      }
      if (j < i);
    }
  }

  public DiskCache.Entry get(String paramString)
  {
    try
    {
      CacheHeader localCacheHeader = (CacheHeader)this.mEntries.get(paramString);
      Object localObject2 = null;
      if (localCacheHeader == null);
      while (true)
      {
        return localObject2;
        File localFile = getFileForKey(paramString);
        localCacheHeader.lastModified = System.currentTimeMillis();
        DiskDataManager.writeHeader(localCacheHeader, localFile);
        try
        {
          DiskCache.Entry localEntry = DiskDataManager.readEntry(localFile);
          localObject2 = localEntry;
        }
        catch (IOException localIOException)
        {
          String str = TAG;
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = localFile.getAbsolutePath();
          arrayOfObject[1] = localIOException.toString();
          Logger.d(str, String.format("%s: %s", arrayOfObject));
          remove(paramString);
          localObject2 = null;
        }
      }
    }
    finally
    {
    }
  }

  public File getFileForKey(String paramString)
  {
    return new File(this.mRootDirectory, getFilenameForKey(paramString));
  }

  // ERROR //
  public void initialize()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 51	com/starcor/cache/disk/DiskBaseCache:mRootDirectory	Ljava/io/File;
    //   6: invokevirtual 238	java/io/File:exists	()Z
    //   9: ifne +28 -> 37
    //   12: aload_0
    //   13: getfield 51	com/starcor/cache/disk/DiskBaseCache:mRootDirectory	Ljava/io/File;
    //   16: invokevirtual 241	java/io/File:mkdirs	()Z
    //   19: ifne +15 -> 34
    //   22: ldc 243
    //   24: aload_0
    //   25: getfield 51	com/starcor/cache/disk/DiskBaseCache:mRootDirectory	Ljava/io/File;
    //   28: invokevirtual 225	java/io/File:getAbsolutePath	()Ljava/lang/String;
    //   31: invokestatic 246	com/starcor/utils/Logger:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   34: aload_0
    //   35: monitorexit
    //   36: return
    //   37: aload_0
    //   38: getfield 51	com/starcor/cache/disk/DiskBaseCache:mRootDirectory	Ljava/io/File;
    //   41: invokevirtual 197	java/io/File:listFiles	()[Ljava/io/File;
    //   44: astore_2
    //   45: aload_2
    //   46: ifnull -12 -> 34
    //   49: aload_2
    //   50: arraylength
    //   51: istore_3
    //   52: iconst_0
    //   53: istore 4
    //   55: iload 4
    //   57: iload_3
    //   58: if_icmpge -24 -> 34
    //   61: aload_2
    //   62: iload 4
    //   64: aaload
    //   65: astore 5
    //   67: aload 5
    //   69: invokestatic 250	com/starcor/cache/disk/DiskDataManager:readHeader	(Ljava/io/File;)Lcom/starcor/cache/disk/DiskBaseCache$CacheHeader;
    //   72: astore 11
    //   74: aload_0
    //   75: aload 11
    //   77: getfield 156	com/starcor/cache/disk/DiskBaseCache$CacheHeader:key	Ljava/lang/String;
    //   80: aload 11
    //   82: invokespecial 252	com/starcor/cache/disk/DiskBaseCache:putEntry	(Ljava/lang/String;Lcom/starcor/cache/disk/DiskBaseCache$CacheHeader;)V
    //   85: iconst_0
    //   86: ifeq +7 -> 93
    //   89: aconst_null
    //   90: invokevirtual 257	java/io/FileInputStream:close	()V
    //   93: iinc 4 1
    //   96: goto -41 -> 55
    //   99: astore 8
    //   101: aload 5
    //   103: ifnull +9 -> 112
    //   106: aload 5
    //   108: invokevirtual 165	java/io/File:delete	()Z
    //   111: pop
    //   112: iconst_0
    //   113: ifeq -20 -> 93
    //   116: aconst_null
    //   117: invokevirtual 257	java/io/FileInputStream:close	()V
    //   120: goto -27 -> 93
    //   123: astore 9
    //   125: goto -32 -> 93
    //   128: astore 6
    //   130: iconst_0
    //   131: ifeq +7 -> 138
    //   134: aconst_null
    //   135: invokevirtual 257	java/io/FileInputStream:close	()V
    //   138: aload 6
    //   140: athrow
    //   141: astore_1
    //   142: aload_0
    //   143: monitorexit
    //   144: aload_1
    //   145: athrow
    //   146: astore 7
    //   148: goto -10 -> 138
    //   151: astore 12
    //   153: goto -60 -> 93
    //
    // Exception table:
    //   from	to	target	type
    //   67	85	99	java/io/IOException
    //   116	120	123	java/io/IOException
    //   67	85	128	finally
    //   106	112	128	finally
    //   2	34	141	finally
    //   37	45	141	finally
    //   49	52	141	finally
    //   61	67	141	finally
    //   89	93	141	finally
    //   116	120	141	finally
    //   134	138	141	finally
    //   138	141	141	finally
    //   134	138	146	java/io/IOException
    //   89	93	151	java/io/IOException
  }

  public boolean put(String paramString, DiskCache.Entry paramEntry)
  {
    try
    {
      pruneIfNeeded();
      File localFile = getFileForKey(paramString);
      CacheHeader localCacheHeader = new CacheHeader(paramString, paramEntry);
      boolean bool1 = DiskDataManager.writeHeaderAndEntry(localCacheHeader, paramEntry, localFile);
      putEntry(paramString, localCacheHeader);
      if (bool1);
      for (boolean bool2 = true; ; bool2 = false)
      {
        return bool2;
        if (!localFile.delete())
          Logger.d("Could not clean up file %s", localFile.getAbsolutePath());
      }
    }
    finally
    {
    }
  }

  public boolean remove(String paramString)
  {
    try
    {
      boolean bool = getFileForKey(paramString).delete();
      removeEntry(paramString);
      if (!bool)
      {
        String str = TAG;
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = paramString;
        arrayOfObject[1] = getFilenameForKey(paramString);
        Logger.d(str, String.format("Could not delete cache entry for key=%s, filename=%s", arrayOfObject));
      }
      return bool;
    }
    finally
    {
    }
  }

  static class CacheHeader
  {
    public String key;
    public long lastModified;
    public long size;

    CacheHeader()
    {
    }

    public CacheHeader(String paramString, DiskCache.Entry paramEntry)
    {
      this.key = paramString;
      if (paramEntry.data != null)
        this.size = paramEntry.data.length;
      this.lastModified = System.currentTimeMillis();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.cache.disk.DiskBaseCache
 * JD-Core Version:    0.6.2
 */