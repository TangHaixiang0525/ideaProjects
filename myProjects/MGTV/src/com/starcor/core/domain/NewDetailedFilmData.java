package com.starcor.core.domain;

import java.io.Serializable;

public class NewDetailedFilmData
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String bad = "";
  private String dayClicks = "";
  private String good = "";
  private String monthClicks = "";
  private float score = 0.0F;
  private String totalClicks = "";
  private String type = "";
  private String videoId = "";
  private String weekClicks = "";

  public String getBad()
  {
    return this.bad;
  }

  public String getDayClicks()
  {
    return this.dayClicks;
  }

  public String getGood()
  {
    return this.good;
  }

  public String getMonthClicks()
  {
    return this.monthClicks;
  }

  public float getScore()
  {
    return this.score;
  }

  public String getTotalClicks()
  {
    return this.totalClicks;
  }

  public String getType()
  {
    return this.type;
  }

  public String getVideoId()
  {
    return this.videoId;
  }

  public String getWeekClicks()
  {
    return this.weekClicks;
  }

  public void setBad(String paramString)
  {
    this.bad = paramString;
  }

  public void setDayClicks(String paramString)
  {
    this.dayClicks = paramString;
  }

  public void setGood(String paramString)
  {
    this.good = paramString;
  }

  public void setMonthClicks(String paramString)
  {
    this.monthClicks = paramString;
  }

  public void setScore(float paramFloat)
  {
    this.score = paramFloat;
  }

  public void setTotalClicks(String paramString)
  {
    this.totalClicks = paramString;
  }

  public void setType(String paramString)
  {
    this.type = paramString;
  }

  public void setVideoId(String paramString)
  {
    this.videoId = paramString;
  }

  public void setWeekClicks(String paramString)
  {
    this.weekClicks = paramString;
  }

  public String toString()
  {
    return "NewDetailedFilmData==>type=" + this.type + " videoId=" + this.videoId + " score=" + this.score + " good=" + this.good + " bad=" + this.bad + " totalClicks=" + this.totalClicks + " monthClicks=" + this.monthClicks + " weekClicks=" + this.weekClicks + " dayClicks=" + this.dayClicks;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.NewDetailedFilmData
 * JD-Core Version:    0.6.2
 */