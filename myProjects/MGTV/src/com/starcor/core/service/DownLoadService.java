package com.starcor.core.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import com.ei.libs.bitmap.EIBitmapUtil;
import com.starcor.OTTTV;
import com.starcor.config.AppFuncCfg;
import com.starcor.core.domain.ApiTask;
import com.starcor.core.domain.ImageTask;
import com.starcor.core.epgapi.Api;
import com.starcor.core.http.HttpWrapper;
import com.starcor.core.image.BitmapTools;
import com.starcor.core.interfaces.IXmlParser;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.utils.GeneralUtils;
import com.starcor.core.utils.IOTools;
import com.starcor.core.utils.Logger;
import com.starcor.mgtv.boss.webUrlFormatter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

public class DownLoadService extends Service
{
  private static final int API_TASK_NUM = 4;
  private static final long CACHE_EXPIRATION_TIME = 300000L;
  private static final int HIGH_IMAGE_TASK_NUM = 2;
  private static final int LOW_IMAGE_TASK_NUM = 1;
  private static final String TAG = "DownLoadService";
  public static final int TASK_SLEEP_TIME = 100;
  private Thread[] ApiPool = new Thread[4];
  private Thread[] HighImagePool = new Thread[2];
  private Thread[] LowImagePool = new Thread[1];
  private int apiTaskCount = 0;
  private List<ApiTask> apiTaskQueue = new ArrayList();
  private final IBinder binder = new MyBinder();
  private long checkClearCacheTime = 0L;
  private boolean exitFlag;
  private List<ImageTask> highImageQueue = new ArrayList();
  private HttpWrapper http;
  private int imageTaskCount = 0;
  private List<ImageTask> imgTaskQueue = new ArrayList();
  private boolean isUserMemoryCache = true;
  private HashMap<String, ApiCacheObject> localCacheManager = new HashMap();
  private int taskId;

  private static String getClassName()
  {
    String str = Thread.currentThread().getStackTrace()[4].getClassName();
    return str.substring(1 + str.lastIndexOf("."));
  }

  private ApiTask getTaskFromApiQueue()
  {
    synchronized (this.apiTaskQueue)
    {
      if (this.apiTaskQueue.size() > 0)
      {
        ApiTask localApiTask = (ApiTask)this.apiTaskQueue.remove(0);
        return localApiTask;
      }
      return null;
    }
  }

  private ImageTask getTaskFromHighImageQueue()
  {
    synchronized (this.highImageQueue)
    {
      if (this.highImageQueue.size() > 0)
      {
        Logger.i("image", "getTaskFormHigh queueSize=" + this.highImageQueue.size());
        ImageTask localImageTask = (ImageTask)this.highImageQueue.remove(0);
        return localImageTask;
      }
      return null;
    }
  }

  private ImageTask getTaskFromLowImageQueue()
  {
    synchronized (this.imgTaskQueue)
    {
      if (this.imgTaskQueue.size() > 0)
      {
        ImageTask localImageTask = (ImageTask)this.imgTaskQueue.remove(0);
        return localImageTask;
      }
      return null;
    }
  }

  private int getTaskId()
  {
    try
    {
      int i = 1 + this.taskId;
      this.taskId = i;
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public int addHighTask(ImageTask paramImageTask)
  {
    if ((paramImageTask != null) && (!TextUtils.isEmpty(paramImageTask.getUrl())))
    {
      Logger.i("image", "addHighTask[" + paramImageTask.getName() + "] queueSize:" + this.highImageQueue.size() + " url:" + paramImageTask.getUrl());
      paramImageTask.setTaskId(getTaskId());
      synchronized (this.highImageQueue)
      {
        this.imageTaskCount = (1 + this.imageTaskCount);
        this.highImageQueue.add(paramImageTask);
        return paramImageTask.getTaskId();
      }
    }
    return -1;
  }

  public int addTask(ApiTask paramApiTask)
  {
    if (paramApiTask != null)
      synchronized (this.apiTaskQueue)
      {
        this.apiTaskCount = (1 + this.apiTaskCount);
        paramApiTask.setTaskId(getTaskId());
        this.apiTaskQueue.add(paramApiTask);
        Logger.i("Api", "taskId:" + paramApiTask.getTaskId() + ", addTaskToTold Api name:" + paramApiTask.getApi().getApiName() + ", url:" + paramApiTask.getApi().toString());
        int i = paramApiTask.getTaskId();
        return i;
      }
    return -1;
  }

  public int addTask(ImageTask paramImageTask)
  {
    if ((paramImageTask != null) && (!TextUtils.isEmpty(paramImageTask.getUrl())))
    {
      Logger.i("image", "addTask [" + paramImageTask.getName() + "]url:" + paramImageTask.getUrl());
      synchronized (this.imgTaskQueue)
      {
        this.imageTaskCount = (1 + this.imageTaskCount);
        paramImageTask.setTaskId(getTaskId());
        this.imgTaskQueue.add(paramImageTask);
        int i = paramImageTask.getTaskId();
        return i;
      }
    }
    return -1;
  }

  public int addTaskToHead(ApiTask paramApiTask)
  {
    if (paramApiTask != null)
      synchronized (this.apiTaskQueue)
      {
        this.apiTaskCount = (1 + this.apiTaskCount);
        paramApiTask.setTaskId(getTaskId());
        this.apiTaskQueue.add(0, paramApiTask);
        Logger.i("Api", "taskId:" + paramApiTask.getTaskId() + ", addTaskToHead Api:" + paramApiTask.getApi().toString());
        int i = paramApiTask.getTaskId();
        return i;
      }
    return -1;
  }

  public void cancelApiTask(int paramInt)
  {
    while (true)
    {
      int i;
      synchronized (this.apiTaskQueue)
      {
        i = -1 + this.apiTaskQueue.size();
        if (i >= 0)
        {
          if (paramInt == ((ApiTask)this.apiTaskQueue.get(i)).getTaskId())
          {
            ApiTask localApiTask = (ApiTask)this.apiTaskQueue.remove(i);
            localApiTask.setHandler(null);
            Logger.i("API", "cancelApiTask apiTaskQueue API:" + localApiTask.getApi());
          }
        }
        else
          return;
      }
      i--;
    }
  }

  public void cancelImageTask(int paramInt)
  {
    while (true)
    {
      int i;
      int j;
      synchronized (this.imgTaskQueue)
      {
        i = -1 + this.imgTaskQueue.size();
        if (i >= 0)
        {
          if (paramInt != ((ImageTask)this.imgTaskQueue.get(i)).getTaskId())
            break label228;
          ImageTask localImageTask1 = (ImageTask)this.imgTaskQueue.remove(i);
          localImageTask1.setHandler(null);
          Logger.i("image", "cancelImageTask imgTaskQueue url:" + localImageTask1.getUrl());
          return;
        }
        synchronized (this.highImageQueue)
        {
          j = -1 + this.highImageQueue.size();
          if (j < 0)
            break label224;
          if (paramInt == ((ImageTask)this.highImageQueue.get(j)).getTaskId())
          {
            ImageTask localImageTask2 = (ImageTask)this.highImageQueue.remove(j);
            localImageTask2.setHandler(null);
            Logger.i("image", "cancelImageTask highImageQueue url:" + localImageTask2.getUrl());
            return;
          }
        }
      }
      j--;
      continue;
      label224: return;
      label228: i--;
    }
  }

  public void clearHighQueue()
  {
    Logger.i("image", "clearHighQueue");
    synchronized (this.highImageQueue)
    {
      this.highImageQueue.clear();
      return;
    }
  }

  public IBinder onBind(Intent paramIntent)
  {
    Logger.i("DownLoadService", "onBind");
    Logger.i("Application", "DownLoadService onBind");
    return this.binder;
  }

  public void onCreate()
  {
    super.onCreate();
    Logger.i("TAG", "onCreate");
    for (int i = 0; i < this.HighImagePool.length; i++)
    {
      this.HighImagePool[i] = new Thread(new ImageRunnalbe(0));
      this.HighImagePool[i].setName("HighImagePool:" + i);
      this.HighImagePool[i].setPriority(4);
      this.HighImagePool[i].start();
    }
    for (int j = 0; j < this.LowImagePool.length; j++)
    {
      this.LowImagePool[j] = new Thread(new ImageRunnalbe(1));
      this.LowImagePool[j].setName("LowImagePool:" + j);
      this.HighImagePool[j].setPriority(3);
      this.LowImagePool[j].start();
    }
    for (int k = 0; k < this.ApiPool.length; k++)
    {
      this.ApiPool[k] = new Thread(new ApiRunnable());
      this.ApiPool[k].setName("ApiImagePool:" + k);
      this.ApiPool[k].start();
    }
    this.http = HttpWrapper.getInstance(this);
  }

  public void unbindService(ServiceConnection paramServiceConnection)
  {
    int i = OTTTV.shutdown();
    Logger.i("exit_text", "download service unBindService");
    Logger.d("service", "停止OTTTV中间件:" + i);
    super.unbindService(paramServiceConnection);
    this.exitFlag = true;
    onDestroy();
  }

  class ApiRunnable
    implements Runnable
  {
    ApiRunnable()
    {
    }

    public void clearOldLocalCacheInMemory()
    {
      try
      {
        synchronized (DownLoadService.this.localCacheManager)
        {
          if (DownLoadService.this.localCacheManager.size() > 100)
          {
            Logger.i("api", "clearOldLocalCacheInMemory force clear too much size:" + DownLoadService.this.localCacheManager.size());
            DownLoadService.this.localCacheManager.clear();
            DownLoadService.access$402(DownLoadService.this, SystemClock.elapsedRealtime());
            return;
          }
          if ((SystemClock.elapsedRealtime() < 60000L + DownLoadService.this.checkClearCacheTime) && (DownLoadService.this.localCacheManager.size() < 200))
            return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
      DownLoadService.access$402(DownLoadService.this, SystemClock.elapsedRealtime());
      Logger.i("api", "clearOldLocalCacheInMemory size1:" + DownLoadService.this.localCacheManager.size());
      Iterator localIterator = DownLoadService.this.localCacheManager.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        if ((localEntry == null) || (localEntry.getValue() == null) || (!((ApiCacheObject)localEntry.getValue()).isCacheValid()))
          localIterator.remove();
      }
      Logger.i("api", "clearOldLocalCacheInMemory size2:" + DownLoadService.this.localCacheManager.size());
    }

    public void doRun(ApiTask paramApiTask)
    {
      String str = webUrlFormatter.i().formatUrl(paramApiTask.getApi().toString(), paramApiTask.getApi().getApiName());
      if (DownLoadService.this.isUserMemoryCache);
      for (Object localObject = processLocalCacheResultInMemory(paramApiTask, str); localObject != null; localObject = processLocalCacheResultInFile(paramApiTask, str))
      {
        Logger.i("Api", "taskId:" + paramApiTask.getTaskId() + ", doRun object:" + localObject.toString());
        notifyMessage(paramApiTask, 200, localObject);
        return;
      }
      int i = 1 + paramApiTask.getApi().getRetryNum();
      while (i > 0)
      {
        Message localMessage = processTaskFromUrl(paramApiTask, str);
        if ((i == 1) || (localMessage.arg1 == 200))
        {
          notifyMessage(paramApiTask, localMessage.arg1, localMessage.obj);
          return;
        }
        if (localMessage.arg1 != 200)
          i--;
        Logger.i("DownLoadService", "taskId:" + paramApiTask.getTaskId() + ",本次请求Num:" + i + ",retryNum:" + paramApiTask.getApi().getRetryNum());
      }
    }

    public void notifyMessage(ApiTask paramApiTask, int paramInt, Object paramObject)
    {
      Handler localHandler = paramApiTask.getHandler();
      Message localMessage = new Message();
      if ((localHandler != null) && (localMessage != null))
      {
        localMessage.obj = paramObject;
        localMessage.arg1 = paramInt;
        localMessage.arg2 = paramApiTask.getTaskId();
        localMessage.what = paramApiTask.getMessageNumber();
        localHandler.sendMessage(localMessage);
      }
    }

    public Object processLocalCacheResultInFile(ApiTask paramApiTask, String paramString)
    {
      long l = paramApiTask.getApi().getCacheValidTime();
      if (l < 0L)
        l = 300000L;
      if (l == 0L)
      {
        Logger.i("Api", "taskId:" + paramApiTask.getTaskId() + ", processLocalCacheResult cacheValidTime==0");
        return null;
      }
      File localFile = new File(GlobalEnv.getInstance().getAPICachePath() + File.separator + GeneralUtils.MD5(paramString));
      if ((localFile == null) || (!localFile.exists()) || (!localFile.isFile()))
      {
        Logger.i("Api", "taskId:" + paramApiTask.getTaskId() + ", processLocalCacheResult File error");
        return null;
      }
      if ((System.currentTimeMillis() < localFile.lastModified()) || (System.currentTimeMillis() - localFile.lastModified() > l))
      {
        Logger.i("Api", "taskId:" + paramApiTask.getTaskId() + ",processLocalCacheResult time error");
        return null;
      }
      Object localObject = IOTools.readObject(localFile);
      StringBuilder localStringBuilder = new StringBuilder().append("taskId:").append(paramApiTask.getTaskId()).append(", cache hit[now:").append(System.currentTimeMillis()).append("],[file:").append(localFile.lastModified()).append("] ");
      if (localObject != null);
      for (String str = "object 不为空"; ; str = "object 为空")
      {
        Logger.i("Api", str + "url:" + paramString);
        return localObject;
      }
    }

    public Object processLocalCacheResultInMemory(ApiTask paramApiTask, String paramString)
    {
      long l = paramApiTask.getApi().getCacheValidTime();
      if (l < 0L)
        l = 300000L;
      if (l == 0L)
      {
        Logger.i("Api", "taskId:" + paramApiTask.getTaskId() + ", processLocalCacheResultInMemory cacheValidTime==0");
        return null;
      }
      String str1 = GeneralUtils.MD5(paramString);
      while (true)
      {
        synchronized (DownLoadService.this.localCacheManager)
        {
          ApiCacheObject localApiCacheObject = (ApiCacheObject)DownLoadService.this.localCacheManager.get(str1);
          Object localObject2;
          if (localApiCacheObject != null)
          {
            if (localApiCacheObject.isCacheValid(l))
            {
              localObject2 = localApiCacheObject.getCacheObject();
              StringBuilder localStringBuilder = new StringBuilder().append("taskId:").append(paramApiTask.getTaskId()).append(", processLocalCacheResultInMemory cache hit");
              if (localObject2 != null)
              {
                str2 = "object 不为空";
                Logger.i("Api", str2 + "url:" + paramString);
                return localObject2;
              }
            }
            else
            {
              DownLoadService.this.localCacheManager.remove(str1);
              localObject2 = null;
              continue;
            }
          }
          else
          {
            Logger.i("Api", "not found key:" + str1);
            localObject2 = null;
          }
        }
        String str2 = "object 为空";
      }
    }

    public Message processTaskFromUrl(ApiTask paramApiTask, String paramString)
    {
      Message localMessage = new Message();
      InputStream localInputStream = DownLoadService.this.http.getInputStreamFromURL(localMessage, paramString, paramApiTask);
      if (localInputStream == null)
        Logger.i("Api", "taskId:" + paramApiTask.getTaskId() + ", getInputStreamFromURL in==null code:" + localMessage.arg1);
      do
      {
        return localMessage;
        localMessage.obj = paramApiTask.getParser().parser(localInputStream);
        if (localMessage.obj == null)
        {
          localMessage.arg1 = 602;
          return localMessage;
        }
      }
      while (localMessage.arg1 != 200);
      if (DownLoadService.this.isUserMemoryCache)
      {
        processWriteLocalCacheInMemory(paramApiTask, paramString, localMessage.obj);
        return localMessage;
      }
      processWriteLocalCacheInFile(paramApiTask, paramString, localMessage.obj);
      return localMessage;
    }

    public void processWriteLocalCacheInFile(ApiTask paramApiTask, String paramString, Object paramObject)
    {
      long l = paramApiTask.getApi().getCacheValidTime();
      if (l < 0L)
        l = 300000L;
      if (l == 0L)
        return;
      Logger.i("Api", "taskId:" + paramApiTask.getTaskId() + ", write to cache file url:" + paramString);
      File localFile = new File(GlobalEnv.getInstance().getAPICachePath() + File.separator + GeneralUtils.MD5(paramString));
      if ((localFile.exists()) && (localFile.isFile()))
        localFile.delete();
      Logger.i("Api", "taskId:" + paramApiTask.getTaskId() + ", processWriteLocalCache object:" + paramObject);
      IOTools.writeObject(localFile, paramObject);
    }

    public void processWriteLocalCacheInMemory(ApiTask paramApiTask, String paramString, Object paramObject)
    {
      long l = paramApiTask.getApi().getCacheValidTime();
      if (l < 0L)
        l = 300000L;
      if (l == 0L)
        return;
      Logger.i("Api", "taskId:" + paramApiTask.getTaskId() + ", processWriteLocalCacheInMemory write to cache url:" + paramString);
      String str = GeneralUtils.MD5(paramString);
      synchronized (DownLoadService.this.localCacheManager)
      {
        ApiCacheObject localApiCacheObject = new ApiCacheObject(paramObject, l);
        DownLoadService.this.localCacheManager.put(str, localApiCacheObject);
        Logger.i("Api", "localCacheManager size:" + DownLoadService.this.localCacheManager.size());
        Logger.i("Api", "taskId:" + paramApiTask.getTaskId() + ", processWriteLocalCacheInMemory object:" + paramObject);
        return;
      }
    }

    public void run()
    {
      while (!DownLoadService.this.exitFlag)
      {
        ApiTask localApiTask = DownLoadService.this.getTaskFromApiQueue();
        if (localApiTask != null)
        {
          if (DownLoadService.this.apiTaskCount % 500 == 499)
            GlobalEnv.getInstance().clearApiOldCache();
          clearOldLocalCacheInMemory();
          Logger.i("Api", "taskId:" + localApiTask.getTaskId() + ", " + localApiTask.getApi().getApiName() + ",getApiTask url:" + localApiTask.getApi().toString());
          doRun(localApiTask);
        }
        else
        {
          try
          {
            Thread.sleep(100L);
          }
          catch (InterruptedException localInterruptedException)
          {
            localInterruptedException.printStackTrace();
          }
        }
      }
    }
  }

  class ImageRunnalbe
    implements Runnable
  {
    private static final int HIGH = 0;
    private static final int LOW = 1;
    private ImageTask task = null;
    private int threadType = 0;

    public ImageRunnalbe(int arg2)
    {
      int i;
      this.threadType = i;
    }

    public void doRun()
    {
      int i = this.task.getProcess();
      int j = i & 0x2;
      int k = 0;
      int m = 0;
      if (j == 2)
      {
        k = this.task.getScaledWidth();
        m = this.task.getScaledHeight();
      }
      Bitmap.Config localConfig = Bitmap.Config.ARGB_8888;
      if ((i & 0x2) == 2)
        localConfig = this.task.getOutPixelFormat();
      if ((i & 0x1) == 1);
      for (Bitmap localBitmap = getImage(this.task.getUrl(), 1, k, m, localConfig); ; localBitmap = getImage(this.task.getUrl(), 0, k, m, localConfig))
      {
        Handler localHandler = this.task.getHandler();
        if (localHandler != null)
        {
          Message localMessage = new Message();
          localMessage.obj = localBitmap;
          localMessage.arg2 = this.task.getTaskId();
          localMessage.what = this.task.getMessageNumber();
          localHandler.sendMessage(localMessage);
        }
        this.task = null;
        return;
      }
    }

    public Bitmap getImage(String paramString, int paramInt1, int paramInt2, int paramInt3, Bitmap.Config paramConfig)
    {
      if (AppFuncCfg.FUNCTION_BITMAP_FROM_JNI)
        return getImageJNI(paramString, paramInt1, paramInt2, paramInt3, paramConfig);
      return getImageNative(paramString, paramInt1, paramInt2, paramInt3, paramConfig);
    }

    public Bitmap getImageJNI(String paramString, int paramInt1, int paramInt2, int paramInt3, Bitmap.Config paramConfig)
    {
      File localFile1 = new File(GlobalEnv.getInstance().getPicCachePath(), GeneralUtils.getImageNameFromUrl(paramString));
      if (localFile1.exists())
      {
        Bitmap localBitmap2 = EIBitmapUtil.decodeFile(localFile1.getAbsolutePath(), paramConfig, paramInt2, paramInt3);
        if (localBitmap2 != null)
        {
          if (localBitmap2.getWidth() * localBitmap2.getHeight() != 0)
          {
            localFile1.setLastModified(System.currentTimeMillis());
            TempFileManager.getInstance().addTempFile(localFile1);
            Logger.i("image", "getImage[" + this.task.getName() + "] cache hit:" + paramString);
            return localBitmap2;
          }
          localFile1.delete();
        }
      }
      try
      {
        localInputStream = DownLoadService.this.http.getImageInputStreamFromURL(paramString);
        if (localInputStream == null)
        {
          Logger.e("image", "getImage[" + this.task.getName() + "] inputStream is null url:" + paramString);
          return null;
        }
        localFile2 = new File(GlobalEnv.getInstance().getPicCachePath(), UUID.randomUUID().toString());
        localFileOutputStream = new FileOutputStream(localFile2);
        byte[] arrayOfByte = new byte[32768];
        while (true)
        {
          int i = localInputStream.read(arrayOfByte);
          if (i == -1)
            break;
          localFileOutputStream.write(arrayOfByte, 0, i);
        }
      }
      catch (MalformedURLException localMalformedURLException)
      {
        InputStream localInputStream;
        File localFile2;
        FileOutputStream localFileOutputStream;
        Logger.e("image", "getImage[" + this.task.getName() + "] MalformedURLException url:" + paramString);
        return null;
        localFileOutputStream.close();
        localInputStream.close();
        localFile2.renameTo(localFile1);
        Logger.i("image", "getImage[" + this.task.getName() + "] DataOK ok url" + paramString);
        Bitmap localBitmap1 = EIBitmapUtil.decodeFile(localFile1.getAbsolutePath(), paramConfig, paramInt2, paramInt3);
        return localBitmap1;
      }
      catch (ProtocolException localProtocolException)
      {
        while (true)
          Logger.e("image", "getImage[" + this.task.getName() + "] ProtocolException url:" + paramString);
      }
      catch (FileNotFoundException localFileNotFoundException)
      {
        while (true)
          Logger.e("image", "getImage[" + this.task.getName() + "] FileNotFoundException url:" + paramString);
      }
      catch (IOException localIOException)
      {
        while (true)
        {
          Logger.e("image", "getImage[" + this.task.getName() + "] IOException url:" + paramString);
          if (localFile1.exists())
            localFile1.delete();
        }
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }

    public Bitmap getImageNative(String paramString, int paramInt1, int paramInt2, int paramInt3, Bitmap.Config paramConfig)
    {
      File localFile1 = new File(GlobalEnv.getInstance().getPicCachePath(), GeneralUtils.getImageNameFromUrl(paramString));
      if (localFile1.exists())
      {
        Bitmap localBitmap2 = BitmapTools.decodeFile(localFile1.getAbsolutePath(), paramConfig, paramInt2, paramInt3);
        if (localBitmap2 != null)
        {
          if (localBitmap2.getWidth() * localBitmap2.getHeight() != 0)
          {
            Logger.i("image", "getImage[" + this.task.getName() + "] cache hit:" + paramString);
            localFile1.setLastModified(System.currentTimeMillis());
            TempFileManager.getInstance().addTempFile(localFile1);
            return localBitmap2;
          }
          localFile1.delete();
        }
      }
      try
      {
        localInputStream = DownLoadService.this.http.getImageInputStreamFromURL(paramString);
        if (localInputStream == null)
        {
          Logger.e("image", "getImage[" + this.task.getName() + "] inputStream is null url:" + paramString);
          return null;
        }
        localFile2 = new File(GlobalEnv.getInstance().getPicCachePath(), UUID.randomUUID().toString());
        localFileOutputStream = new FileOutputStream(localFile2);
        byte[] arrayOfByte = new byte[32768];
        while (true)
        {
          int i = localInputStream.read(arrayOfByte);
          if (i == -1)
            break;
          localFileOutputStream.write(arrayOfByte, 0, i);
        }
      }
      catch (MalformedURLException localMalformedURLException)
      {
        InputStream localInputStream;
        File localFile2;
        FileOutputStream localFileOutputStream;
        Logger.e("image", "getImage[" + this.task.getName() + "] MalformedURLException url:" + paramString);
        return null;
        localFileOutputStream.close();
        localInputStream.close();
        localFile2.renameTo(localFile1);
        TempFileManager.getInstance().addTempFile(localFile1);
        Logger.i("image", "getImage[" + this.task.getName() + "] DataOK ok url" + paramString);
        Bitmap localBitmap1 = BitmapTools.decodeFile(localFile1.getAbsolutePath(), paramConfig, paramInt2, paramInt3);
        return localBitmap1;
      }
      catch (ProtocolException localProtocolException)
      {
        while (true)
          Logger.e("image", "getImage[" + this.task.getName() + "] ProtocolException url:" + paramString);
      }
      catch (FileNotFoundException localFileNotFoundException)
      {
        while (true)
          Logger.e("image", "getImage[" + this.task.getName() + "] FileNotFoundException url:" + paramString);
      }
      catch (IOException localIOException)
      {
        while (true)
        {
          Logger.e("image", "getImage[" + this.task.getName() + "] IOException url:" + paramString);
          if (localFile1.exists())
            localFile1.delete();
        }
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }

    public void run()
    {
      if (!DownLoadService.this.exitFlag)
      {
        if (this.threadType == 0);
        for (this.task = DownLoadService.this.getTaskFromHighImageQueue(); ; this.task = DownLoadService.this.getTaskFromLowImageQueue())
        {
          while (true)
          {
            if (this.task != null)
              break label66;
            try
            {
              Thread.sleep(100L);
            }
            catch (Exception localException)
            {
              localException.printStackTrace();
            }
          }
          break;
        }
        label66: if (DownLoadService.this.imageTaskCount % 500 == 499)
          GlobalEnv.getInstance().clearPicOldCache();
        StringBuilder localStringBuilder = new StringBuilder().append("getImageTask[").append(this.task.getName()).append("] isHigh:");
        if (this.threadType == 0);
        for (boolean bool = true; ; bool = false)
        {
          Logger.i("image", bool + ",url:" + this.task.getUrl());
          doRun();
          break;
        }
      }
    }
  }

  public class MyBinder extends Binder
  {
    public MyBinder()
    {
    }

    public DownLoadService getService()
    {
      return DownLoadService.this;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.service.DownLoadService
 * JD-Core Version:    0.6.2
 */