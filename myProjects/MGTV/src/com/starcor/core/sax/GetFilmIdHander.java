package com.starcor.core.sax;

import com.starcor.core.domain.VideoInfo;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetFilmIdHander extends DefaultHandler
{
  private VideoInfo videoInfo;

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
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
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (paramString2.equalsIgnoreCase("video"))
    {
      this.videoInfo.videoId = paramAttributes.getValue("id");
      this.videoInfo.videoType = Integer.parseInt(paramAttributes.getValue("type"));
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetFilmIdHander
 * JD-Core Version:    0.6.2
 */