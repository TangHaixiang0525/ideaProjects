package com.starcor.cache.memory;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LimitedAgeMemoryCache
  implements MemoryCache
{
  private final MemoryCache cache;
  private final Map<String, Long> loadingDates = Collections.synchronizedMap(new HashMap());
  private final long maxAge;

  public LimitedAgeMemoryCache(MemoryCache paramMemoryCache, long paramLong)
  {
    this.cache = paramMemoryCache;
    this.maxAge = (1000L * paramLong);
  }

  public void clear()
  {
    this.cache.clear();
    this.loadingDates.clear();
  }

  public Object get(String paramString)
  {
    Long localLong = (Long)this.loadingDates.get(paramString);
    if ((localLong != null) && (System.currentTimeMillis() - localLong.longValue() > this.maxAge))
    {
      this.cache.remove(paramString);
      this.loadingDates.remove(paramString);
    }
    return this.cache.get(paramString);
  }

  public Collection<String> keys()
  {
    return this.cache.keys();
  }

  public boolean put(String paramString, Object paramObject)
  {
    boolean bool = this.cache.put(paramString, paramObject);
    if (bool)
      this.loadingDates.put(paramString, Long.valueOf(System.currentTimeMillis()));
    return bool;
  }

  public Object remove(String paramString)
  {
    this.loadingDates.remove(paramString);
    return this.cache.remove(paramString);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.cache.memory.LimitedAgeMemoryCache
 * JD-Core Version:    0.6.2
 */