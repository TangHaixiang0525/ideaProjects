package com.starcor.core.domain;

import java.io.Serializable;

public class CityItem
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String id = "";
  private String name = "";
  private String pid = "";
  private String pinyin_pre = "";
  private String type = "";

  public String getId()
  {
    return this.id;
  }

  public String getName()
  {
    return this.name;
  }

  public String getPid()
  {
    return this.pid;
  }

  public String getPinyin_pre()
  {
    return this.pinyin_pre;
  }

  public String getType()
  {
    return this.type;
  }

  public void setId(String paramString)
  {
    this.id = paramString;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setPid(String paramString)
  {
    this.pid = paramString;
  }

  public void setPinyin_pre(String paramString)
  {
    this.pinyin_pre = paramString;
  }

  public void setType(String paramString)
  {
    this.type = paramString;
  }

  public String toString()
  {
    return "CityItem [id=" + this.id + ", pid=" + this.pid + ", name=" + this.name + ", pinyin_pre=" + this.pinyin_pre + ", type=" + this.type + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.CityItem
 * JD-Core Version:    0.6.2
 */