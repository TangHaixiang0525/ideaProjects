package com.starcor.core.domain;

import java.io.Serializable;
import java.util.Comparator;

public class MetadataInfo
  implements Serializable, Cloneable, Comparator<MetadataInfo>
{
  private static final long serialVersionUID = 1L;
  public String action_type = "";
  public String ad = "";
  public String begin_time = "";
  public String category_id = "";
  public String group = "";
  public String img_url = "";
  public String img_url1 = "";
  public String img_url2 = "";
  public String info = "";
  public String name = "";
  public int order;
  public String packet_id = "";
  public String packet_type = "";
  public String time_len = "";
  public String type = "";
  public int uiStyle;
  public String url = "";
  public String video_id = "";
  public String video_index = "";
  public String video_type = "";

  public int compare(MetadataInfo paramMetadataInfo1, MetadataInfo paramMetadataInfo2)
  {
    return paramMetadataInfo1.order - paramMetadataInfo2.order;
  }

  public String toString()
  {
    return "MetadataInfo [type=" + this.type + ", packet_type=" + this.packet_type + ", packet_id=" + this.packet_id + ", category_id=" + this.category_id + ", action_type=" + this.action_type + ", name=" + this.name + ", img_url=" + this.img_url + ", img_url1=" + this.img_url1 + ", img_url2=" + this.img_url2 + ", group=" + this.group + ", url=" + this.url + ", ad=" + this.ad + ", info=" + this.info + ", video_id=" + this.video_id + ", video_type=" + this.video_type + ", video_index=" + this.video_index + ", order=" + this.order + ", begin_time=" + this.begin_time + ", time_len=" + this.time_len + ", uiStyle=" + this.uiStyle + "]";
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.MetadataInfo
 * JD-Core Version:    0.6.2
 */