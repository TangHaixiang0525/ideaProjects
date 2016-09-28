package com.starcor.core.domain;

import java.io.Serializable;
import java.util.List;

public class PlayAuthorization
  implements Serializable
{
  public String description;
  public String returncode;
  public List<Urls> urls;

  public String toString()
  {
    return "PlayAuthorization{description='" + this.description + '\'' + ", urls=" + this.urls + ", returncode='" + this.returncode + '\'' + '}';
  }

  public static class Urls
  {
    public String cp;
    public String mediacode;
    public String playurl;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.PlayAuthorization
 * JD-Core Version:    0.6.2
 */