package com.starcor.core.sax;

import com.starcor.core.domain.VideoIndex;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetVideoIndexHandler extends DefaultHandler
{
  private VideoIndex videoIndex;

  public VideoIndex getVideoIndex()
  {
    return this.videoIndex;
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (paramString2.equalsIgnoreCase("index"))
      this.videoIndex = new VideoIndex();
    try
    {
      this.videoIndex.index = Integer.parseInt(paramAttributes.getValue("index"));
      this.videoIndex.index_name = paramAttributes.getValue("index_name");
      this.videoIndex.name = paramAttributes.getValue("name");
      this.videoIndex.desc = paramAttributes.getValue("desc");
      this.videoIndex.imgUrl = paramAttributes.getValue("img");
      this.videoIndex.actor = paramAttributes.getValue("actor");
      this.videoIndex.director = paramAttributes.getValue("director");
      return;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      while (true)
        this.videoIndex.index = -1;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetVideoIndexHandler
 * JD-Core Version:    0.6.2
 */