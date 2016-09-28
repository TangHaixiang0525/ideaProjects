package com.starcor.core.domain;

import java.io.Serializable;

public class UiUpdatePackage
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public int force_update;
  public String modify_time = "";
  public String ui_version = "";
  public int update;

  public String toString()
  {
    return "UiUpdatePackage[ui_version:" + this.ui_version + ", modify_time:" + this.modify_time + ", update:" + this.update + ", force_update:" + this.force_update + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.UiUpdatePackage
 * JD-Core Version:    0.6.2
 */