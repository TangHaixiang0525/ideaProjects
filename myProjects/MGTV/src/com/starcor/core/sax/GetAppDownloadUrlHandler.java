package com.starcor.core.sax;

import com.starcor.core.domain.AppDownloadUrl;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetAppDownloadUrlHandler extends DefaultHandler
{
  private AppDownloadUrl appDownloadUrl;
  private StringBuilder sb = new StringBuilder();
  private String tagName;

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    super.characters(paramArrayOfChar, paramInt1, paramInt2);
    this.sb.append(paramArrayOfChar, paramInt1, paramInt2);
    String str = this.sb.toString();
    if ("state".equals(this.tagName))
      this.appDownloadUrl.state = str;
    do
    {
      return;
      if ("reason".equals(this.tagName))
      {
        this.appDownloadUrl.reason = str;
        return;
      }
      if ("app_id".equals(this.tagName))
      {
        this.appDownloadUrl.appId = str;
        return;
      }
      if ("version".equals(this.tagName))
      {
        this.appDownloadUrl.version = str;
        return;
      }
    }
    while (!"url".equals(this.tagName));
    this.appDownloadUrl.url = str;
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    super.endElement(paramString1, paramString2, paramString3);
  }

  public AppDownloadUrl getAppDownloadUrl()
  {
    return this.appDownloadUrl;
  }

  public void startDocument()
    throws SAXException
  {
    this.appDownloadUrl = new AppDownloadUrl();
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    super.startElement(paramString1, paramString2, paramString3, paramAttributes);
    this.sb.setLength(0);
    this.tagName = paramString2;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetAppDownloadUrlHandler
 * JD-Core Version:    0.6.2
 */