package com.starcor.jump.bussines.behavior;

import android.util.Log;

public class CommonCmdSelector extends BaseSelector
{
  public static final String CMD_PLAY_TIMESHIFT_VIDEO = "show_timeshift_list";
  public static final String CMD_PLAY_VIDEO = "play_video";
  public static final String CMD_SHOW_APPS_LIST = "show_apps_list";
  public static final String CMD_SHOW_CATEGORY = "show_category";
  public static final String CMD_SHOW_DETAIL = "show_video_detail";
  public static final String CMD_SHOW_FAVORITE = "show_favorite";
  public static final String CMD_SHOW_PLAY_LIST = "show_play_list";
  public static final String CMD_SHOW_SEARCH = "show_search";
  public static final String CMD_SHOW_SPECIAL_PAGE = "show_special_subject";
  public static final String CMD_SHOW_SPECIAL_PAGE_2 = "show_special_page";
  public static final String CMD_SHOW_SPECIAL_WEB_PAGE = "show_special_subject_web";
  public static final String CMD_SHOW_STAR_DETAIL = "open_star_detail";
  public static final String CMD_SHOW_USER_CENTER = "show_user_center_page";
  public static final String CMD_START_APP_BY_PARAMS = "start_app_by_params";
  private static final String TAG = CommonCmdSelector.class.getSimpleName();

  public void selector(String paramString)
  {
    Log.i(TAG, "CommonCmdSelector:" + paramString);
    if (compareKey("show_video_detail", paramString))
      this.extraData.cmd = BehaviorCmd.VIDEO_INFO_SHOW;
    do
    {
      return;
      if (compareKey("play_video", paramString))
      {
        this.extraData.cmd = BehaviorCmd.VIDEO_PLAY;
        return;
      }
      if (compareKey("show_category", paramString))
      {
        this.extraData.cmd = BehaviorCmd.CATEGORY_SHOW;
        return;
      }
      if (compareKey("show_favorite", paramString))
      {
        this.extraData.cmd = BehaviorCmd.COLLECT_LIST_SHOW;
        return;
      }
      if (compareKey("show_search", paramString))
      {
        this.extraData.cmd = BehaviorCmd.SEARCH;
        return;
      }
      if (compareKey("show_play_list", paramString))
      {
        this.extraData.cmd = BehaviorCmd.PLAY_RECORD_SHOW;
        return;
      }
      if (compareKey("show_timeshift_list", paramString))
      {
        this.extraData.cmd = BehaviorCmd.TIME_SHIFT_SHOW;
        return;
      }
      if (compareKey("show_apps_list", paramString))
      {
        this.extraData.cmd = BehaviorCmd.APP_LIST_SHOW;
        return;
      }
      if (compareKey("start_app_by_params", paramString))
      {
        this.extraData.cmd = BehaviorCmd.APP_START_BY_PARAMS;
        return;
      }
      if (compareKey("show_special_subject", paramString))
      {
        this.extraData.cmd = BehaviorCmd.SPECIAL_SHOW;
        return;
      }
      if (compareKey("show_special_subject_web", paramString))
      {
        this.extraData.cmd = BehaviorCmd.SPECIAL_WEB_SHOW;
        return;
      }
      if (compareKey("show_special_page", paramString))
      {
        this.extraData.cmd = BehaviorCmd.SPECIAL_SHOW;
        return;
      }
      if (compareKey("show_user_center_page", paramString))
      {
        this.extraData.cmd = BehaviorCmd.USER_CENTER_SHOW;
        return;
      }
    }
    while (!compareKey("open_star_detail", paramString));
    this.extraData.cmd = BehaviorCmd.STAR_DETAIL_SHOW;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.jump.bussines.behavior.CommonCmdSelector
 * JD-Core Version:    0.6.2
 */