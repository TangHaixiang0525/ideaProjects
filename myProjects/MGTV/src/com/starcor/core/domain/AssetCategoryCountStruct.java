package com.starcor.core.domain;

import java.io.Serializable;

public class AssetCategoryCountStruct
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String category_id = "";
  public int count;
  public String media_assets_id = "";

  public String toString()
  {
    return "AssetCategoryCountStruct [media_assets_id=" + this.media_assets_id + ", category_id=" + this.category_id + ", count=" + this.count + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AssetCategoryCountStruct
 * JD-Core Version:    0.6.2
 */