package com.starcor.hunan.service.apidetect.http;

import android.os.Handler;
import android.os.Message;
import com.starcor.core.utils.Logger;
import com.starcor.httpapi.core.SCResponse;
import com.starcor.server.api.manage.ServerApiTask;
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

public class ApiDetectSCHttpApiEngine
{
  private static final int API_TASK_NUM = 1;
  public static final String TAG = "DetectSCHttpApiEngine";
  private Thread[] ApiPool = new Thread[1];
  private List<ServerApiTask> apiTaskQueue = new ArrayList();
  private boolean exitFlag = false;
  private ApiDetectSCHttpApiEngineStat stat = new ApiDetectSCHttpApiEngineStat();
  private int taskCurrentId = 0;
  private Handler uiHander = null;

  private int buildNewTaskId()
  {
    int i = 1 + this.taskCurrentId;
    this.taskCurrentId = i;
    return i;
  }

  private ServerApiTask getWaitTask()
  {
    synchronized (this.apiTaskQueue)
    {
      if (this.apiTaskQueue.size() > 0)
      {
        ServerApiTask localServerApiTask = (ServerApiTask)this.apiTaskQueue.remove(0);
        return localServerApiTask;
      }
      return null;
    }
  }

  public int addTask(ServerApiTask paramServerApiTask)
  {
    if (paramServerApiTask != null)
      synchronized (this.apiTaskQueue)
      {
        paramServerApiTask.setTaskId(buildNewTaskId());
        paramServerApiTask.markInQueueTime();
        this.apiTaskQueue.add(paramServerApiTask);
        Logger.i("DetectSCHttpApiEngine", "addTask taskId:" + paramServerApiTask.getTaskId() + ", name:" + paramServerApiTask.getTaskName() + ", info:" + paramServerApiTask.toString());
        int i = paramServerApiTask.getTaskId();
        return i;
      }
    return -1;
  }

  public void cancelTask(int paramInt)
  {
    Logger.i("DetectSCHttpApiEngine", "cancelTask taskId:" + paramInt);
    List localList = this.apiTaskQueue;
    for (int i = 0; ; i++)
      try
      {
        if (i < this.apiTaskQueue.size())
        {
          if (((ServerApiTask)this.apiTaskQueue.get(i)).getTaskId() == paramInt)
            this.apiTaskQueue.remove(i);
        }
        else
          return;
      }
      finally
      {
      }
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
          ServerApiTask localServerApiTask;
          do
          {
            return;
            localServerApiTask = (ServerApiTask)paramAnonymousMessage.obj;
          }
          while (localServerApiTask == null);
          localServerApiTask.perform(localServerApiTask.getSCResponse());
        }
      };
    for (int i = 0; i < this.ApiPool.length; i++)
    {
      this.ApiPool[i] = new Thread(new ApiRunnable());
      this.ApiPool[i].setName("ApiThread:" + i);
      this.ApiPool[i].start();
    }
  }

  public void unInit()
  {
    Logger.i("DetectSCHttpApiEngine", "unInit");
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
          Logger.i("DetectSCHttpApiEngine", "cache timeout!!! " + localCacheEntry.life + ", " + localCacheEntry.data.length + ", " + localCacheEntry.key);
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
      label135: Logger.i("DetectSCHttpApiEngine", "cache hit!!! " + localCacheEntry.life + ", " + localCacheEntry.data.length + ", " + localCacheEntry.key);
      return localCacheEntry.decodeData();
    }

    private static long timestamp()
    {
      return System.nanoTime() / 1000000L;
    }

    public static void updateCache(String paramString, byte[] paramArrayOfByte, long paramLong)
    {
      if ((paramArrayOfByte == null) || (paramString == null) || (paramLong <= 0L))
      {
        Logger.i("DetectSCHttpApiEngine", "update cache failed!!!");
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
            Logger.i("DetectSCHttpApiEngine", "new cache " + localCacheEntry2.life + ", " + localCacheEntry2.data.length + ", " + localCacheEntry2.key);
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
              ApiDetectSCHttpApiEngine.ApiCache.CacheEntry localCacheEntry1 = (ApiDetectSCHttpApiEngine.ApiCache.CacheEntry)paramAnonymousObject1;
              ApiDetectSCHttpApiEngine.ApiCache.CacheEntry localCacheEntry2 = (ApiDetectSCHttpApiEngine.ApiCache.CacheEntry)paramAnonymousObject2;
              if (localCacheEntry1.life < localCacheEntry2.life)
                return -1;
              if (localCacheEntry1.life > localCacheEntry2.life)
                return 1;
              return 0;
            }
          });
          int i = arrayOfObject.length;
          int j = 0;
          label203: if (j < i)
          {
            CacheEntry localCacheEntry3 = (CacheEntry)arrayOfObject[j];
            if (currentCacheSize >= cacheSizeLimit)
            {
              Logger.i("DetectSCHttpApiEngine", "cache deleted!!! " + localCacheEntry3.life + ", " + localCacheEntry3.data.length + ", " + localCacheEntry3.key);
              currentCacheSize -= localCacheEntry3.data.length;
              cacheMap.remove(localCacheEntry3.key);
            }
            while (localCacheEntry3.life >= l2)
            {
              j++;
              break label203;
              localCacheEntry1.life = l1;
              currentCacheSize += localCacheEntry1.compressData(paramArrayOfByte);
              Logger.i("DetectSCHttpApiEngine", "update cache " + localCacheEntry1.life + ", " + localCacheEntry1.data.length + ", " + localCacheEntry1.key);
              break;
              localObject1 = finally;
              throw localObject1;
            }
            Logger.i("DetectSCHttpApiEngine", "cache timeout(2)!!! " + localCacheEntry3.life + ", " + localCacheEntry3.data.length + ", " + localCacheEntry3.key);
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
      Logger.i("DetectSCHttpApiEngine", "ApiRunnable thread:" + Thread.currentThread().getName());
      while (!ApiDetectSCHttpApiEngine.this.exitFlag)
      {
        ServerApiTask localServerApiTask = ApiDetectSCHttpApiEngine.this.getWaitTask();
        if (localServerApiTask != null)
          runTask(localServerApiTask);
        else
          try
          {
            Thread.sleep(10L);
          }
          catch (InterruptedException localInterruptedException)
          {
            localInterruptedException.printStackTrace();
          }
      }
    }

    public void runTask(ServerApiTask paramServerApiTask)
    {
      Logger.i("DetectSCHttpApiEngine", "runTask taskId:" + paramServerApiTask.getTaskId() + ", Name:" + paramServerApiTask.getTaskName() + " , url:" + paramServerApiTask.getUrl());
      SCResponse localSCResponse = ApiDetectSCHttpTools.doRequest(paramServerApiTask, 5000, 10000);
      if (localSCResponse == null)
        return;
      paramServerApiTask.setSCResponse(localSCResponse);
      paramServerApiTask.perform(paramServerApiTask.getSCResponse());
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.service.apidetect.http.ApiDetectSCHttpApiEngine
 * JD-Core Version:    0.6.2
 */