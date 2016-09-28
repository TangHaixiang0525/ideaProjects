package com.starcor.core.service;

import android.os.SystemClock;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ApiCacheObject
{
  private long cacheCreateTime = 0L;
  private long cacheExpireTime = 60000L;
  private Object cacheObject;

  ApiCacheObject(Object paramObject, long paramLong)
  {
    this.cacheExpireTime = (paramLong + this.cacheCreateTime);
    try
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(localByteArrayOutputStream);
      localObjectOutputStream.writeObject(paramObject);
      ObjectInputStream localObjectInputStream = new ObjectInputStream(new ByteArrayInputStream(localByteArrayOutputStream.toByteArray()));
      this.cacheObject = localObjectInputStream.readObject();
      localObjectOutputStream.close();
      localObjectInputStream.close();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void clearCacheObject()
  {
    this.cacheObject = null;
  }

  public Object getCacheObject()
  {
    try
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      ObjectOutputStream localObjectOutputStream = new ObjectOutputStream(localByteArrayOutputStream);
      localObjectOutputStream.writeObject(this.cacheObject);
      ObjectInputStream localObjectInputStream = new ObjectInputStream(new ByteArrayInputStream(localByteArrayOutputStream.toByteArray()));
      Object localObject = localObjectInputStream.readObject();
      localObjectOutputStream.close();
      localObjectInputStream.close();
      return localObject;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public boolean isCacheValid()
  {
    if (this.cacheObject == null);
    long l;
    do
    {
      return false;
      l = SystemClock.elapsedRealtime();
    }
    while ((this.cacheExpireTime < l) || (l < this.cacheCreateTime));
    return true;
  }

  public boolean isCacheValid(long paramLong)
  {
    if (this.cacheObject == null);
    long l;
    do
    {
      return false;
      l = SystemClock.elapsedRealtime();
    }
    while ((paramLong + this.cacheCreateTime < l) || (l < this.cacheCreateTime) || (this.cacheExpireTime < l) || (l < this.cacheCreateTime));
    return true;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.service.ApiCacheObject
 * JD-Core Version:    0.6.2
 */