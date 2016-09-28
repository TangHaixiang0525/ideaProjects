package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class CategoryIndexList
  implements Serializable
{
  public int currentPage;
  public ArrayList<Item> items = new ArrayList();
  public String reason = "";
  public String state = "";
  public int totalPage;
  public int totalRows;

  public String toString()
  {
    return "CategoryIndexList{state='" + this.state + '\'' + ", reason='" + this.reason + '\'' + ", currentPage=" + this.currentPage + ", totalPage=" + this.totalPage + ", totalRows=" + this.totalRows + ", items=" + this.items + '}';
  }

  public class Item
  {
    public String actor = "";
    public String aliasName = "";
    public String id = "";
    public String imgH = "";
    public String imgS = "";
    public String imgV = "";
    public String name = "";
    public String releaseTime = "";
    public String type = "";
    public String videoId = "";
    public String videoIndex = "";
    public String videoType = "";
    public String watchFocus = "";

    public Item()
    {
    }

    public String toString()
    {
      return "Item{type='" + this.type + '\'' + ", id='" + this.id + '\'' + ", name='" + this.name + '\'' + ", aliasName='" + this.aliasName + '\'' + ", imgH='" + this.imgH + '\'' + ", imgS='" + this.imgS + '\'' + ", imgV='" + this.imgV + '\'' + ", videoId='" + this.videoId + '\'' + ", videoType='" + this.videoType + '\'' + ", videoIndex='" + this.videoIndex + '\'' + ", actor='" + this.actor + '\'' + ", watchFocus='" + this.watchFocus + '\'' + ", releaseTime='" + this.releaseTime + '\'' + '}';
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.CategoryIndexList
 * JD-Core Version:    0.6.2
 */