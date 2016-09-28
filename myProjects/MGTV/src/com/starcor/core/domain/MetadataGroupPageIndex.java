package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class MetadataGroupPageIndex
  implements Cloneable, Serializable
{
  private static final long serialVersionUID = 1L;
  public ArrayList<MetadataInfo> meta_item_list;
  public int page_index;

  public String toString()
  {
    return "MetadataGroupPageIndex [page_index=" + this.page_index + ", meta_item_list=" + this.meta_item_list.toString() + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.MetadataGroupPageIndex
 * JD-Core Version:    0.6.2
 */