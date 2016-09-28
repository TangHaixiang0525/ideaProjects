package com.starcor.core.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GetPlaybillSelectedListInfo
  implements Serializable
{
  public String artithmeticId = "";
  public String begin_time = "";
  public String cur_page = "";
  public String end_time = "";
  public String estimateId = "";
  public List<Item> items = new ArrayList();
  public int pageIndex = 0;
  public String reason = "";
  public String state = "";
  public String total_page = "";
  public String total_rows = "";

  public String toString()
  {
    return this.items.toString();
  }

  public static class Item
  {
    public String abstractInfo = "";
    public String begin_time = "";
    public String category_id = "";
    public String day = "";
    public String end_time = "";
    public String huawei_cid = "";
    public String id = "";
    public String img_h = "";
    public String img_s = "";
    public String img_v = "";
    public String info = "";
    public String label_id = "";
    public String mark = "";
    public String mark_img = "";
    public String media_assets_id = "";
    public String name = "";
    public String nns_asset_import_id = "";
    public String online_mode = "";
    public String play_count = "0";
    public String play_type = "";
    public String poster = "";
    public String series_id = "";
    public String special_id = "";
    public String summary = "";
    public String time_len = "";
    public String time_zone = "";
    public String ts_default_pos = "";
    public String ts_limit_max = "";
    public String ts_limit_min = "";
    public String type = "";
    public int ui_style;
    public String url = "";
    public float user_score;
    public String video_actor = "";
    public String video_id = "";
    public String video_type = "";

    public String toString()
    {
      return "Item{type='" + this.type + '\'' + ", id='" + this.id + '\'' + ", id='" + this.special_id + '\'' + ", name='" + this.name + '\'' + ", img_h='" + this.img_h + '\'' + ", img_s='" + this.img_s + '\'' + ", img_v='" + this.img_v + '\'' + ", user_score=" + this.user_score + ", time_zone='" + this.time_zone + '\'' + ", ts_limit_min='" + this.ts_limit_min + '\'' + ", ts_limit_max='" + this.ts_limit_max + '\'' + ", ts_default_pos='" + this.ts_default_pos + '\'' + ", video_type='" + this.video_type + '\'' + ", day='" + this.day + '\'' + ", begin_time='" + this.begin_time + '\'' + ", time_len='" + this.time_len + '\'' + ", ui_style=" + this.ui_style + ", media_assets_id='" + this.media_assets_id + '\'' + ", category_id='" + this.category_id + '\'' + ", video_id='" + this.video_id + '\'' + ", huawei_cid='" + this.huawei_cid + '\'' + ", play_count='" + this.play_count + '\'' + ", url='" + this.url + '\'' + ", play_type='" + this.play_type + '\'' + ", summary='" + this.summary + '\'' + ", online_mode='" + this.online_mode + '\'' + ", poster='" + this.poster + '\'' + ", end_time='" + this.end_time + '\'' + ", info='" + this.info + '\'' + ", video_actor='" + this.video_actor + '\'' + ", label_id='" + this.label_id + '\'' + ", mark_img='" + this.mark_img + '\'' + ", mark='" + this.mark + '\'' + ", nns_asset_import_id='" + this.nns_asset_import_id + '\'' + ", series_id='" + this.series_id + '\'' + ", abstractInfo='" + this.abstractInfo + '\'' + '}';
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.GetPlaybillSelectedListInfo
 * JD-Core Version:    0.6.2
 */