package com.starcor.service;

import android.app.Service;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import com.starcor.core.utils.Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.json.JSONException;
import org.json.JSONObject;

public class DownloadManagerService extends Service
{
  private static final String TAG = DownloadManagerService.class.getSimpleName();
  static DownloadManager _mgr;

  public IBinder onBind(Intent paramIntent)
  {
    if (_mgr == null)
      _mgr = new DownloadManager();
    return _mgr.asBinder();
  }

  public void onDestroy()
  {
    Logger.d(TAG, "service destroyed!");
    super.onDestroy();
  }

  class DownloadManager extends IDownloadManager.Stub
  {
    private final String TAG = DownloadManager.class.getSimpleName();
    DownloadManagerService.FileDownloader _downloader;
    int _lastTaskId = 0;
    IDownloadEventListener _listener;
    DownloadTaskInfo _runningTask = null;
    HashMap<Integer, DownloadTaskInfo> _taskMap = new HashMap();

    DownloadManager()
    {
    }

    private boolean _doResumeTask(DownloadTaskInfo paramDownloadTaskInfo)
    {
      if (this._runningTask != null);
      while (true)
      {
        return false;
        if ((!paramDownloadTaskInfo.state.equals("downloading")) && (!paramDownloadTaskInfo.state.equals("installing")) && (!paramDownloadTaskInfo.state.equals("success")))
        {
          this._downloader = new DownloadManagerService.FileDownloader(paramDownloadTaskInfo);
          DownloadManagerService.FileDownloader localFileDownloader = this._downloader;
          String str = paramDownloadTaskInfo.url;
          File localFile = new File(paramDownloadTaskInfo.localFile);
          if (!paramDownloadTaskInfo.state.equals("cancelled"));
          for (boolean bool = true; localFileDownloader.start(str, localFile, bool, this); bool = false)
          {
            this._runningTask = paramDownloadTaskInfo;
            this._runningTask.state = "downloading";
            return true;
          }
        }
      }
    }

    private void _executeAnotherRunnableTask()
    {
      if (this._runningTask != null);
      DownloadTaskInfo localDownloadTaskInfo;
      do
      {
        return;
        Iterator localIterator;
        while (!localIterator.hasNext())
          localIterator = this._taskMap.keySet().iterator();
        Integer localInteger = (Integer)localIterator.next();
        localDownloadTaskInfo = (DownloadTaskInfo)this._taskMap.get(localInteger);
      }
      while (!"pending".equals(localDownloadTaskInfo.state));
      _doResumeTask(localDownloadTaskInfo);
    }

    private void _notifyTaskStateChanged(DownloadTaskInfo paramDownloadTaskInfo)
    {
      IDownloadEventListener localIDownloadEventListener = this._listener;
      if (localIDownloadEventListener == null)
        return;
      try
      {
        localIDownloadEventListener.onEvent(paramDownloadTaskInfo.state, paramDownloadTaskInfo.id, paramDownloadTaskInfo);
        return;
      }
      catch (RemoteException localRemoteException)
      {
        localRemoteException.printStackTrace();
      }
    }

    public int cancelTask(int paramInt)
      throws RemoteException
    {
      DownloadTaskInfo localDownloadTaskInfo = (DownloadTaskInfo)this._taskMap.get(Integer.valueOf(paramInt));
      if (localDownloadTaskInfo == null)
        return -1;
      if (localDownloadTaskInfo == this._runningTask)
      {
        this._downloader.stop();
        this._runningTask = null;
      }
      localDownloadTaskInfo.state = "cancelled";
      _executeAnotherRunnableTask();
      return 0;
    }

    public int createTask(String paramString1, String paramString2, String paramString3)
      throws RemoteException
    {
      URL localURL;
      DownloadTaskInfo localDownloadTaskInfo;
      while (true)
      {
        String str2;
        try
        {
          localURL = new URL(paramString1);
          this._lastTaskId = (1 + this._lastTaskId);
          localDownloadTaskInfo = new DownloadTaskInfo();
          localDownloadTaskInfo.id = this._lastTaskId;
          localDownloadTaskInfo.beginTime = System.currentTimeMillis();
          String[] arrayOfString = paramString2.split(",");
          if (arrayOfString == null)
            break;
          int i = arrayOfString.length;
          int j = 0;
          if (j >= i)
            break;
          str2 = arrayOfString[j].trim();
          if (TextUtils.isEmpty(str2))
          {
            j++;
            continue;
          }
        }
        catch (MalformedURLException localMalformedURLException)
        {
          localMalformedURLException.printStackTrace();
          return -1;
        }
        localDownloadTaskInfo.tags.add(str2);
      }
      try
      {
        JSONObject localJSONObject = new JSONObject(paramString3);
        localDownloadTaskInfo.extInfo = localJSONObject;
        localDownloadTaskInfo.url = paramString1;
        localObject = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        if ((!((File)localObject).exists()) || (!((File)localObject).canWrite()))
        {
          localObject = Environment.getExternalStorageDirectory();
          if (((File)localObject).exists())
            localFile1 = new File((File)localObject, "StarcorDownload");
        }
      }
      catch (JSONException localException2)
      {
        try
        {
          localFile1.mkdirs();
          localFile1.setReadable(true, false);
          boolean bool2 = localFile1.exists();
          if (bool2)
            localObject = localFile1;
          if ((!((File)localObject).exists()) || (!((File)localObject).canWrite()))
          {
            localObject = new File("/data/tmp");
            if (((File)localObject).exists())
              localFile2 = new File((File)localObject, "StarcorDownload");
          }
        }
        catch (Exception localException2)
        {
          try
          {
            Object localObject;
            localFile2.mkdirs();
            localFile2.setReadable(true, false);
            boolean bool1 = localFile2.exists();
            if (bool1)
              localObject = localFile2;
            if ((!((File)localObject).exists()) || (((File)localObject).canWrite()))
              localObject = DownloadManagerService.this.getDir("StarcorDownload", 1);
            String str1 = localURL.getFile();
            if (str1.contains("?"))
              str1 = str1.split("\\?")[0];
            localFile3 = new File((File)localObject, new File(str1).getName());
            Logger.d(this.TAG, "download url:" + paramString1);
            Logger.d(this.TAG, "download local file:" + localFile3.getAbsolutePath());
          }
          catch (Exception localException2)
          {
            try
            {
              File localFile2;
              while (true)
              {
                File localFile1;
                File localFile3;
                if (localFile3.exists())
                  localFile3.delete();
                localFile3.createNewFile();
                localFile3.setReadable(true, false);
                localDownloadTaskInfo.localFile = localFile3.getAbsolutePath();
                localDownloadTaskInfo.state = "pending";
                this._taskMap.put(Integer.valueOf(localDownloadTaskInfo.getTaskId()), localDownloadTaskInfo);
                _doResumeTask(localDownloadTaskInfo);
                return localDownloadTaskInfo.id;
                localJSONException = localJSONException;
                localJSONException.printStackTrace();
                continue;
                localException1 = localException1;
                Logger.e(this.TAG, "cannotCreate " + localFile1.getAbsolutePath());
              }
              localException2 = localException2;
              Logger.e(this.TAG, "cannotCreate " + localFile2.getAbsolutePath());
            }
            catch (Exception localException3)
            {
              localException3.printStackTrace();
            }
          }
        }
      }
      return -1;
    }

    public DownloadTaskInfo getTaskInfo(int paramInt)
      throws RemoteException
    {
      return (DownloadTaskInfo)this._taskMap.get(Integer.valueOf(paramInt));
    }

    public int[] getTaskList(String paramString)
      throws RemoteException
    {
      int[] arrayOfInt;
      if (this._taskMap.isEmpty())
        arrayOfInt = null;
      while (true)
      {
        return arrayOfInt;
        arrayOfInt = new int[this._taskMap.size()];
        int i = 0;
        Iterator localIterator = this._taskMap.keySet().iterator();
        while (localIterator.hasNext())
        {
          arrayOfInt[i] = ((Integer)localIterator.next()).intValue();
          i++;
        }
      }
    }

    public void onDownloadMessage(DownloadManagerService.FileDownloader paramFileDownloader, int paramInt, DownloadTaskInfo paramDownloadTaskInfo)
    {
      if (paramDownloadTaskInfo != this._runningTask)
        return;
      switch (paramInt)
      {
      default:
        return;
      case 1:
        paramDownloadTaskInfo.totalSize = DownloadManagerService.FileDownloader.access$000(paramFileDownloader);
        paramDownloadTaskInfo.downloadSize = DownloadManagerService.FileDownloader.access$100(paramFileDownloader);
        _notifyTaskStateChanged(paramDownloadTaskInfo);
        return;
      case 2:
        paramDownloadTaskInfo.state = "failed";
        this._runningTask = null;
        paramDownloadTaskInfo.endTime = System.currentTimeMillis();
        _executeAnotherRunnableTask();
        _notifyTaskStateChanged(paramDownloadTaskInfo);
        return;
      case 3:
        paramDownloadTaskInfo.state = "success";
        this._runningTask = null;
        paramDownloadTaskInfo.endTime = System.currentTimeMillis();
        new File(paramDownloadTaskInfo.localFile).setReadable(true, false);
        _executeAnotherRunnableTask();
        _notifyTaskStateChanged(paramDownloadTaskInfo);
        return;
      case 4:
        paramDownloadTaskInfo.state = "downloading";
        paramDownloadTaskInfo.totalSize = DownloadManagerService.FileDownloader.access$000(paramFileDownloader);
        paramDownloadTaskInfo.downloadSize = DownloadManagerService.FileDownloader.access$100(paramFileDownloader);
        _notifyTaskStateChanged(paramDownloadTaskInfo);
        return;
      case 5:
      }
      paramDownloadTaskInfo.totalSize = DownloadManagerService.FileDownloader.access$000(paramFileDownloader);
      paramDownloadTaskInfo.downloadSize = DownloadManagerService.FileDownloader.access$100(paramFileDownloader);
    }

    public int pauseTask(int paramInt)
      throws RemoteException
    {
      DownloadTaskInfo localDownloadTaskInfo = (DownloadTaskInfo)this._taskMap.get(Integer.valueOf(paramInt));
      if (localDownloadTaskInfo == null)
        return -1;
      if (localDownloadTaskInfo == this._runningTask)
      {
        this._downloader.stop();
        this._runningTask = null;
      }
      localDownloadTaskInfo.state = "paused";
      _executeAnotherRunnableTask();
      return 0;
    }

    public int removeTask(int paramInt)
      throws RemoteException
    {
      DownloadTaskInfo localDownloadTaskInfo = (DownloadTaskInfo)this._taskMap.remove(Integer.valueOf(paramInt));
      if (localDownloadTaskInfo == null)
        return -1;
      if (localDownloadTaskInfo == this._runningTask)
      {
        this._downloader.stop();
        this._runningTask = null;
      }
      localDownloadTaskInfo.state = "cancelled";
      new File(localDownloadTaskInfo.localFile).delete();
      _executeAnotherRunnableTask();
      return 0;
    }

    public int resumeTask(int paramInt)
      throws RemoteException
    {
      DownloadTaskInfo localDownloadTaskInfo = (DownloadTaskInfo)this._taskMap.get(Integer.valueOf(paramInt));
      if (localDownloadTaskInfo == null)
        return -1;
      _doResumeTask(localDownloadTaskInfo);
      return 0;
    }

    public int setEventListener(IDownloadEventListener paramIDownloadEventListener)
      throws RemoteException
    {
      this._listener = paramIDownloadEventListener;
      return 0;
    }
  }

  static class FileDownloader
    implements Runnable
  {
    static final int ERR_CANNOT_OPEN_LOCALFILE = 17;
    static final int ERR_HTTP_RESPONSE_ERROR = 19;
    static final int ERR_INTERNAL_ERROR = 1;
    static final int ERR_IO_ERROR = 18;
    static final int ERR_NO_ERROR = 0;
    static final int ERR_RESUME_CHECK_FAILED = 16;
    static final int MSG_ERROR = 2;
    static final int MSG_FINISHED = 3;
    static final int MSG_PROGRESSING = 5;
    static final int MSG_RECIVING = 4;
    static final int MSG_STARTING = 1;
    public static final String TAG = "file_downloader.run";
    private volatile AndroidHttpClient _client = null;
    private InputStream _cur_is;
    private int _error_status;
    private RandomAccessFile _file;
    private File _file_info;
    private volatile long _file_size;
    private volatile long _file_write_pos;
    private HttpGet _get;
    private volatile DownloadManagerService.DownloadManager _handler;
    private long _resume_pos;
    private byte[] _resume_sign;
    private StatusLine _status;
    private String _url;
    private Thread _worker_thread = null;
    DownloadTaskInfo taskInfo;

    public FileDownloader(DownloadTaskInfo paramDownloadTaskInfo)
    {
      this.taskInfo = paramDownloadTaskInfo;
    }

    private void sendMessage(int paramInt)
    {
      if (this._handler != null)
        this._handler.onDownloadMessage(this, paramInt, this.taskInfo);
    }

    public int getError()
    {
      return this._error_status;
    }

    public float getProgress()
    {
      if (this._file_size > 0L)
        return (float)this._file_write_pos / (float)this._file_size;
      return 0.0F;
    }

    public boolean resume()
    {
      if (this._client == null)
        return false;
      DownloadManagerService.DownloadManager localDownloadManager = this._handler;
      File localFile = this._file_info;
      String str = this._url;
      stop();
      return start(str, localFile, true, localDownloadManager);
    }

    public void run()
    {
      InputStream localInputStream;
      long l2;
      long l3;
      long l5;
      try
      {
        Log.d("file_downloader.run", "start downloading... " + this._url);
        URI localURI = new URI(this._url);
        if ((localURI.getHost() == null) || (localURI.getPath() == null))
        {
          Log.e("file_downloader.run", "URI no host/path error!! " + this._url);
          this._error_status = 1;
          sendMessage(2);
          return;
        }
        HttpGet localHttpGet = new HttpGet(localURI);
        if (this._resume_pos > 0L)
        {
          Log.d("file_downloader.run", "resuming... bytes=" + this._resume_pos + "-");
          localHttpGet.setHeader("Range", "bytes=" + Long.toString(this._resume_pos) + "-");
        }
        this._get = localHttpGet;
        sendMessage(1);
        localHttpResponse = this._client.execute(localHttpGet);
        this._status = localHttpResponse.getStatusLine();
        l1 = this._status.getStatusCode();
        Log.d("file_downloader.run", "response " + l1 + " " + this._status.getReasonPhrase());
        if ((l1 != 200L) && (l1 != 206L))
        {
          Log.e("file_downloader.run", "HTTP error!! ");
          this._error_status = 19;
          sendMessage(2);
          return;
        }
      }
      catch (IOException localIOException)
      {
        HttpResponse localHttpResponse;
        long l1;
        Log.e("file_downloader.run", "io exception");
        this._error_status = 18;
        sendMessage(2);
        return;
        sendMessage(4);
        HttpEntity localHttpEntity = localHttpResponse.getEntity();
        localInputStream = localHttpEntity.getContent();
        this._cur_is = localInputStream;
        l2 = localHttpEntity.getContentLength();
        this._file_size = l2;
        l3 = 0L;
        if ((this._resume_pos <= 0L) || (l1 != 206L))
          break label891;
        Log.d("file_downloader.run", "check content-range...");
        arrayOfHeader = localHttpResponse.getHeaders("Content-Range");
        if ((arrayOfHeader == null) || (arrayOfHeader.length != 1))
        {
          Log.e("file_downloader.run", "resume: HTTP error, no content-range header!");
          this._error_status = 1;
          sendMessage(2);
          return;
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        Header[] arrayOfHeader;
        Log.e("file_downloader.run", "number format exception");
        this._error_status = 1;
        sendMessage(2);
        return;
        String str = arrayOfHeader[0].getValue();
        localMatcher = Pattern.compile("^bytes\\s+(\\d+)-(\\d*)/(\\d+)$", 2).matcher(str);
        if (!localMatcher.matches())
        {
          Log.e("file_downloader.run", "resume: unsupported content-range:" + str);
          this._error_status = 1;
          sendMessage(2);
          return;
        }
      }
      catch (URISyntaxException localURISyntaxException)
      {
        Matcher localMatcher;
        Log.e("file_downloader.run", "URI syntax error!! " + this._url);
        this._error_status = 1;
        sendMessage(2);
        return;
        long l4 = Long.parseLong(localMatcher.group(1));
        Long.parseLong(localMatcher.group(2));
        l5 = Long.parseLong(localMatcher.group(3));
        if (l4 != this._resume_pos)
        {
          Log.e("file_downloader.run", "resume: file range invalid");
          this._error_status = 16;
          sendMessage(2);
          return;
        }
      }
      catch (Exception localException)
      {
        Log.e("file_downloader.run", "unknown exception!! " + localException.getMessage());
        this._error_status = 1;
        sendMessage(2);
        return;
      }
      this._file_size = l5;
      byte[] arrayOfByte1 = new byte[this._resume_sign.length];
      int i = 0;
      while (true)
      {
        int j = this._resume_sign.length;
        if (i >= j)
          break;
        if (Thread.interrupted())
        {
          Log.w("file_downloader.run", "download interrupted!!");
          return;
        }
        int k = this._resume_sign.length - i;
        i += localInputStream.read(arrayOfByte1, i, k);
      }
      l3 += i;
      int m = this._resume_sign.length;
      if ((i != m) || (!Arrays.equals(arrayOfByte1, this._resume_sign)))
      {
        Log.e("file_downloader.run", "resume data check failed!!");
        this._error_status = 16;
        sendMessage(2);
        return;
      }
      Log.d("file_downloader.run", "continue download from " + this._resume_pos);
      byte[] arrayOfByte2 = new byte[2048];
      while (true)
      {
        if (l3 >= l2)
          break label963;
        long l6 = Math.min(arrayOfByte2.length, l2 - l3);
        if (Thread.interrupted())
        {
          Log.w("file_downloader.run", "download interrupted!!");
          return;
          label891: Log.d("file_downloader.run", "download from begining...");
          this._file.seek(0L);
          break;
        }
        int n = localInputStream.read(arrayOfByte2, 0, (int)l6);
        this._file.write(arrayOfByte2, 0, n);
        l3 += n;
        this._file_write_pos = this._file.getFilePointer();
        sendMessage(5);
      }
      label963: this._file_write_pos = this._file.getFilePointer();
      this._file.setLength(this._file_write_pos);
      this._file.close();
      this._file = null;
      this._cur_is = null;
      this._get = null;
      localInputStream.close();
      this._client.close();
      Log.d("file_downloader.run", "download finished!");
      sendMessage(3);
      this._client = null;
    }

    public boolean start(String paramString, File paramFile, DownloadManagerService.DownloadManager paramDownloadManager)
    {
      return start(paramString, paramFile, false, paramDownloadManager);
    }

    public boolean start(String paramString, File paramFile, boolean paramBoolean, DownloadManagerService.DownloadManager paramDownloadManager)
    {
      if (this._client != null)
        return false;
      this._error_status = 0;
      this._client = AndroidHttpClient.newInstance("Starcor upgrade service");
      this._worker_thread = new Thread(this);
      try
      {
        this._file_info = paramFile;
        this._file = new RandomAccessFile(this._file_info, "rw");
        long l = this._file.length();
        if ((paramBoolean) && (l > 512L))
        {
          this._file.seek(l - 512L);
          byte[] arrayOfByte = new byte[512];
          this._file.read(arrayOfByte);
          this._resume_sign = arrayOfByte;
        }
        for (this._resume_pos = (l - 512L); ; this._resume_pos = 0L)
        {
          this._handler = paramDownloadManager;
          this._url = paramString;
          this._worker_thread.start();
          this._status = null;
          return true;
          this._resume_sign = null;
        }
      }
      catch (FileNotFoundException localFileNotFoundException)
      {
        this._error_status = 17;
        Log.e("file_downloader.start", "file not found exception");
        return false;
      }
      catch (IOException localIOException)
      {
        this._error_status = 18;
        Log.e("file_downloader.start", "io exception");
      }
      return false;
    }

    public boolean stop()
    {
      if (this._client == null)
        return false;
      this._handler = null;
      AndroidHttpClient localAndroidHttpClient;
      if (this._client != null)
      {
        localAndroidHttpClient = this._client;
        this._client = null;
      }
      try
      {
        localAndroidHttpClient.getConnectionManager().shutdown();
        localAndroidHttpClient.close();
        label47: if (this._cur_is != null);
        try
        {
          this._cur_is.close();
          if (this._get == null);
        }
        catch (Exception localException1)
        {
          try
          {
            this._get.abort();
          }
          catch (Exception localException1)
          {
            try
            {
              this._worker_thread.interrupt();
              this._worker_thread = null;
              if (this._file == null);
            }
            catch (Exception localException1)
            {
              try
              {
                while (true)
                {
                  this._file.close();
                  return true;
                  localException3 = localException3;
                  localException3.printStackTrace();
                  continue;
                  localException2 = localException2;
                  localException2.printStackTrace();
                }
                localException1 = localException1;
                localException1.printStackTrace();
              }
              catch (IOException localIOException)
              {
                while (true)
                  localIOException.printStackTrace();
              }
            }
          }
        }
      }
      catch (Exception localException4)
      {
        break label47;
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.service.DownloadManagerService
 * JD-Core Version:    0.6.2
 */