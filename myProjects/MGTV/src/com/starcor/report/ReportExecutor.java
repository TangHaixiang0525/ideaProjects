package com.starcor.report;

import android.content.Context;
import android.text.TextUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.hunan.domain.ReportMessageEntity;
import com.starcor.mgtv.boss.WebUrlBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;

public class ReportExecutor
{
  private static final int CONNECTION_TIMEOUT = 10000;
  private static final int READ_TIMEOUT = 10000;
  private static final String TAG = ReportExecutor.class.getSimpleName();
  private static ReportExecutor instance = null;
  private boolean isRunning = false;
  private Context mContext;
  private ReportMessageManager messageManager;

  private ReportExecutor(Context paramContext)
  {
    this.mContext = paramContext;
    this.messageManager = ReportMessageManager.getInstance(this.mContext);
    new Thread(new ReportTask(null)).start();
  }

  public static ReportExecutor getInstance()
  {
    if (instance == null)
      instance = new ReportExecutor(App.getInstance().getApplicationContext());
    return instance;
  }

  private ReportMessageEntity getPendingMessage()
  {
    return this.messageManager.peek();
  }

  public static boolean report(ReportMessageEntity paramReportMessageEntity)
  {
    if (TextUtils.isEmpty(paramReportMessageEntity.content))
      return true;
    String str1 = paramReportMessageEntity.content;
    String str2 = "";
    if (!TextUtils.isEmpty(paramReportMessageEntity.desc))
      str2 = paramReportMessageEntity.desc;
    String str3;
    boolean bool;
    StringBuilder localStringBuilder;
    if (paramReportMessageEntity.isLiveReport)
    {
      str3 = WebUrlBuilder.getLiveReportUrl();
      if (TextUtils.isEmpty(str3))
        break label154;
      bool = reportWithHttpURLConn(str3, str1);
      localStringBuilder = new StringBuilder().append("report(").append(paramReportMessageEntity.msgId).append(":").append(str2).append(") ");
      if (!bool)
        break label147;
    }
    label147: for (String str4 = "success"; ; str4 = "fail")
    {
      Logger.d("ReportService", str4 + ", reportUrl: " + str3 + ", content: " + str1);
      return bool;
      str3 = WebUrlBuilder.getReportUrl();
      break;
    }
    label154: Logger.e("ReportService", "reportUrl is null when report(" + paramReportMessageEntity.msgId + ":" + str2 + "), isLiveReport: " + paramReportMessageEntity.isLiveReport + ", content: " + str1);
    return false;
  }

  private boolean reportMessage(ReportMessageEntity paramReportMessageEntity)
  {
    boolean bool = report(paramReportMessageEntity);
    if (bool)
      this.messageManager.deleteReportMessageEntity(paramReportMessageEntity);
    return bool;
  }

  private static boolean reportWithHttpClient(String paramString1, String paramString2)
  {
    HttpPost localHttpPost = new HttpPost(paramString1);
    try
    {
      localHttpPost.addHeader("Content-Type", "application/json");
      localHttpPost.addHeader("Content-Encoding", "utf-8");
      localHttpPost.setEntity(new StringEntity(paramString2, "UTF-8"));
      DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
      localDefaultHttpClient.getParams().setParameter("http.connection.timeout", Integer.valueOf(10000));
      localDefaultHttpClient.getParams().setParameter("http.socket.timeout", Integer.valueOf(10000));
      int i = localDefaultHttpClient.execute(localHttpPost).getStatusLine().getStatusCode();
      boolean bool = false;
      if (200 == i)
        bool = true;
      return bool;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }

  private static boolean reportWithHttpURLConn(String paramString1, String paramString2)
  {
    boolean bool1 = TextUtils.isEmpty(paramString1);
    boolean bool2 = false;
    HttpURLConnection localHttpURLConnection;
    if (!bool1)
      localHttpURLConnection = null;
    try
    {
      localHttpURLConnection = (HttpURLConnection)new URL(paramString1).openConnection();
      localHttpURLConnection.setUseCaches(false);
      localHttpURLConnection.setDoOutput(true);
      localHttpURLConnection.setConnectTimeout(10000);
      localHttpURLConnection.setReadTimeout(10000);
      localHttpURLConnection.setRequestMethod("POST");
      localHttpURLConnection.setRequestProperty("Content-Type", "application/json");
      localHttpURLConnection.setRequestProperty("Connection", "Keep-Alive");
      localHttpURLConnection.setRequestProperty("Content-Encoding", "UTF-8");
      PrintWriter localPrintWriter = new PrintWriter(localHttpURLConnection.getOutputStream());
      localPrintWriter.print(paramString2);
      localPrintWriter.flush();
      localPrintWriter.close();
      int i = localHttpURLConnection.getResponseCode();
      bool2 = false;
      if (200 == i)
        bool2 = true;
      return bool2;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      localMalformedURLException.printStackTrace();
      bool2 = false;
      return false;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      bool2 = false;
      return false;
    }
    finally
    {
      if (localHttpURLConnection != null)
        localHttpURLConnection.disconnect();
    }
  }

  public void stop()
  {
    Logger.d(TAG, "stop...");
    this.isRunning = false;
  }

  private class ReportTask
    implements Runnable
  {
    private ReportTask()
    {
    }

    public void run()
    {
      while (ReportExecutor.this.isRunning)
      {
        ReportMessageEntity localReportMessageEntity = ReportExecutor.this.getPendingMessage();
        if (localReportMessageEntity == null)
        {
          try
          {
            Thread.sleep(1000L);
          }
          catch (InterruptedException localInterruptedException3)
          {
            localInterruptedException3.printStackTrace();
          }
        }
        else
        {
          while ((ReportExecutor.this.isRunning) && (!ReportExecutor.this.reportMessage(localReportMessageEntity)))
            try
            {
              Thread.sleep(3000L);
            }
            catch (InterruptedException localInterruptedException2)
            {
              localInterruptedException2.printStackTrace();
            }
          if (ReportExecutor.this.isRunning)
            try
            {
              Thread.sleep(1000L);
            }
            catch (InterruptedException localInterruptedException1)
            {
              localInterruptedException1.printStackTrace();
            }
        }
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.report.ReportExecutor
 * JD-Core Version:    0.6.2
 */