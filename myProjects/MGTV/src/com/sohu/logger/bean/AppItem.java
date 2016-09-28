package com.sohu.logger.bean;

import java.util.List;

public class AppItem
{
  private String apk_type;
  private String channel_id;
  List<AppInfo> mcclist;
  private String platform;
  private String pro;
  private String pro_form;
  private String pro_type;
  private String uid;

  public String getApk_type()
  {
    return this.apk_type;
  }

  public String getChannel_id()
  {
    return this.channel_id;
  }

  public List<AppInfo> getMcclist()
  {
    return this.mcclist;
  }

  public String getPlatform()
  {
    return this.platform;
  }

  public String getPro()
  {
    return this.pro;
  }

  public String getPro_form()
  {
    return this.pro_form;
  }

  public String getPro_type()
  {
    return this.pro_type;
  }

  public String getUid()
  {
    return this.uid;
  }

  public void setApk_type(String paramString)
  {
    this.apk_type = paramString;
  }

  public void setChannel_id(String paramString)
  {
    this.channel_id = paramString;
  }

  public void setMcclist(List<AppInfo> paramList)
  {
    this.mcclist = paramList;
  }

  public void setPlatform(String paramString)
  {
    this.platform = paramString;
  }

  public void setPro(String paramString)
  {
    this.pro = paramString;
  }

  public void setPro_form(String paramString)
  {
    this.pro_form = paramString;
  }

  public void setPro_type(String paramString)
  {
    this.pro_type = paramString;
  }

  public void setUid(String paramString)
  {
    this.uid = paramString;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.logger.bean.AppItem
 * JD-Core Version:    0.6.2
 */