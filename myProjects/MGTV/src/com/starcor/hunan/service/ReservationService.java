package com.starcor.hunan.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import com.starcor.config.DeviceInfo;
import com.starcor.core.logic.GlobalEnv;
import com.starcor.core.logic.GlobalLogic;
import com.starcor.core.utils.GeneralUtils;
import com.starcor.core.utils.Logger;
import com.starcor.hunan.db.dao.ReservationDao;
import com.starcor.hunan.domain.Reservation;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class ReservationService
{
  public static final String ACTION_RESERVATION_CHANGE = "com.starcor.reservation.ReservationChange";
  private static final int REQUEST_CODE_ALARM = 100;
  public static final String RESERVATION_ACTION = "com.starcor.hunan.service.reservation_action";
  public static final int STATE_CANPLAY = 1;
  public static final int STATE_NOT_START = 2;
  public static final int STATE_WITHDRAWN = 3;
  private static final String TAG = ReservationService.class.getSimpleName();
  private static ReservationService mService = null;
  private ReservationDao dao;
  private Context mContext;
  private Reservation mReservation = null;
  private List<ReservationRefrshListener> refrshListenerList = new ArrayList();

  public static int checkReservationState(Reservation paramReservation)
  {
    long l = SystemTimeManager.getCurrentServerTime();
    if (paramReservation.getExpiredTime() < l)
      return 3;
    if (paramReservation.getReservation_time() > SystemTimeManager.getCurrentServerTime())
      return 2;
    return 1;
  }

  public static ReservationService getinstance()
  {
    if (mService == null)
      mService = new ReservationService();
    return mService;
  }

  private void reset()
  {
    this.dao = new ReservationDao();
    String str = GlobalLogic.getInstance().getUserPrivateConfigPath() + File.separator;
    this.dao.initData(SystemTimeManager.getCurrentServerTime(), str);
    resetReservation();
    checkExpiredReservation();
  }

  private void resetReservation()
  {
    if (this.dao == null)
      return;
    this.mReservation = this.dao.findFirstReservation(SystemTimeManager.getCurrentServerTime());
    if (this.mReservation != null)
      if (DeviceInfo.isFactoryCH())
      {
        setAlarm(this.mReservation.getReservation_time());
        label46: Logger.i(TAG, "resetReservation [" + this.mReservation.getChannel() + "]" + this.mReservation.getName() + ":" + new Date(this.mReservation.getReservation_time()).toLocaleString());
      }
    while (true)
    {
      Iterator localIterator = this.refrshListenerList.iterator();
      while (localIterator.hasNext())
        ((ReservationRefrshListener)localIterator.next()).onReservationRefrsh();
      break;
      setAlarm(this.mContext, this.mReservation.getReservation_time());
      break label46;
      cancelAlarm(this.mContext, 0L);
      Logger.i(TAG, "resetReservation not reservation");
    }
  }

  public static long time2Reservation(String paramString)
  {
    return GeneralUtils.getTimeInMillis(paramString) + GlobalEnv.getInstance().getReservationDelayNotifyTime();
  }

  public static long time2Reservation(String paramString, int paramInt)
  {
    return GeneralUtils.getTimeInMillis(paramString) + paramInt * 1000;
  }

  public void addReservation(Reservation paramReservation)
  {
    if (this.dao == null)
      return;
    paramReservation.setReservation_time();
    this.dao.addReservation(paramReservation);
    Logger.i(TAG, "addReservation [" + paramReservation.getChannel() + "]" + paramReservation.getName() + ":" + new Date(paramReservation.getReservation_time()).toLocaleString() + ",film_type:" + paramReservation.getFilm_type());
    resetReservation();
  }

  public PendingIntent cancelAlarm(Context paramContext, long paramLong)
  {
    AlarmManager localAlarmManager = (AlarmManager)paramContext.getSystemService("alarm");
    Intent localIntent = new Intent("com.starcor.hunan.service.reservation_action");
    localIntent.putExtra("Time", paramLong);
    localIntent.putExtra("systime", SystemTimeManager.ServerTime2SystemTime(paramLong));
    Logger.i(TAG, "生成一个预约Intent,time:" + paramLong + ",systime" + SystemTimeManager.ServerTime2SystemTime(paramLong));
    PendingIntent localPendingIntent = PendingIntent.getBroadcast(paramContext, 100, localIntent, 134217728);
    localAlarmManager.cancel(localPendingIntent);
    return localPendingIntent;
  }

  public void checkExpiredReservation()
  {
    if (haveExpiredReservation(SystemTimeManager.getCurrentServerTime()))
    {
      Intent localIntent = new Intent("com.starcor.hunan.service.reservation_action");
      localIntent.addFlags(32);
      localIntent.putExtra("Time", SystemTimeManager.getCurrentServerTime());
      this.mContext.sendBroadcast(localIntent);
      Logger.i(TAG, "检查到有未通知的过期预约");
    }
  }

  public int checkReservationCount(long paramLong)
  {
    if (this.dao == null)
      return 0;
    return this.dao.findAllExpiredReservation(1000L + paramLong).size();
  }

  public void clearnReservation()
  {
    if (this.dao == null)
      return;
    this.dao.clearnReservation();
    resetReservation();
  }

  public Reservation findReservation(long paramLong, String paramString)
  {
    if (this.dao == null)
      return null;
    return this.dao.findReservation(paramLong, paramString);
  }

  public List<Reservation> getAllNotExpiredReservation(long paramLong)
  {
    if (this.dao == null)
      return null;
    return this.dao.findAllNotExpiredReservation(paramLong);
  }

  public List<Reservation> getAllReservation()
  {
    if (this.dao == null)
      return null;
    return this.dao.findAllReservation();
  }

  public List<Reservation> getAndRemoveExpiredReservation(long paramLong)
  {
    if (this.dao == null)
      return null;
    List localList = this.dao.findAllExpiredReservation(1000L + paramLong);
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      Reservation localReservation = (Reservation)localIterator.next();
      this.dao.setReservataionExpired(localReservation.get_id(), true);
    }
    resetReservation();
    return localList;
  }

  public boolean haveExpiredReservation(long paramLong)
  {
    if (this.dao == null)
      return false;
    return this.dao.haveExpiredReservation(paramLong);
  }

  public void init(Context paramContext)
  {
    this.mContext = paramContext.getApplicationContext();
  }

  public void regisiteRefrshListener(ReservationRefrshListener paramReservationRefrshListener)
  {
    if ((paramReservationRefrshListener == null) || (this.refrshListenerList.contains(paramReservationRefrshListener)))
      return;
    this.refrshListenerList.add(paramReservationRefrshListener);
  }

  public void removeReservation(long paramLong)
  {
    if (this.dao == null)
      return;
    this.dao.removeReservation(paramLong);
    resetReservation();
  }

  public void setAlarm(long paramLong)
  {
    Intent localIntent = new Intent("com.starcor.hunan.service.reservation_action");
    localIntent.putExtra("Time", paramLong);
    localIntent.putExtra("systime", SystemTimeManager.ServerTime2SystemTime(paramLong));
    SystemTimeManager.getInstance().setAlarm(localIntent, paramLong);
    Logger.i("reservation", " setAlarm(long time) 添加一个全局定时器，time：" + new Date(SystemTimeManager.ServerTime2SystemTime(paramLong)).toLocaleString());
  }

  public void setAlarm(Context paramContext, long paramLong)
  {
    ((AlarmManager)paramContext.getSystemService("alarm")).set(0, SystemTimeManager.ServerTime2SystemTime(paramLong), cancelAlarm(paramContext, paramLong));
    Logger.i("reservation", " setAlarm(Context context, long time) 添加一个全局定时器，time：" + new Date(SystemTimeManager.ServerTime2SystemTime(paramLong)).toLocaleString());
  }

  public void unRegisiteRefrshListener(ReservationRefrshListener paramReservationRefrshListener)
  {
    if (paramReservationRefrshListener == null)
      return;
    this.refrshListenerList.remove(paramReservationRefrshListener);
  }

  public void userLogin()
  {
    reset();
    Logger.i(TAG, "resetpath:" + GlobalLogic.getInstance().getUserPrivateConfigPath() + File.separator);
  }

  public void userLogout()
  {
    this.dao = null;
    reset();
  }

  public static abstract interface ReservationRefrshListener
  {
    public abstract void onReservationRefrsh();
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.hunan.service.ReservationService
 * JD-Core Version:    0.6.2
 */