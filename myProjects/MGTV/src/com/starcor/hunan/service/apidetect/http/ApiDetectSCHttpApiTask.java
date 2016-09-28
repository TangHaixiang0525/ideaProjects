package com.starcor.hunan.service.apidetect.http;

import android.os.SystemClock;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;

public abstract class ApiDetectSCHttpApiTask
{
  public static final long StdCacheLife = 300000L;
  private long BodyOkTime = 0L;
  private long ConnectOkTime = 0L;
  private long ConnectStartTime = 0L;
  private long HeadOkTime = 0L;
  private long cacheLife = 0L;
  private long createTime = 0L;
  private long inQueueTime = 0L;
  private boolean isUiSafe = true;
  private boolean needToStop = false;
  private ApiDetectSCResponse rep;
  private int taskId;

  public long getCacheLife()
  {
    return this.cacheLife;
  }

  public boolean getIsUiSafe()
  {
    return this.isUiSafe;
  }

  public HttpEntity getPostBody()
  {
    return null;
  }

  public String getPostEncoding()
  {
    return "UTF-8";
  }

  public List<NameValuePair> getPostForm()
  {
    return null;
  }

  public List<Header> getReqHeaders()
  {
    return null;
  }

  public int getRequestMethod()
  {
    return 0;
  }

  public long getRunTotalTime()
  {
    if (this.BodyOkTime >= this.inQueueTime)
      return this.BodyOkTime - this.inQueueTime;
    return 0L;
  }

  public ApiDetectSCResponse getSCResponse()
  {
    return this.rep;
  }

  public int getTaskId()
  {
    return this.taskId;
  }

  public abstract String getTaskName();

  public abstract String getUrl();

  public boolean isAutoRedirect()
  {
    return true;
  }

  public boolean isNeedToStop()
  {
    return this.needToStop;
  }

  public void markBodyOkTime()
  {
    this.BodyOkTime = SystemClock.elapsedRealtime();
  }

  public void markConnectOkTime()
  {
    this.ConnectOkTime = SystemClock.elapsedRealtime();
  }

  public void markConnectStartTime()
  {
    this.ConnectStartTime = SystemClock.elapsedRealtime();
  }

  public void markCreateTime()
  {
    this.createTime = SystemClock.elapsedRealtime();
  }

  public void markHeadOkTime()
  {
    this.HeadOkTime = SystemClock.elapsedRealtime();
  }

  public void markInQueueTime()
  {
    this.inQueueTime = SystemClock.elapsedRealtime();
  }

  public abstract void perform(ApiDetectSCResponse paramApiDetectSCResponse);

  public long setCacheLife(long paramLong)
  {
    this.cacheLife = paramLong;
    return this.cacheLife;
  }

  public void setIsUiSafe(boolean paramBoolean)
  {
    this.isUiSafe = paramBoolean;
  }

  public void setSCResponse(ApiDetectSCResponse paramApiDetectSCResponse)
  {
    this.rep = paramApiDetectSCResponse;
  }

  public void setStop()
  {
    this.needToStop = true;
  }

  public void setTaskId(int paramInt)
  {
    this.taskId = paramInt;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.service.apidetect.http.ApiDetectSCHttpApiTask
 * JD-Core Version:    0.6.2
 */