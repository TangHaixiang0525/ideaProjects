package com.starcor.core.sax;

import com.starcor.core.domain.FilmListItem;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetHotFilmListHander extends DefaultHandler
{
  private ArrayList<FilmListItem> infoList;

  public ArrayList<FilmListItem> getHotFilmList()
  {
    return this.infoList;
  }

  public void startDocument()
    throws SAXException
  {
    super.startDocument();
    this.infoList = new ArrayList();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    super.startElement(paramString1, paramString2, paramString3, paramAttributes);
    FilmListItem localFilmListItem;
    if (paramString2.equalsIgnoreCase("item"))
    {
      localFilmListItem = new FilmListItem();
      localFilmListItem.video_id = paramAttributes.getValue("video_id");
      localFilmListItem.film_name = paramAttributes.getValue("name");
      localFilmListItem.small_img_url = paramAttributes.getValue("img_small");
      localFilmListItem.corner_mark_img_url = paramAttributes.getValue("corner_mark_img");
      localFilmListItem.package_id = paramAttributes.getValue("media_assets_id");
      localFilmListItem.category_id = paramAttributes.getValue("category_id");
    }
    try
    {
      localFilmListItem.uiStyle = Integer.valueOf(paramAttributes.getValue("ui_style")).intValue();
    }
    catch (Exception localException1)
    {
      try
      {
        while (true)
        {
          localFilmListItem.video_type = Integer.valueOf(paramAttributes.getValue("video_type")).intValue();
          this.infoList.add(localFilmListItem);
          return;
          localException1 = localException1;
          localFilmListItem.uiStyle = 1;
        }
      }
      catch (Exception localException2)
      {
        while (true)
          localFilmListItem.video_type = 1;
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetHotFilmListHander
 * JD-Core Version:    0.6.2
 */