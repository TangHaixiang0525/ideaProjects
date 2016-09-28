package com.starcor.core.domain;

import java.io.Serializable;

public class StarInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String alias_name = "";
  private String en_name = "";
  private String id = "";
  private String img_h = "";
  private String img_s = "";
  private String img_v = "";
  private String info = "";
  private String label_id = "";
  private String label_id_v2 = "";
  private String name = "";
  private String old_name = "";
  private String sex = "";
  private String star_type = "";
  private String type = "";

  public String getAliasName()
  {
    return this.alias_name;
  }

  public String getEnName()
  {
    return this.en_name;
  }

  public String getId()
  {
    return this.id;
  }

  public String getImgH()
  {
    return this.img_h;
  }

  public String getImgS()
  {
    return this.img_s;
  }

  public String getImgV()
  {
    return this.img_v;
  }

  public String getInfo()
  {
    return this.info;
  }

  public String getLabelId()
  {
    return this.label_id;
  }

  public String getLabelIdV2()
  {
    return this.label_id_v2;
  }

  public String getName()
  {
    return this.name;
  }

  public String getOldName()
  {
    return this.old_name;
  }

  public String getSex()
  {
    return this.sex;
  }

  public String getStarType()
  {
    return this.star_type;
  }

  public String getType()
  {
    return this.type;
  }

  public void setAliasName(String paramString)
  {
    this.alias_name = paramString;
  }

  public void setEnName(String paramString)
  {
    this.en_name = paramString;
  }

  public void setId(String paramString)
  {
    this.id = paramString;
  }

  public void setImgH(String paramString)
  {
    this.img_h = paramString;
  }

  public void setImgS(String paramString)
  {
    this.img_s = paramString;
  }

  public void setImgV(String paramString)
  {
    this.img_v = paramString;
  }

  public void setInfo(String paramString)
  {
    this.info = paramString;
  }

  public void setLabelId(String paramString)
  {
    this.label_id = paramString;
  }

  public void setLabelIdV2(String paramString)
  {
    this.label_id_v2 = paramString;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setOldName(String paramString)
  {
    this.old_name = paramString;
  }

  public void setSex(String paramString)
  {
    this.sex = paramString;
  }

  public void setStarType(String paramString)
  {
    this.star_type = paramString;
  }

  public void setType(String paramString)
  {
    this.type = paramString;
  }

  public String toString()
  {
    return "StarInfo==> type=" + this.type + " id=" + this.id + " name=" + this.name + " alias_name=" + this.alias_name + " img_h=" + this.img_h + " img_s=" + this.img_s + " img_v=" + this.img_v + " info=" + this.info + " label_id=" + this.label_id + " sex=" + this.sex + " old_name=" + this.old_name + " en_name=" + this.en_name;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.StarInfo
 * JD-Core Version:    0.6.2
 */