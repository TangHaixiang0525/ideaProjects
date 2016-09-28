package com.starcor.core.sax;

import com.starcor.core.domain.CategoryIndexList;
import com.starcor.core.domain.CategoryIndexList.Item;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetIndexListByCategoryHandler extends DefaultHandler
{
  private CategoryIndexList indexList;
  private CategoryIndexList.Item item;
  private StringBuilder sb = new StringBuilder();
  private String tagName;

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    super.characters(paramArrayOfChar, paramInt1, paramInt2);
    this.sb.append(paramArrayOfChar, paramInt1, paramInt2);
    String str = this.sb.toString();
    if ("state".equals(this.tagName))
      this.indexList.state = str;
    do
    {
      return;
      if ("reason".equals(this.tagName))
      {
        this.indexList.reason = str;
        return;
      }
      if ("cur_page".equals(this.tagName))
        try
        {
          this.indexList.currentPage = Integer.parseInt(str);
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
          this.indexList.totalPage = Integer.parseInt(str);
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
          this.indexList.totalRows = Integer.parseInt(str);
          return;
        }
        catch (NumberFormatException localNumberFormatException1)
        {
          localNumberFormatException1.printStackTrace();
          return;
        }
      if ("type".equals(this.tagName))
      {
        this.item.type = str;
        return;
      }
      if ("id".equals(this.tagName))
      {
        this.item.id = str;
        return;
      }
      if ("name".equals(this.tagName))
      {
        this.item.name = str;
        return;
      }
      if ("alias_name".equals(this.tagName))
      {
        this.item.aliasName = str;
        return;
      }
      if ("img_h".equals(this.tagName))
      {
        this.item.imgH = str;
        return;
      }
      if ("img_s".equals(this.tagName))
      {
        this.item.imgS = str;
        return;
      }
      if ("img_v".equals(this.tagName))
      {
        this.item.imgV = str;
        return;
      }
      if ("video_id".equals(this.tagName))
      {
        this.item.videoId = str;
        return;
      }
      if ("video_type".equals(this.tagName))
      {
        this.item.videoType = str;
        return;
      }
      if ("video_index".equals(this.tagName))
      {
        this.item.videoIndex = str;
        return;
      }
      if ("actor".equals(this.tagName))
      {
        this.item.actor = str;
        return;
      }
      if ("watch_focus".equals(this.tagName))
      {
        this.item.watchFocus = str;
        return;
      }
    }
    while (!"release_time".equals(this.tagName));
    this.item.releaseTime = str;
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    super.endElement(paramString1, paramString2, paramString3);
    if (paramString2.equalsIgnoreCase("i"))
      this.indexList.items.add(this.item);
  }

  public CategoryIndexList getCategoryIndexList()
  {
    return this.indexList;
  }

  public void startDocument()
    throws SAXException
  {
    this.indexList = new CategoryIndexList();
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    this.sb.setLength(0);
    this.tagName = paramString2;
    if (paramString2.equalsIgnoreCase("i"))
    {
      CategoryIndexList localCategoryIndexList = this.indexList;
      localCategoryIndexList.getClass();
      this.item = new CategoryIndexList.Item(localCategoryIndexList);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetIndexListByCategoryHandler
 * JD-Core Version:    0.6.2
 */