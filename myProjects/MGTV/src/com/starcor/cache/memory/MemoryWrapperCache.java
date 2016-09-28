package com.starcor.cache.memory;

import com.starcor.cache.naming.FileNameGenerator;
import java.util.Collection;

public class MemoryWrapperCache
  implements MemoryCache
{
  private final MemoryCache cache;
  private final FileNameGenerator generator;

  public MemoryWrapperCache(MemoryCache paramMemoryCache, FileNameGenerator paramFileNameGenerator)
  {
    this.cache = paramMemoryCache;
    this.generator = paramFileNameGenerator;
  }

  public void clear()
  {
    this.cache.clear();
  }

  public Object get(String paramString)
  {
    return this.cache.get(this.generator.generate(paramString));
  }

  public Collection<String> keys()
  {
    return this.cache.keys();
  }

  public boolean put(String paramString, Object paramObject)
  {
    return this.cache.put(this.generator.generate(paramString), paramObject);
  }

  public Object remove(String paramString)
  {
    return this.cache.remove(this.generator.generate(paramString));
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.cache.memory.MemoryWrapperCache
 * JD-Core Version:    0.6.2
 */