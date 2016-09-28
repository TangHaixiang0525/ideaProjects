package com.starcor.cache.core;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Build.VERSION;
import com.starcor.cache.disk.DiskBaseCache;
import com.starcor.cache.disk.DiskCache;
import com.starcor.cache.disk.DiskWrapperCache;
import com.starcor.cache.memory.LruMemoryCache;
import com.starcor.cache.memory.MemoryCache;
import com.starcor.cache.memory.MemorySizeProcessor;
import com.starcor.cache.memory.MemoryWrapperCache;
import com.starcor.cache.naming.FileNameGenerator;
import com.starcor.cache.naming.HashCodeFileNameGenerator;
import com.starcor.cache.utils.StorageUtils;
import com.starcor.utils.Logger;
import java.io.File;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultConfigurationFactory
{
  public static DiskCache createDiskCache(Context paramContext, FileNameGenerator paramFileNameGenerator, int paramInt1, int paramInt2)
  {
    createReserveDiskCacheDir(paramContext);
    if ((paramInt1 > 0) || (paramInt2 > 0))
    {
      File localFile = StorageUtils.getIndividualCacheDirectory(paramContext);
      try
      {
        DiskWrapperCache localDiskWrapperCache = new DiskWrapperCache(new DiskBaseCache(localFile, paramInt1), paramFileNameGenerator);
        return localDiskWrapperCache;
      }
      catch (Exception localException)
      {
        Logger.e(localException);
      }
    }
    return new DiskWrapperCache(new DiskBaseCache(StorageUtils.getCacheDirectory(paramContext)), paramFileNameGenerator);
  }

  public static FileNameGenerator createFileNameGenerator()
  {
    return new HashCodeFileNameGenerator();
  }

  public static MemoryCache createMemoryCache(Context paramContext, FileNameGenerator paramFileNameGenerator, MemorySizeProcessor paramMemorySizeProcessor, int paramInt)
  {
    if (paramInt == 0)
      paramInt = 1048576 * ((ActivityManager)paramContext.getSystemService("activity")).getMemoryClass() / 8;
    if (paramFileNameGenerator != null)
      return new MemoryWrapperCache(new LruMemoryCache(paramMemorySizeProcessor, paramInt), paramFileNameGenerator);
    return new LruMemoryCache(paramMemorySizeProcessor, paramInt);
  }

  public static MemoryCache createMemoryCache(FileNameGenerator paramFileNameGenerator)
  {
    if (paramFileNameGenerator != null)
      return new MemoryWrapperCache(new LruMemoryCache(), paramFileNameGenerator);
    return new LruMemoryCache();
  }

  private static File createReserveDiskCacheDir(Context paramContext)
  {
    Object localObject = StorageUtils.getCacheDirectory(paramContext, false);
    File localFile = new File((File)localObject, "uil-images");
    if ((localFile.exists()) || (localFile.mkdir()))
      localObject = localFile;
    return localObject;
  }

  private static boolean hasHoneycomb()
  {
    return Build.VERSION.SDK_INT >= 11;
  }

  private static class DefaultThreadFactory
    implements ThreadFactory
  {
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final String namePrefix;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final int threadPriority;

    DefaultThreadFactory(int paramInt, String paramString)
    {
      this.threadPriority = paramInt;
      this.group = Thread.currentThread().getThreadGroup();
      this.namePrefix = (paramString + poolNumber.getAndIncrement() + "-thread-");
    }

    public Thread newThread(Runnable paramRunnable)
    {
      Thread localThread = new Thread(this.group, paramRunnable, this.namePrefix + this.threadNumber.getAndIncrement(), 0L);
      if (localThread.isDaemon())
        localThread.setDaemon(false);
      localThread.setPriority(this.threadPriority);
      return localThread;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.cache.core.DefaultConfigurationFactory
 * JD-Core Version:    0.6.2
 */