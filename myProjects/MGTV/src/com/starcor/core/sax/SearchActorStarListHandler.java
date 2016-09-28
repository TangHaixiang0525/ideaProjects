package com.starcor.core.sax;

import com.starcor.core.domain.SearchActorStarList;
import com.starcor.core.domain.SearchActorStarList.ActorStar;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SearchActorStarListHandler extends DefaultHandler
{
  private SearchActorStarList.ActorStar actorStar;
  private StringBuilder sb = new StringBuilder();
  private SearchActorStarList searchActorStarList;
  private String tagName;

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    super.characters(paramArrayOfChar, paramInt1, paramInt2);
    this.sb.append(paramArrayOfChar, paramInt1, paramInt2);
    String str = this.sb.toString();
    if ("state".equals(this.tagName))
      this.searchActorStarList.state = str;
    do
    {
      return;
      if ("reason".equals(this.tagName))
      {
        this.searchActorStarList.reason = str;
        return;
      }
      if ("cur_page".equals(this.tagName))
        try
        {
          this.searchActorStarList.cur_page = Integer.parseInt(str);
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
          this.searchActorStarList.total_page = Integer.parseInt(str);
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
          this.searchActorStarList.total_rows = Integer.parseInt(str);
          return;
        }
        catch (NumberFormatException localNumberFormatException1)
        {
          localNumberFormatException1.printStackTrace();
          return;
        }
      if ("type".equals(this.tagName))
      {
        this.actorStar.type = str;
        return;
      }
      if ("id".equals(this.tagName))
      {
        this.actorStar.id = str;
        return;
      }
      if ("name".equals(this.tagName))
      {
        this.actorStar.name = str;
        return;
      }
      if ("alias_name".equals(this.tagName))
      {
        this.actorStar.alias_name = str;
        return;
      }
      if ("img_h".equals(this.tagName))
      {
        this.actorStar.img_h = str;
        return;
      }
      if ("img_s".equals(this.tagName))
      {
        this.actorStar.img_s = str;
        return;
      }
      if ("img_v".equals(this.tagName))
      {
        this.actorStar.img_v = str;
        return;
      }
      if ("info".equals(this.tagName))
      {
        this.actorStar.info = str;
        return;
      }
      if ("label_id".equals(this.tagName))
      {
        this.actorStar.label_id = str;
        return;
      }
      if ("sex".equals(this.tagName))
      {
        this.actorStar.sex = str;
        return;
      }
      if ("old_name".equals(this.tagName))
      {
        this.actorStar.old_name = str;
        return;
      }
      if ("en_name".equals(this.tagName))
      {
        this.actorStar.en_name = str;
        return;
      }
    }
    while (!"label_id_v2".equals(this.tagName));
    this.actorStar.label_id_v2 = str;
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    super.endElement(paramString1, paramString2, paramString3);
    if (paramString2.equalsIgnoreCase("i"))
      this.searchActorStarList.lists.add(this.actorStar);
  }

  public SearchActorStarList getStarGuestLabelList()
  {
    return this.searchActorStarList;
  }

  public void startDocument()
    throws SAXException
  {
    this.searchActorStarList = new SearchActorStarList();
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
      SearchActorStarList localSearchActorStarList = this.searchActorStarList;
      localSearchActorStarList.getClass();
      this.actorStar = new SearchActorStarList.ActorStar(localSearchActorStarList);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.SearchActorStarListHandler
 * JD-Core Version:    0.6.2
 */