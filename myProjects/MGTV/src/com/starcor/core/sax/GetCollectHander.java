package com.starcor.core.sax;

import com.starcor.core.domain.CollectListItem;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetCollectHander extends DefaultHandler
{
  private ArrayList<CollectListItem> collectList;

  public ArrayList<CollectListItem> getCollectList()
  {
    return this.collectList;
  }

  public void startDocument()
    throws SAXException
  {
    if (this.collectList == null)
      this.collectList = new ArrayList();
    while (true)
    {
      super.startDocument();
      return;
      this.collectList.clear();
    }
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    CollectListItem localCollectListItem;
    if (paramString2.equalsIgnoreCase("collect"))
    {
      localCollectListItem = new CollectListItem();
      localCollectListItem.id = paramAttributes.getValue("id");
      localCollectListItem.video_id = paramAttributes.getValue("video_id");
    }
    try
    {
      localCollectListItem.video_type = Integer.valueOf(paramAttributes.getValue("video_type")).intValue();
      localCollectListItem.video_name = paramAttributes.getValue("video_name");
    }
    catch (NumberFormatException localNumberFormatException5)
    {
      try
      {
        localCollectListItem.video_index = Integer.valueOf(paramAttributes.getValue("video_index")).intValue();
      }
      catch (NumberFormatException localNumberFormatException5)
      {
        try
        {
          localCollectListItem.played_time = Integer.valueOf(paramAttributes.getValue("played_time")).intValue();
        }
        catch (NumberFormatException localNumberFormatException5)
        {
          try
          {
            localCollectListItem.uiStyle = Integer.valueOf(paramAttributes.getValue("ui_style")).intValue();
          }
          catch (NumberFormatException localNumberFormatException5)
          {
            try
            {
              localCollectListItem.online = Integer.valueOf(paramAttributes.getValue("online")).intValue();
            }
            catch (NumberFormatException localNumberFormatException5)
            {
              try
              {
                while (true)
                {
                  localCollectListItem.update_index = Integer.valueOf(paramAttributes.getValue("update_index")).intValue();
                  localCollectListItem.media_assets_name = paramAttributes.getValue("media_assets_name");
                  localCollectListItem.add_time = paramAttributes.getValue("collect_time");
                  localCollectListItem.media_assets_id = paramAttributes.getValue("media_assets_id");
                  localCollectListItem.category_id = paramAttributes.getValue("category_id");
                  localCollectListItem.ts_begin = paramAttributes.getValue("ts_begin");
                  localCollectListItem.ts_day = paramAttributes.getValue("ts_day");
                  localCollectListItem.ts_time_len = paramAttributes.getValue("ts_time_len");
                  this.collectList.add(localCollectListItem);
                  return;
                  localNumberFormatException1 = localNumberFormatException1;
                  localCollectListItem.video_type = 1;
                  continue;
                  localNumberFormatException2 = localNumberFormatException2;
                  localCollectListItem.video_index = 0;
                  continue;
                  localNumberFormatException3 = localNumberFormatException3;
                  localCollectListItem.played_time = 0;
                  continue;
                  localNumberFormatException4 = localNumberFormatException4;
                  localCollectListItem.uiStyle = 0;
                  continue;
                  localNumberFormatException5 = localNumberFormatException5;
                  localCollectListItem.online = 1;
                }
              }
              catch (NumberFormatException localNumberFormatException6)
              {
                while (true)
                  localCollectListItem.update_index = 0;
              }
            }
          }
        }
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetCollectHander
 * JD-Core Version:    0.6.2
 */