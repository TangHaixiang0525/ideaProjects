package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class PreInstallList
  implements Serializable
{
  public ArrayList<Apps> lists = new ArrayList();
  public String reason;
  public String state;

  public String toString()
  {
    return "PreInstallList{state='" + this.state + '\'' + ", reason='" + this.reason + '\'' + ", lists=" + this.lists + '}';
  }

  public class Apps
    implements Serializable
  {
    public String id;
    public String packageName;
    public String type;
    public String version;
    public String versionId;

    public Apps()
    {
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.PreInstallList
 * JD-Core Version:    0.6.2
 */