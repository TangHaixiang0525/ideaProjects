package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class HotAppList
  implements Serializable
{
  public ArrayList<Apps> lists = new ArrayList();
  public String reason = "";
  public String state = "";

  public String toString()
  {
    return "HotAppList{state='" + this.state + '\'' + ", reason='" + this.reason + '\'' + ", lists=" + this.lists + '}';
  }

  public class Apps
    implements Serializable
  {
    public String downCount;
    public String icon;
    public String id;
    public String name;
    public String nameEn;
    public String namePinyin;
    public String packageName;
    public String publishTime;
    public String type;
    public String version;
    public String versionName;

    public Apps()
    {
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.HotAppList
 * JD-Core Version:    0.6.2
 */