package com.starcor.jump.bussines.behavior;

import android.text.TextUtils;
import android.util.Log;
import com.starcor.jump.bussines.data.CommonData;

public class ActionSelector extends BaseSelector
{
  private static final String RECEIVE_BROADCAST_ACTION = "com.starcor.hunan.hmd.playvideo";
  private static final String RECEIVE_HMD_RECORD = "com.starcor.hunan.hmd.record";
  public static final String RECEIVE_TEST = "com.starcor.hunan.test";
  public static final String RECEIVE_TEST_2 = "com.starcor.hunan.test2";
  public static final String RECEIVE_THIRD_DELET_ALL = "com.tv.tothird.delall";
  private static final String TAG = ActionSelector.class.getSimpleName();
  public static final String TCL_2995_ACTION_DEL_COLLECT = "com.tv.favorite.del.toapp";
  public static final String TCL_2995_ACTION_DEL_PLAY_LIST = "com.tv.history.del.toapp";
  public static final String TCL_2995_LIVE_REQUEST = "com.tv.live.request";

  public void selector(String paramString)
  {
    if (compareKey("com.starcor.hunan.hmd.playvideo", paramString))
    {
      this.extraData.cmd = BehaviorCmd.VIDEO_PLAY;
      this.extraData.what = 10;
    }
    do
    {
      return;
      if (compareKey("com.starcor.hunan.hmd.record", paramString))
      {
        this.extraData.cmd = BehaviorCmd.VIDEO_INFO_SHOW;
        this.extraData.what = 10;
        return;
      }
      if (compareKey("com.starcor.hunan.test", paramString))
      {
        this.extraData.cmd = BehaviorCmd.TEST_1;
        return;
      }
      if (compareKey("com.starcor.hunan.test2", paramString))
      {
        this.extraData.cmd = BehaviorCmd.TEST_2;
        return;
      }
      if (compareKey("com.tv.favorite.del.toapp", paramString))
      {
        Log.i(TAG, "buildCommand:TCL_2995_ACTION_DEL_COLLECT");
        this.extraData.cmd = BehaviorCmd.RECORD_OPERATOR;
        if (TextUtils.isEmpty(this.mBussinesData.videoId))
        {
          this.extraData.what = 6;
          return;
        }
        this.extraData.what = 5;
        return;
      }
      if (compareKey("com.tv.history.del.toapp", paramString))
      {
        Log.i(TAG, "buildCommand:TCL_2995_ACTION_DEL_PLAY_LIST");
        this.extraData.cmd = BehaviorCmd.RECORD_OPERATOR;
        if (TextUtils.isEmpty(this.mBussinesData.videoId))
        {
          this.extraData.what = 3;
          return;
        }
        this.extraData.what = 2;
        return;
      }
      if (compareKey("com.tv.live.request", paramString))
      {
        Log.i(TAG, "buildCommand:TCL_2995_LIVE_REQUEST");
        this.extraData.cmd = BehaviorCmd.TIME_SHIFT_SHOW;
        return;
      }
    }
    while (!compareKey("com.tv.tothird.delall", paramString));
    Log.i(TAG, "buildCommand:RECEIVE_THIRD_DELET_ALL");
    this.extraData.cmd = BehaviorCmd.RECORD_OPERATOR;
    this.extraData.what = 10;
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.jump.bussines.behavior.ActionSelector
 * JD-Core Version:    0.6.2
 */