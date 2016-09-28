package com.starcor.cache.memory;

public abstract interface MemorySizeProcessor
{
  public abstract void sizeOutOfLimit(int paramInt);

  public abstract int sizeof(String paramString, Object paramObject);
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.cache.memory.MemorySizeProcessor
 * JD-Core Version:    0.6.2
 */