package com.starcor.cache.core;

import android.content.Context;
import com.starcor.cache.disk.DiskCache;
import com.starcor.cache.memory.MemoryCache;
import com.starcor.cache.memory.MemorySizeProcessor;
import com.starcor.cache.naming.FileNameGenerator;
import com.starcor.utils.Logger;

public class CacheConfig
{
  Context context;
  final DiskCache diskCache;
  final MemoryCache memoryCache;

  private CacheConfig(Builder paramBuilder)
  {
    this.diskCache = paramBuilder.diskCache;
    this.memoryCache = paramBuilder.memoryCache;
    this.context = paramBuilder.context;
  }

  public static CacheConfig createDefault(Context paramContext)
  {
    return new Builder(paramContext).build();
  }

  public static class Builder
  {
    private static final String WARNING_OVERLAP_DISK_CACHE_NAME_GENERATOR = "diskCache() and diskCacheFileNameGenerator() calls overlap each other";
    private static final String WARNING_OVERLAP_DISK_CACHE_PARAMS = "diskCache(), diskCacheSize() and diskCacheFileCount calls overlap each other";
    private static final String WARNING_OVERLAP_EXECUTOR = "threadPoolSize(), threadPriority() and tasksProcessingOrder() calls can overlap taskExecutor() and taskExecutorForCachedImages() calls.";
    private static final String WARNING_OVERLAP_MEMORY_CACHE = "memoryCache() and memoryCacheSize() calls overlap each other";
    private Context context;
    private DiskCache diskCache = null;
    private int diskCacheFileCount = 0;
    private FileNameGenerator diskCacheFileNameGenerator = null;
    private int diskCacheSize = 0;
    private FileNameGenerator memCacheNameGenerator = null;
    private MemoryCache memoryCache = null;
    private int memoryCacheSize = 0;
    private MemorySizeProcessor memorySizeProcessor;

    public Builder(Context paramContext)
    {
      this.context = paramContext;
    }

    private void initEmptyFieldsWithDefaultValues()
    {
      if (this.diskCache == null)
      {
        if (this.diskCacheFileNameGenerator == null)
          this.diskCacheFileNameGenerator = DefaultConfigurationFactory.createFileNameGenerator();
        this.diskCache = DefaultConfigurationFactory.createDiskCache(this.context, this.diskCacheFileNameGenerator, this.diskCacheSize, this.diskCacheFileCount);
      }
      this.diskCache.initialize();
      if (this.memoryCache == null)
      {
        if (this.memCacheNameGenerator == null)
          this.memCacheNameGenerator = DefaultConfigurationFactory.createFileNameGenerator();
        if (this.memorySizeProcessor != null)
          this.memoryCache = DefaultConfigurationFactory.createMemoryCache(this.context, this.memCacheNameGenerator, this.memorySizeProcessor, this.memoryCacheSize);
      }
      else
      {
        return;
      }
      this.memoryCache = DefaultConfigurationFactory.createMemoryCache(this.memCacheNameGenerator);
    }

    public CacheConfig build()
    {
      initEmptyFieldsWithDefaultValues();
      return new CacheConfig(this, null);
    }

    @Deprecated
    public Builder discCache(DiskCache paramDiskCache)
    {
      return diskCache(paramDiskCache);
    }

    @Deprecated
    public Builder discCacheFileCount(int paramInt)
    {
      return diskCacheFileCount(paramInt);
    }

    @Deprecated
    public Builder discCacheFileNameGenerator(FileNameGenerator paramFileNameGenerator)
    {
      return diskCacheFileNameGenerator(paramFileNameGenerator);
    }

    @Deprecated
    public Builder discCacheSize(int paramInt)
    {
      return diskCacheSize(paramInt);
    }

    public Builder diskCache(DiskCache paramDiskCache)
    {
      if ((this.diskCacheSize > 0) || (this.diskCacheFileCount > 0))
        Logger.d("error", "diskCache(), diskCacheSize() and diskCacheFileCount calls overlap each other");
      if (this.diskCacheFileNameGenerator != null)
        Logger.d("error", "diskCache() and diskCacheFileNameGenerator() calls overlap each other");
      this.diskCache = paramDiskCache;
      return this;
    }

    public Builder diskCacheFileCount(int paramInt)
    {
      if (paramInt <= 0)
        throw new IllegalArgumentException("maxFileCount must be a positive number");
      if (this.diskCache != null)
        Logger.d("error", "diskCache(), diskCacheSize() and diskCacheFileCount calls overlap each other");
      this.diskCacheFileCount = paramInt;
      return this;
    }

    public Builder diskCacheFileNameGenerator(FileNameGenerator paramFileNameGenerator)
    {
      if (this.diskCache != null)
        Logger.d("error", "diskCache() and diskCacheFileNameGenerator() calls overlap each other");
      this.diskCacheFileNameGenerator = paramFileNameGenerator;
      return this;
    }

    public Builder diskCacheSize(int paramInt)
    {
      if (paramInt <= 0)
        throw new IllegalArgumentException("maxCacheSize must be a positive number");
      if (this.diskCache != null)
        Logger.d("error", "diskCache(), diskCacheSize() and diskCacheFileCount calls overlap each other");
      this.diskCacheSize = paramInt;
      return this;
    }

    public Builder memoryCaCheSizeProcessor(MemorySizeProcessor paramMemorySizeProcessor)
    {
      this.memorySizeProcessor = paramMemorySizeProcessor;
      return this;
    }

    public Builder memoryCache(MemoryCache paramMemoryCache)
    {
      if (this.memoryCacheSize != 0)
        Logger.d("error", "memoryCache() and memoryCacheSize() calls overlap each other");
      this.memoryCache = paramMemoryCache;
      return this;
    }

    public Builder memoryCacheNameGenerator(FileNameGenerator paramFileNameGenerator)
    {
      if (this.diskCache != null)
        Logger.d("error", "diskCache() and diskCacheFileNameGenerator() calls overlap each other");
      this.memCacheNameGenerator = paramFileNameGenerator;
      return this;
    }

    public Builder memoryCacheSize(int paramInt)
    {
      if (paramInt <= 0)
        throw new IllegalArgumentException("memoryCacheSize must be a positive number");
      if (this.memoryCache != null)
        Logger.d("error", "memoryCache() and memoryCacheSize() calls overlap each other");
      this.memoryCacheSize = paramInt;
      return this;
    }

    public Builder memoryCacheSizePercentage(int paramInt)
    {
      if ((paramInt <= 0) || (paramInt >= 100))
        throw new IllegalArgumentException("availableMemoryPercent must be in range (0 < % < 100)");
      if (this.memoryCache != null)
        Logger.d("error", "memoryCache() and memoryCacheSize() calls overlap each other");
      this.memoryCacheSize = ((int)((float)Runtime.getRuntime().maxMemory() * (paramInt / 100.0F)));
      return this;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.cache.core.CacheConfig
 * JD-Core Version:    0.6.2
 */