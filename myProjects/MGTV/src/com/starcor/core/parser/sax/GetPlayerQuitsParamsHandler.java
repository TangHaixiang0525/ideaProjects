package com.starcor.core.parser.sax;

import com.starcor.core.domain.PlayerQuitsVideoInfo;
import com.starcor.core.domain.QuitVideoIndexsParams;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetPlayerQuitsParamsHandler extends DefaultHandler
{
  private String courrentTag;
  private boolean flag = false;
  private PlayerQuitsVideoInfo item;
  private QuitVideoIndexsParams params;
  private StringBuffer sb = new StringBuffer();

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    super.characters(paramArrayOfChar, paramInt1, paramInt2);
    if (!this.flag);
    String str;
    do
    {
      do
      {
        return;
        this.sb.append(paramArrayOfChar, paramInt1, paramInt2);
        str = this.sb.toString();
      }
      while (str == null);
      if ("estimateId".equals(this.courrentTag))
      {
        this.params.estimateId = str;
        return;
      }
      if ("count".equals(this.courrentTag))
      {
        this.params.video_count = Integer.parseInt(str);
        return;
      }
      if ("artithmeticId".equals(this.courrentTag))
      {
        this.params.artithmeticId = str;
        return;
      }
      if ("asset_import_id".equals(this.courrentTag))
      {
        this.item.assets_import_id = str;
        return;
      }
      if ("id".equals(this.courrentTag))
      {
        this.item.id = str;
        return;
      }
      if ("name".equals(this.courrentTag))
      {
        this.item.name = str;
        return;
      }
      if ("series_id".equals(this.courrentTag))
      {
        this.item.serial_id = str;
        return;
      }
      if ("img_h".equals(this.courrentTag))
      {
        this.item.img_h = str;
        return;
      }
      if ("img_v".equals(this.courrentTag))
      {
        this.item.img_v = str;
        return;
      }
      if ("desc".equals(this.courrentTag))
      {
        this.item.desc = str;
        return;
      }
      if ("media_assets_id".equals(this.courrentTag))
      {
        this.item.media_assets_id = str;
        return;
      }
      if ("category_id".equals(this.courrentTag))
      {
        this.item.category_id = str;
        return;
      }
      if ("video_type".equals(this.courrentTag))
      {
        this.item.video_type = str;
        return;
      }
      if ("view_type".equals(this.courrentTag))
      {
        this.item.view_type = str;
        return;
      }
      if ("point".equals(this.courrentTag))
      {
        this.item.point = str;
        return;
      }
      if ("online_time".equals(this.courrentTag))
      {
        this.item.online_time = str;
        return;
      }
      if ("time_len".equals(this.courrentTag))
      {
        this.item.time_len = Integer.parseInt(str);
        return;
      }
      if ("corner_mark".equals(this.courrentTag))
      {
        this.item.corner_mark = str;
        return;
      }
      if ("corner_mark_img".equals(this.courrentTag))
      {
        this.item.corner_mark_img = str;
        return;
      }
      if ("video_actor".equals(this.courrentTag))
      {
        this.item.video_actor = str;
        return;
      }
      if ("video_director".equals(this.courrentTag))
      {
        this.item.video_director = str;
        return;
      }
      if ("new_index".equals(this.courrentTag))
      {
        this.item.new_index = str;
        return;
      }
      if ("all_index".equals(this.courrentTag))
      {
        this.item.all_index = str;
        return;
      }
      if ("play_count".equals(this.courrentTag))
      {
        this.item.play_count = str;
        return;
      }
      if ("collect_count".equals(this.courrentTag))
      {
        this.item.collect_count = str;
        return;
      }
    }
    while (!"user_score".equals(this.courrentTag));
    this.item.user_score = str;
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    super.endElement(paramString1, paramString2, paramString3);
    this.flag = false;
    if (paramString2.equalsIgnoreCase("i"))
      this.params.videoList.add(this.item);
  }

  public QuitVideoIndexsParams getParams()
  {
    return this.params;
  }

  public void startDocument()
    throws SAXException
  {
    this.params = new QuitVideoIndexsParams();
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    this.sb.setLength(0);
    this.courrentTag = paramString2;
    this.flag = true;
    if (paramString2.equalsIgnoreCase("i"))
      this.item = new PlayerQuitsVideoInfo();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.parser.sax.GetPlayerQuitsParamsHandler
 * JD-Core Version:    0.6.2
 */