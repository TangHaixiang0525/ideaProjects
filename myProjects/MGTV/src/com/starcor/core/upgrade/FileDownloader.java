package com.starcor.core.upgrade;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.os.Handler;
import android.util.Log;
import com.starcor.core.utils.GeneralUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;

public class FileDownloader
  implements Runnable
{
  static final int ERR_CANNOT_OPEN_LOCALFILE = 17;
  static final int ERR_HTTP_RESPONSE_ERROR = 19;
  static final int ERR_INTERNAL_ERROR = 1;
  static final int ERR_IO_ERROR = 18;
  static final int ERR_NO_ERROR = 0;
  static final int ERR_RESUME_CHECK_FAILED = 16;
  public static final int MSG_ERROR = 2;
  public static final int MSG_FINISHED = 3;
  public static final int MSG_PROGRESSING = 5;
  public static final int MSG_RECIVING = 4;
  public static final int MSG_STARTING = 1;
  private volatile AndroidHttpClient _client = null;
  private Context _context;
  private int _error_status;
  private RandomAccessFile _file;
  private File _file_info;
  private volatile long _file_size;
  private volatile long _file_write_pos;
  private volatile Handler _handler;
  private long _resume_pos;
  private byte[] _resume_sign;
  private StatusLine _status;
  private String _url;
  private Thread _worker_thread = null;

  public FileDownloader(Context paramContext)
  {
    this._context = paramContext;
  }

  private void sendMessage(int paramInt)
  {
    if (this._handler != null)
    {
      this._handler.removeMessages(paramInt);
      this._handler.sendEmptyMessage(paramInt);
    }
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
    Handler localHandler = this._handler;
    File localFile = this._file_info;
    String str = this._url;
    stop();
    return start(str, localFile, true, localHandler);
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
      l2 = localHttpEntity.getContentLength();
      this._file_size = l2;
      l3 = 0L;
      if ((this._resume_pos <= 0L) || (l1 != 206L))
        break label882;
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
    catch (Exception localException1)
    {
      Log.e("file_downloader.run", "unknown exception!! " + localException1.getMessage());
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
    float f = 0.0F;
    while (true)
    {
      if (l3 >= l2)
        break label974;
      long l6 = Math.min(arrayOfByte2.length, l2 - l3);
      if (Thread.interrupted())
      {
        Log.w("file_downloader.run", "download interrupted!!");
        return;
        label882: Log.d("file_downloader.run", "download from begining...");
        this._file.seek(0L);
        break;
      }
      int n = localInputStream.read(arrayOfByte2, 0, (int)l6);
      this._file.write(arrayOfByte2, 0, n);
      l3 += n;
      this._file_write_pos = this._file.getFilePointer();
      if (getProgress() - f > 0.1F)
      {
        f = getProgress();
        sendMessage(5);
      }
    }
    label974: this._file_write_pos = this._file.getFilePointer();
    this._file.setLength(this._file_write_pos);
    this._file.close();
    this._file = null;
    this._client.close();
    Log.d("file_downloader.run", "download finished!");
    File localFile = new File(this._file_info.getAbsolutePath());
    boolean bool = localFile.exists();
    if (bool);
    try
    {
      GeneralUtils.copyFile(this._context, localFile, "tmp.apk");
      localFile.delete();
      sendMessage(3);
      this._client = null;
      return;
    }
    catch (Exception localException2)
    {
      while (true)
        localException2.printStackTrace();
    }
  }

  public boolean start(String paramString, File paramFile, Handler paramHandler)
  {
    return start(paramString, paramFile, false, paramHandler);
  }

  public boolean start(String paramString, File paramFile, boolean paramBoolean, Handler paramHandler)
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
        this._handler = paramHandler;
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
    try
    {
      this._worker_thread.interrupt();
      this._worker_thread.join();
      this._worker_thread = null;
      if (this._file == null);
    }
    catch (InterruptedException localInterruptedException)
    {
      try
      {
        this._file.close();
        if (this._client != null)
          this._client.close();
        this._client = null;
        return true;
        localInterruptedException = localInterruptedException;
        localInterruptedException.printStackTrace();
      }
      catch (IOException localIOException)
      {
        while (true)
          localIOException.printStackTrace();
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.upgrade.FileDownloader
 * JD-Core Version:    0.6.2
 */