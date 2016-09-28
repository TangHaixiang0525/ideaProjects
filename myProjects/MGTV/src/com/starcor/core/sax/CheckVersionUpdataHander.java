package com.starcor.core.sax;

import com.starcor.core.domain.AppVersion;
import com.starcor.core.domain.Version;
import com.starcor.core.utils.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class CheckVersionUpdataHander extends DefaultHandler
{
  private Version version;

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    if (paramString2.equalsIgnoreCase("app_version"));
  }

  public Version getVersion()
  {
    return this.version;
  }

  public void startDocument()
    throws SAXException
  {
    Logger.d("SAX", "startDocument");
    this.version = new Version();
    this.version.appVersion = new AppVersion();
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    Logger.d("SAX", "startElement localName:" + paramString2 + ", qName:" + paramString3);
    if ((paramString2.equalsIgnoreCase("app_version")) && (this.version.appVersion != null));
    try
    {
      this.version.appVersion.state = Integer.parseInt(paramAttributes.getValue("state"));
      this.version.appVersion.ver = paramAttributes.getValue("ver");
      this.version.appVersion.url = paramAttributes.getValue("url");
      this.version.appVersion.url_backup = paramAttributes.getValue("url_backup");
      this.version.appVersion.type = paramAttributes.getValue("type");
      this.version.appVersion.update_time = paramAttributes.getValue("update_time");
      this.version.appVersion.desc = paramAttributes.getValue("desc");
      return;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      while (true)
        this.version.appVersion.state = -1;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.CheckVersionUpdataHander
 * JD-Core Version:    0.6.2
 */