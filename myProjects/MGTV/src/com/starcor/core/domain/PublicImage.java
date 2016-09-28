package com.starcor.core.domain;

import java.io.Serializable;
import java.util.List;

public class PublicImage
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String id = "";
  public String name = "";
  public List<String> url_list;

  public String toString()
  {
    return "PublicImage [id=" + this.id + ", name=" + this.name + ", url=" + this.url_list.toString() + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.PublicImage
 * JD-Core Version:    0.6.2
 */