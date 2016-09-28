package com.starcor.core.sax;

import com.starcor.core.domain.CommonVideoIDInfo;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetCommonVideoIdHandler extends DefaultHandler
{
  private CommonVideoIDInfo commonVideoIDInfo;

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    super.endElement(paramString1, paramString2, paramString3);
  }

  public CommonVideoIDInfo getCommonVideoIDInfo()
  {
    return this.commonVideoIDInfo;
  }

  public void startDocument()
    throws SAXException
  {
    super.startDocument();
    this.commonVideoIDInfo = new CommonVideoIDInfo();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (paramString2.equalsIgnoreCase("video"))
    {
      this.commonVideoIDInfo.mAssetId = paramAttributes.getValue("id");
      this.commonVideoIDInfo.mAssetName = paramAttributes.getValue("name");
    }
    if (paramString2.equalsIgnoreCase("index"))
    {
      this.commonVideoIDInfo.mClipId = paramAttributes.getValue("clip_id");
      this.commonVideoIDInfo.mClipName = paramAttributes.getValue("name");
      this.commonVideoIDInfo.mClipIndex = paramAttributes.getValue("index");
      this.commonVideoIDInfo.mClipVideoId = paramAttributes.getValue("video_id");
    }
    if (paramString2.equalsIgnoreCase("media"))
    {
      this.commonVideoIDInfo.mFileId = paramAttributes.getValue("file_id");
      this.commonVideoIDInfo.mFileQuality = paramAttributes.getValue("quality");
      this.commonVideoIDInfo.mFileDimensions = paramAttributes.getValue("dimensions");
      this.commonVideoIDInfo.mFileClipId = paramAttributes.getValue("clip_id");
      this.commonVideoIDInfo.mFileClipName = paramAttributes.getValue("index");
      this.commonVideoIDInfo.mFileClipName = paramAttributes.getValue("video_id");
    }
    if (paramString2.equalsIgnoreCase("live"))
    {
      this.commonVideoIDInfo.mLiveId = paramAttributes.getValue("id");
      this.commonVideoIDInfo.mLiveName = paramAttributes.getValue("name");
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetCommonVideoIdHandler
 * JD-Core Version:    0.6.2
 */