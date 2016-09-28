package com.starcor.jump.bussines.behavior;

import android.util.Log;

public class jsonActionSelector extends BaseSelector
{
  private static final String TAG = jsonActionSelector.class.getSimpleName();

  public void selector(String paramString)
  {
    Log.i(TAG, "jsonActionSelector:" + paramString);
    if (compareKey("m_open_detail_page", paramString))
      this.extraData.cmd = BehaviorCmd.VIDEO_INFO_SHOW;
    do
    {
      return;
      if (compareKey("m_open_player", paramString))
      {
        this.extraData.cmd = BehaviorCmd.VIDEO_PLAY;
        return;
      }
      if (compareKey("m_open_asset_page", paramString))
      {
        this.extraData.cmd = BehaviorCmd.CATEGORY_SHOW;
        return;
      }
      if (compareKey("m_user_manager_page", paramString))
      {
        this.extraData.cmd = BehaviorCmd.USER_CENTER_SHOW;
        return;
      }
      if (compareKey("m_open_tstv_page", paramString))
      {
        this.extraData.cmd = BehaviorCmd.TIME_SHIFT_SHOW;
        return;
      }
    }
    while (!compareKey("m_open_special_page", paramString));
    this.extraData.cmd = BehaviorCmd.SPECIAL_SHOW;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.jump.bussines.behavior.jsonActionSelector
 * JD-Core Version:    0.6.2
 */