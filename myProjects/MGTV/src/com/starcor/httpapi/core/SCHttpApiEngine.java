package com.starcor.httpapi.core;

import android.os.Handler;
import android.os.Message;
import com.starcor.config.AppFuncCfg;
import com.starcor.core.utils.Logger;
import com.starcor.server.api.manage.ServerApiCachedTask;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class SCHttpApiEngine
{
  public static final String TAG = "SCHttpApiEngine";
  private Thread[] ApiPool = null;
  private List<SCHttpApiTask> apiTaskQueue = new ArrayList();
  private boolean exitFlag = false;
  private SCHttpApiEngineStat stat = new SCHttpApiEngineStat();
  private int taskCurrentId = 0;
  private Object taskWaitObject = new Object();
  private Handler uiHander = null;

  private int buildNewTaskId()
  {
    int i = 1 + this.taskCurrentId;
    this.taskCurrentId = i;
    return i;
  }

  private SCHttpApiTask findTask(int paramInt)
  {
    List localList = this.apiTaskQueue;
    for (int i = 0; ; i++)
      try
      {
        if (i < this.apiTaskQueue.size())
        {
          if (((SCHttpApiTask)this.apiTaskQueue.get(i)).getTaskId() == paramInt)
          {
            SCHttpApiTask localSCHttpApiTask = (SCHttpApiTask)this.apiTaskQueue.get(i);
            return localSCHttpApiTask;
          }
        }
        else
          return null;
      }
      finally
      {
      }
  }

  private SCHttpApiTask getWaitTask()
  {
    while (true)
    {
      int m;
      synchronized (this.apiTaskQueue)
      {
        int i = this.apiTaskQueue.size();
        int j = -1;
        int k = -1;
        m = 0;
        if (m < i)
        {
          if (((SCHttpApiTask)this.apiTaskQueue.get(m)).getTaskPrioroty() > k)
          {
            j = m;
            k = ((SCHttpApiTask)this.apiTaskQueue.get(m)).getTaskPrioroty();
          }
        }
        else
        {
          if (j >= 0)
          {
            SCHttpApiTask localSCHttpApiTask = (SCHttpApiTask)this.apiTaskQueue.remove(j);
            return localSCHttpApiTask;
          }
          return null;
        }
      }
      m++;
    }
  }

  public int addTask(SCHttpApiTask paramSCHttpApiTask)
  {
    if (paramSCHttpApiTask != null)
    {
      if ((AppFuncCfg.FUNCTION_USE_CACHED_DATA) && ((paramSCHttpApiTask instanceof ServerApiCachedTask)) && (ServerApiCachedTask.userLocalDataFlag))
      {
        byte[] arrayOfByte = ((ServerApiCachedTask)paramSCHttpApiTask).loadLocalApiData();
        if (arrayOfByte != null)
        {
          SCResponse localSCResponse = new SCResponse();
          localSCResponse.setHttpCode(200);
          localSCResponse.setContents(arrayOfByte);
          localSCResponse.setRunState(1);
          localSCResponse.setHttpReason("cached");
          localSCResponse.setTaskName(paramSCHttpApiTask.getTaskName());
          paramSCHttpApiTask.perform(localSCResponse);
          return -1;
        }
        ServerApiCachedTask.userLocalDataFlag = false;
        Logger.i("SCHttpApiEngine", "本地数据异常");
        return -2;
      }
      int i;
      synchronized (this.apiTaskQueue)
      {
        paramSCHttpApiTask.setTaskId(buildNewTaskId());
        paramSCHttpApiTask.markInQueueTime();
        this.apiTaskQueue.add(paramSCHttpApiTask);
        synchronized (this.taskWaitObject)
        {
          i = paramSCHttpApiTask.getTaskId();
        }
      }
      localObject3 = finally;
      throw localObject3;
    }
    return -1;
  }

  public void cancelTask(SCHttpApiTask paramSCHttpApiTask)
  {
    paramSCHttpApiTask.setCanceled(true);
  }

  public SCHttpApiTask getTask(int paramInt)
  {
    return findTask(paramInt);
  }

  public byte[] getTaskDataCache(SCHttpApiTask paramSCHttpApiTask)
  {
    if (paramSCHttpApiTask == null);
    while (paramSCHttpApiTask.getCacheLife() < 1L)
      return null;
    return ApiCache.getCachedData(paramSCHttpApiTask.getRealUrl());
  }

  public void init(boolean paramBoolean)
  {
    if (paramBoolean)
      this.uiHander = new Handler()
      {
        public void handleMessage(Message paramAnonymousMessage)
        {
          switch (paramAnonymousMessage.what)
          {
          default:
          case 9977:
          }
          SCHttpApiTask localSCHttpApiTask;
          do
          {
            return;
            localSCHttpApiTask = (SCHttpApiTask)paramAnonymousMessage.obj;
          }
          while (localSCHttpApiTask == null);
          localSCHttpApiTask.perform(localSCHttpApiTask.getSCResponse());
        }
      };
    if (Runtime.getRuntime().availableProcessors() <= 2);
    for (int i = 3; ; i = 6)
    {
      this.ApiPool = new Thread[i];
      for (int j = 0; j < this.ApiPool.length; j++)
      {
        this.ApiPool[j] = new Thread(new ApiRunnable());
        this.ApiPool[j].setName("ApiThread:" + j);
        this.ApiPool[j].start();
      }
    }
  }

  public boolean isTaskDataCacheValid(SCHttpApiTask paramSCHttpApiTask)
  {
    if (paramSCHttpApiTask == null);
    while (paramSCHttpApiTask.getCacheLife() < 1L)
      return false;
    return ApiCache.isCachedDataValid(paramSCHttpApiTask.getRealUrl());
  }

  public void setTaskJustCache(int paramInt, boolean paramBoolean)
  {
    SCHttpApiTask localSCHttpApiTask = findTask(paramInt);
    if (localSCHttpApiTask != null)
      localSCHttpApiTask.setJustCache(paramBoolean);
  }

  public void setTaskPriority(int paramInt1, int paramInt2)
  {
    SCHttpApiTask localSCHttpApiTask = findTask(paramInt1);
    if (localSCHttpApiTask != null)
      localSCHttpApiTask.setTaskPriority(paramInt2);
  }

  public void unInit()
  {
    Logger.i("SCHttpApiEngine", "unInit");
    this.exitFlag = true;
  }

  private static class ApiCache
  {
    private static HashMap<String, CacheEntry> cacheMap = new HashMap();
    private static int cacheSizeLimit = 1048576;
    private static int currentCacheSize = 0;

    public static byte[] getCachedData(String paramString)
    {
      CacheEntry localCacheEntry;
      synchronized (cacheMap)
      {
        localCacheEntry = (CacheEntry)cacheMap.get(paramString);
        if (localCacheEntry != null)
        {
          if (localCacheEntry.life >= timestamp())
            break label135;
          Logger.i("SCHttpApiEngine", "cache timeout!!! " + localCacheEntry.life + ", " + localCacheEntry.data.length + ", " + localCacheEntry.key);
        }
      }
      synchronized (cacheMap)
      {
        currentCacheSize -= localCacheEntry.data.length;
        cacheMap.remove(localCacheEntry.key);
        return null;
        localObject1 = finally;
        throw localObject1;
      }
      label135: return localCacheEntry.decodeData();
    }

    public static boolean isCachedDataValid(String paramString)
    {
      CacheEntry localCacheEntry;
      synchronized (cacheMap)
      {
        localCacheEntry = (CacheEntry)cacheMap.get(paramString);
        if (localCacheEntry != null)
        {
          if (localCacheEntry.life >= timestamp())
            break label135;
          Logger.i("SCHttpApiEngine", "cache timeout!!! " + localCacheEntry.life + ", " + localCacheEntry.data.length + ", " + localCacheEntry.key);
        }
      }
      synchronized (cacheMap)
      {
        currentCacheSize -= localCacheEntry.data.length;
        cacheMap.remove(localCacheEntry.key);
        return false;
        localObject1 = finally;
        throw localObject1;
      }
      label135: return true;
    }

    private static long timestamp()
    {
      return System.nanoTime() / 1000000L;
    }

    public static void updateCache(String paramString, byte[] paramArrayOfByte, long paramLong)
    {
      if ((paramArrayOfByte == null) || (paramString == null) || (paramLong <= 0L))
      {
        Logger.i("SCHttpApiEngine", "update cache failed!!!");
        return;
      }
      long l1 = paramLong + timestamp();
      while (true)
      {
        CacheEntry localCacheEntry1;
        synchronized (cacheMap)
        {
          localCacheEntry1 = (CacheEntry)cacheMap.get(paramString);
          if (localCacheEntry1 == null)
          {
            CacheEntry localCacheEntry2 = new CacheEntry(paramString, l1, paramArrayOfByte);
            cacheMap.put(paramString, localCacheEntry2);
            currentCacheSize += localCacheEntry2.data.length;
            Logger.i("SCHttpApiEngine", "new cache " + localCacheEntry2.life + ", " + paramArrayOfByte.length + ", " + localCacheEntry2.key);
            if (currentCacheSize < cacheSizeLimit)
              break;
          }
        }
        synchronized (cacheMap)
        {
          long l2 = timestamp();
          Object[] arrayOfObject = cacheMap.values().toArray();
          Arrays.sort(arrayOfObject, new Comparator()
          {
            public int compare(Object paramAnonymousObject1, Object paramAnonymousObject2)
            {
              SCHttpApiEngine.ApiCache.CacheEntry localCacheEntry1 = (SCHttpApiEngine.ApiCache.CacheEntry)paramAnonymousObject1;
              SCHttpApiEngine.ApiCache.CacheEntry localCacheEntry2 = (SCHttpApiEngine.ApiCache.CacheEntry)paramAnonymousObject2;
              if (localCacheEntry1.life < localCacheEntry2.life)
                return -1;
              if (localCacheEntry1.life > localCacheEntry2.life)
                return 1;
              return 0;
            }
          });
          int i = arrayOfObject.length;
          int j = 0;
          label199: if (j < i)
          {
            CacheEntry localCacheEntry3 = (CacheEntry)arrayOfObject[j];
            if (currentCacheSize >= cacheSizeLimit)
            {
              Logger.i("SCHttpApiEngine", "cache deleted!!! " + localCacheEntry3.life + ", " + localCacheEntry3.data.length + ", " + localCacheEntry3.key);
              currentCacheSize -= localCacheEntry3.data.length;
              cacheMap.remove(localCacheEntry3.key);
            }
            while (localCacheEntry3.life >= l2)
            {
              j++;
              break label199;
              localCacheEntry1.life = l1;
              currentCacheSize += localCacheEntry1.compressData(paramArrayOfByte);
              Logger.i("SCHttpApiEngine", "update cache " + localCacheEntry1.life + ", " + paramArrayOfByte.length + ", " + localCacheEntry1.key);
              break;
              localObject1 = finally;
              throw localObject1;
            }
            Logger.i("SCHttpApiEngine", "cache timeout(2)!!! " + localCacheEntry3.life + ", " + localCacheEntry3.data.length + ", " + localCacheEntry3.key);
            currentCacheSize -= localCacheEntry3.data.length;
            cacheMap.remove(localCacheEntry3.key);
          }
        }
      }
    }

    private static class CacheEntry
    {
      static HashMap<Long, Deflater> deflaters = new HashMap();
      static HashMap<Long, Inflater> inflaters = new HashMap();
      byte[] data;
      int dataLen;
      String key;
      long life;

      public CacheEntry(String paramString, long paramLong, byte[] paramArrayOfByte)
      {
        this.key = paramString;
        this.life = paramLong;
        compressData(paramArrayOfByte);
      }

      static Deflater getDeflater()
      {
        synchronized (deflaters)
        {
          Long localLong = Long.valueOf(Thread.currentThread().getId());
          Deflater localDeflater = (Deflater)deflaters.get(localLong);
          if (localDeflater == null)
          {
            localDeflater = new Deflater(1);
            deflaters.put(localLong, localDeflater);
            return localDeflater;
          }
          localDeflater.reset();
        }
      }

      static Inflater getInflater()
      {
        synchronized (inflaters)
        {
          Long localLong = Long.valueOf(Thread.currentThread().getId());
          Inflater localInflater = (Inflater)inflaters.get(localLong);
          if (localInflater == null)
          {
            localInflater = new Inflater();
            inflaters.put(localLong, localInflater);
            return localInflater;
          }
          localInflater.reset();
        }
      }

      public int compressData(byte[] paramArrayOfByte)
      {
        Deflater localDeflater;
        ByteArrayOutputStream localByteArrayOutputStream;
        try
        {
          this.dataLen = paramArrayOfByte.length;
          localDeflater = getDeflater();
          localDeflater.setInput(paramArrayOfByte);
          localDeflater.finish();
          localByteArrayOutputStream = new ByteArrayOutputStream();
          byte[] arrayOfByte1 = new byte[4096];
          while (!localDeflater.finished())
            localByteArrayOutputStream.write(arrayOfByte1, 0, localDeflater.deflate(arrayOfByte1));
        }
        finally
        {
        }
        localDeflater.reset();
        byte[] arrayOfByte2 = this.data;
        int i = 0;
        if (arrayOfByte2 != null)
          i = this.data.length;
        this.data = localByteArrayOutputStream.toByteArray();
        int j = this.data.length;
        int k = j - i;
        return k;
      }

      public byte[] decodeData()
      {
        try
        {
          Inflater localInflater = getInflater();
          localInflater.setInput(this.data, 0, this.data.length);
          byte[] arrayOfByte = new byte[this.dataLen];
          try
          {
            int i = localInflater.inflate(arrayOfByte);
            int j = this.dataLen;
            if (i != j)
            {
              arrayOfByte = null;
              return arrayOfByte;
            }
          }
          catch (DataFormatException localDataFormatException)
          {
            while (true)
            {
              localDataFormatException.printStackTrace();
              localInflater.reset();
            }
          }
        }
        finally
        {
        }
      }
    }
  }

  class ApiRunnable
    implements Runnable
  {
    ApiRunnable()
    {
    }

    public void run()
    {
      Logger.i("SCHttpApiEngine", "ApiRunnable thread:" + Thread.currentThread().getName());
      while (!SCHttpApiEngine.this.exitFlag)
      {
        SCHttpApiTask localSCHttpApiTask = SCHttpApiEngine.this.getWaitTask();
        if (localSCHttpApiTask != null)
          runTask(localSCHttpApiTask);
        else
          try
          {
            synchronized (SCHttpApiEngine.this.taskWaitObject)
            {
              SCHttpApiEngine.this.taskWaitObject.wait(10L);
            }
          }
          catch (InterruptedException localInterruptedException)
          {
            localInterruptedException.printStackTrace();
          }
      }
    }

    public void runTask(SCHttpApiTask paramSCHttpApiTask)
    {
      long l = System.currentTimeMillis();
      String str = paramSCHttpApiTask.getRealUrl();
      Logger.i("SCHttpApiEngine", "runTask taskId:" + paramSCHttpApiTask.getTaskId() + ", Name:" + paramSCHttpApiTask.getTaskName() + ",forceUpdate:" + paramSCHttpApiTask.isForceUpdate() + " , url:" + str);
      if (paramSCHttpApiTask.isCanceled())
      {
        Logger.w("SCHttpApiEngine", "Canceled Task taskId:" + paramSCHttpApiTask.getTaskId() + ", Name:" + paramSCHttpApiTask.getTaskName());
        return;
      }
      SCResponse localSCResponse1;
      if (paramSCHttpApiTask.getCacheLife() > 0L)
      {
        byte[] arrayOfByte = SCHttpApiEngine.ApiCache.getCachedData(str);
        if ((arrayOfByte != null) && (!paramSCHttpApiTask.isForceUpdate()))
        {
          SCResponse localSCResponse2 = new SCResponse();
          localSCResponse2.setHttpCode(200);
          localSCResponse2.setContents(arrayOfByte);
          localSCResponse2.setRunState(1);
          localSCResponse2.setHttpReason("cached");
          localSCResponse2.setTaskName(paramSCHttpApiTask.getTaskName());
          localSCResponse1 = localSCResponse2;
          Logger.i("SCHttpApiEngine", "runTask engine cache hit taskId:" + paramSCHttpApiTask.getTaskId() + " taskName:" + paramSCHttpApiTask.getTaskName() + " CacheLife:" + paramSCHttpApiTask.getCacheLife() / 1000L + "s");
          label257: if (localSCResponse1 == null)
            break label625;
          paramSCHttpApiTask.setSCResponse(localSCResponse1);
          if (!paramSCHttpApiTask.getIsUiSafe())
            break label690;
          if ((!paramSCHttpApiTask.getJustCache()) && (SCHttpApiEngine.this.uiHander != null))
          {
            Message localMessage = SCHttpApiEngine.this.uiHander.obtainMessage(9977, paramSCHttpApiTask);
            SCHttpApiEngine.this.uiHander.sendMessage(localMessage);
          }
        }
      }
      while (true)
      {
        Logger.i("SCHttpApiEngine", "runTask complete taskId:" + paramSCHttpApiTask.getTaskId() + ", taskName:" + paramSCHttpApiTask.getTaskName() + ", CacheLife:" + paramSCHttpApiTask.getCacheLife() / 1000L + "s" + ", coast time:" + (System.currentTimeMillis() - l) + "ms");
        return;
        if (paramSCHttpApiTask.getCacheLife() > 0L)
          Logger.w("SCHttpApiEngine", "runTask engine cache not hit taskId:" + paramSCHttpApiTask.getTaskId() + " taskName:" + paramSCHttpApiTask.getTaskName() + " CacheLife:" + paramSCHttpApiTask.getCacheLife() / 1000L + "s");
        while (true)
        {
          int i = 0;
          int j = paramSCHttpApiTask.getRetryTimes();
          int k = paramSCHttpApiTask.getConnectTimeOutMs();
          int m = paramSCHttpApiTask.getSoTimeOutMs();
          if ((j < 0) || (j > 5))
            j = 3;
          if ((k < 2000) || (k > 30000))
            k = 5000;
          if (m >= 2000)
          {
            i = 0;
            if (m <= 15000);
          }
          else
          {
            m = 10000;
          }
          do
          {
            i++;
            localSCResponse1 = SCHttpTools.doRequest(paramSCHttpApiTask, str, k, m);
          }
          while ((i <= j) && ((localSCResponse1 == null) || (localSCResponse1.getRunState() != 1)));
          if ((paramSCHttpApiTask.getCacheLife() <= 0L) || (localSCResponse1 == null) || (localSCResponse1.getHttpCode() != 200))
            break label257;
          SCHttpApiEngine.ApiCache.updateCache(str, localSCResponse1.getContents(), paramSCHttpApiTask.getCacheLife());
          break label257;
          label625: break;
          Logger.i("SCHttpApiEngine", "runTask engine cache not use taskId:" + paramSCHttpApiTask.getTaskId() + " taskName:" + paramSCHttpApiTask.getTaskName() + " CacheLife:" + paramSCHttpApiTask.getCacheLife() / 1000L + "s");
        }
        label690: if (!paramSCHttpApiTask.getJustCache())
          paramSCHttpApiTask.perform(paramSCHttpApiTask.getSCResponse());
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.httpapi.core.SCHttpApiEngine
 * JD-Core Version:    0.6.2
 */