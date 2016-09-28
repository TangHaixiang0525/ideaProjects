package com.sohu.logger.model;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Process;
import com.sohu.logger.SohuLoggerAgent;
import com.sohu.logger.bean.UserActionLogItem;
import com.sohu.logger.common.DeviceConstants;
import com.sohu.logger.log.OutputLog;
import com.sohu.logger.util.TimerUtil;
import com.sohu.logger.util.TimerUtil.Timer;
import java.util.Iterator;
import java.util.List;

public class UserDataLogger
{
  public static final int APP_ENTRANCE_PUSH = 1;
  private static final long DURATION_1_HOUR = 3600000L;
  public static String SOHU_PACKAGE_NAME = "sohu";
  private static final String TAG = "AppContext";
  private static UserDataLogger _instance = new UserDataLogger();
  private static long mAppRunTimeHeartBeatCount = 1L;
  private Context _context = null;
  private boolean hasInited = false;
  private boolean hasSetAppStartParams = false;
  private boolean isAppNormalStart = false;
  private boolean isExit = false;
  private boolean isPlaying = false;
  private boolean isRunningForeground = false;
  private boolean isScreenLocked = false;
  private int mActiveTime = 0;
  private String mEnterId = "";
  private int mForegroundActivityCount = 0;
  private Handler mHandler = new Handler();
  private boolean mIsNewUser = false;
  private final Object mLock = new Object();
  private Runnable mRunlogAppRunTimeHeartBeat = new Runnable()
  {
    public void run()
    {
      UserDataLogger.this.logAppRunTimeHeartBeat();
    }
  };
  private String mStartId = "";
  private long startSendTimestamp = -1L;

  private UserActionLogItem buildAppStartLogItem(String paramString)
  {
    UserActionLogItem localUserActionLogItem = new UserActionLogItem();
    localUserActionLogItem.setParamsMapItem("url", "1002");
    this.startSendTimestamp = System.currentTimeMillis();
    localUserActionLogItem.setParamsMapItem("time", String.valueOf(this.startSendTimestamp));
    localUserActionLogItem.setParamsMapItem("memo", paramString);
    return localUserActionLogItem;
  }

  private void checkToSendAppStartLog(boolean paramBoolean, Activity paramActivity)
  {
    long l1 = 600000L;
    if (!paramBoolean);
    do
    {
      return;
      this.mForegroundActivityCount = (1 + this.mForegroundActivityCount);
      OutputLog.d("AppContext", "mForegroundActivityCount:" + this.mForegroundActivityCount);
    }
    while (this.mForegroundActivityCount != 1);
    resetParamsWhenAppStart();
    if (this.startSendTimestamp > 0L)
    {
      long l2 = System.currentTimeMillis();
      long l3 = l2 - this.startSendTimestamp;
      UserActionLogItem localUserActionLogItem = buildAppStartLogItem("1");
      if (l3 > l1);
      while (true)
      {
        localUserActionLogItem.setStartStamp(l1);
        localUserActionLogItem.setParamsMapItem("time", l2);
        this.startSendTimestamp = 0L;
        OutputLog.d("AppContext", "App start log, url = 1002, memo = 1 report！" + this.startSendTimestamp);
        SohuLoggerAgent.getInstance().onEvent(this._context, localUserActionLogItem);
        return;
        l1 = l3;
      }
    }
    OutputLog.d("AppContext", "App start log, url = 1002, memo = 1 not report ,because not send memo = 0");
  }

  private void enterBackground()
  {
    long l1 = 3600000L;
    this.isRunningForeground = false;
    TimerUtil.getTimerByTag("com.sohu.app.tag.app_runtime").pause();
    this.mHandler.removeCallbacks(this.mRunlogAppRunTimeHeartBeat);
    UserActionLogItem localUserActionLogItem = new UserActionLogItem();
    localUserActionLogItem.setParamsMapItem("url", "2001");
    long l2 = TimerUtil.getTimerByTag("com.sohu.app.tag.app_runtime").getDuration();
    if (l2 > l1);
    while (true)
    {
      localUserActionLogItem.setStartStamp(l1);
      SohuLoggerAgent.getInstance().onEvent(this._context, localUserActionLogItem);
      resetEnterIdWhenEnterBackgroud();
      OutputLog.d("AppContext", "duration:" + TimerUtil.getTimerByTag("com.sohu.app.tag.app_runtime").getDuration());
      return;
      l1 = l2;
    }
  }

  private void enterForeground(Activity paramActivity)
  {
    this.isRunningForeground = true;
    mAppRunTimeHeartBeatCount = 1L;
    TimerUtil.getTimerByTag("com.sohu.app.tag.app_runtime").restart();
    this.mHandler.removeCallbacks(this.mRunlogAppRunTimeHeartBeat);
    this.mHandler.postDelayed(this.mRunlogAppRunTimeHeartBeat, 3600000L);
    this.mActiveTime = (1 + this.mActiveTime);
    if ((this.mActiveTime == 1) && (paramActivity != null))
      OutputLog.d("AppContext", "activity:" + paramActivity.getClass().getName());
    UserActionLogItem localUserActionLogItem;
    if (this.mForegroundActivityCount != 1)
    {
      localUserActionLogItem = new UserActionLogItem();
      if (!this.isPlaying)
        break label161;
    }
    label161: for (int i = 2003; ; i = 2002)
    {
      localUserActionLogItem.setParamsMapItem("url", i + "");
      SohuLoggerAgent.getInstance().onEvent(this._context, localUserActionLogItem);
      return;
    }
  }

  private String getCurProcessName(Context paramContext)
  {
    int i = Process.myPid();
    Iterator localIterator = ((ActivityManager)paramContext.getSystemService("activity")).getRunningAppProcesses().iterator();
    while (localIterator.hasNext())
    {
      ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)localIterator.next();
      if (localRunningAppProcessInfo.pid == i)
        return localRunningAppProcessInfo.processName;
    }
    return null;
  }

  public static UserDataLogger getInstance()
  {
    return _instance;
  }

  private boolean hasInited()
  {
    synchronized (this.mLock)
    {
      boolean bool = this.hasInited;
      return bool;
    }
  }

  private boolean isTopActivity()
  {
    List localList = ((ActivityManager)this._context.getSystemService("activity")).getRunningTasks(1);
    if (localList.size() > 0)
    {
      OutputLog.d("AppContext", "topActivity:" + ((ActivityManager.RunningTaskInfo)localList.get(0)).topActivity);
      if (this._context.getPackageName().equals(((ActivityManager.RunningTaskInfo)localList.get(0)).topActivity.getPackageName()))
        return !isScreenLocked();
    }
    return false;
  }

  private void logAppRunTimeHeartBeat()
  {
    if (TimerUtil.getTimerByTag("com.sohu.app.tag.app_runtime").getDuration() >= 3600000L * mAppRunTimeHeartBeatCount)
    {
      UserActionLogItem localUserActionLogItem = new UserActionLogItem();
      localUserActionLogItem.setParamsMapItem("url", Integer.toString(2004));
      localUserActionLogItem.setStartStamp(3600000L * mAppRunTimeHeartBeatCount);
      SohuLoggerAgent.getInstance().onEvent(this._context, localUserActionLogItem);
      mAppRunTimeHeartBeatCount = 1L + mAppRunTimeHeartBeatCount;
    }
    this.mHandler.removeCallbacks(this.mRunlogAppRunTimeHeartBeat);
    this.mHandler.postDelayed(this.mRunlogAppRunTimeHeartBeat, 3600000L);
  }

  private void onActivityStateChanged(final Activity paramActivity, final boolean paramBoolean1, boolean paramBoolean2)
  {
    checkToSendAppStartLog(paramBoolean1, paramActivity);
    Handler localHandler = this.mHandler;
    Runnable local1 = new Runnable()
    {
      public void run()
      {
        UserDataLogger.this.updateAppState(paramActivity, paramBoolean1);
        OutputLog.i("AppContext", "App is running foreground?" + UserDataLogger.this.isRunningForeground);
        if (!UserDataLogger.this.isRunningForeground)
          OutputLog.i("AppContext", "App has run " + UserDataLogger.this.getAppRunTime() / 1000L + " second(s)");
      }
    };
    if (paramBoolean2);
    for (int i = 500; ; i = 0)
    {
      localHandler.postDelayed(local1, i);
      return;
    }
  }

  private void registerScreenStateReceiver()
  {
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.intent.action.SCREEN_ON");
    localIntentFilter.addAction("android.intent.action.SCREEN_OFF");
    localIntentFilter.addAction("android.intent.action.USER_PRESENT");
    this._context.registerReceiver(new ScreenStateReceiver(), localIntentFilter);
  }

  private void resetEnterIdWhenEnterBackgroud()
  {
    setEnterId("");
  }

  private void resetParamsWhenAppStart()
  {
    if (this.hasSetAppStartParams)
      return;
    if (DeviceConstants.getInstance().getGenType() != 2);
    for (boolean bool = true; ; bool = false)
    {
      setNewUser(bool);
      setStartId(String.valueOf(System.currentTimeMillis()));
      this.hasSetAppStartParams = true;
      return;
    }
  }

  private void setContext(Context paramContext)
  {
    this._context = paramContext;
  }

  private void setHasInited(boolean paramBoolean)
  {
    synchronized (this.mLock)
    {
      this.hasInited = paramBoolean;
      return;
    }
  }

  private void updateAppState(Activity paramActivity, boolean paramBoolean)
  {
    try
    {
      boolean bool = isTopActivity();
      StringBuilder localStringBuilder = new StringBuilder().append("updateAppState, isTop:").append(bool).append(", foreground:").append(paramBoolean);
      String str;
      if (paramActivity == null)
      {
        str = "";
        OutputLog.d("AppContext", str);
        if ((!isExit()) && ((!this.isRunningForeground) || (bool) || (paramBoolean)))
          break label103;
        enterBackground();
      }
      while (true)
      {
        return;
        str = paramActivity.getClass().getName();
        break;
        label103: if ((!this.isRunningForeground) && (paramBoolean))
          enterForeground(paramActivity);
      }
    }
    finally
    {
    }
  }

  public long getAppRunTime()
  {
    return TimerUtil.getTimerByTag("com.sohu.app.tag.app_runtime").getDuration();
  }

  public Context getContext()
  {
    return this._context;
  }

  public String getEnterId()
  {
    return this.mEnterId;
  }

  public String getStartId()
  {
    return this.mStartId;
  }

  public Object getSystemService(String paramString)
  {
    return this._context.getSystemService(paramString);
  }

  public void initInstance(Context paramContext)
  {
    _instance = getInstance();
    if (_instance.hasInited())
      return;
    SOHU_PACKAGE_NAME = getCurProcessName(paramContext);
    _instance.setContext(paramContext);
    TimerUtil.getTimerByTag("com.sohu.app.tag.app_runtime").start();
    _instance.setHasInited(true);
    _instance.registerScreenStateReceiver();
  }

  public boolean isAppNormalStart()
  {
    return this.isAppNormalStart;
  }

  public boolean isExit()
  {
    return this.isExit;
  }

  public boolean isNewUser()
  {
    return this.mIsNewUser;
  }

  public boolean isPlaying()
  {
    return this.isPlaying;
  }

  public boolean isScreenLocked()
  {
    return this.isScreenLocked;
  }

  public void onActivityDestory(Activity paramActivity, boolean paramBoolean)
  {
    onActivityStateChanged(paramActivity, false, true);
    this.startSendTimestamp = -1L;
    if ((this.isAppNormalStart) && (!paramBoolean));
  }

  public void onActivityStateChanged(Activity paramActivity, boolean paramBoolean)
  {
    onActivityStateChanged(paramActivity, paramBoolean, false);
  }

  public void resetForegroundActivityCountWhenNeed()
  {
    this.mForegroundActivityCount = 0;
    this.hasSetAppStartParams = false;
    this.isAppNormalStart = false;
    OutputLog.d("AppContext", "App reset log");
  }

  public void sendAppStartLog()
  {
    resetParamsWhenAppStart();
    UserActionLogItem localUserActionLogItem = buildAppStartLogItem("0");
    OutputLog.d("AppContext", "App start log, url = 1002, memo = 0");
    SohuLoggerAgent.getInstance().onEvent(this._context, localUserActionLogItem);
    this.isAppNormalStart = true;
  }

  public void setEnterId(String paramString)
  {
    this.mEnterId = paramString;
  }

  public void setExit(boolean paramBoolean)
  {
    this.isExit = paramBoolean;
  }

  public void setNewUser(boolean paramBoolean)
  {
    this.mIsNewUser = paramBoolean;
  }

  public void setPlaying(boolean paramBoolean)
  {
    this.isPlaying = paramBoolean;
  }

  public void setStartId(String paramString)
  {
    this.mStartId = paramString;
  }

  public void startService(Intent paramIntent)
  {
    this._context.startService(paramIntent);
  }

  public void updateEnterIdIfNeed(int paramInt)
  {
    if (this.isRunningForeground);
    while (paramInt != 1)
      return;
    setEnterId("1");
  }

  public class ScreenStateReceiver extends BroadcastReceiver
  {
    public ScreenStateReceiver()
    {
    }

    public void onReceive(Context paramContext, Intent paramIntent)
    {
      boolean bool = true;
      int i;
      if (paramIntent.getAction().equals("android.intent.action.SCREEN_OFF"))
      {
        UserDataLogger.access$202(UserDataLogger.this, bool);
        i = 0;
      }
      while (true)
      {
        if (UserDataLogger.this.mForegroundActivityCount <= 0);
        while (!UserDataLogger.SOHU_PACKAGE_NAME.equals(UserDataLogger.this.getCurProcessName(paramContext)))
        {
          return;
          if (paramIntent.getAction().equals("android.intent.action.SCREEN_ON"))
          {
            UserDataLogger.access$202(UserDataLogger.this, false);
            i = 0;
            break;
          }
          if (!paramIntent.getAction().equals("android.intent.action.USER_PRESENT"))
            break label142;
          UserDataLogger.access$202(UserDataLogger.this, false);
          i = bool;
          break;
        }
        UserDataLogger localUserDataLogger = UserDataLogger.this;
        if ((!UserDataLogger.this.isScreenLocked) && (i != 0));
        while (true)
        {
          localUserDataLogger.onActivityStateChanged(null, bool);
          return;
          bool = false;
        }
        label142: i = 0;
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.sohu.logger.model.UserDataLogger
 * JD-Core Version:    0.6.2
 */