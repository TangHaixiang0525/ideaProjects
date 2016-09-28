package com.starcor.core.domain;

import java.io.Serializable;

public class CityItemById
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String id = "";
  private String level = "";
  private String name = "";
  private String type = "";

  public String getId()
  {
    return this.id;
  }

  public String getLevel()
  {
    return this.level;
  }

  public String getName()
  {
    return this.name;
  }

  public String getType()
  {
    return this.type;
  }

  public void setId(String paramString)
  {
    this.id = paramString;
  }

  public void setLevel(String paramString)
  {
    this.level = paramString;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setType(String paramString)
  {
    this.type = paramString;
  }

  public String toString()
  {
    return "CityItemById [level=" + this.level + ", id=" + this.id + ", name=" + this.name + ", type=" + this.type + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.CityItemById
 * JD-Core Version:    0.6.2
 */