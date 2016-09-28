package com.starcor.core.sax;

import com.starcor.core.domain.AppDetail;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetAppInfoHandler extends DefaultHandler
{
  private AppDetail appInfo;
  private StringBuilder sb = new StringBuilder();
  private String tagName;

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    super.characters(paramArrayOfChar, paramInt1, paramInt2);
    this.sb.append(paramArrayOfChar, paramInt1, paramInt2);
    String str = this.sb.toString();
    if ("state".equals(this.tagName))
      this.appInfo.state = str;
    do
    {
      return;
      if ("reason".equals(this.tagName))
      {
        this.appInfo.reason = str;
        return;
      }
      if ("id".equals(this.tagName))
      {
        this.appInfo.id = str;
        return;
      }
      if ("category_id".equals(this.tagName))
      {
        this.appInfo.categoryId = str;
        return;
      }
      if ("developer_id".equals(this.tagName))
      {
        this.appInfo.developerId = str;
        return;
      }
      if ("name".equals(this.tagName))
      {
        this.appInfo.name = str;
        return;
      }
      if ("name_en".equals(this.tagName))
      {
        this.appInfo.nameEn = str;
        return;
      }
      if ("name_pinyin".equals(this.tagName))
      {
        this.appInfo.namePinyin = str;
        return;
      }
      if ("icon".equals(this.tagName))
      {
        this.appInfo.icon = str;
        return;
      }
      if ("package".equals(this.tagName))
      {
        this.appInfo.packageName = str;
        return;
      }
      if ("publish_time".equals(this.tagName))
      {
        this.appInfo.publishTime = str;
        return;
      }
      if ("down_count".equals(this.tagName))
      {
        this.appInfo.downCount = str;
        return;
      }
      if ("version_id".equals(this.tagName))
      {
        this.appInfo.versionId = str;
        return;
      }
      if ("version".equals(this.tagName))
      {
        this.appInfo.version = str;
        return;
      }
      if ("version_name".equals(this.tagName))
      {
        this.appInfo.versionName = str;
        return;
      }
      if ("size".equals(this.tagName))
      {
        this.appInfo.size = str;
        return;
      }
      if ("image0".equals(this.tagName))
      {
        this.appInfo.image0 = str;
        return;
      }
      if ("image1".equals(this.tagName))
      {
        this.appInfo.image1 = str;
        return;
      }
      if ("image2".equals(this.tagName))
      {
        this.appInfo.image2 = str;
        return;
      }
      if ("image3".equals(this.tagName))
      {
        this.appInfo.image3 = str;
        return;
      }
      if ("image4".equals(this.tagName))
      {
        this.appInfo.image4 = str;
        return;
      }
      if ("image5".equals(this.tagName))
      {
        this.appInfo.image5 = str;
        return;
      }
    }
    while (!"summary".equals(this.tagName));
    this.appInfo.summary = str;
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    super.endElement(paramString1, paramString2, paramString3);
  }

  public AppDetail getAppInfo()
  {
    return this.appInfo;
  }

  public void startDocument()
    throws SAXException
  {
    this.appInfo = new AppDetail();
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
 * Qualified Name:     com.starcor.core.sax.GetAppInfoHandler
 * JD-Core Version:    0.6.2
 */