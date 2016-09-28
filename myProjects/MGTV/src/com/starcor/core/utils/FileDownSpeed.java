package com.starcor.core.utils;

import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.SpeedStruct;
import com.starcor.core.logic.UserCPLLogic;
import com.starcor.mgtv.boss.WebUrlBuilder;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

public class FileDownSpeed
{
  private static final String TAG = "FileDownSpeed";
  private static FileDownSpeed fdown;
  private long dataSize;
  private MultipartEntity entity;
  private boolean isTimeOut;
  private Timer mTimer;
  private TimerTask mTimerTask;
  private ArrayList<SpeedStruct> urlQueue = new ArrayList();
  private String value = "";

  private void down()
  {
    SpeedStruct localSpeedStruct;
    if ((this.urlQueue != null) && (!this.urlQueue.isEmpty()))
    {
      localSpeedStruct = (SpeedStruct)this.urlQueue.get(0);
      this.urlQueue.remove(0);
      if ((localSpeedStruct.time > 0) && (localSpeedStruct.url != null))
        break label57;
      down();
    }
    label57: 
    do
    {
      return;
      URL localURL;
      InputStream localInputStream;
      try
      {
        localURL = new URL(localSpeedStruct.url);
        this.mTimer = new Timer();
        this.mTimerTask = new TimerTask()
        {
          public void run()
          {
            FileDownSpeed.access$402(FileDownSpeed.this, false);
          }
        };
        Logger.d(" ---> 开始任务 -  速度测试 = " + localSpeedStruct.toString());
        this.mTimer.schedule(this.mTimerTask, 1000 * localSpeedStruct.time);
        try
        {
          URLConnection localURLConnection = localURL.openConnection();
          localURLConnection.setReadTimeout(10000);
          localInputStream = localURLConnection.getInputStream();
          byte[] arrayOfByte = new byte[1024];
          this.dataSize = 0L;
          this.isTimeOut = true;
          while (this.isTimeOut)
          {
            int i = localInputStream.read(arrayOfByte, 0, 1024);
            if (i == -1)
              break;
            this.dataSize += i;
          }
        }
        catch (Exception localException)
        {
          Matcher localMatcher1 = Pattern.compile("^http[s]?://([^\\s]*?)[:,/]{1}").matcher(localURL.toString());
          localMatcher1.find();
          String str1 = "127.0.0.1";
          if (localMatcher1.groupCount() > 0)
            str1 = localMatcher1.group(1);
          this.value = (this.value + localSpeedStruct.id + "," + str1 + "," + 0 + ";");
          down();
          localException.printStackTrace();
          return;
        }
      }
      catch (MalformedURLException localMalformedURLException)
      {
        localMalformedURLException.printStackTrace();
        return;
      }
      localInputStream.close();
      Matcher localMatcher2 = Pattern.compile("^http[s]?://([^\\s]*?)[:,/]{1}").matcher(localURL.toString());
      localMatcher2.find();
      String str2 = "127.0.0.1";
      if (localMatcher2.groupCount() > 0)
        str2 = localMatcher2.group(1);
      this.value = (this.value + localSpeedStruct.id + "," + str2 + "," + this.dataSize / 1024L / localSpeedStruct.time + ";");
    }
    while (this.urlQueue.isEmpty());
    down();
  }

  public static FileDownSpeed getInstance()
  {
    try
    {
      if (fdown == null)
        fdown = new FileDownSpeed();
      FileDownSpeed localFileDownSpeed = fdown;
      return localFileDownSpeed;
    }
    finally
    {
    }
  }

  public void add(SpeedStruct paramSpeedStruct)
  {
    this.urlQueue.add(paramSpeedStruct);
  }

  public void doTask()
  {
    new Thread()
    {
      public void run()
      {
        Logger.i("FileDownSpeed", " ---> 开启测速 ：  测速地址= " + WebUrlBuilder.getSpeedUrl() + "上报地址 = " + WebUrlBuilder.getspeedCallbackUrl());
        FileDownSpeed.access$002(FileDownSpeed.this, new MultipartEntity());
        FileDownSpeed.access$102(FileDownSpeed.this, "");
        FileDownSpeed.this.down();
        try
        {
          FileDownSpeed.access$102(FileDownSpeed.this, FileDownSpeed.this.value.substring(0, -1 + FileDownSpeed.this.value.length()));
          Logger.d(" ---> 测速   post 发送测速内容验证 = value " + FileDownSpeed.this.value);
          FileDownSpeed.this.entity.addPart("macid", new StringBody(DeviceInfo.getMac()));
          FileDownSpeed.this.entity.addPart("speeds", new StringBody(FileDownSpeed.this.value));
          FileDownSpeed.this.entity.addPart("file_id", new StringBody(UserCPLLogic.getInstance().getLastPlayMgtvFileId()));
          FileDownSpeed.this.postHeartbeatPack(WebUrlBuilder.getspeedCallbackUrl());
          Logger.d(" ---> 测速上报ok　！！");
          FileDownSpeed.access$302(FileDownSpeed.this, null);
          return;
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
          while (true)
          {
            Logger.d(" ---> 测速封装数据异常　！！");
            localUnsupportedEncodingException.printStackTrace();
          }
        }
      }
    }
    .start();
  }

  public boolean isSupportGzip(HttpResponse paramHttpResponse)
  {
    Header localHeader = paramHttpResponse.getFirstHeader("Content-Encoding");
    return (localHeader != null) && (localHeader.getValue().equalsIgnoreCase("gzip"));
  }

  public int postHeartbeatPack(String paramString)
  {
    HttpPost localHttpPost = new HttpPost();
    localHttpPost.setURI(URI.create(paramString));
    supportGzip(localHttpPost);
    int i;
    while (true)
    {
      HttpEntity localHttpEntity;
      try
      {
        if (this.entity == null)
          return -1;
        localHttpPost.setEntity(this.entity);
        HttpResponse localHttpResponse = new DefaultHttpClient().execute(localHttpPost);
        i = localHttpResponse.getStatusLine().getStatusCode();
        if (i != 200)
          break;
        localHttpEntity = localHttpResponse.getEntity();
        if (isSupportGzip(localHttpResponse))
        {
          localObject = new GZIPInputStream(localHttpEntity.getContent());
          Logger.d("speed", new String(StreamTools.getBytes((InputStream)localObject)));
          return i;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return -1;
      }
      Object localObject = localHttpEntity.getContent();
    }
    Logger.i("Api", "api connect fail code=" + i + " url:" + paramString);
    return i;
  }

  public void supportGzip(HttpRequest paramHttpRequest)
  {
    paramHttpRequest.addHeader("Accept-Encoding", "gzip");
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.utils.FileDownSpeed
 * JD-Core Version:    0.6.2
 */