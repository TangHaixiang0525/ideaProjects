package com.starcor.core.domain;

import java.io.Serializable;

public class SystemMessageBody
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  public String content = "";
  public String msgID = "";
  public String publishTime = "";
  public String title = "";
  public String type = "";
  public VideoDetail videoDetail = new VideoDetail();
  public VideoId videoId = new VideoId();
  public WebPage webPage = new WebPage();

  public String toString()
  {
    String str = "SystemMessageBody: msgID=" + this.msgID + ", title=" + this.title + ", content=" + this.content + ", type=" + this.type + ", publishTime=" + this.publishTime;
    if (this.videoId != null)
      str = str + ", videoId.assetId=" + this.videoId.assetId + ", videoId.clipId=" + this.videoId.clipId + ", videoId.fileId=" + this.videoId.fileId;
    if (this.videoDetail != null)
      str = str + ", videoDetail.assetId=" + this.videoDetail.assetId + ", videoDetail.assetsCategory=" + this.videoDetail.assetsCategory;
    if (this.webPage != null)
      str = str + ", webPage.url=" + this.webPage.url;
    return str;
  }

  public class VideoDetail
  {
    public String assetId = "";
    public String assetsCategory = "";

    public VideoDetail()
    {
    }
  }

  public class VideoId
  {
    public String assetId = "";
    public String clipId = "";
    public String fileId = "";

    public VideoId()
    {
    }
  }

  public class WebPage
  {
    public String url = "";

    public WebPage()
    {
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.SystemMessageBody
 * JD-Core Version:    0.6.2
 */