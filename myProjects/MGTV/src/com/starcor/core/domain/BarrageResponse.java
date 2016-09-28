package com.starcor.core.domain;

import java.util.ArrayList;
import java.util.List;

public class BarrageResponse
{
  private List<BarrageMeta> barrageMetaList = new ArrayList();
  private String curTimeKey = "";
  private int errorCode;
  private String errorMsg = "";
  private int interval;
  private int totalCount;

  public void addBarrageMeta(BarrageMeta paramBarrageMeta)
  {
    if (paramBarrageMeta != null)
      this.barrageMetaList.add(paramBarrageMeta);
  }

  public List<BarrageMeta> getBarrageMetaList()
  {
    return this.barrageMetaList;
  }

  public String getCurTimeKey()
  {
    return this.curTimeKey;
  }

  public int getErrorCode()
  {
    return this.errorCode;
  }

  public String getErrorMsg()
  {
    return this.errorMsg;
  }

  public int getInterval()
  {
    return this.interval;
  }

  public int getTotalCount()
  {
    return this.totalCount;
  }

  public void setCurTimeKey(String paramString)
  {
    this.curTimeKey = paramString;
  }

  public void setErrorCode(int paramInt)
  {
    this.errorCode = paramInt;
  }

  public void setErrorMsg(String paramString)
  {
    this.errorMsg = paramString;
  }

  public void setInterval(int paramInt)
  {
    this.interval = paramInt;
  }

  public void setTotalCount(int paramInt)
  {
    this.totalCount = paramInt;
  }

  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer(50);
    localStringBuffer.append("BarrageResponse [");
    localStringBuffer.append("errCode: " + this.errorCode);
    localStringBuffer.append(", errMsg: " + this.errorMsg);
    localStringBuffer.append(", interval: " + this.interval);
    localStringBuffer.append(", curTimeKey: " + this.curTimeKey);
    localStringBuffer.append(", totalCount: " + this.totalCount);
    localStringBuffer.append("]");
    return localStringBuffer.toString();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.BarrageResponse
 * JD-Core Version:    0.6.2
 */