package com.starcor.core.domain;

import java.io.Serializable;

public class AdvertisementPosInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private int aliveTime;
  private int beginTime;
  private String event = "";
  private float hPercent;
  private int hPosition;
  private String id = "";
  private String name = "";
  private float wPercent;
  private int wPosition;
  private float xPercent;
  private int xPosition;
  private float yPercent;
  private int yPosition;

  public int getAliveTime()
  {
    return this.aliveTime;
  }

  public int getBeginTime()
  {
    return this.beginTime;
  }

  public String getEvent()
  {
    return this.event;
  }

  public String getId()
  {
    return this.id;
  }

  public String getName()
  {
    return this.name;
  }

  public float gethPercent()
  {
    return this.hPercent;
  }

  public float gethPosition()
  {
    return this.hPosition;
  }

  public float getwPercent()
  {
    return this.wPercent;
  }

  public float getwPosition()
  {
    return this.wPosition;
  }

  public float getxPercent()
  {
    return this.xPercent;
  }

  public float getxPosition()
  {
    return this.xPosition;
  }

  public float getyPercent()
  {
    return this.yPercent;
  }

  public int getyPosition()
  {
    return this.yPosition;
  }

  public void setAliveTime(int paramInt)
  {
    this.aliveTime = paramInt;
  }

  public void setBeginTime(int paramInt)
  {
    this.beginTime = paramInt;
  }

  public void setEvent(String paramString)
  {
    this.event = paramString;
  }

  public void setId(String paramString)
  {
    this.id = paramString;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void sethPercent(float paramFloat)
  {
    this.hPercent = paramFloat;
  }

  public void sethPosition(int paramInt)
  {
    this.hPosition = paramInt;
  }

  public void setwPercent(float paramFloat)
  {
    this.wPercent = paramFloat;
  }

  public void setwPosition(int paramInt)
  {
    this.wPosition = paramInt;
  }

  public void setxPercent(float paramFloat)
  {
    this.xPercent = paramFloat;
  }

  public void setxPosition(int paramInt)
  {
    this.xPosition = paramInt;
  }

  public void setyPercent(float paramFloat)
  {
    this.yPercent = paramFloat;
  }

  public void setyPosition(int paramInt)
  {
    this.yPosition = paramInt;
  }

  public String toString()
  {
    String str1 = "AdvertisementInfo: [" + "id:" + this.id;
    String str2 = str1 + ", name:" + this.name;
    String str3 = str2 + ", event:" + this.event;
    String str4 = str3 + ", xPercent:" + this.xPercent;
    String str5 = str4 + ", yPercent:" + this.yPercent;
    String str6 = str5 + ", wPercent:" + this.wPercent;
    String str7 = str6 + ", hPercent:" + this.hPercent;
    String str8 = str7 + ", xPosition:" + this.xPosition;
    String str9 = str8 + ", yPosition:" + this.yPosition;
    String str10 = str9 + ", wPosition:" + this.wPosition;
    String str11 = str10 + ", hPosition:" + this.hPosition;
    String str12 = str11 + ", beginTime:" + this.beginTime;
    String str13 = str12 + ", aliveTime:" + this.aliveTime;
    return str13 + " ]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AdvertisementPosInfo
 * JD-Core Version:    0.6.2
 */