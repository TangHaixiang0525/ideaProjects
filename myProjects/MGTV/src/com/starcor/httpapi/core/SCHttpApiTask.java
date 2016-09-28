package com.starcor.httpapi.core;

import android.os.SystemClock;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;

public abstract class SCHttpApiTask
{
  public static final long StdCacheLife = 1800000L;
  private long BodyOkTime = 0L;
  private long ConnectOkTime = 0L;
  private long ConnectStartTime = 0L;
  private long HeadOkTime = 0L;
  private long cacheLife = 0L;
  private long createTime = 0L;
  private boolean forceUpdate = false;
  private long inQueueTime = 0L;
  private boolean isCanceled = false;
  private boolean isUiSafe = true;
  private boolean justCache = false;
  private boolean needToStop = false;
  private int priority = 999;
  private String realTaskUrl = null;
  private SCResponse rep;
  private int taskId;

  public long getCacheLife()
  {
    return this.cacheLife;
  }

  public int getConnectTimeOutMs()
  {
    return 5000;
  }

  public boolean getIsUiSafe()
  {
    return this.isUiSafe;
  }

  public boolean getJustCache()
  {
    return this.justCache;
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

  public String getRealUrl()
  {
    if (this.realTaskUrl != null)
      return this.realTaskUrl;
    this.realTaskUrl = getUrl();
    return this.realTaskUrl;
  }

  public List<Header> getReqHeaders()
  {
    return null;
  }

  public int getRequestMethod()
  {
    return 0;
  }

  public int getRetryTimes()
  {
    return 3;
  }

  public long getRunTotalTime()
  {
    if (this.BodyOkTime >= this.inQueueTime)
      return this.BodyOkTime - this.inQueueTime;
    return 0L;
  }

  public SCResponse getSCResponse()
  {
    return this.rep;
  }

  public int getSoTimeOutMs()
  {
    return 10000;
  }

  public int getTaskId()
  {
    return this.taskId;
  }

  public abstract String getTaskName();

  public int getTaskPrioroty()
  {
    return this.priority;
  }

  public abstract String getUrl();

  public boolean isAutoRedirect()
  {
    return true;
  }

  public boolean isCanceled()
  {
    return this.isCanceled;
  }

  public boolean isForceUpdate()
  {
    return this.forceUpdate;
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

  public abstract void perform(SCResponse paramSCResponse);

  public long setCacheLife(long paramLong)
  {
    this.cacheLife = paramLong;
    return this.cacheLife;
  }

  public void setCanceled(boolean paramBoolean)
  {
    this.isCanceled = paramBoolean;
  }

  public void setForceUpdate(boolean paramBoolean)
  {
    this.forceUpdate = paramBoolean;
  }

  public void setIsUiSafe(boolean paramBoolean)
  {
    this.isUiSafe = paramBoolean;
  }

  public void setJustCache(boolean paramBoolean)
  {
    this.justCache = paramBoolean;
  }

  public void setSCResponse(SCResponse paramSCResponse)
  {
    this.rep = paramSCResponse;
  }

  public void setStop()
  {
    this.needToStop = true;
  }

  public void setTaskId(int paramInt)
  {
    this.taskId = paramInt;
  }

  public void setTaskPriority(int paramInt)
  {
    this.priority = paramInt;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.httpapi.core.SCHttpApiTask
 * JD-Core Version:    0.6.2
 */