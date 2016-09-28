package com.starcor.core.domain;

import java.io.Serializable;

public class CategoryPoster
  implements Serializable
{
  public static final String POSTER_TYPE_INDEX = "index";
  public static final String POSTER_TYPE_SPECIAL = "special";
  public static final String POSTER_TYPE_STAR = "actor_star";
  public static final String POSTER_TYPE_TOP10 = "charts_video";
  public static final String POSTER_TYPE_VIDEO = "video";
  public static final String SPECIAL_ONLINE_MODE_HORIZONTAL = "S1";
  public static final String SPECIAL_ONLINE_MODE_VERTICAL = "S2";
  public static final String SPECIAL_PLAY_TYPE_AUTO = "auto";
  public static final String SPECIAL_PLAY_TYPE_SINGLE = "single";
  public static final int SPECIAL_TYPE_LIVESHOW = 2;
  public static final int SPECIAL_TYPE_NORMAL = 0;
  public static final int SPECIAL_TYPE_PMP = 4;
  public static final int SPECIAL_TYPE_TEMPLATE = 3;
  public static final int SPECIAL_TYPE_WEB = 1;
  public String abstractInfo = "";
  public int allIndex = 0;
  public String categoryId = "";
  public int categoryType = -1;
  public int collectCount = 0;
  public String cornerMarkImg = "";
  public String definition = "";
  public String id = "";
  public String img_h = "";
  public String img_s = "";
  public String img_v = "";
  public String importId = "";
  public String indexUiStyle = "";
  public String name = "";
  public int newIndex = 0;
  public String onlineTime = "";
  public String packetId = "";
  public int playCount = 0;
  public int playedTime = 0;
  public String point = "";
  public String posterType = "video";
  public String score = "";
  public String seriesId = "";
  public String specialBkg = "";
  public String specialExName = "";
  public String specialOnlineMode = "S1";
  public String specialPlayType = "";
  public String specialSummary = "";
  public int specialType = 0;
  public String specialUrl = "";
  public String starAliasName = "";
  public String starArea = "";
  public String starCountry = "";
  public String starEnglishName = "";
  public String starLabelId = "";
  public String starOldName = "";
  public String starProfession = "";
  public String starSex = "";
  public String starWorks = "";
  public int timeLength = 0;
  public String uiStyle = "";
  public String videoActor = "";
  public String videoDirector = "";
  public int videoType = 0;
  public int viewType = -1;

  public boolean isPlayRecordChanged(CategoryPoster paramCategoryPoster)
  {
    if (paramCategoryPoster == null);
    while ((!this.id.equals(paramCategoryPoster.id)) || (this.playedTime != paramCategoryPoster.playedTime))
      return true;
    return false;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.domain.CategoryPoster
 * JD-Core Version:    0.6.2
 */