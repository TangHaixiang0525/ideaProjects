package com.starcor.hunan;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.starcor.config.AppFuncCfg;
import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AirControlPlayState;
import com.starcor.core.domain.FilmListPageInfo;
import com.starcor.core.domain.MediaInfo;
import com.starcor.core.domain.MetadataGoup;
import com.starcor.core.domain.MetadataGroupPageIndex;
import com.starcor.core.domain.MetadataInfo;
import com.starcor.core.domain.PlayerIntentParams;
import com.starcor.core.domain.PurchaseParam;
import com.starcor.core.domain.VideoIndex;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.exception.ApplicationException;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.service.BitmapService;
import com.starcor.core.utils.Logger;
import com.starcor.core.utils.NetSpeed;
import com.starcor.core.utils.Tools;
import com.starcor.hunan.domain.Reservation;
import com.starcor.hunan.opendownload.logupload.LogCacheManger;
import com.starcor.hunan.opendownload.logupload.LogCacheManger.ErrorType;
import com.starcor.hunan.uilogic.InitApiData;
import com.starcor.hunan.uilogic.InitApiData.onApiOKListener;
import com.starcor.hunan.widget.ReservationDialog;
import com.starcor.hunan.widget.ReservationDialog.ReservationOnClickListener;
import com.starcor.media.player.MediaPlayerCore;
import com.starcor.media.player.MplayerADView;
import com.starcor.media.player.MplayerDialogView;
import com.starcor.media.player.MplayerDialogView.IMplayerDialogViewListener;
import com.starcor.media.player.MplayerPlaybackView;
import com.starcor.media.player.MplayerTimeshiftView;
import com.starcor.media.player.MplayerVODView;
import com.starcor.report.ReportInfo;
import com.starcor.sccms.api.SccmsApiGetVideoIndexListTask.ISccmsApiGetVideoIndexListTaskListener;
import com.starcor.sccms.api.SccmsApiGetVideoInfoV2Task.ISccmsApiGetVideoInfoV2TaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MplayerV2 extends DialogActivity
{
  public static final int HIMEDIA_Q5_DUAL_CORE_START = 1013;
  public static final String INTENT_EXITAPP_FLAG = "exit_tcl_app";
  public static final String INTENT_EXIT_APP = "exit_app";
  public static final String INTENT_FLAG = "PINTENT";
  public static final String INTENT_VIDEOID = "videoid";
  public static final int MEDIA_INFO_VIDEO_RENDERING_START = 3;
  private static final int PLAYER_SLOW_TIMER = 1281;
  private static final int PLAYRECORD_ADD_EVENT = 1282;
  public static final int PLAY_TIMER_INTERVAL = 499;
  public static final String TAG = "MplayerV2";
  private static final int TPT_PLAY_BACK = 0;
  private static final int TPT_REAL_TIME = 2;
  private static final int TPT_TIME_SHIFT = 1;
  private static final int TPT_UNKNOWN = -1;
  public static String cid = "";
  public static String suuid = "";
  int count = 360;
  private int curTpt = -1;
  private MplayerDialogView dialogView;
  private DetailPageActivity.DisplayStyle episodeType = DetailPageActivity.DisplayStyle.NUMBER_EPISODE;
  private boolean exitAppFlag = false;
  private boolean isCleanCalled = false;
  private boolean isExitApp = false;
  public String isFromOut;
  public boolean isFromWeiXin = false;
  private boolean isLoadSuccess = false;
  private boolean isPreLoadingViewShow;
  private String is_charge = "0";
  private boolean is_special = false;
  private String is_trylook = "0";
  boolean isrunning;
  private ImageView loadingImg;
  private LinearLayout loadingLayout;
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default:
      case 1281:
      case 1282:
      }
      do
      {
        do
        {
          do
          {
            do
            {
              return;
              if ((MplayerV2.this.pParamsReal.mode != 6) && (MplayerV2.this.pParamsReal.mode != 5) && (MplayerV2.this.pParamsReal.mode != 4))
                break;
            }
            while (MplayerV2.this.mpTimeshiftView == null);
            MplayerV2.this.mpTimeshiftView.playerTimerSlowTask(paramAnonymousMessage);
            return;
            if (MplayerV2.this.pParamsReal.mode != 3)
              break;
          }
          while (MplayerV2.this.mpPlaybackView == null);
          MplayerV2.this.mpPlaybackView.playerTimerSlowTask(paramAnonymousMessage);
          return;
        }
        while (MplayerV2.this.mpVodView == null);
        MplayerV2.this.mpVodView.playerTimerSlowTask(paramAnonymousMessage);
        return;
      }
      while (((MplayerV2.this.pParamsReal.mode != 2) && (MplayerV2.this.pParamsReal.mode != 0)) || (MplayerV2.this.mpVodView == null));
      MplayerV2.this.mpVodView.saveLatestPlayRecord();
    }
  };
  private Handler mHandlerAirCtrl = new Handler();
  private Drawable mLoadingDrawable = null;
  private WindowManager mWm;
  private long mdEndTime;
  private long mdStartTime;
  private MediaPlayerCore mpCore = null;
  private MplayerPlaybackView mpPlaybackView = null;
  private MplayerTimeshiftView mpTimeshiftView = null;
  private MplayerVODView mpVodView = null;
  private MplayerADView mplayerADView = null;
  private ImageView oldloadingImg;
  private TextView oldtvDownloadSpeed;
  private OnPlayerControllEventCallback onPlayerControllEventCallback = new OnPlayerControllEventCallback()
  {
    public void onPause()
    {
      if (MplayerV2.this.mplayerADView != null)
      {
        MplayerV2.this.mplayerADView.setVisibility(0);
        MplayerV2.this.mplayerADView.showPauseImage();
      }
    }

    public void onPlay()
    {
      if (MplayerV2.this.mplayerADView != null)
      {
        MplayerV2.this.mplayerADView.setVisibility(8);
        MplayerV2.this.mplayerADView.dismissPuaseImage();
      }
    }
  };
  private String out_play = "";
  PlayerIntentParams pParams = null;
  PlayerIntentParams pParamsReal = null;
  private boolean playerTimerIsRunning = false;
  private Bitmap preLoadingBkg;
  private String recommend_type = "-1,-1";
  private ReservationDialog reservationDialog;
  private ViewGroup rootView;
  private RotateAnimation rotateAnimation;
  int sleep_count = 0;
  private NetSpeed speed;
  private String strErrorNormalMsg = "";
  private String strErrorTitle = "";
  private String strErrorTitleCancelMsg = "";
  private String strOrderContent = "";
  private String strOrderTitle = "";
  private String strOrderTitleCancelMsg = "";
  private boolean threadExitFlag = false;
  private Map<String, String> timeMap = new HashMap();
  private ImageView tipsImg;
  private String titleShowInfo;
  private TextView tvDownloadSpeed;
  private TextView tvVideoName;
  private String vip_id = "";

  private void autoPause()
  {
    Logger.i("MplayerV2", "autoPause()");
    if (this.pParamsReal == null)
      Logger.i("MplayerV2", "autoPause() pParamsReal is null");
    do
    {
      do
      {
        do
        {
          return;
          if ((this.pParamsReal.mode != 6) && (this.pParamsReal.mode != 5) && (this.pParamsReal.mode != 4))
            break;
        }
        while (this.mpTimeshiftView == null);
        this.mpTimeshiftView.ctrlPausePlay();
        return;
        if (this.pParamsReal.mode != 3)
          break;
      }
      while (this.mpPlaybackView == null);
      this.mpPlaybackView.ctrlPausePlay();
      return;
    }
    while (this.mpVodView == null);
    this.mpVodView.ctrlPausePlay();
  }

  private void autoPlay()
  {
    Logger.i("MplayerV2", "autoStop()");
    if (this.pParamsReal == null)
      Logger.i("MplayerV2", "autoStop() pParamsReal is null");
    do
    {
      do
      {
        do
        {
          return;
          if ((this.pParamsReal.mode != 6) && (this.pParamsReal.mode != 5) && (this.pParamsReal.mode != 4))
            break;
        }
        while (this.mpTimeshiftView == null);
        this.mpTimeshiftView.ctrlStartPlay();
        return;
        if (this.pParamsReal.mode != 3)
          break;
      }
      while (this.mpPlaybackView == null);
      this.mpPlaybackView.ctrlStartPlay();
      return;
    }
    while (this.mpVodView == null);
    this.mpVodView.ctrlStartPlay();
  }

  private void autoSeek(long paramLong)
  {
    Logger.i("MplayerV2", "autoSeek() time:" + paramLong);
    long l = paramLong * 1000L;
    if (this.pParamsReal == null)
      Logger.i("MplayerV2", "autoStop() pParamsReal is null");
    do
    {
      do
      {
        do
        {
          return;
          if ((this.pParamsReal.mode != 6) && (this.pParamsReal.mode != 5) && (this.pParamsReal.mode != 4))
            break;
        }
        while (this.mpTimeshiftView == null);
        this.mpTimeshiftView.ctrlSeekTo(l);
        return;
        if (this.pParamsReal.mode != 3)
          break;
      }
      while (this.mpPlaybackView == null);
      this.mpPlaybackView.ctrlSeekTo(l);
      return;
    }
    while (this.mpVodView == null);
    this.mpVodView.ctrlSeekTo(l);
  }

  private void autoStop()
  {
    Logger.i("MplayerV2", "autoStop()");
    if (this.pParamsReal == null)
      Logger.i("MplayerV2", "autoStop() pParamsReal is null");
    do
    {
      do
      {
        do
        {
          return;
          if ((this.pParamsReal.mode != 6) && (this.pParamsReal.mode != 5) && (this.pParamsReal.mode != 4))
            break;
        }
        while (this.mpTimeshiftView == null);
        this.mpTimeshiftView.ctrlStopPlay();
        return;
        if (this.pParamsReal.mode != 3)
          break;
      }
      while (this.mpPlaybackView == null);
      this.mpPlaybackView.ctrlStopPlay();
      return;
    }
    while (this.mpVodView == null);
    this.mpVodView.ctrlStopPlay();
  }

  private void checkAppInterfaceReady()
  {
    App.getInstance().setOnserviceOKListener(new OnServiceOK(null));
  }

  private void checkUserStatusView()
  {
    if ((AppFuncCfg.FUNCTION_GUEST_CAN_PLAY_VIDEO) || (GlobalLogic.getInstance().isUserLogined()))
    {
      processIntent(getIntent());
      return;
    }
    RelativeLayout localRelativeLayout = new RelativeLayout(this);
    ViewGroup.LayoutParams localLayoutParams = new ViewGroup.LayoutParams(-1, -1);
    AssetManager localAssetManager = App.getAppContext().getAssets();
    try
    {
      BitmapDrawable localBitmapDrawable1 = new BitmapDrawable(BitmapFactory.decodeStream(localAssetManager.open("xul/user_center/login_bg.jpg")));
      localBitmapDrawable2 = localBitmapDrawable1;
      localRelativeLayout.setBackgroundDrawable(localBitmapDrawable2);
      this.rootView.addView(localRelativeLayout, localLayoutParams);
      ImageView localImageView = new ImageView(this);
      RelativeLayout.LayoutParams localLayoutParams1 = new RelativeLayout.LayoutParams(App.Operation(525.0F), App.Operation(444.0F));
      localImageView.setImageBitmap(decodeResource(getResources(), 2130837579));
      localLayoutParams1.addRule(13);
      localRelativeLayout.addView(localImageView, localLayoutParams1);
      Button localButton = new Button(this);
      localButton.setBackgroundResource(2130837578);
      RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams(App.Operation(260.0F), App.Operation(60.0F));
      localLayoutParams2.addRule(14);
      localLayoutParams2.addRule(12);
      localLayoutParams2.bottomMargin = App.Operation(200.0F);
      localButton.setText("立即登录");
      localButton.getPaint().setTextSize(App.Operation(25.0F));
      localButton.setTypeface(Typeface.defaultFromStyle(1));
      localButton.setGravity(17);
      localButton.setTextColor(Color.parseColor("#DEDEDE"));
      localRelativeLayout.addView(localButton, localLayoutParams2);
      localButton.requestFocus();
      localButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          Intent localIntent = new Intent(MplayerV2.this, XULActivity.class);
          localIntent.putExtra("xulPageId", "LoginAndRegistration");
          localIntent.putExtra("xulIsClose", "true");
          GlobalLogic.getInstance().setPurchaseParam(new PurchaseParam(false, "", ""));
          localIntent.addFlags(8388608);
          MplayerV2.this.startActivity(localIntent);
        }
      });
      return;
    }
    catch (Exception localException)
    {
      while (true)
        BitmapDrawable localBitmapDrawable2 = null;
    }
  }

  private void cleanBeforeDestroy()
  {
    if (this.isCleanCalled)
      return;
    this.isCleanCalled = true;
    this.threadExitFlag = true;
    if (this.mpPlaybackView != null)
      this.mpPlaybackView.destroy();
    if (this.mpVodView != null)
      this.mpVodView.destroy();
    if (this.mpTimeshiftView != null)
      this.mpTimeshiftView.destroy();
    App.getInstance().setOnserviceOKListener(null);
  }

  private Bitmap decodeResource(Resources paramResources, int paramInt)
  {
    TypedValue localTypedValue = new TypedValue();
    paramResources.openRawResource(paramInt, localTypedValue);
    BitmapFactory.Options localOptions = new BitmapFactory.Options();
    localOptions.inTargetDensity = localTypedValue.density;
    return BitmapFactory.decodeResource(paramResources, paramInt, localOptions);
  }

  private void destroyView()
  {
    if (this.mplayerADView != null)
      this.mplayerADView.setVisibility(8);
    if (this.mpTimeshiftView != null)
    {
      Logger.i("MplayerV2", "null != mpTimeshiftView");
      this.mpTimeshiftView.setVisibility(4);
      this.mpTimeshiftView.destroy();
      this.rootView.removeView(this.mpTimeshiftView);
      this.mpTimeshiftView = null;
    }
    if (this.mpVodView != null)
    {
      Logger.i("MplayerV2", "null != mpVodView");
      this.mpVodView.setVisibility(4);
      this.mpVodView.destroy();
      this.rootView.removeView(this.mpVodView);
      this.mpVodView = null;
    }
    if (this.mpPlaybackView != null)
    {
      Logger.i("MplayerV2", "null != mpPlaybackView");
      this.mpPlaybackView.setVisibility(4);
      this.mpPlaybackView.destroy();
      this.rootView.removeView(this.mpPlaybackView);
      this.mpPlaybackView = null;
    }
  }

  private void dismissPreLoadingView()
  {
    if (this.mWm == null)
      return;
    if (this.isPreLoadingViewShow)
      this.mWm.removeView(this.loadingLayout);
    this.isPreLoadingViewShow = false;
  }

  private void doStartPlay()
  {
    if ((this.pParams.mode == 6) || (this.pParams.mode == 5))
    {
      if ((this.mpTimeshiftView != null) && (!TextUtils.isEmpty(this.pParams.nns_videoInfo.videoId)) && (!this.mpTimeshiftView.checkChannelSupportTSTV(this.pParams.nns_videoInfo.videoId)))
      {
        Logger.i("MplayerV2", "startToPlay() the channel does not support TSTV:" + this.pParams.nns_videoInfo.videoId + ", pParams.mode:" + this.pParams.mode);
        this.mpTimeshiftView.displayNoTSTVNotice();
        return;
      }
      prepareToPlayTimeShift();
    }
    while (true)
    {
      startPlayerTimer();
      return;
      if (this.pParams.mode == 4)
      {
        prepareToPlayTimeShift();
      }
      else if (this.pParams.mode == 3)
      {
        if ((this.mpTimeshiftView != null) && (!TextUtils.isEmpty(this.pParams.nns_videoInfo.videoId)) && (!this.mpTimeshiftView.checkChannelSupportTSTV(this.pParams.nns_videoInfo.videoId)))
        {
          Logger.i("MplayerV2", "startToPlay() the channel does not support TSTV:" + this.pParams.nns_videoInfo.videoId + ", pParams.mode:" + this.pParams.mode);
          this.mpTimeshiftView.displayNoTSTVNotice();
          return;
        }
        if ((this.mpPlaybackView != null) && (!TextUtils.isEmpty(this.pParams.nns_videoInfo.videoId)) && (!this.mpPlaybackView.checkChannelSupportTSTV(this.pParams.nns_videoInfo.videoId)))
        {
          Logger.i("MplayerV2", "startToPlay() the channel does not support TSTV:" + this.pParams.nns_videoInfo.videoId + ", pParams.mode:" + this.pParams.mode);
          this.mpPlaybackView.displayNoTSTVNotice();
          return;
        }
        prepareToPlayback();
      }
      else if (1 == this.pParams.nns_videoInfo.videoType)
      {
        if ((this.mpTimeshiftView != null) && (!TextUtils.isEmpty(this.pParams.nns_videoInfo.videoId)) && (!this.mpTimeshiftView.checkChannelSupportTSTV(this.pParams.nns_videoInfo.videoId)))
        {
          Logger.i("MplayerV2", "startToPlay() the channel does not support TSTV:" + this.pParams.nns_videoInfo.videoId + ", pParams.mode:" + this.pParams.mode);
          this.mpTimeshiftView.displayNoTSTVNotice();
          return;
        }
        if ((this.mpPlaybackView != null) && (!TextUtils.isEmpty(this.pParams.nns_videoInfo.videoId)) && (!this.mpPlaybackView.checkChannelSupportTSTV(this.pParams.nns_videoInfo.videoId)))
        {
          Logger.i("MplayerV2", "startToPlay() the channel does not support TSTV:" + this.pParams.nns_videoInfo.videoId + ", pParams.mode:" + this.pParams.mode);
          this.mpPlaybackView.displayNoTSTVNotice();
          return;
        }
        this.pParams.mode = 3;
        prepareToPlayback();
      }
      else
      {
        prepareToPlayVOD();
      }
    }
  }

  private void getVideoIndexList(String paramString, int paramInt)
  {
    ServerApiManager.i().APIGetVideoIndexList(paramString, paramInt, 0, 1000, false, new SccmsApiGetVideoIndexListTask.ISccmsApiGetVideoIndexListTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        MplayerV2.this.showApiErrorDialog("n3_a_a_12 播放器获取剧集列表onError", paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, FilmListPageInfo paramAnonymousFilmListPageInfo)
      {
        if (paramAnonymousFilmListPageInfo != null)
        {
          MplayerV2.this.pParams.indexOrder = paramAnonymousFilmListPageInfo.getIndex_order();
          ArrayList localArrayList = paramAnonymousFilmListPageInfo.getFilmInfo();
          MplayerV2.this.pParams.nns_mediaIndexList = localArrayList;
          try
          {
            Iterator localIterator = localArrayList.iterator();
            while (localIterator.hasNext())
            {
              VideoIndex localVideoIndex = (VideoIndex)localIterator.next();
              if (localVideoIndex.index == MplayerV2.this.pParams.nns_index)
                MplayerV2.this.pParams.subfix_title = localVideoIndex.name;
            }
            MplayerV2.access$1202(MplayerV2.this, Tools.time2sec(Tools.getTimeString()));
            Logger.i("MplayerV2", "mdEndTime=" + MplayerV2.this.mdEndTime + ",mdStartTime=" + MplayerV2.this.mdStartTime);
            MplayerV2.this.startToPlay();
            return;
          }
          catch (Exception localException)
          {
            while (true)
              localException.printStackTrace();
          }
        }
        MplayerV2.this.showApiErrorDialog("n3_a_a_12 播放器内获取剧集列表onSuccess, 但是接口返回数据为空", paramAnonymousServerApiTaskInfo, null);
      }
    });
  }

  private void getVideoInfoV2(final String paramString, final int paramInt)
  {
    Logger.i("MplayerV2", "getVideoInfoV2");
    ServerApiManager.i().APIGetVideoInfoV2(paramString, paramInt, new SccmsApiGetVideoInfoV2Task.ISccmsApiGetVideoInfoV2TaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.e("MplayerV2", "getVideoInfoV2().onError() error:" + paramAnonymousServerApiCommonError);
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append("getVideoInfoV2().onError() error:");
        localStringBuffer.append(paramAnonymousServerApiCommonError);
        MplayerV2.this.showApiErrorDialog("ISccmsApiGetVideoInfoV2TaskListener.onError", paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, VideoInfo paramAnonymousVideoInfo)
      {
        Logger.i("MplayerV2", "getVideoInfoV2().onSuccess() result:" + paramAnonymousVideoInfo);
        String str;
        VideoIndex localVideoIndex2;
        if (MplayerV2.this.pParams.nns_videoInfo.videoType == 0)
        {
          if (MplayerV2.this.pParams.nns_videoInfo == null)
            MplayerV2.this.pParams.nns_videoInfo = paramAnonymousVideoInfo;
          while (true)
          {
            MplayerV2.this.pParams.nns_videoInfo.name = paramAnonymousVideoInfo.name;
            MplayerV2.this.pParams.nns_videoInfo.indexList = paramAnonymousVideoInfo.indexList;
            MplayerV2.this.pParams.nns_videoInfo.index_ui_type = paramAnonymousVideoInfo.index_ui_type;
            try
            {
              Iterator localIterator2 = paramAnonymousVideoInfo.indexList.iterator();
              while (localIterator2.hasNext())
              {
                VideoIndex localVideoIndex3 = (VideoIndex)localIterator2.next();
                if (localVideoIndex3.index == MplayerV2.this.pParams.nns_index)
                  MplayerV2.this.pParams.subfix_title = localVideoIndex3.name;
              }
              if (TextUtils.isEmpty(MplayerV2.this.pParams.mediaQuality))
              {
                str = GlobalLogic.getInstance().getQuality();
                Logger.i("quality=" + str);
                localArrayList = new ArrayList();
                if ((paramAnonymousVideoInfo != null) && (paramAnonymousVideoInfo.indexList != null) && (paramAnonymousVideoInfo.indexList.size() > 0))
                {
                  VideoIndex localVideoIndex1 = (VideoIndex)paramAnonymousVideoInfo.indexList.get(0);
                  if ((localVideoIndex1 != null) && (localVideoIndex1.mediaInfo != null) && (localVideoIndex1.mediaInfo.size() > 0))
                  {
                    localVideoIndex2 = (VideoIndex)paramAnonymousVideoInfo.indexList.get(0);
                    Iterator localIterator1 = localVideoIndex2.mediaInfo.iterator();
                    while (localIterator1.hasNext())
                    {
                      localArrayList.add(((MediaInfo)localIterator1.next()).type.toUpperCase(Locale.CHINA));
                      continue;
                      MplayerV2.this.pParams.nns_videoInfo.import_id = paramAnonymousVideoInfo.import_id;
                      MplayerV2.this.pParams.nns_videoInfo.serial_id = paramAnonymousVideoInfo.serial_id;
                      MplayerV2.this.pParams.nns_videoInfo.keyWords = paramAnonymousVideoInfo.keyWords;
                      MplayerV2.this.pParams.nns_videoInfo.kind = paramAnonymousVideoInfo.kind;
                      MplayerV2.this.pParams.nns_videoInfo.showTime = paramAnonymousVideoInfo.showTime;
                      MplayerV2.this.pParams.nns_videoInfo.view_type = paramAnonymousVideoInfo.view_type;
                      MplayerV2.this.pParams.nns_videoInfo.imgVUrl = paramAnonymousVideoInfo.imgVUrl;
                      MplayerV2.this.pParams.nns_videoInfo.imgHUrl = paramAnonymousVideoInfo.imgHUrl;
                      MplayerV2.this.pParams.nns_videoInfo.imgSUrl = paramAnonymousVideoInfo.imgSUrl;
                    }
                  }
                }
              }
            }
            catch (Exception localException1)
            {
              ArrayList localArrayList;
              while (true)
                localException1.printStackTrace();
              if (localArrayList.contains(str.toUpperCase(Locale.CHINA)));
            }
          }
        }
        try
        {
          str = ((MediaInfo)localVideoIndex2.mediaInfo.get(0)).type;
          label551: MplayerV2.this.pParams.mediaQuality = str;
          MplayerV2.this.getVideoIndexList(paramString, paramInt);
          return;
        }
        catch (Exception localException2)
        {
          break label551;
        }
      }
    });
  }

  private void initPreLoadingView()
  {
    this.preLoadingBkg = GlobalEnv.getInstance().getPreLoadingBkg(this);
    this.loadingLayout = new LinearLayout(this);
    LinearLayout.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(-1, -1);
    this.loadingLayout.setOrientation(1);
    this.loadingLayout.setGravity(1);
    this.loadingLayout.setId(2131165217);
    this.loadingLayout.setLayoutParams(localLayoutParams1);
    this.loadingLayout.setBackgroundDrawable(new BitmapDrawable(this.preLoadingBkg));
    this.rotateAnimation = new RotateAnimation(-359.0F, 0.0F, 1, 0.5F, 1, 0.5F);
    this.rotateAnimation.setInterpolator(new LinearInterpolator());
    this.rotateAnimation.setDuration(1000L);
    this.rotateAnimation.setRepeatCount(-1);
    this.loadingImg = new ImageView(this);
    this.loadingImg.setBackgroundDrawable(this.mLoadingDrawable);
    LinearLayout.LayoutParams localLayoutParams2 = new LinearLayout.LayoutParams(App.OperationHeight(108), App.OperationHeight(108));
    localLayoutParams2.topMargin = App.Operation(215.0F);
    this.loadingImg.setLayoutParams(localLayoutParams2);
    this.loadingLayout.addView(this.loadingImg);
    this.loadingImg.startAnimation(this.rotateAnimation);
    this.loadingImg.setFocusable(false);
    this.tvVideoName = new TextView(this);
    this.tvVideoName.setGravity(1);
    this.tvVideoName.setTextSize(0, App.Operation(31.0F));
    this.tvVideoName.setTextColor(-1711276033);
    this.tvVideoName.setSingleLine();
    this.tvVideoName.setEllipsize(TextUtils.TruncateAt.valueOf("END"));
    LinearLayout.LayoutParams localLayoutParams3 = new LinearLayout.LayoutParams(-2, -2);
    localLayoutParams3.topMargin = App.Operation(20.0F);
    this.tvVideoName.setLayoutParams(localLayoutParams3);
    String str1 = this.pParams.nns_videoInfo.name;
    if (TextUtils.isEmpty(str1))
      str1 = this.pParams.nns_videoInfo.film_name;
    int i = this.pParams.nns_index;
    if (TextUtils.isEmpty(str1))
    {
      str1 = this.pParams.nns_videoInfo.name;
      i = this.pParams.nns_index;
    }
    String str2;
    if (i == -1)
      str2 = str1;
    while (true)
    {
      Logger.i("tvVideoNameText=" + str2);
      GlobalLogic.getInstance().setTitleShowString(this.titleShowInfo);
      this.tvVideoName.setText(str2);
      this.loadingLayout.addView(this.tvVideoName);
      this.tvDownloadSpeed = new TextView(this);
      this.tvDownloadSpeed.setGravity(1);
      this.tvDownloadSpeed.setTextSize(0, App.Operation(28.0F));
      LinearLayout.LayoutParams localLayoutParams4 = new LinearLayout.LayoutParams(-2, -2);
      localLayoutParams4.topMargin = App.Operation(8.0F);
      this.tvDownloadSpeed.setTextColor(872415231);
      this.tvDownloadSpeed.setLayoutParams(localLayoutParams4);
      this.loadingLayout.addView(this.tvDownloadSpeed);
      this.tipsImg = new ImageView(this);
      Drawable localDrawable = GlobalLogic.getInstance().getRandomLoadingImage();
      if (localDrawable != null)
      {
        this.tipsImg.setImageDrawable(localDrawable);
        LinearLayout.LayoutParams localLayoutParams5 = new LinearLayout.LayoutParams(App.Operation(localDrawable.getIntrinsicWidth()), App.Operation(localDrawable.getIntrinsicHeight()));
        localLayoutParams5.topMargin = App.Operation(105.0F);
        this.tipsImg.setLayoutParams(localLayoutParams5);
      }
      this.loadingLayout.addView(this.tipsImg);
      refreshSpeed();
      return;
      if (this.pParams.nns_videoInfo.videoType == 0)
      {
        this.titleShowInfo = showVideoTitleInfo();
        str2 = str1 + " " + this.titleShowInfo;
      }
      else
      {
        str2 = str1;
      }
    }
  }

  private void initStaticText()
  {
    this.strErrorTitle = "提示";
    this.strErrorTitleCancelMsg = "按\"返回键\"取消";
    this.strErrorNormalMsg = "系统出现异常";
    this.strOrderTitle = "购买";
    this.strOrderTitleCancelMsg = "按\"返回键\"退出播放";
    this.strOrderContent = "该频道为VIP频道，如需观看请您购买服务";
  }

  private void initView()
  {
    setContentView(2130903041);
    this.mLoadingDrawable = new BitmapDrawable(getResources(), BitmapService.getInstance(this).getBitmap("Loading_new.png"));
    initPreLoadingView();
    this.rootView = ((ViewGroup)findViewById(2131165220));
    this.mpCore = ((MediaPlayerCore)findViewById(2131165221));
    this.oldloadingImg = new ImageView(this);
    this.oldloadingImg.setBackgroundDrawable(this.mLoadingDrawable);
    RelativeLayout.LayoutParams localLayoutParams1 = new RelativeLayout.LayoutParams(App.OperationHeight(108), App.OperationHeight(108));
    localLayoutParams1.addRule(13);
    this.oldloadingImg.setId(2131165203);
    this.rootView.addView(this.oldloadingImg, localLayoutParams1);
    this.oldloadingImg.setFocusable(false);
    this.oldtvDownloadSpeed = new TextView(this);
    this.oldtvDownloadSpeed.setGravity(1);
    this.oldtvDownloadSpeed.setTextSize(0, App.Operation(24.0F));
    RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams(App.OperationHeight(100), App.OperationHeight(100));
    localLayoutParams2.addRule(13);
    localLayoutParams2.addRule(3, 2131165203);
    localLayoutParams2.width = App.Operation(150.0F);
    localLayoutParams2.height = -2;
    localLayoutParams2.topMargin = App.Operation(15.0F);
    this.oldtvDownloadSpeed.setLayoutParams(localLayoutParams2);
    this.rootView.addView(this.oldtvDownloadSpeed, localLayoutParams2);
    this.dialogView = new MplayerDialogView(this);
    this.dialogView.setConfirmText("确定");
    this.dialogView.setProblemFeedbackText("问题反馈");
    MplayerDialogViewListener localMplayerDialogViewListener = new MplayerDialogViewListener(null);
    this.dialogView.init(localMplayerDialogViewListener, this.strErrorTitle, this.strErrorTitleCancelMsg, this.strErrorNormalMsg);
    RelativeLayout.LayoutParams localLayoutParams3 = new RelativeLayout.LayoutParams(App.OperationHeight(410), App.OperationHeight(304));
    localLayoutParams3.addRule(13);
    this.dialogView.setLayoutParams(localLayoutParams3);
    this.rootView.addView(this.dialogView, localLayoutParams3);
    this.dialogView.setVisibility(4);
  }

  private void p2pErrorReport(String paramString1, String paramString2)
  {
    if (!AppFuncCfg.FUNCTION_P2P_REPORT);
  }

  private void prepareToPlayTimeShift()
  {
    setCurTpt(1);
    destroyView();
    this.mpTimeshiftView = new MplayerTimeshiftView(this, new MplayerV2Listener(null));
    this.mpTimeshiftView.setTimeMap(this.timeMap);
    this.mpTimeshiftView.setOnPlayerControllEventCallback(this.onPlayerControllEventCallback);
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-1, -1);
    localLayoutParams.addRule(10);
    this.rootView.addView(this.mpTimeshiftView, localLayoutParams);
    this.mpTimeshiftView.setVisibility(0);
    this.mpTimeshiftView.bringToFront();
    this.mpTimeshiftView.bindMediaPlayerCore(this.mpCore);
    this.pParamsReal = this.pParams;
    this.pParamsReal.out_play = this.out_play;
    this.pParams.autoPlay = 0;
    this.pParamsReal.is_special = this.is_special;
    this.mpTimeshiftView.bindPlayInfo(this.pParamsReal);
    this.mpTimeshiftView.startToPlay();
    reportPageLoad(true);
  }

  private void prepareToPlayVOD()
  {
    setCurTpt(-1);
    destroyView();
    this.mpVodView = new MplayerVODView(this, new MplayerV2Listener(null));
    this.mpVodView.setOnPlayerControllEventCallback(this.onPlayerControllEventCallback);
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-1, -1);
    localLayoutParams.addRule(10);
    this.rootView.addView(this.mpVodView, localLayoutParams);
    this.mpVodView.setVisibility(0);
    this.mpVodView.bringToFront();
    this.pParamsReal = this.pParams;
    this.pParamsReal.nns_videoInfo.is_charge = this.is_charge;
    this.pParamsReal.nns_videoInfo.is_recommend = this.recommend_type;
    this.pParamsReal.nns_videoInfo.is_trylook = this.is_trylook;
    this.pParamsReal.nns_videoInfo.vip_id = this.vip_id;
    this.pParamsReal.out_play = this.out_play;
    this.pParamsReal.is_special = this.is_special;
    this.pParamsReal.autoPlay = this.pParams.autoPlay;
    this.pParamsReal.isMediasConPlay = this.pParams.isMediasConPlay;
    this.pParamsReal.hostMediaVideoId = this.pParams.hostMediaVideoId;
    this.mpVodView.bindPlayInfo(this.pParamsReal);
    this.mpVodView.bindMediaPlayerCore(this.mpCore);
    this.mpVodView.start();
    reportPageLoad(true);
  }

  private void prepareToPlayback()
  {
    setCurTpt(0);
    destroyView();
    if (this.mpPlaybackView == null)
    {
      this.mpPlaybackView = new MplayerPlaybackView(this, new MplayerV2Listener(null));
      this.mpPlaybackView.setOnPlayerControllEventCallback(this.onPlayerControllEventCallback);
      RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-1, -1);
      localLayoutParams.addRule(10);
      this.rootView.addView(this.mpPlaybackView, localLayoutParams);
      this.mpPlaybackView.setVisibility(0);
    }
    this.mpPlaybackView.bringToFront();
    if ((this.pParams.nns_videoInfo != null) && (TextUtils.isEmpty(this.pParams.nns_videoInfo.packageId)) && (TextUtils.isEmpty(this.pParams.nns_videoInfo.categoryId)))
    {
      Iterator localIterator1 = GlobalLogic.getInstance().getN3A2MetaGroups().iterator();
      label299: 
      while (localIterator1.hasNext())
      {
        Iterator localIterator2 = ((MetadataGoup)localIterator1.next()).meta_index_list.iterator();
        while (true)
        {
          if (!localIterator2.hasNext())
            break label299;
          Iterator localIterator3 = ((MetadataGroupPageIndex)localIterator2.next()).meta_item_list.iterator();
          if (localIterator3.hasNext())
          {
            MetadataInfo localMetadataInfo = (MetadataInfo)localIterator3.next();
            Logger.i("MplayerV2", "startPlay() MetadataInfo info:" + localMetadataInfo.toString());
            if (!"player".equals(localMetadataInfo.action_type))
              break;
            this.pParams.nns_videoInfo.categoryId = localMetadataInfo.category_id;
            this.pParams.nns_videoInfo.packageId = localMetadataInfo.packet_id;
          }
        }
      }
    }
    this.pParamsReal = this.pParams;
    this.pParamsReal.nns_videoInfo.is_recommend = this.recommend_type;
    this.pParamsReal.out_play = this.out_play;
    this.pParams.autoPlay = 0;
    this.mpPlaybackView.bindPlayInfo(this.pParamsReal);
    this.mpPlaybackView.bindMediaPlayerCore(this.mpCore);
    this.mpPlaybackView.startToPlay();
    reportPageLoad(true);
  }

  private void processIntent(Intent paramIntent)
  {
    if (this.oldloadingImg != null)
    {
      this.oldloadingImg.startAnimation(this.rotateAnimation);
      this.oldloadingImg.setVisibility(0);
      this.oldloadingImg.bringToFront();
    }
    if (1 == this.pParams.nns_videoInfo.videoType)
      showPreLoadingView();
    this.isFromOut = paramIntent.getStringExtra("cmd_is_from_out");
    if ((this.isFromOut != null) && (this.isFromOut.trim().length() == 0))
      this.isFromOut = null;
    Logger.d("MplayerV2", "MplayerV2--->processIntent, isFromOut = " + this.isFromOut);
    this.isExitApp = paramIntent.getBooleanExtra("exit_app", false);
    this.exitAppFlag = paramIntent.getBooleanExtra("exit_tcl_app", false);
    this.isFromWeiXin = getIntent().getBooleanExtra("isFromWeiXin", false);
    Logger.d("MplayerV2", "extras = " + paramIntent.getExtras().toString());
    Logger.i("debug_videinfo", "processIntent:pParams.nns_videoInfo.is_recommend=" + this.pParams.nns_videoInfo.is_recommend);
    this.recommend_type = this.pParams.nns_videoInfo.is_recommend;
    this.is_charge = this.pParams.nns_videoInfo.is_charge;
    this.vip_id = this.pParams.nns_videoInfo.vip_id;
    this.is_special = this.pParams.is_special;
    Logger.i("MplayerV2", "processIntent isFromOut:" + this.isFromOut + "  isExitApp=" + this.isExitApp + "   exitAppFlag" + this.exitAppFlag);
    if (TextUtils.isEmpty(this.pParams.nns_videoInfo.is_trylook))
      this.pParams.nns_videoInfo.is_trylook = "3";
    this.is_trylook = this.pParams.nns_videoInfo.is_trylook;
    if (this.isFromOut == null)
    {
      if (paramIntent.getBooleanExtra("flag_action_from_mgtv", false))
      {
        cid = "webview";
        this.out_play = this.pParams.out_play;
        if ((this.pParams == null) || (this.pParams.nns_mediaIndexList != null) || (this.pParams.nns_videoInfo.videoType != 0) || (!TextUtils.isEmpty(this.pParams.nns_video_preview_type)))
          break label531;
        Logger.i("MplayerV2", "type=" + this.pParams.nns_videoInfo.videoType);
        this.mdStartTime = Tools.time2sec(Tools.getTimeString());
        getVideoInfoV2(this.pParams.nns_videoInfo.videoId, this.pParams.nns_videoInfo.videoType);
      }
      while (true)
      {
        Logger.i("MplayerV2", " processIntent addPlayTask OK");
        return;
        cid = "";
        break;
        label531: startToPlay();
      }
    }
    Logger.i("MplayerV2", "processIntent isFromOut: initApi");
    ReportInfo.getInstance().setEntranceSrc("null");
    if (this.isFromWeiXin)
    {
      this.out_play = "IMGO";
      cid = "wechat";
    }
    while (true)
    {
      checkAppInterfaceReady();
      break;
      cid = "factory";
      this.out_play = (Tools.getOutPlayOriginal() + "-2");
    }
  }

  private void refreshSpeed()
  {
    if (this.isrunning)
      return;
    this.isrunning = true;
    Handler local3 = new Handler(Looper.getMainLooper())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        if (paramAnonymousMessage.what == 1)
        {
          float f1 = 1.0F * paramAnonymousMessage.arg1;
          if (f1 > 1024.0F)
          {
            float f2 = f1 / 1024.0F;
            DecimalFormat localDecimalFormat = new DecimalFormat("#.00");
            localDecimalFormat.format(f2);
            MplayerV2.this.tvDownloadSpeed.setText(localDecimalFormat.format(f2) + "MB/S");
          }
        }
        else
        {
          return;
        }
        MplayerV2.this.tvDownloadSpeed.setText(paramAnonymousMessage.arg1 + "KB/S");
      }
    };
    this.speed = NetSpeed.getInstant(App.getAppContext(), local3);
    this.speed.startCalculateNetSpeed();
  }

  private void reportPageExit()
  {
    reportExit(this.isLoadSuccess, null);
  }

  private void reportPageLoad(boolean paramBoolean)
  {
    this.isLoadSuccess = paramBoolean;
    reportLoad(paramBoolean, null);
  }

  private void setCurTpt(int paramInt)
  {
    this.curTpt = paramInt;
  }

  private void showApiErrorDialog(String paramString, ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
  {
    dismissPreLoadingView();
    ApplicationException.showErrorDialogWithReport(this, 9, "1002005", paramString, paramServerApiTaskInfo, paramServerApiCommonError, getCurPageName());
  }

  private void showPreLoadingView()
  {
    if (this.mWm == null)
      this.mWm = ((WindowManager)getSystemService("window"));
    if (this.isPreLoadingViewShow)
      return;
    if (isFinishing())
    {
      Logger.i("MplayerV2", "showPreLoadingView mplayer isFinishing");
      return;
    }
    this.isPreLoadingViewShow = true;
    WindowManager.LayoutParams localLayoutParams = new WindowManager.LayoutParams(-1, -1);
    localLayoutParams.type = 2002;
    localLayoutParams.format = 1;
    localLayoutParams.flags = 40;
    this.mWm.addView(this.loadingLayout, localLayoutParams);
    this.loadingImg.clearAnimation();
    this.loadingImg.startAnimation(this.rotateAnimation);
  }

  private String showVideoTitleInfo()
  {
    String str = "";
    this.episodeType = GlobalLogic.getInstance().getDisplayStyle(this.pParams.nns_videoInfo.view_type, this.pParams.nns_videoInfo.index_ui_type);
    Object localObject;
    if ((this.pParams.nns_mediaIndexList != null) && (this.pParams.nns_mediaIndexList.size() > 0))
    {
      Iterator localIterator = this.pParams.nns_mediaIndexList.iterator();
      VideoIndex localVideoIndex;
      do
      {
        boolean bool = localIterator.hasNext();
        localObject = null;
        if (!bool)
          break;
        localVideoIndex = (VideoIndex)localIterator.next();
      }
      while (((TextUtils.isEmpty(this.pParams.nns_video_index_id)) || (!this.pParams.nns_video_index_id.equals(localVideoIndex.id))) && ((!TextUtils.isEmpty(this.pParams.nns_video_index_id)) || (localVideoIndex.index != this.pParams.nns_index)));
      localObject = localVideoIndex;
      if (localObject == null)
        return "";
      Logger.i("MplayerV2", "pParams.nns_index=" + this.pParams.nns_index + ",time=" + GlobalLogic.getInstance().getTargetDateString(localObject));
      if (!this.episodeType.equals(DetailPageActivity.DisplayStyle.NUMBER_EPISODE))
        break label236;
      str = localObject.name;
    }
    while (true)
    {
      return str;
      label236: if (this.episodeType.equals(DetailPageActivity.DisplayStyle.WATCH_FOCUS))
        str = localObject.desc;
      else if (this.episodeType.equals(DetailPageActivity.DisplayStyle.WATCH_FOCUS_DATE))
        str = "第" + GlobalLogic.getInstance().getTargetDateString(localObject) + "期:  " + localObject.desc;
      else if (this.episodeType.equals(DetailPageActivity.DisplayStyle.WATCH_FOCUS_EPISODE))
        str = localObject.name + " " + localObject.desc;
      else
        str = localObject.name;
    }
  }

  private void startPlayerTimer()
  {
    if (this.playerTimerIsRunning)
      return;
    this.playerTimerIsRunning = true;
    if (DeviceInfo.isFactoryTCL())
      this.count = 40;
    new Thread()
    {
      public void run()
      {
        while (!MplayerV2.this.threadExitFlag)
          try
          {
            if (MplayerV2.this.sleep_count == 0)
              MplayerV2.this.mHandler.sendEmptyMessage(1282);
            MplayerV2.this.mHandler.sendEmptyMessage(1281);
            Thread.sleep(499L);
            MplayerV2 localMplayerV2 = MplayerV2.this;
            localMplayerV2.sleep_count = (1 + localMplayerV2.sleep_count);
            if (MplayerV2.this.sleep_count == MplayerV2.this.count)
              MplayerV2.this.sleep_count = 0;
          }
          catch (InterruptedException localInterruptedException)
          {
            localInterruptedException.printStackTrace();
          }
      }
    }
    .start();
  }

  private void startToPlay()
  {
    if (this.pParams == null)
    {
      Logger.e("MplayerV2", "startPlay() PlayerIntentParams is null");
      finish();
      return;
    }
    Logger.i("MplayerV2", "startToPlay() pParams:" + this.pParams);
    if (isFinishing())
    {
      Logger.e("MplayerV2", "startPlay() isFinishing()");
      return;
    }
    doStartPlay();
  }

  protected void airControlPauseVideo()
  {
    autoPause();
  }

  protected void airControlResumeVideo()
  {
    autoPlay();
  }

  protected void airControlSeekVideo(final long paramLong)
  {
    Logger.i("MplayerV2", "airControlSeekVideo() seekTime:" + paramLong);
    this.mHandlerAirCtrl.post(new Runnable()
    {
      public void run()
      {
        MplayerV2.this.autoSeek(paramLong);
      }
    });
  }

  protected void airControlStopVideo()
  {
    autoStop();
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    if (this.isPreLoadingViewShow)
      if ((paramKeyEvent.getAction() == 0) && (paramKeyEvent.getKeyCode() == 4))
      {
        dismissPreLoadingView();
        finish();
      }
    do
    {
      return true;
      return false;
      if (this.pParamsReal == null)
        return super.dispatchKeyEvent(paramKeyEvent);
      if ((this.pParamsReal.mode != 6) && (this.pParamsReal.mode != 5) && (this.pParamsReal.mode != 4))
        break;
    }
    while ((this.mpTimeshiftView != null) && (this.mpTimeshiftView.dispatchKeyEvent(paramKeyEvent)));
    do
    {
      do
      {
        return super.dispatchKeyEvent(paramKeyEvent);
        if (this.pParamsReal.mode != 3)
          break;
      }
      while ((this.mpPlaybackView == null) || (!this.mpPlaybackView.dispatchKeyEvent(paramKeyEvent)));
      return true;
    }
    while ((this.mpVodView == null) || (!this.mpVodView.dispatchKeyEvent(paramKeyEvent)));
    return true;
  }

  public void finish()
  {
    if (TextUtils.isEmpty(this.isFromOut))
    {
      super.finish();
      return;
    }
    Logger.i("MplayerV2", "finish()");
    if ((this.pParams.mode == 6) || (this.pParams.mode == 5))
      if (AppFuncCfg.FUNCTION_GOTO_MAIN_IF_FROM_OUT)
      {
        Logger.i("MplayerV2", "onBackPressed_gotoMainActivityIfFromOut()");
        gotoMainActivityIfFromOut(false);
      }
    while (true)
    {
      super.finish();
      return;
      if ((this.pParams.mode == 4) && (AppFuncCfg.FUNCTION_GOTO_MAIN_IF_FROM_OUT))
        gotoMainActivityIfFromOut(false);
    }
  }

  protected AirControlPlayState getAirControlPlayState()
  {
    if ((this.pParams.mode == 6) || (this.pParams.mode == 5) || (this.pParams.mode == 4))
    {
      if (this.mpTimeshiftView != null)
        return this.mpTimeshiftView.ctrlGetPlayState();
    }
    else if (this.pParams.mode == 3)
    {
      if (this.mpPlaybackView != null)
        return this.mpPlaybackView.ctrlGetPlayState();
    }
    else if (this.mpVodView != null)
      return this.mpVodView.ctrlGetPlayState();
    return AirControlPlayState.NULL;
  }

  public long getCurrentPos()
  {
    if (this.mpCore != null)
      return this.mpCore.getCurrentPosition();
    return -1L;
  }

  protected boolean needShowPopupDialog()
  {
    return false;
  }

  public Boolean onCommDialogEvent(Dialog paramDialog, int paramInt1, int paramInt2)
  {
    if (paramInt1 == -1)
      switch (paramInt2)
      {
      default:
      case 3:
      }
    while (true)
    {
      return super.onCommDialogEvent(paramDialog, paramInt1, paramInt2);
      Logger.i("MplayerV2", "onCommDialogEvent() DIALOG_TYPE_WEB");
      if ((this.pParams.mode == 6) || (this.pParams.mode == 5) || (this.pParams.mode == 4))
        this.mpTimeshiftView.onWebDialogClose();
      else if ((this.pParams.mode == 2) || (this.pParams.mode != 3));
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Intent localIntent = getIntent();
    this.pParams = ((PlayerIntentParams)localIntent.getExtras().get("PINTENT"));
    if ((!TextUtils.isEmpty(getIntent().getStringExtra("channelId"))) && ("voicecontrol".equals(localIntent.getStringExtra("cmdstring"))))
      this.pParams.mode = 5;
    xulOnRenderIsReady();
    Logger.i("Application", "MplayerV2 onCreate");
  }

  protected void onDestroy()
  {
    Logger.i("MplayerV2", "onDestroy");
    this.rootView.removeAllViews();
    dismissPreLoadingView();
    cleanBeforeDestroy();
    if (this.speed != null)
      this.speed.stopCalculateNetSpeed();
    if ((this.mWm != null) && (this.isPreLoadingViewShow == true))
    {
      this.mWm.removeView(this.loadingLayout);
      this.mWm = null;
    }
    super.onDestroy();
  }

  protected void onNewIntent(Intent paramIntent)
  {
    setIntent(paramIntent);
    this.pParams = ((PlayerIntentParams)getIntent().getExtras().get("PINTENT"));
    Logger.i("MplayerV2", "onNewIntent intent=" + paramIntent.getExtras().toString());
    if (xulIsReady())
      checkUserStatusView();
  }

  protected void onPause()
  {
    super.onPause();
  }

  public void onReservationClick(Reservation paramReservation, PlayerIntentParams paramPlayerIntentParams)
  {
    this.pParams = paramPlayerIntentParams;
    prepareToPlayback();
  }

  protected void onResume()
  {
    super.onResume();
  }

  protected void onStop()
  {
    reportPageExit();
    Logger.i("MplayerV2", "onStop");
    if (DeviceInfo.isBDYB())
    {
      super.onStop();
      return;
    }
    cleanBeforeDestroy();
    finish();
    super.onStop();
  }

  protected void refreshViews()
  {
    if ((this.pParams.mode == 6) || (this.pParams.mode == 5) || (this.pParams.mode == 4))
      this.mpTimeshiftView.onWebDialogResult();
    while (true)
    {
      super.refreshViews();
      return;
      if ((this.pParams.mode == 2) || (this.pParams.mode != 3));
    }
  }

  protected void xulOnRenderIsReady()
  {
    super.xulOnRenderIsReady();
    initStaticText();
    initView();
    checkUserStatusView();
  }

  public static abstract interface IMplayerV2Listener
  {
    public abstract void onErrorNoticeViewDisplay(int paramInt, String paramString1, String paramString2, boolean paramBoolean);

    public abstract void onLoadingViewDisplay(int paramInt);

    public abstract void onMplayerDialogViewDiaplay(KeyEvent paramKeyEvent);

    public abstract void onOrderNoticeViewDisplay(int paramInt, boolean paramBoolean);

    public abstract void onPreLoadingViewDisplay(int paramInt, String paramString);

    public abstract void onReservationDialogDisplay(int paramInt, long paramLong);

    public abstract void onSpeedTextDisplay(int paramInt, String paramString);

    public abstract void onWebDialogDisplay(int paramInt, String paramString);
  }

  private class MplayerDialogViewListener
    implements MplayerDialogView.IMplayerDialogViewListener
  {
    private MplayerDialogViewListener()
    {
    }

    public void onCancel()
    {
      Logger.i("MplayerV2", "MplayerDialogViewListener.onCancel()");
    }

    public void onConfirm(int paramInt)
    {
      if (MplayerV2.this.mpVodView != null)
        MplayerV2.this.mpVodView.onConfirm(paramInt);
      if (MplayerV2.this.mpPlaybackView != null)
        MplayerV2.this.mpPlaybackView.onConfirm(paramInt);
      if (MplayerV2.this.mpTimeshiftView != null)
        MplayerV2.this.mpTimeshiftView.onConfirm(paramInt);
      Logger.i("MplayerV2", "MplayerDialogViewListener.onConfirm()");
    }
  }

  private class MplayerV2Listener
    implements MplayerV2.IMplayerV2Listener
  {
    private MplayerV2Listener()
    {
    }

    public void onErrorNoticeViewDisplay(int paramInt, String paramString1, String paramString2, boolean paramBoolean)
    {
      StringBuilder localStringBuilder = new StringBuilder().append("MplayerV2Listener.onErrorNoticeViewDisplay() display:");
      if (paramInt == 0);
      for (String str1 = "visible"; ; str1 = "invisible")
      {
        Logger.e("MplayerV2", str1 + ", errCode:" + paramString1);
        onPreLoadingViewDisplay(4, "");
        if (paramInt != 0)
          break;
        LogCacheManger.getInstance().notifyWriteFile(LogCacheManger.ErrorType.ERROR, paramString1);
        String str2 = (String)ApplicationException.appExceptionMap.get(paramString1);
        String str3 = str2 + " [" + paramString1 + "]";
        MplayerV2.this.dialogView.init(new MplayerV2.MplayerDialogViewListener(MplayerV2.this, null), MplayerV2.this.strErrorTitle, MplayerV2.this.strErrorTitleCancelMsg, str3);
        MplayerV2.this.dialogView.bringToFront();
        MplayerV2.this.dialogView.setVisibility(paramInt);
        return;
      }
      if (paramBoolean)
      {
        MplayerV2.this.finish();
        return;
      }
      MplayerV2.this.dialogView.setVisibility(paramInt);
    }

    public void onLoadingViewDisplay(int paramInt)
    {
      if (paramInt == 0)
        if (MplayerV2.this.oldloadingImg.getVisibility() != 0)
        {
          Logger.i("MplayerV2", "MplayerV2Listener.onLoadingViewDisplay() visible");
          MplayerV2.this.oldloadingImg.startAnimation(MplayerV2.this.rotateAnimation);
          MplayerV2.this.oldloadingImg.setVisibility(0);
          MplayerV2.this.oldloadingImg.bringToFront();
        }
      while (MplayerV2.this.oldloadingImg.getVisibility() == 4)
        return;
      Logger.i("MplayerV2", "MplayerV2Listener.onLoadingViewDisplay() invisible");
      MplayerV2.this.oldloadingImg.clearAnimation();
      MplayerV2.this.oldloadingImg.setVisibility(4);
    }

    public void onMplayerDialogViewDiaplay(KeyEvent paramKeyEvent)
    {
      MplayerV2.this.dialogView.onInputEvent(paramKeyEvent);
    }

    public void onOrderNoticeViewDisplay(int paramInt, boolean paramBoolean)
    {
      Logger.i("MplayerV2", "MplayerV2Listener.onOrderNoticeViewDisplay() display:" + paramInt);
      if (paramInt == 0)
      {
        MplayerV2.this.dialogView.init(new MplayerV2.MplayerDialogViewListener(MplayerV2.this, null), MplayerV2.this.strOrderTitle, MplayerV2.this.strOrderTitleCancelMsg, MplayerV2.this.strOrderContent);
        MplayerV2.this.dialogView.bringToFront();
        MplayerV2.this.dialogView.setVisibility(paramInt);
        return;
      }
      if (paramBoolean)
      {
        MplayerV2.this.finish();
        return;
      }
      MplayerV2.this.dialogView.setVisibility(paramInt);
    }

    public void onPreLoadingViewDisplay(int paramInt, String paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder().append("MplayerV2Listener.onPreLoadingViewDisplay() display:");
      String str1;
      if (paramInt == 0)
      {
        str1 = "visible";
        Logger.i("MplayerV2", str1 + ",videoname=" + paramString);
        if (paramInt != 0)
          break label207;
        MplayerV2.this.showPreLoadingView();
      }
      while (true)
      {
        String str2 = "";
        if ((paramString != null) && (!paramString.isEmpty()))
        {
          MplayerV2.access$2002(MplayerV2.this, MplayerV2.this.showVideoTitleInfo());
          String[] arrayOfString = paramString.split("/");
          if (arrayOfString != null)
          {
            Logger.i("MplayerV2", "videoNameAndEpisode=" + arrayOfString[0] + ",titleShowInfo=" + MplayerV2.this.titleShowInfo);
            str2 = arrayOfString[0] + " " + MplayerV2.this.titleShowInfo;
          }
        }
        GlobalLogic.getInstance().setTitleShowString(MplayerV2.this.titleShowInfo);
        MplayerV2.this.tvVideoName.setText(str2);
        return;
        str1 = "invisible";
        break;
        label207: MplayerV2.this.dismissPreLoadingView();
      }
    }

    public void onReservationDialogDisplay(int paramInt, long paramLong)
    {
      StringBuilder localStringBuilder = new StringBuilder().append("MplayerV2Listener.onReservationDialogDisplay() display:");
      if (paramInt == 0);
      for (String str = "visible"; ; str = "invisible")
      {
        Logger.i("MplayerV2", str + ", checkTime:" + paramLong);
        if (MplayerV2.this.reservationDialog == null)
        {
          MplayerV2.access$2702(MplayerV2.this, new ReservationDialog(MplayerV2.this, 2131296258));
          MplayerV2.this.reservationDialog.setListener(new MplayerV2.ReservationOnClickListener(MplayerV2.this, null));
        }
        if (paramInt != 0)
          break;
        MplayerV2.this.reservationDialog.show();
        MplayerV2.this.reservationDialog.addReservation(paramLong);
        return;
      }
      MplayerV2.this.reservationDialog.dismiss();
    }

    public void onSpeedTextDisplay(int paramInt, String paramString)
    {
      if (MplayerV2.this.oldtvDownloadSpeed.getVisibility() != paramInt)
        MplayerV2.this.oldtvDownloadSpeed.setVisibility(paramInt);
      MplayerV2.this.oldtvDownloadSpeed.setText(paramString);
      MplayerV2.this.oldtvDownloadSpeed.postInvalidate();
    }

    public void onWebDialogDisplay(int paramInt, String paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder().append("MplayerV2Listener.onWebDialogDisplay() display:");
      if (paramInt == 0);
      for (String str = "visible"; ; str = "invisible")
      {
        Logger.i("MplayerV2", str + ", url:" + paramString);
        if (paramInt != 0)
          break;
        MplayerV2.this.showWebDialog(paramString, null);
        return;
      }
      MplayerV2.this.dismissDialog(3);
    }
  }

  private class OnApiOk
    implements InitApiData.onApiOKListener
  {
    private OnApiOk()
    {
    }

    public void onApiOk(int paramInt)
    {
      if (paramInt == 1)
      {
        Logger.i("MplayerV2", "api is ok");
        if (MplayerV2.this.getIntent().getIntExtra("videoType", 1) == 0)
          Logger.i("MplayerV2", "点播功能，暂不支持");
        if ((MplayerV2.this.pParams != null) && (MplayerV2.this.pParams.nns_mediaIndexList == null) && (MplayerV2.this.pParams.nns_videoInfo.videoType == 0))
          MplayerV2.this.getVideoInfoV2(MplayerV2.this.pParams.nns_videoInfo.videoId, MplayerV2.this.pParams.nns_videoInfo.videoType);
      }
      else
      {
        return;
      }
      MplayerV2.this.startToPlay();
    }

    public void onGetApiDataError()
    {
    }

    public void onNeedFinishActivity()
    {
      MplayerV2.this.finish();
    }
  }

  public static abstract interface OnPlayerControllEventCallback
  {
    public abstract void onPause();

    public abstract void onPlay();
  }

  private class OnServiceOK
    implements App.OnServiceOKListener
  {
    private OnServiceOK()
    {
    }

    public void onServiceOK()
    {
      if (GlobalLogic.getInstance().isAppInterfaceReady())
      {
        if ((MplayerV2.this.pParams != null) && (MplayerV2.this.pParams.nns_mediaIndexList == null) && (MplayerV2.this.pParams.nns_videoInfo.videoType == 0))
        {
          MplayerV2.this.getVideoInfoV2(MplayerV2.this.pParams.nns_videoInfo.videoId, MplayerV2.this.pParams.nns_videoInfo.videoType);
          return;
        }
        MplayerV2.this.startToPlay();
        return;
      }
      new InitApiData(MplayerV2.this).setOnApiOkListener(new MplayerV2.OnApiOk(MplayerV2.this, null));
    }
  }

  private class ReservationOnClickListener
    implements ReservationDialog.ReservationOnClickListener
  {
    private ReservationOnClickListener()
    {
    }

    public void onReservationClick(Reservation paramReservation, PlayerIntentParams paramPlayerIntentParams)
    {
      Logger.i("MplayerV2", "ReservationOnClickListener.onReservationClick() info:" + paramPlayerIntentParams.toString());
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.MplayerV2
 * JD-Core Version:    0.6.2
 */