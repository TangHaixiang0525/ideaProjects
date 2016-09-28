package com.starcor.core.sax;

import com.starcor.core.domain.AppList;
import com.starcor.core.domain.AppList.Apps;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetAppListHandler extends DefaultHandler
{
  private AppList appList;
  private AppList.Apps apps;
  private StringBuilder sb = new StringBuilder();
  private String tagName;

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    super.characters(paramArrayOfChar, paramInt1, paramInt2);
    this.sb.append(paramArrayOfChar, paramInt1, paramInt2);
    String str = this.sb.toString();
    if ("state".equals(this.tagName))
      this.appList.state = str;
    do
    {
      return;
      if ("reason".equals(this.tagName))
      {
        this.appList.reason = str;
        return;
      }
      if ("cur_page".equals(this.tagName))
        try
        {
          this.appList.currentPage = Integer.parseInt(str);
          return;
        }
        catch (NumberFormatException localNumberFormatException3)
        {
          localNumberFormatException3.printStackTrace();
          return;
        }
      if ("total_page".equals(this.tagName))
        try
        {
          this.appList.totalPage = Integer.parseInt(str);
          return;
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          localNumberFormatException2.printStackTrace();
          return;
        }
      if ("total_rows".equals(this.tagName))
        try
        {
          this.appList.totalRows = Integer.parseInt(str);
          return;
        }
        catch (NumberFormatException localNumberFormatException1)
        {
          localNumberFormatException1.printStackTrace();
          return;
        }
      if ("type".equals(this.tagName))
      {
        this.apps.type = str;
        return;
      }
      if ("app_id".equals(this.tagName))
      {
        this.apps.appId = str;
        return;
      }
      if ("version_id".equals(this.tagName))
      {
        this.apps.versionId = str;
        return;
      }
      if ("name".equals(this.tagName))
      {
        this.apps.name = str;
        return;
      }
      if ("name_en".equals(this.tagName))
      {
        this.apps.nameEn = str;
        return;
      }
      if ("name_pinyin".equals(this.tagName))
      {
        this.apps.namePinyin = str;
        return;
      }
      if ("icon".equals(this.tagName))
      {
        this.apps.icon = str;
        return;
      }
      if ("down_count".equals(this.tagName))
      {
        this.apps.downCount = str;
        return;
      }
      if ("publish_time".equals(this.tagName))
      {
        this.apps.publishTime = str;
        return;
      }
      if ("version".equals(this.tagName))
      {
        this.apps.version = str;
        return;
      }
      if ("version_name".equals(this.tagName))
      {
        this.apps.versionName = str;
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
      this.appList.lists.add(this.apps);
  }

  public AppList getAppList()
  {
    return this.appList;
  }

  public void startDocument()
    throws SAXException
  {
    this.appList = new AppList();
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
      AppList localAppList = this.appList;
      localAppList.getClass();
      this.apps = new AppList.Apps(localAppList);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetAppListHandler
 * JD-Core Version:    0.6.2
 */