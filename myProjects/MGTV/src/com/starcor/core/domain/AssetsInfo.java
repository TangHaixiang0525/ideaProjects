package com.starcor.core.domain;

import java.io.Serializable;

public class AssetsInfo
  implements Serializable
{
  public String categoryId = "";
  public String packageId = "";

  public String toString()
  {
    return "AssetsInfo[packageId=" + this.packageId + ", categoryId= " + this.categoryId + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AssetsInfo
 * JD-Core Version:    0.6.2
 */