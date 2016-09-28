package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class AppList
  implements Serializable
{
  public int currentPage;
  public ArrayList<Apps> lists = new ArrayList();
  public String reason = "";
  public String state = "";
  public int totalPage;
  public int totalRows;

  public String toString()
  {
    return "AppList{state='" + this.state + '\'' + ", reason='" + this.reason + '\'' + ", currentPage=" + this.currentPage + ", totalPage=" + this.totalPage + ", totalRows=" + this.totalRows + ", lists=" + this.lists + '}';
  }

  public class Apps
    implements Serializable
  {
    public String appId;
    public String downCount;
    public String icon;
    public String name;
    public String nameEn;
    public String namePinyin;
    public String packageName;
    public String publishTime;
    public String type;
    public String version;
    public String versionId;
    public String versionName;

    public Apps()
    {
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AppList
 * JD-Core Version:    0.6.2
 */