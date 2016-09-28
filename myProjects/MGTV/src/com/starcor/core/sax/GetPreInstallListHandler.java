package com.starcor.core.sax;

import com.starcor.core.domain.PreInstallList;
import com.starcor.core.domain.PreInstallList.Apps;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetPreInstallListHandler extends DefaultHandler
{
  private PreInstallList.Apps apps;
  private PreInstallList preInstallList;
  private StringBuilder sb = new StringBuilder();
  private String tagName;

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    super.characters(paramArrayOfChar, paramInt1, paramInt2);
    this.sb.append(paramArrayOfChar, paramInt1, paramInt2);
    String str = this.sb.toString();
    if ("state".equals(this.tagName))
      this.preInstallList.state = str;
    do
    {
      return;
      if ("reason".equals(this.tagName))
      {
        this.preInstallList.reason = str;
        return;
      }
      if ("type".equals(this.tagName))
      {
        this.apps.type = str;
        return;
      }
      if ("id".equals(this.tagName))
      {
        this.apps.id = str;
        return;
      }
      if ("package".equals(this.tagName))
      {
        this.apps.packageName = str;
        return;
      }
      if ("version_id".equals(this.tagName))
      {
        this.apps.versionId = str;
        return;
      }
    }
    while (!"version".equals(this.tagName));
    this.apps.version = str;
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    super.endElement(paramString1, paramString2, paramString3);
    if (paramString2.equalsIgnoreCase("i"))
      this.preInstallList.lists.add(this.apps);
  }

  public PreInstallList getPreInstallList()
  {
    return this.preInstallList;
  }

  public void startDocument()
    throws SAXException
  {
    this.preInstallList = new PreInstallList();
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    super.startElement(paramString1, paramString2, paramString3, paramAttributes);
    this.sb.setLength(0);
    this.tagName = paramString2;
    if (paramString2.equalsIgnoreCase("i"))
    {
      PreInstallList localPreInstallList = this.preInstallList;
      localPreInstallList.getClass();
      this.apps = new PreInstallList.Apps(localPreInstallList);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetPreInstallListHandler
 * JD-Core Version:    0.6.2
 */