package com.starcor.core.sax;

import com.starcor.core.domain.StarGuestLabelList;
import com.starcor.core.domain.StarGuestLabelList.StarGuest;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetStarGuestLabelListHandler extends DefaultHandler
{
  private StarGuestLabelList.StarGuest guest;
  private StringBuilder sb = new StringBuilder();
  private StarGuestLabelList starGuestLabelList;
  private String tagName;

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    super.characters(paramArrayOfChar, paramInt1, paramInt2);
    this.sb.append(paramArrayOfChar, paramInt1, paramInt2);
    String str = this.sb.toString();
    if ("state".equals(this.tagName))
      this.starGuestLabelList.state = str;
    do
    {
      return;
      if ("reason".equals(this.tagName))
      {
        this.starGuestLabelList.reason = str;
        return;
      }
      if ("cur_page".equals(this.tagName))
        try
        {
          this.starGuestLabelList.cur_page = Integer.parseInt(str);
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
          this.starGuestLabelList.total_page = Integer.parseInt(str);
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
          this.starGuestLabelList.total_rows = Integer.parseInt(str);
          return;
        }
        catch (NumberFormatException localNumberFormatException1)
        {
          localNumberFormatException1.printStackTrace();
          return;
        }
      if ("type".equals(this.tagName))
      {
        this.guest.type = str;
        return;
      }
      if ("id".equals(this.tagName))
      {
        this.guest.id = str;
        return;
      }
      if ("name".equals(this.tagName))
      {
        this.guest.name = str;
        return;
      }
      if ("alias_name".equals(this.tagName))
      {
        this.guest.alias_name = str;
        return;
      }
      if ("img_h".equals(this.tagName))
      {
        this.guest.img_h = str;
        return;
      }
      if ("img_s".equals(this.tagName))
      {
        this.guest.img_s = str;
        return;
      }
      if ("img_v".equals(this.tagName))
      {
        this.guest.img_v = str;
        return;
      }
      if ("info".equals(this.tagName))
      {
        this.guest.info = str;
        return;
      }
      if ("video_id".equals(this.tagName))
      {
        this.guest.video_id = str;
        return;
      }
      if ("video_type".equals(this.tagName))
      {
        this.guest.video_type = str;
        return;
      }
      if ("video_index".equals(this.tagName))
      {
        this.guest.video_index = str;
        return;
      }
      if ("actor".equals(this.tagName))
      {
        this.guest.actor = str;
        return;
      }
      if ("watch_focus".equals(this.tagName))
      {
        this.guest.watch_focus = str;
        return;
      }
    }
    while (!"release_time".equals(this.tagName));
    this.guest.release_time = str;
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    super.endElement(paramString1, paramString2, paramString3);
    if (paramString2.equalsIgnoreCase("i"))
      this.starGuestLabelList.lists.add(this.guest);
  }

  public StarGuestLabelList getStarGuestLabelList()
  {
    return this.starGuestLabelList;
  }

  public void startDocument()
    throws SAXException
  {
    this.starGuestLabelList = new StarGuestLabelList();
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
      StarGuestLabelList localStarGuestLabelList = this.starGuestLabelList;
      localStarGuestLabelList.getClass();
      this.guest = new StarGuestLabelList.StarGuest(localStarGuestLabelList);
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetStarGuestLabelListHandler
 * JD-Core Version:    0.6.2
 */