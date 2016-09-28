package com.starcor.cache.memory;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class LruMemoryCache
  implements MemoryCache
{
  private MemorySizeProcessor mSizeProcessor;
  private final LinkedHashMap<String, Object> map;
  private int maxSize;
  private int size;

  public LruMemoryCache()
  {
    this.map = new LinkedHashMap(0, 0.75F, true);
  }

  public LruMemoryCache(MemorySizeProcessor paramMemorySizeProcessor, int paramInt)
  {
    if (paramInt <= 0)
      throw new IllegalArgumentException("maxSize <= 0");
    this.maxSize = paramInt;
    this.mSizeProcessor = paramMemorySizeProcessor;
    this.map = new LinkedHashMap(0, 0.75F, true);
  }

  private void processSize()
  {
    if (this.size == 0);
    while ((this.size <= this.maxSize) || (this.mSizeProcessor == null))
      return;
    this.mSizeProcessor.sizeOutOfLimit(this.maxSize - this.size);
  }

  private int sizeOf(String paramString, Object paramObject)
  {
    if (this.mSizeProcessor != null)
      return this.mSizeProcessor.sizeof(paramString, paramObject);
    return 0;
  }

  public void clear()
  {
    this.map.clear();
    this.size = 0;
    processSize();
  }

  public final Object get(String paramString)
  {
    if (paramString == null)
      throw new NullPointerException("key == null");
    try
    {
      Object localObject2 = this.map.get(paramString);
      return localObject2;
    }
    finally
    {
    }
  }

  public Collection<String> keys()
  {
    try
    {
      HashSet localHashSet = new HashSet(this.map.keySet());
      return localHashSet;
    }
    finally
    {
    }
  }

  public final boolean put(String paramString, Object paramObject)
  {
    if ((paramString == null) || (paramObject == null))
      throw new NullPointerException("key == null || value == null");
    try
    {
      this.size += sizeOf(paramString, paramObject);
      Object localObject2 = this.map.put(paramString, paramObject);
      if (localObject2 != null)
        this.size -= sizeOf(paramString, localObject2);
      processSize();
      return true;
    }
    finally
    {
    }
  }

  public final Object remove(String paramString)
  {
    if (paramString == null)
      throw new NullPointerException("key == null");
    try
    {
      Object localObject2 = this.map.remove(paramString);
      if (localObject2 != null)
        this.size -= sizeOf(paramString, localObject2);
      return localObject2;
    }
    finally
    {
    }
  }

  public final String toString()
  {
    try
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(this.maxSize);
      String str = String.format("LruCache[maxSize=%d]", arrayOfObject);
      return str;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.cache.memory.LruMemoryCache
 * JD-Core Version:    0.6.2
 */