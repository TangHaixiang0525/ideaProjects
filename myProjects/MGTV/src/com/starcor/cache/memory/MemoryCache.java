package com.starcor.cache.memory;

import java.util.Collection;

public abstract interface MemoryCache
{
  public abstract void clear();

  public abstract Object get(String paramString);

  public abstract Collection<String> keys();

  public abstract boolean put(String paramString, Object paramObject);

  public abstract Object remove(String paramString);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.cache.memory.MemoryCache
 * JD-Core Version:    0.6.2
 */