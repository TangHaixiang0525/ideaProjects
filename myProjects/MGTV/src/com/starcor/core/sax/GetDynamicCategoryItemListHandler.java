package com.starcor.core.sax;

import android.text.TextUtils;
import com.starcor.core.domain.CollectListItem;
import com.starcor.core.domain.UserRecommendV2Item;
import com.starcor.core.utils.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetDynamicCategoryItemListHandler extends DefaultHandler
{
  private CollectListItem collectListItem = null;
  private ArrayList<CollectListItem> listCollectListItem;
  private ArrayList<UserRecommendV2Item> listUserRecommendItem;
  HashMap<String, ArrayList> map = new HashMap();
  private UserRecommendV2Item recommendItem = null;
  private String type = "";
  private String value = "";
  private StringBuilder valueBuffer = new StringBuilder();

  public HashMap<String, ArrayList> GetPlayListAndLikeList()
  {
    this.map.put("guess_like", this.listUserRecommendItem);
    this.map.put("play_record", this.listCollectListItem);
    return this.map;
  }

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    if (paramInt2 > 0)
    {
      String str = new String(paramArrayOfChar, 0, paramInt2);
      if ((!TextUtils.isEmpty(str)) && (paramArrayOfChar[0] != '\n'))
        this.valueBuffer.append(str);
    }
    super.characters(paramArrayOfChar, paramInt1, paramInt2);
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    this.value = this.valueBuffer.toString();
    if ("guess_like".equals(this.value))
    {
      this.type = "guess_like";
      this.listUserRecommendItem = new ArrayList();
    }
    if ("play_record".equals(this.value))
    {
      this.type = "play_record";
      this.listCollectListItem = new ArrayList();
    }
    if (paramString2.equals("content_list"))
      this.type = "";
    if (TextUtils.isEmpty(this.type))
    {
      label94: return;
    }
    else
    {
      if (this.recommendItem == null)
        break label695;
      if (paramString3.equals("asset_import_id"))
        this.recommendItem.ohitid = this.value;
      if (paramString3.equals("video_id"))
        this.recommendItem.video_id = this.value;
      if (paramString3.equals("name"))
        this.recommendItem.name = this.value;
      if (paramString3.equals("type"))
        this.recommendItem.video_type = this.value;
      if (paramString3.equals("img_h"))
        this.recommendItem.img_h = this.value;
      if (paramString3.equals("img_s"))
        this.recommendItem.img_s = this.value;
      if (paramString3.equals("img_v"))
        this.recommendItem.img_v = this.value;
      if (paramString3.equals("media_assets_id"))
        this.recommendItem.media_assets_id = this.value;
      if (paramString3.equals("category_id"))
        this.recommendItem.category_id = this.value;
      if (paramString3.equals("online_time"))
        this.recommendItem.online_time = this.value;
      if (paramString3.equals("ui_style"))
        this.recommendItem.ui_style = this.value;
      if (paramString3.equals("point"))
        this.recommendItem.ui_style = this.value;
      if (paramString3.equals("user_score"))
        this.recommendItem.user_score = this.value;
      if (paramString3.equals("new_index"))
        this.recommendItem.new_index = this.value;
      if (paramString3.equals("all_index"))
        this.recommendItem.all_index = this.value;
      if (paramString3.equals("view_type"))
        this.recommendItem.view_type = this.value;
      if (paramString3.equals("desc"))
        this.recommendItem.desc = this.value;
      if (paramString3.equals("billing"))
        this.recommendItem.billing = this.value;
      if (paramString3.equals("price_type"))
        this.recommendItem.price_type = this.value;
      if (paramString3.equals("price_value"))
        this.recommendItem.price_value = this.value;
      if (paramString3.equals("price_code"))
        this.recommendItem.price_code = this.value;
      if (paramString3.equals("corner_mark"))
        this.recommendItem.corner_mark = this.value;
      if (paramString3.equals("corner_mark_img"))
        this.recommendItem.corner_mark_img = this.value;
      if (paramString3.equals("serial_id"))
        this.recommendItem.sohitid = this.value;
      if (paramString3.equals("is_show_new_index"))
        this.recommendItem.is_show_new_index = this.value;
      if (paramString3.equals("play_count"))
        this.recommendItem.play_count = this.value;
      if (paramString3.equals("index_ui_style"))
        this.recommendItem.index_ui_style = this.value;
      if (paramString3.equals("i"))
      {
        this.listUserRecommendItem.add(this.recommendItem);
        this.recommendItem = null;
      }
    }
    while (true)
    {
      if (this.valueBuffer.length() <= 0)
        break label94;
      this.valueBuffer.delete(0, this.valueBuffer.length());
      return;
      label695: if (this.collectListItem == null)
        break;
      if (paramString3.equals("state"))
        this.collectListItem.state = this.value;
      if (paramString3.equals("reason"))
        this.collectListItem.reason = this.value;
      if (paramString3.equals("type"))
        this.collectListItem.type = this.value;
      if (paramString3.equals("id"))
        this.collectListItem.id = this.value;
      if (paramString3.equals("name"))
        this.collectListItem.video_name = this.value;
      if (paramString3.equals("img_h"))
        this.collectListItem.img_h = this.value;
      if (paramString3.equals("img_s"))
        this.collectListItem.img_s = this.value;
      if (paramString3.equals("img_v"))
        this.collectListItem.img_v = this.value;
      if (paramString3.equals("info"))
        this.collectListItem.info = this.value;
      if (paramString3.equals("video_id"))
        this.collectListItem.video_id = this.value;
      if (paramString3.equals("video_type"));
      try
      {
        this.collectListItem.video_type = Integer.parseInt(this.value);
        label925: if (!paramString3.equals("video_index"));
      }
      catch (Exception localException2)
      {
        try
        {
          this.collectListItem.video_index = Integer.parseInt(this.value);
          label949: if (paramString3.equals("sub_name"))
            this.collectListItem.sub_name = this.value;
          if (paramString3.equals("tstv_begin_time"))
            this.collectListItem.ts_begin = this.value;
        }
        catch (Exception localException2)
        {
          try
          {
            String str = this.value;
            if (str.length() > 8)
            {
              this.collectListItem.ts_day = str.substring(0, 8);
              this.collectListItem.ts_begin = str.substring(8);
            }
            label1036: if (paramString3.equals("tstv_time_len"))
              this.collectListItem.ts_time_len = this.value;
            if (paramString3.equals("media_assets_id"))
              this.collectListItem.media_assets_id = this.value;
            if (paramString3.equals("category_id"))
              this.collectListItem.category_id = this.value;
            if (paramString3.equals("version"))
              this.collectListItem.version = this.value;
            if (paramString3.equals("add_time"))
              this.collectListItem.add_time = this.value;
            if (paramString3.equals("collect_time"))
              this.collectListItem.add_time = this.value;
            if (paramString3.equals("video_all_index"));
            try
            {
              this.collectListItem.video_all_index = Integer.parseInt(this.value);
              if (!paramString3.equals("video_new_index"));
            }
            catch (Exception localException2)
            {
              try
              {
                this.collectListItem.update_index = Integer.parseInt(this.value);
                if (paramString3.equals("video_actor"))
                  this.collectListItem.video_actor = this.value;
                if (!paramString3.equals("video_director"));
              }
              catch (Exception localException2)
              {
                try
                {
                  this.collectListItem.video_director = this.value;
                  if (paramString3.equals("video_kind"))
                    this.collectListItem.video_kind = this.value;
                  if (paramString3.equals("quality"))
                    this.collectListItem.quality = this.value;
                  if (paramString3.equals("mark"))
                    this.collectListItem.mark = this.value;
                  if (paramString3.equals("corner_mark_img"))
                    this.collectListItem.corner_mark_img = this.value;
                  if (!paramString3.equals("played_time_len"));
                }
                catch (Exception localException2)
                {
                  try
                  {
                    this.collectListItem.played_time = Integer.parseInt(this.value);
                    if (!paramString3.equals("time_len"));
                  }
                  catch (Exception localException2)
                  {
                    try
                    {
                      this.collectListItem.duration = Integer.parseInt(this.value);
                      if (!paramString3.equals("online"));
                    }
                    catch (Exception localException2)
                    {
                      try
                      {
                        while (true)
                        {
                          this.collectListItem.online = Integer.parseInt(this.value);
                          if (paramString3.equals("index_ui_style"))
                            this.collectListItem.index_ui_style = this.value;
                          if (!paramString3.equals("i"))
                            break;
                          this.listCollectListItem.add(this.collectListItem);
                          this.collectListItem = null;
                          break;
                          localException9 = localException9;
                          this.collectListItem.video_type = 0;
                          break label925;
                          localException8 = localException8;
                          this.collectListItem.video_index = 0;
                          break label949;
                          localException6 = localException6;
                          this.collectListItem.video_all_index = 0;
                          continue;
                          localException5 = localException5;
                          this.collectListItem.update_index = 0;
                          continue;
                          localException4 = localException4;
                          this.collectListItem.video_director = "";
                          continue;
                          localException3 = localException3;
                          this.collectListItem.played_time = 0;
                        }
                        localException2 = localException2;
                        this.collectListItem.duration = 1;
                      }
                      catch (Exception localException1)
                      {
                        while (true)
                          this.collectListItem.online = 1;
                      }
                    }
                  }
                }
              }
            }
          }
          catch (Exception localException7)
          {
            break label1036;
          }
        }
      }
    }
  }

  public void startDocument()
    throws SAXException
  {
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (paramString2.equals("i"))
    {
      if (!"guess_like".equals(this.type))
        break label59;
      this.recommendItem = new UserRecommendV2Item();
      Logger.i("new recommendItem=" + this.recommendItem);
    }
    label59: 
    while (!"play_record".equals(this.type))
      return;
    this.collectListItem = new CollectListItem();
    Logger.i("new collectListItem=" + this.collectListItem);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetDynamicCategoryItemListHandler
 * JD-Core Version:    0.6.2
 */