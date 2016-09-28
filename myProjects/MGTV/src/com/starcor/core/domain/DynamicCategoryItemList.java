package com.starcor.core.domain;

import java.io.Serializable;

public class DynamicCategoryItemList
  implements Serializable
{
  public ContentList contentList = new ContentList();
  public int total;
  public String type;

  public class ContentList
  {
    public String all_index;
    public String asset_import_id;
    public String category_id;
    public String corner_mark;
    public String corner_mark_img;
    public String img_h;
    public String img_s;
    public String img_v;
    public String index_ui_style;
    public String is_show_new_index;
    public String media_assets_id;
    public String name;
    public String played_time_len;
    public String quality;
    public String series_id;
    public String time_len;
    public String tstv_begin_time;
    public String tstv_time_len;
    public String type;
    public String video_id;
    public String video_index;
    public String video_new_index;
    public String video_type;
    public String view_type;

    public ContentList()
    {
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.DynamicCategoryItemList
 * JD-Core Version:    0.6.2
 */