package com.starcor.core.domain;

import java.io.Serializable;

public class Skin
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String id = "";
  private String name = "";
  private String url = "";

  public String getId()
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

  public void setId(String paramString)
  {
    this.id = paramString;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setUrl(String paramString)
  {
    this.url = paramString;
  }

  public String toString()
  {
    return "Skin [id=" + this.id + ", name=" + this.name + ", url=" + this.url + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.Skin
 * JD-Core Version:    0.6.2
 */