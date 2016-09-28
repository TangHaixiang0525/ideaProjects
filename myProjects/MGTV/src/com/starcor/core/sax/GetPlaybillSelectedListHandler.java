package com.starcor.core.sax;

import android.text.TextUtils;
import com.starcor.core.domain.GetPlaybillSelectedListInfo;
import com.starcor.core.domain.GetPlaybillSelectedListInfo.Item;
import com.starcor.xul.XulUtils;
import java.io.PrintStream;
import java.util.List;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetPlaybillSelectedListHandler extends DefaultHandler
{
  private String curTagName;
  private GetPlaybillSelectedListInfo getPlaybillSelectedListInfo;
  GetPlaybillSelectedListInfo.Item item;
  private String str;

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    super.characters(paramArrayOfChar, paramInt1, paramInt2);
    String str1 = new String(paramArrayOfChar, paramInt1, paramInt2);
    if (!TextUtils.isEmpty(this.curTagName))
    {
      if (this.str != null)
        this.str += str1;
    }
    else
      return;
    this.str = str1;
  }

  public void endDocument()
    throws SAXException
  {
    super.endDocument();
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    super.endElement(paramString1, paramString2, paramString3);
    if (paramString2.equalsIgnoreCase("type"))
      if (this.item != null)
        this.item.type = this.str;
    while (true)
    {
      this.curTagName = null;
      this.str = null;
      return;
      if (paramString2.equalsIgnoreCase("id"))
      {
        if (this.item != null)
          this.item.id = this.str;
      }
      else if (paramString2.equalsIgnoreCase("special_id"))
      {
        if (this.item != null)
          this.item.special_id = this.str;
      }
      else if (paramString2.equalsIgnoreCase("name"))
      {
        if (this.item != null)
          this.item.name = this.str;
      }
      else if (paramString2.equalsIgnoreCase("time_zone"))
      {
        if (this.item != null)
          this.item.time_zone = this.str;
      }
      else if (paramString2.equalsIgnoreCase("ts_limit_min"))
      {
        if (this.item != null)
          this.item.ts_limit_min = this.str;
      }
      else if (paramString2.equalsIgnoreCase("ts_limit_max"))
      {
        if (this.item != null)
          this.item.ts_limit_max = this.str;
      }
      else if (paramString2.equalsIgnoreCase("ts_default_pos"))
      {
        if (this.item != null)
          this.item.ts_default_pos = this.str;
      }
      else if (paramString2.equalsIgnoreCase("video_type"))
      {
        if (this.item != null)
          this.item.video_type = this.str;
      }
      else if (paramString2.equalsIgnoreCase("day"))
      {
        if (this.item != null)
          this.item.day = this.str;
      }
      else if (paramString2.equalsIgnoreCase("begin_time"))
      {
        if (this.item != null)
          this.item.begin_time = this.str;
        else
          this.getPlaybillSelectedListInfo.begin_time = this.str;
      }
      else if (paramString2.equalsIgnoreCase("time_len"))
      {
        if (this.item != null)
          this.item.time_len = this.str;
      }
      else if (paramString2.equalsIgnoreCase("img_h"))
      {
        if (this.item != null)
          this.item.img_h = this.str;
      }
      else if (paramString2.equalsIgnoreCase("img_s"))
      {
        if (this.item != null)
          this.item.img_s = this.str;
      }
      else if (paramString2.equalsIgnoreCase("img_v"))
      {
        if (this.item != null)
          this.item.img_v = this.str;
      }
      else if (paramString2.equalsIgnoreCase("user_score"))
      {
        if (this.item != null)
          this.item.user_score = XulUtils.tryParseFloat(this.str, 0.0F);
      }
      else if (paramString2.equalsIgnoreCase("ui_style"))
      {
        if (this.item != null)
          this.item.ui_style = XulUtils.tryParseInt(this.str);
      }
      else if (paramString2.equalsIgnoreCase("media_assets_id"))
      {
        if (this.item != null)
          this.item.media_assets_id = this.str;
      }
      else if (paramString2.equalsIgnoreCase("category_id"))
      {
        if (this.item != null)
          this.item.category_id = this.str;
      }
      else if (paramString2.equalsIgnoreCase("video_id"))
      {
        if (this.item != null)
          this.item.video_id = this.str;
      }
      else if (paramString2.equalsIgnoreCase("huawei_cid"))
      {
        if (this.item != null)
          this.item.huawei_cid = this.str;
      }
      else if (paramString2.equalsIgnoreCase("play_count"))
      {
        if (this.item != null)
          this.item.play_count = this.str;
      }
      else if (paramString2.equalsIgnoreCase("play_type"))
      {
        if (this.item != null)
          this.item.play_type = this.str;
      }
      else if (paramString2.equalsIgnoreCase("label_id"))
      {
        if (this.item != null)
          this.item.label_id = this.str;
      }
      else if (paramString2.equalsIgnoreCase("summary"))
      {
        if (this.item != null)
          this.item.summary = this.str;
      }
      else if (paramString2.equalsIgnoreCase("online_mode"))
      {
        if (this.item != null)
          this.item.online_mode = this.str;
      }
      else if (paramString2.equalsIgnoreCase("poster"))
      {
        if (this.item != null)
          this.item.poster = this.str;
      }
      else if (paramString2.equalsIgnoreCase("end_time"))
      {
        if (this.item != null)
          this.item.end_time = this.str;
        else
          this.getPlaybillSelectedListInfo.end_time = this.str;
      }
      else if (paramString2.equalsIgnoreCase("info"))
      {
        if (this.item != null)
          this.item.info = this.str;
      }
      else if (paramString2.equalsIgnoreCase("video_actor"))
      {
        if (this.item != null)
          this.item.video_actor = this.str;
      }
      else if (paramString2.equalsIgnoreCase("url"))
      {
        if (this.item != null)
          this.item.url = this.str;
      }
      else if (paramString2.equalsIgnoreCase("cur_page"))
        this.getPlaybillSelectedListInfo.cur_page = this.str;
      else if (paramString2.equalsIgnoreCase("total_page"))
        this.getPlaybillSelectedListInfo.total_page = this.str;
      else if (paramString2.equalsIgnoreCase("total_rows"))
        this.getPlaybillSelectedListInfo.total_rows = this.str;
      else if (paramString2.equalsIgnoreCase("state"))
        this.getPlaybillSelectedListInfo.state = this.str;
      else if (paramString2.equalsIgnoreCase("reason"))
        this.getPlaybillSelectedListInfo.reason = this.str;
      else if (paramString2.equalsIgnoreCase("i"))
        this.item = null;
      else if (paramString2.equalsIgnoreCase("estimateId"))
        this.getPlaybillSelectedListInfo.estimateId = this.str;
      else if (paramString2.equalsIgnoreCase("artithmeticId"))
        this.getPlaybillSelectedListInfo.artithmeticId = this.str;
      else if (paramString2.equalsIgnoreCase("nns_asset_import_id"))
        this.item.nns_asset_import_id = this.str;
      else if (paramString2.equalsIgnoreCase("series_id"))
        this.item.series_id = this.str;
      else if (paramString2.equalsIgnoreCase("abstract"))
        this.item.abstractInfo = this.str;
      else if ((paramString2.equalsIgnoreCase("mark")) || (paramString2.equalsIgnoreCase("corner_mark")))
      {
        if (this.item != null)
          this.item.mark = this.str;
      }
      else if (((paramString2.equalsIgnoreCase("mark_img")) || (paramString2.equalsIgnoreCase("corner_mark_img"))) && (this.item != null))
        this.item.mark_img = this.str;
    }
  }

  public GetPlaybillSelectedListInfo getGetPlaybillSelectedListInfo()
  {
    System.out.println();
    return this.getPlaybillSelectedListInfo;
  }

  public void startDocument()
    throws SAXException
  {
    this.getPlaybillSelectedListInfo = new GetPlaybillSelectedListInfo();
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    if (paramString2.equalsIgnoreCase("i"))
    {
      this.item = new GetPlaybillSelectedListInfo.Item();
      this.getPlaybillSelectedListInfo.items.add(this.item);
    }
    this.curTagName = paramString2;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetPlaybillSelectedListHandler
 * JD-Core Version:    0.6.2
 */