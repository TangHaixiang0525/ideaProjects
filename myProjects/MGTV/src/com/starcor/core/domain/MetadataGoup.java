package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class MetadataGoup
  implements Cloneable, Serializable
{
  private static final long serialVersionUID = 1L;
  public ArrayList<MetadataGroupPageIndex> meta_index_list;
  public String type = "";

  public String toString()
  {
    return "MetadataGoup [type=" + this.type + ", meta_index_list=" + this.meta_index_list.toString() + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.MetadataGoup
 * JD-Core Version:    0.6.2
 */