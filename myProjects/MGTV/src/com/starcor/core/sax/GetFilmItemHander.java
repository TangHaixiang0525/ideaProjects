package com.starcor.core.sax;

import com.starcor.core.domain.FilmItem;
import com.starcor.core.domain.FilmListItem;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetFilmItemHander extends DefaultHandler
{
  private FilmListItem fItem;
  private FilmItem filmItem;

  public FilmItem getFilmItem()
  {
    return this.filmItem;
  }

  public void startDocument()
    throws SAXException
  {
    this.filmItem = new FilmItem();
    this.filmItem.filmList = new ArrayList();
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (paramString2.equalsIgnoreCase("item_list"));
    try
    {
      this.filmItem.film_num = Integer.valueOf(paramAttributes.getValue("count_num")).intValue();
      if (paramString2.equalsIgnoreCase("item"))
      {
        this.fItem = new FilmListItem();
        this.fItem.package_id = paramAttributes.getValue("media_assets_id");
        this.fItem.category_id = paramAttributes.getValue("category_id");
        this.fItem.item_id = paramAttributes.getValue("id");
        this.fItem.film_name = paramAttributes.getValue("name");
        this.fItem.big_img_url = paramAttributes.getValue("img_big");
        this.fItem.normal_img_url = paramAttributes.getValue("img_normal");
        this.fItem.small_img_url = paramAttributes.getValue("img_small");
        this.fItem.desc = paramAttributes.getValue("desc");
        this.fItem.video_id = paramAttributes.getValue("video_id");
        this.fItem.corner_mark = paramAttributes.getValue("corner_mark");
        this.fItem.corner_mark_img_url = paramAttributes.getValue("corner_mark_img");
      }
    }
    catch (NumberFormatException localNumberFormatException4)
    {
      try
      {
        this.fItem.channel_index = Integer.valueOf(paramAttributes.getValue("channel_index")).intValue();
        this.fItem.video_ex_type = paramAttributes.getValue("video_ex_type");
        if (this.fItem.video_ex_type == null)
          this.fItem.video_ex_type = "";
      }
      catch (Exception localNumberFormatException4)
      {
        try
        {
          this.fItem.billing = Integer.valueOf(paramAttributes.getValue("billing")).intValue();
        }
        catch (Exception localNumberFormatException4)
        {
          try
          {
            this.fItem.video_type = Integer.valueOf(paramAttributes.getValue("video_type")).intValue();
          }
          catch (NumberFormatException localNumberFormatException4)
          {
            try
            {
              this.fItem.uiStyle = Integer.valueOf(paramAttributes.getValue("ui_style")).intValue();
            }
            catch (NumberFormatException localNumberFormatException4)
            {
              try
              {
                this.fItem.play_count = Integer.valueOf(paramAttributes.getValue("play_count")).intValue();
              }
              catch (NumberFormatException localNumberFormatException4)
              {
                try
                {
                  float f = Float.valueOf(paramAttributes.getValue("user_score")).floatValue();
                  this.fItem.user_score = ((int)f);
                }
                catch (NumberFormatException localNumberFormatException4)
                {
                  try
                  {
                    while (true)
                    {
                      this.fItem.point = Integer.valueOf(paramAttributes.getValue("point")).intValue();
                      this.filmItem.filmList.add(this.fItem);
                      super.startElement(paramString1, paramString2, paramString3, paramAttributes);
                      return;
                      localNumberFormatException6 = localNumberFormatException6;
                      this.filmItem.film_num = 0;
                      continue;
                      localException1 = localException1;
                      this.fItem.channel_index = 0;
                      continue;
                      localException2 = localException2;
                      this.fItem.billing = 0;
                      continue;
                      localNumberFormatException1 = localNumberFormatException1;
                      this.fItem.video_type = -1;
                      continue;
                      localNumberFormatException2 = localNumberFormatException2;
                      this.fItem.uiStyle = 0;
                      continue;
                      localNumberFormatException3 = localNumberFormatException3;
                      this.fItem.play_count = 0;
                      continue;
                      localNumberFormatException4 = localNumberFormatException4;
                      this.fItem.user_score = 0;
                    }
                  }
                  catch (NumberFormatException localNumberFormatException5)
                  {
                    while (true)
                      this.fItem.point = 0;
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetFilmItemHander
 * JD-Core Version:    0.6.2
 */