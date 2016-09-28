package com.starcor.settings.download;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.starcor.settings.utils.Debug;
import java.io.PrintStream;

class RealSystemFacade
  implements SystemFacade
{
  private Context mContext;
  private NotificationManager mNotificationManager;

  public RealSystemFacade(Context paramContext)
  {
    this.mContext = paramContext;
    this.mNotificationManager = ((NotificationManager)this.mContext.getSystemService("notification"));
  }

  public void cancelAllNotifications()
  {
  }

  public void cancelNotification(long paramLong)
  {
  }

  public long currentTimeMillis()
  {
    return System.currentTimeMillis();
  }

  public Integer getActiveNetworkType()
  {
    ConnectivityManager localConnectivityManager = (ConnectivityManager)this.mContext.getSystemService("connectivity");
    if (localConnectivityManager == null)
    {
      Debug.w("DownloadManager", "couldn't get connectivity manager");
      return null;
    }
    NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
    System.out.println(localNetworkInfo);
    if (localNetworkInfo == null)
    {
      Debug.v("DownloadManager", "network is not available");
      return null;
    }
    System.out.println(localNetworkInfo);
    return Integer.valueOf(localNetworkInfo.getType());
  }

  public void postNotification(long paramLong, Notification paramNotification)
  {
    this.mNotificationManager.notify((int)paramLong, paramNotification);
  }

  public void sendBroadcast(Intent paramIntent)
  {
    this.mContext.sendBroadcast(paramIntent);
  }
}

/* Location:           C:\Users\THX\Desktop\aa\aa\反编译工具包\out\classes_dex2jar.jar
 * Qualified Name:     com.starcor.settings.download.RealSystemFacade
 * JD-Core Version:    0.6.2
 */