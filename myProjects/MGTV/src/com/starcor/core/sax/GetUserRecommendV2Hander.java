package com.starcor.core.sax;

import com.starcor.core.domain.UserRecommendV2Item;
import com.starcor.core.utils.Logger;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class GetUserRecommendV2Hander extends DefaultHandler
{
  private UserRecommendV2Item item;
  private ArrayList<UserRecommendV2Item> list = new ArrayList();
  private boolean mark = true;
  private String ohitid = "";
  private String reqid = "";
  private StringBuilder sb = new StringBuilder();
  private String value = "";
  private String ver = "";

  public void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    throws SAXException
  {
    this.sb.append(paramArrayOfChar, paramInt1, paramInt2);
    this.value = this.sb.toString();
    super.characters(paramArrayOfChar, paramInt1, paramInt2);
  }

  public void endElement(String paramString1, String paramString2, String paramString3)
    throws SAXException
  {
    if ((this.item == null) && (this.mark));
    do
    {
      return;
      if (paramString3.equals("estimateId"))
        this.reqid = this.value;
      if (paramString3.equals("artithmeticId"))
        this.ver = this.value;
      if (paramString3.equals("asset_import_id"))
        this.item.ohitid = this.value;
      if (paramString3.equals("video_id"))
        this.item.video_id = this.value;
      if (paramString3.equals("name"))
        this.item.name = this.value;
      if (paramString3.equals("video_type"))
        this.item.video_type = this.value;
      if (paramString3.equals("img_h"))
        this.item.img_h = this.value;
      if (paramString3.equals("img_s"))
        this.item.img_s = this.value;
      if (paramString3.equals("img_v"))
        this.item.img_v = this.value;
      if (paramString3.equals("play_count"))
        this.item.play_count = this.value;
      if (paramString3.equals("media_assets_id"))
        this.item.media_assets_id = this.value;
      if (paramString3.equals("category_id"))
        this.item.category_id = this.value;
      if (paramString3.equals("online_time"))
        this.item.online_time = this.value;
      if (paramString3.equals("ui_style"))
        this.item.ui_style = this.value;
      if (paramString3.equals("point"))
        this.item.ui_style = this.value;
      if (paramString3.equals("user_score"))
        this.item.user_score = this.value;
      if (paramString3.equals("new_index"))
        this.item.new_index = this.value;
      if (paramString3.equals("all_index"))
        this.item.all_index = this.value;
      if (paramString3.equals("view_type"))
        this.item.view_type = this.value;
      if (paramString3.equals("corner_mark"))
        this.item.corner_mark = this.value;
      if (paramString3.equals("corner_mark_img"))
        this.item.corner_mark_img = this.value;
      if (paramString3.equals("desc"))
        this.item.desc = this.value;
      if (paramString3.equals("billing"))
        this.item.billing = this.value;
      if (paramString3.equals("price_type"))
        this.item.price_type = this.value;
      if (paramString3.equals("price_value"))
        this.item.price_value = this.value;
      if (paramString3.equals("price_code"))
        this.item.price_code = this.value;
      if (paramString3.equals("series_id"))
        this.item.sohitid = this.value;
    }
    while (!paramString3.equals("i"));
    this.item.ver = this.ver;
    this.item.reqid = this.reqid;
    this.list.add(this.item);
    this.mark = true;
  }

  public ArrayList<UserRecommendV2Item> getUserRecommendList()
  {
    return this.list;
  }

  public void startDocument()
    throws SAXException
  {
    super.startDocument();
  }

  public void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    throws SAXException
  {
    this.sb.setLength(0);
    if (paramString2.equals("i"))
    {
      this.item = new UserRecommendV2Item();
      Logger.i("new item" + this.item);
    }
    if (paramString2.equals("epg"))
      this.mark = false;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.sax.GetUserRecommendV2Hander
 * JD-Core Version:    0.6.2
 */