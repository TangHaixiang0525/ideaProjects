package com.starcor.core.domain;

import android.text.TextUtils;
import java.io.Serializable;
import java.util.ArrayList;

public class VideoInfo
  implements Serializable, Cloneable
{
  private static final long serialVersionUID = 1L;
  public String abstractInfo;
  public String actor = "";
  public ArrayList<VideoRoleInfo> actorList;
  public String area = "";
  public int asset_type;
  public int billing = 0;
  public String categoryId = "";
  public String corner_mark;
  public String corner_mark_img_url;
  public int coupon_count;
  public String definition;
  public String desc = "";
  public String director = "";
  public ArrayList<VideoRoleInfo> directorList;
  public String film_name;
  public String huawei_cid = "";
  public String id = "";
  public String imgBigUrl = "";
  public String imgHUrl = "";
  public String imgNormalUrl = "";
  public String imgSUrl = "";
  public String imgSmallUrl = "";
  public String imgVUrl = "";
  public String import_id = "";
  public int indexCount;
  public int indexCurrent;
  public ArrayList<VideoIndex> indexList = new ArrayList();
  public int index_num = 0;
  public String index_order;
  public String index_ui_type = "";
  public String is_charge = "";
  public String is_recommend = "";
  public int is_show_index;
  public int is_show_new_index;
  public String is_trylook = "";
  public boolean is_useCoupon;
  public boolean isfrom_special = false;
  public String keyWords = "";
  public String kind = "";
  public String mark;
  public String name = "";
  public String packageId = "";
  public int play_count;
  public float point;
  public int score_times;
  public String serial_id;
  public String showTime = "";
  public String special_id = "";
  public String timeLen = "";
  public int tsDefaultPos = 0;
  public int tsLimitMax = 0;
  public int tsLimitMin = 0;
  public int uiStyle;
  public double user_score;
  public String videoId = "";
  public int videoType;
  public int view_type;
  public String vip_id = "";
  public String watch_focus;

  protected Object clone()
    throws CloneNotSupportedException
  {
    return super.clone();
  }

  public String getImgUrl()
  {
    String str = "";
    if (!TextUtils.isEmpty(this.imgVUrl))
      str = this.imgVUrl;
    do
    {
      return str;
      if (!TextUtils.isEmpty(this.imgSUrl))
        return this.imgSUrl;
    }
    while (TextUtils.isEmpty(this.imgHUrl));
    return this.imgHUrl;
  }

  public String toString()
  {
    return "VideoInfo{videoId='" + this.videoId + '\'' + ", videoType=" + this.videoType + ", huawei_cid='" + this.huawei_cid + '\'' + ", view_type=" + this.view_type + ", is_recommend='" + this.is_recommend + '\'' + ", is_charge='" + this.is_charge + '\'' + ", is_trylook='" + this.is_trylook + '\'' + ", is_useCoupon=" + this.is_useCoupon + ", coupon_count=" + this.coupon_count + ", asset_type=" + this.asset_type + ", vip_id='" + this.vip_id + '\'' + ", special_id='" + this.special_id + '\'' + ", isfrom_special=" + this.isfrom_special + ", id='" + this.id + '\'' + ", index_ui_type='" + this.index_ui_type + '\'' + ", name='" + this.name + '\'' + ", area='" + this.area + '\'' + ", actor='" + this.actor + '\'' + ", director='" + this.director + '\'' + ", showTime='" + this.showTime + '\'' + ", timeLen='" + this.timeLen + '\'' + ", desc='" + this.desc + '\'' + ", kind='" + this.kind + '\'' + ", point=" + this.point + ", uiStyle=" + this.uiStyle + ", imgNormalUrl='" + this.imgNormalUrl + '\'' + ", imgBigUrl='" + this.imgBigUrl + '\'' + ", imgSmallUrl='" + this.imgSmallUrl + '\'' + ", imgVUrl='" + this.imgVUrl + '\'' + ", imgSUrl='" + this.imgSUrl + '\'' + ", imgHUrl='" + this.imgHUrl + '\'' + ", indexCount=" + this.indexCount + ", indexCurrent=" + this.indexCurrent + ", index_num=" + this.index_num + ", packageId='" + this.packageId + '\'' + ", categoryId='" + this.categoryId + '\'' + ", tsLimitMin=" + this.tsLimitMin + ", tsLimitMax=" + this.tsLimitMax + ", tsDefaultPos=" + this.tsDefaultPos + ", import_id='" + this.import_id + '\'' + ", keyWords='" + this.keyWords + '\'' + ", film_name='" + this.film_name + '\'' + ", user_score=" + this.user_score + ", play_count=" + this.play_count + ", score_times=" + this.score_times + ", actorList=" + this.actorList + ", directorList=" + this.directorList + ", indexList=" + this.indexList + ", billing=" + this.billing + ", corner_mark='" + this.corner_mark + '\'' + ", corner_mark_img_url='" + this.corner_mark_img_url + '\'' + ", serial_id='" + this.serial_id + '\'' + ", watch_focus='" + this.watch_focus + '\'' + ", abstractInfo='" + this.abstractInfo + '\'' + ", definition='" + this.definition + '\'' + ", is_show_new_index=" + this.is_show_new_index + ", index_order='" + this.index_order + '\'' + ", mark='" + this.mark + '\'' + ", is_show_index=" + this.is_show_index + '}';
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.VideoInfo
 * JD-Core Version:    0.6.2
 */