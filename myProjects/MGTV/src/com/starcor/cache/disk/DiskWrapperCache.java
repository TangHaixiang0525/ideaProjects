package com.starcor.cache.disk;

import com.starcor.cache.naming.FileNameGenerator;

public class DiskWrapperCache
  implements DiskCache
{
  private final DiskCache cache;
  private final FileNameGenerator generator;

  public DiskWrapperCache(DiskCache paramDiskCache, FileNameGenerator paramFileNameGenerator)
  {
    this.cache = paramDiskCache;
    this.generator = paramFileNameGenerator;
  }

  public void clear()
  {
    this.cache.clear();
  }

  public DiskCache.Entry get(String paramString)
  {
    return this.cache.get(this.generator.generate(paramString));
  }

  public void initialize()
  {
    this.cache.initialize();
  }

  public boolean put(String paramString, DiskCache.Entry paramEntry)
  {
    return this.cache.put(this.generator.generate(paramString), paramEntry);
  }

  public boolean remove(String paramString)
  {
    return this.cache.remove(this.generator.generate(paramString));
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.cache.disk.DiskWrapperCache
 * JD-Core Version:    0.6.2
 */