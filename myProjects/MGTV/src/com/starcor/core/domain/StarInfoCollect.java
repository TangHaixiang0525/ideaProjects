package com.starcor.core.domain;

import java.io.Serializable;
import java.util.List;

public class StarInfoCollect
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String reason = "";
  private List<StarInfo> starInfos = null;
  private String state = "";

  public void addStarInfo(StarInfo paramStarInfo)
  {
    this.starInfos.add(paramStarInfo);
  }

  public String getReason()
  {
    return this.reason;
  }

  public List<StarInfo> getStarInfo()
  {
    return this.starInfos;
  }

  public String getState()
  {
    return this.state;
  }

  public void setReason(String paramString)
  {
    this.reason = paramString;
  }

  public void setState(String paramString)
  {
    this.state = paramString;
  }

  public String toString()
  {
    return "StarInfo==>state=" + this.state + " reason=" + this.reason + " starInfos=" + this.starInfos;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.StarInfoCollect
 * JD-Core Version:    0.6.2
 */