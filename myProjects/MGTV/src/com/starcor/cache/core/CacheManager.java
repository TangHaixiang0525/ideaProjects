package com.starcor.cache.core;

import com.starcor.cache.disk.DiskCache;
import com.starcor.cache.disk.DiskCache.Entry;
import com.starcor.cache.memory.MemoryCache;
import com.starcor.utils.Logger;
import java.io.InputStream;

public class CacheManager
{
  private static final String ERROR_INIT_CONFIG_WITH_NULL = "ImageLoader configuration can not be initialized with null";
  private static final String ERROR_NOT_INIT = "ImageLoader must be init with configuration before using";
  private static final String ERROR_WRONG_ARGUMENTS = "Wrong arguments were passed to displayImage() method (ImageView reference must not be null)";
  static final String LOG_DESTROY = "Destroy ImageLoader";
  static final String LOG_INIT_CONFIG = "Initialize ImageLoader with configuration";
  static final String LOG_LOAD_IMAGE_FROM_MEMORY_CACHE = "Load image from memory cache [%s]";
  public static final String TAG = CacheManager.class.getSimpleName();
  private static final String WARNING_RE_INIT_CONFIG = "Try to initialize ImageLoader which had already been initialized before. To re-init ImageLoader with new configuration call ImageLoader.destroy() at first.";
  private static volatile CacheManager instance;
  private CacheConfig configuration;

  public static CacheManager getInstance()
  {
    if (instance == null);
    try
    {
      if (instance == null)
        instance = new CacheManager();
      return instance;
    }
    finally
    {
    }
  }

  public void clearDisk()
  {
    this.configuration.diskCache.clear();
  }

  public void clearMemory()
  {
    this.configuration.memoryCache.clear();
  }

  public byte[] getDisk(String paramString)
  {
    return this.configuration.diskCache.get(paramString).data;
  }

  public Object getMemory(String paramString)
  {
    return this.configuration.memoryCache.get(paramString);
  }

  public void init(CacheConfig paramCacheConfig)
  {
    if (paramCacheConfig == null)
      try
      {
        throw new IllegalArgumentException("ImageLoader configuration can not be initialized with null");
      }
      finally
      {
      }
    if (this.configuration == null)
    {
      Logger.d(TAG, "Initialize ImageLoader with configuration");
      this.configuration = paramCacheConfig;
    }
    while (true)
    {
      return;
      Logger.d(TAG, "Try to initialize ImageLoader which had already been initialized before. To re-init ImageLoader with new configuration call ImageLoader.destroy() at first.");
    }
  }

  public boolean putDisk(String paramString, InputStream paramInputStream)
  {
    DiskCache.Entry localEntry = new DiskCache.Entry();
    localEntry.inputStream = paramInputStream;
    return this.configuration.diskCache.put(paramString, localEntry);
  }

  public boolean putDisk(String paramString, byte[] paramArrayOfByte)
  {
    DiskCache.Entry localEntry = new DiskCache.Entry();
    localEntry.data = paramArrayOfByte;
    return this.configuration.diskCache.put(paramString, localEntry);
  }

  public boolean putMemory(String paramString, Object paramObject)
  {
    return this.configuration.memoryCache.put(paramString, paramObject);
  }

  public boolean removeDisk(String paramString)
  {
    return this.configuration.diskCache.remove(paramString);
  }

  public Object removeMemory(String paramString)
  {
    return this.configuration.memoryCache.remove(paramString);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.cache.core.CacheManager
 * JD-Core Version:    0.6.2
 */