package com.starcor.core.domain;

import java.io.Serializable;

public class AdvertisementPos
  implements Serializable
{
  private String id = "";
  private AdInfos info;
  private String name = "";

  public String getId()
  {
    return this.id;
  }

  public AdInfos getInfo()
  {
    return this.info;
  }

  public String getName()
  {
    return this.name;
  }

  public void setId(String paramString)
  {
    this.id = paramString;
  }

  public void setInfo(AdInfos paramAdInfos)
  {
    this.info = paramAdInfos;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public String toString()
  {
    String str1 = "AdvertisementPos:[" + "id:" + this.id;
    String str2 = str1 + "name:" + this.name;
    String str3 = str2 + "info:" + this.info;
    return str3 + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AdvertisementPos
 * JD-Core Version:    0.6.2
 */