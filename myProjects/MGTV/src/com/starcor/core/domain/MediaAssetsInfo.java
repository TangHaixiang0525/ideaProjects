package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class MediaAssetsInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public ArrayList<CategoryItem> cList;
  public String category_id = "";
  public String package_id = "";
  public String package_img = "";
  public String package_name = "";

  public String toString()
  {
    return "CategoryInfo [package_id=" + this.package_id + ", package_name=" + this.package_name + ", cList=" + this.cList + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.MediaAssetsInfo
 * JD-Core Version:    0.6.2
 */