package com.starcor.hunan.ads;

import java.util.ArrayList;

public class Creative
{
  String adformat;
  String cid;
  ArrayList<String> click;
  int duration;
  ArrayList<String> impression;
  ArrayList<MediaFile> mediaFiles;

  public String getAdformat()
  {
    return this.adformat;
  }

  public String getCid()
  {
    return this.cid;
  }

  public ArrayList<String> getClick()
  {
    return this.click;
  }

  public int getDuration()
  {
    return this.duration;
  }

  public ArrayList<String> getImpression()
  {
    return this.impression;
  }

  public ArrayList<MediaFile> getMediaFiles()
  {
    return this.mediaFiles;
  }

  public void setAdformat(String paramString)
  {
    this.adformat = paramString;
  }

  public void setCid(String paramString)
  {
    this.cid = paramString;
  }

  public void setClick(ArrayList<String> paramArrayList)
  {
    this.click = paramArrayList;
  }

  public void setDuration(int paramInt)
  {
    this.duration = paramInt;
  }

  public void setImpression(ArrayList<String> paramArrayList)
  {
    this.impression = paramArrayList;
  }

  public void setMediaFiles(ArrayList<MediaFile> paramArrayList)
  {
    this.mediaFiles = paramArrayList;
  }

  public String toString()
  {
    String str1 = "Creative:[" + "cid:" + this.cid;
    String str2 = str1 + ",adformat:" + this.adformat;
    String str3 = str2 + ",duration:" + this.duration;
    String str4 = str3 + ",mediaFiles:" + this.mediaFiles;
    String str5 = str4 + ",impression:" + this.impression;
    String str6 = str5 + ",click:" + this.click;
    return str6 + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.ads.Creative
 * JD-Core Version:    0.6.2
 */