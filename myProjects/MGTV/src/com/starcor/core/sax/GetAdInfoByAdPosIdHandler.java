package com.starcor.core.sax;

import com.starcor.core.domain.AdInfos;
import com.starcor.core.domain.AnimationAd;
import com.starcor.core.domain.ImageAd;
import com.starcor.core.domain.TextAd;
import com.starcor.core.domain.Video;
import com.starcor.core.domain.VideoAd;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetAdInfoByAdPosIdHandler extends DefaultHandler
{
  private AnimationAd animationAds;
  private ArrayList<String> animations;
  private ArrayList<String> imageUrls;
  private ImageAd imagesAds;
  private AdInfos infos;
  private TextAd textAds;
  private ArrayList<String> texts;
  private VideoAd videoAds;
  private ArrayList<Video> videos;

  public void endDocument()
    throws SAXException
  {
    if (this.animationAds != null)
      this.animationAds.setUrls(this.animations);
    if (this.imagesAds != null)
      this.imagesAds.setImageUrl(this.imageUrls);
    if (this.videoAds != null)
      this.videoAds.setVideos(this.videos);
    if (this.textAds != null)
      this.textAds.setTexts(this.texts);
    if (this.infos != null)
    {
      this.infos.setAnimationList(this.animationAds);
      this.infos.setIamgeAdList(this.imagesAds);
      this.infos.setVideoAdList(this.videoAds);
      this.infos.setTextAdList(this.textAds);
    }
    super.endDocument();
  }

  public AdInfos getAdInfo()
  {
    return this.infos;
  }

  public void startDocument()
    throws SAXException
  {
    if (this.videos == null)
    {
      this.videos = new ArrayList();
      if (this.imageUrls != null)
        break label87;
      this.imageUrls = new ArrayList();
      label36: if (this.texts != null)
        break label97;
      this.texts = new ArrayList();
      label54: if (this.animations != null)
        break label107;
      this.animations = new ArrayList();
    }
    while (true)
    {
      super.startDocument();
      return;
      this.videos.clear();
      break;
      label87: this.imageUrls.clear();
      break label36;
      label97: this.texts.clear();
      break label54;
      label107: this.animations.clear();
    }
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    super.startElement(paramString1, paramString2, paramString3, paramAttributes);
    if ("ad_info".equalsIgnoreCase(paramString2))
    {
      this.infos = new AdInfos();
      this.infos.setId(paramAttributes.getValue("id"));
      this.infos.setName(paramAttributes.getValue("name"));
    }
    if ("video_list".equalsIgnoreCase(paramString2))
      this.videoAds = new VideoAd();
    try
    {
      this.videoAds.setAlpha(Integer.valueOf(paramAttributes.getValue("alpha")).intValue());
    }
    catch (NumberFormatException localNumberFormatException1)
    {
      try
      {
        this.videoAds.setScale(Integer.valueOf(paramAttributes.getValue("scale")).intValue());
        label125: if ("video".equalsIgnoreCase(paramString2))
          str4 = paramAttributes.getValue("video_id");
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        try
        {
          while (true)
          {
            String str4;
            int j = Integer.valueOf(paramAttributes.getValue("video_type")).intValue();
            i = j;
            Video localVideo = new Video(str4, i);
            this.videos.add(localVideo);
            if ("image_list".equalsIgnoreCase(paramString2))
              this.imagesAds = new ImageAd();
            try
            {
              this.imagesAds.setAlpha(Integer.valueOf(paramAttributes.getValue("alpha")).intValue());
            }
            catch (NumberFormatException localNumberFormatException1)
            {
              try
              {
                this.imagesAds.setScale(Integer.valueOf(paramAttributes.getValue("scale")).intValue());
                this.imagesAds.setAction(paramAttributes.getValue("action"));
              }
              catch (NumberFormatException localNumberFormatException1)
              {
                try
                {
                  this.imagesAds.setInterval(Integer.valueOf(paramAttributes.getValue("interval")).intValue());
                  if ("image".equalsIgnoreCase(paramString2))
                  {
                    String str3 = paramAttributes.getValue("url");
                    this.imageUrls.add(str3);
                  }
                  if ("text_list".equalsIgnoreCase(paramString2))
                    this.textAds = new TextAd();
                }
                catch (NumberFormatException localNumberFormatException1)
                {
                  try
                  {
                    this.textAds.setAlpha(Integer.valueOf(paramAttributes.getValue("alpha")).intValue());
                  }
                  catch (NumberFormatException localNumberFormatException1)
                  {
                    try
                    {
                      this.textAds.setScale(Integer.valueOf(paramAttributes.getValue("scale")).intValue());
                      this.textAds.setAction(paramAttributes.getValue("action"));
                    }
                    catch (NumberFormatException localNumberFormatException1)
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
                          this.animationAds = new AnimationAd();
                      }
                      catch (NumberFormatException localNumberFormatException1)
                      {
                        try
                        {
                          this.animationAds.setAlpha(Integer.valueOf(paramAttributes.getValue("alpha")).intValue());
                        }
                        catch (NumberFormatException localNumberFormatException1)
                        {
                          try
                          {
                            while (true)
                            {
                              this.animationAds.setScale(Integer.valueOf(paramAttributes.getValue("scale")).intValue());
                              if ("animate".equalsIgnoreCase(paramString2))
                              {
                                String str1 = paramAttributes.getValue("url");
                                this.animations.add(str1);
                              }
                              return;
                              localNumberFormatException10 = localNumberFormatException10;
                              this.videoAds.setAlpha(0);
                              break;
                              localNumberFormatException11 = localNumberFormatException11;
                              this.videoAds.setScale(0);
                              break label125;
                              localNumberFormatException6 = localNumberFormatException6;
                              this.imagesAds.setAlpha(0);
                              continue;
                              localNumberFormatException7 = localNumberFormatException7;
                              this.imagesAds.setScale(0);
                              continue;
                              localNumberFormatException8 = localNumberFormatException8;
                              this.imagesAds.setInterval(0);
                              continue;
                              localNumberFormatException3 = localNumberFormatException3;
                              this.textAds.setAlpha(0);
                              continue;
                              localNumberFormatException4 = localNumberFormatException4;
                              this.textAds.setScale(0);
                              continue;
                              localNumberFormatException5 = localNumberFormatException5;
                              this.textAds.setInterval(0);
                              continue;
                              localNumberFormatException1 = localNumberFormatException1;
                              this.animationAds.setAlpha(0);
                            }
                          }
                          catch (NumberFormatException localNumberFormatException2)
                          {
                            while (true)
                              this.animationAds.setScale(0);
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
        catch (NumberFormatException localNumberFormatException9)
        {
          while (true)
            int i = 0;
        }
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetAdInfoByAdPosIdHandler
 * JD-Core Version:    0.6.2
 */