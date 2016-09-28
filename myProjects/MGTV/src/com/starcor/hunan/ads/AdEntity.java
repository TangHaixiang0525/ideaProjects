package com.starcor.hunan.ads;

import java.util.ArrayList;

public class AdEntity
{
  String aid;
  ArrayList<Creative> creatives;

  public String getAid()
  {
    return this.aid;
  }

  public ArrayList<Creative> getCreatives()
  {
    return this.creatives;
  }

  public void setAid(String paramString)
  {
    this.aid = paramString;
  }

  public void setCreative(ArrayList<Creative> paramArrayList)
  {
    this.creatives = paramArrayList;
  }

  public String toString()
  {
    String str1 = "AdEntity:[" + "aid:" + this.aid;
    String str2 = str1 + ", creatives:" + this.creatives;
    return str2 + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.ads.AdEntity
 * JD-Core Version:    0.6.2
 */