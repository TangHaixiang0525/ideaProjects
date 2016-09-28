package com.starcor.hunan.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import com.starcor.config.AppFuncCfg;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.App;
import com.starcor.sccms.api.SccmsApiSyncTimeTask.ISccmsApiSyncTimeTaskListener;
import com.starcor.server.api.manage.ServerApiCommonError;
import com.starcor.server.api.manage.ServerApiManager;
import com.starcor.server.api.manage.ServerApiTaskInfo;
import com.starcor.service.BroadCastDispather;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.util.TimerTask;

public class SystemTimeManager
{
  public static final String ACTION_TIME_SYNC_SUCCESSFUL = "com.starcor.hunan.TIME_SYNC_SUCCESSFUL";
  private static final int PROTECTION_TIME = 60000;
  private static final int RESULT_TIME = 0;
  private static final int RETRY_TIME = 60000;
  private static final int SYNCHRONIZE_DELAYED = 3600000;
  private static final String TAG = SystemTimeManager.class.getSimpleName();
  public static SystemTimeManager manager = null;
  private static ArrayList<CommonTimer> timerList = null;
  private long TD;
  private boolean TDModeTime = false;
  private Intent alarmIntent;
  private long alarmTime;
  private long autoTickSynchronize = 0L;
  private boolean exitFlag = false;
  private Context mContext;
  private SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmssZ");
  private Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      String str;
      if (paramAnonymousMessage.obj != null)
      {
        str = (String)paramAnonymousMessage.obj;
        if (TextUtils.isEmpty(str))
        {
          Logger.w(SystemTimeManager.TAG, "Time synchronization fails, the return time is empty");
          postDelayed(SystemTimeManager.this.trydoSynchronizedTime, 60000L);
        }
      }
      while (true)
      {
        SystemTimeManager.access$302(SystemTimeManager.this, 0L);
        Logger.i(SystemTimeManager.TAG, "handleMessage reset synProtection=0");
        return;
        SystemTimeManager.this.synchronizedTime(str);
        Logger.i(SystemTimeManager.TAG, "Time synchronization sucess!");
        continue;
        Logger.w(SystemTimeManager.TAG, "Time synchronization fails,reslut code:" + paramAnonymousMessage.arg1);
        postDelayed(SystemTimeManager.this.trydoSynchronizedTime, 60000L);
      }
    }
  };
  private long synProtection = 60000L;
  private BroadcastReceiver timeChangeReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      Logger.i(SystemTimeManager.TAG, "timeChangeReceiver user change time,call doSynchronizedTime");
      SystemTimeManager.this.doSynchronizedTime();
    }
  };
  private Runnable trydoSynchronizedTime = new Runnable()
  {
    public void run()
    {
      SystemTimeManager.this.doSynchronizedTime();
    }
  };

  private SystemTimeManager(Context paramContext)
  {
    this.mContext = paramContext.getApplicationContext();
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.intent.action.TIME_SET");
    this.mContext.registerReceiver(this.timeChangeReceiver, localIntentFilter);
    Logger.i(TAG, "SystemTimeManager regsiter chageTimeFilter receiver");
    new TickThread().start();
  }

  public static long ServerTime2SystemTime(long paramLong)
  {
    if ((manager != null) && (manager.TDModeTime))
      paramLong = paramLong - getCurrentServerTime() + System.currentTimeMillis();
    return paramLong;
  }

  public static void cancel(TimerTask paramTimerTask)
  {
    if (timerList == null)
      return;
    timerList.remove(paramTimerTask);
  }

  private void doSynchronizedTime()
  {
    if (this.synProtection < 60000L)
    {
      Logger.i(TAG, "syncProtect abandon the request value:" + this.synProtection);
      return;
    }
    this.synProtection = 0L;
    this.autoTickSynchronize = 0L;
    Logger.i(TAG, "doSynchronizedTime reset synProtection=0");
    ServerApiManager.i().APISyncServerTime(new SccmsApiSyncTimeTask.ISccmsApiSyncTimeTaskListener()
    {
      public void onError(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, ServerApiCommonError paramAnonymousServerApiCommonError)
      {
        Message localMessage = new Message();
        SystemTimeManager.this.mHandler.handleMessage(localMessage);
      }

      public void onSuccess(ServerApiTaskInfo paramAnonymousServerApiTaskInfo, String paramAnonymousString)
      {
        Message localMessage = new Message();
        localMessage.obj = paramAnonymousString;
        SystemTimeManager.this.mHandler.handleMessage(localMessage);
      }
    });
  }

  public static String getCurrentServerDate()
  {
    long l = getCurrentServerTime();
    return new SimpleDateFormat("yyyyMMdd").format(new Date(l));
  }

  public static long getCurrentServerTime()
  {
    if (manager != null)
    {
      if (manager.TDModeTime)
        return manager.TD + SystemClock.elapsedRealtime();
      Logger.i(TAG, "getCurrentServerTime TDModeTime:" + manager.TDModeTime);
    }
    return System.currentTimeMillis();
  }

  public static String getCurrentServerTimeHMS()
  {
    long l = getCurrentServerTime();
    return new SimpleDateFormat("HHmmss").format(new Date(l));
  }

  public static SystemTimeManager getInstance()
  {
    if (manager == null)
      manager = new SystemTimeManager(App.getInstance().getApplicationContext());
    return manager;
  }

  public static SystemTimeManager getinstance(Context paramContext)
  {
    if (manager == null)
    {
      manager = new SystemTimeManager(paramContext);
      manager.doSynchronizedTime();
    }
    return manager;
  }

  public void SynchronizedTime()
  {
    Logger.i(TAG, "SynchronizedTime");
    this.synProtection = 60001L;
    doSynchronizedTime();
  }

  public void addTask(TimerTask paramTimerTask)
  {
    if (paramTimerTask != null)
      new Thread(paramTimerTask)
      {
      }
      .start();
  }

  public void addTask(TimerTask paramTimerTask, int paramInt)
  {
    if (timerList == null)
      timerList = new ArrayList();
    CommonTimer localCommonTimer = new CommonTimer(paramTimerTask, paramInt);
    timerList.add(localCommonTimer);
  }

  protected void finalize()
    throws Throwable
  {
    this.exitFlag = true;
    if (this.timeChangeReceiver != null)
    {
      Logger.i(TAG, "finalize unregsiter timeChangeReceiver receiver");
      this.mContext.unregisterReceiver(this.timeChangeReceiver);
      this.timeChangeReceiver = null;
    }
    manager = null;
    super.finalize();
  }

  public boolean isInit()
  {
    return this.TDModeTime;
  }

  public void setAlarm(Intent paramIntent, long paramLong)
  {
    if (paramIntent == null)
      return;
    this.alarmIntent = paramIntent;
    this.alarmTime = paramLong;
  }

  public void synchronizedTime(String paramString)
  {
    try
    {
      this.TD = (this.mFormat.parse(paramString).getTime() - SystemClock.elapsedRealtime());
      this.TDModeTime = true;
      Intent localIntent = new Intent();
      localIntent.setAction("com.starcor.hunan.TIME_SYNC_SUCCESSFUL");
      localIntent.addFlags(32);
      this.mContext.sendBroadcast(localIntent);
      Logger.i(TAG, "synchronizedTime ok,Server time:" + paramString + ",localeTime:" + new Date(System.currentTimeMillis()).toLocaleString() + ",TD:" + this.TD);
      boolean bool = TextUtils.isEmpty(paramString);
      if (!bool);
      try
      {
        if (paramString.contains("-"))
          TimeZone.setDefault(TimeZone.getTimeZone("GMT" + paramString.substring(paramString.indexOf("-"))));
        while (true)
        {
          Logger.i(TAG, "synchronizedTime getTimeZone = " + TimeZone.getDefault().getID());
          return;
          if (paramString.contains("+"))
            TimeZone.setDefault(TimeZone.getTimeZone("GMT" + paramString.substring(paramString.indexOf("+"))));
        }
      }
      catch (Exception localException)
      {
        while (true)
          Logger.i(TAG, "synchronizedTime setTimeZone Exception; timeStr = " + paramString);
      }
    }
    catch (ParseException localParseException)
    {
      Logger.i(TAG, "synchronizedTime timeString:" + paramString);
    }
  }

  private class CommonTimer
  {
    private long lastHandleTime = 0L;
    private long period = 0L;
    private TimerTask task;

    public CommonTimer(TimerTask paramInt, int arg3)
    {
      this.task = paramInt;
      int i;
      this.period = i;
    }

    public long getLastHandleTime()
    {
      return this.lastHandleTime;
    }

    public long getPeriod()
    {
      return this.period;
    }

    public TimerTask getTask()
    {
      return this.task;
    }

    public void setLastHandleTime(long paramLong)
    {
      this.lastHandleTime = paramLong;
    }
  }

  class TickThread extends Thread
  {
    TickThread()
    {
    }

    public void run()
    {
      while (!SystemTimeManager.this.exitFlag)
      {
        SystemTimeManager.access$314(SystemTimeManager.this, 1000L);
        SystemTimeManager.access$614(SystemTimeManager.this, 1000L);
        if ((SystemTimeManager.this.alarmIntent != null) && (SystemTimeManager.getCurrentServerTime() >= SystemTimeManager.this.alarmTime))
        {
          if (!AppFuncCfg.isReservationUseCallBack())
            break label207;
          BroadCastDispather.getInstance().sendBroadcast(SystemTimeManager.this.alarmIntent);
        }
        while (true)
        {
          SystemTimeManager.access$702(SystemTimeManager.this, null);
          if (SystemTimeManager.this.autoTickSynchronize >= 3600000L)
          {
            Logger.i(SystemTimeManager.TAG, "timeTickReceiver onReceive start sync time");
            SystemTimeManager.this.doSynchronizedTime();
          }
          if ((SystemTimeManager.timerList == null) || (SystemTimeManager.timerList.size() <= 0))
            break;
          int i = 0;
          int j = SystemTimeManager.timerList.size();
          while (i < j)
          {
            SystemTimeManager.CommonTimer localCommonTimer = (SystemTimeManager.CommonTimer)SystemTimeManager.timerList.get(i);
            if ((localCommonTimer.getTask() != null) && (SystemTimeManager.getCurrentServerTime() - localCommonTimer.getLastHandleTime() >= localCommonTimer.getPeriod()))
            {
              localCommonTimer.setLastHandleTime(SystemTimeManager.getCurrentServerTime());
              localCommonTimer.getTask().run();
            }
            i++;
          }
          label207: App.getInstance().sendBroadcast(SystemTimeManager.this.alarmIntent);
        }
        try
        {
          sleep(1000L);
        }
        catch (InterruptedException localInterruptedException)
        {
        }
      }
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.service.SystemTimeManager
 * JD-Core Version:    0.6.2
 */