package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;

public class AdInfos
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private AnimationAd animationList;
  private ImageAd iamgeAdList;
  private String id = "";
  private String name = "";
  private TextAd textAdList;
  private VideoAd videoAdList;

  public AnimationAd getAnimationList()
  {
    return this.animationList;
  }

  public ImageAd getIamgeAdList()
  {
    return this.iamgeAdList;
  }

  public String getId()
  {
    return this.id;
  }

  public String getName()
  {
    return this.name;
  }

  public TextAd getTextAdList()
  {
    return this.textAdList;
  }

  public VideoAd getVideoAdList()
  {
    return this.videoAdList;
  }

  public boolean hasAnimAdvert()
  {
    return (this.animationList != null) && (this.animationList.getUrls() != null) && (this.animationList.getUrls().size() > 0);
  }

  public boolean hasImageAdvert()
  {
    return (this.iamgeAdList != null) && (this.iamgeAdList.getImageUrls() != null) && (this.iamgeAdList.getImageUrls().size() > 0);
  }

  public boolean hasTextAdvert()
  {
    return (this.textAdList != null) && (this.textAdList.getTexts() != null) && (this.textAdList.getTexts().size() > 0);
  }

  public boolean hasVideoAdvert()
  {
    return (this.videoAdList != null) && (this.videoAdList.getVideos() != null) && (this.videoAdList.getVideos().size() > 0);
  }

  public void setAnimationList(AnimationAd paramAnimationAd)
  {
    this.animationList = paramAnimationAd;
  }

  public void setIamgeAdList(ImageAd paramImageAd)
  {
    this.iamgeAdList = paramImageAd;
  }

  public void setId(String paramString)
  {
    this.id = paramString;
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }

  public void setTextAdList(TextAd paramTextAd)
  {
    this.textAdList = paramTextAd;
  }

  public void setVideoAdList(VideoAd paramVideoAd)
  {
    this.videoAdList = paramVideoAd;
  }

  public String toString()
  {
    String str1 = "AdInfos: [" + "id:" + this.id;
    String str2 = str1 + ", name:" + this.name;
    String str3 = str2 + ", videoAdList:" + this.videoAdList;
    String str4 = str3 + ", iamgeAdList:" + this.iamgeAdList;
    String str5 = str4 + ", textAdList:" + this.textAdList;
    String str6 = str5 + ", animationList:" + this.animationList;
    return str6 + " ]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.AdInfos
 * JD-Core Version:    0.6.2
 */