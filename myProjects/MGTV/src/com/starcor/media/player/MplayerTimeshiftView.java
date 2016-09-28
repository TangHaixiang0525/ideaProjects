package com.starcor.media.player;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.starcor.config.AppFuncCfg;
import com.starcor.config.DeviceInfo;
import com.starcor.core.domain.AirControlPlayState;
import com.starcor.core.domain.AuthState;
import com.starcor.core.domain.BarrageMeta;
import com.starcor.core.domain.BarrageResponse;
import com.starcor.core.domain.CategoryItem;
import com.starcor.core.domain.ChannelInfoV2;
import com.starcor.core.domain.ChannelItemInfoV2;
import com.starcor.core.domain.FilmListItem;
import com.starcor.core.domain.MediaAssetsInfo;
import com.starcor.core.domain.MovieData;
import com.starcor.core.domain.PlayBillItem;
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
import com.starcor.core.exception.ApplicationException;
import com.starcor.core.http.BitmapCache;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.logic.UserCPLLogic;
import com.starcor.core.logic.UserCPLLogic.TimeshiftPlayRecord;
import com.starcor.core.report.enums.BufferEnum;
import com.starcor.core.utils.AppKiller;
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
import com.starcor.hunan.service.ReservationService;
import com.starcor.hunan.service.SystemTimeManager;
import com.starcor.hunan.widget.BarrageView;
import com.starcor.hunan.widget.MovieCouponDialog;
import com.starcor.hunan.widget.MovieCouponDialog.MovieCouponOkDialogListener;
import com.starcor.hunan.widget.MovieCouponDialog.MovieCouponTipDialogListener;
import com.starcor.hunan.widget.TokenDialog;
import com.starcor.hunan.widget.TokenDialog.TokenDialogListener;
import com.starcor.message.MessageHandler;
import com.starcor.mgtv.api.MgtvApiGetBarrageDataTask.IGetBarrageDataTaskListener;
import com.starcor.mgtv.boss.WebUrlBuilder;
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
import com.starcor.sccms.api.SccmsApiGetMediaAssetsInfoTask.ISccmsApiGetMediaAssetsInfoTaskListener;
import com.starcor.sccms.api.SccmsApiGetPlaybillTask.ISccmsApiGetPlaybillTaskListener;
import com.starcor.sccms.api.SccmsApiGetTerminalRealtimeParamsTask.IGetTerminalRealtimeParamsTaskListener;
import com.starcor.sccms.api.SccmsApiRequestVideoPlayV2Task.ISccmsApiRequestVideoPlayV2TaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import java.net.URI;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

public class MplayerTimeshiftView extends RelativeLayout
{
  private static final int BUFFERING_LASTTIME_BELOW_WATERLINE = 3;
  private static final int BUFFERING_LASTTIME_UPON_WATERLINE = 120;
  private static final int CHANGE_CHANNEL_WATERLINE = 2;
  private static final int DEFAULT_GET_BARRAGE_INTERVAL = 5000;
  private static final int DISPLAY_VIEW_CONTROLL_PANEL = 4;
  private static final int DISPLAY_VIEW_EPISODE = 8;
  private static final int DISPLAY_VIEW_ERROR_NOTICE = 16;
  private static final int DISPLAY_VIEW_INTERACTIVE_TIPS = 4096;
  private static final int DISPLAY_VIEW_INTERACTIVE_WEB = 8192;
  private static final int DISPLAY_VIEW_LOADING = 2;
  private static final int DISPLAY_VIEW_MENU = 64;
  private static final int DISPLAY_VIEW_ORDER_NOTICE = 512;
  private static final int DISPLAY_VIEW_QRCODE = 16384;
  private static final int DISPLAY_VIEW_QRCODE_PAUSE = 32768;
  private static final int DISPLAY_VIEW_QUIT = 256;
  private static final int DISPLAY_VIEW_RESERVATION_DIALOG = 32;
  private static final int DISPLAY_VIEW_TOAST_NOTICE = 2048;
  private static final int DISPLAY_VIEW_WEB = 1024;
  private static final int LOG_PRINT_INTERVAL = 10;
  private static final int NOTICE_TIME_LEN_ERRORCHANNELNUM = 4;
  private static final int NOTICE_TIME_LEN_NOTICE = 6;
  private static final int NOTICE_TIME_LEN_NO_TSTV = 10;
  private static final int NOTICE_TIME_LEN_OPGUID = 12;
  private static final int NO_OPERATION_CONTROL_TIME = 16;
  private static final int RESERVATION_CHECK_INTERVAL = 20;
  private static final int SHOW_VIEW_TIME_LENGTH = 16;
  private static final String TAG = "MplayerTimeshiftView";
  private static final int TIME_NODE_RENEW_INTERVAL = 120;
  private static final boolean debugBarrage;
  private static int dragCount = 0;
  private static int heartbeatCount = 0;
  private boolean accessCMSFinalInvoke = false;
  private String activity_id = "";
  private String airPlayState = "not_play";
  private ApiTaskControl apiTaskControl;
  private boolean authInvokeByRefresh = false;
  private String barrageCategory = "liveshow";
  private int bufferPeriodCount;
  private int bufferTimeCount = 0;
  private int bufferTimeLength = 0;
  LinkedHashMap<String, String> bulletHashMap = new LinkedHashMap();
  private ArrayList<Drawable> bulletScreenIcons;
  private ImageView bulletScreenNoticeView;
  private int bulletScreenOperateNoticeCount = 0;
  private String bulletScreenValue;
  private String cameraposition = "";
  private String cdnAccessCMSApiUrl = "";
  private boolean cdnAccessCMSSuccess = false;
  private boolean cdnAccessFirstFrame = false;
  private Runnable cdnTimerRunnable = null;
  private CDNReportHelper.ChangeCodeRateCategory changeCodeRateCategory;
  private ChannelInfoV2 channelListDataV2;
  private MplayerTimeShiftEpisodeView channelListView;
  private String channelNumToChange = "";
  public int channelSelectedIndex;
  private ArrayList<ChannelItemInfoV2> commonChannels;
  private Context context;
  private String curChannelName = "";
  private String curChannelNum = "";
  private InteractiveMessage curInteractiveMsg;
  private String curTimeKey;
  private String currentPlayCategory = "";
  private String currentPlayChannel = "";
  private int currentPlayPos = 0;
  private String delayToShowErrExMsg = "";
  private String delayToShowErrorCode = "";
  private String dialogMsg = "";
  private int displayViews = 0;
  private Runnable doAuthor = null;
  private int getBarrageTaskId;
  private ReportJSONObject heartbeatColumn;
  private String import_id = "";
  private int ineractiveShowTimeCount;
  private String interactiveHistoryWebUrl = "";
  private ApiTaskControl interactiveMenuTaskControl;
  private MplayerInteractiveView interactiveView;
  private MplayerInteractiveWebView interactiveWebView;
  private boolean isActivityIdSame = false;
  private boolean isAlreadyShowQrCode = false;
  private boolean isBarrageOpen = false;
  private boolean isBuffering = false;
  private boolean isBulletScreenStream = false;
  private boolean isDisplayQrCode = false;
  private boolean isEnableFfordAndRwind = true;
  private boolean isEventStopPlayer = false;
  private boolean isFirstLoading = true;
  private boolean isFullPlayMode;
  private boolean isGetBarrageTaskRunning;
  private boolean isNeedToChangeChannelByNumKey = false;
  private boolean isNeedToChangeChannelByUpDownKey = false;
  private boolean isNeedToNotifyErrorChannelNum = false;
  private boolean isNeedToNotifyNoTSTV = false;
  private boolean isNotifiedPlaySuccess = false;
  private boolean isPlayerComplete = false;
  private boolean isPlayerSucceed = false;
  private boolean isReportGflow = false;
  private boolean isReportLoad = false;
  private boolean isReportPlay = false;
  private boolean isReportStop = false;
  private boolean isShowQrCode = true;
  private boolean isStartToCheckBuffering = false;
  private boolean isliveIdSame = false;
  private int lastPlayPos = 0;
  private int lastPlayStatus = 1;
  private String live_video_type = "";
  private long loadEndTime;
  private long loadStartTime;
  private MplayerV2.IMplayerV2Listener lsnr = null;
  private int mBarrageGetInterval = -1;
  private TimerTask mBarrageGetTask;
  private BarrageView mBarrageView;
  private MediaPlayerAdapter.OnCompletionListener mCompletionListener = new MediaPlayerAdapter.OnCompletionListener()
  {
    public void onCompletion(MediaPlayerAdapter paramAnonymousMediaPlayerAdapter)
    {
      Logger.i("MplayerTimeshiftView", "OnCompletionListener.onCompletion()");
      Logger.w("MplayerTimeshiftView", "live video terminated!! retrying...");
      ReportInfo.getInstance().setEntranceSrc(ReportArea.getValue("mplayer_same_episodic"));
      MplayerTimeshiftView.this.mplayerRetryLogic.initRetryParams();
      MplayerTimeshiftView.this.onChangePlayUrlEvent();
      MplayerTimeshiftView.this.addPlayRequestTask(MplayerTimeshiftView.this.tstvBeginTime + MplayerTimeshiftView.this.currentPlayPos);
      MplayerTimeshiftView.this.reportCDNIF2Error("400001");
      MplayerTimeshiftView.this.reportError("1003008", "时移模式视频不应该结束！！！,地址:" + MplayerTimeshiftView.this.playUrl);
      MplayerTimeshiftView.this.mdc.onError("1003008");
    }
  };
  private MediaPlayerAdapter.OnErrorListener mErrorListener = new MediaPlayerAdapter.OnErrorListener()
  {
    public boolean onError(MediaPlayerAdapter paramAnonymousMediaPlayerAdapter, int paramAnonymousInt1, int paramAnonymousInt2)
    {
      Logger.i("MplayerTimeshiftView", "OnErrorListener.onError() what:" + paramAnonymousInt1 + ", extra:" + paramAnonymousInt2);
      MplayerTimeshiftView.this.mdc.onPlayerError(paramAnonymousInt1);
      if (MplayerTimeshiftView.this.isEventStopPlayer)
      {
        Logger.e("MplayerTimeshiftView", "MediaPlayerAdapter.OnErrorListener 播放器已经停止！");
        return true;
      }
      if (MplayerTimeshiftView.this.isNotifiedPlaySuccess)
        MplayerTimeshiftView.this.reportCDNIF2Error("400000");
      while (true)
      {
        MplayerTimeshiftView.access$2602(MplayerTimeshiftView.this, false);
        MplayerTimeshiftView.this.displayLoadingInfo(0);
        if (!MplayerTimeshiftView.this.mplayerRetryLogic.isCanRetry())
          break;
        MplayerTimeshiftView.this.mplayerRetryLogic.printError("播放出错重试");
        MplayerTimeshiftView.this.mplayerRetryLogic.state = 257;
        MplayerTimeshiftView.this.mplayerRetryLogic.addPlayRequestCount();
        MplayerTimeshiftView.this.addPlayRequestTask(MplayerTimeshiftView.this.tstvBeginTime + MplayerTimeshiftView.this.currentPlayPos);
        return true;
        MplayerTimeshiftView.this.reportCDNIF1AccessCacheFail(MplayerTimeshiftView.this.playUrl);
      }
      MplayerTimeshiftView.this.mplayerRetryLogic.printError("播放出错弹框");
      MplayerTimeshiftView.this.lsnr.onPreLoadingViewDisplay(4, "");
      if (MplayerTimeshiftView.this.checkErrorNoticeViewCanDisplay())
      {
        MplayerTimeshiftView.this.hideCenterViews();
        String str = "MediaPlayerAdapter.OnErrorListener what:" + paramAnonymousInt1 + ",extra:" + paramAnonymousInt2 + ",地址:" + MplayerTimeshiftView.this.playUrl;
        MplayerTimeshiftView.this.reportError("1003009", str);
        MplayerTimeshiftView.this.mdc.onError("1003009");
        MplayerTimeshiftView.this.lsnr.onErrorNoticeViewDisplay(0, "1003009", str, true);
        MplayerTimeshiftView.access$4176(MplayerTimeshiftView.this, 16);
        MplayerTimeshiftView.access$4202(MplayerTimeshiftView.this, ApplicationException.getErrorDiscrib("1003009"));
      }
      MplayerTimeshiftView.access$3202(MplayerTimeshiftView.this, true);
      MplayerTimeshiftView.this.onEventStopPlayer();
      return true;
    }
  };
  private Handler mHandler = new Handler();
  private MediaPlayerAdapter.OnInfoListener mInfoListener = new MediaPlayerAdapter.OnInfoListener()
  {
    public boolean onInfo(MediaPlayerAdapter paramAnonymousMediaPlayerAdapter, int paramAnonymousInt1, int paramAnonymousInt2)
    {
      if ((paramAnonymousInt1 == 3) || (paramAnonymousInt1 == 1013))
      {
        MplayerTimeshiftView.this.displayLoadingInfo(4);
        MplayerTimeshiftView.this.lsnr.onPreLoadingViewDisplay(4, "");
      }
      return false;
    }
  };
  private long mMplayerQuitPageStarTime = -1L;
  private MediaPlayerAdapter.OnPreparedListener mPreparedListener = new MediaPlayerAdapter.OnPreparedListener()
  {
    public void onPrepared(MediaPlayerAdapter paramAnonymousMediaPlayerAdapter)
    {
      Logger.i("MplayerTimeshiftView", "onPrepared");
      MplayerTimeshiftView.this.reportPlayQuality(2);
      ReportState.setStateStartTime(3);
      MplayerTimeshiftView.this.lsnr.onPreLoadingViewDisplay(4, "");
      MplayerTimeshiftView.access$202(MplayerTimeshiftView.this, "in_play");
      String str = GlobalLogic.getInstance().getVideoLayoutRatio();
      if ("16v9".equals(str))
      {
        MplayerTimeshiftView.this.mpCore.changeVideoLayoutTo16v9();
        Logger.i("MplayerTimeshiftView", "loadEndTime=" + MplayerTimeshiftView.this.loadEndTime + ",loadStartTime=" + MplayerTimeshiftView.this.loadStartTime);
        MplayerTimeshiftView.access$402(MplayerTimeshiftView.this, Tools.time2sec(Tools.getTimeString()));
        MplayerTimeshiftView.access$602(MplayerTimeshiftView.this, MplayerTimeshiftView.access$700());
        Logger.i("MplayerTimeshiftView", "playTime=" + MplayerTimeshiftView.this.playTime + ",pParams.nns_videoInfo.videoId=" + MplayerTimeshiftView.this.pParams.nns_videoInfo.videoId + ",TextUtils.isEmpty(timeHashMap.get(pParams.nns_videoInfo.videoId))=" + TextUtils.isEmpty((CharSequence)MplayerTimeshiftView.this.timeHashMap.get(MplayerTimeshiftView.this.pParams.nns_videoInfo.videoId)));
        if (!TextUtils.isEmpty((CharSequence)MplayerTimeshiftView.this.timeHashMap.get(MplayerTimeshiftView.this.pParams.nns_videoInfo.videoId)))
          break label850;
        MplayerTimeshiftView.this.timeHashMap.clear();
        MplayerTimeshiftView.this.timeHashMap.put(MplayerTimeshiftView.this.pParams.nns_videoInfo.videoId, Long.toString(MplayerTimeshiftView.this.playTime));
        int i = GlobalLogic.getInstance().getLiveChannelBulletScreenCount();
        Logger.i("MplayerTimeshiftView", ",count=" + i);
        if (i >= 3)
          break label835;
        GlobalLogic.getInstance().setLiveChannelBulletScreenCount(i + 1);
        MplayerTimeshiftView.this.bulletScreenNoticeView.setVisibility(0);
        label372: MplayerTimeshiftView.access$1102(MplayerTimeshiftView.this, 0);
        MplayerTimeshiftView.access$1202(MplayerTimeshiftView.this, false);
        MplayerTimeshiftView.access$1302(MplayerTimeshiftView.this, true);
        MplayerTimeshiftView.access$1402(MplayerTimeshiftView.this, false);
        MplayerTimeshiftView.access$1502(MplayerTimeshiftView.this, false);
        label417: Logger.i("MplayerTimeshiftView", "onPrepared() isFirstLoading:" + MplayerTimeshiftView.this.isFirstLoading + ",playTime=" + MplayerTimeshiftView.this.playTime + ",timeHashMap.get(pParams.nns_videoInfo.videoId)=" + (String)MplayerTimeshiftView.this.timeHashMap.get(MplayerTimeshiftView.this.pParams.nns_videoInfo.videoId) + ",timeHashMap.size=" + MplayerTimeshiftView.this.timeHashMap.size());
        if (!MplayerTimeshiftView.this.isFirstLoading)
          break label892;
        MplayerTimeshiftView.access$1602(MplayerTimeshiftView.this, false);
        MplayerTimeshiftView.this.mdc.onPlayerFirstStart(MplayerTimeshiftView.this.playUrl, 6, MplayerTimeshiftView.this.tstvBeginTime + MplayerTimeshiftView.this.currentPlayPos, MplayerTimeshiftView.this.tstvBeginTime, MplayerTimeshiftView.this.tstvRealOffset, MplayerTimeshiftView.this.getChannelIdForDataCollect(), MplayerTimeshiftView.this.mpCore, MplayerTimeshiftView.this.pParams.nns_videoInfo.packageId, MplayerTimeshiftView.this.pParams.nns_videoInfo.film_name, 0L, MplayerTimeshiftView.this.pParams);
      }
      while (true)
      {
        MplayerTimeshiftView.this.mpCore.start();
        MplayerTimeshiftView.this.mBarrageView.resume();
        MplayerTimeshiftView.access$2402(MplayerTimeshiftView.this, 1);
        MplayerTimeshiftView.access$2502(MplayerTimeshiftView.this, 0);
        if (!MplayerTimeshiftView.this.isStartToCheckBuffering)
          MplayerTimeshiftView.access$2602(MplayerTimeshiftView.this, true);
        InteractiveManager.getInstance().subscribeChannel(MplayerTimeshiftView.this.import_id);
        MplayerTimeshiftView.access$2802(MplayerTimeshiftView.this, 5000);
        MplayerTimeshiftView.this.switchBarrage(MplayerTimeshiftView.this.isBarrageOpen);
        return;
        if ("4v3".equals(str))
        {
          MplayerTimeshiftView.this.mpCore.changeVideoLayoutTo4v3();
          break;
        }
        if ("full".equals(str))
        {
          MplayerTimeshiftView.this.mpCore.changeVideoLayoutToFullScreen();
          break;
        }
        if ("default".equals(str))
        {
          MplayerTimeshiftView.this.mpCore.changeVideoLayoutToDefault();
          break;
        }
        if ("235".equals(str))
        {
          MplayerTimeshiftView.this.mpCore.changeVideoLayoutTo2351();
          break;
        }
        MplayerTimeshiftView.this.mpCore.changeVideoLayoutTo16v9();
        break;
        label835: MplayerTimeshiftView.this.bulletScreenNoticeView.setVisibility(8);
        break label372;
        label850: MplayerTimeshiftView.access$602(MplayerTimeshiftView.this, Long.parseLong((String)MplayerTimeshiftView.this.timeHashMap.get(MplayerTimeshiftView.this.pParams.nns_videoInfo.videoId)));
        break label417;
        label892: MplayerTimeshiftView.this.mdc.onPlayerBufferEnd(MplayerTimeshiftView.this.tstvBeginTime + MplayerTimeshiftView.this.currentPlayPos);
      }
    }
  };
  private MediaPlayerAdapter.OnSeekCompleteListener mSeekCompleteListener = new MediaPlayerAdapter.OnSeekCompleteListener()
  {
    public void onSeekComplete(MediaPlayerAdapter paramAnonymousMediaPlayerAdapter)
    {
      Logger.i("MplayerTimeshiftView", "OnSeekCompleteListener.onSeekComplete()");
      MplayerTimeshiftView.this.mdc.onPlayerSeekEnd(MplayerTimeshiftView.this.currentPlayPos);
      MplayerTimeshiftView.this.mpCore.start();
    }
  };
  private Timer mTimer;
  private int maxChannelIndexInCurrentCategory = -1;
  private int maxProgressPos = 0;
  private long mdEndTime;
  private long mdStartTime;
  private MplayerDataCollector mdc;
  private MediaAssetsInfo mediaAssetsInfo;
  private MplayerMenuView menuView;
  private int minChannelIndexInCurrentCategory = -1;
  private int minProgressPos = 0;
  private int minStepLen = 0;
  private MediaPlayerCore mpCore = null;
  private MplayerRetryLogic mplayerRetryLogic = null;
  private boolean mutexSearchChannel = false;
  private boolean needCDNTimerReport = true;
  private boolean needReportHeartbeat = true;
  private ProgressBar nextProgramLoading;
  private TextView nextProgramTitle;
  private int noOperationTimer = 0;
  private MplayerNoticeView notice;
  private String noticeErrorChannel = "";
  private String noticeNoTSTV = "";
  private boolean notifyChanngeQuality = true;
  private int notifyChanngeQualityCountter = 0;
  private int notifyErrorChannelNumTimer = 0;
  private boolean notifyNetWorkLow = true;
  private int notifyNetWorkLowCountter = 0;
  private int notifyNoTSTVTimer = 0;
  private MplayerV2.OnPlayerControllEventCallback onPlayerControllEventCallback;
  private String orderUrl = "";
  private long p2pStarTime = 0L;
  private PlayInfo pInfo = null;
  private PlayerIntentParams pParams = null;
  private String pUrl = "";
  private String package_id = null;
  private int pay = 0;
  private MplayerPlayStateView playState;
  private int playStatusFlag = 0;
  private long playTime = 0L;
  private String playUrl = "";
  private int posMovingTimes = 0;
  private int posNoMovingTimes = 0;
  private ProgressBar prevProgramLoading;
  private TextView prevProgramTitle;
  private ArrayList<Drawable> qrCodeIcons;
  private ImageView qrCodeView;
  private MplayerQuitXulPage quitView;
  private String reportway_type = "";
  private Runnable runnable = null;
  private long seekStartPos = 0L;
  private MplayerSeekBar seekbar;
  private int setLastPlayStustimes = 0;
  private long startPlayTime = 0L;
  private Map<String, String> timeHashMap;
  List timeNodeData;
  List timeNodeDiscrib;
  private int timerCount = 0;
  private View timeshiftPreviewer;
  private MplayerTitleView title;
  private TokenDialog tokenDialog;
  private long tstvBeginTime;
  private long tstvRealOffset;
  private long tstvSeekTime;
  private String tv_channel = "";
  private String type_name = "";

  public MplayerTimeshiftView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.context = paramContext;
    this.bulletHashMap.put("open", "打开");
    this.bulletHashMap.put("close", "关闭");
    initViews();
  }

  public MplayerTimeshiftView(Context paramContext, MplayerV2.IMplayerV2Listener paramIMplayerV2Listener)
  {
    super(paramContext);
    this.context = paramContext;
    this.lsnr = paramIMplayerV2Listener;
    this.bulletHashMap.put("open", "打开");
    this.bulletHashMap.put("close", "关闭");
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
      Logger.d("MplayerTimeshiftView", str + "互动菜单");
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

  private void addPlayAuthTask(String paramString)
  {
    Logger.i("MplayerTimeshiftView", "debug addPlayAuthTask() videoId:" + paramString);
    addPlayRequestTask();
    addPlaybillRequestTask(this.pParams.nns_videoInfo.videoId);
    this.quitView.setPlayerInfos(this.pParams.nns_videoInfo.packageId, this.pParams.nns_videoInfo.categoryId, this.pParams.nns_videoInfo.videoId, this.pParams.nns_videoInfo.videoType);
    this.quitView.loadFilms();
  }

  private void addPlayRequestTask()
  {
    if (this.mdc != null)
      this.mdc.onAddVideotaskToPrePare(this.context, this.pParams.nns_videoInfo.videoId, getChannelNameForDataCollect(), "-1", getChannelNameForDataCollect(), this.pParams.mode);
    Logger.i("MplayerTimeshiftView", "debug addPlayRequestTask()");
    if (this.apiTaskControl.checkSameTask("ApiRequestVideoPlay", this.pParams.nns_videoInfo.videoId))
    {
      Logger.i("MplayerTimeshiftView", "debug addPlayRequestTask() there is already a task runing");
      return;
    }
    initPlayParams();
    this.startPlayTime = System.currentTimeMillis();
    this.mdc.startCollector();
    Date localDate = new Date(this.tstvBeginTime);
    String str1 = new SimpleDateFormat("yyyyMMdd").format(localDate);
    String str2 = new SimpleDateFormat("HHmmss").format(localDate);
    Logger.i("MplayerTimeshiftView", "debug addPlayRequestTask() begin_day:" + str1 + ", begin_time:" + str2 + ", nns_videoInfo.videoType=" + this.pParams.nns_videoInfo.videoType + ", videoid:" + this.pParams.nns_videoInfo.videoId);
    this.mplayerRetryLogic.print("请求开始");
    ServerApiManager localServerApiManager = ServerApiManager.i();
    String str3 = this.pParams.nns_videoInfo.videoId;
    int i = this.pParams.nns_videoInfo.videoType;
    String str4 = this.pParams.nns_videoInfo.packageId;
    String str5 = this.pParams.nns_videoInfo.categoryId;
    int j = this.pParams.nns_index;
    String str6 = this.pParams.mediaQuality;
    String str7 = getCaps();
    String str8 = this.mplayerRetryLogic.svrip;
    String str9 = String.valueOf(this.mplayerRetryLogic.request_times);
    SccmsApiRequestVideoPlayV2TaskListener localSccmsApiRequestVideoPlayV2TaskListener = new SccmsApiRequestVideoPlayV2TaskListener();
    int k = localServerApiManager.ApiRequestVideoPlayV2(str3, i, str4, str5, j, "", str6, str1, str2, "", "", str7, str8, str9, localSccmsApiRequestVideoPlayV2TaskListener);
    this.loadStartTime = Tools.time2sec(Tools.getTimeString());
    this.apiTaskControl.clear();
    this.apiTaskControl.addTaskLabel(k, "ApiRequestVideoPlay", this.pParams.nns_videoInfo.videoId);
    updateInteractiveMenu();
  }

  private void addPlayRequestTask(long paramLong)
  {
    Logger.i("MplayerTimeshiftView", "addPlayRequestTask() beginPos:" + paramLong);
    ReportState.setStateStartTime(1);
    this.tstvBeginTime = paramLong;
    setTitleText();
    this.mdc.onPlayerSeekEnd(paramLong);
    this.mdc.onPlayerBufferBegin(BufferEnum.BUFFER_DRAG, this.tstvBeginTime);
    this.isBuffering = true;
    this.cdnAccessCMSSuccess = false;
    Date localDate = new Date(this.tstvBeginTime);
    String str1 = new SimpleDateFormat("yyyyMMdd").format(localDate);
    String str2 = new SimpleDateFormat("HHmmss").format(localDate);
    Logger.i("MplayerTimeshiftView", "addPlayRequestTaskForUserSeek() begin_day:" + str1 + ", begin_time:" + str2 + ", nns_videoInfo.videoType=" + this.pParams.nns_videoInfo.videoType);
    this.mplayerRetryLogic.print("请求开始");
    ServerApiManager localServerApiManager = ServerApiManager.i();
    String str3 = this.pParams.nns_videoInfo.videoId;
    int i = this.pParams.nns_videoInfo.videoType;
    String str4 = this.pParams.nns_videoInfo.packageId;
    String str5 = this.pParams.nns_videoInfo.categoryId;
    int j = this.pParams.nns_index;
    String str6 = this.pParams.mediaQuality;
    String str7 = this.mplayerRetryLogic.svrip;
    String str8 = String.valueOf(this.mplayerRetryLogic.request_times);
    SccmsApiRequestVideoPlayV2TaskListener localSccmsApiRequestVideoPlayV2TaskListener = new SccmsApiRequestVideoPlayV2TaskListener();
    int k = localServerApiManager.ApiRequestVideoPlayV2(str3, i, "", str4, str5, j, "", str6, str1, str2, "", "", str7, str8, localSccmsApiRequestVideoPlayV2TaskListener);
    this.apiTaskControl.clear();
    this.apiTaskControl.addTaskLabel(k, "ApiRequestVideoPlay", this.pParams.nns_videoInfo.videoId);
    updateInteractiveMenu();
  }

  private void addPlaybillRequestTask(String paramString)
  {
    Logger.i("MplayerTimeshiftView", "debug addPlaybillRequestTask() videoId:" + paramString);
    this.mdStartTime = Tools.time2sec(Tools.getTimeString());
    int i = ServerApiManager.i().APIGetPlaybill(paramString, 1, 1, new SccmsApiGetPlaybillTaskListener());
    this.apiTaskControl.addTaskLabel(i, "APIGetPlaybill", paramString);
  }

  private void addTaskOfCategoryMediaAsset(String paramString)
  {
    ServerApiManager.i().APIGetMediaAssetsInfo(paramString, new SccmsApiGetMediaAssetsInfoTask.ISccmsApiGetMediaAssetsInfoTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.i("MplayerTimeshiftView", "addTaskOfCategoryMediaAsset.onError() error" + paramAnonymousServerApiCommonError);
        String str = "MplayerTimeshiftView.addTaskOfCategoryMediaAsset() error:" + paramAnonymousServerApiCommonError.toString();
        MplayerTimeshiftView.this.mplayerRetryLogic.initRetryParams();
        MplayerTimeshiftView.this.onChangePlayUrlEvent();
        if (MplayerTimeshiftView.this.checkErrorNoticeViewCanDisplay())
        {
          MplayerTimeshiftView.this.hideCenterViews();
          MplayerTimeshiftView.this.mdc.onError("1002007");
          if (MplayerTimeshiftView.this.isFullPlayMode);
          for (boolean bool = true; ; bool = false)
          {
            MplayerTimeshiftView.this.lsnr.onErrorNoticeViewDisplay(0, "1002007", str, bool);
            MplayerTimeshiftView.this.reportError("1002007", "ISccmsApiGetMediaAssetsInfoTaskListener.onError", paramAnonymousServerApiTaskInfo, paramAnonymousServerApiCommonError);
            MplayerTimeshiftView.access$4176(MplayerTimeshiftView.this, 16);
            MplayerTimeshiftView.access$4202(MplayerTimeshiftView.this, ApplicationException.getErrorDiscrib("1002007"));
            return;
          }
        }
        MplayerTimeshiftView.access$8102(MplayerTimeshiftView.this, "1003004");
        MplayerTimeshiftView.access$8202(MplayerTimeshiftView.this, str);
        MplayerTimeshiftView.this.mdc.onError("1003004");
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, MediaAssetsInfo paramAnonymousMediaAssetsInfo)
      {
        Logger.i("MplayerTimeshiftView", "addTaskOfCategoryMediaAsset.onSuccess() MediaAssetsInfo" + paramAnonymousMediaAssetsInfo);
        GlobalLogic.getInstance().setTimeshiftAssetsInfo(paramAnonymousMediaAssetsInfo);
        MplayerTimeshiftView.access$7902(MplayerTimeshiftView.this, paramAnonymousMediaAssetsInfo);
        MplayerTimeshiftView.this.refreshChannelList();
      }
    });
  }

  private boolean canTimeShift()
  {
    if ((this.channelListDataV2 == null) || (this.channelListDataV2.channelList == null) || (this.channelListDataV2.channelList.size() < this.channelSelectedIndex));
    ChannelItemInfoV2 localChannelItemInfoV2;
    do
    {
      return false;
      localChannelItemInfoV2 = (ChannelItemInfoV2)this.channelListDataV2.channelList.get(this.channelSelectedIndex);
    }
    while ((localChannelItemInfoV2 == null) || (TextUtils.isEmpty(localChannelItemInfoV2.capability)) || (!localChannelItemInfoV2.capability.contains("TSTV")));
    Logger.i("MplayerTimeshiftView", "canTimeShift true");
    return true;
  }

  private void cancelTasks()
  {
    Logger.i("MplayerTimeshiftView", "cancelTasks");
    if (this.apiTaskControl != null)
      this.apiTaskControl.clear();
  }

  private void changeQrCodeStatus(boolean paramBoolean)
  {
    int i;
    if ((0x4000 & this.displayViews) > 0)
    {
      i = 1;
      if ((0x8000 & this.displayViews) <= 0)
        break label85;
    }
    label85: for (int j = 1; ; j = 0)
    {
      if (!paramBoolean)
      {
        if (i != 0)
        {
          this.displayViews = (0xFFFFBFFF & this.displayViews);
          this.qrCodeView.setVisibility(8);
        }
        if (j != 0)
        {
          this.displayViews = (0xFFFF7FFF & this.displayViews);
          this.qrCodeView.setVisibility(8);
        }
      }
      return;
      i = 0;
      break;
    }
  }

  private void changeVideoToNextChannel()
  {
    ChannelItemInfoV2 localChannelItemInfoV2 = findNextChannel();
    if (localChannelItemInfoV2 == null)
      return;
    this.curChannelNum = makeIdxString(localChannelItemInfoV2.channelNum);
    this.curChannelName = localChannelItemInfoV2.name;
    this.isNeedToChangeChannelByUpDownKey = true;
    if (this.timeNodeData != null);
    while (true)
    {
      synchronized (this.timeNodeData)
      {
        this.timeNodeData.clear();
        this.timeNodeDiscrib.clear();
        if (!this.isFullPlayMode)
        {
          this.nextProgramLoading.setVisibility(0);
          this.prevProgramLoading.setVisibility(0);
          this.nextProgramTitle.setText("下个节目：加载中...");
          this.prevProgramTitle.setText("正在播出：加载中...");
          Logger.i("MplayerTimeshiftView", "changeVideoToNextChannel() channel_index:" + localChannelItemInfoV2.channelNum + ", film_name:" + localChannelItemInfoV2.name);
          return;
        }
      }
      setTitleChannelNumAndName();
      if ((0x4 & this.displayViews) == 0)
      {
        this.displayViews = (0x4 | this.displayViews);
        this.seekbar.display();
        this.title.display();
      }
    }
  }

  private void changeVideoToNumberedChannel(String paramString)
  {
    if (this.channelNumToChange.isEmpty())
      this.channelNumToChange = ("00" + paramString);
    while (true)
    {
      this.isNeedToChangeChannelByNumKey = true;
      setTitleChannelNum(this.channelNumToChange);
      if ((0x4 & this.displayViews) == 0)
      {
        this.displayViews = (0x4 | this.displayViews);
        this.seekbar.display();
        this.title.display();
      }
      return;
      int i = Integer.valueOf(this.channelNumToChange).intValue();
      if (i < 10)
        this.channelNumToChange = ("0" + i + paramString);
      else if (i < 100)
        this.channelNumToChange = (i + paramString);
    }
  }

  private void changeVideoToPrevChannel()
  {
    ChannelItemInfoV2 localChannelItemInfoV2 = findPrevChannel();
    if (localChannelItemInfoV2 == null);
    do
    {
      return;
      Logger.i("MplayerTimeshiftView", "changeVideoToPrevChannel() channel_index:" + localChannelItemInfoV2.channelNum + ", film_name:" + localChannelItemInfoV2.name);
      this.curChannelNum = makeIdxString(localChannelItemInfoV2.channelNum);
      this.curChannelName = localChannelItemInfoV2.name;
      this.isNeedToChangeChannelByUpDownKey = true;
      if (this.timeNodeData != null);
      synchronized (this.timeNodeData)
      {
        this.timeNodeData.clear();
        this.timeNodeDiscrib.clear();
        if (!this.isFullPlayMode)
        {
          this.nextProgramLoading.setVisibility(0);
          this.prevProgramLoading.setVisibility(0);
          this.nextProgramTitle.setText("下个节目：加载中...");
          this.prevProgramTitle.setText("正在播出：加载中...");
          return;
        }
      }
      setTitleChannelNumAndName();
    }
    while ((0x4 & this.displayViews) != 0);
    this.displayViews = (0x4 | this.displayViews);
    this.seekbar.display();
    this.title.display();
  }

  private void channelListDataNullException()
  {
    Logger.i("MplayerTimeshiftView", "channelListDataNullException() result or result.channelList is null/empty");
    boolean bool;
    if (checkErrorNoticeViewCanDisplay())
    {
      hideCenterViews();
      if (this.isFullPlayMode)
      {
        bool = true;
        this.lsnr.onErrorNoticeViewDisplay(0, "1002007", "channelListDataNullException() result or result.channelList is null/empty", bool);
        reportError("1002007", "channelListDataNullException() result or result.channelList is null/empty");
        this.displayViews = (0x10 | this.displayViews);
        this.mdc.onError("1002007");
        this.dialogMsg = ApplicationException.getErrorDiscrib("1002007");
      }
    }
    while (true)
    {
      this.mplayerRetryLogic.initRetryParams();
      onChangePlayUrlEvent();
      return;
      bool = false;
      break;
      this.delayToShowErrorCode = "1003004";
      this.delayToShowErrExMsg = "channelListDataNullException() result or result.channelList is null/empty";
    }
  }

  private void checkDelayChangeChannel()
  {
    if (this.isNeedToChangeChannelByUpDownKey)
      if (this.noOperationTimer >= 2);
    while ((!this.isNeedToChangeChannelByNumKey) || (this.noOperationTimer <= 2))
    {
      return;
      this.isNeedToChangeChannelByUpDownKey = false;
      delayToStartVideo();
      return;
    }
    Logger.i("MplayerTimeshiftView", "checkDelayChangeChannel() curChannelNum" + this.curChannelNum + ", channelNumToChange:" + this.channelNumToChange);
    if (this.curChannelNum.equals(this.channelNumToChange))
    {
      this.channelNumToChange = "";
      this.isNeedToChangeChannelByNumKey = false;
      setTitleText();
      return;
    }
    ChannelItemInfoV2 localChannelItemInfoV2 = findChannelByNum(Integer.valueOf(this.channelNumToChange).intValue(), this.currentPlayCategory);
    if (localChannelItemInfoV2 == null)
    {
      this.isNeedToNotifyErrorChannelNum = true;
      checkDisplayNotice();
      setTitleText();
    }
    while (true)
    {
      this.channelNumToChange = "";
      this.isNeedToChangeChannelByNumKey = false;
      return;
      if (this.timeNodeData != null);
      synchronized (this.timeNodeData)
      {
        this.timeNodeData.clear();
        this.timeNodeDiscrib.clear();
        this.curChannelNum = makeIdxString(localChannelItemInfoV2.channelNum);
        this.curChannelName = localChannelItemInfoV2.name;
        this.channelSelectedIndex = findChannelIndex(localChannelItemInfoV2);
        delayToStartVideo();
      }
    }
  }

  private void checkDelayShowErrorView()
  {
    if (this.delayToShowErrorCode.isEmpty());
    while (((0x200 & this.displayViews) > 0) || ((0x400 & this.displayViews) > 0))
      return;
    hideCenterViews();
    this.lsnr.onErrorNoticeViewDisplay(0, this.delayToShowErrorCode, this.delayToShowErrExMsg, true);
    reportError(this.delayToShowErrorCode, this.delayToShowErrExMsg);
    this.displayViews = (0x10 | this.displayViews);
    this.delayToShowErrorCode = "";
  }

  private void checkDisplayNotice()
  {
    if (this.bulletScreenOperateNoticeCount > 16)
    {
      this.bulletScreenOperateNoticeCount = (-1 + this.bulletScreenOperateNoticeCount);
      if (this.bulletScreenNoticeView.getVisibility() == 0)
      {
        this.bulletScreenNoticeView.setVisibility(8);
        setBulletScreenQrCodeStatusByStream(0);
        if ((0x4000 & this.displayViews) > 0)
          this.isAlreadyShowQrCode = true;
      }
      if ((!this.notifyChanngeQuality) || ((this.bufferTimeLength <= 60) && (this.bufferTimeCount <= 10)))
        break label209;
      this.notifyChanngeQualityCountter = (1 + this.notifyChanngeQualityCountter);
      if ((this.notifyChanngeQualityCountter > 6) || (!isErrorNoticeShowing()))
        break label169;
      this.notice.setPlayNotice("您的网络不给力，按菜单键进行清晰度切换");
      if ((0x100 & this.displayViews) == 0)
      {
        hideQuitView();
        this.notice.setVisibility(0);
        this.displayViews = (0x800 | this.displayViews);
      }
    }
    label169: label209: label359: 
    do
    {
      do
      {
        do
        {
          do
          {
            return;
            this.bulletScreenOperateNoticeCount = (1 + this.bulletScreenOperateNoticeCount);
            break;
            this.displayViews = (0xFFFFF7FF & this.displayViews);
            this.notice.setVisibility(4);
            this.notice.setPlayNotice("");
            this.notifyChanngeQualityCountter = 0;
            this.notifyChanngeQuality = false;
            if ((!this.notifyNetWorkLow) || (this.startPlayTime == 0L) || (System.currentTimeMillis() - this.startPlayTime <= 20000L) || (this.isPlayerSucceed))
              break label359;
            this.notifyNetWorkLowCountter = (1 + this.notifyNetWorkLowCountter);
            if ((this.notifyNetWorkLowCountter > 6) || (!isErrorNoticeShowing()))
              break label319;
            this.notice.setPlayNotice("当前网络可能存在问题，请调整清晰度或前往首页-服务-网络测速功能优化网络。");
          }
          while ((0x100 & this.displayViews) != 0);
          hideQuitView();
          this.notice.setVisibility(0);
          this.displayViews = (0x800 | this.displayViews);
          return;
          this.displayViews = (0xFFFFF7FF & this.displayViews);
          this.notice.setVisibility(4);
          this.notice.setPlayNotice("");
          this.notifyNetWorkLowCountter = 0;
          this.notifyNetWorkLow = false;
          if (!this.isNeedToNotifyNoTSTV)
            break label514;
          String str2 = this.noticeNoTSTV;
          if (this.notifyNoTSTVTimer == 0)
            this.notice.setAlarmTime(0, false);
          this.notifyNoTSTVTimer = (1 + this.notifyNoTSTVTimer);
          Logger.i("MplayerTimeshiftView", "checkDisplayNotice() notifyNoTSTVTimer:" + this.notifyNoTSTVTimer);
          if (this.notifyNoTSTVTimer > 10)
            break label474;
          this.notice.setPlayNotice(str2);
        }
        while ((0x100 & this.displayViews) != 0);
        this.notice.setVisibility(0);
        this.displayViews = (0x800 | this.displayViews);
        return;
        this.displayViews = (0xFFFFF7FF & this.displayViews);
        this.notice.setVisibility(4);
        this.notice.setPlayNotice("");
        this.isNeedToNotifyNoTSTV = false;
        this.notifyNoTSTVTimer = 0;
      }
      while (!this.isNeedToNotifyErrorChannelNum);
      String str1 = this.noticeErrorChannel;
      if (this.notifyErrorChannelNumTimer == 0)
        this.notice.setAlarmTime(0, false);
      this.notifyErrorChannelNumTimer = (1 + this.notifyErrorChannelNumTimer);
      Logger.i("MplayerTimeshiftView", "checkDisplayNotice() notifyErrorChannelNumTimer:" + this.notifyErrorChannelNumTimer);
      if (this.notifyErrorChannelNumTimer > 4)
        break label628;
      this.notice.setPlayNotice(str1);
    }
    while ((0x100 & this.displayViews) != 0);
    label319: label474: this.notice.setVisibility(0);
    label514: this.displayViews = (0x800 | this.displayViews);
    return;
    label628: this.displayViews = (0xFFFFF7FF & this.displayViews);
    this.notice.setVisibility(4);
    this.notice.setPlayNotice("");
    this.isNeedToNotifyErrorChannelNum = false;
    this.notifyErrorChannelNumTimer = 0;
  }

  private boolean checkErrorNoticeViewCanDisplay()
  {
    displayLoadingInfo(4);
    (0x0 | 0x230);
    return (0x230 & this.displayViews) <= 0;
  }

  private void checkInBufferState()
  {
    int i = 1;
    if (!this.isStartToCheckBuffering);
    label208: 
    do
    {
      do
      {
        return;
        if (this.cdnAccessCMSSuccess)
          break;
      }
      while (this.timerCount % 10 != 0);
      Logger.e("MplayerTimeshiftView", "IF1 访问CMS失败，不检查缓冲!");
      return;
      if (this.currentPlayPos != this.lastPlayPos)
        break;
      if (this.playStatusFlag != 2);
      for (this.posNoMovingTimes = (1 + this.posNoMovingTimes); ; this.posNoMovingTimes = 0)
      {
        if ((this.posNoMovingTimes < 3) || (this.posNoMovingTimes > 120))
          break label208;
        if (!this.isBuffering)
        {
          this.isBuffering = i;
          if (this.isPlayerSucceed)
          {
            this.bufferTimeCount = (1 + this.bufferTimeCount);
            this.bufferPeriodCount = (1 + this.bufferPeriodCount);
            if (i == this.bufferPeriodCount)
              reportCDNIF2Buffer(this.bufferPeriodCount);
          }
          this.mdc.onPlayerBufferBegin(BufferEnum.BUFFER_COMMON, this.tstvBeginTime + this.lastPlayPos);
          ReportState.setStateStartTime(7);
          ReportState.setBufferCause(2);
        }
        this.bufferTimeLength = (1 + this.bufferTimeLength);
        displayLoadingInfo(0);
        if (this.timerCount % 10 != 0)
          break;
        Logger.i("MplayerTimeshiftView", "playerTimerSlowTask() video play is buffering");
        return;
      }
    }
    while (this.posNoMovingTimes <= 120);
    if (!this.isNotifiedPlaySuccess)
      reportCDNIF1AccessCacheFail(this.playUrl);
    while ((!this.isPlayerSucceed) && (this.mplayerRetryLogic.isCanRetry()))
    {
      this.mplayerRetryLogic.printError("缓冲失败重试");
      Logger.d("MplayerTimeshiftView", "缓冲失败重试");
      this.mplayerRetryLogic.state = 257;
      this.mplayerRetryLogic.addPlayRequestCount();
      addPlayRequestTask();
      this.isStartToCheckBuffering = false;
      this.isBuffering = false;
      return;
      reportCDNIF2Error("303000");
    }
    this.mplayerRetryLogic.printError("缓冲失败弹框");
    this.isStartToCheckBuffering = false;
    this.isBuffering = false;
    this.mdc.onPlayerBufferEnd(this.tstvBeginTime + this.lastPlayPos);
    reportPlayQuality(7);
    this.mdc.onPlayerStop(this.tstvBeginTime + this.lastPlayPos);
    reportCDNIF2Complete(this.bufferPeriodCount);
    stopCDNTimerReport();
    this.mpCore.stop();
    displayLoadingInfo(4);
    this.mdc.onError("1003007");
    boolean bool;
    if (checkErrorNoticeViewCanDisplay())
    {
      hideCenterViews();
      if (this.isFullPlayMode);
      while (true)
      {
        this.lsnr.onErrorNoticeViewDisplay(0, "1003007", "", i);
        reportError("1003007", "checkInBufferState 缓冲失败");
        this.displayViews = (0x10 | this.displayViews);
        this.dialogMsg = ApplicationException.getErrorDiscrib("1003007");
        return;
        bool = false;
      }
    }
    this.delayToShowErrorCode = "1003007";
    return;
    if (this.isBuffering)
    {
      this.isBuffering = false;
      this.mdc.onPlayerBufferEnd(this.tstvBeginTime + this.lastPlayPos);
      reportPlayQuality(7);
    }
    this.isPlayerSucceed = bool;
    displayLoadingInfo(4);
    checkPlaySuccessed();
    this.lastPlayPos = this.currentPlayPos;
    this.posNoMovingTimes = 0;
  }

  private void checkInteractiveShow()
  {
    if (this.interactiveView != null)
    {
      this.ineractiveShowTimeCount = (1 + this.ineractiveShowTimeCount);
      if ((0x1000 & this.displayViews) <= 0)
        break label66;
      if (this.curInteractiveMsg != null)
      {
        int i = (int)(1000L * this.curInteractiveMsg.showTime / 499L);
        if (this.ineractiveShowTimeCount >= i)
          setInteractiveVisibility(4);
      }
    }
    label66: LiveTopicMessageData localLiveTopicMessageData;
    do
    {
      return;
      localLiveTopicMessageData = InteractiveManager.getInstance().getMessageData();
    }
    while (localLiveTopicMessageData == null);
    this.curInteractiveMsg = InteractiveMessage.newFromLiveMsg(localLiveTopicMessageData);
    this.interactiveView.setInteractiveMsg(this.curInteractiveMsg);
    setInteractiveVisibility(0);
    this.interactiveView.bringToFront();
    this.ineractiveShowTimeCount = 0;
  }

  private void checkNoOperationForFJYD()
  {
    if (((0x40 & this.displayViews) > 0) || ((0x8 & this.displayViews) > 0) || ((0x4 & this.displayViews) > 0))
      this.noOperationTimer = (1 + this.noOperationTimer);
    if ((this.noOperationTimer >= 16) && ((0x4 & this.displayViews) > 0) && ((0x2 & this.displayViews) == 0))
    {
      this.seekbar.hide();
      this.title.hide();
      this.displayViews = (0xFFFFFFFB & this.displayViews);
      if ((0x8000 & this.displayViews) > 0)
        setBulletScreenQrCodeStatusByPause(8);
    }
    if (this.noOperationTimer >= 16)
    {
      if ((0x8 & this.displayViews) > 0)
      {
        Logger.d("MplayerTimeshiftView", "无操作隐藏ChannelList");
        setChannelListVisibility(4);
        this.displayViews = (0xFFFFFFF7 & this.displayViews);
      }
      if ((0x40 & this.displayViews) > 0)
      {
        setMenuViewVisibility(4);
        this.displayViews = (0xFFFFFFBF & this.displayViews);
      }
    }
  }

  private boolean checkPlayInfoData()
  {
    if (this.pInfo.state == 1)
    {
      Logger.i("MplayerTimeshiftView", "checkPlayInfoData() 没有授权");
      if (this.apiTaskControl.checkSameTask("APIGetUserAuth", this.pParams.nns_videoInfo.videoId))
      {
        Logger.i("MplayerTimeshiftView", "checkPlayInfoData() 没有授权 waite APIGetUserAuth return");
        this.isStartToCheckBuffering = false;
        return false;
      }
      PurchaseParam localPurchaseParam = new PurchaseParam(true, this.pParams.nns_videoInfo.videoId, "1");
      localPurchaseParam.setVideoName(this.pParams.nns_videoInfo.name);
      localPurchaseParam.setPackageId(this.package_id);
      localPurchaseParam.setCategoryId(this.pParams.nns_videoInfo.categoryId);
      localPurchaseParam.def = this.pParams.mediaQuality;
      this.tokenDialog.setPurchaseInfo(localPurchaseParam);
      if (checkErrorNoticeViewCanDisplay())
      {
        hideCenterViews();
        showTokenDialog(20003, false);
        return false;
      }
      showTokenDialog(20003, false);
      return false;
    }
    if (this.pInfo.state == 2)
    {
      Logger.i("MplayerTimeshiftView", "checkPlayInfoData() 数据异常");
      if (checkErrorNoticeViewCanDisplay())
      {
        hideCenterViews();
        showTokenDialog(20004, false);
        return false;
      }
      showTokenDialog(20004, false);
      return false;
    }
    if (this.pInfo.state == 3)
    {
      Logger.i("MplayerTimeshiftView", "checkPlayInfoData() 内容未到播放时间");
      if (checkErrorNoticeViewCanDisplay())
        hideCenterViews();
      showTokenDialog(20005, false);
      return false;
    }
    if (this.pInfo.state == 5)
    {
      Logger.i("MplayerTimeshiftView", "checkPlayInfoData() 内容已过期");
      if (checkErrorNoticeViewCanDisplay())
        hideCenterViews();
      showTokenDialog(20006, false);
      return false;
    }
    if (this.pInfo.state == 9)
    {
      Logger.e("MplayerTimeshiftView", "SccmsApiRequestVideoPlayTaskListener.onSuccess() Token失效");
      return false;
    }
    if ((this.pInfo.state != 0) && (this.pInfo.state != 6))
    {
      Logger.i("MplayerTimeshiftView", "checkPlayInfoData() 其他错误");
      if (checkErrorNoticeViewCanDisplay())
        hideCenterViews();
      showTokenDialog(20007, false);
      return false;
    }
    if ((TextUtils.isEmpty(this.pInfo.playUrl)) || ("null".equalsIgnoreCase(this.pInfo.playUrl)))
    {
      Logger.i("MplayerTimeshiftView", "checkPlayInfoData() 播放地址空");
      if (checkErrorNoticeViewCanDisplay())
        hideCenterViews();
      showTokenDialog(20007, false);
      return false;
    }
    return true;
  }

  private void checkPlaySuccessed()
  {
    if ((this.isPlayerSucceed) && (!this.isNotifiedPlaySuccess))
    {
      this.lsnr.onPreLoadingViewDisplay(4, "");
      this.isNotifiedPlaySuccess = true;
      reportPlayerState(3);
      reportCDNIF1AccessCacheSuccess(this.playUrl);
      startCDNTimerReport();
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

  private void dealChannelListDataReturn(ChannelInfoV2 paramChannelInfoV2)
  {
    if ((paramChannelInfoV2 == null) || (paramChannelInfoV2.channelList == null) || (paramChannelInfoV2.channelList.size() <= 0))
    {
      channelListDataNullException();
      return;
    }
    Logger.i("MplayerTimeshiftView", "debug SccmsApiGetChannelListV2TaskListener.onSuccess() result:" + paramChannelInfoV2.toString());
    this.channelListDataV2 = new ChannelInfoV2();
    this.channelListDataV2.channelList = new ArrayList();
    HashMap localHashMap = new HashMap();
    int i = 0;
    int j = 0;
    if (j < paramChannelInfoV2.channelList.size())
    {
      ChannelItemInfoV2 localChannelItemInfoV23 = (ChannelItemInfoV2)paramChannelInfoV2.channelList.get(j);
      Logger.i("MplayerTimeshiftView", "dealChannelListDataReturn() idx:" + j + ", item:" + localChannelItemInfoV23.toString());
      if (getCategoryNameById(localChannelItemInfoV23.categoryId) == null);
      while (true)
      {
        j++;
        break;
        this.channelListDataV2.channelList.add(localChannelItemInfoV23);
        localHashMap.put(localChannelItemInfoV23.id, Integer.valueOf(i));
        i++;
      }
    }
    List localList1 = getPlayedHistoryRecords(localHashMap);
    List localList2 = getLastPlayedHistoryRecords(localHashMap);
    this.commonChannels = getCommonChannel(localList1, localHashMap);
    if (this.commonChannels.size() > 0)
      this.channelListView.addCommonChannel(this.commonChannels);
    int k = -1;
    boolean bool = false;
    if (localList2 != null)
    {
      int n = localList2.size();
      bool = false;
      if (n > 0)
      {
        Logger.i("MplayerTimeshiftView", "dealChannelListDataReturn() lastPlayedVideoId:" + ((UserCPLLogic.TimeshiftPlayRecord)localList2.get(0)).videoId + ", lastPlayedVideoCategoryId:" + ((UserCPLLogic.TimeshiftPlayRecord)localList2.get(0)).categoryId + ",lastPlayedHistoryRecords.isFromCommonChannel:" + ((UserCPLLogic.TimeshiftPlayRecord)localList2.get(0)).isFromCommonChannel);
        bool = ((UserCPLLogic.TimeshiftPlayRecord)localList2.get(0)).isFromCommonChannel;
        this.channelSelectedIndex = getLastPlayedHistoryRecordsIndex((UserCPLLogic.TimeshiftPlayRecord)localList2.get(0), this.channelListDataV2.channelList);
        if (!bool)
          break label917;
        k = getLastPlayedHistoryRecordsIndex((UserCPLLogic.TimeshiftPlayRecord)localList2.get(0), this.commonChannels);
      }
    }
    while (true)
    {
      this.channelListView.bindData(this.mediaAssetsInfo, this.channelListDataV2);
      if (k < 0)
        k = 0;
      if (this.channelSelectedIndex < 0)
        this.channelSelectedIndex = 0;
      int m = 0;
      label473: ChannelItemInfoV2 localChannelItemInfoV21;
      if (m < this.channelListDataV2.channelList.size())
      {
        ChannelItemInfoV2 localChannelItemInfoV22 = (ChannelItemInfoV2)this.channelListDataV2.channelList.get(m);
        if ((localChannelItemInfoV22.id != null) && (localChannelItemInfoV22.id.equals(this.currentPlayChannel)))
        {
          this.channelSelectedIndex = m;
          k = this.channelSelectedIndex;
          bool = false;
        }
      }
      else
      {
        this.channelListView.setSelectedItem(k, bool);
        refreshChannelIndexRange();
        cancelTasks();
        if (!bool)
          break label932;
        localChannelItemInfoV21 = (ChannelItemInfoV2)this.commonChannels.get(k);
        setBulletScreenShowLogic(localChannelItemInfoV21);
        this.bulletScreenValue = localChannelItemInfoV21.bullet_screen;
      }
      try
      {
        this.pParams.nns_index = 0;
        if (this.pParams.nns_videoInfo == null)
        {
          this.pParams.nns_videoInfo = new VideoInfo();
          this.pParams.nns_videoInfo.packageId = "TimeShift";
        }
        this.pParams.played_time = 0L;
        this.pParams.nns_videoInfo.videoId = localChannelItemInfoV21.id;
        this.pParams.nns_videoInfo.categoryId = localChannelItemInfoV21.categoryId;
        this.pParams.nns_videoInfo.name = localChannelItemInfoV21.name;
        this.pParams.nns_videoInfo.film_name = localChannelItemInfoV21.name;
        this.pParams.nns_videoInfo.videoType = 1;
        this.pParams.channel_index = String.valueOf(localChannelItemInfoV21.channelNum);
        this.pParams.mode = 6;
        this.curChannelName = localChannelItemInfoV21.name;
        this.curChannelNum = makeIdxString(localChannelItemInfoV21.channelNum);
        this.pParams.real_min_back_time_len = localChannelItemInfoV21.tsLimitMin;
        this.pParams.real_max_back_time_len = localChannelItemInfoV21.tsLimitMax;
        this.pParams.real_default_back_pos = localChannelItemInfoV21.tsLimitPos;
        initChannelInfo(localChannelItemInfoV21);
        onChangePlayUrlEvent();
        addPlayAuthTask(this.pParams.nns_videoInfo.videoId);
        this.currentPlayChannel = this.pParams.nns_videoInfo.videoId;
        this.curChannelNum = makeIdxString(localChannelItemInfoV21.channelNum);
        this.curChannelName = localChannelItemInfoV21.name;
        if ((this.channelListView.getItemCount() > 0) && ((0x8 & this.displayViews) == 0))
        {
          setChannelListVisibility(0);
          this.displayViews = (0x8 | this.displayViews);
        }
        this.channelListView.requestFocus();
        this.channelListView.invalidate();
        return;
        label917: k = this.channelSelectedIndex;
        continue;
        m++;
        break label473;
        label932: localChannelItemInfoV21 = (ChannelItemInfoV2)this.channelListDataV2.channelList.get(k);
      }
      catch (Exception localException)
      {
        while (true)
          localException.printStackTrace();
      }
    }
  }

  private void delayToStartVideo()
  {
    Logger.i("MplayerTimeshiftView", "delayToStartVideo()");
    stopToPlay();
    if ((0x200 & this.displayViews) > 0)
    {
      this.lsnr.onOrderNoticeViewDisplay(4, false);
      this.displayViews = (0xFFFFFDFF & this.displayViews);
    }
    if ((0x10 & this.displayViews) > 0)
    {
      this.lsnr.onErrorNoticeViewDisplay(4, "", "", false);
      this.displayViews = (0xFFFFFFEF & this.displayViews);
    }
    displayLoadingInfo(0);
    cancelTasks();
    onEventStopPlayer();
    this.channelListView.setSelectedItem(this.channelSelectedIndex, false);
    ChannelItemInfoV2 localChannelItemInfoV2 = (ChannelItemInfoV2)this.channelListDataV2.channelList.get(this.channelSelectedIndex);
    refreshChannelIndexRange();
    Logger.i("MplayerTimeshiftView", "delayToStartVideo() currentPlayCategory:" + this.currentPlayCategory + ", channelSelectedIndex:" + this.channelSelectedIndex + ", itemData:" + localChannelItemInfoV2.toString());
    if (tryToDirectlyPlay())
      addPlaybillRequestTask(localChannelItemInfoV2.id);
    while (true)
    {
      if (this.timeNodeData != null);
      synchronized (this.timeNodeData)
      {
        this.timeNodeData.clear();
        this.timeNodeDiscrib.clear();
        return;
        try
        {
          this.pParams.nns_index = 0;
          if (this.pParams.nns_videoInfo == null)
          {
            this.pParams.nns_videoInfo = new VideoInfo();
            this.pParams.nns_videoInfo.packageId = "TimeShift";
          }
          this.pParams.played_time = 0L;
          this.pParams.nns_videoInfo.videoId = localChannelItemInfoV2.id;
          this.pParams.nns_videoInfo.categoryId = localChannelItemInfoV2.categoryId;
          this.pParams.nns_videoInfo.name = localChannelItemInfoV2.name;
          this.pParams.nns_videoInfo.film_name = localChannelItemInfoV2.name;
          this.pParams.nns_videoInfo.videoType = 1;
          this.pParams.channel_index = String.valueOf(localChannelItemInfoV2.channelNum);
          this.pParams.mode = 6;
          this.pParams.real_min_back_time_len = localChannelItemInfoV2.tsLimitMin;
          this.pParams.real_max_back_time_len = localChannelItemInfoV2.tsLimitMax;
          this.pParams.real_default_back_pos = localChannelItemInfoV2.tsLimitPos;
          this.currentPlayChannel = this.pParams.nns_videoInfo.videoId;
          addPlaybillRequestTask(localChannelItemInfoV2.id);
          addPlayAuthTask(localChannelItemInfoV2.id);
        }
        catch (Exception localException)
        {
          while (true)
            localException.printStackTrace();
        }
      }
    }
  }

  private void directlyPlay()
  {
    if (this.mdc != null)
      this.mdc.onAddVideotaskToPrePare(this.context, this.pParams.nns_videoInfo.videoId, getChannelNameForDataCollect(), "-1", getChannelNameForDataCollect(), this.pParams.mode);
    initPlayParams();
    this.mdc.startCollector();
    this.playUrl = this.pInfo.playUrl;
    if ((this.isFirstLoading) && (!this.isBuffering))
    {
      this.mdc.onPlayerCreate(this.playUrl);
      this.isBuffering = true;
      this.mdc.onPlayerBufferBegin(BufferEnum.BUFFER_AUTH, this.tstvBeginTime + this.currentPlayPos);
    }
    this.mpCore.setVideoURI(formatUrl(this.playUrl));
    Logger.i("MplayerTimeshiftView", "debug directlyPlay() playUrl:" + this.playUrl);
  }

  private void displayLoadingInfo(int paramInt)
  {
    if (paramInt == 0)
    {
      if ((this.playState.getVisibility() == 0) || ((0x10 & this.displayViews) > 0) || ((0x40 & this.displayViews) > 0) || ((0x100 & this.displayViews) > 0) || ((0x8 & this.displayViews) > 0) || ((0x200 & this.displayViews) > 0) || ((0x20 & this.displayViews) > 0) || (this.playStatusFlag == 2))
      {
        if ((0x2 & this.displayViews) == 0)
          return;
        this.lsnr.onLoadingViewDisplay(4);
        this.displayViews = (0xFFFFFFFD & this.displayViews);
        return;
      }
      this.lsnr.onLoadingViewDisplay(0);
      this.displayViews = (0x2 | this.displayViews);
      return;
    }
    this.lsnr.onLoadingViewDisplay(4);
    this.displayViews = (0xFFFFFFFD & this.displayViews);
  }

  private void doPauseVideo()
  {
    Logger.i("MplayerTimeshiftView", "doPauseVideo()");
    this.mBarrageView.pause();
    stopGetBarrageTask();
    this.isStartToCheckBuffering = false;
    displayLoadingInfo(4);
    this.mpCore.pause();
    this.playStatusFlag = 2;
    this.mdc.onPlayerPause(this.tstvBeginTime + this.currentPlayPos);
    this.seekbar.updatePlayStatus(this.playStatusFlag);
    setBulletScreenQrCodeStatusByPause(0);
    this.airPlayState = "in_pause";
    if (this.onPlayerControllEventCallback != null)
      this.onPlayerControllEventCallback.onPause();
  }

  private void doQuitPlay()
  {
    Logger.i("MplayerTimeshiftView", "doQuitPlay()");
    stopToPlay();
    this.isEventStopPlayer = true;
    onEventStopPlayer();
    if (!this.isReportStop)
    {
      reportPlayerState(8);
      this.isReportStop = true;
    }
    finishPlayerAndQuit();
  }

  private void doStartVideo()
  {
    Logger.i("MplayerTimeshiftView", "doStartVideo()");
    this.isStartToCheckBuffering = true;
    if (this.currentPlayPos + this.tstvBeginTime - 5000L < getServerTime() - 1000L * this.pParams.real_max_back_time_len)
    {
      ApplicationException localApplicationException = new ApplicationException(this.context, "1003301");
      localApplicationException.setShowDialog(true);
      localApplicationException.start();
      this.tstvBeginTime = (getServerTime() - 1000L * Math.max(this.pParams.real_default_back_pos, this.pParams.real_min_back_time_len));
      reportPlayerState(8);
      this.mplayerRetryLogic.initRetryParams();
      onChangePlayUrlEvent();
      addPlayRequestTask(this.tstvBeginTime);
    }
    this.mdc.onPlayerStart(this.tstvBeginTime + this.currentPlayPos);
    this.mpCore.start();
    this.playStatusFlag = 1;
    this.seekbar.updatePlayStatus(this.playStatusFlag);
    setBulletScreenQrCodeStatusByPause(8);
    this.airPlayState = "in_play";
    if (this.onPlayerControllEventCallback != null)
      this.onPlayerControllEventCallback.onPlay();
    this.mBarrageView.resume();
    if (this.isBarrageOpen)
      startGetBarrageTask();
  }

  private ChannelItemInfoV2 findChannelByNum(int paramInt, String paramString)
  {
    for (int i = 0; ; i++)
      if ((i >= this.channelListDataV2.channelList.size()) || (((ChannelItemInfoV2)this.channelListDataV2.channelList.get(i)).channelNum == paramInt))
      {
        if (i >= this.channelListDataV2.channelList.size())
          break;
        this.channelSelectedIndex = i;
        return (ChannelItemInfoV2)this.channelListDataV2.channelList.get(this.channelSelectedIndex);
      }
    return null;
  }

  private ChannelItemInfoV2 findChannelByVideoId(String paramString)
  {
    Logger.i("MplayerTimeshiftView", "findChannelByVideoId() videoId:" + paramString);
    ChannelItemInfoV2 localChannelItemInfoV2;
    if ((this.channelListDataV2 == null) || (this.channelListDataV2.channelList == null) || (TextUtils.isEmpty(paramString)))
    {
      localChannelItemInfoV2 = null;
      return localChannelItemInfoV2;
    }
    for (int i = 0; ; i++)
    {
      if (i >= this.channelListDataV2.channelList.size())
        break label101;
      localChannelItemInfoV2 = (ChannelItemInfoV2)this.channelListDataV2.channelList.get(i);
      if (paramString.equals(localChannelItemInfoV2.id))
        break;
    }
    label101: return null;
  }

  private int findChannelIndex(ChannelItemInfoV2 paramChannelItemInfoV2)
  {
    int i;
    if (this.channelListDataV2 != null)
    {
      ArrayList localArrayList = this.channelListDataV2.channelList;
      i = 0;
      if (localArrayList != null);
    }
    else
    {
      i = -1;
    }
    for (int j = 0; ; j++)
      if (j < this.channelListDataV2.channelList.size())
      {
        ChannelItemInfoV2 localChannelItemInfoV2 = (ChannelItemInfoV2)this.channelListDataV2.channelList.get(j);
        if ((paramChannelItemInfoV2.categoryId.equals(localChannelItemInfoV2.categoryId)) && (paramChannelItemInfoV2.id.equals(localChannelItemInfoV2.id)))
          i = j;
      }
      else
      {
        if (i < 0)
          i = 0;
        return i;
      }
  }

  private String findCurrentProgramByPlayPos(long paramLong)
  {
    if ((this.timeNodeData == null) || (this.timeNodeDiscrib == null) || (this.timeNodeData.size() == 0) || (this.timeNodeDiscrib.size() == 0))
      return "";
    List localList = this.timeNodeData;
    int i = 0;
    while (true)
    {
      try
      {
        if (i >= this.timeNodeData.size())
          break label125;
        if (paramLong < ((Long)this.timeNodeData.get(i)).longValue())
        {
          break label125;
          String str = (String)this.timeNodeDiscrib.get(i);
          return str;
        }
      }
      finally
      {
      }
      i++;
      continue;
      label125: if (i > 0)
        i--;
    }
  }

  private ChannelItemInfoV2 findNextChannel()
  {
    Logger.i("MplayerTimeshiftView", "findNextChannel() minChannelIndexInCurrentCategory:" + this.minChannelIndexInCurrentCategory + ", maxChannelIndexInCurrentCategory:" + this.maxChannelIndexInCurrentCategory);
    if ((this.channelListDataV2 == null) || (this.channelListDataV2.channelList == null) || (this.channelListDataV2.channelList.size() <= 1));
    int i;
    do
    {
      return null;
      i = 1 + this.channelSelectedIndex;
      if (i > this.maxChannelIndexInCurrentCategory)
        i = this.minChannelIndexInCurrentCategory;
    }
    while ((i < 0) || (i >= this.channelListDataV2.channelList.size()));
    this.channelSelectedIndex = i;
    this.channelListView.setSelectedItem(this.channelSelectedIndex, false);
    Logger.i("MplayerTimeshiftView", "findNextChannel() channelSelectedIndex:" + this.channelSelectedIndex);
    return (ChannelItemInfoV2)this.channelListDataV2.channelList.get(this.channelSelectedIndex);
  }

  private ChannelItemInfoV2 findPlayDataByCategoryIdAndchannelId(String paramString1, String paramString2)
  {
    ChannelItemInfoV2 localChannelItemInfoV2;
    if ((this.mediaAssetsInfo == null) || (this.mediaAssetsInfo.cList == null) || (this.mediaAssetsInfo.cList.size() <= 0) || (this.channelListDataV2 == null) || (this.channelListDataV2.channelList == null) || (this.channelListDataV2.channelList.size() <= 0))
    {
      localChannelItemInfoV2 = null;
      return localChannelItemInfoV2;
    }
    int i = this.channelListDataV2.channelList.size();
    for (int j = 0; ; j++)
    {
      if (j >= i)
        break label144;
      localChannelItemInfoV2 = (ChannelItemInfoV2)this.channelListDataV2.channelList.get(j);
      if ((localChannelItemInfoV2.categoryId != null) && (localChannelItemInfoV2.categoryId.equals(paramString1)) && (localChannelItemInfoV2.id != null) && (localChannelItemInfoV2.id.equals(paramString2)))
        break;
    }
    label144: return null;
  }

  private ChannelItemInfoV2 findPrevChannel()
  {
    Logger.i("MplayerTimeshiftView", "findPrevChannel() minChannelIndexInCurrentCategory:" + this.minChannelIndexInCurrentCategory + ", maxChannelIndexInCurrentCategory:" + this.maxChannelIndexInCurrentCategory);
    if ((this.channelListDataV2 == null) || (this.channelListDataV2.channelList == null) || (this.channelListDataV2.channelList.size() <= 1));
    int i;
    do
    {
      return null;
      i = -1 + this.channelSelectedIndex;
      if (i < this.minChannelIndexInCurrentCategory)
        i = this.maxChannelIndexInCurrentCategory;
    }
    while ((i < 0) || (i >= this.channelListDataV2.channelList.size()));
    this.channelSelectedIndex = i;
    this.channelListView.setSelectedItem(this.channelSelectedIndex, false);
    Logger.i("MplayerTimeshiftView", "findPrevChannel() channelSelectedIndex:" + this.channelSelectedIndex);
    return (ChannelItemInfoV2)this.channelListDataV2.channelList.get(this.channelSelectedIndex);
  }

  private void finishPlayerAndQuit()
  {
    if (this.context == null)
    {
      Logger.i("MplayerTimeshiftView", "finishPlayerAndQuit() Context is null");
      return;
    }
    hideAllViews();
    ((Activity)this.context).finish();
  }

  private String formatUrl(String paramString)
  {
    if (DeviceInfo.isHMD());
    try
    {
      URI localURI = URI.create(paramString);
      List localList = URLEncodedUtils.parse(localURI, "utf-8");
      ArrayList localArrayList = new ArrayList();
      localArrayList.addAll(localList);
      localArrayList.add(new BasicNameValuePair("hmd_video_duration", "-1"));
      paramString = new URI(localURI.getScheme(), localURI.getUserInfo(), localURI.getHost(), localURI.getPort(), localURI.getPath(), URLEncodedUtils.format(localArrayList, "utf-8"), localURI.getFragment()).toString();
      Logger.i("MplayerTimeshiftView", "build HMD url:" + paramString);
      if (isEnableM3U8())
        paramString = paramString.replace(".ts", ".m3u8");
      return paramString;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  private String formateTime(long paramLong)
  {
    return new SimpleDateFormat("HH:mm:ss").format(new Date(paramLong));
  }

  private static String genProgramDesc(PlayBillItem paramPlayBillItem)
  {
    if ((paramPlayBillItem == null) || (paramPlayBillItem.desc == null))
      return "无";
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(paramPlayBillItem.desc);
    if (!TextUtils.isEmpty(paramPlayBillItem.begin));
    try
    {
      SimpleDateFormat localSimpleDateFormat1 = new SimpleDateFormat("HHmmss");
      SimpleDateFormat localSimpleDateFormat2 = new SimpleDateFormat("HH:mm");
      Date localDate1 = localSimpleDateFormat1.parse(paramPlayBillItem.begin);
      Date localDate2 = new Date(localDate1.getTime() + 1000 * paramPlayBillItem.timeLen);
      localStringBuffer.append("  ");
      localStringBuffer.append(localSimpleDateFormat2.format(localDate1));
      localStringBuffer.append(" - ");
      localStringBuffer.append(localSimpleDateFormat2.format(localDate2));
      return localStringBuffer.toString();
    }
    catch (ParseException localParseException)
    {
      while (true)
        localParseException.printStackTrace();
    }
  }

  private void getBarrageData()
  {
    String str1 = this.import_id;
    String str2 = this.barrageCategory;
    Logger.d("MplayerTimeshiftView", "getBarrageData channelId: " + str1 + ", category: " + str2);
    if (TextUtils.isEmpty(str1))
    {
      Logger.e("MplayerTimeshiftView", "getBarrageData channelId empty");
      return;
    }
    this.getBarrageTaskId = ServerApiManager.i().APIGetBarrageData(str1, str2, this.curTimeKey, new MgtvApiGetBarrageDataTask.IGetBarrageDataTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        if (paramAnonymousServerApiTaskInfo.getTaskId() != MplayerTimeshiftView.this.getBarrageTaskId)
        {
          Logger.w("MplayerTimeshiftView", "APIGetBarrageData invalid task");
          return;
        }
        if (!MplayerTimeshiftView.this.isGetBarrageTaskRunning)
        {
          Logger.w("MplayerTimeshiftView", "弹幕任务已经停止，不继续运行");
          return;
        }
        if (((MplayerTimeshiftView.this.context instanceof Activity)) && (((Activity)MplayerTimeshiftView.this.context).isFinishing()))
        {
          Logger.w("MplayerTimeshiftView", "播放器准备销毁，不继续运行弹幕任务");
          return;
        }
        Logger.e("MplayerTimeshiftView", "APIGetBarrageData onError: " + paramAnonymousServerApiCommonError.toString());
        MplayerTimeshiftView.access$2802(MplayerTimeshiftView.this, 5000);
        MplayerTimeshiftView.this.startGetBarrage();
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, BarrageResponse paramAnonymousBarrageResponse)
      {
        if (paramAnonymousServerApiTaskInfo.getTaskId() != MplayerTimeshiftView.this.getBarrageTaskId)
        {
          Logger.w("MplayerTimeshiftView", "APIGetBarrageData invalid task");
          return;
        }
        if (!MplayerTimeshiftView.this.isGetBarrageTaskRunning)
        {
          Logger.w("MplayerTimeshiftView", "弹幕任务已经停止，不继续运行");
          return;
        }
        if (((MplayerTimeshiftView.this.context instanceof Activity)) && (((Activity)MplayerTimeshiftView.this.context).isFinishing()))
        {
          Logger.w("MplayerTimeshiftView", "播放器准备销毁，不继续运行弹幕任务");
          return;
        }
        Logger.d("MplayerTimeshiftView", "APIGetBarrageData onSuccess");
        MplayerTimeshiftView.this.processBarrage(paramAnonymousBarrageResponse);
      }
    });
  }

  private String getCDNErrorCode(ServerApiCommonError paramServerApiCommonError)
  {
    return ReportUtil.parseCDNErrorCode(paramServerApiCommonError);
  }

  private String getCaps()
  {
    if ((this.channelListDataV2 == null) || (this.channelListDataV2.channelList == null) || (this.channelListDataV2.channelList.size() == 0))
      return "";
    try
    {
      localChannelItemInfoV2 = (ChannelItemInfoV2)this.channelListDataV2.channelList.get(this.channelSelectedIndex);
      if ((localChannelItemInfoV2 == null) || (localChannelItemInfoV2.capability == null))
        return "";
      if (localChannelItemInfoV2.capability.contains("LIVE"))
      {
        Logger.i("MplayerTimeshiftView", "tryToDirectlyPlay() cinfo.capability has no LIVE");
        return "LIVE";
      }
      if (localChannelItemInfoV2.capability.contains("TSTV"))
        return "TSTV";
      return "";
    }
    catch (Exception localException)
    {
      while (true)
        ChannelItemInfoV2 localChannelItemInfoV2 = null;
    }
  }

  private String getCategoryIdByName(String paramString)
  {
    MediaAssetsInfo localMediaAssetsInfo = GlobalLogic.getInstance().getTimeshiftAssetsInfo();
    if ((paramString == null) || (localMediaAssetsInfo == null) || (localMediaAssetsInfo.cList == null));
    while (true)
    {
      return null;
      for (int i = 0; i < localMediaAssetsInfo.cList.size(); i++)
      {
        CategoryItem localCategoryItem = (CategoryItem)localMediaAssetsInfo.cList.get(i);
        if (paramString.equals(localCategoryItem.name))
          return localCategoryItem.id;
      }
    }
  }

  private String getCategoryNameById(String paramString)
  {
    MediaAssetsInfo localMediaAssetsInfo = GlobalLogic.getInstance().getTimeshiftAssetsInfo();
    if ((paramString == null) || (localMediaAssetsInfo == null) || (localMediaAssetsInfo.cList == null));
    while (true)
    {
      return null;
      for (int i = 0; i < localMediaAssetsInfo.cList.size(); i++)
      {
        CategoryItem localCategoryItem = (CategoryItem)localMediaAssetsInfo.cList.get(i);
        if (paramString.equals(localCategoryItem.id))
          return localCategoryItem.name;
      }
    }
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
    Logger.i("MplayerTimeshiftView", "getChannelIdFromUrl:" + paramString);
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
    return this.pParams.nns_videoInfo.name;
  }

  private ArrayList<ChannelItemInfoV2> getCommonChannel(List<UserCPLLogic.TimeshiftPlayRecord> paramList, HashMap<String, Integer> paramHashMap)
  {
    ArrayList localArrayList = new ArrayList();
    if (paramList != null)
    {
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        UserCPLLogic.TimeshiftPlayRecord localTimeshiftPlayRecord = (UserCPLLogic.TimeshiftPlayRecord)localIterator.next();
        if (localTimeshiftPlayRecord.totalPlayedTime < 1800L)
        {
          Logger.e("MplayerTimeshiftView", "时移频道记录：跳过总播放时长小于30分的视频，videoId:" + localTimeshiftPlayRecord.videoId);
        }
        else if (paramHashMap.containsKey(localTimeshiftPlayRecord.videoId))
        {
          int i = ((Integer)paramHashMap.get(localTimeshiftPlayRecord.videoId)).intValue();
          localArrayList.add((ChannelItemInfoV2)this.channelListDataV2.channelList.get(i));
        }
        else
        {
          Logger.e("MplayerTimeshiftView", "时移频道记录错误！！ 未知的videoId:" + localTimeshiftPlayRecord.videoId);
        }
      }
    }
    return localArrayList;
  }

  private String getCurPageName()
  {
    if ((this.context != null) && ((this.context instanceof DialogActivity)))
      return ((DialogActivity)this.context).curPageInfo.page;
    return "";
  }

  private List<UserCPLLogic.TimeshiftPlayRecord> getLastPlayedHistoryRecords(final HashMap<String, Integer> paramHashMap)
  {
    return UserCPLLogic.getInstance().getTopTimeshiftPlayHistoryRecords(1, new Comparator()
    {
      public int compare(UserCPLLogic.TimeshiftPlayRecord paramAnonymousTimeshiftPlayRecord1, UserCPLLogic.TimeshiftPlayRecord paramAnonymousTimeshiftPlayRecord2)
      {
        boolean bool = paramHashMap.containsKey(paramAnonymousTimeshiftPlayRecord1.videoId);
        if (bool == paramHashMap.containsKey(paramAnonymousTimeshiftPlayRecord2.videoId))
        {
          long l = paramAnonymousTimeshiftPlayRecord2.lastPlayTime.getTime() - paramAnonymousTimeshiftPlayRecord1.lastPlayTime.getTime();
          if (Math.abs(l) >= 2147483647L)
            return (int)(l / 2147483647L);
          return (int)l;
        }
        if (bool)
          return -1;
        return 1;
      }
    });
  }

  private int getLastPlayedHistoryRecordsIndex(UserCPLLogic.TimeshiftPlayRecord paramTimeshiftPlayRecord, ArrayList<ChannelItemInfoV2> paramArrayList)
  {
    String str1 = paramTimeshiftPlayRecord.videoId;
    String str2 = paramTimeshiftPlayRecord.categoryId;
    int i = -1;
    for (int j = 0; j < paramArrayList.size(); j++)
    {
      ChannelItemInfoV2 localChannelItemInfoV2 = (ChannelItemInfoV2)paramArrayList.get(j);
      if ((str1.equals(localChannelItemInfoV2.id)) && (str2 != null) && (localChannelItemInfoV2.categoryId.equals(str2)))
        i = j;
    }
    return i;
  }

  private List<UserCPLLogic.TimeshiftPlayRecord> getPlayedHistoryRecords(final HashMap<String, Integer> paramHashMap)
  {
    return UserCPLLogic.getInstance().getTopTimeshiftPlayHistoryRecords(10, new Comparator()
    {
      public int compare(UserCPLLogic.TimeshiftPlayRecord paramAnonymousTimeshiftPlayRecord1, UserCPLLogic.TimeshiftPlayRecord paramAnonymousTimeshiftPlayRecord2)
      {
        boolean bool = paramHashMap.containsKey(paramAnonymousTimeshiftPlayRecord1.videoId);
        if (bool == paramHashMap.containsKey(paramAnonymousTimeshiftPlayRecord2.videoId))
        {
          long l = paramAnonymousTimeshiftPlayRecord2.totalPlayedTime - paramAnonymousTimeshiftPlayRecord1.totalPlayedTime;
          if (Math.abs(l) >= 2147483647L)
            return (int)(l / 2147483647L);
          return (int)l;
        }
        if (bool)
          return -1;
        return 1;
      }
    });
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

  private long getServerTime()
  {
    return SystemTimeManager.getCurrentServerTime();
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
        localBuilder.addTpt(1);
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
        localBuilder.addStreamid(getChannelIdFromUrl(this.playUrl));
        localBuilder.addLn(this.pParams.nns_videoInfo.name);
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
    if (!this.isFullPlayMode);
    do
    {
      return;
      if ((0x40 & this.displayViews) > 0)
      {
        setMenuViewVisibility(4);
        this.displayViews = (0xFFFFFFBF & this.displayViews);
      }
      if ((0x8 & this.displayViews) > 0)
      {
        setChannelListVisibility(4);
        this.displayViews = (0xFFFFFFF7 & this.displayViews);
      }
      if ((0x100 & this.displayViews) > 0)
      {
        this.displayViews = (0xFFFFFEFF & this.displayViews);
        this.quitView.setVisibility(8);
      }
      if ((0x2 & this.displayViews) > 0)
      {
        displayLoadingInfo(4);
        this.displayViews = (0xFFFFFFFD & this.displayViews);
      }
    }
    while ((0x400 & this.displayViews) <= 0);
    this.lsnr.onWebDialogDisplay(4, null);
    this.displayViews = (0xFFFFFBFF & this.displayViews);
  }

  private void hideControllPanel()
  {
    if ((0x4 & this.displayViews) > 0)
    {
      this.title.hide();
      this.seekbar.hide();
      if ((0x8000 & this.displayViews) > 0)
        setBulletScreenQrCodeStatusByPause(8);
      this.displayViews = (0xFFFFFFFB & this.displayViews);
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
      this.barrageCategory = paramChannelItemInfoV2.barrageCategory;
      this.cameraposition = paramChannelItemInfoV2.cameraposition;
      this.reportway_type = paramChannelItemInfoV2.reportway_type;
      this.tv_channel = paramChannelItemInfoV2.tv_channel;
      this.activity_id = paramChannelItemInfoV2.activity_id;
      this.type_name = paramChannelItemInfoV2.type_name;
    }
    for (this.live_video_type = paramChannelItemInfoV2.live_video_type; ; this.live_video_type = "")
    {
      Logger.i("MplayerTimeshiftView", "initChannelInfo:import_id=" + this.import_id + ",cameraposition=" + this.cameraposition + ",reportway_type=" + this.reportway_type + ",tv_channel=" + this.tv_channel + ",activity_id=" + this.activity_id + ",type_name=" + this.type_name);
      return;
      this.import_id = "";
      this.barrageCategory = "liveshow";
      this.cameraposition = "";
      this.reportway_type = "";
      this.tv_channel = "";
      this.activity_id = "";
      this.type_name = "";
    }
  }

  private void initData(PlayerIntentParams paramPlayerIntentParams)
  {
    if (paramPlayerIntentParams == null)
      return;
    Logger.i("MplayerTimeshiftView", "initData() playerIntentParams:" + paramPlayerIntentParams.toString());
    final PlayerIntentParams localPlayerIntentParams;
    int i;
    if (paramPlayerIntentParams.nns_videoInfo != null)
      if ((this.pParams == null) || (!paramPlayerIntentParams.nns_videoInfo.packageId.equals(this.pParams.nns_videoInfo.packageId)))
      {
        localPlayerIntentParams = (PlayerIntentParams)paramPlayerIntentParams.clone();
        i = this.channelListView.findItem(new Comparable()
        {
          public int compareTo(Object paramAnonymousObject)
          {
            if (((ChannelItemInfoV2)paramAnonymousObject).id.equals(localPlayerIntentParams.nns_videoInfo.videoId))
              return 0;
            return -1;
          }
        });
        Logger.i("MplayerTimeshiftView", "initData() itemIdx:" + i);
        if (i < 0)
        {
          this.pParams = localPlayerIntentParams;
          this.channelListView.removeAllItems();
        }
      }
    while (true)
    {
      this.currentPlayChannel = this.pParams.nns_videoInfo.videoId;
      Logger.i("MplayerTimeshiftView", "pParams:" + this.pParams.nns_videoInfo.toString());
      String str1 = this.pParams.nns_videoInfo.packageId;
      if (TextUtils.isEmpty(str1))
      {
        str1 = DialogActivity.getMediaAssetId("m_open_tstv_page");
        this.pParams.nns_videoInfo.packageId = str1;
      }
      addTaskOfCategoryMediaAsset(str1);
      return;
      if ((this.pParams != null) && (this.pParams.nns_videoInfo != null))
        localPlayerIntentParams.nns_videoInfo.packageId = this.pParams.nns_videoInfo.packageId;
      this.pParams = localPlayerIntentParams;
      this.channelListView.setSelectedItem(i, false);
      continue;
      if ((this.pParams != null) && (this.pParams.nns_videoInfo != null))
      {
        if (paramPlayerIntentParams.nns_videoInfo.videoId == this.pParams.nns_videoInfo.videoId)
          break;
        String str2 = paramPlayerIntentParams.nns_videoInfo.videoId;
        int j = 0;
        if (str2 != null)
          j = 1;
        if ((j & paramPlayerIntentParams.nns_videoInfo.videoId.equals(this.pParams.nns_videoInfo.videoId)) != 0)
          break;
      }
      if ((paramPlayerIntentParams != null) && (paramPlayerIntentParams.nns_videoInfo != null) && (paramPlayerIntentParams.nns_videoInfo.videoId != null) && (!paramPlayerIntentParams.nns_videoInfo.videoId.equals(this.pParams.nns_videoInfo.videoId)))
      {
        this.pParams = ((PlayerIntentParams)paramPlayerIntentParams.clone());
        this.channelListView.setSelectedItem(new Comparable()
        {
          public int compareTo(Object paramAnonymousObject)
          {
            if (((ChannelItemInfoV2)paramAnonymousObject).id.equals(MplayerTimeshiftView.this.pParams.nns_videoInfo.videoId))
              return 0;
            return -1;
          }
        });
        return;
      }
      restartCurrentChannel();
      return;
      this.pParams = ((PlayerIntentParams)paramPlayerIntentParams.clone());
    }
  }

  private void initPlayParams()
  {
    this.bufferPeriodCount = 0;
    this.delayToShowErrorCode = "";
    this.delayToShowErrExMsg = "";
    this.isNeedToNotifyErrorChannelNum = false;
    this.notifyErrorChannelNumTimer = 0;
    this.displayViews = (0xFFFFF7FF & this.displayViews);
    this.notice.setVisibility(4);
    this.notice.setPlayNotice("");
    this.isPlayerComplete = false;
    this.isPlayerSucceed = false;
    this.isNotifiedPlaySuccess = false;
    this.isEventStopPlayer = false;
    this.cdnAccessCMSSuccess = false;
    this.isBuffering = false;
    this.isStartToCheckBuffering = true;
    this.posNoMovingTimes = 0;
    this.posMovingTimes = 0;
    this.currentPlayPos = 0;
    this.lastPlayPos = 0;
    this.pParams.played_time = 0L;
    this.minProgressPos = 0;
    this.maxProgressPos = (1000 * (int)(this.pParams.real_max_back_time_len - this.pParams.real_min_back_time_len));
    this.tstvRealOffset = (1000L * this.pParams.real_min_back_time_len);
    if (this.isEnableFfordAndRwind)
      if (this.tstvSeekTime > 0L)
        this.tstvBeginTime = this.tstvSeekTime;
    while (true)
    {
      Logger.i("MplayerTimeshiftView", "initPlayParams() pParams:" + this.pParams.toString());
      Logger.i("MplayerTimeshiftView", "initPlayParams() minProgressPos:" + this.minProgressPos + ", maxProgressPos:" + this.maxProgressPos + ", tstvRealOffset:" + this.tstvRealOffset + ", tstvBeginTime:" + this.tstvBeginTime + ", getServerTime:" + getServerTime());
      setTitleText();
      this.seekbar.reset();
      this.minStepLen = (this.maxProgressPos / this.seekbar.getMax());
      return;
      this.tstvBeginTime = (getServerTime() - 1000L * Math.max(this.pParams.real_default_back_pos, this.pParams.real_min_back_time_len));
      continue;
      this.tstvBeginTime = getServerTime();
    }
  }

  private void initViews()
  {
    Logger.i("MplayerTimeshiftView", "initViews()");
    this.mBarrageView = new BarrageView(this.context);
    RelativeLayout.LayoutParams localLayoutParams1 = new RelativeLayout.LayoutParams(-1, -1);
    localLayoutParams1.addRule(10);
    addView(this.mBarrageView, localLayoutParams1);
    LayoutInflater.from(this.context).inflate(2130903069, this);
    this.notice = ((MplayerNoticeView)findViewById(2131165301));
    this.notice.initSize(App.OperationHeight(20), App.OperationHeight(20), App.Operation(400.0F), App.Operation(60.0F));
    RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
    localLayoutParams2.addRule(14);
    localLayoutParams2.addRule(12);
    localLayoutParams2.bottomMargin = App.Operation(193.0F);
    this.notice.setLayoutParams(localLayoutParams2);
    this.notice.setVisibility(4);
    this.title = ((MplayerTitleView)findViewById(2131165298));
    this.title.initMenuNoticeText("点", "呼出菜单", "\"菜单键\"");
    this.title.initUIPara(3, App.OperationHeight(5), App.OperationHeight(5));
    RelativeLayout.LayoutParams localLayoutParams3 = new RelativeLayout.LayoutParams(-2, App.OperationHeight(367));
    localLayoutParams3.addRule(14);
    localLayoutParams3.addRule(10);
    this.title.setLayoutParams(localLayoutParams3);
    this.title.setVisibility(4);
    this.seekbar = ((MplayerSeekBar)findViewById(2131165300));
    App.OperationHeight(25);
    int i = App.OperationHeight(720);
    int j = App.OperationHeight(1280);
    this.seekbar.initUIPara(3, j, i);
    RelativeLayout.LayoutParams localLayoutParams4 = new RelativeLayout.LayoutParams(j, i);
    localLayoutParams4.addRule(14);
    localLayoutParams4.addRule(12);
    this.seekbar.setLayoutParams(localLayoutParams4);
    this.seekbar.setVisibility(4);
    this.quitView = new MplayerQuitXulPage(this.context);
    addView(this.quitView, -1, -1);
    this.quitView.setVisibility(8);
    MplayerQuitXulPage localMplayerQuitXulPage = this.quitView;
    MplayerQuitXulPage.OnItemClickListener local6 = new MplayerQuitXulPage.OnItemClickListener()
    {
      public void onNextEpisodeClick()
      {
      }

      public void onQuitClick()
      {
        MplayerTimeshiftView.this.doQuitPlay();
        String str = MplayerTimeshiftView.this.pParams.nns_videoInfo.videoId;
        MplayerTimeshiftView.this.reportPlayerQuitButtonClick(str);
        if ((MplayerTimeshiftView.this.context instanceof MplayerV2))
        {
          MplayerV2 localMplayerV2 = (MplayerV2)MplayerTimeshiftView.this.context;
          if ((!TextUtils.isEmpty(localMplayerV2.isFromOut)) && (!localMplayerV2.isFromWeiXin))
            AppKiller.getInstance().killApp();
        }
      }

      public void onRecommendItemClick(int paramAnonymousInt, Object paramAnonymousObject)
      {
        FilmListItem localFilmListItem = (FilmListItem)paramAnonymousObject;
        Intent localIntent = new Intent(MplayerTimeshiftView.this.getContext(), DetailPageActivity.class);
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
        MplayerTimeshiftView.this.getContext().startActivity(localIntent);
        MplayerTimeshiftView.this.doQuitPlay();
      }
    };
    localMplayerQuitXulPage.setOnItemClickListener(local6);
    TokenDialogListener localTokenDialogListener = new TokenDialogListener();
    this.tokenDialog = new TokenDialog(this.context);
    this.tokenDialog.setListener(localTokenDialogListener);
    this.bulletScreenNoticeView = new ImageView(this.context);
    this.bulletScreenIcons = GlobalLogic.getInstance().getDownLoadImageByType(0);
    int k;
    int m;
    label527: int n;
    if ((this.bulletScreenIcons != null) && (this.bulletScreenIcons.get(0) != null))
    {
      k = ((Drawable)this.bulletScreenIcons.get(0)).getIntrinsicWidth();
      if ((this.bulletScreenIcons == null) || (this.bulletScreenIcons.get(0) == null))
        break label1529;
      m = ((Drawable)this.bulletScreenIcons.get(0)).getIntrinsicHeight();
      RelativeLayout.LayoutParams localLayoutParams5 = new RelativeLayout.LayoutParams(App.Operation(k), App.Operation(m));
      localLayoutParams5.addRule(12, -1);
      localLayoutParams5.addRule(11, -1);
      localLayoutParams5.rightMargin = App.Operation(64.0F);
      localLayoutParams5.bottomMargin = App.Operation(123.0F);
      this.bulletScreenNoticeView.setLayoutParams(localLayoutParams5);
      if (this.bulletScreenIcons != null)
        this.bulletScreenNoticeView.setImageDrawable((Drawable)this.bulletScreenIcons.get(0));
      this.bulletScreenNoticeView.setVisibility(8);
      addView(this.bulletScreenNoticeView, localLayoutParams5);
      this.qrCodeView = new ImageView(this.context);
      this.qrCodeIcons = GlobalLogic.getInstance().getDownLoadImageByType(2);
      if ((this.qrCodeIcons == null) || (this.qrCodeIcons.get(0) == null))
        break label1535;
      n = ((Drawable)this.qrCodeIcons.get(0)).getIntrinsicWidth();
      label699: if ((this.qrCodeIcons == null) || (this.qrCodeIcons.get(0) == null))
        break label1541;
    }
    label1541: for (int i1 = ((Drawable)this.qrCodeIcons.get(0)).getIntrinsicHeight(); ; i1 = 0)
    {
      RelativeLayout.LayoutParams localLayoutParams6 = new RelativeLayout.LayoutParams(App.Operation(n), App.Operation(i1));
      localLayoutParams6.addRule(12, -1);
      localLayoutParams6.addRule(11, -1);
      localLayoutParams6.rightMargin = App.Operation(64.0F);
      localLayoutParams6.bottomMargin = App.Operation(123.0F);
      this.qrCodeView.setLayoutParams(localLayoutParams6);
      if (this.qrCodeIcons != null)
        this.qrCodeView.setImageDrawable((Drawable)this.qrCodeIcons.get(0));
      this.qrCodeView.setVisibility(8);
      addView(this.qrCodeView, localLayoutParams6);
      this.menuView = new MplayerMenuView(this.context);
      MplayerMenuView localMplayerMenuView = this.menuView;
      NewMenuItemOnClickListener localNewMenuItemOnClickListener = new NewMenuItemOnClickListener(null);
      localMplayerMenuView.setOnItemClickListener(localNewMenuItemOnClickListener);
      boolean bool = AppFuncCfg.FUNCTION_CHANGE_SCREEN_RATIO;
      int i2 = 0;
      if (bool)
        i2 = 0x0 | 0x2;
      this.menuView.setItemFlags(i2);
      String str = GlobalLogic.getInstance().getVideoLayoutRatio();
      this.menuView.setItemState(2, str);
      if (this.menuView.getItemFlag() == 0)
        this.title.initMenuNoticeText("", "", "");
      this.menuView.refreshView();
      RelativeLayout.LayoutParams localLayoutParams7 = new RelativeLayout.LayoutParams(-1, -1);
      localLayoutParams7.addRule(15);
      localLayoutParams7.leftMargin = App.Operation(56.0F);
      this.menuView.setLayoutParams(localLayoutParams7);
      addView(this.menuView, localLayoutParams7);
      setMenuViewVisibility(4);
      this.interactiveView = new MplayerInteractiveView(this.context);
      RelativeLayout.LayoutParams localLayoutParams8 = new RelativeLayout.LayoutParams(-1, -1);
      addView(this.interactiveView, localLayoutParams8);
      this.interactiveView.setBackgroundColor(16777215);
      this.interactiveView.setVisibility(4);
      this.interactiveWebView = new MplayerInteractiveWebView(this.context);
      MplayerInteractiveWebView localMplayerInteractiveWebView = this.interactiveWebView;
      MplayerInteractiveWebView.OnCloseWebListener local7 = new MplayerInteractiveWebView.OnCloseWebListener()
      {
        public void onCloseWeb()
        {
          MplayerTimeshiftView.access$4172(MplayerTimeshiftView.this, -8193);
        }
      };
      localMplayerInteractiveWebView.setOnCloseWebListener(local7);
      addView(this.interactiveWebView, localLayoutParams8);
      this.interactiveWebView.setBackgroundColor(718758);
      this.interactiveWebView.setVisibility(4);
      this.channelListView = new MplayerTimeShiftEpisodeView(this.context);
      MplayerTimeShiftEpisodeView localMplayerTimeShiftEpisodeView = this.channelListView;
      MplayerTimeShiftEpisodeView.onChannelItemClickListener local8 = new MplayerTimeShiftEpisodeView.onChannelItemClickListener()
      {
        public void onChannelItemClick(String paramAnonymousString1, String paramAnonymousString2)
        {
          MplayerTimeshiftView.this.refreshChannelIndexRange();
          ChannelItemInfoV2 localChannelItemInfoV2 = MplayerTimeshiftView.this.findPlayDataByCategoryIdAndchannelId(paramAnonymousString1, paramAnonymousString2);
          if (localChannelItemInfoV2 == null)
            return;
          boolean bool2;
          boolean bool1;
          if (!TextUtils.isEmpty(localChannelItemInfoV2.activity_id))
          {
            MplayerTimeshiftView localMplayerTimeshiftView2 = MplayerTimeshiftView.this;
            if (localChannelItemInfoV2.activity_id.equals(MplayerTimeshiftView.this.activity_id))
            {
              bool2 = true;
              MplayerTimeshiftView.access$5002(localMplayerTimeshiftView2, bool2);
            }
          }
          else
          {
            if (!TextUtils.isEmpty(localChannelItemInfoV2.cameraposition))
            {
              MplayerTimeshiftView localMplayerTimeshiftView1 = MplayerTimeshiftView.this;
              if (!localChannelItemInfoV2.cameraposition.equals(MplayerTimeshiftView.this.cameraposition))
                break label708;
              bool1 = true;
              label102: MplayerTimeshiftView.access$5202(localMplayerTimeshiftView1, bool1);
            }
            MplayerTimeshiftView.this.initChannelInfo(localChannelItemInfoV2);
            int i = MplayerTimeshiftView.this.findChannelIndex(localChannelItemInfoV2);
            Logger.i("MplayerTimeshiftView", "onChannelItemClick channelSelectedIndex:" + MplayerTimeshiftView.this.channelSelectedIndex + ", channelIndex:" + i + ", itemData:" + localChannelItemInfoV2.toString());
            if (MplayerTimeshiftView.this.channelSelectedIndex == i)
              break label742;
            MplayerTimeshiftView.this.channelSelectedIndex = i;
            MplayerTimeshiftView.this.stopToPlay();
            MplayerTimeshiftView.this.displayLoadingInfo(0);
            MplayerTimeshiftView.this.onEventStopPlayer();
            if (!MplayerTimeshiftView.this.isReportStop)
            {
              MplayerTimeshiftView.this.reportPlayerState(8);
              MplayerTimeshiftView.access$5702(MplayerTimeshiftView.this, true);
            }
            MplayerTimeshiftView.this.cancelTasks();
            MplayerTimeshiftView.this.setInteractiveVisibility(4);
            MplayerTimeshiftView.access$6102(MplayerTimeshiftView.this, null);
            InteractiveManager.getInstance().unSubScribeChannel();
            MplayerTimeshiftView.this.switchBarrage(false);
            MplayerTimeshiftView.this.setBulletScreenShowLogic(localChannelItemInfoV2);
            MplayerTimeshiftView.access$6302(MplayerTimeshiftView.this, localChannelItemInfoV2.bullet_screen);
          }
          while (true)
          {
            try
            {
              MplayerTimeshiftView.this.pParams.nns_index = 0;
              if (MplayerTimeshiftView.this.pParams.nns_videoInfo == null)
              {
                MplayerTimeshiftView.this.pParams.nns_videoInfo = new VideoInfo();
                MplayerTimeshiftView.this.pParams.nns_videoInfo.packageId = DialogActivity.getMediaAssetId("m_open_tstv_page");
              }
              MplayerTimeshiftView.this.pParams.played_time = 0L;
              MplayerTimeshiftView.this.pParams.nns_videoInfo.videoId = localChannelItemInfoV2.id;
              MplayerTimeshiftView.this.pParams.nns_videoInfo.categoryId = localChannelItemInfoV2.categoryId;
              MplayerTimeshiftView.this.pParams.nns_videoInfo.name = localChannelItemInfoV2.name;
              MplayerTimeshiftView.this.pParams.nns_videoInfo.film_name = localChannelItemInfoV2.name;
              MplayerTimeshiftView.this.pParams.nns_videoInfo.videoType = 1;
              MplayerTimeshiftView.this.pParams.channel_index = String.valueOf(localChannelItemInfoV2.channelNum);
              MplayerTimeshiftView.this.pParams.mode = 6;
              MplayerTimeshiftView.this.pParams.real_default_back_pos = localChannelItemInfoV2.tsLimitPos;
              MplayerTimeshiftView.this.pParams.real_max_back_time_len = localChannelItemInfoV2.tsLimitMax;
              MplayerTimeshiftView.this.pParams.real_min_back_time_len = localChannelItemInfoV2.tsLimitMin;
              MplayerTimeshiftView.access$6402(MplayerTimeshiftView.this, MplayerTimeshiftView.this.pParams.nns_videoInfo.videoId);
              MplayerTimeshiftView.this.mplayerRetryLogic.initRetryParams();
              MplayerTimeshiftView.this.onChangePlayUrlEvent();
              if (!MplayerTimeshiftView.this.tryToDirectlyPlay())
              {
                if (MplayerTimeshiftView.this.canTimeShift())
                  MplayerTimeshiftView.this.addPlayAuthTask(MplayerTimeshiftView.this.currentPlayChannel);
              }
              else
              {
                MplayerTimeshiftView.access$6802(MplayerTimeshiftView.this, MplayerTimeshiftView.this.makeIdxString(localChannelItemInfoV2.channelNum));
                MplayerTimeshiftView.access$7002(MplayerTimeshiftView.this, localChannelItemInfoV2.name);
                MplayerTimeshiftView.this.setTitleChannelNumAndName();
                if (MplayerTimeshiftView.this.timeNodeData == null);
              }
            }
            catch (Exception localException2)
            {
              synchronized (MplayerTimeshiftView.this.timeNodeData)
              {
                MplayerTimeshiftView.this.timeNodeData.clear();
                MplayerTimeshiftView.this.timeNodeDiscrib.clear();
                MplayerTimeshiftView.this.reportCameraChange();
                return;
                bool2 = false;
                break;
                label708: bool1 = false;
                break label102;
                localException2 = localException2;
                localException2.printStackTrace();
                continue;
                MplayerTimeshiftView.this.displayNoTSTVNotice();
              }
            }
            label742: if (MplayerTimeshiftView.this.isPlayerSucceed)
            {
              MplayerTimeshiftView.access$7302(MplayerTimeshiftView.this, true);
              MplayerTimeshiftView.this.resetUIMode();
              continue;
            }
            try
            {
              MplayerTimeshiftView.this.pParams.nns_index = 0;
              if (MplayerTimeshiftView.this.pParams.nns_videoInfo == null)
              {
                MplayerTimeshiftView.this.pParams.nns_videoInfo = new VideoInfo();
                MplayerTimeshiftView.this.pParams.nns_videoInfo.packageId = "TimeShift";
              }
              MplayerTimeshiftView.this.pParams.played_time = 0L;
              MplayerTimeshiftView.this.pParams.nns_videoInfo.videoId = localChannelItemInfoV2.id;
              MplayerTimeshiftView.this.pParams.nns_videoInfo.categoryId = localChannelItemInfoV2.categoryId;
              MplayerTimeshiftView.this.pParams.nns_videoInfo.name = localChannelItemInfoV2.name;
              MplayerTimeshiftView.this.pParams.nns_videoInfo.film_name = localChannelItemInfoV2.name;
              MplayerTimeshiftView.this.pParams.nns_videoInfo.videoType = 1;
              MplayerTimeshiftView.this.pParams.channel_index = String.valueOf(localChannelItemInfoV2.channelNum);
              MplayerTimeshiftView.this.pParams.mode = 6;
              MplayerTimeshiftView.this.pParams.real_default_back_pos = localChannelItemInfoV2.tsLimitPos;
              MplayerTimeshiftView.this.pParams.real_max_back_time_len = localChannelItemInfoV2.tsLimitMax;
              MplayerTimeshiftView.this.pParams.real_min_back_time_len = localChannelItemInfoV2.tsLimitMin;
              MplayerTimeshiftView.this.addPlayAuthTask(MplayerTimeshiftView.this.pParams.nns_videoInfo.videoId);
              MplayerTimeshiftView.access$6402(MplayerTimeshiftView.this, MplayerTimeshiftView.this.pParams.nns_videoInfo.videoId);
              MplayerTimeshiftView.access$6802(MplayerTimeshiftView.this, MplayerTimeshiftView.this.makeIdxString(localChannelItemInfoV2.channelNum));
              MplayerTimeshiftView.access$7002(MplayerTimeshiftView.this, localChannelItemInfoV2.name);
            }
            catch (Exception localException1)
            {
              while (true)
                localException1.printStackTrace();
            }
          }
        }
      };
      localMplayerTimeShiftEpisodeView.setOnChannelItemClickListener(local8);
      this.channelListView.setFocusable(true);
      this.channelListView.setBackgroundColor(16777215);
      RelativeLayout.LayoutParams localLayoutParams9 = new RelativeLayout.LayoutParams(-1, -1);
      localLayoutParams9.addRule(9);
      addView(this.channelListView, localLayoutParams9);
      this.timeshiftPreviewer = findViewById(2131165302);
      RelativeLayout.LayoutParams localLayoutParams10 = new RelativeLayout.LayoutParams(App.OperationHeight(903), App.OperationHeight(710));
      localLayoutParams10.addRule(11);
      localLayoutParams10.width = App.Operation(903.0F);
      localLayoutParams10.height = App.Operation(710.0F);
      localLayoutParams10.rightMargin = App.Operation(5.0F);
      localLayoutParams10.topMargin = App.Operation(75.0F);
      localLayoutParams10.bottomMargin = App.Operation(0.0F);
      this.timeshiftPreviewer.setLayoutParams(localLayoutParams10);
      this.mplayerRetryLogic = new MplayerRetryLogic(GlobalEnv.getInstance().getTVRetryCount(), GlobalEnv.getInstance().getPlayRetryCount());
      this.nextProgramLoading = ((ProgressBar)findViewById(2131165305));
      this.prevProgramLoading = ((ProgressBar)findViewById(2131165303));
      this.nextProgramLoading.setFocusable(false);
      this.prevProgramLoading.setFocusable(false);
      this.nextProgramTitle = ((TextView)findViewById(2131165306));
      this.prevProgramTitle = ((TextView)findViewById(2131165304));
      this.noticeNoTSTV = "该频道不支持时移播放";
      this.noticeErrorChannel = "该频道不存在";
      this.playState = new MplayerPlayStateView(this.context);
      RelativeLayout.LayoutParams localLayoutParams11 = new RelativeLayout.LayoutParams(App.Operation(120.0F), App.Operation(120.0F));
      localLayoutParams11.addRule(13);
      addView(this.playState, localLayoutParams11);
      if ((this.context != null) && ((this.context instanceof DialogActivity)))
        ((DialogActivity)this.context).initReportPageInfo(MplayerTimeshiftView.class.getSimpleName());
      Logger.i("MplayerTimeshiftView", "initViews() end");
      return;
      k = 0;
      break;
      label1529: m = 0;
      break label527;
      label1535: n = 0;
      break label699;
    }
  }

  private boolean isEnableM3U8()
  {
    return AppFuncCfg.FUNCTION_TIMESHIFT_ENABLE_M3U8;
  }

  private boolean isErrorNoticeShowing()
  {
    return (0x230 & this.displayViews) > 0;
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

  private void loadProgramData(List<PlayBillStruct> paramList)
  {
    Object localObject1 = null;
    Object localObject2 = null;
    this.nextProgramLoading.setVisibility(8);
    this.prevProgramLoading.setVisibility(8);
    if (this.timeNodeData == null)
    {
      this.timeNodeData = new ArrayList();
      this.timeNodeDiscrib = new ArrayList();
    }
    while (true)
    {
      int i;
      int j;
      PlayBillItem localPlayBillItem;
      try
      {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMdd:HHmmss");
        i = 0;
        if (i >= paramList.size())
          break label508;
        PlayBillStruct localPlayBillStruct = (PlayBillStruct)paramList.get(i);
        Logger.i("MplayerTimeshiftView", "loadProgramData() playBill" + i + ": " + localPlayBillStruct.toString());
        ArrayList localArrayList = localPlayBillStruct.arrPlayBill;
        j = 0;
        if (j >= localArrayList.size())
          break label554;
        localPlayBillItem = (PlayBillItem)localArrayList.get(j);
        long l = localSimpleDateFormat.parse(localPlayBillStruct.day + ":" + localPlayBillItem.begin).getTime();
        synchronized (this.timeNodeData)
        {
          this.timeNodeData.add(Long.valueOf(l));
          this.timeNodeDiscrib.add(localPlayBillItem.desc);
          Logger.i("MplayerTimeshiftView", "loadProgramData() item:" + localPlayBillItem.toString());
          if (1 == i)
          {
            if (2 == localPlayBillItem.can_play)
            {
              localObject1 = localPlayBillItem;
              Logger.i("MplayerTimeshiftView", "loadProgramData() curItem idx:" + j);
            }
          }
          else
          {
            j++;
            continue;
            synchronized (this.timeNodeData)
            {
              this.timeNodeData.clear();
              this.timeNodeDiscrib.clear();
            }
          }
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        setTitleText();
        this.seekbar.setTimeNodeData(this.timeNodeData, null);
        this.prevProgramTitle.setText("正在播出：" + genProgramDesc(localObject1) + "");
        this.nextProgramTitle.setText("下个节目：" + genProgramDesc((PlayBillItem)localObject2));
        return;
      }
      label554: if ((localPlayBillItem.can_play == 0) && (localObject2 == null))
      {
        localObject2 = localPlayBillItem;
        Logger.i("MplayerTimeshiftView", "loadProgramData() nextItem idx:" + j);
        continue;
        label508: if ((localObject2 == null) && (paramList.size() > 2))
        {
          localObject2 = (PlayBillItem)((PlayBillStruct)paramList.get(2)).arrPlayBill.get(0);
          Logger.i("MplayerTimeshiftView", "loadProgramData() nextItem get from next day");
          continue;
          i++;
        }
      }
    }
  }

  private void makeFakeBarrage(BarrageResponse paramBarrageResponse)
  {
    Random localRandom = new Random(System.currentTimeMillis());
    int i = 1 + localRandom.nextInt(10);
    long l = System.currentTimeMillis();
    int j;
    String str;
    if (i == 1)
    {
      j = 1 + localRandom.nextInt(300);
      str = j + "条测试数据";
    }
    for (int k = 0; ; k++)
    {
      if (k >= j)
        break label256;
      BarrageMeta localBarrageMeta = new BarrageMeta();
      int m = localRandom.nextInt(20);
      StringBuilder localStringBuilder = new StringBuilder(m + 10);
      localStringBuilder.append(str);
      int n = 0;
      while (true)
        if (n < m)
        {
          localStringBuilder.append(n);
          n++;
          continue;
          if (i == 10)
          {
            j = 1 + localRandom.nextInt(150);
            break;
          }
          if ((i == 3) || (i == 4) || (i == 7) || (i == 2) || (i == 9))
          {
            j = 1 + localRandom.nextInt(20);
            break;
          }
          if ((i == 5) || (i == 6) || (i == 8))
          {
            j = 3;
            break;
          }
          j = 10;
          break;
        }
      localBarrageMeta.content = localStringBuilder.toString();
      localBarrageMeta.rcvTime = l;
      paramBarrageResponse.addBarrageMeta(localBarrageMeta);
    }
    label256: paramBarrageResponse.setTotalCount(j);
    onBarrageGetSuccess(paramBarrageResponse.getBarrageMetaList());
  }

  private String makeIdxString(int paramInt)
  {
    if (paramInt < 10)
      return "00" + paramInt;
    if (paramInt < 100)
      return "0" + paramInt;
    return String.valueOf(paramInt);
  }

  private void onBarrageGetSuccess(List<BarrageMeta> paramList)
  {
    this.mBarrageView.addData(paramList);
    if (this.bulletScreenNoticeView.getVisibility() == 0)
      this.isBulletScreenStream = true;
    while (this.isAlreadyShowQrCode)
      return;
    this.isBulletScreenStream = true;
    setBulletScreenQrCodeStatusByStream(0);
    this.isAlreadyShowQrCode = true;
  }

  private void onChangePlayUrlEvent()
  {
    Logger.d("MplayerTimeshiftView", "onChangePlayUrlEvent!");
    this.pUrl = "";
    dragCount = 0;
    heartbeatCount = 0;
    this.dialogMsg = "";
    ReportState.clearBufferCount();
    this.isReportGflow = false;
    this.isReportLoad = false;
    this.isReportPlay = false;
    this.isReportStop = false;
    MplayerV2.suuid = UUID.randomUUID().toString();
    reportPlayerState(0);
    reportPlayQuality(9);
    resetAccessCMSFinalInvokeFlag();
    this.changeCodeRateCategory = CDNReportHelper.ChangeCodeRateCategory.FIRST_LOAD;
  }

  private void processBarrage(BarrageResponse paramBarrageResponse)
  {
    Logger.d("MplayerTimeshiftView", "processBarrage errorCode: " + paramBarrageResponse.getErrorCode() + ", interval: " + paramBarrageResponse.getInterval() + ", curTimeKey: " + paramBarrageResponse.getCurTimeKey());
    this.mBarrageGetInterval = (1000 * paramBarrageResponse.getInterval());
    if (this.mBarrageGetInterval <= 0)
      this.mBarrageGetInterval = 5000;
    if (paramBarrageResponse.getErrorCode() == 200)
    {
      this.curTimeKey = paramBarrageResponse.getCurTimeKey();
      Logger.d("MplayerTimeshiftView", "请求弹幕成功，弹幕数： " + paramBarrageResponse.getTotalCount());
      if ((paramBarrageResponse.getTotalCount() > 0) && (paramBarrageResponse.getBarrageMetaList() != null))
        onBarrageGetSuccess(paramBarrageResponse.getBarrageMetaList());
    }
    while (true)
    {
      startGetBarrage();
      return;
      Logger.e("MplayerTimeshiftView", "请求弹幕失败, errorCode: " + paramBarrageResponse.getErrorCode() + ", errorMsg: " + paramBarrageResponse.getErrorMsg());
    }
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

  private void refreshChannelIndexRange()
  {
    if ((this.channelListView == null) || (this.channelListDataV2 == null) || (this.channelListDataV2.channelList == null) || (this.channelListDataV2.channelList.size() == 0))
      return;
    this.currentPlayCategory = this.channelListView.getCurrentCategoryId();
    this.minChannelIndexInCurrentCategory = -1;
    this.maxChannelIndexInCurrentCategory = -1;
    int i = 0;
    if (i < this.channelListDataV2.channelList.size())
    {
      ChannelItemInfoV2 localChannelItemInfoV2 = (ChannelItemInfoV2)this.channelListDataV2.channelList.get(i);
      if (!this.currentPlayCategory.equals(localChannelItemInfoV2.categoryId));
      while (true)
      {
        i++;
        break;
        if (-1 != this.minChannelIndexInCurrentCategory)
          break label139;
        this.minChannelIndexInCurrentCategory = i;
        if (-1 != this.maxChannelIndexInCurrentCategory)
          break label165;
        this.maxChannelIndexInCurrentCategory = i;
      }
      label139: if (this.minChannelIndexInCurrentCategory > i);
      for (int j = i; ; j = this.minChannelIndexInCurrentCategory)
      {
        this.minChannelIndexInCurrentCategory = j;
        break;
      }
      label165: if (this.maxChannelIndexInCurrentCategory > i);
      for (int k = this.maxChannelIndexInCurrentCategory; ; k = i)
      {
        this.maxChannelIndexInCurrentCategory = k;
        break;
      }
    }
    Logger.i("MplayerTimeshiftView", "refreshChannelIndexRange() minChannelIndexInCurrentCategory:" + this.minChannelIndexInCurrentCategory + ", maxChannelIndexInCurrentCategory:" + this.maxChannelIndexInCurrentCategory);
  }

  private void refreshControlPannel()
  {
    if ((0x8 & this.displayViews) > 0)
    {
      this.seekbar.hide();
      this.title.hide();
      this.displayViews = (0xFFFFFFFB & this.displayViews);
    }
    refreshSeekBar();
    refreshTitleView();
  }

  private void refreshSeekBar()
  {
    long l1 = this.currentPlayPos + this.tstvBeginTime;
    long l2 = getServerTime() - this.tstvRealOffset;
    if (l1 > l2)
      l1 = l2;
    this.seekbar.refreshProgressByPlay(l1);
    this.seekbar.invalidate();
  }

  private void refreshTitleView()
  {
    this.title.setCurrentTime();
    if ((this.timerCount % 120 == 0) && (!this.isNeedToChangeChannelByNumKey))
      setTitleText();
    this.title.invalidate();
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
    CDNReportHelper.reportIF1("IF1访问CMS上报", paramBoolean, this.accessCMSFinalInvoke, CDNReportHelper.PlayStep.ACCESS_CMS, paramString1, getChangeCodeRateCategory(), getQuality(), CDNReportHelper.PlayType.LIVE, paramString2);
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
    CDNReportHelper.reportIF1("IF1访问Cache上报", paramBoolean, this.accessCMSFinalInvoke, CDNReportHelper.PlayStep.ACCESS_CACHE, paramString1, getChangeCodeRateCategory(), getQuality(), CDNReportHelper.PlayType.LIVE, paramString2);
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

  private void reportCDNIF2Buffer(int paramInt)
  {
    CDNReportHelper.reportIF2("IF2卡顿时上报", paramInt, CDNReportIF2Builder.ReportType.BUFFER_REPORT, this.playUrl, CDNReportHelper.PlayType.LIVE, -1L, getQuality(), null);
  }

  private void reportCDNIF2Complete(int paramInt)
  {
    if (this.cdnAccessFirstFrame)
    {
      CDNReportHelper.reportIF2("IF2完成时上报", paramInt, CDNReportIF2Builder.ReportType.COMPLETE_REPORT, this.playUrl, CDNReportHelper.PlayType.LIVE, -1L, getQuality(), null);
      this.cdnAccessFirstFrame = false;
    }
  }

  private void reportCDNIF2Error(String paramString)
  {
    CDNReportHelper.reportIF2("IF2错误即时上报", 1, CDNReportIF2Builder.ReportType.ERROR_REPORT, this.playUrl, CDNReportHelper.PlayType.LIVE, -1L, getQuality(), paramString);
  }

  private void reportCDNIF2Timer(int paramInt)
  {
    Logger.d("MplayerTimeshiftView", "reportCDNIF2Timer bufferCountPeriod: " + paramInt + ", playUrl: " + this.playUrl);
    CDNReportHelper.reportIF2("IF2定时上报", paramInt, CDNReportIF2Builder.ReportType.TIMER_REPORT, this.playUrl, CDNReportHelper.PlayType.LIVE, -1L, getQuality(), null);
  }

  private void reportCameraChange()
  {
    if (!isLivePlay())
    {
      Logger.i("MplayerTimeshiftView", "reportCameraChange  !isLivePlay");
      return;
    }
    if (!this.isActivityIdSame)
    {
      Logger.i("MplayerTimeshiftView", "reportCameraChange  !isActivityIdSame");
      return;
    }
    if (this.isliveIdSame)
    {
      Logger.i("MplayerTimeshiftView", "reportCameraChange  !isActivityIdSame");
      return;
    }
    ReportMessage localReportMessage = new ReportMessage();
    ReportJSONObject localReportJSONObject = new ReportJSONObject();
    localReportMessage.setIsLiveReport(true);
    long l1 = (System.currentTimeMillis() - this.p2pStarTime) / 1000L;
    long l2 = this.tstvBeginTime + this.currentPlayPos;
    try
    {
      localReportJSONObject.put("act", "switch");
      localReportJSONObject.put("bid", "3.1.1.1");
      localReportJSONObject.put("suuid", MplayerV2.suuid);
      localReportJSONObject.put("vid", "");
      localReportJSONObject.put("ovid", "");
      localReportJSONObject.put("plid", this.pParams.nns_videoInfo.videoId);
      localReportJSONObject.put("oplid", this.pParams.nns_videoInfo.import_id);
      localReportJSONObject.put("url", URLEncoder.encode(ReportState.url, "UTF-8"));
      localReportJSONObject.put("purl", URLEncoder.encode(this.pUrl, "UTF-8"));
      localReportJSONObject.put("pay", this.pay);
      localReportJSONObject.put("def", this.pParams.mediaQuality);
      localReportJSONObject.put("istry", 0);
      localReportJSONObject.put("ref", this.pParams.out_play);
      localReportJSONObject.put("pt", getReportPt());
      localReportJSONObject.put("tpt", 1);
      localReportJSONObject.put("ap", 0);
      localReportJSONObject.put("lcid", this.import_id);
      localReportJSONObject.put("sourceid", this.tv_channel);
      localReportJSONObject.put("streamid", getChannelIdFromUrl(this.pUrl));
      localReportJSONObject.put("liveid", this.cameraposition);
      localReportJSONObject.put("ln", this.pParams.nns_videoInfo.name);
      localReportJSONObject.put("pagename", getCurPageName());
      localReportJSONObject.put("playsrc", ReportInfo.getInstance().getEntranceSrc());
      localReportJSONObject.put("ct", l2 / 1000L);
      localReportJSONObject.put("idx", "");
      localReportJSONObject.put("ext1", ReportState.getP2PReportData(12, l1));
      localReportJSONObject.put("ext2", this.activity_id);
      localReportMessage.mExtData = new Column().buildJsonData(localReportJSONObject);
      localReportMessage.setDesc("现场直播切换镜头事件上报");
      MessageHandler.instance().doNotify(localReportMessage);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        Logger.w("ReportService", "json exception!", localException);
    }
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
    localApplicationException.setIsLiveReport(isLivePlay());
    localApplicationException.setShowDialog(false);
    localApplicationException.start();
  }

  private void reportInteractiveMsgClick()
  {
    if (((this.context instanceof DialogActivity)) && (this.curInteractiveMsg != null))
      ((DialogActivity)this.context).reportClick(false, 6, "", this.curInteractiveMsg.toJSONString());
  }

  private void reportPlayQuality(int paramInt)
  {
    Logger.d("MplayerTimeshiftView", "reportPlayQuality: " + ReportState.getReportStateDsc(paramInt));
    ReportMessage localReportMessage = new ReportMessage();
    ReportJSONObject localReportJSONObject = new ReportJSONObject();
    long l = (System.currentTimeMillis() - this.p2pStarTime) / 1000L;
    try
    {
      localReportJSONObject.put("act", ReportState.getReportStateDsc(paramInt));
      String str;
      if (isLivePlay())
      {
        str = "3.1.6.1";
        localReportJSONObject.put("bid", str);
        if (isLivePlay())
          localReportMessage.setIsLiveReport(true);
        localReportJSONObject.put("idx", "");
        localReportJSONObject.put("pt", getReportPt());
        localReportJSONObject.put("tpt", 1);
        localReportJSONObject.put("lcid", this.import_id);
        localReportJSONObject.put("sourceid", this.tv_channel);
        localReportJSONObject.put("streamid", getChannelIdFromUrl(this.pUrl));
        localReportJSONObject.put("liveid", this.cameraposition);
        localReportJSONObject.put("ln", this.pParams.nns_videoInfo.name);
        localReportJSONObject.put("td", "");
        localReportJSONObject.put("purl", URLEncoder.encode(this.pUrl, "UTF-8"));
        localReportJSONObject.put("pagename", getCurPageName());
      }
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
          localReportJSONObject.put("vid", "");
          localReportJSONObject.put("ovid", "");
          localReportJSONObject.put("sovid", "");
          localReportJSONObject.put("plid", this.pParams.nns_videoInfo.videoId);
          localReportJSONObject.put("oplid", this.pParams.nns_videoInfo.import_id);
          localReportJSONObject.put("soplid", this.pParams.nns_videoInfo.serial_id);
          localReportMessage.mExtData = new Column().buildJsonData(localReportJSONObject);
          localReportMessage.setDesc("播放器(" + ReportState.getReportStateDsc(paramInt) + ")事件上报");
          MessageHandler.instance().doNotify(localReportMessage);
          do
          {
            return;
            str = "3.1.6";
            break;
          }
          while (this.isReportLoad);
          localReportJSONObject.put("td", (System.nanoTime() - ReportState.getStateStartTime(paramInt).longValue()) / 1000000L);
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
      ((DialogActivity)this.context).reportClick(false, 4, paramString, "0");
  }

  private void reportPlayerState(int paramInt)
  {
    Logger.d("MplayerTimeshiftView", "reportPlayerState: " + ReportState.getReportStateDsc(paramInt));
    ReportMessage localReportMessage = new ReportMessage();
    ReportJSONObject localReportJSONObject = new ReportJSONObject();
    long l1 = (System.currentTimeMillis() - this.p2pStarTime) / 1000L;
    if (isLivePlay())
      localReportMessage.setIsLiveReport(true);
    try
    {
      updatePlayStateJSON(localReportJSONObject, paramInt);
      long l2 = this.tstvBeginTime;
      int i = this.currentPlayPos;
      l3 = l2 + i;
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
          localReportJSONObject.put("ext2", this.activity_id);
          localReportJSONObject.put("ext1", ReportState.getP2PReportData(0, l1));
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
          long l3;
          Logger.w("ReportService", "json exception!", localException);
          continue;
          if (!this.isReportPlay)
          {
            startMplayerHeartbeat();
            localReportJSONObject.put("idx", "");
            localReportJSONObject.put("ct", 0);
            localReportJSONObject.put("td", "");
            localReportJSONObject.put("ext1", ReportState.getP2PReportData(3, l1));
            localReportJSONObject.put("ext2", this.activity_id);
            this.isReportPlay = true;
            continue;
            localReportJSONObject.put("ct", l3 / 1000L);
            localReportJSONObject.put("idx", "");
            localReportJSONObject.put("td", "");
            localReportJSONObject.put("ext1", ReportState.getP2PReportData(4, l1));
            localReportJSONObject.put("ext2", this.activity_id);
            continue;
            localReportJSONObject.put("ct", l3 / 1000L);
            localReportJSONObject.put("idx", "");
            localReportJSONObject.put("td", "");
            localReportJSONObject.put("ext1", ReportState.getP2PReportData(5, l1));
            localReportJSONObject.put("ext2", this.activity_id);
            continue;
            int j = 1 + dragCount;
            dragCount = j;
            localReportJSONObject.put("idx", j);
            localReportJSONObject.put("ct", this.seekStartPos / 1000L);
            localReportJSONObject.put("et", l3 / 1000L);
            localReportJSONObject.put("td", (System.nanoTime() - ReportState.getStateStartTime(paramInt).longValue()) / 1000000000L);
            localReportJSONObject.put("ext1", ReportState.getP2PReportData(6, l1));
            localReportJSONObject.put("ext2", this.activity_id);
          }
        }
      while (ReportState.getStateStartTime(3) == null);
      localReportJSONObject.put("ct", (this.tstvBeginTime + this.currentPlayPos) / 1000L);
      localReportJSONObject.put("idx", "");
      localReportJSONObject.put("td", (System.nanoTime() - ReportState.getStateStartTime(3).longValue()) / 1000000000L);
      localReportJSONObject.put("ext1", ReportState.getP2PReportData(8, l1));
      localReportJSONObject.put("ext2", this.activity_id);
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
        Logger.i("MplayerTimeshiftView", "reportPlayerState: playsrc=" + (String)localObject);
        break;
        str2 = str1 + "0";
      }
    }
  }

  private void resetAccessCMSFinalInvokeFlag()
  {
    this.accessCMSFinalInvoke = false;
  }

  private void resetUIMode()
  {
    Logger.i("MplayerTimeshiftView", "resetUIMode() isFullPlayMode:" + this.isFullPlayMode + ", displayViews:" + this.displayViews);
    if (this.isFullPlayMode)
    {
      if ((0x4 & this.displayViews) == 0)
      {
        this.title.setVisibility(0);
        this.title.display();
        this.seekbar.setVisibility(0);
        this.seekbar.display();
        this.displayViews = (0x4 | this.displayViews);
      }
      resetChannelListToFullPlayMode();
      resetVideoRectToFullPlayMode();
      requestLayout();
      bringToFront();
      return;
    }
    if ((0x4 & this.displayViews) > 0)
    {
      this.title.setVisibility(4);
      this.title.hide();
      this.seekbar.setVisibility(4);
      this.seekbar.hide();
      this.displayViews = (0xFFFFFFFB & this.displayViews);
    }
    resetChannelListToPreviewMode();
    resetVideoRectToPreviewMode();
  }

  private void resetVideoRectToFullPlayMode()
  {
    Logger.i("MplayerTimeshiftView", "resetVideoRectToFullPlayMode()");
    if (this.mpCore == null)
      return;
    this.mpCore.bringToFront();
  }

  private void resetVideoRectToPreviewMode()
  {
    Logger.i("MplayerTimeshiftView", "resetVideoRectToPreviewMode()");
    if (this.mpCore == null)
      return;
    RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)this.mpCore.getLayoutParams();
    localLayoutParams.leftMargin = App.Operation(390.0F);
    localLayoutParams.topMargin = App.Operation(100.0F);
    localLayoutParams.width = App.Operation(870.0F);
    localLayoutParams.height = App.Operation(513.0F);
    this.mpCore.requestLayout();
    this.mpCore.bringToFront();
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

  private void setBulletScreenItemStatus(String paramString)
  {
    String str = GlobalLogic.getInstance().getBulletScreenStatus();
    this.menuView.addItemFlag(512);
    this.menuView.setItemStates(512, this.bulletHashMap);
    if (!TextUtils.isEmpty(str))
    {
      this.menuView.setItemState(512, str);
      this.isBarrageOpen = str.equals("open");
      return;
    }
    this.menuView.setItemState(512, paramString);
  }

  private void setBulletScreenQrCodeShowRule(KeyEvent paramKeyEvent)
  {
    int i;
    int j;
    if ((0x4000 & this.displayViews) > 0)
    {
      i = 1;
      if ((0x8000 & this.displayViews) <= 0)
        break label70;
      j = 1;
      label25: switch (paramKeyEvent.getKeyCode())
      {
      default:
      case 19:
      case 20:
      case 82:
      }
    }
    label70: 
    do
    {
      do
      {
        do
        {
          return;
          i = 0;
          break;
          j = 0;
          break label25;
        }
        while ((i != 0) && (j == 0));
        this.displayViews = (0xFFFF7FFF & this.displayViews);
        this.qrCodeView.setVisibility(8);
        return;
      }
      while ((i != 0) && (j == 0));
      this.displayViews = (0xFFFF7FFF & this.displayViews);
      this.qrCodeView.setVisibility(8);
      return;
    }
    while ((i != 0) && (j == 0));
    this.displayViews = (0xFFFF7FFF & this.displayViews);
    this.qrCodeView.setVisibility(8);
  }

  private void setBulletScreenQrCodeStatusByPause(int paramInt)
  {
    if ((AppFuncCfg.FUNCTION_ENABLE_BARRAGE_CONTROL) && (this.isBarrageOpen))
    {
      if ((0x4000 & this.displayViews) > 0)
        this.displayViews = (0xFFFFBFFF & this.displayViews);
      if (paramInt != 0)
        break label71;
    }
    label71: for (this.displayViews = (0x8000 | this.displayViews); ; this.displayViews = (0xFFFF7FFF & this.displayViews))
    {
      if (this.qrCodeView.getVisibility() != paramInt)
        this.qrCodeView.setVisibility(paramInt);
      return;
    }
  }

  private void setBulletScreenQrCodeStatusByStream(int paramInt)
  {
    if ((AppFuncCfg.FUNCTION_ENABLE_BARRAGE_CONTROL) && (this.isBarrageOpen) && ((0x8000 & this.displayViews) <= 0) && (this.isBulletScreenStream))
      if (paramInt != 0)
        break label66;
    label66: for (this.displayViews = (0x4000 | this.displayViews); ; this.displayViews = (0xFFFFBFFF & this.displayViews))
    {
      if (this.qrCodeView.getVisibility() != paramInt)
        this.qrCodeView.setVisibility(paramInt);
      return;
    }
  }

  private void setBulletScreenShowLogic(ChannelItemInfoV2 paramChannelItemInfoV2)
  {
    this.isBarrageOpen = false;
    Logger.d("MplayerTimeshiftView", "AppFuncCfg.FUNCTION_ENABLE_BARRAGE_CONTROL: " + AppFuncCfg.FUNCTION_ENABLE_BARRAGE_CONTROL + ", item.bullet_screen: " + paramChannelItemInfoV2.bullet_screen);
    if (AppFuncCfg.FUNCTION_ENABLE_BARRAGE_CONTROL)
      if ((TextUtils.isEmpty(paramChannelItemInfoV2.bullet_screen)) || ("1".equals(paramChannelItemInfoV2.bullet_screen)))
      {
        Logger.d("MplayerTimeshiftView", "弹幕开启");
        this.isBarrageOpen = true;
        setBulletScreenItemStatus("open");
      }
    while (true)
    {
      this.menuView.refreshView();
      return;
      if ("2".equals(paramChannelItemInfoV2.bullet_screen))
      {
        setBulletScreenItemStatus("close");
      }
      else
      {
        this.menuView.removeItemByFlag(512);
        continue;
        this.menuView.removeItemByFlag(512);
      }
    }
  }

  private void setChannelListVisibility(int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (paramInt == 0);
    for (String str = "show"; ; str = "hide")
    {
      Logger.d("MplayerTimeshiftView", str + " channelList.");
      if (this.channelListView != null)
      {
        this.channelListView.setVisibility(paramInt);
        if (paramInt == 0)
          this.channelListView.requestLayout();
        this.channelListView.bringToFront();
        if ((0x1000 & this.displayViews) > 0)
          this.interactiveView.bringToFront();
      }
      return;
    }
  }

  private void setInteractiveViewVisible(boolean paramBoolean)
  {
    boolean bool;
    MplayerInteractiveView localMplayerInteractiveView;
    int i;
    if (this.interactiveView != null)
    {
      if (this.interactiveView.getVisibility() != 0)
        break label70;
      bool = true;
      if (bool != paramBoolean)
      {
        localMplayerInteractiveView = this.interactiveView;
        i = 0;
        if (!paramBoolean)
          break label75;
      }
    }
    while (true)
    {
      localMplayerInteractiveView.setVisibility(i);
      if (paramBoolean)
        this.interactiveView.requestLayout();
      if (!paramBoolean)
        break label81;
      this.displayViews = (0x1000 | this.displayViews);
      return;
      label70: bool = false;
      break;
      label75: i = 4;
    }
    label81: this.displayViews = (0xFFFFEFFF & this.displayViews);
  }

  private void setInteractiveVisibility(int paramInt)
  {
    if (this.interactiveView != null)
      if (paramInt != 0)
        break label39;
    label39: for (this.displayViews = (0x1000 | this.displayViews); ; this.displayViews = (0xFFFFEFFF & this.displayViews))
    {
      this.interactiveView.setVisibility(paramInt);
      this.interactiveView.requestLayout();
      return;
    }
  }

  private void setInteractiveWebViewVisible(boolean paramBoolean)
  {
    MplayerInteractiveWebView localMplayerInteractiveWebView;
    if (this.interactiveWebView != null)
    {
      if (!paramBoolean)
        break label56;
      this.interactiveWebView.setFocusable(true);
      this.interactiveWebView.requestFocus();
      this.displayViews = (0x2000 | this.displayViews);
      localMplayerInteractiveWebView = this.interactiveWebView;
      if (!paramBoolean)
        break label78;
    }
    label56: label78: for (int i = 0; ; i = 4)
    {
      localMplayerInteractiveWebView.setVisibility(i);
      return;
      this.interactiveWebView.clearFocus();
      this.displayViews = (0xFFFFDFFF & this.displayViews);
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

  private void setQrCodeInvisible()
  {
    this.displayViews = (0xFFFFBFFF & this.displayViews);
    this.displayViews = (0xFFFF7FFF & this.displayViews);
    this.qrCodeView.setVisibility(8);
  }

  private void setTitleChannelNum(String paramString)
  {
    this.title.setVideoDiscribForTimeshift(paramString, "", "");
    this.title.invalidate();
  }

  private void setTitleChannelNumAndName()
  {
    this.title.setVideoDiscribForTimeshift(this.curChannelNum, "", this.curChannelName);
    this.title.invalidate();
  }

  private void setTitleText()
  {
    String str = findCurrentProgramByPlayPos(this.currentPlayPos + this.tstvBeginTime);
    this.title.setVideoDiscribForTimeshift(this.curChannelNum, str, this.curChannelName);
  }

  private void showChannelView()
  {
    Logger.i("MplayerTimeshiftView", "showChannelView()");
    setChannelListVisibility(0);
    this.displayViews = (0x8 | this.displayViews);
  }

  private void showCouponDialog()
  {
    Logger.i("tv_channel=" + this.tv_channel);
    final MovieCouponDialog localMovieCouponDialog = new MovieCouponDialog(this.context, "观看该节目需要使用1张观影券，兑换成功后您可以免费观看。", "影片兑换成功！你可以免费观看该节目了。", "确定兑换", "立即观看");
    localMovieCouponDialog.setMovieCouponTipDialogListener(new MovieCouponDialog.MovieCouponTipDialogListener()
    {
      public void onCancel()
      {
        localMovieCouponDialog.dissmissDialog();
      }

      public void onOkButtonClick()
      {
        MplayerTimeshiftView.access$4202(MplayerTimeshiftView.this, "影片兑换成功！你可以免费观看该节目了。");
        localMovieCouponDialog.startUserCoupon();
      }
    });
    localMovieCouponDialog.setMovieCouponOkDialogListener(new MovieCouponDialog.MovieCouponOkDialogListener()
    {
      public void onCancel()
      {
        MplayerTimeshiftView.this.restartCurrentChannel();
      }

      public void onOkButtonClick()
      {
        MplayerTimeshiftView.this.restartCurrentChannel();
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
    Logger.i("MplayerTimeshiftView", "showQuitDialog()");
    if ((0x800 & this.displayViews) > 0)
    {
      this.displayViews = (0xFFFFF7FF & this.displayViews);
      this.notice.setVisibility(4);
      this.notice.setPlayNotice("");
    }
    this.quitView.bringToFront();
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
    this.lsnr.onPreLoadingViewDisplay(4, "");
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
          MplayerTimeshiftView.this.reportCDNIF2Timer(MplayerTimeshiftView.this.bufferPeriodCount);
          MplayerTimeshiftView.access$10302(MplayerTimeshiftView.this, 0);
          if (MplayerTimeshiftView.this.mHandler != null)
            MplayerTimeshiftView.this.mHandler.postDelayed(MplayerTimeshiftView.this.cdnTimerRunnable, 300000L);
        }
      };
    if ((this.mHandler != null) && (this.needCDNTimerReport))
    {
      Logger.d("ReportService", "startCDNTimerReport");
      this.mHandler.postDelayed(this.cdnTimerRunnable, 300000L);
      this.needCDNTimerReport = false;
    }
  }

  private void startGetBarrage()
  {
    if (this.isGetBarrageTaskRunning)
    {
      int i = this.mBarrageGetInterval;
      if (i < 0)
        i = 0;
      Logger.d("MplayerTimeshiftView", "开始获取弹幕，将在 " + i / 1000 + " s 后开始执行");
      if (this.mTimer == null)
        this.mTimer = new Timer();
      if (this.mBarrageGetTask != null)
      {
        this.mBarrageGetTask.cancel();
        this.mBarrageGetTask = null;
      }
      this.mBarrageGetTask = new GetBarrageTimerTask(null);
      this.mTimer.schedule(this.mBarrageGetTask, i);
      return;
    }
    Logger.d("MplayerTimeshiftView", "拉取弹幕任务已经停止，不再继续运行");
  }

  private void startGetBarrageTask()
  {
    if (!this.isGetBarrageTaskRunning)
    {
      this.isGetBarrageTaskRunning = true;
      Logger.d("MplayerTimeshiftView", "开启弹幕任务");
      startGetBarrage();
      return;
    }
    Logger.d("MplayerTimeshiftView", "弹幕任务已经开始");
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
          MplayerTimeshiftView.this.updatePlayStateJSON(MplayerTimeshiftView.this.heartbeatColumn, -1);
          if (MplayerTimeshiftView.this.isLivePlay())
            localReportMessage.setIsLiveReport(true);
          try
          {
            long l = (System.currentTimeMillis() - MplayerTimeshiftView.this.p2pStarTime) / 1000L;
            MplayerTimeshiftView.this.heartbeatColumn.put("act", "heartbeat");
            MplayerTimeshiftView.this.heartbeatColumn.put("ct", (MplayerTimeshiftView.this.tstvBeginTime + MplayerTimeshiftView.this.currentPlayPos) / 1000L);
            MplayerTimeshiftView.this.heartbeatColumn.put("idx", MplayerTimeshiftView.access$11904());
            String str = ReportState.getP2PReportData(11, l);
            MplayerTimeshiftView.this.heartbeatColumn.put("ext1", str);
            MplayerTimeshiftView.this.heartbeatColumn.put("ext2", MplayerTimeshiftView.this.activity_id);
            localReportMessage.mExtData = new Column().buildJsonData(MplayerTimeshiftView.this.heartbeatColumn);
            localReportMessage.setDesc("播放器心跳上报(间隔300s)");
            MessageHandler.instance().doNotify(localReportMessage);
            if (MplayerTimeshiftView.this.mHandler != null)
              MplayerTimeshiftView.this.mHandler.postDelayed(this, 300000L);
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

  private void stopGetBarrageTask()
  {
    Logger.d("MplayerTimeshiftView", "停止弹幕拉取任务");
    this.isGetBarrageTaskRunning = false;
    if (this.mTimer != null)
    {
      this.mTimer.cancel();
      this.mTimer = null;
    }
    if (this.mBarrageGetTask != null)
    {
      this.mBarrageGetTask.cancel();
      this.mBarrageGetTask = null;
    }
    this.mBarrageGetInterval = -1;
    this.curTimeKey = "";
  }

  private void stopToPlay()
  {
    Logger.i("MplayerTimeshiftView", "stopToPlay()");
    this.isStartToCheckBuffering = false;
    this.isFirstLoading = true;
    this.mdc.onPlayerStop(this.tstvBeginTime + this.currentPlayPos);
    this.mpCore.stop();
  }

  private void switchBarrage(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mBarrageView.setBarrageOn();
      startGetBarrageTask();
      return;
    }
    this.mBarrageView.setBarrageOff();
    stopGetBarrageTask();
  }

  private static long timestamp()
  {
    return System.nanoTime() / 1000000L;
  }

  private boolean tryToDirectlyPlay()
  {
    if ((this.channelListDataV2 == null) || (this.channelListDataV2.channelList == null) || (this.channelListDataV2.channelList.size() == 0))
      return false;
    for (int i = 0; i < this.channelListDataV2.channelList.size(); i++)
    {
      ChannelItemInfoV2 localChannelItemInfoV22 = (ChannelItemInfoV2)this.channelListDataV2.channelList.get(i);
      Logger.i("MplayerTimeshiftView", "tryToDirectlyPlay() i:" + i + ", temp:" + localChannelItemInfoV22.toString());
    }
    ChannelItemInfoV2 localChannelItemInfoV21 = (ChannelItemInfoV2)this.channelListDataV2.channelList.get(this.channelSelectedIndex);
    Logger.i("MplayerTimeshiftView", "debug tryToDirectlyPlay() currentPlayCategory:" + this.currentPlayCategory + ", cinfo:" + localChannelItemInfoV21.toString());
    if (!localChannelItemInfoV21.capability.contains("TSTV"))
    {
      this.isEnableFfordAndRwind = false;
      this.seekbar.displayThumb(4);
    }
    while (!localChannelItemInfoV21.capability.contains("LIVE"))
    {
      Logger.i("MplayerTimeshiftView", "tryToDirectlyPlay() cinfo.capability has no LIVE");
      return false;
      this.isEnableFfordAndRwind = true;
      this.seekbar.displayThumb(0);
    }
    if (localChannelItemInfoV21.liveMulticastUrl.length() == 0)
    {
      Logger.i("MplayerTimeshiftView", "tryToDirectlyPlay() cinfo.liveMulticastUrl is invalid");
      return false;
    }
    this.pParams.mode = 4;
    this.pParams.real_min_back_time_len = localChannelItemInfoV21.tsLimitMin;
    this.pParams.real_max_back_time_len = localChannelItemInfoV21.tsLimitMax;
    this.pParams.real_default_back_pos = localChannelItemInfoV21.tsLimitPos;
    this.pParams.nns_index = 0;
    if (this.pParams.nns_videoInfo == null)
      this.pParams.nns_videoInfo = new VideoInfo();
    this.pParams.played_time = 0L;
    this.pParams.nns_videoInfo.videoId = localChannelItemInfoV21.id;
    this.pParams.nns_videoInfo.categoryId = localChannelItemInfoV21.categoryId;
    this.pParams.nns_videoInfo.name = localChannelItemInfoV21.name;
    this.pParams.nns_videoInfo.film_name = localChannelItemInfoV21.name;
    this.pParams.channel_index = String.valueOf(localChannelItemInfoV21.channelNum);
    this.currentPlayChannel = this.pParams.nns_videoInfo.videoId;
    this.curChannelName = localChannelItemInfoV21.name;
    this.curChannelNum = makeIdxString(localChannelItemInfoV21.channelNum);
    if (this.pInfo == null)
      this.pInfo = new PlayInfo();
    this.pInfo.playUrl = localChannelItemInfoV21.liveMulticastUrl;
    UserCPLLogic.getInstance().setLastPlayMgtvFileId(this.pInfo.fileId);
    directlyPlay();
    return true;
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
    int i = ServerApiManager.i().APIGetTerminalRealtimeParamsTask(new SccmsApiGetTerminalRealtimeParamsTask.IGetTerminalRealtimeParamsTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Logger.e("MplayerTimeshiftView", "APIGetTerminalRealtimeParamsTask onError: " + paramAnonymousServerApiCommonError.toString());
        if (!MplayerTimeshiftView.this.interactiveMenuTaskControl.checkTaskValid(paramAnonymousServerApiTaskInfo.getTaskId(), "interactive"))
        {
          Logger.e("MplayerTimeshiftView", "APIGetTerminalRealtimeParamsTask onError,  invalid task!");
          return;
        }
        MplayerTimeshiftView.this.addOrRemoveInteractiveMenu(false);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, TerminalRealtimeParamList paramAnonymousTerminalRealtimeParamList)
      {
        Logger.d("MplayerTimeshiftView", "APIGetTerminalRealtimeParamsTask onSuccess: " + paramAnonymousTerminalRealtimeParamList.toString());
        if (!MplayerTimeshiftView.this.interactiveMenuTaskControl.checkTaskValid(paramAnonymousServerApiTaskInfo.getTaskId(), "interactive"))
          Logger.e("MplayerTimeshiftView", "APIGetTerminalRealtimeParamsTask onSuccess,  invalid task!");
        TerminalRealtimeParam localTerminalRealtimeParam;
        do
        {
          do
            return;
          while (paramAnonymousTerminalRealtimeParamList == null);
          localTerminalRealtimeParam = paramAnonymousTerminalRealtimeParamList.getInteractiveMenuParam();
        }
        while (localTerminalRealtimeParam == null);
        MplayerTimeshiftView.access$7802(MplayerTimeshiftView.this, localTerminalRealtimeParam.value);
        MplayerTimeshiftView.this.addOrRemoveInteractiveMenu(true);
      }
    });
    this.interactiveMenuTaskControl.clear();
    this.interactiveMenuTaskControl.addTaskLabel(i, "APIGetTerminalRealtimeParamsTask", "interactive");
  }

  private void updatePlayStateJSON(ReportJSONObject paramReportJSONObject, int paramInt)
  {
    if (paramReportJSONObject != null);
    while (true)
    {
      try
      {
        paramReportJSONObject.put("act", ReportState.getReportStateDsc(paramInt));
        if (!isLivePlay())
          break label350;
        str = "3.1.1.1";
        paramReportJSONObject.put("bid", str);
        paramReportJSONObject.put("suuid", MplayerV2.suuid);
        paramReportJSONObject.put("vid", "");
        paramReportJSONObject.put("ovid", "");
        paramReportJSONObject.put("plid", this.pParams.nns_videoInfo.videoId);
        paramReportJSONObject.put("oplid", this.pParams.nns_videoInfo.import_id);
        if (paramInt == 0)
        {
          paramReportJSONObject.put("url", "");
          paramReportJSONObject.put("purl", URLEncoder.encode(this.pUrl, "UTF-8"));
          paramReportJSONObject.put("pay", this.pay);
          paramReportJSONObject.put("def", this.pParams.mediaQuality);
          paramReportJSONObject.put("istry", 0);
          paramReportJSONObject.put("ref", this.pParams.out_play);
          paramReportJSONObject.put("pt", getReportPt());
          paramReportJSONObject.put("tpt", 1);
          paramReportJSONObject.put("ap", 0);
          paramReportJSONObject.put("lcid", this.import_id);
          paramReportJSONObject.put("sourceid", this.tv_channel);
          paramReportJSONObject.put("streamid", getChannelIdFromUrl(this.pUrl));
          paramReportJSONObject.put("liveid", this.cameraposition);
          paramReportJSONObject.put("ln", this.pParams.nns_videoInfo.name);
          paramReportJSONObject.put("pagename", getCurPageName());
          paramReportJSONObject.put("playsrc", ReportInfo.getInstance().getEntranceSrc());
          return;
        }
        paramReportJSONObject.put("url", URLEncoder.encode(ReportState.url, "UTF-8"));
        continue;
      }
      catch (Exception localException)
      {
        Logger.w("ReportService", "json exception!", localException);
      }
      return;
      label350: String str = "3.1.1";
    }
  }

  public int bindMediaPlayerCore(MediaPlayerCore paramMediaPlayerCore)
  {
    Logger.i("MplayerTimeshiftView", "bindMediaPlayerCore()");
    if (paramMediaPlayerCore == null)
    {
      Logger.i("MplayerTimeshiftView", "bindMediaPlayerCore() mpCore is null");
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
    this.package_id = paramPlayerIntentParams.nns_videoInfo.packageId;
    Logger.i("MplayerTimeshiftView", "bindPlayInfo() pParams:" + paramPlayerIntentParams.toString());
    initData(paramPlayerIntentParams);
    return 0;
  }

  public boolean checkChannelSupportTSTV(String paramString)
  {
    ChannelItemInfoV2 localChannelItemInfoV2 = findChannelByVideoId(paramString);
    if (localChannelItemInfoV2 == null);
    do
    {
      return false;
      Logger.i("MplayerTimeshiftView", "checkChannelSupportTSTV() videoId:" + paramString + ", cinfo:" + localChannelItemInfoV2.toString());
    }
    while (!localChannelItemInfoV2.capability.contains("TSTV"));
    return true;
  }

  public AirControlPlayState ctrlGetPlayState()
  {
    Logger.i("MplayerTimeshiftView", "ctrlGetPlayState()");
    if (this.pParams == null)
    {
      Logger.i("MplayerTimeshiftView", "ctrlGetPlayState pParams is null");
      return AirControlPlayState.NULL;
    }
    if ("not_play" == this.airPlayState)
      return AirControlPlayState.NULL;
    AirControlPlayState localAirControlPlayState = new AirControlPlayState();
    localAirControlPlayState.getState().setValue(this.airPlayState);
    localAirControlPlayState.getNow_pos().setValue(String.valueOf(this.currentPlayPos / 1000));
    if (this.pParams.mode == 4)
    {
      localAirControlPlayState.getType().setValue("live");
      localAirControlPlayState.getProgram_name().setValue(this.pParams.nns_videoInfo.name);
      localAirControlPlayState.getTime_len().setValue(String.valueOf(this.tstvRealOffset / 1000L));
    }
    while (true)
    {
      localAirControlPlayState.getVideo_id().setValue(this.pParams.nns_videoInfo.videoId);
      localAirControlPlayState.getVideo_type().setValue(String.valueOf(this.pParams.nns_videoInfo.videoType));
      localAirControlPlayState.getVideo_name().setValue(this.pParams.nns_videoInfo.name);
      Logger.i("MplayerTimeshiftView", "ctrlGetPlayState getNow_pos:" + localAirControlPlayState.getNow_pos().getValue() + ", pParams.mode -->" + this.pParams.mode + ",  state.getType()-->" + localAirControlPlayState.getType().getValue());
      return localAirControlPlayState;
      if ((this.pParams.mode == 6) || (this.pParams.mode == 5))
      {
        localAirControlPlayState.getType().setValue("tstv");
        localAirControlPlayState.getProgram_name().setValue(this.pParams.nns_videoInfo.name);
        localAirControlPlayState.getTime_len().setValue(String.valueOf(this.pParams.real_max_back_time_len));
        localAirControlPlayState.getBegin().setValue(String.valueOf(this.pParams.real_min_back_time_len));
        long l = this.tstvBeginTime - this.tstvRealOffset + this.currentPlayPos;
        localAirControlPlayState.getNow_pos().setValue(String.valueOf((l - getServerTime()) / 1000L));
      }
    }
  }

  public void ctrlPausePlay()
  {
    Logger.i("MplayerTimeshiftView", "ctrlPausePlay()");
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
    Logger.i("MplayerTimeshiftView", "ctrlSeekTo() seekTime:" + paramLong + ",isEnableFfordAndRwind=" + this.isEnableFfordAndRwind);
    if (!this.isEnableFfordAndRwind);
    do
    {
      do
        return;
      while (this.seekbar == null);
      if (-1L == paramLong)
        paramLong = this.maxProgressPos;
      int i = (int)(paramLong * this.seekbar.getMax() / this.maxProgressPos);
      this.seekbar.seekTo(i, true);
    }
    while ((0x4 & this.displayViews) != 0);
    this.noOperationTimer = 0;
    this.displayViews = (0x4 | this.displayViews);
    this.seekbar.display();
    this.title.display();
  }

  public void ctrlStartPlay()
  {
    Logger.i("MplayerTimeshiftView", "ctrlStartPlay()");
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
    Logger.i("MplayerTimeshiftView", "ctrlStopPlay()");
    doQuitPlay();
  }

  public int destroy()
  {
    Logger.i("MplayerTimeshiftView", "destroy()");
    hideAllViews();
    this.seekbar.unInit();
    stopToPlay();
    this.isEventStopPlayer = true;
    if (!this.isReportStop)
    {
      reportPlayerState(8);
      this.isReportStop = true;
    }
    if (this.tokenDialog != null)
      this.tokenDialog.dismiss();
    onEventStopPlayer();
    this.mdc.release();
    ReportState.clearBufferCount();
    stopCDNTimerReport();
    if (this.mHandler != null)
    {
      this.mHandler.removeCallbacks(this.runnable);
      this.mHandler = null;
    }
    InteractiveManager.getInstance().unSubScribeChannel();
    stopGetBarrageTask();
    this.mBarrageView.stop();
    return 0;
  }

  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    requestLayout();
    this.noOperationTimer = 0;
    boolean bool;
    if (((0x2000 & this.displayViews) > 0) && (paramKeyEvent.getKeyCode() != 82) && (paramKeyEvent.getKeyCode() != 4))
    {
      this.interactiveWebView.dispatchKeyEvent(paramKeyEvent);
      bool = true;
    }
    int i;
    do
    {
      return bool;
      if (paramKeyEvent.getAction() == 0)
      {
        Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown code:" + paramKeyEvent.getKeyCode() + ", displayViews:" + this.displayViews);
        if (this.bulletScreenNoticeView.getVisibility() == 0)
        {
          this.bulletScreenNoticeView.setVisibility(8);
          if ((paramKeyEvent.getKeyCode() == 4) && (this.isBulletScreenStream))
            this.isDisplayQrCode = true;
          setBulletScreenQrCodeStatusByStream(0);
          if ((0x4000 & this.displayViews) > 0)
            this.isAlreadyShowQrCode = true;
        }
        switch (paramKeyEvent.getKeyCode())
        {
        default:
          Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown 事件未被处理");
          return false;
        case 4:
          Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEYCODE_BACK");
          if ((0x2000 & this.displayViews) > 0)
            this.interactiveWebView.dispatchKeyEvent(paramKeyEvent);
          break;
        case 19:
        case 20:
        case 21:
        case 22:
        case 23:
        case 66:
        case 166:
        case 167:
        }
        while (true)
        {
          Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown 事件被处理");
          return true;
          if (((0x40 & this.displayViews) > 0) && (this.menuView.dispatchKeyEvent(paramKeyEvent)))
          {
            Logger.i("MplayerTimeshiftView", "onInputEvent() keyDown KEYCODE_BACK  DISPLAY_VIEW_MENU");
          }
          else
          {
            MplayerQuitXulPage localMplayerQuitXulPage = this.quitView;
            bool = false;
            if (localMplayerQuitXulPage == null)
              break;
            this.quitView.setNextEpisodeVisibility(8);
            return false;
            Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_UP");
            if ((0x100 & this.displayViews) > 0)
            {
              Logger.i("MplayerTimeshiftView", "onInputEvent() keyUp KEY_UP DISPLAY_VIEW_QUIT");
              return this.quitView.dispatchKeyEvent(paramKeyEvent);
            }
            if ((0x40 & this.displayViews) > 0)
            {
              Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_UP  DISPLAY_VIEW_MENU");
              this.menuView.dispatchKeyEvent(paramKeyEvent);
            }
            else if ((0x8 & this.displayViews) > 0)
            {
              Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_UP  DISPLAY_VIEW_EPISODE");
              this.channelListView.dispatchKeyEvent(paramKeyEvent);
            }
            else if ((0x100 & this.displayViews) > 0)
            {
              Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_UP  DISPLAY_VIEW_QUIT");
            }
            else if ((0x10 & this.displayViews) > 0)
            {
              Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_UP  DISPLAY_VIEW_ERROR_NOTICE");
            }
            else if ((0x20 & this.displayViews) > 0)
            {
              Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_UP  DISPLAY_VIEW_RESERVATION_DIALOG");
            }
            else if ((0x400 & this.displayViews) > 0)
            {
              Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_UP  DISPLAY_VIEW_WEB");
            }
            else if ((0x8 & this.displayViews) > 0)
            {
              Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_UP  DISPLAY_VIEW_EPISODE");
            }
            else
            {
              Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEY_UP  other");
              showChannelView();
              continue;
              Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_DOWN");
              if ((0x100 & this.displayViews) > 0)
              {
                Logger.i("MplayerTimeshiftView", "onInputEvent() keyUp KEY_UP  DISPLAY_VIEW_QUIT");
                return this.quitView.dispatchKeyEvent(paramKeyEvent);
              }
              if ((0x40 & this.displayViews) > 0)
              {
                Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_DOWN  DISPLAY_VIEW_MENU");
                this.menuView.dispatchKeyEvent(paramKeyEvent);
              }
              else if ((0x8 & this.displayViews) > 0)
              {
                Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_DOWN  DISPLAY_VIEW_EPISODE");
                this.channelListView.dispatchKeyEvent(paramKeyEvent);
              }
              else if ((0x100 & this.displayViews) > 0)
              {
                Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_DOWN  DISPLAY_VIEW_QUIT");
              }
              else if ((0x10 & this.displayViews) > 0)
              {
                Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_DOWN  DISPLAY_VIEW_ERROR_NOTICE");
              }
              else if ((0x20 & this.displayViews) > 0)
              {
                Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_DOWN  DISPLAY_VIEW_RESERVATION_DIALOG");
              }
              else if ((0x400 & this.displayViews) > 0)
              {
                Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_DOWN  DISPLAY_VIEW_WEB");
              }
              else
              {
                Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_DOWN  other");
                showChannelView();
                continue;
                if ((0x100 & this.displayViews) > 0)
                {
                  Logger.i("MplayerTimeshiftView", "onInputEvent() keyUp KEY_UP  DISPLAY_VIEW_QUIT");
                  return this.quitView.dispatchKeyEvent(paramKeyEvent);
                }
                if ((0x40 & this.displayViews) > 0)
                {
                  Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_LEFT  DISPLAY_VIEW_MENU");
                  this.menuView.dispatchKeyEvent(paramKeyEvent);
                }
                else if ((0x8 & this.displayViews) > 0)
                {
                  Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_LEFT  DISPLAY_VIEW_EPISODE");
                  this.channelListView.dispatchKeyEvent(paramKeyEvent);
                }
                else if ((0x100 & this.displayViews) > 0)
                {
                  Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_LEFT  DISPLAY_VIEW_QUIT");
                }
                else if ((0x10 & this.displayViews) > 0)
                {
                  Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_LEFT  DISPLAY_VIEW_ERROR_NOTICE");
                }
                else if ((0x20 & this.displayViews) > 0)
                {
                  Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_LEFT  DISPLAY_VIEW_RESERVATION_DIALOG");
                }
                else if ((0x200 & this.displayViews) > 0)
                {
                  Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_LEFT  DISPLAY_VIEW_ORDER_NOTICE");
                }
                else if ((0x4 & this.displayViews) > 0)
                {
                  Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_LEFT  DISPLAY_VIEW_CONTROLL_PANEL");
                  if ((!this.isPlayerComplete) && (this.isEnableFfordAndRwind))
                  {
                    this.seekbar.onInputEvent(paramKeyEvent);
                    this.title.onInputEvent(paramKeyEvent);
                  }
                }
                else
                {
                  Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_LEFT  other");
                  if ((!this.isPlayerComplete) && (this.isEnableFfordAndRwind))
                  {
                    this.displayViews = (0x4 | this.displayViews);
                    this.seekbar.onInputEvent(paramKeyEvent);
                    this.title.onInputEvent(paramKeyEvent);
                  }
                  else if (!this.isEnableFfordAndRwind)
                  {
                    this.displayViews = (0x4 | this.displayViews);
                    this.seekbar.display();
                    this.title.display();
                    continue;
                    if ((0x100 & this.displayViews) > 0)
                    {
                      Logger.i("MplayerTimeshiftView", "onInputEvent() keyUp KEY_UP  DISPLAY_VIEW_QUIT");
                      return this.quitView.dispatchKeyEvent(paramKeyEvent);
                    }
                    if ((0x40 & this.displayViews) > 0)
                    {
                      Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_LEFT  DISPLAY_VIEW_MENU");
                      this.menuView.dispatchKeyEvent(paramKeyEvent);
                    }
                    else if ((0x8 & this.displayViews) > 0)
                    {
                      Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_RIGHT  DISPLAY_VIEW_EPISODE");
                      this.channelListView.dispatchKeyEvent(paramKeyEvent);
                    }
                    else if ((0x100 & this.displayViews) > 0)
                    {
                      Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_RIGHT  DISPLAY_VIEW_QUIT");
                    }
                    else if ((0x10 & this.displayViews) > 0)
                    {
                      Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_RIGHT  DISPLAY_VIEW_ERROR_NOTICE");
                    }
                    else if ((0x20 & this.displayViews) > 0)
                    {
                      Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_RIGHT  DISPLAY_VIEW_RESERVATION_DIALOG");
                    }
                    else if ((0x200 & this.displayViews) > 0)
                    {
                      Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_DOWN  DISPLAY_VIEW_ORDER_NOTICE");
                    }
                    else if ((0x4 & this.displayViews) > 0)
                    {
                      Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_RIGHT  DISPLAY_VIEW_CONTROLL_PANEL");
                      if ((!this.isPlayerComplete) && (this.isEnableFfordAndRwind))
                      {
                        this.seekbar.onInputEvent(paramKeyEvent);
                        this.title.onInputEvent(paramKeyEvent);
                      }
                    }
                    else
                    {
                      Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEY_RIGHT  other");
                      if ((!this.isPlayerComplete) && (this.isEnableFfordAndRwind))
                      {
                        this.displayViews = (0x4 | this.displayViews);
                        this.seekbar.onInputEvent(paramKeyEvent);
                        this.title.onInputEvent(paramKeyEvent);
                      }
                      else if (!this.isEnableFfordAndRwind)
                      {
                        this.displayViews = (0x4 | this.displayViews);
                        this.seekbar.display();
                        this.title.display();
                        continue;
                        if ((0x100 & this.displayViews) > 0)
                        {
                          Logger.i("MplayerTimeshiftView", "onInputEvent() keyUp KEY_UP  DISPLAY_VIEW_QUIT");
                          return this.quitView.dispatchKeyEvent(paramKeyEvent);
                        }
                        if ((0x40 & this.displayViews) > 0)
                        {
                          Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEYCODE_ENTER  DISPLAY_VIEW_MENU");
                          this.menuView.dispatchKeyEvent(paramKeyEvent);
                        }
                        else if ((0x10 & this.displayViews) > 0)
                        {
                          Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEYCODE_ENTER  DISPLAY_VIEW_ERROR_NOTICE");
                        }
                        else if ((0x20 & this.displayViews) > 0)
                        {
                          Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEYCODE_ENTER  DISPLAY_VIEW_RESERVATION_DIALOG");
                        }
                        else if ((0x200 & this.displayViews) > 0)
                        {
                          Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEYCODE_ENTER  DISPLAY_VIEW_ORDER_NOTICE");
                        }
                        else if ((0x8 & this.displayViews) > 0)
                        {
                          Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEYCODE_ENTER  DISPLAY_VIEW_EPISODE");
                          this.channelListView.dispatchKeyEvent(paramKeyEvent);
                        }
                        else
                        {
                          Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEYCODE_ENTER  other");
                          if ((!this.isPlayerComplete) && (this.isEnableFfordAndRwind))
                          {
                            this.displayViews = (0x4 | this.displayViews);
                            this.seekbar.onInputEvent(paramKeyEvent);
                            this.title.onInputEvent(paramKeyEvent);
                          }
                          else if (!this.isEnableFfordAndRwind)
                          {
                            this.displayViews = (0x4 | this.displayViews);
                            this.seekbar.display();
                            this.title.display();
                            continue;
                            Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEYCODE_CHANNEL_UP");
                            if (!this.mutexSearchChannel)
                            {
                              this.mutexSearchChannel = true;
                              changeVideoToNextChannel();
                              this.mutexSearchChannel = false;
                              continue;
                              Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyDown KEYCODE_CHANNEL_DOWN");
                              if (!this.mutexSearchChannel)
                              {
                                this.mutexSearchChannel = true;
                                changeVideoToPrevChannel();
                                this.mutexSearchChannel = false;
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
      i = paramKeyEvent.getAction();
      bool = false;
    }
    while (i != 1);
    Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp code:" + paramKeyEvent.getKeyCode() + ", displayViews:" + this.displayViews);
    setBulletScreenQrCodeShowRule(paramKeyEvent);
    int j;
    int k;
    switch (paramKeyEvent.getKeyCode())
    {
    default:
      Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp 事件未被处理");
      return false;
    case 4:
      if (((0x4000 & this.displayViews) > 0) || ((0x8000 & this.displayViews) > 0))
      {
        Logger.i("MplayerTimeshiftView", "onInputEvent() keyDown KEYCODE_BACK  DISPLAY_VIEW_QRCODE");
        if (!this.isDisplayQrCode)
          if ((0x4000 & this.displayViews) > 0)
          {
            j = 1;
            if ((0x8000 & this.displayViews) <= 0)
              break label2120;
            k = 1;
            label1981: if (j != 0)
            {
              this.displayViews = (0xFFFFBFFF & this.displayViews);
              this.qrCodeView.setVisibility(8);
            }
            if (k != 0)
            {
              this.displayViews = (0xFFFF7FFF & this.displayViews);
              this.qrCodeView.setVisibility(8);
            }
            if (((0x10 & this.displayViews) > 0) || ((0x200 & this.displayViews) > 0) || ((0x2000 & this.displayViews) > 0) || ((0x20 & this.displayViews) > 0) || ((0x8 & this.displayViews) > 0) || ((0x40 & this.displayViews) > 0) || ((0x4 & this.displayViews) > 0))
              break label2126;
          }
      }
      break;
    case 19:
    case 20:
    case 21:
    case 22:
    case 23:
    case 66:
    case 82:
    case 7:
    case 8:
    case 9:
    case 10:
    case 11:
    case 12:
    case 13:
    case 14:
    case 15:
    case 16:
    }
    while (true)
    {
      Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp 事件被处理");
      return true;
      j = 0;
      break;
      label2120: k = 0;
      break label1981;
      label2126: this.isDisplayQrCode = false;
      if ((0x10 & this.displayViews) > 0)
      {
        Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEYCODE_BACK  DISPLAY_VIEW_ERROR_NOTICE");
        this.displayViews = (0xFFFFFFEF & this.displayViews);
        this.lsnr.onErrorNoticeViewDisplay(4, "", "", true);
        doQuitPlay();
      }
      else if ((0x200 & this.displayViews) > 0)
      {
        Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEYCODE_BACK  DISPLAY_VIEW_ORDER_NOTICE");
        this.displayViews = (0xFFFFFDFF & this.displayViews);
        doQuitPlay();
        this.lsnr.onOrderNoticeViewDisplay(4, false);
      }
      else if ((0x2000 & this.displayViews) > 0)
      {
        this.interactiveWebView.dispatchKeyEvent(paramKeyEvent);
      }
      else if ((0x20 & this.displayViews) > 0)
      {
        Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEYCODE_BACK  DISPLAY_VIEW_RESERVATION_DIALOG");
      }
      else if ((0x8 & this.displayViews) > 0)
      {
        Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEYCODE_BACK  DISPLAY_VIEW_EPISODE");
        setChannelListVisibility(4);
        this.displayViews = (0xFFFFFFF7 & this.displayViews);
      }
      else if ((0x40 & this.displayViews) > 0)
      {
        if (this.menuView.dispatchKeyEvent(paramKeyEvent))
        {
          Logger.i("MplayerTimeshiftView", "onInputEvent() keyUp KEYCODE_BACK  DISPLAY_VIEW_MENU");
        }
        else
        {
          Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEYCODE_BACK  DISPLAY_VIEW_CONTROLL_PANEL");
          setMenuViewVisibility(4);
          this.displayViews = (0xFFFFFFBF & this.displayViews);
        }
      }
      else if ((0x4 & this.displayViews) > 0)
      {
        Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEYCODE_BACK  DISPLAY_VIEW_CONTROLL_PANEL");
        this.seekbar.onInputEvent(paramKeyEvent);
        this.title.onInputEvent(paramKeyEvent);
        this.displayViews = (0xFFFFFFFB & this.displayViews);
      }
      else
      {
        Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEYCODE_BACK  other");
        if ((0x100 & this.displayViews) > 0)
        {
          this.quitView.setVisibility(4);
          this.quitView.bringToFront();
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
            Logger.i("MplayerTimeshiftView", "onInputEvent() keyUp KEY_UP  DISPLAY_VIEW_QUIT");
            return this.quitView.dispatchKeyEvent(paramKeyEvent);
          }
          if ((0x40 & this.displayViews) > 0)
          {
            Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEY_UP  DISPLAY_VIEW_MENU");
            this.menuView.dispatchKeyEvent(paramKeyEvent);
          }
          else if ((0x8 & this.displayViews) > 0)
          {
            Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEY_UP  DISPLAY_VIEW_EPISODE");
            this.channelListView.dispatchKeyEvent(paramKeyEvent);
          }
          else if ((0x100 & this.displayViews) > 0)
          {
            Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEY_UP  DISPLAY_VIEW_QUIT");
          }
          else if ((0x10 & this.displayViews) > 0)
          {
            Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEY_UP  DISPLAY_VIEW_ERROR_NOTICE");
          }
          else if ((0x200 & this.displayViews) > 0)
          {
            Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEY_UP  DISPLAY_VIEW_ORDER_NOTICE");
          }
          else if ((0x20 & this.displayViews) > 0)
          {
            Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEY_UP  DISPLAY_VIEW_RESERVATION_DIALOG");
          }
          else
          {
            Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEY_UP  other");
            continue;
            if ((0x100 & this.displayViews) > 0)
            {
              Logger.i("MplayerTimeshiftView", "onInputEvent() keyUp KEY_UP  DISPLAY_VIEW_QUIT");
              return this.quitView.dispatchKeyEvent(paramKeyEvent);
            }
            if ((0x40 & this.displayViews) > 0)
            {
              Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEY_DOWN  DISPLAY_VIEW_MENU");
              this.menuView.dispatchKeyEvent(paramKeyEvent);
            }
            else if ((0x8 & this.displayViews) > 0)
            {
              Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEY_DOWN  DISPLAY_VIEW_EPISODE");
              this.channelListView.dispatchKeyEvent(paramKeyEvent);
            }
            else if ((0x100 & this.displayViews) > 0)
            {
              Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEY_DOWN  DISPLAY_VIEW_QUIT");
            }
            else if ((0x10 & this.displayViews) > 0)
            {
              Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEY_DOWN  DISPLAY_VIEW_ERROR_NOTICE");
            }
            else if ((0x200 & this.displayViews) > 0)
            {
              Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEY_DOWN  DISPLAY_VIEW_ORDER_NOTICE");
            }
            else if ((0x20 & this.displayViews) > 0)
            {
              Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEY_DOWN  DISPLAY_VIEW_RESERVATION_DIALOG");
            }
            else
            {
              Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEY_DOWN  other");
              continue;
              if ((0x100 & this.displayViews) > 0)
              {
                Logger.i("MplayerTimeshiftView", "onInputEvent() keyUp KEY_UP  DISPLAY_VIEW_QUIT");
                return this.quitView.dispatchKeyEvent(paramKeyEvent);
              }
              if ((0x40 & this.displayViews) > 0)
              {
                Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEY_LEFT   DISPLAY_VIEW_MENU");
                this.menuView.dispatchKeyEvent(paramKeyEvent);
              }
              else if ((0x8 & this.displayViews) > 0)
              {
                Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEY_LEFT  DISPLAY_VIEW_EPISODE");
                this.channelListView.dispatchKeyEvent(paramKeyEvent);
              }
              else if ((0x100 & this.displayViews) > 0)
              {
                Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEY_LEFT  DISPLAY_VIEW_QUIT");
              }
              else if ((0x10 & this.displayViews) > 0)
              {
                Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEY_LEFT  DISPLAY_VIEW_ERROR_NOTICE");
                this.lsnr.onMplayerDialogViewDiaplay(paramKeyEvent);
              }
              else if ((0x20 & this.displayViews) > 0)
              {
                Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEY_LEFT  DISPLAY_VIEW_RESERVATION_DIALOG");
              }
              else if ((0x200 & this.displayViews) > 0)
              {
                Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEY_LEFT  DISPLAY_VIEW_ORDER_NOTICE");
              }
              else
              {
                Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEY_LEFT  other");
                if ((!this.isPlayerComplete) && (this.isEnableFfordAndRwind))
                {
                  this.seekbar.onInputEvent(paramKeyEvent);
                  this.title.onInputEvent(paramKeyEvent);
                  continue;
                  if ((0x100 & this.displayViews) > 0)
                  {
                    Logger.i("MplayerTimeshiftView", "onInputEvent() keyUp KEY_UP  DISPLAY_VIEW_QUIT");
                    return this.quitView.dispatchKeyEvent(paramKeyEvent);
                  }
                  if ((0x8 & this.displayViews) > 0)
                  {
                    Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEY_RIGHT  DISPLAY_VIEW_EPISODE");
                    this.channelListView.dispatchKeyEvent(paramKeyEvent);
                  }
                  else if ((0x100 & this.displayViews) > 0)
                  {
                    Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEY_RIGHT  DISPLAY_VIEW_QUIT");
                  }
                  else if ((0x10 & this.displayViews) > 0)
                  {
                    Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEY_RIGHT  DISPLAY_VIEW_ERROR_NOTICE");
                    this.lsnr.onMplayerDialogViewDiaplay(paramKeyEvent);
                  }
                  else if ((0x200 & this.displayViews) > 0)
                  {
                    Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEY_RIGHT  DISPLAY_VIEW_ORDER_NOTICE");
                  }
                  else if ((0x20 & this.displayViews) > 0)
                  {
                    Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEY_RIGHT  DISPLAY_VIEW_RESERVATION_DIALOG");
                  }
                  else
                  {
                    Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEY_RIGHT  other");
                    if ((!this.isPlayerComplete) && (this.isEnableFfordAndRwind))
                    {
                      this.seekbar.onInputEvent(paramKeyEvent);
                      this.title.onInputEvent(paramKeyEvent);
                      continue;
                      if ((0x100 & this.displayViews) > 0)
                      {
                        Logger.i("MplayerTimeshiftView", "onInputEvent() keyUp KEY_UP  DISPLAY_VIEW_QUIT");
                        return this.quitView.dispatchKeyEvent(paramKeyEvent);
                      }
                      if ((0x40 & this.displayViews) > 0)
                      {
                        Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEYCODE_ENTER  DISPLAY_VIEW_MENU");
                        this.menuView.dispatchKeyEvent(paramKeyEvent);
                      }
                      else if ((0x10 & this.displayViews) > 0)
                      {
                        Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEYCODE_ENTER  DISPLAY_VIEW_ERROR_NOTICE");
                        this.lsnr.onErrorNoticeViewDisplay(4, "", "", true);
                        this.lsnr.onMplayerDialogViewDiaplay(paramKeyEvent);
                      }
                      else if ((0x200 & this.displayViews) > 0)
                      {
                        Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEYCODE_ENTER  DISPLAY_VIEW_ORDER_NOTICE");
                        this.lsnr.onOrderNoticeViewDisplay(4, false);
                        this.displayViews = (0xFFFFFDFF & this.displayViews);
                        this.lsnr.onWebDialogDisplay(0, WebUrlBuilder.getBuyUrl(this.orderUrl));
                        this.displayViews = (0x400 | this.displayViews);
                      }
                      else if ((0x20 & this.displayViews) > 0)
                      {
                        Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEYCODE_ENTER  DISPLAY_VIEW_RESERVATION_DIALOG");
                      }
                      else if ((0x8 & this.displayViews) > 0)
                      {
                        Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEYCODE_ENTER  DISPLAY_VIEW_EPISODE");
                        this.channelListView.dispatchKeyEvent(paramKeyEvent);
                      }
                      else
                      {
                        Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEYCODE_ENTER  other");
                        if ((!this.isPlayerComplete) && (this.isEnableFfordAndRwind))
                        {
                          this.displayViews = (0x4 | this.displayViews);
                          this.seekbar.onInputEvent(paramKeyEvent);
                          this.title.onInputEvent(paramKeyEvent);
                        }
                        else if (!this.isEnableFfordAndRwind)
                        {
                          this.displayViews = (0x4 | this.displayViews);
                          this.seekbar.display();
                          this.title.display();
                          continue;
                          if (((0x1000 & this.displayViews) > 0) || ((0x2000 & this.displayViews) > 0))
                          {
                            hideControllPanel();
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
                              Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEYCODE_MENU DISPLAY_VIEW_EPISODE");
                              setChannelListVisibility(4);
                              this.displayViews = (0xFFFFFFF7 & this.displayViews);
                            }
                            if ((0x1000 & this.displayViews) > 0)
                              showInteractiveMsgWeb();
                          }
                          else if ((this.menuView != null) && (this.menuView.getItemFlag() != 0))
                          {
                            hideControllPanel();
                            if ((0x40 & this.displayViews) > 0)
                            {
                              setMenuViewVisibility(4);
                              this.displayViews = (0xFFFFFFBF & this.displayViews);
                            }
                            else if ((0x100 & this.displayViews) > 0)
                            {
                              this.quitView.setVisibility(4);
                              this.displayViews = (0xFFFFFEFF & this.displayViews);
                              setMenuViewVisibility(0);
                              this.displayViews = (0x40 | this.displayViews);
                            }
                            else if ((0x10 & this.displayViews) > 0)
                            {
                              Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEYCODE_MENU DISPLAY_VIEW_ERROR_NOTICE");
                            }
                            else if ((0x200 & this.displayViews) > 0)
                            {
                              Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEYCODE_MENU DISPLAY_VIEW_ORDER_NOTICE");
                            }
                            else if ((0x20 & this.displayViews) > 0)
                            {
                              Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEYCODE_MENU DISPLAY_VIEW_RESERVATION_DIALOG");
                            }
                            else if ((0x8 & this.displayViews) > 0)
                            {
                              Logger.i("MplayerTimeshiftView", "dispatchKeyEvent() keyUp KEYCODE_MENU DISPLAY_VIEW_EPISODE");
                              setChannelListVisibility(4);
                              this.displayViews = (0xFFFFFFF7 & this.displayViews);
                              setMenuViewVisibility(0);
                              this.displayViews = (0x40 | this.displayViews);
                            }
                            else
                            {
                              setMenuViewVisibility(0);
                              this.displayViews = (0x40 | this.displayViews);
                              continue;
                              changeVideoToNumberedChannel("0");
                              continue;
                              changeVideoToNumberedChannel("1");
                              continue;
                              changeVideoToNumberedChannel("2");
                              continue;
                              changeVideoToNumberedChannel("3");
                              continue;
                              changeVideoToNumberedChannel("4");
                              continue;
                              changeVideoToNumberedChannel("5");
                              continue;
                              changeVideoToNumberedChannel("6");
                              continue;
                              changeVideoToNumberedChannel("7");
                              continue;
                              changeVideoToNumberedChannel("8");
                              continue;
                              changeVideoToNumberedChannel("9");
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
      this.displayViews = (0xFFFFFFFD & this.displayViews);
    }
    if ((0x8 & this.displayViews) > 0)
    {
      this.displayViews = (0xFFFFFFF7 & this.displayViews);
      setChannelListVisibility(8);
    }
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
    if ((0x400 & this.displayViews) > 0)
    {
      this.lsnr.onWebDialogDisplay(4, null);
      this.displayViews = (0xFFFFFBFF & this.displayViews);
    }
  }

  protected boolean isLivePlay()
  {
    return "Event".equals(this.type_name);
  }

  public void onConfirm(int paramInt)
  {
    Logger.i("MplayerTimeshiftView", "MplayerDialogViewListener.onConfirm() focusPosition=" + paramInt);
    this.displayViews = (0xFFFFFFEF & this.displayViews);
    if (paramInt == 0)
    {
      doQuitPlay();
      return;
    }
    Logger.i("MplayerTimeshiftView", "MplayerDialogViewListener.onConfirm() focusPositionqq=" + paramInt);
    Intent localIntent = new Intent(this.context, UserFeedbackActivity.class);
    localIntent.putExtra("xulPageId", "UserFeedbackPage");
    localIntent.putExtra("xulData", "");
    localIntent.addFlags(8388608);
    this.context.startActivity(localIntent);
    doQuitPlay();
  }

  public void onEventStopPlayer()
  {
    Logger.i("MplayerTimeshiftView", "onEventStopPlayer() airPlayState: " + this.airPlayState);
    if (this.airPlayState != "not_play")
    {
      stopCDNTimerReport();
      reportCDNIF2Complete(this.bufferPeriodCount);
      reportPlayerState(8);
      this.isReportStop = true;
    }
    this.airPlayState = "not_play";
    this.tstvSeekTime = 0L;
    setQrCodeInvisible();
    if (!this.isPlayerSucceed);
    do
    {
      do
      {
        do
          return;
        while (this.playTime <= 0L);
        this.playTime = (timestamp() - this.playTime);
      }
      while ((this.currentPlayCategory == null) || (this.pParams.nns_videoInfo.videoId == null) || (this.playTime <= 0L));
      boolean bool = "common_channel".equals(this.channelListView.getCurrentCategoryId());
      Logger.i("MplayerTimeshiftView", "onEventStopPlayer() addTimeshiftPlayRecord categoryId:" + this.pParams.nns_videoInfo.categoryId + ",videoId: " + this.pParams.nns_videoInfo.videoId + " name: " + this.pParams.nns_videoInfo.name);
      UserCPLLogic.getInstance().addTimeshiftPlayRecord(bool, this.pParams.nns_videoInfo.categoryId, this.pParams.nns_videoInfo.videoId, this.playTime / 1000L);
      this.playTime = 0L;
    }
    while (this.mBarrageView == null);
    this.mBarrageView.stop();
  }

  public void onRequestVideoPlayError(ServerApiCommonError paramServerApiCommonError)
  {
    this.isStartToCheckBuffering = false;
    if (checkErrorNoticeViewCanDisplay())
    {
      hideCenterViews();
      String str = "MplayerTimeshiftView.SccmsApiRequestVideoPlayTaskListener.onError error:" + paramServerApiCommonError.toString();
      this.lsnr.onErrorNoticeViewDisplay(0, "1002005", str, true);
      reportError("1002005", str);
      this.displayViews = (0x10 | this.displayViews);
      Logger.i("MplayerTimeshiftView", "SccmsApiRequestVideoPlayTaskListener.onError()");
      this.dialogMsg = ApplicationException.getErrorDiscrib("1002005");
    }
  }

  public void onRequestVideoPlaySuccess(PlayInfo paramPlayInfo)
  {
    this.pInfo = paramPlayInfo;
    if ((isNeedRetry()) && (retryRequest()))
    {
      reportCDNIF1AccessCMSFail(this.cdnAccessCMSApiUrl, "104000");
      return;
    }
    if (!checkPlayInfoData())
    {
      setAccessCMSFinalInvoke();
      reportCDNIF1AccessCMSFail(this.cdnAccessCMSApiUrl, "104000");
      return;
    }
    reportCDNIF1AccessCMSSuccess(this.cdnAccessCMSApiUrl);
    this.cdnAccessCMSSuccess = true;
    UserCPLLogic.getInstance().setLastPlayMgtvFileId(this.pInfo.fileId);
    this.playUrl = this.pInfo.playUrl;
    Logger.i("MplayerTimeshiftView", "debug SccmsApiRequestVideoPlayTaskListener.onSuccess()() isFirstLoading:" + this.isFirstLoading + ", isBuffering:" + this.isBuffering);
    if ((this.isFirstLoading) && (!this.isBuffering))
    {
      this.mdc.onPlayerCreate(this.playUrl);
      this.isBuffering = true;
      this.mdc.onPlayerBufferBegin(BufferEnum.BUFFER_AUTH, this.tstvBeginTime);
      ReportState.setStateStartTime(7);
      ReportState.setBufferCause(1);
    }
    reportPlayQuality(10);
    ReportState.setStateStartTime(2);
    this.mpCore.setVideoURI(formatUrl(this.playUrl));
  }

  public void onWebDialogClose()
  {
    Logger.i("MplayerTimeshiftView", "onWebDialogClose");
    this.displayViews = (0xFFFFFBFF & this.displayViews);
    doQuitPlay();
  }

  public void onWebDialogResult()
  {
    Logger.i("MplayerTimeshiftView", "onWebDialogResult");
    this.authInvokeByRefresh = true;
    restartCurrentChannel();
  }

  public void playerTimerSlowTask(Message paramMessage)
  {
    if (this.mpCore == null)
    {
      Logger.i("MplayerTimeshiftView", "playerTimerSlowTask() mpCore is null");
      return;
    }
    if (this.isPlayerComplete)
    {
      Logger.i("MplayerTimeshiftView", "playerTimerSlowTask() isPlayerComplete is true");
      return;
    }
    this.timerCount = (1 + this.timerCount);
    this.currentPlayPos = this.mpCore.getCurrentPosition();
    checkInBufferState();
    checkDisplayNotice();
    checkNoOperationForFJYD();
    checkDelayChangeChannel();
    refreshControlPannel();
    checkInteractiveShow();
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
              if (localUserKey.key.equalsIgnoreCase("token_status"))
              {
                str1 = localUserKey.value;
              }
              else if ("statistic".equals(localUserKey.key))
              {
                String str2 = localUserKey.value;
                Logger.i("MplayerTimeshiftView", "statistic=" + str2);
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
      localPurchaseParam.setPackageId(this.package_id);
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

  public void refreshChannelList()
  {
    Logger.i("MplayerTimeshiftView", "initData() refreshChannelList()");
    SccmsApiGetChannelListV2TaskListener localSccmsApiGetChannelListV2TaskListener = new SccmsApiGetChannelListV2TaskListener();
    ServerApiManager.i().APIGetChannelListV2(this.pParams.nns_videoInfo.packageId, "1000", localSccmsApiGetChannelListV2TaskListener);
  }

  public void resetChannelListToFullPlayMode()
  {
    Logger.i("MplayerTimeshiftView", "resetChannelListToFullPlayMode()");
    if (this.channelListView == null)
      return;
    this.timeshiftPreviewer.setVisibility(4);
    setBackgroundDrawable(null);
    this.channelListView.requestLayout();
  }

  public void resetChannelListToPreviewMode()
  {
    Logger.i("MplayerTimeshiftView", "resetChannelListToPreviewMode()");
    if (this.channelListView == null)
      return;
    RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)this.channelListView.getLayoutParams();
    localLayoutParams.width = App.Operation(257.0F);
    localLayoutParams.height = App.Operation(551.0F);
    localLayoutParams.topMargin = App.Operation(80.0F);
    localLayoutParams.bottomMargin = App.Operation(0.0F);
    localLayoutParams.leftMargin = App.Operation(10.0F);
    this.timeshiftPreviewer.setVisibility(0);
    setBackgroundDrawable(new BitmapDrawable(BitmapCache.getInstance(this.context).getBitmapFromCache("moive_channel_list.png")));
    this.channelListView.requestLayout();
    setChannelListVisibility(0);
    this.displayViews = (0x8 | this.displayViews);
  }

  public void restartCurrentChannel()
  {
    Logger.i("MplayerTimeshiftView", "restartCurrentChannel()");
    this.isStartToCheckBuffering = false;
    stopToPlay();
    displayLoadingInfo(0);
    cancelTasks();
    onEventStopPlayer();
    ChannelItemInfoV2 localChannelItemInfoV2 = (ChannelItemInfoV2)this.channelListDataV2.channelList.get(this.channelSelectedIndex);
    if (localChannelItemInfoV2 == null)
    {
      Logger.i("MplayerTimeshiftView", "restartCurrentChannel() cInfo is null. channelSelectedIndex:" + this.channelSelectedIndex);
      return;
    }
    try
    {
      this.pParams.nns_index = 0;
      if (this.pParams.nns_videoInfo == null)
      {
        this.pParams.nns_videoInfo = new VideoInfo();
        this.pParams.nns_videoInfo.packageId = "TimeShift";
      }
      this.pParams.played_time = 0L;
      this.pParams.nns_videoInfo.videoId = localChannelItemInfoV2.id;
      this.pParams.nns_videoInfo.categoryId = localChannelItemInfoV2.categoryId;
      this.pParams.nns_videoInfo.name = localChannelItemInfoV2.name;
      this.pParams.nns_videoInfo.film_name = localChannelItemInfoV2.name;
      this.pParams.nns_videoInfo.videoType = 1;
      this.pParams.channel_index = String.valueOf(localChannelItemInfoV2.channelNum);
      this.pParams.mode = 6;
      this.pParams.real_default_back_pos = localChannelItemInfoV2.tsLimitPos;
      this.pParams.real_max_back_time_len = localChannelItemInfoV2.tsLimitMax;
      this.pParams.real_min_back_time_len = localChannelItemInfoV2.tsLimitMin;
      addPlayAuthTask(this.pParams.nns_videoInfo.videoId);
      this.currentPlayChannel = this.pParams.nns_videoInfo.videoId;
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void setOnPlayerControllEventCallback(MplayerV2.OnPlayerControllEventCallback paramOnPlayerControllEventCallback)
  {
    this.onPlayerControllEventCallback = paramOnPlayerControllEventCallback;
  }

  public void setTimeMap(Map<String, String> paramMap)
  {
    this.timeHashMap = paramMap;
  }

  public int startToPlay()
  {
    Logger.i("MplayerTimeshiftView", "startToPlay()");
    this.startPlayTime = System.currentTimeMillis();
    this.isFullPlayMode = true;
    resetUIMode();
    displayLoadingInfo(0);
    this.lsnr.onPreLoadingViewDisplay(0, this.pParams.nns_videoInfo.film_name);
    showChannelView();
    return 0;
  }

  private class GetBarrageTimerTask extends TimerTask
  {
    private GetBarrageTimerTask()
    {
    }

    public void run()
    {
      MplayerTimeshiftView.this.getBarrageData();
    }
  }

  class MplayerSeekBarListener
    implements MplayerSeekBar.IMplayerSeekBarListener
  {
    Runnable run = new Runnable()
    {
      public void run()
      {
        MplayerTimeshiftView.this.playState.setVisibility(8);
      }
    };

    MplayerSeekBarListener()
    {
    }

    public String getCurrentProgramName(long paramLong)
    {
      return MplayerTimeshiftView.this.findCurrentProgramByPlayPos(paramLong);
    }

    public String getPosDiscribByPlayPos(long paramLong)
    {
      return MplayerTimeshiftView.this.formateTime(paramLong);
    }

    public void notifyCurrentState(int paramInt)
    {
      if (paramInt == -1)
      {
        MplayerTimeshiftView.this.playState.setVisibility(8);
        return;
      }
      if (paramInt == -2)
      {
        MplayerTimeshiftView.this.playState.setVisibility(0);
        return;
      }
      if (paramInt == 1)
      {
        MplayerTimeshiftView.this.playState.setVisibility(8);
        return;
      }
      MplayerTimeshiftView.this.displayLoadingInfo(4);
      MplayerTimeshiftView.this.playState.setCurrentMode(paramInt);
    }

    public void onPlayToPreNode()
    {
    }

    public void onUserPauseOrStart()
    {
      if (MplayerTimeshiftView.this.mpCore.isPlaying())
      {
        MplayerTimeshiftView.this.doPauseVideo();
        MplayerTimeshiftView.this.reportPlayerState(4);
        return;
      }
      MplayerTimeshiftView.this.doStartVideo();
      MplayerTimeshiftView.this.reportPlayerState(5);
    }

    public void onUserSeekEnd(long paramLong)
    {
      Logger.i("MplayerTimeshiftView", "MplayerSeekBarListener.onUserSeekEnd() seekPos:" + paramLong);
      ReportState.setStateStartTime(7);
      ReportState.setBufferCause(3);
      MplayerTimeshiftView.this.mpCore.pause();
      MplayerTimeshiftView.this.displayLoadingInfo(0);
      MplayerTimeshiftView.access$2502(MplayerTimeshiftView.this, 14);
      MplayerTimeshiftView.access$2602(MplayerTimeshiftView.this, true);
      MplayerTimeshiftView.access$1902(MplayerTimeshiftView.this, MplayerTimeshiftView.this.mpCore.getCurrentPosition());
      MplayerTimeshiftView.access$10002(MplayerTimeshiftView.this, MplayerTimeshiftView.this.currentPlayPos);
      if (MplayerTimeshiftView.this.getServerTime() - MplayerTimeshiftView.this.tstvRealOffset - paramLong < 3000L)
      {
        MplayerTimeshiftView.access$10202(MplayerTimeshiftView.this, 0L);
        if (MplayerTimeshiftView.this.tryToDirectlyPlay())
          return;
      }
      MplayerTimeshiftView.this.pParams.mode = 6;
      MplayerTimeshiftView.access$10202(MplayerTimeshiftView.this, paramLong);
      MplayerTimeshiftView.this.reportPlayerState(6);
      MplayerTimeshiftView.this.reportPlayerState(8);
      MplayerTimeshiftView.this.reportCDNIF2Complete(MplayerTimeshiftView.this.bufferPeriodCount);
      MplayerTimeshiftView.this.stopCDNTimerReport();
      ReportInfo.getInstance().setEntranceSrc("I10");
      MplayerTimeshiftView.this.mplayerRetryLogic.initRetryParams();
      MplayerTimeshiftView.this.onChangePlayUrlEvent();
      MplayerTimeshiftView.access$3302(MplayerTimeshiftView.this, false);
      MplayerTimeshiftView.this.addPlayRequestTask(MplayerTimeshiftView.this.tstvSeekTime);
    }

    public void onUserSeekStart()
    {
      long l = MplayerTimeshiftView.this.tstvBeginTime + MplayerTimeshiftView.this.currentPlayPos;
      MplayerTimeshiftView.access$2602(MplayerTimeshiftView.this, false);
      if (MplayerTimeshiftView.this.isBuffering)
        MplayerTimeshiftView.this.mdc.onPlayerBufferEnd(l);
      ReportState.setStateStartTime(6);
      MplayerTimeshiftView.this.mdc.onPlayerSeekBegin(l);
      MplayerTimeshiftView.access$9902(MplayerTimeshiftView.this, MplayerTimeshiftView.this.tstvBeginTime + MplayerTimeshiftView.this.currentPlayPos);
    }

    public int playProgress2uiProgress(long paramLong)
    {
      if (MplayerTimeshiftView.this.minStepLen == 0)
        return 0;
      return (int)(MplayerTimeshiftView.this.seekbar.getMax() * (MplayerTimeshiftView.this.maxProgressPos - (MplayerTimeshiftView.this.getServerTime() - MplayerTimeshiftView.this.tstvRealOffset - paramLong)) / MplayerTimeshiftView.this.maxProgressPos);
    }

    public long uiProgress2PlayProgress(int paramInt)
    {
      return MplayerTimeshiftView.this.getServerTime() - MplayerTimeshiftView.this.tstvRealOffset - (MplayerTimeshiftView.this.maxProgressPos - paramInt * MplayerTimeshiftView.this.minStepLen);
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
          MplayerTimeshiftView.this.mpCore.changeVideoLayoutTo16v9();
          GlobalLogic.getInstance().setVideoLayoutRatio(paramString2);
          MplayerTimeshiftView.this.menuView.setItemState(4, paramString2);
        }
      do
      {
        do
        {
          return;
          if ("4v3".equals(paramString2))
          {
            MplayerTimeshiftView.this.mpCore.changeVideoLayoutTo4v3();
            GlobalLogic.getInstance().setVideoLayoutRatio(paramString2);
            MplayerTimeshiftView.this.menuView.setItemState(4, paramString2);
            return;
          }
          if ("full".equals(paramString2))
          {
            MplayerTimeshiftView.this.mpCore.changeVideoLayoutToFullScreen();
            GlobalLogic.getInstance().setVideoLayoutRatio(paramString2);
            MplayerTimeshiftView.this.menuView.setItemState(4, paramString2);
            return;
          }
          if ("default".equals(paramString2))
          {
            MplayerTimeshiftView.this.mpCore.changeVideoLayoutToDefault();
            GlobalLogic.getInstance().setVideoLayoutRatio(paramString2);
            MplayerTimeshiftView.this.menuView.setItemState(4, paramString2);
            return;
          }
        }
        while (!"235".equals(paramString2));
        MplayerTimeshiftView.this.mpCore.changeVideoLayoutTo2351();
        GlobalLogic.getInstance().setVideoLayoutRatio(paramString2);
        MplayerTimeshiftView.this.menuView.setItemState(4, paramString2);
        return;
        if ("interactive".equalsIgnoreCase(paramString1))
        {
          Logger.d("MplayerTimeshiftView", "click interactive menu...");
          MplayerTimeshiftView.this.setMenuViewVisibility(4);
          MplayerTimeshiftView.access$4172(MplayerTimeshiftView.this, -65);
          MplayerTimeshiftView.this.showHistoryInteractiveMsgesWeb();
          return;
        }
      }
      while (!"bullet_screen".equalsIgnoreCase(paramString1));
      Logger.d("MplayerTimeshiftView", "click KEY_BULLET_SCREEN menu...value=" + paramString2);
      GlobalLogic.getInstance().setBulletScreenStatus(paramString2);
      MplayerTimeshiftView.this.menuView.setItemState(512, paramString2);
      MplayerTimeshiftView.access$2902(MplayerTimeshiftView.this, paramString2.equals("open"));
      MplayerTimeshiftView.this.changeQrCodeStatus(MplayerTimeshiftView.this.isBarrageOpen);
      MplayerTimeshiftView.this.switchBarrage(MplayerTimeshiftView.this.isBarrageOpen);
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
      Logger.i("MplayerTimeshiftView", "debug SccmsApiGetChannelListV2TaskListener.onError() error:" + paramServerApiCommonError.toString());
      String str = "MplayerTimeshiftView.SccmsApiGetChannelListV2TaskListener() error:" + paramServerApiCommonError.toString();
      MplayerTimeshiftView.this.mplayerRetryLogic.initRetryParams();
      MplayerTimeshiftView.this.onChangePlayUrlEvent();
      if (MplayerTimeshiftView.this.checkErrorNoticeViewCanDisplay())
      {
        MplayerTimeshiftView.this.hideCenterViews();
        MplayerTimeshiftView.this.mdc.onError("1002007");
        if (MplayerTimeshiftView.this.isFullPlayMode);
        for (boolean bool = true; ; bool = false)
        {
          MplayerTimeshiftView.this.lsnr.onErrorNoticeViewDisplay(0, "1002007", str, bool);
          MplayerTimeshiftView.this.reportError("1002007", "ISccmsApiGetChannelListV2TaskListener.onError", paramServerApiTaskInfo, paramServerApiCommonError);
          MplayerTimeshiftView.access$4176(MplayerTimeshiftView.this, 16);
          MplayerTimeshiftView.access$4202(MplayerTimeshiftView.this, ApplicationException.getErrorDiscrib("1002007"));
          return;
        }
      }
      MplayerTimeshiftView.access$8102(MplayerTimeshiftView.this, "1003004");
      MplayerTimeshiftView.access$8202(MplayerTimeshiftView.this, str);
      MplayerTimeshiftView.this.mdc.onError("1003004");
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ChannelInfoV2 paramChannelInfoV2)
    {
      MplayerTimeshiftView.this.dealChannelListDataReturn(paramChannelInfoV2);
    }
  }

  class SccmsApiGetPlaybillTaskListener
    implements SccmsApiGetPlaybillTask.ISccmsApiGetPlaybillTaskListener
  {
    SccmsApiGetPlaybillTaskListener()
    {
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i("MplayerTimeshiftView", "debug SccmsApiGetPlaybillTaskListener.onError() error:" + paramServerApiCommonError.toString());
      if (!MplayerTimeshiftView.this.apiTaskControl.checkTaskValid(paramServerApiTaskInfo.getTaskId(), MplayerTimeshiftView.this.pParams.nns_videoInfo.videoId))
        Logger.i("MplayerTimeshiftView", "debug SccmsApiGetPlaybillTaskListener.onError() invalid task:" + paramServerApiTaskInfo.getTaskId() + ", videoId:" + MplayerTimeshiftView.this.pParams.nns_videoInfo.videoId);
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, ArrayList<PlayBillStruct> paramArrayList)
    {
      if (!MplayerTimeshiftView.this.apiTaskControl.checkTaskValid(paramServerApiTaskInfo.getTaskId(), MplayerTimeshiftView.this.pParams.nns_videoInfo.videoId))
      {
        Logger.i("MplayerTimeshiftView", "debug SccmsApiGetPlaybillTaskListener.onSuccess() invalid task:" + paramServerApiTaskInfo.getTaskId() + ", videoId:" + MplayerTimeshiftView.this.pParams.nns_videoInfo.videoId);
        return;
      }
      MplayerTimeshiftView.access$9202(MplayerTimeshiftView.this, Tools.time2sec(Tools.getTimeString()));
      Logger.i("MplayerTimeshiftView", "mdEndTime=" + MplayerTimeshiftView.this.mdEndTime + ",mdStartTime=" + MplayerTimeshiftView.this.mdStartTime);
      if (paramArrayList == null)
      {
        Logger.i("MplayerTimeshiftView", "SccmsApiGetPlaybillTaskListener.onSuccess() result is null");
        if (MplayerTimeshiftView.this.checkErrorNoticeViewCanDisplay())
        {
          MplayerTimeshiftView.this.hideCenterViews();
          if (MplayerTimeshiftView.this.isFullPlayMode);
          for (boolean bool = true; ; bool = false)
          {
            MplayerTimeshiftView.this.lsnr.onErrorNoticeViewDisplay(0, "1002007", "SccmsApiGetPlaybillTaskListener.onSuccess() result is null", bool);
            MplayerTimeshiftView.this.reportError("1002007", "SccmsApiGetPlaybillTaskListener.onSuccess() result is null", paramServerApiTaskInfo, null);
            MplayerTimeshiftView.access$4176(MplayerTimeshiftView.this, 16);
            MplayerTimeshiftView.this.mdc.onError("1002007");
            MplayerTimeshiftView.access$4202(MplayerTimeshiftView.this, ApplicationException.getErrorDiscrib("1002007"));
            return;
          }
        }
        MplayerTimeshiftView.access$8102(MplayerTimeshiftView.this, "1002007");
        MplayerTimeshiftView.access$8202(MplayerTimeshiftView.this, "SccmsApiGetPlaybillTaskListener.onSuccess() result is null");
        MplayerTimeshiftView.this.mdc.onError("1002007");
        return;
      }
      Logger.i("MplayerTimeshiftView", "debug SccmsApiGetPlaybillTaskListener.onSuccess() result:" + paramArrayList.toString());
      if (paramArrayList.size() < 3)
        Logger.i("MplayerTimeshiftView", "SccmsApiGetPlaybillTaskListener.onSuccess() result.size():" + paramArrayList.size());
      MplayerTimeshiftView.this.loadProgramData(paramArrayList);
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
      Logger.i("MplayerTimeshiftView", "debug SccmsApiRequestVideoPlayV2TaskListener.onError() error:" + paramServerApiCommonError.toString());
      if (!MplayerTimeshiftView.this.apiTaskControl.checkTaskValid(paramServerApiTaskInfo.getTaskId(), MplayerTimeshiftView.this.pParams.nns_videoInfo.videoId))
      {
        Logger.i("MplayerTimeshiftView", "debug SccmsApiRequestVideoPlayV2TaskListener.onError() invalid task");
        return;
      }
      MplayerTimeshiftView.access$8502(MplayerTimeshiftView.this, paramServerApiTaskInfo.getHttpRequestUrl());
      if (MplayerTimeshiftView.this.retryRequest())
      {
        MplayerTimeshiftView.this.reportCDNIF1AccessCMSFail(paramServerApiTaskInfo.getHttpRequestUrl(), MplayerTimeshiftView.access$8700(MplayerTimeshiftView.this, paramServerApiCommonError));
        return;
      }
      MplayerTimeshiftView.this.setAccessCMSFinalInvoke();
      MplayerTimeshiftView.this.reportCDNIF1AccessCMSFail(paramServerApiTaskInfo.getHttpRequestUrl(), MplayerTimeshiftView.access$8700(MplayerTimeshiftView.this, paramServerApiCommonError));
      MplayerTimeshiftView.this.mplayerRetryLogic.print("请求返回失败弹框");
      MplayerTimeshiftView.this.onRequestVideoPlayError(paramServerApiCommonError);
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, PlayInfoV2 paramPlayInfoV2)
    {
      if (!MplayerTimeshiftView.this.apiTaskControl.checkTaskValid(paramServerApiTaskInfo.getTaskId(), MplayerTimeshiftView.this.pParams.nns_videoInfo.videoId))
        Logger.i("MplayerTimeshiftView", "SccmsApiRequestVideoPlayV2TaskListener.onSuccess() invalid task:" + paramServerApiTaskInfo.getTaskId() + ", videoId:" + MplayerTimeshiftView.this.pParams.nns_videoInfo.videoId);
      do
      {
        return;
        MplayerTimeshiftView.access$8502(MplayerTimeshiftView.this, paramServerApiTaskInfo.getHttpRequestUrl());
        if (paramPlayInfoV2 == null)
          Logger.i("MplayerTimeshiftView", "SccmsApiRequestVideoPlayV2TaskListener.onSuccess() result is null");
        Logger.i("MplayerTimeshiftView", "SccmsApiRequestVideoPlayV2TaskListener.onSuccess() result:" + paramPlayInfoV2.toString());
        MplayerTimeshiftView.access$9002(MplayerTimeshiftView.this, paramPlayInfoV2.playUrl);
        MplayerTimeshiftView.access$9102(MplayerTimeshiftView.this, 0);
        MplayerTimeshiftView.this.mplayerRetryLogic.svrip = paramPlayInfoV2.svrip;
        MplayerTimeshiftView.this.mplayerRetryLogic.print("请求返回成功: state = " + paramPlayInfoV2.state.state);
        if (MplayerTimeshiftView.this.processUserStatus(paramPlayInfoV2))
        {
          MplayerTimeshiftView.this.lsnr.onPreLoadingViewDisplay(4, "");
          MplayerTimeshiftView.this.setAccessCMSFinalInvoke();
          MplayerTimeshiftView.this.reportCDNIF1AccessCMSFail(MplayerTimeshiftView.this.cdnAccessCMSApiUrl, "104000");
          return;
        }
      }
      while (MplayerTimeshiftView.this.tryToDirectlyPlay());
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
      MplayerTimeshiftView.this.onRequestVideoPlaySuccess(localPlayInfo);
    }
  }

  class TokenDialogListener
    implements TokenDialog.TokenDialogListener
  {
    TokenDialogListener()
    {
    }

    public void onCancel(int paramInt)
    {
      MplayerTimeshiftView.this.doQuitPlay();
    }

    public void onPositiveClick(int paramInt)
    {
      if ((paramInt == 20003) || (paramInt == 2009))
        MplayerTimeshiftView.this.reportBuy();
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.media.player.MplayerTimeshiftView
 * JD-Core Version:    0.6.2
 */