package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class FilmItem
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String artithmeticId = "";
  public String estimateId = "";
  public ArrayList<FilmListItem> filmList;
  public int film_num;
  public int page_index;
  public int page_total;

  public String toString()
  {
    return "film_num=" + this.film_num + "page_index=" + this.page_index + "page_total=" + this.page_total + "estimateId=" + this.estimateId + "artithmeticId=" + this.artithmeticId + "filmList" + this.filmList.toString();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.FilmItem
 * JD-Core Version:    0.6.2
 */