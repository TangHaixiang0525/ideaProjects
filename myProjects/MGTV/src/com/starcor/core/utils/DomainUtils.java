package com.starcor.core.utils;

import com.starcor.core.domain.FilmListItem;
import com.starcor.core.domain.SpecialTopicPkgCntLstStruct;

public class DomainUtils
{
  private static final String TAG = DomainUtils.class.getSimpleName();

  public static FilmListItem specialTopicPkgCntLstStruct2FilmListItem(SpecialTopicPkgCntLstStruct paramSpecialTopicPkgCntLstStruct)
  {
    if (paramSpecialTopicPkgCntLstStruct == null)
      return null;
    FilmListItem localFilmListItem = new FilmListItem();
    localFilmListItem.big_img_url = paramSpecialTopicPkgCntLstStruct.img0;
    localFilmListItem.video_id = paramSpecialTopicPkgCntLstStruct.video_id;
    localFilmListItem.package_id = paramSpecialTopicPkgCntLstStruct.packet_id;
    localFilmListItem.category_id = paramSpecialTopicPkgCntLstStruct.packet_category_id;
    localFilmListItem.uiStyle = paramSpecialTopicPkgCntLstStruct.ui_style;
    localFilmListItem.film_name = paramSpecialTopicPkgCntLstStruct.name;
    try
    {
      localFilmListItem.video_type = Integer.valueOf(paramSpecialTopicPkgCntLstStruct.video_type).intValue();
      return localFilmListItem;
    }
    catch (Exception localException)
    {
      Logger.e(TAG, "video_type NumberFormatException video_type:" + paramSpecialTopicPkgCntLstStruct.video_type);
    }
    return localFilmListItem;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.utils.DomainUtils
 * JD-Core Version:    0.6.2
 */