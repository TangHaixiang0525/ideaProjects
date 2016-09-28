package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class ImageAd
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String action = "";
  private int alpha;
  private ArrayList<String> imageUrls;
  private int interval;
  private int scale;

  public String getAction()
  {
    return this.action;
  }

  public int getAlpha()
  {
    return this.alpha;
  }

  public ArrayList<String> getImageUrls()
  {
    return this.imageUrls;
  }

  public int getInterval()
  {
    return this.interval;
  }

  public int getScale()
  {
    return this.scale;
  }

  public void setAction(String paramString)
  {
    this.action = paramString;
  }

  public void setAlpha(int paramInt)
  {
    this.alpha = paramInt;
  }

  public void setImageUrl(ArrayList<String> paramArrayList)
  {
    this.imageUrls = paramArrayList;
  }

  public void setInterval(int paramInt)
  {
    this.interval = paramInt;
  }

  public void setScale(int paramInt)
  {
    this.scale = paramInt;
  }

  public String toString()
  {
    String str1 = "ImageAd [" + " alpha: " + this.alpha;
    String str2 = str1 + ", scale:" + this.scale;
    String str3 = str2 + ", action:" + this.action;
    String str4 = str3 + ", interval:" + this.interval;
    String str5 = str4 + ", imageUrls:" + this.imageUrls;
    return str5 + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.ImageAd
 * JD-Core Version:    0.6.2
 */