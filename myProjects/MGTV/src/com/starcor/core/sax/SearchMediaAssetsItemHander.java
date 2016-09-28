package com.starcor.core.sax;

import com.starcor.core.domain.SearchListItem;
import com.starcor.core.domain.SearchStruct;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SearchMediaAssetsItemHander extends DefaultHandler
{
  private SearchListItem fItem;
  private SearchStruct sInfo;

  public SearchStruct getSearchInfo()
  {
    return this.sInfo;
  }

  public void startDocument()
    throws SAXException
  {
    this.sInfo = new SearchStruct();
    this.sInfo.sItemList = new ArrayList();
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (paramString2.equalsIgnoreCase("item_list"));
    try
    {
      this.sInfo.item_num = Integer.valueOf(paramAttributes.getValue("count_num")).intValue();
      if (paramString2.equalsIgnoreCase("item"))
      {
        this.fItem = new SearchListItem();
        this.fItem.item_id = paramAttributes.getValue("id");
        this.fItem.name = paramAttributes.getValue("name");
        this.fItem.big_img_url = paramAttributes.getValue("img_big");
        this.fItem.normal_img_url = paramAttributes.getValue("img_normal");
        this.fItem.small_img_url = paramAttributes.getValue("img_small");
        this.fItem.video_id = paramAttributes.getValue("video_id");
        this.fItem.corner_mark = paramAttributes.getValue("corner_mark");
        this.fItem.corner_mark_img_url = paramAttributes.getValue("corner_mark_img");
      }
    }
    catch (NumberFormatException localNumberFormatException4)
    {
      try
      {
        this.fItem.billing = Integer.valueOf(paramAttributes.getValue("billing")).intValue();
      }
      catch (Exception localNumberFormatException4)
      {
        try
        {
          this.fItem.ui_style = Integer.valueOf(paramAttributes.getValue("ui_style")).intValue();
        }
        catch (NumberFormatException localNumberFormatException4)
        {
          try
          {
            this.fItem.video_type = Integer.valueOf(paramAttributes.getValue("video_type")).intValue();
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
                    this.fItem.package_id = paramAttributes.getValue("media_asset_id");
                    this.fItem.category_id = paramAttributes.getValue("category_id");
                    this.sInfo.sItemList.add(this.fItem);
                    return;
                    localNumberFormatException6 = localNumberFormatException6;
                    this.sInfo.item_num = 0;
                    continue;
                    localException = localException;
                    this.fItem.billing = 0;
                    continue;
                    localNumberFormatException1 = localNumberFormatException1;
                    this.fItem.ui_style = 0;
                    continue;
                    localNumberFormatException2 = localNumberFormatException2;
                    this.fItem.video_type = -1;
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

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.SearchMediaAssetsItemHander
 * JD-Core Version:    0.6.2
 */