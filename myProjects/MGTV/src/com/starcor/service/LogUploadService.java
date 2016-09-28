package com.starcor.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.text.TextUtils;
import com.starcor.config.MgtvUrl;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.opendownload.logupload.LogCacheManger;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogUploadService extends Service
{
  private static final String CHARSET = "utf-8";
  public static final String FAILURE = "failed";
  public static final String SUCCESS = "success";
  private static final int TIME_OUT = 300000;
  private static ArrayList<String> filelist = new ArrayList();
  private final String TAG = LogUploadService.class.getSimpleName();
  private IBinder binder = new LocalBinder();
  private boolean exitFlag = false;
  private ExecutorService pool;

  private void getFiles(String paramString)
  {
    if (LogCacheManger.getInstance().isWritingFile());
    while (true)
    {
      return;
      for (File localFile : new File(paramString).listFiles())
        if (localFile.isFile())
        {
          Logger.i(this.TAG, "检测到" + localFile.getName());
          filelist.add(localFile.getAbsolutePath());
        }
    }
  }

  public IBinder onBind(Intent paramIntent)
  {
    return this.binder;
  }

  public void onCreate()
  {
    super.onCreate();
    Logger.i(this.TAG, "开启下载上传服务");
    this.pool = Executors.newSingleThreadExecutor();
    this.pool.execute(new CheckAndUpload());
    this.pool.shutdown();
  }

  public void onDestroy()
  {
    super.onDestroy();
    Logger.i(this.TAG, "结束下载上传服务");
    this.exitFlag = true;
  }

  public String uploadFile(File paramFile, String paramString)
  {
    String str = UUID.randomUUID().toString();
    if (TextUtils.isEmpty(paramString))
    {
      Logger.i(this.TAG, "请求url为空");
      return "failed";
    }
    try
    {
      Logger.i(this.TAG, "传入的上传url" + paramString);
      localHttpURLConnection = (HttpURLConnection)new URL(paramString).openConnection();
      localHttpURLConnection.setReadTimeout(300000);
      localHttpURLConnection.setConnectTimeout(300000);
      localHttpURLConnection.setDoInput(true);
      localHttpURLConnection.setDoOutput(true);
      localHttpURLConnection.setUseCaches(false);
      localHttpURLConnection.setRequestMethod("POST");
      localHttpURLConnection.setRequestProperty("Charset", "utf-8");
      localHttpURLConnection.setRequestProperty("connection", "keep-alive");
      localHttpURLConnection.setRequestProperty("Content-Type", "multipart/form-data" + ";boundary=" + str);
      if (paramFile != null)
      {
        localDataOutputStream = new DataOutputStream(localHttpURLConnection.getOutputStream());
        localDataOutputStream.write(("--" + str + "\r\n" + "Content-Disposition: form-data; name=\"img\"; filename=\"" + paramFile.getName() + "\"" + "\r\n" + "Content-Type: application/octet-stream; charset=" + "utf-8" + "\r\n" + "\r\n").getBytes());
        localFileInputStream = new FileInputStream(paramFile);
        byte[] arrayOfByte = new byte[1024];
        while (true)
        {
          int i = localFileInputStream.read(arrayOfByte);
          if (i == -1)
            break;
          localDataOutputStream.write(arrayOfByte, 0, i);
        }
      }
    }
    catch (MalformedURLException localMalformedURLException)
    {
      HttpURLConnection localHttpURLConnection;
      DataOutputStream localDataOutputStream;
      FileInputStream localFileInputStream;
      localMalformedURLException.printStackTrace();
      int j;
      do
      {
        return "failed";
        localFileInputStream.close();
        localDataOutputStream.write("\r\n".getBytes());
        localDataOutputStream.write(("--" + str + "--" + "\r\n").getBytes());
        localDataOutputStream.flush();
        j = localHttpURLConnection.getResponseCode();
        Logger.i(this.TAG, "response code:" + j);
      }
      while (j != 200);
      return "success";
    }
    catch (IOException localIOException)
    {
      while (true)
        localIOException.printStackTrace();
    }
  }

  class CheckAndUpload extends Thread
  {
    CheckAndUpload()
    {
    }

    public void run()
    {
      while (true)
      {
        if ((LogUploadService.this.exitFlag) || (LogUploadService.filelist == null))
          return;
        LogUploadService.filelist.clear();
        LogUploadService.this.getFiles(LogCacheManger.logPath);
        Iterator localIterator = LogUploadService.filelist.iterator();
        while (localIterator.hasNext())
        {
          File localFile = new File((String)localIterator.next());
          if ("success".equals(LogUploadService.this.uploadFile(localFile, MgtvUrl.getReportTerminalStatus())))
          {
            Logger.i(LogUploadService.this.TAG, "日志上传成功");
            if (localFile.exists())
            {
              Logger.i(LogUploadService.this.TAG, "删除日志压缩包" + localFile.getName() + "大小为" + localFile.length());
              localFile.delete();
            }
          }
          else
          {
            Logger.i(LogUploadService.this.TAG, "日志上传失败");
          }
        }
        try
        {
          Thread.sleep(5000L);
        }
        catch (InterruptedException localInterruptedException)
        {
          localInterruptedException.printStackTrace();
        }
      }
    }
  }

  public class LocalBinder extends Binder
  {
    public LocalBinder()
    {
    }

    public LogUploadService getService()
    {
      return LogUploadService.this;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.service.LogUploadService
 * JD-Core Version:    0.6.2
 */