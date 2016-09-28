package com.sohu.logger.model;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.sohu.logger.SohuLoggerAgent;
import com.sohu.logger.bean.LogInfo;
import com.sohu.logger.bean.LogItem;
import com.sohu.logger.bean.PlayQualityLogItem;
import com.sohu.logger.bean.VideoPlayInfoLogItem;
import com.sohu.logger.bean.VideoPlayLogItem;
import com.sohu.logger.log.OutputLog;
import com.sohu.logger.util.LoggerUtil;
import com.sohu.logger.util.TimerUtil;
import java.util.Date;
import java.util.Locale;

public class SohuPlayerLogger
{
  private static int EXCEPTION_SUM = 0;
  private static final long MAX_DURATION = 20000000L;
  private static final long MIN_DURATION;
  private static int QUERY_DURATION = 200;
  private int exceptionCatchNum = 0;
  private HandlerThread handlerThread;
  private boolean isPaused;
  private int mBufferTime = 0;
  private Context mContext;
  private final HeartBeatHandler mHandler = new HeartBeatHandler();
  private boolean mIsSelfCountCarton = false;
  private boolean mIsTheSameVideo = false;
  private long mLastHeartBeatNo = 0L;
  private int mLastPosition = 0;
  private long mLoadingDuration;
  private LogItem mLogItemCount;
  private LogItem mLogItemMvv;
  private LogItem mLogItemQuality;
  private String mPlayId;
  private LogInfo mPlayInfo;
  private Handler mPositionHandler;
  private int mPositionSameCount = 0;
  private long mPreparedTime;
  private Runnable mQueryPositionRunnable = new Runnable()
  {
    public void run()
    {
      int i;
      if ((SohuPlayerLogger.this.mSohuPlayerInfo != null) && (SohuPlayerLogger.this.mSohuPlayerInfo.isPlaying()))
      {
        i = SohuPlayerLogger.this.getCurrentPosition();
        if (i != -1)
          break label143;
        SohuPlayerLogger.access$608(SohuPlayerLogger.this);
        SohuPlayerLogger.access$702(5000);
        if (SohuPlayerLogger.this.mLastPosition != i)
          break label162;
        SohuPlayerLogger.access$908(SohuPlayerLogger.this);
        if (SohuPlayerLogger.this.mPositionSameCount > 5)
          SohuPlayerLogger.access$1008(SohuPlayerLogger.this);
      }
      while (true)
      {
        SohuPlayerLogger.access$802(SohuPlayerLogger.this, i);
        if (SohuPlayerLogger.this.exceptionCatchNum < SohuPlayerLogger.EXCEPTION_SUM)
        {
          SohuPlayerLogger.this.initHandler();
          SohuPlayerLogger.this.mPositionHandler.postDelayed(SohuPlayerLogger.this.mQueryPositionRunnable, SohuPlayerLogger.QUERY_DURATION);
        }
        return;
        label143: SohuPlayerLogger.access$602(SohuPlayerLogger.this, 0);
        SohuPlayerLogger.access$702(200);
        break;
        label162: SohuPlayerLogger.access$902(SohuPlayerLogger.this, 0);
        if ((i <= SohuPlayerLogger.this.mLastPosition) || (i - SohuPlayerLogger.this.mLastPosition > 1000));
      }
    }
  };
  private long mSetUrlTime;
  private ISohuPlayerLogger mSohuPlayerInfo;
  private long mStartTime;
  private long mWatchDuration;

  public SohuPlayerLogger(Context paramContext, boolean paramBoolean, ISohuPlayerLogger paramISohuPlayerLogger)
  {
    this.mContext = paramContext.getApplicationContext();
    this.mIsSelfCountCarton = paramBoolean;
    this.mSohuPlayerInfo = paramISohuPlayerLogger;
    if (this.mSohuPlayerInfo != null)
      this.mPlayInfo = this.mSohuPlayerInfo.getPlayInfo();
  }

  private LogItem buildPlayCountLogItem()
  {
    this.mLogItemCount = new VideoPlayInfoLogItem();
    this.mLogItemCount.setParamsMapItem("t", System.currentTimeMillis());
    this.mLogItemCount.setParamsMapItem("videoId", this.mPlayInfo.getVideoID());
    this.mLogItemCount.setParamsMapItem("tvid", this.mPlayInfo.getTvID());
    this.mLogItemCount.setParamsMapItem("playlistId", this.mPlayInfo.getAlbumID());
    this.mLogItemCount.setParamsMapItem("categoryId", this.mPlayInfo.getCategoryID());
    this.mLogItemCount.setParamsMapItem("catecode", this.mPlayInfo.getCateCode());
    this.mLogItemCount.setParamsMapItem("type", this.mPlayInfo.getSiteName());
    this.mLogItemCount.setParamsMapItem("online", 0);
    this.mLogItemCount.setParamsMapItem("action", 0);
    this.mLogItemCount.setParamsMapItem("position", this.mSohuPlayerInfo.getCurrentPosition());
    this.mLogItemCount.setParamsMapItem("duration", this.mSohuPlayerInfo.getDuration());
    return this.mLogItemCount;
  }

  private LogItem buildPlayMVVLogItem()
  {
    this.mLogItemMvv = new VideoPlayLogItem();
    this.mLogItemMvv.setParamsMapItem("playid", this.mPlayId);
    this.mLogItemMvv.setParamsMapItem("playlistid", this.mPlayInfo.getAlbumID());
    this.mLogItemMvv.setParamsMapItem("vid", this.mPlayInfo.getVideoID());
    this.mLogItemMvv.setParamsMapItem("type", this.mPlayInfo.getSiteName());
    this.mLogItemMvv.setParamsMapItem("ltype", this.mPlayInfo.getType());
    this.mLogItemMvv.setParamsMapItem("version", this.mPlayInfo.getCurrentDefinitionType());
    this.mLogItemMvv.setParamsMapItem("cateid", this.mPlayInfo.getCategoryID());
    this.mLogItemMvv.setParamsMapItem("company", this.mPlayInfo.getCompany());
    this.mLogItemMvv.setParamsMapItem("channeled", this.mPlayInfo.getChanneled());
    this.mLogItemMvv.setParamsMapItem("language", this.mPlayInfo.getLanguage());
    this.mLogItemMvv.setParamsMapItem("area", this.mPlayInfo.getArea());
    this.mLogItemMvv.setParamsMapItem("wtype", this.mPlayInfo.getWatchType());
    this.mLogItemMvv.setParamsMapItem("catecode", this.mPlayInfo.getCateCode());
    this.mLogItemMvv.setParamsMapItem("enterid", this.mPlayInfo.getEnterID());
    this.mLogItemMvv.setParamsMapItem("isedit", this.mPlayInfo.getIsEdit());
    this.mLogItemMvv.setParamsMapItem("tvid", this.mPlayInfo.getTvID());
    LogItem localLogItem1 = this.mLogItemMvv;
    int i;
    LogItem localLogItem2;
    if (this.mPlayInfo.isFee())
    {
      i = 1;
      localLogItem1.setParamsMapItem("isfee", i);
      localLogItem2 = this.mLogItemMvv;
      if (!TextUtils.isEmpty(this.mPlayInfo.getIsSohu()))
        break label438;
    }
    label438: for (String str = "0"; ; str = this.mPlayInfo.getIsSohu())
    {
      localLogItem2.setParamsMapItem("issohu", str);
      this.mLogItemMvv.setParamsMapItem("ottcateid", this.mPlayInfo.getStatCode());
      this.mLogItemMvv.setParamsMapItem("subcateid", this.mPlayInfo.getSubCategoryID());
      this.mLogItemMvv.setParamsMapItem("vid", this.mPlayInfo.getVideoID());
      this.mLogItemMvv.setParamsMapItem("lcid", this.mPlayInfo.getLiveChannelID());
      this.mLogItemMvv.setParamsMapItem("lsid", this.mPlayInfo.getLiveStreamID());
      this.mLogItemMvv.setParamsMapItem("sourceid", LoggerUtil.getSourceId(this.mPlayInfo.getEnterID()));
      return this.mLogItemMvv;
      i = 0;
      break;
    }
  }

  private LogItem buildPlayQualityLogItem()
  {
    this.mLogItemQuality = new PlayQualityLogItem();
    this.mLogItemQuality.setParamsMapItem("playid", this.mPlayId);
    LogItem localLogItem = this.mLogItemQuality;
    String str;
    if (TextUtils.isEmpty(this.mPlayInfo.getAlbumID()))
    {
      str = "0";
      localLogItem.setParamsMapItem("sid", str);
      this.mLogItemQuality.setParamsMapItem("vid", this.mPlayInfo.getVideoID());
      this.mLogItemQuality.setParamsMapItem("playmode", this.mPlayInfo.getPlaymode());
      this.mLogItemQuality.setParamsMapItem("ltype", this.mPlayInfo.getType());
      if (!this.mPlayInfo.getCurrentUrl().toLowerCase(Locale.getDefault()).endsWith("mp4"))
        break label187;
      this.mLogItemQuality.setParamsMapItem("vtype", "mp4");
    }
    while (true)
    {
      this.mLogItemQuality.setParamsMapItem("version", this.mPlayInfo.getCurrentDefinitionType());
      this.mLogItemQuality.setParamsMapItem("playmode", this.mPlayInfo.getPlaymode());
      return this.mLogItemQuality;
      str = this.mPlayInfo.getAlbumID();
      break;
      label187: this.mLogItemQuality.setParamsMapItem("vtype", "m3u8");
    }
  }

  private void calculateWatchDuration()
  {
    long l = TimerUtil.getApproximateServerTime(this.mContext).getTime();
    OutputLog.e("SohuLoggerAgent", "计算观看时长  mStartTime:  " + this.mStartTime + "t :　" + l);
    if (this.mStartTime != 0L)
    {
      this.mWatchDuration += l - this.mStartTime;
      if ((this.mWatchDuration > 20000000L) || (this.mWatchDuration < 0L))
        this.mWatchDuration = 0L;
    }
    else
    {
      return;
    }
    this.mStartTime = l;
  }

  private long filterTime(long paramLong)
  {
    if (paramLong < 0L)
      paramLong = 0L;
    while (paramLong <= 20000000L)
      return paramLong;
    return 20000000L;
  }

  private int getCurrentPosition()
  {
    if (this.mSohuPlayerInfo != null)
      return this.mSohuPlayerInfo.getCurrentPosition();
    return 0;
  }

  private void initHandler()
  {
    if (this.mPositionHandler == null)
    {
      if (this.handlerThread == null)
      {
        this.handlerThread = new HandlerThread("query_position");
        this.handlerThread.start();
      }
      this.mPositionHandler = new Handler(this.handlerThread.getLooper())
      {
        public void handleMessage(Message paramAnonymousMessage)
        {
        }
      };
    }
  }

  private void logStart()
  {
    if ((this.mLogItemMvv == null) || (this.mPlayInfo == null))
      return;
    this.mHandler.start();
    this.mLogItemMvv.setParamsMapItem("msg", "videoStart");
    this.mLogItemMvv.setParamsMapItem("td", this.mSohuPlayerInfo.getDuration() / 1000);
    this.mLogItemMvv.setParamsMapItem("playtime", (499L + this.mLoadingDuration) / 1000L);
    SohuLoggerAgent.getInstance().onEvent(this.mContext, this.mLogItemMvv);
    SohuLoggerAgent.getInstance().onEvent(this.mContext, this.mLogItemCount);
    this.mLogItemQuality.setParamsMapItem("code", 5);
    this.mLogItemQuality.setParamsMapItem("ct", 0);
    this.mLogItemQuality.setParamsMapItem("duration", this.mLoadingDuration);
    SohuLoggerAgent.getInstance().onEvent(this.mContext, this.mLogItemQuality);
  }

  private void logVV()
  {
    if ((this.mLogItemMvv == null) || (this.mPlayInfo == null));
    while (this.mIsTheSameVideo)
      return;
    this.mLogItemMvv.setParamsMapItem("msg", "playCount");
    this.mLogItemMvv.setParamsMapItem("playtime", 0);
    SohuLoggerAgent.getInstance().onEvent(this.mContext, this.mLogItemMvv);
    this.mLogItemQuality.setParamsMapItem("code", 10);
    this.mLogItemQuality.setParamsMapItem("ct", 0);
    this.mLogItemQuality.setParamsMapItem("duration", 0);
    SohuLoggerAgent.getInstance().onEvent(this.mContext, this.mLogItemQuality);
  }

  public void bitrateChange()
  {
    this.mIsTheSameVideo = true;
  }

  public void complete()
  {
    this.isPaused = false;
    if (UserDataLogger.getInstance().isPlaying())
    {
      OutputLog.e("SohuLoggerAgent", "播放自动完成");
      UserDataLogger.getInstance().setPlaying(false);
      calculateWatchDuration();
      this.mStartTime = 0L;
      logHeartBeat();
      this.mHandler.stop();
      logEnd("videoends");
      if ((this.mIsSelfCountCarton) && (this.mPositionHandler != null))
        this.mPositionHandler.removeCallbacks(this.mQueryPositionRunnable);
    }
  }

  public void error()
  {
    UserDataLogger.getInstance().setPlaying(false);
    OutputLog.e("SohuLoggerAgent", "播放出现异常");
    calculateWatchDuration();
    this.mStartTime = 0L;
    logHeartBeat();
    if (this.mLogItemQuality != null)
    {
      this.mLogItemQuality.setParamsMapItem("ct", 0);
      this.mLogItemQuality.setParamsMapItem("duration", this.mLoadingDuration);
      this.mLogItemQuality.setParamsMapItem("code", 8);
      SohuLoggerAgent.getInstance().onEvent(this.mContext, this.mLogItemQuality);
    }
    logEnd("vclose");
    this.mHandler.stop();
    if ((this.mIsSelfCountCarton) && (this.mPositionHandler != null))
      this.mPositionHandler.removeCallbacks(this.mQueryPositionRunnable);
  }

  public LogInfo getPlayInfo()
  {
    if (this.mSohuPlayerInfo != null)
      return this.mSohuPlayerInfo.getPlayInfo();
    return this.mPlayInfo;
  }

  public void logBreakoff()
  {
    if ((this.mLogItemMvv == null) || (this.mPlayInfo == null))
      return;
    this.mLogItemMvv.setParamsMapItem("msg", "breakoff");
    this.mLogItemMvv.setParamsMapItem("playtime", this.mWatchDuration / 1000L);
    SohuLoggerAgent.getInstance().onEvent(this.mContext, this.mLogItemMvv);
  }

  public void logBufferInfo()
  {
    if ((this.mLogItemMvv == null) || (this.mPlayInfo == null))
      return;
    this.mBufferTime = (1 + this.mBufferTime);
    if (this.mBufferTime == 1)
    {
      calculateWatchDuration();
      this.mLogItemQuality.setParamsMapItem("ct", this.mBufferTime);
      this.mLogItemQuality.setParamsMapItem("duration", this.mWatchDuration);
      this.mLogItemQuality.setParamsMapItem("code", 6);
      SohuLoggerAgent.getInstance().onEvent(this.mContext, this.mLogItemQuality);
    }
    this.mLogItemQuality.setParamsMapItem("ct", this.mBufferTime);
    this.mLogItemQuality.setParamsMapItem("duration", this.mLoadingDuration);
    this.mLogItemQuality.setParamsMapItem("code", 4);
    SohuLoggerAgent.getInstance().onEvent(this.mContext, this.mLogItemQuality);
  }

  public void logEnd(String paramString)
  {
    if ((this.mLogItemMvv == null) || (this.mPlayInfo == null));
    do
    {
      do
        return;
      while (this.mIsTheSameVideo);
      this.mLogItemMvv.setParamsMapItem("msg", paramString);
      this.mLogItemMvv.setParamsMapItem("playtime", this.mWatchDuration / 1000L);
      SohuLoggerAgent.getInstance().onEvent(this.mContext, this.mLogItemMvv);
      if (this.mLogItemCount == null)
        buildPlayCountLogItem();
      this.mLogItemCount.setParamsMapItem("t", System.currentTimeMillis());
      this.mLogItemCount.setParamsMapItem("action", 1);
      this.mLogItemCount.setParamsMapItem("position", filterTime(this.mSohuPlayerInfo.getCurrentPosition()));
      this.mLogItemCount.setParamsMapItem("duration", filterTime(this.mSohuPlayerInfo.getDuration()));
      SohuLoggerAgent.getInstance();
      SohuLoggerAgent.getInstance().onEvent(this.mContext, this.mLogItemCount);
    }
    while (this.mLogItemQuality == null);
    this.mLogItemQuality.setParamsMapItem("ct", this.mBufferTime);
    this.mLogItemQuality.setParamsMapItem("duration", this.mWatchDuration);
    this.mLogItemQuality.setParamsMapItem("code", 7);
    SohuLoggerAgent.getInstance().onEvent(this.mContext, this.mLogItemQuality);
  }

  public void logHeartBeat()
  {
    if ((this.mLogItemMvv == null) || (this.mPlayInfo == null));
    long l;
    do
    {
      return;
      calculateWatchDuration();
      l = this.mWatchDuration / 120000L;
    }
    while ((l <= 0L) || (l == this.mLastHeartBeatNo));
    this.mLastHeartBeatNo = l;
    OutputLog.e("SohuLoggerAgent", "物理时长： " + this.mSohuPlayerInfo.getDuration() / 1000 + "mWatchDuration: " + this.mWatchDuration + "观看时长：" + 120L * this.mLastHeartBeatNo);
    this.mLogItemMvv.setParamsMapItem("td", this.mSohuPlayerInfo.getDuration() / 1000);
    this.mLogItemMvv.setParamsMapItem("msg", "caltime");
    this.mLogItemMvv.setParamsMapItem("playtime", 120L * this.mLastHeartBeatNo);
    SohuLoggerAgent.getInstance().onEvent(this.mContext, this.mLogItemMvv);
  }

  public void pause()
  {
    calculateWatchDuration();
    this.isPaused = true;
    this.mStartTime = 0L;
    OutputLog.e("SohuLoggerAgent", "暂停");
    logHeartBeat();
    this.mHandler.stop();
    if ((this.mIsSelfCountCarton) && (this.mPositionHandler != null))
      this.mPositionHandler.removeCallbacks(this.mQueryPositionRunnable);
  }

  public void reset()
  {
    this.isPaused = false;
    this.mIsTheSameVideo = false;
    this.mBufferTime = 0;
    this.mSetUrlTime = 0L;
    this.mStartTime = 0L;
    this.mPreparedTime = 0L;
    this.mLoadingDuration = 0L;
    this.mWatchDuration = 0L;
    this.mLastHeartBeatNo = 0L;
    if (this.mHandler != null)
      this.mHandler.stop();
    if (this.mPositionHandler != null)
      this.mPositionHandler.removeCallbacks(this.mQueryPositionRunnable);
  }

  public void setUrl()
  {
    if (this.mPlayInfo == null);
    do
    {
      return;
      UserDataLogger.getInstance().setPlaying(true);
      if (!this.mIsTheSameVideo)
        break;
      if (this.mLogItemQuality != null)
      {
        this.mLogItemQuality.setParamsMapItem("vid", this.mPlayInfo.getVideoID());
        this.mLogItemQuality.setParamsMapItem("version", this.mPlayInfo.getCurrentDefinitionType());
      }
      if (this.mLogItemMvv != null)
        this.mLogItemMvv.setParamsMapItem("vid", this.mPlayInfo.getVideoID());
    }
    while (this.mLogItemCount == null);
    this.mLogItemCount.setParamsMapItem("videoId", this.mPlayInfo.getVideoID());
    return;
    OutputLog.e("SohuLoggerAgent", "播放加载");
    reset();
    this.mPlayId = Long.toString(System.currentTimeMillis());
    this.mLogItemMvv = buildPlayMVVLogItem();
    this.mLogItemQuality = buildPlayQualityLogItem();
    this.mSetUrlTime = TimerUtil.getApproximateServerTime(this.mContext).getTime();
    logVV();
  }

  public void start()
  {
    if (this.mIsTheSameVideo)
      this.mIsTheSameVideo = false;
    do
    {
      return;
      if (this.isPaused)
      {
        this.mStartTime = TimerUtil.getApproximateServerTime(this.mContext).getTime();
        calculateWatchDuration();
        OutputLog.e("SohuLoggerAgent", "暂停后播放");
        logHeartBeat();
        this.mHandler.start();
        this.isPaused = false;
        return;
      }
      this.mStartTime = TimerUtil.getApproximateServerTime(this.mContext).getTime();
      OutputLog.e("SohuLoggerAgent", "播放  mStartTime:  " + this.mStartTime + "mWatchDuration:　" + this.mWatchDuration);
      calculateWatchDuration();
      logHeartBeat();
      this.mPreparedTime = TimerUtil.getApproximateServerTime(this.mContext).getTime();
      this.mLoadingDuration = (this.mPreparedTime - this.mSetUrlTime);
      buildPlayCountLogItem();
      logStart();
    }
    while (!this.mIsSelfCountCarton);
    initHandler();
    this.mPositionHandler.removeCallbacks(this.mQueryPositionRunnable);
    this.mPositionHandler.postDelayed(this.mQueryPositionRunnable, QUERY_DURATION);
  }

  public boolean stop()
  {
    if (this.mIsTheSameVideo);
    do
    {
      return false;
      this.isPaused = false;
    }
    while (!UserDataLogger.getInstance().isPlaying());
    OutputLog.e("SohuLoggerAgent", "播放停止");
    UserDataLogger.getInstance().setPlaying(false);
    calculateWatchDuration();
    this.mStartTime = 0L;
    logHeartBeat();
    logEnd("vclose");
    this.mHandler.stop();
    if ((this.mIsSelfCountCarton) && (this.mPositionHandler != null))
      this.mPositionHandler.removeCallbacks(this.mQueryPositionRunnable);
    return true;
  }

  class HeartBeatHandler extends Handler
  {
    public static final int HEART_BEAT_INTEVAL = 1000;
    private static final int MSG_HEART_BEAT;
    private boolean isActive = false;

    HeartBeatHandler()
    {
    }

    private boolean isActive()
    {
      try
      {
        boolean bool = this.isActive;
        return bool;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }

    private void setActive(boolean paramBoolean)
    {
      try
      {
        this.isActive = paramBoolean;
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }

    private void start()
    {
      if (isActive())
        return;
      setActive(true);
      removeCallbacksAndMessages(null);
      sendEmptyMessage(0);
    }

    private void stop()
    {
      setActive(false);
      removeCallbacksAndMessages(null);
    }

    public void handleMessage(Message paramMessage)
    {
      super.handleMessage(paramMessage);
      if (!isActive())
      {
        removeCallbacksAndMessages(null);
        return;
      }
      SohuPlayerLogger.this.logHeartBeat();
      OutputLog.w("SohuLoggerAgent", "播放心跳：  mWatchDuration: " + SohuPlayerLogger.this.mWatchDuration + "   mLastHeartBeatNo:" + SohuPlayerLogger.this.mLastHeartBeatNo);
      sendEmptyMessageDelayed(0, 1000L);
    }
  }

  public static abstract interface ISohuPlayerLogger
  {
    public abstract int getCurrentPosition();

    public abstract int getDuration();

    public abstract LogInfo getPlayInfo();

    public abstract boolean isPlaying();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.logger.model.SohuPlayerLogger
 * JD-Core Version:    0.6.2
 */