package com.starcor.core.sax;

import com.starcor.core.domain.AdInfos;
import com.starcor.core.domain.AdPosEntity;
import com.starcor.core.domain.AnimationAd;
import com.starcor.core.domain.ImageAd;
import com.starcor.core.domain.TextAd;
import com.starcor.core.domain.Video;
import com.starcor.core.domain.VideoAd;
import com.starcor.core.utils.Logger;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetAdInfoByadPosListHandler extends DefaultHandler
{
  private ArrayList<AdPosEntity> adInfoList;
  private AdPosEntity adPos;
  private AnimationAd animationAds;
  private ArrayList<String> animations;
  private ArrayList<String> imageUrls;
  private ImageAd imagesAds;
  private AdInfos infos;
  private TextAd textAds;
  private ArrayList<String> texts;
  private VideoAd videoAds;
  private ArrayList<Video> videos;

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    Logger.d("TAG", paramString2);
    if ("ad_pos".equalsIgnoreCase(paramString2))
    {
      Logger.d("TAG", this.adPos.toString());
      this.adInfoList.add(this.adPos);
    }
    if ("ad_info".equalsIgnoreCase(paramString2))
    {
      Logger.d("TAG", this.infos.toString());
      this.adPos.setInfo(this.infos);
    }
    if ("video_list".equalsIgnoreCase(paramString2))
    {
      this.videoAds.setVideos(this.videos);
      Logger.d("TAG", this.videoAds.toString());
      this.infos.setVideoAdList(this.videoAds);
    }
    if ("image_list".equalsIgnoreCase(paramString2))
    {
      this.imagesAds.setImageUrl(this.imageUrls);
      Logger.d("TAG", this.imagesAds.toString());
      this.infos.setIamgeAdList(this.imagesAds);
    }
    if ("text_list".equalsIgnoreCase(paramString2))
    {
      this.textAds.setTexts(this.texts);
      Logger.d("TAG", this.textAds.toString());
      this.infos.setTextAdList(this.textAds);
    }
    if ("animate_list".equalsIgnoreCase(paramString2))
    {
      this.animationAds.setUrls(this.animations);
      Logger.d("TAG", this.animationAds.toString());
      this.infos.setAnimationList(this.animationAds);
    }
  }

  public ArrayList<AdPosEntity> getAdInfoList()
  {
    return this.adInfoList;
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    super.startElement(paramString1, paramString2, paramString3, paramAttributes);
    if ("ad_pos_list".equalsIgnoreCase(paramString2))
      this.adInfoList = new ArrayList();
    if ("ad_pos".equalsIgnoreCase(paramString2))
    {
      this.adPos = new AdPosEntity();
      this.adPos.setId(paramAttributes.getValue("id"));
      this.adPos.setName(paramAttributes.getValue("name"));
    }
    if ("ad_info".equalsIgnoreCase(paramString2))
    {
      this.infos = new AdInfos();
      this.infos.setId(paramAttributes.getValue("id"));
      this.infos.setName(paramAttributes.getValue("name"));
    }
    if ("video_list".equalsIgnoreCase(paramString2))
    {
      this.videoAds = new VideoAd();
      this.videos = new ArrayList();
    }
    String str4;
    if ("video".equalsIgnoreCase(paramString2))
      str4 = paramAttributes.getValue("video_id");
    try
    {
      int j = Integer.valueOf(paramAttributes.getValue("video_type")).intValue();
      i = j;
      Video localVideo = new Video(str4, i);
      this.videos.add(localVideo);
      if ("image_list".equalsIgnoreCase(paramString2))
      {
        this.imagesAds = new ImageAd();
        this.imageUrls = new ArrayList();
        this.imagesAds.setAction(paramAttributes.getValue("action"));
      }
      try
      {
        this.imagesAds.setInterval(Integer.valueOf(paramAttributes.getValue("interval")).intValue());
        if ("image".equalsIgnoreCase(paramString2))
        {
          String str3 = paramAttributes.getValue("url");
          this.imageUrls.add(str3);
        }
        if ("text_list".equalsIgnoreCase(paramString2))
        {
          this.textAds = new TextAd();
          this.texts = new ArrayList();
          this.textAds.setAction(paramAttributes.getValue("action"));
        }
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        try
        {
          this.textAds.setInterval(Integer.valueOf(paramAttributes.getValue("interval")).intValue());
          if ("text".equalsIgnoreCase(paramString2))
          {
            String str2 = paramAttributes.getValue("data");
            this.texts.add(str2);
          }
          if ("animate_list".equalsIgnoreCase(paramString2))
          {
            this.animationAds = new AnimationAd();
            this.animations = new ArrayList();
            this.animationAds.setAction(paramAttributes.getValue("action"));
          }
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          try
          {
            while (true)
            {
              this.animationAds.setInterval(Integer.valueOf(paramAttributes.getValue("interval")).intValue());
              if ("animate".equalsIgnoreCase(paramString2))
              {
                String str1 = paramAttributes.getValue("url");
                this.animations.add(str1);
              }
              return;
              localNumberFormatException3 = localNumberFormatException3;
              this.imagesAds.setInterval(0);
            }
            localNumberFormatException2 = localNumberFormatException2;
            this.textAds.setInterval(0);
          }
          catch (NumberFormatException localNumberFormatException1)
          {
            while (true)
              this.animationAds.setInterval(0);
          }
        }
      }
    }
    catch (NumberFormatException localNumberFormatException4)
    {
      while (true)
        int i = 0;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetAdInfoByadPosListHandler
 * JD-Core Version:    0.6.2
 */