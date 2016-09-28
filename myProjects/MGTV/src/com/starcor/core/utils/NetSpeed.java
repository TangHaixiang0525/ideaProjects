package com.starcor.core.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.TrafficStats;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.util.Timer;
import java.util.TimerTask;

public class NetSpeed
{
  private static final String TAG = "NetSpeed";
  private static NetSpeed mNetSpeed;
  private Context mContext;
  private Handler mHandler;
  private Timer mTimer = null;
  private long preRxBytes = 0L;

  private NetSpeed(Context paramContext, Handler paramHandler)
  {
    this.mContext = paramContext;
    this.mHandler = paramHandler;
  }

  public static NetSpeed getInstant(Context paramContext, Handler paramHandler)
  {
    mNetSpeed = new NetSpeed(paramContext, paramHandler);
    return mNetSpeed;
  }

  private long getNetworkRxBytes()
  {
    int i = getUid();
    long l;
    if (i < 0)
      l = 0L;
    do
    {
      return l;
      l = TrafficStats.getUidRxBytes(i);
    }
    while (l != -1L);
    Log.d("NetSpeed", "getUidRxBytes fail !!!");
    return TrafficStats.getTotalRxBytes();
  }

  private int getUid()
  {
    try
    {
      int i = this.mContext.getPackageManager().getApplicationInfo(this.mContext.getPackageName(), 1).uid;
      return i;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      localNameNotFoundException.printStackTrace();
    }
    return -1;
  }

  public int getNetSpeed()
  {
    long l1 = getNetworkRxBytes();
    long l2 = l1 - this.preRxBytes;
    this.preRxBytes = l1;
    int i = (int)Math.floor(0.5D + l2 / 1024L);
    if (i < 0)
      i = 0;
    return i;
  }

  public void startCalculateNetSpeed()
  {
    this.preRxBytes = getNetworkRxBytes();
    if (this.mTimer != null)
    {
      this.mTimer.cancel();
      this.mTimer = null;
    }
    if (this.mTimer == null)
    {
      this.mTimer = new Timer();
      this.mTimer.schedule(new TimerTask()
      {
        public void run()
        {
          Message localMessage = new Message();
          localMessage.what = 1;
          localMessage.arg1 = NetSpeed.this.getNetSpeed();
          NetSpeed.this.mHandler.sendMessage(localMessage);
        }
      }
      , 1000L, 1000L);
    }
  }

  public void stopCalculateNetSpeed()
  {
    if (this.mTimer != null);
    try
    {
      this.mTimer.cancel();
      label14: this.mTimer = null;
      return;
    }
    catch (Exception localException)
    {
      break label14;
    }
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.core.utils.NetSpeed
 * JD-Core Version:    0.6.2
 */