package com.sohu.logger.util;

import android.content.Context;
import android.os.SystemClock;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TimerUtil
{
  public static final String KEY_CURRENT_TIME_OFFSET = "current_time_offset";
  public static final String TAG_APP_RUNTIME = "com.sohu.app.tag.app_runtime";
  public static final String TAG_VIDEO_PLAY = "com.sohu.app.tag.video_play";
  private static Map<String, Timer> mTimers = new HashMap();

  public static Date getApproximateServerTime(Context paramContext)
  {
    return new Date(System.currentTimeMillis() - PrefUtil.getLong(paramContext, "current_time_offset", 0L));
  }

  public static String getCurrentDateTime()
  {
    Date localDate = new Date();
    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(localDate);
  }

  public static long getCurrentDateTimeLong()
  {
    Date localDate = new Date();
    return Long.parseLong(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(localDate).replace("-", "").replace(" ", "").replace(":", ""));
  }

  public static long getCurrentDateTimeLong(String paramString)
  {
    return Long.parseLong(paramString.replace("-", "").replace(" ", "").replace(":", ""));
  }

  public static long getCurrentTime()
  {
    return SystemClock.elapsedRealtime();
  }

  public static TimeEntity getTimeStringFromLong(long paramLong)
  {
    Date localDate = new Date(paramLong);
    System.out.println(localDate);
    String[] arrayOfString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(localDate).split(" ");
    TimeEntity localTimeEntity = new TimeEntity();
    localTimeEntity.setDate(arrayOfString[0]);
    localTimeEntity.setTime(arrayOfString[1]);
    return localTimeEntity;
  }

  public static Timer getTimerByTag(String paramString)
  {
    if ((Timer)mTimers.get(paramString) == null)
      mTimers.put(paramString, new Timer());
    return (Timer)mTimers.get(paramString);
  }

  public static String secondToString(Long paramLong)
  {
    while (true)
    {
      int m;
      StringBuilder localStringBuilder;
      try
      {
        int i = (int)paramLong.longValue();
        int j = i / 3600;
        k = (i - j * 3600) / 60;
        m = i % 60;
        localStringBuilder = new StringBuilder();
        if (j < 10)
        {
          localStringBuilder.append("0").append(j);
          localStringBuilder.append(":");
          if (k < 10)
          {
            localStringBuilder.append("0").append(k);
            localStringBuilder.append(":");
            if (m >= 10)
              break label159;
            localStringBuilder.append("0").append(m);
            return localStringBuilder.toString();
          }
        }
        else
        {
          localStringBuilder.append(j);
          continue;
        }
      }
      catch (Exception localException)
      {
        int k;
        localException.printStackTrace();
        return null;
        localStringBuilder.append(k);
        continue;
      }
      catch (Error localError)
      {
        localError.printStackTrace();
        return null;
      }
      label159: localStringBuilder.append(m);
    }
  }

  public static void setServerDateOffset(Context paramContext, long paramLong)
  {
    PrefUtil.putLong(paramContext, "current_time_offset", System.currentTimeMillis() - paramLong);
  }

  public static class TimeEntity
  {
    private String date;
    private String time;

    public String getDate()
    {
      return this.date;
    }

    public String getTime()
    {
      return this.time;
    }

    public void setDate(String paramString)
    {
      this.date = paramString;
    }

    public void setTime(String paramString)
    {
      this.time = paramString;
    }
  }

  public static class Timer
  {
    private int mBlockingTimes = 0;
    private long mDuration;
    private boolean mIsCounting = false;
    private long mLastPauseTime;
    private long mStartTime;

    private long getInteval()
    {
      return TimerUtil.getCurrentTime() - this.mStartTime;
    }

    public long getDuration()
    {
      if (this.mIsCounting)
        return this.mDuration + getInteval();
      return this.mDuration;
    }

    public long getLastPauseTime()
    {
      return this.mLastPauseTime;
    }

    public void pause()
    {
      if (!this.mIsCounting)
        return;
      this.mIsCounting = false;
      this.mBlockingTimes = (1 + this.mBlockingTimes);
      this.mDuration += getInteval();
      this.mLastPauseTime = TimerUtil.getCurrentTime();
    }

    public void restart()
    {
      this.mIsCounting = false;
      this.mStartTime = 0L;
      this.mLastPauseTime = 0L;
      this.mDuration = 0L;
      start();
    }

    public void start()
    {
      if (this.mIsCounting)
        return;
      this.mIsCounting = true;
      this.mStartTime = TimerUtil.getCurrentTime();
    }

    public long stop()
    {
      long l = this.mDuration;
      if (!this.mIsCounting);
      while (true)
      {
        this.mDuration = 0L;
        this.mLastPauseTime = 0L;
        return l;
        l += getInteval();
        this.mIsCounting = false;
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.logger.util.TimerUtil
 * JD-Core Version:    0.6.2
 */