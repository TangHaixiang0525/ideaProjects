package com.starcor.core.domain;

import java.io.Serializable;

public class AppVersion
  implements Serializable
{
  public static final int STATE_UPDATE = 0;
  public static final String TYPE_FORCE = "force";
  public static final String TYPE_MANUAL = "manual";
  private static final long serialVersionUID = 1L;
  public String desc = "";
  public int state;
  public String type = "";
  public String update_time = "";
  public String url = "";
  public String url_backup = "";
  public String ver = "";

  public String toString()
  {
    return "AppVersion [state=" + this.state + ", ver=" + this.ver + ", url=" + this.url + ", url_backup=" + this.url_backup + ", type=" + this.type + ", update_time=" + this.update_time + this.url_backup + ", desc=" + this.desc + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AppVersion
 * JD-Core Version:    0.6.2
 */