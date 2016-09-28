package com.starcor.core.sax;

import com.starcor.core.domain.MediaInfo;
import com.starcor.core.domain.VideoIndex;
import com.starcor.core.domain.VideoInfo;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetFilmItemInfoHander extends DefaultHandler
{
  private VideoIndex mIndex = null;
  private MediaInfo mi = null;
  private VideoInfo videoInfo;

  public VideoInfo getVideoInfo()
  {
    return this.videoInfo;
  }

  public void startDocument()
    throws SAXException
  {
    super.startDocument();
    this.videoInfo = new VideoInfo();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (paramString2.equalsIgnoreCase("video"))
    {
      this.videoInfo.videoId = paramAttributes.getValue("id");
      this.videoInfo.name = paramAttributes.getValue("name");
      this.videoInfo.timeLen = paramAttributes.getValue("time_len");
      this.videoInfo.area = paramAttributes.getValue("area");
      this.videoInfo.actor = paramAttributes.getValue("actor");
      this.videoInfo.desc = paramAttributes.getValue("desc");
      this.videoInfo.director = paramAttributes.getValue("director");
      this.videoInfo.showTime = paramAttributes.getValue("show_time");
    }
    try
    {
      this.videoInfo.indexCount = Integer.valueOf(paramAttributes.getValue("index_count")).intValue();
    }
    catch (NumberFormatException localNumberFormatException1)
    {
      try
      {
        while (true)
        {
          this.videoInfo.point = Float.valueOf(paramAttributes.getValue("point")).floatValue();
          this.videoInfo.imgBigUrl = paramAttributes.getValue("img_big");
          this.videoInfo.imgNormalUrl = paramAttributes.getValue("img_normal");
          this.videoInfo.imgSmallUrl = paramAttributes.getValue("img_small");
          return;
          localNumberFormatException1 = localNumberFormatException1;
          this.videoInfo.indexCount = 0;
        }
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        while (true)
          this.videoInfo.point = 0.0F;
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetFilmItemInfoHander
 * JD-Core Version:    0.6.2
 */