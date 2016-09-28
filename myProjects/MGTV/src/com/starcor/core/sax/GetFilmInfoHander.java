package com.starcor.core.sax;

import com.starcor.core.domain.MediaInfo;
import com.starcor.core.domain.VideoIndex;
import com.starcor.core.domain.VideoInfo;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetFilmInfoHander extends DefaultHandler
{
  private VideoIndex mIndex;
  private MediaInfo mi;
  private VideoInfo videoInfo;

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    if (paramString2.equalsIgnoreCase("index"))
      this.videoInfo.indexList.add(this.mIndex);
    super.endElement(paramString1, paramString2, paramString3);
  }

  public VideoInfo getVideoInfo()
  {
    return this.videoInfo;
  }

  public void startDocument()
    throws SAXException
  {
    super.startDocument();
    this.videoInfo = new VideoInfo();
    this.videoInfo.indexList = new ArrayList();
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
      this.videoInfo.kind = paramAttributes.getValue("kind");
      this.videoInfo.corner_mark = paramAttributes.getValue("corner_mark");
    }
    do
    {
      do
      {
        try
        {
          float f2 = Float.valueOf(paramAttributes.getValue("user_score")).floatValue();
          this.videoInfo.user_score = ((int)f2);
        }
        catch (Exception localNumberFormatException10)
        {
          try
          {
            this.videoInfo.play_count = Integer.valueOf(paramAttributes.getValue("play_count")).intValue();
          }
          catch (Exception localNumberFormatException10)
          {
            try
            {
              this.videoInfo.billing = Integer.valueOf(paramAttributes.getValue("billing")).intValue();
            }
            catch (Exception localNumberFormatException10)
            {
              try
              {
                this.videoInfo.indexCount = Integer.valueOf(paramAttributes.getValue("index_count")).intValue();
              }
              catch (NumberFormatException localNumberFormatException10)
              {
                try
                {
                  this.videoInfo.indexCurrent = Integer.valueOf(paramAttributes.getValue("index_current")).intValue();
                }
                catch (NumberFormatException localNumberFormatException10)
                {
                  try
                  {
                    this.videoInfo.point = Float.valueOf(paramAttributes.getValue("point")).floatValue();
                  }
                  catch (NumberFormatException localNumberFormatException10)
                  {
                    try
                    {
                      this.videoInfo.uiStyle = Integer.valueOf(paramAttributes.getValue("ui_style")).intValue();
                      this.videoInfo.imgBigUrl = paramAttributes.getValue("img_big");
                      this.videoInfo.imgNormalUrl = paramAttributes.getValue("img_normal");
                      this.videoInfo.imgSmallUrl = paramAttributes.getValue("img_small");
                    }
                    catch (NumberFormatException localNumberFormatException10)
                    {
                      try
                      {
                        this.videoInfo.tsLimitMin = Integer.valueOf(paramAttributes.getValue("ts_limit_min")).intValue();
                      }
                      catch (NumberFormatException localNumberFormatException10)
                      {
                        try
                        {
                          this.videoInfo.tsLimitMax = Integer.valueOf(paramAttributes.getValue("ts_limit_max")).intValue();
                        }
                        catch (NumberFormatException localNumberFormatException10)
                        {
                          try
                          {
                            while (true)
                            {
                              this.videoInfo.tsDefaultPos = Integer.valueOf(paramAttributes.getValue("ts_default_pos")).intValue();
                              return;
                              localException1 = localException1;
                              this.videoInfo.user_score = -1.0D;
                              continue;
                              localException2 = localException2;
                              this.videoInfo.play_count = -1;
                              continue;
                              localException3 = localException3;
                              this.videoInfo.billing = 0;
                              continue;
                              localNumberFormatException5 = localNumberFormatException5;
                              this.videoInfo.indexCount = 0;
                              continue;
                              localNumberFormatException6 = localNumberFormatException6;
                              this.videoInfo.indexCurrent = 0;
                              continue;
                              localNumberFormatException7 = localNumberFormatException7;
                              this.videoInfo.point = 0.0F;
                              continue;
                              localNumberFormatException8 = localNumberFormatException8;
                              this.videoInfo.uiStyle = 0;
                              continue;
                              localNumberFormatException9 = localNumberFormatException9;
                              this.videoInfo.tsLimitMin = 0;
                              continue;
                              localNumberFormatException10 = localNumberFormatException10;
                              this.videoInfo.tsLimitMax = 0;
                            }
                          }
                          catch (NumberFormatException localNumberFormatException11)
                          {
                            this.videoInfo.tsDefaultPos = 0;
                            return;
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
        if (paramString2.equalsIgnoreCase("index"))
        {
          this.mIndex = new VideoIndex();
          try
          {
            this.mIndex.index = Integer.parseInt(paramAttributes.getValue("index"));
            this.mIndex.name = paramAttributes.getValue("name");
            this.mIndex.desc = paramAttributes.getValue("desc");
            this.mIndex.imgUrl = paramAttributes.getValue("img");
          }
          catch (NumberFormatException localNumberFormatException3)
          {
            try
            {
              float f1 = Float.valueOf(paramAttributes.getValue("user_score")).floatValue();
              this.mIndex.userScore = ((int)f1);
            }
            catch (NumberFormatException localNumberFormatException3)
            {
              try
              {
                while (true)
                {
                  this.mIndex.timeLen = Integer.parseInt(paramAttributes.getValue("time_len"));
                  this.mIndex.onlineTime = paramAttributes.getValue("online_time");
                  this.mIndex.mediaInfo = new ArrayList();
                  return;
                  localNumberFormatException2 = localNumberFormatException2;
                  this.mIndex.index = -1;
                  continue;
                  localNumberFormatException3 = localNumberFormatException3;
                  this.mIndex.userScore = 0;
                }
              }
              catch (NumberFormatException localNumberFormatException4)
              {
                while (true)
                  this.mIndex.timeLen = 0;
              }
            }
          }
        }
        if (!paramString2.equalsIgnoreCase("media"))
          break;
        this.mi = new MediaInfo();
        this.mi.media_id = paramAttributes.getValue("id");
        this.mi.type = paramAttributes.getValue("type");
        this.mi.caps = paramAttributes.getValue("caps");
      }
      while ((this.mIndex == null) || (this.mIndex.mediaInfo == null));
      this.mIndex.mediaInfo.add(this.mi);
      return;
    }
    while (!paramString2.equalsIgnoreCase("index_list"));
    try
    {
      this.videoInfo.index_num = Integer.parseInt(paramAttributes.getValue("count_num"));
      return;
    }
    catch (NumberFormatException localNumberFormatException1)
    {
      this.videoInfo.index_num = 0;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetFilmInfoHander
 * JD-Core Version:    0.6.2
 */