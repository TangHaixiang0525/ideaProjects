package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class UiPackage
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String modify_time = "";
  public ArrayList<UiLayoutItem> ui_layout_list;
  public String ui_version = "";

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder().append("UiPackage [ui_version:").append(this.ui_version).append(", modify_time:").append(this.modify_time).append(",ui_layout_list：");
    if (this.ui_layout_list == null);
    for (String str = "(null)"; ; str = this.ui_layout_list.toString())
      return str + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.UiPackage
 * JD-Core Version:    0.6.2
 */