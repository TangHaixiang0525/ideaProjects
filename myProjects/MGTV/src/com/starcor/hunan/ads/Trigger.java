package com.starcor.hunan.ads;

import java.util.ArrayList;

public class Trigger
{
  String action;
  ArrayList<Integer> ads;
  String content;
  String id;

  public String getAction()
  {
    return this.action;
  }

  public ArrayList<Integer> getAds()
  {
    return this.ads;
  }

  public String getContent()
  {
    return this.content;
  }

  public String getId()
  {
    return this.id;
  }

  public void setAction(String paramString)
  {
    this.action = paramString;
  }

  public void setAds(ArrayList<Integer> paramArrayList)
  {
    this.ads = paramArrayList;
  }

  public void setContent(String paramString)
  {
    this.content = paramString;
  }

  public void setId(String paramString)
  {
    this.id = paramString;
  }

  public String toString()
  {
    String str1 = "Trigger:[" + "id:" + this.id;
    String str2 = str1 + ", action:" + this.action;
    String str3 = str2 + ", content:" + this.content;
    String str4 = str3 + ", ads:" + this.ads;
    return str4 + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.ads.Trigger
 * JD-Core Version:    0.6.2
 */