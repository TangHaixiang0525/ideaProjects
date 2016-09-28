package com.starcor.jump.bussines;

import android.text.TextUtils;
import android.util.Log;
import com.starcor.common.IntentDataManager;
import com.starcor.config.AppFuncCfg;
import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.MovieData;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.GeneralUtils;
import com.starcor.core.utils.Logger;
import com.starcor.core.utils.ToastUtil;
import com.starcor.hunan.DetailPageActivity;
import com.starcor.hunan.DialogActivity;
import com.starcor.hunan.InitApiActivity;
import com.starcor.jump.bussines.data.CommonData;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

public class ShowDetailBussines extends CommonBussines
{
  private static final String TAG = ShowDetailBussines.class.getSimpleName();

  private boolean checkVideoIsRecommoned()
  {
    String str = this._data.videoId;
    List localList = DialogActivity.getAllRecommVideo();
    if (!TextUtils.isEmpty(str))
    {
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
        if (((String)localIterator.next()).equals(str))
          return true;
    }
    return false;
  }

  private void initSpecialData()
    throws Exception
  {
    String str = this._data.manager.getStringValue("cmd_ex");
    if (TextUtils.isEmpty(str));
    do
    {
      return;
      JSONObject localJSONObject = new JSONObject(str);
      if (localJSONObject.has("video_id"))
        this._data.videoId = localJSONObject.getString("video_id");
      if (localJSONObject.has("video_type"))
        this._data.videoType = localJSONObject.getInt("video_type");
      if (localJSONObject.has("video_ui_style"))
        this._data.videoUiStyle = localJSONObject.getInt("video_ui_style");
      putExtra("video_id", this._data.videoId);
      putExtra("video_type", this._data.videoType);
      putExtra("video_ui_style", this._data.videoUiStyle);
    }
    while ((this._data.videoId != null) && (!"".equals(this._data.videoId)) && (this._data.videoType != -100) && (this._data.videoUiStyle != -100));
    ToastUtil.showToast(this._data.getContext(), "收藏记录的信息不全或者错误！");
  }

  private void jumpToDetailPage()
  {
    MovieData localMovieData = new MovieData();
    localMovieData.videoId = this._data.videoId;
    localMovieData.videoType = this._data.videoType;
    putExtra("movieData", localMovieData);
    setClassForActivity(DetailPageActivity.class);
    putExtra("xulPageId", "DetailPage");
    putExtra("xulData", "");
  }

  private void jumpToMainPage(boolean paramBoolean)
  {
    putExtra("videoId", this._data.videoId);
    putExtra("videoType", this._data.videoType);
    putExtra("floatingDialog", paramBoolean);
    processForMainActivity();
  }

  protected void commonStart()
  {
    putExtra("Cmd", "com.starcor.hunan.cmd.show_video_info");
    if (isSpecialJsonData());
    try
    {
      initSpecialData();
      if (((DeviceInfo.isTCL_RT2995()) || (DeviceInfo.isHMD())) && (isFromOut()))
        this._data.videoPlayDirect = 0;
      if (this._data.videoPlayDirect == 1)
      {
        changeBussines(new PlayVideoBussines());
        return;
      }
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
      if ((!this._data.isFromWeiXin) && (!this._data.isFromWeb));
      for (int i = 1; ; i = 0)
      {
        Logger.i("isfrom=" + this._data.manager.getStringValue("cmd_is_from_out", ""));
        if ((!AppFuncCfg.FUNCTION_ENABLE_RECOMMEND_POASTER) || (i == 0))
          break label223;
        if ((!AppFuncCfg.FUNCTION_FROM_OUT_SHOW_LOADING_LOGO) || (this._data.manager.getBooleanValue("isFromInit", false)))
          break;
        Logger.i("ShowDetailBussines !isShowed, go to InitApiActivity!");
        setClassForActivity(InitApiActivity.class);
        return;
      }
      if (GlobalLogic.getInstance().isAppInterfaceReady())
      {
        if (checkVideoIsRecommoned())
        {
          jumpToMainPage(false);
          return;
        }
        jumpToDetailPage();
        return;
      }
      Logger.i("ShowDetailBussines !isAppInterfaceReady, go to InitApiActivity!");
      setClassForActivity(InitApiActivity.class);
      return;
    }
    label223: if ((AppFuncCfg.FUNCTION_ENABLE_FLOATING_DETAIL_PAGE) && (isFromOut()))
      jumpToMainPage(true);
    while (true)
    {
      Logger.d(TAG, "showVideoDetail video_id-->" + this._data.videoId + ",videoType=" + this._data.videoType);
      return;
      jumpToDetailPage();
    }
  }

  protected void startFromActivity()
  {
  }

  protected void startFromReciever()
  {
    Log.i(TAG, "startFromReciever");
    if ((GeneralUtils.isVersion4()) && (!DeviceInfo.isHMD()))
      addFlags(32768);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.jump.bussines.ShowDetailBussines
 * JD-Core Version:    0.6.2
 */