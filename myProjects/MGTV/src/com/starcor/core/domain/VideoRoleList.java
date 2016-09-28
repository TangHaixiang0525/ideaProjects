package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class VideoRoleList
  implements Serializable, Cloneable
{
  private static final long serialVersionUID = 1L;
  public ArrayList<VideoRoleInfo> actorList;
  public ArrayList<VideoRoleInfo> directorList;

  public String toString()
  {
    return "VideoRoleList: actorList=" + this.actorList + ", directorList=" + this.directorList;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.VideoRoleList
 * JD-Core Version:    0.6.2
 */