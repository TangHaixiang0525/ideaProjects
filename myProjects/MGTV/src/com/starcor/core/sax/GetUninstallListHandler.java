package com.starcor.core.sax;

import com.starcor.core.domain.UninstallList;
import com.starcor.core.domain.UninstallList.Apps;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetUninstallListHandler extends DefaultHandler
{
  private UninstallList.Apps apps;
  private StringBuilder sb = new StringBuilder();
  private String tagName;
  private UninstallList uninstallList;

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    super.characters(paramArrayOfChar, paramInt1, paramInt2);
    this.sb.append(paramArrayOfChar, paramInt1, paramInt2);
    String str = this.sb.toString();
    if ("state".equals(this.tagName))
      this.uninstallList.state = str;
    do
    {
      return;
      if ("reason".equals(this.tagName))
      {
        this.uninstallList.reason = str;
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
    }
    while (!"package".equals(this.tagName));
    this.apps.packageName = str;
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    super.endElement(paramString1, paramString2, paramString3);
    if (paramString2.equalsIgnoreCase("i"))
      this.uninstallList.lists.add(this.apps);
  }

  public UninstallList getUninstallList()
  {
    return this.uninstallList;
  }

  public void startDocument()
    throws SAXException
  {
    this.uninstallList = new UninstallList();
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
      UninstallList localUninstallList = this.uninstallList;
      localUninstallList.getClass();
      this.apps = new UninstallList.Apps(localUninstallList);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetUninstallListHandler
 * JD-Core Version:    0.6.2
 */