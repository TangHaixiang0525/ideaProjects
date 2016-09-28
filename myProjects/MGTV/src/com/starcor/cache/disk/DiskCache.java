package com.starcor.cache.disk;

import java.io.InputStream;

public abstract interface DiskCache
{
  public abstract void clear();

  public abstract Entry get(String paramString);

  public abstract void initialize();

  public abstract boolean put(String paramString, Entry paramEntry);

  public abstract boolean remove(String paramString);

  public static class Entry
  {
    public byte[] data;
    public InputStream inputStream;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.cache.disk.DiskCache
 * JD-Core Version:    0.6.2
 */