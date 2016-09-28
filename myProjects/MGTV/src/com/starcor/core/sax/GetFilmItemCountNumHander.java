package com.starcor.core.sax;

import com.starcor.core.domain.FilmListPageInfo;
import com.starcor.core.domain.MediaInfo;
import com.starcor.core.domain.VideoIndex;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetFilmItemCountNumHander extends DefaultHandler
{
  private ArrayList<MediaInfo> mediaInfoList;
  private FilmListPageInfo pageInfo;
  private VideoIndex tempVideoIndex;
  private ArrayList<VideoIndex> videoIndexList;

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    super.endElement(paramString1, paramString2, paramString3);
    if (paramString2.equalsIgnoreCase("index"))
      if (this.tempVideoIndex != null)
      {
        this.tempVideoIndex.mediaInfo = this.mediaInfoList;
        this.videoIndexList.add(this.tempVideoIndex);
        this.tempVideoIndex = null;
      }
    while (!paramString2.equalsIgnoreCase("index_list"))
      return;
    this.pageInfo.setFilmInfo(this.videoIndexList);
  }

  public FilmListPageInfo getFilmListPageInfo()
  {
    return this.pageInfo;
  }

  public void startDocument()
    throws SAXException
  {
    this.pageInfo = new FilmListPageInfo();
    this.videoIndexList = new ArrayList();
    this.mediaInfoList = new ArrayList();
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (paramString2.equalsIgnoreCase("index_list"))
      this.pageInfo.setIndex_order(paramAttributes.getValue("index_order"));
    do
    {
      try
      {
        this.pageInfo.setCount_num(Integer.parseInt(paramAttributes.getValue("index")));
        return;
      }
      catch (NumberFormatException localNumberFormatException3)
      {
        this.pageInfo.setCount_num(0);
        return;
      }
      if (paramString2.equalsIgnoreCase("index"))
      {
        VideoIndex localVideoIndex = new VideoIndex();
        try
        {
          localVideoIndex.index = Integer.parseInt(paramAttributes.getValue("index"));
          localVideoIndex.index_name = paramAttributes.getValue("index_name");
          localVideoIndex.name = paramAttributes.getValue("name");
          localVideoIndex.id = paramAttributes.getValue("id");
          localVideoIndex.import_id = paramAttributes.getValue("import_id");
          localVideoIndex.timeLen = Integer.valueOf(paramAttributes.getValue("time_len")).intValue();
          localVideoIndex.desc = paramAttributes.getValue("desc");
          localVideoIndex.imgUrl = paramAttributes.getValue("img");
          localVideoIndex.onlineTime = paramAttributes.getValue("online_time");
          localVideoIndex.serial_id = paramAttributes.getValue("series_id");
        }
        catch (NumberFormatException localNumberFormatException1)
        {
          try
          {
            localVideoIndex.userScore = ((int)Float.valueOf(paramAttributes.getValue("user_score")).floatValue());
            this.tempVideoIndex = localVideoIndex;
            return;
            localNumberFormatException1 = localNumberFormatException1;
            localVideoIndex.index = -1;
          }
          catch (NumberFormatException localNumberFormatException2)
          {
            while (true)
              localVideoIndex.userScore = -1;
          }
        }
      }
    }
    while (!paramString2.equalsIgnoreCase("media"));
    MediaInfo localMediaInfo = new MediaInfo();
    localMediaInfo.type = paramAttributes.getValue("type");
    localMediaInfo.media_id = paramAttributes.getValue("id");
    localMediaInfo.caps = paramAttributes.getValue("caps");
    localMediaInfo.onLineTime = paramAttributes.getValue("online_time");
    this.mediaInfoList.add(localMediaInfo);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetFilmItemCountNumHander
 * JD-Core Version:    0.6.2
 */