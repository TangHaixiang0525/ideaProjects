package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class EPGMetaInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public ArrayList<Serializable> metaList = new ArrayList();
  public ArrayList<MetadataGoup> meta_group_list = new ArrayList();

  public String toString()
  {
    return "EPGMetaInfo [metaList=" + this.metaList + ", meta_group_list=" + this.meta_group_list + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.EPGMetaInfo
 * JD-Core Version:    0.6.2
 */