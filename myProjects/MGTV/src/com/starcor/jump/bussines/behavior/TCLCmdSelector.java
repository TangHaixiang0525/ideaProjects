package com.starcor.jump.bussines.behavior;

import android.text.TextUtils;
import android.util.Log;
import com.starcor.common.IntentDataManager;
import com.starcor.jump.bussines.data.CommonData;

public class TCLCmdSelector extends BaseSelector
{
  private static final String TAG = TCLCmdSelector.class.getSimpleName();
  public static final String TCL_2995_ACTION_DEL_COLLECT = "com.tv.favorite.del.toapp";
  public static final String TCL_2995_ACTION_DEL_PLAY_LIST = "com.tv.history.del.toapp";
  private static final String TCL_CMD_STRING_ACTORINFO = "actorinfo";
  public static final String TCL_CMD_STRING_PLAYVIDEO = "playvideo";
  private static final String TCL_CMD_STRING_SHOWVIDEODETAIL = "showvideodetail";
  private static final String TCL_CMD_STRING_USERCENTER = "usercenter";
  private static final String TCL_CMD_STRING_VOICECONTROL = "voicecontrol";

  public void selector(String paramString)
  {
    Log.i(TAG, "buildCommand:TCL_CMD_STRING_VOICECONTROL");
    if (compareKey("playvideo", paramString))
    {
      Log.i(TAG, "buildCommand:TCL_CMD_STRING_PLAYVIDEO");
      this.extraData.cmd = BehaviorCmd.VIDEO_PLAY;
    }
    do
    {
      do
      {
        return;
        if (compareKey("showvideodetail", paramString))
        {
          Log.i(TAG, "buildCommand:TCL_CMD_STRING_SHOWVIDEODETAIL");
          this.extraData.cmd = BehaviorCmd.VIDEO_INFO_SHOW;
          return;
        }
        if (!compareKey("voicecontrol", paramString))
          break;
        Log.i(TAG, "buildCommand:TCL_CMD_STRING_VOICECONTROL");
        if (!TextUtils.isEmpty(this.mBussinesData.manager.getStringValue("subjectId")))
        {
          this.extraData.cmd = BehaviorCmd.SPECIAL_SHOW;
          return;
        }
        if (!TextUtils.isEmpty(this.mBussinesData.manager.getStringValue("channelId")))
        {
          this.extraData.cmd = BehaviorCmd.TIME_SHIFT_SHOW;
          return;
        }
      }
      while (TextUtils.isEmpty(this.mBussinesData.manager.getStringValue("category")));
      this.extraData.cmd = BehaviorCmd.CATEGORY_SHOW;
      return;
      if (compareKey("usercenter", paramString))
      {
        Log.i(TAG, "buildCommand:TCL_CMD_STRING_USERCENTER");
        this.extraData.cmd = BehaviorCmd.USER_CENTER_SHOW;
        return;
      }
    }
    while ((!compareKey("actorinfo", paramString)) || (!TextUtils.isEmpty(this.mBussinesData.manager.getStringValue("name"))));
    this.extraData.cmd = null;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.jump.bussines.behavior.TCLCmdSelector
 * JD-Core Version:    0.6.2
 */