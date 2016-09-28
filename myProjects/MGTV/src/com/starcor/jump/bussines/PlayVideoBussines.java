package com.starcor.jump.bussines;

import android.text.TextUtils;
import android.util.Log;
import com.starcor.common.IntentDataManager;
import com.starcor.config.AppFuncCfg;
import com.starcor.core.domain.PlayerIntentParams;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.InitApiActivity;
import com.starcor.hunan.uilogic.ActivityAdapter;
import com.starcor.jump.bussines.data.CommonData;
import org.json.JSONObject;

public class PlayVideoBussines extends CommonBussines
{
  private static final String TAG = PlayVideoBussines.class.getSimpleName();

  private void initSpecialData()
    throws Exception
  {
    String str = this._data.manager.getStringValue("cmd");
    if (TextUtils.isEmpty(str))
      return;
    JSONObject localJSONObject = new JSONObject(str);
    this._data.videoId = localJSONObject.getString("video_id");
    this._data.videoType = localJSONObject.getInt("video_type");
    this._data.videoIndex = localJSONObject.getInt("video_index");
    this._data.videoIndexName = localJSONObject.getString("video_index_name");
    this._data.videoUiStyle = localJSONObject.getInt("video_ui_style");
    if (localJSONObject.has("video_begin_time"))
      this._data.videoBeginTime = localJSONObject.getString("video_begin_time");
    if (localJSONObject.has("current_position"))
      this._data.videoPlayedTime = localJSONObject.getInt("current_position");
    while (true)
    {
      this._data.videoDuration = localJSONObject.getInt("video_duration");
      if (!localJSONObject.has("play_video_direct"))
        break;
      this._data.videoPlayDirect = localJSONObject.getInt("play_video_direct");
      return;
      if (localJSONObject.has("played_time"))
        this._data.videoPlayedTime = localJSONObject.getInt("played_time");
    }
    this._data.videoPlayDirect = 0;
  }

  private void processForPlay()
  {
    Logger.i(TAG, "playVideo play");
    PlayerIntentParams localPlayerIntentParams = new PlayerIntentParams();
    localPlayerIntentParams.nns_index = this._data.videoIndex;
    localPlayerIntentParams.nns_timeLen = (this._data.videoDuration + "");
    localPlayerIntentParams.played_time = this._data.videoPlayedTime;
    localPlayerIntentParams.nns_videoInfo = new VideoInfo();
    localPlayerIntentParams.nns_videoInfo.videoId = this._data.videoId;
    localPlayerIntentParams.nns_videoInfo.videoType = 0;
    localPlayerIntentParams.nns_videoInfo.name = this._data.videoIndexName;
    localPlayerIntentParams.nns_videoInfo.film_name = this._data.videoIndexName;
    putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_FLAG, localPlayerIntentParams);
    if ((!this._data.isFromWeiXin) && (!this._data.isFromWeb));
    for (int i = 1; (AppFuncCfg.FUNCTION_FROM_OUT_SHOW_LOADING_LOGO) && (i != 0); i = 0)
    {
      if (!this._data.manager.getBooleanValue("isFromInit", false))
      {
        Logger.i("PlayVideoBussines !isShowed, go to InitApiActivity!");
        putExtra("is_need_api", false);
        setClassForActivity(InitApiActivity.class);
      }
      return;
    }
    setClassForActivity(ActivityAdapter.getInstance().getMPlayer());
  }

  private void processForReplay()
  {
    Logger.i(TAG, "playVideo replay");
    PlayerIntentParams localPlayerIntentParams = new PlayerIntentParams();
    String str = getTimeString(this._data.videoBeginTime, "yyyyMMddhhmmss");
    if (str != null)
    {
      Logger.i(TAG, "begin_time:" + str);
      localPlayerIntentParams.nns_day = str.substring(0, 8);
      localPlayerIntentParams.nns_beginTime = str.substring(8, 14);
      localPlayerIntentParams.nns_index = 0;
      localPlayerIntentParams.nns_timeLen = (this._data.videoDuration + "");
      localPlayerIntentParams.nns_videoInfo = new VideoInfo();
      localPlayerIntentParams.nns_videoInfo.videoId = this._data.videoId;
      localPlayerIntentParams.nns_videoInfo.videoType = this._data.videoType;
      localPlayerIntentParams.nns_videoInfo.name = this._data.videoIndexName;
      localPlayerIntentParams.nns_videoInfo.film_name = this._data.videoIndexName;
      localPlayerIntentParams.nns_videoInfo.packageId = this._data.mediaAssetId;
      localPlayerIntentParams.nns_videoInfo.categoryId = this._data.categoryId;
      localPlayerIntentParams.nns_videoInfo.huawei_cid = this._data.huaweiCid;
      localPlayerIntentParams.channel_index = this._data.videoChannelIndex;
      localPlayerIntentParams.isSelectTv = this._data.isSelectTv;
      if ((str != null) || (this._data.videoDuration > 0))
        break label349;
      localPlayerIntentParams.mode = 4;
      label266: putExtra(ActivityAdapter.getInstance().MPlayer_INTENT_FLAG, localPlayerIntentParams);
      if ((this._data.isFromWeiXin) || (this._data.isFromWeb))
        break label376;
    }
    label349: label376: for (int i = 1; ; i = 0)
    {
      if ((!AppFuncCfg.FUNCTION_FROM_OUT_SHOW_LOADING_LOGO) || (i == 0))
        break label381;
      if (!this._data.manager.getBooleanValue("isFromInit", false))
      {
        Logger.i("PlayVideoBussines !isShowed, go to InitApiActivity!");
        setClassForActivity(InitApiActivity.class);
      }
      return;
      Logger.i(TAG, "begin_time is null");
      break;
      if (this._data.videoDuration <= 0)
      {
        localPlayerIntentParams.mode = 6;
        break label266;
      }
      localPlayerIntentParams.mode = 3;
      break label266;
    }
    label381: setClassForActivity(ActivityAdapter.getInstance().getMPlayer());
  }

  protected void commonStart()
  {
    putExtra("Cmd", "com.starcor.hunan.cmd.play_video");
    if (isSpecialJsonData());
    try
    {
      initSpecialData();
      if ((isFromOut()) && (this._data.videoType == 0) && (!GlobalLogic.getInstance().checkAllowExternalToPlay()))
        this._data.videoPlayDirect = 0;
      if (this._data.videoPlayDirect == 0)
      {
        changeBussines(new ShowDetailBussines());
        return;
      }
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
      if (this._data.videoType == 1)
      {
        processForReplay();
        return;
      }
      processForPlay();
    }
  }

  protected void startFromActivity()
  {
    Log.d(TAG, "startFromActivity");
  }

  protected void startFromReciever()
  {
    Log.d(TAG, "startFromReciever");
    Log.d(TAG, "PlayVideoData: video_id: " + this._data.videoId);
    Log.d(TAG, "PlayVideoData: video_type: " + this._data.videoType);
    Log.d(TAG, "PlayVideoData: play_video_direct: " + this._data.videoPlayDirect);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.jump.bussines.PlayVideoBussines
 * JD-Core Version:    0.6.2
 */