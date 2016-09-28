package com.starcor.media.player;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.starcor.GetStreamInfoResult;
import com.starcor.GetStreamInfoResult.Result;
import com.starcor.GetStreamInfoResult.StreamInfo;
import com.starcor.OTTTV;
import com.starcor.config.AppFuncCfg;
import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AirControlPlayState;
import com.starcor.core.domain.AuthState;
import com.starcor.core.domain.ChannelInfoV2;
import com.starcor.core.domain.ChannelItemInfoV2;
import com.starcor.core.domain.CollectListItem;
import com.starcor.core.domain.FilmListItem;
import com.starcor.core.domain.MovieData;
import com.starcor.core.domain.PlayBillStruct;
import com.starcor.core.domain.PlayInfo;
import com.starcor.core.domain.PlayInfoV2;
import com.starcor.core.domain.PlayerIntentParams;
import com.starcor.core.domain.PurchaseParam;
import com.starcor.core.domain.TerminalRealtimeParam;
import com.starcor.core.domain.TerminalRealtimeParamList;
import com.starcor.core.domain.UserKey;
import com.starcor.core.domain.VideoIndex;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.epgapi.StringParams;
import com.starcor.core.epgapi.params.AddCollectV2Params;
import com.starcor.core.exception.ApplicationException;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.logic.UserCPLLogic;
import com.starcor.core.report.enums.BufferEnum;
import com.starcor.core.utils.AppKiller;
import com.starcor.core.utils.GeneralUtils;
import com.starcor.core.utils.GeneralUtils.LocationObserve;
import com.starcor.core.utils.Logger;
import com.starcor.core.utils.PinYinUtil;
import com.starcor.core.utils.Tools;
import com.starcor.hunan.App;
import com.starcor.hunan.DetailPageActivity;
import com.starcor.hunan.DialogActivity;
import com.starcor.hunan.MplayerV2;
import com.starcor.hunan.MplayerV2.IMplayerV2Listener;
import com.starcor.hunan.MplayerV2.OnPlayerControllEventCallback;
import com.starcor.hunan.UserFeedbackActivity;
import com.starcor.hunan.domain.ReportData;
import com.starcor.hunan.msgsys.data.LiveTopicMessageData;
import com.starcor.hunan.msgsys.data.reservetopic.InteractiveMessage;
import com.starcor.hunan.service.CollectAndPlayListLogic;
import com.starcor.hunan.service.ReservationService;
import com.starcor.hunan.service.SystemTimeManager;
import com.starcor.hunan.widget.MovieCouponDialog;
import com.starcor.hunan.widget.MovieCouponDialog.MovieCouponOkDialogListener;
import com.starcor.hunan.widget.MovieCouponDialog.MovieCouponTipDialogListener;
import com.starcor.hunan.widget.TokenDialog;
import com.starcor.hunan.widget.TokenDialog.TokenDialogListener;
import com.starcor.media.player.ad.AdManager;
import com.starcor.message.MessageHandler;
import com.starcor.player.MediaPlayerAdapter;
import com.starcor.player.MediaPlayerAdapter.OnCompletionListener;
import com.starcor.player.MediaPlayerAdapter.OnErrorListener;
import com.starcor.player.MediaPlayerAdapter.OnInfoListener;
import com.starcor.player.MediaPlayerAdapter.OnPreparedListener;
import com.starcor.player.MediaPlayerAdapter.OnSeekCompleteListener;
import com.starcor.report.Column.Column;
import com.starcor.report.Column.ErrorColumn;
import com.starcor.report.Column.ErrorColumn.Builder;
import com.starcor.report.Column.ReportJSONObject;
import com.starcor.report.ReportArea;
import com.starcor.report.ReportInfo;
import com.starcor.report.ReportMessage;
import com.starcor.report.ReportPageInfo;
import com.starcor.report.ReportState;
import com.starcor.report.ReportUtil;
import com.starcor.report.cdn.CDNReportHelper;
import com.starcor.report.cdn.CDNReportHelper.ChangeCodeRateCategory;
import com.starcor.report.cdn.CDNReportHelper.PlayStep;
import com.starcor.report.cdn.CDNReportHelper.PlayType;
import com.starcor.report.cdn.CDNReportHelper.Quality;
import com.starcor.report.cdn.CDNReportIF2Builder.ReportType;
import com.starcor.report.pay.PayReportHelper;
import com.starcor.report.pay.TvPayInfo;
import com.starcor.sccms.api.SccmsApiGetChannelListV2Task.ISccmsApiGetChannelListV2TaskListener;
import com.starcor.sccms.api.SccmsApiGetPlaybillTask.ISccmsApiGetPlaybillTaskListener;
import com.starcor.sccms.api.SccmsApiGetTerminalRealtimeParamsTask.IGetTerminalRealtimeParamsTaskListener;
import com.starcor.sccms.api.SccmsApiRequestVideoPlayV2Task.ISccmsApiRequestVideoPlayV2TaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import org.json.JSONException;

public class MplayerPlaybackView extends RelativeLayout
{
  private static final int BUFFERING_LASTTIME_BELOW_WATERLINE = 3;
  private static final int BUFFERING_LASTTIME_UPON_WATERLINE = 120;
  private static final int CONTINUE_PLAY_LIMIT_TAIL = 10;
  private static final int DISPLAY_VIEW_CONTROLL_PANEL = 4;
  private static final int DISPLAY_VIEW_EPISODE = 8;
  private static final int DISPLAY_VIEW_ERROR_NOTICE = 16;
  private static final int DISPLAY_VIEW_LOADING = 2;
  private static final int DISPLAY_VIEW_MENU = 64;
  private static final int DISPLAY_VIEW_ORDER_NOTICE = 512;
  private static final int DISPLAY_VIEW_QUIT = 256;
  private static final int DISPLAY_VIEW_RECOMMEND = 128;
  private static final int DISPLAY_VIEW_RESERVATION_DIALOG = 32;
  private static final int DISPLAY_VIEW_TOAST_NOTICE = 2048;
  private static final int DISPLAY_VIEW_VIDEO = 1;
  private static final int DISPLAY_VIEW_WEB = 1024;
  private static final int LOG_PRINT_INTERVAL = 10;
  private static final int MSG_OTTTV_GET_STREAM_INFO = 100;
  private static final int NOTICE_TIME_LEN_NOTICE = 6;
  private static final int NOTICE_TIME_LEN_NO_TSTV = 10;
  private static final int NOTICE_TIME_LEN_OPGUID = 12;
  private static final int NO_OPERATION_CONTROL_TIME = 16;
  private static final int RESERVATION_CHECK_INTERVAL = 20;
  private static final int SERIES_PLAY_NOTICE_TIME_LEN = 10;
  private static final int SHOW_VIEW_TIME_LENGTH = 16;
  private static final String TAG = "MplayerPlaybackView";
  private static int dragCount = 0;
  private static int heartbeatCount = 0;
  private boolean accessCMSFinalInvoke = false;
  private String activityId = "";
  private String activity_id = "";
  private AdManager adManager;
  private MplayerADView adView;
  private String airPlayState = "not_play";
  private ApiTaskControl apiTaskControl;
  private int beginToNotifySeriesPlayPos = 0;
  private int bufferPeriodCount;
  private int bufferTimeCount = 0;
  private int bufferTimeLength = 0;
  private String cameraposition = "";
  private String cdnAccessCMSApiUrl = "";
  private boolean cdnAccessCMSSuccess = false;
  private boolean cdnAccessFirstFrame = false;
  private Runnable cdnTimerRunnable = null;
  private CDNReportHelper.ChangeCodeRateCategory changeCodeRateCategory;
  private ChannelInfoV2 channelListDataV2;
  private Context context;
  private InteractiveMessage curInteractiveMsg;
  public String currentChannelCaps = "";
  private int currentPlayPos = 0;
  private String currentSelectedVideoId;
  private String dialogMsg = "";
  private int displayViews = 0;
  private int duration = 0;
  private int endToNotifySeriesPlayPos = 0;
  private ReportJSONObject heartbeatColumn;
  private String import_id = "";
  private String interactiveHistoryWebUrl = "";
  private ApiTaskControl interactiveMenuTaskControl;
  private MplayerInteractiveView interactiveView;
  private MplayerInteractiveWebView interactiveWebView;
  private boolean isAdPlaying = false;
  private boolean isBuffering = false;
  private boolean isDestroyed = false;
  private boolean isDragged = false;
  private boolean isEventStopPlayer = false;
  private boolean isNeedToNotifyNetwork = false;
  private boolean isNeedToNotifyNoTSTV = false;
  private boolean isNeedToNotifyOpGuid = false;
  private boolean isNeedToNotifySeriesPlay = false;
  private boolean isNotifiedPlaySuccess = false;
  private boolean isPlayerComplete = false;
  private boolean isPlayerSucceed = false;
  private boolean isPlayingRecommendProgram = false;
  private boolean isProgramItemClick = false;
  private boolean isReportGflow = false;
  private boolean isReportLoad = false;
  private boolean isReportPlay = false;
  private boolean isReportStop = false;
  private boolean isSeriesPlayNotifyDisplay = false;
  private boolean isStartToCheckBuffering = false;
  private boolean isUseP2p = false;
  private int lastPlayPos = 0;
  private int lastPlayStatus = 1;
  private int liveMsgShowCount = 0;
  private String live_video_type = "";
  private long loadEndTime;
  private long loadStartTime;
  private MplayerV2.IMplayerV2Listener lsnr = null;
  private MediaPlayerAdapter.OnCompletionListener mCompletionListener = new MediaPlayerAdapter.OnCompletionListener()
  {
    public void onCompletion(MediaPlayerAdapter paramAnonymousMediaPlayerAdapter)
    {
      Logger.i("MplayerPlaybackView", "OnCompletionListener.onCompletion()");
      ReportInfo.getInstance().setEntranceSrc(ReportArea.getValue("mplayer_same_episodic"));
      MplayerPlaybackView.this.playNextPlayBackProgram();
    }
  };
  private MediaPlayerAdapter.OnErrorListener mErrorListener = new MediaPlayerAdapter.OnErrorListener()
  {
    public boolean onError(MediaPlayerAdapter paramAnonymousMediaPlayerAdapter, int paramAnonymousInt1, int paramAnonymousInt2)
    {
      Logger.i("MplayerPlaybackView", "OnErrorListener.onError() what:" + paramAnonymousInt1 + ", extra:" + paramAnonymousInt2);
      MplayerPlaybackView.this.mdc.onPlayerError(paramAnonymousInt1);
      if (MplayerPlaybackView.this.isEventStopPlayer)
      {
        Logger.e("MplayerPlaybackView", "MediaPlayerAdapter.OnErrorListener 播放器已经停止！");
        return true;
      }
      if (MplayerPlaybackView.this.isNotifiedPlaySuccess)
        MplayerPlaybackView.this.reportCDNIF2Error("400000", MplayerPlaybackView.this.lastPlayPos);
      while (true)
      {
        MplayerPlaybackView.access$2202(MplayerPlaybackView.this, false);
        MplayerPlaybackView.this.displayLoadingInfo(0);
        if (!MplayerPlaybackView.this.isRetryRequest())
          break;
        MplayerPlaybackView.this.mplayerRetryLogic.printError("播放出错重试");
        MplayerPlaybackView.this.mplayerRetryLogic.addPlayRequestCount();
        MplayerPlaybackView.this.addPlayRequestTask();
        return true;
        MplayerPlaybackView.this.reportCDNIF1AccessCacheFail(MplayerPlaybackView.this.playUrl);
      }
      MplayerPlaybackView.this.mplayerRetryLogic.printError("播放出错弹框");
      MplayerPlaybackView.this.lsnr.onPreLoadingViewDisplay(4, "");
      if (MplayerPlaybackView.this.checkErrorNoticeViewCanDisplay())
      {
        MplayerPlaybackView.this.hideCenterViews();
        String str = "MediaPlayerAdapter.OnErrorListener what:" + paramAnonymousInt1 + ",extra:" + paramAnonymousInt2 + ",地址:" + MplayerPlaybackView.this.playUrl;
        MplayerPlaybackView.this.reportError("1003009", str);
        MplayerPlaybackView.this.mdc.onError("1003009");
        MplayerPlaybackView.this.lsnr.onErrorNoticeViewDisplay(0, "1003009", str, true);
        MplayerPlaybackView.access$1476(MplayerPlaybackView.this, 16);
        MplayerPlaybackView.access$1502(MplayerPlaybackView.this, ApplicationException.getErrorDiscrib("1003009"));
      }
      MplayerPlaybackView.access$2402(MplayerPlaybackView.this, true);
      MplayerPlaybackView.this.onEventStopPlayer();
      return true;
    }
  };
  private StringBuilder mFormatBuilder;
  private Formatter mFormatter;
  private Handler mHandler = new Handler(Looper.getMainLooper())
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default:
        return;
      case 100:
      }
      MplayerPlaybackView.this.processGetStreamInfo(paramAnonymousMessage);
    }
  };
  private MediaPlayerAdapter.OnInfoListener mInfoListener = new MediaPlayerAdapter.OnInfoListener()
  {
    public boolean onInfo(MediaPlayerAdapter paramAnonymousMediaPlayerAdapter, int paramAnonymousInt1, int paramAnonymousInt2)
    {
      if ((paramAnonymousInt1 == 3) || (paramAnonymousInt1 == 1013))
      {
        MplayerPlaybackView.this.displayLoadingInfo(4);
        MplayerPlaybackView.this.lsnr.onPreLoadingViewDisplay(4, "");
      }
      return false;
    }
  };
  private long mMplayerQuitPageStarTime = -1L;
  private String mPlayBackRequestUrl = "";
  private ArrayList<PlayBillStruct> mPlayBillStruct;
  private MediaPlayerAdapter.OnPreparedListener mPreparedListener = new MediaPlayerAdapter.OnPreparedListener()
  {
    public void onPrepared(MediaPlayerAdapter paramAnonymousMediaPlayerAdapter)
    {
      Logger.i("MplayerPlaybackView", "onPrepared");
      ReportState.setStateStartTime(3);
      MplayerPlaybackView.this.mplayerRetryLogic.state = 257;
      MplayerPlaybackView.this.lsnr.onPreLoadingViewDisplay(4, "");
      MplayerPlaybackView.access$302(MplayerPlaybackView.this, "in_play");
      MplayerPlaybackView.access$402(MplayerPlaybackView.this, Tools.time2sec(Tools.getTimeString()));
      String str = GlobalLogic.getInstance().getVideoLayoutRatio();
      if ("16v9".equals(str))
      {
        MplayerPlaybackView.this.mpCore.changeVideoLayoutTo16v9();
        MplayerPlaybackView.access$602(MplayerPlaybackView.this, MplayerPlaybackView.this.mpCore.getDuration());
        if (MplayerPlaybackView.this.duration <= 0)
          break label478;
        MplayerPlaybackView.access$702(MplayerPlaybackView.this, MplayerPlaybackView.this.duration / MplayerPlaybackView.this.seekbar.getMax());
        MplayerPlaybackView.this.seekbar.setRightSideTime(MplayerPlaybackView.this.timeToHHMMSSShow(MplayerPlaybackView.this.duration));
        MplayerPlaybackView.access$902(MplayerPlaybackView.this, -10000 + MplayerPlaybackView.this.duration);
        Logger.i("MplayerPlaybackView", "onPrepared() duration:" + MplayerPlaybackView.this.duration + ", timeStr:" + MplayerPlaybackView.this.timeToHHMMSSShow(MplayerPlaybackView.this.duration));
      }
      while (true)
      {
        MplayerPlaybackView.this.mdc.onPlayerFirstStart(MplayerPlaybackView.this.playUrl, 3, MplayerPlaybackView.this.currentPlayPos, MplayerPlaybackView.this.tstvBeginTime, MplayerPlaybackView.this.tstvTimeLen, MplayerPlaybackView.this.getChannelIdForDataCollect(), MplayerPlaybackView.this.mpCore, MplayerPlaybackView.this.pParams.nns_videoInfo.packageId, MplayerPlaybackView.this.pParams.nns_videoInfo.film_name, MplayerPlaybackView.this.duration, MplayerPlaybackView.this.pParams);
        MplayerPlaybackView.this.mpCore.start();
        if (!MplayerPlaybackView.this.isStartToCheckBuffering)
          MplayerPlaybackView.access$2202(MplayerPlaybackView.this, true);
        InteractiveManager.getInstance().subscribeCommChannel();
        return;
        if ("4v3".equals(str))
        {
          MplayerPlaybackView.this.mpCore.changeVideoLayoutTo4v3();
          break;
        }
        if ("full".equals(str))
        {
          MplayerPlaybackView.this.mpCore.changeVideoLayoutToFullScreen();
          break;
        }
        if ("default".equals(str))
        {
          MplayerPlaybackView.this.mpCore.changeVideoLayoutToDefault();
          break;
        }
        if ("235".equals(str))
        {
          MplayerPlaybackView.this.mpCore.changeVideoLayoutTo2351();
          break;
        }
        MplayerPlaybackView.this.mpCore.changeVideoLayoutTo16v9();
        break;
        label478: Logger.i("MplayerPlaybackView", "onPrepared() duration:" + MplayerPlaybackView.this.duration + ", timeStr:" + MplayerPlaybackView.this.timeToHHMMSSShow(MplayerPlaybackView.this.duration));
        MplayerPlaybackView.this.stopToPlay();
        MplayerPlaybackView.this.mdc.onError("1003300");
        if (MplayerPlaybackView.this.checkErrorNoticeViewCanDisplay())
        {
          MplayerPlaybackView.this.lsnr.onErrorNoticeViewDisplay(0, "1003300", "MplayerPlaybackView.onPrepared()", true);
          MplayerPlaybackView.this.hideCenterViews();
          MplayerPlaybackView.access$1476(MplayerPlaybackView.this, 16);
          MplayerPlaybackView.access$1502(MplayerPlaybackView.this, ApplicationException.getErrorDiscrib("1003300"));
        }
      }
    }
  };
  private MediaPlayerAdapter.OnSeekCompleteListener mSeekCompleteListener = new MediaPlayerAdapter.OnSeekCompleteListener()
  {
    public void onSeekComplete(MediaPlayerAdapter paramAnonymousMediaPlayerAdapter)
    {
      Logger.i("MplayerPlaybackView", "OnSeekCompleteListener.onSeekComplete()");
      if (MplayerPlaybackView.this.playStatusFlag != 2)
      {
        MplayerPlaybackView.this.mpCore.start();
        MplayerPlaybackView.access$3302(MplayerPlaybackView.this, 1);
        MplayerPlaybackView.this.seekbar.updatePlayStatus(MplayerPlaybackView.this.playStatusFlag);
      }
      MplayerPlaybackView.access$1702(MplayerPlaybackView.this, MplayerPlaybackView.this.mpCore.getCurrentPosition());
      MplayerPlaybackView.access$2602(MplayerPlaybackView.this, MplayerPlaybackView.this.currentPlayPos);
      Logger.i("MplayerPlaybackView", "OnSeekCompleteListener.onSeekComplete() currentPlayPos:" + MplayerPlaybackView.this.currentPlayPos);
    }
  };
  private long mdEndTime;
  private long mdStartTime;
  private MplayerDataCollector mdc;
  private MplayerMenuView menuView;
  private double minStepLen = 0.0D;
  private MediaPlayerCore mpCore = null;
  private MplayerRetryLogic mplayerRetryLogic = null;
  private boolean needCDNTimerReport = true;
  private boolean needReportHeartbeat = true;
  private int noOperationTimer = 0;
  private int noOperationTimerForQuitView = 0;
  private MplayerNoticeView notice;
  private String noticeNoTSTV = "";
  private String noticeOpGuid = "";
  private boolean notifyChanngeQuality = true;
  private int notifyChanngeQualityCountter = 0;
  private boolean notifyNetWorkLow = true;
  private int notifyNetWorkLowCountter = 0;
  private int notifyNetworkTimer = 0;
  private int notifyNoTSTVTimer = 0;
  private int notifyOpGuidTimer = 0;
  private MplayerV2.OnPlayerControllEventCallback onPlayerControllEventCallback;
  private boolean ottStreamHasSpeed = false;
  private int ottStreamIndex;
  private long p2pStarTime;
  private PlayInfo pInfo = null;
  private PlayerIntentParams pParams = null;
  private String pUrl = "";
  private int pay;
  private MplayerPlayStateView playState;
  private int playStatusFlag = 0;
  private String playUrl = "";
  private MplayerPlaybackEpisodeView playbillView;
  private int posNoMovingTimes = 0;
  private MplayerQuitXulPage quitView;
  private String reportWayType = "";
  private String reportway_type = "";
  private Runnable runnable = null;
  private int seekStartPos = 0;
  private MplayerSeekBar seekbar;
  private int setLastPlayStustimes = 0;
  private long startPlayTime = 0L;
  private String str_date_format = "MM月dd日";
  private String str_replay_recommond = "回看推荐";
  private String str_today = "今日";
  private int timerCount = 0;
  private MplayerTitleView title;
  public TokenDialog tokenDialog;
  private long tstvBeginTime;
  private long tstvTimeLen;
  private String tv_channel = "";
  private String type_name = "";

  public MplayerPlaybackView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.context = paramContext;
    initViews();
  }

  public MplayerPlaybackView(Context paramContext, MplayerV2.IMplayerV2Listener paramIMplayerV2Listener)
  {
    super(paramContext);
    this.context = paramContext;
    this.lsnr = paramIMplayerV2Listener;
    this.apiTaskControl = new ApiTaskControl();
    this.interactiveMenuTaskControl = new ApiTaskControl();
    this.mdc = MplayerDataCollector.create();
    initViews();
  }

  private void addOrRemoveInteractiveMenu(boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    String str;
    if (paramBoolean)
    {
      str = "添加";
      Logger.d("MplayerPlaybackView", str + "互动菜单");
      if (this.menuView != null)
      {
        if (!paramBoolean)
          break label87;
        if (!this.menuView.hasItem(128).booleanValue())
        {
          this.menuView.addItemFlag(128);
          this.menuView.refreshView();
        }
      }
    }
    label87: 
    while (!this.menuView.hasItem(128).booleanValue())
    {
      return;
      str = "删除";
      break;
    }
    this.menuView.removeItemByFlag(128);
    this.menuView.refreshView();
  }

  private void addPlayRequestTask()
  {
    stopToPlay();
    initPlayParams();
    setTitleText();
    Logger.i("MplayerPlaybackView", "addPlayRequestTask");
    this.mdc.startCollector();
    this.isBuffering = true;
    this.mplayerRetryLogic.print("请求开始");
    if (this.curInteractiveMsg != null)
    {
      if (isInteractiveMsgShowing())
        setInteractiveViewVisible(false);
      this.curInteractiveMsg = null;
    }
    ServerApiManager localServerApiManager = ServerApiManager.i();
    String str1 = this.pParams.nns_videoInfo.videoId;
    int i = this.pParams.nns_videoInfo.videoType;
    String str2 = this.pParams.nns_videoInfo.packageId;
    String str3 = this.pParams.nns_videoInfo.categoryId;
    int j = this.pParams.nns_index;
    String str4 = this.pParams.mediaQuality;
    String str5 = this.pParams.nns_day;
    String str6 = this.pParams.nns_beginTime;
    String str7 = this.pParams.nns_timeLen;
    String str8 = this.currentChannelCaps;
    String str9 = this.mplayerRetryLogic.svrip;
    String str10 = String.valueOf(this.mplayerRetryLogic.request_times);
    SccmsApiRequestVideoPlayV2TaskListener localSccmsApiRequestVideoPlayV2TaskListener = new SccmsApiRequestVideoPlayV2TaskListener();
    int k = localServerApiManager.ApiRequestVideoPlayV2(str1, i, str2, str3, j, "", str4, str5, str6, str7, "", str8, str9, str10, localSccmsApiRequestVideoPlayV2TaskListener);
    this.loadStartTime = Tools.time2sec(Tools.getTimeString());
    this.apiTaskControl.clear();
    this.apiTaskControl.addTaskLabel(k, "ApiRequestVideoPlay", this.pParams.nns_videoInfo.videoId);
    updateInteractiveMenu();
  }

  private void cancelTasks()
  {
    Logger.i("MplayerPlaybackView", "cancelTasks");
    if (this.apiTaskControl != null)
      this.apiTaskControl.clear();
  }

  private void checkDisplayNotice()
  {
    if (this.isNeedToNotifyNoTSTV)
    {
      str = this.noticeNoTSTV;
      if (this.notifyNoTSTVTimer == 0)
        this.notice.setAlarmTime(0, false);
      this.notifyNoTSTVTimer = (1 + this.notifyNoTSTVTimer);
      Logger.i("MplayerPlaybackView", "checkDisplayNotice() notifyNoTSTVTimer:" + this.notifyNoTSTVTimer);
      if (this.notifyNoTSTVTimer <= 10)
      {
        this.notice.setPlayNotice(str);
        if ((0x100 & this.displayViews) == 0)
        {
          this.notice.setVisibility(0);
          this.displayViews = (0x800 | this.displayViews);
        }
      }
    }
    while (!this.isPlayingRecommendProgram)
    {
      String str;
      return;
      this.displayViews = (0xFFFFF7FF & this.displayViews);
      this.notice.setVisibility(4);
      this.notice.setPlayNotice("");
      this.isNeedToNotifyNoTSTV = false;
      this.notifyNoTSTVTimer = 0;
    }
    Logger.i("MplayerPlaybackView", "isPlayingRecommendProgram = true");
  }

  private boolean checkErrorNoticeViewCanDisplay()
  {
    displayLoadingInfo(4);
    (0x0 | 0x230);
    return (0x230 & this.displayViews) <= 0;
  }

  private void checkInBufferState()
  {
    if (!this.isStartToCheckBuffering);
    label233: 
    do
    {
      do
      {
        do
        {
          return;
          if (this.cdnAccessCMSSuccess)
            break;
        }
        while (this.timerCount % 10 != 0);
        Logger.e("MplayerPlaybackView", "IF1 访问CMS 接口还没有成功，不允许检查缓冲!");
        return;
        if (this.currentPlayPos != this.lastPlayPos)
          break;
        if (this.playStatusFlag != 2);
        for (this.posNoMovingTimes = (1 + this.posNoMovingTimes); ; this.posNoMovingTimes = 0)
        {
          if ((this.posNoMovingTimes < 3) || (this.posNoMovingTimes > 120))
            break label233;
          if (!this.isBuffering)
          {
            this.isBuffering = true;
            if (this.isPlayerSucceed)
            {
              this.bufferTimeCount = (1 + this.bufferTimeCount);
              if (isNaturalBuffer())
              {
                this.bufferPeriodCount = (1 + this.bufferPeriodCount);
                if (1 == this.bufferPeriodCount)
                  reportCDNIF2Buffer(this.bufferPeriodCount, this.lastPlayPos);
              }
            }
            this.mdc.onPlayerBufferBegin(BufferEnum.BUFFER_COMMON, this.lastPlayPos);
            ReportState.setStateStartTime(7);
            ReportState.setBufferCause(2);
          }
          this.bufferTimeLength = (1 + this.bufferTimeLength);
          displayLoadingInfo(0);
          if (this.ottStreamIndex > 0)
            OTTTV.getStreamInfo(this.mHandler, 100, this.ottStreamIndex);
          if (this.timerCount % 10 != 0)
            break;
          Logger.i("MplayerPlaybackView", "playerTimerSlowTask() video play is buffering");
          return;
        }
      }
      while (this.posNoMovingTimes <= 120);
      if (!this.isNotifiedPlaySuccess)
        reportCDNIF1AccessCacheFail(this.playUrl);
      while ((!this.isPlayerSucceed) && (isRetryRequest()))
      {
        this.mplayerRetryLogic.printError("缓冲失败重试");
        this.mplayerRetryLogic.addPlayRequestCount();
        addPlayRequestTask();
        this.isStartToCheckBuffering = false;
        this.isBuffering = false;
        return;
        reportCDNIF2Error("303000", this.lastPlayPos);
      }
      this.mplayerRetryLogic.printError("缓冲失败弹框");
      this.isStartToCheckBuffering = false;
      this.mdc.onError("1003007");
      this.isBuffering = false;
      this.mdc.onPlayerBufferEnd(this.lastPlayPos);
      reportPlayQuality(7);
      reportCDNIF2Complete(this.bufferPeriodCount, this.lastPlayPos);
      stopCDNTimerReport();
      this.mpCore.stop();
      displayLoadingInfo(4);
    }
    while (!checkErrorNoticeViewCanDisplay());
    hideCenterViews();
    this.lsnr.onErrorNoticeViewDisplay(0, "1003007", "", true);
    reportError("1003007", "checkInBufferState 缓冲失败");
    this.displayViews = (0x10 | this.displayViews);
    this.dialogMsg = ApplicationException.getErrorDiscrib("1003007");
    return;
    if (this.isBuffering)
    {
      this.isBuffering = false;
      this.mdc.onPlayerBufferEnd(this.lastPlayPos);
      reportPlayQuality(7);
    }
    this.isPlayerSucceed = true;
    displayLoadingInfo(4);
    checkPlaySuccessed();
    this.lastPlayPos = this.currentPlayPos;
    this.posNoMovingTimes = 0;
    this.seekbar.refreshProgressByPlay(this.currentPlayPos);
  }

  private void checkInteractiveMsg()
  {
    if ((isInteractiveMsgShowing()) && (!checkInteractiveMsgShowOver()))
      return;
    checkShowLiveInteractiveMsg();
  }

  private boolean checkInteractiveMsgShowOver()
  {
    if (this.curInteractiveMsg != null)
    {
      if (this.curInteractiveMsg.msgType == 2)
      {
        int i = this.liveMsgShowCount;
        this.liveMsgShowCount = (i + 1);
        if (i >= 1000L * this.curInteractiveMsg.showTime / 499L)
        {
          Logger.d("MplayerPlaybackView", "隐藏实时互动消息：" + this.curInteractiveMsg.title);
          setInteractiveViewVisible(false);
        }
      }
    }
    else
      return true;
    return false;
  }

  private void checkNoOperation()
  {
    if (((0x40 & this.displayViews) > 0) || (((0x8 & this.displayViews) > 0) && (!this.playbillView.isLoading())) || ((0x4 & this.displayViews) > 0))
      this.noOperationTimer = (1 + this.noOperationTimer);
    if (((0x4 & this.displayViews) > 0) && (this.noOperationTimer >= 16) && ((0x2 & this.displayViews) == 0))
    {
      this.seekbar.hide();
      this.title.hide();
      this.displayViews = (0xFFFFFFFB & this.displayViews);
    }
    if (this.noOperationTimer >= 16)
    {
      if ((0x40 & this.displayViews) > 0)
      {
        setMenuViewVisibility(4);
        this.displayViews = (0xFFFFFFBF & this.displayViews);
      }
      if ((0x8 & this.displayViews) > 0)
      {
        this.playbillView.setVisibility(4);
        this.displayViews = (0xFFFFFFF7 & this.displayViews);
      }
    }
  }

  private boolean checkPlayInfoData()
  {
    if (this.pInfo.state == 1)
    {
      Logger.e("MplayerPlaybackView", "SccmsApiRequestVideoPlayTaskListener.onSuccess() 没有授权");
      this.mdc.onError("1003004");
      if (checkErrorNoticeViewCanDisplay())
      {
        PurchaseParam localPurchaseParam = new PurchaseParam(true, this.pParams.nns_videoInfo.videoId, "1");
        localPurchaseParam.setVideoName(this.pParams.nns_videoInfo.name);
        localPurchaseParam.setPackageId(this.pParams.nns_videoInfo.packageId);
        localPurchaseParam.setCategoryId(this.pParams.nns_videoInfo.categoryId);
        localPurchaseParam.def = this.pParams.mediaQuality;
        this.tokenDialog.setPurchaseInfo(localPurchaseParam);
        showTokenDialog(20003, false);
        hideCenterViews();
        this.displayViews = (0x10 | this.displayViews);
      }
    }
    do
    {
      do
      {
        return false;
        if (this.pInfo.state != 2)
          break;
        Logger.e("MplayerPlaybackView", "SccmsApiRequestVideoPlayTaskListener.onSuccess() 数据异常");
        this.mdc.onError("1003002");
      }
      while (!checkErrorNoticeViewCanDisplay());
      String str6 = "MplayerTimeshiftView.checkPlayInfoData() url:" + this.pInfo.playUrl + ", pinfo.state:" + this.pInfo.state;
      this.lsnr.onErrorNoticeViewDisplay(0, "1003002", str6, true);
      reportError("1003002", str6);
      hideCenterViews();
      this.displayViews = (0x10 | this.displayViews);
      this.dialogMsg = ("1003002" + ApplicationException.getErrorDiscrib("1003002"));
      return false;
      if (this.pInfo.state == 3)
      {
        Logger.e("MplayerPlaybackView", "SccmsApiRequestVideoPlayTaskListener.onSuccess() 内容未到播放时间");
        this.mdc.onError("1003300");
        if (checkErrorNoticeViewCanDisplay())
        {
          String str5 = "MplayerTimeshiftView.checkPlayInfoData() url:" + this.pInfo.playUrl + ", pinfo.state:" + this.pInfo.state;
          this.lsnr.onErrorNoticeViewDisplay(0, "1003300", str5, true);
          reportError("1003300", str5);
          hideCenterViews();
          this.displayViews = (0x10 | this.displayViews);
        }
        this.dialogMsg = ("1003300" + ApplicationException.getErrorDiscrib("1003300"));
        return false;
      }
      if (this.pInfo.state == 5)
      {
        Logger.e("MplayerPlaybackView", "SccmsApiRequestVideoPlayTaskListener.onSuccess() 内容已过期");
        this.mdc.onError("1003301");
        if (checkErrorNoticeViewCanDisplay())
        {
          String str4 = "MplayerTimeshiftView.checkPlayInfoData() url:" + this.pInfo.playUrl + ", pinfo.state:" + this.pInfo.state;
          this.lsnr.onErrorNoticeViewDisplay(0, "1003301", str4, true);
          reportError("1003301", str4);
          hideCenterViews();
          this.displayViews = (0x10 | this.displayViews);
        }
        this.dialogMsg = ("1003301" + ApplicationException.getErrorDiscrib("1003301"));
        return false;
      }
      if (this.pInfo.state == 9)
      {
        Logger.e("MplayerPlaybackView", "SccmsApiRequestVideoPlayTaskListener.onSuccess() Token失效");
        return false;
      }
      if (this.pInfo.state == 14)
      {
        this.mdc.onError("1002300");
        if (checkErrorNoticeViewCanDisplay())
        {
          String str3 = "MplayerVODView.checkPlayInfoData()state:" + this.pInfo.state;
          hideCenterViews();
          this.lsnr.onErrorNoticeViewDisplay(0, "1002300", str3, true);
          this.displayViews = (0x10 | this.displayViews);
        }
        this.dialogMsg = ApplicationException.getErrorDiscrib("1002300");
        return false;
      }
      if ((this.pInfo.state != 0) && (this.pInfo.state != 6))
      {
        Logger.e("MplayerPlaybackView", "SccmsApiRequestVideoPlayTaskListener.onSuccess() 其他错误");
        this.mdc.onError("1003001");
        if (checkErrorNoticeViewCanDisplay())
        {
          String str2 = "MplayerTimeshiftView.checkPlayInfoData() url:" + this.pInfo.playUrl + ", pinfo.state:" + this.pInfo.state;
          this.lsnr.onErrorNoticeViewDisplay(0, "1003001", str2, true);
          reportError("1003001", str2);
          hideCenterViews();
          this.displayViews = (0x10 | this.displayViews);
        }
        this.dialogMsg = ("1003001" + ApplicationException.getErrorDiscrib("1003001"));
        return false;
      }
      if ((!TextUtils.isEmpty(this.pInfo.playUrl)) && (!"null".equalsIgnoreCase(this.pInfo.playUrl)))
        break;
      Logger.e("MplayerPlaybackView", "SccmsApiRequestVideoPlayTaskListener.onSuccess() 播放地址空");
      this.mdc.onError("1003005");
    }
    while (!checkErrorNoticeViewCanDisplay());
    String str1 = "MplayerTimeshiftView.checkPlayInfoData() url:" + this.pInfo.playUrl + ", pinfo.state:" + this.pInfo.state;
    this.lsnr.onErrorNoticeViewDisplay(0, "1003005", str1, true);
    reportError("1003005", str1);
    hideCenterViews();
    this.displayViews = (0x10 | this.displayViews);
    this.dialogMsg = ("1003005" + ApplicationException.getErrorDiscrib("1003005"));
    return false;
    return true;
  }

  private void checkPlaySuccessed()
  {
    if ((this.isPlayerSucceed) && (!this.isNotifiedPlaySuccess))
    {
      this.lsnr.onPreLoadingViewDisplay(4, "");
      this.isNotifiedPlaySuccess = true;
      this.isProgramItemClick = false;
      reportPlayQuality(2);
      reportPlayerState(3);
      reportCDNIF1AccessCacheSuccess(this.playUrl);
      startCDNTimerReport();
    }
    if ((this.isPlayerSucceed) && (this.isProgramItemClick))
    {
      this.isProgramItemClick = false;
      reportPlayQuality(2);
      reportPlayerState(3);
    }
  }

  private void checkReservationNotice()
  {
    if (this.timerCount % 20 != 0);
    long l;
    do
    {
      return;
      l = SystemTimeManager.getCurrentServerTime();
    }
    while ((ReservationService.getinstance().checkReservationCount(l) <= 0) || ((0x20 & this.displayViews) > 0));
    this.displayViews = (0x20 | this.displayViews);
    this.lsnr.onReservationDialogDisplay(0, l);
  }

  private boolean checkShowLiveInteractiveMsg()
  {
    LiveTopicMessageData localLiveTopicMessageData = InteractiveManager.getInstance().getMessageData();
    if (localLiveTopicMessageData != null)
    {
      this.curInteractiveMsg = InteractiveMessage.newFromLiveMsg(localLiveTopicMessageData);
      if (this.curInteractiveMsg != null)
      {
        Logger.d("MplayerPlaybackView", "开始显示实时互动消息: " + this.curInteractiveMsg.title + " ，显示 " + this.curInteractiveMsg.showTime + " s");
        return showInteractiveMsg(this.curInteractiveMsg);
      }
    }
    return false;
  }

  private void dealUserSeekEndOperator(boolean paramBoolean, long paramLong)
  {
    this.ottStreamHasSpeed = false;
    this.playStatusFlag = 1;
    this.noOperationTimer = 14;
    this.isStartToCheckBuffering = true;
    this.isBuffering = true;
    this.currentPlayPos = ((int)paramLong);
    this.lastPlayPos = ((int)paramLong);
    reportPlayerState(6);
    if (paramBoolean)
    {
      this.mpCore.seekTo((int)paramLong);
      return;
    }
    playNextPlayBackProgram();
  }

  private void displayLoadingInfo(int paramInt)
  {
    if (paramInt == 0)
    {
      if ((this.playState.getVisibility() == 0) || ((0x10 & this.displayViews) > 0) || ((0x8 & this.displayViews) > 0) || ((0x40 & this.displayViews) > 0) || ((0x100 & this.displayViews) > 0) || ((0x20 & this.displayViews) > 0) || (this.playStatusFlag == 2))
      {
        if ((0x2 & this.displayViews) == 0)
          return;
        this.lsnr.onLoadingViewDisplay(4);
        this.lsnr.onSpeedTextDisplay(4, "");
        this.displayViews = (0xFFFFFFFD & this.displayViews);
        return;
      }
      this.lsnr.onLoadingViewDisplay(0);
      this.lsnr.onSpeedTextDisplay(0, "");
      this.displayViews = (0x2 | this.displayViews);
      return;
    }
    this.lsnr.onLoadingViewDisplay(4);
    this.lsnr.onSpeedTextDisplay(4, "");
    this.displayViews = (0xFFFFFFFD & this.displayViews);
  }

  private void doPauseOrStartVideo()
  {
    if (this.mpCore.isPlaying())
    {
      doPauseVideo();
      reportPlayerState(4);
      return;
    }
    doStartVideo();
    reportPlayerState(5);
  }

  private void doPauseVideo()
  {
    if (this.isAdPlaying);
    do
    {
      return;
      Logger.i("MplayerPlaybackView", "doPauseVideo()");
      this.isStartToCheckBuffering = false;
      displayLoadingInfo(4);
      this.mpCore.pause();
      this.playStatusFlag = 2;
      this.mdc.onPlayerPause(this.currentPlayPos);
      this.seekbar.updatePlayStatus(this.playStatusFlag);
      this.airPlayState = "in_pause";
    }
    while (this.onPlayerControllEventCallback == null);
    this.onPlayerControllEventCallback.onPause();
  }

  private void doQuitPlay()
  {
    Logger.i("MplayerPlaybackView", "doQuitPlay()");
    onEventStopPlayer();
    stopToPlay();
    this.isEventStopPlayer = true;
    if (!this.isReportStop)
    {
      reportPlayerState(8);
      this.isReportStop = true;
    }
    finishPlayerAndQuit();
  }

  private void doStartVideo()
  {
    if (this.isAdPlaying);
    do
    {
      return;
      Logger.i("MplayerPlaybackView", "doStartVideo()");
      this.isStartToCheckBuffering = true;
      this.mdc.onPlayerStart(this.currentPlayPos);
      this.mpCore.start();
      this.playStatusFlag = 1;
      this.seekbar.updatePlayStatus(this.playStatusFlag);
      this.airPlayState = "in_play";
    }
    while (this.onPlayerControllEventCallback == null);
    this.onPlayerControllEventCallback.onPlay();
  }

  private void finishPlayerAndQuit()
  {
    if (this.context == null)
    {
      Logger.i("MplayerPlaybackView", "finishPlayerAndQuit() Context is null");
      return;
    }
    hideAllViews();
    ((Activity)this.context).finish();
  }

  private String formatUrl(String paramString)
  {
    if (isEnableM3U8())
      paramString = paramString.replace(".ts", ".m3u8");
    return paramString;
  }

  private String getCDNErrorCode(ServerApiCommonError paramServerApiCommonError)
  {
    return ReportUtil.parseCDNErrorCode(paramServerApiCommonError);
  }

  private CDNReportHelper.ChangeCodeRateCategory getChangeCodeRateCategory()
  {
    return this.changeCodeRateCategory;
  }

  private String getChannelIdForDataCollect()
  {
    String str = PinYinUtil.getFirstSpell(this.pParams.nns_videoInfo.film_name);
    if (TextUtils.isEmpty(str))
      str = getChannelIdFromUrl(this.playUrl);
    return str.toUpperCase();
  }

  private String getChannelIdFromUrl(String paramString)
  {
    String str = "";
    if ((paramString == null) || (TextUtils.isEmpty(paramString)))
      return str;
    Logger.i("MplayerPlaybackView", "getChannelIdFromUrl:" + paramString);
    String[] arrayOfString1 = paramString.split("/");
    if ((arrayOfString1 != null) && (arrayOfString1.length > 0))
    {
      String[] arrayOfString2 = arrayOfString1[(-1 + arrayOfString1.length)].split("\\.");
      if ((arrayOfString2 != null) && (arrayOfString2.length > 0))
        str = arrayOfString2[0];
    }
    return str;
  }

  private String getChannelNameForDataCollect()
  {
    String str;
    if ((this.pParams == null) || (this.pParams.nns_videoInfo == null))
      str = "";
    do
    {
      return str;
      str = this.pParams.nns_videoInfo.film_name;
    }
    while (!TextUtils.isEmpty(str));
    return this.pParams.nns_videoInfo.name;
  }

  private String getCurPageName()
  {
    if ((this.context != null) && ((this.context instanceof DialogActivity)))
      return ((DialogActivity)this.context).curPageInfo.page;
    return "";
  }

  private ChannelItemInfoV2 getItem(String paramString1, String paramString2)
  {
    ChannelItemInfoV2 localChannelItemInfoV2;
    if ((this.channelListDataV2 == null) || (this.channelListDataV2.channelList == null) || (this.channelListDataV2.channelList.size() <= 0))
    {
      localChannelItemInfoV2 = null;
      return localChannelItemInfoV2;
    }
    int i = this.channelListDataV2.channelList.size();
    for (int j = 0; ; j++)
    {
      if (j >= i)
        break label114;
      localChannelItemInfoV2 = (ChannelItemInfoV2)this.channelListDataV2.channelList.get(j);
      if ((localChannelItemInfoV2.categoryId != null) && (localChannelItemInfoV2.categoryId.equals(paramString1)) && (localChannelItemInfoV2.id != null) && (localChannelItemInfoV2.id.equals(paramString2)))
        break;
    }
    label114: return null;
  }

  private void getLocationUrl(String paramString)
  {
    Logger.i("MplayerPlaybackView", " AppFuncCfg.FUNCTION_OTTTV_P2P=" + AppFuncCfg.FUNCTION_OTTTV_P2P);
    Logger.i("MplayerPlaybackView", " AppFuncCfg.FUNCTION_OTTTV_PROXY=" + AppFuncCfg.FUNCTION_OTTTV_PROXY);
    if (this.isUseP2p)
    {
      Logger.i("getP2PUrl start....");
      this.p2pStarTime = System.currentTimeMillis();
      String str = GeneralUtils.getP2PUrl(this.pParams.nns_videoInfo.videoId, this.pParams.nns_videoInfo.videoType, this.pInfo.playUrl);
      Message localMessage = new Message();
      if (!TextUtils.isEmpty(str))
      {
        localMessage.what = 200;
        localMessage.arg1 = 0;
        localMessage.obj = str;
      }
      while (true)
      {
        new LocationObserver(Looper.getMainLooper()).sendMessage(localMessage);
        return;
        localMessage.what = -1;
        reportError("1004000", "获取P2P播放地址失败，使用原有播放地址：" + this.pInfo.playUrl);
      }
    }
    GeneralUtils.getLocationUrl(this.context, formatUrl(this.pInfo.playUrl), paramString, new LocationObserver(Looper.getMainLooper()));
  }

  private CDNReportHelper.Quality getQuality()
  {
    return CDNReportHelper.qualityFromString(this.pParams.mediaQuality);
  }

  private int getReportPt()
  {
    int i = 4;
    if (TextUtils.isEmpty(this.live_video_type))
      return i;
    try
    {
      int j = Integer.parseInt(this.live_video_type);
      if (j == 0)
      {
        if ("1".equals(this.reportway_type))
        {
          i = 4;
        }
        else
        {
          boolean bool = "0".equals(this.reportway_type);
          if (bool)
            i = 1;
        }
      }
      else if (j == 1)
        i = 2;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return i;
  }

  private ErrorColumn getVideoInfoForErrReport(String paramString1, String paramString2, String paramString3)
  {
    ErrorColumn.Builder localBuilder = new ErrorColumn.Builder(paramString1, paramString2);
    localBuilder.addServerCode(paramString3);
    if (this.pParams != null);
    try
    {
      VideoInfo localVideoInfo = this.pParams.nns_videoInfo;
      if (localVideoInfo != null)
      {
        localBuilder.addPlayType(getReportPt());
        localBuilder.addTpt(0);
        if ((this.pParams.nns_mediaIndexList != null) && (this.pParams.nns_mediaIndexList.size() > 0))
        {
          VideoIndex localVideoIndex = (VideoIndex)this.pParams.nns_mediaIndexList.get(this.pParams.nns_index);
          if (localVideoIndex != null)
          {
            localBuilder.addVid(localVideoIndex.id);
            localBuilder.addOvid(localVideoIndex.import_id);
            localBuilder.addSovid(localVideoIndex.serial_id);
          }
        }
        localBuilder.addPlid(localVideoInfo.videoId);
        localBuilder.addOplid(localVideoInfo.import_id);
        localBuilder.addSoplid(localVideoInfo.serial_id);
        localBuilder.addLcid(this.import_id);
        localBuilder.addSourceid(this.tv_channel);
        localBuilder.addStreamid(getChannelIdFromUrl(this.pUrl));
        localBuilder.addLn(getChannelNameForDataCollect());
        localBuilder.addLiveid(this.cameraposition);
      }
      return localBuilder.build();
    }
    catch (Exception localException)
    {
      while (true)
        Logger.w("ReportService", "unexpected exception: " + localException, localException);
    }
  }

  private void hideCenterViews()
  {
    if ((0x40 & this.displayViews) > 0)
    {
      setMenuViewVisibility(4);
      this.displayViews = (0xFFFFFFBF & this.displayViews);
    }
    if ((0x100 & this.displayViews) > 0)
    {
      this.displayViews = (0xFFFFFEFF & this.displayViews);
      this.quitView.setVisibility(8);
    }
    if ((0x8 & this.displayViews) > 0)
    {
      this.playbillView.setVisibility(4);
      this.displayViews = (0xFFFFFFF7 & this.displayViews);
    }
    if ((0x2 & this.displayViews) > 0)
    {
      displayLoadingInfo(4);
      this.displayViews = (0xFFFFFFFD & this.displayViews);
    }
    if ((0x400 & this.displayViews) > 0)
    {
      this.lsnr.onWebDialogDisplay(4, null);
      this.displayViews = (0xFFFFFBFF & this.displayViews);
    }
  }

  private void hideQuitView()
  {
    if ((this.quitView != null) && (this.quitView.isShown()))
      this.quitView.setVisibility(4);
  }

  private void initChannelInfo(ChannelItemInfoV2 paramChannelItemInfoV2)
  {
    if (paramChannelItemInfoV2 != null)
    {
      this.import_id = paramChannelItemInfoV2.import_id;
      this.cameraposition = paramChannelItemInfoV2.cameraposition;
      this.reportway_type = paramChannelItemInfoV2.reportway_type;
      this.tv_channel = paramChannelItemInfoV2.tv_channel;
      this.activity_id = paramChannelItemInfoV2.activity_id;
    }
    for (this.type_name = paramChannelItemInfoV2.type_name; ; this.type_name = "")
    {
      Logger.i("MplayerPlaybackView", "initChannelInfo:import_id=" + this.import_id + ",cameraposition=" + this.cameraposition + ",reportway_type=" + this.reportway_type + ",tv_channel=" + this.tv_channel + ",activity_id=" + this.activity_id + ",type_name=" + this.type_name);
      return;
      this.import_id = "";
      this.cameraposition = "";
      this.reportway_type = "";
      this.tv_channel = "";
      this.activity_id = "";
    }
  }

  private void initPlayParams()
  {
    this.bufferPeriodCount = 0;
    this.isPlayerComplete = false;
    this.isEventStopPlayer = false;
    this.isStartToCheckBuffering = true;
    this.isNeedToNotifySeriesPlay = false;
    this.cdnAccessCMSSuccess = false;
    if (this.pParams.playbackProgramSource == 2)
      this.isPlayingRecommendProgram = true;
    while (true)
    {
      this.playStatusFlag = 1;
      this.startPlayTime = System.currentTimeMillis();
      this.lastPlayPos = 0;
      this.currentPlayPos = 0;
      this.seekbar.reset();
      this.notifyNetworkTimer = 0;
      try
      {
        this.tstvBeginTime = new SimpleDateFormat("yyyyMMddHHmmss").parse(this.pParams.nns_day + this.pParams.nns_beginTime).getTime();
        this.tstvTimeLen = (1000 * Integer.valueOf(this.pParams.nns_timeLen).intValue());
        if ((this.pParams.nns_videoInfo.videoType == 0) && (this.pParams.played_time == 0L))
        {
          this.pParams.played_time = UserCPLLogic.getInstance().getPlayedTimeInPlayRecordListIncludeLocal(this.pParams.nns_videoInfo.videoId, this.pParams.nns_index);
          Logger.i("MplayerPlaybackView", "initParams auto playedTime:" + this.pParams.played_time);
          return;
          this.isPlayingRecommendProgram = false;
        }
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
        if ((this.pParams.nns_videoInfo.videoType != 0) && (this.pParams.nns_videoInfo.videoType != 1))
          this.pParams.played_time = 0L;
        Logger.i("MplayerPlaybackView", "initParams man playedTime:" + this.pParams.played_time);
      }
    }
  }

  private void initViews()
  {
    LayoutInflater.from(this.context).inflate(2130903068, this);
    this.mFormatBuilder = new StringBuilder();
    this.mFormatter = new Formatter(this.mFormatBuilder, Locale.getDefault());
    this.notice = ((MplayerNoticeView)findViewById(2131165301));
    this.notice.initSize(App.Operation(20.0F), App.Operation(20.0F), App.Operation(400.0F), App.Operation(60.0F));
    RelativeLayout.LayoutParams localLayoutParams1 = new RelativeLayout.LayoutParams(-2, -2);
    localLayoutParams1.addRule(14);
    localLayoutParams1.addRule(12);
    localLayoutParams1.bottomMargin = App.Operation(193.0F);
    this.notice.setLayoutParams(localLayoutParams1);
    this.notice.setVisibility(4);
    this.title = ((MplayerTitleView)findViewById(2131165298));
    this.title.initMenuNoticeText("点", "呼出菜单", "\"菜单键\"");
    this.title.initUIPara(2, App.OperationHeight(5), App.OperationHeight(5));
    RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams(-2, App.OperationHeight(367));
    localLayoutParams2.addRule(14);
    localLayoutParams2.addRule(10);
    this.title.setLayoutParams(localLayoutParams2);
    this.seekbar = ((MplayerSeekBar)findViewById(2131165300));
    App.OperationHeight(25);
    int i = App.OperationHeight(720);
    int j = App.OperationHeight(1280);
    this.seekbar.initUIPara(2, j, i);
    RelativeLayout.LayoutParams localLayoutParams3 = new RelativeLayout.LayoutParams(j, i);
    localLayoutParams3.addRule(14);
    localLayoutParams3.addRule(12);
    this.seekbar.setLayoutParams(localLayoutParams3);
    this.mplayerRetryLogic = new MplayerRetryLogic(GlobalEnv.getInstance().getTVRetryCount(), GlobalEnv.getInstance().getPlayRetryCount());
    this.quitView = new MplayerQuitXulPage(this.context);
    addView(this.quitView, -1, -1);
    this.quitView.setVisibility(8);
    MplayerQuitXulPage localMplayerQuitXulPage = this.quitView;
    MplayerQuitXulPage.OnItemClickListener local7 = new MplayerQuitXulPage.OnItemClickListener()
    {
      public void onNextEpisodeClick()
      {
      }

      public void onQuitClick()
      {
        MplayerPlaybackView.this.doQuitPlay();
        String str = MplayerPlaybackView.this.pParams.nns_videoInfo.videoId;
        MplayerPlaybackView.this.reportPlayerQuitButtonClick(str);
        if ((MplayerPlaybackView.this.context instanceof MplayerV2))
        {
          MplayerV2 localMplayerV2 = (MplayerV2)MplayerPlaybackView.this.context;
          if ((!TextUtils.isEmpty(localMplayerV2.isFromOut)) && (!localMplayerV2.isFromWeiXin))
            AppKiller.getInstance().killApp();
        }
      }

      public void onRecommendItemClick(int paramAnonymousInt, Object paramAnonymousObject)
      {
        FilmListItem localFilmListItem = (FilmListItem)paramAnonymousObject;
        Intent localIntent = new Intent(MplayerPlaybackView.this.getContext(), DetailPageActivity.class);
        localIntent.putExtra("xulPageId", "DetailPage");
        localIntent.putExtra("xulData", "");
        MovieData localMovieData = new MovieData();
        ReportData localReportData = new ReportData();
        localMovieData.videoId = localFilmListItem.video_id;
        localMovieData.videoType = localFilmListItem.video_type;
        localMovieData.name = localFilmListItem.film_name;
        localMovieData.viewType = localFilmListItem.viewType;
        localIntent.putExtra("movieData", localMovieData);
        localIntent.putExtra("reportData", localReportData);
        localIntent.addFlags(8388608);
        MplayerPlaybackView.this.getContext().startActivity(localIntent);
        MplayerPlaybackView.this.doQuitPlay();
      }
    };
    localMplayerQuitXulPage.setOnItemClickListener(local7);
    TokenDialogListener localTokenDialogListener = new TokenDialogListener();
    this.tokenDialog = new TokenDialog(this.context);
    this.tokenDialog.setListener(localTokenDialogListener);
    this.menuView = new MplayerMenuView(this.context);
    NewMenuItemOnClickListener localNewMenuItemOnClickListener = new NewMenuItemOnClickListener(null);
    this.menuView.setOnItemClickListener(localNewMenuItemOnClickListener);
    boolean bool = AppFuncCfg.FUNCTION_CHANGE_SCREEN_RATIO;
    int k = 0;
    if (bool)
      k = 0x0 | 0x2;
    this.menuView.setItemFlags(k);
    String str = GlobalLogic.getInstance().getVideoLayoutRatio();
    this.menuView.setItemState(2, str);
    if (this.menuView.getItemFlag() == 0)
      this.title.initMenuNoticeText("", "", "");
    new RelativeLayout.LayoutParams(-2, -2);
    RelativeLayout.LayoutParams localLayoutParams4 = new RelativeLayout.LayoutParams(-2, -2);
    localLayoutParams4.addRule(15);
    localLayoutParams4.leftMargin = App.Operation(56.0F);
    this.menuView.setLayoutParams(localLayoutParams4);
    this.menuView.refreshView();
    addView(this.menuView, localLayoutParams4);
    setMenuViewVisibility(4);
    this.playbillView = new MplayerPlaybackEpisodeView(this.context);
    RelativeLayout.LayoutParams localLayoutParams5 = new RelativeLayout.LayoutParams(-1, -1);
    localLayoutParams5.addRule(9);
    addView(this.playbillView, localLayoutParams5);
    this.playbillView.setVisibility(4);
    MplayerPlaybackEpisodeView localMplayerPlaybackEpisodeView = this.playbillView;
    MplayerPlaybackEpisodeView.OnPlayBillClickListener local8 = new MplayerPlaybackEpisodeView.OnPlayBillClickListener()
    {
      public void onChannelItemClick(String paramAnonymousString1, String paramAnonymousString2)
      {
        Logger.i("MplayerPlaybackView", "onChannelItemClick");
        MplayerPlaybackView.this.loadPlayBillData(paramAnonymousString1, 1);
      }

      public void onError(String paramAnonymousString)
      {
      }

      public void onProgramItemClick(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, String paramAnonymousString4, String paramAnonymousString5, String paramAnonymousString6)
      {
        Logger.i("MplayerPlaybackView", "onProgramItemClick===");
        MplayerPlaybackView.this.onEventStopPlayer();
        MplayerPlaybackView.access$3802(MplayerPlaybackView.this, true);
        MplayerPlaybackView.this.pParams.nns_videoInfo.videoId = paramAnonymousString1;
        MplayerPlaybackView.this.pParams.nns_videoInfo.categoryId = paramAnonymousString2;
        MplayerPlaybackView.this.pParams.nns_day = paramAnonymousString3;
        MplayerPlaybackView.this.pParams.nns_beginTime = paramAnonymousString4;
        MplayerPlaybackView.this.pParams.nns_timeLen = paramAnonymousString5;
        MplayerPlaybackView.this.pParams.nns_videoInfo.name = paramAnonymousString6;
        MplayerPlaybackView.this.lsnr.onPreLoadingViewDisplay(0, MplayerPlaybackView.this.pParams.nns_videoInfo.name);
        ChannelItemInfoV2 localChannelItemInfoV2 = MplayerPlaybackView.this.getItem(paramAnonymousString2, paramAnonymousString1);
        MplayerPlaybackView.this.initChannelInfo(localChannelItemInfoV2);
        MplayerPlaybackView.this.mplayerRetryLogic.initRetryParams();
        MplayerPlaybackView.this.onChangePlayUrlEvent();
        MplayerPlaybackView.this.addPlayRequestTask();
      }
    };
    localMplayerPlaybackEpisodeView.setOnPlayBillClickListener(local8);
    this.displayViews = 5;
    this.noticeOpGuid = "上键：选择节目单；左右：快退快进";
    this.noticeNoTSTV = "该频道不支持时移播放";
    this.playState = new MplayerPlayStateView(this.context);
    RelativeLayout.LayoutParams localLayoutParams6 = new RelativeLayout.LayoutParams(App.Operation(120.0F), App.Operation(120.0F));
    localLayoutParams6.addRule(13);
    addView(this.playState, localLayoutParams6);
    this.interactiveView = new MplayerInteractiveView(this.context);
    RelativeLayout.LayoutParams localLayoutParams7 = new RelativeLayout.LayoutParams(-1, -1);
    addView(this.interactiveView, localLayoutParams7);
    this.interactiveView.setBackgroundColor(16777215);
    this.interactiveView.setVisibility(4);
    this.interactiveWebView = new MplayerInteractiveWebView(this.context);
    addView(this.interactiveWebView, localLayoutParams7);
    this.interactiveWebView.setBackgroundColor(718758);
    this.interactiveWebView.setVisibility(4);
    this.isNeedToNotifyOpGuid = true;
    this.notifyOpGuidTimer = 0;
    if ((this.context != null) && ((this.context instanceof DialogActivity)))
      ((DialogActivity)this.context).initReportPageInfo(MplayerPlaybackView.class.getSimpleName());
    Logger.i("MplayerPlaybackView", "initViews() end");
  }

  private boolean isEnableM3U8()
  {
    return AppFuncCfg.FUNCTION_PLAYBACK_ENABLE_M3U8;
  }

  private boolean isErrorNoticeShowing()
  {
    return (0x230 & this.displayViews) > 0;
  }

  private boolean isInteractiveMsgShowing()
  {
    return this.interactiveView.getVisibility() == 0;
  }

  private boolean isNaturalBuffer()
  {
    return !this.isDragged;
  }

  private boolean isNeedRetry()
  {
    int i = 1;
    if ((this.pInfo.state == i) || (this.pInfo.state == 0))
    {
      this.mplayerRetryLogic.print("请求返回未授权或者授权,不需要重试");
      i = 0;
    }
    while ((TextUtils.isEmpty(this.pInfo.playUrl)) || (!"null".equalsIgnoreCase(this.pInfo.playUrl)))
      return i;
    return i;
  }

  private boolean isRetryRequest()
  {
    boolean bool2;
    if (this.mplayerRetryLogic.state == 256)
    {
      boolean bool3 = this.mplayerRetryLogic.isCanRequest();
      bool2 = false;
      if (bool3)
      {
        this.mplayerRetryLogic.print("请求返回失败重试");
        bool2 = true;
      }
    }
    while (true)
    {
      if (bool2)
        updateChangeCodeRateCategoryWhenRetry();
      return bool2;
      boolean bool1 = this.mplayerRetryLogic.isCanRetry();
      bool2 = false;
      if (bool1)
      {
        this.mplayerRetryLogic.print("请求返回失败重试");
        bool2 = true;
      }
    }
  }

  private void loadPlayBillData(String paramString, int paramInt)
  {
    this.currentSelectedVideoId = paramString;
    if (paramInt == 0)
      this.playbillView.setPlayIntentParams(this.pParams);
    ServerApiManager.i().APIGetPlaybill(this.currentSelectedVideoId, 6, 0, Long.valueOf(60000L), new SccmsApiGetPlaybillTaskListener(paramString));
  }

  private void onChangePlayUrlEvent()
  {
    Logger.d("MplayerPlaybackView", "onChangePlayUrlEvent!");
    this.pUrl = "";
    dragCount = 0;
    heartbeatCount = 0;
    this.dialogMsg = "";
    ReportState.clearBufferCount();
    this.isReportGflow = false;
    this.isReportLoad = false;
    this.isReportStop = false;
    this.isReportPlay = false;
    MplayerV2.suuid = UUID.randomUUID().toString();
    reportPlayerState(0);
    reportPlayQuality(9);
    this.isUseP2p = GeneralUtils.isCanUseP2P();
    boolean bool = this.isUseP2p;
    int i = 0;
    if (bool)
      i = 1;
    ReportState.isp2p = i;
    Logger.d("MplayerPlaybackView", "onChangePlayUrlEvent!ReportState.isp2p=" + ReportState.isp2p);
    resetAccessCMSFinalInvokeFlag();
    this.changeCodeRateCategory = CDNReportHelper.ChangeCodeRateCategory.FIRST_LOAD;
  }

  private void playNextPlayBackProgram()
  {
    Logger.i("MplayerPlaybackView", "MplayerPlaybackView playNextPlayBackProgram()");
    this.isStartToCheckBuffering = false;
    this.posNoMovingTimes = 0;
    this.isEventStopPlayer = true;
    this.isPlayerComplete = true;
    onEventStopPlayer();
    this.mpCore.stop();
    finishPlayerAndQuit();
  }

  private void processGetStreamInfo(Message paramMessage)
  {
    if ((0x2 & this.displayViews) == 0)
      return;
    GetStreamInfoResult localGetStreamInfoResult = (GetStreamInfoResult)paramMessage.obj;
    if ((localGetStreamInfoResult == null) || (localGetStreamInfoResult.httpcode != 200) || (localGetStreamInfoResult.result.ret != 0) || (localGetStreamInfoResult.stream == null) || (localGetStreamInfoResult.stream.index != this.ottStreamIndex) || (localGetStreamInfoResult.stream.is_proxy_tunnel != 0))
    {
      Logger.i("MplayerPlaybackView", "processGetStreamInfo()");
      return;
    }
    if (localGetStreamInfoResult.stream.flow_r_kbps > 0L)
      this.ottStreamHasSpeed = true;
    if ((DeviceInfo.isFactoryCH()) && (this.ottStreamHasSpeed) && (localGetStreamInfoResult.stream.flow_r_kbps <= 8L))
      localGetStreamInfoResult.stream.flow_r_kbps = 8L;
    if (this.timerCount % 10 == 0)
      Logger.i("MplayerPlaybackView", "processGetStreamInfo speed:" + localGetStreamInfoResult.stream.flow_r_kbps / 8L + "KB/S");
    String str = localGetStreamInfoResult.stream.flow_r_kbps / 8L + "KB/S";
    this.lsnr.onSpeedTextDisplay(0, str);
  }

  private boolean processTokenStatus(int paramInt, String paramString)
  {
    if (TextUtils.isEmpty(paramString));
    do
    {
      do
        return false;
      while (!GlobalLogic.getInstance().isUserLogined());
      if (paramString.equals("kicked"))
      {
        showTokenDialog(20001, true);
        return true;
      }
    }
    while ((!paramString.equals("expired")) || (paramInt == 0));
    showTokenDialog(20000, true);
    return true;
  }

  private void refreshCurrentTime()
  {
    this.title.setCurrentTime();
    if (((0x8 & this.displayViews) > 0) && ((0x4 & this.displayViews) > 0))
    {
      this.displayViews = (0xFFFFFFFB & this.displayViews);
      this.seekbar.hide();
      this.title.hide();
    }
  }

  private void reportBuy()
  {
    if ((this.context instanceof DialogActivity))
    {
      DialogActivity localDialogActivity = (DialogActivity)this.context;
      TvPayInfo localTvPayInfo = new TvPayInfo();
      localTvPayInfo.vid = this.pParams.nns_videoInfo.videoId;
      localTvPayInfo.cid = this.pParams.nns_videoInfo.packageId;
      localTvPayInfo.lcid = this.import_id;
      localTvPayInfo.def = this.pParams.mediaQuality;
      PayReportHelper.reportBuy(localDialogActivity.curPageInfo.page, localDialogActivity.lastPageInfo.page, null, localTvPayInfo);
    }
  }

  private void reportCDNIF1AccessCMS(boolean paramBoolean, String paramString1, String paramString2)
  {
    if (paramBoolean)
      setAccessCMSFinalInvoke();
    CDNReportHelper.reportIF1("IF1访问CMS上报", paramBoolean, this.accessCMSFinalInvoke, CDNReportHelper.PlayStep.ACCESS_CMS, paramString1, getChangeCodeRateCategory(), getQuality(), CDNReportHelper.PlayType.VOD, paramString2);
  }

  private void reportCDNIF1AccessCMSFail(String paramString1, String paramString2)
  {
    reportCDNIF1AccessCMS(false, paramString1, paramString2);
  }

  private void reportCDNIF1AccessCMSSuccess(String paramString)
  {
    reportCDNIF1AccessCMS(true, paramString, null);
  }

  private void reportCDNIF1AccessCache(boolean paramBoolean, String paramString1, String paramString2)
  {
    CDNReportHelper.reportIF1("IF1访问Cache上报", paramBoolean, this.accessCMSFinalInvoke, CDNReportHelper.PlayStep.ACCESS_CACHE, paramString1, getChangeCodeRateCategory(), getQuality(), CDNReportHelper.PlayType.VOD, paramString2);
  }

  private void reportCDNIF1AccessCacheFail(String paramString)
  {
    reportCDNIF1AccessCache(false, paramString, "302000");
  }

  private void reportCDNIF1AccessCacheSuccess(String paramString)
  {
    this.cdnAccessFirstFrame = true;
    reportCDNIF1AccessCache(true, paramString, null);
  }

  private void reportCDNIF2Buffer(int paramInt, long paramLong)
  {
    CDNReportHelper.reportIF2("IF2卡顿时上报", paramInt, CDNReportIF2Builder.ReportType.BUFFER_REPORT, this.playUrl, CDNReportHelper.PlayType.VOD, paramLong, getQuality(), null);
  }

  private void reportCDNIF2Complete(int paramInt, long paramLong)
  {
    if (this.cdnAccessFirstFrame)
    {
      CDNReportHelper.reportIF2("IF2完成时上报", paramInt, CDNReportIF2Builder.ReportType.COMPLETE_REPORT, this.playUrl, CDNReportHelper.PlayType.VOD, paramLong, getQuality(), null);
      this.cdnAccessFirstFrame = false;
    }
  }

  private void reportCDNIF2Error(String paramString, long paramLong)
  {
    CDNReportHelper.reportIF2("IF2错误即时上报", 1, CDNReportIF2Builder.ReportType.ERROR_REPORT, this.playUrl, CDNReportHelper.PlayType.VOD, paramLong, getQuality(), paramString);
  }

  private void reportCDNIF2Timer(int paramInt)
  {
    Logger.d("MplayerPlaybackView", "reportCDNIF2Timer bufferCountPeriod: " + paramInt + ", playUrl: " + this.playUrl);
    CDNReportHelper.reportIF2("IF2定时上报", paramInt, CDNReportIF2Builder.ReportType.TIMER_REPORT, this.playUrl, CDNReportHelper.PlayType.VOD, -1L, getQuality(), null);
  }

  private void reportError(String paramString1, String paramString2)
  {
    reportError(paramString1, paramString2, null, null);
  }

  private void reportError(String paramString1, String paramString2, ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
  {
    ApplicationException localApplicationException = new ApplicationException(this.context, paramString1);
    String str1 = "";
    StringBuilder localStringBuilder = new StringBuilder();
    if (!TextUtils.isEmpty(paramString2))
      localStringBuilder.append(paramString2);
    if (paramServerApiTaskInfo != null)
    {
      String str2 = paramServerApiTaskInfo.getHttpRequestUrl();
      if (!TextUtils.isEmpty(str2))
      {
        localStringBuilder.append(", request url: ");
        localStringBuilder.append(str2);
      }
    }
    if (paramServerApiCommonError != null)
    {
      str1 = paramServerApiCommonError.getHttpCode() + "";
      localStringBuilder.append(", error: ");
      localStringBuilder.append(paramServerApiCommonError.toString());
    }
    localApplicationException.setErrorColumn(getVideoInfoForErrReport(paramString1, localStringBuilder.toString(), str1));
    localApplicationException.setCurPageName(getCurPageName());
    localApplicationException.setShowDialog(false);
    localApplicationException.start();
  }

  private void reportInteractiveMsgClick()
  {
    if (((this.context instanceof DialogActivity)) && (this.curInteractiveMsg != null))
      ((DialogActivity)this.context).reportClick(6, null, this.curInteractiveMsg.toJSONString());
  }

  private void reportPlayQuality(int paramInt)
  {
    Logger.d("MplayerPlaybackView", "reportPlayQuality: " + ReportState.getReportStateDsc(paramInt));
    ReportMessage localReportMessage = new ReportMessage();
    ReportJSONObject localReportJSONObject = new ReportJSONObject();
    long l = (System.currentTimeMillis() - this.p2pStarTime) / 1000L;
    try
    {
      localReportJSONObject.put("act", ReportState.getReportStateDsc(paramInt));
      localReportJSONObject.put("bid", "3.1.6");
      localReportJSONObject.put("idx", "");
      localReportJSONObject.put("pt", getReportPt());
      localReportJSONObject.put("tpt", 0);
      localReportJSONObject.put("lcid", this.import_id);
      localReportJSONObject.put("sourceid", this.tv_channel);
      localReportJSONObject.put("streamid", getChannelIdFromUrl(this.pUrl));
      localReportJSONObject.put("liveid", this.cameraposition);
      localReportJSONObject.put("ln", getChannelNameForDataCollect());
      localReportJSONObject.put("td", "");
      localReportJSONObject.put("purl", URLEncoder.encode(this.pUrl, "UTF-8"));
      localReportJSONObject.put("pagename", getCurPageName());
      switch (paramInt)
      {
      case 3:
      case 4:
      case 5:
      case 6:
      case 8:
      default:
      case 2:
        while (true)
        {
          localReportJSONObject.put("suuid", MplayerV2.suuid);
          localReportJSONObject.put("vid", this.currentSelectedVideoId);
          localReportJSONObject.put("ovid", "");
          localReportJSONObject.put("sovid", "");
          localReportJSONObject.put("plid", this.pParams.nns_videoInfo.videoId);
          localReportJSONObject.put("oplid", this.pParams.nns_videoInfo.import_id);
          localReportJSONObject.put("soplid", this.pParams.nns_videoInfo.serial_id);
          localReportMessage.mExtData = new Column().buildJsonData(localReportJSONObject);
          localReportMessage.setDesc("播放器(" + ReportState.getReportStateDsc(paramInt) + ")事件上报");
          MessageHandler.instance().doNotify(localReportMessage);
          do
            return;
          while (this.isReportLoad);
          localReportJSONObject.put("td", (System.nanoTime() - ReportState.getStateStartTime(paramInt).longValue() - 499L) / 1000000000L);
          localReportJSONObject.put("ext1", ReportState.getP2PReportData(2, l));
          this.isReportLoad = true;
        }
      case 7:
      case 10:
      case 9:
      }
    }
    catch (Exception localException)
    {
      while (true)
      {
        Logger.w("ReportService", "json exception!", localException);
        continue;
        localReportJSONObject.put("idx", ReportState.getBufferCount());
        localReportJSONObject.put("td", (System.nanoTime() - ReportState.getStateStartTime(paramInt).longValue()) / 1000000000L);
        localReportJSONObject.put("bftype", ReportState.getBufferCause());
        localReportJSONObject.put("ext1", ReportState.getP2PReportData(7, l));
        continue;
        if (!this.isReportGflow)
        {
          localReportJSONObject.put("ext1", ReportState.getP2PReportData(10, l));
          this.isReportGflow = true;
          continue;
          localReportJSONObject.put("purl", "");
          localReportJSONObject.put("ext1", ReportState.getP2PReportData(9, l));
        }
      }
    }
  }

  private void reportPlayerQuitButtonClick(String paramString)
  {
    if ((this.context instanceof DialogActivity))
      ((DialogActivity)this.context).reportClick(4, paramString, "0");
  }

  private void reportPlayerState(int paramInt)
  {
    Logger.d("MplayerPlaybackView", "reportPlayerState: " + ReportState.getReportStateDsc(paramInt));
    ReportMessage localReportMessage = new ReportMessage();
    ReportJSONObject localReportJSONObject = new ReportJSONObject();
    long l = (System.currentTimeMillis() - this.p2pStarTime) / 1000L;
    try
    {
      updatePlayStateJSON(localReportJSONObject, paramInt);
      switch (paramInt)
      {
      case 1:
      case 2:
      case 7:
      default:
      case 0:
        while (true)
        {
          localReportMessage.mExtData = new Column().buildJsonData(localReportJSONObject);
          localReportMessage.setDesc("播放器(" + ReportState.getReportStateDsc(paramInt) + ")事件上报");
          MessageHandler.instance().doNotify(localReportMessage);
          return;
          localReportJSONObject.put("idx", "");
          localReportJSONObject.put("ct", this.currentPlayPos / 1000);
          localReportJSONObject.put("td", "");
          localReportJSONObject.put("purl", "");
          localReportJSONObject.put("ext1", ReportState.getP2PReportData(0, l));
          localReportJSONObject.put("ext2", "");
        }
      case 3:
      case 4:
      case 5:
      case 6:
      case 8:
      }
    }
    catch (Exception localException)
    {
      do
        while (true)
        {
          Logger.w("ReportService", "json exception!", localException);
          continue;
          if (!this.isReportPlay)
          {
            startMplayerHeartbeat();
            localReportJSONObject.put("idx", "");
            localReportJSONObject.put("ct", 0);
            localReportJSONObject.put("td", "");
            localReportJSONObject.put("ext1", ReportState.getP2PReportData(3, l));
            localReportJSONObject.put("ext2", this.activityId);
            this.isReportPlay = true;
            continue;
            localReportJSONObject.put("ct", this.currentPlayPos / 1000);
            localReportJSONObject.put("idx", "");
            localReportJSONObject.put("td", "");
            localReportJSONObject.put("ext1", ReportState.getP2PReportData(4, l));
            localReportJSONObject.put("ext2", this.activityId);
            continue;
            localReportJSONObject.put("ct", this.currentPlayPos / 1000);
            localReportJSONObject.put("idx", "");
            localReportJSONObject.put("td", "");
            localReportJSONObject.put("ext1", ReportState.getP2PReportData(5, l));
            localReportJSONObject.put("ext2", this.activityId);
            continue;
            int i = 1 + dragCount;
            dragCount = i;
            localReportJSONObject.put("idx", i);
            localReportJSONObject.put("ct", this.seekStartPos / 1000);
            localReportJSONObject.put("et", this.currentPlayPos / 1000);
            localReportJSONObject.put("td", (System.nanoTime() - ReportState.getStateStartTime(paramInt).longValue()) / 1000000000L);
            localReportJSONObject.put("ext1", ReportState.getP2PReportData(6, l));
            localReportJSONObject.put("ext2", this.activityId);
          }
        }
      while (ReportState.getStateStartTime(3) == null);
      localReportJSONObject.put("ct", this.currentPlayPos / 1000);
      localReportJSONObject.put("idx", "");
      localReportJSONObject.put("td", (System.nanoTime() - ReportState.getStateStartTime(3).longValue()) / 1000000000L);
      localReportJSONObject.put("ext1", ReportState.getP2PReportData(8, l));
      localReportJSONObject.put("ext2", this.activityId);
      if (TextUtils.isEmpty(this.playUrl));
    }
    for (String str1 = this.mplayerRetryLogic.request_times + "-"; ; str1 = "0-")
    {
      if (this.isPlayerSucceed);
      String str2;
      for (Object localObject = str1 + this.mplayerRetryLogic.play_times; ; localObject = str2)
      {
        if (!TextUtils.isEmpty(this.dialogMsg))
          localObject = (String)localObject + "|" + this.dialogMsg;
        localReportJSONObject.put("playsrc", localObject);
        Logger.i("MplayerPlaybackView", "reportPlayerState: playsrc=" + (String)localObject);
        break;
        str2 = str1 + "0";
      }
    }
  }

  private void requestChannelList()
  {
    this.mdStartTime = Tools.time2sec(Tools.getTimeString());
    ServerApiManager.i().APIGetChannelListV2(this.pParams.nns_videoInfo.packageId, this.pParams.nns_videoInfo.categoryId, new SccmsApiGetChannelListV2TaskListener());
  }

  private void resetAccessCMSFinalInvokeFlag()
  {
    this.accessCMSFinalInvoke = false;
  }

  private boolean retryRequest()
  {
    boolean bool = isRetryRequest();
    if (bool)
    {
      this.mplayerRetryLogic.addPlayRequestCount();
      addPlayRequestTask();
    }
    return bool;
  }

  private void setAccessCMSFinalInvoke()
  {
    this.accessCMSFinalInvoke = true;
  }

  private void setInteractiveViewVisible(boolean paramBoolean)
  {
    MplayerInteractiveView localMplayerInteractiveView;
    int i;
    if (this.interactiveView != null)
    {
      this.liveMsgShowCount = 0;
      if (isInteractiveMsgShowing() != paramBoolean)
      {
        localMplayerInteractiveView = this.interactiveView;
        i = 0;
        if (!paramBoolean)
          break label48;
      }
    }
    while (true)
    {
      localMplayerInteractiveView.setVisibility(i);
      if (paramBoolean)
        this.interactiveView.requestLayout();
      return;
      label48: i = 4;
    }
  }

  private void setInteractiveWebViewVisible(boolean paramBoolean)
  {
    MplayerInteractiveWebView localMplayerInteractiveWebView;
    if (this.interactiveWebView != null)
    {
      if (!paramBoolean)
        break label44;
      this.interactiveWebView.setFocusable(true);
      this.interactiveWebView.requestFocus();
      localMplayerInteractiveWebView = this.interactiveWebView;
      if (!paramBoolean)
        break label54;
    }
    label44: label54: for (int i = 0; ; i = 4)
    {
      localMplayerInteractiveWebView.setVisibility(i);
      return;
      this.interactiveWebView.clearFocus();
      break;
    }
  }

  private void setMenuViewVisibility(int paramInt)
  {
    if (this.menuView != null)
    {
      if (paramInt != 0)
        break label47;
      this.menuView.setFocusable(true);
      this.menuView.requestFocus();
    }
    for (this.displayViews = (0x40 | this.displayViews); ; this.displayViews = (0xFFFFFFBF & this.displayViews))
    {
      this.menuView.setVisibility(paramInt);
      return;
      label47: this.menuView.clearFocus();
    }
  }

  private void setTitleText()
  {
    Logger.i("MplayerPlaybackView", "setTitleText() pParams:" + this.pParams.toString());
    String str1 = "";
    if (this.pParams.nns_videoInfo.name != null)
      str1 = str1 + this.pParams.nns_videoInfo.name;
    String str2 = this.pParams.subfix_title;
    String str3;
    if ("HD".equals(this.pParams.mediaQuality))
    {
      str3 = "高清";
      this.isNeedToNotifyNetwork = true;
    }
    while (true)
    {
      this.title.setVideoDiscribForVod(str1, str2, str3);
      return;
      if ("STD".equals(this.pParams.mediaQuality))
        str3 = "标清";
      else if ("SD".equals(this.pParams.mediaQuality))
        str3 = "超清";
      else if ("LOW".equals(this.pParams.mediaQuality))
        str3 = "";
      else
        str3 = "";
    }
  }

  private void showCouponDialog()
  {
    final MovieCouponDialog localMovieCouponDialog = new MovieCouponDialog(this.context, "观看该节目需要使用1张观影券，兑换成功后您可以免费观看。", "影片兑换成功！你可以免费观看该节目了。", "确定兑换", "立即观看");
    localMovieCouponDialog.setMovieCouponTipDialogListener(new MovieCouponDialog.MovieCouponTipDialogListener()
    {
      public void onCancel()
      {
        localMovieCouponDialog.dissmissDialog();
      }

      public void onOkButtonClick()
      {
        localMovieCouponDialog.startUserCoupon();
        MplayerPlaybackView.access$1502(MplayerPlaybackView.this, "影片兑换成功！你可以免费观看该节目了。");
      }
    });
    localMovieCouponDialog.setMovieCouponOkDialogListener(new MovieCouponDialog.MovieCouponOkDialogListener()
    {
      public void onCancel()
      {
        MplayerPlaybackView.this.onChangePlayUrlEvent();
        MplayerPlaybackView.this.addPlayRequestTask();
      }

      public void onOkButtonClick()
      {
        MplayerPlaybackView.this.onChangePlayUrlEvent();
        MplayerPlaybackView.this.addPlayRequestTask();
      }
    });
    localMovieCouponDialog.setCouponType("1");
    localMovieCouponDialog.showCouponTipDialog(this.tv_channel);
    this.dialogMsg = "观看该节目需要使用1张观影券，兑换成功后您可以免费观看。";
  }

  private void showHistoryInteractiveMsgesWeb()
  {
    if (this.interactiveWebView != null)
    {
      List localList = InteractiveManager.getInstance().getInteractiveMsgListRcvd();
      this.interactiveWebView.loadHistoryInteractiveMsges(this.interactiveHistoryWebUrl, localList);
      setInteractiveWebViewVisible(true);
    }
  }

  private boolean showInteractiveMsg(InteractiveMessage paramInteractiveMessage)
  {
    if (paramInteractiveMessage != null)
    {
      this.interactiveView.setInteractiveMsg(paramInteractiveMessage);
      setInteractiveViewVisible(true);
      this.interactiveView.bringToFront();
      return true;
    }
    return false;
  }

  private void showInteractiveMsgWeb()
  {
    if ((this.interactiveWebView != null) && (this.curInteractiveMsg != null))
    {
      reportInteractiveMsgClick();
      this.interactiveWebView.loadInteractiveMsg(this.curInteractiveMsg.webUrl);
      setInteractiveViewVisible(false);
      setInteractiveWebViewVisible(true);
    }
  }

  private void showQuitDialog()
  {
    Logger.i("MplayerPlaybackView", "showQuitDialog()");
    if ((0x800 & this.displayViews) > 0)
    {
      this.displayViews = (0xFFFFF7FF & this.displayViews);
      this.notice.setVisibility(4);
      if (this.isSeriesPlayNotifyDisplay)
        this.isSeriesPlayNotifyDisplay = false;
    }
    this.quitView.setVisibility(0);
    this.quitView.requestFocus();
    this.quitView.setDefaultFocus();
    this.quitView.invalidate();
  }

  private void showTokenDialog(int paramInt, boolean paramBoolean)
  {
    hideCenterViews();
    this.isStartToCheckBuffering = false;
    displayLoadingInfo(4);
    this.playStatusFlag = 2;
    this.tokenDialog.setType(paramInt);
    this.tokenDialog.setIsNeedQuit(paramBoolean);
    this.tokenDialog.show();
    this.dialogMsg = this.tokenDialog.getDialogMsg();
  }

  private void startCDNTimerReport()
  {
    if (this.cdnTimerRunnable == null)
      this.cdnTimerRunnable = new Runnable()
      {
        public void run()
        {
          MplayerPlaybackView.this.reportCDNIF2Timer(MplayerPlaybackView.this.bufferPeriodCount);
          MplayerPlaybackView.access$8402(MplayerPlaybackView.this, 0);
          if (MplayerPlaybackView.this.mHandler != null)
            MplayerPlaybackView.this.mHandler.postDelayed(MplayerPlaybackView.this.cdnTimerRunnable, 300000L);
        }
      };
    if ((this.mHandler != null) && (this.needCDNTimerReport))
    {
      Logger.d("ReportService", "startCDNTimerReport");
      this.mHandler.postDelayed(this.cdnTimerRunnable, 300000L);
      this.needCDNTimerReport = false;
    }
  }

  private void startMplayerHeartbeat()
  {
    if (this.runnable == null)
    {
      this.heartbeatColumn = new ReportJSONObject();
      this.runnable = new Runnable()
      {
        public void run()
        {
          ReportMessage localReportMessage = new ReportMessage();
          MplayerPlaybackView.this.updatePlayStateJSON(MplayerPlaybackView.this.heartbeatColumn, -1);
          try
          {
            long l = (System.currentTimeMillis() - MplayerPlaybackView.this.p2pStarTime) / 1000L;
            MplayerPlaybackView.this.heartbeatColumn.put("act", "heartbeat");
            MplayerPlaybackView.this.heartbeatColumn.put("ct", MplayerPlaybackView.this.currentPlayPos / 1000);
            MplayerPlaybackView.this.heartbeatColumn.put("idx", MplayerPlaybackView.access$8104());
            String str = ReportState.getP2PReportData(11, l);
            MplayerPlaybackView.this.heartbeatColumn.put("ext1", str);
            MplayerPlaybackView.this.heartbeatColumn.put("ext2", MplayerPlaybackView.this.activityId);
            localReportMessage.mExtData = new Column().buildJsonData(MplayerPlaybackView.this.heartbeatColumn);
            localReportMessage.setDesc("播放器心跳上报(间隔300s)");
            MessageHandler.instance().doNotify(localReportMessage);
            if (MplayerPlaybackView.this.mHandler != null)
              MplayerPlaybackView.this.mHandler.postDelayed(this, 300000L);
            return;
          }
          catch (JSONException localJSONException)
          {
            while (true)
              localJSONException.printStackTrace();
          }
        }
      };
    }
    if ((this.mHandler != null) && (this.needReportHeartbeat))
    {
      this.mHandler.post(this.runnable);
      this.needReportHeartbeat = false;
    }
  }

  private void stopCDNTimerReport()
  {
    if ((this.mHandler != null) && (this.cdnTimerRunnable != null))
    {
      Logger.d("ReportService", "stopCDNTimerReport");
      this.mHandler.removeCallbacks(this.cdnTimerRunnable);
    }
    this.needCDNTimerReport = true;
  }

  private void stopToPlay()
  {
    Logger.i("MplayerPlaybackView", "stopToPlay()");
    this.isStartToCheckBuffering = false;
    this.mpCore.stop();
    if (this.ottStreamIndex > 0)
    {
      Logger.i("MplayerPlaybackView", "关闭中间件流 ottStreamIndex:" + this.ottStreamIndex);
      GeneralUtils.stopStream(this.ottStreamIndex);
      this.ottStreamIndex = 0;
      this.ottStreamHasSpeed = false;
    }
    if (AppFuncCfg.FUNCTION_OTTTV_P2P)
    {
      Logger.d("MplayerPlaybackView", "关闭P2P流");
      GeneralUtils.closeStream();
    }
  }

  private void updateChangeCodeRateCategoryWhenRetry()
  {
    if (this.isNotifiedPlaySuccess)
    {
      this.changeCodeRateCategory = CDNReportHelper.ChangeCodeRateCategory.AUTO_RETRY;
      return;
    }
    this.changeCodeRateCategory = CDNReportHelper.ChangeCodeRateCategory.FIRST_LOAD;
  }

  private void updateInteractiveMenu()
  {
    Logger.i("MplayerPlaybackView", "updateInteractiveMenu...");
    int i = ServerApiManager.i().APIGetTerminalRealtimeParamsTask(new SccmsApiGetTerminalRealtimeParamsTask.IGetTerminalRealtimeParamsTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.e("MplayerPlaybackView", "APIGetTerminalRealtimeParamsTask onError: " + paramAnonymousServerApiCommonError.toString());
        if (!MplayerPlaybackView.this.interactiveMenuTaskControl.checkTaskValid(paramAnonymousServerApiTaskInfo.getTaskId(), "interactive"))
        {
          Logger.e("MplayerPlaybackView", "APIGetTerminalRealtimeParamsTask onSuccess,  invalid task!");
          return;
        }
        MplayerPlaybackView.this.addOrRemoveInteractiveMenu(false);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, TerminalRealtimeParamList paramAnonymousTerminalRealtimeParamList)
      {
        Logger.d("MplayerPlaybackView", "APIGetTerminalRealtimeParamsTask onSuccess: " + paramAnonymousTerminalRealtimeParamList.toString());
        if (!MplayerPlaybackView.this.interactiveMenuTaskControl.checkTaskValid(paramAnonymousServerApiTaskInfo.getTaskId(), "interactive"))
          Logger.e("MplayerPlaybackView", "APIGetTerminalRealtimeParamsTask onSuccess,  invalid task!");
        TerminalRealtimeParam localTerminalRealtimeParam;
        do
        {
          do
            return;
          while (paramAnonymousTerminalRealtimeParamList == null);
          localTerminalRealtimeParam = paramAnonymousTerminalRealtimeParamList.getInteractiveMenuParam();
        }
        while (localTerminalRealtimeParam == null);
        MplayerPlaybackView.access$4502(MplayerPlaybackView.this, localTerminalRealtimeParam.value);
        MplayerPlaybackView.this.addOrRemoveInteractiveMenu(true);
      }
    });
    this.interactiveMenuTaskControl.clear();
    this.interactiveMenuTaskControl.addTaskLabel(i, "APIGetTerminalRealtimeParamsTask", "interactive");
  }

  private void updatePlayStateJSON(ReportJSONObject paramReportJSONObject, int paramInt)
  {
    if (paramReportJSONObject != null)
      try
      {
        paramReportJSONObject.put("act", ReportState.getReportStateDsc(paramInt));
        paramReportJSONObject.put("bid", "3.1.1");
        paramReportJSONObject.put("suuid", MplayerV2.suuid);
        paramReportJSONObject.put("vid", "");
        paramReportJSONObject.put("ovid", "");
        paramReportJSONObject.put("plid", this.pParams.nns_videoInfo.videoId);
        paramReportJSONObject.put("oplid", this.pParams.nns_videoInfo.import_id);
        if (paramInt == 0)
          paramReportJSONObject.put("url", "null");
        while (true)
        {
          paramReportJSONObject.put("purl", URLEncoder.encode(this.pUrl, "UTF-8"));
          paramReportJSONObject.put("pay", this.pay);
          paramReportJSONObject.put("def", this.pParams.mediaQuality);
          paramReportJSONObject.put("istry", 0);
          paramReportJSONObject.put("pt", getReportPt());
          paramReportJSONObject.put("ref", this.pParams.out_play);
          paramReportJSONObject.put("tpt", 0);
          paramReportJSONObject.put("ap", 0);
          paramReportJSONObject.put("lcid", this.import_id);
          paramReportJSONObject.put("sourceid", this.tv_channel);
          paramReportJSONObject.put("streamid", getChannelIdFromUrl(this.pUrl));
          paramReportJSONObject.put("liveid", this.cameraposition);
          paramReportJSONObject.put("ln", getChannelNameForDataCollect());
          paramReportJSONObject.put("pagename", getCurPageName());
          paramReportJSONObject.put("playsrc", ReportInfo.getInstance().getEntranceSrc());
          return;
          paramReportJSONObject.put("url", URLEncoder.encode(ReportState.url, "UTF-8"));
        }
      }
      catch (Exception localException)
      {
        Logger.w("ReportService", "json exception!", localException);
      }
  }

  public int bindMediaPlayerCore(MediaPlayerCore paramMediaPlayerCore)
  {
    if (paramMediaPlayerCore == null)
    {
      Logger.i("MplayerPlaybackView", "bindMediaPlayerCore() mpCore is null");
      return -1;
    }
    this.mpCore = paramMediaPlayerCore;
    this.mpCore.setOnPreparedListener(this.mPreparedListener);
    this.mpCore.setOnInfoListener(this.mInfoListener);
    this.mpCore.setOnErrorListener(this.mErrorListener);
    this.mpCore.setOnCompletionListener(this.mCompletionListener);
    this.mpCore.setOnSeekCompleteListener(this.mSeekCompleteListener);
    this.seekbar.init(new MplayerSeekBarListener(), 100, 30, 500);
    return 0;
  }

  public int bindPlayInfo(PlayerIntentParams paramPlayerIntentParams)
  {
    if (paramPlayerIntentParams == null)
      return -1;
    Logger.i("MplayerPlaybackView", "bindPlayInfo() pParams:" + paramPlayerIntentParams.toString());
    if (this.mdc != null)
      this.mdc.onAddVideotaskToPrePare(this.context, paramPlayerIntentParams.nns_videoInfo.videoId, paramPlayerIntentParams.nns_videoInfo.name, "-1", paramPlayerIntentParams.nns_videoInfo.film_name, paramPlayerIntentParams.mode);
    this.pParams = paramPlayerIntentParams;
    this.quitView.setPlayerInfos(this.pParams.nns_videoInfo.packageId, this.pParams.nns_videoInfo.categoryId, paramPlayerIntentParams.nns_videoInfo.videoId, this.pParams.nns_videoInfo.videoType);
    return 0;
  }

  public boolean checkChannelSupportTSTV(String paramString)
  {
    Logger.i("MplayerPlaybackView", "checkChannelSupportTSTV() videoId:" + paramString);
    return this.playbillView != null;
  }

  public AirControlPlayState ctrlGetPlayState()
  {
    Logger.i("MplayerPlaybackView", "ctrlGetPlayState()");
    if (this.pParams == null)
    {
      Logger.i("MplayerPlaybackView", "ctrlGetPlayState pParams is null");
      return AirControlPlayState.NULL;
    }
    if ("not_play" == this.airPlayState)
      return AirControlPlayState.NULL;
    AirControlPlayState localAirControlPlayState = new AirControlPlayState();
    localAirControlPlayState.getState().setValue(this.airPlayState);
    localAirControlPlayState.getNow_pos().setValue(String.valueOf(this.currentPlayPos / 1000));
    localAirControlPlayState.getType().setValue("playback");
    localAirControlPlayState.getVideo_index_name().setValue(this.pParams.subfix_title);
    localAirControlPlayState.getProgram_name().setValue(this.pParams.nns_videoInfo.name);
    localAirControlPlayState.getTime_len().setValue(String.valueOf(this.duration / 1000));
    localAirControlPlayState.getBegin().setValue(this.pParams.nns_day + this.pParams.nns_beginTime);
    localAirControlPlayState.getVideo_id().setValue(this.pParams.nns_videoInfo.videoId);
    localAirControlPlayState.getVideo_type().setValue(String.valueOf(this.pParams.nns_videoInfo.videoType));
    localAirControlPlayState.getVideo_name().setValue(this.pParams.nns_videoInfo.name);
    Logger.i("MplayerPlaybackView", "ctrlGetPlayState getNow_pos:" + localAirControlPlayState.getNow_pos().getValue());
    return localAirControlPlayState;
  }

  public void ctrlPausePlay()
  {
    Logger.i("MplayerPlaybackView", "ctrlPausePlay()");
    if (this.mpCore.isPlaying())
    {
      doPauseVideo();
      if ((0x4 & this.displayViews) == 0)
      {
        this.displayViews = (0x4 | this.displayViews);
        this.seekbar.display();
        this.title.display();
      }
    }
  }

  public void ctrlSeekTo(long paramLong)
  {
    Logger.i("MplayerPlaybackView", "ctrlSeekTo() seekTime:" + paramLong);
    if (this.seekbar != null)
    {
      if (-1L == paramLong)
        paramLong = this.duration;
      int i = (int)(paramLong * this.seekbar.getMax() / this.duration);
      this.seekbar.seekTo(i, true);
      if ((0x4 & this.displayViews) == 0)
      {
        this.noOperationTimer = 0;
        this.displayViews = (0x4 | this.displayViews);
        this.seekbar.display();
        this.title.display();
      }
    }
  }

  public void ctrlStartPlay()
  {
    Logger.i("MplayerPlaybackView", "ctrlStartPlay()");
    if (!this.mpCore.isPlaying())
    {
      doStartVideo();
      if ((0x4 & this.displayViews) == 0)
      {
        this.displayViews = (0x4 | this.displayViews);
        this.seekbar.display();
        this.title.display();
      }
    }
  }

  public void ctrlStopPlay()
  {
    Logger.i("MplayerPlaybackView", "ctrlStopPlay()");
    doQuitPlay();
  }

  public int destroy()
  {
    Logger.i("MplayerPlaybackView", "destroy()");
    if (this.isDestroyed)
    {
      Logger.d("MplayerPlaybackView", "already destroyed!");
      return 0;
    }
    this.isDestroyed = true;
    hideAllViews();
    this.seekbar.unInit();
    onEventStopPlayer();
    stopToPlay();
    this.isEventStopPlayer = true;
    if (!this.isReportStop)
    {
      reportPlayerState(8);
      this.isReportStop = true;
    }
    stopCDNTimerReport();
    if (this.tokenDialog != null)
      this.tokenDialog.dismiss();
    this.mdc.release();
    if (this.mHandler != null)
    {
      this.mHandler.removeCallbacks(this.runnable);
      this.mHandler = null;
    }
    InteractiveManager.getInstance().unSubScribeChannel();
    ReportState.clearBufferCount();
    return 0;
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    requestLayout();
    Logger.i("MplayerPlaybackView", "onInputEvent() code:" + paramKeyEvent.getKeyCode() + ", event.getAction():" + paramKeyEvent.getAction());
    if ((this.interactiveWebView.getVisibility() == 0) && (paramKeyEvent.getKeyCode() != 82) && (paramKeyEvent.getKeyCode() != 4))
    {
      this.interactiveWebView.dispatchKeyEvent(paramKeyEvent);
      return true;
    }
    if (paramKeyEvent.getAction() == 0)
    {
      this.noOperationTimer = 0;
      Logger.i("MplayerPlaybackView", "onInputEvent() keyDown code:" + paramKeyEvent.getKeyCode() + ", displayViews:" + this.displayViews);
      switch (paramKeyEvent.getKeyCode())
      {
      default:
        Logger.i("MplayerPlaybackView", "onInputEvent() keyDown 事件未被处理");
        return false;
      case 4:
        Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEYCODE_BACK");
        if (this.interactiveWebView.getVisibility() == 0)
          this.interactiveWebView.dispatchKeyEvent(paramKeyEvent);
        break;
      case 19:
      case 20:
      case 21:
      case 22:
      case 23:
      case 66:
      case 85:
      }
      while (true)
      {
        Logger.i("MplayerPlaybackView", "onInputEvent() keyDown 事件被处理");
        return true;
        if (((0x40 & this.displayViews) > 0) && (this.menuView.dispatchKeyEvent(paramKeyEvent)))
        {
          Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEYCODE_BACK  DISPLAY_VIEW_MENU");
        }
        else
        {
          if (this.quitView != null)
            this.quitView.setNextEpisodeVisibility(8);
          this.noOperationTimerForQuitView = 0;
          return false;
          if ((0x100 & this.displayViews) > 0)
          {
            Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_UP DISPLAY_VIEW_QUIT");
            return this.quitView.dispatchKeyEvent(paramKeyEvent);
          }
          if (((0x4 & this.displayViews) != 0) || ((0x100 & this.displayViews) > 0))
          {
            Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEY_UP  DISPLAY_VIEW_QUIT");
          }
          else if ((0x40 & this.displayViews) > 0)
          {
            Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEY_UP  DISPLAY_VIEW_MENU");
            this.menuView.dispatchKeyEvent(paramKeyEvent);
          }
          else if ((0x8 & this.displayViews) > 0)
          {
            Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEY_UP  DISPLAY_VIEW_EPISODE");
            this.playbillView.dispatchKeyEvent(paramKeyEvent);
          }
          else if ((0x10 & this.displayViews) > 0)
          {
            Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEY_UP  DISPLAY_VIEW_ERROR_NOTICE");
          }
          else if ((0x20 & this.displayViews) > 0)
          {
            Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEY_UP  DISPLAY_VIEW_RESERVATION_DIALOG");
          }
          else if ((0x200 & this.displayViews) > 0)
          {
            Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEY_UP  DISPLAY_VIEW_ORDER_NOTICE");
          }
          else
          {
            Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEY_UP other");
            if (this.pParams.isSelectTv)
            {
              Logger.i("MplayerPlaybackView", "电视精选不显示节目单");
            }
            else
            {
              if (this.playStatusFlag != 2)
              {
                this.title.hide();
                this.seekbar.hide();
                this.displayViews = (0xFFFFFFFB & this.displayViews);
              }
              this.playbillView.setVisibility(0);
              this.displayViews = (0x8 | this.displayViews);
              continue;
              if ((0x100 & this.displayViews) > 0)
              {
                Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_UP  DISPLAY_VIEW_QUIT");
                return this.quitView.dispatchKeyEvent(paramKeyEvent);
              }
              if (((0x4 & this.displayViews) != 0) || ((0x100 & this.displayViews) > 0))
              {
                Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEY_DOWN  DISPLAY_VIEW_QUIT");
              }
              else if ((0x40 & this.displayViews) > 0)
              {
                Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEY_DOWN  DISPLAY_VIEW_MENU");
                this.menuView.dispatchKeyEvent(paramKeyEvent);
              }
              else if ((0x8 & this.displayViews) > 0)
              {
                Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEY_DOWN  DISPLAY_VIEW_EPISODE");
                this.playbillView.dispatchKeyEvent(paramKeyEvent);
              }
              else
              {
                Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEY_DOWN other");
                if (this.pParams.isSelectTv)
                {
                  Logger.i("MplayerPlaybackView", "电视精选不显示节目单");
                }
                else
                {
                  if (this.playStatusFlag != 2)
                  {
                    this.title.hide();
                    this.seekbar.hide();
                    this.displayViews = (0xFFFFFFFB & this.displayViews);
                  }
                  this.playbillView.setVisibility(0);
                  this.displayViews = (0x8 | this.displayViews);
                  continue;
                  if ((0x100 & this.displayViews) > 0)
                  {
                    Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_UP  DISPLAY_VIEW_QUIT");
                    return this.quitView.dispatchKeyEvent(paramKeyEvent);
                  }
                  if ((0x100 & this.displayViews) > 0)
                  {
                    Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEY_LEFT  DISPLAY_VIEW_QUIT");
                  }
                  else if ((0x40 & this.displayViews) > 0)
                  {
                    Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEY_LEFT  DISPLAY_VIEW_MENU");
                    this.menuView.dispatchKeyEvent(paramKeyEvent);
                  }
                  else if ((0x8 & this.displayViews) > 0)
                  {
                    Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEY_LEFT  DISPLAY_VIEW_EPISODE");
                    this.playbillView.dispatchKeyEvent(paramKeyEvent);
                  }
                  else if ((0x10 & this.displayViews) > 0)
                  {
                    Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEY_LEFT  DISPLAY_VIEW_ERROR_NOTICE");
                  }
                  else if ((0x20 & this.displayViews) > 0)
                  {
                    Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEY_LEFT  DISPLAY_VIEW_RESERVATION_DIALOG");
                  }
                  else
                  {
                    Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEY_LEFT  other");
                    if (!this.isPlayerComplete)
                    {
                      this.displayViews = (0x4 | this.displayViews);
                      this.seekbar.onInputEvent(paramKeyEvent);
                      this.title.onInputEvent(paramKeyEvent);
                      continue;
                      if ((0x100 & this.displayViews) > 0)
                      {
                        Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_UP  DISPLAY_VIEW_QUIT");
                        return this.quitView.dispatchKeyEvent(paramKeyEvent);
                      }
                      if ((0x40 & this.displayViews) > 0)
                      {
                        Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEY_RIGHT  DISPLAY_VIEW_MENU");
                        this.menuView.dispatchKeyEvent(paramKeyEvent);
                      }
                      else if ((0x8 & this.displayViews) > 0)
                      {
                        Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEY_RIGHT  DISPLAY_VIEW_EPISODE");
                        this.playbillView.dispatchKeyEvent(paramKeyEvent);
                      }
                      else if ((0x10 & this.displayViews) > 0)
                      {
                        Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEY_RIGHT  DISPLAY_VIEW_ERROR_NOTICE");
                      }
                      else if ((0x20 & this.displayViews) > 0)
                      {
                        Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEY_RIGHT  DISPLAY_VIEW_RESERVATION_DIALOG");
                      }
                      else
                      {
                        Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEY_RIGHT  other");
                        if (!this.isPlayerComplete)
                        {
                          this.displayViews = (0x4 | this.displayViews);
                          this.seekbar.onInputEvent(paramKeyEvent);
                          this.title.onInputEvent(paramKeyEvent);
                          continue;
                          if ((0x100 & this.displayViews) > 0)
                          {
                            Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_UP  DISPLAY_VIEW_QUIT");
                            return this.quitView.dispatchKeyEvent(paramKeyEvent);
                          }
                          if ((0x10 & this.displayViews) > 0)
                          {
                            Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEYCODE_ENTER  DISPLAY_VIEW_ERROR_NOTICE");
                          }
                          else if ((0x20 & this.displayViews) > 0)
                          {
                            Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEYCODE_ENTER  DISPLAY_VIEW_RESERVATION_DIALOG");
                          }
                          else if ((0x100 & this.displayViews) > 0)
                          {
                            Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEYCODE_ENTER  DISPLAY_VIEW_QUIT");
                          }
                          else if ((0x8 & this.displayViews) > 0)
                          {
                            Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEYCODE_ENTER  DISPLAY_VIEW_EPISODE");
                            this.playbillView.dispatchKeyEvent(paramKeyEvent);
                          }
                          else if ((0x40 & this.displayViews) > 0)
                          {
                            Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEYCODE_ENTER  DISPLAY_VIEW_MENU");
                            this.menuView.dispatchKeyEvent(paramKeyEvent);
                          }
                          else
                          {
                            Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEYCODE_ENTER  other");
                            if (!this.isPlayerComplete)
                            {
                              this.displayViews = (0x4 | this.displayViews);
                              this.seekbar.onInputEvent(paramKeyEvent);
                              this.title.onInputEvent(paramKeyEvent);
                              continue;
                              Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEYCODE_MEDIA_PLAY_PAUSE  other");
                              if (!this.isPlayerComplete)
                              {
                                this.displayViews = (0x4 | this.displayViews);
                                this.seekbar.onInputEvent(paramKeyEvent);
                                this.title.onInputEvent(paramKeyEvent);
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    if (paramKeyEvent.getAction() == 1)
    {
      Logger.i("MplayerPlaybackView", "onInputEvent() keyUp code:" + paramKeyEvent.getKeyCode() + ", displayViews:" + this.displayViews);
      switch (paramKeyEvent.getKeyCode())
      {
      default:
        Logger.i("MplayerPlaybackView", "onInputEvent() keyUp 事件未被处理");
        return false;
      case 4:
        if (((0x40 & this.displayViews) > 0) && (this.menuView.dispatchKeyEvent(paramKeyEvent)))
          Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEYCODE_BACK  DISPLAY_VIEW_CONTROLL_PANEL");
        break;
      case 19:
      case 20:
      case 21:
      case 22:
      case 23:
      case 66:
      case 7:
      case 82:
      case 85:
      }
      while (true)
      {
        Logger.i("MplayerPlaybackView", "onInputEvent() keyUp 事件被处理");
        return true;
        if ((0x10 & this.displayViews) > 0)
        {
          Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEYCODE_BACK  DISPLAY_VIEW_ERROR_NOTICE");
          this.displayViews = (0xFFFFFFEF & this.displayViews);
          doQuitPlay();
          this.lsnr.onErrorNoticeViewDisplay(4, "", "", true);
        }
        else if (this.interactiveWebView.getVisibility() == 0)
        {
          this.interactiveWebView.dispatchKeyEvent(paramKeyEvent);
        }
        else if ((0x20 & this.displayViews) > 0)
        {
          Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEYCODE_BACK  DISPLAY_VIEW_RESERVATION_DIALOG");
        }
        else if ((0x8 & this.displayViews) > 0)
        {
          Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEYCODE_BACK  DISPLAY_VIEW_EPISODE");
          this.playbillView.setVisibility(4);
          this.displayViews = (0xFFFFFFF7 & this.displayViews);
        }
        else if ((0x40 & this.displayViews) > 0)
        {
          Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEYCODE_BACK  DISPLAY_VIEW_CONTROLL_PANEL");
          setMenuViewVisibility(4);
          this.displayViews = (0xFFFFFFBF & this.displayViews);
        }
        else if ((0x4 & this.displayViews) > 0)
        {
          Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEYCODE_BACK  DISPLAY_VIEW_CONTROLL_PANEL");
          this.seekbar.onInputEvent(paramKeyEvent);
          this.title.onInputEvent(paramKeyEvent);
          this.displayViews = (0xFFFFFFFB & this.displayViews);
        }
        else
        {
          Logger.i("MplayerPlaybackView", "dispatchKeyEvent() keyUp KEYCODE_BACK  other");
          if ((0x100 & this.displayViews) > 0)
          {
            this.quitView.setVisibility(4);
            this.quitView.setDefaultFocus();
            this.displayViews = (0xFFFFFEFF & this.displayViews);
          }
          else
          {
            showQuitDialog();
            this.displayViews = (0x100 | this.displayViews);
            this.mMplayerQuitPageStarTime = System.currentTimeMillis();
            continue;
            if ((0x100 & this.displayViews) > 0)
            {
              Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_UP  DISPLAY_VIEW_QUIT");
              return this.quitView.dispatchKeyEvent(paramKeyEvent);
            }
            if ((0x40 & this.displayViews) > 0)
            {
              Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_UP  DISPLAY_VIEW_MENU");
              this.menuView.dispatchKeyEvent(paramKeyEvent);
            }
            else if ((0x100 & this.displayViews) > 0)
            {
              Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_UP  DISPLAY_VIEW_QUIT");
            }
            else if ((0x10 & this.displayViews) > 0)
            {
              Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_UP  DISPLAY_VIEW_ERROR_NOTICE");
            }
            else if ((0x20 & this.displayViews) > 0)
            {
              Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEY_UP  DISPLAY_VIEW_RESERVATION_DIALOG");
            }
            else if ((0x200 & this.displayViews) > 0)
            {
              Logger.i("MplayerPlaybackView", "onInputEvent() keyDown KEY_UP  DISPLAY_VIEW_ORDER_NOTICE");
            }
            else if ((0x8 & this.displayViews) > 0)
            {
              Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_UP  DISPLAY_VIEW_EPISODE");
              this.playbillView.dispatchKeyEvent(paramKeyEvent);
            }
            else
            {
              Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_UP  other");
              continue;
              if ((0x100 & this.displayViews) > 0)
              {
                Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_UP  DISPLAY_VIEW_QUIT");
                return this.quitView.dispatchKeyEvent(paramKeyEvent);
              }
              if ((0x40 & this.displayViews) > 0)
              {
                Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_DOWN  DISPLAY_VIEW_MENU");
                this.menuView.dispatchKeyEvent(paramKeyEvent);
              }
              else if ((0x8 & this.displayViews) > 0)
              {
                Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_DOWN  DISPLAY_VIEW_EPISODE");
                this.playbillView.dispatchKeyEvent(paramKeyEvent);
              }
              else if ((0x100 & this.displayViews) > 0)
              {
                Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_UP  DISPLAY_VIEW_QUIT");
              }
              else if ((0x10 & this.displayViews) > 0)
              {
                Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_DOWN  DISPLAY_VIEW_ERROR_NOTICE");
              }
              else if ((0x20 & this.displayViews) > 0)
              {
                Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_DOWN  DISPLAY_VIEW_RESERVATION_DIALOG");
              }
              else
              {
                Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_DOWN  other");
                continue;
                if ((0x100 & this.displayViews) > 0)
                {
                  Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_UP  DISPLAY_VIEW_QUIT");
                  return this.quitView.dispatchKeyEvent(paramKeyEvent);
                }
                if ((0x40 & this.displayViews) > 0)
                {
                  Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_LEFT  DISPLAY_VIEW_MENU");
                  this.menuView.dispatchKeyEvent(paramKeyEvent);
                }
                if ((0x8 & this.displayViews) > 0)
                {
                  Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_LEFT  DISPLAY_VIEW_EPISODE");
                  this.playbillView.dispatchKeyEvent(paramKeyEvent);
                }
                else if ((0x100 & this.displayViews) > 0)
                {
                  Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_LEFT  DISPLAY_VIEW_QUIT");
                }
                else if ((0x10 & this.displayViews) > 0)
                {
                  Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_LEFT  DISPLAY_VIEW_ERROR_NOTICE");
                  this.lsnr.onMplayerDialogViewDiaplay(paramKeyEvent);
                }
                else if ((0x20 & this.displayViews) > 0)
                {
                  Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_LEFT  DISPLAY_VIEW_RESERVATION_DIALOG");
                }
                else
                {
                  Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_LEFT  other");
                  if (!this.isPlayerComplete)
                  {
                    this.seekbar.onInputEvent(paramKeyEvent);
                    this.title.onInputEvent(paramKeyEvent);
                    continue;
                    if ((0x100 & this.displayViews) > 0)
                    {
                      Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_UP  DISPLAY_VIEW_QUIT");
                      return this.quitView.dispatchKeyEvent(paramKeyEvent);
                    }
                    if ((0x8 & this.displayViews) > 0)
                    {
                      Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_RIGHT  DISPLAY_VIEW_EPISODE");
                      this.playbillView.dispatchKeyEvent(paramKeyEvent);
                    }
                    else if ((0x100 & this.displayViews) > 0)
                    {
                      Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_RIGHT  DISPLAY_VIEW_QUIT");
                    }
                    else if ((0x10 & this.displayViews) > 0)
                    {
                      Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_RIGHT  DISPLAY_VIEW_ERROR_NOTICE");
                      this.lsnr.onMplayerDialogViewDiaplay(paramKeyEvent);
                    }
                    else if ((0x20 & this.displayViews) > 0)
                    {
                      Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_RIGHT  DISPLAY_VIEW_RESERVATION_DIALOG");
                    }
                    else
                    {
                      Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_RIGHT  other");
                      if (!this.isPlayerComplete)
                      {
                        this.seekbar.onInputEvent(paramKeyEvent);
                        this.title.onInputEvent(paramKeyEvent);
                        continue;
                        if ((0x100 & this.displayViews) > 0)
                        {
                          Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEY_UP  DISPLAY_VIEW_QUIT");
                          return this.quitView.dispatchKeyEvent(paramKeyEvent);
                        }
                        if ((0x40 & this.displayViews) > 0)
                        {
                          Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEYCODE_ENTER  DISPLAY_VIEW_MENU");
                          this.menuView.dispatchKeyEvent(paramKeyEvent);
                        }
                        else if ((0x10 & this.displayViews) > 0)
                        {
                          Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEYCODE_ENTER  DISPLAY_VIEW_ERROR_NOTICE");
                          this.lsnr.onMplayerDialogViewDiaplay(paramKeyEvent);
                          this.lsnr.onErrorNoticeViewDisplay(4, "", "", true);
                        }
                        else if ((0x20 & this.displayViews) > 0)
                        {
                          Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEYCODE_ENTER  DISPLAY_VIEW_RESERVATION_DIALOG");
                        }
                        else if ((0x8 & this.displayViews) > 0)
                        {
                          Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEYCODE_ENTER  DISPLAY_VIEW_EPISODE");
                          this.playbillView.dispatchKeyEvent(paramKeyEvent);
                        }
                        else
                        {
                          Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEYCODE_ENTER  other");
                          this.seekbar.onInputEvent(paramKeyEvent);
                          this.title.onInputEvent(paramKeyEvent);
                          continue;
                          if ((isInteractiveMsgShowing()) || (this.interactiveWebView.getVisibility() == 0))
                          {
                            if ((0x4 & this.displayViews) > 0)
                            {
                              this.title.hide();
                              this.seekbar.hide();
                              this.displayViews = (0xFFFFFFFB & this.displayViews);
                            }
                            if ((0x40 & this.displayViews) > 0)
                            {
                              setMenuViewVisibility(4);
                              this.displayViews = (0xFFFFFFBF & this.displayViews);
                            }
                            if ((0x100 & this.displayViews) > 0)
                            {
                              this.quitView.setVisibility(4);
                              this.displayViews = (0xFFFFFEFF & this.displayViews);
                            }
                            if ((0x8 & this.displayViews) > 0)
                            {
                              Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEYCODE_MENU DISPLAY_VIEW_EPISODE");
                              this.playbillView.setVisibility(4);
                              this.displayViews = (0xFFFFFFF7 & this.displayViews);
                            }
                            if (isInteractiveMsgShowing())
                              showInteractiveMsgWeb();
                          }
                          else if ((this.menuView != null) && (this.menuView.getItemFlag() != 0))
                          {
                            if ((0x4 & this.displayViews) > 0)
                            {
                              this.title.hide();
                              this.seekbar.hide();
                              this.displayViews = (0xFFFFFFFB & this.displayViews);
                            }
                            if ((0x40 & this.displayViews) > 0)
                            {
                              Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEYCODE_MENU DISPLAY_VIEW_MENU");
                              setMenuViewVisibility(4);
                              this.displayViews = (0xFFFFFFBF & this.displayViews);
                            }
                            else if ((0x100 & this.displayViews) > 0)
                            {
                              Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEYCODE_MENU DISPLAY_VIEW_QUIT");
                              this.quitView.setVisibility(4);
                              this.displayViews = (0xFFFFFEFF & this.displayViews);
                              setMenuViewVisibility(0);
                              this.displayViews = (0x40 | this.displayViews);
                            }
                            else if ((0x10 & this.displayViews) > 0)
                            {
                              Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEYCODE_MENU DISPLAY_VIEW_ERROR_NOTICE");
                            }
                            else if ((0x20 & this.displayViews) > 0)
                            {
                              Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEYCODE_MENU DISPLAY_VIEW_RESERVATION_DIALOG");
                            }
                            else if ((0x8 & this.displayViews) > 0)
                            {
                              Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEYCODE_MENU DISPLAY_VIEW_EPISODE");
                              this.playbillView.setVisibility(4);
                              this.displayViews = (0xFFFFFFF7 & this.displayViews);
                              setMenuViewVisibility(0);
                              this.displayViews = (0x40 | this.displayViews);
                            }
                            else
                            {
                              setMenuViewVisibility(0);
                              this.displayViews = (0x40 | this.displayViews);
                              continue;
                              Logger.i("MplayerPlaybackView", "onInputEvent() keyUp KEYCODE_MEDIA_PLAY_PAUSE  other");
                              this.seekbar.onInputEvent(paramKeyEvent);
                              this.title.onInputEvent(paramKeyEvent);
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    return false;
  }

  public void displayNoTSTVNotice()
  {
    this.isNeedToNotifyNoTSTV = true;
  }

  public void hideAllViews()
  {
    if ((0x10 & this.displayViews) > 0)
    {
      this.lsnr.onErrorNoticeViewDisplay(4, "", "", false);
      this.displayViews = (0xFFFFFFEF & this.displayViews);
    }
    if ((0x200 & this.displayViews) > 0)
    {
      this.lsnr.onOrderNoticeViewDisplay(4, false);
      this.displayViews = (0xFFFFFDFF & this.displayViews);
    }
    if ((0x2 & this.displayViews) > 0)
    {
      this.lsnr.onLoadingViewDisplay(4);
      this.lsnr.onSpeedTextDisplay(4, "");
      this.displayViews = (0xFFFFFFFD & this.displayViews);
    }
    if ((0x40 & this.displayViews) > 0)
    {
      setMenuViewVisibility(4);
      this.displayViews = (0xFFFFFFBF & this.displayViews);
    }
    if ((0x8 & this.displayViews) > 0)
    {
      this.playbillView.setVisibility(4);
      this.displayViews = (0xFFFFFFF7 & this.displayViews);
    }
    if ((0x400 & this.displayViews) > 0)
    {
      this.lsnr.onWebDialogDisplay(4, null);
      this.displayViews = (0xFFFFFBFF & this.displayViews);
    }
  }

  public void onConfirm(int paramInt)
  {
    if (paramInt == 0)
    {
      Logger.i("MplayerPlaybackView", "MplayerDialogViewListener.onConfirm()");
      this.displayViews = (0xFFFFFFEF & this.displayViews);
      onEventStopPlayer();
      this.mpCore.stop();
      this.isEventStopPlayer = true;
      finishPlayerAndQuit();
      return;
    }
    Intent localIntent = new Intent(this.context, UserFeedbackActivity.class);
    localIntent.putExtra("xulPageId", "UserFeedbackPage");
    localIntent.putExtra("xulData", "");
    localIntent.addFlags(8388608);
    this.context.startActivity(localIntent);
    ((Activity)this.context).finish();
  }

  public void onEventStopPlayer()
  {
    Logger.i("MplayerPlaybackView", "onEventStopPlayer() airPlayState: " + this.airPlayState);
    if (this.airPlayState != "not_play")
    {
      stopCDNTimerReport();
      reportCDNIF2Complete(this.bufferPeriodCount, this.lastPlayPos);
      reportPlayerState(8);
      this.isReportStop = true;
    }
    this.airPlayState = "not_play";
    this.mdc.onPlayerStop(this.currentPlayPos);
    CollectListItem localCollectListItem = new CollectListItem();
    localCollectListItem.video_id = this.pParams.nns_videoInfo.videoId;
    localCollectListItem.video_type = this.pParams.nns_videoInfo.videoType;
    localCollectListItem.video_name = this.pParams.nns_videoInfo.name;
    localCollectListItem.video_index = this.pParams.nns_index;
    localCollectListItem.videoIndexName = this.pParams.subfix_title;
    localCollectListItem.played_time = (this.lastPlayPos / 1000);
    localCollectListItem.duration = (this.duration / 1000);
    if ((this.isPlayerComplete) || ((10000 + this.lastPlayPos > this.duration) && (this.duration > 0)))
      localCollectListItem.played_time = 0;
    if (!this.isPlayerSucceed);
    while (this.lastPlayPos <= 0)
      return;
    UserCPLLogic.getInstance().addPlayRecordLocal(localCollectListItem);
    Logger.i("MplayerPlaybackView", "播放记录name=" + this.pParams.nns_videoInfo.name + "$$$$$" + this.pParams.toString());
    AddCollectV2Params localAddCollectV2Params = new AddCollectV2Params(2, this.pParams.nns_videoInfo.videoId, this.pParams.nns_videoInfo.name, this.pParams.nns_videoInfo.videoType, this.pParams.nns_index, localCollectListItem.played_time, this.pParams.nns_day, this.pParams.nns_beginTime, this.pParams.nns_timeLen, this.pParams.nns_videoInfo.packageId, this.pParams.nns_videoInfo.categoryId, this.pParams.nns_videoInfo.videoType);
    localAddCollectV2Params.getPackage_id().setValue(this.pParams.nns_videoInfo.packageId);
    localAddCollectV2Params.getCategory_id().setValue(this.pParams.nns_videoInfo.categoryId);
    localAddCollectV2Params.getVideo_name().setValue(this.pParams.nns_videoInfo.name);
    localAddCollectV2Params.getQuality().setValue(UserCPLLogic.getInstance().getMediaQuality(this.pParams.nns_videoInfo));
    new CollectAndPlayListLogic().addPlayList(null, localAddCollectV2Params, this.pParams.nns_videoInfo);
    this.lastPlayPos = 0;
  }

  public void playerTimerSlowTask(Message paramMessage)
  {
    if (this.mpCore == null)
    {
      Logger.i("MplayerPlaybackView", "playerTimerSlowTask() mpCore is null");
      return;
    }
    if (this.isPlayerComplete)
    {
      Logger.i("MplayerPlaybackView", "playerTimerSlowTask() isPlayerComplete is true");
      return;
    }
    this.timerCount = (1 + this.timerCount);
    this.currentPlayPos = this.mpCore.getCurrentPosition();
    checkInBufferState();
    checkDisplayNotice();
    checkNoOperation();
    refreshCurrentTime();
    checkInteractiveMsg();
  }

  boolean processUserStatus(PlayInfoV2 paramPlayInfoV2)
  {
    boolean bool;
    if ((paramPlayInfoV2.state == null) || (paramPlayInfoV2.videoKeyList == null))
      bool = false;
    ArrayList localArrayList;
    do
    {
      int i;
      do
      {
        return bool;
        localArrayList = paramPlayInfoV2.videoKeyList;
        i = paramPlayInfoV2.state.state;
        String str1 = "normal";
        if (localArrayList != null)
        {
          Iterator localIterator = localArrayList.iterator();
          while (localIterator.hasNext())
          {
            UserKey localUserKey = (UserKey)localIterator.next();
            if (localUserKey != null)
              if (localUserKey.key.equals("token_status"))
              {
                str1 = localUserKey.value;
              }
              else if ("statistic".equals(localUserKey.key))
              {
                String str2 = localUserKey.value;
                Logger.i("MplayerPlaybackView", "statistic=" + str2);
                GlobalEnv.getInstance();
                String str3 = (String)GlobalEnv.parseData(str2).get("isfree");
                if ((!TextUtils.isEmpty(str3)) && ("false".equals(str3)))
                  this.pay = 1;
              }
          }
        }
        bool = processTokenStatus(i, str1);
      }
      while (i != 1);
      GlobalLogic.getInstance().setProductList(localArrayList);
      PurchaseParam localPurchaseParam = new PurchaseParam(true, this.pParams.nns_videoInfo.videoId, "1");
      localPurchaseParam.setVideoName(this.pParams.nns_videoInfo.name);
      localPurchaseParam.setImport_id(this.tv_channel);
      localPurchaseParam.setCategoryId(this.pParams.nns_videoInfo.categoryId);
      localPurchaseParam.setPackageId(this.pParams.nns_videoInfo.packageId);
      localPurchaseParam.setMode(2);
      localPurchaseParam.setPlaybackParams(this.pParams.nns_day, this.pParams.nns_beginTime, this.pParams.nns_timeLen);
      localPurchaseParam.def = this.pParams.mediaQuality;
      GlobalLogic.getInstance().setPurchaseParam(localPurchaseParam);
      this.tokenDialog.setPurchaseInfo(localPurchaseParam);
    }
    while (bool);
    if (GlobalLogic.getInstance().isChannelCanUseCoupon(localArrayList))
      showCouponDialog();
    while (true)
    {
      return true;
      showTokenDialog(20003, false);
    }
  }

  public void setOnPlayerControllEventCallback(MplayerV2.OnPlayerControllEventCallback paramOnPlayerControllEventCallback)
  {
    this.onPlayerControllEventCallback = paramOnPlayerControllEventCallback;
  }

  public int startToPlay()
  {
    Logger.i("MplayerPlaybackView", "startToPlay()");
    displayLoadingInfo(0);
    this.startPlayTime = System.currentTimeMillis();
    Logger.i("MplayerPlaybackView", "displayLoadingInfo() end");
    this.seekbar.reset();
    Logger.i("MplayerPlaybackView", "startToPlay() end");
    this.isStartToCheckBuffering = true;
    requestChannelList();
    return 0;
  }

  public String timeToHHMMSSShow(int paramInt)
  {
    int i = paramInt / 1000;
    int j = i % 60;
    int k = i / 60 % 60;
    int m = i / 3600;
    this.mFormatBuilder.setLength(0);
    if (m > 0)
    {
      Formatter localFormatter2 = this.mFormatter;
      Object[] arrayOfObject2 = new Object[3];
      arrayOfObject2[0] = Integer.valueOf(m);
      arrayOfObject2[1] = Integer.valueOf(k);
      arrayOfObject2[2] = Integer.valueOf(j);
      return localFormatter2.format("%02d:%02d:%02d", arrayOfObject2).toString();
    }
    Formatter localFormatter1 = this.mFormatter;
    Object[] arrayOfObject1 = new Object[2];
    arrayOfObject1[0] = Integer.valueOf(k);
    arrayOfObject1[1] = Integer.valueOf(j);
    return localFormatter1.format("00:%02d:%02d", arrayOfObject1).toString();
  }

  class LocationObserver extends GeneralUtils.LocationObserve
  {
    public LocationObserver(Looper arg2)
    {
      super();
    }

    public void getResult(Message paramMessage)
    {
      if (MplayerPlaybackView.this.mpCore == null)
        return;
      if ((paramMessage.what == 200) && (paramMessage.obj != null))
      {
        String str = (String)paramMessage.obj;
        MplayerPlaybackView.access$6602(MplayerPlaybackView.this, paramMessage.arg1);
        MplayerPlaybackView.access$6702(MplayerPlaybackView.this, false);
        Logger.i("MplayerPlaybackView", "playTaskDoReturn 播放地址 转换成功 / 新地址  = " + str);
        MplayerPlaybackView.this.pInfo.playUrl = str;
      }
      while (true)
      {
        MplayerPlaybackView.this.reportPlayQuality(10);
        ReportState.setStateStartTime(2);
        MplayerPlaybackView.this.mpCore.setVideoURI(MplayerPlaybackView.this.pInfo.playUrl);
        return;
        Logger.e("MplayerPlaybackView", "playTaskDoReturn  播放地址 转换失败 !/ 继续使用原有播放pInfo.playUrl： " + MplayerPlaybackView.this.pInfo.playUrl);
      }
    }
  }

  class MplayerDialogViewListener
    implements MplayerDialogView.IMplayerDialogViewListener
  {
    MplayerDialogViewListener()
    {
    }

    public void onCancel()
    {
      Logger.i("MplayerPlaybackView", "MplayerDialogViewListener.onCancel()");
      MplayerPlaybackView.access$1472(MplayerPlaybackView.this, -17);
      MplayerPlaybackView.this.onEventStopPlayer();
      MplayerPlaybackView.this.mpCore.stop();
      MplayerPlaybackView.access$2402(MplayerPlaybackView.this, true);
      MplayerPlaybackView.this.finishPlayerAndQuit();
    }

    public void onConfirm(int paramInt)
    {
      if (paramInt == 0)
      {
        Logger.i("MplayerPlaybackView", "MplayerDialogViewListener.onConfirm()");
        MplayerPlaybackView.access$1472(MplayerPlaybackView.this, -17);
        MplayerPlaybackView.this.onEventStopPlayer();
        MplayerPlaybackView.this.mpCore.stop();
        MplayerPlaybackView.access$2402(MplayerPlaybackView.this, true);
        MplayerPlaybackView.this.finishPlayerAndQuit();
        return;
      }
      Intent localIntent = new Intent(MplayerPlaybackView.this.context, UserFeedbackActivity.class);
      localIntent.putExtra("xulPageId", "UserFeedbackPage");
      localIntent.putExtra("xulData", "");
      localIntent.addFlags(8388608);
      MplayerPlaybackView.this.context.startActivity(localIntent);
      ((Activity)MplayerPlaybackView.this.context).finish();
    }
  }

  class MplayerSeekBarListener
    implements MplayerSeekBar.IMplayerSeekBarListener
  {
    Runnable run = new Runnable()
    {
      public void run()
      {
        MplayerPlaybackView.this.playState.setVisibility(8);
      }
    };

    MplayerSeekBarListener()
    {
    }

    public String getCurrentProgramName(long paramLong)
    {
      return null;
    }

    public String getPosDiscribByPlayPos(long paramLong)
    {
      return MplayerPlaybackView.this.timeToHHMMSSShow((int)paramLong);
    }

    public void notifyCurrentState(int paramInt)
    {
      if (paramInt == -1)
      {
        MplayerPlaybackView.this.playState.setVisibility(8);
        return;
      }
      if (paramInt == -2)
      {
        MplayerPlaybackView.this.playState.setVisibility(0);
        return;
      }
      if (paramInt == 1)
      {
        MplayerPlaybackView.this.playState.setVisibility(8);
        return;
      }
      MplayerPlaybackView.this.displayLoadingInfo(4);
      MplayerPlaybackView.this.playState.setCurrentMode(paramInt);
    }

    public void onPlayToPreNode()
    {
    }

    public void onUserPauseOrStart()
    {
      MplayerPlaybackView.this.doPauseOrStartVideo();
    }

    public void onUserSeekEnd(long paramLong)
    {
      Logger.i("MplayerPlaybackView", "onUserSeekEnd seekPos: " + paramLong + ", duration: " + MplayerPlaybackView.this.duration);
      boolean bool = true;
      MplayerPlaybackView.this.mdc.onPlayerSeekEnd(paramLong);
      MplayerPlaybackView.this.mdc.onPlayerBufferBegin(BufferEnum.BUFFER_DRAG, paramLong);
      ReportState.setStateStartTime(7);
      ReportState.setBufferCause(3);
      if (paramLong <= 0L)
        paramLong = 0L;
      while (true)
      {
        MplayerPlaybackView.this.dealUserSeekEndOperator(bool, paramLong);
        return;
        if ((paramLong >= -10000 + MplayerPlaybackView.this.duration) && (paramLong < -1000 + MplayerPlaybackView.this.duration))
          paramLong = -10000 + MplayerPlaybackView.this.duration;
        else if (paramLong >= -1000 + MplayerPlaybackView.this.duration)
          bool = false;
      }
    }

    public void onUserSeekStart()
    {
      MplayerPlaybackView.this.mdc.onPlayerSeekBegin(MplayerPlaybackView.this.currentPlayPos);
      MplayerPlaybackView.access$7102(MplayerPlaybackView.this, MplayerPlaybackView.this.currentPlayPos);
      ReportState.setStateStartTime(6);
    }

    public int playProgress2uiProgress(long paramLong)
    {
      if (MplayerPlaybackView.this.duration == 0)
        return 0;
      return (int)(paramLong * MplayerPlaybackView.this.seekbar.getMax() / MplayerPlaybackView.this.duration);
    }

    public long uiProgress2PlayProgress(int paramInt)
    {
      return ()(paramInt * MplayerPlaybackView.this.minStepLen);
    }
  }

  private class NewMenuItemOnClickListener
    implements MplayerMenuView.OnItemClickListener
  {
    private NewMenuItemOnClickListener()
    {
    }

    public void onItemClick(String paramString1, String paramString2)
    {
      if ("screen_ratio".equalsIgnoreCase(paramString1))
        if ("16v9".equals(paramString2))
        {
          MplayerPlaybackView.this.mpCore.changeVideoLayoutTo16v9();
          GlobalLogic.getInstance().setVideoLayoutRatio(paramString2);
          MplayerPlaybackView.this.menuView.setItemState(4, paramString2);
        }
      while (!"interactive".equals(paramString1))
      {
        do
        {
          return;
          if ("4v3".equals(paramString2))
          {
            MplayerPlaybackView.this.mpCore.changeVideoLayoutTo4v3();
            GlobalLogic.getInstance().setVideoLayoutRatio(paramString2);
            MplayerPlaybackView.this.menuView.setItemState(4, paramString2);
            return;
          }
          if ("full".equals(paramString2))
          {
            MplayerPlaybackView.this.mpCore.changeVideoLayoutToFullScreen();
            GlobalLogic.getInstance().setVideoLayoutRatio(paramString2);
            MplayerPlaybackView.this.menuView.setItemState(4, paramString2);
            return;
          }
          if ("default".equals(paramString2))
          {
            MplayerPlaybackView.this.mpCore.changeVideoLayoutToDefault();
            GlobalLogic.getInstance().setVideoLayoutRatio(paramString2);
            MplayerPlaybackView.this.menuView.setItemState(4, paramString2);
            return;
          }
        }
        while (!"235".equals(paramString2));
        MplayerPlaybackView.this.mpCore.changeVideoLayoutTo2351();
        GlobalLogic.getInstance().setVideoLayoutRatio(paramString2);
        MplayerPlaybackView.this.menuView.setItemState(4, paramString2);
        return;
      }
      MplayerPlaybackView.this.setMenuViewVisibility(4);
      MplayerPlaybackView.this.showHistoryInteractiveMsgesWeb();
    }
  }

  class SccmsApiGetChannelListV2TaskListener
    implements SccmsApiGetChannelListV2Task.ISccmsApiGetChannelListV2TaskListener
  {
    SccmsApiGetChannelListV2TaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("MplayerPlaybackView", "SccmsApiGetChannelListV2TaskListener.onError() error:" + paramServerApiCommonError.toString());
      MplayerPlaybackView.this.playbillView.bindChannelList(null);
      MplayerPlaybackView.this.mplayerRetryLogic.initRetryParams();
      MplayerPlaybackView.this.onChangePlayUrlEvent();
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ChannelInfoV2 paramChannelInfoV2)
    {
      Logger.i("MplayerPlaybackView", "SccmsApiGetChannelListV2TaskListener.onSuccess() result:" + paramChannelInfoV2.toString());
      MplayerPlaybackView.access$4702(MplayerPlaybackView.this, paramChannelInfoV2);
      if (MplayerPlaybackView.this.channelListDataV2.channelList.size() <= 0)
      {
        MplayerPlaybackView.this.playbillView.bindChannelList(null);
        MplayerPlaybackView.this.mplayerRetryLogic.initRetryParams();
        MplayerPlaybackView.this.onChangePlayUrlEvent();
        return;
      }
      MplayerPlaybackView.this.playbillView.bindChannelList(MplayerPlaybackView.this.channelListDataV2);
      MplayerPlaybackView.this.loadPlayBillData(MplayerPlaybackView.this.pParams.nns_videoInfo.videoId, 0);
      MplayerPlaybackView.access$4802(MplayerPlaybackView.this, Tools.time2sec(Tools.getTimeString()));
      Logger.i("MplayerPlaybackView", "mdEndTime=" + MplayerPlaybackView.this.mdEndTime + ",mdStartTime=" + MplayerPlaybackView.this.mdStartTime);
    }
  }

  class SccmsApiGetPlaybillTaskListener
    implements SccmsApiGetPlaybillTask.ISccmsApiGetPlaybillTaskListener
  {
    private String videoId;

    public SccmsApiGetPlaybillTaskListener(String arg2)
    {
      Object localObject;
      this.videoId = localObject;
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("MplayerPlaybackView", "SccmsApiGetPlaybillTaskListener.onError() error:" + paramServerApiCommonError.toString());
      MplayerPlaybackView.this.playbillView.bindProgramList(this.videoId, null);
      MplayerPlaybackView.this.reportError("1002005", "SccmsApiGetPlaybillTaskListener.onError", paramServerApiTaskInfo, paramServerApiCommonError);
      MplayerPlaybackView.this.mplayerRetryLogic.initRetryParams();
      MplayerPlaybackView.this.onChangePlayUrlEvent();
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<PlayBillStruct> paramArrayList)
    {
      Logger.i("MplayerPlaybackView", "SccmsApiGetPlaybillTaskListener.onSuccess() result:" + paramArrayList.toString());
      MplayerPlaybackView.access$5102(MplayerPlaybackView.this, paramArrayList);
      if (MplayerPlaybackView.this.mPlayBillStruct.size() <= 0)
      {
        MplayerPlaybackView.this.playbillView.bindProgramList(this.videoId, null);
        MplayerPlaybackView.this.mplayerRetryLogic.initRetryParams();
        MplayerPlaybackView.this.onChangePlayUrlEvent();
        return;
      }
      MplayerPlaybackView.this.playbillView.bindProgramList(this.videoId, MplayerPlaybackView.this.mPlayBillStruct);
    }
  }

  class SccmsApiRequestVideoPlayV2TaskListener
    implements SccmsApiRequestVideoPlayV2Task.ISccmsApiRequestVideoPlayV2TaskListener
  {
    SccmsApiRequestVideoPlayV2TaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      if (!MplayerPlaybackView.this.apiTaskControl.checkTaskValid(paramServerApiTaskInfo.getTaskId(), MplayerPlaybackView.this.pParams.nns_videoInfo.videoId))
        Logger.i("MplayerPlaybackView", "SccmsApiRequestVideoPlayV2TaskListener.onError() invalid task");
      do
      {
        return;
        MplayerPlaybackView.access$5302(MplayerPlaybackView.this, paramServerApiTaskInfo.getHttpRequestUrl());
        if (MplayerPlaybackView.this.retryRequest())
        {
          MplayerPlaybackView.this.reportCDNIF1AccessCMSFail(paramServerApiTaskInfo.getHttpRequestUrl(), MplayerPlaybackView.access$5500(MplayerPlaybackView.this, paramServerApiCommonError));
          return;
        }
        MplayerPlaybackView.this.setAccessCMSFinalInvoke();
        MplayerPlaybackView.this.reportCDNIF1AccessCMSFail(paramServerApiTaskInfo.getHttpRequestUrl(), MplayerPlaybackView.access$5500(MplayerPlaybackView.this, paramServerApiCommonError));
        MplayerPlaybackView.this.mplayerRetryLogic.print("请求返回失败弹框");
        MplayerPlaybackView.this.mdc.onError("1002005");
      }
      while (!MplayerPlaybackView.this.checkErrorNoticeViewCanDisplay());
      MplayerPlaybackView.this.hideCenterViews();
      String str = "MplayerVODView.SccmsApiRequestVideoPlayV2TaskListener.onError error:" + paramServerApiCommonError.toString();
      MplayerPlaybackView.this.lsnr.onErrorNoticeViewDisplay(0, "1002005", str, true);
      MplayerPlaybackView.this.reportError("1002005", "ISccmsApiRequestVideoPlayV2TaskListener.onError", paramServerApiTaskInfo, paramServerApiCommonError);
      MplayerPlaybackView.access$1476(MplayerPlaybackView.this, 16);
      Logger.i("MplayerPlaybackView", "SccmsApiRequestVideoPlayV2TaskListener.onError()");
      MplayerPlaybackView.access$1502(MplayerPlaybackView.this, ApplicationException.getErrorDiscrib("1002005"));
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, PlayInfoV2 paramPlayInfoV2)
    {
      if (!MplayerPlaybackView.this.apiTaskControl.checkTaskValid(paramServerApiTaskInfo.getTaskId(), MplayerPlaybackView.this.pParams.nns_videoInfo.videoId))
      {
        Logger.i("MplayerPlaybackView", "SccmsApiRequestVideoPlayTaskListener.onSuccess() invalid task");
        return;
      }
      MplayerPlaybackView.access$5302(MplayerPlaybackView.this, paramServerApiTaskInfo.getHttpRequestUrl());
      Logger.i("MplayerPlaybackView", "SccmsApiRequestVideoPlayTaskListener.onSuccess() PlayInfo:" + paramPlayInfoV2.toString());
      MplayerPlaybackView.access$5802(MplayerPlaybackView.this, paramPlayInfoV2.playUrl);
      MplayerPlaybackView.access$5902(MplayerPlaybackView.this, 0);
      MplayerPlaybackView.this.mplayerRetryLogic.svrip = paramPlayInfoV2.svrip;
      MplayerPlaybackView.this.mplayerRetryLogic.print("请求返回成功: state = " + paramPlayInfoV2.state.state);
      if (MplayerPlaybackView.this.processUserStatus(paramPlayInfoV2))
      {
        MplayerPlaybackView.this.setAccessCMSFinalInvoke();
        MplayerPlaybackView.this.reportCDNIF1AccessCMSFail(MplayerPlaybackView.this.cdnAccessCMSApiUrl, "104000");
        MplayerPlaybackView.this.lsnr.onPreLoadingViewDisplay(4, "");
        return;
      }
      PlayInfo localPlayInfo = new PlayInfo();
      localPlayInfo.state = paramPlayInfoV2.state.state;
      localPlayInfo.playUrl = paramPlayInfoV2.playUrl;
      localPlayInfo.fileId = paramPlayInfoV2.fileId;
      localPlayInfo.fileType = paramPlayInfoV2.fileType;
      localPlayInfo.otherCdn = 0;
      localPlayInfo.reason = paramPlayInfoV2.state.reason;
      localPlayInfo.type = paramPlayInfoV2.quality;
      localPlayInfo.quality = paramPlayInfoV2.quality;
      localPlayInfo.begin_time = paramPlayInfoV2.begin_time;
      localPlayInfo.time_len = paramPlayInfoV2.time_len;
      localPlayInfo.dimensions = paramPlayInfoV2.dimensions;
      localPlayInfo.IsOtherCdn = paramPlayInfoV2.IsOtherCdn;
      localPlayInfo.mediaTimeNodeList = new ArrayList();
      localPlayInfo.mediaTimeNodeList.addAll(paramPlayInfoV2.mediaTimeNodeList);
      MplayerPlaybackView.access$6002(MplayerPlaybackView.this, localPlayInfo);
      if ((MplayerPlaybackView.this.isNeedRetry()) && (MplayerPlaybackView.this.retryRequest()))
      {
        MplayerPlaybackView.this.reportCDNIF1AccessCMSFail(MplayerPlaybackView.this.cdnAccessCMSApiUrl, "104000");
        return;
      }
      if (!MplayerPlaybackView.this.checkPlayInfoData())
      {
        MplayerPlaybackView.this.lsnr.onPreLoadingViewDisplay(4, "");
        MplayerPlaybackView.this.setAccessCMSFinalInvoke();
        MplayerPlaybackView.this.reportCDNIF1AccessCMSFail(MplayerPlaybackView.this.cdnAccessCMSApiUrl, "104000");
        return;
      }
      MplayerPlaybackView.this.reportCDNIF1AccessCMSSuccess(MplayerPlaybackView.this.cdnAccessCMSApiUrl);
      MplayerPlaybackView.access$6402(MplayerPlaybackView.this, true);
      MplayerPlaybackView.this.mpCore.stop();
      UserCPLLogic.getInstance().setLastPlayMgtvFileId(MplayerPlaybackView.this.pInfo.fileId);
      MplayerPlaybackView.access$1602(MplayerPlaybackView.this, MplayerPlaybackView.this.pInfo.playUrl);
      MplayerPlaybackView.this.mdc.onPlayerCreate(MplayerPlaybackView.this.playUrl);
      MplayerPlaybackView.this.mdc.onPlayerBufferBegin(BufferEnum.BUFFER_AUTH, MplayerPlaybackView.this.currentPlayPos);
      ReportState.setStateStartTime(7);
      ReportState.setBufferCause(1);
      String str = "";
      if ((MplayerPlaybackView.this.pInfo.IsOtherCdn == 0) && (MplayerPlaybackView.this.pParams.nns_videoInfo.videoType == 0))
        str = "mgtv";
      MplayerPlaybackView.this.getLocationUrl(str);
    }
  }

  public class TokenDialogListener
    implements TokenDialog.TokenDialogListener
  {
    public TokenDialogListener()
    {
    }

    public void onCancel(int paramInt)
    {
      Logger.i("MplayerPlaybackView", "doQuitPlay()");
      MplayerPlaybackView.this.onEventStopPlayer();
      MplayerPlaybackView.this.stopToPlay();
      MplayerPlaybackView.access$2402(MplayerPlaybackView.this, true);
      MplayerPlaybackView.this.finishPlayerAndQuit();
    }

    public void onPositiveClick(int paramInt)
    {
      if ((paramInt == 20003) || (paramInt == 2009))
        MplayerPlaybackView.this.reportBuy();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.media.player.MplayerPlaybackView
 * JD-Core Version:    0.6.2
 */