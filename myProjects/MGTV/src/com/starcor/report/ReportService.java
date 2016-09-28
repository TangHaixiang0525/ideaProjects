package com.starcor.report;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.text.TextUtils;
import com.starcor.config.AppFuncCfg;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.domain.ReportMessageEntity;
import com.starcor.message.Message;
import com.starcor.message.MessageHandler;
import com.starcor.message.MessageObserver;
import com.starcor.mgtv.boss.WebUrlBuilder;
import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

public class ReportService extends Service
{
  public static final int CDN_TIMER_PERIOD = 300000;
  public static final int HEARTBEAT_PERIOD = 300000;
  private static final int MAX_PATH = 50;
  private static final int MAX_RETRY_TIME = 3;
  private static final int REPORT_PATH = 4;
  public static final String TAG = "ReportService";
  public static boolean isAlive = false;
  private static OnReportServiceOkListener listener;
  private static LimitedLinkedList<String> lll = new LimitedLinkedList();
  private MessageObserver mDataReporter = new MessageObserver()
  {
    public void doNotify(Message paramAnonymousMessage)
    {
      ReportService.this.reportWrapper(paramAnonymousMessage);
    }
  };
  private ArrayList<ReportMessage> mPendingMessage = new ArrayList();

  private static String formatJson(JSONObject paramJSONObject)
  {
    StringBuilder localStringBuilder = new StringBuilder("?");
    Iterator localIterator = paramJSONObject.keys();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localStringBuilder.append(str + "=" + paramJSONObject.optString(str) + "&");
    }
    return localStringBuilder.toString();
  }

  public static LimitedLinkedList getReportPath()
  {
    if (lll == null)
      lll = new LimitedLinkedList();
    return lll;
  }

  private void report(Message paramMessage)
  {
    ReportMessage localReportMessage1 = (ReportMessage)paramMessage;
    switch (localReportMessage1.getReportType())
    {
    default:
      reportByGet(localReportMessage1);
      return;
    case 3:
      synchronized (this.mPendingMessage)
      {
        Iterator localIterator = this.mPendingMessage.iterator();
        if (localIterator.hasNext())
        {
          ReportMessage localReportMessage2 = (ReportMessage)localIterator.next();
          Logger.i("ReportService", "REPORT_TYPE_POST_DELAY pending message size = " + this.mPendingMessage.size());
          reportByPostWithHttpPost(localReportMessage2);
          localReportMessage2.setDelayReportEnable(false);
          Logger.i("ReportService", "REPORT_TYPE_POST_DELAY " + this.mPendingMessage.size());
          localIterator.remove();
        }
      }
      return;
    case 2:
    }
    reportByPostWithHttpPost(localReportMessage1);
  }

  private void reportByGet(ReportMessage paramReportMessage)
  {
    String str1;
    if (paramReportMessage.getIsLiveReport())
      str1 = WebUrlBuilder.getLiveReportUrl();
    while (true)
    {
      String str2 = str1 + formatJson((JSONObject)paramReportMessage.mExtData);
      HttpGet localHttpGet = new HttpGet(str2);
      int i = 0;
      try
      {
        i = new DefaultHttpClient().execute(localHttpGet).getStatusLine().getStatusCode();
        Logger.i("ReportService", paramReportMessage.getDesc() + " Report(GET) OK: " + i + " , url: " + str2);
        return;
        str1 = WebUrlBuilder.getReportUrl();
      }
      catch (Exception localException)
      {
        Logger.e("ReportService", paramReportMessage.getDesc() + " Report(GET) Exception: " + i + " , url: " + str2, localException);
      }
    }
  }

  private void reportByPostWithHttpPost(ReportMessage paramReportMessage)
  {
    String str1;
    if (paramReportMessage.getIsLiveReport())
      str1 = WebUrlBuilder.getLiveReportUrl();
    while ((TextUtils.isEmpty(str1)) && (paramReportMessage.getDelayReportEnable()))
      synchronized (this.mPendingMessage)
      {
        this.mPendingMessage.add(paramReportMessage);
        Logger.i("ReportService", "add pending message(" + paramReportMessage.getDesc() + ", reportUrl is empty, wait until init service is ok...");
        return;
        str1 = WebUrlBuilder.getReportUrl();
      }
    HttpPost localHttpPost = new HttpPost(str1);
    int i = 0;
    String str2 = paramReportMessage.mExtData.toString();
    int j = 0;
    while (true)
    {
      if (j > 0);
      try
      {
        Logger.w("ReportService", paramReportMessage.getDesc() + " Report(POST) retry: " + j + " , url: " + str1 + ", param : " + str2);
        localHttpPost.addHeader("Content-Type", "application/json");
        localHttpPost.addHeader("Content-Encoding", "utf-8");
        localHttpPost.setEntity(new StringEntity(str2, "UTF-8"));
        i = new DefaultHttpClient().execute(localHttpPost).getStatusLine().getStatusCode();
        Logger.i("ReportService", paramReportMessage.getDesc() + " Report(POST) OK: " + i + " , url: " + str1 + ", param : " + str2);
        if (i != 200)
        {
          j++;
          if (j < 3)
          {
            k = 1;
            if (k != 0)
              continue;
            return;
          }
        }
      }
      catch (Exception localException)
      {
        while (true)
        {
          localException.printStackTrace();
          Logger.e("ReportService", paramReportMessage.getDesc() + " Report(POST) Exception: " + i + " , url: " + str1, localException);
          continue;
          int k = 0;
          continue;
          k = 0;
        }
      }
    }
  }

  private void reportByPostWithHttpURLConnection(ReportMessage paramReportMessage)
  {
    String str1;
    if (paramReportMessage.getIsLiveReport())
      str1 = WebUrlBuilder.getLiveReportUrl();
    while ((TextUtils.isEmpty(str1)) && (paramReportMessage.getDelayReportEnable()))
      synchronized (this.mPendingMessage)
      {
        this.mPendingMessage.add(paramReportMessage);
        Logger.w("ReportService", "add pending message, reportUrl is empty, wait until init service is ok...");
        return;
        str1 = WebUrlBuilder.getReportUrl();
      }
    int i = 0;
    String str2 = paramReportMessage.mExtData.toString();
    byte[] arrayOfByte = str2.getBytes();
    int j = 0;
    while (true)
    {
      if (j > 0);
      try
      {
        Logger.w("ReportService", paramReportMessage.getDesc() + " Report(POST) retry: " + j + " , url: " + str1 + ", param : " + str2);
        HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(str1).openConnection();
        localHttpURLConnection.setConnectTimeout(10000);
        localHttpURLConnection.setDoOutput(true);
        localHttpURLConnection.setRequestMethod("POST");
        localHttpURLConnection.setUseCaches(false);
        localHttpURLConnection.setRequestProperty("Content-Type", "application/json");
        localHttpURLConnection.setRequestProperty("Connection", "Keep-Alive");
        localHttpURLConnection.setRequestProperty("Content-Encoding", "UTF-8");
        localHttpURLConnection.setFixedLengthStreamingMode(arrayOfByte.length);
        BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(localHttpURLConnection.getOutputStream());
        localBufferedOutputStream.write(arrayOfByte);
        localBufferedOutputStream.flush();
        localBufferedOutputStream.close();
        i = localHttpURLConnection.getResponseCode();
        Logger.i("ReportService", paramReportMessage.getDesc() + " Report(POST) OK: " + i + " , url: " + str1 + ", param : " + str2);
        if (i != 200)
        {
          j++;
          if (j < 3)
          {
            k = 1;
            if (k != 0)
              continue;
            return;
          }
        }
      }
      catch (Exception localException)
      {
        while (true)
        {
          Logger.e("ReportService", paramReportMessage.getDesc() + " Report(POST) Exception: " + i + " , url: " + str1, localException);
          continue;
          int k = 0;
          continue;
          k = 0;
        }
      }
    }
  }

  private void reportGet(ReportMessage paramReportMessage)
  {
    String str;
    HttpGet localHttpGet;
    int i;
    int j;
    if (paramReportMessage.mExtData != null)
    {
      str = paramReportMessage.mExtData.toString();
      localHttpGet = new HttpGet(str);
      i = 0;
      j = 0;
    }
    while (true)
    {
      if (j > 0);
      try
      {
        Logger.w("ReportService", paramReportMessage.getDesc() + " Report(GET) retry: " + j + " , url: " + str);
        i = new DefaultHttpClient().execute(localHttpGet).getStatusLine().getStatusCode();
        Logger.i("ReportService", paramReportMessage.getDesc() + " Report(GET) OK: " + i + " , url: " + str);
        k = 0;
        if (k == 0)
          return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        j++;
        if (j >= 3);
      }
    }
    for (int k = 1; ; k = 0)
    {
      Logger.e("ReportService", paramReportMessage.getDesc() + " Report(GET) Exception: " + i + " , url: " + str, localException);
      break;
    }
  }

  private void reportWrapper(Message paramMessage)
  {
    ReportMessage localReportMessage = (ReportMessage)paramMessage;
    if (localReportMessage.getReportType() == 2)
    {
      ReportMessageEntity localReportMessageEntity = new ReportMessageEntity();
      localReportMessageEntity.msgId = localReportMessage.getId();
      if (localReportMessage.mExtData != null)
        localReportMessageEntity.content = localReportMessage.mExtData.toString();
      localReportMessageEntity.isLiveReport = localReportMessage.getIsLiveReport();
      localReportMessageEntity.desc = localReportMessage.getDesc();
      String str = "";
      if (!TextUtils.isEmpty(localReportMessageEntity.desc))
        str = localReportMessageEntity.desc;
      if (TextUtils.isEmpty(localReportMessageEntity.content))
        Logger.e("ReportService", "report message content null, debug this! id: " + localReportMessageEntity.msgId + ", desc: " + str);
      do
      {
        return;
        Logger.d("ReportService", "prepare to report(" + localReportMessageEntity.msgId + ":" + str + ")" + ", content: " + localReportMessageEntity.content);
      }
      while ((AppFuncCfg.FUNCTION_REPORT_DIRECT) && (ReportExecutor.report(localReportMessageEntity)));
      ReportMessageManager.getInstance(this).report(localReportMessageEntity);
      return;
    }
    reportGet(localReportMessage);
  }

  public static void setReportListener(OnReportServiceOkListener paramOnReportServiceOkListener)
  {
    listener = paramOnReportServiceOkListener;
  }

  public IBinder onBind(Intent paramIntent)
  {
    return null;
  }

  public void onCreate()
  {
    Logger.i("ReportService", "ReportService onCreate");
    isAlive = true;
    MessageHandler.instance().register(Integer.valueOf(16384), this.mDataReporter);
    if (listener != null)
      listener.onReportServiceOk();
  }

  public void onDestroy()
  {
    MessageHandler.instance().unregister(this.mDataReporter);
    isAlive = false;
    Logger.i("ReportService", "ReportService onDestroy");
  }

  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    return 1;
  }

  public static abstract interface OnReportServiceOkListener
  {
    public abstract void onReportServiceOk();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.report.ReportService
 * JD-Core Version:    0.6.2
 */