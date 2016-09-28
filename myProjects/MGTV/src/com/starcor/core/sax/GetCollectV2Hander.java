package com.starcor.core.sax;

import android.text.TextUtils;
import com.starcor.core.domain.CollectListItem;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetCollectV2Hander extends DefaultHandler
{
  private CollectListItem collect;
  private ArrayList<CollectListItem> collectList = new ArrayList();
  private String name;
  private String value = "";
  private StringBuilder valueBuffer = new StringBuilder();

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    super.characters(paramArrayOfChar, paramInt1, paramInt2);
    if (paramInt2 > 0)
    {
      String str = new String(paramArrayOfChar, 0, paramInt2);
      if ((!TextUtils.isEmpty(str)) && (paramArrayOfChar[0] != '\n'))
        this.valueBuffer.append(str);
    }
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    this.value = this.valueBuffer.toString();
    if (paramString3.equals("state"))
      this.collect.state = this.value;
    while (true)
    {
      if (this.valueBuffer.length() > 0)
        this.valueBuffer.delete(0, this.valueBuffer.length());
      super.endElement(paramString1, paramString2, paramString3);
      return;
      if (paramString3.equals("reason"))
      {
        this.collect.reason = this.value;
      }
      else if (paramString3.equals("type"))
      {
        this.collect.type = this.value;
      }
      else if (paramString3.equals("id"))
      {
        this.collect.id = this.value;
      }
      else if (paramString3.equals("name"))
      {
        this.collect.video_name = this.value;
      }
      else if (paramString3.equals("img_h"))
      {
        this.collect.img_h = this.value;
      }
      else if (paramString3.equals("img_s"))
      {
        this.collect.img_s = this.value;
      }
      else if (paramString3.equals("img_v"))
      {
        this.collect.img_v = this.value;
      }
      else if (paramString3.equals("info"))
      {
        this.collect.info = this.value;
      }
      else if (paramString3.equals("video_id"))
      {
        this.collect.video_id = this.value;
      }
      else if (paramString3.equals("video_type"))
      {
        try
        {
          this.collect.video_type = Integer.parseInt(this.value);
        }
        catch (Exception localException10)
        {
          this.collect.video_type = 0;
        }
      }
      else if (paramString3.equals("view_type"))
      {
        try
        {
          this.collect.view_type = Integer.parseInt(this.value);
        }
        catch (Exception localException9)
        {
          this.collect.view_type = 0;
        }
      }
      else if (paramString3.equals("index_ui_style"))
      {
        this.collect.index_ui_style = this.value;
      }
      else if (paramString3.equals("video_index"))
      {
        try
        {
          this.collect.video_index = Integer.parseInt(this.value);
        }
        catch (Exception localException8)
        {
          this.collect.video_index = 0;
        }
      }
      else if (paramString3.equals("sub_name"))
      {
        this.collect.sub_name = this.value;
      }
      else if (paramString3.equals("tstv_begin_time"))
      {
        this.collect.ts_begin = this.value;
        try
        {
          String str = this.value;
          if (str.length() <= 8)
            continue;
          this.collect.ts_day = str.substring(0, 8);
          this.collect.ts_begin = str.substring(8);
        }
        catch (Exception localException7)
        {
        }
      }
      else if (paramString3.equals("tstv_time_len"))
      {
        this.collect.ts_time_len = this.value;
      }
      else if (paramString3.equals("media_assets_id"))
      {
        this.collect.media_assets_id = this.value;
      }
      else if (paramString3.equals("category"))
      {
        this.collect.category_id = this.value;
      }
      else if (paramString3.equals("version"))
      {
        this.collect.version = this.value;
      }
      else if (paramString3.equals("add_time"))
      {
        this.collect.add_time = this.value;
      }
      else if (paramString3.equals("collect_time"))
      {
        this.collect.add_time = this.value;
      }
      else if (paramString3.equals("video_all_index"))
      {
        try
        {
          this.collect.video_all_index = Integer.parseInt(this.value);
        }
        catch (Exception localException6)
        {
          this.collect.video_all_index = 0;
        }
      }
      else if (paramString3.equals("video_new_index"))
      {
        try
        {
          this.collect.update_index = Integer.parseInt(this.value);
        }
        catch (Exception localException5)
        {
          this.collect.update_index = 0;
        }
      }
      else if (paramString3.equals("video_actor"))
      {
        this.collect.video_actor = this.value;
      }
      else if (paramString3.equals("video_director"))
      {
        try
        {
          this.collect.video_director = this.value;
        }
        catch (Exception localException4)
        {
          this.collect.video_director = "";
        }
      }
      else if (paramString3.equals("video_kind"))
      {
        this.collect.video_kind = this.value;
      }
      else if (paramString3.equals("quality"))
      {
        this.collect.quality = this.value;
      }
      else if (paramString3.equals("mark"))
      {
        this.collect.mark = this.value;
      }
      else if (paramString3.equals("corner_mark_img"))
      {
        this.collect.corner_mark_img = this.value;
      }
      else if (paramString3.equals("played_time_len"))
      {
        try
        {
          this.collect.played_time = Integer.parseInt(this.value);
        }
        catch (Exception localException3)
        {
          this.collect.played_time = 0;
        }
      }
      else if (paramString3.equals("time_len"))
      {
        try
        {
          this.collect.duration = Integer.parseInt(this.value);
        }
        catch (Exception localException2)
        {
          this.collect.duration = 1;
        }
      }
      else if (paramString3.equals("online"))
      {
        try
        {
          this.collect.online = Integer.parseInt(this.value);
        }
        catch (Exception localException1)
        {
          this.collect.online = 1;
        }
      }
      else if (paramString3.equals("new_index_release_time"))
      {
        this.collect.new_index_release_time = this.value;
      }
      else if (paramString3.equals("current_index_release_time"))
      {
        this.collect.current_index_release_time = this.value;
      }
      else if (paramString3.equals("i"))
      {
        this.collectList.add(this.collect);
      }
    }
  }

  public ArrayList<CollectListItem> getCollectList()
  {
    return this.collectList;
  }

  public void startDocument()
    throws SAXException
  {
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (paramString3.equals("i"))
      this.collect = new CollectListItem();
    this.name = paramString3;
    super.startElement(paramString1, paramString2, paramString3, paramAttributes);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetCollectV2Hander
 * JD-Core Version:    0.6.2
 */