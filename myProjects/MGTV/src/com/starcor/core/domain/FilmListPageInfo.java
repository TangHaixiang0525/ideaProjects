package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class FilmListPageInfo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private int count_num;
  private ArrayList<VideoIndex> filmInfo;
  private String index_order = "";

  public int getCount_num()
  {
    return this.count_num;
  }

  public ArrayList<VideoIndex> getFilmInfo()
  {
    return this.filmInfo;
  }

  public String getIndex_order()
  {
    return this.index_order;
  }

  public void setCount_num(int paramInt)
  {
    this.count_num = paramInt;
  }

  public void setFilmInfo(ArrayList<VideoIndex> paramArrayList)
  {
    this.filmInfo = paramArrayList;
  }

  public void setIndex_order(String paramString)
  {
    this.index_order = paramString;
  }

  public String toString()
  {
    return "FilmListPageInfo [count_num=" + this.count_num + ", filmInfo=" + this.filmInfo + ", index_order=" + this.index_order + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.FilmListPageInfo
 * JD-Core Version:    0.6.2
 */