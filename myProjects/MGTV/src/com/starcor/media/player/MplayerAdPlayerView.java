package com.starcor.media.player;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.starcor.core.domain.PlayerIntentParams;
import com.starcor.core.domain.VideoIndex;
import com.starcor.core.domain.VideoInfo;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.Logger;
import com.starcor.core.utils.MgtvAdErrorTools;
import com.starcor.hunan.App;
import com.starcor.hunan.MplayerEx;
import com.starcor.hunan.MplayerEx.ScreenMode;
import com.starcor.hunan.ads.AdEntity;
import com.starcor.hunan.ads.AdInfoParser;
import com.starcor.hunan.ads.Creative;
import com.starcor.hunan.ads.GetAdInfoTask.IGetAdInfoListener;
import com.starcor.hunan.ads.GetAdUrlFromMgTask.IMGCmsGetAdInfoTaskListener;
import com.starcor.hunan.ads.MediaFile;
import com.starcor.hunan.ads.MgAdInfo;
import com.starcor.hunan.ads.MgAdInfo.Action;
import com.starcor.hunan.ads.UrlTools;
import com.starcor.hunan.service.SystemTimeManager;
import com.starcor.message.MessageHandler;
import com.starcor.mgtv.boss.WebUrlBuilder;
import com.starcor.player.MediaPlayerAdapter;
import com.starcor.player.MediaPlayerAdapter.OnCompletionListener;
import com.starcor.player.MediaPlayerAdapter.OnErrorListener;
import com.starcor.player.MediaPlayerAdapter.OnPreparedListener;
import com.starcor.report.Column.Column;
import com.starcor.report.ReportMessage;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTask;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.xul.XulUtils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class MplayerAdPlayerView extends RelativeLayout
{
  private static final int ACTION_REFRESH_TIME = 1000;
  protected static final int ACTION_SET_PAUSE_IMG = 1001;
  private static final int ACTION_STOP_PLAY_AD = 1002;
  private static final int DELAY = 2000;
  private static final int REQUEST_FIRST_TIME = 0;
  private static final int REQUEST_SECOND_TIME = 1;
  protected static final String TAG = MplayerAdPlayerView.class.getSimpleName();
  private static final String TYPE_BUFFER = "5";
  private static final String TYPE_FLOAT = "4";
  private static final String TYPE_MIDROLL = "2";
  private static final String TYPE_PAUSE = "6";
  private static final String TYPE_POSTROLL = "3";
  private static final String TYPE_PREROLL = "1";
  private static int pauseMediaIndex = -1;
  private static int videoMediaIndex = -1;
  private String AD_TYPE_PIC = "pic";
  private String AD_TYPE_VIDEO = "video";
  private ImageView adPause;
  int ad_index = 0;
  private ArrayList<Integer> ads;
  private ArrayList<Creative> creativeList;
  private MediaFile currentMediaFile = null;
  private long currentTime = 0L;
  private String currentVideoUrl = "";
  private long duration = 0L;
  private boolean hasCleared = true;
  private boolean hasProcessedStop = false;
  private MgAdInfo info;
  private boolean isPlayPauseAdDrectly = false;
  private OnAdPlayerListener lsnr;
  private Context mContext;
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default:
        return;
      case 1000:
        MplayerAdPlayerView.this.time.bringToFront();
        MplayerAdPlayerView.this.time.setText("广告时间：" + MplayerAdPlayerView.this.duration + " 秒");
        MplayerAdPlayerView.access$110(MplayerAdPlayerView.this);
        if (MplayerAdPlayerView.this.duration == 0L)
          MplayerAdPlayerView.access$102(MplayerAdPlayerView.this, 1L);
        sendEmptyMessageDelayed(1000, 1000L);
        return;
      case 1001:
      }
      Bitmap localBitmap = (Bitmap)paramAnonymousMessage.obj;
      MplayerAdPlayerView.this.adPause.setLayoutParams(new RelativeLayout.LayoutParams(localBitmap.getWidth(), localBitmap.getHeight()));
      MplayerAdPlayerView.this.adPause.setImageBitmap(localBitmap);
      MplayerAdPlayerView.this.adPause.setVisibility(0);
    }
  };
  private MplayerEx.ScreenMode mScreenMode;
  private MediaPlayerCore mpCore;
  private PlayerIntentParams pParams;
  private ArrayList<MediaFile> pauseMediaFileList = new ArrayList();
  private String playAdStartDate = "";
  private long playAdStartTimeS = 0L;
  private String taskParam = "";
  private TextView time;
  private long totalTime;
  private String urlPath = "";
  private ArrayList<MediaFile> videoMediaFileList = new ArrayList();

  public MplayerAdPlayerView(Context paramContext)
  {
    super(paramContext);
    this.mContext = paramContext;
    this.mScreenMode = MplayerEx.ScreenMode.FULL_SCREEN;
    initViews();
  }

  public MplayerAdPlayerView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mContext = paramContext;
    this.mScreenMode = MplayerEx.ScreenMode.FULL_SCREEN;
    initViews();
  }

  public MplayerAdPlayerView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.mContext = paramContext;
    this.mScreenMode = MplayerEx.ScreenMode.FULL_SCREEN;
    initViews();
  }

  private void clearAds()
  {
    this.info = null;
  }

  private void getCurPlayTime()
  {
    MediaPlayerCore localMediaPlayerCore = this.mpCore;
    int i = 0;
    if (localMediaPlayerCore != null)
    {
      int j = this.mpCore.getCurrentPosition();
      ArrayList localArrayList = this.videoMediaFileList;
      i = 0;
      if (localArrayList != null)
      {
        int k = this.videoMediaFileList.size();
        int m = videoMediaIndex;
        i = 0;
        if (m >= 0)
        {
          int n = videoMediaIndex;
          i = 0;
          if (n <= k)
          {
            int i1 = j / 1000;
            if (videoMediaIndex > 0)
              for (int i2 = 0; i2 < videoMediaIndex; i2++)
                i1 += getCurrentTime((MediaFile)this.videoMediaFileList.get(i2));
            i = i1;
          }
        }
      }
    }
    this.currentTime = i;
  }

  private int getCurrentTime(MediaFile paramMediaFile)
  {
    ArrayList localArrayList = this.info.getAllPreRollCreative();
    int i = localArrayList.size();
    for (int j = 0; j < i; j++)
    {
      Creative localCreative = (Creative)localArrayList.get(j);
      Iterator localIterator = localCreative.getMediaFiles().iterator();
      while (localIterator.hasNext())
      {
        MediaFile localMediaFile = (MediaFile)localIterator.next();
        if ((localMediaFile != null) && (paramMediaFile != null) && (localMediaFile.equals(paramMediaFile)) && (localCreative.getDuration() > 0))
          return localCreative.getDuration();
      }
    }
    return 0;
  }

  private String getIndexId(PlayerIntentParams paramPlayerIntentParams)
  {
    if (paramPlayerIntentParams == null)
      return "";
    if ((paramPlayerIntentParams.nns_mediaIndexList == null) || (paramPlayerIntentParams.nns_mediaIndexList.size() <= 0))
      return "";
    for (int i = 0; i < paramPlayerIntentParams.nns_mediaIndexList.size(); i++)
      if ((paramPlayerIntentParams.nns_mediaIndexList.get(i) != null) && (((VideoIndex)paramPlayerIntentParams.nns_mediaIndexList.get(i)).index == paramPlayerIntentParams.nns_index))
        return ((VideoIndex)paramPlayerIntentParams.nns_mediaIndexList.get(i)).import_id;
    return "";
  }

  private String getIndexName(PlayerIntentParams paramPlayerIntentParams)
  {
    if (paramPlayerIntentParams == null)
      return "";
    if ((paramPlayerIntentParams.nns_mediaIndexList == null) || (paramPlayerIntentParams.nns_mediaIndexList.size() <= 0))
      return "";
    for (int i = 0; i < paramPlayerIntentParams.nns_mediaIndexList.size(); i++)
      if ((paramPlayerIntentParams.nns_mediaIndexList.get(i) != null) && (((VideoIndex)paramPlayerIntentParams.nns_mediaIndexList.get(i)).index == paramPlayerIntentParams.nns_index))
        return ((VideoIndex)paramPlayerIntentParams.nns_mediaIndexList.get(i)).import_name;
    return "";
  }

  private String getIndexOnlineTime(PlayerIntentParams paramPlayerIntentParams)
  {
    if (paramPlayerIntentParams == null)
      return "";
    if (paramPlayerIntentParams.nns_videoInfo == null)
      return "";
    String str;
    try
    {
      str = paramPlayerIntentParams.nns_videoInfo.showTime;
      if ((TextUtils.isEmpty(str)) || (str.length() < 4))
        return "";
    }
    catch (Exception localException)
    {
      while (true)
        str = "";
    }
    return str.substring(0, 4);
  }

  private int getIndexVtt(PlayerIntentParams paramPlayerIntentParams)
  {
    if (paramPlayerIntentParams == null)
      return 0;
    if ((paramPlayerIntentParams.nns_mediaIndexList == null) || (paramPlayerIntentParams.nns_mediaIndexList.size() <= 0))
      return 0;
    for (int i = 0; i < paramPlayerIntentParams.nns_mediaIndexList.size(); i++)
      if ((paramPlayerIntentParams.nns_mediaIndexList.get(i) != null) && (((VideoIndex)paramPlayerIntentParams.nns_mediaIndexList.get(i)).index == paramPlayerIntentParams.nns_index))
        return ((VideoIndex)paramPlayerIntentParams.nns_mediaIndexList.get(i)).timeLen;
    return 0;
  }

  private MediaFile getNextMediaFile(int paramInt)
  {
    if (paramInt == 0)
      if ((this.videoMediaFileList != null) && (this.videoMediaFileList.size() > 0));
    do
    {
      do
      {
        do
        {
          return null;
          videoMediaIndex = 1 + videoMediaIndex;
        }
        while (this.videoMediaFileList.size() <= videoMediaIndex);
        return (MediaFile)this.videoMediaFileList.get(videoMediaIndex);
      }
      while ((this.pauseMediaFileList == null) || (this.pauseMediaFileList.size() <= 0));
      pauseMediaIndex = 1 + pauseMediaIndex;
    }
    while (this.pauseMediaFileList.size() <= pauseMediaIndex);
    return (MediaFile)this.pauseMediaFileList.get(pauseMediaIndex);
  }

  private static String getSafeString(String paramString)
  {
    if (paramString != null)
      return paramString;
    return "";
  }

  private String getSubType(PlayerIntentParams paramPlayerIntentParams)
  {
    if (paramPlayerIntentParams == null)
      return "";
    if (paramPlayerIntentParams.nns_videoInfo.kind != null)
      return paramPlayerIntentParams.nns_videoInfo.kind.replace("/", ",");
    return "";
  }

  private long getTotalTime()
  {
    return this.totalTime;
  }

  private void initPlayer()
  {
    this.mpCore = ((MediaPlayerCore)findViewById(2131165294));
    this.mpCore.setVisibility(8);
    bindMediaPlayerCore();
  }

  private void initViews()
  {
    LayoutInflater.from(this.mContext).inflate(2130903067, this);
    setGravity(17);
    setBackgroundColor(0);
    this.adPause = ((ImageView)findViewById(2131165297));
    this.adPause.setVisibility(4);
    this.time = ((TextView)findViewById(2131165295));
    this.time.getPaint().setTextSize(App.Operation(30.0F));
    this.time.setText("");
    initPlayer();
    layoutOnModeChanged(this.mScreenMode);
  }

  private void layoutOnModeChanged(MplayerEx.ScreenMode paramScreenMode)
  {
    int i = getActualPixels(paramScreenMode, 30);
    this.time.setTextSize(0, i);
    int j = getActualPixels(paramScreenMode, 17);
    int k = getActualPixels(paramScreenMode, 10);
    this.time.setPadding(j, k, j, k);
  }

  private void playPauseAd()
  {
    this.adPause.setImageBitmap(null);
    this.adPause.setVisibility(0);
    MediaFile localMediaFile;
    do
    {
      localMediaFile = getNextMediaFile(1);
      if (localMediaFile == null)
        return;
      this.currentMediaFile = localMediaFile;
    }
    while (TextUtils.isEmpty(localMediaFile.getUrl()));
    this.time.setVisibility(4);
    this.mpCore.setVisibility(4);
    showAdImgByUrl(this.adPause, localMediaFile.getUrl());
    processImpression(this.info.getImpressionbyMediaFile(localMediaFile));
    setBackgroundColor(0);
  }

  private void playVideoAd()
  {
    Logger.i(TAG, "playVideoAd");
    MediaFile localMediaFile = getNextMediaFile(0);
    this.currentMediaFile = localMediaFile;
    if (localMediaFile == null);
    while (true)
    {
      if (localMediaFile != null)
        break label73;
      reportAdPlay(1);
      processStopPlayAd();
      this.time.setVisibility(4);
      do
      {
        return;
        this.ad_index = (1 + this.ad_index);
      }
      while (localMediaFile == null);
      if (TextUtils.isEmpty(localMediaFile.getUrl()))
        break;
    }
    label73: processImpression(this.info.getImpressionbyMediaFile(localMediaFile));
    this.currentVideoUrl = localMediaFile.getUrl();
    this.mpCore.stop();
    this.mpCore.setVisibility(0);
    this.mpCore.bringToFront();
    try
    {
      this.mpCore.setVideoURI(this.currentVideoUrl);
      return;
    }
    catch (Exception localException)
    {
      reportAdPlay(1);
      processStopPlayAd();
    }
  }

  private void processAdResult(MgAdInfo paramMgAdInfo, int paramInt)
  {
    if (!isShown())
    {
      Logger.i(TAG, "processAdResult mplayer !isShown!");
      processStopPlayAd();
    }
    while (paramInt != 0)
      return;
    Logger.d(TAG, "processAdResult REQUEST_FIRST_TIME ");
    this.info = paramMgAdInfo;
    this.ads = this.info.getIdsbyAction(MgAdInfo.Action.PREROLL);
    if ((this.ads == null) || (this.ads.size() <= 0))
      processPreNotInTriggerCase();
    while (true)
    {
      this.duration = getTotalTime();
      playVideoAd();
      return;
      processPreInTriggerCase();
    }
  }

  private void processImpression(ArrayList<String> paramArrayList)
  {
    if ((paramArrayList == null) || (paramArrayList.size() <= 0));
    while (true)
    {
      return;
      Iterator localIterator = paramArrayList.iterator();
      while (localIterator.hasNext())
      {
        String str1 = (String)localIterator.next();
        if (!TextUtils.isEmpty(str1))
        {
          String str2 = str1;
          if (str2.contains("?"))
          {
            String str3 = UrlTools.getPath(str1);
            String str4 = UrlTools.getUrlParamsEncoded(str1);
            str2 = str3 + "?" + str4;
          }
          ServerApiManager.i().APIReportAdImpression(str2);
        }
      }
    }
  }

  private void processPreInTriggerCase()
  {
    int i = this.ads.size();
    ArrayList localArrayList = new ArrayList();
    for (int j = 0; j < i; j++)
    {
      Integer localInteger = (Integer)this.ads.get(j);
      localArrayList.addAll(this.info.getAdByAid(String.valueOf(localInteger)).getCreatives());
    }
    this.creativeList = localArrayList;
    int k = this.creativeList.size();
    for (int m = 0; m < k; m++)
    {
      Creative localCreative = (Creative)this.creativeList.get(m);
      if (localCreative.getDuration() > 0)
      {
        for (int n = 0; n < localCreative.getMediaFiles().size(); n++)
          this.videoMediaFileList.add(localCreative.getMediaFiles().get(n));
        this.totalTime += localCreative.getDuration();
      }
    }
  }

  private void processPreNotInTriggerCase()
  {
    this.creativeList = this.info.getAllPreRollCreative();
    int i = this.creativeList.size();
    for (int j = 0; j < i; j++)
    {
      Creative localCreative = (Creative)this.creativeList.get(j);
      if (localCreative.getDuration() > 0)
      {
        for (int k = 0; k < localCreative.getMediaFiles().size(); k++)
          this.videoMediaFileList.add(localCreative.getMediaFiles().get(k));
        this.totalTime += localCreative.getDuration();
      }
    }
  }

  private void processStopPlayAd()
  {
    destroy();
    if (this.hasProcessedStop);
    do
    {
      return;
      this.hasProcessedStop = true;
      this.mpCore.stop();
      this.mpCore.setVisibility(8);
      if ((this.mContext != null) && (((Activity)this.mContext).isFinishing()))
      {
        Logger.i(TAG, "processStopPlayAd: mplayer isFinishing!");
        return;
      }
      Logger.i(TAG, "processStopPlayAd: mplayer isShowing!");
      if (this.hasCleared)
        break;
    }
    while (this.lsnr == null);
    this.lsnr.OnCompletion();
    return;
    Logger.w(TAG, "ad player has already been cleared. Don't execute OnCompletion callback.");
  }

  public static void reportAdError(ErrorParams paramErrorParams)
  {
    ReportMessage localReportMessage = new ReportMessage();
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("act", "aderror");
      localJSONObject.put("err", paramErrorParams.ad_error_code);
      localJSONObject.put("perr", paramErrorParams.ad_perror);
      localJSONObject.put("bid", "3.1.10");
      localJSONObject.put("vid", paramErrorParams.video_id);
      localJSONObject.put("at", paramErrorParams.ad_pos_type);
      localJSONObject.put("param", paramErrorParams.request_params);
      localJSONObject.put("response", paramErrorParams.response);
      localJSONObject.put("errorMessage", getSafeString(paramErrorParams.errorMessage));
      localJSONObject.put("errorDesc", getSafeString(paramErrorParams.errorDesc));
      localJSONObject.put("type", paramErrorParams.ad_type);
      localJSONObject.put("url", paramErrorParams.request_url);
      localJSONObject.put("aid", paramErrorParams.slot_ad_id);
      localJSONObject.put("cid", paramErrorParams.card_id);
      localJSONObject.put("rd", new Random().nextInt(1000));
      localReportMessage.mExtData = new Column().buildJsonData(localJSONObject);
      localReportMessage.setDesc("广告错误事件");
      MessageHandler.instance().doNotify(localReportMessage);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        Logger.w("ReportService", "json exception!", localException);
    }
  }

  public int bindMediaPlayerCore()
  {
    if (this.mpCore == null)
    {
      Logger.i(TAG, "bindMediaPlayerCore() mpCore is null");
      return -1;
    }
    this.mpCore.setOnPreparedListener(new MediaPlayerAdapter.OnPreparedListener()
    {
      public void onPrepared(MediaPlayerAdapter paramAnonymousMediaPlayerAdapter)
      {
        if ((MplayerAdPlayerView.this.mContext != null) && (((Activity)MplayerAdPlayerView.this.mContext).isFinishing()))
        {
          Logger.i(MplayerAdPlayerView.TAG, "onPrepared mplayer isFinishing!");
          MplayerAdPlayerView.this.processStopPlayAd();
          return;
        }
        if (!MplayerAdPlayerView.this.isShown())
        {
          Logger.i(MplayerAdPlayerView.TAG, "onPrepared mplayer !isShown!");
          MplayerAdPlayerView.this.processStopPlayAd();
          return;
        }
        if (MplayerAdPlayerView.this.lsnr != null)
          MplayerAdPlayerView.this.lsnr.onPrePared();
        MplayerAdPlayerView.this.mpCore.changeVideoLayoutToFullScreen();
        MplayerAdPlayerView.this.mpCore.start();
        MplayerAdPlayerView.this.mpCore.requestLayout();
        MplayerAdPlayerView.this.time.bringToFront();
        MplayerAdPlayerView.this.mHandler.removeMessages(1000);
        MplayerAdPlayerView.this.mHandler.sendEmptyMessage(1000);
        MplayerAdPlayerView.this.time.setVisibility(0);
      }
    });
    this.mpCore.setOnErrorListener(new MediaPlayerAdapter.OnErrorListener()
    {
      public boolean onError(MediaPlayerAdapter paramAnonymousMediaPlayerAdapter, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        Logger.e(MplayerAdPlayerView.TAG, "what:" + paramAnonymousInt1 + ", extra:" + paramAnonymousInt2);
        MplayerAdPlayerView.this.mpCore.stop();
        MplayerAdPlayerView.this.reportAdPlay(2);
        MplayerAdPlayerView.this.processStopPlayAd();
        return true;
      }
    });
    this.mpCore.setOnCompletionListener(new MediaPlayerAdapter.OnCompletionListener()
    {
      public void onCompletion(MediaPlayerAdapter paramAnonymousMediaPlayerAdapter)
      {
        Logger.i(MplayerAdPlayerView.TAG, "videoView onCompletion(MediaPlayer mp)");
        MplayerAdPlayerView.this.playVideoAd();
      }
    });
    return 0;
  }

  public void changeMode(MplayerEx.ScreenMode paramScreenMode)
  {
    if (this.mScreenMode != paramScreenMode)
    {
      this.mScreenMode = paramScreenMode;
      layoutOnModeChanged(this.mScreenMode);
    }
  }

  public void destroy()
  {
    this.mHandler.removeMessages(1000);
    this.mHandler.removeMessages(1001);
    this.mHandler.removeMessages(1002);
    this.videoMediaFileList.clear();
    this.totalTime = 0L;
  }

  public int getActualPixels(MplayerEx.ScreenMode paramScreenMode, int paramInt)
  {
    return MplayerEx.getActualPixels(paramScreenMode, paramInt);
  }

  public boolean isAdPauseVisible()
  {
    return this.adPause.getVisibility() == 0;
  }

  public boolean isAdVideoViewVisible()
  {
    return this.mpCore.getVisibility() == 0;
  }

  public void reportAdExit()
  {
    getCurPlayTime();
    AdParams localAdParams = new AdParams(null);
    localAdParams.ad_pos_type = "1";
    localAdParams.video_id = this.pParams.nns_videoInfo.videoId;
    if (this.info != null)
    {
      localAdParams.slot_ad_id = this.info.getAIdbyMediaFile(this.currentMediaFile);
      localAdParams.ad_num = String.valueOf(this.info.getAd().size());
      localAdParams.ad_pre_num = String.valueOf(this.info.getAllPreRollCreative().size());
      localAdParams.ad_index = String.valueOf(this.ad_index);
    }
    localAdParams.ad_total_time = (this.currentTime + "");
    localAdParams.ad_start = this.playAdStartDate;
    localAdParams.ad_status = "3";
    localAdParams.ad_end = (SystemTimeManager.getCurrentServerDate() + SystemTimeManager.getCurrentServerTimeHMS());
    reportAdPlay(localAdParams);
  }

  public void reportAdPlay(int paramInt)
  {
    getCurPlayTime();
    AdParams localAdParams = new AdParams(null);
    localAdParams.ad_pos_type = "1";
    localAdParams.video_id = this.pParams.nns_videoInfo.videoId;
    if (this.info != null)
    {
      localAdParams.slot_ad_id = this.info.getAIdbyMediaFile(this.currentMediaFile);
      localAdParams.ad_num = String.valueOf(this.info.getAd().size());
      localAdParams.ad_pre_num = String.valueOf(this.info.getAllPreRollCreative().size());
      localAdParams.ad_index = String.valueOf(this.ad_index);
    }
    localAdParams.ad_total_time = (this.currentTime + "");
    localAdParams.ad_start = this.playAdStartDate;
    localAdParams.ad_status = String.valueOf(paramInt);
    localAdParams.ad_end = (SystemTimeManager.getCurrentServerDate() + SystemTimeManager.getCurrentServerTimeHMS());
    reportAdPlay(localAdParams);
  }

  public void reportAdPlay(AdParams paramAdParams)
  {
    ReportMessage localReportMessage = new ReportMessage();
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("act", "adplay");
      localJSONObject.put("bid", "3.1.10");
      localJSONObject.put("ad_status", paramAdParams.ad_status);
      localJSONObject.put("vid", paramAdParams.video_id);
      localJSONObject.put("at", paramAdParams.ad_pos_type);
      localJSONObject.put("aid", paramAdParams.slot_ad_id);
      localJSONObject.put("ad_num", paramAdParams.ad_num);
      localJSONObject.put("ad_pre_num", paramAdParams.ad_pre_num);
      localJSONObject.put("ad_total_time", paramAdParams.ad_total_time);
      localJSONObject.put("ad_start", paramAdParams.ad_start);
      localJSONObject.put("ad_end", paramAdParams.ad_end);
      localJSONObject.put("ad_index", paramAdParams.ad_index);
      if (paramAdParams.ad_status.equals("1"))
        localJSONObject.put("ad_index", "");
      localReportMessage.mExtData = new Column().buildJsonData(localJSONObject);
      localReportMessage.setDesc("广告播放事件");
      MessageHandler.instance().doNotify(localReportMessage);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        Logger.w("ReportService", "json exception!", localException);
    }
  }

  public void requestAdInfo(PlayerIntentParams paramPlayerIntentParams, int paramInt1, int paramInt2)
  {
    if (MgtvAdErrorTools.getErrorTimes() > 3)
    {
      if (XulUtils.timestamp() - MgtvAdErrorTools.lastErrorTime() < 300000L)
      {
        Logger.i(TAG, "请求广告出现错误超过三次， 五分钟内不进行重试");
        processStopPlayAd();
        return;
      }
      MgtvAdErrorTools.reset();
      Logger.i(TAG, "请求广告出现错误超过三次， 已经超过五分钟，继续请求广告");
    }
    this.pParams = paramPlayerIntentParams;
    String str1 = TAG;
    StringBuilder localStringBuilder = new StringBuilder().append("requestAdInfo isvip:");
    int i;
    if (GlobalLogic.getInstance().isVip())
      i = 1;
    while (true)
    {
      Logger.i(str1, i + ",ispay:" + paramInt1 + ",ispreview:" + paramInt2);
      ServerApiManager localServerApiManager = ServerApiManager.i();
      String str2 = GlobalLogic.getInstance().getPlayerAdId();
      String str3 = getIndexId(paramPlayerIntentParams);
      String str4 = getIndexName(paramPlayerIntentParams);
      String str5 = paramPlayerIntentParams.nns_videoInfo.import_id;
      String str6 = paramPlayerIntentParams.nns_videoInfo.name;
      String str7 = paramPlayerIntentParams.nns_videoInfo.packageId;
      String str8 = paramPlayerIntentParams.nns_videoInfo.name + "+" + getIndexName(paramPlayerIntentParams);
      String str9 = paramPlayerIntentParams.nns_videoInfo.keyWords;
      String str10 = getIndexOnlineTime(paramPlayerIntentParams);
      String str11 = getSubType(paramPlayerIntentParams);
      int j = getIndexVtt(paramPlayerIntentParams);
      GetAdUrlFromMgTaskListener localGetAdUrlFromMgTaskListener = new GetAdUrlFromMgTaskListener(0);
      ServerApiTask localServerApiTask = localServerApiManager.APIGetAdUrlFromMgTask(str2, "", 1, str3, str4, str5, str6, str7, "", str8, str9, str10, str11, "", j, paramInt1, paramInt2, localGetAdUrlFromMgTaskListener);
      try
      {
        UrlEncodedFormEntity localUrlEncodedFormEntity = new UrlEncodedFormEntity(localServerApiTask.getPostForm(), "UTF-8");
        this.taskParam = EntityUtils.toString(localUrlEncodedFormEntity, "UTF-8");
        this.taskParam = URLDecoder.decode(this.taskParam, "utf-8");
        this.playAdStartTimeS = (SystemTimeManager.getCurrentServerTime() / 1000L);
        this.playAdStartDate = (SystemTimeManager.getCurrentServerDate() + SystemTimeManager.getCurrentServerTimeHMS());
        return;
        i = 0;
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        while (true)
          localUnsupportedEncodingException.printStackTrace();
      }
      catch (IOException localIOException)
      {
        while (true)
          localIOException.printStackTrace();
      }
    }
  }

  public void setAdPlayerListener(OnAdPlayerListener paramOnAdPlayerListener)
  {
    this.lsnr = paramOnAdPlayerListener;
  }

  public void setVisibility(int paramInt)
  {
    if (paramInt == 0);
    while (true)
    {
      super.setVisibility(paramInt);
      return;
      this.adPause.setVisibility(4);
      this.mpCore.setVisibility(4);
      this.time.setVisibility(4);
    }
  }

  public void showAdImgByUrl(ImageView paramImageView, final String paramString)
  {
    new Thread()
    {
      // ERROR //
      public void run()
      {
        // Byte code:
        //   0: new 29	java/net/URL
        //   3: dup
        //   4: aload_0
        //   5: getfield 19	com/starcor/media/player/MplayerAdPlayerView$2:val$url	Ljava/lang/String;
        //   8: invokespecial 32	java/net/URL:<init>	(Ljava/lang/String;)V
        //   11: astore_1
        //   12: aload_1
        //   13: astore_2
        //   14: aload_2
        //   15: invokevirtual 36	java/net/URL:openConnection	()Ljava/net/URLConnection;
        //   18: checkcast 38	java/net/HttpURLConnection
        //   21: astore 4
        //   23: aload 4
        //   25: iconst_1
        //   26: invokevirtual 42	java/net/HttpURLConnection:setDoInput	(Z)V
        //   29: aload 4
        //   31: invokevirtual 45	java/net/HttpURLConnection:connect	()V
        //   34: aload 4
        //   36: invokevirtual 49	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
        //   39: astore 5
        //   41: aload 5
        //   43: invokestatic 55	android/graphics/BitmapFactory:decodeStream	(Ljava/io/InputStream;)Landroid/graphics/Bitmap;
        //   46: astore 6
        //   48: aload 5
        //   50: invokevirtual 60	java/io/InputStream:close	()V
        //   53: aload 6
        //   55: ifnonnull +16 -> 71
        //   58: return
        //   59: astore 9
        //   61: aload 9
        //   63: invokevirtual 63	java/net/MalformedURLException:printStackTrace	()V
        //   66: aconst_null
        //   67: astore_2
        //   68: goto -54 -> 14
        //   71: new 65	android/os/Message
        //   74: dup
        //   75: invokespecial 66	android/os/Message:<init>	()V
        //   78: astore 7
        //   80: aload 7
        //   82: aload 6
        //   84: putfield 70	android/os/Message:obj	Ljava/lang/Object;
        //   87: aload 7
        //   89: sipush 1001
        //   92: putfield 74	android/os/Message:what	I
        //   95: aload_0
        //   96: getfield 17	com/starcor/media/player/MplayerAdPlayerView$2:this$0	Lcom/starcor/media/player/MplayerAdPlayerView;
        //   99: invokestatic 78	com/starcor/media/player/MplayerAdPlayerView:access$300	(Lcom/starcor/media/player/MplayerAdPlayerView;)Landroid/os/Handler;
        //   102: aload 7
        //   104: invokevirtual 84	android/os/Handler:sendMessage	(Landroid/os/Message;)Z
        //   107: pop
        //   108: return
        //   109: astore_3
        //   110: aload_3
        //   111: invokevirtual 85	java/lang/Exception:printStackTrace	()V
        //   114: return
        //
        // Exception table:
        //   from	to	target	type
        //   0	12	59	java/net/MalformedURLException
        //   14	53	109	java/lang/Exception
        //   71	108	109	java/lang/Exception
      }
    }
    .start();
  }

  public void showPauseAd()
  {
    pauseMediaIndex = -1;
    this.adPause.setImageBitmap(null);
    this.adPause.setVisibility(0);
    if (this.info == null)
    {
      Logger.e(TAG, "无广告数据   不执行后续操作");
      return;
    }
    if (this.info.getIdsbyAction(MgAdInfo.Action.PAUSE).size() <= 0)
    {
      Logger.w(TAG, "无暂停广告 1");
      return;
    }
    ArrayList localArrayList = this.info.getAllPauseCreative();
    int i = localArrayList.size();
    if (i > 0)
    {
      this.pauseMediaFileList.clear();
      for (int j = 0; j < i; j++)
        this.pauseMediaFileList.addAll(((Creative)localArrayList.get(j)).getMediaFiles());
      playPauseAd();
      return;
    }
    Logger.w(TAG, "无暂停广告 2");
  }

  public void showVideoAd()
  {
    this.hasCleared = false;
    this.adPause.setVisibility(4);
    videoMediaIndex = -1;
    this.hasProcessedStop = false;
  }

  public void stop()
  {
    if (!this.hasCleared)
    {
      Logger.d(TAG, "stop ad");
      this.hasCleared = true;
      destroy();
      clearAds();
      this.mpCore.stop();
      this.mpCore.setVisibility(4);
      this.adPause.setVisibility(8);
      setVisibility(4);
      return;
    }
    Logger.i(TAG, "ad not start, or stopped. Don't stop again.");
  }

  private class AdParams
  {
    public String ad_end = "";
    public String ad_index = "0";
    public String ad_num = "";
    public String ad_pos_type = "";
    public String ad_pre_num = "";
    public String ad_start = "";
    public String ad_status = "";
    public String ad_total_time = "";
    public String slot_ad_id = "";
    public String video_id = "";

    private AdParams()
    {
    }
  }

  public static class ErrorParams
  {
    public String ad_error_code = "";
    public String ad_perror = "";
    public String ad_pos_type = "";
    public String ad_type = "";
    public String card_id = "";
    public String errorDesc = "";
    public String errorMessage = "";
    public String request_params = "";
    public String request_url = "";
    public String response = "";
    public String slot_ad_id = "";
    public String video_id = "";
  }

  public class GetAdInfoTaskListener
    implements GetAdInfoTask.IGetAdInfoListener
  {
    private int type = 0;

    public GetAdInfoTaskListener(int arg2)
    {
      int i;
      this.type = i;
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i(MplayerAdPlayerView.TAG, "GetAdInfoTaskListener onError");
      MgtvAdErrorTools.addErrorCount();
      if (this.type == 0)
        MplayerAdPlayerView.this.processStopPlayAd();
      MplayerAdPlayerView.ErrorParams localErrorParams = new MplayerAdPlayerView.ErrorParams();
      localErrorParams.ad_error_code = "102";
      localErrorParams.ad_perror = (paramServerApiCommonError.getHttpCode() + "");
      if (this.type == 0);
      for (String str = "1"; ; str = "6")
      {
        localErrorParams.ad_pos_type = str;
        if (MplayerAdPlayerView.this.info != null)
          localErrorParams.slot_ad_id = MplayerAdPlayerView.this.info.getAIdbyMediaFile(MplayerAdPlayerView.this.currentMediaFile);
        localErrorParams.card_id = "";
        localErrorParams.ad_type = MplayerAdPlayerView.this.AD_TYPE_VIDEO;
        localErrorParams.video_id = MplayerAdPlayerView.this.pParams.nns_videoInfo.videoId;
        localErrorParams.request_params = MplayerAdPlayerView.this.taskParam;
        localErrorParams.request_url = MplayerAdPlayerView.this.urlPath;
        localErrorParams.response = (paramServerApiCommonError.getHttpCode() + "");
        localErrorParams.errorMessage = paramServerApiCommonError.getHttpReason();
        localErrorParams.errorDesc = paramServerApiCommonError.getHttpReason();
        MplayerAdPlayerView.reportAdError(localErrorParams);
        return;
      }
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, MgAdInfo paramMgAdInfo)
    {
      Logger.i(MplayerAdPlayerView.TAG, "GetAdInfoTaskListener onSuccess result-- > " + paramMgAdInfo);
      MplayerAdPlayerView.this.processAdResult(paramMgAdInfo, this.type);
    }
  }

  public class GetAdUrlFromMgTaskListener
    implements GetAdUrlFromMgTask.IMGCmsGetAdInfoTaskListener
  {
    private int type = 0;

    public GetAdUrlFromMgTaskListener(int arg2)
    {
      int i;
      this.type = i;
    }

    public void onError(ServerApiTaskInfo paramServerApiTaskInfo, ServerApiCommonError paramServerApiCommonError)
    {
      Logger.i(MplayerAdPlayerView.TAG, "GetAdUrlFromMgTaskListener onError");
      MgtvAdErrorTools.addErrorCount();
      if (this.type == 0)
        MplayerAdPlayerView.this.processStopPlayAd();
      for (String str = "1"; ; str = "6")
      {
        MplayerAdPlayerView.ErrorParams localErrorParams = new MplayerAdPlayerView.ErrorParams();
        localErrorParams.ad_error_code = "102";
        localErrorParams.ad_perror = String.valueOf(paramServerApiCommonError.getHttpCode());
        localErrorParams.ad_pos_type = str;
        if (MplayerAdPlayerView.this.info != null)
          localErrorParams.slot_ad_id = MplayerAdPlayerView.this.info.getAIdbyMediaFile(MplayerAdPlayerView.this.currentMediaFile);
        localErrorParams.card_id = "";
        localErrorParams.ad_type = MplayerAdPlayerView.this.AD_TYPE_VIDEO;
        localErrorParams.video_id = MplayerAdPlayerView.this.pParams.nns_videoInfo.videoId;
        localErrorParams.request_params = MplayerAdPlayerView.this.taskParam;
        localErrorParams.request_url = WebUrlBuilder.getAdInfoUrl();
        localErrorParams.response = String.valueOf(paramServerApiCommonError.getHttpCode());
        localErrorParams.errorMessage = paramServerApiCommonError.getHttpReason();
        localErrorParams.errorDesc = paramServerApiCommonError.getHttpReason();
        MplayerAdPlayerView.reportAdError(localErrorParams);
        return;
      }
    }

    public void onSuccess(ServerApiTaskInfo paramServerApiTaskInfo, String paramString)
    {
      Logger.i(MplayerAdPlayerView.TAG, "GetAdUrlFromMgTaskListener onSuccess result-- > " + paramString);
      try
      {
        localJSONObject = new JSONObject(paramString);
        if ((localJSONObject.has("url")) && (localJSONObject.has("success")))
        {
          String str2 = localJSONObject.getString("url");
          MplayerAdPlayerView.access$902(MplayerAdPlayerView.this, UrlTools.getPath(str2));
          String str3 = UrlTools.getUrlParamsMap(str2);
          ServerApiManager.i().APIGetAdInfoTask(MplayerAdPlayerView.this.urlPath, str3, new MplayerAdPlayerView.GetAdInfoTaskListener(MplayerAdPlayerView.this, this.type));
          return;
        }
        if (localJSONObject.has("errorcode"))
        {
          MplayerAdPlayerView.ErrorParams localErrorParams = new MplayerAdPlayerView.ErrorParams();
          localErrorParams.ad_error_code = "101";
          localErrorParams.ad_perror = localJSONObject.getString("errorcode");
          if (this.type == 0)
          {
            str1 = "1";
            localErrorParams.ad_pos_type = str1;
            if (MplayerAdPlayerView.this.info != null)
            {
              localErrorParams.slot_ad_id = MplayerAdPlayerView.this.info.getAIdbyMediaFile(MplayerAdPlayerView.this.currentMediaFile);
              localErrorParams.card_id = MplayerAdPlayerView.this.info.getCreativeIdbyMediaFile(MplayerAdPlayerView.this.currentMediaFile);
            }
            localErrorParams.ad_type = MplayerAdPlayerView.this.AD_TYPE_PIC;
            localErrorParams.video_id = MplayerAdPlayerView.this.pParams.nns_videoInfo.videoId;
            localErrorParams.request_params = MplayerAdPlayerView.this.taskParam;
            localErrorParams.request_url = WebUrlBuilder.getAdInfoUrl();
            localErrorParams.response = "200";
            localErrorParams.errorMessage = "";
            localErrorParams.errorDesc = "";
            MplayerAdPlayerView.reportAdError(localErrorParams);
            return;
          }
        }
      }
      catch (JSONException localJSONException)
      {
        JSONObject localJSONObject;
        while (true)
        {
          localJSONException.printStackTrace();
          if (this.type != 0)
            break;
          MplayerAdPlayerView.this.processStopPlayAd();
          return;
          String str1 = "6";
        }
        if ((localJSONObject.has("init")) && (localJSONObject.has("ad")))
        {
          MgAdInfo localMgAdInfo = AdInfoParser.parseAdInfo(paramString);
          MplayerAdPlayerView.this.processAdResult(localMgAdInfo, this.type);
          return;
        }
        if (this.type == 0)
          MplayerAdPlayerView.this.processStopPlayAd();
      }
    }
  }

  public static abstract interface OnAdPlayerListener
  {
    public abstract void OnCompletion();

    public abstract void onPrePared();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.media.player.MplayerAdPlayerView
 * JD-Core Version:    0.6.2
 */