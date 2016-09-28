package com.sohutv.tv.player.entity;

import java.io.Serializable;

public class PlayUrl
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private int id;
  private String name;
  private String url;
  private String url_h265;
  private long vid;
  private long vid_h2265;

  public int getId()
  {
    return this.id;
  }

  public String getName()
  {
    return this.name;
  }

  public String getUrl()
  {
    return this.url;
  }

  public String getUrl_h265()
  {
    return this.url_h265;
  }

  public long getVid()
  {
    return this.vid;
  }

  public long getVid_h2265()
  {
    return this.vid_h2265;
  }

  public void setId(int paramInt)
  {
    this.id = paramInt;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setUrl(String paramString)
  {
    this.url = paramString;
  }

  public void setVid(long paramLong)
  {
    this.vid = paramLong;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohutv.tv.player.entity.PlayUrl
 * JD-Core Version:    0.6.2
 */